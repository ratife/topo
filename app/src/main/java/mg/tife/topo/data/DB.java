package mg.tife.topo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import mg.tife.topo.data.model.LoggedInUser;
import mg.tife.topo.data.model.Parametres;
import mg.tife.topo.data.model.Record;
import mg.tife.topo.data.model.RecordItem;
import mg.tife.topo.data.model.User;

public class DB extends SQLiteOpenHelper {
    private static DB INSTANCE;
    public static LoggedInUser userConnecte;
    public static final String DATABASE_NAME = "topo.db";
    public static final int DATABASE_VERSION = 40;

    public static final String COLUMN_DATE_CREATION = "date_create";
    public static final String COLUMN_DATE_UPDATE = "date_update";
    public static final String COLUMN_ID = "id";

    public static final String USER_TABLE_NAME = "user";
    public static final String USER_COLUMN_LOGIN = "login";
    public static final String USER_COLUMN_PASSWORD = "password";
    public static final String USER_COLUMN_NOM = "nom";
    public static final String USER_COLUMN_PRENOM = "prenom";
    public static final String USER_COLUMN_ROLE = "role";

    public static final String RECORD_TABLE_NAME = "record";
    public static final String RECORD_COLUMN_LTX = "ltx";
    public static final String RECORD_COLUMN_LOTISSEMENT = "lotissement";
    public static final String RECORD_COLUMN_TYPE_TRAVAUX = "type_travaux";
    public static final String RECORD_COLUMN_ADRESS = "adress";
    public static final String RECORD_COLUMN_DATE = "date";
    public static final String RECORD_COLUMN_TN = "tn";
    public static final String RECORD_COLUMN_PARCELLE = "parcelle";
    public static final String RECORD_COLUMN_PROPRIETAIRE = "proprietaire";
    public static final String RECORD_COLUMN_XO = "xo";
    public static final String RECORD_COLUMN_YO = "yo";
    public static final String RECORD_COLUMN_ZO = "zo";
    public static final String RECORD_COLUMN_VO = "vo";
    public static final String RECORD_COLUMN_USER_ID= "user_id";

    public static final String RECORD_ITEM_TABLE_NAME = "record_item";
    public static final String RECORD_ITEM_COLUMN_ID_RECORD = "record_id";
    public static final String RECORD_ITEM_COLUMN_ANGLE_H = "angle_horizontal";
    public static final String RECORD_ITEM_COLUMN_ANGLE_V = "angle_vertical";
    public static final String RECORD_ITEM_COLUMN_DISTANCE = "distance";
    public static final String RECORD_ITEM_COLUMN_STANTION = "stantion";
    public static final String RECORD_ITEM_COLUMN_OBSERVATION = "observation";

    public static final String PARAM_TABLE_NAME = "parameters";
    public static final String PARAM_COLUMN_PATH = "path";
    public static final String PARAM_COLUMN_MODE = "mode";

    public Long recordItemId;

    public User userConnected;

    private HashMap hp;

    //private SQLiteDatabase db;
    public static DB getInstance(Context context){
        if(INSTANCE==null) INSTANCE = new DB(context) ;
        return INSTANCE;
    }
    private DB(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table "+USER_TABLE_NAME+" " +
                        "("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+USER_COLUMN_LOGIN+" text,"+USER_COLUMN_PASSWORD+" text,"+USER_COLUMN_NOM+" text, "+USER_COLUMN_PRENOM+" text,"+USER_COLUMN_ROLE+" text,"+COLUMN_DATE_CREATION+" text,"+COLUMN_DATE_UPDATE+" text)"
        );


        db.execSQL(
                "create table "+RECORD_TABLE_NAME+" " +
                        "("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                        +RECORD_COLUMN_LTX+" text,"
                        +RECORD_COLUMN_LOTISSEMENT+" text,"
                        +RECORD_COLUMN_TYPE_TRAVAUX+" text,"
                        +RECORD_COLUMN_ADRESS+" text,"
                        +RECORD_COLUMN_DATE+" text,"
                        +RECORD_COLUMN_TN+" text,"
                        +RECORD_COLUMN_PARCELLE+" text,"
                        +RECORD_COLUMN_PROPRIETAIRE+" text,"
                        +RECORD_COLUMN_XO+" text,"
                        +RECORD_COLUMN_YO+" text,"
                        +RECORD_COLUMN_ZO+" text,"
                        +RECORD_COLUMN_VO+" text,"
                        +RECORD_COLUMN_USER_ID+" integer,"
                        +COLUMN_DATE_CREATION+" text,"
                        +COLUMN_DATE_UPDATE+" text)"
        );

        db.execSQL(
                "create table "+RECORD_ITEM_TABLE_NAME+" " +
                        "("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                        +RECORD_ITEM_COLUMN_ANGLE_H+" text,"
                        +RECORD_ITEM_COLUMN_ANGLE_V+" text,"
                        +RECORD_ITEM_COLUMN_DISTANCE+" integer,"
                        +RECORD_ITEM_COLUMN_STANTION+" text,"
                        +RECORD_ITEM_COLUMN_OBSERVATION+" text,"
                        +RECORD_ITEM_COLUMN_ID_RECORD+" integer,"
                        +COLUMN_DATE_CREATION+" text,"
                        +COLUMN_DATE_UPDATE+" text)"
        );

        db.execSQL(
                "create table "+PARAM_TABLE_NAME+" " +
                        "("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+PARAM_COLUMN_PATH+" text,"+PARAM_COLUMN_MODE+" text,"+COLUMN_DATE_CREATION+" text,"+COLUMN_DATE_UPDATE+" text)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS "+USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+RECORD_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+RECORD_ITEM_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+PARAM_TABLE_NAME);
        onCreate(db);

    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean insertUser (String login, String pws, String nom, String prenom,String role) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_COLUMN_LOGIN, login);
        contentValues.put(USER_COLUMN_PASSWORD, pws);
        contentValues.put(USER_COLUMN_NOM, nom);
        contentValues.put(USER_COLUMN_PRENOM, prenom);
        contentValues.put(USER_COLUMN_ROLE, role);

        contentValues.put(COLUMN_DATE_CREATION, Calendar.getInstance().getTime().toString());
        long res = db.insert(USER_TABLE_NAME, null, contentValues);

        //db.insert("+USER_TABLE_NAME+", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, USER_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (Integer id, String name, String phone, String email, String street,String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContact (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public List<String> getAllCotacts() {
        List<String> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            //array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }

    public Result<LoggedInUser> login(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res =  db.rawQuery("select id,nom,prenom,role from user where login=? and password=?",new String[]{username,password});
        int cpt = res.getCount();
        res.moveToFirst();
        try {
            // TODO: handle loggedInUser authentication
            LoggedInUser fakeUser =
                    new LoggedInUser(
                            res.getLong(0),
                            res.getString(1) +" "+res.getString(2),res.getString(3));
            userConnecte = fakeUser;
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public Long createRecord(Record record) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RECORD_COLUMN_LTX, record.getLtx());
        contentValues.put(RECORD_COLUMN_LOTISSEMENT, record.getLotissement());
        contentValues.put(RECORD_COLUMN_TYPE_TRAVAUX, record.getTypeTravaux());
        contentValues.put(RECORD_COLUMN_ADRESS, record.getAdressTerrain());
        contentValues.put(RECORD_COLUMN_DATE, record.getDate());
        contentValues.put(RECORD_COLUMN_TN, record.getTn());
        contentValues.put(RECORD_COLUMN_PARCELLE, record.getParcelle());
        contentValues.put(RECORD_COLUMN_PROPRIETAIRE, record.getProprietaire());
        contentValues.put(RECORD_COLUMN_XO, record.getXo());
        contentValues.put(RECORD_COLUMN_YO, record.getYo());
        contentValues.put(RECORD_COLUMN_ZO, record.getZo());
        contentValues.put(RECORD_COLUMN_VO, record.getVo());
        contentValues.put(RECORD_COLUMN_USER_ID, userConnected.getId());
        contentValues.put(COLUMN_DATE_CREATION, Calendar.getInstance().getTime().toString());

        Long res = db.insert(RECORD_TABLE_NAME, null, contentValues);

        return res;
    }

    public List<Record> getListRecord() {
        Long userId = userConnected.getId();
        List<Record> ret = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + RECORD_TABLE_NAME+" where "+RECORD_COLUMN_USER_ID+"=?", new String[]{userId+""});
        res.moveToFirst();

        while(res.isAfterLast() == false){
            Record rec = new Record();
            rec.setId(res.getLong(0));
            rec.setLtx(res.getString(1));
            rec.setLotissement(res.getString(2));
            rec.setTypeTravaux(res.getString(3));
            rec.setAdressTerrain(res.getString(4));
            rec.setDate(res.getString(5));
            rec.setTn(res.getString(6));
            rec.setParcelle(res.getString(7));
            rec.setProprietaire(res.getString(8));
            rec.setXo(res.getDouble(9));
            rec.setYo(res.getDouble(10));
            rec.setZo(res.getDouble(11));
            rec.setVo(res.getDouble(12));
            rec.setUserId(res.getLong(13));
            ret.add(rec);
            //array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME));
            res.moveToNext();
        }
        return ret;
    }

    public void updateRecord(Record record) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(RECORD_COLUMN_LTX, record.getLtx());
        contentValues.put(RECORD_COLUMN_LOTISSEMENT, record.getLotissement());
        contentValues.put(RECORD_COLUMN_TYPE_TRAVAUX, record.getTypeTravaux());
        contentValues.put(RECORD_COLUMN_ADRESS, record.getAdressTerrain());
        contentValues.put(RECORD_COLUMN_DATE, record.getDate());
        contentValues.put(RECORD_COLUMN_TN, record.getTn());
        contentValues.put(RECORD_COLUMN_PARCELLE, record.getParcelle());
        contentValues.put(RECORD_COLUMN_PROPRIETAIRE, record.getProprietaire());
        contentValues.put(RECORD_COLUMN_XO, record.getXo());
        contentValues.put(RECORD_COLUMN_YO, record.getYo());
        contentValues.put(RECORD_COLUMN_ZO, record.getZo());
        contentValues.put(RECORD_COLUMN_VO, record.getVo());
        contentValues.put(RECORD_COLUMN_USER_ID, userConnected.getId());
        contentValues.put(COLUMN_DATE_CREATION, Calendar.getInstance().getTime().toString());

        db.update(RECORD_TABLE_NAME, contentValues,"id=?", new String[]{record.getId()+""});
    }

    public void createRecordItem(RecordItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RECORD_ITEM_COLUMN_ID_RECORD, item.getRecordId());
        contentValues.put(RECORD_ITEM_COLUMN_ANGLE_H, item.getAngleH());
        contentValues.put(RECORD_ITEM_COLUMN_ANGLE_V, item.getAngelV());
        contentValues.put(RECORD_ITEM_COLUMN_DISTANCE, item.getDistance());
        contentValues.put(RECORD_ITEM_COLUMN_STANTION, item.getStantion());
        contentValues.put(RECORD_ITEM_COLUMN_OBSERVATION, item.getObservation());
        contentValues.put(COLUMN_DATE_CREATION, Calendar.getInstance().getTime().toString());
        db.insert(RECORD_ITEM_TABLE_NAME, null, contentValues);
    }

    public List<RecordItem> getListRecordItem(Long record_id) {
        List<RecordItem> ret = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + RECORD_ITEM_TABLE_NAME +" where "+RECORD_ITEM_COLUMN_ID_RECORD+"=?", new String[]{record_id+""} );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            RecordItem item = new RecordItem(res.getLong(0));
            item.setAngleH(res.getFloat(1));
            item.setAngelV(res.getFloat(2));
            item.setDistance(res.getFloat(3));
            item.setStantion(res.getString(4));
            item.setObservation(res.getString(5));
            item.setRecordId(res.getLong(6));
            ret.add(item);
            res.moveToNext();
        }
        return ret;
    }

    public void deleteRecordItem(Long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(RECORD_ITEM_TABLE_NAME,
                "id = ? ",
                new String[] { id+""});
    }

    public Record getRecord(Long record_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + RECORD_TABLE_NAME +" where "+COLUMN_ID+"=?", new String[]{record_id+""} );
        Record rc = new Record();
        if(res.getCount()>0){
            res.moveToFirst();
            rc.setId(res.getLong(0));
            rc.setLtx(res.getString(1));
            rc.setLotissement(res.getString(2));
            rc.setTypeTravaux(res.getString(3));
            rc.setAdressTerrain(res.getString(4));
            rc.setDate(res.getString(5));
            rc.setTn(res.getString(6));
            rc.setParcelle(res.getString(7));
            rc.setProprietaire(res.getString(8));
            rc.setXo(res.getDouble(9));
            rc.setYo(res.getDouble(10));
            rc.setZo(res.getDouble(11));
            rc.setVo(res.getDouble(12));
            rc.setUserId(res.getLong(13));
        }

        return rc;
    }

    public void setCurrentRecordItemId(Long id) {
        recordItemId = id;
    }

    public Long getCurrentRecordItemId(){
        return recordItemId;
    }

    public void updateRecordItem(RecordItem recordItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RECORD_ITEM_COLUMN_ANGLE_H, recordItem.getAngleH());
        contentValues.put(RECORD_ITEM_COLUMN_ANGLE_V, recordItem.getAngelV());
        contentValues.put(RECORD_ITEM_COLUMN_DISTANCE, recordItem.getDistance());
        contentValues.put(RECORD_ITEM_COLUMN_STANTION, recordItem.getStantion());
        contentValues.put(RECORD_ITEM_COLUMN_OBSERVATION, recordItem.getObservation());
        contentValues.put(COLUMN_DATE_UPDATE, Calendar.getInstance().getTime().toString());
        db.update(RECORD_ITEM_TABLE_NAME, contentValues,"id=?", new String[]{recordItem.getId()+""});
    }

    public int getNbrRecordItem(Long record_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select count(*) from " + RECORD_ITEM_TABLE_NAME +" where "+RECORD_ITEM_COLUMN_ID_RECORD+"=?", new String[]{record_id+""} );
        res.moveToFirst();
        return res.getInt(0);
    }

    public List<Record> findRecord(String txt) {
        List<Record> ret = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + RECORD_TABLE_NAME +" where "+RECORD_COLUMN_LTX+" like ? or "+RECORD_COLUMN_LOTISSEMENT+" like ?", new String[]{"%"+txt+"%","%"+txt+"%"}  );
        res.moveToFirst();
        while(res.isLast() == false){
            Record rc = new Record();
            rc.setId(res.getLong(0));
            rc.setLtx(res.getString(1));
            rc.setLotissement(res.getString(2));
            rc.setTypeTravaux(res.getString(3));
            rc.setAdressTerrain(res.getString(4));
            rc.setDate(res.getString(5));
            rc.setTn(res.getString(6));
            rc.setParcelle(res.getString(7));
            rc.setProprietaire(res.getString(8));
            rc.setXo(res.getDouble(9));
            rc.setYo(res.getDouble(10));
            rc.setZo(res.getDouble(11));
            rc.setVo(res.getDouble(12));
            rc.setUserId(res.getLong(13));
            ret.add(rc);
            //array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            res.moveToNext();
        }
        return ret;
    }

    public int getNbrUser(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select count(*) from " + USER_TABLE_NAME , null);
        res.moveToFirst();
        return res.getInt(0);
    }

    public List<User> getAllUsers() {
        List<User> ret = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + USER_TABLE_NAME, null );
        res.moveToFirst();
        while(res.isAfterLast() == false){
            ret.add(new User(res.getLong(0),res.getString(1),res.getString(3),res.getString(4),res.getString(5)));
            res.moveToNext();
        }
        return ret;
    }

    public boolean updateUser(Long currentUserId, String loginTxt, String psw1, String nomTxt, String prenomTxt, String roleTxt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_COLUMN_LOGIN, loginTxt);
        contentValues.put(USER_COLUMN_PASSWORD, psw1);
        contentValues.put(USER_COLUMN_NOM, nomTxt);
        contentValues.put(USER_COLUMN_PRENOM, prenomTxt);
        contentValues.put(USER_COLUMN_ROLE, roleTxt);

        contentValues.put(COLUMN_DATE_UPDATE, Calendar.getInstance().getTime().toString());

        db.update(USER_TABLE_NAME, contentValues,"id=?", new String[]{currentUserId+""});

        return true;
    }

    public void deleteUser(Long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(USER_TABLE_NAME,
                "id = ? ",
                new String[] { id+""});
    }

    public int getNbrUserByLogin(String loginTxt) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select count(*) from " + USER_TABLE_NAME +" where "+USER_COLUMN_LOGIN+"=?", new String[]{loginTxt+""} );
        res.moveToFirst();
        return res.getInt(0);
    }

    public Parametres getParam(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + PARAM_TABLE_NAME , null);
        res.moveToFirst();
        return new Parametres(res.getString(1),res.getString(2));
    }

    public boolean updateParam(String path, String mode) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select count(*) from " + PARAM_TABLE_NAME ,null );
        res.moveToFirst();
        int nb = res.getInt(0);

        db= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PARAM_COLUMN_PATH, path);
        contentValues.put(PARAM_COLUMN_MODE, mode);

        contentValues.put(COLUMN_DATE_UPDATE, Calendar.getInstance().getTime().toString());

        if(nb>0){
            db.update(PARAM_TABLE_NAME, contentValues,"1=1", null);
        }
        else{
            db.insert(PARAM_TABLE_NAME, null, contentValues);
        }

        return true;
    }

    public User getUserById(Long userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + USER_TABLE_NAME +" where "+COLUMN_ID+"=?", new String[]{userId+""} );
        res.moveToFirst();
        User user = new User(res.getLong(0), res.getString(1), res.getString(3), res.getString(4), res.getString(5));
        return user;
    }

    public void setUserConnected(Long id) {
        userConnected = getUserById(id);
    }

    public void deleteRecord(Long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(RECORD_ITEM_TABLE_NAME,
                RECORD_ITEM_COLUMN_ID_RECORD+" = ? ",
                new String[] { id+""});
        db.delete(RECORD_TABLE_NAME,
                "id = ? ",
                new String[] { id+""});
    }
}
