package com.chest.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.chest.R;
import com.chest.adapter.ListCardAdapter;
import com.chest.constant.Config;
import com.chest.constant.ItemClickListener;
import com.chest.constant.ItemCommonOffset;
import com.chest.database.DatabaseHandler;
import com.chest.model.Card;

import java.util.List;

/**
 * Created by TrungTV on 01/03/2018.
 */

public class ListCardActivity extends AppCompatActivity {

    private DatabaseHandler handler;
    private RecyclerView.LayoutManager mLayoutManager;
    private ItemCommonOffset itemCommonOffset;
    private ListCardAdapter listCardAdapter;
    private List<Card> cardList;

    private RecyclerView rcvListCard;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_card);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        rcvListCard = findViewById(R.id.rcvListCard);

        progressDialog = new ProgressDialog(this, R.style.MyTheme);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.setCancelable(true);

        handler = new DatabaseHandler(this);

        mLayoutManager = new GridLayoutManager(this, 3);
        itemCommonOffset = new ItemCommonOffset(this, R.dimen.item_offset);
        rcvListCard.setHasFixedSize(true);
        rcvListCard.addItemDecoration(itemCommonOffset);
        rcvListCard.setLayoutManager(mLayoutManager);

        new GetListCard().execute();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class GetListCard extends AsyncTask<Void, Void, List<Card>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog != null && !progressDialog.isShowing())
                progressDialog.show();
        }

        @Override
        protected List<Card> doInBackground(Void... voids) {
            cardList = handler.getAllCard();
            return cardList;
        }

        @Override
        protected void onPostExecute(List<Card> cardList) {
            super.onPostExecute(cardList);
            listCardAdapter = new ListCardAdapter(cardList, new ItemClickListener() {

                @Override
                public void onItemClick(int cardId) {
                    Intent intent = new Intent(ListCardActivity.this, CardDetailActivity.class);
                    intent.putExtra(Config.KEY_CARD_ID, cardId);
                    startActivity(intent);
                }
            });
            rcvListCard.setAdapter(listCardAdapter);

            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();
        }
    }
}
