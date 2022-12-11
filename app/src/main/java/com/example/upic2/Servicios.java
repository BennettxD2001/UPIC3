package com.example.upic2;

public class Servicios {
    String ID,Nombre,Servicios;
    Double Latitud,Longitud;

    public Servicios() {
    }

    public Servicios(String ID, String nombre, String servicios, Double latitud, Double longitud) {
        this.ID = ID;
        Nombre = nombre;
        Servicios = servicios;
        Latitud = latitud;
        Longitud = longitud;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getServicios() {
        return Servicios;
    }

    public void setServicios(String servicios) {
        Servicios = servicios;
    }

    public Double getLatitud() {
        return Latitud;
    }

    public void setLatitud(Double latitud) {
        Latitud = latitud;
    }

    public Double getLongitud() {
        return Longitud;
    }

    public void setLongitud(Double longitud) {
        Longitud = longitud;
    }
}

