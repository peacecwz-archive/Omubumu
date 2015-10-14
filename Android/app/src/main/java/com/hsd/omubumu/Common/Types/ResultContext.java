package com.hsd.omubumu.Common.Types;

import java.io.Serializable;

/**
 * Created by APALYazilim on 9.7.2015.
 */
public class ResultContext implements Serializable {
    private Boolean Sonuc;
    private String Mesaj;
    private Object Data;

    public ResultContext(){

    }

    public ResultContext(Boolean sonuc, String mesaj, Object data) {
        Sonuc = sonuc;
        Mesaj = mesaj;
        Data = data;
    }

    public Boolean getSonuc() {
        return Sonuc;
    }

    public void setSonuc(Boolean sonuc) {
        Sonuc = sonuc;
    }

    public String getMesaj() {
        return Mesaj;
    }

    public void setMesaj(String mesaj) {
        Mesaj = mesaj;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object data) {
        Data = data;
    }
}
