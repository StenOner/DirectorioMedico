package com.example.alberto.directoriomedico;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    public static String EXTRA_MEDICO_ID = "medicoId";
    public static String EXTRA_PACIENTE_ID = "pacienteId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String id = getIntent().getStringExtra(EXTRA_MEDICO_ID);
        if (id != null){
            setTitle("Lista Pacientes");
            PacientesFragment fragment = (PacientesFragment)
                    getSupportFragmentManager().findFragmentById(R.id.medicos_container);
            if (fragment == null){
                fragment = PacientesFragment.newInstance(id);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.medicos_container,fragment)
                        .commit();
            }
        }else{
            setTitle("Directorio Medico");
            MedicosFragment fragment = (MedicosFragment)
                    getSupportFragmentManager().findFragmentById(R.id.medicos_container);
            if(fragment == null){
                fragment = MedicosFragment.newInstance();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.medicos_container,fragment)
                        .commit();
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageDrawable(getResources()
                .getDrawable(R.drawable.ic_account_plus,null));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_medico){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
