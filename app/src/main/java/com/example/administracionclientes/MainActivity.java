package com.example.administracionclientes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administracionclientes.Adapters.clientsAdapter;
import com.example.administracionclientes.Clases.Client;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    clientsAdapter myAdapter;
    private String url, urlDel;
    List<Client> lst;
    RecyclerView myrv;
    FloatingActionButton btnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lst = new ArrayList<>();
        url = "https://apiclientes.000webhostapp.com/WebAPI/GetClients.php";
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

    ////////////////////////////////////volley request////////////
    public void Get_All_clients() {

        requestQueue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray json = response.getJSONArray("clientes");
                    lst.clear();
                    for (int i = 0; i < json.length(); i++) {

                        JSONObject jsonObject = json.getJSONObject(i);

                        lst.add(new Client(Integer.parseInt(jsonObject.getString("id_cliente")), jsonObject.getString("Nombres"), jsonObject.getString("Apellidos"),
                                jsonObject.getString("DUI"), jsonObject.getString("NIT"), jsonObject.getString("Direccion"), jsonObject.getString("Telefono"),
                                jsonObject.getString("Correo")));
                    }
                    if (myAdapter != null) {
                        myAdapter.notifyDataSetChanged();
                    }
                    myrv = (RecyclerView) findViewById(R.id.rvlistClient);
                    myAdapter = new clientsAdapter(MainActivity.this, lst);
                    myrv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    myrv.setAdapter(myAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "No hay datos", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(request);

    }


    ///////////////////////////////////funciones/////////////////////////////

    public void verDetalles(View v) {
        TextView id = v.findViewById(R.id.tv_id_client);
        Intent intent = new Intent(MainActivity.this, DetailClient.class);
        intent.putExtra("id", id.getText().toString());
        startActivity(intent);
    }


}
