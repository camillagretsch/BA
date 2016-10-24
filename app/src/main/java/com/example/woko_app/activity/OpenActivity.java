package com.example.woko_app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.woko_app.R;
import com.example.woko_app.constants.ApartmentType;
import com.example.woko_app.models.AP;
import com.example.woko_app.models.WallState;

import java.util.ArrayList;
import java.util.List;

public class OpenActivity extends Activity {

    private AP currentAP;
    private TableLayout room;
    private List<Boolean> check = new ArrayList<>();

    private TableRow row;
    private TextView t;
    private CheckBox c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);

        intentReceiver();

        room = (TableLayout)findViewById(R.id.room);
        addRoomEntries();

    }

    private void addRoomEntries() {

        // wallstate
        row = new TableRow(this);
        row.setLayoutMode(R.layout.table_header1);

    }

    /**
     * get data from home activity
     */
    private void intentReceiver() {
        Intent intent = getIntent();
        currentAP = AP.findById(intent.getLongExtra("ID", 0000000));
    }
}
