package jp.co.ysk.pixy.service;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import jp.co.ysk.pixy.PixyApplication;
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
public class GoogleCalendarSharedServiceTest {
    @Autowired
    GoogleCalendarSharedService service;

    @Test
    public void listServiceAccountUserCalendarEvent() throws Exception {
        List<Event> events = service.listServiceAccountUserCalendarEvent();
        for(Event event : events) {
            System.out.println(event);
        }
    }

    @Test
    public void listResource(){

        List<CalendarListEntry> calendarListEntries = service.listResource();
        for (CalendarListEntry entry : calendarListEntries) {
            System.out.println(entry);
        }
    }

    @Test
    public void listCalendarEvent() {
        List<Event> events = service.listCalendarEvent("kaoki@beadsan.com");
//        List<Event> events = service.listCalendarEvent("ye-aoki@beadsan.com");
        for(Event event : events) {
            System.out.println(event);
        }
    }

    @Test
    public void addEvent() {

        Event event = new Event();
        event.setSummary("テストクラスで作成したイベントです");
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 3600000);
        DateTime start = new DateTime(startDate, TimeZone.getTimeZone("UTC"));
        event.setStart(new EventDateTime().setDateTime(start));
        DateTime end = new DateTime(endDate, TimeZone.getTimeZone("UTC"));
        event.setEnd(new EventDateTime().setDateTime(end));
        // 明示しないと表示されない
        event.setLocation("会議室A");
        EventAttendee eventAttendee = new EventAttendee();
        // リソースのメールアドレス
        eventAttendee.setEmail("beadsan.com_35323735313334362d393331@resource.calendar.google.com");
        event.setAttendees(Arrays.asList(eventAttendee));
        Event result = service.addEvent("kaoki@beadsan.com", event);
        System.out.println(result.getId());
    }

    @Test
    public void deleteServiceAccountEvent() {

        service.deleteServiceAccountEvent("1gnkfqa6r71hd0v4kjnm7ptmbk");
    }


    @Test
    public void updateEvent() {

        Event event = new Event();
        event.setSummary("更新前イベントです");
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 3600000);
        DateTime start = new DateTime(startDate, TimeZone.getTimeZone("UTC"));
        event.setStart(new EventDateTime().setDateTime(start));
        DateTime end = new DateTime(endDate, TimeZone.getTimeZone("UTC"));
        event.setEnd(new EventDateTime().setDateTime(end));
        // 明示しないと表示されない
        event.setLocation("会議室A");
        EventAttendee eventAttendee = new EventAttendee();
        eventAttendee.setId("52751346-931");
        // リソースのメールアドレス
        eventAttendee.setEmail("beadsan.com_35323735313334362d393331@resource.calendar.google.com");
        event.setAttendees(Arrays.asList(eventAttendee));
        Event result = service.addEvent("kaoki@beadsan.com", event);

        result.setSummary("更新後イベント");
        result.setDescription("更新後");
        Event update = service.updateEvent("kaoki@beadsan.com", result);
        System.out.println(
                "-------------------------------------------------------------------");
        System.out.println(update.getStatus());
    }

    @Test
    public void isExistEvent() {

        System.out.println();
        System.out.println(
                "-------------------------------------------------------------------");
        String account = "kaoki@beadsan.com";
        String start = "2016-06-04T13:00";
        String end = "2016-06-04T14:00";
        System.out.println(
                service.isExistEvent(account,
                        start, end)
        );
        start = "2016-06-04T14:22";
        end = "2016-06-04T14:42";
        System.out.println(
                service.isExistEvent(account,
                        start, end)
        );
        start = "2016-06-04T14:40";
        end = "2016-06-04T14:43";
        System.out.println(
                service.isExistEvent(account,
                        start, end)
        );
        start = "2016-06-04T14:40";
        end = "2016-06-04T14:42";
        System.out.println(
                service.isExistEvent(account,
                        start, end)
        );
        start = "2016-06-04T15:42";
        end = "2016-06-04T15:43";
        System.out.println(
                service.isExistEvent(account,
                        start, end)
        );
        start = "2016-06-04T15:46";
        end = "2016-06-04T15:47";
        System.out.println(
                service.isExistEvent(account,
                        start, end)
        );
    }

    @Test
    public void isExistResourceEvent() {

        System.out.println();
        System.out.println(
                "-------------------------------------------------------------------");
        String resourceAccount = "beadsan.com_35323735313334362d393331@resource.calendar.google.com";
        String start = "2016-06-04T13:00";
        String end = "2016-06-04T14:00";
        System.out.println(
                service.isExistResourceEvent(resourceAccount,
                        start, end)
        );
        start = "2016-06-04T14:40";
        end = "2016-06-04T14:43";
        System.out.println(
                service.isExistResourceEvent(resourceAccount,
                        start, end)
        );
        start = "2016-06-04T14:40";
        end = "2016-06-04T14:42";
        System.out.println(
                service.isExistResourceEvent(resourceAccount,
                        start, end)
        );
        start = "2016-06-04T15:42";
        end = "2016-06-04T15:43";
        System.out.println(
                service.isExistResourceEvent(resourceAccount,
                        start, end)
        );
        start = "2016-06-04T15:46";
        end = "2016-06-04T15:47";
        System.out.println(
                service.isExistResourceEvent(resourceAccount,
                        start, end)
        );
    }
}