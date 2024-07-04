package com.sportsprime.sqliteappcontacts;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final String LOG_TAG = "myLogs";
    Button btnAdd, btnRead, btnClear, btnDelete;
    EditText etName, etEmail;
    RecyclerView recyclerView;
    TextView resultText;

    List<Item> items = new ArrayList<>();

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = findViewById(R.id.btnAdd);
        btnRead = findViewById(R.id.btnRead);
        btnClear = findViewById(R.id.btnClear);
        btnDelete = findViewById(R.id.delete);

        resultText = findViewById(R.id.text_result);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(this);
        btnRead.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);

        dbHelper = new DBHelper(this);

    }

    @Override
    public void onClick(View v) {

        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        Item newItem = new Item(null, name, email);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch (v.getId()){

            case(R.id.btnAdd):
                dbHelper.addItem(db, newItem);
                Spanned strAdd = Html.fromHtml("Add new Item with name: <b>"
                        + name + "</b> and email: <b>" + email + "</b>");
                resultText.setText(strAdd);
                items.clear();
                setRecyclerViewAdapter();
                break;

            case (R.id.btnRead):
                items = dbHelper.getAll(db);
                Spanned strGet = Html.fromHtml("<b>All items: </b>");
                resultText.setText(strGet);
                setRecyclerViewAdapter();
                break;

            case(R.id.btnClear):
                dbHelper.clearDB(db);
                Spanned strClean = Html.fromHtml("<b>DB is cleared!</b>");
                resultText.setText(strClean);
                items.clear();
                setRecyclerViewAdapter();
                break;

            case(R.id.delete):
                if(dbHelper.deleteItem(db, name, email)){
                    Spanned strDelete = Html.fromHtml("Delete Item with name: <b>"
                            + name + "</b> and email: <b>" + email + "</b>");
                    resultText.setText(strDelete);
                }else{
                    Spanned strDelete = Html.fromHtml("The item to be deleted was not found!");
                    resultText.setText(strDelete);
                }
                items.clear();
                setRecyclerViewAdapter();
        }

        dbHelper.close();
    }

    private void setRecyclerViewAdapter(){
        MyAdapter adapter = new MyAdapter(this, items);
        recyclerView.setAdapter(adapter);
    }
}