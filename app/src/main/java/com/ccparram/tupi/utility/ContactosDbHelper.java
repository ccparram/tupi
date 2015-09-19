package com.ccparram.tupi.utility;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by camilow8 on 14/09/2015.
 */
public class ContactosDbHelper extends SQLiteOpenHelper {

    private final String TAG = this.getClass().getName();

    public static final String DATABASE_NAME = "Contacto.db";
    public static final int DATABASE_VERSION = 1;

    public ContactosDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        //Crear la tabla Quotes
        db.execSQL(ContactoDataSource.CREATE_CONTACTO_SCRIPT);



        //Insertar registros iniciales
        //db.execSQL(ContactoDataSource.INSERT_CONTACTO_SCRIPT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Añade los cambios que se realizarán en el esquema
    }
}
