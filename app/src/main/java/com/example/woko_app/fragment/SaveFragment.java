package com.example.woko_app.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;


import com.cete.dynamicpdf.Align;
import com.cete.dynamicpdf.Document;
import com.cete.dynamicpdf.Font;
import com.cete.dynamicpdf.Grayscale;
import com.cete.dynamicpdf.LineStyle;
import com.cete.dynamicpdf.Page;
import com.cete.dynamicpdf.PageOrientation;
import com.cete.dynamicpdf.PageSize;
import com.cete.dynamicpdf.RgbColor;
import com.cete.dynamicpdf.TextAlign;
import com.cete.dynamicpdf.VAlign;
import com.cete.dynamicpdf.pageelements.Cell2;
import com.cete.dynamicpdf.pageelements.Column2;
import com.cete.dynamicpdf.pageelements.Label;
import com.cete.dynamicpdf.pageelements.Row2;
import com.cete.dynamicpdf.pageelements.Table2;
import com.cete.dynamicpdf.pageelements.TextArea;
import com.example.woko_app.R;
import com.example.woko_app.activity.HV_EditActivity;
import com.example.woko_app.activity.HV_HomeActivity;
import com.example.woko_app.activity.LoginActivity;
import com.example.woko_app.constants.ApartmentType;
import com.example.woko_app.constants.Role;
import com.example.woko_app.models.AP;
import com.example.woko_app.models.BalconyState;
import com.example.woko_app.models.BasementState;
import com.example.woko_app.models.Bathroom;
import com.example.woko_app.models.CupboardState;
import com.example.woko_app.models.CutleryState;
import com.example.woko_app.models.DoorState;
import com.example.woko_app.models.FloorState;
import com.example.woko_app.models.FridgeState;
import com.example.woko_app.models.FurnitureState;
import com.example.woko_app.models.Kitchen;
import com.example.woko_app.models.MattressState;
import com.example.woko_app.models.OvenState;
import com.example.woko_app.models.PDFCreator;
import com.example.woko_app.models.PersonalSerializer;
import com.example.woko_app.models.RadiatorState;
import com.example.woko_app.models.Room;
import com.example.woko_app.models.ShowerState;
import com.example.woko_app.models.SocketState;
import com.example.woko_app.models.User;
import com.example.woko_app.models.VentilationState;
import com.example.woko_app.models.WallState;
import com.example.woko_app.models.WindowState;

import java.io.File;


public class SaveFragment extends Fragment {

    private Document document;
    private static String path;
    private Button btnClose;
    private Button btnSend;
    private Button btnLogout;


    private CheckBox check1;
    private CheckBox check2;

    private User currentUser;
    private AP currentAP;

    private Typeface font;

    public SaveFragment() {
        // Required empty public constructor
    }

    public static SaveFragment newInstance() {
        SaveFragment fragment = new SaveFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_save, container, false);

        //fontawesome
        font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/fontawesome-webfont.ttf");

        btnClose = (Button) view.findViewById(R.id.btnClose);
        btnClose.setTypeface(font);
        btnSend = (Button) view.findViewById(R.id.btnSend);
        btnSend.setTypeface(font);
        btnLogout = (Button) view.findViewById(R.id.btnLogout);
        btnLogout.setTypeface(font);


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
            currentAP = AP.findById(bundle.getLong("AP"));
        }

        path = Environment.getExternalStorageDirectory() + "/Abnahmeprotokoll_" + currentAP.getName() + ".pdf";
        createPDF();
        setCheckBoxText();
        setOnClickLogout();
        setOnClickClose();
        setOnClickSend();
    }

    /**
     *
     */
    private void setOnClickLogout() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().finish();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     *
     */
    private void setOnClickClose() {
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HV_EditActivity editActivity = (HV_EditActivity) getActivity();
                editActivity.callHomeActivity();
            }
        });
    }

    /**
     *
     */
    private void setOnClickSend() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.fromFile(new File(path));
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, "");
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Abnahmeprotokoll");
                emailIntent.setType("application/pdf");
                emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(emailIntent, "Send email"));
            }
        });
    }

    /**
     *
     */
    private void setCheckBoxText() {
        if (Role.HAUSVERANTWORTLICHER.equals(currentUser.getRole())) {
            check1.setText(getActivity().getResources().getText(R.string.HW));
            check2.setText(getActivity().getResources().getText(R.string.VW));
        }
    }

    /**
     *
     */
    private void createPDF() {
        document = new Document();

        Bitmap bitmap = ((BitmapDrawable)getResources().getDrawable(R.drawable.cross)).getBitmap();
        byte[] cross = PersonalSerializer.getBytes(bitmap);

        bitmap = ((BitmapDrawable)getResources().getDrawable(R.drawable.logo_gross)).getBitmap();
        byte[] logo = PersonalSerializer.getBytes(bitmap);

        PDFCreator pdfCreator = new PDFCreator(currentAP, document, getActivity().getResources().getStringArray(R.array.header_variante1), getActivity().getResources().getStringArray(R.array.header_variante2), getActivity().getResources().getString(R.string.edit_title), cross, logo);
        pdfCreator.create();

        try {
            document.draw(path);
        } catch (Exception e) {
            Toast.makeText(getActivity().getBaseContext(), "Error", Toast.LENGTH_SHORT);
        }

    }
}

