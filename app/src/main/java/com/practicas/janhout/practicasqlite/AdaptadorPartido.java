package com.practicas.janhout.practicasqlite;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class AdaptadorPartido extends CursorAdapter {

    public AdaptadorPartido(Context co, Cursor cu){
        super(co, cu, true);
    }

    @Override
    public View newView(Context co, Cursor cu, ViewGroup vg) {
        LayoutInflater i = LayoutInflater.from(vg.getContext());
        View v = i.inflate(R.layout.lista_detalle_partido, vg, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tv = ((TextView)view.findViewById(R.id.textView));
        Partido p = GestorPartido.getRow(cursor);
        tv.setText(p.toString());
    }
}