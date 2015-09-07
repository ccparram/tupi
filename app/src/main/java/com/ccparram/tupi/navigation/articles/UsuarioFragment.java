package com.ccparram.tupi.navigation.articles;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ccparram.tupi.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class UsuarioFragment extends Fragment {

    private final String TAG = this.getClass().getName();
    public static final String ARG_ARTICLES_NUMBER = "articles_number";
    private String strJsonGraph;
    private ImageView userFoto;
    private TextView userNombre;
    private TextView userEmail;
    private TextView userBirthday;

    public UsuarioFragment() {
        // Constructor vacío
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_usuario, container, false);
        int i = getArguments().getInt(ARG_ARTICLES_NUMBER);
        strJsonGraph = getArguments().getString("strJsonGraph");

        userFoto = (ImageView)rootView.findViewById(R.id.fotoPerfil);
        userNombre = (TextView)rootView.findViewById(R.id.nombre);
        userEmail = (TextView)rootView.findViewById(R.id.email);
        userBirthday = (TextView)rootView.findViewById(R.id.birthday);

        String article = getResources().getStringArray(R.array.Tags)[i];
        getActivity().setTitle(article);

        new setInfoUserTask().execute(strJsonGraph);

        Log.e(TAG, "usuario: " + strJsonGraph);

        return rootView;
    }


    private class setInfoUserTask extends AsyncTask<String, Void, Void> {

        Bitmap mIcon1;
        String ID;
        String nombre;
        String email;
        String birthday;


        @Override
        protected Void doInBackground(String... params) {

            try {
                JSONObject infoJson = new JSONObject(params[0]);

                ID = infoJson.getString("id");
                nombre = infoJson.getString("name");
                email = infoJson.getString("email");
                birthday = infoJson.getString("birthday");

                URL img_value = new URL("https://graph.facebook.com/"+ID+"/picture?type=large");
                mIcon1 = BitmapFactory.decodeStream(img_value.openConnection().getInputStream());




            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            userFoto.setImageBitmap(mIcon1);
            userNombre.setText(getResources().getString(R.string.nombre) + nombre);
            userEmail.setText(getResources().getString(R.string.email) + (email));
            userBirthday.setText(getResources().getString(R.string.birthday) + birthday);
        }
    }

}
