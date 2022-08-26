package daone.bmap.tools;

import daone.bmap.service.ParkService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class ParkDataAutoRenew {

    private final ParkService parkService;

    @Scheduled(cron = "0 0 0 * * *")        //cron = 초 분 시 일 월 요일
    public void renewParkData() throws IOException {
        parkService.saveParkData();
    }
}
