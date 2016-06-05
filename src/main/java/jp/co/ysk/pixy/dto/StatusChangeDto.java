package jp.co.ysk.pixy.dto;

import lombok.Data;

/**
 * Created by ko-aoki on 2015/12/23.
 */
@Data
public class StatusChangeDto {

    /**
     * ディスプレイID
     */
    private String displayId;
    
    /**
     * ステータスコード
     */
    private String statusCd;

}