package com.practicas.janhout.practicasqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Ayudante extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "futbolistas.db";
    public static final int DATABASE_VERSION = 5;

    public Ayudante(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;
        sql = "create table " + Contrato.TablaJugador.TABLA +
                " (" + Contrato.TablaJugadorV2._ID +
                " integer primary key autoincrement, " +
                Contrato.TablaJugadorV2.NOMBRE + " text unique, " +
                Contrato.TablaJugadorV2.TELEFONO + " text, " +
                Contrato.TablaJugadorV2.FNAC + " text)";
        db.execSQL(sql);

        sql = "create table " + Contrato.TablaPartido.TABLA +
                " (" + Contrato.TablaPartido._ID +
                " integer primary key autoincrement, " +
                Contrato.TablaPartido.ID_JUGADOR + " integer, " +
                Contrato.TablaPartido.CONTRINCANTE + " text, " +
                Contrato.TablaPartido.VALORACION + " integer, " +
                "unique(" + Contrato.TablaPartido.ID_JUGADOR + ", " + Contrato.TablaPartido.CONTRINCANTE + "))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //1º crear tablas de respaldo(identicas) con nombre diferenete y sin autoincrement
        String sql = "create table respaldo" + Contrato.TablaJugador.TABLA +
                " (" + Contrato.TablaJugador._ID +
                " integer primary key, " +
                Contrato.TablaJugador.NOMBRE + " text, " +
                Contrato.TablaJugador.TELEFONO + " text, " +
                Contrato.TablaJugador.VALORACION + " integer, " +
                Contrato.TablaJugador.FNAC + " text)";
        db.execSQL(sql);

        //2º copiar datos a esas tablas
        sql = "insert into respaldo" + Contrato.TablaJugador.TABLA + " select * from " + Contrato.TablaJugador.TABLA;
        db.execSQL(sql);

        //3º borro las tablas originales
        sql = "drop table " + Contrato.TablaJugador.TABLA;
        db.execSQL(sql);

        //4º creo las tablas nuevas
        onCreate(db);

        //5º copio los datos a las tablas de respaldo
        sql = "insert into " + Contrato.TablaJugadorV2.TABLA + " select " + Contrato.TablaJugadorV2._ID +
                ", " + Contrato.TablaJugadorV2.NOMBRE +
                ", " + Contrato.TablaJugadorV2.TELEFONO +
                ", " + Contrato.TablaJugadorV2.FNAC + " from respaldo" + Contrato.TablaJugadorV2.TABLA;
        db.execSQL(sql);

        sql = "insert into " + Contrato.TablaPartido.TABLA + "(" + Contrato.TablaPartido.ID_JUGADOR +
                ", " + Contrato.TablaPartido.VALORACION +
                ") select " + Contrato.TablaJugador._ID +
                ", " + Contrato.TablaJugador.VALORACION + " from respaldo" + Contrato.TablaJugador.TABLA;
        db.execSQL(sql);
        sql = "update " + Contrato.TablaPartido.TABLA + " set " + Contrato.TablaPartido.CONTRINCANTE +
                " = 'ValoracionInicial'";
        db.execSQL(sql);

        //6º borro las tablas de respaldo
        sql = "drop table respaldo" + Contrato.TablaJugador.TABLA;
        db.execSQL(sql);
    }
}
