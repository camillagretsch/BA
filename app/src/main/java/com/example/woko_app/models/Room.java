package com.example.woko_app.models;

import android.graphics.drawable.GradientDrawable;
import android.inputmethodservice.Keyboard;
import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.cete.dynamicpdf.Document;
import com.cete.dynamicpdf.Font;
import com.cete.dynamicpdf.Page;
import com.cete.dynamicpdf.PageOrientation;
import com.cete.dynamicpdf.PageSize;
import com.cete.dynamicpdf.TextAlign;
import com.cete.dynamicpdf.pageelements.Label;
import com.cete.dynamicpdf.pageelements.Row;
import com.cete.dynamicpdf.pageelements.RowList;
import com.cete.dynamicpdf.pageelements.Table2;
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

    @Column(name = "name")
    private String name = "Zimmer";

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

    /**
     * set the name of the room
     * first add the icon
     * if any of the children contains an exclamation mark in his name, then add one to the room name
     * @param ap
     * @param newEx
     * @param oldEx
     * @param icon
     * @return
     */
    public static String updateRoomName(AP ap, String newEx, String oldEx, String icon) {
        Room room = Room.findByApartment(ap.getApartment());
        room.setName(icon + " Zimmer ");
        room.save();

        int i = 0;
        while (i < updateRoomItems(ap).size()) {
            if (updateRoomItems(ap).get(i).contains(newEx)) {
                room.setName(room.getName().concat(newEx));
                break;
            }
            i++;
        }

        int j = 0;
        while (j < updateRoomItems(ap).size()) {
            if (updateRoomItems(ap).get(j).contains(oldEx)) {
                room.setName(room.getName().concat(oldEx));
                break;
            }
            j++;
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
}
