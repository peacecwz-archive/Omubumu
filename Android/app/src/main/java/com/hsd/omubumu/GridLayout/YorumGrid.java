package com.hsd.omubumu.GridLayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hsd.omubumu.Common.Types.Yorum;
import com.hsd.omubumu.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by APALYazilim on 24.7.2015.
 */
public class YorumGrid extends BaseAdapter {
    private Context mContext;
    private List<Yorum> yorumlar;

    public YorumGrid(Context c, List<Yorum> yorumlar) {
        mContext = c;
        this.yorumlar = yorumlar;
    }

    @Override
    public int getCount() {
        return yorumlar.size();
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
            v = inflater.inflate(R.layout.grid_yorumlar, parent, false);
        } else {
            v = (View) convertView;
        }
        ((TextView) v.findViewById(R.id.yorum_lblKullaniciAdi)).setText(yorumlar.get(position).getKullaniciAdi());
        ((TextView) v.findViewById(R.id.yorum_lblYorum)).setText(yorumlar.get(position).getYorum());
        ((TextView) v.findViewById(R.id.yorum_lblTarih)).setText(yorumlar.get(position).getSaveDate());
        Picasso.with(mContext).load(yorumlar.get(position).getProfileImage()).into(((ImageView) v.findViewById(R.id.yorum_imgProfil)));
        return v;
    }
}