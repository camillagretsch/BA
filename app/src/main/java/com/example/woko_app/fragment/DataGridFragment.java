package com.example.woko_app.fragment;

import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.example.woko_app.R;
import com.example.woko_app.constants.ApartmentType;
import com.example.woko_app.models.AP;
import com.example.woko_app.models.Apartment;
import com.example.woko_app.models.BalconyState;
import com.example.woko_app.models.BasementState;
import com.example.woko_app.models.Bathroom;
import com.example.woko_app.models.CupboardState;
import com.example.woko_app.models.DoorState;
import com.example.woko_app.models.EntryStateInterface;
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
import java.util.Arrays;
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
    private String child;

    private List<String> rowNames = new ArrayList<>();
    private List<Boolean> check = new ArrayList<>();
    private List<Boolean> checkOld = new ArrayList<>();
    private List<String> comments = new ArrayList<>();

    private EntryStateInterface tableEntries;

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
            child = bundle.getString("Child");
        }

        if (parent == 3) {
            tableEntries = BalconyState.findByApartmentAndAP(currentAP.getApartment(), currentAP);
            tableEntries.getEntries(this);
        } else if (parent == 4) {
            tableEntries = BasementState.findByApartmentAndAP(currentAP.getApartment(), currentAP);
            tableEntries.getEntries(this);
        } else
            switchCaseEntries();

    }

    private void switchCaseEntries() {
        switch (child) {
            case "Kühlschrank, Tiefkühlfach":
                tableEntries = FridgeState.findByKitchenAndAP(currentAP.getKitchen(), currentAP);
                tableEntries.getEntries(this);
                break;
            case "Backofen, Herdplatte":
                tableEntries = OvenState.findByKitchenAndAP(currentAP.getKitchen(), currentAP);
                tableEntries.getEntries(this);
                break;
            case "Dampfabzug":
                tableEntries = VentilationState.findByKitchenAndAP(currentAP.getKitchen(), currentAP);
                tableEntries.getEntries(this);
                break;
            case "Pfannen, Geschirr, Besteck":
                // TODO
                break;
            case "Schränke":
                tableEntries = CupboardState.findByKitchenAndAP(currentAP.getKitchen(), currentAP);
                tableEntries.getEntries(this);
                break;
            case "WC, Dusche, Lavabo":
                tableEntries = ShowerState.findByBathroomAndAP(currentAP.getBathroom(), currentAP);
                tableEntries.getEntries(this);
                break;
            case "Bettwäsche, Matratze":
                tableEntries = MattressState.findByRoomAndAP(currentAP.getRoom(), currentAP);
                tableEntries.getEntries(this);
                break;
            case "Mobiliar":
                // TODO
                break;
            case "Wände, Decke":
                tableEntries = WallState.findByRoomAndAP(currentAP.getRoom(), currentAP);
                tableEntries.getEntries(this);
                break;
            case "Boden":
                tableEntries = FloorState.findByRoomAndAP(currentAP.getRoom(), currentAP);
                tableEntries.getEntries(this);
                break;
            case "Fenster":
                tableEntries = WindowState.findByRoomAndAP(currentAP.getRoom(), currentAP);
                tableEntries.getEntries(this);
                break;
            case "Tür":
                tableEntries = DoorState.findByRoomAndAP(currentAP.getRoom(), currentAP);
                tableEntries.getEntries(this);
                break;
            case "Lampen, Steckdosen":
                tableEntries = SocketState.findByRoomAndAP(currentAP.getRoom(), currentAP);
                tableEntries.getEntries(this);
                break;
            case "Heizkörper, Ventil":
                tableEntries = RadiatorState.findByRoomAndAP(currentAP.getRoom(), currentAP);
                tableEntries.getEntries(this);
                break;
        }
    }

    public AP getCurrentAP() {
        return currentAP;
    }

    public List<Boolean> getCheck() {
        return check;
    }

    public List<Boolean> getCheckOld() {
        return checkOld;
    }

    public List<String> getComments() {
        return comments;
    }

    public List<String> getRowNames() {
        return rowNames;
    }

    public int getParent() {
        return parent;
    }

    public void setTableEntries(EntryStateInterface tableEntries) {
        this.tableEntries = tableEntries;
    }

    public EntryStateInterface getTableEntries() {
        return tableEntries;
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
            if (checkOld.get(i) == true) {
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
