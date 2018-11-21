package reclamaciones.libro.com.libroreclamaciones.data.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sucursal {

    @SerializedName("idSucursal")
    @Expose
    private String idSucursal;
    @SerializedName("nombreEmpresa")
    @Expose
    private String nombreEmpresa;
    @SerializedName("direccion")
    @Expose
    private String direccion;
    @SerializedName("puntaje_promedio")
    @Expose
    private Float puntajePromedio;
    @SerializedName("comentario_usuario")
    @Expose
    private List<ComentarioUsuario> comentarioUsuario = null;
    @SerializedName("comentarios")
    @Expose
    private List<Comentario> comentarios = null;

    public String getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(String idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Float getPuntajePromedio() {
        return puntajePromedio;
    }

    public void setPuntajePromedio(Float puntajePromedio) {
        this.puntajePromedio = puntajePromedio;
    }

    public List<ComentarioUsuario> getComentarioUsuario() {
        return comentarioUsuario;
    }

    public void setComentarioUsuario(List<ComentarioUsuario> comentarioUsuario) {
        this.comentarioUsuario = comentarioUsuario;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
}