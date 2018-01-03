package com.chest.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chest.R;
import com.chest.constant.Config;
import com.chest.customviews.CustomAnimationDrawable;
import com.chest.customviews.ScaleInAnimation;
import com.chest.database.DatabaseHandler;
import com.chest.model.Card;
import com.chest.utils.SharePref;

import java.util.Random;

/**
 * Created by TrungTV on 01/03/2018.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgChest;
    private ImageView imgCard;
    private TextView tvStore;

    private CustomAnimationDrawable animationDrawable;
    private DatabaseHandler handler;
    private SharePref sharePref;

    private int cardNumber;
    private long timeClickChest;
    private long timeOpenApp;
    private long timeToSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeOpenApp = System.currentTimeMillis();

        handler = new DatabaseHandler(this);
        sharePref = new SharePref(this);

        imgChest = findViewById(R.id.imgChest);
        imgCard = findViewById(R.id.imgCard);
        tvStore = findViewById(R.id.tvStore);

        imgChest.setBackgroundResource(R.drawable.chest_1);

        timeClickChest = sharePref.getTimeClickChest();
        saveCardToDatabase();

        imgChest.setOnClickListener(this);
        tvStore.setOnClickListener(this);

    }

    private int randomCard() {
        int min = 0;
        int max = Config.MAX_CARD - 1;
        Random r = new Random();
        int i = r.nextInt(max - min + 1) + min;
        cardNumber = i;
        sharePref.saveCardNumber(cardNumber);
        return i;
    }

    private void saveCardToDatabase() {
        timeToSave = timeOpenApp - timeClickChest;
        long time24Hours = timeToSave / (60 * 60 * 1000);
        Log.e("OKMEN", time24Hours + "-------");
        if (time24Hours >= Config.DURATION_TO_SAVE_DB) {
            int cardNumber = sharePref.getCardNumber();
            Card card = new Card(cardNumber);
            handler.addCard(card);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgChest:
                sharePref.saveTimeClickChest(System.currentTimeMillis());
                animationDrawable = new CustomAnimationDrawable((AnimationDrawable) getResources().getDrawable(
                        R.drawable.chest_animtion)) {
                    @Override
                    public void onAnimationFinish() {
                        animationDrawable.stop();
                        imgChest.setOnClickListener(null);
                        imgCard.setBackgroundResource(Config.listCard[randomCard()]);
                        new ScaleInAnimation(imgCard).setDuration(500).animate();
                    }

                    @Override
                    public void onAnimationStart() {

                    }
                };
                v.setBackgroundDrawable(animationDrawable);
                animationDrawable.start();
                break;
            case R.id.imgCard:
                Intent intent = new Intent(MainActivity.this, ListCardActivity.class);
                startActivity(intent);
                break;
            default:
                    /*TODO*/
                break;
        }
    }
}
