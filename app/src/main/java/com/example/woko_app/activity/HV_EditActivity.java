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

import com.example.woko_app.ExpandableListAdapter;
import com.example.woko_app.R;
import com.example.woko_app.constants.ApartmentType;
import com.example.woko_app.fragment.DataGridFragment;
import com.example.woko_app.fragment.PersonalDataFragment;
import com.example.woko_app.fragment.SaveFragment;
import com.example.woko_app.models.AP;
import com.example.woko_app.models.Apartment;
import com.example.woko_app.models.House;
import com.example.woko_app.models.Room;
import com.example.woko_app.models.User;

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
    private AP unsavedAP;
    private Apartment currentApartment;
    private House currentHouse;
    private Room currentRoom;

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

        //fontawesome
        font = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf");

        intentReceiver();

        currentApartment = currentAP.getApartment();
        unsavedAP = currentAP;
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
                //1. Balcony, 2. Basement, 3. Personal Data, 4. Save, 5. Stop
                if (ApartmentType.STUDIO.equals(currentApartment.getType()) && groupPosition == 3) {
                    callDatagridFargment(groupPosition, "Balkon");
                    btnBack.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.VISIBLE);
                } else if (ApartmentType.STUDIO.equals(currentApartment.getType()) && groupPosition == 4) {
                    callDatagridFargment(groupPosition, "Kellerabteil");
                    btnNext.setVisibility(View.VISIBLE);
                    btnBack.setVisibility(View.VISIBLE);
                } else if ((ApartmentType.STUDIO.equals(currentApartment.getType()) && groupPosition == 5) || (ApartmentType.SHARED_APARTMENT.equals(currentApartment.getType()) && groupPosition == 1)) {
                    Log.d("Personal data of", currentAP.getName() + "is opened" );
                    callPersonalDataFragment();
                    btnBack.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.VISIBLE);
                } else if ((ApartmentType.STUDIO.equals(currentApartment.getType()) && groupPosition == 6) || (ApartmentType.SHARED_APARTMENT.equals(currentApartment.getType()) && groupPosition == 2)) {
                    callSaveFragment();
                    Toast toast = Toast.makeText(getBaseContext(), getResources().getText(R.string.saved), Toast.LENGTH_LONG);
                    LinearLayout toastLayout = (LinearLayout) toast.getView();
                    TextView toastText = (TextView) toastLayout.getChildAt(0);
                    toastText.setTextSize(25);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                    btnBack.setVisibility(View.VISIBLE);
                    btnNext.setText(getResources().getText(R.string.logout_btn));
                    btnNext.setVisibility(View.VISIBLE);
                } else if ((ApartmentType.STUDIO.equals(currentApartment.getType()) && groupPosition == 7) || (ApartmentType.SHARED_APARTMENT.equals(currentApartment.getType()) && groupPosition == 3)) {
                    setAPtoUnsavedAP();
                }
                return false;
            }
        });
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                closeOpenFragment();
                btnBack.setVisibility(View.INVISIBLE);
                btnNext.setVisibility(View.INVISIBLE);
            }
        });
        sidebarContainer.addView(expandableListView);
    }

    /**
     *
     */
    public void setChildDataStudio() {

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

    public void setChildDataSharedApartment() {

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
        openFragment = dataGridFragment;
        bundle = new Bundle();
        bundle.putLong("AP", currentAP.getId());
        bundle.putInt("Parent", parent);
        bundle.putString("Child", child);
        dataGridFragment.setArguments(bundle);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.datagrid_container, dataGridFragment, null).addToBackStack(null).commit();
    }

    public void callPersonalDataFragment() {
        personalDataFragment = new PersonalDataFragment();
        openFragment = personalDataFragment;
        bundle = new Bundle();
        bundle.putLong("AP", currentAP.getId());
        personalDataFragment.setArguments(bundle);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.datagrid_container, personalDataFragment, null).addToBackStack(null).commit();
    }

    public void callSaveFragment() {
        saveFragment = new SaveFragment(font);
        openFragment = saveFragment;
        bundle = new Bundle();
        bundle.putString("Username", currentUser.getUsername());
        bundle.putLong("AP", currentAP.getId());
        saveFragment.setArguments(bundle);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.datagrid_container, saveFragment, null).addToBackStack(null).commit();
    }

    public void setAPtoUnsavedAP() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        final TextView textView = new TextView(this);
        textView.setText(getResources().getText(R.string.stop_message));
        textView.setGravity(Gravity.CENTER);
        alertDialogBuilder.setView(textView);

        AlertDialog.Builder builder = alertDialogBuilder.setCancelable(false).setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO AP zurücksetzen
                currentAP = unsavedAP;
                currentAP.save();
                callHomeActivity();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void callHomeActivity() {
        Intent intent = new Intent(this, HV_HomeActivity.class);
        intent.putExtra("Username", currentUser.getUsername());
        startActivity(intent);
    }

    public void closeOpenFragment() {
        if (openFragment != null) {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(openFragment).commit();
        }
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
