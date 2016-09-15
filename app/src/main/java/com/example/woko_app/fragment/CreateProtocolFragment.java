package com.example.woko_app.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.woko_app.R;
import com.example.woko_app.activity.HV_HomeActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class CreateProtocolFragment extends Fragment {

    private Button btnNew;

    public CreateProtocolFragment() {
        // Required empty public constructor
    }

    public static CreateProtocolFragment newInstance() {

        Bundle args = new Bundle();

        CreateProtocolFragment fragment = new CreateProtocolFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_protocol, container, false);

        btnNew = (Button) view.findViewById(R.id.btnNew);
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("new Protocol", "is initialized");
                HV_HomeActivity hv_homeActivity = (HV_HomeActivity) getActivity();
                hv_homeActivity.callDuplicateFragment();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = new Bundle();
        if (bundle != null) {

        }
    }
}
