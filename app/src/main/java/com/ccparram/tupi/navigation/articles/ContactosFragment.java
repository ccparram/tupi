package com.ccparram.tupi.navigation.articles;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ccparram.tupi.R;
import com.ccparram.tupi.utility.Contacto;
import com.ccparram.tupi.utility.ContactoAdapter;
import com.ccparram.tupi.utility.ContactoDataSource;
import com.ccparram.tupi.utility.ContactoDataSource.ColumnContacto;

import java.util.ArrayList;
import java.util.List;


public class ContactosFragment extends Fragment {
    private final String TAG = this.getClass().getName();
    public static final String ARG_ARTICLES_NUMBER = "articles_number";

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    private ContactoDataSource ContactoDataSource;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contactos, container, false);
        int i = getArguments().getInt(ARG_ARTICLES_NUMBER);
        String article = getResources().getStringArray(R.array.Tags)[i];

        getActivity().setTitle(article);

        ContactoDataSource = new ContactoDataSource(getContext());
        // Inicializar Contactos
        List items = new ArrayList();
        Cursor filasContactoDB = ContactoDataSource.getAllContactos();
        items = cursorToListItems(filasContactoDB);

        // Obtener el Recycler
        recycler = (RecyclerView) rootView.findViewById(R.id.reciclador);
        //recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new ContactoAdapter(items);
        recycler.setAdapter(adapter);



        return rootView;
    }

    public List cursorToListItems(Cursor cursorFilasContactoDB){

        List items = new ArrayList();

        if (cursorFilasContactoDB.moveToFirst() == false){
            //el cursor está vacío
            return null;
        }

        String foto, nombre, telefono, email, direccion;

        do {
            foto = cursorFilasContactoDB.getString(cursorFilasContactoDB
                    //.getColumnIndex(ContactoDataSource.ColumnContacto.FOTO_CONTACTO));
                    .getColumnIndex(ColumnContacto.FOTO_CONTACTO));
            nombre = cursorFilasContactoDB.getString(cursorFilasContactoDB
                            .getColumnIndex(ColumnContacto.NOMBRE_CONTACTO));
            telefono = cursorFilasContactoDB.getString(cursorFilasContactoDB
                    .getColumnIndex(ColumnContacto.TELEFONO_CONTACTO));
            email = cursorFilasContactoDB.getString(cursorFilasContactoDB
                    .getColumnIndex(ColumnContacto.EMAIL_CONTACTO));
            direccion = cursorFilasContactoDB.getString(cursorFilasContactoDB
                    .getColumnIndex(ColumnContacto.DIRECCION_CONTACTO));

            // Añadimos un nuevo objeto DatosTabla al ArrayList.
            items.add(new Contacto(foto, nombre, telefono, email, direccion));
            Log.e(TAG, "Añadido contato a items");
        } while (cursorFilasContactoDB.moveToNext());

        return items;

    }
}
