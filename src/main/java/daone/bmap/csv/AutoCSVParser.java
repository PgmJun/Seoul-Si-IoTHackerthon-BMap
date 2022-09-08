package daone.bmap.csv;

import com.opencsv.exceptions.CsvValidationException;
import daone.bmap.service.ParkService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
@Component
public class AutoCSVParser {

    private final ParkService parkService;

    @Scheduled(cron = "0 0 0 * * *")        //cron = 초 분 시 일 월 요일
    public void parse() throws CsvValidationException, IOException {

        Runtime runtime = Runtime.getRuntime();

        Process p1 = runtime.exec("cmd /c start D:\\github\\Seoul-Si-IoTHackerthon-BMap\\src\\main\\java\\daone\\bmap\\csv\\autoCsvInstall.bat");
        InputStream is = p1.getInputStream();
        int i = 0;
        while ((i = is.read()) != -1) {
            System.out.print((char) i);
        }
        parkService.saveParkData();
    }
}
