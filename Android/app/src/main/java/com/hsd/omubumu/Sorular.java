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
import android.widget.ListView;

import com.hsd.omubumu.Common.Types.ResultContext;
import com.hsd.omubumu.Common.Types.Soru;
import com.hsd.omubumu.GridLayout.SoruGrid;
import com.hsd.omubumu.Helper.Mesaj;

import java.util.List;


public class Sorular extends ActionBarActivity {

    private ResultContext Sonuc;
    private ListView ListSorularGrid;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorular);

        Bundle b = getIntent().getExtras();
        new SorularAPI().execute();
        ListSorularGrid = (ListView)findViewById(R.id.sorular_listSoru);
        ListSorularGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent myIntent = new Intent(getBaseContext(), SoruDetay.class);
                myIntent.putExtra("Soru",((List<Soru>)Sonuc.getData()).get(position));
                Sorular.this.startActivity(myIntent);
            }
        });
    }

    public void Sorular_GeriOnClick(View v) {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sorular, menu);
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

    private class SorularAPI extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(Sorular.this);
            progressDialog.setTitle("O mu ? Bu mu ? ");
            progressDialog.setMessage("Sorular Yükleniyor...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            Sonuc = Giris.Client.Sorular("","","");
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            if (Sonuc != null) {
                if(Sonuc.getSonuc()){
                    ListSorularGrid.setAdapter(new SoruGrid(Sorular.this,(List<Soru>)Sonuc.getData()));
                }else {
                    Mesaj.HataMesaj(Sonuc.getMesaj(),Sorular.this);
                }
            }
        }
    }
}
