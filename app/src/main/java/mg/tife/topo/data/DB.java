package mg.tife.topo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import mg.tife.topo.data.model.LoggedInUser;
import mg.tife.topo.data.model.Record;

public class DB extends SQLiteOpenHelper {
    private static DB INSTANCE;
    public static LoggedInUser userConnecte;
    public static final String DATABASE_NAME = "topo.db";
    public static final int DATABASE_VERSION = 12;

    public static final String COLUMN_DATE_CREATION = "date_create";
    public static final String COLUMN_DATE_UPDATE = "date_update";
    public static final String COLUMN_ID = "id";

    public static final String USER_TABLE_NAME = "user";
    public static final String USER_COLUMN_LOGIN = "login";
    public static final String USER_COLUMN_PASSWORD = "password";
    public static final String USER_COLUMN_NOM = "nom";
    public static final String USER_COLUMN_PRENOM = "prenom";

    public static final String RECORD_TABLE_NAME = "record";
    public static final String RECORD_COLUMN_LTX = "ltx";
    public static final String RECORD_COLUMN_LOTISSEMENT = "lotissement";
    public static final String RECORD_COLUMN_USER_ID= "user_id";

    public static final String RECORD_ITEM_TABLE_NAME = "record_item";
    public static final String RECORD_ITEM_COLUMN_ID_RECORD = "record_id";
    public static final String RECORD_ITEM_COLUMN_ANGLE = "angle";
    public static final String RECORD_ITEM_COLUMN_DISTANCE = "distance";
    public static final String RECORD_ITEM_COLUMN_OBSERVATION = "observation";

    private HashMap hp;

    //private SQLiteDatabase db;
    public static DB getInstance(Context context){
        if(INSTANCE==null) INSTANCE = new DB(context) ;
        return INSTANCE;
    }
    public DB(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table "+USER_TABLE_NAME+" " +
                        "("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+USER_COLUMN_LOGIN+" text,"+USER_COLUMN_PASSWORD+" text,"+USER_COLUMN_NOM+" text, "+USER_COLUMN_PRENOM+" text,"+COLUMN_DATE_CREATION+" text)"
        );
        db.execSQL(
                "create table "+RECORD_TABLE_NAME+" " +
                        "("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+RECORD_COLUMN_LTX+" text,"+RECORD_COLUMN_LOTISSEMENT+" text,"+RECORD_COLUMN_USER_ID+" integer,"+COLUMN_DATE_CREATION+" text)"
        );
        db.execSQL(
                "create table "+RECORD_ITEM_TABLE_NAME+" " +
                        "("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+RECORD_ITEM_COLUMN_ANGLE+" text,"+RECORD_ITEM_COLUMN_DISTANCE+" text,"+RECORD_ITEM_COLUMN_OBSERVATION+" text,"+RECORD_ITEM_COLUMN_ID_RECORD+" integer,"+COLUMN_DATE_CREATION+" text)"
        );
        //insertUser("tife","111111","RATIFE","KELY");
       // insertUser("lita","111111","LITA","KELY");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS "+USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+RECORD_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+RECORD_ITEM_TABLE_NAME);
        onCreate(db);

    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean insertUser (String login, String pws, String nom, String prenom) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_COLUMN_LOGIN, login);
        contentValues.put(USER_COLUMN_PASSWORD, pws);
        contentValues.put(USER_COLUMN_NOM, nom);
        contentValues.put(USER_COLUMN_PRENOM, prenom);

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

        Cursor res =  db.rawQuery("select id,nom,prenom from user where login=? and password=?",new String[]{username,password});
        int cpt = res.getCount();
        res.moveToFirst();
        try {
            // TODO: handle loggedInUser authentication
            LoggedInUser fakeUser =
                    new LoggedInUser(
                            res.getInt(0),
                            res.getString(1) +" "+res.getString(2));
            userConnecte = fakeUser;
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public Long createRecord(String ltxTxt, String lotTxt,Integer userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(RECORD_COLUMN_LTX, ltxTxt);
        contentValues.put(RECORD_COLUMN_LOTISSEMENT, lotTxt);
        contentValues.put(RECORD_COLUMN_USER_ID, userId);

        contentValues.put(COLUMN_DATE_CREATION, Calendar.getInstance().getTime().toString());
        Long res = db.insert(RECORD_TABLE_NAME, null, contentValues);
        return res;
    }

    public List<Record> getListRecord() {
        List<Record> ret = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + RECORD_TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            ret.add(new Record(res.getLong(0),res.getString(1),res.getString(2)));
            //array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            res.moveToNext();
        }
        return ret;
    }
}
