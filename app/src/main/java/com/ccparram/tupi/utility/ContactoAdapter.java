package com.ccparram.tupi.utility;

import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ccparram.tupi.R;

import java.util.List;

/**
 * Created by camilow8 on 19/09/2015.
 */
public class ContactoAdapter extends RecyclerView.Adapter<ContactoAdapter.ContactoViewHolder>{

    private List<Contacto> items;

    public static class ContactoViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public ImageView imagen;
        public TextView nombre;
        public TextView telefono;
        public TextView email;
        public TextView direccion;

        public ContactoViewHolder(View v) {
            super(v);
            imagen = (ImageView) v.findViewById(R.id.imagen);
            nombre = (TextView) v.findViewById(R.id.nombre);
            telefono = (TextView) v.findViewById(R.id.telefono);
            email = (TextView) v.findViewById(R.id.email);
            direccion = (TextView) v.findViewById(R.id.direccion);
        }
    }

    public ContactoAdapter(List<Contacto> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        if(items == null) return 0;
        return items.size();
    }

    @Override
    public ContactoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.contacto_card, viewGroup, false);
        return new ContactoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ContactoViewHolder viewHolder, int i) {
        viewHolder.imagen.setImageBitmap(BitmapFactory.decodeFile(items.get(i).getImg()));
        viewHolder.nombre.setText(items.get(i).getNombre());
        viewHolder.telefono.setText(items.get(i).getTelefono());
        viewHolder.email.setText(items.get(i).getEmail());
        viewHolder.direccion.setText(items.get(i).getDireccion());
    }

}
