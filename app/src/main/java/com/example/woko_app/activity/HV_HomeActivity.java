package com.example.woko_app.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;


import com.cete.dynamicpdf.Document;
import com.example.woko_app.constants.ApartmentType;
import com.example.woko_app.R;
import com.example.woko_app.adapter.ExpandableListAdapter;
import com.example.woko_app.fragment.CreateProtocolFragment;
import com.example.woko_app.fragment.DuplicateFragment;
import com.example.woko_app.fragment.MainFragment;
import com.example.woko_app.fragment.ShowOldFragment;
import com.example.woko_app.models.AP;
import com.example.woko_app.models.Apartment;
import com.example.woko_app.models.House;
import com.example.woko_app.models.PDFCreator;
import com.example.woko_app.models.PersonalSerializer;
import com.example.woko_app.models.Room;
import com.example.woko_app.models.User;

/**
 * Created by camillagretsch on 05.09.16.
 */
public class HV_HomeActivity extends Activity {

    private ArrayList<String> parentItems = new ArrayList<>();
    private ArrayList<Object> childItems = new ArrayList<>();
    private static int prev = -1;

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
    private MainFragment mainFragment;
    private CreateProtocolFragment createProtocolFragment;
    private DuplicateFragment duplicateFragment;
    private ShowOldFragment showOldFragment;
    private Fragment openFragment;


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

    @Override
    protected void onPause() {
        super.onPause();
        if (getFragmentManager().getBackStackEntryCount() != 0) {
            int index = getFragmentManager().getBackStackEntryCount() - 1;
            FragmentManager.BackStackEntry backEntry = getFragmentManager().getBackStackEntryAt(index);
            String tag = backEntry.getName();

            switch (tag) {
                case "main":
                    callMainFragment();
                    break;
                case "create":
                    callMainFragment();
                    break;
            }
        }
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
    private void generateSideView() {
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

        final ExpandableListView expandableListView = new ExpandableListView(this);
        expandableListView.setDivider(new ColorDrawable(this.getResources().getColor(R.color.black)));
        expandableListView.setDividerHeight(3);
        expandableListView.setChildDivider(new ColorDrawable(this.getResources().getColor(R.color.black)));
        expandableListView.setClickable(true);

        ExpandableListAdapter adapter = new ExpandableListAdapter(parentItems, childItems, R.layout.home_expandablelist_parent, R.layout.home_expandablelist_child, null, null);
        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
        expandableListView.setAdapter(adapter);
        // expand group listener takes care that only one group ist expanded at the time
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (prev != groupPosition) {
                    expandableListView.collapseGroup(prev);
                }
                prev = groupPosition;
            }
        });
        // child click listener opens the main fragment or create protocol fragment
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                v.setSelected(true);
                Log.d("onClick:", "child at position: " + childPosition + " is chlicked");

                currentApartment = currentHouse.getApartments().get(groupPosition);
                currentRoom = currentApartment.getRooms().get(childPosition);

                if (!(currentRoom.getAPs().isEmpty())) {
                    Log.d("number of APs: ", String.valueOf(AP.findByRoom(currentRoom).size()));
                    //currentAP = AP.findByRoom(currentRoom).get(0);
                    currentAP = AP.findByRoom(currentRoom).get(AP.findByRoom(currentRoom).size() - 1);
                    callMainFragment();
                } else {
                    currentAP = null;
                    callCreateProtocolFragment();
                }
                return true;
            }
        });
        // collapse group listener close the current open fragment
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                closeOpenFragment();
            }
        });
        sidebarContainer.addView(expandableListView);
    }

    /**
     * creates the list only with parent items
     */
    private void setListView() {
        ListView listView = new ListView(this);
        listView.setDivider(new ColorDrawable(this.getResources().getColor(R.color.black)));
        listView.setDividerHeight(3);

        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.home_listview, parentItems);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                Log.d("onClick:", "child at position: " + position + " is chlicked");
                currentApartment = currentHouse.getApartments().get(position);

                if (!(currentApartment.getAPs().isEmpty())) {
                    Log.d("number of APs: ", String.valueOf(AP.findByApartment(currentApartment).size()));
                    currentAP = AP.findByApartment(currentApartment).get(AP.findByApartment(currentApartment).size() - 1);

                    callMainFragment();
                } else {
                    currentAP = null;
                    callCreateProtocolFragment();
                }
            }
        });
        sidebarContainer.addView(listView);
    }

    /**
     * opens the main fragment
     * this contains a search function for protocols
     * and the newest protocol
     */
    public void callMainFragment() {
        mainFragment = new MainFragment();
        openFragment = mainFragment;
        bundle = new Bundle();
        bundle.putLong("AP_ID", currentAP.getId());
        mainFragment.setArguments(bundle);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mainFragment).addToBackStack("main").commit();
    }

    /**
     * opens the create new protocol fragment
     * this fragmnet is opened, when no protocol for this apartment or room exists
     */
    public void callCreateProtocolFragment() {
        createProtocolFragment = new CreateProtocolFragment();
        openFragment = createProtocolFragment;
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, createProtocolFragment).addToBackStack("create").commit();
    }

    /**
     * open the duplicate fragment
     * when the duplicate button in the main fragment is pressed
     */
    public void callDuplicateFragment() {
        duplicateFragment = new DuplicateFragment();
        openFragment = duplicateFragment;
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
        fragmentTransaction.replace(R.id.fragment_container, duplicateFragment).commit();
    }

    /**
     * open the show old fragment
     * when an old protocol in the main fragment is searched
     */
    public void callShowOldFragment(int year) {
        showOldFragment = new ShowOldFragment();
        openFragment = showOldFragment;
        bundle = new Bundle();
        bundle.putLong("Apartment", currentApartment.getId());
        if (currentRoom != null) {
            bundle.putLong("Room", currentRoom.getId());
        }
        bundle.putInt("Year", year);
        showOldFragment.setArguments(bundle);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, showOldFragment).addToBackStack("old").commit();
    }

    /**
     * close the current open fragment
     */
    public void closeOpenFragment() {
        if (openFragment != null) {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(openFragment).commit();
        }
    }

    /**
     * open the previous fragment
     * @param i
     */
    public void openPreviousFragment(int i) {
        if (getFragmentManager().getBackStackEntryCount() == 0) {

        } else {
            int index = getFragmentManager().getBackStackEntryCount() - i;
            FragmentManager.BackStackEntry backEntry = getFragmentManager().getBackStackEntryAt(index);
            String tag = backEntry.getName();

            switch (tag) {
                case "main":
                    callMainFragment();
                    break;
                case "create":
                    callCreateProtocolFragment();
                    break;
            }
        }
    }

    /**
     * open the edit activity
     * when the edit button is pressed or a new protocol is created
     * @param currentAP_Id
     */
    public void callEditActivity(long currentAP_Id) {
        Intent intent = new Intent(this, HV_EditActivity.class);
        intent.putExtra("Username", currentUser.getUsername());
        intent.putExtra("AP Id", currentAP_Id);
        intent.putExtra("House", currentHouse.getId());
        currentAP = AP.findById(currentAP_Id);
        startActivity(intent);
    }

    /**
     * open the open activity
     * when open button is pressed
     */
    public void callOpenActivity() {
        Intent intent = new Intent(this, OpenActivity.class);
        intent.putExtra("ID", currentAP.getId());
        startActivity(intent);
    }

    /**
     * get the data from login activity
     */
    private void intentReceiver() {
        Intent intent = getIntent();
        currentUser = User.findByUsername(intent.getStringExtra("Username"));
    }

    @Override
    public void onBackPressed() {

    }

    /**
     * logout button is pressed
     * set user to offline
     * opens the login screen
     * @param v
     */
    private void onClickLogout(View v) {

        finish();
        currentUser.setUserOffline(currentUser);
        Log.d("Status: ", currentUser.getName() + " is " + currentUser.getStatus().toString());
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
