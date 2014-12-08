package com.practicas.janhout.practicasqlite;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class AdaptadorJugador extends CursorAdapter {

    public AdaptadorJugador(Context co, Cursor cu) {
        super(co, cu, true);
    }

    @Override
    public View newView(Context co, Cursor cu, ViewGroup vg) {
        LayoutInflater i = LayoutInflater.from(vg.getContext());
        View v = i.inflate(R.layout.lista_detalle_jugador, vg, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvNombre = ((TextView) view.findViewById(R.id.tvNombre));
        TextView tvTelefono = ((TextView) view.findViewById(R.id.tvTelefono));
        TextView tvValoracion = ((TextView) view.findViewById(R.id.tvValoracion));

        tvNombre.setText(cursor.getLong(0) + " " + cursor.getString(1));
        tvTelefono.setText(cursor.getString(2) + " - " + cursor.getString(3));
        tvValoracion.setText(cursor.getLong(4)+"");
    }
}