package com.example.woko_app.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
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

    /**
     *
     * @param id
     * @return
     */
    public static Room findById(long id) {
        return new Select().from(Room.class).where("id = ?", id).executeSingle();
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
