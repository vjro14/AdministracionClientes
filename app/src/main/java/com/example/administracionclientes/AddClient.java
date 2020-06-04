package com.example.administracionclientes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AddClient extends AppCompatActivity {

    EditText etnombre, etapellido, etdui, etnit, etdireccion, ettelefono, etcorreo;
    AlertDialog.Builder DialogBuilder;
    RequestQueue requestQueue;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        url = "https://apiclientes.000webhostapp.com/WebAPI/AddClient.php";
        DialogBuilder = new AlertDialog.Builder(AddClient.this);

        etnombre = findViewById(R.id.et_nombresAdd);
        etapellido = findViewById(R.id.et_apellidosAdd);
        etdui = findViewById(R.id.et_duiAdd);
        etnit = findViewById(R.id.et_nitAdd);
        etdireccion = findViewById(R.id.et_direccionAdd);
        ettelefono = findViewById(R.id.et_telefonoAdd);
        etcorreo = findViewById(R.id.et_correoAdd);
    }

    ///////////////////////////volley////////////////////////////////////////////////////////////
    public void add() {
        requestQueue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response

                        DialogBuilder.setTitle("Agregar");
                        DialogBuilder.setMessage("Se ha agregado con exito!");
                        DialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(AddClient.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                        DialogBuilder.show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        DialogBuilder.setTitle("Agregar");
                        DialogBuilder.setMessage("Ha ocurrido un error, por favor intente de nuevo");
                        DialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        DialogBuilder.show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nombre", etnombre.getText().toString());
                params.put("apellido", etapellido.getText().toString());
                params.put("dui", etdui.getText().toString());
                params.put("nit", etnit.getText().toString());
                params.put("direccion", etdireccion.getText().toString());
                params.put("telefono", ettelefono.getText().toString());
                params.put("correo", etcorreo.getText().toString());

                return params;
            }
        };
        requestQueue.add(postRequest);
    }

    /////////////////////////////////////funciones////////////////////////////////////////////////
    public void validateCli(View v) {
        if (empty(etnombre) || empty(etapellido) || empty(etdui) || empty(etnit) || empty(etdireccion) || empty(ettelefono) || empty(etcorreo)) {
            Toast.makeText(AddClient.this, "Complete todos los campos", Toast.LENGTH_LONG).show();
        } else {
            if (isEmailValid(etcorreo.getText())) {
                add();
            } else {
                Toast.makeText(AddClient.this, "Correo Invalido", Toast.LENGTH_LONG).show();
            }
        }
    }

    public boolean empty(EditText e) {
        if (e.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
