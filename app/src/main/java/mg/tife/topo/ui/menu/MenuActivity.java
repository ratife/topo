package mg.tife.topo.ui.menu;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import mg.tife.topo.data.DB;
import mg.tife.topo.databinding.ActivityMenuBinding;

import mg.tife.topo.R;

public class MenuActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMenuBinding binding;
    DB db;
    Button btnUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = DB.getInstance(getApplicationContext());
        super.onCreate(savedInstanceState);

        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        btnUser = (Button) findViewById(R.id.btnUser);
        String role = getIntent().getStringExtra("role");

        updateBtn();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateBtn();
    }

    @Override
    public boolean onSupportNavigateUp() {
        updateBtn();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void updateBtn(){
        String role = db.userConnecte.getRole();
        if("admin".equals(role)){
            btnUser.setVisibility(View.VISIBLE);
            btnUser.setHeight(100);
        }
        if("user".equals(role)){
            btnUser.setHeight(0);
            btnUser.setVisibility(View.GONE);
        }
    }
}