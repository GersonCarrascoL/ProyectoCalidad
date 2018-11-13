package reclamaciones.libro.com.libroreclamaciones.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("apellido")
    @Expose
    private String apellido;
    @SerializedName("correo")
    @Expose
    private String correo;
    @SerializedName("nombre_usuario")
    @Expose
    private String nombreUsuario;
    @SerializedName("dni")
    @Expose
    private String dni;
    @SerializedName("sexo")
    @Expose
    private String sexo;
    @SerializedName("distrito")
    @Expose
    private String distrito;
    @SerializedName("ocupacion")
    @Expose
    private String ocupacion;
    @SerializedName("grado_educativo")
    @Expose
    private String gradoEducativo;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getGradoEducativo() {
        return gradoEducativo;
    }

    public void setGradoEducativo(String gradoEducativo) {
        this.gradoEducativo = gradoEducativo;
    }

}