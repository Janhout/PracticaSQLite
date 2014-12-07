package com.practicas.janhout.practicasqlite;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class Principal extends Activity {

    private ListView lv;
    private GestorJugador gj;
    private GestorPartido gp;
    private AdaptadorJugador a;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        actionBar = getActionBar();

        if (actionBar != null) {
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            ActionBar.Tab tab2 = actionBar.newTab()
                    .setText(getString(R.string.tab_partido))
                    .setTabListener(new EscuchadorTabs<>(
                            this, getString(R.string.tab_partido), FragmentoListaPartido.class));

            ActionBar.Tab tab1 = actionBar.newTab()
                    .setText(getString(R.string.tab_jugador))
                    .setTabListener(new EscuchadorTabs<>(
                            this, getString(R.string.tab_jugador), FragmentoListaJugador.class));
            actionBar.addTab(tab1);
            actionBar.addTab(tab2);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_Jugador) {
            FragmentoListaJugador f = new FragmentoListaJugador();
            f.nuevoJugador();
            return true;
        }else if (id == R.id.menu_Partido) {
            FragmentoListaPartido f = new FragmentoListaPartido();
            f.nuevoPartido();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        actionBar.setSelectedNavigationItem(savedInstanceState.getInt(getString(R.string.tab_selected)));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.tab_selected),actionBar.getSelectedNavigationIndex());
    }

/*

    public void alta(View view) {
        String nombre, valoracion, fecha, telefono;
        nombre = etNombre.getText().toString();
        valoracion = etValoracion.getText().toString();
        fecha = etFecha.getText().toString();
        telefono = etTelefono.getText().toString();
        Jugador j = new Jugador(nombre, telefono, valoracion, fecha);
        long id = gj.insert(j);
        a.getCursor().close();
        a.changeCursor(gj.getCursor());
        Toast.makeText(this, "Jugador insertado, id: " + id, Toast.LENGTH_LONG).show();
    }*/
}
