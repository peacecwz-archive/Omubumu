package com.hsd.omubumu.Helper;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Created by APALYazilim on 23.7.2015.
 */
public class DataEncrypter {
    private static char[] Original = "ABCDEFGHIİJKLMNOPRSŞTUÜVYZWXQ1234567890!'^+%&/()=?_><:.;,~>£#½{[]}|abcdefghıijklmnoöprsştuüvyzwqx".toCharArray();

    private static char[] Sezar = "W>niE?IPK#RQxlNs(Br£:9J/w>50m]FVdvh2T}<M6İ3HşeXU_;ÜfG~Z{jyüpöCqzc)ıAaD1ŞYb.½SoLg8%7O&k,4!u'+|=^[t".toCharArray();

    private static Charset UTF8 = Charset.forName("UTF-8");

    private static String Base64Decode(String source) {
        byte[] byteArray;
        try {
            byteArray = source.getBytes("UTF-8");
            return new String(Base64.decode(source, Base64.DEFAULT));
        } catch (UnsupportedEncodingException e) {

            return "";
        }
    }

    private static String Base64Encode(String text) {
        return Base64.encodeToString(text.getBytes(UTF8), Base64.DEFAULT);
    }

    public static String Encrypt(String decoded) {
        decoded = Base64Encode(decoded).replace("\n","");
        String encoded = "";
        for (char ch : decoded.toCharArray()) {
            encoded += Sezar[new String(Original).indexOf(ch)];
        }
        return decoded;
    }

    public static String Decrypt(String encoded) {
        encoded = encoded.replace("\n", "");
        String decoded = "";
        decoded = Base64Decode(encoded);
        return decoded;
    }
}