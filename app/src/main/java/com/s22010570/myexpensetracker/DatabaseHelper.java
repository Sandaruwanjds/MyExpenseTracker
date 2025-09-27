package com.s22010570.myexpensetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Define Database
    public static final String DATABASE_NAME = "expense.db"; // Database name
    public static final String TABLE_NAME = "expense_table"; // Table name
    public static final String COL_1 = "ID"; // Column name for ID
    public static final String COL_2 = "date"; // Column name for date
    public static final String COL_3 = "Expense"; // Column name for expense
    public static final String COL_4 = "Amount"; // Column name for amount

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase(); // Create or open the database for writing
    }

    // Method  database is created
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the expense table with columns ID, date, expense, and amount
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " + "DATE TEXT, EXPENSE TEXT, AMOUNT INTEGER)");
    }

    // Method  database needs to be upgraded
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create new table
        onCreate(db);
    }

    // Method to insert data into the table
    public boolean insertData(String date, String Expense, String Amount) {
        SQLiteDatabase db = this.getWritableDatabase(); // Open the database for writing
        ContentValues contentValues = new ContentValues(); // Create ContentValues to store the data
        contentValues.put(COL_2, date);
        contentValues.put(COL_3, Expense);
        contentValues.put(COL_4, Amount);
        long results = db.insert(TABLE_NAME, null, contentValues);
        // Return true if data is inserted successfully, false otherwise
        return results != -1;
    }

    // Method to retrieve all data from the table
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase(); // Open the database for writing
        Cursor results = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return results;
    }

    // Method to update data in the table
    public boolean updateData(String id, String date, String Expense, String Amount) {
        SQLiteDatabase db = this.getWritableDatabase(); // Open the database for writing
        ContentValues contentValues = new ContentValues(); // Create ContentValues to store the updated data
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, date);
        contentValues.put(COL_3, Expense);
        contentValues.put(COL_4, Amount);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        return true;
    }

    // Method to delete data from the table
    public Integer btndelete(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }
}
