package com.chest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.chest.model.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TrungTV on 01/03/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "cardManager";

    // Card Table name
    private static final String TABLE_CARD = "card";

    // Card Table Columns names
    private static final String KEY_CARD_ID = "id";
    private static final String KEY_CARD_NUMBER = "card_number";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*Creating Tables*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CARD_TABLE = "CREATE TABLE " + TABLE_CARD + "("
                + KEY_CARD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_CARD_NUMBER + " INTEGER" + ")";
        db.execSQL(CREATE_CARD_TABLE);
    }

    /*Upgrading database*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARD);

        onCreate(db);
    }

    /*Adding new card*/
    public void addCard(Card card) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CARD_NUMBER, card.getCardNumber());

        db.insert(TABLE_CARD, null, values);
        db.close();
    }

    /*Getting single card by card id*/
    public Card getCardById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CARD, new String[]{KEY_CARD_ID,
                        KEY_CARD_NUMBER}, KEY_CARD_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Card card = new Card(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)));
        return card;
    }

    /* Getting All Card*/
    public List<Card> getAllCard() {
        List<Card> cardList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CARD;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Card card = new Card();
                card.setCardId(Integer.parseInt(cursor.getString(0)));
                card.setCardNumber(Integer.parseInt(cursor.getString(1)));
                cardList.add(card);
            } while (cursor.moveToNext());
        }

        return cardList;
    }

    // Deleting single card
    public void deleteCard(Card card) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CARD, KEY_CARD_ID + " = ?",
                new String[]{String.valueOf(card.getCardId())});
        db.close();
    }
}
