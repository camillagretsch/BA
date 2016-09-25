package com.example.woko_app.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.woko_app.R;
import com.example.woko_app.constants.Account;
import com.example.woko_app.models.AP;
import com.example.woko_app.models.TenantOld;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PersonalDataFragment extends Fragment {

    private EditText etStreetNr;
    private EditText etPLZ;
    private EditText etEmail;
    private CheckBox checkTenant;
    private CheckBox checkOtherPerson;
    private EditText etOtherPerson;
    private CheckBox checkPost;
    private EditText etAccNr;
    private CheckBox checkBank;
    private EditText etBankName;
    private EditText etSwift;
    private EditText etIban;
    private EditText etClearingNr;
    private TextView date;

    private AP currentAP;
    private TenantOld tenantOld;

    public PersonalDataFragment() {
        // Required empty public constructor
    }

    public static PersonalDataFragment newInstance() {
        PersonalDataFragment fragment = new PersonalDataFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_data, container, false);

        etStreetNr = (EditText) view.findViewById(R.id.etStreetNr);
        etPLZ = (EditText) view.findViewById(R.id.etPLZ);
        etEmail = (EditText) view.findViewById(R.id.etEmail);
        checkTenant = (CheckBox) view.findViewById(R.id.checkTenant);
        checkOtherPerson = (CheckBox) view.findViewById(R.id.checkOtherPerson);
        etOtherPerson = (EditText) view.findViewById(R.id.etOtherPerson);
        checkPost = (CheckBox) view.findViewById(R.id.checkPost);
        etAccNr = (EditText)view.findViewById(R.id.etAccNr);
        checkBank = (CheckBox) view.findViewById(R.id.checkBank);
        etBankName = (EditText) view.findViewById(R.id.etBankName);
        etSwift = (EditText) view.findViewById(R.id.etSwift);
        etIban = (EditText) view.findViewById(R.id.etIban);
        etClearingNr = (EditText) view.findViewById(R.id.etClearingNr);
        date = (TextView) view.findViewById(R.id.date);
        date.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date()));

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();

        if (bundle != null) {
            currentAP = AP.findById(bundle.getLong("AP"));
            tenantOld = currentAP.getTenantOld();
        }

        fillInPersonalData();
    }

    private void fillInPersonalData() {
        //Log.d("tenant with the name", tenantOld.getName());
        etStreetNr.setText(tenantOld.getStreetAndNumber());
        etPLZ.setText(tenantOld.getPlz_town_country());
        etEmail.setText(tenantOld.getEmail());

        if (tenantOld.isRefunder()) {
            checkTenant.setChecked(true);
            checkTenant.setEnabled(false);
            etOtherPerson.setEnabled(false);
        } else {
            checkOtherPerson.setChecked(true);
            checkOtherPerson.setEnabled(false);
        }

        //TODO other person

        if (Account.POST.equals(tenantOld.getAccount())) {
            checkPost.setChecked(true);
            checkPost.setEnabled(false);
            etAccNr.setText(tenantOld.getAccountNumber());
            etBankName.setEnabled(false);
            etSwift.setEnabled(false);
            etIban.setEnabled(false);
            etClearingNr.setEnabled(false);
        } else {
            checkBank.setChecked(true);
            checkBank.setEnabled(false);
            etBankName.setText(tenantOld.getBankName());
            etSwift.setText(tenantOld.getSwift());
            etIban.setText(tenantOld.getIban());
            etClearingNr.setText(tenantOld.getAccountNumber());
            etAccNr.setEnabled(false);
        }

        setOnClickStreetNr();
        setOnClickPLZ();
        setOnClickEmail();
        setOnClickTenant();
        setOnClickOtherPerson();
        setOnClickEtOtherPerson();
        setOnClickPost();
        setOnClickAccNr();
        setOnClickBank();
        setOnClickBankName();
        setOnClickSwift();
        setOnClickIban();
        setOnClickClearingNr();
    }

    private void setOnClickStreetNr() {
       etStreetNr.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           }

           @Override
           public void afterTextChanged(Editable s) {
               tenantOld.setStreetAndNumber(s.toString());
               tenantOld.save();
           }
       });
    }

    private void setOnClickPLZ() {
        etPLZ.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tenantOld.setPlz_town_country(s.toString());
                tenantOld.save();
            }
        });
    }

    private void setOnClickEmail() {
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tenantOld.setEmail(s.toString());
                tenantOld.save();
            }
        });
    }

    private void setOnClickAccNr() {
        etAccNr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tenantOld.setAccountNumber(s.toString());
                tenantOld.save();
            }
        });
    }

    private void setOnClickTenant() {
        checkTenant.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkTenant.setEnabled(false);

                    checkOtherPerson.setChecked(false);
                    checkOtherPerson.setEnabled(true);
                    etOtherPerson.setEnabled(false);
                    etOtherPerson.setText("");

                    tenantOld.setRefunder(isChecked);
                    tenantOld.save();
                }
            }
        });
    }

    private void setOnClickOtherPerson() {
        checkOtherPerson.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkOtherPerson.setEnabled(false);
                    etOtherPerson.setEnabled(true);

                    checkTenant.setChecked(false);
                    checkTenant.setEnabled(true);

                    tenantOld.setRefunder(false);
                    tenantOld.save();
                }
            }
        });
    }

    private void setOnClickEtOtherPerson() {
        etOtherPerson.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tenantOld.setOtherRefunder(s.toString());
                tenantOld.save();
            }
        });
    }

    private void setOnClickPost() {
        checkPost.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkPost.setEnabled(false);
                    etAccNr.setEnabled(true);

                    checkBank.setChecked(false);
                    checkBank.setEnabled(true);
                    etBankName.setEnabled(false);
                    etBankName.setText("");
                    etSwift.setEnabled(false);
                    etSwift.setText("");
                    etIban.setEnabled(false);
                    etIban.setText("");
                    etClearingNr.setEnabled(false);
                    etClearingNr.setText("");

                    tenantOld.setAccount(Account.POST);
                    tenantOld.save();
                }
            }
        });
    }

    private void setOnClickBank() {
        checkBank.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBank.setEnabled(false);
                    etBankName.setEnabled(true);
                    etSwift.setEnabled(true);
                    etIban.setEnabled(true);
                    etClearingNr.setEnabled(true);

                    checkPost.setChecked(false);
                    checkPost.setEnabled(true);
                    etAccNr.setEnabled(false);
                    etAccNr.setText("");

                    tenantOld.setAccount(Account.BANK);
                    tenantOld.save();
                }
            }
        });
    }

    private void setOnClickBankName() {
        etBankName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tenantOld.setBankName(s.toString());
                tenantOld.save();
            }
        });
    }

    private void setOnClickSwift() {
        etSwift.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tenantOld.setSwift(s.toString());
                tenantOld.save();
            }
        });
    }

    private void setOnClickIban() {
        etIban.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tenantOld.setIban(s.toString());
                tenantOld.save();
            }
        });
    }

    private void setOnClickClearingNr() {
        etClearingNr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tenantOld.setAccountNumber(s.toString());
                tenantOld.save();
            }
        });
    }
}
