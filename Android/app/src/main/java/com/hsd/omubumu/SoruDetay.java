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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hsd.omubumu.Common.Types.Oylar;
import com.hsd.omubumu.Common.Types.ResultContext;
import com.hsd.omubumu.Common.Types.Soru;
import com.hsd.omubumu.Common.Types.Yorum;
import com.hsd.omubumu.GridLayout.YorumGrid;
import com.hsd.omubumu.Helper.Mesaj;
import com.squareup.picasso.Picasso;

import java.util.List;


public class SoruDetay extends ActionBarActivity {

    private TextView lblKullaniciAdi, lblTarih, lblBaslik,lblResim1Oy,lblResim2Oy;
    private ImageView imgResim1, imgResim2, imgProfil;
    private EditText txtYorum;
    private GridView listYorumlar;
    private ResultContext Yorumlar_Sonuc,YorumEkle_Sonuc,Oylar_Sonuc,Oyla_Sonuc;
    private Soru soru;
    private Boolean ResimNo;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soru_detay);

        lblKullaniciAdi = (TextView) findViewById(R.id.SoruDetay_lblKullaniciAdi);
        lblTarih = (TextView) findViewById(R.id.SoruDetay_lblTarih);
        lblBaslik = (TextView) findViewById(R.id.SoruDetay_lblBaslik);

        lblResim1Oy = (TextView)findViewById(R.id.SoruDetay_lblResim1Oy);
        lblResim2Oy = (TextView)findViewById(R.id.SoruDetay_lblResim2Oy);

        txtYorum = (EditText)findViewById(R.id.SoruDetay_txtYorum);

        listYorumlar = (GridView)findViewById(R.id.SoruDetay_listYorumlar);

        imgResim1 =  (ImageView)findViewById(R.id.SoruDetay_imgResim1);
        imgResim2 =  (ImageView)findViewById(R.id.SoruDetay_imgResim2);

        imgProfil =  (ImageView)findViewById(R.id.SoruDetay_imgProfil);
        listYorumlar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent myIntent = new Intent(getBaseContext(), UyeProfil.class);
                myIntent.putExtra("UyeID",((List<Yorum>)Yorumlar_Sonuc.getData()).get(position).getUyeID());
                SoruDetay.this.startActivity(myIntent);
            }
        });
        Bundle b = getIntent().getExtras();
        if(b!=null){
            soru = (Soru)b.getSerializable("Soru");
            new OylarAPI().execute();

            lblKullaniciAdi.setText(soru.getKullaniciAdi());
            lblBaslik.setText(soru.getBaslik());
            lblTarih.setText(soru.getSaveDate());

            Picasso.with(this).load(soru.getResim1()).into(imgResim1);
            Picasso.with(this).load(soru.getResim2()).into(imgResim2);
            Picasso.with(this).load(soru.getProfileImage()).into(imgProfil);
            new OylarAPI().execute();
            new YorumAPI().execute();
        }
    }

    public void SoruDetay_GeriOnClick(View v) {
        finish();
    }

    public void SoruDetay_Resim1OnClick(View v){
        ResimNo = true;
        new OylaAPI().execute();
    }

    public void SoruDetay_Resim2OnClick(View v){
        ResimNo=false;
        new OylaAPI().execute();
    }

    public void SoruDetay_btnYorumEkle(View v){
        new YorumYapAPI().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_soru_detay, menu);
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

    private class OylarAPI extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            // Do stuff before the operation
        }

        @Override
        protected String doInBackground(String... params) {

            Oylar_Sonuc = Giris.Client.Oylar(soru.getSoruID());
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (Oylar_Sonuc != null) {
                if(Oylar_Sonuc.getSonuc()){
                    Oylar oylar = ((Oylar)Oylar_Sonuc.getData());
                    lblResim1Oy.setText(oylar.getResim1Oy());
                    lblResim2Oy.setText(oylar.getResim2Oy());
                }
            }
        }
    }

    private class OylaAPI extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(SoruDetay.this);
            progressDialog.setTitle("O mu ? Bu mu ? ");
            progressDialog.setMessage("Oyunuz Güncelleniyor...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            Oyla_Sonuc = Giris.Client.Oyla(soru.getSoruID(),ResimNo);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            if (Oyla_Sonuc != null) {
                if(Oyla_Sonuc.getSonuc()){
                    new OylarAPI().execute();
                }
            }
        }
    }

    private class YorumYapAPI extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            // Do stuff before the operation
            progressDialog = new ProgressDialog(SoruDetay.this);
            progressDialog.setTitle("O mu ? Bu mu ? ");
            progressDialog.setMessage("Yorumlar Yapılıyor...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            YorumEkle_Sonuc = Giris.Client.YorumEkle(soru.getSoruID(), txtYorum.getText().toString(), null, "");
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            if (YorumEkle_Sonuc != null) {
                if(YorumEkle_Sonuc.getSonuc()){
                    Mesaj.SonucMesaj(YorumEkle_Sonuc.getMesaj(),SoruDetay.this);
                    new YorumAPI().execute();
                }
                else{
                    Mesaj.HataMesaj(YorumEkle_Sonuc.getMesaj(),SoruDetay.this);
                }
            }
        }
    }

    private class YorumAPI extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(SoruDetay.this);
            progressDialog.setTitle("O mu ? Bu mu ? ");
            progressDialog.setMessage("Soru Detayları Yükleniyor...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            Yorumlar_Sonuc = Giris.Client.Yorumlar(soru.getSoruID());
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            if (Yorumlar_Sonuc != null) {
                if(Yorumlar_Sonuc.getSonuc()){
                    listYorumlar.setAdapter(new YorumGrid(SoruDetay.this, (List<Yorum>) Yorumlar_Sonuc.getData()));
                }
            }
        }
    }
}