package jp.co.ysk.pixy.dto;

/**
 * Created by ko-aoki on 2016/02/06.
 */
public class GoogleChartDto {

    /**
     * 時刻
     */
    private String checkedTime;

    /**
     * 訪問者数
     */
    private int visitorsCnt;

    public String getCheckedTime() {
        return checkedTime;
    }

    public void setCheckedTime(String checkedTime) {
        this.checkedTime = checkedTime;
    }

    public int getVisitorsCnt() {
        return visitorsCnt;
    }

    public void setVisitorsCnt(int visitorsCnt) {
        this.visitorsCnt = visitorsCnt;
    }
}
