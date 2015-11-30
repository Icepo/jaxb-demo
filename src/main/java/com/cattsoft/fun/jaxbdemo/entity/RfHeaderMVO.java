package com.cattsoft.fun.jaxbdemo.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by liuzhangjun on 2015-11-30.
 */
@XmlRootElement(name = "com.cattsoft.fun.jaxbdemo.entity.RfHeaderMVO")
public class RfHeaderMVO extends RfHeader{

    private String extendField;

    public String getExtendField() {
        return extendField;
    }

    public void setExtendField(String extendField) {
        this.extendField = extendField;
    }

    public RfHeaderMVO() {
        super();
    }

    public RfHeaderMVO(String rfHeaderId, String title, String extendField) {
        this.setRfHeaderId(rfHeaderId);
        this.setTitle(title);
        this.extendField = extendField;
    }

    @Override
    public String toString() {
        return "RfHeaderMVO{" +
                "extendField='" + extendField + '\'' +
                "rfHeaderId='" + getRfHeaderId() + '\'' +
                "title='" + getTitle() + '\'' +
                '}';
    }
}
