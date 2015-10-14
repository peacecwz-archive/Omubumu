package com.hsd.omubumu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


import com.hsd.omubumu.Common.DataClient;
import com.hsd.omubumu.Common.Types.ResultContext;
import com.dd.processbutton.iml.ActionProcessButton;


public class Giris extends ActionBarActivity {
    public static DataClient Client = new DataClient();
    private EditText txtKullaniciAdi, txtSifre;
    public static ResultContext Uye;
    private ActionProcessButton btnSignIn;
    private SharedPreferences settings;
    private Boolean saveLogin;
    private String username, password;
    private Button ok;
    private EditText editTextUsername, editTextPassword;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_giris);

        txtKullaniciAdi = (EditText) findViewById(R.id.giris_kullaniciAdi);
        txtSifre = (EditText) findViewById(R.id.editTextPassword);
        btnSignIn = (ActionProcessButton) findViewById(R.id.btnSignIn);
        txtKullaniciAdi = (EditText) findViewById(R.id.giris_kullaniciAdi);
        txtSifre = (EditText) findViewById(R.id.editTextPassword);
        settings = PreferenceManager.getDefaultSharedPreferences(Giris.this);
        if (settings.getBoolean("BeniHatirla", false)) {
            txtKullaniciAdi.setText(settings.getString("KullaniciAdi", ""));
            txtSifre.setText(settings.getString("Sifre", ""));
            new GirisAPI().execute();
        }
    }

    public static DataClient GetClient() {
        return Client;
    }

    public static ResultContext GetUye() {
        return Uye;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_giris, menu);
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

    public void SetProc(int p) {
        btnSignIn.setProgress(p);
    }

    public void Giris_BtnSifremiUntClick(View v) {
        Intent sifremiUnututm = new Intent(getBaseContext(), SifremiUnuttum.class);
        startActivity(sifremiUnututm);
    }

    public void Giris_BtnGirisOnClick(View v) {
        btnSignIn.setProgress(0);
        new GirisAPI().execute();
    }

    public void Giris_BtnKayitOnClick(View v) {
        Intent kayitOlForm = new Intent(getBaseContext(), KayitOl.class);
        startActivity(kayitOlForm);
    }

    public static void Log(String mesaj){
        Client.Log(mesaj);
    }

    private class GirisAPI extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            SetProc(25);
            progressDialog = new ProgressDialog(Giris.this);
            progressDialog.setTitle("O mu? Bu mu?");
            progressDialog.setMessage("Giriş yapılıyor... Lütfen Bekleyiniz!");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            Uye = Client.Giris(txtKullaniciAdi.getText().toString(),
                    txtSifre.getText().toString());
            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            if (Uye != null && Uye.getSonuc()) {
                if (settings == null)
                    settings = PreferenceManager.getDefaultSharedPreferences(Giris.this);
                settings.edit().putString("KullaniciAdi", txtKullaniciAdi.getText().toString()).commit();
                settings.edit().putString("Sifre", txtSifre.getText().toString()).commit();
                settings.edit().putBoolean("BeniHatirla", true).commit();
                btnSignIn.setText("Giriş Başarılı");
                SetProc(100);
                Intent hosgeldinForm = new Intent(getBaseContext(), Hosgeldin.class);
                startActivity(hosgeldinForm);
            } else {
                if (Uye != null) {
                    SetProc(-1);
                    btnSignIn.setText(Uye.getMesaj());
                }
            }
        }
    }
}