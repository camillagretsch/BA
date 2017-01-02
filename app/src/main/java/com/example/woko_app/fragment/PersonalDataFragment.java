package com.example.woko_app.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.woko_app.R;
import com.example.woko_app.activity.SignatureActivity;
import com.example.woko_app.constants.Account;
import com.example.woko_app.models.AP;
import com.example.woko_app.models.PersonalSerializer;
import com.example.woko_app.models.Tenant;

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
    private TextView etDateOldTenant;
    private TextView etDateNewTenant;
    private TextView etDateWoko;
    private static ImageView ivOldTenant;
    private static ImageView ivTenant;
    private static ImageView ivWoko;

    private Typeface font;

    private static AP currentAP;
    private static Tenant tenant;

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
        etDateOldTenant = (TextView) view.findViewById(R.id.dateOldTenant);
        etDateNewTenant = (TextView) view.findViewById(R.id.dateNewTenant);
        etDateWoko = (TextView) view.findViewById(R.id.dateWoko);
        ivOldTenant = (ImageView) view.findViewById(R.id.ivOldTenant);
        ivTenant = (ImageView) view.findViewById(R.id.ivTenant);
        ivWoko = (ImageView) view.findViewById(R.id.ivWoko);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();

        if (bundle != null) {
            currentAP = AP.findById(bundle.getLong("AP"));
            tenant = currentAP.getTenant();
        }

        fillInPersonalData();
    }

    /**
     * fill the data about the tenant from the db in edit views
     */
    private void fillInPersonalData() {
        //Log.d("tenant with the name", tenant.getName());
        etStreetNr.setText(tenant.getStreetAndNumber());
        etPLZ.setText(tenant.getPlz_town_country());
        etEmail.setText(tenant.getEmail());

        if (tenant.isRefunder()) {
            checkTenant.setChecked(true);
            checkTenant.setEnabled(false);
            etOtherPerson.setEnabled(false);
            etOtherPerson.setText("");
        } else {
            checkOtherPerson.setChecked(true);
            checkOtherPerson.setEnabled(false);
            etOtherPerson.setText(tenant.getOtherRefunder());
        }

        if (Account.POST.equals(tenant.getAccount())) {
            checkPost.setChecked(true);
            checkPost.setEnabled(false);
            etAccNr.setText(tenant.getAccountNumber());
            etBankName.setEnabled(false);
            etSwift.setEnabled(false);
            etIban.setEnabled(false);
            etClearingNr.setEnabled(false);
        } else {
            checkBank.setChecked(true);
            checkBank.setEnabled(false);
            etBankName.setText(tenant.getBankName());
            etSwift.setText(tenant.getSwift());
            etIban.setText(tenant.getIban());
            etClearingNr.setText(tenant.getAccountNumber());
            etAccNr.setEnabled(false);
        }

        etDateOldTenant.setText(tenant.getDate());
        etDateNewTenant.setText(currentAP.getDateNewTenant());
        etDateWoko.setText(currentAP.getDateWoko());

        if (null != tenant.getSignature()) {
            ivOldTenant.setImageBitmap(PersonalSerializer.getImage(tenant.getSignature()));
        }
        if (null != currentAP.getSignatureNewTenant()) {
            ivTenant.setImageBitmap(PersonalSerializer.getImage(currentAP.getSignatureNewTenant()));
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
        setOnClickDateOldTenant();
        setOnClickDateNewTenant();
        setOnClickDateWoko();
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
               tenant.setStreetAndNumber(s.toString());
               tenant.save();
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
                tenant.setPlz_town_country(s.toString());
                tenant.save();
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
                tenant.setEmail(s.toString());
                tenant.save();
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

                    tenant.setRefunder(isChecked);
                    tenant.save();
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

                    tenant.setRefunder(false);
                    tenant.save();
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
                tenant.setOtherRefunder(s.toString());
                tenant.save();
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

                    tenant.setAccount(Account.POST);
                    tenant.save();
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
                tenant.setAccountNumber(s.toString());
                tenant.save();
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

                    tenant.setAccount(Account.BANK);
                    tenant.save();
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
                tenant.setBankName(s.toString());
                tenant.save();
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
                tenant.setSwift(s.toString());
                tenant.save();
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
                tenant.setIban(s.toString());
                tenant.save();
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
                tenant.setAccountClearingNumber(s.toString());
                tenant.save();
            }
        });
    }

    /**
     * save the change in the text view for date to the db
     */
    private void setOnClickDateOldTenant() {
        etDateOldTenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                final DatePicker datePicker = new DatePicker(getActivity());
                datePicker.setCalendarViewShown(false);
                alertDialogBuilder.setView(datePicker);
                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tenant.setDate(datePicker.getDayOfMonth() + "." + (datePicker.getMonth() + 1) + "." + datePicker.getYear());
                        tenant.save();
                        etDateOldTenant.setText(tenant.getDate());
                        getActivity().closeContextMenu();
                    }
                }).setNegativeButton("Schliessen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().closeContextMenu();
                    }
                });
                alertDialogBuilder.create().show();
            }
        });
    }

    /**
     * open the signatur activity
     * give the tenant id to the signatur activity
     */
    private void setOnClickOldTenantSignature() {
        ivOldTenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignatureActivity.class);
                intent.putExtra("tenant id", tenant.getId());
                startActivity(intent);
            }
        });
    }

    /**
     * save the change in the text view for date to the db
     */
    private void setOnClickDateNewTenant() {
        etDateNewTenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                final DatePicker datePicker = new DatePicker(getActivity());
                datePicker.setCalendarViewShown(false);
                alertDialogBuilder.setView(datePicker);
                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        currentAP.setDateNewTenant(datePicker.getDayOfMonth() + "." + (datePicker.getMonth() + 1) + "." + datePicker.getYear());
                        currentAP.save();
                        etDateNewTenant.setText(currentAP.getDateNewTenant());
                        getActivity().closeContextMenu();
                    }
                }).setNegativeButton("Schliessen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().closeContextMenu();
                    }
                });
                alertDialogBuilder.create().show();
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
     * save the change in the text view for date to the db
     */
    private void setOnClickDateWoko() {
        etDateWoko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                final DatePicker datePicker = new DatePicker(getActivity());
                datePicker.setCalendarViewShown(false);
                alertDialogBuilder.setView(datePicker);
                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        currentAP.setDateWoko(datePicker.getDayOfMonth() + "." + (datePicker.getMonth() + 1) + "." + datePicker.getYear());
                        currentAP.save();
                        etDateWoko.setText(currentAP.getDateWoko());
                        getActivity().closeContextMenu();
                    }
                }).setNegativeButton("Schliessen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().closeContextMenu();
                    }
                });
                alertDialogBuilder.create().show();
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

    /**
     * update the signature image
     * when signature activity is closed
     */
    public static void updateSignatureImage() {
        if (null != tenant.getSignature()) {
            ivOldTenant.setImageBitmap(PersonalSerializer.getImage(tenant.getSignature()));
        }

        if (null != currentAP.getSignatureNewTenant()) {
            ivTenant.setImageBitmap(PersonalSerializer.getImage(currentAP.getSignatureNewTenant()));
        }

        if (null != currentAP.getSignatureWoko()) {
            ivWoko.setImageBitmap(PersonalSerializer.getImage(currentAP.getSignatureWoko()));
        }
    }
}
