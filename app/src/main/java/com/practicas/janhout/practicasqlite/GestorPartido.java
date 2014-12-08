package com.practicas.janhout.practicasqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class GestorPartido {

    private Ayudante abd;
    private SQLiteDatabase bd;

    public GestorPartido(Context c) {
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

    public long insert(Partido objeto) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaPartido.ID_JUGADOR, objeto.getIdJugador());
        valores.put(Contrato.TablaPartido.CONTRINCANTE, objeto.getContrincante());
        valores.put(Contrato.TablaPartido.VALORACION, objeto.getValoracion());
        try {
            long id = bd.insert(Contrato.TablaPartido.TABLA, null, valores);
            return id;
        } catch (SQLiteConstraintException e) {
        } catch (SQLException e) {
        }
        return -1;
    }

    public int delete(Partido objeto) {
        String condicion = Contrato.TablaPartido._ID + " = ?";
        String[] argumentos = {objeto.getId() + ""};
        int cuenta = bd.delete(Contrato.TablaPartido.TABLA, condicion, argumentos);
        return cuenta;
    }

    public int deleteV2(Partido objeto) {
        String condicion = Contrato.TablaPartido.CONTRINCANTE + " = ?";
        String[] argumentos = {objeto.getContrincante()};
        int cuenta = bd.delete(Contrato.TablaPartido.TABLA, condicion, argumentos);
        return cuenta;
    }

    public int update(Partido objeto) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaPartido.ID_JUGADOR, objeto.getIdJugador());
        valores.put(Contrato.TablaPartido.CONTRINCANTE, objeto.getContrincante());
        valores.put(Contrato.TablaPartido.VALORACION, objeto.getValoracion());
        String condicion = Contrato.TablaPartido._ID + " = ?";
        String[] argumentos = {objeto.getId() + ""};
        int cuenta = bd.update(Contrato.TablaPartido.TABLA, valores, condicion, argumentos);
        return cuenta;
    }

    public List<Partido> select(String condicion, String[] parametros, String order) {
        List<Partido> alp = new ArrayList<Partido>();
        Cursor cursor = bd.query(Contrato.TablaPartido.TABLA, null,
                condicion, parametros, null, null, order);
        cursor.moveToFirst();
        Partido objeto;
        while (!cursor.isAfterLast()) {
            objeto = getRow(cursor);
            alp.add(objeto);
            cursor.moveToNext();
        }
        cursor.close();
        return alp;
    }

    public static Partido getRow(Cursor c) {
        Partido objeto = new Partido();
        objeto.setId(c.getLong(0));
        objeto.setIdJugador(c.getLong(1));
        objeto.setContrincante(c.getString(2));
        objeto.setValoracion(c.getInt(3));
        return objeto;
    }

    public Partido getRow(long id) {
        List<Partido> alp = select(Contrato.TablaPartido._ID + " = ?", new String[]{id + ""}, null);
        if (!alp.isEmpty()) {
            alp.get(0);
        }
        return null;
    }

    public Cursor getCursor(String condicion, String[] parametros, String order) {
        Cursor cursor = bd.query(
                Contrato.TablaPartido.TABLA, null, condicion, parametros, null, null, order);
        return cursor;
    }

    public Cursor getCursor() {
        return getCursor(null, null, null);
    }

    public Cursor getCursorFinal(){
        String tablaJugador = Contrato.TablaJugadorV2.TABLA;
        String tablaPartido = Contrato.TablaPartido.TABLA;
        String jugadorNombre = Contrato.TablaJugadorV2.TABLA + "." + Contrato.TablaJugadorV2.NOMBRE;
        String jugadorid = Contrato.TablaJugadorV2.TABLA + "." + Contrato.TablaJugadorV2._ID;
        String jugadoridpartido = Contrato.TablaPartido.TABLA + "." + Contrato.TablaPartido.ID_JUGADOR;
        String idpartido = Contrato.TablaPartido.TABLA + "." + Contrato.TablaPartido._ID;

        String consulta = "select " + idpartido +
                ", " + jugadorNombre +
                ", " + Contrato.TablaPartido.CONTRINCANTE +
                ", " + Contrato.TablaPartido.VALORACION +
                " from " + tablaPartido +
                " left outer join " + tablaJugador +
                " on " + jugadorid + " = " + jugadoridpartido;
        Cursor c = bd.rawQuery(consulta, null);
        return c;
    }

    public List<Partido> select() {
        return select(null, null, null);
    }
}
