package com.example.woko_app.fragment;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.woko_app.R;
import com.example.woko_app.constants.Role;
import com.example.woko_app.models.User;


public class SaveFragment extends Fragment {

    private Typeface font;

    private Button btnClose;
    private Button btnSend;

    private CheckBox check1;
    private CheckBox check2;

    private User currentUser;

    public SaveFragment() {
        // Required empty public constructor
    }

    public SaveFragment(Typeface font) {
        this.font = font;
    }

    public static SaveFragment newInstance() {
        SaveFragment fragment = new SaveFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_save, container, false);

        btnClose = (Button) view.findViewById(R.id.btnClose);
        btnClose.setTypeface(font);
        btnSend = (Button) view.findViewById(R.id.btnSend);
        btnSend.setTypeface(font);

        check1 = (CheckBox) view.findViewById(R.id.check1);
        check2 = (CheckBox) view.findViewById(R.id.check2);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();

        if (bundle != null) {
            currentUser = User.findByUsername(bundle.getString("Username"));
        }

        setCheckBoxText();
    }

    private void setCheckBoxText() {
        if (Role.HAUSVERANTWORTLICHER.equals(currentUser.getRole())) {
            check1.setText(getActivity().getResources().getText(R.string.HW));
            check2.setText(getActivity().getResources().getText(R.string.VW));
        }
    }
}
