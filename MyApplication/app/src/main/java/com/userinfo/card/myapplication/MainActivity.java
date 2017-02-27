package com.userinfo.card.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private int[] iconId = {
            R.drawable.interest_movie, R.drawable.interest_sport, R.drawable.interest_idol, R.drawable.interest_food,
            R.drawable.interest_book_author, R.drawable.interest_tourist
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
