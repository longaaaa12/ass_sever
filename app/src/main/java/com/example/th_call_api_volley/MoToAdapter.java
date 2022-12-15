package com.example.th_call_api_volley;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;

import java.util.List;

public class MoToAdapter extends BaseAdapter {
    private List<MoTo> motoList;
    private Context context;

    public MoToAdapter(List<MoTo> motoList, Context context) {
        this.motoList = motoList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return motoList.size();
    }

    @Override
    public Object getItem(int position) {
        return motoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_moto, parent, false);
        ImageView img = view.findViewById(R.id.img_moto);
        TextView txt_creatAt = view.findViewById(R.id.txt_creatAT);
        TextView txt_name_manga = view.findViewById(R.id.txt_name);
        TextView txt_price = view.findViewById(R.id.txt_price);
        TextView txt_color = view.findViewById(R.id.txt_color);

        txt_creatAt.setText(motoList.get(position).getCreatedAt());
        txt_name_manga.setText(motoList.get(position).getName());
        txt_price.setText(motoList.get(position).getPrice()+"");
        txt_color.setText(motoList.get(position).getColor());
        Glide.with(context)
                .load(motoList.get(position).getImage())
                .into(img);
        ImageView imgDelete = view.findViewById(R.id.img_delete);
        //--------------
        MoTo mo = motoList.get(position);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PutMoToActivity.class);
                intent.putExtra("data", mo);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        //--------------
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                builder.setTitle("Delete item");
                builder.setMessage("Do you want item this delete?");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
        return view;
    }
}
