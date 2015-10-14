package com.hsd.omubumu;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.hsd.omubumu.Common.Types.Kategori;
import com.hsd.omubumu.Common.Types.ResultContext;
import com.hsd.omubumu.Helper.FileHelper;
import com.hsd.omubumu.Helper.Mesaj;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class SoruEkle extends ActionBarActivity {
    private EditText
            txtBaslik,
            txtAciklama;
    private Spinner listKategoriler;
    private ResultContext SoruEkle_Sonuc, Kategoriler_Sonuc;
    private final int Resim1_Sec = 100;
    private final int Resim2_Sec = 200;
    private String Resim1_Adi, Resim2_Adi;
    private String Resim1_Data, Resim2_Data;
    private ImageView imgResim1,imgResim2;
    private String KategoriID;
    Boolean Resim;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soru_ekle);

        imgResim1 = (ImageView)findViewById(R.id.SoruEkle_imgResim1);
        imgResim2 = (ImageView)findViewById(R.id.SoruEkle_imgResim2);

        txtBaslik = (EditText) findViewById(R.id.soruEkle_Baslik);
        txtAciklama = (EditText) findViewById(R.id.soruEkle_Aciklama);
        listKategoriler = (Spinner) findViewById(R.id.SoruEkle_listKategoriler);
        listKategoriler.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                List<Kategori> kategoriler = ((List<Kategori>) Kategoriler_Sonuc.getData());
                KategoriID = kategoriler.get(position).getKategoriID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        new KategorilerAPI().execute();
    }

    public void SoruEkle_EkleOClick(View v) {
        new SoruEkleAPI().execute();

    }

    public void SoruEkle_Resim1OnClick(View v) {
        selectImage(true);
    }

    public void SoruEkle_Resim2OnClick(View v) {
        selectImage(false);
    }

    public void SoruEkle_GeriOnClick(View v) {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_soru_ekle, menu);
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

    private void selectImage(final Boolean resim) {
        final CharSequence[] items = { "Fotoğraf Çek", "Galeriden Seç",
                "Iptal" };

        AlertDialog.Builder builder = new AlertDialog.Builder(SoruEkle.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Fotoğraf Çek")) {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture,1);
                } else if (items[item].equals("Galeriden Seç")) {
                    Resim = resim;
                    Crop.pickImage(SoruEkle.this);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void beginCrop(Uri source) {
        Uri destination =null;
        if(Resim){
            Resim1_Adi = FileHelper.GetFileNameFromPath(source,SoruEkle.this);
            destination = Uri.fromFile(new File(getCacheDir(), Resim1_Adi));
        }
        else{
            Resim2_Adi = FileHelper.GetFileNameFromPath(source,SoruEkle.this);
            destination = Uri.fromFile(new File(getCacheDir(), Resim2_Adi));
        }
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            if(Resim){
                Resim1_Data = Crop.getOutput(result).getPath();
                Resim1_Adi = FileHelper.GetFileNameFromPath(Crop.getOutput(result),getBaseContext());
                imgResim1.setImageURI(Crop.getOutput(result));
            }
            else{
                Resim2_Data = Crop.getOutput(result).getPath();
                Resim2_Adi = FileHelper.GetFileNameFromPath(Crop.getOutput(result),SoruEkle.this);
                imgResim2.setImageURI(Crop.getOutput(result));
            }
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resim) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(resim.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, resim);
        }
    }

    private class KategorilerAPI extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            // Do stuff before the operation
        }

        @Override
        protected String doInBackground(String... params) {

            Kategoriler_Sonuc = Giris.Client.Kategoriler();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (Kategoriler_Sonuc != null) {
                List<String> KategoriAdlar = new ArrayList<String>();
                List<Kategori> kategoriler = (List<Kategori>) Kategoriler_Sonuc.getData();
                for (int i = 0; i < kategoriler.size(); i++) {
                    KategoriAdlar.add(kategoriler.get(i).getKategoriAdi());
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(SoruEkle.this,
                        android.R.layout.simple_spinner_item, KategoriAdlar);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                listKategoriler.setAdapter(dataAdapter);
            }

        }
    }

    private class SoruEkleAPI extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            // Do stuff before the operation
            progressDialog = new ProgressDialog(SoruEkle.this);
            progressDialog.setTitle("O mu ? Bu mu ?");
            progressDialog.setMessage("Soru Ekleniyor...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            SoruEkle_Sonuc = Giris.Client.SoruEkle(
                    Resim1_Data, Resim1_Adi, Resim2_Data, Resim2_Adi,
                    txtBaslik.getText().toString(),
                    txtAciklama.getText().toString(),
                    KategoriID
            );
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            if (SoruEkle_Sonuc != null) {
                if(SoruEkle_Sonuc.getSonuc()){
                    Mesaj.SonucMesaj(SoruEkle_Sonuc.getMesaj(), SoruEkle.this);
                    finish();
                }
                else {
                    Mesaj.HataMesaj(SoruEkle_Sonuc.getMesaj(), SoruEkle.this);
                }
            }
        }
    }
}