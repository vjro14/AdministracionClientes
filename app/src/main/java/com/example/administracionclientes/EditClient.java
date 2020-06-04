package com.example.administracionclientes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class EditClient extends AppCompatActivity {

    String id, nombre, apellido, dui, nit, direccion, telefono, correo, url;
    EditText etnombre, etapellido, etdui, etnit, etdireccion, ettelefono, etcorreo;
    AlertDialog.Builder DialogBuilder;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_client);
        Bundle extra = getIntent().getExtras();
        url = "https://apiclientes.000webhostapp.com/WebAPI/EditClient.php";
        DialogBuilder = new AlertDialog.Builder(EditClient.this);
        id = extra.getString("id");
        nombre = extra.getString("nombre");
        apellido = extra.getString("apellido");
        dui = extra.getString("dui");
        nit = extra.getString("nit");
        direccion = extra.getString("direccion");
        telefono = extra.getString("telefono");
        correo = extra.getString("correo");

        etnombre = findViewById(R.id.et_nombres);
        etapellido = findViewById(R.id.et_apellidos);
        etdui = findViewById(R.id.et_dui);
        etnit = findViewById(R.id.et_nit);
        etdireccion = findViewById(R.id.et_direccion);
        ettelefono = findViewById(R.id.et_telefono);
        etcorreo = findViewById(R.id.et_correo);

        etnombre.setText(nombre);
        etapellido.setText(apellido);
        etdui.setText(dui);
        etnit.setText(nit);
        etdireccion.setText(direccion);
        ettelefono.setText(telefono);
        etcorreo.setText(correo);
    }

    ///////////////////////////volley////////////////////////////////////////////////////////////
    public void edit() {
        requestQueue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response

                        DialogBuilder.setTitle("Editar");
                        DialogBuilder.setMessage("Se ha editado con exito!");
                        DialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(EditClient.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                        DialogBuilder.show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        DialogBuilder.setTitle("Editar");
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
                params.put("id", id);
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
    public void validate(View v) {
        if (empty(etnombre) || empty(etapellido) || empty(etdui) || empty(etnit) || empty(etdireccion) || empty(ettelefono) || empty(etcorreo)) {
            Toast.makeText(EditClient.this, "Complete todos los campos", Toast.LENGTH_LONG).show();
        } else {
            if (isEmailValid(etcorreo.getText())) {
                edit();
            } else {
                Toast.makeText(EditClient.this, "Correo Invalido", Toast.LENGTH_LONG).show();
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
