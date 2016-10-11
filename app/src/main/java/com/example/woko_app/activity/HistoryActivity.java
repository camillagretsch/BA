package com.example.woko_app.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
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
import com.example.woko_app.models.DbBitmapUtility;
import com.example.woko_app.models.DoorState;
import com.example.woko_app.models.EntryStateInterface;
import com.example.woko_app.models.FloorState;
import com.example.woko_app.models.FridgeState;
import com.example.woko_app.models.FurnitureState;
import com.example.woko_app.models.House;
import com.example.woko_app.models.MattressState;
import com.example.woko_app.models.OvenState;
import com.example.woko_app.models.RadiatorState;
import com.example.woko_app.models.ShowerState;
import com.example.woko_app.models.SocketState;
import com.example.woko_app.models.User;
import com.example.woko_app.models.VentilationState;
import com.example.woko_app.models.WallState;
import com.example.woko_app.models.WindowState;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.BlockingDeque;

public class HistoryActivity extends Activity{

    private Typeface font;

    private Button btnCamera;
    private ImageView ivPicture;
    private TextView txtDate;
    private TextView txtComment;

    private String comment;
    private String date;
    private Bitmap picture;
    private EntryStateInterface tableEntries;

    private AP currentAP;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        intentReceiver();

        //fontawesome
        font = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf");

        btnCamera = (Button)findViewById(R.id.btnCamera);
        btnCamera.setTypeface(font);
        if (tableEntries.getCheckAtPosition(pos) == true && tableEntries.getCheckOldAtPosition(pos) == false) {
            btnCamera.setVisibility(View.INVISIBLE);
        }
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });

        fillInDateAndComment();
    }

    private void fillInDateAndComment() {
        int i = 1;
        AP ap = currentAP;

        while (i < 6) {
            date = String.valueOf(ap.getDay()) + "." + String.valueOf(ap.getMonth()) + "." + ap.getYear();
            setComment(ap);
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
            txtDate.setText(date);
            txtComment.setText(comment);
            ivPicture.setImageBitmap(picture);

            if (ap.getOldAP() != null) {
                ap = ap.getOldAP();
            } else
                break;
            i++;
        }
    }

    private void setComment(AP ap) {
        if (tableEntries.getClass().getName().equals(MattressState.class.getName())) {
            getComment(MattressState.findByRoomAndAP(ap.getRoom(), ap));
            getPicture(MattressState.findByRoomAndAP(ap.getRoom(), ap));
        } else if (tableEntries.getClass().getName().equals(FurnitureState.class.getName())) {
            getComment(FurnitureState.findByRoomAndAP(ap.getRoom(), ap));
            getPicture(FurnitureState.findByRoomAndAP(ap.getRoom(), ap));
        } else if (tableEntries.getClass().getName().equals(WallState.class.getName())) {
            getComment(WallState.checkBelonging((WallState) tableEntries, ap));
            getPicture(WallState.checkBelonging((WallState) tableEntries, ap));
        } else if (tableEntries.getClass().getName().equals(FloorState.class.getName())) {
            getComment(FloorState.checkBelongin((FloorState) tableEntries, ap));
            getPicture(FloorState.checkBelongin((FloorState) tableEntries, ap));
        } else if (tableEntries.getClass().getName().equals(WindowState.class.getName())) {
            getComment(WindowState.checkBelonging((WindowState) tableEntries, ap));
            getPicture(WindowState.checkBelonging((WindowState) tableEntries, ap));
        } else if (tableEntries.getClass().getName().equals(DoorState.class.getName())) {
            getComment(DoorState.checkBelonging((DoorState) tableEntries, ap));
            getPicture(DoorState.checkBelonging((DoorState) tableEntries, ap));
        } else if (tableEntries.getClass().getName().equals(SocketState.class.getName())) {
            getComment(SocketState.checkBelonging((SocketState) tableEntries, ap));
            getPicture(SocketState.checkBelonging((SocketState) tableEntries, ap));
        } else if (tableEntries.getClass().getName().equals(RadiatorState.class.getName())) {
            getComment(RadiatorState.checkBelonging((RadiatorState) tableEntries, ap));
            getPicture(RadiatorState.checkBelonging((RadiatorState) tableEntries, ap));
        } else if (tableEntries.getClass().getName().equals(FridgeState.class.getName())) {
            getComment(FridgeState.findByKitchenAndAP(ap.getKitchen(), ap));
            getPicture(FridgeState.findByKitchenAndAP(ap.getKitchen(), ap));
        } else if (tableEntries.getClass().getName().equals(OvenState.class.getName())) {
            getComment(OvenState.findByKitchenAndAP(ap.getKitchen(), ap));
            getPicture(OvenState.findByKitchenAndAP(ap.getKitchen(), ap));
        } else if (tableEntries.getClass().getName().equals(VentilationState.class.getName())) {
            getComment(VentilationState.findByKitchenAndAP(ap.getKitchen(), ap));
            getPicture(VentilationState.findByKitchenAndAP(ap.getKitchen(), ap));
        } else if (tableEntries.getClass().getName().equals(CutleryState.class.getName())) {
            getComment(CutleryState.findByKitchenAndAP(ap.getKitchen(), ap));
            getPicture(CutleryState.findByKitchenAndAP(ap.getKitchen(), ap));
        } else if (tableEntries.getClass().getName().equals(CupboardState.class.getName())) {
            getComment(CupboardState.findByKitchenAndAP(ap.getKitchen(), ap));
            getPicture(CupboardState.findByKitchenAndAP(ap.getKitchen(), ap));
        } else if (tableEntries.getClass().getName().equals(ShowerState.class.getName())) {
            getComment(ShowerState.findByBathroomAndAP(ap.getBathroom(), ap));
            getPicture(ShowerState.findByBathroomAndAP(ap.getBathroom(), ap));
        } else if (tableEntries.getClass().getName().equals(BalconyState.class.getName())) {
            getComment(BalconyState.findByApartmentAndAP(ap.getApartment(), ap));
            getPicture(BalconyState.findByApartmentAndAP(ap.getApartment(), ap));
        } else if (tableEntries.getClass().getName().equals(BasementState.class.getName())) {
            getComment(BasementState.findByApartmentAndAP(ap.getApartment(), ap));
            getPicture(BasementState.findByApartmentAndAP(ap.getApartment(), ap));
        }
    }

    private void getComment(EntryStateInterface tableEntries) {
        comment = tableEntries.getCommentAtPosition(pos);
    }

    private void getPicture(EntryStateInterface tableEntries) {
        if (null != tableEntries.getPictureAtPosition(pos)) {
            picture = DbBitmapUtility.getImage(tableEntries.getPictureAtPosition(pos));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ivPicture = (ImageView)findViewById(R.id.picture1);

        Bitmap bmPicture = (Bitmap) data.getExtras().get("data");
        // save picture to DB
        tableEntries.savePicture(pos, DbBitmapUtility.getBytes(bmPicture));
        // show picture on device
        ivPicture.setImageBitmap(bmPicture);
    }

    private void intentReceiver() {
        Intent intent = getIntent();
        currentAP = AP.findById(intent.getLongExtra("AP", 0));
        pos = intent.getIntExtra("position", 100);
        tableEntries = currentAP.getLastOpend();
        Log.d("last opend", tableEntries.getClass().getName());
    }
}
