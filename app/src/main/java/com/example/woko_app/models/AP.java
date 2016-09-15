package com.example.woko_app.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by camillagretsch on 09.09.16.
 */
@Table(name = "AP")
public class AP extends Model{

    @Column(name = "day", notNull = true)
    private long day;

    @Column(name = "month", notNull = true)
    private long month;

    @Column(name = "year", notNull = true)
    private long year;

    @Column(name = "name", notNull = true)
    private String name;

    @Column(name = "room", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Room room;

    @Column(name = "apartment", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Apartment apartment;

    @Column(name = "oldAP")
    private AP oldAP;

    public AP() {
        super();
    }

    public AP(long day, long month, long year) {
        super();
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public long getDay() {
        return day;
    }

    public long getMonth() {
        return month;
    }

    public long getYear() {
        return year;
    }

    public void setSharedApartmentName(String street, String streetNr, int apartmentNr, int roomNr, long day, long month, long year) {
        this.name = street + "." + streetNr + "_W" + apartmentNr + "_Z" + roomNr + "_" + day + "." + month + "." + year;
    }

    public void setStudioName(String street, String streetNr, int apartmentNr, long day, long month, long year) {
        this.name = street + "." + streetNr + "_W" + apartmentNr + "_" + day + "." + month + "." + year;
    }

    public String getName() {
        return name;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setOldAP(AP oldAP) {
        this.oldAP = oldAP;
    }

    public AP getOldAP() {
        return oldAP;
    }

    public static List<AP> findByRoom(Room room) {
        return new Select().from(AP.class).where("room = ?", room.getId()).orderBy("day DESC").orderBy("month DESC").orderBy("year DESC").execute();
    }

    public static List<AP> findByApartment(Apartment apartment) {
        return new Select().from(AP.class).where("apartment = ?", apartment.getId()).orderBy("day DESC").orderBy("month DESC").orderBy("year DESC").execute();
    }

    public static AP findById(long id) {
        return new Select().from(AP.class).where("id = ?", id).executeSingle();
    }

    public static List<AP> findByRoomAndYear(Room room, long year) {
        return new Select().from(AP.class).where("room = ? and year = ?", room.getId(), year).execute();
    }

    public static List<AP> findByApartmentAndYear(Apartment apartment, long year) {
        return new Select().from(AP.class).where("apartment = ? and year = ?", apartment.getId(), year).execute();
    }

    public static void initializeAPs(List<House> houses) {
        int size = new Select().from(AP.class).execute().size();

        if (size == 0) {
            House house = houses.get(0);
            Apartment apartment = house.getApartments().get(0);

            Room room = apartment.getRooms().get(0);
            AP ap = new AP(31, 07, 2015);
            ap.setRoom(room);
            ap.setApartment(apartment);
            ap.setSharedApartmentName(house.getStreet(), house.getStreetNumber(), room.getApartment().getApartmentNumber(), room.getRoomNumber(), ap.getDay(), ap.getMonth(), ap.getYear());
            ap.save();

            ap = new AP(25, 07, 2015);
            ap.setRoom(room);
            ap.setApartment(apartment);
            ap.setSharedApartmentName(house.getStreet(), house.getStreetNumber(), room.getApartment().getApartmentNumber(), room.getRoomNumber(), ap.getDay(), ap.getMonth(), ap.getYear());
            ap.save();

            room = house.getApartments().get(0).getRooms().get(2);
            ap = new AP(26, 05, 2013);
            ap.setRoom(room);
            ap.setApartment(apartment);
            ap.setSharedApartmentName(house.getStreet(), house.getStreetNumber(), room.getApartment().getApartmentNumber(), room.getRoomNumber(), ap.getDay(), ap.getMonth(), ap.getYear());
            ap.save();

            ap = new AP(28, 06, 2014);
            ap.setRoom(room);
            ap.setApartment(apartment);
            ap.setSharedApartmentName(house.getStreet(), house.getStreetNumber(), room.getApartment().getApartmentNumber(), room.getRoomNumber(), ap.getDay(), ap.getMonth(), ap.getYear());
            ap.save();

            ap = new AP(30, 06, 2015);
            ap.setRoom(room);
            ap.setApartment(apartment);
            ap.setSharedApartmentName(house.getStreet(), house.getStreetNumber(), room.getApartment().getApartmentNumber(), room.getRoomNumber(), ap.getDay(), ap.getMonth(), ap.getYear());
            ap.save();

            apartment = house.getApartments().get(1);
            room = apartment.getRooms().get(1);
            ap = new AP(20, 06, 2014);
            ap.setRoom(room);
            ap.setApartment(apartment);
            ap.setSharedApartmentName(house.getStreet(), house.getStreetNumber(), room.getApartment().getApartmentNumber(), room.getRoomNumber(), ap.getDay(), ap.getMonth(), ap.getYear());
            ap.save();

            house = houses.get(1);
            apartment = house.getApartments().get(0);
            ap = new AP(30, 10, 2016);
            ap.setApartment(apartment);
            ap.setStudioName(house.getStreet(), house.getStreetNumber(), apartment.getApartmentNumber(), ap.getDay(), ap.getMonth(), ap.getYear());
            ap.save();

        }
    }
}
