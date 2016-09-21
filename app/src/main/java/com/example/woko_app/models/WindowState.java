package com.example.woko_app.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.example.woko_app.constants.ApartmentType;
import com.example.woko_app.fragment.DataGridFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by camillagretsch on 18.09.16.
 */
@Table(name = "WindowState")
public class WindowState extends Model implements EntryStateInterface {

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

    public List<Boolean> createCheckList(WindowState window) {
        return new ArrayList<>(Arrays.asList(window.isClean(), window.hasNoDamage()));
    }

    public List<String> createCommentsList(WindowState window) {
        return new ArrayList<>(Arrays.asList(window.getCleanComment(), window.getDamageComment()));
    }

    public List<Boolean> createCheckOldList(WindowState window) {
        return new ArrayList<>(Arrays.asList(window.isCleanOld(), window.isDamageOld()));
    }

    public void getEntries(DataGridFragment frag) {
        WindowState window = WindowState.findByRoomAndAP(frag.getCurrentAP().getRoom(), frag.getCurrentAP());

        if (ApartmentType.STUDIO.equals(frag.getCurrentAP().getApartment().getType())) {
            if (frag.getParent() == 0) {
                window = WindowState.findByKitchenAndAP(frag.getCurrentAP().getKitchen(), frag.getCurrentAP());
                frag.setTableEntries(WindowState.findByKitchenAndAP(frag.getCurrentAP().getKitchen(), frag.getCurrentAP()));
            } else if (frag.getParent() == 1) {
                window = WindowState.findByBathroomAndAP(frag.getCurrentAP().getBathroom(), frag.getCurrentAP());
                frag.setTableEntries(WindowState.findByBathroomAndAP(frag.getCurrentAP().getBathroom(), frag.getCurrentAP()));
            }
        }
        frag.setHeaderVariante1();
        frag.getRowNames().addAll(window.ROW_NAMES);
        frag.getCheck().addAll(createCheckList(window));
        frag.getCheckOld().addAll(createCheckOldList(window));
        frag.getComments().addAll(createCommentsList(window));
        frag.setTableContentVariante1();
    }

    public void duplicateEntries(AP ap, AP oldAP) {
        WindowState oldWindow = WindowState.findByRoomAndAP(oldAP.getRoom(), oldAP);
        this.copyOldEntries(oldWindow);
        this.save();

        if (ApartmentType.STUDIO.equals(ap.getApartment().getType())) {
            WindowState window = new WindowState(ap.getKitchen(), ap);
            oldWindow = WindowState.findByKitchenAndAP(oldAP.getKitchen(), oldAP);
            window.copyOldEntries(oldWindow);
            window.save();

            window = new WindowState(ap.getBathroom(), ap);
            oldWindow = WindowState.findByBathroomAndAP(oldAP.getBathroom(), oldAP);
            window.copyOldEntries(oldWindow);
            window.save();
        }
    }

    public void copyOldEntries(WindowState oldWindow) {
        this.setIsClean(oldWindow.isClean());
        this.setIsCleanOld(oldWindow.isCleanOld());
        this.setCleanComment(oldWindow.getCleanComment());
        this.setHasNoDamage(oldWindow.hasNoDamage());
        this.setIsDamageOld(oldWindow.isDamageOld());
        this.setDamageComment(oldWindow.getDamageComment());
    }

    public void createNewEntry(AP ap) {
        this.save();

        if (ApartmentType.STUDIO.equals(ap.getApartment().getType())) {
            WindowState window = new WindowState(ap.getKitchen(), ap);
            window.save();

            window = new WindowState(ap.getBathroom(), ap);
            window.save();
        }
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

