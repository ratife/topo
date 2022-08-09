package mg.tife.topo.activities.record;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import mg.tife.topo.R;
import mg.tife.topo.data.DB;
import mg.tife.topo.data.model.Record;
import mg.tife.topo.util.Utils;

public class RecordItem2Activity extends AppCompatActivity {
    public static final Integer RecordAudioRequestCode = 1;
    DB db;
    private EditText editTextX;
    private EditText editTextY;
    private EditText editTextZ;
    private EditText editTextVo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_record_item2);

        editTextX = (EditText) findViewById(R.id.editTextX);
        editTextY = (EditText) findViewById(R.id.editTextY);
        editTextZ = (EditText) findViewById(R.id.editTextZ);
        editTextVo = (EditText) findViewById(R.id.editTextVo);

        db = DB.getInstance(getApplicationContext());

        Long record_id = getIntent().getLongExtra(DB.COLUMN_ID,0);

        if(record_id>0){
            Record record = db.getRecord(record_id);
            editTextX.setText(record.getXo()+"");
            editTextY.setText(record.getYo()+"");
            editTextZ.setText(record.getZo()+"");
            editTextVo.setText(record.getVo()+"");
        }


        findViewById(R.id.btnDescription).setOnClickListener((e)->{
            Intent intent = new Intent(this, RecordItem1Activity.class);
            intent.putExtra(DB.COLUMN_ID, record_id);
            startActivity(intent);
            finish();
                });

        findViewById(R.id.buttonStartLecture).setOnClickListener((e)->{

            if(!Utils.testNotVide(editTextX,"Xo"))return;
            if(!Utils.testNotVide(editTextY,"Yo"))return;
            if(!Utils.testNotVide(editTextVo,"Vo"))return;

            String xo  = editTextX.getText().toString();
            String yo = editTextY.getText().toString();
            String zo = editTextZ.getText().toString();
            String vo = editTextVo.getText().toString();
            if("".equals(zo))
                zo = "0.0";

            Record record = db.getRecord(record_id);
            record.setXo(Double.valueOf(xo));
            record.setYo(Double.valueOf(yo));
            record.setZo(Double.valueOf(zo));
            record.setVo(Double.valueOf(vo));
            if(record_id>0){
                db.updateRecord(record);
            }
            Intent intent = new Intent(this, RecordItem3Activity.class);
            intent.putExtra(DB.COLUMN_ID, record.getId());
            startActivity(intent);
            finish();
        });
    }
}