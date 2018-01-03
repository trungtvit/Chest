package com.chest.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chest.R;
import com.chest.constant.Config;
import com.chest.model.Card;

import java.util.List;

/**
 * Created by TrungTV on 01/03/2018.
 */

public class ListCardAdapter extends RecyclerView.Adapter<ListCardAdapter.ViewHolder> {
    List<Card> cardList;

    public ListCardAdapter(List<Card> cardList) {
        this.cardList = cardList;
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
        Card card = cardList.get(position);
        holder.imgCard.setImageResource(Config.listCard[card.getCardNumber()]);
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }
}
