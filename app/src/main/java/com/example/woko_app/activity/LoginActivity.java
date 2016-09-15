package com.example.woko_app.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
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
import com.example.woko_app.models.Floor;
import com.example.woko_app.models.House;
import com.example.woko_app.models.Room;
import com.example.woko_app.models.User;
import com.example.woko_app.R;

import java.util.List;

public class LoginActivity extends Activity {

    private Button btnLogin;

    private EditText etUsername;
    private EditText etPassword;

    private Typeface font;


    private User currentUser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeDB();

        //fontawesome
        font = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf");

        etUsername = (EditText)findViewById(R.id.etUsername_login);
        etPassword = (EditText)findViewById(R.id.etPassword);
        btnLogin = (Button)findViewById(R.id.login_btn);

        btnLogin.setTypeface(font);
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onClickLogin(v);
            }
        });
    }

    /**
     * when username and passowrd is correct set user to online
     * opens the right home screen depends of the user type
     * @param v
     */
    public void onClickLogin(View v) {

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
            Toast toast = Toast.makeText(getBaseContext(), "falscher Benutzername oder Passwort!", Toast.LENGTH_LONG);

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
        ActiveAndroid.dispose();
        ActiveAndroid.initialize(this);
        List<User> users = User.initializeUsers();
        List<House> houses = House.initializeHouses(users);
        List<Apartment> apartments = Apartment.initializeApartments(houses);
        List<Room> rooms = Room.initializeRooms(apartments);
        AP.initializeAPs(houses);
        Floor.initializeRoomFloor(rooms);
    }

}
