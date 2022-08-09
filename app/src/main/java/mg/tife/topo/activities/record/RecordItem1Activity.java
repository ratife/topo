package mg.tife.topo.activities.record;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import mg.tife.topo.R;
import mg.tife.topo.data.DB;
import mg.tife.topo.data.model.Record;
import mg.tife.topo.util.Utils;

public class RecordItem1Activity extends AppCompatActivity {
    public static final Integer RecordAudioRequestCode = 1;
    DB db;
    //private EditText editTextLtx;
    //private EditText editTextLotissement;
    private EditText editTextTypeTravaux;
    private EditText editTextAdress;
    private EditText editTextDate;
    private EditText editTextTN;
    private EditText editTextParcelle;
    private EditText editTextProprietaire;
    private Button btnSauvegarde;
    private Long record_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_item1);
        //editTextLtx = (EditText) findViewById(R.id.editTextLTX);
        //editTextLotissement = (EditText) findViewById(R.id.editTextLotissement);
        editTextTypeTravaux = (EditText) findViewById(R.id.editTextTypeTravaux);
        editTextAdress = (EditText) findViewById(R.id.editTextAdressTerrain);
        editTextDate = (EditText) findViewById(R.id.editTextDate);
        editTextTN = (EditText) findViewById(R.id.editTextTN);
        editTextParcelle = (EditText) findViewById(R.id.editTextParcelle);
        editTextProprietaire = (EditText) findViewById(R.id.editTextProprietaire);
        btnSauvegarde = (Button) findViewById(R.id.btnSaveRecord);

        db = DB.getInstance(getApplicationContext());
        db.setCurrentRecordItemId(null);

        record_id = getIntent().getLongExtra(DB.COLUMN_ID,0);

        if(record_id>0){
            Record record = db.getRecord(record_id);

            //editTextLtx.setText(record.getLtx());
            // editTextLotissement.setText(record.getLotissement());
            editTextTypeTravaux.setText(record.getTypeTravaux());

            editTextAdress.setText(record.getAdressTerrain());
            editTextDate.setText(record.getDate());
            editTextTN.setText(record.getTn());
            editTextParcelle.setText(record.getParcelle());
            editTextProprietaire.setText(record.getProprietaire());
        }

        findViewById(R.id.btnSaveRecord).setOnClickListener((e)->{
            //String ltxTxt  = editTextLtx.getText().toString();
            //String lotTxt = editTextLotissement.getText().toString();
            String typeTravaux = editTextTypeTravaux.getText().toString();
            String adress = editTextAdress.getText().toString();
            String date = editTextDate.getText().toString();
            String tn = editTextTN.getText().toString();
            String parcelle = editTextParcelle.getText().toString();
            String proprietaire = editTextProprietaire.getText().toString();

            //if(!FormUtil.testNotVide(editTextLtx,"LTX"))return;
            //if(!FormUtil.testNotVide(editTextLotissement,"lotissement"))return;
            if(!Utils.testNotVide(editTextTypeTravaux,"type de travaux"))return;
            if(!Utils.testNotVide(editTextAdress,"Localisation terrain"))return;
            if(!Utils.testNotVide(editTextDate,"date"))return;
            if(!Utils.testNotVide(editTextTN,"TN"))return;
            if(!Utils.testNotVide(editTextParcelle,"parcelle"))return;
            if(!Utils.testNotVide(editTextProprietaire,"Proprietaire"))return;
            Record record;
            if(record_id>0){
                record = db.getRecord(record_id);
            }
            else{
                record = new Record();
            }
            //record.setLtx(ltxTxt);
            // record.setLotissement(lotTxt);
            record.setTypeTravaux(typeTravaux);
            record.setDate(date);
            record.setProprietaire(proprietaire);
            record.setTn(tn);
            record.setAdressTerrain(adress);
            record.setParcelle(parcelle);

            if(record_id>0){
                db.updateRecord(record);
            }
            else {
                record_id = db.createRecord(record);
            }

            Intent intent = new Intent(this, RecordItem2Activity.class);
            intent.putExtra(DB.COLUMN_ID, record_id);
            startActivity(intent);
            finish();
        });
    }
}