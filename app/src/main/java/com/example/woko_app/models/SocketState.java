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
@Table(name = "SocketState")
public class SocketState extends Model implements EntryStateInterface {

    @Column(name = "isWorking")
    private boolean isWorking = true;

    @Column(name = "isWorkingOld")
    private boolean isWorkingOld = false;

    @Column(name = "working_comment")
    private String workingComment;

    @Column(name = "hasNoDamage")
    private boolean hasNoDamage = true;

    @Column(name = "isDamageOld")
    private boolean isDamageOld = false;

    @Column(name = "damage_comment")
    private String damageComment;

    //TODO damagePicture

    private static final List<String> ROW_NAMES = Arrays.asList("Funktionieren?", "Ist alles intakt?");

    @Column(name = "room", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Room room;

    @Column(name = "bathroom", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Bathroom bathroom;

    @Column(name = "kitchen", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Kitchen kitchen;

    @Column(name = "AP", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private AP ap;

    public SocketState() {
        super();
    }

    public SocketState(Room room, AP ap) {
        super();
        this.room = room;
        this.ap = ap;
    }

    public SocketState(Bathroom bathroom, AP ap) {
        super();
        this.bathroom = bathroom;
        this.ap = ap;
    }

    public SocketState(Kitchen kitchen, AP ap) {
        super();
        this.kitchen = kitchen;
        this.ap = ap;
    }

    public void setIsWorking(boolean isWorking) {
        this.isWorking = isWorking;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setIsWorkingOld(boolean isWorkingOld) {
        this.isWorkingOld = isWorkingOld;
    }

    public boolean isWorkingOld() {
        return isWorkingOld;
    }

    public void setWorkingComment(String workingComment) {
        this.workingComment = workingComment;
    }

    public String getWorkingComment() {
        return workingComment;
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

    public void setAp(AP ap) {
        this.ap = ap;
    }

    public AP getAp() {
        return ap;
    }

    public List<Boolean> createCheckList(SocketState socket) {
        return new ArrayList<>(Arrays.asList(socket.isWorking(), socket.hasNoDamage()));
    }

    public List<String> createCommentsList(SocketState socket) {
        return new ArrayList<>(Arrays.asList(socket.getWorkingComment(), socket.getDamageComment()));
    }

    public List<Boolean> createCheckOldList(SocketState socket) {
        return new ArrayList<>(Arrays.asList(socket.isWorkingOld(), socket.isDamageOld()));
    }

    @Override
    public void getEntries(DataGridFragment frag) {
        SocketState socket = SocketState.findByRoomAndAP(frag.getCurrentAP().getRoom(), frag.getCurrentAP());

        if (ApartmentType.STUDIO.equals(frag.getCurrentAP().getApartment().getType())) {
            if (frag.getParent() == 0) {
                socket = SocketState.findByKitchenAndAP(frag.getCurrentAP().getKitchen(), frag.getCurrentAP());
                frag.setTableEntries(SocketState.findByKitchenAndAP(frag.getCurrentAP().getKitchen(), frag.getCurrentAP()));
            } else if (frag.getParent() == 1) {
                socket = SocketState.findByBathroomAndAP(frag.getCurrentAP().getBathroom(), frag.getCurrentAP());
                frag.setTableEntries(SocketState.findByBathroomAndAP(frag.getCurrentAP().getBathroom(), frag.getCurrentAP()));
            }
        }
        frag.setHeaderVariante1();
        frag.getRowNames().addAll(socket.ROW_NAMES);
        frag.getCheck().addAll(createCheckList(socket));
        frag.getCheckOld().addAll(createCheckOldList(socket));
        frag.getComments().addAll(createCommentsList(socket));
        frag.setTableContentVariante1();
    }

    @Override
    public void duplicateEntries(AP ap, AP oldAP) {
        SocketState oldSocket = SocketState.findByRoomAndAP(oldAP.getRoom(), oldAP);
        this.copyOldEntries(oldSocket);
        this.save();

        if (ApartmentType.STUDIO.equals(ap.getApartment().getType())) {
            SocketState socket = new SocketState(ap.getKitchen(), ap);
            oldSocket = SocketState.findByKitchenAndAP(oldAP.getKitchen(), oldAP);
            socket.copyOldEntries(oldSocket);
            socket.save();

            socket = new SocketState(ap.getBathroom(), ap);
            oldSocket = SocketState.findByBathroomAndAP(oldAP.getBathroom(), oldAP);
            socket.copyOldEntries(oldSocket);
            socket.save();
        }
    }

    public void copyOldEntries(SocketState oldSocket) {
        this.setIsWorking(oldSocket.isWorking());
        this.setIsWorkingOld(oldSocket.isWorkingOld());
        this.setWorkingComment(oldSocket.getWorkingComment());
        this.setHasNoDamage(oldSocket.hasNoDamage());
        this.setIsDamageOld(oldSocket.isDamageOld());
        this.setDamageComment(oldSocket.getDamageComment());
    }

    @Override
    public void createNewEntry(AP ap) {
        this.save();

        if (ApartmentType.STUDIO.equals(ap.getApartment().getType())) {
            SocketState socket = new SocketState(ap.getKitchen(), ap);
            socket.save();

            socket = new SocketState(ap.getBathroom(), ap);
            socket.save();
        }
    }

    @Override
    public void saveCheckEntries(List<Boolean> check) {
        this.setIsWorking(check.get(0));
        this.setHasNoDamage(check.get(1));
        this.save();
    }

    @Override
    public void saveCheckOldEntries(List<Boolean> checkOld) {
        this.setIsWorkingOld(checkOld.get(0));
        this.setIsDamageOld(checkOld.get(1));
        this.save();
    }

    @Override
    public void saveCommentsEntries(List<String> comments) {
        this.setWorkingComment(comments.get(0));
        this.setDamageComment(comments.get(1));
        this.save();
    }


    public static SocketState findByRoomAndAP(Room room, AP ap) {
        return new Select().from(SocketState.class).where("room = ? and AP = ?", room.getId(), ap.getId()).executeSingle();
    }

    public static SocketState findByBathroomAndAP(Bathroom bathroom, AP ap) {
        return new Select().from(SocketState.class).where("bathroom = ? and AP = ?", bathroom.getId(), ap.getId()).executeSingle();
    }

    public static SocketState findByKitchenAndAP(Kitchen kitchen, AP ap) {
        return new Select().from(SocketState.class).where("kitchen = ? and AP = ?", kitchen.getId(), ap.getId()).executeSingle();
    }

    public static SocketState findById(long id) {
        return new Select().from(SocketState.class).where("id = ?", id).executeSingle();
    }

    public static void initializeRoomSocket(List<AP> aps) {
        for (AP ap : aps) {
            SocketState socket = new SocketState(ap.getRoom(), ap);
            socket.setIsWorking(false);
            socket.setWorkingComment("Deckenlampe ist kaputt");
            socket.setHasNoDamage(true);
            socket.save();
        }
    }

    public static void initializeBathroomSocket(List<AP> aps) {
        for (AP ap : aps) {
            SocketState socket = new SocketState(ap.getBathroom(), ap);
            socket.setIsWorking(true);
            socket.setHasNoDamage(true);
            socket.save();
        }
    }

    public static void initializeKitchenSocket(List<AP> aps) {
        for (AP ap : aps) {
            SocketState socket = new SocketState(ap.getKitchen(), ap);
            socket.setIsWorking(false);
            socket.setHasNoDamage(true);
            socket.save();
        }
    }
}
