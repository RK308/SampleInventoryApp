package com.example.android.sampleinventoryapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.sampleinventoryapp.data.BookDbHelper;
import com.example.android.sampleinventoryapp.data.StoreContract.BookEntry;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getName();

    /** Database helper that will provide us access to the database */
    private BookDbHelper mDbHelper;

    private String bookName, supplierName, supplierPhoneNumber;
    private int price, quantity, bookType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create database helper
        mDbHelper = new BookDbHelper(this);

        // Dummy values
        bookName = "Life of Pi";
        price = 30;
        quantity = 2;
        supplierName = "Baker & Taylor";
        supplierPhoneNumber = "310 223 6985";
        bookType = BookEntry.BOOK_TYPE_FICTION;

    }

    private void displayInfo() {

        Log.e(LOG_TAG, "Display Info method is called");

        // Get the database in a read mode.
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                BookEntry._ID,
                BookEntry.COLUMN_BOOK_NAME,
                BookEntry.COLUMN_BOOK_TYPE,
                BookEntry.COLUMN_SUPPLIER_NAME,
                BookEntry.COLUMN_SUPPLIER_PHONE_NUMBER};

        Cursor cursor = db.query(BookEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        StringBuilder displayText = new StringBuilder();

        try {
            displayText.append("The Books table contains " + cursor.getCount() + "books.\n\n");
            displayText.append(BookEntry._ID + " - " +
                    BookEntry.COLUMN_BOOK_NAME + " - " +
                    BookEntry.COLUMN_BOOK_TYPE + " - " +
                    BookEntry.COLUMN_SUPPLIER_NAME + " - " +
                    BookEntry.COLUMN_SUPPLIER_PHONE_NUMBER + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(BookEntry._ID);
            int bookNameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_NAME);
            int typeColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_TYPE);
            int supplierNameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_SUPPLIER_NAME);
            int supplierPhoneColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_SUPPLIER_PHONE_NUMBER);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                //  int currentID = cursor.getInt(cursor.getColumnIndex(BookEntry._ID));
                int currentID = cursor.getInt(idColumnIndex);
                String currentBookName = cursor.getString(bookNameColumnIndex);
                int currentBookType = cursor.getInt(typeColumnIndex);
                String currentSupplierName = cursor.getString(supplierNameColumnIndex);
                String currentSupplierPhone = cursor.getString(supplierPhoneColumnIndex);

                displayText.append("\n" + currentID + " - " +
                        currentBookName + " - " +
                        currentBookType + " - " +
                        currentSupplierName + " - " +
                        currentSupplierPhone);
            }
        } finally {
            // closing the cursor when done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }

        Toast.makeText(this, displayText.toString(),Toast.LENGTH_LONG).show();
    }

    private void insert() {

        Log.e(LOG_TAG, "Insert method is called");

        // Get the database in write mode.
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create the contentValues object where column names are the keys
        // and the book attributes are the values.
        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_BOOK_NAME, bookName);
        values.put(BookEntry.COLUMN_BOOK_PRICE, price);
        values.put(BookEntry.COLUMN_BOOK_QUANTITY, quantity);
        values.put(BookEntry.COLUMN_BOOK_TYPE, bookType);
        values.put(BookEntry.COLUMN_SUPPLIER_NAME, supplierName);
        values.put(BookEntry.COLUMN_SUPPLIER_PHONE_NUMBER, supplierPhoneNumber);

        // Insert a new row into the database.
        long newRowId = db.insert(BookEntry.TABLE_NAME, null, values);

        if (newRowId == -1) {
            Toast.makeText(this,"Error while inserting the data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Added a book with a row id : " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/main_menu.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_insert :
                insert();
                return true;
            case R.id.action_read :
                displayInfo();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
