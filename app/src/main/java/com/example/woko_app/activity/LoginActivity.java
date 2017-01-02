package com.example.woko_app.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.example.woko_app.models.AP;
import com.example.woko_app.models.Apartment;
import com.example.woko_app.models.BalconyState;
import com.example.woko_app.models.BasementState;
import com.example.woko_app.models.Bathroom;
import com.example.woko_app.models.CupboardState;
import com.example.woko_app.models.CutleryState;
import com.example.woko_app.models.PersonalSerializer;
import com.example.woko_app.models.DoorState;
import com.example.woko_app.models.FloorState;
import com.example.woko_app.models.FridgeState;
import com.example.woko_app.models.FurnitureState;
import com.example.woko_app.models.House;
import com.example.woko_app.models.Kitchen;
import com.example.woko_app.models.MattressState;
import com.example.woko_app.models.OvenState;
import com.example.woko_app.models.RadiatorState;
import com.example.woko_app.models.Room;
import com.example.woko_app.models.ShowerState;
import com.example.woko_app.models.SocketState;
import com.example.woko_app.models.User;
import com.example.woko_app.R;
import com.example.woko_app.models.VentilationState;
import com.example.woko_app.models.WallState;
import com.example.woko_app.models.WindowState;

import java.util.List;

public class LoginActivity extends Activity {

    private Button btnLogin;

    private EditText etUsername;
    private EditText etPassword;

    private Typeface fontawesome;


    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeDB();

        //fontawesome
        fontawesome = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf");

        etUsername = (EditText)findViewById(R.id.etUsername_login);
        etPassword = (EditText)findViewById(R.id.etPassword);
        btnLogin = (Button)findViewById(R.id.login_btn);

        btnLogin.setTypeface(fontawesome);
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onClickLogin(v);
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    /**
     * when username and passowrd is correct set user to online
     * opens the right home screen depends of the user type
     * @param v
     */
    private void onClickLogin(View v) {

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        currentUser = User.findByUsername(username);

        if (currentUser != null && currentUser.getPassword().equals(password)) {

            currentUser.setUserOnline(currentUser);
            Log.d("Status: ", currentUser.getName() + " is " + currentUser.getStatus().toString());

            Intent intent;

            switch (currentUser.getRole()) {
                case HAUSVERANTWORTLICHER:
                    intent = new Intent(this, HV_HomeActivity.class);
                    intent.putExtra("Username", currentUser.getUsername());
                    startActivity(intent);
                    break;
                case HAUSWART:
                    break;
                case VERWALTER:
                    break;
            }
        } else {
            Toast toast = Toast.makeText(getBaseContext(), R.string.wrong_entry, Toast.LENGTH_LONG);

            LinearLayout toastlayout = (LinearLayout) toast.getView();
            TextView txtToast = (TextView) toastlayout.getChildAt(0);
            txtToast.setTextSize(25);
            toast.show();
        }
    }

    /**
     * fills the DB with start data
     */
    private void initializeDB() {
        String newEx = getResources().getString(R.string.exclamation_mark_new);
        Bitmap bitmap = null;
        ActiveAndroid.dispose();
        ActiveAndroid.initialize(this);
        List<User> users = User.initializeUsers();
        List<House> houses = House.initializeHouses(users);
        List<Apartment> apartments = Apartment.initializeApartments(houses);
        Room.initializeRooms(apartments);
        Bathroom.initializeBathroom(apartments);
        Kitchen.initializeKitchen(apartments);
        List<AP> aps = AP.initializeAPs(houses);
        FloorState.initializeRoomFloor(aps, newEx);
        FloorState.initializeBathroomFloor(aps, newEx);
        FloorState.initializeKitchenFloor(aps);
        WallState.initializeRoomWall(aps, newEx);
        bitmap = ((BitmapDrawable)getResources().getDrawable(R.drawable.kitchen_wall_spot)).getBitmap();
        WallState.initializeBathroomWall(aps, PersonalSerializer.getBytes(bitmap), newEx);
        WallState.initializeKitchenWall(aps, PersonalSerializer.getBytes(bitmap), newEx);
        DoorState.initializeRoomDoor(aps, newEx);
        DoorState.initializeBathroomDoor(aps);
        DoorState.initializeKitchenDoor(aps, newEx);
        WindowState.initializeRoomWindow(aps);
        WindowState.initializeBathroomWindow(aps);
        WindowState.initializeKitchenWindow(aps, newEx);
        SocketState.initializeRoomSocket(aps, newEx);
        SocketState.initializeBathroomSocket(aps);
        SocketState.initializeKitchenSocket(aps, newEx);
        RadiatorState.initializeRoomRadiator(aps, newEx);
        RadiatorState.initializeBathroomRadiator(aps);
        RadiatorState.initializeKitchenRadiator(aps, newEx);
        MattressState.initializeRoomMattress(aps, newEx);
        bitmap = ((BitmapDrawable)getResources().getDrawable(R.drawable.broken_chair)).getBitmap();
        FurnitureState.initializeRoomFurniture(aps, PersonalSerializer.getBytes(bitmap), newEx, getResources().getString(R.string.exclamation_mark_old));
        ShowerState.initializeBathroomShower(aps, newEx);
        FridgeState.initializeKitchenFridge(aps, newEx);
        OvenState.initializeKitchenOven(aps, newEx);
        bitmap = ((BitmapDrawable)getResources().getDrawable(R.drawable.broken_pan)).getBitmap();
        CutleryState.initializeKitchenCutlery(aps, PersonalSerializer.getBytes(bitmap), newEx);
        bitmap = ((BitmapDrawable)getResources().getDrawable(R.drawable.ventilation_damage)).getBitmap();
        VentilationState.initializeKitchenVentilation(aps, PersonalSerializer.getBytes(bitmap), newEx);
        CupboardState.initializeKitchenCupboard(aps);
        BalconyState.initializeBalcony(aps);
        BasementState.initializeBasement(aps);
    }

}
