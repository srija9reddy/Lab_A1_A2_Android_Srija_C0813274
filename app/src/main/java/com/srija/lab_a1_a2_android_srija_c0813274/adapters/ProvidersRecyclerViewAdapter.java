package com.srija.lab_a1_a2_android_srija_c0813274.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.srija.lab_a1_a2_android_srija_c0813274.DetailsActivity;
import com.srija.lab_a1_a2_android_srija_c0813274.R;
import com.srija.lab_a1_a2_android_srija_c0813274.database.SQLiteHandler;
import com.srija.lab_a1_a2_android_srija_c0813274.models.Product;
import com.srija.lab_a1_a2_android_srija_c0813274.models.Provider;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class ProvidersRecyclerViewAdapter extends RecyclerView.Adapter<ProvidersRecyclerViewAdapter.ViewHolder> implements ItemTouchHelperAdapter {
    SQLiteHandler db;
    private List<Provider> mList;
    private Context mContext;

    public ProvidersRecyclerViewAdapter(Context context, List<Provider> list) {
        this.mList = list;
        this.mContext = context;
        this.db = new SQLiteHandler(mContext);
    }

    public ProvidersRecyclerViewAdapter() {
        // Required empty public constructor
    }

    public void updateList(List<Provider> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mList.toArray().length;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder vh, final int position) {
        vh.getName().setText(mList.get(position).getProviderName());
        vh.getDe().setText(mList.get(position).getProviderEmail());
        vh.getPc().setText(mList.get(position).getCount() + "");
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {

    }

    @Override
    public void onItemDismiss(int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setMessage("Delete " + mList.get(position).getProviderName() + "?");
        dialog.setCancelable(true);
        dialog.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        db.deleteProvider(mList.get(position));
                        mList.remove(position);
                        notifyItemRemoved(position);
                        dialog.cancel();
                    }
                });

        dialog.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
        notifyItemChanged(position);
    }

    @Override
    public void onItemEdit(int position) {
        Provider provider = mList.get(position);
        Intent intent = new Intent(mContext, DetailsActivity.class);
        intent.putExtra("id", provider.getProviderId());
        intent.putExtra("screenType", false);
        intent.putExtra("type", 2);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
        notifyItemChanged(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView name, de, pc;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.list_name);
            de = view.findViewById(R.id.desc_email);
            pc = view.findViewById(R.id.price_count);
            view.setOnClickListener(this);
        }

        public TextView getName() {
            return name;
        }

        public TextView getDe() {
            return de;
        }

        public TextView getPc() {
            return pc;
        }

        @Override
        public void onClick(View v) {
            if (mContext != null) {
                Provider product = mList.get(getAdapterPosition());
                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putExtra("id", product.getProviderId());
                intent.putExtra("screenType",false);
                intent.putExtra("type",0);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }

        }
    }


}

