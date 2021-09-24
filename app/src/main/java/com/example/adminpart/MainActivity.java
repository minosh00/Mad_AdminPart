package com.example.adminpart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    RecyclerView rew;
    myadapter adapter;
    FloatingActionButton fb;
    private FirebaseAuth mAuth;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Search");

        rew = (RecyclerView) findViewById(R.id.rew);
        rew.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("foods"), model.class)
                        .build();


        adapter = new myadapter(options);

        rew.setAdapter(adapter);

        fb = (FloatingActionButton) findViewById(R.id.fadd);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), adddata.class));
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchmenu, menu);

        MenuItem item = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) item.getActionView();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    private void processsearch(String s) {

        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("foods").orderByChild("name").startAt(s).endAt(s + "\uf8ff"), model.class)
                        .build();

        adapter = new myadapter(options);
        adapter.startListening();
        rew.setAdapter(adapter);
    }






    @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch (id){

            case R.id.idLogOut:
                Toast.makeText(this , "Admin logout  success ", Toast.LENGTH_SHORT).show();
                Intent ik = new Intent(MainActivity.this,AdminLoginActivity.class);
                startActivity(ik);
                this.finish();



                mAuth.signOut();
                Intent i = new Intent(MainActivity.this,AdminLoginActivity.class);
                startActivity(i);
                this.finish();
                return true;
            default:
                return  super.onOptionsItemSelected(item);
        }




    }
}
