<?php

require("SMTP/class.phpmailer.php");

function IsNullOrEmptyString($question){
    return (!isset($question) || trim($question)==='');
}

function send_push_notification($registatoin_ids, $message) {
    
    
    $url = 'https://android.googleapis.com/gcm/send';
    
    $fields = array(
        'registration_ids' => $registatoin_ids,
        'data' => $message,
    );
    
    $headers = array(
        'Authorization: key=' . 'AIzaSyBQcSbiG2HT-sx-rHe1C96NKe8PsGHBMhY',
        'Content-Type: application/json'
    );
    
    $ch = curl_init();
    
    
    curl_setopt($ch, CURLOPT_URL, $url);
    
    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    
    
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
    
    curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
    
    
    $result = curl_exec($ch);
    if ($result === FALSE) {
        die('Curl failed: ' . curl_error($ch));
    }
    
    
    curl_close($ch);
}

function GetUyeID(){
    if(isset($_SESSION["UyeID"])){
        return $_SESSION["UyeID"];
    }
    else{
        return false;
    }
}

function SetUyeID($UyeID){
    if(!isset($_SESSION["UyeID"])){
        $_SESSION["UyeID"] = $UyeID;
    }
}


function DosyaAdOlustur($dosyaAdi,$Klasor){
    $uret=array("as","rt","ty","yu","fg");
    $uzanti=substr($dosyaAdi,-4,4);
    $sayi_tut=rand(1,10000);
    return $Klasor.$uret[rand(0,4)].$sayi_tut.$dosyaAdi;
}

function seo($s) {
 $tr = array('ş','Ş','ı','I','İ','ğ','Ğ','ü','Ü','ö','Ö','Ç','ç','(',')','/',':',',');
 $eng = array('s','s','i','i','i','g','g','u','u','o','o','c','c','','','-','-','');
 $s = str_replace($tr,$eng,$s);
 $s = strtolower($s);
 $s = preg_replace('/&amp;amp;amp;amp;amp;amp;amp;amp;amp;.+?;/', '', $s);
 $s = preg_replace('/\s+/', '-', $s);
 $s = preg_replace('|-+|', '-', $s);
 $s = preg_replace('/#/', '', $s);
 $s = str_replace("?","",$s);
 $s = trim($s, '-');
 return $s;
}

function ResimYukle($Tip,$UyeID=1,$Baslik=""){
    //Tip 1 = Üye Profil Resmi
    //Tip 2 = Soru Resmi
    //Tip 3 = Yorum Resmi
    $klasor ="";
    $TekilDosya="";
    $Resimler = array();
    switch($Tip){
        case 1:
            $klasor = "Uploads/ProfileImages/";
            $TekilDosya = seo($_FILES["Resim"]["name"]);
            $YeniDosyaAdi = DosyaAdOlustur($TekilDosya,$klasor);
            if (move_uploaded_file($_FILES["Resim"]["tmp_name"],$YeniDosyaAdi)){
                return "http://omubumuapp.com/api/".$YeniDosyaAdi;
            }
        case 2:
            $klasor = "Uploads/SoruImages/";
            $Resim1 = seo($_FILES["Resim1"]['name']);
            $Resim2 = seo($_FILES["Resim2"]['name']);
            if(!empty($Resim1) && !empty($Resim2)){
                if(!file_exists($klasor.$UyeID.'-'.seo($Baslik)))
                    mkdir($klasor.$UyeID.'-'.seo($Baslik),0777);
                $Resim1DosyaAdi = DosyaAdOlustur($Resim1,$klasor.$UyeID.'-'.seo($Baslik).'/');
                $Resim2DosyaAdi = DosyaAdOlustur($Resim2,$klasor.$UyeID.'-'.seo($Baslik).'/');
                if (move_uploaded_file($_FILES["Resim1"]["tmp_name"],$Resim1DosyaAdi) &&
                    move_uploaded_file($_FILES["Resim2"]["tmp_name"],$Resim2DosyaAdi)){
                    array_push($Resimler,"http://omubumuapp.com/api/".$Resim1DosyaAdi);
                    array_push($Resimler,"http://omubumuapp.com/api/".$Resim2DosyaAdi);
                }
            }
            return $Resimler;
        case 3:
            $klasor = "Uploads/YorumImages/";
            $TekilDosya = seo($_FILES["Resim"]["name"]);
            $YeniDosyaAdi = DosyaAdOlustur($TekilDosya,$klasor);
            if (move_uploaded_file($_FILES["Resim"]["tmp_name"],$YeniDosyaAdi)){
                return "http://omubumuapp.com/api/".$YeniDosyaAdi;
            }
        default:
            return "";
    }
}

function SifirlamaMailGonder($EMail,$AdSoyad,$ResetKey){
    $mail = new PHPMailer();
    $mail->IsSMTP();
    $mail->SMTPDebug = false; 
    $mail->SMTPAuth = true;
    $mail->Host = "MailHost"; 
    $mail->Port = 25; 
    $mail->IsHTML(true);
    $mail->SetLanguage("tr", "phpmailer/language");
    $mail->CharSet  ="utf-8";
    $mail->Username = "MailUsername"; 
    $mail->Password = "Password"; 
    $mail->SetFrom("support@omubumuapp.com", "O mu Bu mu Destek Ekibi"); 
    $mail->AddAddress($EMail);
    $mail->Subject = "O mu Bu mu - Şfre Sıfırlama";
    $mail->Body = "
            Merhaba <b>".$AdSoyad."</b><br/><br/>
            
            ".date("Y-m-d")." tarihinde bir şifre sıfırlama isteğinde bulundun. Eğer bu işlemi sen gerçekleştirmediysen <b><a href='mailto:support@omubumuapp.com'>support@omubumuapp.com</a></b> a mail atabilirsin.<br/><br/>
            
            Bu işlemi sen gerçekleştirdiysen aşağıdaki bağlantıya tıklayarak bu maili doğrula.<br/><br/>
            
            <a href='http://omubumuapp.com/HesapSifirla.php?Mail=$EMail&ResetKey=$ResetKey'>Şfre Sıfırlama İsteğini Doğrula</a><br/><br/>
    
            iyi günler Dileriz...<br/><br/>
            
            O mu Bu mu Destek Ekibi
    ";
    if(!$mail->Send()){
        return false;
    } else {
        return true;
    }

}

?>