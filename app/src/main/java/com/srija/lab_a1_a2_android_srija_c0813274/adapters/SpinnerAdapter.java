package com.srija.lab_a1_a2_android_srija_c0813274.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.srija.lab_a1_a2_android_srija_c0813274.R;
import com.srija.lab_a1_a2_android_srija_c0813274.models.Provider;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter {
    Context context;
    List<Provider> providerList;
    LayoutInflater inflter;

    public SpinnerAdapter(Context applicationContext, List<Provider> providerList) {
        this.context = applicationContext;
        this.providerList = providerList;
        inflter = (LayoutInflater.from(applicationContext));
    }

    public void updateData(List<Provider> providerList) {
        this.providerList = providerList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return providerList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.spinner_layout, null);
        TextView item = view.findViewById(R.id.spinner_text);
        item.setText(providerList.get(i).getProviderName());
        return view;
    }
}
