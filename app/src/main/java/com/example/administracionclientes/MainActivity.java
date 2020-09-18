package com.example.administracionclientes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.administracionclientes.Adapters.clientsAdapter;
import com.example.administracionclientes.Clases.Client;
import com.example.administracionclientes.DB.DataBaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    clientsAdapter myAdapter;
    List<Client> lst;
    RecyclerView myrv;
    FloatingActionButton btnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lst = new ArrayList<>();
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddClient.class);
                startActivity(intent);
            }
        });
        Get_All_clients();

    }

    ////////////////////////////////////db request////////////
    public void Get_All_clients() {

        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
        lst = dataBaseHelper.getAll();

        if (!lst.isEmpty()) {
            if (myAdapter != null) {
                myAdapter.notifyDataSetChanged();
            }
            myrv = (RecyclerView) findViewById(R.id.rvlistClient);
            myAdapter = new clientsAdapter(MainActivity.this, lst);
            myrv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            myrv.setAdapter(myAdapter);
        } else {
            Toast.makeText(MainActivity.this, "No hay Datos", Toast.LENGTH_SHORT).show();
        }


    }


    ///////////////////////////////////funciones/////////////////////////////

    public void verDetalles(View v) {
        TextView id = v.findViewById(R.id.tv_id_client);
        int id_cli = Integer.parseInt(id.getText().toString());
        Intent intent = new Intent(MainActivity.this, DetailClient.class);
        intent.putExtra("id", id_cli);
        startActivity(intent);
    }


}
