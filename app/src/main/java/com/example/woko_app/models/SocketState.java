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
@Table(name = "SocketState")
public class SocketState extends Model{

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

    public static List<Boolean> createCheckList(SocketState socket) {
        return new ArrayList<>(Arrays.asList(socket.isWorking(), socket.hasNoDamage()));
    }

    public static List<String> createCommentsList(SocketState socket) {
        return new ArrayList<>(Arrays.asList(socket.getWorkingComment(), socket.getDamageComment()));
    }

    public static List<Boolean> createCheckOldList(SocketState socket) {
        return new ArrayList<>(Arrays.asList(socket.isWorkingOld(), socket.isDamageOld()));
    }

    public static void duplicateSocketEntries(SocketState socket, SocketState oldSocket) {
        socket.setIsWorking(oldSocket.isWorking());
        socket.setIsWorkingOld(socket.isWorkingOld());
        socket.setWorkingComment(oldSocket.getWorkingComment());
        socket.setHasNoDamage(oldSocket.hasNoDamage());
        socket.setIsDamageOld(oldSocket.isDamageOld());
        socket.setDamageComment(oldSocket.getDamageComment());
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
