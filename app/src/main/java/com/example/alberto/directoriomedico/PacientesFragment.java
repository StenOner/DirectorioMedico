package com.example.alberto.directoriomedico;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.alberto.directoriomedico.addeditpaciente.AddEditPacienteActivity;
import com.example.alberto.directoriomedico.datos.PacientesContract;
import com.example.alberto.directoriomedico.datos.PacientesCursorAdapter;
import com.example.alberto.directoriomedico.datos.PacientesDbHelper;
import com.example.alberto.directoriomedico.pacientedetail.PacienteDetailActivity;

public class PacientesFragment extends Fragment {

    private static final String ARG_MEDICO_ID = "medicoId";

    ListView mPacientesList;
    FloatingActionButton mAddButton;
    PacientesCursorAdapter mPacientesAdapter;
    PacientesDbHelper mPacientesDBHelper;

    private static String mMedicoId;

    public static final int REQUEST_UPDATE_DELETE_PACIENTE = 2;


    public PacientesFragment() {
        // Required empty public constructor
    }

    public static PacientesFragment newInstance(String medicoId){
        PacientesFragment pacientesFragment = new PacientesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MEDICO_ID,medicoId);
        pacientesFragment.setArguments(args);
        mMedicoId=medicoId;
        return pacientesFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_pacientes, container, false);

        mPacientesList = (ListView)root.findViewById(R.id.pacientes_list);
        mPacientesAdapter = new PacientesCursorAdapter(getActivity(),null,0);
        mAddButton = (FloatingActionButton)getActivity().findViewById(R.id.fab);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddScreen();
            }
        });

        mPacientesList.setAdapter(mPacientesAdapter);

        mPacientesDBHelper = new PacientesDbHelper(getActivity());

        mPacientesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor currentItem = (Cursor)mPacientesAdapter.getItem(position);
                String currentPacienteId = currentItem.getString(currentItem.getColumnIndex(PacientesContract.PacientesEntry.ID));
                showDetailScreen(currentPacienteId);
            }
        });
        loadPacientesbyMedicoId();

        return root;
    }

    private void showAddScreen(){
        Intent intent = new Intent(getActivity(),AddEditPacienteActivity.class);
        intent.putExtra(ARG_MEDICO_ID,mMedicoId);
        startActivityForResult(intent,AddEditPacienteActivity.REQUEST_ADD_PACIENTE);
    }

    private void showDetailScreen(String pacienteId){
        Intent intent = new Intent(getActivity(), PacienteDetailActivity.class);
        intent.putExtra(MainActivity.EXTRA_PACIENTE_ID,pacienteId);
        intent.putExtra(MainActivity.EXTRA_MEDICO_ID,mMedicoId);
        startActivityForResult(intent,REQUEST_UPDATE_DELETE_PACIENTE);
    }

    private void loadPacientesbyMedicoId(){
        new PacientesLoadbyMedicoIdTask().execute();
    }

    private class PacientesLoadbyMedicoIdTask extends AsyncTask<Void,Void,Cursor> {


        @Override
        protected Cursor doInBackground(Void... params) {
            return mPacientesDBHelper.getAllPacientesByIdMedico(mMedicoId);
        }

        @Override
        protected void onPostExecute(Cursor c){
            if(c!=null && c.getCount()>0){
                mPacientesAdapter.swapCursor(c);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(Activity.RESULT_OK == resultCode){
            switch(requestCode){
                case AddEditPacienteActivity.REQUEST_ADD_PACIENTE:
                    showSuccessfullSavedMessage();
                    loadPacientesbyMedicoId();
                    break;
                case REQUEST_UPDATE_DELETE_PACIENTE:
                    loadPacientesbyMedicoId();
                    break;
            }
        }
    }

    private void showSuccessfullSavedMessage(){
        Toast.makeText(getActivity(),"Paciente guardado correctamente",Toast.LENGTH_SHORT).show();
    }
}
