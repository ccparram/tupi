package com.ccparram.tupi.navigation;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ccparram.tupi.R;
import com.ccparram.tupi.navigation.articles.ArticleFragment;
import com.ccparram.tupi.navigation.articles.ContactosFragment;
import com.ccparram.tupi.navigation.articles.PronosticoFragment;
import com.ccparram.tupi.navigation.articles.RegistroFragment;
import com.ccparram.tupi.navigation.articles.UsuarioFragment;

import java.util.ArrayList;


public class Navigation extends AppCompatActivity{

    private final String TAG = this.getClass().getName();

    /*
     DECLARACIONES
     */
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    private CharSequence activityTitle;
    private CharSequence itemTitle;
    private String[] tagTitles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        itemTitle = activityTitle = getTitle();
        tagTitles = getResources().getStringArray(R.array.Tags);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);

        // Setear una sombra sobre el contenido principal cuando el drawer se despliegue
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        //Crear elementos de la lista
        ArrayList<DrawerItem> items = new ArrayList<DrawerItem>();
        items.add(new DrawerItem(tagTitles[0], R.drawable.ic_html));
        items.add(new DrawerItem(tagTitles[1], R.drawable.ic_css));
        items.add(new DrawerItem(tagTitles[2], R.drawable.ic_javascript));
        items.add(new DrawerItem(tagTitles[3], R.drawable.ic_angular));
        items.add(new DrawerItem(tagTitles[4], R.drawable.ic_python));


        // Relacionar el adaptador y la escucha de la lista del drawer
        drawerList.setAdapter(new DrawerListAdapter(this, items));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        // Habilitar el icono de la app por si hay algún estilo que lo deshabilitó
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Crear ActionBarDrawerToggle para la apertura y cierre
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close)
        {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(itemTitle);

                /*Usa este método si vas a modificar la action bar
                con cada fragmento
                 */
                //invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(activityTitle);

                /*Usa este método si vas a modificar la action bar
                con cada fragmento
                 */
                //invalidateOptionsMenu();
            }
        };
        //Seteamos la escucha
        drawerLayout.setDrawerListener(drawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            // Toma los eventos de selección del toggle aquí
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* La escucha del ListView en el Drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        final String ARG_ARTICLES_NUMBER = "articles_number";
        Bundle args = new Bundle();

        switch (position){
            case 0:
                String strJsonGraph = getIntent ().getExtras().getString("strJsonGraph");

                // Reemplazar el contenido del layout principal por un fragmento
                UsuarioFragment fragment0 = new UsuarioFragment();

                args.putInt(ARG_ARTICLES_NUMBER, position);
                args.putString("strJsonGraph", strJsonGraph);
                fragment0.setArguments(args);

                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment0).commit();

                break;
            case 1:
                RegistroFragment fragment1 = new RegistroFragment();

                args.putInt(ARG_ARTICLES_NUMBER, position);
                fragment1.setArguments(args);

                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment1).commit();

                break;
            case 2:
                ContactosFragment fragment2 = new ContactosFragment();

                args.putInt(ARG_ARTICLES_NUMBER, position);
                fragment2.setArguments(args);

                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment2).commit();

                break;
            case 3:
                PronosticoFragment fragment3 = new PronosticoFragment();

                args.putInt(ARG_ARTICLES_NUMBER, position);
                fragment3.setArguments(args);

                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment3).commit();

                break;
            case 4:
                ArticleFragment fragment4 = new ArticleFragment();

                args.putInt(ARG_ARTICLES_NUMBER, position);
                fragment4.setArguments(args);

                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment4).commit();

                break;

        }




        // Se actualiza el item seleccionado y el título, después de cerrar el drawer
        drawerList.setItemChecked(position, true);
        setTitle(tagTitles[position]);
        drawerLayout.closeDrawer(drawerList);

        Log.e("Mensaje", "Índice: " + position);

    }

    /* Método auxiliar para setear el titulo de la action bar */
    @Override
    public void setTitle(CharSequence title) {
        itemTitle = title;
        getSupportActionBar().setTitle(itemTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sincronizar el estado del drawer
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Cambiar las configuraciones del drawer si hubo modificaciones
        drawerToggle.onConfigurationChanged(newConfig);
    }


}