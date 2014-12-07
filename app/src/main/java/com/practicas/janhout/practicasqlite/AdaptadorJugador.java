package com.practicas.janhout.practicasqlite;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class AdaptadorJugador extends CursorAdapter {

    public AdaptadorJugador(Context co, Cursor cu){
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
        TextView tv = ((TextView)view.findViewById(R.id.textView));
        tv.setText(cursor.getLong(0)+"id");
        tv.append(cursor.getString(1)+"nombre");
        tv.append(cursor.getString(2)+"telefono");
        tv.append(cursor.getString(3)+"fnac");
        tv.append(cursor.getLong(4)+"media");
    }
}