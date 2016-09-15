package com.example.woko_app.fragment;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.woko_app.R;
import com.example.woko_app.constants.APStatus;
import com.example.woko_app.constants.ApartmentType;
import com.example.woko_app.models.AP;
import com.example.woko_app.models.Apartment;
import com.example.woko_app.models.Floor;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link DataGridFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DataGridFragment extends Fragment {

    private TableLayout table;
    private TableRow header;

    private Typeface font;

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

        header = (TableRow) view.findViewById(R.id.header);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {

        }

    }
}
