package com.chest.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chest.R;
import com.chest.constant.Config;
import com.chest.constant.ItemClickListener;
import com.chest.model.Card;

import java.util.List;

/**
 * Created by TrungTV on 01/03/2018.
 */

public class ListCardAdapter extends RecyclerView.Adapter<ListCardAdapter.ViewHolder> {

    private List<Card> cardList;
    private ItemClickListener itemClickListener;

    public ListCardAdapter(List<Card> cardList, ItemClickListener itemClickListener) {
        this.cardList = cardList;
        this.itemClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgCard;

        public ViewHolder(View itemView) {
            super(itemView);
            imgCard = itemView.findViewById(R.id.imgCard);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_card, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Card card = cardList.get(position);
        holder.imgCard.setImageResource(Config.listCard[card.getCardNumber()]);

        holder.imgCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(card.getCardId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }
}
