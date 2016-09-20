package com.example.woko_app.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by camillagretsch on 11.09.16.
 */
@Table(name = "FloorState")
public class FloorState extends Model {

    @Column(name = "isClean")
    private boolean isClean = true;

    @Column(name = "isCleanOld")
    private boolean isCleanOld = false;

    @Column(name = "clean_comment")
    private String cleanComment;

    //TODO cleanPicture

    @Column(name = "hasNoDamage")
    private boolean hasNoDamage = true;

    @Column(name = "isDamageOld")
    private boolean isDamageOld = false;

    @Column(name = "damage_comment")
    private String damageComment;

    //TODO damagePicture

    private static final List<String> ROW_NAMES = Arrays.asList("Ist besenrein?", "Ist alles intakt?");

    @Column(name = "room", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Room room;

    @Column(name = "bathroom", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Bathroom bathroom;

    @Column(name = "kitchen", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Kitchen kitchen;

    @Column(name = "AP", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private AP ap;

    public FloorState() {
        super();
    }

    public FloorState(Room room, AP ap) {
        super();
        this.room = room;
        this.ap = ap;
    }

    public FloorState(Bathroom bathroom, AP ap) {
        super();
        this.bathroom = bathroom;
        this.ap = ap;
    }

    public FloorState(Kitchen kitchen, AP ap) {
        super();
        this.kitchen = kitchen;
        this.ap = ap;
    }

    public void setIsClean(boolean isClean) {
        this.isClean = isClean;
    }

    public boolean isClean() {
        return isClean;
    }

    public void setIsCleanOld(boolean isCleanOld) {
        this.isCleanOld = isCleanOld;
    }

    public boolean isCleanOld() {
        return isCleanOld;
    }

    public void setCleanComment(String cleanComment) {
        this.cleanComment = cleanComment;
    }

    public String getCleanComment() {
        return cleanComment;
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

    public static List<Boolean> createCheckList(FloorState floor) {
        return new ArrayList<>(Arrays.asList(floor.isClean(), floor.hasNoDamage()));
    }

    public static List<String> createCommentsList(FloorState floor) {
       return new ArrayList<>(Arrays.asList(floor.getCleanComment(), floor.getDamageComment()));
    }

    public static List<Boolean> createCheckOldList(FloorState floor) {
        return new ArrayList<>(Arrays.asList(floor.isCleanOld(), floor.isDamageOld()));
    }

    public static void duplicateFloorEntries(FloorState floor, FloorState oldFloor) {
        floor.setIsClean(oldFloor.isClean());
        floor.setIsCleanOld(oldFloor.isCleanOld());
        floor.setCleanComment(oldFloor.getCleanComment());
        floor.setHasNoDamage(oldFloor.hasNoDamage());
        floor.setIsDamageOld(oldFloor.isDamageOld());
        floor.setDamageComment(oldFloor.getDamageComment());
    }

    public static FloorState findByRoomAndAP(Room room, AP ap) {
        return new Select().from(FloorState.class).where("room = ? and AP = ?", room.getId(), ap.getId()).executeSingle();
    }

    public static FloorState findByBathroomAndAP(Bathroom bathroom, AP ap) {
        return new Select().from(FloorState.class).where("bathroom = ? and AP = ?", bathroom.getId(), ap.getId()).executeSingle();
    }

    public static FloorState findByKitchenAndAP(Kitchen kitchen, AP ap) {
        return new Select().from(FloorState.class).where("kitchen = ? and AP = ?", kitchen.getId(), ap.getId()).executeSingle();
    }

    public static FloorState findById(long id) {
        return new Select().from(FloorState.class).where("id = ?", id).executeSingle();
    }

    public static void initializeRoomFloor(List<AP> aps) {
        for (AP ap : aps) {
            FloorState floor = new FloorState(ap.getRoom(), ap);
            floor.setIsClean(true);
            floor.setHasNoDamage(false);
            floor.setDamageComment("Risse im Boden");
            floor.save();
        }
    }

    public static void initializeBathroomFloor(List<AP> aps) {
        for (AP ap :aps) {
            FloorState floor = new FloorState(ap.getBathroom(), ap);
            floor.setIsClean(false);
            floor.setCleanComment("Krümmel am Boden");
            floor.setHasNoDamage(true);
            floor.save();
        }
    }

    public static void initializeKitchenFloor(List<AP> aps) {
        for (AP ap :aps) {
            FloorState floor = new FloorState(ap.getKitchen(), ap);
            floor.setIsClean(true);
            floor.setHasNoDamage(true);
            floor.save();
        }
    }
}
