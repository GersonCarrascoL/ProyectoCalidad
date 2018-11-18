package reclamaciones.libro.com.libroreclamaciones.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BranchOffice {

    @SerializedName("idSucursal")
    @Expose
    private Integer idSucursal;
    @SerializedName("nombreEmpresa")
    @Expose
    private String nombreEmpresa;
    @SerializedName("nombreDistrito")
    @Expose
    private String nombreDistrito;
    @SerializedName("direccion")
    @Expose
    private String direccion;
    @SerializedName("latitud")
    @Expose
    private Double latitud;
    @SerializedName("longitud")
    @Expose
    private Double longitud;

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getNombreDistrito() {
        return nombreDistrito;
    }

    public void setNombreDistrito(String nombreDistrito) {
        this.nombreDistrito = nombreDistrito;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

}