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

import com.cete.dynamicpdf.Document;
import com.cete.dynamicpdf.Font;
import com.cete.dynamicpdf.Page;
import com.cete.dynamicpdf.PageOrientation;
import com.cete.dynamicpdf.PageSize;
import com.cete.dynamicpdf.TextAlign;
import com.cete.dynamicpdf.pageelements.Label;
import com.cete.dynamicpdf.pageelements.Table;
import com.cete.dynamicpdf.pageelements.TextArea;
import com.example.woko_app.R;
import com.example.woko_app.activity.HV_HomeActivity;
import com.example.woko_app.activity.LoginActivity;
import com.example.woko_app.constants.ApartmentType;
import com.example.woko_app.constants.Role;
import com.example.woko_app.models.AP;
import com.example.woko_app.models.BalconyState;
import com.example.woko_app.models.BasementState;
import com.example.woko_app.models.Bathroom;
import com.example.woko_app.models.FloorState;
import com.example.woko_app.models.FurnitureState;
import com.example.woko_app.models.Kitchen;
import com.example.woko_app.models.MattressState;
import com.example.woko_app.models.PersonalSerializer;
import com.example.woko_app.models.Room;
import com.example.woko_app.models.User;
import com.example.woko_app.models.WallState;
import com.example.woko_app.models.WindowState;

import java.io.File;


public class SaveFragment extends Fragment {

    private Document document;
    private Label label;
    private static String path = Environment.getExternalStorageDirectory() + "/simple.pdf";
    private Button btnClose;
    private Button btnSend;
    private Button btnBack;
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
        btnBack = (Button) getActivity().findViewById(R.id.btnBack);
        btnBack.setText(getActivity().getResources().getString(R.string.back_btn));
        btnBack.setTypeface(font);
        btnBack.setVisibility(View.VISIBLE);
        btnLogout = (Button) getActivity().findViewById(R.id.btnNext);
        btnLogout.setText(getActivity().getResources().getString(R.string.logout_btn));
        btnLogout.setTypeface(font);
        btnLogout.setVisibility(View.VISIBLE);


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

        setCheckBoxText();
        setOnClickLogout();
        setOnClickClose();
        setOnClickSend();
        setOnClickBack();
        createPdf();
    }

    /**
     *
     */
    private void setOnClickLogout() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                Intent intent = new Intent(getActivity(), HV_HomeActivity.class);
                intent.putExtra("Username", currentUser.getUsername());
                startActivity(intent);
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
                emailIntent.putExtra(Intent.EXTRA_EMAIL, "camilla.gretsch@bluewin.ch");
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
    private void setCheckBoxText() {
        if (Role.HAUSVERANTWORTLICHER.equals(currentUser.getRole())) {
            check1.setText(getActivity().getResources().getText(R.string.HW));
            check2.setText(getActivity().getResources().getText(R.string.VW));
        }
    }

    private void createPdf() {
        document = new Document();

        label = new Label(getActivity().getResources().getString(R.string.edit_title), 0, 0, 504, 100, Font.getHelveticaBold(), 20, TextAlign.CENTER);

        Bitmap bitmap = ((BitmapDrawable)getResources().getDrawable(R.drawable.cross)).getBitmap();
        byte[] cross = PersonalSerializer.getBytes(bitmap);

        if (ApartmentType.SHARED_APARTMENT.equals(currentAP.getApartment().getType())) {
            // room
            for (Page page : Room.createPDF(currentAP, cross)) {
                document.getPages().add(page);
            }
        } else {
            // kitchen
            document.getPages().add(Kitchen.createPDF(currentAP, cross));
            // bathroom
            document.getPages().add(Bathroom.createPDF(currentAP, cross));
            // room
            for (Page page : Room.createPDF(currentAP, cross)) {
                document.getPages().add(page);
            }
            // balcony + basement
            Page page = new Page(PageSize.A4, PageOrientation.LANDSCAPE);
            com.cete.dynamicpdf.pageelements.Table table;
            float pageWidth = page.getDimensions().getWidth();
            float posY = 0;
            float padding = 20;
            Label title = new Label("Balkon", 0, 0, pageWidth, 0, Font.getHelvetica(), 18, TextAlign.LEFT);
            posY = posY + padding;
            page.getElements().add(title);
            table = BalconyState.createPDF(BalconyState.findByApartmentAndAP(currentAP.getApartment(), currentAP), pageWidth, posY, cross);
            page.getElements().add(table);
            posY = posY + padding + table.getHeight();
            title = new Label("Keller", 0, posY, pageWidth, 0, Font.getHelvetica(), 18, TextAlign.LEFT);
            posY = posY + padding;
            page.getElements().add(title);
            table = BasementState.createPDF(BasementState.findByApartmentAndAP(currentAP.getApartment(), currentAP), pageWidth, posY, cross);
            page.getElements().add(table);
            document.getPages().add(page);


        }

        try {
            document.draw(Environment.getExternalStorageDirectory() + "/simple.pdf");
        } catch (Exception e) {
            Toast.makeText(getActivity().getBaseContext(), "Error", Toast.LENGTH_SHORT);
        }

    }
}
