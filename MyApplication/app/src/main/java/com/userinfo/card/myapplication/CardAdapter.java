package com.userinfo.card.myapplication;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.userinfo.card.myapplication.our.CircleImageView;
import com.userinfo.card.myapplication.our.PixelUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jameson on 8/30/16.
 */
class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private int bigWidth = PixelUtil.dpToPx(56), smallWidth = PixelUtil.dpToPx(39), midWidth = PixelUtil.dpToPx(44);
    private int[] mList ;

    public CardAdapter(int[] mList) {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mImageView.setImageResource(mList[position]);
    }

    @Override
    public int getItemCount() {
        Log.e("haha",mList.length+"");
        return mList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final CircleImageView mImageView;

        public ViewHolder(final View itemView) {
            super(itemView);
            mImageView = (CircleImageView) itemView.findViewById(R.id.civ_img);
        }

    }

}
