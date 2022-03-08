package mg.tife.topo.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import mg.tife.topo.R;
import mg.tife.topo.data.DB;
import mg.tife.topo.data.model.Record;
import mg.tife.topo.data.model.RecordItem;
import mg.tife.topo.ui.adapter.RecordAdapter;
import mg.tife.topo.ui.adapter.RecordItemAdapter;

public class RecordItemActivity extends AppCompatActivity {
    public static final Integer RecordAudioRequestCode = 1;
    DB db;
    private EditText ltx;
    private EditText lotissement;
    private EditText editTextAngle;
    private EditText editTextDist;
    private EditText editTextObs;
    private ImageButton imageButtonRec;
    private Long record_id;
    private SpeechRecognizer speechRecognizer;

    private EditText speechEditorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            checkPermission();
        }
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        setContentView(R.layout.activity_record_item);

        ltx = (EditText) findViewById(R.id.editTextLTX);
        lotissement = (EditText) findViewById(R.id.editTextLotissement);
        editTextAngle = (EditText) findViewById(R.id.editTextAngle);
        editTextDist = (EditText) findViewById(R.id.editTextDist);
        editTextObs = (EditText) findViewById(R.id.editTextObs);
        imageButtonRec = (ImageButton) findViewById(R.id.imageButtonRec);

        db = DB.getInstance(getApplicationContext());
        db.setCurrentRecordItemId(null);

        record_id = getIntent().getLongExtra(DB.COLUMN_ID,0);
        if(record_id>0){
            Record record = db.getRecord(record_id);
            List<RecordItem> recordItems  = db.getListRecordItem(record_id);
            ListView listView = (ListView)findViewById(R.id.listViewItemRecordItem);
            listView.setAdapter(new RecordItemAdapter(this,recordItems));
            String record_ltx = record.getLtx();
            String record_lotissement = record.getLotissement();
            ltx.setText(record_ltx);
            lotissement.setText(record_lotissement);
            findViewById(R.id.contAddItem1).setVisibility(View.VISIBLE);
            findViewById(R.id.contAddItem2).setVisibility(View.VISIBLE);
            findViewById(R.id.listViewItemRecordItem).setVisibility(View.VISIBLE);
        }

        findViewById(R.id.btnSaveRecord).setOnClickListener((e)->{
            String ltxTxt  = ltx.getText().toString();
            String lotTxt = lotissement.getText().toString();
            if(ltxTxt.trim().equals("")){
                ltx.setError("LTX obligatoire");
                return;
            }
            if(lotTxt.trim().equals("")){
                lotissement.setError("lotissement obligatoire");
                return;
            }
            if(record_id>0){
                db.updateRecord(record_id,ltxTxt,lotTxt);
            }
            else{
                db.createRecord(ltxTxt,lotTxt,db.userConnecte.getUserId());
            }
            finish();
        });

        findViewById(R.id.btnAddRecordItemItem).setOnClickListener((e)->{
            String angleTxt  = editTextAngle.getText().toString();
            String distTxt = editTextDist.getText().toString();
            String obsTxt = editTextObs.getText().toString();

            if(angleTxt.trim().equals("")){
                editTextAngle.setError("Angle obligatoire");
                return;
            }
            if(distTxt.trim().equals("")){
                editTextDist.setError("Distance obligatoire");
                return;
            }

            if(db.getCurrentRecordItemId()!=null){
                db.updateRecordItem(db.getCurrentRecordItemId(),angleTxt,distTxt,obsTxt);
                db.setCurrentRecordItemId(null);
            }
            else
                db.createRecordItem(angleTxt,distTxt,obsTxt,record_id);
            editTextDist.setText("");
            editTextObs.setText("");
            editTextAngle.setText("");
            List<RecordItem> recordItems1  = db.getListRecordItem(record_id);
            ListView listView1 = (ListView)findViewById(R.id.listViewItemRecordItem);
            listView1.setAdapter(new RecordItemAdapter(this,recordItems1));
        });

        speechEditorText = editTextAngle;

        editTextAngle.setOnFocusChangeListener((view,v)->{
            speechEditorText = editTextAngle;
        });
        editTextDist.setOnFocusChangeListener((view,v)->{
            speechEditorText = editTextDist;
        });
        editTextObs.setOnFocusChangeListener((view,v)->{
            speechEditorText = editTextObs;
        });

        final Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fr-FR");

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {
                editTextAngle.setHint("Angle");
                editTextDist.setHint("Distance");
                editTextObs.setHint("Observation");
                speechEditorText.setText("");
                speechEditorText.setHint("Listening...");
            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                speechEditorText.setText(data.get(0));
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        imageButtonRec.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    speechRecognizer.stopListening();
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    //imageButtonRec.setImageResource(R.drawable.ic_mic_black_24dp);
                    speechRecognizer.startListening(speechRecognizerIntent);
                }
                return false;
            }
        });

    }
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},RecordAudioRequestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RecordAudioRequestCode && grantResults.length > 0 ){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
        }
    }
}