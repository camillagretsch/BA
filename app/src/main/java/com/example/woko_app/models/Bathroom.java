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
 * Created by camillagretsch on 18.09.16.
 */
@Table(name = "Bathroom")
public class Bathroom extends Model{

    @Column(name = "apartment", unique = true, notNull = true, onUpdate = Column.ForeignKeyAction.CASCADE)
    private Apartment apartment;

    @Column(name = "APs", onUpdate = Column.ForeignKeyAction.CASCADE)
    private List<AP> APs = new ArrayList<>();

    @Column(name = "name")
    private String name = "Badezimmer";

    public Bathroom() {
        super();
    }

    public Bathroom(Apartment apartment) {
        super();
        this.apartment = apartment;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public List<AP> getAPs() {
        return getMany(AP.class, "bathroom");
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * set the name of the bathroom
     * first add the icon
     * if any of the children contains an exclamation mark in his name, then add one to the bathroom name
     * @param ap
     * @param ex
     * @param icon
     * @return
     */
    public static String updateBathroomName(AP ap, String ex, String icon) {
        Bathroom bathroom = Bathroom.findByApartment(ap.getApartment());

        int i = 0;
        while (i < updateBathroomItems(ap).size()) {
            if (updateBathroomItems(ap).get(i).contains(ex)) {
                bathroom.setName(icon + " Badezimmer " + ex);
                break;
            } else
                bathroom.setName(icon + " Badezimmer");
            i++;
        }
        bathroom.save();
        return bathroom.getName();
    }

    /**
     * get all names of the entries which belong to the bathroom
     * and save them to a list
     * shower, wall, floor, window, door, socket and radiator
     * @param ap
     * @return
     */
     public static ArrayList<String> updateBathroomItems(AP ap) {
        ArrayList<String> bathroomItems = new ArrayList();
        bathroomItems.add(ShowerState.findByBathroomAndAP(ap.getBathroom(), ap).getName());
        bathroomItems.add(WallState.findByBathroomAndAP(ap.getBathroom(), ap).getName());
        bathroomItems.add(FloorState.findByBathroomAndAP(ap.getBathroom(), ap).getName());
        bathroomItems.add(WindowState.findByBathroomAndAP(ap.getBathroom(), ap).getName());
        bathroomItems.add(DoorState.findByBathroomAndAP(ap.getBathroom(), ap).getName());
        bathroomItems.add(SocketState.findByBathroomAndAP(ap.getBathroom(), ap).getName());
        bathroomItems.add(RadiatorState.findByBathroomAndAP(ap.getBathroom(), ap).getName());
        return bathroomItems;
    }

    /**
     * search it in the db with the apartment id
     * @param apartment
     * @return
     */
    public static Bathroom findByApartment(Apartment apartment) {
        return new Select().from(Bathroom.class).where("apartment = ?", apartment.getId()).executeSingle();
    }

    /**
     * fill in the db with initial entries
     * @param apartments
     */
    public static void initializeBathroom(List<Apartment> apartments) {
        for (Apartment apartment : apartments) {
            Bathroom bathroom = new Bathroom(apartment);
            bathroom.save();
        }
    }

    public static Page createPDF(AP ap, byte[] cross) {
        Page page = new Page(PageSize.A4, PageOrientation.LANDSCAPE);
        com.cete.dynamicpdf.pageelements.Table table;
        float pageWidth = page.getDimensions().getWidth();
        float posY = 0;
        float padding = 20;
        Label title = new Label("Zimmer", 0, 0, pageWidth, 0, Font.getHelvetica(), 18, TextAlign.LEFT);
        posY = posY + 40;
        page.getElements().add(title);

        // shower
        TextArea textArea = new TextArea("WC, Dusche, Lavabo:", 0, posY, pageWidth, 0);
        posY = posY + padding;
        page.getElements().add(textArea);
        table = ShowerState.createPDF(ShowerState.findByBathroomAndAP(ap.getBathroom(), ap), pageWidth, posY, cross);
        page.getElements().add(table);
        posY = posY + padding + table.getHeight();

        // wall
        /*textArea = new TextArea("Wände, Decke:", 0, posY, pageWidth, 0);
        posY = posY + padding;
        page.getElements().add(textArea);
        table = WallState.createPDF(WallState.findByBathroomAndAP(ap.getBathroom(), ap), pageWidth, posY, cross);
        page.getElements().add(table);
        posY = posY + padding + table.getHeight();

        // floor
        textArea = new TextArea("Boden:", 0, posY, pageWidth, 0);
        posY = posY + padding;
        page.getElements().add(textArea);
        table = FloorState.createPDF(FloorState.findByBathroomAndAP(ap.getBathroom(), ap), pageWidth, posY, cross);
        page.getElements().add(table);
        posY = posY + padding + table.getHeight();

        // window
        textArea = new TextArea("Fenster:", 0, posY, pageWidth, 0);
        posY = posY + padding;
        page.getElements().add(textArea);
        table = WindowState.createPDF(WindowState.findByBathroomAndAP(ap.getBathroom(), ap), pageWidth, posY, cross);
        page.getElements().add(table);
        posY = posY + padding + table.getHeight();

        // door
        textArea = new TextArea("Tür:", 0, posY, 1000, 0);
        posY = posY + padding;
        page.getElements().add(textArea);
        table = DoorState.createPDF(DoorState.findByBathroomAndAP(ap.getBathroom(), ap), pageWidth, posY, cross);
        page.getElements().add(table);
        posY = posY + padding + table.getHeight();

        // socket
        textArea = new TextArea("Lampen, Steckdosen:", 0, posY, 1000, 0);
        posY = posY + padding;
        page.getElements().add(textArea);
        table = SocketState.createPDF(SocketState.findByBathroomAndAP(ap.getBathroom(), ap), pageWidth, posY, cross);
        page.getElements().add(table);
        posY = posY + padding + table.getHeight();

        // radiation
        textArea = new TextArea("Heizkörper, Ventil:", 0, posY, 1000, 0);
        posY = posY + padding;
        page.getElements().add(textArea);
        table = RadiatorState.createPDF(RadiatorState.findByBathroomAndAP(ap.getBathroom(), ap), pageWidth, posY, cross);
        page.getElements().add(table);*/


        return page;
    }
}
