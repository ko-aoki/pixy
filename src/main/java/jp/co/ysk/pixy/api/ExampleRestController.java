package jp.co.ysk.pixy.api;

import jp.co.ysk.pixy.dto.ExampleDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ko-aoki on 2015/12/23.
 */
@RestController
@RequestMapping("/api/example")
public class ExampleRestController {

    @RequestMapping(method = RequestMethod.GET)
    ExampleDto getMessage() {

        ExampleDto exDto = new ExampleDto();
        exDto.setMessage("pixyです！");

        return exDto;
    }

}
