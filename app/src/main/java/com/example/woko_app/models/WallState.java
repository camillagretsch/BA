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
 * Created by camillagretsch on 16.09.16.
 */
@Table(name = "WallState")
public class WallState extends Model implements EntryStateInterface{

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
    private String name = NAME;

    private final static String NAME = "Wände, Decke ";

    private static final List<String> ROW_NAMES = Arrays.asList("Ist alles intakt?");

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
        WallState wall = (WallState) entryStateInterface;
        AP ap = wall.getAp();

        int counter = 0;
        int year = 0;

        while (year < 5) {

            if (null != WallState.checkBelonging(wall, ap).getPictureAtPosition(pos)) {
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
        frag.setTableHeader(frag.getResources().getStringArray(R.array.header_variante1));
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
    public void saveCheckEntries(List<String> check) {
        this.setHasNoDamage(Boolean.parseBoolean(check.get(0)));
        this.save();
    }

    @Override
    public void saveCheckOldEntries(List<Boolean> checkOld) {
        this.setIsDamageOld(checkOld.get(0));
        this.save();
    }

    @Override
    public void saveCommentsEntries(List<String> comments) {
        this.setDamageComment(comments.get(0));
        this.save();
    }

    @Override
    public void savePicture(int pos, byte[] picture) {
        switch (pos) {
            case 0:
                this.setDamagePicture(picture);
                break;
        }
        this.save();
    }

    @Override
    public void updateName(String newEx, String oldEx) {
        this.setName(NAME);
        this.save();

        if (createCheckList(this).contains(false)) {
            this.setName(this.getName().concat(newEx));
        }

        if (createCheckOldList(this).contains(true)) {
            this.setName(this.getName().concat(oldEx));
        }
        this.save();
    }

    /**
     * add all columns which contain the verification of the correctness of the entries to a list
     * false when something is incorrect
     * true when something is correct
     * @param wall
     * @return
     */
    private static List<Boolean> createCheckList(WallState wall) {
        return new ArrayList<>(Arrays.asList(wall.hasNoDamage()));
    }

    /**
     * add all columns which contain the comment to a list
     * @param wall
     * @return
     */
    private static List<String> createCommentsList(WallState wall) {
        return new ArrayList<>(Arrays.asList(wall.getDamageComment()));
    }

    /**
     * add all columns which contain the verification if the entry is old to a list
     * false when the entry is new
     * true when the entry is old
     * @param wall
     * @return
     */
    private static List<Boolean> createCheckOldList(WallState wall) {
        return new ArrayList<>(Arrays.asList(wall.isDamageOld()));
    }

    /**
     * add all columns which contain the picture to a list
     * @param wall
     * @return
     */
    private static List<byte[]> createPictureList(WallState wall) {
        return new ArrayList<>(Arrays.asList(wall.getDamagePicture()));
    }

    /**
     * copies all columns
     * @param oldWall
     */
    private void copyOldEntries(WallState oldWall) {
        this.setHasNoDamage(oldWall.hasNoDamage());
        this.setIsDamageOld(oldWall.isDamageOld());
        this.setDamageComment(oldWall.getDamageComment());
        this.setDamagePicture(oldWall.getDamagePicture());
        this.setName(oldWall.getName());
    }

    /**
     * search it in the db with the room id and protocol id
     * @param room
     * @param ap
     * @return
     */
    public static WallState findByRoomAndAP(Room room, AP ap) {
        return new Select().from(WallState.class).where("room = ? and AP = ?", room.getId(), ap.getId()).executeSingle();
    }

    /**
     * search it in the db with the bathroom id and protocol id
     * @param bathroom
     * @param ap
     * @return
     */
    public static WallState findByBathroomAndAP(Bathroom bathroom, AP ap) {
        return new Select().from(WallState.class).where("bathroom = ? and AP = ?", bathroom.getId(), ap.getId()).executeSingle();
    }

    /**
     * search it in the db with the kitchen id and protocol id
     * @param kitchen
     * @param ap
     * @return
     */
    public static WallState findByKitchenAndAP(Kitchen kitchen, AP ap) {
        return new Select().from(WallState.class).where("kitchen = ? and AP = ?", kitchen.getId(), ap.getId()).executeSingle();
    }

    /**
     * check if the entry belong to the kitchen, bathroom or room
     * @param wall
     * @param ap
     * @return
     */
    public static WallState checkBelonging(WallState wall, AP ap) {
        if (wall.getRoom() != null ) {
            return WallState.findByRoomAndAP(ap.getRoom(), ap);
        } else if (wall.getBathroom() != null) {
            return WallState.findByBathroomAndAP(ap.getBathroom(), ap);
        } else
            return WallState.findByKitchenAndAP(ap.getKitchen(), ap);
    }

    /**
     * fill in the db with initial entries
     * @param aps
     * @param ex
     */
    public static void initializeRoomWall(List<AP> aps, String ex) {
        for (AP ap : aps) {
            WallState wall = new WallState(ap.getRoom(), ap);
            wall.setName(wall.getName() + " " + ex);
            wall.setDamageComment("Loch in der Wand hinter der Tür");
            wall.setHasNoDamage(false);
            wall.save();
        }
    }

    /**
     * fill in the db with initial entries
     * @param aps
     * @param ex
     */
    public static void initializeBathroomWall(List<AP> aps, byte[] image, String ex) {
        for (AP ap : aps) {
            WallState wall = new WallState(ap.getBathroom(), ap);
            wall.setName(NAME + ex);
            wall.setHasNoDamage(false);
            wall.setDamageComment("grosser brauner fleck");
            wall.setDamagePicture(image);
            wall.save();
        }
    }

    /**
     * fill in the db with initial entries
     * @param aps
     * @param image
     * @param ex
     */
    public static void initializeKitchenWall(List<AP> aps, byte[] image, String ex) {
        for (AP ap : aps) {
            WallState wall = new WallState(ap.getKitchen(), ap);
            wall.save();
        }
    }

    /**
     * fill in the entries in a table to display it in a pdf
     * @param wall
     * @param posX
     * @param posY
     * @param pageWidth
     * @param headers
     * @param cross
     * @return
     */
    public static Table2 createTable(WallState wall, float posX, float posY, float pageWidth, String[] headers, byte[] cross) {
        Table2 table = new Table2(posX, posY, pageWidth, 700);

        table.getColumns().add(105);
        table.getColumns().add(30);
        table.getColumns().add(30);
        table.getColumns().add(50);
        table.getColumns().add(125);
        table.getColumns().add(175);

        Row2 header = table.getRows().add(30);
        header.getCellDefault().setFont(Font.getHelveticaBold());
        header.getCellDefault().setFontSize(11);
        header.getCellDefault().setAlign(TextAlign.CENTER);
        header.getCellDefault().setVAlign(VAlign.CENTER);
        for (int i = 0; i < headers.length; i++) {
            header.getCells().add(headers[i]);
        }

        int i = 0;
        for (String s : ROW_NAMES) {
            Row2 row = table.getRows().add(30);
            row.getCellDefault().setFontSize(11);
            row.getCellDefault().setAlign(TextAlign.CENTER);
            row.getCellDefault().setVAlign(VAlign.CENTER);

            row.getCells().add(s);

            if (createCheckList(wall).get(i)) {
                row.getCells().add(new Image(cross, 0, 0));
                row.getCells().add("");
            } else {
                row.getCells().add("");
                row.getCells().add(new Image(cross, 0, 0));
            }

            if (createCheckOldList(wall).get(i)) {
                row.getCells().add(new Image(cross, 0, 0));
            } else
                row.getCells().add("");

            if (null != createCommentsList(wall).get(i)) {
                row.getCells().add(createCommentsList(wall).get(i));
            } else
                row.getCells().add("");

            if (null != createPictureList(wall).get(i)) {
                row.getCells().add(new Image(createPictureList(wall).get(i), 0, 0));
            } else
                row.getCells().add("");

            i++;
        }
        return table;
    }
}
