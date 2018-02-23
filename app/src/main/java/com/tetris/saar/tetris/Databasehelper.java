package com.tetris.saar.tetris;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by user on 13/11/2017.
 */
//This class control all the database stuff
public class Databasehelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "Scoreboard.db";
    private static final int DATABASE_VERSION = 1;

    /**
     * The constant TABLE_NAME.
     */
    public static final String TABLE_NAME = "scoreboard1";
    /**
     * The constant UID.
     */
    public static final String UID = "id";                 // primary Key, automatic ID
    /**
     * The constant NAME.
     */
    public static final String NAME = "name";           // name of the player
    /**
     * The constant SCORE.
     */
    public static final String SCORE = "score";               // number of wins


    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME + " (" + UID + " INTEGER PRIMARY KEY, "
            + NAME + " TEXT, " + SCORE + " INTEGER );";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "+ TABLE_NAME;

    /**
     * Instantiates a new Databasehelper.
     *
     * @param context the context
     */
    public Databasehelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    /**
     * Add data.
     *
     * @param name  the name
     * @param score the score
     */
    //Adding new entire
    public void addData(String name,int score)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NAME, name);
        cv.put(SCORE, score);
        boolean inserted =  db.insert(TABLE_NAME, null, cv)>0;
    }

    /**
     * Remove person.
     *
     * @param uid the uid
     */
    public void remove(String uid){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "DELETE FROM " + TABLE_NAME +" WHERE id="+uid;
        db.execSQL(sql);
    }

    /**
     * Call the remove function.
     *
     * @param uid     the uid
     * @param context the context
     */
    public static void callRemove(String uid,Context context){
        Databasehelper temp = new Databasehelper(context);
        temp.remove(uid);
    }
    /**
     * Display list array list.
     *
     * @param order the order
     * @return the array list
     */
    //Displaying the list by the order
    public ArrayList<ArrayList<String>> displayList(int order){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM scoreboard1 ORDER BY cast(score as REAL) DESC",null);
        ArrayList<String> displayName = new ArrayList<>();
        ArrayList<String> displayScore = new ArrayList<>();
        ArrayList<String> uidList= new ArrayList<>();
        //Order by the given order
        switch (order){
            case 0:
               cursor =db.rawQuery("SELECT * FROM scoreboard1 ORDER BY cast(score as REAL) DESC",null);
                break;
            case 1:
                cursor = db.rawQuery("SELECT * FROM scoreboard1 ORDER BY cast(score as REAL) ASC",null);
                break;
            case 2:
                cursor = db.rawQuery("SELECT * FROM scoreboard1 ORDER BY name DESC",null);
                break;
            case 3:
                cursor = db.rawQuery("SELECT * FROM scoreboard1 ORDER BY name ASC",null);
                break;
        }
        //Splitting the columns
        int i=1;
        int nameColumn = cursor.getColumnIndex("name");
        int scoreColumn = cursor.getColumnIndex("score");
        int uidColumn = cursor.getColumnIndex("id");
        //Displaying the DB
        cursor.moveToFirst();
        if(cursor != null && (cursor.getCount() > 0)){
            do{
                String name = cursor.getString(nameColumn);
                String score = cursor.getString(scoreColumn);
                String uid = cursor.getString(uidColumn);
                displayName.add(i +"." + name);
                displayScore.add(score);
                uidList.add(uid);
                i++;
            }while(cursor.moveToNext());
        }
        ArrayList<ArrayList<String>> displayDB = new ArrayList<>();
        displayDB.add(0,displayName);
        displayDB.add(1,displayScore);
        displayDB.add(2,uidList);
        return displayDB;
    }
}

