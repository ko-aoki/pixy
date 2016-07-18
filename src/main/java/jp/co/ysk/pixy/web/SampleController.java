package jp.co.ysk.pixy.web;

import jp.co.ysk.pixy.entity.TEventTmp;
import jp.co.ysk.pixy.service.ScheduleHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ko-aoki on 2016/07/16.
 */

@RestController
@RequestMapping("/web/sample")
public class SampleController {

    @Autowired
    ScheduleHandlerService service;
    
    @RequestMapping(method = RequestMethod.GET)
    public int getTest() {

        // ログ出力テスト用のサービス実行
        TEventTmp tEventTmpByTEventTmpId = service.getTEventTmpByTEventTmpId(1);

        return 1;
    }

}
