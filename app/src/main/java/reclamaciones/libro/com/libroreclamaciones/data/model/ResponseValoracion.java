package reclamaciones.libro.com.libroreclamaciones.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseValoracion {

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("valoracion")
    @Expose
    private Valoracion valoracion;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Valoracion getValoracion() {
        return valoracion;
    }

    public void setValoracion(Valoracion valoracion) {
        this.valoracion = valoracion;
    }

}