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
@Table(name = "VentilationState")
public class VentilationState extends Model implements EntryStateInterface {

    @Column(name = "isClean")
    private boolean isClean = true;

    @Column(name = "isCleanOld")
    private boolean isCleanOld = false;

    @Column(name = "clean_comment")
    private String cleanComment = null;

    @Column(name = "clean_picture")
    private byte[] cleanPicture = null;

    @Column(name = "isWorking")
    private boolean isWorking = true;

    @Column(name = "isWorkingOld")
    private boolean isWorkingOld = false;

    @Column(name = "working_comment")
    private String workingComment = null;

    @Column(name = "working_picture")
    private byte[] workingPicture = null;

    @Column(name = "lampIsWorking")
    private boolean lampIsWorking = true;

    @Column(name = "isLampWorkingOld")
    private boolean isLampWorkingOld = false;

    @Column(name = "lamp_comment")
    private String lampComment = null;

    @Column(name = "lamp_picture")
    private byte[] lampPicture = null;

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
    private String name = "Dampfabzug";

    private static final List<String> ROW_NAMES = Arrays.asList("Ist gereinigt?", "Funktioniert?", "Lämpchen sind intakt?", "Ist alles intakt?");

    public VentilationState() {
        super();
    }

    public VentilationState(Kitchen kitchen, AP ap) {
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

    public void setIsWorking(boolean isWorking) {
        this.isWorking = isWorking;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setIsWorkingOld(boolean isWorkingOld) {
        this.isWorkingOld = isWorkingOld;
    }

    public boolean isWorkingOld() {
        return isWorkingOld;
    }

    public void setWorkingComment(String workingComment) {
        this.workingComment = workingComment;
    }

    public String getWorkingComment() {
        return workingComment;
    }

    public void setWorkingPicture(byte[] workingPicture) {
        this.workingPicture = workingPicture;
    }

    public byte[] getWorkingPicture() {
        return workingPicture;
    }

    public void setLampIsWorking(boolean lampIsWorking) {
        this.lampIsWorking = lampIsWorking;
    }

    public boolean getLampIsWorking() {
        return lampIsWorking;
    }

    public void setIsLampWorkingOld(boolean isLampWorkingOld) {
        this.isLampWorkingOld = isLampWorkingOld;
    }

    public boolean isLampWorkingOld() {
        return isLampWorkingOld;
    }

    public void setLampComment(String lampComment) {
        this.lampComment = lampComment;
    }

    public String getLampComment() {
        return lampComment;
    }

    public void setLampPicture(byte[] lampPicture) {
        this.lampPicture = lampPicture;
    }

    public byte[] getLampPicture() {
        return lampPicture;
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
        VentilationState ventilation = (VentilationState) entryStateInterface;
        AP ap = ventilation.getAp();

        int counter = 0;
        int year = 0;

        while (year < 5) {

            if (null != VentilationState.findByKitchenAndAP(ap.getKitchen(), ap).getPictureAtPosition(pos)) {
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
            VentilationState oldVentilation = VentilationState.findByKitchenAndAP(oldAP.getKitchen(), oldAP);
            this.copyOldEntries(oldVentilation);
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
        this.setIsWorking(Boolean.parseBoolean(check.get(1)));
        this.setLampIsWorking(Boolean.parseBoolean(check.get(2)));
        this.setHasNoDamage(Boolean.parseBoolean(check.get(3)));
        if (check.contains("false")) {
            this.setName("Dampfabzug " + ex);
        } else
            this.setName("Dampfabzug");
        this.save();
    }

    @Override
    public void saveCheckOldEntries(List<Boolean> checkOld) {
        this.setIsCleanOld(checkOld.get(0));
        this.setIsWorkingOld(checkOld.get(1));
        this.setIsLampWorkingOld(checkOld.get(2));
        this.setIsDamageOld(checkOld.get(3));
        this.save();
    }

    @Override
    public void saveCommentsEntries(List<String> comments) {
        this.setCleanComment(comments.get(0));
        this.setWorkingComment(comments.get(1));
        this.setLampComment(comments.get(2));
        this.setDamageComment(comments.get(3));
        this.save();
    }

    @Override
    public void savePicture(int pos, byte[] picture) {
        switch (pos) {
            case 0:
                this.setCleanPicture(picture);
                break;
            case 1:
                this.setWorkingPicture(picture);
                break;
            case 2:
                this.setLampPicture(picture);
                break;
            case 3:
                this.setDamagePicture(picture);
        }
        this.save();
    }

    /**
     * add all columns which contain the verification of the correctness of the entries to a list
     * false when something is incorrect
     * true when something is correct
     * @param ventilation
     * @return
     */
    private static List<Boolean> createCheckList(VentilationState ventilation) {
        return new ArrayList<>(Arrays.asList(ventilation.isClean(), ventilation.isWorking(), ventilation.getLampIsWorking(), ventilation.hasNoDamage()));
    }

    /**
     * add all columns which contain the comment to a list
     * @param ventilation
     * @return
     */
    private static List<String> createCommentsList(VentilationState ventilation) {
        return new ArrayList<>(Arrays.asList(ventilation.getCleanComment(), ventilation.getWorkingComment(), ventilation.getLampComment(), ventilation.getDamageComment()));
    }

    /**
     * add all columns which contain the verification if the entry is old to a list
     * false when the entry is new
     * true when the entry is old
     * @param ventilation
     * @return
     */
    private static List<Boolean> createCheckOldList(VentilationState ventilation) {
        return new ArrayList<>(Arrays.asList(ventilation.isCleanOld(), ventilation.isWorkingOld(), ventilation.isLampWorkingOld(), ventilation.isDamageOld()));
    }

    /**
     * add all columns which contain the picture to a list
     * @param ventilation
     * @return
     */
    private static List<byte[]> createPictureList(VentilationState ventilation) {
        return new ArrayList<>(Arrays.asList(ventilation.getCleanPicture(), ventilation.getWorkingPicture(), ventilation.getLampPicture(), ventilation.getDamagePicture()));
    }

    /**
     * copies all columns
     * @param oldVentilation
     */
    private void copyOldEntries(VentilationState oldVentilation) {
        this.setIsClean(oldVentilation.isClean());
        this.setIsCleanOld(oldVentilation.isCleanOld());
        this.setCleanComment(oldVentilation.getCleanComment());
        this.setCleanPicture(oldVentilation.getCleanPicture());
        this.setIsWorking(oldVentilation.isWorking());
        this.setIsWorkingOld(oldVentilation.isWorkingOld());
        this.setWorkingComment(oldVentilation.getWorkingComment());
        this.setWorkingPicture(oldVentilation.getWorkingPicture());
        this.setLampIsWorking(oldVentilation.getLampIsWorking());
        this.setIsLampWorkingOld(oldVentilation.isLampWorkingOld());
        this.setLampComment(oldVentilation.getLampComment());
        this.setLampPicture(oldVentilation.getLampPicture());
        this.setHasNoDamage(oldVentilation.hasNoDamage());
        this.setIsDamageOld(oldVentilation.isDamageOld());
        this.setDamageComment(oldVentilation.getDamageComment());
        this.setDamagePicture(oldVentilation.getDamagePicture());
        this.setName(oldVentilation.getName());
    }

    /**
     * search it in the db with the kitchen id and protocol id
     * @param kitchen
     * @param ap
     * @return
     */
    public static VentilationState findByKitchenAndAP(Kitchen kitchen, AP ap) {
        return new Select().from(VentilationState.class).where("kitchen = ? and AP = ?", kitchen.getId(), ap.getId()).executeSingle();
    }

    /**
     * fill in the db with initial entries
     * @param aps
     * @param image
     * @param ex
     */
    public static void initializeKitchenVentilation(List<AP> aps, byte[] image, String ex) {
        for (AP ap : aps) {
            VentilationState ventilation = new VentilationState(ap.getKitchen(), ap);
            ventilation.setHasNoDamage(false);
            ventilation.setDamageComment("Verdeckung ist abgebrochen");
            ventilation.setName(ventilation.getName() + " " + ex);
            ventilation.setDamagePicture(image);
            ventilation.save();
        }
    }

    public static com.cete.dynamicpdf.pageelements.Table createPDF(VentilationState ventilation, float pageWidth, float posY, byte[] cross) {
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

            if (createCheckList(ventilation).get(i)) {
                row.getCellList().add(new Image(cross, 0, 0));
                row.getCellList().add("");
            } else {
                row.getCellList().add("");
                row.getCellList().add(new Image(cross, 0, 0));
            }

            if (createCheckOldList(ventilation).get(i)) {
                row.getCellList().add(new Image(cross, 0, 0));
            } else
                row.getCellList().add("");

            if (null != createCommentsList(ventilation).get(i)) {
                row.getCellList().add(createCommentsList(ventilation).get(i));
            } else
                row.getCellList().add("");

            if (null != createPictureList(ventilation).get(i)) {
                Image image = new Image(createPictureList(ventilation).get(i), 0, 0);
                row.getCellList().add(image);
            } else
                row.getCellList().add("");

            i++;
        }
        table.setHeight(table.getRequiredHeight());
        return table;
    }
}
