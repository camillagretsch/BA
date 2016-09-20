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
@Table(name = "WindowState")
public class WindowState extends Model{

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

    private static final List<String> ROW_NAMES = Arrays.asList("Sind gereinigt?", "Ist alles intakt?");

    @Column(name = "room", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Room room;

    @Column(name = "bathroom", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Bathroom bathroom;

    @Column(name = "kitchen", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Kitchen kitchen;

    @Column(name = "AP", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private AP ap;

    public WindowState() {
        super();
    }

    public WindowState(Room room, AP ap) {
        super();
        this.room = room;
        this.ap = ap;
    }

    public WindowState(Bathroom bathroom, AP ap) {
        super();
        this.bathroom = bathroom;
        this.ap = ap;
    }

    public WindowState(Kitchen kitchen, AP ap) {
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

    public AP getAp() {
        return ap;
    }

    public static List<Boolean> createCheckList(WindowState window) {
        return new ArrayList<>(Arrays.asList(window.isClean(), window.hasNoDamage()));
    }

    public static List<String> createCommentsList(WindowState window) {
        return new ArrayList<>(Arrays.asList(window.getCleanComment(), window.getDamageComment()));
    }

    public static List<Boolean> createCheckOldList(WindowState window) {
        return new ArrayList<>(Arrays.asList(window.isCleanOld(), window.isDamageOld()));
    }

    public static void duplicateWindowEntries(WindowState window, WindowState oldWindow) {
        window.setIsClean(oldWindow.isClean());
        window.setIsCleanOld(oldWindow.isCleanOld());
        window.setCleanComment(oldWindow.getCleanComment());
        window.setHasNoDamage(oldWindow.hasNoDamage());
        window.setIsDamageOld(oldWindow.isDamageOld());
        window.setDamageComment(oldWindow.getDamageComment());
    }

    public static WindowState findByRoomAndAP(Room room, AP ap) {
        return new Select().from(WindowState.class).where("room = ? and AP = ?", room.getId(), ap.getId()).executeSingle();
    }

    public static WindowState findByBathroomAndAP(Bathroom bathroom, AP ap) {
        return new Select().from(WindowState.class).where("bathroom = ? and AP = ?", bathroom.getId(), ap.getId()).executeSingle();
    }

    public static WindowState findByKitchenAndAP(Kitchen kitchen, AP ap) {
        return new Select().from(WindowState.class).where("kitchen = ? and AP = ?", kitchen.getId(), ap.getId()).executeSingle();
    }

    public static WindowState findById(long id) {
        return new Select().from(WindowState.class).where("id = ?", id).executeSingle();
    }

    public static void initializeRoomWindow(List<AP> aps) {
        for (AP ap : aps) {
            WindowState window = new WindowState(ap.getRoom(), ap);
            window.setIsClean(true);
            window.setHasNoDamage(true);
            window.save();
        }
    }

    public static void initializeBathroomWindow(List<AP> aps) {
        for (AP ap : aps) {
            WindowState window = new WindowState(ap.getBathroom(), ap);
            window.setIsClean(true);
            window.setHasNoDamage(true);
            window.save();
        }
    }

    public static void initializeKitchenWindow(List<AP> aps) {
        for (AP ap : aps) {
            WindowState window = new WindowState(ap.getKitchen(), ap);
            window.setIsClean(true);
            window.setHasNoDamage(false);
            window.setDamageComment("Sprung in einer Scheibe");
            window.save();
        }
    }
}

