package com.example.woko_app.fragment;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.woko_app.R;
import com.example.woko_app.activity.HV_HomeActivity;
import com.example.woko_app.models.AP;

import java.lang.reflect.Field;

public class MainFragment extends Fragment {

    private Typeface font;

    private DatePicker datePicker;

    private TextView fileIcon;
    private TextView txtFileName;

    private Button btnOpen;
    private Button btnEdit;
    private Button btnDuplicate;
    private Button btnSearch;

    private AP currentAP;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        //fontawesome
        font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/fontawesome-webfont.ttf");

        //home activity is the root activity of this fragment
        final HV_HomeActivity rootActivity = (HV_HomeActivity) getActivity();

        fileIcon = (TextView) view.findViewById(R.id.fileicon);
        fileIcon.setTypeface(font);

        txtFileName = (TextView) view.findViewById(R.id.txtFileName);

        // set buttons on click listener
        btnOpen = (Button) view.findViewById(R.id.btnOpen);
        btnOpen.setTypeface(font);
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Open: ", currentAP.getApartment().getHouse().getHV().getName() + " opens AP with the id " + currentAP.getId());
                rootActivity.callOpenActivity();
            }
        });

        btnEdit = (Button) view.findViewById(R.id.btnEdit);
        btnEdit.setTypeface(font);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Edit: ", currentAP.getApartment().getHouse().getHV().getName() + " edits AP with the id " + currentAP.getId());
                currentAP.save();
                rootActivity.callEditActivity(currentAP.getId());
            }
        });

        btnDuplicate = (Button) view.findViewById(R.id.btnDuplicate);
        btnDuplicate.setTypeface(font);
        btnDuplicate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Duplicate: ", currentAP.getApartment().getHouse().getHV().getName() + " duplicate AP with the id " + currentAP.getId());
                rootActivity.callDuplicateFragment();
            }
        });

        datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        findAndHideField(datePicker, "mDaySpinner");
        findAndHideField(datePicker, "mMonthSpinner");

        btnSearch = (Button) view.findViewById(R.id.btnSearch);
        btnSearch.setTypeface(font);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Search: ", currentAP.getApartment().getHouse().getHV().getName() + " search for APs with the year " + String.valueOf(datePicker.getYear()));
                rootActivity.callShowOldFragment(datePicker.getYear());
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            currentAP = AP.findById(bundle.getLong("AP_ID"));
            txtFileName.setText(currentAP.getName());
        }
    }

    /**
     * hide the day and month of the android datepicker
     * @param object
     * @param name
     */
    private static void findAndHideField(Object object, String name) {
        try {
            final Field field = object.getClass().getDeclaredField(name);
            field.setAccessible(true);
            final View fieldInstance = (View) field.get(object);
            fieldInstance.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

