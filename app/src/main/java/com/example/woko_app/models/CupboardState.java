package com.example.woko_app.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.cete.dynamicpdf.Font;
import com.cete.dynamicpdf.TextAlign;
import com.cete.dynamicpdf.VAlign;
import com.cete.dynamicpdf.pageelements.CellAlign;
import com.cete.dynamicpdf.pageelements.CellVAlign;
import com.cete.dynamicpdf.pageelements.Image;
import com.cete.dynamicpdf.pageelements.Row;
import com.cete.dynamicpdf.pageelements.Row2;
import com.cete.dynamicpdf.pageelements.Table2;
import com.example.woko_app.R;
import com.example.woko_app.constants.ApartmentType;
import com.example.woko_app.fragment.DataGridFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by camillagretsch on 19.09.16.
 */
@Table(name = "CupboardState")
public class CupboardState extends Model implements EntryStateInterface{

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

    @Column(name = "kitchen", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Kitchen kitchen;

    @Column(name = "AP", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private AP ap;

    @Column(name = "name")
    private String name = NAME;

    private final static String NAME = "Schränke ";

    private static final List<String> ROW_NAMES = Arrays.asList("Sind gereinigt?", "Ist alles intakt?");

    public CupboardState() {
        super();
    }

    public CupboardState(Kitchen kitchen, AP ap) {
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
        CupboardState cupboard = (CupboardState) entryStateInterface;
        AP ap = cupboard.getAp();

        int counter = 0;
        int year = 0;

        while (year < 5) {

            if (null != CupboardState.findByKitchenAndAP(ap.getKitchen(), ap).getPictureAtPosition(pos)) {
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
        if (ApartmentType.STUDIO.equals(ap.getApartment().getType())) {
            CupboardState oldCupboard = CupboardState.findByKitchenAndAP(oldAP.getKitchen(), oldAP);
            this.copyOldEntries(oldCupboard);
            this.save();
        }
    }

    @Override
    public void createNewEntry(AP ap) {
        if (ApartmentType.STUDIO.equals(ap.getApartment().getType())) {
            this.save();
        }
    }

    @Override
    public void saveCheckEntries(List<String> check) {
        this.setIsClean(Boolean.parseBoolean(check.get(0)));
        this.setHasNoDamage(Boolean.parseBoolean(check.get(1)));
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
     * @param cupboard
     * @return
     */
    private static List<Boolean> createCheckList(CupboardState cupboard) {
        return new ArrayList<>(Arrays.asList(cupboard.isClean(), cupboard.hasNoDamage()));
    }

    /**
     * add all columns which contain the comment to a list
     * @param cupboard
     * @return
     */
    private static List<String> createCommentsList(CupboardState cupboard) {
        return new ArrayList<>(Arrays.asList(cupboard.getCleanComment(), cupboard.getDamageComment()));
    }

    /**
     * add all columns which contain the verification if the entry is old to a list
     * false when the entry is new
     * true when the entry is old
     * @param cupboard
     * @return
     */
    private static List<Boolean> createCheckOldList(CupboardState cupboard) {
        return new ArrayList<>(Arrays.asList(cupboard.isCleanOld(), cupboard.isDamageOld()));
    }

    /**
     * add all columns which contain the picture to a list
     * @param cupboard
     * @return
     */
    private static List<byte[]> createPictureList(CupboardState cupboard) {
        return new ArrayList<>(Arrays.asList(cupboard.getCleanPicture(), cupboard.getDamagePicture()));
    }

    /**
     * copies all columns
     * @param oldCupboard
     */
    private void copyOldEntries(CupboardState oldCupboard) {
        this.setIsClean(oldCupboard.isClean());
        this.setIsCleanOld(oldCupboard.isCleanOld());
        this.setCleanComment(oldCupboard.getCleanComment());
        this.setCleanPicture(oldCupboard.getCleanPicture());
        this.setHasNoDamage(oldCupboard.hasNoDamage());
        this.setIsDamageOld(oldCupboard.isDamageOld());
        this.setDamageComment(oldCupboard.getDamageComment());
        this.setDamagePicture(oldCupboard.getDamagePicture());
        this.setName(oldCupboard.getName());
    }

    /**
     * search it in the db with the kitchen id and protocol id
     * @param kitchen
     * @param ap
     * @return
     */
    public static CupboardState findByKitchenAndAP(Kitchen kitchen, AP ap) {
        return new Select().from(CupboardState.class).where("kitchen = ? and AP = ?", kitchen.getId(), ap.getId()).executeSingle();
    }

    /**
     * fill in the db with initial entries
     * @param aps
     */
    public static void initializeKitchenCupboard(List<AP> aps) {
        for (AP ap : aps) {
            CupboardState cupboard = new CupboardState(ap.getKitchen(), ap);
            cupboard.save();
        }
    }

    /**
     * fill in the entries in a table to display it in a pdf
     * @param cupboard
     * @param posX
     * @param posY
     * @param pageWidth
     * @param headers
     * @param cross
     * @return
     */
    public static Table2 createTable(CupboardState cupboard, float posX, float posY, float pageWidth, String[] headers, byte[] cross) {
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

            if (createCheckList(cupboard).get(i)) {
                row.getCells().add(new Image(cross, 0, 0));
                row.getCells().add("");
            } else {
                row.getCells().add("");
                row.getCells().add(new Image(cross, 0, 0));
            }

            if (createCheckOldList(cupboard).get(i)) {
                row.getCells().add(new Image(cross, 0, 0));
            } else
                row.getCells().add("");

            if (null != createCommentsList(cupboard).get(i)) {
                row.getCells().add(createCommentsList(cupboard).get(i));
            } else
                row.getCells().add("");

            if (null != createPictureList(cupboard).get(i)) {
                row.getCells().add(new Image(createPictureList(cupboard).get(i), 0, 0));
            } else
                row.getCells().add("");

            i++;
        }
        return table;
    }
}
