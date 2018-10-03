package com.example.android.sampleinventoryapp.data;

import android.provider.BaseColumns;

public final class StoreContract {

    // Empty constructor to prevent someone from accidentally instantiating the contract class.
    private StoreContract() {}

    // Inner class that defines constant values for books database table.
    public static final class BookEntry implements BaseColumns {

        // Name of the database table for books
        public static final String TABLE_NAME = "books";

        // Unique ID number for the product of type INTEGER.
        public static final String _ID = BaseColumns._ID;

        // Name of the book of type TEXT
        public static final String COLUMN_BOOK_NAME = "product_name";

        // Price of the book of type INTEGER
        public static final String COLUMN_BOOK_PRICE = "price";

        // Books quantity of type INTEGER
        public static final String COLUMN_BOOK_QUANTITY = "quantity";

        // Type of book
        public static final String COLUMN_BOOK_TYPE = "type";

        // Name of the book supplier of type TEXT
        public static final String COLUMN_SUPPLIER_NAME = "supplier_name";

        // Supplier phone number of type INTEGER
        public static final String COLUMN_SUPPLIER_PHONE_NUMBER = "supplier_phone_number";

        public static final int BOOK_TYPE_FICTION = 0;

        public static final int BOOK_TYPE_NON_FICTION = 1;

    }
}
