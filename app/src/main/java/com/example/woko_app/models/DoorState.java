package com.example.woko_app.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by camillagretsch on 18.09.16.
 */
@Table(name = "DoorState")
public class DoorState extends Model{

    @Column(name = "hasNoSpot")
    private boolean hasNoSpot = true;

    @Column(name = "isSpotOld")
    private boolean isSpotOld = false;

    @Column(name = "spot_comment")
    private String spotComment;

    //TODO spotPicture

    @Column(name = "hasNoHole")
    private boolean hasNoHole = true;

    @Column(name = "isHoleOld")
    private boolean isHoleOld = false;

    @Column(name = "hole_comment")
    private String holeComment;

    //TODO holePicture

    @Column(name = "hasNoDamage")
    private boolean hasNoDamage = true;

    @Column(name = "isDamageOld")
    private boolean isDamageOld = false;

    @Column(name = "damage_comment")
    private String damageComment;

    //TODO damagePicture

    private static final List<String> ROW_NAMES = Arrays.asList("Sind alle Flecken entfernt?", "Sind keine Löcher zusehen?", "Ist alles intakt?");

    @Column(name = "room", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Room room;

    @Column(name = "bathroom", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Bathroom bathroom;

    @Column(name = "kitchen", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Kitchen kitchen;

    @Column(name = "AP", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private AP ap;

    public DoorState() {
        super();
    }

    public DoorState(Room room, AP ap) {
        super();
        this.room = room;
        this.ap = ap;
    }

    public DoorState(Bathroom bathroom, AP ap) {
        super();
        this.bathroom = bathroom;
        this.ap = ap;
    }

    public DoorState(Kitchen kitchen, AP ap) {
        super();
        this.kitchen = kitchen;
        this.ap = ap;
    }

    public void setHasNoSpot(boolean hasNoSpot) {
        this.hasNoSpot = hasNoSpot;
    }

    public boolean hasNoSpot() {
        return hasNoSpot;
    }

    public void setIsSpotOld(boolean isSpotOld) {
        this.isSpotOld = isSpotOld;
    }

    public boolean isSpotOld() {
        return isSpotOld;
    }

    public void setSpotComment(String spotComment) {
        this.spotComment = spotComment;
    }

    public String getSpotComment() {
        return spotComment;
    }

    public void setHasNoHole(boolean hasNoHole) {
        this.hasNoHole = hasNoHole;
    }

    public boolean hasNoHole() {
        return hasNoHole;
    }

    public void setIsHoleOld(boolean isHoleOld) {
        this.isHoleOld = isHoleOld;
    }

    public boolean isHoleOld() {
        return isHoleOld;
    }

    public void setHoleComment(String holeComment) {
        this.holeComment = holeComment;
    }

    public String getHoleComment() {
        return holeComment;
    }

    public void setHasNoDamage(boolean hasNoDamage) {
        this.hasNoDamage = hasNoDamage;
    }

    public boolean hasNoDamage() {
        return hasNoDamage;
    }

    public void setIsDamageOld(boolean isDamageOld) {
        this.isDamageOld = isDamageOld;
    }

    public boolean isDamageOld() {
        return isDamageOld;
    }

    public void setDamageComment(String damageComment) {
        this.damageComment = damageComment;
    }

    public String getDamageComment() {
        return damageComment;
    }

    public List<String> getRowNames() {
        return ROW_NAMES;
    }

    public Room getRoom() {
        return room;
    }

    public Bathroom getBathroom() {
        return bathroom;
    }

    public Kitchen getKitchen() {
        return kitchen;
    }

    public AP getAp() {
        return ap;
    }

    public static List<Boolean> createCheckList(DoorState door) {
        return new ArrayList<>(Arrays.asList(door.hasNoSpot(), door.hasNoHole(), door.hasNoDamage()));
    }

    public static List<String> createCommentsList(DoorState door) {
        return new ArrayList<>(Arrays.asList(door.getSpotComment(), door.getHoleComment(), door.getDamageComment()));
    }

    public static List<Boolean> createCheckOldList(DoorState door) {
        return new ArrayList<>(Arrays.asList(door.isSpotOld(), door.isHoleOld(), door.isDamageOld()));
    }

    public static void duplicateDoorEntries(DoorState door, DoorState oldDoor) {
        door.setHasNoSpot(oldDoor.hasNoSpot());
        door.setIsSpotOld(oldDoor.isSpotOld());
        door.setSpotComment(oldDoor.getSpotComment());
        door.setHasNoHole(oldDoor.hasNoHole());
        door.setIsHoleOld(oldDoor.isHoleOld());
        door.setHoleComment(oldDoor.getHoleComment());
        door.setHasNoDamage(oldDoor.hasNoDamage());
        door.setIsDamageOld(oldDoor.isDamageOld());
        door.setDamageComment(oldDoor.getDamageComment());
    }

    public static DoorState findByRoomAndAP(Room room, AP ap) {
        return new Select().from(DoorState.class).where("room = ? and AP = ?", room.getId(), ap.getId()).executeSingle();
    }

    public static DoorState findByBathroomAndAP(Bathroom bathroom, AP ap) {
        return new Select().from(DoorState.class).where("bathroom = ? and AP = ?", bathroom.getId(), ap.getId()).executeSingle();
    }

    public static DoorState findByKitchenAndAP(Kitchen kitchen, AP ap) {
        return new Select().from(DoorState.class).where("kitchen = ? and AP = ?", kitchen.getId(), ap.getId()).executeSingle();
    }

    public static DoorState findById(long id) {
        return new Select().from(DoorState.class).where("id = ?", id).executeSingle();
    }

    public static void initializeRoomDoor(List<AP> aps) {
        for (AP ap : aps) {
            DoorState door = new DoorState(ap.getRoom(), ap);
            door.setHasNoSpot(true);
            door.setHasNoHole(true);
            door.setHasNoDamage(false);
            door.setDamageComment("Klinke ist kaputt");
            door.save();
        }
    }

    public static void initializeBathroomDoor(List<AP> aps) {
        for (AP ap : aps) {
            DoorState door = new DoorState(ap.getBathroom(), ap);
            door.setHasNoSpot(true);
            door.setHasNoHole(true);
            door.setHasNoDamage(true);
            door.save();
        }
    }

    public static void initializeKitchenDoor(List<AP> aps) {
        for (AP ap : aps) {
            DoorState door = new DoorState(ap.getKitchen(), ap);
            door.setHasNoSpot(true);
            door.setHasNoHole(false);
            door.setHasNoDamage(true);
            door.save();
        }
    }
}
