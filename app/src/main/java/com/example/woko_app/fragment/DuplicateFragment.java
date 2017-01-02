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
import com.example.woko_app.constants.ApartmentType;
import com.example.woko_app.models.AP;
import com.example.woko_app.models.Apartment;
import com.example.woko_app.models.Bathroom;
import com.example.woko_app.models.House;
import com.example.woko_app.models.Kitchen;
import com.example.woko_app.models.Room;
import com.example.woko_app.models.Tenant;

public class DuplicateFragment extends Fragment {

    private Typeface font;

    private Button btnNext;
    private Button btnStop;

    private TextView etAdress;
    private TextView etApartmentNr;
    private TextView etRoomNr;

    private TextView txtRoom;

    private House currentHouse;
    private Apartment currentApartment;
    private Room currentRoom;
    private AP oldAP;

    private DatePicker datePicker;

    public DuplicateFragment() {
        // Required empty public constructor
    }

    public static DuplicateFragment newInstance() {
        DuplicateFragment fragment = new DuplicateFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_duplicate, container, false);

        //fontawesome
        font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/fontawesome-webfont.ttf");

        etAdress = (TextView) view.findViewById(R.id.etAddress);
        etApartmentNr = (TextView) view.findViewById(R.id.etApartmentNr);
        etRoomNr = (TextView) view.findViewById(R.id.etRoomNr);

        txtRoom = (TextView) view.findViewById(R.id.roomNr);

        datePicker = (DatePicker) view.findViewById(R.id.datePicker);

        // set buttons on click listener
        btnNext = (Button) view.findViewById(R.id.btnNext);
        btnNext.setTypeface(font);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnClickNext();
            }
        });

        btnStop = (Button) view.findViewById(R.id.btnStop);
        btnStop.setTypeface(font);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HV_HomeActivity hv_homeActivity = (HV_HomeActivity) getActivity();
                hv_homeActivity.openPreviousFragment(1);
            }
        });
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            currentHouse = House.findById(bundle.getLong("House"));
            currentApartment = Apartment.findById(bundle.getLong("Apartment"));
            currentRoom = Room.findById(bundle.getLong("Room"));
            oldAP = AP.findById(bundle.getLong("Old AP"));
        }

        etAdress.setText(currentHouse.getStreet() + " " + currentHouse.getStreetNumber() + ", " + currentHouse.getPLZ() + " " + currentHouse.getTown());
        etApartmentNr.setText(String.valueOf(currentApartment.getApartmentNumber()));

        if (currentRoom != null) {
            txtRoom.setVisibility(View.VISIBLE);
            etRoomNr.setVisibility(View.VISIBLE);
            etRoomNr.setText(String.valueOf(currentRoom.getRoomNumber()));
        } else {
             txtRoom.setVisibility(View.INVISIBLE);
            etRoomNr.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * creates a new protocol
     * if there is an old protocol it duplicates all old entries and save them to the new protocol
     */
    private void setOnClickNext() {
        AP ap;
        if (ApartmentType.SHARED_APARTMENT.equals(currentApartment.getType())) {
            ap = new AP(datePicker.getDayOfMonth(), datePicker.getMonth() + 1, datePicker.getYear(), currentApartment, currentRoom, Tenant.saveNewTenant());
            ap.setSharedApartmentName(currentHouse.getStreet(), currentHouse.getStreetNumber(), currentApartment.getApartmentNumber(), currentRoom.getRoomNumber(), ap.getDay(), ap.getMonth(), ap.getYear());
        } else {
            ap = new AP(datePicker.getDayOfMonth(), datePicker.getMonth() + 1, datePicker.getYear(), currentApartment, currentApartment.getRooms().get(0), Bathroom.findByApartment(currentApartment), Kitchen.findByApartment(currentApartment), Tenant.saveNewTenant());
            ap.setStudioName(currentHouse.getStreet(), currentHouse.getStreetNumber(), currentApartment.getApartmentNumber(), ap.getDay(), ap.getMonth(), ap.getYear());

        }
        ap.setOldAP(oldAP);
        ap.save();
        if (oldAP != null) {
            ap.duplicateAP(ap, oldAP);
        } else {
            ap.createNewAP(ap);
        }

        HV_HomeActivity hv_homeActivity = (HV_HomeActivity) getActivity();
        hv_homeActivity.callEditActivity(ap.getId());


        Log.d("new AP", ap.getName());
    }
}
