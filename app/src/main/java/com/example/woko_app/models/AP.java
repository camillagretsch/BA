package com.example.woko_app.models;

import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.example.woko_app.constants.ApartmentType;

import java.util.ArrayList;
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

    @Column(name = "room", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private Room room;

    @Column(name = "bathroom", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Bathroom bathroom;

    @Column(name = "kitchen", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Kitchen kitchen;

    @Column(name = "apartment", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private Apartment apartment;

    @Column(name = "old_AP")
    private AP oldAP;

    public AP() {
        super();
    }

    public AP(long day, long month, long year, Apartment apartment, Room room) {
        super();
        this.day = day;
        this.month = month;
        this.year = year;
        this.apartment = apartment;
        this.room = room;
    }

    public AP(long day, long month, long year, Apartment apartment, Room room, Bathroom bathroom, Kitchen kitchen) {
        super();
        this.day = day;
        this.month = month;
        this.year = year;
        this.apartment = apartment;
        this.room = room;
        this.bathroom = bathroom;
        this.kitchen = kitchen;
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

    public void createNewAP(AP ap) {
        // Room
        MattressState mattress = new MattressState(ap.getRoom(), ap);
        mattress.save();
        FloorState floor = new FloorState(ap.getRoom(), ap);
        floor.save();
        WallState wall = new WallState(ap.getRoom(), ap);
        wall.save();
        DoorState door = new DoorState(ap.getRoom(), ap);
        door.save();
        WindowState window = new WindowState(ap.getRoom(), ap);
        window.save();
        SocketState socket = new SocketState(ap.getRoom(), ap);
        socket.save();
        RadiatorState radiator = new RadiatorState(ap.getRoom(), ap);
        radiator.save();

        if (ApartmentType.STUDIO.equals(ap.getApartment().getType())) {
            // Bathroom
            ShowerState shower = new ShowerState(ap.getBathroom(), ap);
            shower.save();
            wall = new WallState(ap.getBathroom(), ap);
            wall.save();
            floor = new FloorState(ap.getBathroom(), ap);
            floor.save();
            window = new WindowState(ap.getBathroom(), ap);
            window.save();
            door = new DoorState(ap.getBathroom(), ap);
            door.save();
            socket = new SocketState(ap.getBathroom(), ap);
            socket.save();
            radiator = new RadiatorState(ap.getBathroom(), ap);
            radiator.save();

            // Kitchen
            FridgeState fridge = new FridgeState(ap.getKitchen(), ap);
            fridge.save();
            OvenState oven = new OvenState(ap.getKitchen(), ap);
            oven.save();
            VentilationState ventilation = new VentilationState(ap.getKitchen(), ap);
            ventilation.save();
            CupboardState cupboard = new CupboardState(ap.getKitchen(), ap);
            cupboard.save();
            wall = new WallState(ap.getKitchen(), ap);
            wall.save();
            floor = new FloorState(ap.getKitchen(), ap);
            floor.save();
            window = new WindowState(ap.getKitchen(), ap);
            window.save();
            door = new DoorState(ap.getKitchen(), ap);
            door.save();
            socket = new SocketState(ap.getKitchen(), ap);
            socket.save();
            radiator = new RadiatorState(ap.getKitchen(), ap);
            radiator.save();

            // Balcony
            BalconyState balcony = new BalconyState(ap.getApartment(), ap);
            balcony.save();

            // Basement
            BasementState basement = new BasementState(ap.getApartment(), ap);
            basement.save();
        }
    }

    public void duplicateAP(AP ap, AP oldAP) {
        // Room
        MattressState mattress = new MattressState(ap.getRoom(), ap);
        MattressState oldMattress = MattressState.findByRoomAndAP(oldAP.getRoom(), oldAP);
        MattressState.duplicateMattressEntries(mattress, oldMattress);
        mattress.save();
        FloorState floor = new FloorState(ap.getRoom(), ap);
        FloorState oldFloor = FloorState.findByRoomAndAP(oldAP.getRoom(), oldAP);
        FloorState.duplicateFloorEntries(floor, oldFloor);
        floor.save();
        Log.d("Check for same entry", floor.getDamageComment() + " old: " + oldFloor.getDamageComment());
        WallState wall = new WallState(ap.getRoom(), ap);
        WallState oldWall = WallState.findByRoomAndAP(oldAP.getRoom(), oldAP);
        WallState.duplicateWallEntries(wall, oldWall);
        wall.save();
        DoorState door = new DoorState(ap.getRoom(), ap);
        DoorState oldDoor = DoorState.findByRoomAndAP(oldAP.getRoom(), oldAP);
        DoorState.duplicateDoorEntries(door, oldDoor);
        door.save();
        WindowState window = new WindowState(ap.getRoom(), ap);
        WindowState oldWindow = WindowState.findByRoomAndAP(oldAP.getRoom(), oldAP);
        WindowState.duplicateWindowEntries(window, oldWindow);
        window.save();
        SocketState socket = new SocketState(ap.getRoom(), ap);
        SocketState oldSocket = SocketState.findByRoomAndAP(oldAP.getRoom(), oldAP);
        SocketState.duplicateSocketEntries(socket, oldSocket);
        socket.save();
        RadiatorState radiator = new RadiatorState(ap.getRoom(), ap);
        RadiatorState oldRadiator = RadiatorState.findByRoomAndAP(oldAP.getRoom(), oldAP);
        RadiatorState.duplicateRadiatorEntries(radiator, oldRadiator);
        radiator.save();

        if (ApartmentType.STUDIO.equals(ap.getApartment().getType())) {
            // Bathroom
            ShowerState shower = new ShowerState(ap.getBathroom(), ap);
            ShowerState oldShower = ShowerState.findByBathroomAndAP(oldAP.getBathroom(), oldAP);
            ShowerState.duplicateShowerEntries(shower, oldShower);
            shower.save();
            wall = new WallState(ap.getBathroom(), ap);
            oldWall = WallState.findByBathroomAndAP(oldAP.getBathroom(), oldAP);
            WallState.duplicateWallEntries(wall, oldWall);
            wall.save();
            floor = new FloorState(ap.getBathroom(), ap);
            oldFloor = FloorState.findByBathroomAndAP(oldAP.getBathroom(), oldAP);
            FloorState.duplicateFloorEntries(floor, oldFloor);
            floor.save();
            window = new WindowState(ap.getBathroom(), ap);
            oldWindow = WindowState.findByBathroomAndAP(oldAP.getBathroom(), oldAP);
            WindowState.duplicateWindowEntries(window, oldWindow);
            window.save();
            door = new DoorState(ap.getBathroom(), ap);
            oldDoor = DoorState.findByBathroomAndAP(oldAP.getBathroom(), oldAP);
            DoorState.duplicateDoorEntries(door, oldDoor);
            door.save();
            socket = new SocketState(ap.getBathroom(), ap);
            oldSocket = SocketState.findByBathroomAndAP(oldAP.getBathroom(), oldAP);
            SocketState.duplicateSocketEntries(socket, oldSocket);
            socket.save();
            radiator = new RadiatorState(ap.getBathroom(), ap);
            oldRadiator = RadiatorState.findByBathroomAndAP(oldAP.getBathroom(), oldAP);
            RadiatorState.duplicateRadiatorEntries(radiator, oldRadiator);
            radiator.save();

            // Kitchen
            FridgeState fridge = new FridgeState(ap.getKitchen(), ap);
            FridgeState oldFridge = FridgeState.findByKitchenAndAP(oldAP.getKitchen(), oldAP);
            FridgeState.duplicateFridgeEntries(fridge, oldFridge);
            fridge.save();
            OvenState oven = new OvenState(ap.getKitchen(), ap);
            OvenState oldOven = OvenState.findByKitchenAndAP(oldAP.getKitchen(), oldAP);
            OvenState.duplicateOvenEntries(oven, oldOven);
            oven.save();
            VentilationState ventilation = new VentilationState(ap.getKitchen(), ap);
            VentilationState oldVentilation = VentilationState.findByKitchenAndAP(oldAP.getKitchen(), oldAP);
            VentilationState.duplicateVentilationEntries(ventilation, oldVentilation);
            ventilation.save();
            CupboardState cupboard = new CupboardState(ap.getKitchen(), ap);
            CupboardState oldCupboard = CupboardState.findByKitchenAndAP(oldAP.getKitchen(), oldAP);
            CupboardState.duplicateCupboardEntries(cupboard, oldCupboard);
            cupboard.save();
            wall = new WallState(ap.getKitchen(), ap);
            oldWall = WallState.findByKitchenAndAP(oldAP.getKitchen(), oldAP);
            WallState.duplicateWallEntries(wall, oldWall);
            wall.save();
            floor = new FloorState(ap.getKitchen(), ap);
            oldFloor = FloorState.findByKitchenAndAP(oldAP.getKitchen(), oldAP);
            FloorState.duplicateFloorEntries(floor, oldFloor);
            floor.save();
            window = new WindowState(ap.getKitchen(), ap);
            oldWindow = WindowState.findByKitchenAndAP(oldAP.getKitchen(), oldAP);
            WindowState.duplicateWindowEntries(window, oldWindow);
            window.save();
            door = new DoorState(ap.getKitchen(), ap);
            oldDoor = DoorState.findByKitchenAndAP(oldAP.getKitchen(), oldAP);
            DoorState.duplicateDoorEntries(door, oldDoor);
            door.save();
            socket = new SocketState(ap.getKitchen(), ap);
            oldSocket = SocketState.findByKitchenAndAP(oldAP.getKitchen(), oldAP);
            SocketState.duplicateSocketEntries(socket, oldSocket);
            socket.save();
            radiator = new RadiatorState(ap.getKitchen(), ap);
            oldRadiator = RadiatorState.findByKitchenAndAP(oldAP.getKitchen(), oldAP);
            RadiatorState.duplicateRadiatorEntries(radiator, oldRadiator);
            radiator.save();

            // Balcony
            BalconyState balcony = new BalconyState(ap.getApartment(), ap);
            BalconyState oldBalcony = BalconyState.findByApartmentAndAP(oldAP.getApartment(), oldAP);
            BalconyState.duplicateBalconyEntries(balcony, oldBalcony);
            balcony.save();

            // Basement
            BasementState basement = new BasementState(ap.getApartment(), ap);
            BasementState oldBasement = BasementState.findByApartmentAndAP(oldAP.getApartment(), oldAP);
            BasementState.duplicateBasementEntries(basement, oldBasement);
            basement.save();
        }
    }

    public static List<AP> initializeAPs(List<House> houses) {
        int size = new Select().from(AP.class).execute().size();
        List<AP> aps = new ArrayList<>();

        if (size == 0) {
            House house = houses.get(0);
            Apartment apartment = house.getApartments().get(0);

            Room room = apartment.getRooms().get(0);
            AP ap = new AP(31, 07, 2015, apartment, room);
            ap.setSharedApartmentName(house.getStreet(), house.getStreetNumber(), room.getApartment().getApartmentNumber(), room.getRoomNumber(), ap.getDay(), ap.getMonth(), ap.getYear());
            aps.add(ap);
            ap.save();

            ap = new AP(25, 07, 2015, apartment, room);
            ap.setSharedApartmentName(house.getStreet(), house.getStreetNumber(), room.getApartment().getApartmentNumber(), room.getRoomNumber(), ap.getDay(), ap.getMonth(), ap.getYear());
            aps.add(ap);
            ap.save();

            room = house.getApartments().get(0).getRooms().get(2);
            ap = new AP(26, 05, 2013, apartment, room);
            ap.setSharedApartmentName(house.getStreet(), house.getStreetNumber(), room.getApartment().getApartmentNumber(), room.getRoomNumber(), ap.getDay(), ap.getMonth(), ap.getYear());
            aps.add(ap);
            ap.save();

            ap = new AP(28, 06, 2014, apartment, room);
            ap.setSharedApartmentName(house.getStreet(), house.getStreetNumber(), room.getApartment().getApartmentNumber(), room.getRoomNumber(), ap.getDay(), ap.getMonth(), ap.getYear());
            aps.add(ap);
            ap.save();

            ap = new AP(30, 06, 2015, apartment, room);
            ap.setSharedApartmentName(house.getStreet(), house.getStreetNumber(), room.getApartment().getApartmentNumber(), room.getRoomNumber(), ap.getDay(), ap.getMonth(), ap.getYear());
            aps.add(ap);
            ap.save();

            apartment = house.getApartments().get(1);
            room = apartment.getRooms().get(1);
            ap = new AP(20, 06, 2014, apartment, room);
            ap.setSharedApartmentName(house.getStreet(), house.getStreetNumber(), room.getApartment().getApartmentNumber(), room.getRoomNumber(), ap.getDay(), ap.getMonth(), ap.getYear());
            aps.add(ap);
            ap.save();

            house = houses.get(1);
            apartment = house.getApartments().get(0);
            room = apartment.getRooms().get(0);
            ap = new AP(30, 10, 2015, apartment, room, Bathroom.findByApartment(apartment), Kitchen.findByApartment(apartment));
            ap.setStudioName(house.getStreet(), house.getStreetNumber(), apartment.getApartmentNumber(), ap.getDay(), ap.getMonth(), ap.getYear());
            aps.add(ap);
            ap.save();

        }
        return aps;
    }
}
