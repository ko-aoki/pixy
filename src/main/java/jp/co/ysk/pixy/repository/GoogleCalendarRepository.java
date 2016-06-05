package jp.co.ysk.pixy.repository;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 *
 * Googleカレンダーを操作するリポジトリクラス.
 * @author ko-aoki
 *
 */
@Repository
public class GoogleCalendarRepository {

	/**
	 * アプリケーション名
	 * Be sure to specify the name of your application. If the application name is {@code null} or
	 * blank, the application will log a warning. Suggested format is "MyCompany-ProductName/1.0".
	 */
	@Value("${pixy.application.name}")
	private String APPLICATION_NAME;

	/** P12ファイル納箇所. */
	@Value("${pixy.p12file.path}")
	private String P12_PATH;

	/** サービスアカウントID */
	@Value("${pixy.service.account.id}")
	private String SERVICE_ACCOUNT_ID;

	/** サービスアカウントユーザ */
	@Value("${pixy.service.account.user}")
	private String SERVICE_ACCOUNT_USER;

	/** サービスアカウントユーザの資格情報 */
	private Credential CREDENTIAL_SERVICE_ACCOUNT_USER = null;

	final HttpTransport TRANSPORT = new NetHttpTransport();
	final JsonFactory JSON_FACTORY = new JacksonFactory();

	/** 資格情報 */
	private GoogleCredential credential = null;

	/**
	 * サービスアカウントユーザの資格情報を設定します.
	 * @return 資格情報
	 */
	private void authorizeServiceAccountUser() {

		if (CREDENTIAL_SERVICE_ACCOUNT_USER != null) {
			return;
		}
		try {
			this.CREDENTIAL_SERVICE_ACCOUNT_USER = new GoogleCredential.Builder()
					.setTransport(TRANSPORT)
					.setJsonFactory(JSON_FACTORY)
					.setServiceAccountId(SERVICE_ACCOUNT_ID)
					.setServiceAccountPrivateKeyFromP12File(new File(P12_PATH))
					.setServiceAccountScopes(Collections.singleton(CalendarScopes.CALENDAR))
					.setServiceAccountUser(SERVICE_ACCOUNT_USER)
					.build();
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 資格情報を設定します.
	 * @param account 操作したいカレンダーのアカウント
	 * @return 資格情報
	 */
	private void authorize(String account) {

		// 同一資格情報なら作成しない
		if (this.credential != null && account.equals(credential.getServiceAccountUser())) {
			return;
		}
		try {
			this.credential = new GoogleCredential.Builder()
					.setTransport(TRANSPORT)
					.setJsonFactory(JSON_FACTORY)
					.setServiceAccountId(SERVICE_ACCOUNT_ID)
					.setServiceAccountPrivateKeyFromP12File(new File(P12_PATH))
					.setServiceAccountScopes(Collections.singleton(CalendarScopes.CALENDAR))
					.setServiceAccountUser(account)
					.build();
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * カレンダークライアントクラスを作成します.
	 * @param credential 資格情報
	 * @return
	 */
	private com.google.api.services.calendar.Calendar createClient(Credential credential) {

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
	 * @param start 開始日時(yyyy-MM-dd'T'HH:mm)
	 * @param end 終了日時(yyyy-MM-dd'T'HH:mm)
	 * @param events イベントリスト
	 * @return true: あり false:なし
	 * @throws ParseException
     */
	private boolean isExistEvent(String start, String end, List<Event> events) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		Date tgtStart = sdf.parse(start);
		Date tgtEnd = sdf.parse(end);

		for (Event event : events) {
            Date eventStart = new Date(event.getStart().getDateTime().getValue());
            Date eventEnd = new Date(event.getEnd().getDateTime().getValue());
            if (tgtStart.compareTo(eventEnd) < 0 &&
					tgtEnd.compareTo(eventStart) > 0 &&
                    tgtEnd.compareTo(eventEnd) < 0) {
				return true;
            }
            if (tgtStart.compareTo(eventStart) < 0 &&
                    tgtEnd.compareTo(eventEnd) > 0) {
				return true;
            }
            if (tgtStart.compareTo(eventStart) > 0 &&
                    tgtEnd.compareTo(eventEnd) < 0) {
				return true;
            }
            if (tgtStart.compareTo(eventStart) > 0 &&
					tgtStart.compareTo(eventEnd) < 0 &&
					tgtEnd.compareTo(eventEnd) > 0) {
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
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
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
	 * サービスアカウントユーザにイベントを削除します.
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
		try {
			com.google.api.services.calendar.Calendar client = this.createClient(this.credential);
			client.events().delete(account, id).execute();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
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
