package jp.co.ysk.pixy.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * ログ出力のアスペクトクラス.
 * Created by ko-aoki on 2016/06/28.
 */
@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * publicメソッドのポイントカット.
     */
    @Pointcut("execution(public * *(..))")
    private void anyPublicOperation() {}

    /**
     * serviceパッケージのポイントカット.
     */
    @Pointcut("within(jp.co.ysk.pixy.service..*)")
    private void inService() {}

    /**
     * webパッケージのポイントカット.
     */
    @Pointcut("within(jp.co.ysk.pixy.web..*)")
    private void inWeb() {}

    /**
     * コントローラ、サービスクラスのpublicメソッド実行前にログ出力します.
     * @param jp ジョインポイント
     */
    @Before("anyPublicOperation() && (inService() || inWeb())")
    public void before(JoinPoint jp) {

        Signature sig = jp.getSignature();
        logger.info("メソッド開始：" + sig.getDeclaringTypeName() + "." + sig.getName());

    }

    /**
     * コントローラ、サービスクラスのpublicメソッド実行後にログ出力します.
     * @param jp ジョインポイント
     */
    @After("anyPublicOperation() && (inService() || inWeb())")
    public void after(JoinPoint jp) {

        Signature sig = jp.getSignature();
        logger.info("メソッド終了：" + sig.getDeclaringTypeName() + "." + sig.getName());

    }
}
