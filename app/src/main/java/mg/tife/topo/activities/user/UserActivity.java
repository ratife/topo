package mg.tife.topo.activities.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import mg.tife.topo.R;
import mg.tife.topo.data.DB;
import mg.tife.topo.data.model.User;
import mg.tife.topo.activities.user.adapter.UserAdapter;

public class UserActivity extends AppCompatActivity {
    DB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        db = DB.getInstance(getApplicationContext());
        List<User> listUser  = db.getAllUsers();
        ListView listView = (ListView)findViewById(R.id.user_list_view);
        listView.setAdapter(new UserAdapter(this,listUser));

        Button btnAddUser = findViewById(R.id.btnAddUser);
        btnAddUser.setOnClickListener((e)->{
            Intent intent = new Intent(this, UserFormActivity.class);
            this.startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<User> listUser  = db.getAllUsers();
        ListView listView = (ListView)findViewById(R.id.user_list_view);
        listView.setAdapter(new UserAdapter(this,listUser));
    }
}