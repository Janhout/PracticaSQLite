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

public class FragmentoListaPartido extends Fragment {

    private ListView lvPartido;
    private AdaptadorPartido ad;
    private GestorPartido gp;
    private AlertDialog alerta;

    private final int ACTIVIDAD_PARTIDO = 1;

    public FragmentoListaPartido() {
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int index = info.position;
        if(id == R.id.cont_borr_par){
            return borrarPartido(index);
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.contextual_partido, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragmento_lista, container, false);
        gp = new GestorPartido(getActivity());
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        gp.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        gp.open();
        Cursor c = gp.getCursor();
        ad = new AdaptadorPartido(getActivity(), c);
        lvPartido = (ListView) getView().findViewById(R.id.lv);
        lvPartido.setAdapter(ad);
        registerForContextMenu(lvPartido);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(alerta != null){
            alerta.dismiss();
        }
    }

    private boolean borrarPartido(int index){
        Cursor c = (Cursor)lvPartido.getItemAtPosition(index);
        final Partido p = GestorPartido.getRow(c);

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle(getString(R.string.borrar_jugador));
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final View vista = inflater.inflate(R.layout.dialogo_borrar, null);
        alert.setView(vista);
        String nombre = p.getContrincante() + " (jugador: " +  p.getIdJugador() + ")";
        TextView texto = (TextView)vista.findViewById(R.id.tvConfirmacion);
        texto.setText(getString(R.string.seguro) + " " + nombre + "?");
        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int whichButton){
                gp.delete(p);
                ad.changeCursor(gp.getCursor());
            }
        });
        alert.setNegativeButton(android.R.string.no, null);
        alerta = alert.create();
        alerta.show();
        return true;
    }

    public void nuevoPartido() {

    }
}