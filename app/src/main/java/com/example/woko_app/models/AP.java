package com.example.woko_app.models;

import android.test.AndroidTestCase;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.example.woko_app.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
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

    @Column(name = "room", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private Room room;

    @Column(name = "bathroom", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Bathroom bathroom;

    @Column(name = "kitchen", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Kitchen kitchen;

    @Column(name = "apartment", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private Apartment apartment;

    @Column(name = "tenant", notNull = true, onUpdate = Column.ForeignKeyAction.CASCADE)
    private Tenant tenant;

    @Column(name = "old_AP")
    private AP oldAP;

    @Column(name = "signature_tenant")
    private byte[] signatureNewTenant = null;

    @Column(name = "date_tenant")
    private String dateNewTenant;

    @Column(name = "signature_woko")
    private byte[] signatureWoko = null;

    @Column(name = "date_woko")
    private String dateWoko;

    private EntryStateInterface lastOpend;

    public AP() {
        super();
    }

    public AP(long day, long month, long year, Apartment apartment, Room room, Tenant tenant) {
        super();
        this.day = day;
        this.month = month;
        this.year = year;
        this.apartment = apartment;
        this.room = room;
        this.tenant = tenant;
    }

    public AP(long day, long month, long year, Apartment apartment, Room room, Bathroom bathroom, Kitchen kitchen, Tenant tenant) {
        super();
        this.day = day;
        this.month = month;
        this.year = year;
        this.apartment = apartment;
        this.room = room;
        this.bathroom = bathroom;
        this.kitchen = kitchen;
        this.tenant = tenant;
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

    public void setBathroom(Bathroom bathroom) {
        this.bathroom = bathroom;
    }

    public Bathroom getBathroom() {
        return bathroom;
    }

    public void setKitchen(Kitchen kitchen) {
        this.kitchen = kitchen;
    }

    public Kitchen getKitchen() {
        return kitchen;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setOldAP(AP oldAP) {
        this.oldAP = oldAP;
    }

    public AP getOldAP() {
        return oldAP;
    }

    public void setSignatureNewTenant(byte[] signatureNewTenant) {
        this.signatureNewTenant = signatureNewTenant;
    }

    public byte[] getSignatureNewTenant() {
        return signatureNewTenant;
    }

    public void setDateNewTenant(String dateNewTenant) {
        this.dateNewTenant = dateNewTenant;
    }

    public String getDateNewTenant() {
        return dateNewTenant;
    }

    public void setSignatureWoko(byte[] signatureWoko) {
        this.signatureWoko = signatureWoko;
    }

    public byte[] getSignatureWoko() {
        return signatureWoko;
    }

    public void setDateWoko(String dateWoko) {
        this.dateWoko = dateWoko;
    }

    public String getDateWoko() {
        return dateWoko;
    }

    public void setLastOpend(EntryStateInterface lastOpend) {
        this.lastOpend = lastOpend;
    }

    public EntryStateInterface getLastOpend() {
        return lastOpend;
    }

    /**
     * find a protocol by a room id
     * @param room
     * @return
     */
    public static List<AP> findByRoom(Room room) {
        return new Select().from(AP.class).where("room = ?", room.getId()).execute();
    }

    /**
     * find a protocol by a apartment id
     * @param apartment
     * @return
     */
    public static List<AP> findByApartment(Apartment apartment) {
        return new Select().from(AP.class).where("apartment = ?", apartment.getId()).execute();
    }

    /**
     * find a protocol by his id
     * @param id
     * @return
     */
    public static AP findById(long id) {
        return new Select().from(AP.class).where("id = ?", id).executeSingle();
    }

    /**
     * find a protocol by a room id and a year
     * @param room
     * @param year
     * @return
     */
    public static List<AP> findByRoomAndYear(Room room, long year) {
        return new Select().from(AP.class).where("room = ? and year = ?", room.getId(), year).execute();
    }

    /**
     * find a protocol by a apartment id and a year
     * @param apartment
     * @param year
     * @return
     */
    public static List<AP> findByApartmentAndYear(Apartment apartment, long year) {
        return new Select().from(AP.class).where("apartment = ? and year = ?", apartment.getId(), year).execute();
    }

    /**
     * creates a new protocol and add all entries
     * @param ap
     */
    public void createNewAP(AP ap) {

        EntryStateInterface entryState = new FridgeState(ap.getKitchen(), ap);
        entryState.createNewEntry(ap);

        entryState = new OvenState(ap.getKitchen(), ap);
        entryState.createNewEntry(ap);

        entryState = new CutleryState(ap.getKitchen(), ap);
        entryState.createNewEntry(ap);

        entryState = new VentilationState(ap.getKitchen(), ap);
        entryState.createNewEntry(ap);

        entryState = new CupboardState(ap.getKitchen(), ap);
        entryState.createNewEntry(ap);

        entryState = new ShowerState(ap.getBathroom(), ap);
        entryState.createNewEntry(ap);

        entryState = new MattressState(ap.getRoom(), ap);
        entryState.createNewEntry(ap);

        entryState = new FurnitureState(ap.getRoom(), ap);
        entryState.createNewEntry(ap);

        entryState = new BalconyState(ap.getApartment(), ap);
        entryState.createNewEntry(ap);

        entryState = new BasementState(ap.getApartment(), ap);
        entryState.createNewEntry(ap);

        entryState = new WallState(ap.getRoom(), ap);
        entryState.createNewEntry(ap);

        entryState = new FloorState(ap.getRoom(), ap);
        entryState.createNewEntry(ap);

        entryState = new WindowState(ap.getRoom(), ap);
        entryState.createNewEntry(ap);
        
        entryState = new DoorState(ap.getRoom(), ap);
        entryState.createNewEntry(ap);

        entryState = new SocketState(ap.getRoom(), ap);
        entryState.createNewEntry(ap);

        entryState = new RadiatorState(ap.getRoom(), ap);
        entryState.createNewEntry(ap);
    }

    /**
     * create a new protocol and duplicate all old entries
     * @param ap
     * @param oldAP
     */
    public void duplicateAP(AP ap, AP oldAP) {

        EntryStateInterface entryState = new FridgeState(ap.getKitchen(), ap);
        entryState.duplicateEntries(ap, oldAP);

        entryState = new OvenState(ap.getKitchen(), ap);
        entryState.duplicateEntries(ap, oldAP);

        entryState = new CutleryState(ap.getKitchen(), ap);
        entryState.duplicateEntries(ap, oldAP);

        entryState = new VentilationState(ap.getKitchen(), ap);
        entryState.duplicateEntries(ap, oldAP);

        entryState = new CupboardState(ap.getKitchen(), ap);
        entryState.duplicateEntries(ap, oldAP);

        entryState = new ShowerState(ap.getBathroom(), ap);
        entryState.duplicateEntries(ap, oldAP);

        entryState = new MattressState(ap.getRoom(), ap);
        entryState.duplicateEntries(ap, oldAP);

        entryState = new FurnitureState(ap.getRoom(), ap);
        entryState.duplicateEntries(ap, oldAP);

        entryState = new BalconyState(ap.getApartment(), ap);
        entryState.duplicateEntries(ap, oldAP);

        entryState = new BasementState(ap.getApartment(), ap);
        entryState.duplicateEntries(ap, oldAP);

        entryState = new WallState(ap.getRoom(), ap);
        entryState.duplicateEntries(ap, oldAP);

        entryState = new FloorState(ap.getRoom(), ap);
        entryState.duplicateEntries(ap, oldAP);

        entryState = new WindowState(ap.getRoom(), ap);
        entryState.duplicateEntries(ap, oldAP);

        entryState = new DoorState(ap.getRoom(), ap);
        entryState.duplicateEntries(ap, oldAP);

        entryState = new SocketState(ap.getRoom(), ap);
        entryState.duplicateEntries(ap, oldAP);

        entryState = new RadiatorState(ap.getRoom(), ap);
        entryState.duplicateEntries(ap, oldAP);
    }

    /**
     * fill in the db with initial data
     * @param houses
     * @return
     */
    public static List<AP> initializeAPs(List<House> houses) {
        int size = new Select().from(AP.class).execute().size();
        List<AP> aps = new ArrayList<>();

        if (size == 0) {
            House house = houses.get(0);
            Apartment apartment = house.getApartments().get(0);

            Room room = apartment.getRooms().get(0);
            AP ap = new AP(31, 07, 2015, apartment, room, Tenant.initializeTenant());
            ap.setSharedApartmentName(house.getStreet(), house.getStreetNumber(), room.getApartment().getApartmentNumber(), room.getRoomNumber(), ap.getDay(), ap.getMonth(), ap.getYear());
            aps.add(ap);
            ap.save();

            ap = new AP(25, 07, 2015, apartment, room, Tenant.initializeTenant());
            ap.setSharedApartmentName(house.getStreet(), house.getStreetNumber(), room.getApartment().getApartmentNumber(), room.getRoomNumber(), ap.getDay(), ap.getMonth(), ap.getYear());
            aps.add(ap);
            ap.save();

            room = house.getApartments().get(0).getRooms().get(2);
            ap = new AP(26, 05, 2013, apartment, room, Tenant.initializeTenant());
            ap.setSharedApartmentName(house.getStreet(), house.getStreetNumber(), room.getApartment().getApartmentNumber(), room.getRoomNumber(), ap.getDay(), ap.getMonth(), ap.getYear());
            aps.add(ap);
            ap.save();

            ap = new AP(28, 06, 2014, apartment, room, Tenant.initializeTenant());
            ap.setSharedApartmentName(house.getStreet(), house.getStreetNumber(), room.getApartment().getApartmentNumber(), room.getRoomNumber(), ap.getDay(), ap.getMonth(), ap.getYear());
            aps.add(ap);
            ap.save();

            ap = new AP(30, 06, 2015, apartment, room, Tenant.initializeTenant());
            ap.setSharedApartmentName(house.getStreet(), house.getStreetNumber(), room.getApartment().getApartmentNumber(), room.getRoomNumber(), ap.getDay(), ap.getMonth(), ap.getYear());
            aps.add(ap);
            ap.save();

            apartment = house.getApartments().get(1);
            room = apartment.getRooms().get(1);
            ap = new AP(20, 06, 2014, apartment, room, Tenant.initializeTenant());
            ap.setSharedApartmentName(house.getStreet(), house.getStreetNumber(), room.getApartment().getApartmentNumber(), room.getRoomNumber(), ap.getDay(), ap.getMonth(), ap.getYear());
            aps.add(ap);
            ap.save();

            house = houses.get(1);
            apartment = house.getApartments().get(0);
            room = apartment.getRooms().get(0);
            ap = new AP(30, 10, 2015, apartment, room, Bathroom.findByApartment(apartment), Kitchen.findByApartment(apartment), Tenant.initializeTenant());
            ap.setStudioName(house.getStreet(), house.getStreetNumber(), apartment.getApartmentNumber(), ap.getDay(), ap.getMonth(), ap.getYear());
            aps.add(ap);
            ap.save();

        }
        return aps;
    }
}
