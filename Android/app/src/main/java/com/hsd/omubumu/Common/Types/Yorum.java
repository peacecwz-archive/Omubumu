package com.hsd.omubumu.Common.Types;

import java.io.Serializable;

/**
 * Created by APALYazilim on 9.7.2015.
 */
public class Yorum implements Serializable {
    public String YorumID;
    public String UyeID;
    public String SoruID;
    public String Yorum;
    public String Resim;
    public String SaveDate;
    public String AdiSoyadi;
    public String KullaniciAdi;
    public String ProfileImage;

    public Yorum(){

    }

    public Yorum(String yorumID, String uyeID, String soruID, String yorum, String resim, String saveDate, String adiSoyadi, String kullaniciAdi, String profileImage) {
        YorumID = yorumID;
        UyeID = uyeID;
        SoruID = soruID;
        Yorum = yorum;
        Resim = resim;
        SaveDate = saveDate;
        AdiSoyadi = adiSoyadi;
        KullaniciAdi = kullaniciAdi;
        ProfileImage = profileImage;
    }

    public String getYorumID() {
        return YorumID;
    }

    public void setYorumID(String yorumID) {
        YorumID = yorumID;
    }

    public String getUyeID() {
        return UyeID;
    }

    public void setUyeID(String uyeID) {
        UyeID = uyeID;
    }

    public String getSoruID() {
        return SoruID;
    }

    public void setSoruID(String soruID) {
        SoruID = soruID;
    }

    public String getYorum() {
        return Yorum;
    }

    public void setYorum(String yorum) {
        Yorum = yorum;
    }

    public String getResim() {
        return Resim;
    }

    public void setResim(String resim) {
        Resim = resim;
    }

    public String getSaveDate() {
        return SaveDate;
    }

    public void setSaveDate(String saveDate) {
        SaveDate = saveDate;
    }

    public String getAdiSoyadi() {
        return AdiSoyadi;
    }

    public void setAdiSoyadi(String adiSoyadi) {
        AdiSoyadi = adiSoyadi;
    }

    public String getKullaniciAdi() {
        return KullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        KullaniciAdi = kullaniciAdi;
    }

    public String getProfileImage() {
        return ProfileImage;
    }

    public void setProfileImage(String profileImage) {
        ProfileImage = profileImage;
    }
}
