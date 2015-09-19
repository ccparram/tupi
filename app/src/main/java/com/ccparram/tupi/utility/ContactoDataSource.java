package com.ccparram.tupi.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.widget.Toast;

import com.ccparram.tupi.R;

/**
 * Created by camilow8 on 14/09/2015.
 */
public class ContactoDataSource {

    Context context;

    //Metainformación de la base de datos
    public static final String CONTACTO_TABLE_NAME = "contacto";
    public static final String STRING_TYPE = "text";
    public static final String INT_TYPE = "integer";

    private ContactosDbHelper openHelper;
    private SQLiteDatabase database;


    public ContactoDataSource(Context context) {
        this.context = context;
        //Creando una instancia hacia la base de datos
        openHelper = new ContactosDbHelper(context);
        database = openHelper.getWritableDatabase();
    }

    //Campos de la tabla contacto
    public static class ColumnContacto{
        public static final String ID_CONTACTO = BaseColumns._ID;
        public static final String NOMBRE_CONTACTO = "nombre";
        public static final String TELEFONO_CONTACTO = "telefono";
        public static final String EMAIL_CONTACTO = "email";
        public static final String DIRECCION_CONTACTO = "direccion";
        public static final String FOTO_CONTACTO = "foto";
    }

    //Script de Creación de la tabla contacto
    public static final String CREATE_CONTACTO_SCRIPT =
            "create table "+ CONTACTO_TABLE_NAME+"(" +
                    ColumnContacto.ID_CONTACTO+" "+INT_TYPE+" primary key autoincrement," +
                    ColumnContacto.NOMBRE_CONTACTO+" "+STRING_TYPE+" not null," +
                    ColumnContacto.TELEFONO_CONTACTO+" "+STRING_TYPE+" not null," +
                    ColumnContacto.EMAIL_CONTACTO+" "+STRING_TYPE+" not null," +
                    ColumnContacto.DIRECCION_CONTACTO+" "+STRING_TYPE+" not null," +
                    ColumnContacto.FOTO_CONTACTO+" "+STRING_TYPE+")";

    //Scripts de inserción por defecto
    public static final String INSERT_CONTACTO_SCRIPT =
            "insert into "+CONTACTO_TABLE_NAME+" values(" +
                    "null," +
                    "\"Camilo\"," +
                    "\"3153824554\"," +
                    "\"ccparram@unal.edu.co\"," +
                    "\"Cll. 10A\"," +
                    "\"urlFotoCamilo\"), (" +

                    "null," +
                    "\"Daniel\"," +
                    "\"3189856532\"," +
                    "\"wdospinal@unal.edu.co\"," +
                    "\"Cll. 73\"," +
                    "\"urlFotoDaniel\")";

    public void saveContactRow(String nombre, String telefono, String email, String direccion, String foto){
        //Nuestro contenedor de valores
        ContentValues values = new ContentValues();

        //Seteando body y author
        values.put(ColumnContacto.NOMBRE_CONTACTO, nombre);
        values.put(ColumnContacto.TELEFONO_CONTACTO, telefono);
        values.put(ColumnContacto.EMAIL_CONTACTO, email);
        values.put(ColumnContacto.DIRECCION_CONTACTO, direccion);
        values.put(ColumnContacto.FOTO_CONTACTO, foto);

        //Insertando en la base de datos
        database.insert(CONTACTO_TABLE_NAME, null, values);

        Toast toast = Toast.makeText(context, R.string.insert_ok, Toast.LENGTH_SHORT);
        toast.show();

    }

    public Cursor getAllContactos(){
        //Seleccionamos todas las filas de la tabla Contacto
        return database.rawQuery(
                "select * from " + CONTACTO_TABLE_NAME, null);
    }


}
