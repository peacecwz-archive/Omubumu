package com.hsd.omubumu.Common.Types;

import java.io.Serializable;

/**
 * Created by APALYazilim on 9.7.2015.
 */
public class Uye implements Serializable {
    public String UyeID;
    public String KullaniciAdi;
    public String AdiSoyadi;
    public String Email;
    public String ProfileImage;

    public Uye(){

    }

    public Uye(String uyeID, String kullaniciAdi, String adiSoyadi, String email, String profileImage) {
        UyeID = uyeID;
        KullaniciAdi = kullaniciAdi;
        AdiSoyadi = adiSoyadi;
        Email = email;
        ProfileImage = profileImage;
    }

    public String getUyeID() {
        return UyeID;
    }

    public void setUyeID(String uyeID) {
        UyeID = uyeID;
    }

    public String getKullaniciAdi() {
        return KullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        KullaniciAdi = kullaniciAdi;
    }

    public String getAdiSoyadi() {
        return AdiSoyadi;
    }

    public void setAdiSoyadi(String adiSoyadi) {
        AdiSoyadi = adiSoyadi;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getProfileImage() {
        return ProfileImage;
    }

    public void setProfileImage(String profileImage) {
        ProfileImage = profileImage;
    }
}
