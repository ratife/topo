package mg.tife.topo.activities.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import mg.tife.topo.R;
import mg.tife.topo.data.DB;
import mg.tife.topo.data.model.User;
import mg.tife.topo.ui.menu.MenuActivity;

public class UserFormActivity extends AppCompatActivity {
    DB db;

    private EditText loginEdit;
    private EditText nomEdit;
    private EditText prenomEdit;
    private EditText psw1Edit;
    private EditText psw2Edit;
    private CheckBox roleText;

    private Long currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_form);
        db = DB.getInstance(getApplicationContext());


        Long userId = getIntent().getLongExtra(DB.COLUMN_ID,0L);



        nomEdit = (EditText) findViewById(R.id.editTextFormNom);
        prenomEdit = (EditText) findViewById(R.id.editTextFormPrenom);
        loginEdit = (EditText) findViewById(R.id.editTextFormEmailAddress);
        psw1Edit = (EditText) findViewById(R.id.editTextFormPassword);
        psw2Edit = (EditText) findViewById(R.id.editTextFormPassword2);
        roleText = (CheckBox) findViewById(R.id.editTextFormRole);

        currentUserId = userId;
        if(userId!=0) {
            User user = db.getUserById(userId);
            nomEdit.setText(user.getNom());
            prenomEdit.setText(user.getPrenom());
            loginEdit.setText(user.getLogin());
            if ("admin".equals(user.getRole()))
                roleText.setChecked(true);
        }
        ImageButton btsSave = findViewById(R.id.btnUserSave);

        btsSave.setOnClickListener((e)->{

            String loginTxt = loginEdit.getText().toString();
            String psw1 =  psw1Edit.getText().toString();
            String psw2 =  psw2Edit.getText().toString();
            String nomTxt =  nomEdit.getText().toString();
            String prenomTxt = prenomEdit.getText().toString();
            String roleTxt = roleText.isChecked()?"admin":"user";

            if(nomTxt==null || nomTxt.equals("")){
                nomEdit.setError("nom obligatoire");
                return;
            }

            if(prenomTxt==null || prenomTxt.equals("")){
                prenomEdit.setError("prenom obligatoire");
                return;
            }
            if(loginTxt==null || loginTxt.equals("")){
                loginEdit.setError("login error");
                return;
            }
            else {
                int nb = db.getNbrUserByLogin(loginTxt);
                if(currentUserId==0 && nb==1){
                    loginEdit.setError("login existe déjà");
                    return;
                }
            }



            if(psw1==null || psw1.equals("") || !psw1.equals(psw2)){
                psw1Edit.setError("password error");
                return;
            }
            if(currentUserId==0){
                db.insertUser(loginTxt,psw1,nomTxt,prenomTxt,roleTxt);
            }
            else{
                db.updateUser(currentUserId,loginTxt,psw1,nomTxt,prenomTxt,roleTxt);
            }

            if(db.userConnected.getRole().equals("admin")){
                Intent intent = new Intent(this, UserActivity.class);
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
            }
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}