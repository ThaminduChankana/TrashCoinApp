package com.example.trashcoinapp.database;

import android.provider.BaseColumns;

public final class InventoryMaster {
    private InventoryMaster(){}

    public static class Inventory implements BaseColumns{
        public static final String TABLE_NAME = "inventory";
        public static final String COLUMN_NAME_ID = "_id";
        public static final String COLUMN_NAME_ITEM_NAME = "itemName";
        public static final String COLUMN_NAME_ITEM_DESCRIPTION = "itemDescription";
        public static final String COLUMN_NAME_ITEM_ADDED_DATE = "addedDate";

    }
}
