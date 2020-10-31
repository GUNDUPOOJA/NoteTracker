package com.example.andriodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    public ArrayList<String> data;
    private ListView listView;
    private Button button;
    private Button button3;
    String s="";
    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        button = findViewById(R.id.button);
        button3 = findViewById(R.id.button3);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MainActivity2.class));


            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data= new ArrayList<>();
                addItem(view);
                rootNode=FirebaseDatabase.getInstance();
                reference=rootNode.getReference("addItem");

                for(int i=0;i<items.size();i++)
                {
                 // adding each item to database
                    s=s+items.get(i);


                    reference.setValue(s);

                }
            }

        });
        // adding each element to arraylist in listview
        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);


    }

    private void setUpListViewListner() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Context context = getApplicationContext();
                // removing each task from to-do list
                // adding a feedback message as a pop-up it an itemis deleted.
                Toast.makeText(context, "Item in list is removed", Toast.LENGTH_LONG).show();
                items.remove(i);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    private void addItem(View view) {
        EditText input = findViewById(R.id.edittext);
        String itemText = input.getText().toString();

        if (!(itemText.equals(""))) {
            // adding each task to to-do list
            itemsAdapter.add(itemText);
            input.setText("");
        } else {
            // adding a feedback message as a alert if user tries to add an empty text
            Toast.makeText(getApplicationContext(), "Please enter text in list", Toast.LENGTH_LONG).show();
        }
    }


}
