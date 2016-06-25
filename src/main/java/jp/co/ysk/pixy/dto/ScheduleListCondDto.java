package jp.co.ysk.pixy.dto;

/**
 * Created by ko-aoki on 2016/06/25.
 */
public class ScheduleListCondDto {

    private String searchDate;

    private String visitorName;

    private String companyName;

    public String getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(String searchDate) {
        this.searchDate = searchDate;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
