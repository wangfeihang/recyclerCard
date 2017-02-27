package com.userinfo.card.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import com.userinfo.card.myapplication.our.PixelUtil;
import com.userinfo.card.myapplication.our.RecycleViewDivider;
import com.userinfo.card.myapplication.our.SpeedRecyclerView;

public class MainActivity extends AppCompatActivity {

    CardAdapter adapter;
    SpeedRecyclerView recyclerView;

    private int[] iconId = {
            R.drawable.portrait_default, R.drawable.portrait_default, R.drawable.portrait_default,
            R.drawable.portrait_default, R.drawable.portrait_default, R.drawable.portrait_default,
            R.drawable.portrait_default, R.drawable.portrait_default, R.drawable.portrait_default,
            R.drawable.portrait_default, R.drawable.portrait_default, R.drawable.portrait_default,
            R.drawable.portrait_default, R.drawable.portrait_default, R.drawable.portrait_default,
            R.drawable.portrait_default, R.drawable.portrait_default, R.drawable.portrait_default,
            R.drawable.portrait_default, R.drawable.portrait_default, R.drawable.portrait_default,
            R.drawable.portrait_default, R.drawable.portrait_default, R.drawable.portrait_default,
            R.drawable.portrait_default, R.drawable.portrait_default, R.drawable.portrait_default,
            R.drawable.portrait_default, R.drawable.portrait_default, R.drawable.portrait_default,
            R.drawable.portrait_default, R.drawable.portrait_default, R.drawable.portrait_default,
            R.drawable.portrait_default, R.drawable.portrait_default, R.drawable.portrait_default,
            R.drawable.portrait_default, R.drawable.portrait_default, R.drawable.portrait_default,
            R.drawable.portrait_default, R.drawable.portrait_default, R.drawable.portrait_default,
            R.drawable.portrait_default, R.drawable.portrait_default, R.drawable.portrait_default,
            R.drawable.portrait_default, R.drawable.portrait_default, R.drawable.portrait_default,
            R.drawable.portrait_default, R.drawable.portrait_default, R.drawable.portrait_default,
            R.drawable.portrait_default, R.drawable.portrait_default, R.drawable.portrait_default,
            R.drawable.portrait_default, R.drawable.portrait_default, R.drawable.portrait_default,
            R.drawable.portrait_default, R.drawable.portrait_default, R.drawable.portrait_default,
            R.drawable.portrait_default, R.drawable.portrait_default, R.drawable.portrait_default,
            R.drawable.portrait_default, R.drawable.portrait_default, R.drawable.portrait_default,
            R.drawable.portrait_default, R.drawable.portrait_default, R.drawable.portrait_default,
            R.drawable.portrait_default, R.drawable.portrait_default, R.drawable.portrait_default,
            R.drawable.portrait_default, R.drawable.portrait_default, R.drawable.portrait_default,
            R.drawable.portrait_default, R.drawable.portrait_default, R.drawable.portrait_default,
            R.drawable.portrait_default, R.drawable.portrait_default, R.drawable.portrait_default,
            R.drawable.portrait_default, R.drawable.portrait_default, R.drawable.portrait_default,
            R.drawable.portrait_default, R.drawable.portrait_default, R.drawable.portrait_default,
            R.drawable.portrait_default, R.drawable.portrait_default, R.drawable.portrait_default,
            R.drawable.portrait_default, R.drawable.portrait_default, R.drawable.portrait_default
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (SpeedRecyclerView) findViewById(R.id.rv);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        LinearSnapHelper mLinearSnapHelper = new LinearSnapHelper();
        mLinearSnapHelper.attachToRecyclerView(recyclerView);
        //recyclerView.addItemDecoration(new RecycleViewDivider(
        //        this, LinearLayoutManager.VERTICAL, PixelUtil.dpToPx(22), getResources().getColor(R.color.divide_gray_color)));
        adapter = new CardAdapter(iconId);
        recyclerView.setAdapter(adapter);


    }
}
