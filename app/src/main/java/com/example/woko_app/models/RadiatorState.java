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
@Table(name = "RadiatorState")
public class RadiatorState extends Model{

    @Column(name = "isOn")
    private boolean isOn = true;

    @Column(name = "isOnOld")
    private boolean isOnOld = false;

    @Column(name = "on_comment")
    private String onComment;

    //TODO cleanPicture

    @Column(name = "hasNoDamage")
    private boolean hasNoDamage = true;

    @Column(name = "isDamageOld")
    private boolean isDamageOld = false;

    @Column(name = "damage_comment")
    private String damageComment;

    //TODO damagePicture

    private static final List<String> ROW_NAMES = Arrays.asList("Heizung ist eingeschalten?", "Ist alles intakt?");

    @Column(name = "room", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Room room;

    @Column(name = "bathroom", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Bathroom bathroom;

    @Column(name = "kitchen", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Kitchen kitchen;

    @Column(name = "AP", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private AP ap;

    public RadiatorState() {
        super();
    }

    public RadiatorState(Room room, AP ap) {
        super();
        this.room = room;
        this.ap = ap;
    }

    public RadiatorState(Bathroom bathroom, AP ap) {
        super();
        this.bathroom = bathroom;
        this.ap = ap;
    }

    public RadiatorState(Kitchen kitchen, AP ap) {
        super();
        this.kitchen = kitchen;
        this.ap = ap;
    }

    public void setIsOn(boolean isOn) {
        this.isOn = isOn;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setIsOnOld(boolean isOnOld) {
        this.isOnOld = isOnOld;
    }

    public boolean isOnOld() {
        return isOnOld;
    }

    public void setOnComment(String onComment) {
        this.onComment = onComment;
    }

    public String getOnComment() {
        return onComment;
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

    public static List<Boolean> createCheckList(RadiatorState radiator) {
        return new ArrayList<>(Arrays.asList(radiator.isOn(), radiator.hasNoDamage()));
    }

    public static List<String> createCommentsList(RadiatorState radiator) {
        return new ArrayList<>(Arrays.asList(radiator.getOnComment(), radiator.getDamageComment()));
    }

    public static List<Boolean> createCheckOldList(RadiatorState radiator) {
        return new ArrayList<>(Arrays.asList(radiator.isOnOld(), radiator.isDamageOld()));
    }

    public static void duplicateRadiatorEntries(RadiatorState radiator, RadiatorState oldRadiator) {
        radiator.setIsOn(oldRadiator.isOn());
        radiator.setIsOnOld(oldRadiator.isOnOld());
        radiator.setOnComment(oldRadiator.getOnComment());
        radiator.setHasNoDamage(oldRadiator.hasNoDamage());
        radiator.setIsDamageOld(oldRadiator.isDamageOld());
        radiator.setDamageComment(oldRadiator.getDamageComment());
    }

    public static RadiatorState findByRoomAndAP(Room room, AP ap) {
        return new Select().from(RadiatorState.class).where("room = ? and AP = ?", room.getId(), ap.getId()).executeSingle();
    }

    public static RadiatorState findByBathroomAndAP(Bathroom bathroom, AP ap) {
        return new Select().from(RadiatorState.class).where("bathroom = ? and AP = ?", bathroom.getId(), ap.getId()).executeSingle();
    }

    public static RadiatorState findByKitchenAndAP(Kitchen kitchen, AP ap) {
        return new Select().from(RadiatorState.class).where("kitchen = ? and AP = ?", kitchen.getId(), ap.getId()).executeSingle();
    }

    public static RadiatorState findById(long id) {
        return new Select().from(RadiatorState.class).where("id = ?", id).executeSingle();
    }

    public static void initializeRoomRadiator(List<AP> aps) {
        for (AP ap : aps) {
            RadiatorState radiator = new RadiatorState(ap.getRoom(), ap);
            radiator.setIsOn(false);
            radiator.setHasNoDamage(true);
            radiator.save();
        }
    }

    public static void initializeBathroomRadiator(List<AP> aps) {
        for (AP ap : aps) {
            RadiatorState radiator = new RadiatorState(ap.getBathroom(), ap);
            radiator.setIsOn(true);
            radiator.setHasNoDamage(true);
            radiator.save();
        }
    }

    public static void initializeKitchenRadiator(List<AP> aps) {
        for (AP ap : aps) {
            RadiatorState radiator = new RadiatorState(ap.getKitchen(), ap);
            radiator.setIsOn(true);
            radiator.setHasNoDamage(false);
            radiator.setDamageComment("Ventil ist abgebrochen");
            radiator.save();
        }
    }
}

