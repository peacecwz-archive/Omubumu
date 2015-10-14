package com.hsd.omubumu.Common.Types;

import java.io.Serializable;

/**
 * Created by APALYazilim on 9.7.2015.
 */
public class Oylar implements Serializable {
    private String ToplamOy;
    private String Resim1Oy;
    private String Resim2Oy;

    public Oylar(){

    }

    public Oylar(String toplamOy, String resim1Oy, String resim2Oy) {
        ToplamOy = toplamOy;
        Resim1Oy = resim1Oy;
        Resim2Oy = resim2Oy;
    }

    public String getToplamOy() {
        return ToplamOy;
    }

    public void setToplamOy(String toplamOy) {
        ToplamOy = toplamOy;
    }

    public String getResim1Oy() {
        return Resim1Oy;
    }

    public void setResim1Oy(String resim1Oy) {
        Resim1Oy = resim1Oy;
    }

    public String getResim2Oy() {
        return Resim2Oy;
    }

    public void setResim2Oy(String resim2Oy) {
        Resim2Oy = resim2Oy;
    }
}
