<?php

class DbHandler {
    
    function __construct() {
        require_once dirname(__FILE__) . '/DBConnect.php';
    }
    
    public function UyeEkle($AdiSoyadi,$Email,$KullaniciAdi,$Sifre){
        return DB::exec("INSERT INTO `ogz1_OMBM`.`uyeler` (`AdiSoyadi`, `KullaniciAdi`, `Sifre`, `Email`) VALUES (:adisoyadi, :kullaniciadi, :sifre, :email);",
            array(':adisoyadi'=>$AdiSoyadi,':email'=>$Email,':kullaniciadi'=>$KullaniciAdi,':sifre'=>md5($Sifre)));
    }
    
    public function UyeGuncelle($UyeID,$AdiSoyadi,$Email,$Sifre){
        return DB::exec("UPDATE `ogz1_OMBM`.`uyeler` SET `AdiSoyadi`=:adisoyadi, `Sifre`=:sifre, `Email`=:email WHERE (`UyeID`=:UyeID);",
            array(':UyeID'=>$UyeID,':adisoyadi'=>$AdiSoyadi,':email'=>$Email,':sifre'=>md5($Sifre)));
    }
    
    public function UyeResimGuncelle($UyeID,$ProfilResim){
        return DB::exec("UPDATE `ogz1_OMBM`.`uyeler` SET `ProfileImage`=:ProfilResim WHERE (`UyeID`=:UyeID);",
            array(':UyeID'=>$UyeID,':ProfilResim'=>$ProfilResim));
    }
    
    public function Giris($KullaniciAdi,$Sifre){
        $sonuc = DB::getRow("SELECT UyeID,KullaniciAdi,AdiSoyadi,Email,ProfileImage FROM `uyeler` WHERE `KullaniciAdi` =:username AND `Sifre`=:password",
            array(':username'=>$KullaniciAdi,':password'=>md5($Sifre)));
        return $sonuc;
    }
    
    public function SifirlamaKeyiGuncelle($Mail,$ResetKey){
        return DB::exec("UPDATE `ogz1_OMBM`.`uyeler` SET `SifirlamaKodu`=:SifirlamaKodu WHERE (`Email`=:Email);",
            array(':Email'=>$Mail,':SifirlamaKodu'=>$ResetKey));
    }
    
    public function Sorular(){
        return DB::get("SELECT sorular.SoruID,
                    sorular.UyeID,
                    sorular.Resim1,
                    sorular.Resim2,
                    sorular.Aciklama,
                    sorular.Baslik,
                    sorular.KategoriID,
                    sorular.SaveDate,
                    kategoriler.KategoriAdi,
                    uyeler.AdiSoyadi,
                    uyeler.KullaniciAdi,
                    uyeler.ProfileImage
                    FROM
                    sorular
                    INNER JOIN uyeler ON uyeler.UyeID = sorular.UyeID
                    INNER JOIN kategoriler ON sorular.KategoriID = kategoriler.KategoriID
                    ORDER BY sorular.SaveDate DESC;");
    }
    
    public function SoruByKategori($KategoriID){
        return DB::get("SELECT sorular.SoruID,
                    sorular.UyeID,
                    sorular.Resim1,
                    sorular.Resim2,
                    sorular.Aciklama,
                    sorular.Baslik,
                    sorular.KategoriID,
                    sorular.SaveDate,
                    kategoriler.KategoriAdi,
                    uyeler.AdiSoyadi,
                    uyeler.KullaniciAdi,
                    uyeler.ProfileImage
                    FROM
                    sorular
                    INNER JOIN uyeler ON uyeler.UyeID = sorular.UyeID
                    INNER JOIN kategoriler ON sorular.KategoriID = kategoriler.KategoriID
                    WHERE sorular.KategoriID=:KategoriID
                    ORDER BY sorular.SaveDate DESC;",
                    array(':KategoriID'=>$KategoriID));
    }
    
    public function SoruGetir($SoruID){
        return DB::getRow("SELECT sorular.SoruID,
                    sorular.UyeID,
                    sorular.Resim1,
                    sorular.Resim2,
                    sorular.Aciklama,
                    sorular.Baslik,
                    sorular.KategoriID,
                    sorular.SaveDate,
                    kategoriler.KategoriAdi,
                    uyeler.AdiSoyadi,
                    uyeler.KullaniciAdi,
                    uyeler.ProfileImage
                    FROM
                    sorular
                    INNER JOIN uyeler ON uyeler.UyeID = sorular.UyeID
                    INNER JOIN kategoriler ON sorular.KategoriID = kategoriler.KategoriID
                    WHERE sorular.SoruID=:SoruID;",
                    array(':SoruID'=>$SoruID));
    }    
    
    public function SoruGetirByUye($UyeID){
        return DB::get("SELECT sorular.SoruID,
                    sorular.UyeID,
                    sorular.Resim1,
                    sorular.Resim2,
                    sorular.Aciklama,
                    sorular.Baslik,
                    sorular.KategoriID,
                    sorular.SaveDate,
                    kategoriler.KategoriAdi,
                    uyeler.AdiSoyadi,
                    uyeler.KullaniciAdi,
                    uyeler.ProfileImage
                    FROM
                    sorular
                    INNER JOIN uyeler ON uyeler.UyeID = sorular.UyeID
                    INNER JOIN kategoriler ON sorular.KategoriID = kategoriler.KategoriID
                    WHERE sorular.UyeID=:uyeID
                    ORDER BY sorular.SaveDate DESC;",
                    array(':uyeID'=>$UyeID));
    }
    
    public function SoruGetirByUyeKategori($UyeID,$KategoriID){
        return DB::getRow("SELECT sorular.SoruID,
                    sorular.UyeID,
                    sorular.Resim1,
                    sorular.Resim2,
                    sorular.Aciklama,
                    sorular.Baslik,
                    sorular.KategoriID,
                    sorular.SaveDate,
                    kategoriler.KategoriAdi,
                    uyeler.AdiSoyadi,
                    uyeler.KullaniciAdi,
                    uyeler.ProfileImage
                    FROM
                    sorular
                    INNER JOIN uyeler ON uyeler.UyeID = sorular.UyeID
                    INNER JOIN kategoriler ON sorular.KategoriID = kategoriler.KategoriID
                    WHERE sorular.UyeID=:uyeID AND sorular.KategoriID=:KategoriID;",
                    array(':uyeID'=>$UyeID,':KategoriID'=>$KategoriID));
    }
    
    public function SoruEkle($UyeID,$Aciklama,$Baslik,$KategoriID,$Resim1,$Resim2){
        return DB::exec("INSERT INTO `ogz1_OMBM`.`sorular` (`UyeID`, `Resim1`, `Resim2`, `Aciklama`, `Baslik`, `KategoriID`) VALUES (:UyeID, :Resim1, :Resim2, :Aciklama,  :Baslik, :KategoriID);",
            array(':UyeID'=>$UyeID,':Aciklama'=>$Aciklama,':Baslik'=>$Baslik,':KategoriID'=>$KategoriID,':Resim1'=>$Resim1,':Resim2'=>$Resim2));
    }
    
    public function SoruSil($SoruID,$UyeID){
        return DB::exec("DELETE FROM sorular WHERE SoruID=:SoruID AND UyeID=:UyeID",
            array(':SoruID'=>$SoruID,':UyeID'=>$UyeID));
    }
    
    public function OyladiMi($UyeID,$SoruID){
        $sonuc = DB::getVar("SELECT OyID FROM oylar WHERE SoruID=:SoruID AND UyeID=:UyeID",
            array(':SoruID'=>$SoruID,':UyeID'=>$UyeID));
        if($sonuc==false)
            return false;
        else
            return true;
        
    }
    
    public function Oyla($UyeID,$SoruID,$ResimNo){
        return DB::exec("INSERT INTO `ogz1_OMBM`.`oylar` (`UyeID`, `SoruID`, `ResimNo`) VALUES (:UyeID, :SoruID, :ResimNo);",
            array(':UyeID'=>$UyeID,':SoruID'=>$SoruID,':ResimNo'=>$ResimNo));
    }
    
    public function OyGuncelle($UyeID,$SoruID,$ResimNo){
        return DB::exec("UPDATE `ogz1_OMBM`.`oylar` SET `ResimNo`=:ResimNo WHERE (`UyeID`=:UyeID AND `SoruID`=:SoruID);",
            array(':UyeID'=>$UyeID,':SoruID'=>$SoruID,':ResimNo'=>$ResimNo));
    }
    
    public function OyIstatistik($SoruID){
        return DB::getRow("SELECT (SELECT COUNT(OyID) FROM oylar WHERE SoruID=:SoruID) AS `ToplamOy`,
                (SELECT COUNT(OyID) FROM oylar WHERE SoruID=:SoruID AND ResimNo=b'1') `Resim1Oy`,
                (SELECT COUNT(OyID) FROM oylar WHERE SoruID=:SoruID AND ResimNo=b'0') `Resim2Oy`",
                array(':SoruID'=>$SoruID));
    }
    
    public function YorumEkle($SoruID,$UyeID,$Yorum,$Resim){
        return DB::exec("INSERT INTO `ogz1_OMBM`.`yorumlar` (`UyeID`, `SoruID`, `Yorum`, `Resim`) VALUES ( :UyeID, :SoruID, :Yorum, :Resim );",
            array(':UyeID'=>$UyeID,':SoruID'=>$SoruID,':Yorum'=>$Yorum,':Resim'=>$Resim));
    }
    
    public function YorumSil($YorumID,$UeyID){
        return DB::exec("DELETE FROM `yorumlar` WHERE `UyeID`=:UyeID AND `YorumID`=:YorumID",
            array(':YorumID'=>$YorumID,':UyeID'=>$UeyID));
    }
    
    public function Yorumlar($SoruID){
        return DB::get("SELECT
                    yorumlar.YorumID,
                    yorumlar.UyeID,
                    yorumlar.SoruID,
                    yorumlar.Yorum,
                    yorumlar.Resim,
                    yorumlar.SaveDate,
                    uyeler.AdiSoyadi,
                    uyeler.KullaniciAdi,
                    uyeler.ProfileImage
                    FROM
                    yorumlar
                    INNER JOIN uyeler ON uyeler.UyeID = yorumlar.UyeID
                    WHERE yorumlar.SoruID=:SoruID
                    ORDER BY yorumlar.SaveDate DESC;",
                    array('SoruID'=>$SoruID));
    }
    
    public function Kategoriler(){
        return DB::get("SELECT * FROM kategoriler");
    }
}
?>