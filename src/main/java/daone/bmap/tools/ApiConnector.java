package daone.bmap.tools;

import daone.bmap.domain.park.Park;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class ApiConnector {

    @Value("${api.serviceKey}")
    private String serviceKey;

    @Value("${api.type}")
    private String type;

    @Value("${api.numOfRows}")
    private String numOfRows;

    private String apiUrl;

    @PostConstruct
    protected void init() {
        apiUrl = "http://api.data.go.kr/openapi/tn_pubr_prkplce_info_api?serviceKey=" + serviceKey + "&type=" + type + "&numOfRows=" + numOfRows+ "&pageNo=";
    }

    public List<Park> getAllData() throws IOException {
        int pageNo = 1;
        JSONArray itemsObject;
        ArrayList<Park> parks = new ArrayList<>();
        URL url;
        HttpURLConnection conn;
        InputStream result;
        JSONObject connResult;

        do {
            String nowPageApiUrl = apiUrl+pageNo++;
            System.out.println("apiUrl = " + nowPageApiUrl);
            url = new URL(nowPageApiUrl);
            conn = (HttpURLConnection) url.openConnection();

            result = conn.getInputStream();
            connResult = getJsonResponse(result);

            validateApiIsSafety(connResult);
            itemsObject = getItems(connResult);

            addParkData(itemsObject, parks);

            //System.out.println("pageNo = " + pageNo * Integer.parseInt(numOfRows));
            //System.out.println("itemsObject = " + itemsObject.length());
        } while (itemsObject.length() == 100);
        return parks;
    }

    private void addParkData(JSONArray itemsObject, ArrayList<Park> parks) {
        JSONObject jsonObject;
        String longitude;
        String latitude;
        String prkplceNo;
        for (int i = 0; i < itemsObject.length(); i++) {
            jsonObject = itemsObject.getJSONObject(i);
            longitude = jsonObject.getString("longitude");
            latitude = jsonObject.getString("latitude");
            prkplceNo = jsonObject.getString("prkplceNo");

            if(latitude == null || longitude == null || latitude.isEmpty() || longitude.isEmpty()) {
                continue;
            }
            if(checkDuplicate(parks, prkplceNo)) {
                continue;
            }

            Park park = Park.builder()
                    .prkplceNo(prkplceNo)
                    .prkplceNm(jsonObject.getString("prkplceNm"))
                    .prkplceSe(jsonObject.getString("prkplceSe"))
                    .prkplceType(jsonObject.getString("prkplceType"))
                    .rdnmadr(jsonObject.getString("rdnmadr"))
                    .lnmadr(jsonObject.getString("lnmadr"))
                    .prkcmprt(jsonObject.getString("prkcmprt"))
                    .feedingSe(jsonObject.getString("feedingSe"))
                    .enforceSe(jsonObject.getString("enforceSe"))
                    .operDay(jsonObject.getString("operDay"))
                    .weekdayOperOpenHhmm(jsonObject.getString("weekdayOperOpenHhmm"))
                    .weekdayOperCloseHhmm(jsonObject.getString("weekdayOperColseHhmm"))
                    .satOperOperOpenHhmm(jsonObject.getString("satOperOperOpenHhmm"))
                    .satOperCloseHhmm(jsonObject.getString("satOperCloseHhmm"))
                    .holidayOperOpenHhmm(jsonObject.getString("holidayOperOpenHhmm"))
                    .holidayCloseOpenHhmm(jsonObject.getString("holidayCloseOpenHhmm"))
                    .parkingchrgeInfo(jsonObject.getString("parkingchrgeInfo"))
                    .basicTime(jsonObject.getString("basicTime"))
                    .basicCharge(jsonObject.getString("basicCharge"))
                    .addUnitTime(jsonObject.getString("addUnitTime"))
                    .addUnitCharge(jsonObject.getString("addUnitCharge"))
                    .dayCmmtktAdjTime(jsonObject.getString("dayCmmtktAdjTime"))
                    .dayCmmtkt(jsonObject.getString("dayCmmtkt"))
                    .monthCmmtkt(jsonObject.getString("monthCmmtkt"))
                    .metpay(jsonObject.getString("metpay"))
                    .spcmnt(jsonObject.getString("spcmnt"))
                    .institutionNm(jsonObject.getString("institutionNm"))
                    .phoneNumber(jsonObject.getString("phoneNumber"))
                    .latitude(latitude)
                    .longitude(longitude)
                    .referenceDate(jsonObject.getString("referenceDate"))
                    .insttCode(jsonObject.getString("insttCode"))
                    .build();

            parks.add(park);
        }
    }

    private boolean checkDuplicate(ArrayList<Park> parks, String prkplceNo) {
        return parks.stream()
                .filter(p -> p.getPrkplceNo().equals(prkplceNo))
                .findAny()
                .isPresent();
    }

    private static JSONArray getItems(JSONObject connResult) {
        return connResult.getJSONObject("body").getJSONArray("items");
    }

    private JSONObject getJsonResponse(InputStream input) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(
                new InputStreamReader(input,"utf-8"));
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        //System.out.println("sb.toString() = " + sb.toString());

        String jsonString = sb.toString();

        return new JSONObject(jsonString).getJSONObject("response");
    }

    private void validateApiIsSafety(JSONObject connResult) {
        String resultCode = connResult.getJSONObject("header").getString("resultCode");
        if (!resultCode.equals("00")) {
            throw new IllegalArgumentException("API 요청이 잘못되었습니다. [resultCode: "+resultCode+"]");
        }
    }
}
