package com.example.woko_app.models;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import com.cete.dynamicpdf.Align;
import com.cete.dynamicpdf.Document;
import com.cete.dynamicpdf.Font;
import com.cete.dynamicpdf.LineStyle;
import com.cete.dynamicpdf.Page;
import com.cete.dynamicpdf.PageSize;
import com.cete.dynamicpdf.TextAlign;
import com.cete.dynamicpdf.WebColor;
import com.cete.dynamicpdf.pageelements.Image;
import com.cete.dynamicpdf.pageelements.Label;
import com.cete.dynamicpdf.pageelements.Row2;
import com.cete.dynamicpdf.pageelements.Table2;
import com.cete.dynamicpdf.pageelements.TextArea;
import com.cete.dynamicpdf.pageelements.forms.CheckBox;
import com.example.woko_app.R;
import com.example.woko_app.constants.Account;
import com.example.woko_app.constants.ApartmentType;

/**
 * Created by camillagretsch on 29.10.16.
 */
public class PDFCreator extends Activity {

    private AP ap;

    private Document document;
    private Label label;
    private Page page;
    private Table2 table;
    private float pageHeight;
    private float pageWidth;
    private float posY;
    private float padding = 20;
    private float posX = 0;
    private TenantOld tenantOld;

    private byte[] cross;
    private byte[] logo;
    private String[] header1;
    private String[] header2;
    private String title;

    public PDFCreator(AP ap, Document document, String[] header1, String[] header2, String title, byte[] cross, byte[] logo) {
        this.ap = ap;
        this.document = document;
        this.header1 = header1;
        this.header2 = header2;
        this.title = title;
        this.cross = cross;
        this.logo = logo;
        this.tenantOld = ap.getTenantOld();
    }

    /**
     *
     */
    public void create() {
        createPage();
        label = new Label(title, 0, 0, pageWidth, 0, Font.getHelveticaBold(), 25, TextAlign.LEFT);
        Image image = new Image(logo, 500, 0);
        image.setAlign(Align.RIGHT);
        page.getElements().add(label);
        page.getElements().add(image);
        posY = posY + 40;

        createTableForPersonalData();
        createTableForRefund();
        createTableForAccountInfromations();
        createTableForSignature();

        if (ApartmentType.SHARED_APARTMENT.equals(ap.getApartment().getType())) {
            createTablesForRoom();
        } else {
            createTablesForKitchen();
            creatTablesForBathroom();
            createTablesForRoom();
            createTablesForBalconyAndBasement();
        }
    }

    private void createTableForPersonalData() {
        table = new Table2(posX, posY, pageWidth, 700);

        table.getColumns().add((pageWidth - 80) / 2);
        table.getColumns().add((pageWidth - 80) / 2);

        Row2 row = table.getRows().add(20);
        row.getCellDefault().getBorder().setLineStyle(LineStyle.getNone());
        row.getCells().add("Strasse, Nr.:");
        row.getCells().add(tenantOld.getStreetAndNumber());

        row = table.getRows().add(20);
        row.getCellDefault().getBorder().setLineStyle(LineStyle.getNone());
        row.getCells().add("PLZ, Stadt, Land");
        row.getCells().add(tenantOld.getPlz_town_country());

        row = table.getRows().add(20);
        row.getCellDefault().getBorder().setLineStyle(LineStyle.getNone());
        row.getCells().add("Email:");
        row.getCells().add(tenantOld.getEmail());

        addTableToPage("Neue Adresse des Mieters ");
    }

    private void createTableForRefund() {
        table = new Table2(posX, posY, pageWidth, 700);

        table.getColumns().add(20);
        table.getColumns().add((pageWidth - 100) / 2);
        table.getColumns().add((pageWidth - 100) / 2);

        Row2 row = table.getRows().add(20);
        row.getCellDefault().getBorder().setLineStyle(LineStyle.getNone());
        Row2 row2 = table.getRows().add(20);
        row2.getCellDefault().getBorder().setLineStyle(LineStyle.getNone());

        if (tenantOld.isRefunder()) {
            row.getCells().add(new Image(cross, 0, 0));
            row2.getCells().add("");
        } else {
            row.getCells().add("");
            row2.getCells().add(new Image(cross, 0, 0));
        }
        row.getCells().add("Mieter");
        row2.getCells().add("Andere Person, Name und Adersse:");
        row2.getCells().add(tenantOld.getOtherRefunder());

        addTableToPage("Rückerstattung der Kaution an ");
    }

    private void createTableForAccountInfromations() {
        table = new Table2(posX, posY, pageWidth, 700);

        table.getColumns().add(20);
        table.getColumns().add((pageWidth - 100) / 2);
        table.getColumns().add((pageWidth - 100) / 2);

        Row2 row = table.getRows().add(20);
        row.getCellDefault().getBorder().setLineStyle(LineStyle.getNone());
        Row2 row2 = table.getRows().add(20);
        row2.getCellDefault().getBorder().setLineStyle(LineStyle.getNone());
        Row2 row3 = table.getRows().add(20);
        row3.getCellDefault().getBorder().setLineStyle(LineStyle.getNone());

        if (Account.POST.equals(tenantOld.getAccount())) {
            row.getCells().add(new Image(cross, 0, 0));
            row3.getCells().add("");
        } else {
            row.getCells().add("");
            row3.getCells().add(new Image(cross, 0, 0));
        }
        row.getCells().add("Post");
        row3.getCells().add("Bank Account");

        row2.getCells().add("");
        row2.getCells().add("    Account Nummer:");
        row2.getCells().add(tenantOld.getAccountNumber());

        row3 = table.getRows().add(20);
        row3.getCellDefault().getBorder().setLineStyle(LineStyle.getNone());
        row3.getCells().add("");
        row3.getCells().add("   Name und Adresse der Bank:");
        row3.getCells().add(tenantOld.getBankName());

        row3 = table.getRows().add(20);
        row3.getCellDefault().getBorder().setLineStyle(LineStyle.getNone());
        row3.getCells().add("");
        row3.getCells().add("   SWIFT:");
        row3.getCells().add(tenantOld.getSwift());

        row3 = table.getRows().add(20);
        row3.getCellDefault().getBorder().setLineStyle(LineStyle.getNone());
        row3.getCells().add("");
        row3.getCells().add("   IBAN");
        row3.getCells().add(tenantOld.getIban());

        row3 = table.getRows().add(20);
        row3.getCellDefault().getBorder().setLineStyle(LineStyle.getNone());
        row3.getCells().add("");
        row3.getCells().add("   Account und Clearing Nummer:");
        row3.getCells().add(tenantOld.getAccountClearingNumber());

        addTableToPage("Kontoangaben");
    }

    private void createTableForSignature() {
        table = new Table2(posX, posY, pageWidth, 700);

        table.getColumns().add((pageWidth - 80) / 2);
        table.getColumns().add((pageWidth - 80) / 2);

        Row2 row = table.getRows().add(20);
        row.getCellDefault().getBorder().setLineStyle(LineStyle.getNone());
        row.getCells().add("");
        row.getCells().add("Datum: " + tenantOld.getDate());

        row = table.getRows().add(20);
        row.getCellDefault().getBorder().setLineStyle(LineStyle.getNone());
        row.getCells().add("Mieter:");
        if (null != tenantOld.getSignature()) {
            row.getCells().add(new Image(tenantOld.getSignature(), 0, 0));
        }

        row = table.getRows().add(20);
        row.getCellDefault().getBorder().setLineStyle(LineStyle.getNone());
        row.getCells().add("Nachmieter:");
        if (null != ap.getSignatureTenant()) {
            row.getCells().add(new Image(ap.getSignatureTenant(), 0, 0));
        }

        row = table.getRows().add(20);
        row.getCellDefault().getBorder().setLineStyle(LineStyle.getNone());
        row.getCells().add("WOKO:");
        if (null != ap.getSignatureWoko()) {
            row.getCells().add(new Image(ap.getSignatureWoko(), 0, 0));
        }

        addTableToPage("Unterschriften");
    }

    /**
     *
     */
    private void createTablesForKitchen() {
        label = new Label("Küche", posX, posY, pageWidth, 0, Font.getHelvetica(), 18, TextAlign.LEFT);
        page.getElements().add(label);
        posY = posY + 40;

        table = FridgeState.createTable(FridgeState.findByKitchenAndAP(ap.getKitchen(), ap), posX, posY, pageWidth, header1, cross);
        addTableToPage("Kühlschrank, Tiefkühlfach");

        table = VentilationState.createTable(VentilationState.findByKitchenAndAP(ap.getKitchen(), ap), posX, posY, pageWidth, header1, cross);
        addTableToPage("Dampfabzug:");

        table = OvenState.createTable(OvenState.findByKitchenAndAP(ap.getKitchen(), ap), posX, posY, pageWidth, header1, cross);
        addTableToPage("Herdplatte, Backofen:");

        table = CutleryState.createTable(CutleryState.findByKitchenAndAP(ap.getKitchen(), ap), posX, posY, pageWidth, header2, cross);
        addTableToPage("Pfannen, Geschirr, Besteck:");

        table = CupboardState.createTable(CupboardState.findByKitchenAndAP(ap.getKitchen(), ap), posX, posY, pageWidth, header1, cross);
        addTableToPage("Schränke:");

        table = WallState.createTable(WallState.findByKitchenAndAP(ap.getKitchen(), ap), posX, posY, pageWidth, header1, cross);
        addTableToPage("Wände, Decke:");

        table = FloorState.createTable(FloorState.findByKitchenAndAP(ap.getKitchen(), ap), posX, posY, pageWidth, header1, cross);
        addTableToPage("Boden:");

        table = WindowState.createTable(WindowState.findByKitchenAndAP(ap.getKitchen(), ap), posX, posY, pageWidth, header1, cross);
        addTableToPage("Fenster:");

        table = DoorState.createTable(DoorState.findByKitchenAndAP(ap.getKitchen(), ap), posX, posY, pageWidth, header1, cross);
        addTableToPage("Tür:");

        table = SocketState.createTable(SocketState.findByKitchenAndAP(ap.getKitchen(), ap), posX, posY, pageWidth, header1, cross);
        addTableToPage("Lampen, Steckdosen:");

        table = RadiatorState.createTable(RadiatorState.findByKitchenAndAP(ap.getKitchen(), ap), posX, posY, pageWidth, header1, cross);
        addTableToPage("Heizkörper, Ventil");
    }

    /**
     *
     */
    private void creatTablesForBathroom() {
        label = new Label("Badezimmer", posX, posY, pageWidth, 0, Font.getHelvetica(), 18, TextAlign.LEFT);
        page.getElements().add(label);
        posY = posY + 40;

        table = ShowerState.createTable(ShowerState.findByBathroomAndAP(ap.getBathroom(), ap), posX, posY, pageWidth, header1, cross);
        addTableToPage("WC, Dusche, Lavabo:");

        table = WallState.createTable(WallState.findByBathroomAndAP(ap.getBathroom(), ap), posX, posY, pageWidth, header1, cross);
        addTableToPage("Wände, Decke:");

        table = FloorState.createTable(FloorState.findByBathroomAndAP(ap.getBathroom(), ap), posX, posY, pageWidth, header1, cross);
        addTableToPage("Boden:");

        table = WindowState.createTable(WindowState.findByBathroomAndAP(ap.getBathroom(), ap), posX, posY, pageWidth, header1, cross);
        addTableToPage("Fenster:");

        table = DoorState.createTable(DoorState.findByBathroomAndAP(ap.getBathroom(), ap), posX, posY, pageWidth, header1, cross);
        addTableToPage("Tür:");

        table = SocketState.createTable(SocketState.findByBathroomAndAP(ap.getBathroom(), ap), posX, posY, pageWidth, header1, cross);
        addTableToPage("Lampen, Steckdosen:");

        table = RadiatorState.createTable(RadiatorState.findByBathroomAndAP(ap.getBathroom(), ap), posX, posY, pageWidth, header1, cross);
        addTableToPage("Heizkörper, Ventil");
    }

    /**
     *
     */
    private void createTablesForRoom() {
        label = new Label("Zimmer", posX, posY, pageWidth, 0, Font.getHelvetica(), 18, TextAlign.LEFT);
        page.getElements().add(label);
        posY = posY + 40;

        table = MattressState.createTable(MattressState.findByRoomAndAP(ap.getRoom(), ap), posX, posY, pageWidth, header1, cross);
        addTableToPage("Bettwäsche, Matratze:");

        table = FurnitureState.createTable(FurnitureState.findByRoomAndAP(ap.getRoom(), ap), posX, posY, pageWidth, header2, cross);
        addTableToPage("Mobiliar:");

        table = WallState.createTable(WallState.findByRoomAndAP(ap.getRoom(), ap), posX, posY, pageWidth, header1, cross);
        addTableToPage("Wände, Decke:");

        table = FloorState.createTable(FloorState.findByRoomAndAP(ap.getRoom(), ap), posX, posY, pageWidth, header1, cross);
        addTableToPage("Boden:");

        table = WindowState.createTable(WindowState.findByRoomAndAP(ap.getRoom(), ap), posX, posY, pageWidth, header1, cross);
        addTableToPage("Fenster:");

        table = DoorState.createTable(DoorState.findByRoomAndAP(ap.getRoom(), ap), posX, posY, pageWidth, header1, cross);
        addTableToPage("Tür:");

        table = SocketState.createTable(SocketState.findByRoomAndAP(ap.getRoom(), ap), posX, posY, pageWidth, header1, cross);
        addTableToPage("Lampen, Steckdosen:");

        table = RadiatorState.createTable(RadiatorState.findByRoomAndAP(ap.getRoom(), ap), posX, posY, pageWidth, header1, cross);
        addTableToPage("Heizkörper, Ventil");
    }

    /**
     *
     */
    private void createTablesForBalconyAndBasement() {
        table = BalconyState.createTable(BalconyState.findByApartmentAndAP(ap.getApartment(), ap), posX, posY, pageWidth, header1, cross);
        addTableToPage("Balkon");

        table = BasementState.createTable(BasementState.findByApartmentAndAP(ap.getApartment(), ap), posX, posY, pageWidth, header1, cross);
        addTableToPage("Keller");
    }

    /**
     *
     * @param text
     */
    private void addTableToPage(String text) {
        float tableHeight = 0;
        float tmp = posY + padding + 60;
        if ((table.getRequiredHeight() + tmp > pageHeight)) {
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

    /**
     *
     * @param text
     */
    private void addTextAreaToPage(String text) {
        TextArea textArea = new TextArea(text, posX, posY, pageWidth, 0);
        page.getElements().add(textArea);
        posY = posY + padding;
    }

    /**
     *
     */
    private void createPage() {
        page = new Page(PageSize.A4);
        document.getPages().add(page);
        pageWidth = page.getDimensions().getWidth();
        pageHeight = page.getDimensions().getHeight();
        posY = 0;
    }
}
