package co.edu.unipanamericana.bogosafe.modelo;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

public class Zona {
    private String localidad;
    private String barrio;
    private String direccion;
    private String descripcion;
    private String suceso;
    private GeoPoint ubicacion;
    private Timestamp fecha;

    public Zona() {
    }

    public String getLocalidad() {
        return localidad;
    }

    public String getBarrio() {
        return barrio;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getSuceso() {
        return suceso;
    }

    public GeoPoint getUbicacion() {
        return ubicacion;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setSuceso(String suceso) {
        this.suceso = suceso;
    }

    public void setUbicacion(GeoPoint ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
}
