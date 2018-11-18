package reclamaciones.libro.com.libroreclamaciones.data.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BranchOfficeList {

    @SerializedName("sucursales_cercanas")
    @Expose
    private List<BranchOffice> sucursalesCercanas = null;

    public List<BranchOffice> getSucursalesCercanas() {
        return sucursalesCercanas;
    }

    public void setSucursalesCercanas(List<BranchOffice> sucursalesCercanas) {
        this.sucursalesCercanas = sucursalesCercanas;
    }

}
