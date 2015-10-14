<?php
require("SMTP/class.phpmailer.php");

function generateStrongPassword($length = 9, $add_dashes = false, $available_sets = 'luds')
{
	$sets = array();
	if(strpos($available_sets, 'l') !== false)
		$sets[] = 'abcdefghjkmnpqrstuvwxyz';
	if(strpos($available_sets, 'u') !== false)
		$sets[] = 'ABCDEFGHJKMNPQRSTUVWXYZ';
	if(strpos($available_sets, 'd') !== false)
		$sets[] = '23456789';
	if(strpos($available_sets, 's') !== false)
		$sets[] = '!@#$%&*?';

	$all = '';
	$password = '';
	foreach($sets as $set)
	{
		$password .= $set[array_rand(str_split($set))];
		$all .= $set;
	}

	$all = str_split($all);
	for($i = 0; $i < $length - count($sets); $i++)
		$password .= $all[array_rand($all)];

	$password = str_shuffle($password);

	if(!$add_dashes)
		return $password;

	$dash_len = floor(sqrt($length));
	$dash_str = '';
	while(strlen($password) > $dash_len)
	{
		$dash_str .= substr($password, 0, $dash_len) . '-';
		$password = substr($password, $dash_len);
	}
	$dash_str .= $password;
	return $dash_str;
}

function SifreGonder($EMail,$AdSoyad,$Sifre){
    $mail = new PHPMailer();
    $mail->IsSMTP();
    $mail->SMTPDebug = false; 
    $mail->SMTPAuth = true;
    $mail->Host = "MailHost"; 
    $mail->Port = 587; 
    $mail->IsHTML(true);
    $mail->SetLanguage("tr", "phpmailer/language");
    $mail->CharSet  ="utf-8";
    $mail->Username = "MailUsername"; 
    $mail->Password = "Password"; 
    $mail->SetFrom("support@omubumuapp.com", "O mu Bu mu Ekibi"); 
    $mail->AddAddress($EMail);
    $mail->Subject = "O mu Bu mu - Þfreniz Sýfýrlandý";
    $mail->Body = "
            Merhaba <b>".$AdSoyad."</b><br/><br/>
            
            ".date("Y-m-d")." tarihinde bir þifreniz sýfýrlandý. Eðer bu iþlemi sen gerçekleþtirmediysen <b><a href='mailto:uyelik@omubumuapp.com'>uyelik@omubumuapp.com</a></b> a mail atabilirsin.<br/><br/>
            
            <b>Yeni Þifreniz:</b> ".$Sifre."<br/><br/>
    
            iyi günler Dileriz...<br/><br/>
            
            O mu Bu mu Ekibi
    ";
    if(!$mail->Send()){
        return false;
    } else {
        return true;
    }

}

?>


<html>
<head>
    <title>Þifremi Sýfýrla</title>
</head>
<body>
    <?php
    if(isset($_GET["Mail"]) & isset($_GET["ResetKey"])){
		include('DBConnect.php');
        $Mail = $_GET["Mail"];
        $ResetKey = $_GET["ResetKey"];
        $result = DB::getRow("SELECT Email,AdiSoyadi,SifirlamaKodu FROM uyeler WHERE Email=:Email",
            array(':Email'=>$Mail));
		if($result==false){
            echo "Bu Mail Adresi Bulunamadi.";
        }
        else{
			if(md5(base64_encode($result->SifirlamaKodu))==$ResetKey){
				$YeniSifreSafe = generateStrongPassword();
				$YeniSifre = md5($YeniSifreSafe);
				if(DB::exec("UPDATE uyeler SET Sifre=:Sifre, SifirlamaKodu=NULL WHERE ( Email=:Email);",
				array(':Email'=>$Mail,':Sifre'=>$YeniSifre))==false){
					echo "Sifreniz Güncellenirken hata olustu.";
				}
				else{
					if(SifreGonder($Mail,$result->AdiSoyadi,$YeniSifreSafe)){
						echo "Yeni Þifreniz Mail olarak gönderilmiþtir.";
					}
					else{
						echo "Mail gönderilirken hata oluþtu";
					}
				}
			}
			else
			{
				echo "Gönderilen Sýfýrlama Kodu Hatalý.";
			}
        }
    }
    ?>
</body>
</html>
