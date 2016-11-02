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
import com.example.woko_app.fragment.DataGridFragment;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by camillagretsch on 18.09.16.
 */
@Table(name = "MattressState")
public class MattressState extends Model implements EntryStateInterface {

    @Column(name = "beddingIsClean")
    private boolean beddingIsClean = true;

    @Column(name = "isBeddingOld")
    private boolean isBeddingOld = false;

    @Column(name = "bedding_comment")
    private String beddingComment = null;

    @Column(name = "bedding_picture")
    private byte[] beddingPicture = null;

    @Column(name = "linenIsClean")
    private boolean linenIsClean = true;

    @Column(name = "isLinenOld")
    private boolean isLinenOld = false;

    @Column(name = "linen_comment")
    private String linenComment = null;

    @Column(name = "linen_picture")
    private byte[] linenPicture = null;

    @Column(name = "hasNoDamage")
    private boolean hasNoDamage = true;

    @Column(name = "isDamageOld")
    private boolean isDamageOld = false;

    @Column(name = "damage_comment")
    private String damageComment = null;

    @Column(name = "damage_picture")
    private byte[] damagePicture = null;

    @Column(name = "room", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private Room room;

    @Column(name = "AP", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private AP ap;

    @Column(name = "name")
    private String name = NAME;

    private final static String NAME = "Bettwäsche, Matratze ";

    private static final List<String> ROW_NAMES = Arrays.asList("Wurde gewaschen, getrocknet & gefaltet?", "Matratzenschoner wurde bei 60 gewaschen?", "Ist alles intakt?");

    public MattressState() {
        super();
    }

    public MattressState(Room room, AP ap) {
        super();
        this.room = room;
        this.ap = ap;
    }

    public void setBeddingIsClean(boolean beddingIsClean) {
        this.beddingIsClean = beddingIsClean;
    }

    public boolean getBeddingIsClean() {
        return beddingIsClean;
    }

    public void setIsBeddingOld(boolean isBeddingOld) {
        this.isBeddingOld = isBeddingOld;
    }

    public boolean isBeddingOld() {
        return isBeddingOld;
    }

    public void setBeddingComment(String beddingComment) {
        this.beddingComment = beddingComment;
    }

    public String getBeddingComment() {
        return beddingComment;
    }

    public void setBeddingPicture(byte[] beddingPicture) {
        this.beddingPicture = beddingPicture;
    }

    public byte[] getBeddingPicture() {
        return beddingPicture;
    }

    public void setLinenIsClean(boolean linenIsClean) {
        this.linenIsClean = linenIsClean;
    }

    public boolean getLinenIsClean() {
        return linenIsClean;
    }

    public void setIsLinenOld(boolean isLinenOld) {
        this.isLinenOld = isLinenOld;
    }

    public boolean isLinenOld() {
        return isLinenOld;
    }

    public void setLinenComment(String linenComment) {
        this.linenComment = linenComment;
    }

    public String getLinenComment() {
        return linenComment;
    }

    public void setLinenPicture(byte[] linenPicture) {
        this.linenPicture = linenPicture;
    }

    public byte[] getLinenPicture() {
        return linenPicture;
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
        MattressState mattress = (MattressState) entryStateInterface;
        AP ap = mattress.getAp();

        int counter = 0;
        int year = 0;

        while (year < 5) {

            if (null != MattressState.findByRoomAndAP(ap.getRoom(), ap).getPictureAtPosition(pos)) {
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
        frag.setTableHeader(frag.getResources().getStringArray(R.array.header_variante1));
        frag.getRowNames().addAll(this.ROW_NAMES);
        frag.getCheck().addAll(createCheckList(this));
        frag.getCheckOld().addAll(createCheckOldList(this));
        frag.getComments().addAll(createCommentsList(this));
        frag.getCurrentAP().setLastOpend(this);
        frag.setTableContentVariante1();
    }

    @Override
    public void duplicateEntries(AP ap, AP oldAP) {
        MattressState oldMattress = MattressState.findByRoomAndAP(oldAP.getRoom(), oldAP);
        this.copyEntries(oldMattress);
        this.save();
    }

    @Override
    public void createNewEntry(AP ap) {
        this.save();
    }

    @Override
    public void saveCheckEntries(List<String> check) {
        this.setBeddingIsClean(Boolean.parseBoolean(check.get(0)));
        this.setLinenIsClean(Boolean.parseBoolean(check.get(1)));
        this.setHasNoDamage(Boolean.parseBoolean(check.get(2)));
        this.save();
    }

    @Override
    public void saveCheckOldEntries(List<Boolean> checkOld) {
        this.setIsBeddingOld(checkOld.get(0));
        this.setIsLinenOld(checkOld.get(1));
        this.setIsDamageOld(checkOld.get(2));
        this.save();
    }

    @Override
    public void saveCommentsEntries(List<String> comments) {
        this.setBeddingComment(comments.get(0));
        this.setLinenComment(comments.get(1));
        this.setDamageComment(comments.get(2));
        this.save();
    }
 
    @Override
    public void savePicture(int pos, byte[] picture) {
        switch (pos) {
            case 0:
                this.setBeddingPicture(picture);
                break;
            case 1:
                this.setLinenPicture(picture);
                break;
            case 2:
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
     * @param mattress
     * @return
     */
    private static List<Boolean> createCheckList(MattressState mattress) {
        return new ArrayList<>(Arrays.asList(mattress.getBeddingIsClean(), mattress.getLinenIsClean(), mattress.hasNoDamage()));
    }

    /**
     * add all columns which contain the comment to a list
     * @param mattress
     * @return
     */
    private static List<String> createCommentsList(MattressState mattress) {
        return new ArrayList<>(Arrays.asList(mattress.getBeddingComment(), mattress.getLinenComment(), mattress.getDamageComment()));
    }

    /**
     * add all columns which contain the verification if the entry is old to a list
     * false when the entry is new
     * true when the entry is old
     * @param mattress
     * @return
     */
    private static List<Boolean> createCheckOldList(MattressState mattress) {
        return new ArrayList<>(Arrays.asList(mattress.isBeddingOld(), mattress.isLinenOld(), mattress.isDamageOld()));
    }

    /**
     * add all columns which contain the picture to a list
     * @param mattress
     * @return
     */
    private static List<byte[]> createPictureList(MattressState mattress) {
        return new ArrayList<>(Arrays.asList(mattress.getBeddingPicture(), mattress.getLinenPicture(), mattress.getDamagePicture()));
    }

    /**
     * copies all columns
     * @param oldMattress
     */
    public void copyEntries(MattressState oldMattress) {
        this.setBeddingIsClean(oldMattress.getBeddingIsClean());
        this.setIsBeddingOld(oldMattress.isBeddingOld());
        this.setBeddingComment(oldMattress.getBeddingComment());
        this.setBeddingPicture(oldMattress.getBeddingPicture());
        this.setLinenIsClean(oldMattress.getLinenIsClean());
        this.setIsLinenOld(oldMattress.isLinenOld());
        this.setLinenComment(oldMattress.getLinenComment());
        this.setLinenPicture(oldMattress.getLinenPicture());
        this.setHasNoDamage(oldMattress.hasNoDamage());
        this.setIsDamageOld(oldMattress.isDamageOld());
        this.setDamageComment(oldMattress.getDamageComment());
        this.setDamagePicture(oldMattress.getDamagePicture());
        this.setName(oldMattress.getName());
    }

    /**
     * search it in the db with the room id and protocol id
     * @param room
     * @param ap
     * @return
     */
    public static MattressState findByRoomAndAP(Room room, AP ap) {
        return new Select().from(MattressState.class).where("room = ? and AP = ?", room.getId(), ap.getId()).executeSingle();
    }

    /**
     * fill in the db with initial entries
     * @param aps
     * @param ex
     */
    public static void initializeRoomMattress(List<AP> aps, String ex) {
        for (AP ap : aps) {
            MattressState mattress = new MattressState(ap.getRoom(), ap);
            mattress.setBeddingIsClean(true);
            mattress.setLinenIsClean(true);
            mattress.setHasNoDamage(false);
            mattress.setDamageComment("Loch in der Bettdecke");
            mattress.setName(NAME + ex);
            mattress.save();
        }
    }

    /**
     * fill in the entries in a table to display it in a pdf
     * @param mattress
     * @param posX
     * @param posY
     * @param pageWidth
     * @param headers
     * @param cross
     * @return
     */
    public static Table2 createTable(MattressState mattress, float posX, float posY, float pageWidth, String[] headers, byte[] cross) {
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

            if (createCheckList(mattress).get(i)) {
                row.getCells().add(new Image(cross, 0, 0));
                row.getCells().add("");
            } else {
                row.getCells().add("");
                row.getCells().add(new Image(cross, 0, 0));
            }

            if (createCheckOldList(mattress).get(i)) {
                row.getCells().add(new Image(cross, 0, 0));
            } else
                row.getCells().add("");

            if (null != createCommentsList(mattress).get(i)) {
                row.getCells().add(createCommentsList(mattress).get(i));
            } else
                row.getCells().add("");

            if (null != createPictureList(mattress).get(i)) {
                row.getCells().add(new Image(createPictureList(mattress).get(i), 0, 0));
            } else
                row.getCells().add("");

            i++;
        }
        return table;
    }
}
