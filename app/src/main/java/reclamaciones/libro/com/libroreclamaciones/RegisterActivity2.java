package reclamaciones.libro.com.libroreclamaciones;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class RegisterActivity2 extends AppCompatActivity {
    private MaterialButton btn_siguiente2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);


        MaterialSpinner spinner_distrito = (MaterialSpinner) findViewById(R.id.spinner_distrito);
        spinner_distrito.setItems("Ate", "La Molina", "Surco", "Miraflores", "Pueblo Libre", "Callao", "San Juan de Lurigancho", "Santa Anita");
        spinner_distrito.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });


        MaterialSpinner spinner_ocupacion = (MaterialSpinner) findViewById(R.id.spinner_ocupacion);
        spinner_ocupacion.setItems("Abogado", "Ingeniero", "Medico", "Vendedor", "Docente", "Administrador", "Futbolista", "Otros");
        spinner_ocupacion.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });

        MaterialSpinner spinner_gradoEducativo = (MaterialSpinner) findViewById(R.id.spinner_gradoEducativo);
        spinner_gradoEducativo.setItems("Primaria Incompleta", "Primaria Completa", "Secundaria Completa", "Educacion superior incompleta", "Bachiller", "Titulado", "Posgrado", "Otros");
        spinner_gradoEducativo.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });


        btn_siguiente2 = findViewById(R.id.siguiente2_btn);
        btn_siguiente2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btn_siguiente1 = new Intent(RegisterActivity2.this, RegisterActivity3.class);
                startActivity(btn_siguiente1);
            }
        });
    }
}
