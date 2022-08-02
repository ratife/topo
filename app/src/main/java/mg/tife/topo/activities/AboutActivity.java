package mg.tife.topo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import mg.tife.topo.R;
import mg.tife.topo.data.DB;
import mg.tife.topo.data.model.Parametres;

public class ParametreActivity extends AppCompatActivity {
    DB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = DB.getInstance(getApplicationContext());
        Parametres param = db.getParam();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parametres);
        Switch switchMode = findViewById(R.id.paramMode);
        EditText paramPath  = findViewById(R.id.paramPath);

        findViewById(R.id.paramSave).setOnClickListener((e)->{
            db.updateParam(paramPath.getText().toString(),switchMode.isChecked()?"clair":"dark");
            finish();
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

        });

        if(param.getMode().equals("clair")){
            switchMode.setChecked(true);
        }
        paramPath.setText(param.getPath());
    }
}