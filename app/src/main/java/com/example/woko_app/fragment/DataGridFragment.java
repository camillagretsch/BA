package com.example.woko_app.fragment;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.example.woko_app.R;
import com.example.woko_app.constants.ApartmentType;
import com.example.woko_app.models.AP;
import com.example.woko_app.models.BalconyState;
import com.example.woko_app.models.BasementState;
import com.example.woko_app.models.Bathroom;
import com.example.woko_app.models.CupboardState;
import com.example.woko_app.models.DoorState;
import com.example.woko_app.models.FloorState;
import com.example.woko_app.models.FridgeState;
import com.example.woko_app.models.MattressState;
import com.example.woko_app.models.OvenState;
import com.example.woko_app.models.RadiatorState;
import com.example.woko_app.models.ShowerState;
import com.example.woko_app.models.SocketState;
import com.example.woko_app.models.VentilationState;
import com.example.woko_app.models.WallState;
import com.example.woko_app.models.WindowState;

import java.security.interfaces.RSAKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link DataGridFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DataGridFragment extends Fragment {

    private TableLayout table;

    private String[] headerNames;

    private AP currentAP;

    private int parent;
    private int child;

    private List<String> rowNames = new ArrayList<>();
    private List<Boolean> check = new ArrayList<>();
    private List<Boolean> checkOld = new ArrayList<>();
    private List<String> comments = new ArrayList<>();
    private String className;
    //TODO picture

    private Typeface font;

    private TextView c1;
    private CheckBox c2;
    private CheckBox c3;
    private CheckBox c4;
    private EditText c5;
    private TextView c6;

    public DataGridFragment() {
        // Required empty public constructor
    }

    public DataGridFragment(Typeface font) {
        this.font = font;
    }

    public static DataGridFragment newInstance() {
        DataGridFragment fragment = new DataGridFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data_grid, container, false);

        table = (TableLayout) view.findViewById(R.id.table);
        table.setStretchAllColumns(true);
        table.bringToFront();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            currentAP = AP.findById(bundle.getLong("AP"));
            parent = bundle.getInt("Parent");
            child = bundle.getInt("Child");
        }

        if (ApartmentType.SHARED_APARTMENT.equals(currentAP.getApartment().getType())) {
           getRoomEntries();
        } else  {
            switch (parent) {
                case 0:
                    getKitchenEntries();
                    break;
                case 1:
                    getBathroomEntries();
                    break;
                case 2:
                    getRoomEntries();
                    break;
                case 3:
                    getBalconyEntries();
                    break;
                case 4:
                    getBasementEntries();
                    break;
            }
        }

    }

    public void getKitchenEntries() {
        setHeaderVariante1();

        switch (child) {
            case 0:
                FridgeState fridge = FridgeState.findByKitchenAndAP(currentAP.getKitchen(), currentAP);
                className = fridge.getClass().getName();
                rowNames = fridge.getRowNames();
                check = FridgeState.createCheckList(fridge);
                checkOld = FridgeState.createCheckOldList(fridge);
                comments = FridgeState.createCommentsList(fridge);
                setTableContentVariante1();
                break;
            case 1:
                OvenState oven = OvenState.findByKitchenAndAP(currentAP.getKitchen(), currentAP);
                rowNames = oven.getRowNames();
                check = OvenState.createCheckList(oven);
                checkOld = OvenState.createCheckOldList(oven);
                comments = OvenState.createCommentsList(oven);
                setTableContentVariante1();
                break;
            case 2:
                VentilationState ventilation = VentilationState.findByKitchenAndAP(currentAP.getKitchen(), currentAP);
                rowNames = ventilation.getRowNames();
                check = VentilationState.createCheckList(ventilation);
                checkOld = VentilationState.createCheckOldList(ventilation);
                comments = VentilationState.createCommentsList(ventilation);
                setTableContentVariante1();
                break;
            case 3:
                table.removeAllViews();
                setHeaderVariante2();
                break;
            case 4:
                CupboardState cupboard = CupboardState.findByKitchenAndAP(currentAP.getKitchen(), currentAP);
                rowNames = cupboard.getRowNames();
                check = CupboardState.createCheckList(cupboard);
                checkOld = CupboardState.createCheckOldList(cupboard);
                comments = CupboardState.createCommentsList(cupboard);
                setTableContentVariante1();
                break;
            case 5:
                WallState wall = WallState.findByKitchenAndAP(currentAP.getKitchen(), currentAP);
                rowNames = wall.getRowNames();
                check = WallState.createCheckList(wall);
                checkOld = WallState.createCheckOldList(wall);
                comments = WallState.createCommentsList(wall);
                setTableContentVariante1();
                break;
            case 6:
                FloorState floor = FloorState.findByKitchenAndAP(currentAP.getKitchen(), currentAP);
                rowNames = floor.getRowNames();
                check = FloorState.createCheckList(floor);
                checkOld = FloorState.createCheckOldList(floor);
                comments = FloorState.createCommentsList(floor);
                setTableContentVariante1();
                break;
            case 7:
                WindowState window = WindowState.findByKitchenAndAP(currentAP.getKitchen(), currentAP);
                rowNames = window.getRowNames();
                check = WindowState.createCheckList(window);
                checkOld = WindowState.createCheckOldList(window);
                comments = WindowState.createCommentsList(window);
                setTableContentVariante1();
                break;
            case 8:
                DoorState door = DoorState.findByKitchenAndAP(currentAP.getKitchen(), currentAP);
                rowNames = door.getRowNames();
                check = DoorState.createCheckList(door);
                checkOld = DoorState.createCheckOldList(door);
                comments = DoorState.createCommentsList(door);
                setTableContentVariante1();
                break;
            case 9:
                SocketState socket = SocketState.findByKitchenAndAP(currentAP.getKitchen(), currentAP);
                rowNames = socket.getRowNames();
                check = SocketState.createCheckList(socket);
                checkOld = SocketState.createCheckOldList(socket);
                comments = SocketState.createCommentsList(socket);
                setTableContentVariante1();
                break;
            case 10:
                RadiatorState radiator = RadiatorState.findByKitchenAndAP(currentAP.getKitchen(), currentAP);
                rowNames = radiator.getRowNames();
                check = RadiatorState.createCheckList(radiator);
                checkOld = RadiatorState.createCheckOldList(radiator);
                comments = RadiatorState.createCommentsList(radiator);
                setTableContentVariante1();
                break;
        }
    }

    public void getBathroomEntries() {
        setHeaderVariante1();

        switch (child) {
            case 0:
                ShowerState shower = ShowerState.findByBathroomAndAP(currentAP.getBathroom(), currentAP);
                rowNames = shower.getRowNames();
                check = ShowerState.createCheckList(shower);
                checkOld = ShowerState.createCheckOldList(shower);
                comments = ShowerState.createCommentsList(shower);
                setTableContentVariante1();
                break;
            case 1:
                WallState wall = WallState.findByBathroomAndAP(currentAP.getBathroom(), currentAP);
                rowNames = wall.getRowNames();
                check = WallState.createCheckList(wall);
                checkOld = WallState.createCheckOldList(wall);
                comments = WallState.createCommentsList(wall);
                setTableContentVariante1();
                break;
            case 2:
                FloorState floor = FloorState.findByBathroomAndAP(currentAP.getBathroom(), currentAP);
                rowNames = floor.getRowNames();
                check = FloorState.createCheckList(floor);
                checkOld = FloorState.createCheckOldList(floor);
                comments = FloorState.createCommentsList(floor);
                setTableContentVariante1();
                break;
            case 3:
                WindowState window = WindowState.findByBathroomAndAP(currentAP.getBathroom(), currentAP);
                rowNames = window.getRowNames();
                check = WindowState.createCheckList(window);
                checkOld = WindowState.createCheckOldList(window);
                comments = WindowState.createCommentsList(window);
                setTableContentVariante1();
                break;
            case 4:
                DoorState door = DoorState.findByBathroomAndAP(currentAP.getBathroom(), currentAP);
                rowNames = door.getRowNames();
                check = DoorState.createCheckList(door);
                checkOld = DoorState.createCheckOldList(door);
                comments = DoorState.createCommentsList(door);
                setTableContentVariante1();
                break;
            case 5:
                SocketState socket = SocketState.findByBathroomAndAP(currentAP.getBathroom(), currentAP);
                rowNames = socket.getRowNames();
                check = SocketState.createCheckList(socket);
                checkOld = SocketState.createCheckOldList(socket);
                comments = SocketState.createCommentsList(socket);
                setTableContentVariante1();
                break;
            case 6:
                RadiatorState radiator = RadiatorState.findByBathroomAndAP(currentAP.getBathroom(), currentAP);
                rowNames = radiator.getRowNames();
                check = RadiatorState.createCheckList(radiator);
                checkOld = RadiatorState.createCheckOldList(radiator);
                comments = RadiatorState.createCommentsList(radiator);
                setTableContentVariante1();
                break;
        }
    }

    public void getRoomEntries() {
        setHeaderVariante1();

        switch (child) {
            case 0:
                MattressState mattress = MattressState.findByRoomAndAP(currentAP.getRoom(), currentAP);
                rowNames = mattress.getRowNames();
                check = MattressState.createCheckList(mattress);
                checkOld = MattressState.createCheckOldList(mattress);
                comments = MattressState.createCommentsList(mattress);
                setTableContentVariante1();
                break;
            case 1:
                table.removeAllViews();
                setHeaderVariante2();
                break;
            case 2:
                WallState wall = WallState.findByRoomAndAP(currentAP.getRoom(), currentAP);
                rowNames = wall.getRowNames();
                check = WallState.createCheckList(wall);
                checkOld = WallState.createCheckOldList(wall);
                comments = WallState.createCommentsList(wall);
                setTableContentVariante1();
                break;
            case 3:
                FloorState floor = FloorState.findByRoomAndAP(currentAP.getRoom(), currentAP);
                rowNames = floor.getRowNames();
                check = FloorState.createCheckList(floor);
                checkOld = FloorState.createCheckOldList(floor);
                comments = FloorState.createCommentsList(floor);
                setTableContentVariante1();
                break;
            case 4:
                WindowState window = WindowState.findByRoomAndAP(currentAP.getRoom(), currentAP);
                rowNames = window.getRowNames();
                check = WindowState.createCheckList(window);
                checkOld = WindowState.createCheckOldList(window);
                comments = WindowState.createCommentsList(window);
                setTableContentVariante1();
                break;
            case 5:
                DoorState door = DoorState.findByRoomAndAP(currentAP.getRoom(), currentAP);
                rowNames = door.getRowNames();
                check = DoorState.createCheckList(door);
                checkOld = DoorState.createCheckOldList(door);
                comments = DoorState.createCommentsList(door);
                setTableContentVariante1();
                break;
            case 6:
                SocketState socket = SocketState.findByRoomAndAP(currentAP.getRoom(), currentAP);
                rowNames = socket.getRowNames();
                check = SocketState.createCheckList(socket);
                checkOld = SocketState.createCheckOldList(socket);
                comments = SocketState.createCommentsList(socket);
                setTableContentVariante1();
                break;
            case 7:
                RadiatorState radiator = RadiatorState.findByRoomAndAP(currentAP.getRoom(), currentAP);
                rowNames = radiator.getRowNames();
                check = RadiatorState.createCheckList(radiator);
                checkOld = RadiatorState.createCheckOldList(radiator);
                comments = RadiatorState.createCommentsList(radiator);
                setTableContentVariante1();
                break;
        }
    }

    public void getBalconyEntries() {
        setHeaderVariante1();
        BalconyState balcony = BalconyState.findByApartmentAndAP(currentAP.getApartment(), currentAP);
        rowNames = balcony.getRowNames();
        check = BalconyState.createCheckList(balcony);
        checkOld = BalconyState.createCheckOldList(balcony);
        comments = BalconyState.createCommentsList(balcony);
        setTableContentVariante1();
    }

    public void getBasementEntries() {
        setHeaderVariante1();
        BasementState basement = BasementState.findByApartmentAndAP(currentAP.getApartment(), currentAP);
        rowNames = basement.getRowNames();
        check = BasementState.createCheckList(basement);
        checkOld = BasementState.createCheckOldList(basement);
        comments = BasementState.createCommentsList(basement);
        setTableContentVariante1();
    }

    public void setHeaderVariante1() {
        headerNames = getResources().getStringArray(R.array.header_variante1);
        TableRow header = new TableRow(new ContextThemeWrapper(getActivity(), R.style.RowStyleGreyDark), null);
        table.addView(header);

        for (int i = 0; i < headerNames.length; i++) {
            TextView column = new TextView(new ContextThemeWrapper(getActivity(), R.style.TableHeaderStyle), null);
            column.setText(headerNames[i]);
            header.addView(column);
        }
    }

    public void setHeaderVariante2() {
        headerNames = getResources().getStringArray(R.array.header_variante2);
        TableRow header = new TableRow(new ContextThemeWrapper(getActivity(), R.style.RowStyleGreyDark), null);
        table.addView(header);

        for (int i = 0; i < headerNames.length; i++) {
            TextView column = new TextView(new ContextThemeWrapper(getActivity(), R.style.TableHeaderStyle), null);
            column.setText(headerNames[i]);
            header.addView(column);
        }
    }

    public void setTableContentVariante1() {
        for (int i = 0; i < rowNames.size(); i++) {
            TableRow row;
            if (i % 2 == 0) {
                row = new TableRow(new ContextThemeWrapper(getActivity(), R.style.RowStyleGreyHell), null);
            } else {
                row = new TableRow(new ContextThemeWrapper(getActivity(), R.style.RowStyleWhite), null);
            }
            row.setId(i);
            table.addView(row);

            c1 = new TextView(new ContextThemeWrapper(getActivity(), R.style.TextViewStyle), null);
            c1.setText(rowNames.get(i));
            c1.setId(i);
            row.addView(c1);

            c2 = new CheckBox(new ContextThemeWrapper(getActivity(), R.style.CheckBoxStyle), null);
            c2.setId(i);
            row.addView(c2);

            c3 = new CheckBox(new ContextThemeWrapper(getActivity(), R.style.CheckBoxStyle), null);
            c3.setId(i);
            if (check.get(i) == true) {
                c2.setChecked(true);
            } else {
                c3.setChecked(true);
            }
            row.addView(c3);

            c4 = new CheckBox(new ContextThemeWrapper(getActivity(), R.style.CheckBoxStyle), null);
            c4.setId(i);
            row.addView(c4);
            if (c2.isChecked()) {
                c4.setEnabled(false);
            } if (c3.isChecked()) {
                c4.setEnabled(true);
            }
            if (check.get(i) == true) {
                c4.setChecked(true);
            }

            c5 = new EditText(new ContextThemeWrapper(getActivity(), R.style.EditTextStyle), null);
            c5.setText(comments.get(i));
            c5.setId(i);
            row.addView(c5);

            c6 = new TextView(new ContextThemeWrapper(getActivity(), R.style.PictureStyle), null);
            c6.setTypeface(font);
            c6.setId(i);
            row.addView(c6);

            c2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setOnClickYes((TableRow) buttonView.getParent(), isChecked);
                }
            });
            c3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setOnClickNo((TableRow) buttonView.getParent(), isChecked);
                }
            });
            c4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                }
            });
            c5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    
                }
            });
        }
    }

    private void setOnClickYes(TableRow row, boolean isChecked){
        CheckBox checkBox;
        if (isChecked) {
            // set old disabled
            checkBox = (CheckBox)row.getChildAt(3);
            checkBox.setEnabled(false);
            checkBox.setChecked(false);
            // set no unchecked
            checkBox = (CheckBox)row.getChildAt(2);
            checkBox.setChecked(false);
            // TODO save to DB
            check.set(row.getId(), true);
            // TODO exclamation
        } else {
            // set old enable
            row.getChildAt(3).setEnabled(true);
        }
    }

    private void setOnClickNo(TableRow row, boolean isChecked){
        CheckBox checkBox;
        if (isChecked) {
            // set old enable
            checkBox = (CheckBox)row.getChildAt(3);
            checkBox.setEnabled(true);
            // set yes unchecked
            checkBox = (CheckBox)row.getChildAt(1);
            checkBox.setChecked(false);
            // TODO save to DB
            check.set(row.getId(), false);
            // TODO exclamation
        } else {
            // set old disabled
            checkBox = (CheckBox)row.getChildAt(3);
            checkBox.setEnabled(false);
        }
    }

    private void setOnClickOld(TableRow row, boolean isChecked) {
        CheckBox checkBox;
        if (isChecked) {
            // TODO save to DB
            // TODO exclamation
        }
    }
}
