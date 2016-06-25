package jp.co.ysk.pixy.service;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import jp.co.ysk.pixy.PixyApplication;
import jp.co.ysk.pixy.dto.ScheduleDto;
import jp.co.ysk.pixy.dto.ScheduleListCondDto;
import jp.co.ysk.pixy.dto.ScheduleRegisterDto;
import jp.co.ysk.pixy.entity.TEventTmp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by ko-aoki on 2016/06/03.
 */
@RunWith(SpringJUnit4ClassRunner.class)
// アプリケーションクラスを指定
@SpringApplicationConfiguration(PixyApplication.class)
@WebIntegrationTest
public class ScheduleHandlerServiceTest {
    @Autowired
    ScheduleHandlerService service;


    @Test
    public void getxx() {

        TEventTmp tEventTmpByTEventTmpId = service.getTEventTmpByTEventTmpId(7);
        System.out.println(tEventTmpByTEventTmpId);
    }

    @Test
    public void findSchedule() {

        ScheduleListCondDto cond = new ScheduleListCondDto();
        cond.setSearchDate("2016/06/16");
        cond.setCompanyName("テスト");
        cond.setVisitorName("");
        List<ScheduleDto> list = service.findSchedule(cond);
        System.out.println(list);
    }

    @Test
    public void listServiceAccountUserCalendarEvent() throws Exception {

        ScheduleRegisterDto dto = new ScheduleRegisterDto();
        dto.setChargePersonId("00001");
        dto.setStartDate("2016/06/16");
        dto.setStartTime("21:00");
        dto.setEndDate("2016/06/16");
        dto.setEndTime("21:30");
        dto.setSummery("スケジュールテスト");
        dto.setGuestIdList(Arrays.asList("kaoki@beadsan.com"));

        String s = service.registerSchedule(dto);
        System.out.println(s);
    }

}