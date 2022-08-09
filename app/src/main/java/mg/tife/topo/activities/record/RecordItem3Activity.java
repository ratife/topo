package mg.tife.topo.activities.record;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import mg.tife.topo.R;
import mg.tife.topo.activities.MapsActivity;
import mg.tife.topo.data.DB;
import mg.tife.topo.data.model.Record;
import mg.tife.topo.data.model.RecordItem;
import mg.tife.topo.ui.adapter.RecordItemAdapter;
import mg.tife.topo.util.Utils;

public class RecordItem3Activity extends AppCompatActivity {
    public static final Integer RecordAudioRequestCode = 1;
    DB db;
    private EditText editTextAngleH;
    private EditText editTextAngleV;
    private EditText editTextDistance;
    private TextView textStantion;
    private EditText editTextObservation;

    private ImageButton imageButtonRec;
    private TextView textViewRecordStat;
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

        setContentView(R.layout.activity_record_item3);

        editTextAngleH = (EditText) findViewById(R.id.editTextAngleH);
        editTextAngleV = (EditText) findViewById(R.id.editTextAngleV);
        editTextDistance = (EditText) findViewById(R.id.editTextDistance);
        textStantion = (TextView) findViewById(R.id.textViewStantion);
        editTextObservation = (EditText) findViewById(R.id.editTextObservation);
        textViewRecordStat = (TextView) findViewById(R.id.textViewStatus);
        imageButtonRec = (ImageButton)findViewById(R.id.imageButtonActiveAudio);

        db = DB.getInstance(getApplicationContext());
        db.setCurrentRecordItemId(null);

        record_id = getIntent().getLongExtra(DB.COLUMN_ID,0);

        if(record_id>0){
            List<RecordItem> recordItems  = db.getListRecordItem(record_id);
            ListView listView = (ListView)findViewById(R.id.listViewItemRecordItem);
            listView.setAdapter(new RecordItemAdapter(this,recordItems));
        }

        findViewById(R.id.imageButtonPrev).setOnClickListener((e)->{
            String val = textStantion.getText().toString();
            val = val.replace("S","");
            Integer vInt = Integer.valueOf(val);
            if(vInt>1){
                vInt--;
                textStantion.setText("S"+vInt);
            }
        });

        findViewById(R.id.imageButtonNext).setOnClickListener((e)->{
            String val = textStantion.getText().toString();
            val = val.replace("S","");
            Integer vInt = Integer.valueOf(val);
            vInt++;
            textStantion.setText("S"+vInt);
        });

        findViewById(R.id.buttonInitData).setOnClickListener((e)->{
            Intent intent = new Intent(this, RecordItem2Activity.class);
            intent.putExtra(DB.COLUMN_ID, record_id);
            startActivity(intent);
            finish();
        });
        /*
        findViewById(R.id.btnMap).setOnClickListener((e)->{
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
            finish();
        });
        */
        findViewById(R.id.btnExportLaborde).setOnClickListener((e)->{
            Utils.exportLaborde(db.getRecord(record_id),db,this);
        });

        findViewById(R.id.btnExportBrute).setOnClickListener((e)->{
            Utils.exportBrute(db.getRecord(record_id),db,this);
        });

        findViewById(R.id.imageButtonAddRecordItem).setOnClickListener((e)->{
            if(!Utils.testNotVide(editTextAngleH,"Angle Horizontal"))return;
            if(!Utils.testNotVide(editTextAngleV,"Angle veritical"))return;
            if(!Utils.testNotVide(editTextDistance,"Distance"))return;
            if(!Utils.testNotVide(editTextObservation,"Observation"))return;

            String angleH  = editTextAngleH.getText().toString();
            String angelV = editTextAngleV.getText().toString();
            String distance = editTextDistance.getText().toString();
            String obs = editTextObservation.getText().toString();
            String stantion = textStantion.getText().toString();

            RecordItem recordItem = new RecordItem(db.getCurrentRecordItemId());
            try{
                recordItem.setAngelV(Float.valueOf(angelV));
                recordItem.setAngleH(Float.valueOf(angleH));
                recordItem.setDistance(Float.valueOf(distance));
                recordItem.setObservation(obs);
                recordItem.setStantion(stantion);
                recordItem.setRecordId(record_id);

                if(db.getCurrentRecordItemId()!=null){
                    db.updateRecordItem(recordItem);
                    db.setCurrentRecordItemId(null);
                }
                else
                    db.createRecordItem(recordItem);
            }
            catch(NumberFormatException exc){
                Toast.makeText(getApplicationContext(), "Verifier votre valeur !!!", Toast.LENGTH_LONG).show();
                return;
            }
            Toast.makeText(getApplicationContext(), "donnée sauvegardé !!!", Toast.LENGTH_LONG).show();

            editTextAngleH.setText("");
            editTextAngleV.setText("");
            editTextDistance.setText("");
            editTextObservation.setText("");

            List<RecordItem> recordItems1  = db.getListRecordItem(record_id);
            ListView listView1 = (ListView)findViewById(R.id.listViewItemRecordItem);
            listView1.setAdapter(new RecordItemAdapter(this,recordItems1));
        });

        editTextAngleH.setOnFocusChangeListener((view,v)->{
            speechEditorText = v?editTextAngleH:null;
        });
        editTextAngleV.setOnFocusChangeListener((view,v)->{
            speechEditorText = v?editTextAngleV:null;
        });
        editTextDistance.setOnFocusChangeListener((view,v)->{
            speechEditorText = v?editTextDistance:null;
        });
        editTextObservation.setOnFocusChangeListener((view,v)->{
            speechEditorText = v?editTextObservation:null;
        });


        final Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fr-FR");

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {
                textViewRecordStat.setText("Reconnaissance vocal pret");
            }

            @Override
            public void onBeginningOfSpeech() {
                textViewRecordStat.setText("Ecoute commence");
            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {
                textViewRecordStat.setText("fin d'ecoute");
            }

            @Override
            public void onError(int i) {
                textViewRecordStat.setText("Error code "+i);
            }

            @Override
            public void onResults(Bundle bundle) {

                ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                textViewRecordStat.setText("Resultat :"+data.get(0));

                if(speechEditorText!=null){
                    speechEditorText.setText(data.get(0));
                    return;
                }

                if(editTextAngleH.getText().toString().equals("")){
                    editTextAngleH.setText(data.get(0));
                    return;
                }
                if(editTextAngleV.getText().toString().equals("")){
                    editTextAngleV.setText(data.get(0));
                    return;
                }
                if(editTextDistance.getText().toString().equals("")){
                    editTextDistance.setText(data.get(0));
                    return;
                }
                if(editTextObservation.getText().toString().equals("")){
                    editTextObservation.setText(data.get(0));
                    return;
                }
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