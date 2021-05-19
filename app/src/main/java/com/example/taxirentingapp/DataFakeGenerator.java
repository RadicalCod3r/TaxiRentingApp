package com.example.taxirentingapp;

import android.content.Context;

import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.List;

public class DataFakeGenerator {
    public List<ProfileItem> generateData(Context context){
        List<ProfileItem> items = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            ProfileItem item = new ProfileItem();
            item.setId(i);
            switch (i){
                case 1:
                    item.setItemName("Add Your Name");
                    item.setItemButtonTxt("Add");
                    item.setItemImage(ResourcesCompat.getDrawable(context.getResources(),R.drawable.full_name_icon,null));
                    item.setItemState(ResourcesCompat.getDrawable(context.getResources(),R.drawable.ic_action_edit,null));
                    break;
                case 2:
                    item.setItemName("Add Your Phone");
                    item.setItemButtonTxt("Add");
                    item.setItemImage(ResourcesCompat.getDrawable(context.getResources(),R.drawable.phone_icon,null));
                    item.setItemState(ResourcesCompat.getDrawable(context.getResources(),R.drawable.ic_action_edit,null));
                    break;
                case 3:
                    item.setItemName("Add Your Email");
                    item.setItemButtonTxt("Change");
                    item.setItemImage(ResourcesCompat.getDrawable(context.getResources(),R.drawable.ic_action_alternate_email,null));
                    item.setItemState(ResourcesCompat.getDrawable(context.getResources(),R.drawable.ic_action_check_circle,null));
                    break;
                case 4:
                    item.setItemName("Add Your Username");
                    item.setItemButtonTxt("Change");
                    item.setItemImage(ResourcesCompat.getDrawable(context.getResources(),R.drawable.username_icon,null));
                    item.setItemState(ResourcesCompat.getDrawable(context.getResources(),R.drawable.ic_action_check_circle,null));
                    break;
            }
            items.add(item);
        }
        return items;
    }
}
