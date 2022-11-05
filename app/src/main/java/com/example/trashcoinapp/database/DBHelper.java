package com.example.trashcoinapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Inventory.db";
    private static final int DATABASE_VERSION = 1;


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + InventoryMaster.Inventory.TABLE_NAME + " ("+
                        InventoryMaster.Inventory.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                        InventoryMaster.Inventory.COLUMN_NAME_ITEM_NAME + " TEXT, "+
                        InventoryMaster.Inventory.COLUMN_NAME_ITEM_DESCRIPTION + " TEXT, "+
                        InventoryMaster.Inventory.COLUMN_NAME_ITEM_ADDED_DATE + " TEXT);";

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ InventoryMaster.Inventory.TABLE_NAME);
        onCreate(db);
    }

    public void addInventory(String itemName, String itemDescription, String addedDate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(InventoryMaster.Inventory.COLUMN_NAME_ITEM_NAME,itemName);
        values.put(InventoryMaster.Inventory.COLUMN_NAME_ITEM_DESCRIPTION, itemDescription);
        values.put(InventoryMaster.Inventory.COLUMN_NAME_ITEM_ADDED_DATE, addedDate);

        long result = db.insert(InventoryMaster.Inventory.TABLE_NAME,null, values);

        if(result == -1){
            Toast.makeText(context, "Data insertion Failed !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Data Successfully Inserted !", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllData(){
        String SQL_READ_ALL = "SELECT * FROM " + InventoryMaster.Inventory.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(SQL_READ_ALL, null);
        }
        return cursor;


    }

    public void updateData(String rowId, String itemName, String itemDescription, String addedDate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(InventoryMaster.Inventory.COLUMN_NAME_ITEM_NAME,itemName);
        values.put(InventoryMaster.Inventory.COLUMN_NAME_ITEM_DESCRIPTION, itemDescription);
        values.put(InventoryMaster.Inventory.COLUMN_NAME_ITEM_ADDED_DATE, addedDate);

        long result = db.update(InventoryMaster.Inventory.TABLE_NAME, values, " _id=? ", new String[]{rowId});
        if(result == -1){
            Toast.makeText(context, "Data Update Failed !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Data Successfully Updated !", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteOneRow(String rowId){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(InventoryMaster.Inventory.TABLE_NAME," _id=? ", new String[]{rowId});

        if(result == -1){
            Toast.makeText(context, "Data Deletion Failed !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Data Successfully Deleted !", Toast.LENGTH_SHORT).show();
        }
    }

}
