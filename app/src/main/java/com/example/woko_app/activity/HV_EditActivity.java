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
import com.example.woko_app.constants.ApartmentType;
import com.example.woko_app.fragment.DataGridFragment;
import com.example.woko_app.models.AP;
import com.example.woko_app.models.Apartment;
import com.example.woko_app.models.House;
import com.example.woko_app.models.Room;
import com.example.woko_app.models.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HV_EditActivity extends Activity {

    private ArrayList<String> parentItems;
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
    private DataGridFragment dataGridFragment;

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
            parentItems = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.shared_apartment_parent)));
            setChildDataSharedApartment();
        } else {
            parentItems = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.studio_parent)));
            setChildDataStudio();
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
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                v.setSelected(true);
                Log.d("parent at position: ", String.valueOf(groupPosition) + " is clicked and child at position: " + String.valueOf(childPosition));
                List<String> children;
                if ((ApartmentType.SHARED_APARTMENT.equals(currentApartment.getType()) && groupPosition == 0) || (ApartmentType.STUDIO.equals(currentApartment.getType()) && groupPosition == 2)) {
                    children = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.room_children)));
                    children.addAll(Arrays.asList(getResources().getStringArray(R.array.children)));
                    callDatagridFargment(groupPosition, children.get(childPosition));
                    btnNext.setVisibility(View.VISIBLE);
                } else if (ApartmentType.STUDIO.equals(currentApartment.getType()) && groupPosition == 0) {
                    children = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.kitchen_children)));
                    children.addAll(Arrays.asList(getResources().getStringArray(R.array.children)));
                    callDatagridFargment(groupPosition, children.get(childPosition));
                    btnNext.setVisibility(View.VISIBLE);
                } else if (ApartmentType.STUDIO.equals(currentApartment.getType()) && groupPosition == 1) {
                    children = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.bathroom_children)));
                    children.addAll(Arrays.asList(getResources().getStringArray(R.array.children)));
                    callDatagridFargment(groupPosition, children.get(childPosition));
                    btnBack.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                v.setSelected(true);
                if (ApartmentType.SHARED_APARTMENT.equals(currentApartment.getType())) {

                } else {
                    if (groupPosition == 3) {
                        callDatagridFargment(groupPosition, "Balkon");
                        btnBack.setVisibility(View.VISIBLE);
                        btnNext.setVisibility(View.VISIBLE);
                    } else if (groupPosition == 4) {
                        callDatagridFargment(groupPosition, "Kellerabteil");
                        btnNext.setVisibility(View.VISIBLE);
                        btnBack.setVisibility(View.VISIBLE);
                    }
                }
                return false;
            }
        });
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                closeDataGridFragment();
                btnBack.setVisibility(View.INVISIBLE);
                btnNext.setVisibility(View.INVISIBLE);
            }
        });
        sidebarContainer.addView(expandableListView);
    }

    /**
     *
     */
    private void setChildDataStudio() {

        // kitchen
        ArrayList<String> child = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.kitchen_children)));
        child.addAll(Arrays.asList(getResources().getStringArray(R.array.children)));
        childItems.add(child);

        // bathroom
        child = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.bathroom_children)));
        child.addAll(Arrays.asList(getResources().getStringArray(R.array.children)));
        childItems.add(child);

        // room
        child = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.room_children)));
        child.addAll(Arrays.asList(getResources().getStringArray(R.array.children)));
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

    private void setChildDataSharedApartment() {

        // room
        ArrayList<String> child = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.room_children)));
        child.addAll(Arrays.asList(getResources().getStringArray(R.array.children)));
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


    public void callDatagridFargment(int parent, String child) {
        dataGridFragment = new DataGridFragment(font);
        bundle = new Bundle();
        bundle.putLong("AP", currentAP.getId());
        bundle.putInt("Parent", parent);
        bundle.putString("Child", child);
        dataGridFragment.setArguments(bundle);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.datagrid_container, dataGridFragment, null).addToBackStack(null).commit();
    }

    public void closeDataGridFragment() {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(dataGridFragment).commit();
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
