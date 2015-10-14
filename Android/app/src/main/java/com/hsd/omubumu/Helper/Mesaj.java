package com.hsd.omubumu.Helper;

import android.content.Context;
import android.graphics.Color;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by APALYazilim on 10.7.2015.
 */
public class Mesaj {
    public static void SonucMesaj(String mesaj,Context context)
    {
        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setContentText(mesaj);
        pDialog.setTitle("O mu? Bu mu?");
        pDialog.setTitleText("Yupppiii :)");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public static void UyarıMesaj(String mesaj,Context context)
    {
        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setContentText(mesaj);
        pDialog.setTitle("O mu? Bu mu?");
        pDialog.setTitleText("Hımmmmm :/");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public static void HataMesaj(String mesaj,Context context)
    {
        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setContentText(mesaj);
        pDialog.setTitle("O mu? Bu mu?");
        pDialog.setTitleText("Uffff :(");
        pDialog.setCancelable(false);
        pDialog.show();
    }
}
