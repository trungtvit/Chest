package com.chest.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.chest.R;
import com.chest.constant.Config;
import com.chest.database.DatabaseHandler;
import com.chest.model.Card;

public class CardDetailActivity extends AppCompatActivity {

    private ImageView imgCard;
    private ProgressDialog progressDialog;

    private DatabaseHandler handler;
    private Card card;

    private int cardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_detail);
        imgCard = findViewById(R.id.imgCard);

        progressDialog = new ProgressDialog(this, R.style.MyTheme);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.setCancelable(true);

        handler = new DatabaseHandler(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            cardId = bundle.getInt(Config.KEY_CARD_ID);
            new GetCardDetail().execute(cardId);
        }
    }

    private class GetCardDetail extends AsyncTask<Integer, Void, Card> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog != null && !progressDialog.isShowing())
                progressDialog.show();
        }

        @Override
        protected Card doInBackground(Integer... cardId) {
            card = handler.getCardById(cardId[0]);
            return card;
        }

        @Override
        protected void onPostExecute(Card card) {
            super.onPostExecute(card);
            imgCard.setImageResource(Config.listCard[card.getCardNumber()]);

            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();
        }
    }
}
