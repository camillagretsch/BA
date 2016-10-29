package com.example.woko_app.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.cete.dynamicpdf.Font;
import com.cete.dynamicpdf.TextAlign;
import com.cete.dynamicpdf.VAlign;
import com.cete.dynamicpdf.pageelements.CellAlign;
import com.cete.dynamicpdf.pageelements.Image;
import com.cete.dynamicpdf.pageelements.Row2;
import com.cete.dynamicpdf.pageelements.Table2;
import com.example.woko_app.R;
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
    private String spotComment = null;

    @Column(name = "spot_picture")
    private byte[] spotPicture = null;

    @Column(name = "hasNoHole")
    private boolean hasNoHole = true;

    @Column(name = "isHoleOld")
    private boolean isHoleOld = false;

    @Column(name = "hole_comment")
    private String holeComment = null;

    @Column(name = "hole_picture")
    private byte[] holePicture = null;

    @Column(name = "hasNoDamage")
    private boolean hasNoDamage = true;

    @Column(name = "isDamageOld")
    private boolean isDamageOld = false;

    @Column(name = "damage_comment")
    private String damageComment = null;

    @Column(name = "damage_picture")
    private byte[] damagePicture = null;

    @Column(name = "room", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Room room;

    @Column(name = "bathroom", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Bathroom bathroom;

    @Column(name = "kitchen", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Kitchen kitchen;

    @Column(name = "AP", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private AP ap;

    @Column(name = "name")
    private String name = "Tür";

    private static final List<String> ROW_NAMES = Arrays.asList("Sind alle Flecken entfernt?", "Sind keine Löcher zusehen?", "Ist alles intakt?");

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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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
    public int countPicturesOfLast5Years(int pos, EntryStateInterface entryStateInterface) {
        DoorState door = (DoorState) entryStateInterface;
        AP ap = door.getAp();

        int counter = 0;
        int year = 0;

        while (year < 5) {

            if (null != DoorState.checkBelonging(door, ap).getPictureAtPosition(pos)) {
                counter++;
            }
            if (null != ap.getOldAP()) {
                ap = ap.getOldAP();
            } else
                break;
            year++;
        }
        return counter;
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
        frag.setTableHeader(frag.getResources().getStringArray(R.array.header_variante1));
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
    public void saveCheckEntries(List<String> check, String ex) {
        this.setHasNoSpot(Boolean.parseBoolean(check.get(0)));
        this.setHasNoHole(Boolean.parseBoolean(check.get(1)));
        this.setHasNoDamage(Boolean.parseBoolean(check.get(2)));
        if (check.contains("false")) {
            this.setName("Tür " + ex);
        } else
            this.setName("Tür");
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

    /**
     * add all columns which contain the verification of the correctness of the entries to a list
     * false when something is incorrect
     * true when something is correct
     * @param door
     * @return
     */
    private static List<Boolean> createCheckList(DoorState door) {
        return new ArrayList<>(Arrays.asList(door.hasNoSpot(), door.hasNoHole(), door.hasNoDamage()));
    }

    /**
     * add all columns which contain the comment to a list
     * @param door
     * @return
     */
    private static List<String> createCommentsList(DoorState door) {
        return new ArrayList<>(Arrays.asList(door.getSpotComment(), door.getHoleComment(), door.getDamageComment()));
    }

    /**
     * add all columns which contain the verification if the entry is old to a list
     * false when the entry is new
     * true when the entry is old
     * @param door
     * @return
     */
    private static List<Boolean> createCheckOldList(DoorState door) {
        return new ArrayList<>(Arrays.asList(door.isSpotOld(), door.isHoleOld(), door.isDamageOld()));
    }

    /**
     * add all columns which contain the picture to a list
     * @param door
     * @return
     */
    private static List<byte[]> createPictureList(DoorState door) {
        return new ArrayList<>(Arrays.asList(door.getSpotPicture(), door.getHolePicture(), door.getDamagePicture()));
    }

    /**
     * copies all columns
     * @param oldDoor
     */
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
        this.setName(oldDoor.getName());
    }

    /**
     * search it in the db with the room id and protocol id
     * @param room
     * @param ap
     * @return
     */
    public static DoorState findByRoomAndAP(Room room, AP ap) {
        return new Select().from(DoorState.class).where("room = ? and AP = ?", room.getId(), ap.getId()).executeSingle();
    }

    /**
     * search it in the db with the bathroom id and protocol id
     * @param bathroom
     * @param ap
     * @return
     */
    public static DoorState findByBathroomAndAP(Bathroom bathroom, AP ap) {
        return new Select().from(DoorState.class).where("bathroom = ? and AP = ?", bathroom.getId(), ap.getId()).executeSingle();
    }

    /**
     * search it in the db with the kitchen id and protocol id
     * @param kitchen
     * @param ap
     * @return
     */
    public static DoorState findByKitchenAndAP(Kitchen kitchen, AP ap) {
        return new Select().from(DoorState.class).where("kitchen = ? and AP = ?", kitchen.getId(), ap.getId()).executeSingle();
    }

    /**
     * check if the entry belong to the kitchen, bathroom or room
     * @param door
     * @param ap
     * @return
     */
    public static DoorState checkBelonging(DoorState door, AP ap) {
        if (door.getRoom() != null) {
            return DoorState.findByRoomAndAP(ap.getRoom(), ap);
        } else if (door.getBathroom() != null) {
            return DoorState.findByBathroomAndAP(ap.getBathroom(), ap);
        } else
            return DoorState.findByKitchenAndAP(ap.getKitchen(), ap);
    }

    /**
     * fill in the db with initial entries
     * @param aps
     * @param ex
     */
    public static void initializeRoomDoor(List<AP> aps, String ex) {
        for (AP ap : aps) {
            DoorState door = new DoorState(ap.getRoom(), ap);
            door.setHasNoSpot(true);
            door.setHasNoHole(true);
            door.setHasNoDamage(false);
            door.setDamageComment("Klinke ist kaputt");
            door.setName(door.getName() + " " + ex);
            door.save();
        }
    }

    /**
     * fill in the db with initial entries
     * @param aps
     */
    public static void initializeBathroomDoor(List<AP> aps) {
        for (AP ap : aps) {
            DoorState door = new DoorState(ap.getBathroom(), ap);
            door.setHasNoSpot(true);
            door.setHasNoHole(true);
            door.setHasNoDamage(true);
            door.save();
        }
    }

    /**
     * fill in the db with initial entries
     * @param aps
     * @param ex
     */
    public static void initializeKitchenDoor(List<AP> aps, String ex) {
        for (AP ap : aps) {
            DoorState door = new DoorState(ap.getKitchen(), ap);
            door.setHasNoSpot(true);
            door.setHasNoHole(false);
            door.setName(door.getName() + " " + ex);
            door.setHasNoDamage(true);
            door.save();
        }
    }

    public static Table2 createPDF(DoorState door, float posX, float posY, float pageWidth, byte[] cross) {
        Table2 table = new Table2(posX, posY, pageWidth, 700);

        table.getColumns().add(100);
        table.getColumns().add(30);
        table.getColumns().add(30);
        table.getColumns().add(50);
        table.getColumns().add(100);
        table.getColumns().add(100);

        Row2 header = table.getRows().add(30);
        header.getCellDefault().setFont(Font.getHelveticaBold());
        header.getCellDefault().setFontSize(11);
        header.getCellDefault().setAlign(TextAlign.CENTER);
        header.getCellDefault().setVAlign(VAlign.CENTER);
        header.getCells().add("");
        header.getCells().add("Ja");
        header.getCells().add("Nein");
        header.getCells().add("alter Eintrag");
        header.getCells().add("Kommentar");
        header.getCells().add("Foto");

        int i = 0;
        for (String s : ROW_NAMES) {
            Row2 row = table.getRows().add(30);
            row.getCellDefault().setFontSize(11);
            row.getCellDefault().setAlign(TextAlign.CENTER);
            row.getCellDefault().setVAlign(VAlign.CENTER);

            row.getCells().add(s);

            if (createCheckList(door).get(i)) {
                row.getCells().add(new Image(cross, 0, 0));
                row.getCells().add("");
            } else {
                row.getCells().add("");
                row.getCells().add(new Image(cross, 0, 0));
            }

            if (createCheckOldList(door).get(i)) {
                row.getCells().add(new Image(cross, 0, 0));
            } else
                row.getCells().add("");

            if (null != createCommentsList(door).get(i)) {
                row.getCells().add(createCommentsList(door).get(i));
            } else
                row.getCells().add("");

            if (null != createPictureList(door).get(i)) {
                row.getCells().add(new Image(createPictureList(door).get(i), 0, 0));
            } else
                row.getCells().add("");

            i++;
        }
        return table;
    }
}
