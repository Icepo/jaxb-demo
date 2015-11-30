package com.cattsoft.fun.jaxbdemo.entity;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

/**
 * Created by liuzhangjun on 2015-11-30.
 */
@XmlRootElement(name = "com.cattsoft.fun.jaxbdemo.entity.RfReport")
public class RfReport {

    private String rfReportId;

    private String rfReportCode;

    private List<RfHeaderMVO> header;

    private List<List<RfHeaderMVO>> headers;

    public String getRfReportId() {
        return rfReportId;
    }

    public void setRfReportId(String rfReportId) {
        this.rfReportId = rfReportId;
    }

    public String getRfReportCode() {
        return rfReportCode;
    }

    public void setRfReportCode(String rfReportCode) {
        this.rfReportCode = rfReportCode;
    }

    public List<RfHeaderMVO> getHeader() {
        return header;
    }

    public void setHeader(List<RfHeaderMVO> header) {
        this.header = header;
    }

    @XmlJavaTypeAdapter(DoubleListAdapter.class)
    public List<List<RfHeaderMVO>> getHeaders() {
        return headers;
    }

    public void setHeaders(List<List<RfHeaderMVO>> headers) {
        this.headers = headers;
    }
}
