package au.com.maxcheung.futureclearer.future;

import javax.validation.constraints.NotNull;

/**
 * ProductAddRequest for product maintenance.
 * 
 * @author Max Cheung <max.cheung@lonsec.com.au>
 */

public class LookupLoadRequest {

    @NotNull
    private String specfileName;
    @NotNull
    private String datafileName;

    private String reportfileName;
    
    public String getSpecfileName() {
        return specfileName;
    }
    public void setSpecfileName(String specfileName) {
        this.specfileName = specfileName;
    }
    public String getDatafileName() {
        return datafileName;
    }
    public void setDatafileName(String datafileName) {
        this.datafileName = datafileName;
    }
    public String getReportfileName() {
        return reportfileName;
    }
    public void setReportfileName(String reportfileName) {
        this.reportfileName = reportfileName;
    }

    

}
