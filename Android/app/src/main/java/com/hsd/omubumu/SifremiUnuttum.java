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
import com.hsd.omubumu.Common.Types.Soru;
import com.hsd.omubumu.Helper.Mesaj;


public class SifremiUnuttum extends ActionBarActivity {
    private EditText txtEmail;
    private ResultContext Sonuc;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifremi_unuttum);

        txtEmail = (EditText)findViewById(R.id.sifremiUnt_txtEmail);
    }

    public void SifremiUnt_btnGonderOnClick(View v){
        new SifremiUnuttumAPI().execute();
    }

    public void SifremiUnt_GeriOnClick(View v) {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sifremi_unuttum, menu);
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

    private class SifremiUnuttumAPI extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            // Do stuff before the operation
            progressDialog = new ProgressDialog(SifremiUnuttum.this);
            progressDialog.setTitle("O mu ? Bu mu ?");
            progressDialog.setMessage("Şifre Maili Gönderiliyor...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            Sonuc = Giris.Client.SifremiUnuttum(txtEmail.getText().toString());
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (Sonuc != null) {
                if(Sonuc.getSonuc()){
                    Mesaj.SonucMesaj(Sonuc.getMesaj(),SifremiUnuttum.this);
                }
                else{
                    Mesaj.HataMesaj(Sonuc.getMesaj(),SifremiUnuttum.this);
                }
            }
            progressDialog.dismiss();
        }
    }
}
