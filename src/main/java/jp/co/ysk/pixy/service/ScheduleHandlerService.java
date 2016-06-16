package jp.co.ysk.pixy.service;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import jp.co.ysk.pixy.dto.ScheduleRegisterDto;
import jp.co.ysk.pixy.entity.MEmployee;
import jp.co.ysk.pixy.entity.TAttendees;
import jp.co.ysk.pixy.entity.TEventTmp;
import jp.co.ysk.pixy.repository.MEmployeeRepository;
import jp.co.ysk.pixy.repository.TEventTmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * スケジュールを操作するサービスクラス.
 * Created by ko-aoki on 2016/06/15.
 */
@Service
@Transactional
public class ScheduleHandlerService {

    /** Googleカレンダーサービス */
    @Autowired
    private GoogleCalendarSharedService service;

    @Autowired
    private TEventTmpRepository eventRepo;

    @Autowired
    private MEmployeeRepository empRepo;

    /**
     * スケジュールを登録します.
     * @param dto スケジュール登録DTO
     * @return メッセージ
     */
    public String registerSchedule(ScheduleRegisterDto dto) {

        MEmployee chargePerson = empRepo.findOne(dto.getChargePersonId());
        if (chargePerson == null) {
            throw new RuntimeException("存在しないユーザでスケジュール登録されています " +
                    dto.getChargePersonId()
            );
        }

        if (
                service.isExistEvent(
                        chargePerson.getCalendarId(),
                        dto.getStartDate() + " " + dto.getStartTime(),
                        dto.getEndDate() + " "  + dto.getEndTime())
                ){
            return "同時間帯にスケジュールが存在しています。";
        }

        Event event = this.mapScheduleRegisterDtoToEvent(dto);

        // Googleカレンダーの更新
        Event eventResult = service.addEvent(chargePerson.getCalendarId(), event);

        TEventTmp tEvent = new TEventTmp();

        tEvent.setSummary(dto.getSummery());
        tEvent.setEventId(eventResult.getId());
        tEvent.setCalendarId(chargePerson.getCalendarId());
        tEvent.setStartDatetime(new Date(event.getStart().getDateTime().getValue()));
        tEvent.setEndDatetime(new Date(event.getEnd().getDateTime().getValue()));
        eventRepo.save(tEvent);
        // oneToManyは永続化した後で
        List<TAttendees> tAttendees = new ArrayList<>();
        for (String guestId: dto.getGuestIdList()) {
            TAttendees tAttendee = new TAttendees();
            tAttendee.setEventId(eventResult.getId());
            tAttendee.setTEventTmpId(tEvent);
            tAttendee.setEmail(guestId);
            tAttendees.add(tAttendee);
        }
        tEvent.setTAttendeesCollection(tAttendees);

        return null;
    }

    private Event mapScheduleRegisterDtoToEvent(ScheduleRegisterDto dto) {
        Event event = new Event();
        event.setSummary(dto.getSummery());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        try {
            EventDateTime startEdt = new EventDateTime();
            startEdt.setDateTime(new DateTime(sdf.parse(dto.getStartDate() + " " + dto.getStartTime())));
            event.setStart(startEdt);
            EventDateTime endEdt = new EventDateTime();
            endEdt.setDateTime(new DateTime(sdf.parse(dto.getEndDate() + " "  + dto.getEndTime())));
            event.setEnd(endEdt);
            List<EventAttendee> eventAttendeeList = new ArrayList<>();
            for (String guestId: dto.getGuestIdList()) {
                EventAttendee attendee = new EventAttendee();
                attendee.setEmail(guestId);
                eventAttendeeList.add(attendee);
            }
            event.setAttendees(eventAttendeeList);
            // TODO リソースマスタ
//            event.setLocation();
        } catch (ParseException e) {
            throw new IllegalArgumentException(
                    "開始：" + dto.getStartDate() + " " + dto.getStartTime() +
                            "終了：" + dto.getEndDate() + " " + dto.getEndTime()
            );
        }
        return event;
    }
}
