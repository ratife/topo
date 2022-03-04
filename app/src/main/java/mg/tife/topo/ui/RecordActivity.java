package mg.tife.topo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import mg.tife.topo.R;
import mg.tife.topo.data.DB;
import mg.tife.topo.data.model.Record;
import mg.tife.topo.ui.adapter.RecordAdapter;

public class RecordActivity extends AppCompatActivity {
    DB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        db = DB.getInstance(getApplicationContext());
        findViewById(R.id.btnAddRecord).setOnClickListener((e)->{
            Intent intent = new Intent(this, RecordItemActivity.class);
            startActivity(intent);
        });
        List<Record> records  = db.getListRecord();
        ListView listView = (ListView)findViewById(R.id.record_list_view);
        listView.setAdapter(new RecordAdapter(this,records));
    }
}