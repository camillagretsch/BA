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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.example.woko_app.models.PersonalSerializer;
import com.example.woko_app.models.RadiatorState;
import com.example.woko_app.models.ShowerState;
import com.example.woko_app.models.SocketState;
import com.example.woko_app.models.VentilationState;
import com.example.woko_app.models.WallState;
import com.example.woko_app.models.WindowState;

import java.util.ArrayList;
import java.util.List;

public class DataGridFragment extends Fragment {

    private int parent;
    private String child;
    private List<String> rowNames = new ArrayList<>();
    private List<Boolean> check = new ArrayList<>();
    private List<Boolean> checkOld = new ArrayList<>();
    private List<String> comments = new ArrayList<>();
    private List<Integer> count = new ArrayList<>();
    private List<Integer> countBroken = new ArrayList<>();

    private AP currentAP;
    private EntryStateInterface tableEntries;
    private Typeface font;

    private TableLayout table;
    private TextView titleColumn;
    private CheckBox yesColumn;
    private CheckBox noColumn;
    private CheckBox oldColumn;
    private TextView commentColumn;
    private TextView pictureColumn;
    private TextView countColumn;
    private Spinner brokenColumn;
    private Button btnNext;
    private Button btnBack;

    private HV_EditActivity hv_editActivity;

    public DataGridFragment() {
        // Required empty public constructor
    }

    public static DataGridFragment newInstance() {
        DataGridFragment fragment = new DataGridFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data_grid, container, false);

        //fontawesome
        font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/fontawesome-webfont.ttf");

        btnNext = (Button) getActivity().findViewById(R.id.btnNext);
        btnNext.setText(getActivity().getResources().getString(R.string.next_btn));
        btnNext.setTypeface(font);
        btnNext.setVisibility(View.VISIBLE);

        btnBack = (Button) getActivity().findViewById(R.id.btnBack);
        btnBack.setText(getActivity().getResources().getString(R.string.back_btn));
        btnBack.setTypeface(font);
        btnBack.setVisibility(View.VISIBLE);

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
            switchCaseTableEntries();
    }

    /**
     * checks which model class has to be called
     * depends on the child content
     */
    private void switchCaseTableEntries() {
        if (child.contains("Kühlschrank, Tiefkühlfach")) {
            tableEntries = FridgeState.findByKitchenAndAP(currentAP.getKitchen(), currentAP);
            tableEntries.getEntries(this);
            btnBack.setVisibility(View.INVISIBLE);
        } else if (child.contains("Backofen, Herdplatte")) {
            tableEntries = OvenState.findByKitchenAndAP(currentAP.getKitchen(), currentAP);
            tableEntries.getEntries(this);
        } else if (child.contains("Dampfabzug")) {
            tableEntries = VentilationState.findByKitchenAndAP(currentAP.getKitchen(), currentAP);
            tableEntries.getEntries(this);
        } else if (child.contains("Pfannen, Geschirr, Besteck")) {
            tableEntries = CutleryState.findByKitchenAndAP(currentAP.getKitchen(), currentAP);
            tableEntries.getEntries(this);
        } else if (child.contains("Schränke")) {
            tableEntries = CupboardState.findByKitchenAndAP(currentAP.getKitchen(), currentAP);
            tableEntries.getEntries(this);
        } else if (child.contains("WC, Dusche, Lavabo")) {
            tableEntries = ShowerState.findByBathroomAndAP(currentAP.getBathroom(), currentAP);
            tableEntries.getEntries(this);
        } else if (child.contains("Bettwäsche, Matratze")) {
            tableEntries = MattressState.findByRoomAndAP(currentAP.getRoom(), currentAP);
            tableEntries.getEntries(this);
            if (ApartmentType.SHARED_APARTMENT.equals(currentAP.getApartment().getType())) {
                btnBack.setVisibility(View.INVISIBLE);
            }
        } else if (child.contains("Mobiliar")) {
            tableEntries = FurnitureState.findByRoomAndAP(currentAP.getRoom(), currentAP);
            tableEntries.getEntries(this);
        } else if (child.contains("Wände, Decke")) {
            tableEntries = WallState.findByRoomAndAP(currentAP.getRoom(), currentAP);
            tableEntries.getEntries(this);
        } else if (child.contains("Boden")) {
            tableEntries = FloorState.findByRoomAndAP(currentAP.getRoom(), currentAP);
            tableEntries.getEntries(this);
        } else if (child.contains("Fenster")) {
            tableEntries = WindowState.findByRoomAndAP(currentAP.getRoom(), currentAP);
            tableEntries.getEntries(this);
        } else if (child.contains("Tür")) {
            tableEntries = DoorState.findByRoomAndAP(currentAP.getRoom(), currentAP);
            tableEntries.getEntries(this);
        } else if (child.contains("Lampen, Steckdosen")) {
            tableEntries = SocketState.findByRoomAndAP(currentAP.getRoom(), currentAP);
            tableEntries.getEntries(this);
        } else if (child.contains("Heizkörper, Ventil")) {
            tableEntries = RadiatorState.findByRoomAndAP(currentAP.getRoom(), currentAP);
            tableEntries.getEntries(this);
        }
    }

    /**
     * set the header of the table
     */
    public void setTableHeader(String[] headerNames) {
        TableRow header = new TableRow(new ContextThemeWrapper(getActivity(), R.style.RowStyleGreyDark), null);
        table.addView(header);

        for (int i = 0; i < headerNames.length; i++) {
            TextView column = new TextView(new ContextThemeWrapper(getActivity(), R.style.TableHeaderStyle), null);
            column.setText(headerNames[i]);
            header.addView(column);
        }
    }

    /**
     * fill in the content of the table
     * depends on if the header contains the column yes and no
     */
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
            // set first entry of this row
            titleColumn = new TextView(new ContextThemeWrapper(getActivity(), R.style.TextViewStyle), null);
            titleColumn.setText(rowNames.get(i));
            row.addView(titleColumn);
            // set checkbox for the answer true
            yesColumn = new CheckBox(new ContextThemeWrapper(getActivity(), R.style.CheckBoxStyle), null);
            yesColumn.setText("Ja");
            row.addView(yesColumn);
            // set checkbox for the answer false
            noColumn = new CheckBox(new ContextThemeWrapper(getActivity(), R.style.CheckBoxStyle), null);
            noColumn.setText("Nein");
            row.addView(noColumn);
            // set checkbox for the answer old
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
            // set editview for the comment
            commentColumn = new TextView(new ContextThemeWrapper(getActivity(), R.style.CommentStyle), null);
            commentColumn.setText(comments.get(i));
            row.addView(commentColumn);
            // set textview for the camera or picture icon
            pictureColumn = new TextView(new ContextThemeWrapper(getActivity(), R.style.PictureStyle), null);

            if (tableEntries.countPicturesOfLast5Years(i, tableEntries) > 0) {
                pictureColumn.setText(getActivity().getResources().getText(R.string.picture_btn));
            } else {
                pictureColumn.setText(getActivity().getResources().getText(R.string.camera_icon));
            }
            pictureColumn.setTypeface(font);
            row.addView(pictureColumn);

            setOnClickPicture();
            setOnClickYes();
            setOnClickNo();
            setOnClickOld();
            setOnClickComment();
        }
    }

    /**
     * fill in the content of the table
     * depends on if the header contains the column count broken
     */
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
            // set first entry of this row
            titleColumn = new TextView(new ContextThemeWrapper(getActivity(), R.style.TextViewStyle), null);
            titleColumn.setText(rowNames.get(i));
            row.addView(titleColumn);
            // set the textview for the number of items
            countColumn = new TextView(new ContextThemeWrapper(getActivity(), R.style.TextViewStyle), null);
            countColumn.setGravity(Gravity.CENTER);
            countColumn.setText(count.get(i).toString());
            row.addView(countColumn);
            // set the spinner to count the broken items
            brokenColumn = new Spinner(getActivity());
            List<Integer> counter = new ArrayList<>();
            for (Integer j = 0; j <= count.get(i); j++) {
                counter.add(j);
            }
            brokenColumn.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, counter));
            brokenColumn.setSelection(countBroken.get(i));
            row.addView(brokenColumn);
            // set checkbox for the answer old
            oldColumn = new CheckBox(new ContextThemeWrapper(getActivity(), R.style.CheckBoxStyle), null);
            row.addView(oldColumn);
            if (checkOld.get(i)) {
                oldColumn.setChecked(true);
            }
            if (countBroken.get(i) == 0) {
                oldColumn.setEnabled(false);
            }
            // set editview for the comment
            commentColumn = new TextView(new ContextThemeWrapper(getActivity(), R.style.CommentStyle), null);
            commentColumn.setText(comments.get(i));
            row.addView(commentColumn);
            // set textview for the camera or picture icon
            pictureColumn = new TextView(new ContextThemeWrapper(getActivity(), R.style.PictureStyle), null);

            if (tableEntries.countPicturesOfLast5Years(i, tableEntries) > 0) {
                pictureColumn.setText(getActivity().getResources().getText(R.string.picture_btn));
            } else {
                pictureColumn.setText(getActivity().getResources().getText(R.string.camera_icon));
            }
            pictureColumn.setTypeface(font);
            row.addView(pictureColumn);

            setOnClickBroken();
            setOnClickComment();
            setOnClickOld();
            setOnClickPicture();
        }
    }

    /**
     *  checked the checkbox when yes is clicked
     *  set the checkbox yes disabled
     *  unchecked the checkbox no
     *  set the checkbox old disabled
     *  save the changes to the db
     */
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
                    tableEntries.saveCheckEntries(PersonalSerializer.convertBoolean(check), getActivity().getResources().getString(R.string.exclamation_mark));

                    // exclamation
                    hv_editActivity.updateSideView();
                }
            }
        });
    }

    /**
     * checked the checkbox when no is clicked
     * set the checkbox no disabled
     * unchecked the checkbox yes
     * set the checkbox old enabled
     * save the changes to the db
     */
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
                    tableEntries.saveCheckEntries(PersonalSerializer.convertBoolean(check), getActivity().getResources().getString(R.string.exclamation_mark));

                    //exclamation
                    hv_editActivity.updateSideView();
                }
            }
        });
    }

    /**
     * when 0 is selected set old unchecked and disabled
     * save the changes to db
     */
    private void setOnClickBroken() {
        brokenColumn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = (Spinner) view.getParent();
                TableRow row = (TableRow) spinner.getParent();
                CheckBox checkBox = (CheckBox) row.getChildAt(3);

                if (position == 0) {
                    checkBox.setEnabled(false);
                    checkBox.setChecked(false);
                } else
                    checkBox.setEnabled(true);
                // save to DB
                countBroken.set(row.getId(), position);
                tableEntries.saveCheckEntries(PersonalSerializer.convertInteger(countBroken), getActivity().getResources().getString(R.string.exclamation_mark));

                // exclamation
                hv_editActivity.updateSideView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * save the changses to db
     */
    private void setOnClickOld() {
        oldColumn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TableRow row = (TableRow) buttonView.getParent();

                if (isChecked) {
                    checkOld.set(row.getId(), true);
                } else {
                    checkOld.set(row.getId(), false);
                }
                // save to DB
                tableEntries.saveCheckOldEntries(checkOld);
            }
        });
    }

    /**
     * open an popup window and show the text in an editview
     * save the changes to the db
     */
    private void setOnClickComment() {
        commentColumn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TableRow row = (TableRow) v.getParent();
                final TextView textView = (TextView) row.getChildAt(4);

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

    /**
     * open the history activity which shows the pictures and comments form the past 5 years
     */
    private void setOnClickPicture() {
        pictureColumn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TableRow row = (TableRow) v.getParent();
                table.removeAllViews();
                hv_editActivity.callHistoryActivity(row.getId());
            }
        });
    }

    /**
     * go one fragment back
     */
    private void setOnClickBack() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hv_editActivity.onBackPressed();
            }
        });
    }

    /**
     * go one fragment forward
     */
    private void setOnClickNext() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });
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
}
