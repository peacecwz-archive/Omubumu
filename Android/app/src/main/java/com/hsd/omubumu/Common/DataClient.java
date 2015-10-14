package com.hsd.omubumu.Common;

import android.app.Application;

import com.hsd.omubumu.Common.Types.Kategori;
import com.hsd.omubumu.Common.Types.Oylar;
import com.hsd.omubumu.Common.Types.ResultContext;
import com.hsd.omubumu.Common.Types.Soru;
import com.hsd.omubumu.Common.Types.Uye;
import com.hsd.omubumu.Common.Types.Yorum;
import com.hsd.omubumu.Helper.DataEncrypter;
import com.hsd.omubumu.Helper.HttpClient;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

/**
 * Created by APALYazilim on 9.7.2015.
 */
public class DataClient implements Serializable {
    private HttpClient Client = new HttpClient();
    private String Url = "http://omubumuapp.com/api";

    public ResultContext Giris(final String kullaniciAdi, final String sifre) {
        List<NameValuePair> values = new ArrayList<NameValuePair>();
        values.add(new BasicNameValuePair("KullaniciAdi", DataEncrypter.Encrypt(kullaniciAdi)));
        values.add(new BasicNameValuePair("Sifre", DataEncrypter.Encrypt(sifre)));

        JSONObject sonuc = Client.makeHttpRequest(Url + "/Giris", "POST", values);
        if (sonuc != null) {
            try {
                Uye uye = null;
                if (sonuc.getBoolean("Sonuc")) {
                    JSONObject uyeObj = sonuc.getJSONObject("Data");
                    if (uyeObj != null) {
                        uye = new Uye(uyeObj.getString("UyeID"),
                                uyeObj.getString("KullaniciAdi"),
                                uyeObj.getString("AdiSoyadi"),
                                uyeObj.getString("Email"),
                                uyeObj.getString("ProfileImage"));
                    }
                }
                return new ResultContext(sonuc.getBoolean("Sonuc"),
                        sonuc.getString("Mesaj"),
                        uye);
            } catch (JSONException ex) {
                Log("Giriste - catch hata oluştu");
                return new ResultContext(false, "Hata Oluştu. Detay:" + ex.getMessage(), null);
            }
        } else {
            Log("Giriste - else hata oluştu");
            return new ResultContext(false, "Hata oluştu", null);
        }
    }

    public ResultContext Cikis() {
        List<NameValuePair> values = new ArrayList<NameValuePair>();

        JSONObject sonuc = Client.makeHttpRequest(Url + "/Cikis", "POST", values);
        if (sonuc != null) {
            try {
                return new ResultContext(sonuc.getBoolean("Sonuc"),
                        sonuc.getString("Mesaj"),
                        null);
            } catch (JSONException ex) {
                Log("Çıkış yaparken - catch hata oluştu");
                return new ResultContext(false, "Hata Oluştu. Detay:" + ex.getMessage(), null);
            }
        } else {
            Log("Çıkış yaparken - ELSE hata oluştu");
            return new ResultContext(false, "Hata oluştu", null);
        }
    }

    public ResultContext ProfilGuncelle(final String AdiSoyadi, final String Email, final String sifre) {
        List<NameValuePair> values = new ArrayList<NameValuePair>();
        values.add(new BasicNameValuePair("AdiSoyadi", DataEncrypter.Encrypt(AdiSoyadi)));
        values.add(new BasicNameValuePair("Email", DataEncrypter.Encrypt(Email)));
        values.add(new BasicNameValuePair("Sifre", DataEncrypter.Encrypt(sifre)));

        JSONObject sonuc = Client.makeHttpRequest(Url + "/ProfilGuncelle", "POST", values);
        if (sonuc != null) {
            try {
                return new ResultContext(sonuc.getBoolean("Sonuc"),
                        sonuc.getString("Mesaj"),
                        null);
            } catch (JSONException ex) {
                Log("Profil Düzenlenirken - catch hata oluştu");
                return new ResultContext(false, "Hata Oluştu. Detay:" + ex.getMessage(), null);
            }
        } else {
            Log("Profil Düzenlenirken - ELSE hata oluştu");
            return new ResultContext(false, "Hata oluştu", null);
        }
    }

    public ResultContext Kayit(final String kullaniciAdi, final String AdiSoyadi, final String Email, final String sifre) {
        List<NameValuePair> values = new ArrayList<NameValuePair>();
        values.add(new BasicNameValuePair("KullaniciAdi", DataEncrypter.Encrypt(kullaniciAdi)));
        values.add(new BasicNameValuePair("AdiSoyadi", DataEncrypter.Encrypt(AdiSoyadi)));
        values.add(new BasicNameValuePair("Email", DataEncrypter.Encrypt(Email)));
        values.add(new BasicNameValuePair("Sifre", DataEncrypter.Encrypt(sifre)));

        JSONObject sonuc = Client.makeHttpRequest(Url + "/Kayit", "POST", values);
        if (sonuc != null) {
            try {
                return new ResultContext(sonuc.getBoolean("Sonuc"),
                        sonuc.getString("Mesaj"),
                        null);
            } catch (JSONException ex) {
                Log("Kayıt olurken - catch hata oluştu");
                return new ResultContext(false, "Hata Oluştu. Detay:" + ex.getMessage(), null);
            }
        } else {
            Log("Kayıt olurken - else hata oluştu");
            return new ResultContext(false, "Hata oluştu", null);
        }
    }

    public ResultContext SifremiUnuttum(final String Email) {
        List<NameValuePair> values = new ArrayList<NameValuePair>();
        values.add(new BasicNameValuePair("Email", DataEncrypter.Encrypt(Email)));

        JSONObject sonuc = Client.makeHttpRequest(Url + "/SifremiUnuttum", "POST", values);
        if (sonuc != null) {
            try {
                return new ResultContext(sonuc.getBoolean("Sonuc"),
                        sonuc.getString("Mesaj"),
                        null);
            } catch (JSONException ex) {
                Log("Şifremi sıfırlanırken - CATCH hata oluştu");
                return new ResultContext(false, "Hata Oluştu. Detay:" + ex.getMessage(), null);
            }
        } else {
            Log("Şifremi sıfırlanırken - ELSE hata oluştu");
            return new ResultContext(false, "Hata oluştu", null);
        }
    }

    public ResultContext ResimGuncelle(final String Resim, final String ResimAd) {
        List<NameValuePair> values = new ArrayList<NameValuePair>();

        JSONObject sonuc = Client.makeHttpRequestWithFile(Url + "/ResimGuncelle", "POST", values, Resim, ResimAd);
        if (sonuc != null) {
            try {
                return new ResultContext(sonuc.getBoolean("Sonuc"),
                        sonuc.getString("Mesaj"),
                        null);
            } catch (JSONException ex) {
                Log("Resim güncellenirken - CATCH hata oluştu");
                return new ResultContext(false, "Hata Oluştu. Detay:" + ex.getMessage(), null);
            }
        } else {
            Log("Resim güncellenirken - ELSE hata oluştu");
            return new ResultContext(false, "Hata oluştu", null);
        }
    }

    public ResultContext Sorular(final String KategoriID, final String SoruID, final String UyeID) {
        List<NameValuePair> values = new ArrayList<NameValuePair>();
        values.add(new BasicNameValuePair("KategoriID", DataEncrypter.Encrypt(KategoriID)));
        values.add(new BasicNameValuePair("SoruID", DataEncrypter.Encrypt(SoruID)));
        values.add(new BasicNameValuePair("UyeID", DataEncrypter.Encrypt(UyeID)));

        JSONObject sonuc = Client.makeHttpRequest(Url + "/Sorular", "GET", values);
        if (sonuc != null) {
            try {
                Object data = null;
                List<Soru> sorular = new ArrayList<Soru>();
                Soru soru = null;
                if (SoruID == "") {
                    JSONArray sorularAry = sonuc.getJSONArray("Data");

                    if (sorularAry != null) {
                        for (int i = 0; i < sorularAry.length(); i++) {
                            JSONObject soruAObj = sorularAry.getJSONObject(i);
                            sorular.add(new Soru(
                                    soruAObj.getString("SoruID"),
                                    soruAObj.getString("UyeID"),
                                    soruAObj.getString("Resim1"),
                                    soruAObj.getString("Resim2"),
                                    soruAObj.getString("Aciklama"),
                                    soruAObj.getString("Baslik"),
                                    soruAObj.getString("KategoriID"),
                                    soruAObj.getString("SaveDate"),
                                    soruAObj.getString("KategoriAdi"),
                                    soruAObj.getString("AdiSoyadi"),
                                    soruAObj.getString("KullaniciAdi"),
                                    soruAObj.getString("ProfileImage")
                            ));
                        }
                        data = sorular;
                        return new ResultContext(sonuc.getBoolean("Sonuc"),
                                sonuc.getString("Mesaj"),
                                data);
                    }
                } else {
                    JSONObject soruObj = sonuc.getJSONObject("Data");
                    if (soruObj != null) {
                        soru = new Soru(
                                soruObj.getString("SoruID"),
                                soruObj.getString("UyeID"),
                                soruObj.getString("Resim1"),
                                soruObj.getString("Resim2"),
                                soruObj.getString("Aciklama"),
                                soruObj.getString("Baslik"),
                                soruObj.getString("KategoriID"),
                                soruObj.getString("SaveDate"),
                                soruObj.getString("KategoriAdi"),
                                soruObj.getString("AdiSoyadi"),
                                soruObj.getString("KullaniciAdi"),
                                soruObj.getString("ProfileImage")
                        );
                        data = soru;
                        return new ResultContext(sonuc.getBoolean("Sonuc"),
                                sonuc.getString("Mesaj"),
                                data);
                    }
                }
                return new ResultContext(sonuc.getBoolean("Sonuc"),
                        sonuc.getString("Mesaj"),
                        data);
            } catch (JSONException ex) {
                Log("Sorular ekranında - CATCH hata oluştu");
                return new ResultContext(false, "Hata Oluştu. Detay:" + ex.getMessage(), null);
            }
        } else {
            Log("Sorular ekranında - ELSE hata oluştu");
            return new ResultContext(false, "Hata oluştu", null);
        }
    }

    public ResultContext SoruEkle(final String Resim1, final String Resim1Adi, final String Resim2, final String Resim2Adi, final String Baslik, final String Aciklama, final String KategoriID) {
        List<NameValuePair> values = new ArrayList<NameValuePair>();
        values.add(new BasicNameValuePair("Baslik", DataEncrypter.Encrypt(Baslik)));
        values.add(new BasicNameValuePair("Aciklama", DataEncrypter.Encrypt(Aciklama)));
        values.add(new BasicNameValuePair("KategoriID", DataEncrypter.Encrypt(KategoriID)));

        JSONObject sonuc = Client.makeHttpRequestWithFile(Url + "/SoruEkle", "POST", values, Resim1, Resim2, Resim1Adi, Resim2Adi);
        if (sonuc != null) {
            try {
                return new ResultContext(sonuc.getBoolean("Sonuc"),
                        sonuc.getString("Mesaj"),
                        null);
            } catch (JSONException ex) {
                Log("Soru eklenirken - CATCH hata oluştu");
                return new ResultContext(false, "Hata Oluştu. Detay:" + ex.getMessage(), null);
            }
        } else {
            Log("Soru eklenirken - ELSE hata oluştu");
            return new ResultContext(false, "Hata oluştu", null);

        }
    }

    public ResultContext Oyla(final String SoruID, final Boolean ResimNo) {
        List<NameValuePair> values = new ArrayList<NameValuePair>();
        values.add(new BasicNameValuePair("SoruID", DataEncrypter.Encrypt(SoruID)));

        if (ResimNo)
            values.add(new BasicNameValuePair("ResimNo", DataEncrypter.Encrypt("0")));
        else
            values.add(new BasicNameValuePair("ResimNo", DataEncrypter.Encrypt("1")));


        JSONObject sonuc = Client.makeHttpRequest(Url + "/Oyla", "POST", values);
        if (sonuc != null) {
            try {
                return new ResultContext(sonuc.getBoolean("Sonuc"),
                        sonuc.getString("Mesaj"),
                        null);
            } catch (JSONException ex) {
                Log("Oy verilirken - CATCH hata oluştu");
                return new ResultContext(false, "Hata Oluştu. Detay:" + ex.getMessage(), null);
            }
        } else {
            Log("Oy verilirken - ELSE hata oluştu");
            return new ResultContext(false, "Hata oluştu", null);
        }
    }

    public ResultContext Oylar(final String SoruID) {
        List<NameValuePair> values = new ArrayList<NameValuePair>();
        values.add(new BasicNameValuePair("SoruID", DataEncrypter.Encrypt(SoruID)));

        JSONObject sonuc = Client.makeHttpRequest(Url + "/Oylar", "GET", values);
        if (sonuc != null) {
            try {
                JSONObject oylarObj = sonuc.getJSONObject("Data");
                Oylar oylar = null;
                if (oylarObj != null) {
                    oylar = new Oylar(
                            oylarObj.getString("ToplamOy"),
                            oylarObj.getString("Resim1Oy"),
                            oylarObj.getString("Resim2Oy")
                    );
                }
                return new ResultContext(sonuc.getBoolean("Sonuc"),
                        sonuc.getString("Mesaj"),
                        oylar);
            } catch (JSONException ex) {
                Log("Oylar çekilirken - CATCH hata oluştu");
                return new ResultContext(false, "Hata Oluştu. Detay:" + ex.getMessage(), null);
            }
        } else {
            Log("Oylar çekilirken - ELSE hata oluştu");
            return new ResultContext(false, "Hata oluştu", null);
        }
    }

    public ResultContext Yorumlar(final String SoruID) {
        List<NameValuePair> values = new ArrayList<NameValuePair>();
        values.add(new BasicNameValuePair("SoruID", DataEncrypter.Encrypt(SoruID)));

        JSONObject sonuc = Client.makeHttpRequest(Url + "/Yorumlar", "GET", values);
        if (sonuc != null) {
            try {
                JSONArray yorumlarAry = sonuc.getJSONArray("Data");
                List<Yorum> yorumlar = new ArrayList<Yorum>();
                if (yorumlarAry != null) {
                    for (int i = 0; i < yorumlarAry.length(); i++) {
                        JSONObject yorumObj = yorumlarAry.getJSONObject(i);
                        yorumlar.add(new Yorum(
                                yorumObj.getString("YorumID"),
                                yorumObj.getString("UyeID"),
                                yorumObj.getString("SoruID"),
                                yorumObj.getString("Yorum"),
                                yorumObj.getString("Resim"),
                                yorumObj.getString("SaveDate"),
                                yorumObj.getString("AdiSoyadi"),
                                yorumObj.getString("KullaniciAdi"),
                                yorumObj.getString("ProfileImage")
                        ));
                    }
                }
                return new ResultContext(sonuc.getBoolean("Sonuc"),
                        sonuc.getString("Mesaj"),
                        yorumlar);
            } catch (JSONException ex) {
                Log("Yorumlar çekilirken - CATCH hata oluştu");
                return new ResultContext(false, "Hata Oluştu. Detay:" + ex.getMessage(), null);
            }
        } else {
            Log("Yorumlar çekilirken - ELSE hata oluştu");
            return new ResultContext(false, "Hata oluştu", null);
        }
    }

    public ResultContext YorumEkle(final String SoruID, final String Yorum, final byte[] YorumResim, final String YorumResimAdi) {
        List<NameValuePair> values = new ArrayList<NameValuePair>();
        values.add(new BasicNameValuePair("SoruID", DataEncrypter.Encrypt(SoruID)));
        values.add(new BasicNameValuePair("Yorum", DataEncrypter.Encrypt(Yorum)));

        JSONObject sonuc = Client.makeHttpRequest(Url + "/YorumEkle", "POST", values);
        if (sonuc != null) {
            try {
                return new ResultContext(sonuc.getBoolean("Sonuc"),
                        sonuc.getString("Mesaj"),
                        null);
            } catch (JSONException ex) {
                Log("Yorum eklenirken - CATCH hata oluştu");
                return new ResultContext(false, "Hata Oluştu. Detay:" + ex.getMessage(), null);
            }
        } else {
            Log("Yorum eklenirken - ELSE hata oluştu");
            return new ResultContext(false, "Hata oluştu", null);
        }
    }

    public ResultContext Kategoriler() {
        List<NameValuePair> values = new ArrayList<NameValuePair>();
        JSONObject sonuc = Client.makeHttpRequest(Url + "/Kategoriler", "GET", values);
        if (sonuc != null) {
            try {
                JSONArray kategoriList = sonuc.getJSONArray("Data");
                List<Kategori> kategoriler = new ArrayList<Kategori>();
                if (kategoriList != null) {
                    for (int i = 0; i < kategoriList.length(); i++) {
                        JSONObject kategori = kategoriList.getJSONObject(i);
                        kategoriler.add(new Kategori(kategori.getString("KategoriID"),
                                kategori.getString("KategoriAdi")));
                    }
                }
                return new ResultContext(sonuc.getBoolean("Sonuc"),
                        sonuc.getString("Mesaj"),
                        kategoriler);
            } catch (JSONException ex) {
                Log("Kategoriler çekilirken - CATCH hata oluştu");
                return new ResultContext(false, "Hata Oluştu. Detay:" + ex.getMessage(), null);
            }
        } else {
            Log("Kategoriler çekilirken - ELSE hata oluştu");
            return new ResultContext(false, "Hata oluştu", null);
        }
    }

    public ResultContext UyeProfil(final String UyeID) {
        List<NameValuePair> values = new ArrayList<NameValuePair>();
        values.add(new BasicNameValuePair("UyeID", DataEncrypter.Encrypt(UyeID)));
        JSONObject sonuc = Client.makeHttpRequest(Url + "/UyeProfil", "GET", values);
        if (sonuc != null) {
            try {
                Uye uye = null;
                JSONObject uyeObj = sonuc.getJSONObject("Data");
                if (uyeObj != null) {
                    uye = new Uye(uyeObj.getString("UyeID"),
                            uyeObj.getString("KullaniciAdi"),
                            uyeObj.getString("AdiSoyadi"),
                            uyeObj.getString("Email"),
                            uyeObj.getString("ProfileImage"));
                }
                return new ResultContext(sonuc.getBoolean("Sonuc"),
                        sonuc.getString("Mesaj"),
                        uye);
            } catch (JSONException ex) {
                Log("Uye Profili - CATCH hata oluştu");
                return new ResultContext(false, "Hata Oluştu. Detay:" + ex.getMessage(), null);
            }
        } else {
            Log("Uye Profili - ELSE hata oluştu");
            return new ResultContext(false, "Hata oluştu", null);
        }
    }

    public void Log(final String Mesaj) {
        try {
            List<NameValuePair> values = new ArrayList<NameValuePair>();
            values.add(new BasicNameValuePair("SoruID", DataEncrypter.Encrypt(Mesaj)));
            JSONObject sonuc = Client.makeHttpRequest(Url + "/api/Log", "POST", values);
        }
        catch(Exception ex)
        {
            System.exit(0);
        }
    }
}