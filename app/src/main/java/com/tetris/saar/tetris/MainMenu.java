package com.tetris.saar.tetris;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity {
    TextView tvHeader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        tvHeader = (TextView)findViewById(R.id.tvHeader);
        //String header = "<font color= 'red'>T</font> <font color='orange'>e</font><font color='yellow'>t</font><font color='green'>r</font><font color='blue'>i</font> <font color='purple'>s</font>";
        SpannableString  header= new SpannableString("Tetris");
        header.setSpan(new ForegroundColorSpan(Color.RED),0,0,0);
        header.setSpan(new ForegroundColorSpan(Color.rgb(255,140,0)),1,1,0);
        header.setSpan(new ForegroundColorSpan(Color.YELLOW),2,2,0);
        header.setSpan(new ForegroundColorSpan(Color.GREEN),3,3,0);
        header.setSpan(new ForegroundColorSpan(Color.BLUE),4,4,0);
        header.setSpan(new ForegroundColorSpan(Color.rgb(138,43,226)),5,5,0);
        tvHeader.setText(header);
    }
}
