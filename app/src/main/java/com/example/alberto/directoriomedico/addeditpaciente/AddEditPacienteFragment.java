package com.example.alberto.directoriomedico.addeditpaciente;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.alberto.directoriomedico.MainActivity;
import com.example.alberto.directoriomedico.R;
import com.example.alberto.directoriomedico.datos.Pacientes;
import com.example.alberto.directoriomedico.datos.PacientesDbHelper;

public class AddEditPacienteFragment extends Fragment {

    private static final String ARG_PACIENTE_ID = "arg_paciente_id";
    private static final String ARG_MEDICO_ID = "arg_medico_id";

    private String mPacienteId;
    private String mMedicoId;

    private PacientesDbHelper mPacientesDbHelper;
    private FloatingActionButton mSaveButton;
    private TextInputEditText mNameField;
    private TextInputEditText mPhoneNumberField;
    private TextInputEditText mDniField;
    private TextInputLayout mNameLabel;
    private TextInputLayout mPhoneNumberLabel;
    private TextInputLayout mDniLabel;

    public AddEditPacienteFragment() {
        // Required empty public constructor
    }

    public static AddEditPacienteFragment newInstance(String pacienteId, String medicoId){
        AddEditPacienteFragment fragment = new AddEditPacienteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PACIENTE_ID,pacienteId);
        args.putString(ARG_MEDICO_ID,medicoId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            mPacienteId = getArguments().getString(ARG_PACIENTE_ID);
            mMedicoId = getArguments().getString(ARG_MEDICO_ID);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_edit_paciente, container, false);

        mSaveButton = (FloatingActionButton)getActivity().findViewById(R.id.fab);
        mNameLabel = (TextInputLayout)root.findViewById(R.id.til_name_paciente);
        mNameField = (TextInputEditText)root.findViewById(R.id.et_name_paciente);
        mPhoneNumberLabel = (TextInputLayout)root.findViewById(R.id.til_phone_number_paciente);
        mPhoneNumberField = (TextInputEditText)root.findViewById(R.id.et_phone_number_paciente);
        mDniLabel = (TextInputLayout)root.findViewById(R.id.til_dni_paciente);
        mDniField = (TextInputEditText)root.findViewById(R.id.et_dni_paciente);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEditPaciente();
            }
        });

        mPacientesDbHelper = new PacientesDbHelper(getActivity());

        if(mPacienteId != null) {
            loadPaciente();
        }
        return root;
    }

    private void loadPaciente(){
        new GetPacienteByIdTask().execute();
    }

    private void showPaciente(Pacientes pacientes){
        mNameField.setText(pacientes.getName());
        mPhoneNumberField.setText(pacientes.getPhoneNumber());
        mDniField.setText(pacientes.getDni());
    }

    private void showLoadError(){
        Toast.makeText(getActivity(),"Error al editar",Toast.LENGTH_SHORT).show();
    }

    private class GetPacienteByIdTask extends AsyncTask<Void,Void,Cursor> {

        @Override
        protected Cursor doInBackground(Void... params) {
            return mPacientesDbHelper.getPacienteById(mPacienteId);
        }
        @Override
        protected void onPostExecute(Cursor c){
            if(c!=null && c.moveToLast()){
                showPaciente(new Pacientes(c));
            }else{
                showLoadError();
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        }
    }

    private class AddEditPacienteTask extends AsyncTask<Pacientes,Void,Boolean>{

        @Override
        protected Boolean doInBackground(Pacientes... params) {
            if(mPacienteId != null){
                return mPacientesDbHelper.updatePaciente(params[0],mPacienteId)>0;
            }else{
                return mPacientesDbHelper.savePaciente(params[0])>0;
            }
        }

        @Override
        protected void onPostExecute(Boolean result){
            showPacientesScreen(result);
        }
    }

    private void showPacientesScreen(Boolean requery){
        if(!requery){
            showAddeditError();
            getActivity().setResult(Activity.RESULT_CANCELED);
        }else{
            getActivity().setResult(Activity.RESULT_OK);
        }
        getActivity().finish();
    }

    private void showAddeditError(){
        Toast.makeText(getActivity(),"error",Toast.LENGTH_SHORT).show();
    }

    private void addEditPaciente(){
        boolean error = false;
        String name = mNameField.getText().toString();
        String phoneNumber = mPhoneNumberField.getText().toString();
        String dni = mDniField.getText().toString();

        if(TextUtils.isEmpty(name)){
            mNameLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(phoneNumber)){
            mPhoneNumberLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(dni)){
            mDniLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if(error){
            return;
        }
        Pacientes pacientes = new Pacientes(mMedicoId,name,dni,phoneNumber,"");
        new AddEditPacienteFragment.AddEditPacienteTask().execute(pacientes);
    }
}
