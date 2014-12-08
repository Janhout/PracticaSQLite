package com.practicas.janhout.practicasqlite;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Principal extends Activity {

    private ActionBar actionBar;
    private FragmentoListaPartido fp;
    private FragmentoListaJugador fj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        actionBar = getActionBar();

        fp = new FragmentoListaPartido();
        fj = new FragmentoListaJugador();
        if (actionBar != null) {
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            ActionBar.Tab tab2 = actionBar.newTab()
                    .setText(getString(R.string.tab_partido))
                    .setTabListener(new EscuchadorTabs<>(
                            this, getString(R.string.tab_partido), fp));

            ActionBar.Tab tab1 = actionBar.newTab()
                    .setText(getString(R.string.tab_jugador))
                    .setTabListener(new EscuchadorTabs<>(
                            this, getString(R.string.tab_jugador), fj));
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
            fj.nuevoJugador();
            return true;
        } else if (id == R.id.menu_Partido) {
            fp.nuevoPartido();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(actionBar.getSelectedNavigationIndex()==0){
            menu.findItem(R.id.menu_Partido).setVisible(false);
            menu.findItem(R.id.menu_Jugador).setVisible(true);
        }else{
            menu.findItem(R.id.menu_Jugador).setVisible(false);
            menu.findItem(R.id.menu_Partido).setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    public static void tostada(Context c, String s){
        Toast.makeText(c, s, Toast.LENGTH_SHORT).show();
    }
}
