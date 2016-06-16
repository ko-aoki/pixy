package jp.co.ysk.pixy.dto;

import java.util.List;

/**
 * スケジュール登録DAOクラス
 * Created by ko-aoki on 2016/06/15.
 */
public class ScheduleRegisterDto {

    /** タイトル */
    private String summery;
    /** 開始日 */
    private String startDate;
    /** 開始時刻 */
    private String startTime;
    /** 終了日 */
    private String endDate;
    /** 終了時刻 */
    private String endTime;

    /** 担当者ID */
    private String chargePersonId;
    /** ゲストIDリスト */
    private List<String> guestIdList;

    /** 資源 */
    private String resource;

    public String getSummery() {
        return summery;
    }

    public void setSummery(String summery) {
        this.summery = summery;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getChargePersonId() {
        return chargePersonId;
    }

    public void setChargePersonId(String chargePersonId) {
        this.chargePersonId = chargePersonId;
    }

    public List<String> getGuestIdList() {
        return guestIdList;
    }

    public void setGuestIdList(List<String> guestIdList) {
        this.guestIdList = guestIdList;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
