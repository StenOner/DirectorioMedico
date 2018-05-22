package com.example.alberto.directoriomedico.datos;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.UUID;

public class Pacientes {
    private String id;
    private String id_medico;
    private String name;
    private String dni;
    private String phoneNumber;
    private String avatarUri;

    public Pacientes(String id_medico, String name, String dni, String phoneNumber, String avatarUri) {
        this.id = UUID.randomUUID().toString();
        this.id_medico = id_medico;
        this.name = name;
        this.dni = dni;
        this.phoneNumber = phoneNumber;
        this.avatarUri = avatarUri;
    }
    public Pacientes(Cursor cursor){
        id = cursor.getString(cursor.getColumnIndex
                (PacientesContract.PacientesEntry.ID));
        id_medico = cursor.getString(cursor.getColumnIndex
                (PacientesContract.PacientesEntry.ID_MEDICO));
        name = cursor.getString(cursor.getColumnIndex
                (PacientesContract.PacientesEntry.NAME));
        dni = cursor.getString(cursor.getColumnIndex
                (PacientesContract.PacientesEntry.DNI));
        phoneNumber = cursor.getString(cursor.getColumnIndex
                (PacientesContract.PacientesEntry.PHONE_NUMBER));
        avatarUri = cursor.getString(cursor.getColumnIndex
                (PacientesContract.PacientesEntry.AVATAR_URI));
    }
    public ContentValues toContentValues(){
        ContentValues values = new ContentValues();
        values.put(PacientesContract.PacientesEntry.ID,id);
        values.put(PacientesContract.PacientesEntry.ID_MEDICO,id_medico);
        values.put(PacientesContract.PacientesEntry.NAME,name);
        values.put(PacientesContract.PacientesEntry.DNI
                ,dni);
        values.put(PacientesContract.PacientesEntry.PHONE_NUMBER
                ,phoneNumber);
        values.put(PacientesContract.PacientesEntry.AVATAR_URI
                ,avatarUri);
        return values;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDni() {
        return dni;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAvatarUri() {
        return avatarUri;
    }

    public String getId_medico() {
        return id_medico;
    }
}
