package com.hsd.omubumu;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.hsd.omubumu.Common.Types.ResultContext;
import com.hsd.omubumu.Helper.Mesaj;


public class KayitOl extends ActionBarActivity {
    private EditText
                txtKullaniciAdi,
                txtAdiSoyadi,
                txtEmail,
                txtSifre,
                txtSifreTekrar;

    ResultContext Sonuc;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);
        txtAdiSoyadi = (EditText)findViewById(R.id.kayit_adisoyadi);
        txtKullaniciAdi = (EditText)findViewById(R.id.kayit_kullaniciAdi);
        txtEmail = (EditText)findViewById(R.id.kayit_email);
        txtSifre = (EditText)findViewById(R.id.kayit_sifre);
        txtSifreTekrar = (EditText)findViewById(R.id.kayit_sifreTekrar);
    }

    public void Kayit_BtnKayitOnClick(View v){
        new KayitAPI().execute();
    }

    public void Button3_OnClick(View v){
        new KayitAPI().execute();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kayit_ol, menu);
        return true;
    }

    public void KayitOl_GeriOnClick(View v) {
        finish();
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

    private class KayitAPI extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            // Do stuff before the operation
            progressDialog = new ProgressDialog(KayitOl.this);
            progressDialog.setTitle("O mu ? Bu mu ?");
            progressDialog.setMessage("Hesabınız Oluşturuluyor...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            Sonuc = Giris.Client.Kayit(
                    txtKullaniciAdi.getText().toString(),
                    txtAdiSoyadi.getText().toString(),
                    txtEmail.getText().toString(),
                    txtSifre.getText().toString()
            );
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            if (Sonuc != null) {
                if(Sonuc.getSonuc()){
                    Mesaj.SonucMesaj(Sonuc.getMesaj(),KayitOl.this);
                }
                else{
                    Mesaj.HataMesaj(Sonuc.getMesaj(),KayitOl.this);
                }
            }
            progressDialog.dismiss();
        }
    }
}
