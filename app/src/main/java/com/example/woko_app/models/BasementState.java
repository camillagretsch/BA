package com.example.woko_app.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.cete.dynamicpdf.Font;
import com.cete.dynamicpdf.pageelements.CellAlign;
import com.cete.dynamicpdf.pageelements.CellVAlign;
import com.cete.dynamicpdf.pageelements.Image;
import com.cete.dynamicpdf.pageelements.Row;
import com.example.woko_app.R;
import com.example.woko_app.constants.ApartmentType;
import com.example.woko_app.fragment.DataGridFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by camillagretsch on 19.09.16.
 */
@Table(name = "BasementState")
public class BasementState extends Model implements EntryStateInterface {

    @Column(name = "isClean")
    private boolean isClean = true;

    @Column(name = "isCleanOld")
    private boolean isCleanOld = false;

    @Column(name = "cleanComment")
    private String cleanComment = null;

    @Column(name = "clean_picture")
    private byte[] cleanPicture = null;

    @Column(name = "isEmpty")
    private boolean isEmpty = true;

    @Column(name = "isEmptyOld")
    private boolean isEmptyOld = false;

    @Column(name = "empty_comment")
    private String emptyComment = null;

    @Column(name = "empty_picture")
    private byte[] emptyPicture = null;

    @Column(name = "hasNoDamage")
    private boolean hasNoDamage = true;

    @Column(name = "isDamageOld")
    private boolean isDamageOld = false;

    @Column(name = "damage_comment")
    private String damageComment = null;

    @Column(name = "damage_picture")
    private byte[] damagePicture = null;

    @Column(name = "apartment", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private Apartment apartment;

    @Column(name = "AP", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private AP ap;

    @Column(name = "name")
    private String name = "Keller";

    private static final List<String> ROW_NAMES = Arrays.asList("Ist besenrein?", "Der Keller ist geräumt", "Ist alles intakt?");

    public BasementState() {
        super();
    }

    public BasementState(Apartment apartment, AP ap) {
        super();
        this.apartment = apartment;
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

    public void setIsEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setIsEmptyOld(boolean isEmptyOld) {
        this.isEmptyOld = isEmptyOld;
    }

    public boolean isEmptyOld() {
        return isEmptyOld;
    }

    public void setEmptyComment(String emptyComment) {
        this.emptyComment = emptyComment;
    }

    public String getEmptyComment() {
        return emptyComment;
    }

    public void setEmptyPicture(byte[] emptyPicture) {
        this.emptyPicture = emptyPicture;
    }

    public byte[] getEmptyPicture() {
        return emptyPicture;
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

    public Apartment getApartment() {
        return apartment;
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
        BasementState basement = (BasementState) entryStateInterface;
        AP ap = basement.getAp();

        int counter = 0;
        int year = 0;

        while (year < 5) {

            if (null != BasementState.findByApartmentAndAP(ap.getApartment(), ap).getPictureAtPosition(pos)) {
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
            BasementState oldBasement = BasementState.findByApartmentAndAP(oldAP.getApartment(), oldAP);
            this.copyOldEntries(oldBasement);
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
    public void saveCheckEntries(List<String> check, String ex) {
        this.setIsClean(Boolean.parseBoolean(check.get(0)));
        this.setIsEmpty(Boolean.parseBoolean(check.get(1)));
        this.setHasNoDamage(Boolean.parseBoolean(check.get(2)));
        this.save();
    }

    @Override
    public void saveCheckOldEntries(List<Boolean> checkOld) {
        this.setIsCleanOld(checkOld.get(0));
        this.setIsEmptyOld(checkOld.get(1));
        this.setIsDamageOld(checkOld.get(2));
        this.save();
    }

    @Override
    public void saveCommentsEntries(List<String> comments) {
        this.setCleanComment(comments.get(0));
        this.setEmptyComment(comments.get(1));
        this.setDamageComment(comments.get(2));
        this.save();
    }

    @Override
    public void savePicture(int pos, byte[] picture) {
        switch (pos) {
            case 0:
                this.setCleanPicture(picture);
                break;
            case 1:
                this.setEmptyPicture(picture);
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
     * @param basement
     * @return
     */
    private static List<Boolean> createCheckList(BasementState basement) {
        return new ArrayList<>(Arrays.asList(basement.isClean(), basement.isEmpty(), basement.hasNoDamage()));
    }

    /**
     * add all columns which contain the comment to a list
     * @param basement
     * @return
     */
    private static List<String> createCommentsList(BasementState basement) {
        return new ArrayList<>(Arrays.asList(basement.getCleanComment(), basement.getEmptyComment(), basement.getDamageComment()));
    }

    /**
     * add all columns which contain the verification if the entry is old to a list
     * false when the entry is new
     * true when the entry is old
     * @param basement
     * @return
     */
    private static List<Boolean> createCheckOldList(BasementState basement) {
        return new ArrayList<>(Arrays.asList(basement.isCleanOld(), basement.isEmptyOld(), basement.isDamageOld()));
    }

    /**
     * add all columns which contain the picture to a list
     * @param basement
     * @return
     */
    private static List<byte[]> createPictureList(BasementState basement) {
        return new ArrayList<>(Arrays.asList(basement.getCleanPicture(), basement.getEmptyPicture(), basement.getDamagePicture()));
    }

    /**
     * copies all columns
     * @param oldBasement
     */
    private void copyOldEntries(BasementState oldBasement) {
        this.setIsClean(oldBasement.isClean());
        this.setIsCleanOld(oldBasement.isCleanOld());
        this.setCleanComment(oldBasement.getCleanComment());
        this.setCleanPicture(oldBasement.getCleanPicture());
        this.setIsEmpty(oldBasement.isEmpty());
        this.setIsEmptyOld(oldBasement.isEmptyOld());
        this.setEmptyComment(oldBasement.getEmptyComment());
        this.setEmptyPicture(oldBasement.getEmptyPicture());
        this.setHasNoDamage(oldBasement.hasNoDamage());
        this.setIsDamageOld(oldBasement.isDamageOld());
        this.setDamageComment(oldBasement.getDamageComment());
        this.setDamagePicture(oldBasement.getDamagePicture());
    }

    /**
     * set the name of the basement
     * first add the icon
     * if some check entries are false add an exclamation mark to the basement name
     * @param ap
     * @param ex
     * @param icon
     * @return
     */
    public static String updateBasamentName(AP ap, String ex, String icon) {
        BasementState basement = BasementState.findByApartmentAndAP(ap.getApartment(), ap);

        if (basement.createCheckList(basement).contains(false)) {
            basement.setName(icon + " Keller " + ex);
        } else
            basement.setName(icon + " Keller");

        basement.save();
        return basement.getName();
    }

    /**
     * search it in the db with the apartment id and protocol id
     * @param apartment
     * @param ap
     * @return
     */
    public static BasementState findByApartmentAndAP(Apartment apartment, AP ap) {
        return new Select().from(BasementState.class).where("apartment = ? and AP = ?", apartment.getId(), ap.getId()).executeSingle();
    }

    /**
     * fill in the db with initial entries
     * @param aps
     */
    public static void initializeBasement(List<AP> aps) {
        for (AP ap : aps) {
            BasementState basement = new BasementState(ap.getApartment(), ap);
            basement.setIsEmpty(false);
            basement.save();
        }
    }

    public static com.cete.dynamicpdf.pageelements.Table createPDF(BasementState basement, float pageWidth, float posY, byte[] cross) {
        com.cete.dynamicpdf.pageelements.Table table = new com.cete.dynamicpdf.pageelements.Table(0, posY, pageWidth, 0);

        table.getColumns().add(150);
        table.getColumns().add(30);
        table.getColumns().add(30);
        table.getColumns().add(50);
        table.getColumns().add(170);
        table.getColumns().add(320);

        Row header = table.getRows().add(30);
        header.setFont(Font.getHelveticaBold());
        header.setFontSize(11);
        header.setAlign(CellAlign.CENTER);
        header.setVAlign(CellVAlign.CENTER);
        header.getCellList().add("");
        header.getCellList().add("Ja");
        header.getCellList().add("Nein");
        header.getCellList().add("alter Eintrag");
        header.getCellList().add("Kommentar");
        header.getCellList().add("Foto");

        int i = 0;
        for (String s : ROW_NAMES) {
            Row row = table.getRows().add(30);
            row.setFontSize(11);
            row.setAlign(CellAlign.CENTER);
            row.setVAlign(CellVAlign.CENTER);

            row.getCellList().add(s);

            if (createCheckList(basement).get(i)) {
                row.getCellList().add(new Image(cross, 0, 0));
                row.getCellList().add("");
            } else {
                row.getCellList().add("");
                row.getCellList().add(new Image(cross, 0, 0));
            }

            if (createCheckOldList(basement).get(i)) {
                row.getCellList().add(new Image(cross, 0, 0));
            } else
                row.getCellList().add("");

            if (null != createCommentsList(basement).get(i)) {
                row.getCellList().add(createCommentsList(basement).get(i));
            } else
                row.getCellList().add("");

            if (null != createPictureList(basement).get(i)) {
                Image image = new Image(createPictureList(basement).get(i), 0, 0);
                row.getCellList().add(image);
            } else
                row.getCellList().add("");

            i++;
        }
        table.setHeight(table.getRequiredHeight());
        return table;
    }
}
