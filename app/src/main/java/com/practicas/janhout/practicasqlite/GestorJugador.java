package com.practicas.janhout.practicasqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class GestorJugador {

    private Ayudante abd;
    private SQLiteDatabase bd;

    public GestorJugador(Context c) {
        abd = new Ayudante(c);
    }

    public void open() {
        bd = abd.getWritableDatabase();
    }

    public void openRead() {
        bd = abd.getReadableDatabase();
    }

    public void close() {
        abd.close();
    }

    public long insert(Jugador objeto) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaJugadorV2.FNAC, objeto.getFnac());
        valores.put(Contrato.TablaJugadorV2.TELEFONO, objeto.getTelefono());
        valores.put(Contrato.TablaJugadorV2.NOMBRE, objeto.getNombre());
        long id = bd.insert(Contrato.TablaJugadorV2.TABLA, null, valores);
        return id;
    }

    public int delete(Jugador objeto) {
        String condicion = Contrato.TablaJugadorV2._ID + " = ?";
        String[] argumentos = {objeto.getId() + ""};
        int cuenta = bd.delete(Contrato.TablaJugadorV2.TABLA, condicion, argumentos);
        return cuenta;
    }

    public int deleteAll(Jugador objeto) {
        String condicion = Contrato.TablaJugadorV2._ID + " = ?";
        String[] argumentos = {objeto.getId() + ""};
        int cuenta = bd.delete(Contrato.TablaJugadorV2.TABLA, condicion, argumentos);
        condicion = Contrato.TablaPartido.ID_JUGADOR + " = ?";
        bd.delete(Contrato.TablaPartido.TABLA, condicion, argumentos);
        return cuenta;
    }

    public int deleteV2(Jugador objeto) {
        String condicion = Contrato.TablaJugadorV2.NOMBRE + " = ?";
        String[] argumentos = {objeto.getNombre()};
        int cuenta = bd.delete(Contrato.TablaJugadorV2.TABLA, condicion, argumentos);
        return cuenta;
    }

    public int update(Jugador objeto) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaJugadorV2.NOMBRE, objeto.getNombre());
        valores.put(Contrato.TablaJugadorV2.TELEFONO, objeto.getTelefono());
        valores.put(Contrato.TablaJugadorV2.FNAC, objeto.getFnac());
        String condicion = Contrato.TablaJugadorV2._ID + " = ?";
        String[] argumentos = {objeto.getId() + ""};
        int cuenta = bd.update(Contrato.TablaJugadorV2.TABLA, valores, condicion, argumentos);
        return cuenta;
    }

    public List<Jugador> select(String condicion, String[] parametros, String order) {
        List<Jugador> alj = new ArrayList<Jugador>();
        Cursor cursor = bd.query(Contrato.TablaJugadorV2.TABLA, null,
                condicion, parametros, null, null, order);
        cursor.moveToFirst();
        Jugador objeto;
        while (!cursor.isAfterLast()) {
            objeto = getRow(cursor);
            alj.add(objeto);
            cursor.moveToNext();
        }
        cursor.close();
        return alj;
    }

    public static Jugador getRow(Cursor c) {
        Jugador objeto = new Jugador();
        objeto.setId(c.getLong(0));
        objeto.setNombre(c.getString(1));
        objeto.setTelefono(c.getString(2));
        objeto.setFnac(c.getString(3));
        return objeto;
    }

    public Jugador getRow(long id) {
        List<Jugador> alj = select(Contrato.TablaJugadorV2._ID + " = ?", new String[]{id + ""}, null);
        if (!alj.isEmpty()) {
            alj.get(0);
        }
        return null;
    }

    public Cursor getCursor(String condicion, String[] parametros, String order) {
        Cursor cursor = bd.query(
                Contrato.TablaJugadorV2.TABLA, null, condicion, parametros, null, null, order);
        return cursor;
    }

    public Cursor getCursorFinal(){
        String tablaJugador = Contrato.TablaJugadorV2.TABLA;
        String tablaPartido = Contrato.TablaPartido.TABLA;
        String jugadorid = Contrato.TablaJugadorV2.TABLA + "." + Contrato.TablaJugadorV2._ID;
        String jugadoridpartido = Contrato.TablaPartido.TABLA + "." + Contrato.TablaPartido.ID_JUGADOR;

        String consulta = "select " + jugadorid +
                ", " + Contrato.TablaJugadorV2.NOMBRE +
                ", " + Contrato.TablaJugadorV2.TELEFONO +
                ", " + Contrato.TablaJugadorV2.FNAC +
                ", avg(" + Contrato.TablaPartido.VALORACION +
                ") from " + tablaJugador +
                " inner join " + tablaPartido +
                " on " + jugadorid + " = " + jugadoridpartido +
                " group by " + jugadorid;
        Cursor c = bd.rawQuery (consulta, null);
        return c;
    }

    public Cursor getCursor() {
        return getCursor(null, null, null);
    }

    public List<Jugador> select() {
        return select(null, null, null);
    }
}
