package com.example.taxirentingapp;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProfileRecyclerAdapter extends RecyclerView.Adapter<ProfileRecyclerAdapter.ProfileRecyclerViewHolder> {
    private Context context;
    private List<ProfileItem> items;
    public ProfileRecyclerAdapter(Context context ,List<ProfileItem> items){
        this.context = context;
        this.items = items;
    }
    @NonNull
    @Override
    public ProfileRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProfileRecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_profile_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileRecyclerViewHolder holder, int position) {
        ProfileItem item = items.get(position);
        holder.itemImage.setImageDrawable(item.getItemImage());
        holder.itemState.setImageDrawable(item.getItemState());
        holder.itemName.setText(item.getItemName());
        holder.itemButton.setText(item.getItemButtonTxt());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ProfileRecyclerViewHolder extends RecyclerView.ViewHolder{
        private ImageView itemImage ,itemState;
        private TextView itemName;
        private Button itemButton;
        public ProfileRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = (ImageView)itemView.findViewById(R.id.item_image);
            itemName = (TextView)itemView.findViewById(R.id.item_name);
            itemButton = (Button)itemView.findViewById(R.id.item_button);
            itemState = (ImageView)itemView.findViewById(R.id.item_state);
        }
    }
}
