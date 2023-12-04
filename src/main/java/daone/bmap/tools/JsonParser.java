package daone.bmap.tools;

import daone.bmap.domain.park.Park;
import daone.bmap.domain.park.ParkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JsonParser {
    private final ApiConnector connector;
    private final ParkRepository parkRepository;

    public void parseJsonParkData() throws IOException {
        List<Park> parks = connector.getAllData();
        for (Park park : parks) {
            parkRepository.save(park);
        }

    }
}
