package mg.tife.topo.activities.user;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.List;

import mg.tife.topo.R;
import mg.tife.topo.activities.record.RecordItemActivity;
import mg.tife.topo.data.DB;
import mg.tife.topo.data.model.Record;
import mg.tife.topo.data.model.User;
import mg.tife.topo.ui.adapter.RecordAdapter;
import mg.tife.topo.ui.adapter.UserAdapter;

public class UserActivity extends AppCompatActivity {
    DB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        db = DB.getInstance(getApplicationContext());
        List<User> listUser  = db.getAllUsers();
        ListView listView = (ListView)findViewById(R.id.user_list_view);
        listView.setAdapter(new UserAdapter(this,listView));


    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Record> records  = db.getListRecord();
        ListView listView = (ListView)findViewById(R.id.user_list_view);
        listView.setAdapter(new UserAdapter(this,records));
    }
}