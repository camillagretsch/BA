package com.example.woko_app.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.woko_app.constants.APStatus;
import com.example.woko_app.constants.ApartmentType;
import com.example.woko_app.R;
import com.example.woko_app.ExpandableListAdapter;
import com.example.woko_app.fragment.CreateProtocolFragment;
import com.example.woko_app.fragment.DuplicateFragment;
import com.example.woko_app.fragment.MainFragment;
import com.example.woko_app.fragment.ShowOldFragment;
import com.example.woko_app.models.AP;
import com.example.woko_app.models.Apartment;
import com.example.woko_app.models.House;
import com.example.woko_app.models.Room;
import com.example.woko_app.models.User;

/**
 * Created by camillagretsch on 05.09.16.
 */
public class HV_HomeActivity extends Activity {

    private ArrayList<String> parentItems = new ArrayList<>();
    private ArrayList<Object> childItems = new ArrayList<>();


    private TextView userIcon;
    private TextView txtName;
    private TextView txtRole;
    private TextView txtStreet;
    private TextView txtStreetnr;
    private TextView txtPLZ;
    private TextView txtTown;

    private Button btnLogout;

    private User currentUser;
    private House currentHouse;
    private AP currentAP;
    private Apartment currentApartment;
    private Room currentRoom;

    private Typeface font;

    private RelativeLayout sidebarContainer;

    private FragmentManager fragmentManager;
    private Bundle bundle;
    private FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_hv);
        sidebarContainer = (RelativeLayout)findViewById(R.id.list_holder);

        intentReceiver();

        //fontawesome
        font = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf");

        fragmentManager = getFragmentManager();

        setHeader();

        generateSideView();
    }

    /**
     * creates the usericon
     * fills the Textboxes with the name, usertype and adress
     * creates the logout button
     * woko logo
     */
    private void setHeader() {
        userIcon = (TextView)findViewById(R.id.usericon);
        userIcon.setTypeface(font);

        txtName = (TextView)findViewById(R.id.txtName);
        txtName.setText(currentUser.getName());

        txtRole = (TextView)findViewById(R.id.txtRole);
        txtRole.setText(currentUser.getRole().toString().replace(currentUser.getRole().toString().substring(1), currentUser.getRole().toString().substring(1).toLowerCase()));

        txtStreet = (TextView)findViewById(R.id.txtStreet);
        txtStreet.setText(currentUser.getHousesHV().get(0).getStreet());

        txtStreetnr = (TextView)findViewById(R.id.txtStreetNr);
        txtStreetnr.setText(String.valueOf(currentUser.getHousesHV().get(0).getStreetNumber()));

        txtPLZ = (TextView)findViewById(R.id.txtPLZ);
        txtPLZ.setText(String.valueOf(currentUser.getHousesHV().get(0).getPLZ()));

        txtTown = (TextView)findViewById(R.id.txtTown);
        txtTown.setText(currentUser.getHousesHV().get(0).getTown());

        btnLogout = (Button)findViewById(R.id.btnLogout);
        btnLogout.setTypeface(font);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLogout(v);
            }
        });
    }

    /**
     * creates the list on the side depends on the apartment type
     * shared room: expandablelistview
     * studio: listview
     */
    public void generateSideView() {
        currentHouse = House.findByHV(currentUser);

        setParentData();

        if (ApartmentType.SHARED_APARTMENT.equals(currentHouse.getApartments().get(0).getType())) {
            setExpandableListView();
        } else
            setListView();

    }

    /**
     * fills the list with the number of apartments
     */
    private void setParentData() {
        for (int i = 0; i < currentHouse.getApartments().size(); i++) {
            currentApartment = currentHouse.getApartments().get(i);
            parentItems.add("Wohnung " + String.valueOf(currentApartment.getApartmentNumber()));

            if (ApartmentType.SHARED_APARTMENT.equals(currentApartment.getType())) {
                setChildData();
            }
        }
    }

    /**
     * fills the list with the number of rooms
     */
    private void setChildData() {
        ArrayList<String> child = new ArrayList<>();
            for (int i = 0; i < currentApartment.getRooms().size(); i++) {
                Room room = currentApartment.getRooms().get(i);
                child.add("Zimmer " + String.valueOf(room.getRoomNumber()));
            }
        childItems.add(child);
    }

    /**
     * creates the expandable list with children items
     */
    private void setExpandableListView() {

        ExpandableListView expandableListView = new ExpandableListView(this);
        expandableListView.setDividerHeight(3);
        expandableListView.setClickable(true);

        ExpandableListAdapter adapter = new ExpandableListAdapter(parentItems, childItems, R.layout.home_expandablelist_parent, R.layout.home_expandablelist_child, null);
        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Log.d("onClick:", "child at position: " + childPosition + " is chlicked");

                currentApartment = currentHouse.getApartments().get(groupPosition);
                currentRoom = currentApartment.getRooms().get(childPosition);

                if (!(currentRoom.getAPs().isEmpty())) {
                    Log.d("number of APs: ", String.valueOf(AP.findByRoom(currentRoom).size()));
                    currentAP = AP.findByRoom(currentRoom).get(0);

                    callMainFragment();
                } else {
                    currentAP = null;
                    callCreateProtocolFragment();
                }
                return true;
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });
        sidebarContainer.addView(expandableListView);
    }

    /**
     * creates the list only with parent items
     */
    private void setListView() {
        View v = new View(this);
        v.setMinimumHeight(3);
        v.setMinimumWidth(sidebarContainer.getWidth());
        v.setBackgroundColor(R.color.black);

        ListView listView = new ListView(this);
        //listView.setFooterDividersEnabled(false);
        listView.addFooterView(v);
        ColorDrawable black = new ColorDrawable(this.getResources().getColor(R.color.black));
        listView.setDivider(black);
        listView.setDividerHeight(3);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.home_listview, parentItems);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("onClick:", "child at position: " + position + " is chlicked");
                currentApartment = currentHouse.getApartments().get(position);

                if (!(currentApartment.getAPs().isEmpty())) {
                    Log.d("number of APs: ", String.valueOf(AP.findByApartment(currentApartment).size()));
                    currentAP = AP.findByApartment(currentApartment).get(0);

                    callMainFragment();
                } else {
                    currentAP = null;
                    callCreateProtocolFragment();
                }
            }
        });
        sidebarContainer.addView(listView);
    }

    public void callMainFragment() {
        MainFragment mainFragment = new MainFragment(font);
        bundle = new Bundle();
        bundle.putLong("AP_ID", currentAP.getId());
        mainFragment.setArguments(bundle);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mainFragment, null).addToBackStack(null).commit();
    }

    public void callCreateProtocolFragment() {
        CreateProtocolFragment createProtocolFragment = new CreateProtocolFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, createProtocolFragment, null).addToBackStack(null).commit();
    }

    public void callDuplicateFragment() {
        DuplicateFragment duplicateFragment = new DuplicateFragment(font);
        bundle = new Bundle();
        bundle.putLong("House", currentHouse.getId());
        bundle.putLong("Apartment", currentApartment.getId());
        if (currentRoom != null) {
            bundle.putLong("Room", currentRoom.getId());
        }
        if (currentAP != null) {
            bundle.putLong("Old AP", currentAP.getId());
        }
        duplicateFragment.setArguments(bundle);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, duplicateFragment, null).addToBackStack(null).commit();
    }

    public void callShowOldFragment(int year) {
        ShowOldFragment showOldFragment = new ShowOldFragment(font);
        bundle = new Bundle();
        bundle.putLong("Apartment", currentApartment.getId());
        if (currentRoom != null) {
            bundle.putLong("Room", currentRoom.getId());
        }
        bundle.putInt("Year", year);
        showOldFragment.setArguments(bundle);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, showOldFragment, null).addToBackStack(null).commit();
    }

    public void callEditActivity(long currentAP_Id) {
        Intent intent = new Intent(this, HV_EditActivity.class);
        intent.putExtra("Username", currentUser.getUsername());
        intent.putExtra("AP Id", currentAP_Id);
        intent.putExtra("House", currentHouse.getId());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        int fragments = getFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            finish();
        }
        super.onBackPressed();
    }

    /**
     * get the data form login activity
     */
    private void intentReceiver() {
        Intent intent = getIntent();
        currentUser = User.findByUsername(intent.getStringExtra("Username"));
    }

    /**
     * set user to offline
     * opens the login screen
     * @param v
     */
    public void onClickLogout(View v) {

        currentUser.setUserOffline(currentUser);
        Log.d("Status: ", currentUser.getName() + " is " + currentUser.getStatus().toString());
        Intent intent = new Intent(this, LoginActivity.class);

        startActivity(intent);
    }
}
