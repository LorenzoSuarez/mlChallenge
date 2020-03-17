package com.ls.mlchallenge.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ls.mlchallenge.R;
import com.ls.mlchallenge.model.CreditCard;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CreditCardsAdapter extends RecyclerView.Adapter<CreditCardsAdapter.CustomViewHolder> implements View.OnClickListener{
    private ArrayList<CreditCard> items;
    private View.OnClickListener listener;

    public CreditCardsAdapter(ArrayList<CreditCard> items) {
        this.items = items;
    }

    public ArrayList<CreditCard> getItems() {
        return items;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;

        CustomViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.card_image);
            name = itemView.findViewById(R.id.name);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_bank, parent, false);

        itemView.setOnClickListener(this);

        return new CustomViewHolder(itemView);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(final CreditCardsAdapter.CustomViewHolder holder, final int position) {
        final CreditCard item = items.get(position);
        holder.name.setText(item.name);
        Picasso.get().load(item.secure_thumbnail).placeholder(R.drawable.ic_credit_card).into(holder.image);
    }

    @Override
    public int getItemCount() {
        if (items != null)
            return items.size();
        return 0;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

}
