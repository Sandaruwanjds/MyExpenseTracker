package com.s22010570.myexpensetracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.text.TextUtils;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editTextDate, editTextExpense, editTextAmount, editTextUpdate;
    Button btnAddData;
    Button btnViewData, eUpdate,btnDelete;
    boolean isViewingData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Create new database
        myDb = new DatabaseHelper(this);
        // Initialize EditText fields and buttons from layout
        editTextDate = findViewById(R.id.date);
        editTextExpense = findViewById(R.id.Expense);
        editTextAmount = findViewById(R.id.Amount);
        editTextUpdate = findViewById(R.id.update);
        btnAddData = findViewById(R.id.addButton);
        btnViewData = findViewById(R.id.btnViewData);
        eUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        insertData();
        viewAll();
        updateData();
        deleteData();
    }

    // Method to insert data into the database
    public void insertData(){
        btnAddData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Check if any of the fields are empty
                if (TextUtils.isEmpty(editTextDate.getText())
                        || TextUtils.isEmpty(editTextExpense.getText())
                        || TextUtils.isEmpty(editTextAmount.getText())) {
                    // Show a message if any field is empty
                    Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_LONG).show();
                    return; // Exit the method
                }

                // Proceed with data insertion if all fields are filled
                boolean isDataInserted = myDb.insertData(
                        editTextDate.getText().toString(),
                        editTextExpense.getText().toString(),
                        editTextAmount.getText().toString());

                if (isDataInserted) {
                    Toast.makeText(MainActivity.this, "Data Inserted Successfully", Toast.LENGTH_LONG).show();
                    // Clear the insert fields after successful insertion
                    editTextDate.setText("");
                    editTextExpense.setText("");
                    editTextAmount.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Data Not Inserted ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    // Method to view all data from the database
    public void viewAll() {
        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor results = myDb.getAllData();
                if (results.getCount() == 0) {
                    showMessage("Error Message:", "No Data Available in the table");
                } else {
                    StringBuffer buffer = new StringBuffer();
                    while (results.moveToNext()) {
                        buffer.append("ID :" + results.getString(0) + "\n");
                        buffer.append("Current Date :" + results.getString(1) + "\n");
                        buffer.append("Expense Type :" + results.getString(2) + "\n");
                        buffer.append("Amount Rs:" + results.getString(3) + "\n\n");
                    }
                    showMessage("List Of Data :", buffer.toString());
                    // Set flag to true since data is being viewed
                    isViewingData = true;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (isViewingData) {
            // If data is being viewed, set flag to false and return
            isViewingData = false;
        } else {
            // If not viewing data, proceed with default back button behavior
            super.onBackPressed();
        }
    }

    //Alert Dialog Box
    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    // Method to update data in the database
    public void updateData(){
        eUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdate = myDb.updateData(editTextUpdate.getText().toString(), editTextDate.getText().toString(),
                        editTextExpense.getText().toString(), editTextAmount.getText().toString());

                // Clear the EditText fields
                if (isUpdate) {
                    Toast.makeText(MainActivity.this,"Data Updated",Toast.LENGTH_LONG).show();
                    clearEditTextFields();
                } else {
                    Toast.makeText(MainActivity.this,"Data Not Updated",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // Method to delete data in the database
    public void deleteData(){
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idToDelete = editTextUpdate.getText().toString();
                if (TextUtils.isEmpty(idToDelete)) {
                    Toast.makeText(MainActivity.this, "Please enter an ID to delete", Toast.LENGTH_LONG).show();
                    return;
                }
                // Clear the EditText fields
                Integer deletedatarows = myDb.btndelete(idToDelete);
                if (deletedatarows > 0) {
                    Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                    clearEditTextFields();
                } else {
                    Toast.makeText(MainActivity.this, "Data Not Deleted", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // Method to clear EditText fields
    private void clearEditTextFields() {
        editTextUpdate.getText().clear();
    }

}