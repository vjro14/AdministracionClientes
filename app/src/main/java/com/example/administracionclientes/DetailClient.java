package com.example.administracionclientes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.administracionclientes.Clases.Client;
import com.example.administracionclientes.DB.DataBaseHelper;

public class DetailClient extends AppCompatActivity {
    int id;
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
        if (extra != null) {
            id = extra.getInt("id");
        }

        nombre = findViewById(R.id.tv_nombres);
        apellido = findViewById(R.id.tv_apellidos);
        dui = findViewById(R.id.tv_dui);
        nit = findViewById(R.id.tv_nit);
        direccion = findViewById(R.id.tv_direccion);
        telefono = findViewById(R.id.tv_telefono);
        correo = findViewById(R.id.tv_correo);

        getClient();

        DialogBuilder = new AlertDialog.Builder(DetailClient.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    /////////////////////////////////db///////////////////////////////

    public void getClient() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(DetailClient.this);
        Client data = dataBaseHelper.searchCli(id);
        if (data != null) {
            nombre.setText(data.getNombres());
            apellido.setText(data.getApellidos());
            dui.setText(data.getDui());
            nit.setText(data.getNit());
            direccion.setText(data.getDireccion());
            telefono.setText(data.getTelefono());
            correo.setText(data.getCorreo());
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Ocurrio un error, intente de nuevo")

                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(DetailClient.this, MainActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    public void delete() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(DetailClient.this);
        if (!dataBaseHelper.deleteClient(id)) {
            Intent intent = new Intent(DetailClient.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(DetailClient.this, "Ocurrio un error, intente de nuevo", Toast.LENGTH_SHORT).show();
        }
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
        intento.putExtra("id", id);
        startActivity(intento);
    }
}
