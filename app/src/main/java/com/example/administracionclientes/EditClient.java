package com.example.administracionclientes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.administracionclientes.Clases.Client;
import com.example.administracionclientes.DB.DataBaseHelper;

public class EditClient extends AppCompatActivity {

    int id;
    EditText etnombre, etapellido, etdui, etnit, etdireccion, ettelefono, etcorreo;
    AlertDialog.Builder DialogBuilder;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_client);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Bundle extra = getIntent().getExtras();
        DialogBuilder = new AlertDialog.Builder(EditClient.this);
        if (extra != null) {
            id = extra.getInt("id");
        }
        dataBaseHelper = new DataBaseHelper(EditClient.this);
        etnombre = findViewById(R.id.et_nombres);
        etapellido = findViewById(R.id.et_apellidos);
        etdui = findViewById(R.id.et_dui);
        etnit = findViewById(R.id.et_nit);
        etdireccion = findViewById(R.id.et_direccion);
        ettelefono = findViewById(R.id.et_telefono);
        etcorreo = findViewById(R.id.et_correo);

        getClient();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    ///////////////////////////db////////////////////////////////////////////////////////////
    public void getClient() {

        Client data = dataBaseHelper.searchCli(id);
        if (data != null) {
            etnombre.setText(data.getNombres());
            etapellido.setText(data.getApellidos());
            etdui.setText(data.getDui());
            etnit.setText(data.getNit());
            etdireccion.setText(data.getDireccion());
            ettelefono.setText(data.getTelefono());
            etcorreo.setText(data.getCorreo());
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Ocurrio un error, intente de nuevo")

                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(EditClient.this, MainActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    public void edit() {
        Client data = new Client(id, etnombre.getText().toString(), etapellido.getText().toString(), etdui.getText().toString(), etnit.getText().toString(),
                etdireccion.getText().toString(), ettelefono.getText().toString(), etcorreo.getText().toString());
        boolean result = dataBaseHelper.updateClient(data);

        if (result) {
            Intent intent = new Intent(EditClient.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Ocurrio un error, intente de nuevo")

                    .setPositiveButton(android.R.string.yes, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

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
