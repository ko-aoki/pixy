package jp.co.ysk.pixy.service;

import jp.co.ysk.pixy.PixyApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

/**
 * Created by ko-aoki on 2016/06/09.
 */
@RunWith(SpringJUnit4ClassRunner.class)
// アプリケーションクラスを指定
@SpringApplicationConfiguration(PixyApplication.class)
@WebIntegrationTest
public class GoogleMailServiceTest {

    @Autowired
    GoogleMailService service;

    @Test
    public void testSendMail() throws Exception {

        service.sendMail(Arrays.asList("kaoki@beadsan.com","ye-aoki@beadsan.com"),
                "テストメールです",
                "テストメール本文" + "\r\n" + "改行");
    }
}