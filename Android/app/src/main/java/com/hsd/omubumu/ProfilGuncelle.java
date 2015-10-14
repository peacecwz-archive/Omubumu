package com.hsd.omubumu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.hsd.omubumu.Common.Types.ResultContext;
import com.hsd.omubumu.Common.Types.Uye;
import com.hsd.omubumu.Helper.FileHelper;
import com.hsd.omubumu.Helper.Mesaj;
import com.squareup.picasso.Picasso;


public class ProfilGuncelle extends ActionBarActivity {
    private EditText txtAdiSoyadi,txtEmail,txtSifre,txtSifreTekrar;
    private ImageView imgProfil;
    private ResultContext ResimGuncelle_Sonuc,ProfilGuncelle_Sonuc;
    private String Resim_Data, Resim_Adi;
    private ProgressDialog progressDialog;
    private final int Resim_Sec = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_guncelle);

        txtAdiSoyadi = (EditText)findViewById(R.id.Profil_txtAdiSoyadi);
        txtEmail = (EditText)findViewById(R.id.Profil_txtEmail);
        txtSifre = (EditText)findViewById(R.id.Profil_txtSifre);
        txtSifreTekrar = (EditText)findViewById(R.id.Profil_txtSifreTekrar);
        imgProfil = (ImageView)findViewById(R.id.Profil_imgProfil);

        Uye uye = (Uye)Giris.GetUye().getData();
        txtAdiSoyadi.setText(uye.getAdiSoyadi());
        txtEmail.setText(uye.getEmail());
        Picasso.with(this).load(uye.getProfileImage()).into(imgProfil);
    }

    public void Profil_ResimOnClick(View v){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, Resim_Sec);
    }

    public void Profil_btnGunclleOnClick(View v){
        new ProfilGunclleAPI().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resim) {
        super.onActivityResult(requestCode, resultCode, resim);
        switch (requestCode) {
            case Resim_Sec:
                if (resultCode == RESULT_OK) {
                    Resim_Data = FileHelper.GetPathFromUri(resim.getData(), getBaseContext());
                    Resim_Adi = FileHelper.GetFileNameFromPath(resim.getData(), getBaseContext());
                    new ResimGuncelleAPI().execute();
                }
        }
    }

    public void Profil_GeriOnClick(View v) {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profil_guncelle, menu);
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

    private class ProfilGunclleAPI extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            // Do stuff before the operation
            progressDialog = new ProgressDialog(ProfilGuncelle.this);
            progressDialog.setTitle("O mu ? Bu mu ?");
            progressDialog.setMessage("Profil Güncelleniyor...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            ProfilGuncelle_Sonuc = Giris.Client.ProfilGuncelle(txtAdiSoyadi.getText().toString(),
                    txtEmail.getText().toString(),
                    txtSifre.getText().toString());
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (ProfilGuncelle_Sonuc != null) {
                if(ProfilGuncelle_Sonuc.getSonuc()){
                    Mesaj.SonucMesaj(ProfilGuncelle_Sonuc.getMesaj(),ProfilGuncelle.this);
                }
                else{
                    Mesaj.HataMesaj(ProfilGuncelle_Sonuc.getMesaj(),ProfilGuncelle.this);
                }
            }
            progressDialog.dismiss();
        }
    }

    private class ResimGuncelleAPI extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            // Do stuff before the operation
        }

        @Override
        protected String doInBackground(String... params) {

            ResimGuncelle_Sonuc = Giris.Client.ResimGuncelle(Resim_Data,Resim_Adi);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (ResimGuncelle_Sonuc != null) {
                if(ResimGuncelle_Sonuc.getSonuc()){
                    Mesaj.SonucMesaj(ResimGuncelle_Sonuc.getMesaj(), ProfilGuncelle.this);
                }
                else{
                    Mesaj.HataMesaj(ResimGuncelle_Sonuc.getMesaj(), ProfilGuncelle.this);
                }
            }
        }
    }

}
