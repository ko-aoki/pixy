package jp.co.ysk.pixy.entity;

import jp.co.ysk.pixy.listener.AuditEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * 監査項目(共通カラム)部分のエンティティ.
 * Created by ko-aoki on 2016/06/16.
 */
@MappedSuperclass
@EntityListeners(AuditEntityListener.class)
public class AuditEntity {

    @Column(name = "version")
    @Version
    private Integer version;

    @Column(name = "update_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDatetime;

    @Column(name = "update_id")
    private Integer updateId;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }
}
