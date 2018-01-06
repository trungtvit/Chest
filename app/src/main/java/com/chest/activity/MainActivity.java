package com.chest.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chest.R;
import com.chest.constant.BackupAndRestoreDB;
import com.chest.constant.Config;
import com.chest.customviews.CustomAnimationDrawable;
import com.chest.customviews.ScaleInAnimation;
import com.chest.customviews.ScaleOutAnimation;
import com.chest.database.DatabaseHandler;
import com.chest.model.Card;
import com.chest.utils.ConnectionUtil;
import com.chest.utils.SharePref;

import java.util.Random;

/**
 * Created by TrungTV on 01/03/2018.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgChest;
    private ImageView imgCard;
    private TextView tvStore;
    private AlertDialog.Builder dialogConnection;

    private CustomAnimationDrawable animationDrawable;
    private BackupAndRestoreDB backupAndRestoreDB;
    private DatabaseHandler handler;
    private SharePref sharePref;

    private long timeClickChest;
    private int cardNumber;
    private boolean isChestClicked = false;
    private boolean isCardClicked = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkNetwork();

        backupAndRestoreDB = new BackupAndRestoreDB(this);
        handler = new DatabaseHandler(this);
        sharePref = new SharePref(this);

        backupAndRestoreDB.restoreDb();

        imgChest = findViewById(R.id.imgChest);
        imgCard = findViewById(R.id.imgCard);
        tvStore = findViewById(R.id.tvStore);

        imgCard.setVisibility(View.GONE);

        imgChest.setBackgroundResource(R.drawable.chest_1);

        timeClickChest = sharePref.getTimeClickChest();

        imgChest.setOnClickListener(this);
        tvStore.setOnClickListener(this);
        imgCard.setOnClickListener(this);

    }

    private int randomCard() {
        int min = 0;
        int max = Config.MAX_CARD - 1;
        Random r = new Random();
        int i = r.nextInt(max - min + 1) + min;
        return i;
    }

    private void saveCardToDatabase(int cardNumber) {
        String cardName = Config.listCardName[cardNumber];
        int cardIndex = Config.listCardIndex[cardNumber];
        Card card = new Card(cardNumber, cardName, cardIndex);
        handler.addCard(card);
        backupAndRestoreDB.exportDb();
    }

    private void checkNetwork() {
        if (!ConnectionUtil.isOnline(this)) {
            dialogConnection = new AlertDialog.Builder(this);
            dialogConnection.setTitle("Network Error");
            dialogConnection.setMessage("Check the network and try again");
            dialogConnection.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    checkNetwork();
                }
            });

            dialogConnection.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(0);
                }
            });
            dialogConnection.show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgChest:

                if (ConnectionUtil.isOnline(this)) {
                    if (!isChestClicked) {
                        isChestClicked = true;
                        long timeCurrent = System.currentTimeMillis();
                        long durationOpenChest = timeCurrent - timeClickChest;
                        long timeToOpenChest = durationOpenChest / (60 * 60 * 1000);
                        long minuteLeft = 60 - ((durationOpenChest / (60 * 1000)));
                        if (timeToOpenChest >= Config.DURATION_TO_OPEN_CHEST || timeClickChest == 0) {
                            imgChest.setBackgroundDrawable(null);
                            animationDrawable = new CustomAnimationDrawable((AnimationDrawable) getResources().getDrawable(
                                    R.drawable.chest_animtion_open)) {
                                @Override
                                public void onAnimationFinish() {
                                    imgCard.setVisibility(View.VISIBLE);
                                    animationDrawable.stop();
                                    cardNumber = randomCard();
                                    imgCard.setBackgroundResource(Config.listCard[cardNumber]);
                                    new ScaleInAnimation(imgCard).setDuration(500).animate();
                                    timeClickChest = System.currentTimeMillis();
                                    sharePref.saveTimeClickChest(timeClickChest);
                                    saveCardToDatabase(cardNumber);
                                }

                                @Override
                                public void onAnimationStart() {

                                }
                            };
                            v.setBackgroundDrawable(animationDrawable);
                            animationDrawable.start();
                        } else {
                            Toast.makeText(this, minuteLeft + " minutes remaining", Toast.LENGTH_SHORT).show();
                            isChestClicked = false;
                        }
                    }
                } else {
                    Toast.makeText(this, "Please check the network !", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tvStore:
                if (ConnectionUtil.isOnline(this)) {
                    Intent intent = new Intent(MainActivity.this, ListCardActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Please check the network !", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.imgCard:
                if (!isCardClicked) {
                    isCardClicked = true;
                    imgCard.setBackgroundResource(Config.listCard[cardNumber]);
                    new ScaleOutAnimation(imgCard).setDuration(500).animate();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imgChest.setBackgroundResource(R.drawable.chest_1);
                            Toast.makeText(MainActivity.this, "The card has been added to the store", Toast.LENGTH_SHORT).show();
                            isChestClicked = false;
                            isCardClicked = false;
                        }
                    }, 500);
                }

                break;
            default:
                    /*TODO*/
                break;
        }
    }

}
