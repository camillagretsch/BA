package com.example.woko_app.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woko_app.R;
import com.example.woko_app.fragment.DataGridFragment;
import com.example.woko_app.models.AP;
import com.example.woko_app.models.BalconyState;
import com.example.woko_app.models.BasementState;
import com.example.woko_app.models.CupboardState;
import com.example.woko_app.models.CutleryState;
import com.example.woko_app.models.PersonalSerializer;
import com.example.woko_app.models.DoorState;
import com.example.woko_app.models.EntryStateInterface;
import com.example.woko_app.models.FloorState;
import com.example.woko_app.models.FridgeState;
import com.example.woko_app.models.FurnitureState;
import com.example.woko_app.models.MattressState;
import com.example.woko_app.models.OvenState;
import com.example.woko_app.models.RadiatorState;
import com.example.woko_app.models.ShowerState;
import com.example.woko_app.models.SocketState;
import com.example.woko_app.models.VentilationState;
import com.example.woko_app.models.WallState;
import com.example.woko_app.models.WindowState;

public class HistoryActivity extends Activity{

    private Typeface font;

    private Button btnCamera;
    private Button btnBack;
    private ImageView ivPicture;
    private TextView txtDate;
    private TextView txtComment;

    private String comment;
    private String date;
    private Bitmap picture;
    private EntryStateInterface tableEntries;

    private AP currentAP;
    private int pos;
    private int parent;
    private String child;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        intentReceiver();

        //fontawesome
        font = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf");

        //set button on click listener
        btnBack = (Button)findViewById(R.id.btnBack);
        btnBack.setTypeface(font);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnCamera = (Button)findViewById(R.id.btnCamera);
        btnCamera.setTypeface(font);
        //shows camera button only when the checkbox is on yes
        if (tableEntries.getClass().getName().equals(FurnitureState.class.getName())) {
            FurnitureState furniture = (FurnitureState) tableEntries;
            if (furniture.getCountBrokenAtPosition(pos) == 0 || tableEntries.getCheckOldAtPosition(pos) == true) {
                btnCamera.setVisibility(View.INVISIBLE);
                Toast.makeText(getBaseContext(), getResources().getString(R.string.no_picture), Toast.LENGTH_LONG).show();

            }
        } else if (tableEntries.getClass().getName().equals(CutleryState.class.getName())) {
            CutleryState cutlery = (CutleryState) tableEntries;
            if (cutlery.getCountBrokenAtPosition(pos) == 0 || tableEntries.getCheckOldAtPosition(pos) == true) {
                btnCamera.setVisibility(View.INVISIBLE);
                Toast.makeText(getBaseContext(), getResources().getString(R.string.no_picture), Toast.LENGTH_LONG).show();
            }
        } else {
            if (tableEntries.getCheckAtPosition(pos) == true || tableEntries.getCheckOldAtPosition(pos) == true) {
                btnCamera.setVisibility(View.INVISIBLE);
                Toast.makeText(getBaseContext(), getResources().getString(R.string.no_picture), Toast.LENGTH_LONG).show();
            }
        }

        // changes to the camera
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });

        fillInDateAndComment();
    }

    /**
     * show the picture, comment and date of the last APs on the device
     */
    private void fillInDateAndComment() {
        int i = 1;
        AP ap = currentAP;
        while (i < 6) {
            setCommentAndPicture(ap);
            if (i == 1) {
                txtDate = (TextView)findViewById(R.id.txtDate1);
                txtComment = (TextView)findViewById(R.id.txtComment1);
                ivPicture = (ImageView)findViewById(R.id.picture1);
            } else if (i == 2) {
                txtDate = (TextView)findViewById(R.id.txtDate2);
                txtComment = (TextView)findViewById(R.id.txtComment2);
                ivPicture = (ImageView)findViewById(R.id.picture2);
            } else if (i == 3) {
                txtDate = (TextView)findViewById(R.id.txtDate3);
                txtComment = (TextView)findViewById(R.id.txtComment3);
                ivPicture = (ImageView)findViewById(R.id.picture3);
            } else if (i == 4) {
                txtDate = (TextView)findViewById(R.id.txtDate4);
                txtComment = (TextView)findViewById(R.id.txtComment4);
                ivPicture = (ImageView)findViewById(R.id.picture4);
            } else if (i == 5) {
                txtDate = (TextView)findViewById(R.id.txtDate5);
                txtComment = (TextView)findViewById(R.id.txtComment5);
                ivPicture = (ImageView)findViewById(R.id.picture5);
            }
            i++;
            if (null == picture) {
                comment = "";
                date = "";
            } else
                setDate(ap);
            txtDate.setText(date);
            txtComment.setText(comment);
            ivPicture.setImageBitmap(picture);

            if (ap.getOldAP() != null) {
                ap = ap.getOldAP();
            } else
                break;
        }
    }

    /**
     * calls the setComment and setPicture functions for a specific entry
     * @param ap
     */
    private void setCommentAndPicture(AP ap) {
        if (tableEntries.getClass().getName().equals(MattressState.class.getName())) {
            setComment(MattressState.findByRoomAndAP(ap.getRoom(), ap));
            setPicture(MattressState.findByRoomAndAP(ap.getRoom(), ap));
        } else if (tableEntries.getClass().getName().equals(FurnitureState.class.getName())) {
            setComment(FurnitureState.findByRoomAndAP(ap.getRoom(), ap));
            setPicture(FurnitureState.findByRoomAndAP(ap.getRoom(), ap));
        } else if (tableEntries.getClass().getName().equals(WallState.class.getName())) {
            setComment(WallState.checkBelonging((WallState) tableEntries, ap));
            setPicture(WallState.checkBelonging((WallState) tableEntries, ap));
        } else if (tableEntries.getClass().getName().equals(FloorState.class.getName())) {
            setComment(FloorState.checkBelonging((FloorState) tableEntries, ap));
            setPicture(FloorState.checkBelonging((FloorState) tableEntries, ap));
        } else if (tableEntries.getClass().getName().equals(WindowState.class.getName())) {
            setComment(WindowState.checkBelonging((WindowState) tableEntries, ap));
            setPicture(WindowState.checkBelonging((WindowState) tableEntries, ap));
        } else if (tableEntries.getClass().getName().equals(DoorState.class.getName())) {
            setComment(DoorState.checkBelonging((DoorState) tableEntries, ap));
            setPicture(DoorState.checkBelonging((DoorState) tableEntries, ap));
        } else if (tableEntries.getClass().getName().equals(SocketState.class.getName())) {
            setComment(SocketState.checkBelonging((SocketState) tableEntries, ap));
            setPicture(SocketState.checkBelonging((SocketState) tableEntries, ap));
        } else if (tableEntries.getClass().getName().equals(RadiatorState.class.getName())) {
            setComment(RadiatorState.checkBelonging((RadiatorState) tableEntries, ap));
            setPicture(RadiatorState.checkBelonging((RadiatorState) tableEntries, ap));
        } else if (tableEntries.getClass().getName().equals(FridgeState.class.getName())) {
            setComment(FridgeState.findByKitchenAndAP(ap.getKitchen(), ap));
            setPicture(FridgeState.findByKitchenAndAP(ap.getKitchen(), ap));
        } else if (tableEntries.getClass().getName().equals(OvenState.class.getName())) {
            setComment(OvenState.findByKitchenAndAP(ap.getKitchen(), ap));
            setPicture(OvenState.findByKitchenAndAP(ap.getKitchen(), ap));
        } else if (tableEntries.getClass().getName().equals(VentilationState.class.getName())) {
            setComment(VentilationState.findByKitchenAndAP(ap.getKitchen(), ap));
            setPicture(VentilationState.findByKitchenAndAP(ap.getKitchen(), ap));
        } else if (tableEntries.getClass().getName().equals(CutleryState.class.getName())) {
            setComment(CutleryState.findByKitchenAndAP(ap.getKitchen(), ap));
            setPicture(CutleryState.findByKitchenAndAP(ap.getKitchen(), ap));
        } else if (tableEntries.getClass().getName().equals(CupboardState.class.getName())) {
            setComment(CupboardState.findByKitchenAndAP(ap.getKitchen(), ap));
            setPicture(CupboardState.findByKitchenAndAP(ap.getKitchen(), ap));
        } else if (tableEntries.getClass().getName().equals(ShowerState.class.getName())) {
            setComment(ShowerState.findByBathroomAndAP(ap.getBathroom(), ap));
            setPicture(ShowerState.findByBathroomAndAP(ap.getBathroom(), ap));
        } else if (tableEntries.getClass().getName().equals(BalconyState.class.getName())) {
            setComment(BalconyState.findByApartmentAndAP(ap.getApartment(), ap));
            setPicture(BalconyState.findByApartmentAndAP(ap.getApartment(), ap));
        } else if (tableEntries.getClass().getName().equals(BasementState.class.getName())) {
            setComment(BasementState.findByApartmentAndAP(ap.getApartment(), ap));
            setPicture(BasementState.findByApartmentAndAP(ap.getApartment(), ap));
        }
    }

    /**
     * gets the comment for this entry for a specific year
     * @param tableEntries
     */
    private void setComment(EntryStateInterface tableEntries) {
        comment = null;
        comment = tableEntries.getCommentAtPosition(pos);
    }

    /**
     * gets the picture for this entry for a specific year
     * @param tableEntries
     */
    private void setPicture(EntryStateInterface tableEntries) {
        picture = null;
        if (null != tableEntries.getPictureAtPosition(pos)) {
            picture = PersonalSerializer.getImage(tableEntries.getPictureAtPosition(pos));
        }
    }

    /**
     * gets the date of the AP
     * @param ap
     */
    private void setDate(AP ap) {
        date = ap.getDay() + "." + ap.getMonth() + "." + ap.getYear();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ivPicture = (ImageView)findViewById(R.id.picture1);

        Bitmap bmPicture = (Bitmap) data.getExtras().get("data");
        // save picture to DB
        tableEntries.savePicture(pos, PersonalSerializer.getBytes(bmPicture));
        // show picture on device
        //ivPicture.setImageBitmap(bmPicture);
        fillInDateAndComment();
    }

    @Override
    public void onBackPressed() {

    }

    /**
     * get the data from edit activity
     */
    private void intentReceiver() {
        Intent intent = getIntent();
        currentAP = AP.findById(intent.getLongExtra("AP", 000000));
        pos = intent.getIntExtra("position", 00000);
        parent = intent.getIntExtra("parent", 000);
        child = intent.getStringExtra("child");
        tableEntries = currentAP.getLastOpend();
        Log.d("last opend", tableEntries.getClass().getName());
    }
}
