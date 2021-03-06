package com.example.woko_app.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woko_app.adapter.ExpandableListAdapter;
import com.example.woko_app.R;
import com.example.woko_app.constants.ApartmentType;
import com.example.woko_app.fragment.DataGridFragment;
import com.example.woko_app.fragment.PersonalDataFragment;
import com.example.woko_app.fragment.SaveFragment;
import com.example.woko_app.models.AP;
import com.example.woko_app.models.Apartment;
import com.example.woko_app.models.BalconyState;
import com.example.woko_app.models.BasementState;
import com.example.woko_app.models.Bathroom;
import com.example.woko_app.models.House;
import com.example.woko_app.models.Kitchen;
import com.example.woko_app.models.Room;
import com.example.woko_app.models.User;

import java.security.interfaces.RSAKey;
import java.util.ArrayList;
import java.util.Arrays;

public class HV_EditActivity extends Activity {

    private ArrayList<String> parentItems = new ArrayList<>();
    private ArrayList<Object> childItems = new ArrayList<>();
    private int prev = -1;
    private int parent;
    private String child;

    private Typeface fontawesome;
    private Typeface fonticon;

    private TextView usericon;
    private TextView txtName;
    private TextView txtRole;
    private TextView txtStreet;
    private TextView txtStreetnr;
    private TextView txtPLZ;
    private TextView txtTown;

    private User currentUser;
    private AP currentAP;
    private Apartment currentApartment;
    private House currentHouse;
    private Room currentRoom;

    private ExpandableListAdapter adapter;

    private RelativeLayout sidebarContainer;
    private DataGridFragment dataGridFragment;
    private PersonalDataFragment personalDataFragment;
    private SaveFragment saveFragment;
    private Fragment openFragment;

    private FragmentManager fragmentManager;
    private Bundle bundle;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hv_edit);

        //typefaces
        fontawesome = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf");
        fonticon = Typeface.createFromAsset(getAssets(), "fonts/Flaticon.ttf");

        intentReceiver();

        currentApartment = currentAP.getApartment();
        sidebarContainer = (RelativeLayout)findViewById(R.id.list_holder);
        fragmentManager = getFragmentManager();

        setHeader();

        generateSideView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        openPreviousFragment();
    }

    /**
     *  set the header in the screen, it contains
     *  name of the user
     *  type of the user
     *  title
     *  address of the protocol
     *  logo
     */
    private void setHeader() {
        usericon = (TextView)findViewById(R.id.usericon);
        usericon.setTypeface(fontawesome);

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
    }

    /**
     * generates the list on side depends on the apartment type
     * set the parent items and add the icons
     */
    private void generateSideView() {
        if (ApartmentType.SHARED_APARTMENT.equals(currentHouse.getApartments().get(0).getType())) {
            currentRoom = currentAP.getRoom();
            setParentItemsShared();
        } else {
            setParentItemsStudio();
        }

        setExpandableListView();
    }

    /**
     * add parent items for the apartment type studio
     */
    private void setParentItemsStudio() {
        String newEx = getResources().getString(R.string.exclamation_mark_new);
        String oldEx = getResources().getString(R.string.exclamation_mark_old);
        parentItems.clear();
        parentItems.add(Kitchen.updateKitchenName(currentAP, newEx, oldEx, getResources().getString(R.string.cutlery_icon)));
        parentItems.add(Bathroom.updateBathroomName(currentAP, newEx, oldEx, getResources().getString(R.string.shower_icon)));
        parentItems.add(Room.updateRoomName(currentAP, newEx, oldEx, getResources().getString(R.string.bed_icon)));
        parentItems.add(BalconyState.updateBalconyName(currentAP, newEx, oldEx, getResources().getString(R.string.balcony_icon)));
        parentItems.add(BasementState.updateBasamentName(currentAP, newEx, oldEx, getResources().getString(R.string.house_icon)));
        parentItems.add(getResources().getString(R.string.personal_data));
        parentItems.add(getResources().getString(R.string.save));
        parentItems.add(getResources().getString(R.string.close));
        setStudioItems();
    }

    /**
     * add parent items for the apartment type shared
     */
    private void setParentItemsShared() {
        String newEx = getResources().getString(R.string.exclamation_mark_new);
        String oldEx = getResources().getString(R.string.exclamation_mark_old);
        parentItems.clear();
        parentItems.add(Room.updateRoomName(currentAP, newEx, oldEx, getResources().getString(R.string.bed_icon)));
        parentItems.add(getResources().getString(R.string.personal_data));
        parentItems.add(getResources().getString(R.string.save));
        parentItems.add(getResources().getString(R.string.close));
        setSharedApartmentItems();
    }

    /**
     * add the children items for the apartment type studio
     */
    private void setStudioItems() {
        childItems.clear();
        // kitchen
        childItems.add(Kitchen.updateKitchenItems(currentAP));
        // bathroom
        childItems.add(Bathroom.updateBathroomItems(currentAP));
        // room
        childItems.add(Room.updateRoomItems(currentAP));
        // balcony
        childItems.add(new ArrayList<>());
        // basement
        childItems.add(new ArrayList<>());
        // personal data
        childItems.add(new ArrayList<>());
        // save
        childItems.add(new ArrayList<>());
        // stop
        childItems.add(new ArrayList<>());
    }

    /**
     * add the children items for the apartment type shared
     */
    public void setSharedApartmentItems() {
        childItems.clear();
        // room
        childItems.add(Room.updateRoomItems(currentAP));
        // personal data
        childItems.add(new ArrayList<>(Arrays.asList("")));
        // save
        childItems.add(new ArrayList<>());
        // stop
        childItems.add(new ArrayList<>());
    }

    /**
     * creates the expandable list with children items
     */
    public void setExpandableListView() {
        final ExpandableListView expandableListView = new ExpandableListView(this);
        expandableListView.setDivider(new ColorDrawable(this.getResources().getColor(R.color.black)));
        expandableListView.setChildDivider(new ColorDrawable(this.getResources().getColor(R.color.black)));
        expandableListView.setDividerHeight(3);
        expandableListView.setGroupIndicator(null);
        expandableListView.setClickable(true);

        adapter = new ExpandableListAdapter(parentItems, childItems, R.layout.edit_expandablelist_parent, R.layout.edit_expandablelist_child, fontawesome, fonticon);
        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (prev != groupPosition) {
                    expandableListView.collapseGroup(prev);
                }
                prev = groupPosition;
            }
        });
        // child click listener
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                v.setSelected(true);
                return setOnClickChild(groupPosition, childPosition);
            }
        });
        // set on group click
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return setOnClickGroup(groupPosition);
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
     * opens the datagrid fragment with different arguments
     * depends on the group and child position
     * @param groupPosition
     * @param childPosition
     * @return
     */
    private boolean setOnClickChild(int groupPosition, int childPosition) {
        Log.d("parent at position: ", String.valueOf(groupPosition) + " is clicked and child at position: " + String.valueOf(childPosition));
        parent = groupPosition;
        if ((ApartmentType.SHARED_APARTMENT.equals(currentApartment.getType()) && groupPosition == 0) || (ApartmentType.STUDIO.equals(currentApartment.getType()) && groupPosition == 2)) {
            child = Room.updateRoomItems(currentAP).get(childPosition);
            callDatagridFargment();
        } else if (ApartmentType.STUDIO.equals(currentApartment.getType()) && groupPosition == 0) {
            child = Kitchen.updateKitchenItems(currentAP).get(childPosition);
            callDatagridFargment();
        } else if (ApartmentType.STUDIO.equals(currentApartment.getType()) && groupPosition == 1) {
            child = Bathroom.updateBathroomItems(currentAP).get(childPosition);
            callDatagridFargment();
        }
        return false;
    }

    /**
     * opens the datagrid fragment, personal data fragment or save fragment
     * depends on the group position
     * @param groupPosition
     * @return
     */
    private boolean setOnClickGroup(int groupPosition) {
        parent = groupPosition;
        //1. Balcony, 2. Basement, 3. Personal Data, 4. Save, 5. Stop
        if (ApartmentType.STUDIO.equals(currentApartment.getType()) && groupPosition == 3) {
            child = "Balkon";
            callDatagridFargment();
            return true;
        } else if (ApartmentType.STUDIO.equals(currentApartment.getType()) && groupPosition == 4) {
            child = "Keller";
            callDatagridFargment();
            return true;
        } else if ((ApartmentType.STUDIO.equals(currentApartment.getType()) && groupPosition == 5) || (ApartmentType.SHARED_APARTMENT.equals(currentApartment.getType()) && groupPosition == 1)) {
            Log.d("Personal data of", currentAP.getName() + "is opened");
            callPersonalDataFragment();
            return true;
        } else if ((ApartmentType.STUDIO.equals(currentApartment.getType()) && groupPosition == 6) || (ApartmentType.SHARED_APARTMENT.equals(currentApartment.getType()) && groupPosition == 2)) {
            callSaveFragment();
            Toast toast = Toast.makeText(getBaseContext(), getResources().getText(R.string.saved), Toast.LENGTH_SHORT);
            LinearLayout toastLayout = (LinearLayout) toast.getView();
            TextView toastText = (TextView) toastLayout.getChildAt(0);
            toastText.setTextSize(25);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
            return true;
        } else if ((ApartmentType.STUDIO.equals(currentApartment.getType()) && groupPosition == 7) || (ApartmentType.SHARED_APARTMENT.equals(currentApartment.getType()) && groupPosition == 3)) {
            closeAP();
            return true;
        }
        return false;
    }

    /**
     * updates the sideview when the content of parent or child items change
     */
    public void updateSideView() {
        if (ApartmentType.SHARED_APARTMENT.equals(currentHouse.getApartments().get(0).getType())) {
            setParentItemsShared();
        } else {
            setParentItemsStudio();
        }
        adapter.notifyDataSetChanged();
        adapter.notifyDataSetInvalidated();
    }

    /**
     * open the datagrid fragment
     */
    public void callDatagridFargment() {
        dataGridFragment = new DataGridFragment();
        openFragment = dataGridFragment;
        bundle = new Bundle();
        bundle.putLong("AP", currentAP.getId());
        bundle.putInt("Parent", parent);
        bundle.putString("Child", child);
        dataGridFragment.setArguments(bundle);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.datagrid_container, dataGridFragment).addToBackStack("datagrid").commit();
    }

    /**
     * open the personal data fragment
     */
    public void callPersonalDataFragment() {
        personalDataFragment = new PersonalDataFragment();
        openFragment = personalDataFragment;
        bundle = new Bundle();
        bundle.putLong("AP", currentAP.getId());
        personalDataFragment.setArguments(bundle);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.datagrid_container, personalDataFragment).addToBackStack("personal").commit();
    }

    /**
     * open the save fragment
     */
    public void callSaveFragment() {
        saveFragment = new SaveFragment();
        openFragment = saveFragment;
        bundle = new Bundle();
        bundle.putString("Username", currentUser.getUsername());
        bundle.putLong("AP", currentAP.getId());
        saveFragment.setArguments(bundle);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.datagrid_container, saveFragment).addToBackStack("save").commit();
    }

    /**
     * ask if ap should be closed
     * if yes back to home activity
     * if no stay in edit activity
     */
    public void closeAP() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        final TextView textView = new TextView(this);
        textView.setText(getResources().getText(R.string.stop_message));
        textView.setTextSize(25);
        textView.setPadding(5, 5, 5, 5);
        textView.setGravity(Gravity.CENTER);
        alertDialogBuilder.setView(textView);

         alertDialogBuilder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callHomeActivity();
            }
        }).setNegativeButton("Nein", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 dialog.dismiss();
             }
         });
        alertDialogBuilder.create().show();
    }

    /**
     * open the history activity
     * when the picture button is pressed
     * @param pos
     */
    public void callHistoryActivity(int pos, int parent, String child) {
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("AP", currentAP.getId());
        intent.putExtra("position", pos);
        intent.putExtra("parent", parent);
        intent.putExtra("child", child);
        startActivity(intent);
    }

    /**
     * open the home activity
     * when the last group item in the expandable listview is pressed
     */
    public void callHomeActivity() {
        finish();
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
     */
    public void openPreviousFragment() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {

        } else {
            int index = getFragmentManager().getBackStackEntryCount() - 1;
            FragmentManager.BackStackEntry backEntry = getFragmentManager().getBackStackEntryAt(index);
            String tag = backEntry.getName();

            switch (tag) {
                case "save":
                    callSaveFragment();
                    break;
                case "personal":
                    callPersonalDataFragment();
                    break;
                case "datagrid":
                    callDatagridFargment();
                    break;
            }
        }
    }

    /**
     * get the data from home activity
     */
    private void intentReceiver() {
        Intent intent = getIntent();
        currentUser = User.findByUsername(intent.getStringExtra("Username"));
        currentAP = AP.findById(intent.getLongExtra("AP Id", 0));
        currentHouse = House.findById(intent.getLongExtra("House", 0));
    }

    @Override
    public void onBackPressed() {

    }
}
