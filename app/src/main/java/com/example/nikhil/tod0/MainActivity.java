package com.example.nikhil.tod0;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    FloatingActionButton fab;
    public static RecyclerView recyclerView;
    public static RecyclerView.Adapter adapter;
    List<TodoListItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //whenever the activity is started, it reads data from database and stores it into
        // local array list 'items'
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,"production")
                .build();

        //it is very bad practice to pull data from Room on main UI thread,
        // that's why we create another thread which we use for getting the data and displaying it
        Runnable r = new Runnable(){
            @Override
            public void run() {
                items = db.databaseInterface().getAllItems();
                recyclerView= (RecyclerView)findViewById(R.id.recyclerview);

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        // Stuff that updates the UI

                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
                        adapter = new UserAdapter(items);
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);


                    }
                });


            }
        };

        Thread newThread= new Thread(r);
        newThread.start();

        fab=(FloatingActionButton)findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,AddItemActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}
