package com.hsd.omubumu.Common.Types;

import java.io.Serializable;

/**
 * Created by APALYazilim on 9.7.2015.
 */
public class Kategori implements Serializable {

    private String KategoriID;
    private String KategoriAdi;

    public Kategori(){

    }

    public Kategori(String kategoriID, String kategoriAdi) {
        KategoriID = kategoriID;
        KategoriAdi = kategoriAdi;
    }

    public String getKategoriID() {
        return KategoriID;
    }

    public void setKategoriID(String kategoriID) {
        KategoriID = kategoriID;
    }

    public String getKategoriAdi() {
        return KategoriAdi;
    }

    public void setKategoriAdi(String kategoriAdi) {
        KategoriAdi = kategoriAdi;
    }
}
