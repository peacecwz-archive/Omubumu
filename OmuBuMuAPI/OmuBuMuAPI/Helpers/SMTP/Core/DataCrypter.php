<?php

function CreateRestKey($Mail){
    return md5(base64_encode($Mail . rand(0,100000)).rand(0,1000));
}

?>