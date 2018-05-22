package com.example.alberto.directoriomedico.pacientedetail;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.example.alberto.directoriomedico.MainActivity;
import com.example.alberto.directoriomedico.R;

public class PacienteDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String pacienteId = getIntent().getStringExtra(MainActivity.EXTRA_PACIENTE_ID);
        String medicoId = getIntent().getStringExtra(MainActivity.EXTRA_MEDICO_ID);

        PacienteDetailFragment fragment = (PacienteDetailFragment)
                    getSupportFragmentManager().findFragmentById(R.id.paciente_detail_container);

        if(fragment == null){
            fragment = PacienteDetailFragment.newInstance(pacienteId,medicoId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.paciente_detail_container,fragment)
                    .commit();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_paciente_detail,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}
