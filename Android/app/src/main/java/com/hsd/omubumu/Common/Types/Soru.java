package com.hsd.omubumu.Common.Types;

import java.io.Serializable;

/**
 * Created by APALYazilim on 9.7.2015.
 */
public class Soru implements Serializable {
    public String SoruID;
    public String UyeID;
    public String Resim1;
    public String Resim2;
    public String Aciklama;
    public String Baslik;
    public String KategoriID;
    public String SaveDate;
    public String KategoriAdi;
    public String AdiSoyadi;
    public String KullaniciAdi;
    public String ProfileImage;

    public Soru(){

    }

    public Soru(String soruID, String uyeID, String resim1, String resim2, String aciklama, String baslik, String kategoriID, String saveDate, String kategoriAdi, String adiSoyadi, String kullaniciAdi, String profileImage) {
        SoruID = soruID;
        UyeID = uyeID;
        Resim1 = resim1;
        Resim2 = resim2;
        Aciklama = aciklama;
        Baslik = baslik;
        KategoriID = kategoriID;
        SaveDate = saveDate;
        KategoriAdi = kategoriAdi;
        AdiSoyadi = adiSoyadi;
        KullaniciAdi = kullaniciAdi;
        ProfileImage = profileImage;
    }

    public String getSoruID() {
        return SoruID;
    }

    public void setSoruID(String soruID) {
        SoruID = soruID;
    }

    public String getUyeID() {
        return UyeID;
    }

    public void setUyeID(String uyeID) {
        UyeID = uyeID;
    }

    public String getResim1() {
        return Resim1;
    }

    public void setResim1(String resim1) {
        Resim1 = resim1;
    }

    public String getResim2() {
        return Resim2;
    }

    public void setResim2(String resim2) {
        Resim2 = resim2;
    }

    public String getAciklama() {
        return Aciklama;
    }

    public void setAciklama(String aciklama) {
        Aciklama = aciklama;
    }

    public String getBaslik() {
        return Baslik;
    }

    public void setBaslik(String baslik) {
        Baslik = baslik;
    }

    public String getKategoriID() {
        return KategoriID;
    }

    public void setKategoriID(String kategoriID) {
        KategoriID = kategoriID;
    }

    public String getSaveDate() {
        return SaveDate;
    }

    public void setSaveDate(String saveDate) {
        SaveDate = saveDate;
    }

    public String getKategoriAdi() {
        return KategoriAdi;
    }

    public void setKategoriAdi(String kategoriAdi) {
        KategoriAdi = kategoriAdi;
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
