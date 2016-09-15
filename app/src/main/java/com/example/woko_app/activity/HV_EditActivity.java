package com.example.woko_app.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.woko_app.ExpandableListAdapter;
import com.example.woko_app.R;
import com.example.woko_app.constants.APStatus;
import com.example.woko_app.constants.ApartmentType;
import com.example.woko_app.fragment.DataGridFragment;
import com.example.woko_app.models.AP;
import com.example.woko_app.models.Apartment;
import com.example.woko_app.models.House;
import com.example.woko_app.models.Room;
import com.example.woko_app.models.User;

import java.util.ArrayList;

public class HV_EditActivity extends Activity {

    private ArrayList<String> parentItems = new ArrayList<>();
    private ArrayList<Object> childItems = new ArrayList<>();

    private Typeface font;

    private TextView usericon;
    private TextView txtName;
    private TextView txtRole;

    private Button btnNext;
    private Button btnBack;

    private User currentUser;
    private AP currentAP;
    private Apartment currentApartment;
    private House currentHouse;
    private Room currentRoom;
    private String apStatus;

    private RelativeLayout sidebarContainer;
    private TableLayout table;

    private FragmentManager fragmentManager;
    private Bundle bundle;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hv_edit);

        //fontawesome
        font = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf");

        intentReceiver();

        currentApartment = currentAP.getApartment();

        sidebarContainer = (RelativeLayout)findViewById(R.id.list_holder);

        fragmentManager = getFragmentManager();

        setHeader();

        btnBack = (Button)findViewById(R.id.btnBack);
        btnBack.setTypeface(font);

        btnNext = (Button)findViewById(R.id.btnNext);
        btnNext.setTypeface(font);

        generateSideView();
    }

    /**
     *
     */
    private void setHeader() {
        usericon = (TextView)findViewById(R.id.usericon);
        usericon.setTypeface(font);

        txtName = (TextView)findViewById(R.id.txtName);
        txtName.setText(currentUser.getName());

        txtRole = (TextView)findViewById(R.id.txtRole);
        txtRole.setText(currentUser.getRole().toString().replace(currentUser.getRole().toString().substring(1), currentUser.getRole().toString().substring(1).toLowerCase()));

    }

    public void generateSideView() {
        if (ApartmentType.SHARED_APARTMENT.equals(currentHouse.getApartments().get(0).getType())) {
            currentRoom = currentAP.getRoom();
            setParentDataSharedApartment();
        } else {
            setParentDataStudio();
        }
        setExpandableListView();
    }

    /**
     * creates the expandable list with children items
     */
    private void setExpandableListView() {
        final ExpandableListView expandableListView = new ExpandableListView(this);
        expandableListView.setDividerHeight(3);
        expandableListView.setGroupIndicator(null);
        expandableListView.setClickable(true);

        ExpandableListAdapter adapter = new ExpandableListAdapter(parentItems, childItems, R.layout.edit_expandablelist_parent, R.layout.edit_expandablelist_child, font);
        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
        expandableListView.setAdapter(adapter);

        sidebarContainer.addView(expandableListView);
    }

    /**
     * fills the list with the number of apartments
     */
    private void setParentDataStudio() {
        parentItems.add(getResources().getString(R.string.kitchen));
        parentItems.add(getResources().getString(R.string.bathroom));
        parentItems.add(getResources().getString(R.string.room));
        parentItems.add(getResources().getString(R.string.balcony));
        parentItems.add(getResources().getString(R.string.basement));
        parentItems.add(getResources().getString(R.string.personal_data));
        parentItems.add(getResources().getString(R.string.save));
        parentItems.add(getResources().getString(R.string.stop));
        setChildDataStudio();
    }

    /**
     * fills the list with the number of rooms
     */
    private void setChildDataStudio() {

        // kitchen
        ArrayList<String> child = new ArrayList<>();
        child.add(getResources().getString(R.string.fridge));
        child.add(getResources().getString(R.string.oven));
        child.add(getResources().getString(R.string.ventialtion));
        child.add(getResources().getString(R.string.cutlery));
        child.add(getResources().getString(R.string.cupboard));
        addStandardToChild(child);
        child.add(getResources().getString(R.string.radiator));
        childItems.add(child);

        // bathroom
        child = new ArrayList<>();
        child.add(getResources().getString(R.string.toilet));
        addStandardToChild(child);
        childItems.add(child);

        // room
        setChildDataSharedApartment();

        // balcony
        child = new ArrayList<>();
        childItems.add(child);

        // basement
        child = new ArrayList<>();
        childItems.add(child);

        // personal data
        child = new ArrayList<>();
        childItems.add(child);

        // save
        child = new ArrayList<>();
        childItems.add(child);

        // stop
        child = new ArrayList<>();
        childItems.add(child);
    }

    private void setParentDataSharedApartment() {
        parentItems.add(getResources().getString(R.string.room));
        parentItems.add(getResources().getString(R.string.personal_data));
        parentItems.add(getResources().getString(R.string.save));
        parentItems.add(getResources().getString(R.string.stop));
        setChildDataSharedApartment();
    }

    private void setChildDataSharedApartment() {

        // room
        ArrayList<String> child = new ArrayList<>();
        child.add(getResources().getString(R.string.matress));
        child.add(getResources().getString(R.string.furniture));
        addStandardToChild(child);
        child.add(getResources().getString(R.string.radiator));
        childItems.add(child);

        // personal data
        child = new ArrayList<>();
        childItems.add(child);

        // save
        child = new ArrayList<>();
        childItems.add(child);

        // stop
        child = new ArrayList<>();
        childItems.add(child);
    }


    private ArrayList<String> addStandardToChild(ArrayList<String> child) {
        child.add(getResources().getString(R.string.wall));
        child.add(getResources().getString(R.string.floor));
        child.add(getResources().getString(R.string.window));
        child.add(getResources().getString(R.string.door));
        child.add(getResources().getString(R.string.socket));
        return child;
    }

    public void callDatagridFargment(int parent, int child) {
        DataGridFragment dataGridFragment = new DataGridFragment(font);
        bundle = new Bundle();

        dataGridFragment.setArguments(bundle);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.datagrid_container, dataGridFragment, null).addToBackStack(null).commit();
    }

    /**
     * get the data form home activity
     */
    private void intentReceiver() {
        Intent intent = getIntent();
        currentUser = User.findByUsername(intent.getStringExtra("Username"));
        currentAP = AP.findById(intent.getLongExtra("AP Id", 0));
        currentHouse = House.findById(intent.getLongExtra("House", 0));
    }

}
