package mg.tife.topo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import mg.tife.topo.R;
import mg.tife.topo.data.DB;

public class RecordItemActivity extends AppCompatActivity {
    DB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = DB.getInstance(getApplicationContext());
        setContentView(R.layout.activity_record_item);
        findViewById(R.id.btnSaveRecord).setOnClickListener((e)->{
            EditText ltx = (EditText) findViewById(R.id.editTextLTX);
            String ltxTxt  = ltx.getText().toString();
            EditText lotissement = (EditText) findViewById(R.id.editTextLotissement);
            String lotTxt = lotissement.getText().toString();
            Long idRecord = db.createRecord(ltxTxt,lotTxt,db.userConnecte.getUserId());
            finish();
        });
    }
}