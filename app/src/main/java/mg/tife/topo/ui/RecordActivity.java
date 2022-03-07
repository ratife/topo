package mg.tife.topo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
        findViewById(R.id.imageButtonRecordSearch).setOnClickListener((e)->{
            findViewById(R.id.editTextTextRecordSearch).setVisibility(View.VISIBLE);
        });
        List<Record> records  = db.getListRecord();
        ListView listView = (ListView)findViewById(R.id.record_list_view);
        listView.setAdapter(new RecordAdapter(this,records));

        EditText seachTxt = findViewById(R.id.editTextTextRecordSearch);
        seachTxt.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String txt = seachTxt.getText().toString();
                List<Record> records  = db.findRecord(txt);
                ListView listView = (ListView)findViewById(R.id.record_list_view);
                listView.setAdapter(new RecordAdapter(getApplicationContext(),records));
            }
        });




    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Record> records  = db.getListRecord();
        ListView listView = (ListView)findViewById(R.id.record_list_view);
        listView.setAdapter(new RecordAdapter(this,records));
    }
}