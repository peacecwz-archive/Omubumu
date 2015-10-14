package com.hsd.omubumu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.hsd.omubumu.Common.Types.ResultContext;
import com.hsd.omubumu.Common.Types.Soru;
import com.hsd.omubumu.Common.Types.Uye;
import com.hsd.omubumu.GridLayout.SoruGrid;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UyeProfil extends ActionBarActivity {
    private TextView lblAdiSoyadi,lblKullaniciAdi;
    private RoundedImageView imgProfile;
    private GridView listSorular;
    private ResultContext Sorular_Sonuc,UyeProfil_Sonuc;
    private String UyeID = "-1";
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uye_profil);

        lblAdiSoyadi = (TextView)findViewById(R.id.uyeProfil_lblAdiSoyadi);
        lblKullaniciAdi = (TextView)findViewById(R.id.uyeProfil_lblKullaniciAdi);
        imgProfile = (RoundedImageView )findViewById(R.id.uyeProfil_imgProfil);
        listSorular = (GridView)findViewById(R.id.uyeProfil_listSorular);
        listSorular.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent myIntent = new Intent(getBaseContext(), SoruDetay.class);
                myIntent.putExtra("Soru",((List<Soru>)Sorular_Sonuc.getData()).get(position));
                UyeProfil.this.startActivity(myIntent);
            }
        });
        Bundle b = getIntent().getExtras();
        if(b!=null){
            UyeID = b.getString("UyeID");
            new UyeProfilAPI().execute();

        }
        else{
            Uye uye = (Uye)Giris.GetUye().getData();
            lblAdiSoyadi.setText(uye.getAdiSoyadi());
            lblKullaniciAdi.setText(uye.getKullaniciAdi());
            Picasso.with(this).load(uye.getProfileImage()).into(imgProfile);
        }
        new SorularAPI().execute();
    }

    public void UyeProfil_GeriOnClick(View v) {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_uye_profil, menu);
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

    private class UyeProfilAPI extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            // Do stuff before the operation
            progressDialog = new ProgressDialog(UyeProfil.this);
            progressDialog.setTitle("O mu ? Bu mu ? ");
            progressDialog.setMessage("Sorular Yükleniyor...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            UyeProfil_Sonuc = Giris.Client.UyeProfil(UyeID);

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (UyeProfil_Sonuc != null) {
                if(UyeProfil_Sonuc.getSonuc()){
                    Uye uye = (Uye)UyeProfil_Sonuc.getData();
                    lblAdiSoyadi.setText(uye.getAdiSoyadi());
                    lblKullaniciAdi.setText(uye.getKullaniciAdi());
                    Picasso.with(UyeProfil.this).load(uye.getProfileImage()).into(imgProfile);
                    new SorularAPI().execute();
                }
            }
            progressDialog.dismiss();
        }
    }

    private class SorularAPI extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            // Do stuff before the operation
        }

        @Override
        protected String doInBackground(String... params) {
            if(UyeID!="-1"){
                Sorular_Sonuc = Giris.Client.Sorular("", "", UyeID);
            }
            else{
                Sorular_Sonuc = Giris.Client.Sorular("","",((Uye)Giris.Uye.getData()).getUyeID());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (Sorular_Sonuc != null) {
                if(Sorular_Sonuc.getSonuc()){
                    listSorular.setAdapter(new SoruGrid(UyeProfil.this,(List<Soru>)Sorular_Sonuc.getData()));
                }
            }
        }
    }
}
