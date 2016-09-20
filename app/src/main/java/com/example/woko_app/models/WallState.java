package com.example.woko_app.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by camillagretsch on 16.09.16.
 */
@Table(name = "WallState")
public class WallState extends Model{

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

    public WallState() {
        super();
    }

    public WallState(Room room, AP ap) {
        super();
        this.room = room;
        this.ap = ap;
    }

    public WallState(Bathroom bathroom, AP ap) {
        super();
        this.bathroom = bathroom;
        this.ap = ap;
    }

    public WallState(Kitchen kitchen, AP ap) {
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

    public static List<Boolean> createCheckList(WallState wall) {
        return new ArrayList<>(Arrays.asList(wall.hasNoSpot(), wall.hasNoHole(), wall.hasNoDamage()));
    }

    public static List<String> createCommentsList(WallState wall) {
        return new ArrayList<>(Arrays.asList(wall.getSpotComment(), wall.getHoleComment(), wall.getDamageComment()));
    }

    public static List<Boolean> createCheckOldList(WallState wall) {
        return new ArrayList<>(Arrays.asList(wall.isSpotOld(), wall.isHoleOld(), wall.isDamageOld()));
    }

    public static void duplicateWallEntries(WallState wall, WallState oldWall) {
        wall.setHasNoSpot(oldWall.hasNoSpot());
        wall.setIsSpotOld(oldWall.isSpotOld());
        wall.setSpotComment(oldWall.getSpotComment());
        wall.setHasNoHole(oldWall.hasNoHole());
        wall.setIsHoleOld(oldWall.isHoleOld());
        wall.setHoleComment(oldWall.getHoleComment());
        wall.setHasNoDamage(oldWall.hasNoDamage());
        wall.setIsDamageOld(oldWall.isDamageOld());
        wall.setDamageComment(oldWall.getDamageComment());
    }

    public static WallState findByRoomAndAP(Room room, AP ap) {
        return new Select().from(WallState.class).where("room = ? and AP = ?", room.getId(), ap.getId()).executeSingle();
    }

    public static WallState findByBathroomAndAP(Bathroom bathroom, AP ap) {
        return new Select().from(WallState.class).where("bathroom = ? and AP = ?", bathroom.getId(), ap.getId()).executeSingle();
    }

    public static WallState findByKitchenAndAP(Kitchen kitchen, AP ap) {
        return new Select().from(WallState.class).where("kitchen = ? and AP = ?", kitchen.getId(), ap.getId()).executeSingle();
    }

    public static WallState findById(long id) {
        return new Select().from(WallState.class).where("id = ?", id).executeSingle();
    }

    public static void initializeRoomWall(List<AP> aps) {
        for (AP ap : aps) {
            WallState wall = new WallState(ap.getRoom(), ap);
            wall.setHasNoSpot(false);
            wall.setSpotComment("Braune Flecken");
            wall.setHasNoHole(true);
            wall.setHasNoDamage(true);
            wall.save();
        }
    }

    public static void initializeBathroomWall(List<AP> aps) {
        for (AP ap : aps) {
            WallState wall = new WallState(ap.getBathroom(), ap);
            wall.setHasNoSpot(true);
            wall.setHasNoHole(false);
            wall.setHoleComment("Grosses Loch in der Wand bei der Dusche");
            wall.setHasNoDamage(true);
            wall.save();
        }
    }

    public static void initializeKitchenWall(List<AP> aps) {
        for (AP ap : aps) {
            WallState wall = new WallState(ap.getKitchen(), ap);
            wall.setHasNoSpot(false);
            wall.setSpotComment("Brandflecken");
            wall.setHasNoHole(true);
            wall.setHasNoDamage(true);
            wall.save();
        }
    }
}
