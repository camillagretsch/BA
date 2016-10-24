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
@Table(name = "OvenState")
public class OvenState extends Model implements EntryStateInterface {

    @Column(name = "ovenIsClean")
    private boolean ovenIsClean = true;

    @Column(name = "isOvenOld")
    private boolean isOvenOld = false;

    @Column(name = "oven_comment")
    private String ovenComment = null;

    @Column(name = "oven_picture")
    private byte[] ovenPicture = null;

    @Column(name = "cookerIsClean")
    private boolean cookerIsClean = true;

    @Column(name = "isCookerOld")
    private boolean isCookerOld = false;

    @Column(name = "cooker_comment")
    private String cookerComment = null;

    @Column(name = "cooker_picture")
    private byte[] cookerPicture = null;

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
    private String name = "Herplatte, Backofen";

    private static final List<String> ROW_NAMES = Arrays.asList("Backofen ist gereinigt?", "Herd ist gereinigt?", "Ist alles intakt?");

    public OvenState() {
        super();
    }

    public OvenState(Kitchen kitchen, AP ap) {
        super();
        this.kitchen = kitchen;
        this.ap = ap;
    }

    public void setOvenIsClean(boolean ovenIsClean) {
        this.ovenIsClean = ovenIsClean;
    }

    public boolean getOvenIsClean() {
        return ovenIsClean;
    }

    public void setIsOvenOld(boolean isOvenOld) {
        this.isOvenOld = isOvenOld;
    }

    public boolean isOvenOld() {
        return isOvenOld;
    }

    public void setOvenComment(String ovenComment) {
        this.ovenComment = ovenComment;
    }

    public String getOvenComment() {
        return ovenComment;
    }

    public void setOvenPicture(byte[] ovenPicture) {
        this.ovenPicture = ovenPicture;
    }

    public byte[] getOvenPicture() {
        return ovenPicture;
    }

    public void setCookerIsClean(boolean cookerIsClean) {
        this.cookerIsClean = cookerIsClean;
    }

    public boolean getCookerIsClean() {
        return cookerIsClean;
    }

    public void setIsCookerOld(boolean isCookerOld) {
        this.isCookerOld = isCookerOld;
    }

    public boolean isCookerOld() {
        return isCookerOld;
    }

    public void setCookerComment(String cookerComment) {
        this.cookerComment = cookerComment;
    }

    public String getCookerComment() {
        return cookerComment;
    }

    public void setCookerPicture(byte[] cookerPicture) {
        this.cookerPicture = cookerPicture;
    }

    public byte[] getCookerPicture() {
        return cookerPicture;
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
        OvenState oven = (OvenState) entryStateInterface;
        AP ap = oven.getAp();

        int counter = 0;
        int year = 0;

        while (year < 5) {

            if (null != OvenState.findByKitchenAndAP(ap.getKitchen(), ap).getPictureAtPosition(pos)) {
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
            OvenState oldOven = OvenState.findByKitchenAndAP(oldAP.getKitchen(), oldAP);
            this.copyOldEntries(oldOven);
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
        this.setOvenIsClean(Boolean.parseBoolean(check.get(0)));
        this.setCookerIsClean(Boolean.parseBoolean(check.get(1)));
        this.setHasNoDamage(Boolean.parseBoolean(check.get(2)));
        if (check.contains("false")) {
            this.setName("Herplatte, Backofen " + ex);
        } else
            this.setName("Herplatte, Backofen");
        this.save();
    }

    @Override
    public void saveCheckOldEntries(List<Boolean> checkOld) {
        this.setIsOvenOld(checkOld.get(0));
        this.setIsCookerOld(checkOld.get(1));
        this.setIsDamageOld(checkOld.get(2));
        this.save();
    }

    @Override
    public void saveCommentsEntries(List<String> comments) {
        this.setOvenComment(comments.get(0));
        this.setCookerComment(comments.get(1));
        this.setDamageComment(comments.get(2));
        this.save();
    }

    @Override
    public void savePicture(int pos, byte[] picture) {
        switch (pos) {
            case 0:
                this.setOvenPicture(picture);
                break;
            case 1:
                this.setCookerPicture(picture);
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
     * @param oven
     * @return
     */
    private static List<Boolean> createCheckList(OvenState oven) {
        return new ArrayList<>(Arrays.asList(oven.getOvenIsClean(), oven.getCookerIsClean(), oven.hasNoDamage()));
    }

    /**
     * add all columns which contain the comment to a list
     * @param oven
     * @return
     */
    private static List<String> createCommentsList(OvenState oven) {
        return new ArrayList<>(Arrays.asList(oven.getOvenComment(), oven.getCookerComment(), oven.getDamageComment()));
    }

    /**
     * add all columns which contain the verification if the entry is old to a list
     * false when the entry is new
     * true when the entry is old
     * @param oven
     * @return
     */
    private static List<Boolean> createCheckOldList(OvenState oven) {
        return new ArrayList<>(Arrays.asList(oven.isOvenOld(), oven.isCookerOld(), oven.isDamageOld()));
    }

    /**
     * add all columns which contain the picture to a list
     * @param oven
     * @return
     */
    private static List<byte[]> createPictureList(OvenState oven) {
        return new ArrayList<>(Arrays.asList(oven.getOvenPicture(), oven.getCookerPicture(), oven.getDamagePicture()));
    }

    /**
     * copies all columns
     * @param oldOven
     */
    private void copyOldEntries(OvenState oldOven) {
        this.setOvenIsClean(oldOven.getOvenIsClean());
        this.setIsOvenOld(oldOven.isOvenOld());
        this.setOvenComment(oldOven.getOvenComment());
        this.setOvenPicture(oldOven.getOvenPicture());
        this.setCookerIsClean(oldOven.getCookerIsClean());
        this.setIsCookerOld(oldOven.isCookerOld());
        this.setCookerComment(oldOven.getCookerComment());
        this.setCookerPicture(oldOven.getCookerPicture());
        this.setHasNoDamage(oldOven.hasNoDamage());
        this.setIsDamageOld(oldOven.isDamageOld());
        this.setDamageComment(oldOven.getDamageComment());
        this.setDamagePicture(oldOven.getDamagePicture());
        this.setName(oldOven.getName());
    }

    /**
     * search it in the db with the kitchen id and protocol id
     * @param kitchen
     * @param ap
     * @return
     */
    public static OvenState findByKitchenAndAP(Kitchen kitchen, AP ap) {
        return new Select().from(OvenState.class).where("kitchen = ? and AP = ?", kitchen.getId(), ap.getId()).executeSingle();
    }

    /**
     * fill in the db with initial entries
     * @param aps
     * @param ex
     */
    public static void initializeKitchenOven(List<AP> aps, String ex) {
        for (AP ap : aps) {
            OvenState oven = new OvenState(ap.getKitchen(), ap);
            oven.setOvenIsClean(false);
            oven.setOvenComment("Verbrannte Resten im Ofen");
            oven.setName(oven.getName() + " " + ex);
            oven.save();
        }
    }

    public static com.cete.dynamicpdf.pageelements.Table createPDF(OvenState oven, float pageWidth, float posY, byte[] cross) {
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

            if (createCheckList(oven).get(i)) {
                row.getCellList().add(new Image(cross, 0, 0));
                row.getCellList().add("");
            } else {
                row.getCellList().add("");
                row.getCellList().add(new Image(cross, 0, 0));
            }

            if (createCheckOldList(oven).get(i)) {
                row.getCellList().add(new Image(cross, 0, 0));
            } else
                row.getCellList().add("");

            if (null != createCommentsList(oven).get(i)) {
                row.getCellList().add(createCommentsList(oven).get(i));
            } else
                row.getCellList().add("");

            if (null != createPictureList(oven).get(i)) {
                Image image = new Image(createPictureList(oven).get(i), 0, 0);
                row.getCellList().add(image);
            } else
                row.getCellList().add("");

            i++;
        }
        table.setHeight(table.getRequiredHeight());
        return table;
    }
}
