package com.example.woko_app.models;

import android.graphics.drawable.GradientDrawable;
import android.util.Log;

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
import com.example.woko_app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by camillagretsch on 08.09.16.
 */
@Table(name = "Room")
public class Room extends Model{

    @Column(name = "apartment", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private Apartment apartment;

    @Column(name = "room_number", notNull = true)
    private int roomNumber;

    @Column(name = "APs", onUpdate = Column.ForeignKeyAction.CASCADE)
    private List<AP> APs = new ArrayList<>();

    @Column(name = "name")
    private String name = "Zimmer";

    private AP unsavedAP;

    public Room() {
        super();
    }

    public Room(Apartment apartment, int roomNumber) {
        this.apartment = apartment;
        this.roomNumber = roomNumber;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public List<AP> getAPs() {
        return getMany(AP.class, "room");
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setUnsavedAP(AP unsavedAP) {
        this.unsavedAP = unsavedAP;
    }

    public AP getUnsavedAP() {
        return unsavedAP;
    }

    /**
     * set the name of the room
     * first add the icon
     * if any of the children contains an exclamation mark in his name, then add one to the room name
     * @param ap
     * @param ex
     * @param icon
     * @return
     */
    public static String updateRoomName(AP ap, String ex, String icon) {
        Room room = Room.findByApartment(ap.getApartment());

        int i = 0;
        while (i < updateRoomItems(ap).size()) {
            if (updateRoomItems(ap).get(i).contains(ex)) {
                room.setName(icon + " Zimmer " + ex);
                break;
            } else
                room.setName(icon + " Zimmer");
            i++;
        }
        room.save();
        return room.getName();
    }

    /**
     * get all names of the entries which belong to the room
     * and save them to a list
     * mattress, furniture, wall, floor, window, door, socket and radiator
     * @param ap
     * @return
     */
    public static ArrayList<String> updateRoomItems(AP ap) {
        ArrayList<String> roomItems = new ArrayList<>();
        roomItems.add(MattressState.findByRoomAndAP(ap.getRoom(), ap).getName());
        roomItems.add(FurnitureState.findByRoomAndAP(ap.getRoom(), ap).getName());
        roomItems.add(WallState.findByRoomAndAP(ap.getRoom(), ap).getName());
        roomItems.add(FloorState.findByRoomAndAP(ap.getRoom(), ap).getName());
        roomItems.add(WindowState.findByRoomAndAP(ap.getRoom(), ap).getName());
        roomItems.add(DoorState.findByRoomAndAP(ap.getRoom(), ap).getName());
        roomItems.add(SocketState.findByRoomAndAP(ap.getRoom(), ap).getName());
        roomItems.add(RadiatorState.findByRoomAndAP(ap.getRoom(), ap).getName());
        return roomItems;
    }

    /**
     * find the room by his id
     * @param id
     * @return
     */
    public static Room findById(long id) {
        return new Select().from(Room.class).where("id = ?", id).executeSingle();
    }

    /**
     * find the room by the id of his apartment
     * @param apartment
     * @return
     */
    public static Room findByApartment(Apartment apartment) {
        return new Select().from(Room.class).where("apartment = ?", apartment.getId()).executeSingle();
    }

    /**
     * creates one or more rooms depends on the apartment type and save them to the table
     * @param apartments
     */
    public static List<Room> initializeRooms(List<Apartment> apartments) {
        int size = new Select().from(Room.class).execute().size();
        List<Room> rooms = new ArrayList<>();

        if (size == 0) {
            for (int i = 0; i < apartments.size(); i++) {
                Apartment apartment = apartments.get(i);
                for (int j = 1; j <= apartment.getNumberOfRooms(); j++) {
                    Room room = new Room(apartment, j);
                    room.save();
                    rooms.add(room);
                }
            }
        }
        return rooms;
    }

    public static List<Page> createPDF(AP ap, byte[] cross) {
        List<Page> pages = new ArrayList<>();
        Page page = new Page(PageSize.A4, PageOrientation.LANDSCAPE);
        pages.add(page);
        float height;
        com.cete.dynamicpdf.pageelements.Table table;
        float pageWidth = page.getDimensions().getWidth();
        float posY = 0;
        float padding = 20;
        Label title = new Label("Zimmer", 0, 0, pageWidth, 0, Font.getHelvetica(), 18, TextAlign.LEFT);
        posY = posY + 40;
        page.getElements().add(title);

        // mattress
        TextArea textArea = new TextArea("Bettwäsche, Maratze:", 0, posY, pageWidth, 0);
        posY = posY + padding;
        page.getElements().add(textArea);
        table = MattressState.createPDF(MattressState.findByRoomAndAP(ap.getRoom(), ap), pageWidth, posY, cross);
        page.getElements().add(table);
        posY = posY + padding + table.getHeight();

        // furniture
        textArea = new TextArea("Mobiliar:", 0, posY, pageWidth, 0);
        posY = posY + padding;
        page.getElements().add(textArea);
        table = FurnitureState.createPDF(FurnitureState.findByRoomAndAP(ap.getRoom(), ap), pageWidth, posY, cross);
        height = table.getHeight();
        do {
            if ((posY + table.getRequiredHeight()) > page.getDimensions().getHeight()) {
                page.getElements().add(table);
                page = new Page(PageSize.A4, PageOrientation.LANDSCAPE);
                pages.add(page);
                System.out.println(table.getHeight());
                table = table.getOverflowRows();
            } else {
                page.getElements().add(table);
                posY = posY + padding + height;
            }
        } while (table != null);

        // wall
        textArea = new TextArea("Wände, Decke:", 0, posY, pageWidth, 0);
        posY = posY + padding;
        page.getElements().add(textArea);
        table = WallState.createPDF(WallState.findByRoomAndAP(ap.getRoom(), ap), pageWidth, posY, cross);
        page.getElements().add(table);
        height = table.getHeight();
        do {
            if ((posY + table.getRequiredHeight()) > page.getDimensions().getHeight()) {
                page.getElements().add(table);
                page = new Page(PageSize.A4, PageOrientation.LANDSCAPE);
                pages.add(page);
                System.out.println(table.getHeight());
                table = table.getOverflowRows();
            } else {
                page.getElements().add(table);
                posY = posY + padding + height;
            }
        } while (table != null);

        // floor
        textArea = new TextArea("Boden:", 0, posY, pageWidth, 0);
        posY = posY + padding;
        page.getElements().add(textArea);
        table = FloorState.createPDF(FloorState.findByRoomAndAP(ap.getRoom(), ap), pageWidth, posY, cross);
        page.getElements().add(table);
        height = table.getHeight();
        do {
            if ((posY + table.getRequiredHeight()) > page.getDimensions().getHeight()) {
                page.getElements().add(table);
                page = new Page(PageSize.A4, PageOrientation.LANDSCAPE);
                pages.add(page);
                System.out.println(table.getHeight());
                table = table.getOverflowRows();
            } else {
                page.getElements().add(table);
                posY = posY + padding + height;
            }
        } while (table != null);

        // window
        textArea = new TextArea("Fenster:", 0, posY, pageWidth, 0);
        posY = posY + padding;
        page.getElements().add(textArea);
        table = WindowState.createPDF(WindowState.findByRoomAndAP(ap.getRoom(), ap), pageWidth, posY, cross);
        page.getElements().add(table);
        height = table.getHeight();
        do {
            if ((posY + table.getRequiredHeight()) > page.getDimensions().getHeight()) {
                page.getElements().add(table);
                page = new Page(PageSize.A4, PageOrientation.LANDSCAPE);
                pages.add(page);
                System.out.println(table.getHeight());
                table = table.getOverflowRows();
            } else {
                page.getElements().add(table);
                posY = posY + padding + height;
            }
        } while (table != null);

        // door
        textArea = new TextArea("Tür:", 0, posY, 1000, 0);
        posY = posY + padding;
        page.getElements().add(textArea);
        table = DoorState.createPDF(DoorState.findByRoomAndAP(ap.getRoom(), ap), pageWidth, posY, cross);
        page.getElements().add(table);
        height = table.getHeight();
        do {
            if ((posY + table.getRequiredHeight()) > page.getDimensions().getHeight()) {
                page.getElements().add(table);
                page = new Page(PageSize.A4, PageOrientation.LANDSCAPE);
                pages.add(page);
                System.out.println(table.getHeight());
                table = table.getOverflowRows();
            } else {
                page.getElements().add(table);
                posY = posY + padding + height;
            }
        } while (table != null);

        // socket
        textArea = new TextArea("Lampen, Steckdosen:", 0, posY, 1000, 0);
        posY = posY + padding;
        page.getElements().add(textArea);
        table = SocketState.createPDF(SocketState.findByRoomAndAP(ap.getRoom(), ap), pageWidth, posY, cross);
        page.getElements().add(table);
        posY = posY + padding + table.getHeight();

        // radiator
        textArea = new TextArea("Heizkörper, Ventil:", 0, posY, 1000, 0);
        posY = posY + padding;
        page.getElements().add(textArea);
        table = RadiatorState.createPDF(RadiatorState.findByRoomAndAP(ap.getRoom(), ap), pageWidth, posY, cross);
        page.getElements().add(table);

        return pages;
    }

    public void resetRoomEntries(AP ap) {
        MattressState mattress = MattressState.findByRoomAndAP(ap.getRoom(), ap);
        MattressState m = MattressState.findByDate(mattress.getYear());
        System.out.println(m.getYear() + " " + m.getDamageComment());
        mattress.copyEntries(MattressState.findByDate(mattress.getYear()));
        mattress.save();
    }
}
