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
 * Created by camillagretsch on 16.09.16.
 */
@Table(name = "WallState")
public class WallState extends Model implements EntryStateInterface{

    @Column(name = "hasNoSpot")
    private boolean hasNoSpot = true;

    @Column(name = "isSpotOld")
    private boolean isSpotOld = false;

    @Column(name = "spot_comment")
    private String spotComment;

    @Column(name = "spot_picture")
    private byte[] spotPicture;

    @Column(name = "hasNoHole")
    private boolean hasNoHole = true;

    @Column(name = "isHoleOld")
    private boolean isHoleOld = false;

    @Column(name = "hole_comment")
    private String holeComment;

    @Column(name = "hole_picture")
    private byte[] holePicture;

    @Column(name = "hasNoDamage")
    private boolean hasNoDamage = true;

    @Column(name = "isDamageOld")
    private boolean isDamageOld = false;

    @Column(name = "damage_comment")
    private String damageComment;

    @Column(name = "damage_picture")
    private byte[] damagePicture;

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

    public void setSpotPicture(byte[] spotPicture) {
        this.spotPicture = spotPicture;
    }

    public byte[] getSpotPicture() {
        return spotPicture;
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

    public void setHolePicture(byte[] holePicture) {
        this.holePicture = holePicture;
    }

    public byte[] getHolePicture() {
        return holePicture;
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

    public void setDamagePicture(byte[] damagePicture) {
        this.damagePicture = damagePicture;
    }

    public byte[] getDamagePicture() {
        return damagePicture;
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

    private List<Boolean> createCheckList(WallState wall) {
        return new ArrayList<>(Arrays.asList(wall.hasNoSpot(), wall.hasNoHole(), wall.hasNoDamage()));
    }

    private List<String> createCommentsList(WallState wall) {
        return new ArrayList<>(Arrays.asList(wall.getSpotComment(), wall.getHoleComment(), wall.getDamageComment()));
    }

    private List<Boolean> createCheckOldList(WallState wall) {
        return new ArrayList<>(Arrays.asList(wall.isSpotOld(), wall.isHoleOld(), wall.isDamageOld()));
    }

    private List<byte[]> createPictureList(WallState wall) {
        return new ArrayList<>(Arrays.asList(wall.getSpotPicture(), wall.getHolePicture(), wall.getDamagePicture()));
    }

    @Override
    public String getCommentAtPosition(int pos) {
        return createCommentsList(this).get(pos);
    }

    @Override
    public Boolean getCheckAtPosition(int pos) {
        return createCheckList(this).get(pos);
    }

    @Override
    public Boolean getCheckOldAtPosition(int pos) {
        return createCheckOldList(this).get(pos);
    }

    @Override
    public byte[] getPictureAtPosition(int pos) {
        return createPictureList(this).get(pos);
    }

    @Override
    public void getEntries(DataGridFragment frag) {
        WallState wall = WallState.findByRoomAndAP(frag.getCurrentAP().getRoom(), frag.getCurrentAP());

        if (ApartmentType.STUDIO.equals(frag.getCurrentAP().getApartment().getType())) {
            if (frag.getParent() == 0) {
                wall = WallState.findByKitchenAndAP(frag.getCurrentAP().getKitchen(), frag.getCurrentAP());
                frag.setTableEntries(WallState.findByKitchenAndAP(frag.getCurrentAP().getKitchen(), frag.getCurrentAP()));
            } else if (frag.getParent() == 1) {
                wall = WallState.findByBathroomAndAP(frag.getCurrentAP().getBathroom(), frag.getCurrentAP());
                frag.setTableEntries(WallState.findByBathroomAndAP(frag.getCurrentAP().getBathroom(), frag.getCurrentAP()));
            }
        }
        frag.setHeaderVariante1();
        frag.getRowNames().addAll(wall.ROW_NAMES);
        frag.getCheck().addAll(createCheckList(wall));
        frag.getCheckOld().addAll(createCheckOldList(wall));
        frag.getComments().addAll(createCommentsList(wall));
        frag.getCurrentAP().setLastOpend(wall);
        frag.setTableContentVariante1();
    }

    @Override
    public void duplicateEntries(AP ap, AP oldAP) {
        WallState oldWall = WallState.findByRoomAndAP(oldAP.getRoom(), oldAP);
        this.copyOldEntries(oldWall);
        this.save();

        if (ApartmentType.STUDIO.equals(ap.getApartment().getType())) {
            WallState wall = new WallState(ap.getKitchen(), ap);
            oldWall = WallState.findByKitchenAndAP(oldAP.getKitchen(), oldAP);
            wall.copyOldEntries(oldWall);
            wall.save();

            wall = new WallState(ap.getBathroom(), ap);
            oldWall = WallState.findByBathroomAndAP(oldAP.getBathroom(), oldAP);
            wall.copyOldEntries(oldWall);
            wall.save();
        }
    }

    private void copyOldEntries(WallState oldWall) {
        this.setHasNoSpot(oldWall.hasNoSpot());
        this.setIsSpotOld(oldWall.isSpotOld());
        this.setSpotComment(oldWall.getSpotComment());
        this.setSpotPicture(oldWall.getSpotPicture());
        this.setHasNoHole(oldWall.hasNoHole());
        this.setIsHoleOld(oldWall.isHoleOld());
        this.setHoleComment(oldWall.getHoleComment());
        this.setHolePicture(oldWall.getHolePicture());
        this.setHasNoDamage(oldWall.hasNoDamage());
        this.setIsDamageOld(oldWall.isDamageOld());
        this.setDamageComment(oldWall.getDamageComment());
        this.setDamagePicture(oldWall.getDamagePicture());
    }

    @Override
    public void createNewEntry(AP ap) {
        this.save();

        if (ApartmentType.STUDIO.equals(ap.getApartment().getType())) {
            WallState wall = new WallState(ap.getKitchen(), ap);
            wall.save();

            wall = new WallState(ap.getBathroom(), ap);
            wall.save();
        }
    }

    @Override
    public void saveCheckEntries(List<Boolean> check) {
        this.setHasNoSpot(check.get(0));
        this.setHasNoHole(check.get(1));
        this.setHasNoDamage(check.get(2));
        this.save();
    }

    @Override
    public void saveCheckOldEntries(List<Boolean> checkOld) {
        this.setIsSpotOld(checkOld.get(0));
        this.setIsHoleOld(checkOld.get(1));
        this.setIsDamageOld(checkOld.get(2));
        this.save();
    }

    @Override
    public void saveCommentsEntries(List<String> comments) {
        this.setSpotComment(comments.get(0));
        this.setHoleComment(comments.get(1));
        this.setDamageComment(comments.get(2));
        this.save();
    }

    @Override
    public void savePicture(int pos, byte[] picture) {
        switch (pos) {
            case 0:
                this.setSpotPicture(picture);
                break;
            case 1:
                this.setHolePicture(picture);
                break;
            case 2:
                this.setDamagePicture(picture);
                break;
        }
        this.save();
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

    public static WallState checkBelonging(WallState wall, AP ap) {
        if (wall.getRoom() != null ) {
            return WallState.findByRoomAndAP(ap.getRoom(), ap);
        } else if (wall.getBathroom() != null) {
            return WallState.findByBathroomAndAP(ap.getBathroom(), ap);
        } else
            return WallState.findByKitchenAndAP(ap.getKitchen(), ap);
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

    public static void initializeKitchenWall(List<AP> aps, byte[] image) {
        for (AP ap : aps) {
            WallState wall = new WallState(ap.getKitchen(), ap);
            wall.setHasNoSpot(false);
            wall.setSpotComment("Brauner Fleck");
            wall.setSpotPicture(image);
            wall.setHasNoHole(true);
            wall.setHasNoDamage(true);
            wall.save();
        }
    }
}
