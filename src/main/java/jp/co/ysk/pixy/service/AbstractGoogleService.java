package jp.co.ysk.pixy.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collection;

/**
 * Google APIを使用する抽象クラス.
 *
 * Created by ko-aoki on 2016/06/08.
 */
public abstract class AbstractGoogleService {

	/**
	 * アプリケーション名
	 * Be sure to specify the name of your application. If the application name is {@code null} or
	 * blank, the application will log a warning. Suggested format is "MyCompany-ProductName/1.0".
	 */
	@Value("${pixy.application.name}")
	protected String APPLICATION_NAME;

	/** 資格情報 */
	protected GoogleCredential credential = null;

	/** P12ファイル納箇所. */
	@Value("${pixy.p12file.path}")
	protected String P12_PATH;

	/** サービスアカウントID */
	@Value("${pixy.service.account.id}")
	protected String SERVICE_ACCOUNT_ID;

	/** サービスアカウントユーザ */
	@Value("${pixy.service.account.user}")
	protected String SERVICE_ACCOUNT_USER;

	/** サービスアカウントユーザの資格情報 */
	protected GoogleCredential CREDENTIAL_SERVICE_ACCOUNT_USER = null;

	/**
	 * 資格情報を生成します.
	 * @param serviceAccountUser サービスアカウントユーザ
	 * @param scopes 資格情報のスコープ
	 * @return 資格情報
	 */
	public GoogleCredential create(String serviceAccountUser, Collection<String> scopes) {

		HttpTransport TRANSPORT = new NetHttpTransport();
		JsonFactory JSON_FACTORY = new JacksonFactory();
		try {
			return new GoogleCredential.Builder()
					.setTransport(TRANSPORT)
					.setJsonFactory(JSON_FACTORY)
					.setServiceAccountId(SERVICE_ACCOUNT_ID)
					.setServiceAccountPrivateKeyFromP12File(new File(P12_PATH))
					.setServiceAccountScopes(scopes)
					.setServiceAccountUser(serviceAccountUser)
					.build();
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * サービスアカウントユーザの資格情報を設定します.
	 * @return 資格情報
	 */
	protected void authorizeServiceAccountUser() {

		if (CREDENTIAL_SERVICE_ACCOUNT_USER != null) {
			return;
		}
		this.CREDENTIAL_SERVICE_ACCOUNT_USER =
				this.create(SERVICE_ACCOUNT_USER,
						Arrays.asList(
								CalendarScopes.CALENDAR,
								GmailScopes.GMAIL_COMPOSE
								));
	}

	/**
	 * 資格情報を設定します.
	 * @param account 操作したいカレンダーのアカウント
	 * @return 資格情報
	 */
	protected void authorize(String account) {

		// 同一資格情報なら作成しない
		if (this.credential != null && account.equals(credential.getServiceAccountUser())) {
			return;
		}
		this.credential =
				this.create(account,
						Arrays.asList(
								CalendarScopes.CALENDAR,
								GmailScopes.GMAIL_COMPOSE
						));
	}
}
