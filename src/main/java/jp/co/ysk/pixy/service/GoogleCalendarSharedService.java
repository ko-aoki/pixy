package jp.co.ysk.pixy.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * Googleカレンダーを操作するサービスクラス.
 * @author ko-aoki
 *
 */
@Service
public class GoogleCalendarSharedService extends AbstractGoogleService {

	/**
	 * カレンダークライアントクラスを作成します.
	 * @param credential 資格情報
	 * @return
	 */
	private com.google.api.services.calendar.Calendar createClient(Credential credential) {

		HttpTransport TRANSPORT = new NetHttpTransport();
		JsonFactory JSON_FACTORY = new JacksonFactory();

		com.google.api.services.calendar.Calendar client
				= new com.google.api.services.calendar.Calendar.Builder(TRANSPORT, JSON_FACTORY, credential)
				.setApplicationName(APPLICATION_NAME)
				.build();

		return client;
	}

	/**
	 * サービスアカウントユーザのイベントリストを取得します.
	 * @return イベントリスト
     */
	public List<Event> listServiceAccountUserCalendarEvent() {

		this.authorizeServiceAccountUser();
		com.google.api.services.calendar.Calendar client = this.createClient(CREDENTIAL_SERVICE_ACCOUNT_USER);
		return this.getEventItems(SERVICE_ACCOUNT_USER, client);
	}

	/**
	 * リソースリストを取得します.
	 * @return リソースリスト
	 */
	public List<CalendarListEntry> listResource() {

		List<CalendarListEntry> resourceList = new ArrayList<>();
		this.authorizeServiceAccountUser();
		com.google.api.services.calendar.Calendar client = this.createClient(CREDENTIAL_SERVICE_ACCOUNT_USER);
		CalendarList feed = null;
		try {
			// カレンダーリストの取得
			feed = client.calendarList().list().execute();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		for (CalendarListEntry entry : feed.getItems()) {
			if (entry.getId().endsWith("resource.calendar.google.com")) {
				resourceList.add(entry);
			}
		}
		return  resourceList;
	}

	/**
	 * アカウントのイベントリストを取得します.
	 * @param account アカウント
	 * @return イベントリスト
	 */
	public List<Event> listCalendarEvent(String account) {

		this.authorize(account);
		com.google.api.services.calendar.Calendar client = this.createClient(this.credential);
		return this.getEventItems(account, client);
	}

	/**
	 * リソースのイベントリストを取得します.
	 * @param resourceAccount リソースアカウント
	 * @return イベントリスト
	 */
	public List<Event> listResourceCalendarEvent(String resourceAccount) {

		this.authorizeServiceAccountUser();
		com.google.api.services.calendar.Calendar client = this.createClient(this.CREDENTIAL_SERVICE_ACCOUNT_USER);
		return this.getResourceEventItems(resourceAccount, client);
	}

	/**
	 * アカウントの範囲内のイベント有無を判定します.
	 * @param account アカウント
	 * @param start 開始日時(yyyy-MM-dd'T'HH:mm)
	 * @param end 終了日時(yyyy-MM-dd'T'HH:mm)
     * @return true: あり false:なし
     */
	public boolean isExistEvent(String account, String start, String end) {

		try {
			List<Event> events = this.listCalendarEvent(account);
			return this.isExistEvent(start, end, events);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * リソースの範囲内のイベント有無を判定します.
	 * @param resourceAccount リソースアカウント
	 * @param start 開始日時(yyyy-MM-dd'T'HH:mm)
	 * @param end 終了日時(yyyy-MM-dd'T'HH:mm)
	 * @return true: あり false:なし
	 */
	public boolean isExistResourceEvent(String resourceAccount, String start, String end) {

		try {
			List<Event> events = this.listResourceCalendarEvent(resourceAccount);
			return this.isExistEvent(start, end, events);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 指定された範囲内のイベントの有無を判定します.
	 * @param start 開始日時(yyyy/MM/dd HH:mm)
	 * @param end 終了日時(yyyy/MM/dd HH:mm)
	 * @param events イベントリスト
	 * @return true: あり false:なし
	 * @throws ParseException
     */
	private boolean isExistEvent(String start, String end, List<Event> events) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Date tgtStart = sdf.parse(start);
		Date tgtEnd = sdf.parse(end);

		for (Event event : events) {
            Date eventStart = new Date(event.getStart().getDateTime().getValue());
            Date eventEnd = new Date(event.getEnd().getDateTime().getValue());
            if (tgtStart.compareTo(eventEnd) <= 0 &&
					tgtEnd.compareTo(eventStart) >= 0 &&
                    tgtEnd.compareTo(eventEnd) <= 0) {
				return true;
            }
            if (tgtStart.compareTo(eventStart) <= 0 &&
                    tgtEnd.compareTo(eventEnd) >= 0) {
				return true;
            }
            if (tgtStart.compareTo(eventStart) >= 0 &&
                    tgtEnd.compareTo(eventEnd) <= 0) {
				return true;
            }
            if (tgtStart.compareTo(eventStart) >= 0 &&
					tgtStart.compareTo(eventEnd) <= 0 &&
					tgtEnd.compareTo(eventEnd) >= 0) {
				return true;
            }
        }
		return false;
	}

	/**
	 * サービスアカウントユーザにイベントを追加します.
	 * @param event イベント
	 * @return イベント(追加後)
	 */
	public Event addServiceAccountEvent(Event event) {

		this.authorizeServiceAccountUser();
		com.google.api.services.calendar.Calendar client = this.createClient(CREDENTIAL_SERVICE_ACCOUNT_USER);
		try {
			return client.events().insert(SERVICE_ACCOUNT_USER, event).execute();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 指定されたアカウントにイベントを追加します.
	 * @param account アカウント
	 * @param event イベント
     * @return 追加したイベント
     */
	public Event addEvent(String account, Event event) {

		this.authorize(account);
		com.google.api.services.calendar.Calendar client = this.createClient(this.credential);
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			if (
				this.isExistEvent(
						account,
						sdf.format(new Date(event.getStart().getDateTime().getValue())),
						sdf.format(new Date(event.getEnd().getDateTime().getValue())))
					) {
				return null;
			}
			return client.events().insert(account, event).execute();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * サービスアカウントユーザのイベントを削除します.
	 * @param id イベントID
	 */
	public void deleteServiceAccountEvent(String id) {

		this.authorizeServiceAccountUser();
		com.google.api.services.calendar.Calendar client = this.createClient(CREDENTIAL_SERVICE_ACCOUNT_USER);
		try {
			client.events().delete(SERVICE_ACCOUNT_USER, id).execute();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 指定されたアカウントのイベントを削除します.
	 * @param account アカウント
	 * @param id イベントID
	 */
	public void deleteEvent(String account, String id) {

		this.authorize(account);
		com.google.api.services.calendar.Calendar client = this.createClient(this.credential);
		try {
			client.events().delete(account, id).execute();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * サービスアカウントユーザのイベントを更新します.
	 * @param event イベント
	 * @return 更新したイベント
	 */
	public Event updateServiceAccountEvent(Event event) {

		this.authorizeServiceAccountUser();
		com.google.api.services.calendar.Calendar client = this.createClient(CREDENTIAL_SERVICE_ACCOUNT_USER);
		Event update = null;
		try {
			Event tgtEvent = client.events().get(SERVICE_ACCOUNT_USER, event.getId()).execute();

			this.mapEvent(tgtEvent, event);
			update = client.events().update(SERVICE_ACCOUNT_USER, event.getId(), event).execute();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return update;
	}

	private void mapEvent(Event tgtEvent, Event newEvent) {

		// TODO 設定項目検討
		tgtEvent.setSummary(newEvent.getSummary());
		tgtEvent.setLocation(newEvent.getLocation());
		tgtEvent.setAttendees(newEvent.getAttendees());
		tgtEvent.setStart(newEvent.getStart());
		tgtEvent.setEnd(newEvent.getEnd());
	}

	/**
	 * 指定されたアカウントのイベントを更新します.
	 * @param account アカウント
	 * @param event イベント
	 * @param event 更新したイベント
	 */
	public Event updateEvent(String account, Event event) {

		this.authorize(account);
		Event update = null;
		com.google.api.services.calendar.Calendar client = this.createClient(this.credential);
		try {
			Event tgtEvent = client.events().get(account, event.getId()).execute();
			this.mapEvent(tgtEvent, event);
			update = client.events().update(account, tgtEvent.getId(), tgtEvent).execute();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return update;
	}

	/**
	 * 資格情報からイベントリストを取得します.
	 * @param resourceAccount リソースアカウント
	 * @param client カレンダー
	 * @return イベントリスト
	 */
	private List<Event> getResourceEventItems(String resourceAccount, com.google.api.services.calendar.Calendar client) {
		List<Event> items = new ArrayList<Event>();
		try {
			// イベントの取得
			Events events = client.events().list(resourceAccount).execute();
			items = events.getItems();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return items;
	}

	/**
	 * 資格情報からイベントリストを取得します.
	 * @param account アカウント
	 * @param client カレンダー
	 * @return イベントリスト
     */
	private List<Event> getEventItems(String account, com.google.api.services.calendar.Calendar client) {
		List<Event> items = new ArrayList<Event>();
		try {
			// カレンダーリストの取得
			CalendarList feed = client.calendarList().list().execute();
			CalendarListEntry targetCal = null;
			for (CalendarListEntry entry : feed.getItems()) {
				if (account.equals(entry.getId())) {
					targetCal = entry;
					break;
				}
			}

			// イベントの取得
			Events events = client.events().list(targetCal.getId()).execute();
			items = events.getItems();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return items;
	}

}
