package com.ccparram.tupi.navigation.articles;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ccparram.tupi.R;
import com.ccparram.tupi.utility.ContactoDataSource;


public class RegistroFragment extends Fragment {

    private final String TAG = this.getClass().getName();
    public static final String ARG_ARTICLES_NUMBER = "articles_number";

    private EditText nombre;
    private EditText telefono;
    private EditText email;
    private EditText direccion;
    private Button agregar;

    private ContactoDataSource ContactoDataSource;

    public RegistroFragment() {
        // Constructor vacío
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_registro, container, false);
        int i = getArguments().getInt(ARG_ARTICLES_NUMBER);
        String article = getResources().getStringArray(R.array.Tags)[i];
        getActivity().setTitle(article);

        ContactoDataSource = new ContactoDataSource(getContext());

        nombre = (EditText)rootView.findViewById(R.id.nombre);
        telefono = (EditText)rootView.findViewById(R.id.telefono);
        email = (EditText)rootView.findViewById(R.id.email);
        direccion = (EditText)rootView.findViewById(R.id.direccion);
        agregar = (Button)rootView.findViewById(R.id.agregar);

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarContacto();
            }
        });


        return rootView;
    }

        private void agregarContacto(){

        String strNombre = nombre.getText().toString();
        String strTelefono = telefono.getText().toString();
        String strEmail = email.getText().toString();
        String strDireccion = direccion.getText().toString();

        //Crear nuevo objeto ContactoDataSource

        Log.e(TAG, "Agregar Contacto");

        ContactoDataSource.saveContactRow(strNombre,strTelefono,strEmail,strDireccion, "urlFotito");


    }


}
