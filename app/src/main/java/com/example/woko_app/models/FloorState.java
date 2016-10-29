package com.example.woko_app.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.cete.dynamicpdf.Font;
import com.cete.dynamicpdf.TextAlign;
import com.cete.dynamicpdf.VAlign;
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
 * Created by camillagretsch on 11.09.16.
 */
@Table(name = "FloorState")
public class FloorState extends Model implements EntryStateInterface {

    @Column(name = "isClean")
    private boolean isClean = true;

    @Column(name = "isCleanOld")
    private boolean isCleanOld = false;

    @Column(name = "clean_comment")
    private String cleanComment = null;

    @Column(name = "clean_picture")
    private byte[] cleanPicture = null;

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
    private String name = "Boden";

    private static final List<String> ROW_NAMES = Arrays.asList("Ist besenrein?", "Ist alles intakt?");

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

    public void setCleanPicture(byte[] cleanPicture) {
        this.cleanPicture = cleanPicture;
    }

    public byte[] getCleanPicture() {
        return cleanPicture;
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
        FloorState floor = (FloorState) entryStateInterface;
        AP ap = floor.getAp();

        int counter = 0;
        int year = 0;

        while (year < 5) {

            if (null != FloorState.checkBelonging(floor, ap).getPictureAtPosition(pos)) {
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
        FloorState floor = FloorState.findByRoomAndAP(frag.getCurrentAP().getRoom(), frag.getCurrentAP());

        if (ApartmentType.STUDIO.equals(frag.getCurrentAP().getApartment().getType())) {
            if (frag.getParent() == 0) {
                floor = FloorState.findByKitchenAndAP(frag.getCurrentAP().getKitchen(), frag.getCurrentAP());
                frag.setTableEntries(FloorState.findByKitchenAndAP(frag.getCurrentAP().getKitchen(), frag.getCurrentAP()));
            } else if (frag.getParent() == 1) {
                floor = FloorState.findByBathroomAndAP(frag.getCurrentAP().getBathroom(), frag.getCurrentAP());
                frag.setTableEntries(FloorState.findByBathroomAndAP(frag.getCurrentAP().getBathroom(), frag.getCurrentAP()));
            }
        }
        frag.setTableHeader(frag.getResources().getStringArray(R.array.header_variante1));
        frag.getRowNames().addAll(floor.ROW_NAMES);
        frag.getCheck().addAll(createCheckList(floor));
        frag.getCheckOld().addAll(createCheckOldList(floor));
        frag.getComments().addAll(createCommentsList(floor));
        frag.getCurrentAP().setLastOpend(floor);
        frag.setTableContentVariante1();
    }

    @Override
    public void duplicateEntries(AP ap, AP oldAP) {
        FloorState oldFloor = FloorState.findByRoomAndAP(oldAP.getRoom(), oldAP);
        this.copyOldEntries(oldFloor);
        this.save();

        if (ApartmentType.STUDIO.equals(ap.getApartment().getType())) {
            FloorState floor = new FloorState(ap.getKitchen(), ap);
            oldFloor = FloorState.findByKitchenAndAP(oldAP.getKitchen(), oldAP);
            floor.copyOldEntries(oldFloor);
            floor.save();

            floor = new FloorState(ap.getBathroom(), ap);
            oldFloor = FloorState.findByBathroomAndAP(oldAP.getBathroom(), oldAP);
            floor.copyOldEntries(oldFloor);
            floor.save();
        }
    }

    @Override
    public void createNewEntry(AP ap) {
        this.save();

        if (ApartmentType.STUDIO.equals(ap.getApartment().getType())) {
            FloorState floor = new FloorState(ap.getKitchen(), ap);
            floor.save();

            floor = new FloorState(ap.getBathroom(), ap);
            floor.save();
        }
    }

    @Override
    public void saveCheckEntries(List<String> check, String ex) {
        this.setIsClean(Boolean.parseBoolean(check.get(0)));
        this.setHasNoDamage(Boolean.parseBoolean(check.get(1)));
        if (check.contains("false")) {
            this.setName("Boden " + ex);
        } else
            this.setName("Boden");
        this.save();
    }

    @Override
    public void saveCheckOldEntries(List<Boolean> checkOld) {
        this.setIsCleanOld(checkOld.get(0));
        this.setIsDamageOld(checkOld.get(1));
        this.save();
    }

    @Override
    public void saveCommentsEntries(List<String> comments) {
        this.setCleanComment(comments.get(0));
        this.setDamageComment(comments.get(1));
        this.save();
    }

    @Override
    public void savePicture(int pos, byte[] picture) {
        switch (pos) {
            case 0:
                this.setCleanPicture(picture);
                break;
            case 1:
                this.setDamagePicture(picture);
                break;
        }
        this.save();
    }

    /**
     * add all columns which contain the verification of the correctness of the entries to a list
     * false when something is incorrect
     * true when something is correct
     * @param floor
     * @return
     */
    private static List<Boolean> createCheckList(FloorState floor) {
        return new ArrayList<>(Arrays.asList(floor.isClean(), floor.hasNoDamage()));
    }

    /**
     * add all columns which contain the comment to a list
     * @param floor
     * @return
     */
    private static List<String> createCommentsList(FloorState floor) {
        return new ArrayList<>(Arrays.asList(floor.getCleanComment(), floor.getDamageComment()));
    }

    /**
     * add all columns which contain the verification if the entry is old to a list
     * false when the entry is new
     * true when the entry is old
     * @param floor
     * @return
     */
    private static List<Boolean> createCheckOldList(FloorState floor) {
        return new ArrayList<>(Arrays.asList(floor.isCleanOld(), floor.isDamageOld()));
    }

    /**
     * add all columns which contain the picture to a list
     * @param floor
     * @return
     */
    private static List<byte[]> createPictureList(FloorState floor) {
        return new ArrayList<>(Arrays.asList(floor.getCleanPicture(), floor.getDamagePicture()));
    }

    /**
     * copies all columns
     * @param oldFloor
     */
    private void copyOldEntries(FloorState oldFloor) {
        this.setIsClean(oldFloor.isClean());
        this.setIsCleanOld(oldFloor.isCleanOld());
        this.setCleanComment(oldFloor.getCleanComment());
        this.setCleanPicture(oldFloor.getCleanPicture());
        this.setHasNoDamage(oldFloor.hasNoDamage());
        this.setIsDamageOld(oldFloor.isDamageOld());
        this.setDamageComment(oldFloor.getDamageComment());
        this.setDamagePicture(oldFloor.getDamagePicture());
        this.setName(oldFloor.getName());
    }

    /**
     * search it in the db with the room id and protocol id
     * @param room
     * @param ap
     * @return
     */
    public static FloorState findByRoomAndAP(Room room, AP ap) {
        return new Select().from(FloorState.class).where("room = ? and AP = ?", room.getId(), ap.getId()).executeSingle();
    }

    /**
     * search it in the db with the bathroom id and protocol id
     * @param bathroom
     * @param ap
     * @return
     */
    public static FloorState findByBathroomAndAP(Bathroom bathroom, AP ap) {
        return new Select().from(FloorState.class).where("bathroom = ? and AP = ?", bathroom.getId(), ap.getId()).executeSingle();
    }

    /**
     * search it in the db with the kitchen id and protocol id
     * @param kitchen
     * @param ap
     * @return
     */
    public static FloorState findByKitchenAndAP(Kitchen kitchen, AP ap) {
        return new Select().from(FloorState.class).where("kitchen = ? and AP = ?", kitchen.getId(), ap.getId()).executeSingle();
    }

    /**
     * check if the entry belong to the kitchen, bathroom or room
     * @param floor
     * @param ap
     * @return
     */
    public static FloorState checkBelonging(FloorState floor, AP ap) {
        if (floor.getRoom() != null) {
            return FloorState.findByRoomAndAP(ap.getRoom(), ap);
        } else if (floor.getBathroom() != null) {
            return FloorState.findByBathroomAndAP(ap.getBathroom(), ap);
        } else
            return FloorState.findByKitchenAndAP(ap.getKitchen(), ap);
    }

    /**
     * fill in the db with initial entries
     * @param aps
     * @param ex
     */
    public static void initializeRoomFloor(List<AP> aps, String ex) {
        for (AP ap : aps) {
            FloorState floor = new FloorState(ap.getRoom(), ap);
            floor.setIsClean(true);
            floor.setHasNoDamage(false);
            floor.setDamageComment("Risse im Boden");
            floor.setName(floor.getName() + " " + ex);
            floor.save();
        }
    }

    /**
     * fill in the db with initial entries
     * @param aps
     * @param ex
     */
    public static void initializeBathroomFloor(List<AP> aps, String ex) {
        for (AP ap :aps) {
            FloorState floor = new FloorState(ap.getBathroom(), ap);
            floor.setIsClean(false);
            floor.setCleanComment("Krümmel am Boden");
            floor.setName(floor.getName() + " " + ex);
            floor.setHasNoDamage(true);
            floor.save();
        }
    }

    /**
     * fill in the db with initial entries
     * @param aps
     */
    public static void initializeKitchenFloor(List<AP> aps) {
        for (AP ap :aps) {
            FloorState floor = new FloorState(ap.getKitchen(), ap);
            floor.setIsClean(true);
            floor.setHasNoDamage(true);
            floor.save();
        }
    }

    public static Table2 createPDF(FloorState floor, float posX, float posY, float pageWidth, byte[] cross) {
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

            if (createCheckList(floor).get(i)) {
                row.getCells().add(new Image(cross, 0, 0));
                row.getCells().add("");
            } else {
                row.getCells().add("");
                row.getCells().add(new Image(cross, 0, 0));
            }

            if (createCheckOldList(floor).get(i)) {
                row.getCells().add(new Image(cross, 0, 0));
            } else
                row.getCells().add("");

            if (null != createCommentsList(floor).get(i)) {
                row.getCells().add(createCommentsList(floor).get(i));
            } else
                row.getCells().add("");

            if (null != createPictureList(floor).get(i)) {
                row.getCells().add(new Image(createPictureList(floor).get(i), 0, 0));
            } else
                row.getCells().add("");

            i++;
        }
        return table;
    }
}
