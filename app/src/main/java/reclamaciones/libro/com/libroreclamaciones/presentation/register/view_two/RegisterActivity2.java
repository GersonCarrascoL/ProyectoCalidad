package reclamaciones.libro.com.libroreclamaciones.presentation.register.view_two;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindFont;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import reclamaciones.libro.com.libroreclamaciones.R;
import reclamaciones.libro.com.libroreclamaciones.presentation.register.view_three.RegisterActivity3;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity2 extends AppCompatActivity implements RegisterContract2.View {

    @BindView(R.id.label_register_email)
    TextInputLayout label_register_email;
    @BindView(R.id.register_email)
    TextInputEditText register_email;
    @BindView(R.id.spinner_district)
    MaterialSpinner spinner_district;
    @BindView(R.id.spinner_ocupation)
    MaterialSpinner spinner_ocupation;
    @BindView(R.id.spinner_grade_level)
    MaterialSpinner spinner_grade_level;
    @BindView(R.id.btn_next)
    MaterialButton btn_next;

    //VARIABLES
    private String register_name;
    private String register_last_name;
    private String register_ocupation;
    private String register_scholar_grade;
    private int register_district;
    private Map<Integer, String> map;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        ButterKnife.bind(this);
        Intent intent = getIntent();
        register_name = intent.getStringExtra("register_name");
        register_last_name = intent.getStringExtra("register_last_name");
        Log.d("Registro Nomnbre",""+register_name);
        Log.d("Registro Nomnbre",""+register_last_name);

        map = new HashMap<Integer, String>();
        map.put(0, "Distrito");
        map.put(1, "Lima");
        map.put(2, "Ancón");
        map.put(3, "Ate");
        map.put(4, "Barranco");
        map.put(5, "Breña");
        map.put(6, "Carabayllo");
        map.put(7, "Chaclacayo");
        map.put(8, "Chorrillos");
        map.put(9, "Cieneguilla");
        map.put(10, "Comas");
        map.put(11, "El Agustino");
        map.put(12, "Independencia");
        map.put(13, "Jesús María");
        map.put(14, "La Molina");
        map.put(15, "La Victoria");
        map.put(16, "Lince");
        map.put(17, "Los Olivos");
        map.put(18, "Lurigancho");
        map.put(19, "Lurin");
        map.put(20, "Magdalena del Mar");
        map.put(21, "Pueblo Libre");
        map.put(22, "Miraflores");
        map.put(23, "Pachacamac");
        map.put(24, "Pucusana");
        map.put(25, "Puente Piedra");
        map.put(26, "Punta Hermosa");
        map.put(27, "Punta Negra");
        map.put(28, "Rímac");
        map.put(29, "San Bartolo");
        map.put(30, "San Borja");
        map.put(31, "San Isidro");
        map.put(32, "San Juan de Lurigancho");
        map.put(33, "San Juan de Miraflores");
        map.put(34, "San Luis");
        map.put(35, "San Martín de Porres");
        map.put(36, "San Miguel");
        map.put(37, "Santa Anita");
        map.put(38, "Santa María del Mar");
        map.put(39, "Santa Rosa");
        map.put(40, "Santiago de Surco");
        map.put(41, "Surquillo");
        map.put(42, "Villa El Salvador");
        map.put(43, "Villa María del Triunfo");

        addSpinnerDistrict();
        addSpinnerGradeLevel();
        addSpinnerOcupation();

        spinner_district.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if(!item.toString().equals("Distrito")){
                    for (int i = 0; i < map.size(); i++) {
                        if(map.get(i+1).equals(item.toString())){
                            register_district = i+1;
                            break;
                        }
                    }
                }else{
                    flag = false;
                }
            }
        });

        spinner_ocupation.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (!item.toString().equals("Ocupación")){
                    register_ocupation = item.toString();
                }else{
                    flag = false;
                }
            }
        });

        spinner_grade_level.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if(!item.toString().equals("Grado educativo")) {
                    register_scholar_grade = item.toString();
                }else{
                    flag=false;
                }
            }
        });

    }

    public void registerNextStep(){
        spinner_district.setError(null);
        spinner_grade_level.setError(null);
        spinner_ocupation.setError(null);
        label_register_email.setError(null);
        flag = true;

        validateSpinnerDistrict();
        validateSpinnerGradeLevel();
        validateSpinnerOcupation();
        validateEmailEditText();

        if (flag){
            launchNextLayout();
        }else{
            Snackbar.make(getWindow().getDecorView().getRootView(),getResources().getString(R.string.validate_form),Snackbar.LENGTH_LONG).show();
        }
    }

    public void addSpinnerDistrict() {

        String[] spinnerDistrict = new String[map.size()];

        for (int i = 0; i < map.size(); i++) {
            spinnerDistrict[i] = map.get(i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnerDistrict
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_district.setAdapter(adapter);
    }

    public void addSpinnerOcupation() {
        String[] arrayOcupation = {
                "Ocupación",
                "Abogado",
                "Empresario",
                "Estudiante",
                "Otros"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayOcupation
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_ocupation.setAdapter(adapter);
    }

    public void addSpinnerGradeLevel() {
        String[] arrayGradeLevel = {
                "Grado educativo",
                "Primaria",
                "Secundaria",
                "Tecnico",
                "Universitario",
                "Otros"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayGradeLevel
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_grade_level.setAdapter(adapter);
    }

    public void validateSpinnerDistrict(){
        if (spinner_district.getSelectedIndex() == 0){
            Log.d("SELECCIONADO DISTRITO", spinner_district.getSelectedIndex()+"");
            spinner_district.setError(getResources().getString(R.string.required));
            flag = false;
        }
    }

    public void validateSpinnerOcupation(){
        if (spinner_ocupation.getSelectedIndex() == 0){
            Log.d("SELECCIONADO ocupacion", spinner_ocupation.getSelectedIndex()+"");
            spinner_ocupation.setError(getResources().getString(R.string.required));
            flag = false;
        }
    }

    public void validateSpinnerGradeLevel(){
        if (spinner_grade_level.getSelectedIndex() == 0){
            Log.d("SELECCIONADO GRADO", spinner_grade_level.getSelectedIndex()+"");
            spinner_grade_level.setError(getResources().getString(R.string.required));
            flag = false;
        }
    }

    public boolean validateEmail(String email){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void validateEmailEditText() {
        if (!validateEmail(register_email.getText().toString())) {
            register_email.requestFocus();
            label_register_email.setErrorEnabled(true);
            label_register_email.setError(getResources().getString(R.string.email_required));
            flag = false;
        }
    }
    @Override
    public void launchNextLayout() {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity3.class);

        intent.putExtra("register_name", ""+register_name);
        intent.putExtra("register_last_name", ""+register_last_name);
        intent.putExtra("register_email", ""+register_email.getText());
        intent.putExtra("spinner_ocupation",""+register_ocupation);
        intent.putExtra("spinner_grade_level",""+register_scholar_grade);
        intent.putExtra("spinner_district",""+register_district);

        startActivity(intent);
    }


    @OnClick(R.id.btn_next)
    public void OnViewClicked(View view) {
        registerNextStep();
    }
}
