package com.hsd.omubumu.GridLayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hsd.omubumu.Common.Types.Soru;
import com.hsd.omubumu.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by APALYazilim on 12.7.2015.
 */
public class SoruGrid extends BaseAdapter {
    private Context mContext;
    private List<Soru> sorular;

    public SoruGrid(Context c, List<Soru> sorular) {
        mContext = c;
        this.sorular = sorular;
    }

    @Override
    public int getCount() {
        return sorular.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.grid_sorular, parent, false);
        } else {
            v = (View) convertView;
        }
        ((TextView) v.findViewById(R.id.soru_kategoriAdi)).setText(sorular.get(position).getKategoriAdi());
        ((TextView) v.findViewById(R.id.soru_baslik)).setText(sorular.get(position).getBaslik());
        ((TextView) v.findViewById(R.id.soru_kullaniciAdi)).setText(sorular.get(position).getKullaniciAdi());
        ((TextView) v.findViewById(R.id.soru_tarih)).setText(sorular.get(position).getSaveDate());
        Picasso.with(mContext).load(sorular.get(position).getResim1()).into(((ImageView) v.findViewById(R.id.first)));
        Picasso.with(mContext).load(sorular.get(position).getResim2()).into(((ImageView) v.findViewById(R.id.second)));
        Picasso.with(mContext).load(sorular.get(position).getProfileImage()).into(((ImageView) v.findViewById(R.id.soru_profile)));
        return v;
    }
}