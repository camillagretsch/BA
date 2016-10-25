package com.example.woko_app.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.cete.dynamicpdf.Font;
import com.cete.dynamicpdf.Page;
import com.cete.dynamicpdf.PageOrientation;
import com.cete.dynamicpdf.PageSize;
import com.cete.dynamicpdf.TextAlign;
import com.cete.dynamicpdf.pageelements.Label;
import com.cete.dynamicpdf.pageelements.TextArea;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by camillagretsch on 19.09.16.
 */
@Table(name = "Kitchen")
public class Kitchen  extends Model{

    @Column(name = "apartment", unique = true, notNull = true, onUpdate = Column.ForeignKeyAction.CASCADE)
    private Apartment apartment;

    @Column(name = "APs", onUpdate = Column.ForeignKeyAction.CASCADE)
    private List<AP> APs = new ArrayList<>();

    @Column(name = "name")
    private String name = "Küche";

    public Kitchen() {
        super();
    }

    public Kitchen(Apartment apartment) {
        super();
        this.apartment = apartment;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public List<AP> getAPs() {
        return getMany(AP.class, "kitchen");
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * set the name of the kitchen
     * first add the icon
     * if any of the children contains an exclamation mark in his name, then add one to the kitchen name
     * @param ap
     * @param ex
     * @param icon
     * @return
     */
    public static String updateKitchenName(AP ap, String ex, String icon) {
        Kitchen kitchen = Kitchen.findByApartment(ap.getApartment());

        int i = 0;
        while (i < updateKitchenItems(ap).size()) {
            if (updateKitchenItems(ap).get(i).contains(ex)) {
                kitchen.setName(icon + " Küche " + ex);
                break;
            } else
                kitchen.setName(icon + " Küche");
            i++;
        }
        kitchen.save();
        return kitchen.getName();
    }

    /**
     * get all names of the entries which belong to the kitchen
     * and save them to a list
     * fridge, ventilation, oven, cutlery, cupboard, wall, floor, window, door, socket and radiator
     * @param ap
     * @return
     */
    public static ArrayList<String> updateKitchenItems(AP ap) {
        ArrayList<String> kitchenItems = new ArrayList<>();
        kitchenItems.add(FridgeState.findByKitchenAndAP(ap.getKitchen(), ap).getName());
        kitchenItems.add(VentilationState.findByKitchenAndAP(ap.getKitchen(), ap).getName());
        kitchenItems.add(OvenState.findByKitchenAndAP(ap.getKitchen(), ap).getName());
        kitchenItems.add(CutleryState.findByKitchenAndAP(ap.getKitchen(), ap).getName());
        kitchenItems.add(CupboardState.findByKitchenAndAP(ap.getKitchen(), ap).getName());
        kitchenItems.add(WallState.findByKitchenAndAP(ap.getKitchen(), ap).getName());
        kitchenItems.add(FloorState.findByKitchenAndAP(ap.getKitchen(), ap).getName());
        kitchenItems.add(WindowState.findByKitchenAndAP(ap.getKitchen(), ap).getName());
        kitchenItems.add(DoorState.findByKitchenAndAP(ap.getKitchen(), ap).getName());
        kitchenItems.add(SocketState.findByKitchenAndAP(ap.getKitchen(), ap).getName());
        kitchenItems.add(RadiatorState.findByKitchenAndAP(ap.getKitchen(), ap).getName());
        return kitchenItems;
    }

    /**
     * search it in the db with the apartment id
     * @param apartment
     * @return
     */
    public static Kitchen findByApartment(Apartment apartment) {
        return new Select().from(Kitchen.class).where("apartment = ?", apartment.getId()).executeSingle();
    }

    /**
     * fill in the db with initial entries
     * @param apartments
     */
    public static void initializeKitchen(List<Apartment> apartments) {
        for (Apartment apartment : apartments) {
            Kitchen kitchen = new Kitchen(apartment);
            kitchen.save();
        }
    }

    public static Page createPDF(AP ap, byte[] cross) {
        Page page = new Page(PageSize.A4, PageOrientation.LANDSCAPE);
        com.cete.dynamicpdf.pageelements.Table table;
        float pageWidth = page.getDimensions().getWidth();
        float posY = 0;
        float padding = 20;
        Label title = new Label("Küche", 0, 0, pageWidth, 0, Font.getHelvetica(), 18, TextAlign.LEFT);
        posY = posY + 40;
        page.getElements().add(title);

        // fridge
        TextArea textArea = new TextArea("Kühlschrank, Tiefkühlfach:", 0, posY, pageWidth, 0);
        posY = posY + padding;
        page.getElements().add(textArea);
        table = FridgeState.createPDF(FridgeState.findByKitchenAndAP(ap.getKitchen(), ap), pageWidth, posY, cross);
        page.getElements().add(table);
        posY = posY + padding + table.getHeight();

        // ventilation
        textArea = new TextArea("Dampfabzug:", 0, posY, pageWidth, 0);
        posY = posY + padding;
        page.getElements().add(textArea);
        table = VentilationState.createPDF(VentilationState.findByKitchenAndAP(ap.getKitchen(), ap), pageWidth, posY, cross);
        page.getElements().add(table);
        posY = posY + padding + table.getHeight();

        // oven
        textArea = new TextArea("Herdplatte, Backofen:", 0, posY, pageWidth, 0);
        posY = posY + padding;
        page.getElements().add(textArea);
        table = OvenState.createPDF(OvenState.findByKitchenAndAP(ap.getKitchen(), ap), pageWidth, posY, cross);
        page.getElements().add(table);
        posY = posY + padding + table.getHeight();

        // cutlery
        textArea = new TextArea("Pfannen, Geschirr, Besteck:", 0, posY, pageWidth, 0);
        posY = posY + padding;
        page.getElements().add(textArea);
        table = CutleryState.createPDF(CutleryState.findByKitchenAndAP(ap.getKitchen(), ap), pageWidth, posY, cross);
        page.getElements().add(table);
        posY = posY + padding + table.getHeight();

        // cupboard
        textArea = new TextArea("Schränke", 0, posY, pageWidth, 0);
        posY = posY + padding;
        page.getElements().add(textArea);
        table = CupboardState.createPDF(CupboardState.findByKitchenAndAP(ap.getKitchen(), ap), pageWidth, posY, cross);
        page.getElements().add(table);
        posY = posY + padding + table.getHeight();

        // wall
       /* textArea = new TextArea("Wände, Decke:", 0, posY, pageWidth, 0);
        posY = posY + padding;
        page.getElements().add(textArea);
        table = WallState.createPDF(WallState.findByKitchenAndAP(ap.getKitchen(), ap), pageWidth, posY, cross);
        page.getElements().add(table);
        posY = posY + padding + table.getHeight();

        // floor
        textArea = new TextArea("Boden:", 0, posY, pageWidth, 0);
        posY = posY + padding;
        page.getElements().add(textArea);
        table = FloorState.createPDF(FloorState.findByKitchenAndAP(ap.getKitchen(), ap), pageWidth, posY, cross);
        page.getElements().add(table);
        posY = posY + padding + table.getHeight();

        // window
        textArea = new TextArea("Fenster:", 0, posY, pageWidth, 0);
        page.getElements().add(textArea);
        table = WindowState.createPDF(WindowState.findByKitchenAndAP(ap.getKitchen(), ap), pageWidth, posY, cross);
        page.getElements().add(table);
        posY = posY + padding + table.getHeight();

        // door
        textArea = new TextArea("Tür:", 0, posY, pageWidth, 0);
        page.getElements().add(textArea);
        table = DoorState.createPDF(DoorState.findByKitchenAndAP(ap.getKitchen(), ap), pageWidth, posY, cross);
        page.getElements().add(table);
        posY = posY + padding + table.getHeight();

        // socket
        textArea = new TextArea("Lampen, Steckdosen:", 0, posY, pageWidth, 0);
        page.getElements().add(textArea);
        table = SocketState.createPDF(SocketState.findByKitchenAndAP(ap.getKitchen(), ap), pageWidth, posY, cross);
        page.getElements().add(table);
        posY = posY + padding + table.getHeight();

        // radiator
        textArea = new TextArea("Heizkörper, Ventil:", 0, posY, pageWidth, 0);
        page.getElements().add(textArea);
        table = RadiatorState.createPDF(RadiatorState.findByKitchenAndAP(ap.getKitchen(), ap), pageWidth, posY, cross);
        page.getElements().add(table);*/

        return page;
    }
}
