package reclamaciones.libro.com.libroreclamaciones.presentation.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import reclamaciones.libro.com.libroreclamaciones.data.repository.local.SessionManager;
import reclamaciones.libro.com.libroreclamaciones.presentation.main.maps.MapsFragment;

import reclamaciones.libro.com.libroreclamaciones.presentation.main.profile.ProfileFragment;
import reclamaciones.libro.com.libroreclamaciones.R;
import reclamaciones.libro.com.libroreclamaciones.presentation.login.LoginActivity;


import com.google.android.material.navigation.NavigationView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;


public class MainNavigationActivity extends AppCompatActivity implements MainNavigationContract{

    private DrawerLayout drawerLayout;
    private boolean searchMapFragment = false;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    private MaterialSearchView searchView;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_context);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.navigation_view);

        searchView = findViewById(R.id.search_view);

        if( navigationView != null ){
            setupDrawerContent(navigationView);
            mDrawerToggle = new ActionBarDrawerToggle(
                    this,                    //* host Activity *//*
                    drawerLayout,                    //* DrawerLayout object *//*
                    toolbar,
                    R.string.app_name,  //* "open drawer" description for accessibility *//*
                    R.string.app_name  //* "close drawer" description for accessibility *//*
            );
            mDrawerToggle.syncState();
            drawerLayout.setDrawerListener(mDrawerToggle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            mDrawerToggle.syncState();
        }

        setFragment(0);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }



        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_toolbar, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent( NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener(){
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.nav_map:
                                menuItem.setChecked(true);
                                setFragment(0);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.nav_profile:
                                menuItem.setChecked(true);
                                setFragment(1);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.nav_logout:
                                menuItem.setChecked(true);
                                setFragment(2);
                                drawerLayout.closeDrawer(GravityCompat.START);
                        }
                        return true;
                    }
                }
        );
    }


    private void setFragment(int position){
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        switch (position){
            case 0:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                MapsFragment mapsFragment = new MapsFragment();
                fragmentTransaction.replace(R.id.main_content,mapsFragment);
                fragmentTransaction.commit();
                getSupportActionBar().setTitle("Mapa");
                break;

            case 1:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                ProfileFragment profileFragment = new ProfileFragment();
                fragmentTransaction.replace(R.id.main_content,profileFragment);
                fragmentTransaction.commit();
                getSupportActionBar().setTitle("Perfil");
                break;
            case 2:
                startLogOutActivity();
                break;
        }
    }

    @Override
    public void startLogOutActivity() {
        session = SessionManager.getInstance(getApplicationContext());
        session.logOut();
        Intent loginIntent = new Intent().setClass(MainNavigationActivity.this,LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(loginIntent);
    }
}
