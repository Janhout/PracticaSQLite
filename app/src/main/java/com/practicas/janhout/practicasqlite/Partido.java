package com.practicas.janhout.practicasqlite;

import java.io.Serializable;

public class Partido implements Serializable, Comparable<Partido>{

    private long id;
    private long idJugador;
    private String contrincante;
    private int valoracion;

    public Partido() {
        this(0,0,"",0);
    }

    public Partido(long id, long idJugador, String contrincante, int valoracion) {
        this.id = id;
        this.idJugador = idJugador;
        this.contrincante = contrincante;
        this.valoracion = valoracion;
    }

    public Partido(long idJugador, String contrincante, String valoracion) {
        this.id = 0;
        this.idJugador = idJugador;
        this.contrincante = contrincante;
        try{
            this.valoracion = Integer.parseInt(valoracion);
        }catch (NumberFormatException e){
            this.valoracion = 0;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(long idJugador) {
        this.idJugador = idJugador;
    }

    public String getContrincante() {
        return contrincante;
    }

    public void setContrincante(String contrincante) {
        this.contrincante = contrincante;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Partido partido = (Partido) o;

        if (idJugador != partido.idJugador) return false;
        if (contrincante != null ? !contrincante.equals(partido.contrincante) : partido.contrincante != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idJugador ^ (idJugador >>> 32));
        result = 31 * result + (contrincante != null ? contrincante.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Partido another) {
        if(this.contrincante.compareTo(another.contrincante)!=0){
            return this.contrincante.compareTo(another.contrincante);
        }else{
            return (int)(this.idJugador - another.idJugador);
        }
    }

    @Override
    public String toString() {
        return "Partido{" +
                "id=" + id +
                ", idJugador=" + idJugador +
                ", contrincante='" + contrincante + '\'' +
                ", valoracion=" + valoracion +
                '}';
    }
}
