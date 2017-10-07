package com.tetris.saar.tetris;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Scoreboard extends AppCompatActivity  implements View.OnClickListener{
    //Buttons
    Button btnScore;
    Button btnName;
    int nameState =0;
    int scoreState =0;
    //ListView
    ListView lvScore;
    ArrayList<String> displayDB = new ArrayList<>();
    //Database
    SQLiteDatabase mainDB = null;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        btnScore = (Button) findViewById(R.id.btnScore);
        btnName = (Button) findViewById(R.id.btnName);
        btnScore.setOnClickListener(this);
        btnName.setOnClickListener(this);

        lvScore = (ListView) findViewById(R.id.lvScoreboard);


        //Getting the Database
        mainDB = this.openOrCreateDatabase("Scoreboard",MODE_PRIVATE,null);
        mainDB.execSQL("CREATE TABLE IF NOT EXISTS scoreboard1" + "(id integer primary key , name VARCHAR,score integer);");
        cursor = mainDB.rawQuery("SELECT * FROM scoreboard1 ORDER BY cast(score as REAL) DESC",null);
        displayList();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == btnScore.getId()){
            if(scoreState ==0){
                btnScore.setText("Score ▼");
                scoreState++;
                cursor = mainDB.rawQuery("SELECT * FROM scoreboard1 ORDER BY cast(score as REAL) DESC",null);
            }else {
                    btnScore.setText("Score ▲");
                    scoreState--;
                cursor = mainDB.rawQuery("SELECT * FROM scoreboard1 ORDER BY cast(score as REAL) ASC",null);
            }
            btnName.setText("Name");
            displayList();
        }
        if(v.getId() == btnName.getId()){
            if(nameState ==0){
                btnName.setText("Name ▼");
                nameState++;
                cursor = mainDB.rawQuery("SELECT * FROM scoreboard1 ORDER BY name DESC",null);
            }else {
                btnName.setText("Name ▲");
                nameState--;
                cursor = mainDB.rawQuery("SELECT * FROM scoreboard1 ORDER BY name ASC",null);
            }
            btnScore.setText("Score");
            displayList();
        }
    }
    public void displayList(){
        displayDB.clear();
        //Getting the data from the database
        int i=1;
        int nameColumn = cursor.getColumnIndex("name");
        int scoreColumn = cursor.getColumnIndex("score");
        //Displaying the DB
        cursor.moveToFirst();
        if(cursor != null && (cursor.getCount() > 0)){
            do{
                String name = cursor.getString(nameColumn);
                String score = cursor.getString(scoreColumn);
                displayDB.add(i +".  " + name + "          " + score);
                i++;
            }while(cursor.moveToNext());
        }
        if(!displayDB.isEmpty()){
            ArrayAdapter<String> adpater = new ArrayAdapter<String>(this, R.layout.listviewlayout,displayDB);
            lvScore.setAdapter(adpater);
        }
    }
}
