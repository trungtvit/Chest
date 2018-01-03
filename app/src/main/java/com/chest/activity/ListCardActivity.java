package com.chest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

import com.chest.R;
import com.chest.adapter.ListCardAdapter;
import com.chest.constant.ItemCommonOffset;
import com.chest.database.DatabaseHandler;
import com.chest.model.Card;

import java.util.List;

public class ListCardActivity extends AppCompatActivity {

    private RecyclerView rcvListCard;
    private DatabaseHandler handler;
    private ListCardAdapter listCardAdapter;
    private List<Card> cardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_card);

        rcvListCard = findViewById(R.id.rcvListCard);

        handler = new DatabaseHandler(this);

        cardList = handler.getAllCard();
        listCardAdapter = new ListCardAdapter(cardList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        ItemCommonOffset itemCommonOffset = new ItemCommonOffset(this,R.dimen.item_offset);
        rcvListCard.setHasFixedSize(true);
        rcvListCard.addItemDecoration(itemCommonOffset);
        rcvListCard.setLayoutManager(mLayoutManager);
        rcvListCard.setAdapter(listCardAdapter);

        rcvListCard.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

    }
}
