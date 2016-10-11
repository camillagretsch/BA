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
@Table(name = "DoorState")
public class DoorState extends Model implements EntryStateInterface{

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

    private List<Boolean> createCheckList(DoorState door) {
        return new ArrayList<>(Arrays.asList(door.hasNoSpot(), door.hasNoHole(), door.hasNoDamage()));
    }

    private List<String> createCommentsList(DoorState door) {
        return new ArrayList<>(Arrays.asList(door.getSpotComment(), door.getHoleComment(), door.getDamageComment()));
    }

    private List<Boolean> createCheckOldList(DoorState door) {
        return new ArrayList<>(Arrays.asList(door.isSpotOld(), door.isHoleOld(), door.isDamageOld()));
    }

    private List<byte[]> createPictureList(DoorState door) {
        return new ArrayList<>(Arrays.asList(door.getSpotPicture(), door.getHolePicture(), door.getDamagePicture()));
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
        DoorState door = DoorState.findByRoomAndAP(frag.getCurrentAP().getRoom(), frag.getCurrentAP());

        if (ApartmentType.STUDIO.equals(frag.getCurrentAP().getApartment().getType())) {
            if (frag.getParent() == 0) {
                door = DoorState.findByKitchenAndAP(frag.getCurrentAP().getKitchen(), frag.getCurrentAP());
                frag.setTableEntries(DoorState.findByKitchenAndAP(frag.getCurrentAP().getKitchen(), frag.getCurrentAP()));
            } else if (frag.getParent() == 1) {
                door = DoorState.findByBathroomAndAP(frag.getCurrentAP().getBathroom(), frag.getCurrentAP());
                frag.setTableEntries(DoorState.findByBathroomAndAP(frag.getCurrentAP().getBathroom(), frag.getCurrentAP()));
            }
        }
        frag.setHeaderVariante1();
        frag.getRowNames().addAll(door.ROW_NAMES);
        frag.getCheck().addAll(createCheckList(door));
        frag.getCheckOld().addAll(createCheckOldList(door));
        frag.getComments().addAll(createCommentsList(door));
        frag.getCurrentAP().setLastOpend(door);
        frag.setTableContentVariante1();
    }

    @Override
    public void duplicateEntries(AP ap, AP oldAP) {
        DoorState oldDoor = DoorState.findByRoomAndAP(oldAP.getRoom(), oldAP);
        this.copyOldEntries(oldDoor);
        this.save();

        if (ApartmentType.STUDIO.equals(ap.getApartment().getType())) {
            DoorState door = new DoorState(ap.getKitchen(), ap);
            oldDoor = DoorState.findByKitchenAndAP(oldAP.getKitchen(), oldAP);
            door.copyOldEntries(oldDoor);
            door.save();

            door = new DoorState(ap.getBathroom(), ap);
            oldDoor = DoorState.findByBathroomAndAP(oldAP.getBathroom(), oldAP);
            door.copyOldEntries(oldDoor);
            door.save();
        }
    }

    private void copyOldEntries(DoorState oldDoor) {
        this.setHasNoSpot(oldDoor.hasNoSpot());
        this.setIsSpotOld(oldDoor.isSpotOld());
        this.setSpotComment(oldDoor.getSpotComment());
        this.setSpotPicture(oldDoor.getSpotPicture());
        this.setHasNoHole(oldDoor.hasNoHole());
        this.setIsHoleOld(oldDoor.isHoleOld());
        this.setHoleComment(oldDoor.getHoleComment());
        this.setHolePicture(oldDoor.getHolePicture());
        this.setHasNoDamage(oldDoor.hasNoDamage());
        this.setIsDamageOld(oldDoor.isDamageOld());
        this.setDamageComment(oldDoor.getDamageComment());
        this.setDamagePicture(oldDoor.getDamagePicture());
    }

    @Override
    public void createNewEntry(AP ap) {
        this.save();

        if (ApartmentType.STUDIO.equals(ap.getApartment().getType())) {
            DoorState door = new DoorState(ap.getKitchen(), ap);
            door.save();

            door = new DoorState(ap.getBathroom(), ap);
            door.save();
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

    public static DoorState checkBelonging(DoorState door, AP ap) {
        if (door.getRoom() != null) {
            return DoorState.findByRoomAndAP(ap.getRoom(), ap);
        } else if (door.getBathroom() != null) {
            return DoorState.findByBathroomAndAP(ap.getBathroom(), ap);
        } else
            return DoorState.findByKitchenAndAP(ap.getKitchen(), ap);
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
