package com.example.woko_app.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.woko_app.activity.HV_HomeActivity;
import com.example.woko_app.constants.ApartmentType;
import com.example.woko_app.models.AP;
import com.example.woko_app.models.Apartment;
import com.example.woko_app.models.Room;

import com.example.woko_app.R;

import java.util.ArrayList;
import java.util.List;

public class ShowOldFragment extends Fragment {

    private List<AP> aps = new ArrayList<>();
    private Apartment currentApartment;
    private Room currentRoom;
    private int year;

    private Button btnBack;

    private Typeface font;

    private LinearLayout fileContainer;

    private AP currentAP;
    private HV_HomeActivity hv_homeActivity;


    public ShowOldFragment() {
        // Required empty public constructor
    }

    public static ShowOldFragment newInstance() {
        ShowOldFragment fragment = new ShowOldFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_old, container, false);

        //fontawesome
        font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/fontawesome-webfont.ttf");

        fileContainer = (LinearLayout) view.findViewById(R.id.fileContainer);

        hv_homeActivity = (HV_HomeActivity) getActivity();

        // set buttons on click listener
        btnBack = (Button) view.findViewById(R.id.btnBack);
        btnBack.setTypeface(font);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hv_homeActivity.openPreviousFragment(2);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();

        if (bundle != null) {
            currentApartment = Apartment.findById(bundle.getLong("Apartment"));
            currentRoom = Room.findById(bundle.getLong("Room"));
            year = bundle.getInt("Year");

        }

        // save all protocols from the selected year in a list
        if (ApartmentType.SHARED_APARTMENT.equals(currentApartment.getType())) {
            aps = AP.findByRoomAndYear(currentRoom, year);
            Log.d("How many files:", String.valueOf(aps.size()));
        } else {
            aps = AP.findByApartmentAndYear(currentApartment, year);
            Log.d("How many files:", String.valueOf(aps.size()));
        }

        // checks if protocols for this year exists
        if (aps.isEmpty()) {
            showMessageNoFilesFound();
        } else
            showFiles();
    }

    /**
     * no protocols are found for this year
     * show a popup window and go bach to the main fragmnet
     */
    private void showMessageNoFilesFound() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        final TextView textView = new TextView(getActivity());
        textView.setText("Es wurde kein Protokoll für das Jahr " + year + " gefunden.");
        textView.setTextSize(25);
        textView.setPadding(5, 5, 5, 5);
        textView.setGravity(Gravity.CENTER);
        alertDialogBuilder.setView(textView);

        // set button on click listener
        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                hv_homeActivity.openPreviousFragment(2);
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /**
     * go through each protocol in the list
     * add an file icon, name, open button and edit button
     */
    private void showFiles() {
        int i = 0;

        for (AP ap : aps) {

            LinearLayout linearLayout = new LinearLayout(new ContextThemeWrapper(getActivity(), R.style.VerticalLayoutStyle), null);
            fileContainer.addView(linearLayout);

            // icon for the file
            TextView fileicon = new TextView(getActivity());
            linearLayout.addView(fileicon);
            fileicon.setText(R.string.file_icon);
            fileicon.setTextSize(60);
            fileicon.setGravity(Gravity.CENTER_HORIZONTAL);
            fileicon.setTypeface(font);

            // name of the file
            TextView fileName = new TextView(new ContextThemeWrapper(getActivity(), R.style.TextViewStyle), null);
            fileName.setText(ap.getName());
            linearLayout.addView(fileName);

            // button to open the protocol
            final Button btnOpen = new Button(new ContextThemeWrapper(getActivity(), R.style.ButtonStyle), null);
            btnOpen.setText(R.string.open_btn);
            btnOpen.setGravity(Gravity.CENTER_HORIZONTAL);
            btnOpen.setTypeface(font);
            btnOpen.setId(i);
            linearLayout.addView(btnOpen);
            btnOpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   setOnClickOpen(btnOpen);
                }
            });

            // button to edit the protocol
            final Button btnEdit = new Button(new ContextThemeWrapper(getActivity(), R.style.ButtonStyle), null);
            btnEdit.setText(R.string.edit_btn);
            btnEdit.setGravity(Gravity.CENTER_HORIZONTAL);
            btnEdit.setTypeface(font);
            btnEdit.setId(i);
            linearLayout.addView(btnEdit);
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setOnClickEdit(btnEdit);
                }
            });

            i++;
        }
    }

    /**
     * call the root activity to open the open activity
     * @param btnOpen
     */
    private void setOnClickOpen(Button btnOpen) {
        Log.d("Button ID", String.valueOf(btnOpen.getId()));
        currentAP = aps.get(btnOpen.getId());
        Log.d("Open: ", currentAP.getApartment().getHouse().getHV().getName() + " opens AP with the id " + currentAP.getId());
        hv_homeActivity.callOpenActivity();
    }

    /**
     * call the root activity to open the edit activity
     * @param btnEdit
     */
    private void setOnClickEdit(Button btnEdit) {
        Log.d("Button ID", String.valueOf(btnEdit.getId()));
        currentAP = aps.get(btnEdit.getId());
        Log.d("Edit: ", currentAP.getApartment().getHouse().getHV().getName() + " edits AP with the id " + currentAP.getId());
        hv_homeActivity.callEditActivity(currentAP.getId());
    }
}
