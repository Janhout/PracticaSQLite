package com.practicas.janhout.practicasqlite;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentoListaJugador extends Fragment {

    private ListView lvJugador;
    private AdaptadorJugador ad;
    private GestorJugador gj;
    private AlertDialog alerta;

    private final int ACTIVIDAD_JUGADOR = 1;

    public FragmentoListaJugador() {
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int index = info.position;
        if(id == R.id.cont_borr_jug){
            return borrarJugador(index);
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.contextual_jugador, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragmento_lista, container, false);
        gj = new GestorJugador(getActivity());
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        gj.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        gj.open();
        Cursor c = gj.getCursorFinal();
        ad = new AdaptadorJugador(getActivity(), c);
        lvJugador = (ListView)getView().findViewById(R.id.lv);
        lvJugador.setAdapter(ad);
        registerForContextMenu(lvJugador);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(alerta != null){
            alerta.dismiss();
        }
    }

    private boolean borrarJugador(int index){
        Cursor c = (Cursor)lvJugador.getItemAtPosition(index);
        final Jugador j = GestorJugador.getRow(c);

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle(getString(R.string.borrar_jugador));
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final View vista = inflater.inflate(R.layout.dialogo_borrar, null);
        alert.setView(vista);
        String nombre = j.getNombre() + " (" +  j.getId() + ")";
        TextView texto = (TextView)vista.findViewById(R.id.tvConfirmacion);
        texto.setText(getString(R.string.seguro) + " " + nombre + "?");
        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int whichButton){
                gj.deleteAll(j);
                ad.changeCursor(gj.getCursorFinal());
            }
        });
        alert.setNegativeButton(android.R.string.no, null);
        alerta = alert.create();
        alerta.show();
        return true;
    }

    public void nuevoJugador(){

    }
}
