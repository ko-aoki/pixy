package jp.co.ysk.pixy.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * GMailAPIを操作するサービスクラス.
 * Created by ko-aoki on 2016/06/07.
 */
@Service
public class GoogleMailService extends AbstractGoogleService {

    /**
     * GMailクライアントクラスを作成します.
     * @param credential 資格情報
     * @return
     */
    private Gmail createClient(Credential credential) {

        HttpTransport TRANSPORT = new NetHttpTransport();
        JsonFactory JSON_FACTORY = new JacksonFactory();

        return new Gmail.Builder(TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * Create a MimeMessage using the parameters provided.
     *
     * @param toList 送信先リスト
     * @param subject 表題
     * @param bodyText 本文
     * @return MimeMessage.
     * @throws MessagingException
     */
    public MimeMessage createEmail(List<String> toList, String subject,
                                   String bodyText) throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(this.SERVICE_ACCOUNT_USER));
        for (String to: toList) {
            email.addRecipient(javax.mail.Message.RecipientType.TO,
                    new InternetAddress(to));
        }
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }

    /**
     * Create a Message from an email
     *
     * @param email MimeMessage
     * @return base64urlエンコードしたMimeMessage.
     * @throws IOException
     * @throws MessagingException
     */
    public Message createMessageWithEmail(MimeMessage email)
            throws MessagingException, IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        email.writeTo(bytes);
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes.toByteArray());
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }

    /**
     * メールを送信します。送信元は代表のサービスアカウントです.
     *
     * @param toList 送信先リスト
     * @param subject 表題
     * @param bodyText 本文
     */
    public void sendMail(List<String> toList, String subject, String bodyText) {

        this.authorizeServiceAccountUser();
        Gmail client = this.createClient(this.CREDENTIAL_SERVICE_ACCOUNT_USER);
        try {
            MimeMessage message = this.createEmail(toList, subject, bodyText);
            client.users().messages().send(
                    this.SERVICE_ACCOUNT_USER,this.createMessageWithEmail(message)).execute();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
