package com.example.administracionclientes.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.administracionclientes.Clases.Client;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "Client.db";
    public static final String TABLE_NAME = "Clientes";
    public static final String ID_CLI = "id_cli";
    public static final String NOMBRES = "nombres";
    public static final String APELLIDOS = "apellidos";
    public static final String DUI = "dui";
    public static final String NIT = "nit";
    public static final String DIRECCION = "direccion";
    public static final String TELEFONO = "telefono";
    public static final String CORREO = "correo";

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "Create table " + TABLE_NAME + "(" + ID_CLI + " INTEGER primary key autoincrement not null, " + NOMBRES + " varchar(50), " + APELLIDOS + " varchar(50), " + DUI + " varchar(11), " +
                NIT + " varchar(15)," +
                DIRECCION + " varchar(300), " + TELEFONO + " varchar(9), " + CORREO + " varchar(150))";

        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addClient(Client cli) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NOMBRES, cli.getNombres());
        cv.put(APELLIDOS, cli.getApellidos());
        cv.put(DUI, cli.getDui());
        cv.put(NIT, cli.getNit());
        cv.put(DIRECCION, cli.getDireccion());
        cv.put(TELEFONO, cli.getTelefono());
        cv.put(CORREO, cli.getCorreo());

        long insert = db.insert(TABLE_NAME, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateClient(Client cli) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NOMBRES, cli.getNombres());
        cv.put(APELLIDOS, cli.getApellidos());
        cv.put(DUI, cli.getDui());
        cv.put(NIT, cli.getNit());
        cv.put(DIRECCION, cli.getDireccion());
        cv.put(TELEFONO, cli.getTelefono());
        cv.put(CORREO, cli.getCorreo());

        long update = db.update(TABLE_NAME, cv, "id_cli=" + cli.getId(), null);
        if (update == -1) {
            return false;
        } else {
            return true;
        }
    }

    public List<Client> getAll() {
        List<Client> clients = new ArrayList<>();
        String query = "Select * from " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String nombres = cursor.getString(1);
                String apellidos = cursor.getString(2);
                String dui = cursor.getString(3);
                String nit = cursor.getString(4);
                String direccion = cursor.getString(5);
                String telefono = cursor.getString(6);
                String correo = cursor.getString(7);

                Client cli = new Client(id, nombres, apellidos, dui, nit, direccion, telefono, correo);
                clients.add(cli);

            } while (cursor.moveToNext());
        } else {

        }
        cursor.close();
        db.close();
        return clients;
    }

    public Client searchCli(int cli) {
        Client client = new Client();
        String query = "select * from " + TABLE_NAME + " where id_cli = " + cli;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String nombres = cursor.getString(1);
            String apellidos = cursor.getString(2);
            String dui = cursor.getString(3);
            String nit = cursor.getString(4);
            String direccion = cursor.getString(5);
            String telefono = cursor.getString(6);
            String correo = cursor.getString(7);
            client = new Client(id, nombres, apellidos, dui, nit, direccion, telefono, correo);
        } else {
            client = null;
        }
        return client;
    }

    public List<Client> searchCliByName(String cli) {
        List<Client> clients = new ArrayList<>();
        String query = "select * from " + TABLE_NAME + " where nombres like '%" + cli + "%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String nombres = cursor.getString(1);
            String apellidos = cursor.getString(2);
            String dui = cursor.getString(3);
            String nit = cursor.getString(4);
            String direccion = cursor.getString(5);
            String telefono = cursor.getString(6);
            String correo = cursor.getString(7);
            clients.add(new Client(id, nombres, apellidos, dui, nit, direccion, telefono, correo));
        } else {
            clients = null;
        }
        return clients;
    }

    public boolean deleteClient(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "delete from " + TABLE_NAME + " where id_cli = " + id;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }
}
