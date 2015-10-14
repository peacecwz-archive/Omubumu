<?php
require_once 'Core/DBHandler.php';
require_once 'Core/DataCrypter.php';
require_once 'Helpers/APIHelper.php';
require 'Slim/Slim.php';
session_start();
date_default_timezone_set('Europe/Istanbul');
\Slim\Slim::registerAutoloader();

$app = new \Slim\Slim();


function echoRespnse($status_code, $response) {
    $app = \Slim\Slim::getInstance();
    // Http response code
    $app->status($status_code);
    
    // setting response content type to json
    $app->contentType('application/json; charset=utf-8');
    
    echo Encrypt(json_encode($response));
}

function doLog($Mesaj){
	$db = new DbHandler();
	
	$useragent = $_SERVER["HTTP_USER_AGENT"];
	$IPAdres = $_SERVER["REMOTE_ADDR"];
	$db->Log($IPAdres,$useragent,$Mesaj);
}

$app->post('/Giris', function() use ($app) {
    $db = new DbHandler();
    $response = array();
    
    $User = Decrypt( $app->request->post("KullaniciAdi"));
    $Pass = Decrypt($app->request->post("Sifre"));
    	
    if(isset($User) & isset($Pass)){
        $sonuc = $db->Giris($User,$Pass);
        if($sonuc!=false){
			if($sonuc->Ban==true){
				$response["Mesaj"]="Hesabınız Devre Dışı Bırakılmıştır.";
				$response["Sonuc"] = false;
			}
			else{
				SetUyeID($sonuc->UyeID);
				$response["Mesaj"]='Giriş Yapıldı!';
				$response["Sonuc"]=true;
				$response["Data"]=$sonuc;
			}
        }
        else{
            $response["Mesaj"]='Kullanıcı veya Şifre Hatalı!';
            $response["Sonuc"]=false;        
        }
    }
    else{
    	$response["Sonuc"]=false;
		$response["Mesaj"]="Geçersiz Kullanıcı Adı ve Şifre";
    }
    
    echoRespnse(200,$response);
});

$app->post('/Cikis',function() use($app){
    
    SetUyeID(null);
    $response = array();
    $response["Sonuc"]= true;
    $response8["Mesaj"] = "Çıkış Yapıldı";
    echoRespnse(200,$response);
});

$app->post('/Kayit',function() use($app){
    
    $db = new DbHandler();
    $response = array();
    
    $User = Decrypt($app->request->post("KullaniciAdi"));
    $Pass = Decrypt($app->request->post("Sifre"));
    $Name = Decrypt($app->request->post("AdiSoyadi"));
    $Email = Decrypt($app->request->post("Email"));
    
    if(isset($User) &&
       isset($Pass) &&
       isset($Name) &&
       isset($Email) &&
	   !IsNullOrEmptyString($User) &&
	   !IsNullOrEmptyString($Pass) &&
	   !IsNullOrEmptyString($Name) &&
	   !IsNullOrEmptyString($Email)
	   ){
	   if(!filter_var($Email, FILTER_VALIDATE_EMAIL)){
			$response["Sonuc"]=false;
			$response["Mesaj"]="Geçersiz Mail Adresi.";
			echoRespnse(200,$response);
			return;
	   }
	   if(preg_match('/^[a-zA-Z0-9]{5,}$/', $username)) { 
			$response["Sonuc"]=false;
			$response["Mesaj"]="Lütfen geçerli bir kullanıcı adı giriniz.";
			echoRespnse(200,$response);
			return;
		}
		$sonuc = $db->UyeEkle($Name,$Email,$User,$Pass);
		if($sonuc!=false){
			$response["Mesaj"]='Üyeliğiniz Başarıyla Tamamlandı';
			$response["Sonuc"]=true;        
		}
		else{
			$response["Mesaj"]='Üye olurken bir hata oluştu.';
			$response["Sonuc"]=false;        
		}
    }
    else{
    	$response["Sonuc"]=false;
		$response["Mesaj"]="Geçersiz Üyelik Bilgileri";
    }
    echoRespnse(200,$response);
});

$app->post('/SifremiUnuttum',function() use($app){
    $response = array();
    $db = new DbHandler();
    
    $Mail = Decrypt($app->request->post('Email'));
    
    if(isset($Mail)){
        $ResetKey = CreateRestKey($Mail);
        $sonuc = $db->SifirlamaKeyiGuncelle($Mail,$ResetKey);
        if($sonuc==false){
            $response["Sonuc"] = false;
            $response["Mesaj"] = "Bu mail adresine ait bir kullanıcı bulunamadı.";
        }
        else{
			$kullanici = $db->KullaniciGetir($Mail);
            if(SifirlamaMailGonder($Mail,$kullanici->AdiSoyadi,md5(base64_encode($ResetKey)))){
                $response["Sonuc"] = true;
                $response["Mesaj"] = "Belirtilen Mail adresine sıfırlama talimatlarını gönderdik.";
            }
            else{
                $db->SifirlamaKeyiGuncelle($Mail,"");
                $response["Sonuc"] = false;
                $response["Mesaj"] = "Şifre sıfırlanırken bir hata oluştu.";
            }
        }
    }
    else{
        $response["Mesaj"] = "Geçersiz Değerler.";
        $response["Sonuc"] = false;
    }
    echoRespnse(200,$response);
});

$app->post('/ResimGuncelle',function() use($app){
    $db = new DbHandler();
    
    $response = array();
    
    $UyeID = GetUyeID();//$app->request->post('UyeID');
    if($UyeID==false){
        $response["Sonuc"] = false;
        $response["Mesaj"] = "Giriş Yapılmadı";
        echoRespnse(200,$response);
        return;
    }

    $ProfilResim = ResimYukle(1);

    if(isset($UyeID) ){
        if($db->UyeResimGuncelle($UyeID,$ProfilResim)!=false){
            $response["Mesaj"] = "Resim Başarıyla Güncellendi. Hesabınızda çıkış yaptıktan sonra profiliniz yenilenecektir.";
            $response["Sonuc"] = true;
        }
        else{
            $response["Mesaj"] = "Resim Güncellenirken Hata Oluştu.";
            $response["Sonuc"] = false;
        }
    }
    else{
        $response["Mesaj"] = "Geçersiz Değerler.";
        $response["Sonuc"] = false;
    }
    echoRespnse(200,$response);
});

$app->post('/ProfilGuncelle',function() use($app){
   
    $db = new DbHandler();
    $response = array();
    
    $UyeID = GetUyeID();//$app->request->post("UyeID");
    if($UyeID==false){
        $response["Sonuc"] = false;
        $response["Mesaj"] = "Giriş Yapılmadı";
        echoRespnse(200,$response);
        return;
    }
    $Mail = Decrypt($app->request->post("Email"));
    $Sifre = Decrypt($app->request->post("Sifre"));
    $AdiSoyadi = Decrypt($app->request->post("AdiSoyadi"));
	$kullanici = $db->TamKullaniciGetirByUyeID($UyeID);
	if($Sifre == "Şifre "){
		$Sifre = $kullanici->Sifre;
	}
	else{
		$Sifre = md5($Sifre);
	}
    if(isset($UyeID) &
        isset($Mail) &
        isset($Sifre) &
        isset($AdiSoyadi)){
        if($db->UyeGuncelle($UyeID,$AdiSoyadi,$Mail,$Sifre)!=false){
            $response["Mesaj"] = "Profiliniz başarıyla güncellendi.";
            $response["Sonuc"] = true;
        }else{
            $response["Mesaj"] = "Güncellenirken hata oluştu.";
            $response["Sonuc"] = false;
        }
    }
    else{
        $response["Mesaj"] = "Geçersiz bilgiler.";
        $response["Sonuc"] = false;
    }
    
    echoRespnse(200,$response);
});

$app->get('/Sorular', function() use($app){
   $db = new DbHandler();
   $response = array();
   
   $KategoriID = Decrypt($app->request->get("KategoriID"));
   $SoruID = Decrypt($app->request->get("SoruID"));
   $UyeID = GetUyeID();
   $GelenUyeID = Decrypt($app->request->get("UyeID"));
   if($UyeID==false){
        $response["Sonuc"] = false;
        $response["Mesaj"] = "Giriş Yapılmadı";
        echoRespnse(200,$response);
        return;
    }
    
   if((isset($KategoriID) && $KategoriID != "") & (isset($UyeID) && $UyeID != "" && $UyeID == $GelenUyeID)){
       $response["Data"]=$db->SoruGetirByUyeKategori($UyeID,$KategoriID);
       $response["Mesaj"]="";
       if($response["Data"]==false)
           $response["Sonuc"]=false;
       else
           $response["Sonuc"]=true;
   }
   else if(isset($KategoriID) && $KategoriID != ""){
       $response["Data"]=$db->SoruByKategori($KategoriID);
       $response["Mesaj"]="";
       if($response["Data"]==false)
           $response["Sonuc"]=false;
       else
           $response["Sonuc"]=true;
   }
   else if(isset($SoruID) && $SoruID != ""){
       $response["Data"]=$db->SoruGetir($SoruID);
       $response["Mesaj"]="";
       if($response["Data"]==false)
           $response["Sonuc"]=false;
       else
           $response["Sonuc"]=true;
   }   
   else if(isset($GelenUyeID) && $GelenUyeID != ""){
       $response["Data"]=$db->SoruGetirByUye($GelenUyeID);
       $response["Mesaj"]="";
       if($response["Data"]==false)
           $response["Sonuc"]=false;
       else
           $response["Sonuc"]=true;
   }
   else{
       $response["Data"]=$db->Sorular();
       $response["Mesaj"]="";
       if($response["Data"]==false)
           $response["Sonuc"]=false;
       else
           $response["Sonuc"]=true;
   }
   echoRespnse(200,$response);
});

$app->post('/SoruEkle', function() use($app){
    $db = new DbHandler();
    $response = array();
    
    $UyeID = GetUyeID();//$app->request->post("UyeID");
            if($UyeID==false){
        $response["Sonuc"] = false;
        $response["Mesaj"] = "Giriş Yapılmadı";
        echoRespnse(200,$response);
        return;
    }
    $Aciklama = Decrypt($app->request->post("Aciklama"));
    $Baslik = Decrypt($app->request->post("Baslik"));
    $KategoriID = Decrypt($app->request->post("KategoriID"));
    $Resimler = ResimYukle(2,$UyeID,$Baslik);

    if(isset($UyeID) && isset($Aciklama) && isset($Baslik) && isset($KategoriID) &&  count($Resimler)==2){
        if($db->SoruEkle($UyeID,$Aciklama,$Baslik,$KategoriID,$Resimler[0],$Resimler[1]) != false){
            $response["Sonuc"] = true;
            $response["Mesaj"] = "Soru Eklendi.";
        }
        else{
            $response["Sonuc"] = false;
            $response["Mesaj"] = "Soru Eklenemedi.";
        }
    }
    else{
        $response["Sonuc"] = false;
        $response["Mesaj"] = "Eksik bilgiler";
        $response["Test"] = count($Resimler);
    }
    echoRespnse(200,$response);
});

$app->post('/SoruSil', function() use($app){
    $db = new DbHandler();
    $response = array();
    $UyeID = GetUyeID();//
            if($UyeID==false){
        $response["Sonuc"] = false;
        $response["Mesaj"] = "Giriş Yapılmadı";
        echoRespnse(200,$response);
        return;
    }
    $SoruID = Decrypt($app->request->post("SoruID"));
    if(isset($SoruID)){
        if($db->SoruSil($SoruID,$UyeID) != false){
            $response["Sonuc"] = true;
            $response["Mesaj"] = "Soru Silindi.";
        }
        else{
            $response["Sonuc"] = false;
            $response["Mesaj"] = "Soru Silinemedi.";
        }
    }else{
        $response["Sonuc"] = false;
        $response["Mesaj"] = "Eksik bilgiler";
    }
    echoRespnse(200,$response);
});

$app->get('/Oylar', function() use($app){
    $db = new DbHandler();
    $response = array();
    if(GetUyeID()==false){
        $response["Sonuc"] = false;
        $response["Mesaj"] = "Giriş Yapılmadı";
        echoRespnse(200,$response);
        return;
    }
    $SoruID = Decrypt($app->request->get("SoruID"));
    if(isset($SoruID) && $SoruID!=""){
        $response["Data"] = $db->OyIstatistik($SoruID);
        if($response["Data"]==false){
            $response["Sonuc"]=false;
            $response["Mesaj"] = "İstatistikler alınamadı";
        }
        else{
            $response["Mesaj"] = "";
            $response["Sonuc"]=true;
        }
    }
    else {
        $response["Sonuc"] = false;
        $response["Mesaj"] = "Eksik bilgiler";
    }
    echoRespnse(200,$response);
});

$app->post('/Oyla', function() use($app){
    $db = new DbHandler();
    $response = array();
    
    $UyeID = GetUyeID();//$app->request->post("UyeID");
    if($UyeID==false){
        $response["Sonuc"] = false;
        $response["Mesaj"] = "Giriş Yapılmadı";
        echoRespnse(200,$response);
        return;
    }
    $SoruID = Decrypt($app->request->post("SoruID"));
    $ResimNo = Decrypt($app->request->post("ResimNo"));
    $Oy = false;
    if($ResimNo=="0"){
        $Oy = false;
    }
    else if($ResimNo=="1"){
        $Oy = true;
    }
    if(isset($SoruID) && isset($UyeID) && isset($ResimNo)){
        if($db->OyladiMi($UyeID,$SoruID)==false){
            if($db->Oyla($UyeID,$SoruID,$Oy)==false){
                $response["Sonuc"] = false;
                $response["Mesaj"] = "Oy Eklenemedi";
            }
            else{
                $response["Sonuc"] = true;
                $response["Mesaj"] = "Oy Eklendi";
            }
        }else{
            if($db->OyGuncelle($UyeID,$SoruID,$Oy)==false){
                $response["Sonuc"] = false;
                $response["Mesaj"] = "Oy Güncellenemedi";
            }
            else{
                $response["Sonuc"] = true;
                $response["Mesaj"] = "Oy Güncellendi";
            }
        }
    }
    else{
        $response["Sonuc"] = false;
        $response["Mesaj"] = "Eksik bilgiler";
    }
    echoRespnse(200,$response);
});

$app->get('/Yorumlar', function() use($app){
   $db = new DbHandler();
   $response = array();
   
   if(GetUyeID()==false){
        $response["Sonuc"] = false;
        $response["Mesaj"] = "Giriş Yapılmadı";
        echoRespnse(200,$response);
        return;
    }
   $SoruID = Decrypt($app->request->get("SoruID"));
   
   if(isset($SoruID)){
        $response["Data"] = $db->Yorumlar($SoruID);
        if($response["Data"]!=false){
            $response["Sonuc"] = true;
            $response["Mesaj"] = "";
        }
        else{
            $response["Sonuc"] = false;
            $response["Mesaj"] = "Henüz Yorum Yazılmamıştır.";
        }
   }
   else{
        $response["Sonuc"] = false;
        $response["Mesaj"] = "Eksik bilgiler";
   }
    echoRespnse(200,$response);
});

$app->post('/YorumEkle',function() use($app){
    $db = new DbHandler();
    $response = array();
    
    $UyeID = GetUyeID();//$app->request->post("UyeID");
            if($UyeID==false){
        $response["Sonuc"] = false;
        $response["Mesaj"] = "Giriş Yapılmadı";
        echoRespnse(200,$response);
        return;
    }
    $SoruID =Decrypt($app->request->post("SoruID"));
    $Yorum = Decrypt($app->request->post("Yorum"));
    $YorumResim = ResimYukle(3);
    
    if(isset($UyeID) && isset($SoruID) && isset($Yorum)){
        if($db->YorumEkle($SoruID,$UyeID,$Yorum,$YorumResim)!=false){
            $response["Sonuc"] = true;
            $response["Mesaj"] = "Yorum Eklendi.";
        }
        else{
            $response["Sonuc"] = false;
            $response["Mesaj"] = "Yorum Eklenemedi.";
        }
    }
    else{
            $response["Sonuc"] = true;
            $response["Mesaj"] = "Geçersiz Bilgiler.";
    }
    echoRespnse(200,$response);
});

$app->get('/Kategoriler', function() use($app){
    $response= array();
    
    if(GetUyeID()==false){
        $response["Sonuc"] = false;
        $response["Mesaj"] = "Giriş Yapılmadı";
        echoRespnse(200,$response);
        return;
    }
    
    $db = new DbHandler();
    
    $response["Data"] = $db->Kategoriler();
    if($response["Data"]!=false){
        $response["Sonuc"] = true;
        $response["Mesaj"] = "";
    }
    else{
        $response["Sonuc"] = false;
        $response["Mesaj"] = "Kategori Bulunamadı";
    }
    echoRespnse(200,$response);
});

$app->get('/UyeProfil', function() use($app){
   
    if(GetUyeID()==false){
        $response["Sonuc"] = false;
        $response["Mesaj"] = "Giriş Yapılmadı";
        echoRespnse(200,$response);
        return;
    }
    
    $response= array();
    
    $UyeID = Decrypt($app->request->get("UyeID"));
    
    $db = new DbHandler();
    $response["Data"] = $db->KullaniciGetirByUyeID($UyeID);
    if($response["Data"]!=false){
        $response["Sonuc"] = true;
        $response["Mesaj"] = "";
    }
    else{
        $response["Sonuc"] = false;
        $response["Mesaj"] = "Böyle bir kullanıcı Bulunamadı";
    }
    echoRespnse(200,$response);
    
});

$app->post("/Log",function() use($app){
	if(GetUyeID()==false){
        $response["Sonuc"] = false;
        $response["Mesaj"] = "Giriş Yapılmadı";
        echoRespnse(200,$response);
        return;
    }
	
	$response= array();
    
    $Mesaj = Decrypt($app->request->post("Mesaj"));
	
    echoRespnse(200,$response);	
});

$app->run();

?>