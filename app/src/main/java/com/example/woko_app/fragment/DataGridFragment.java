package com.example.woko_app.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.woko_app.R;
import com.example.woko_app.activity.HV_EditActivity;
import com.example.woko_app.constants.ApartmentType;
import com.example.woko_app.models.AP;
import com.example.woko_app.models.BalconyState;
import com.example.woko_app.models.BasementState;
import com.example.woko_app.models.CupboardState;
import com.example.woko_app.models.CutleryState;
import com.example.woko_app.models.DoorState;
import com.example.woko_app.models.EntryStateInterface;
import com.example.woko_app.models.FloorState;
import com.example.woko_app.models.FridgeState;
import com.example.woko_app.models.FurnitureState;
import com.example.woko_app.models.MattressState;
import com.example.woko_app.models.OvenState;
import com.example.woko_app.models.RadiatorState;
import com.example.woko_app.models.ShowerState;
import com.example.woko_app.models.SocketState;
import com.example.woko_app.models.VentilationState;
import com.example.woko_app.models.WallState;
import com.example.woko_app.models.WindowState;

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
    private String child;

    private List<String> rowNames = new ArrayList<>();
    private List<Boolean> check = new ArrayList<>();
    private List<Boolean> checkOld = new ArrayList<>();
    private List<String> comments = new ArrayList<>();
    private List<Integer> count = new ArrayList<>();
    private List<Integer> countBroken = new ArrayList<>();

    private EntryStateInterface tableEntries;

    //TODO picture

    private Typeface font;

    private TextView titleColumn;
    private CheckBox yesColumn;
    private CheckBox noColumn;
    private CheckBox oldColumn;
    private TextView commentColumn;
    private TextView pictureColumn;
    private TextView countColumn;
    private Spinner brokenColumn;

    private HV_EditActivity hv_editActivity;

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

        hv_editActivity = (HV_EditActivity) getActivity();

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
                tableEntries = CutleryState.findByKitchenAndAP(currentAP.getKitchen(), currentAP);
                tableEntries.getEntries(this);
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
                tableEntries = FurnitureState.findByRoomAndAP(currentAP.getRoom(), currentAP);
                tableEntries.getEntries(this);
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

    public List<Integer> getCount() {
        return count;
    }

    public List<Integer> getCountBroken() {
        return countBroken;
    }

    public int getParent() {
        return parent;
    }

    public void setTableEntries(EntryStateInterface tableEntries) {
        this.tableEntries = tableEntries;
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

            titleColumn = new TextView(new ContextThemeWrapper(getActivity(), R.style.TextViewStyle), null);
            titleColumn.setText(rowNames.get(i));
            row.addView(titleColumn);

            yesColumn = new CheckBox(new ContextThemeWrapper(getActivity(), R.style.CheckBoxStyle), null);
            row.addView(yesColumn);

            noColumn = new CheckBox(new ContextThemeWrapper(getActivity(), R.style.CheckBoxStyle), null);
            row.addView(noColumn);

            oldColumn = new CheckBox(new ContextThemeWrapper(getActivity(), R.style.CheckBoxStyle), null);
            row.addView(oldColumn);
            if (check.get(i) == true) {
                yesColumn.setChecked(true);
                yesColumn.setEnabled(false);
                oldColumn.setEnabled(false);
            } else {
                noColumn.setChecked(true);
                noColumn.setEnabled(false);
            }
            if (checkOld.get(i) == true) {
                oldColumn.setChecked(true);
            }

            commentColumn = new TextView(new ContextThemeWrapper(getActivity(), R.style.CommentStyle), null);
            commentColumn.setText(comments.get(i));
            row.addView(commentColumn);

            pictureColumn = new TextView(new ContextThemeWrapper(getActivity(), R.style.PictureStyle), null);
            pictureColumn.setTypeface(font);
            row.addView(pictureColumn);

            setOnClickYes();
            setOnClickNo();
            setOnClickOld();
            setOnClickComment();
        }
    }

    public void setTableContentVarainte2() {
        for (int i = 0; i < rowNames.size(); i++) {
            TableRow row;
            if (i % 2 == 0) {
                row = new TableRow(new ContextThemeWrapper(getActivity(), R.style.RowStyleGreyHell), null);
            } else {
                row = new TableRow(new ContextThemeWrapper(getActivity(), R.style.RowStyleWhite), null);
            }
            row.setId(i);
            table.addView(row);

            titleColumn = new TextView(new ContextThemeWrapper(getActivity(), R.style.TextViewStyle), null);
            titleColumn.setText(rowNames.get(i));
            row.addView(titleColumn);

            countColumn = new TextView(new ContextThemeWrapper(getActivity(), R.style.TextViewStyle), null);
            countColumn.setGravity(Gravity.CENTER);
            countColumn.setText(count.get(i).toString());
            row.addView(countColumn);

            brokenColumn = new Spinner(getActivity());
            row.addView(brokenColumn);

            oldColumn = new CheckBox(new ContextThemeWrapper(getActivity(), R.style.CheckBoxStyle), null);
            row.addView(oldColumn);
            if (checkOld.get(i)) {
                oldColumn.setChecked(true);
            }
            if (countBroken.get(i) == 0) {
                oldColumn.setEnabled(false);
            }

            commentColumn = new TextView(new ContextThemeWrapper(getActivity(), R.style.CommentStyle), null);
            commentColumn.setText(comments.get(i));
            row.addView(commentColumn);

            pictureColumn = new TextView(new ContextThemeWrapper(getActivity(), R.style.PictureStyle), null);
            pictureColumn.setTypeface(font);
            row.addView(pictureColumn);

            setOnClickComment();
        }
    }

    private void setOnClickYes(){
        yesColumn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TableRow row = (TableRow) buttonView.getParent();
                CheckBox checkBox;

                if (isChecked) {
                    // set yes disabled
                    checkBox = (CheckBox) row.getChildAt(1);
                    checkBox.setEnabled(false);
                    // set old disabled
                    checkBox = (CheckBox) row.getChildAt(3);
                    checkBox.setEnabled(false);
                    checkBox.setChecked(false);
                    // set no unchecked
                    checkBox = (CheckBox) row.getChildAt(2);
                    checkBox.setChecked(false);
                    checkBox.setEnabled(true);
                    // save to DB
                    check.set(row.getId(), true);
                    tableEntries.saveCheckEntries(check);
                    //TODO exclamation
                }
            }
        });
    }

    private void setOnClickNo(){
        noColumn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TableRow row = (TableRow) buttonView.getParent();
                CheckBox checkBox;

                if (isChecked) {
                    // set no disabled
                    checkBox = (CheckBox) row.getChildAt(2);
                    checkBox.setEnabled(false);
                    // set old enabled
                    checkBox = (CheckBox) row.getChildAt(3);
                    checkBox.setEnabled(true);
                    // set yes unchecked
                    checkBox = (CheckBox) row.getChildAt(1);
                    checkBox.setChecked(false);
                    checkBox.setEnabled(true);
                    // save to DB
                    check.set(row.getId(), false);
                    tableEntries.saveCheckEntries(check);
                }
            }
        });
    }

    private void setOnClickOld() {
        oldColumn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TableRow row = (TableRow) buttonView.getParent();

                if (isChecked) {
                    checkOld.set(row.getId(), true);
                    //TODO exclamation
                } else {
                    checkOld.set(row.getId(), false);
                }
                // save to DB
                tableEntries.saveCheckOldEntries(checkOld);
            }
        });
    }

    private void setOnClickComment() {
        commentColumn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TableRow row = (TableRow) v.getParent();
                final TextView textView = (TextView)row.getChildAt(4);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

                final EditText editText = new EditText(getActivity());
                editText.setText(textView.getText());
                alertDialogBuilder.setView(editText);

                alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textView.setText(editText.getText());
                        comments.set(row.getId(), editText.getText().toString());
                        tableEntries.saveCommentsEntries(comments);
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }
}
