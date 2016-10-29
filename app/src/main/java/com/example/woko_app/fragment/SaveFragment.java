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
    private Label label;
    private Page page;
    private Table2 table;
    private float pageHeight;
    private float pageWidth;
    private float posY;
    private float padding = 20;
    private float posX = 0;
    private byte[] cross;
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

        // Create a PDF Document
        document = new Document();
        createPage();
        label = new Label(getActivity().getResources().getString(R.string.edit_title), posX, posY, pageWidth, 0, Font.getHelveticaBold(), 20, TextAlign.CENTER);
        page.getElements().add(label);
        posY = posY + 30;

        Bitmap bitmap = ((BitmapDrawable)getResources().getDrawable(R.drawable.cross)).getBitmap();
        cross = PersonalSerializer.getBytes(bitmap);

        if (ApartmentType.SHARED_APARTMENT.equals(currentAP.getApartment().getType())) {
            createTablesForRoom();
        } else {
            createTablesForKitchen();
            creatTablesForBathroom();
            createTablesForRoom();
        }

        try {
            document.draw(Environment.getExternalStorageDirectory() + "/simple.pdf");
        } catch (Exception e) {
            Toast.makeText(getActivity().getBaseContext(), "Error", Toast.LENGTH_SHORT);
        }
    }

    private void createTablesForKitchen() {
        label = new Label("Küche", posX, posY, pageWidth, 0, Font.getHelvetica(), 18, TextAlign.LEFT);
        page.getElements().add(label);

        table = FridgeState.createPDF(FridgeState.findByKitchenAndAP(currentAP.getKitchen(), currentAP), posX, posY, pageWidth, cross);
        addTableToPage("Kühlschrank, Tiefkühlfach");

        table = VentilationState.createPDF(VentilationState.findByKitchenAndAP(currentAP.getKitchen(), currentAP), posX, posY, pageWidth, cross);
        addTableToPage("Dampfabzug:");

        table = OvenState.createPDF(OvenState.findByKitchenAndAP(currentAP.getKitchen(), currentAP), posX, posY, pageWidth, cross);
        addTableToPage("Herdplatte, Backofen:");

        table = CutleryState.createPDF(CutleryState.findByKitchenAndAP(currentAP.getKitchen(), currentAP), posX, posY, pageWidth, cross);
        addTableToPage("Pfannen, Geschirr, Besteck:");

        table = CupboardState.createPDF(CupboardState.findByKitchenAndAP(currentAP.getKitchen(), currentAP), posX, posY, pageWidth, cross);
        addTableToPage("Schränke:");

        table = WallState.createPDF(WallState.findByKitchenAndAP(currentAP.getKitchen(), currentAP), posX, posY, pageWidth, cross);
        addTableToPage("Wände, Decke:");

        table = FloorState.createPDF(FloorState.findByKitchenAndAP(currentAP.getKitchen(), currentAP), posX, posY, pageWidth, cross);
        addTableToPage("Boden:");

        table = WindowState.createPDF(WindowState.findByKitchenAndAP(currentAP.getKitchen(), currentAP), posX, posY, pageWidth, cross);
        addTableToPage("Fenster:");

        table = DoorState.createPDF(DoorState.findByKitchenAndAP(currentAP.getKitchen(), currentAP), posX, posY, pageWidth, cross);
        addTableToPage("Tür:");

        table = SocketState.createPDF(SocketState.findByKitchenAndAP(currentAP.getKitchen(), currentAP), posX, posY, pageWidth, cross);
        addTableToPage("Lampen, Steckdosen:");

        table = RadiatorState.createPDF(RadiatorState.findByKitchenAndAP(currentAP.getKitchen(), currentAP), posX, posY, pageWidth, cross);
        addTableToPage("Heizkörper, Ventil");
    }

    private void creatTablesForBathroom() {
        label = new Label("Badezimmer", posX, posY, pageWidth, 0, Font.getHelvetica(), 18, TextAlign.LEFT);
        page.getElements().add(label);

        table = ShowerState.createPDF(ShowerState.findByBathroomAndAP(currentAP.getBathroom(), currentAP), posX, posY, pageWidth, cross);
        addTableToPage("WC, Dusche, Lavabo:");

        table = WallState.createPDF(WallState.findByBathroomAndAP(currentAP.getBathroom(), currentAP), posX, posY, pageWidth, cross);
        addTableToPage("Wände, Decke:");

        table = FloorState.createPDF(FloorState.findByBathroomAndAP(currentAP.getBathroom(), currentAP), posX, posY, pageWidth, cross);
        addTableToPage("Boden:");

        table = WindowState.createPDF(WindowState.findByBathroomAndAP(currentAP.getBathroom(), currentAP), posX, posY, pageWidth, cross);
        addTableToPage("Fenster:");

        table = DoorState.createPDF(DoorState.findByBathroomAndAP(currentAP.getBathroom(), currentAP), posX, posY, pageWidth, cross);
        addTableToPage("Tür:");

        table = SocketState.createPDF(SocketState.findByBathroomAndAP(currentAP.getBathroom(), currentAP), posX, posY, pageWidth, cross);
        addTableToPage("Lampen, Steckdosen:");

        table = RadiatorState.createPDF(RadiatorState.findByBathroomAndAP(currentAP.getBathroom(), currentAP), posX, posY, pageWidth, cross);
        addTableToPage("Heizkörper, Ventil");
    }

    private void createTablesForRoom() {
        label = new Label("Zimmer", posX, posY, pageWidth, 0, Font.getHelvetica(), 18, TextAlign.LEFT);
        page.getElements().add(label);

        table = MattressState.createPDF(MattressState.findByRoomAndAP(currentAP.getRoom(), currentAP), posX, posY, pageWidth, cross);
        addTableToPage("Bettwäsche, Matratze:");

        table = FurnitureState.createPDF(FurnitureState.findByRoomAndAP(currentAP.getRoom(), currentAP), posX, posY, pageWidth, cross);
        addTableToPage("Mobiliar:");

        table = WallState.createPDF(WallState.findByRoomAndAP(currentAP.getRoom(), currentAP), posX, posY, pageWidth, cross);
        addTableToPage("Wände, Decke:");

        table = FloorState.createPDF(FloorState.findByRoomAndAP(currentAP.getRoom(), currentAP), posX, posY, pageWidth, cross);
        addTableToPage("Boden:");

        table = WindowState.createPDF(WindowState.findByRoomAndAP(currentAP.getRoom(), currentAP), posX, posY, pageWidth, cross);
        addTableToPage("Fenster:");

        table = DoorState.createPDF(DoorState.findByRoomAndAP(currentAP.getRoom(), currentAP), posX, posY, pageWidth, cross);
        addTableToPage("Tür:");

        table = SocketState.createPDF(SocketState.findByRoomAndAP(currentAP.getRoom(), currentAP), posX, posY, pageWidth, cross);
        addTableToPage("Lampen, Steckdosen:");

        table = RadiatorState.createPDF(RadiatorState.findByRoomAndAP(currentAP.getRoom(), currentAP), posX, posY, pageWidth, cross);
        addTableToPage("Heizkörper, Ventil");
       /* // Create a table
        table = new Table2(0, 0, pageWidth, 700);

        //Add columns to the table
        table.getColumns().add(100);
        table.getColumns().add(100);

        // This loop populates the table
        for ( int i = 1; i <= 400; i++ )  {
            Row2 row = table.getRows().add( 20 );
            row.getCells().add( "Row #" + i );
            row.getCells().add( "Item" );
        }
        addTableToPage();*/
    }

    private void createTableForBalconyAndBasement() {
        label = new Label("Balkon", posX, posY, pageWidth, 0, Font.getHelvetica(), 18, TextAlign.LEFT);
        page.getElements().add(label);

        table = BalconyState.createPDF(BalconyState.findByApartmentAndAP(currentAP.getApartment(), currentAP), posX, posY, pageWidth, cross);
        addTableToPage("");

        label = new Label("Keller", posX, posY, pageWidth, 0, Font.getHelvetica(), 18, TextAlign.LEFT);
        page.getElements().add(label);

        table = BasementState.createPDF(BasementState.findByApartmentAndAP(currentAP.getApartment(), currentAP), posX, posY, pageWidth, cross);
    }

    private void addTableToPage(String text) {
        float tableHeight = 0;
        if ((table.getRequiredHeight() + posY > pageHeight)) {
            do {
                createPage();
                addTextAreaToPage(text);
                table.setY(posY);
                tableHeight = table.getRequiredHeight();
                page.getElements().add( table );
                table = table.getOverflowRows();
            } while ( table != null );
        } else {
            addTextAreaToPage(text);
            table.setY(posY);
            tableHeight = table.getRequiredHeight();
            page.getElements().add(table);
        }

        posY = posY + tableHeight + padding;
    }

    private void addTextAreaToPage(String text) {
        TextArea textArea = new TextArea(text, posX, posY, pageWidth, 0);
        page.getElements().add(textArea);
        posY = posY + padding;
    }

    private void createPage() {
        page = new Page(PageSize.A4);
        document.getPages().add(page);
        pageWidth = page.getDimensions().getWidth();
        pageHeight = page.getDimensions().getHeight();
        posY = 0;
    }
}

