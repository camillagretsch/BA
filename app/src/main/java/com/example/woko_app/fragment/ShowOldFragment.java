package com.example.woko_app.fragment;

import android.app.ActionBar;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.woko_app.activity.HV_HomeActivity;
import com.example.woko_app.constants.ApartmentType;
import com.example.woko_app.models.AP;
import com.example.woko_app.models.Apartment;
import com.example.woko_app.models.Room;

import com.example.woko_app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class ShowOldFragment extends Fragment {

    private List<AP> oldAPs = new ArrayList<>();
    private Apartment currentApartment;
    private Room currentRoom;
    private int year;

    private Button btnBack;

    private Typeface font;

    private LinearLayout fileContainer;

    private AP currentAP;


    public ShowOldFragment() {
        // Required empty public constructor
    }

    public ShowOldFragment(Typeface font) {
        this.font = font;
    }

    public static ShowOldFragment newInstance() {
        ShowOldFragment fragment = new ShowOldFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_old, container, false);

        fileContainer = (LinearLayout) view.findViewById(R.id.fileContainer);

        btnBack = (Button) view.findViewById(R.id.btnBack);
        btnBack.setTypeface(font);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
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

        if (ApartmentType.SHARED_APARTMENT.equals(currentApartment.getType())) {
            oldAPs = AP.findByRoomAndYear(currentRoom, year);
            Log.d("How many files are found:", String.valueOf(oldAPs.size()));
        } else {
            oldAPs = AP.findByApartmentAndYear(currentApartment, year);
            Log.d("How many files are found:", String.valueOf(oldAPs.size()));
        }

        createFiles();
    }

    private void createFiles() {

        int i = 0;

        for (AP ap : oldAPs) {

            LinearLayout linearLayout = new LinearLayout(new ContextThemeWrapper(getActivity(), R.style.VerticalLayoutStyle), null);
            fileContainer.addView(linearLayout);

            TextView fileicon = new TextView(getActivity());
            linearLayout.addView(fileicon);
            fileicon.setText(R.string.file_icon);
            fileicon.setTextSize(60);
            fileicon.setGravity(Gravity.CENTER_HORIZONTAL);
            fileicon.setTypeface(font);

            TextView fileName = new TextView(new ContextThemeWrapper(getActivity(), R.style.TextViewStyle), null);
            fileName.setText(ap.getName());
            linearLayout.addView(fileName);

            final Button btnOpen = new Button(new ContextThemeWrapper(getActivity(), R.style.ButtonStyle), null);
            btnOpen.setText(R.string.open_btn);
            btnOpen.setGravity(Gravity.CENTER_HORIZONTAL);
            btnOpen.setTypeface(font);
            btnOpen.setId(i);
            linearLayout.addView(btnOpen);
            btnOpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Button ID", String.valueOf(btnOpen.getId()));
                    currentAP = oldAPs.get(btnOpen.getId());
                    Log.d("Open: ", currentAP.getApartment().getHouse().getHV().getName() + " opens AP with the id " + currentAP.getId());
                }
            });

            Button btnEdit = new Button(new ContextThemeWrapper(getActivity(), R.style.ButtonStyle), null);
            btnEdit.setText(R.string.edit_btn);
            btnEdit.setGravity(Gravity.CENTER_HORIZONTAL);
            btnEdit.setTypeface(font);
            btnEdit.setId(i);
            linearLayout.addView(btnEdit);
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Button ID", String.valueOf(btnOpen.getId()));
                    currentAP = oldAPs.get(btnOpen.getId());
                    currentAP.save();
                    Log.d("Edit: ", currentAP.getApartment().getHouse().getHV().getName() + " edits AP with the id " + currentAP.getId());
                    HV_HomeActivity hv_homeActivity = (HV_HomeActivity) getActivity();
                    hv_homeActivity.callEditActivity(currentAP.getId());
                }
            });

            i++;
        }
    }
}
