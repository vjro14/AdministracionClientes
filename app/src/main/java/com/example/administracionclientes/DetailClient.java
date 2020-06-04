package com.example.administracionclientes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailClient extends AppCompatActivity {

    String datoRecibido, url, urlDel;
    RequestQueue requestQueue;
    TextView nombre, apellido, dui, nit, direccion, telefono, correo;
    AlertDialog.Builder DialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_client);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Bundle extra = getIntent().getExtras();
        datoRecibido = extra.getString("id");
        url = "https://apiclientes.000webhostapp.com/WebAPI/GetClients.php?id=" + datoRecibido;
        urlDel = "https://apiclientes.000webhostapp.com/WebAPI/deleteClient.php";

        nombre = findViewById(R.id.tv_nombres);
        apellido = findViewById(R.id.tv_apellidos);
        dui = findViewById(R.id.tv_dui);
        nit = findViewById(R.id.tv_nit);
        direccion = findViewById(R.id.tv_direccion);
        telefono = findViewById(R.id.tv_telefono);
        correo = findViewById(R.id.tv_correo);
        Get_Client();
        DialogBuilder = new AlertDialog.Builder(DetailClient.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    /////////////////////////////////volley///////////////////////////////
    public void Get_Client() {

        requestQueue = Volley.newRequestQueue(this);


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject json = response.getJSONObject("cliente");

                    nombre.setText(json.getString("Nombres"));
                    apellido.setText(json.getString("Apellidos"));
                    dui.setText(json.getString("DUI"));
                    nit.setText(json.getString("NIT"));
                    direccion.setText(json.getString("Direccion"));
                    telefono.setText(json.getString("Telefono"));
                    correo.setText(json.getString("Correo"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(request);

    }

    public void delete() {
        requestQueue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, urlDel,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response

                        DialogBuilder.setTitle("Eliminar");
                        DialogBuilder.setMessage("Se ha eliminado con exito!");
                        DialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(DetailClient.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                        DialogBuilder.show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        DialogBuilder.setTitle("Eliminar");
                        DialogBuilder.setMessage("Ha ocurrido un error, por favor intente de nuevo");
                        DialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Hacer cosas aqui al hacer clic en el boton de aceptar
                            }
                        });
                        DialogBuilder.show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", datoRecibido);

                return params;
            }
        };
        requestQueue.add(postRequest);
    }

    ///////////////////////////////////////////////////funciones
    public void deleteClient(View v) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar")
                .setMessage("Seguro que quiere eliminar el cliente?")

                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        delete();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    public void editClient(View v) {
        Intent intento = new Intent(DetailClient.this, EditClient.class);
        intento.putExtra("id", datoRecibido);
        intento.putExtra("nombre", nombre.getText().toString());
        intento.putExtra("apellido", apellido.getText().toString());
        intento.putExtra("dui", dui.getText().toString());
        intento.putExtra("nit", nit.getText().toString());
        intento.putExtra("direccion", direccion.getText().toString());
        intento.putExtra("telefono", telefono.getText().toString());
        intento.putExtra("correo", correo.getText().toString());
        startActivity(intento);
    }
}
