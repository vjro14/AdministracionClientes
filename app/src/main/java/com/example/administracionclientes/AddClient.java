package com.example.administracionclientes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.administracionclientes.Clases.Client;
import com.example.administracionclientes.DB.DataBaseHelper;

public class AddClient extends AppCompatActivity {

    EditText etnombre, etapellido, etdui, etnit, etdireccion, ettelefono, etcorreo;
    AlertDialog.Builder DialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);
        DialogBuilder = new AlertDialog.Builder(AddClient.this);

        etnombre = findViewById(R.id.et_nombresAdd);
        etapellido = findViewById(R.id.et_apellidosAdd);
        etdui = findViewById(R.id.et_duiAdd);
        etnit = findViewById(R.id.et_nitAdd);
        etdireccion = findViewById(R.id.et_direccionAdd);
        ettelefono = findViewById(R.id.et_telefonoAdd);
        etcorreo = findViewById(R.id.et_correoAdd);
    }

    ///////////////////////////db////////////////////////////////////////////////////////////

    public void add() {
        Client cli = new Client(0, etnombre.getText().toString(), etapellido.getText().toString(), etdui.getText().toString(), etnit.getText().toString(),
                etdireccion.getText().toString(), ettelefono.getText().toString(), etcorreo.getText().toString());

        try {

            DataBaseHelper dataBaseHelper = new DataBaseHelper(AddClient.this);
            boolean success = dataBaseHelper.addClient(cli);

            if (success) {
                Intent intent = new Intent(AddClient.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        } catch (Exception e) {
            Toast.makeText(AddClient.this, "Ocurrio un error, intente de nuevo", Toast.LENGTH_SHORT).show();
        }
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
