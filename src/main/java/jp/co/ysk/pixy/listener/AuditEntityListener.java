package jp.co.ysk.pixy.listener;

import jp.co.ysk.pixy.entity.AuditEntity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * 監査項目(共通カラム)を設定するリスナークラス.
 * Created by ko-aoki on 2016/06/16.
 */
public class AuditEntityListener {

    @PrePersist
    public void prePersist(AuditEntity entity) {

        entity.setUpdateDatetime(new Date());
    }

    @PreUpdate
    public void preUpdate(AuditEntity entity) {

        entity.setUpdateDatetime(new Date());
    }
}
