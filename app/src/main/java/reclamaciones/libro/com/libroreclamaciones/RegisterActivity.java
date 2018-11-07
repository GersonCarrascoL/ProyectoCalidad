package reclamaciones.libro.com.libroreclamaciones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

   // private Toolbar toolbar;
    private TextInputEditText et_nombre;
    private TextInputEditText et_apellido;
    private MaterialButton btn_siguiente1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

    /*    toolbar = findViewById(R.id.toolbar_general);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("REGISTRO"); */

        btn_siguiente1 = findViewById(R.id.siguiente1_btn);
        btn_siguiente1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btn_siguiente1 = new Intent(RegisterActivity.this, RegisterActivity2.class);
                startActivity(btn_siguiente1);
            }
        });

    }
}
