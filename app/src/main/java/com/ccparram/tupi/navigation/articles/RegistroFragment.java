package com.ccparram.tupi.navigation.articles;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ccparram.tupi.R;
import com.ccparram.tupi.utility.ContactoDataSource;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class RegistroFragment extends Fragment {

    private final String TAG = this.getClass().getName();
    public static final String ARG_ARTICLES_NUMBER = "articles_number";

    private ImageView icoFoto;
    private EditText nombre;
    private EditText telefono;
    private EditText email;
    private EditText direccion;
    private Button agregar;

    private static ContactoDataSource ContactoDataSource;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    private String mCurrentPhotoPath;

    public static com.ccparram.tupi.utility.ContactoDataSource getContactoDataSource() {
        return ContactoDataSource;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_registro, container, false);
        int i = getArguments().getInt(ARG_ARTICLES_NUMBER);
        String article = getResources().getStringArray(R.array.Tags)[i];
        getActivity().setTitle(article);

        ContactoDataSource = new ContactoDataSource(getContext());

        icoFoto = (ImageView)rootView.findViewById(R.id.iconContact);
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

        icoFoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dispatchTakePictureIntent();
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

        ContactoDataSource.saveContactRow(strNombre, strTelefono, strEmail, strDireccion, mCurrentPhotoPath);
        limpiarFormulario();

    }

        // Function that invokes an intent to capture a photo.

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

        //retrieves image and displays it in iconFoto
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            //Bundle extras = data.getExtras();
            //Bitmap imageBitmap = (Bitmap) extras.get("data");

            icoFoto.setImageBitmap(BitmapFactory.decodeFile(mCurrentPhotoPath));



        }
    }

        // Create a file for the photo
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void limpiarFormulario(){

        icoFoto.setImageResource(R.drawable.ic_contact_cam);
        nombre.setText("");
        telefono.setText("");
        email.setText("");
        direccion.setText("");
    }




}
