<?php

function CreateRestKey($Mail){
    return md5(base64_encode($Mail . rand(0,100000)).rand(0,1000));
}

function Encrypt($decoded)
{
    $Original = "ABCDEFGHIİJKLMNOPRSŞTUÜVYZWXQ1234567890!'^+%&/()=?_><:.;,~>£#½{[]}|abcdefghıijklmnoöprsştuüvyzwqx";

    $Sezar = "W>niE?IPK#RQxlNs(Br£:9J/w>50m]FVdvh2T}<M6İ3HşeXU_;ÜfG~Z{jyüpöCqzc)ıAaD1ŞYb.½SoLg8%7O&k,4!u'+|=^[t";
    $decoded =base64_encode($decoded);
    
    
    $encoded = "";
    for($i=0;$i<strlen($decoded);$i++){
        $encoded = $encoded . $Sezar[strpos($Original,$decoded[$i])];
    }
    return $decoded;
}

function Decrypt($encoded)
{
    $Original = "ABCDEFGHIİJKLMNOPRSŞTUÜVYZWXQ1234567890!'^+%&/()=?_><:.;,~>£#½{[]}|abcdefghıijklmnoöprsştuüvyzwqx";

    $Sezar = "W>niE?IPK#RQxlNs(Br£:9J/w>50m]FVdvh2T}<M6İ3HşeXU_;ÜfG~Z{jyüpöCqzc)ıAaD1ŞYb.½SoLg8%7O&k,4!u'+|=^[t";

    $decoded = "";
    for($i=0;$i<strlen($encoded);$i++){
        $decoded = $decoded . $Original[strpos($Sezar,$encoded[$i])];
    }
    return base64_decode($encoded);
}

?>