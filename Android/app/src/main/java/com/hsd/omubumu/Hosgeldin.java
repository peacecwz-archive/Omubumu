package com.hsd.omubumu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hsd.omubumu.Common.Types.Uye;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class Hosgeldin extends ActionBarActivity {
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private RoundedImageView imgProfile;
    private TextView lblKullaniciAdi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hosgeldin);
        Uye uye = (Uye)Giris.Uye.getData();
        lblKullaniciAdi = (TextView)findViewById(R.id.Hosgeldin_lblUsername);
        imgProfile = (RoundedImageView)findViewById(R.id.Hosgeldin_imgProfile);
        Picasso.with(this).load(uye.getProfileImage()).fit().into(imgProfile);
        lblKullaniciAdi.setText(uye.getKullaniciAdi());
    }

    public void Hosgeldin_SorularOnClick(View v){
        Intent sorularForm = new Intent(getBaseContext(),Sorular.class);
        startActivity(sorularForm);
    }

    public void Hosgeldin_SoruEkleOnClick(View v){
        Intent soruEkleForm = new Intent(getBaseContext(),SoruEkle.class);
        startActivity(soruEkleForm);
    }

    public void Hosgeldin_HesabimOnClick(View v){
        Intent uyeProfil = new Intent(getBaseContext(),UyeProfil.class);
        startActivity(uyeProfil);
    }

    public void Hosgeldin_AyarlarOnClick(View v){
        Intent profilGunclle = new Intent(getBaseContext(),ProfilGuncelle.class);
        startActivity(profilGunclle);
    }

    public void Hosgeldin_CikisOnClick(View v){
        AlertDialog alertMessage = new AlertDialog.Builder(this).create();
        alertMessage.setTitle("O mu? Bu mu?");
        alertMessage.setMessage("Çıkış yapmak istiyor musunuz?\"");
        alertMessage.setButton("Uygulamadan Çık", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        alertMessage.setButton2("Hesabımdan Çık", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                settings.edit().clear().commit();
                finish();
            }
        });
        alertMessage.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hosgeldin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
