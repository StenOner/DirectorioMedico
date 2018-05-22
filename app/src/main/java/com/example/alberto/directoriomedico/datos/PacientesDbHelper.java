package com.example.alberto.directoriomedico.datos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PacientesDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION
            = 1;
    public static final String DATABASE_NAME
            = "Medicos.db";

    public PacientesDbHelper(Context context){
        super(context,DATABASE_NAME
                ,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor getAllPacientesByIdMedico(String medicoId){
        return getReadableDatabase()
                .query(PacientesContract.PacientesEntry.TABLE_NAME
                        ,null // columnas
                        , PacientesContract.PacientesEntry.ID_MEDICO + " LIKE ?"
                        ,new String[] {medicoId}
                        ,null // valores WHERE
                        ,null // GROUP BY
                        ,null // HAVING
                        ,null); // OREDER BY
    }

    public Cursor getPacienteById(String pacienteId){
        return getReadableDatabase()
                .query(PacientesContract.PacientesEntry.TABLE_NAME
                        ,null
                        , PacientesContract.PacientesEntry.ID + " LIKE ?"
                        ,new String[] {pacienteId}
                        ,null
                        ,null
                        ,null);
    }

    public long savePaciente(Pacientes pacientes){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                PacientesContract.PacientesEntry.TABLE_NAME
                ,null
                ,pacientes.toContentValues());
    }

    public int updatePaciente(Pacientes pacientes
            ,String pacienteId){
        return getWritableDatabase().update(
                PacientesContract.PacientesEntry.TABLE_NAME
                ,pacientes.toContentValues()
                , PacientesContract.PacientesEntry.ID + " LIKE ?"
                ,new String[]{pacienteId}
        );
    }

    public int deletePaciente(String pacienteId){
        return getWritableDatabase().delete(
                PacientesContract.PacientesEntry.TABLE_NAME
                , PacientesContract.PacientesEntry.ID + " LIKE ?"
                ,new String[]{pacienteId}
        );
    }
}
