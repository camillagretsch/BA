package com.example.woko_app.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.woko_app.R;
import com.example.woko_app.activity.SignatureActivity;
import com.example.woko_app.constants.Account;
import com.example.woko_app.models.AP;
import com.example.woko_app.models.PersonalSerializer;
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
    private static ImageView ivOldTenant;
    private static ImageView ivTenant;
    private static ImageView ivWoko;

    private Button btnNext;
    private Button btnBack;
    private Typeface font;

    private static AP currentAP;
    private static TenantOld tenantOld;

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

        //fontawesome
        font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/fontawesome-webfont.ttf");

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
        ivOldTenant = (ImageView) view.findViewById(R.id.ivOldTenant);
        ivTenant = (ImageView) view.findViewById(R.id.ivTenant);
        ivWoko = (ImageView) view.findViewById(R.id.ivWoko);

        btnNext = (Button) getActivity().findViewById(R.id.btnNext);
        btnNext.setText(getActivity().getResources().getString(R.string.next_btn));
        btnNext.setTypeface(font);
        btnNext.setVisibility(View.VISIBLE);

        btnBack = (Button) getActivity().findViewById(R.id.btnBack);
        btnBack.setText(getActivity().getResources().getString(R.string.back_btn));
        btnBack.setTypeface(font);
        btnBack.setVisibility(View.VISIBLE);

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

    /**
     * fill the data about the tenant from the db in edit views
     */
    private void fillInPersonalData() {
        //Log.d("tenant with the name", tenantOld.getName());
        etStreetNr.setText(tenantOld.getStreetAndNumber());
        etPLZ.setText(tenantOld.getPlz_town_country());
        etEmail.setText(tenantOld.getEmail());

        if (tenantOld.isRefunder()) {
            checkTenant.setChecked(true);
            checkTenant.setEnabled(false);
            etOtherPerson.setEnabled(false);
            etOtherPerson.setText("");
        } else {
            checkOtherPerson.setChecked(true);
            checkOtherPerson.setEnabled(false);
            etOtherPerson.setText(tenantOld.getOtherRefunder());
        }

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

        if (null != tenantOld.getSignature()) {
            ivOldTenant.setImageBitmap(PersonalSerializer.getImage(tenantOld.getSignature()));
        }
        if (null != currentAP.getSignatureTenant()) {
            ivTenant.setImageBitmap(PersonalSerializer.getImage(currentAP.getSignatureTenant()));
        }
        if (null != currentAP.getSignatureWoko()) {
            ivWoko.setImageBitmap(PersonalSerializer.getImage(currentAP.getSignatureWoko()));
        }

        //set on click listener
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
        setOnClickOldTenantSignature();
        setOnClickTenantSignature();
        setOnClickWokoSignature();
    }

    /**
     * save the changes in the edit view for street and number to the db
     */
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

    /**
     * save the changes in the edit view for PLZ, town and country to the db
     */
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

    /**
     * save the changes in the edit view for email to the db
     */
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

    /**
     * save the changes in the checkbox for refunder to the db
     */
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

    /**
     * save the changes in the checkbox for refunder to the db
     */
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

    /**
     * save the changes in the edit view for refunder to the db
     */
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

    /**
     * save the changes in the checkbox for post to the db
     */
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

    /**
     * save the changes in the edit view for account number to the db
     */
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

    /**
     * save the changes in the checkbox for bank to the db
     */
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

    /**
     * save the changes in the edit view for bank name to the db
     */
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

    /**
     * save the changes in the edit view for swift to the db
     */
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

    /**
     * save the changes in the edit view for iban to the db
     */
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

    /**
     * save the changes in the edit view for clearing number to the db
     */
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

    /**
     * open the signatur activity
     * give the tenantOld id to the signatur activity
     */
    private void setOnClickOldTenantSignature() {
        ivOldTenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignatureActivity.class);
                intent.putExtra("tenantOld id", tenantOld.getId());
                startActivity(intent);
            }
        });
    }

    /**
     * open the signatur activity
     * give the ap id and the identifier tenant to the signature activity
     */
    private void setOnClickTenantSignature() {
        ivTenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignatureActivity.class);
                intent.putExtra("ap id", currentAP.getId());
                intent.putExtra("name", "tenant");
                startActivity(intent);
            }
        });
    }

    /**
     * open the signatur activity
     * give the ap id and the identifier woko to the signatur activity
     */
    private void setOnClickWokoSignature() {
        ivWoko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignatureActivity.class);
                intent.putExtra("ap id", currentAP.getId());
                intent.putExtra("name", "woko");
                startActivity(intent);
            }
        });
    }

    public static void updateSignatureImage() {
        if (null != tenantOld.getSignature()) {
            ivOldTenant.setImageBitmap(PersonalSerializer.getImage(tenantOld.getSignature()));
        }

        if (null != currentAP.getSignatureTenant()) {
            ivTenant.setImageBitmap(PersonalSerializer.getImage(currentAP.getSignatureTenant()));
        }

        if (null != currentAP.getSignatureWoko()) {
            ivWoko.setImageBitmap(PersonalSerializer.getImage(currentAP.getSignatureWoko()));
        }
    }

    /**
     *
     */
    private void setOnClickBack() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
            }
        });
    }

    /**
     *
     */
    private void setOnClickNext() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
            }
        });
    }
}
