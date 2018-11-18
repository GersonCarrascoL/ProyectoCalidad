package reclamaciones.libro.com.libroreclamaciones.presentation.claim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;
import reclamaciones.libro.com.libroreclamaciones.R;
import reclamaciones.libro.com.libroreclamaciones.presentation.login.LoginActivity;
import reclamaciones.libro.com.libroreclamaciones.presentation.register.view_three.RegisterActivity3;
import reclamaciones.libro.com.libroreclamaciones.presentation.register.view_three.RegisterPresenter3;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ClaimActivity extends AppCompatActivity implements ClaimContract.View,EasyPermissions.PermissionCallbacks {

    @BindView(R.id.photo_gallery)
    ImageView photo_gallery;
    @BindView(R.id.pick_photo)
    MaterialButton pick_photo;
    @BindView(R.id.label_claim_message)
    TextInputLayout label_claim_message;
    @BindView(R.id.claim_message)
    TextInputEditText claim_message;
    @BindView(R.id.label_claim_phone)
    TextInputLayout label_claim_phone;
    @BindView(R.id.claim_phone)
    TextInputEditText claim_phone;
    @BindView(R.id.send_claim)
    MaterialButton send_claim;

    private ClaimPresenter presenter;
    private Context context;
    private ProgressDialog mProgressDialog;
    private AlertDialog.Builder builder;
    private String register_name;
    private String register_last_name;
    private String register_email;
    private String register_ocupation;
    private String register_grade_level;
    private String register_district;
    private String register_gender;
    private String register_dni;
    private String register_password;
    private File fileImage;
    private String encodedImage;
    private boolean flag;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim);
        context = this;

        if (presenter == null){
            presenter = new ClaimPresenter(context);
        }
        ButterKnife.bind(this);

        Intent intent = getIntent();
        register_name = intent.getStringExtra("register_name");
        register_last_name = intent.getStringExtra("register_last_name");
        register_email = intent.getStringExtra("register_email");
        register_gender = intent.getStringExtra("register_gender");
        register_district = intent.getStringExtra("Register_district");
        register_ocupation = intent.getStringExtra("register_ocupation");
        register_grade_level= intent.getStringExtra("register_grade_level");
        register_dni = intent.getStringExtra("register_dni");
        register_password = intent.getStringExtra("register_password");

        mProgressDialog = new ProgressDialog(context,R.style.MyProgressDialogTheme);
        mProgressDialog.setMessage(getText(R.string.default_loading_text));
        mProgressDialog.setCancelable(false);

        builder = new AlertDialog.Builder(this);
        builder.setTitle("Aviso");
        builder.setMessage("Su reclamo ha sido enviado correctamente");
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        launchLoginActivity();
                        break;
                }
            }
        };
        builder.setPositiveButton("OK",dialogClickListener);

    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_IMAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, ClaimActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE && data != null){
            imageUri = data.getData();
            ContentResolver cr = this.getContentResolver();
            String mime = cr.getType(imageUri);
            Log.d("Path:",""+imageUri);
            String filePath = getRealPathFromURIPath(imageUri, ClaimActivity.this);
            Log.d("Path Real:",""+filePath);

            // Convert to base64 image
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
                byte[] b = baos.toByteArray();

                encodedImage = Base64.encodeToString(b,Base64.DEFAULT);
                Log.d("Imagen en base 64 ",""+encodedImage);

            } catch (IOException e) {
                e.printStackTrace();
            }

            //END CONVERT
            fileImage = new File(filePath);
            photo_gallery.setImageURI(imageUri);
        }
    }

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    private void registerNextStep(){
        label_claim_message.setErrorEnabled(false);
        label_claim_phone.setErrorEnabled(false);
        label_claim_phone.setError(null);
        label_claim_message.setError(null);
        flag = true;

        validateMessage();
        validatePhone();

        if (flag){
            getPresenter().register(fileImage,claim_message.getText().toString(),claim_phone.getText().toString(),register_name,register_last_name,register_email,register_gender,Integer.parseInt(register_district),register_ocupation,register_grade_level,register_dni,register_password);
        }else{
            Snackbar.make(getWindow().getDecorView().getRootView(),"Tiene campos sin completar",Snackbar.LENGTH_LONG).show();
        }
    }


    public void validateMessage(){
        if (claim_message.length()<=0){
            claim_message.requestFocus();
            label_claim_message.setErrorEnabled(true);
            label_claim_message.setError(getResources().getString(R.string.error_name));
            flag = false;
        }
    }

    public void validatePhone(){
        if (claim_phone.length()<=0){
            claim_phone.requestFocus();
            label_claim_phone.setErrorEnabled(true);
            label_claim_phone.setError(getResources().getString(R.string.error_name));
            flag = false;
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Snackbar.make(getWindow().getDecorView().getRootView(),getResources().getString(R.string.created_error),Snackbar.LENGTH_LONG);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().onViewAttach(ClaimActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideLoadingDialog();
        getPresenter().onViewDettach();
    }

    public ClaimPresenter getPresenter(){
        return this.presenter;
    }

    @Override
    public void showLoadingDialog() {
        mProgressDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        mProgressDialog.hide();
    }

    @Override
    public void showDialogSuccess() {
        builder.show();
    }

    @Override
    public void showSnackBarErrorCreated() {
        Snackbar.make(getWindow().getDecorView().getRootView(),getResources().getString(R.string.created_error),Snackbar.LENGTH_LONG);
    }

    @Override
    public void showConnectionError() {
        Snackbar.make(getWindow().getDecorView().getRootView(),getResources().getString(R.string.error_connect),Snackbar.LENGTH_LONG);
    }

    @Override
    public void launchLoginActivity(){
        Intent intent = new Intent(context,LoginActivity.class);
        startActivity(intent);
    }

    /*
OnClick
*/
    @OnClick({ R.id.send_claim, R.id.pick_photo})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.send_claim:
                registerNextStep();
                break;
            case R.id.pick_photo:
                openGallery();
                break;
        }
    }


}
