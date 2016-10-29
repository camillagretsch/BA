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
import com.example.woko_app.activity.HV_EditActivity;
import com.example.woko_app.constants.ApartmentType;
import com.example.woko_app.fragment.DataGridFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

/**
 * Created by camillagretsch on 19.09.16.
 */
@Table(name = "FridgeState")
public class FridgeState extends Model implements EntryStateInterface{

    @Column(name = "hasNoFood")
    private boolean hasNoFood = true;

    @Column(name = "isFoodOld")
    private boolean isFoodOld = false;

    @Column(name = "food_comment")
    private String foodComment = null;

    @Column(name = "food_picture")
    private byte[] foodPicture = null;

    @Column(name = "isClean")
    private boolean isClean = true;

    @Column(name = "isCleanOld")
    private boolean isCleanOld = false;

    @Column(name = "clean_comment")
    private String cleanComment = null;

    @Column(name = "clean_picture")
    private byte[] cleanPicture = null;

    @Column(name = "isDefrosted")
    private boolean isDefrosted = true;

    @Column(name = "isDefrostedOld")
    private boolean isDefrostedOld = false;

    @Column(name = "defrosted_comment")
    private String defrostedComment = null;

    @Column(name = "defrosted_picture")
    private byte[] defrostedPicture = null;

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
    private String name = "Kühlschrank, Tiefkühlfach";

    private static final List<String> ROW_NAMES = Arrays.asList("Sind alle Esswaren entsorgt?", "Starke Verschmutzungen sind gereinigt?", "Wurden abgetaut und das Wasser aufgewischt?", "Ist alles intakt?");

    public FridgeState() {
        super();
    }

    public FridgeState(Kitchen kitchen, AP ap) {
        super();
        this.kitchen = kitchen;
        this.ap = ap;
    }

    public void setHasNoFood(boolean hasNoFood) {
        this.hasNoFood = hasNoFood;
    }

    public boolean hasNoFood() {
        return hasNoFood;
    }

    public void setIsFoodOld(boolean isFoodOld) {
        this.isFoodOld = isFoodOld;
    }

    public boolean isFoodOld() {
        return isFoodOld;
    }

    public void setFoodComment(String foodComment) {
        this.foodComment = foodComment;
    }

    public String getFoodComment() {
        return foodComment;
    }

    public void setFoodPicture(byte[] foodPicture) {
        this.foodPicture = foodPicture;
    }

    public byte[] getFoodPicture() {
        return foodPicture;
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

    public void setIsDefrosted(boolean isDefrosted) {
        this.isDefrosted = isDefrosted;
    }

    public boolean isDefrosted() {
        return isDefrosted;
    }

    public void setIsDefrostedOld(boolean isDefrostedOld) {
        this.isDefrostedOld = isDefrostedOld;
    }

    public boolean isDefrostedOld() {
        return isDefrostedOld;
    }

    public void setDefrostedComment(String defrostedComment) {
        this.defrostedComment = defrostedComment;
    }

    public String getDefrostedComment() {
        return defrostedComment;
    }

    public void setDefrostedPicture(byte[] defrostedPicture) {
        this.defrostedPicture = defrostedPicture;
    }

    public byte[] getDefrostedPicture() {
        return defrostedPicture;
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
        FridgeState fridge = (FridgeState) entryStateInterface;
        AP ap = fridge.getAp();

        int counter = 0;
        int year = 0;

        while (year < 5) {

            if (null != FridgeState.findByKitchenAndAP(ap.getKitchen(), ap).getPictureAtPosition(pos)) {
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
            FridgeState oldFridge = FridgeState.findByKitchenAndAP(oldAP.getKitchen(), oldAP);
            this.copyOldEntries(oldFridge);
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
        this.setHasNoFood(Boolean.parseBoolean(check.get(0)));
        this.setIsClean(Boolean.parseBoolean(check.get(1)));
        this.setIsDefrosted(Boolean.parseBoolean(check.get(2)));
        this.setHasNoDamage(Boolean.parseBoolean(check.get(3)));
        if (check.contains("false")) {
            this.setName("Kühlschrank, Tiefkühlfach " + ex);
        } else
            this.setName("Kühlschrank, Tiefkühlfach");
        this.save();
    }

    @Override
    public void saveCheckOldEntries(List<Boolean> checkOld) {
        this.setIsFoodOld(checkOld.get(0));
        this.setIsCleanOld(checkOld.get(1));
        this.setIsDefrostedOld(checkOld.get(2));
        this.setIsDamageOld(checkOld.get(3));
        this.save();
    }

    @Override
    public void saveCommentsEntries(List<String> comments) {
        this.setFoodComment(comments.get(0));
        this.setCleanComment(comments.get(1));
        this.setDefrostedComment(comments.get(2));
        this.setDamageComment(comments.get(3));
        this.save();
    }

    @Override
    public void savePicture(int pos, byte[] picture) {
        switch (pos) {
            case 0:
                this.setFoodPicture(picture);
                break;
            case 1:
                this.setCleanPicture(picture);
                break;
            case 2:
                this.setDefrostedPicture(picture);
                break;
            case 3:
                this.setDamagePicture(picture);
                break;
        }
        this.save();
    }

    /**
     * add all columns which contain the verification of the correctness of the entries to a list
     * false when something is incorrect
     * true when something is correct
     * @param fridge
     * @return
     */
    public static List<Boolean> createCheckList(FridgeState fridge) {
        return new ArrayList<>(Arrays.asList(fridge.hasNoFood(), fridge.isClean(), fridge.isDefrosted(), fridge.hasNoDamage()));
    }

    /**
     * add all columns which contain the comment to a list
     * @param fridge
     * @return
     */
    private static List<String> createCommentsList(FridgeState fridge) {
        return new ArrayList<>(Arrays.asList(fridge.getFoodComment(), fridge.getCleanComment(), fridge.getDefrostedComment(), fridge.getDamageComment()));
    }

    /**
     * add all columns which contain the verification if the entry is old to a list
     * false when the entry is new
     * true when the entry is old
     * @param fridge
     * @return
     */
    private static List<Boolean> createCheckOldList(FridgeState fridge) {
        return new ArrayList<>(Arrays.asList(fridge.isFoodOld(), fridge.isCleanOld(), fridge.isDefrostedOld(), fridge.isDamageOld()));
    }

    /**
     * add all columns which contain the picture to a list
     * @param fridge
     * @return
     */
    private static List<byte[]> createPictureList(FridgeState fridge) {
        return new ArrayList<>(Arrays.asList(fridge.getFoodPicture(), fridge.getCleanPicture(), fridge.getDefrostedPicture(), fridge.getDamagePicture()));
    }

    /**
     * copies all columns
     * @param oldFridge
     */
    private void copyOldEntries(FridgeState oldFridge) {
        this.setHasNoFood(oldFridge.hasNoFood());
        this.setIsFoodOld(oldFridge.isFoodOld());
        this.setFoodComment(oldFridge.getFoodComment());
        this.setFoodPicture(oldFridge.getFoodPicture());
        this.setIsClean(oldFridge.isClean());
        this.setIsCleanOld(oldFridge.isCleanOld());
        this.setCleanComment(oldFridge.getCleanComment());
        this.setCleanPicture(oldFridge.getCleanPicture());
        this.setIsDefrosted(oldFridge.isDefrosted());
        this.setIsDefrostedOld(oldFridge.isDefrostedOld());
        this.setDefrostedComment(oldFridge.getDefrostedComment());
        this.setDefrostedPicture(oldFridge.getDefrostedPicture());
        this.setHasNoDamage(oldFridge.hasNoDamage());
        this.setIsDamageOld(oldFridge.isDamageOld());
        this.setDamageComment(oldFridge.getDamageComment());
        this.setDamagePicture(oldFridge.getDamagePicture());
        this.setName(oldFridge.getName());
    }

    /**
     * search it in the db with the kitchen id and protocol id
     * @param kitchen
     * @param ap
     * @return
     */
    public static FridgeState findByKitchenAndAP(Kitchen kitchen, AP ap) {
        return new Select().from(FridgeState.class).where("kitchen = ? and AP = ?", kitchen.getId(), ap.getId()).executeSingle();
    }

    /**
     * fill in the db with initial entries
     * @param aps
     * @param ex
     */
    public static void initializeKitchenFridge(List<AP> aps, String ex) {
        for (AP ap : aps) {
            FridgeState fridge = new FridgeState(ap.getKitchen(), ap);
            fridge.setHasNoFood(true);
            fridge.setIsClean(true);
            fridge.setIsDefrosted(false);
            fridge.setHasNoDamage(true);
            if (createCheckList(fridge).contains(false)) {
                fridge.setName(fridge.getName() + " " + ex);
            }
            fridge.save();
        }
    }

    public static Table2 createPDF(FridgeState fridge, float posX, float posY, float pageWidth, byte[] cross) {
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

            if (createCheckList(fridge).get(i)) {
                row.getCells().add(new Image(cross, 0, 0));
                row.getCells().add("");
            } else {
                row.getCells().add("");
                row.getCells().add(new Image(cross, 0, 0));
            }

            if (createCheckOldList(fridge).get(i)) {
                row.getCells().add(new Image(cross, 0, 0));
            } else
                row.getCells().add("");

            if (null != createCommentsList(fridge).get(i)) {
                row.getCells().add(createCommentsList(fridge).get(i));
            } else
                row.getCells().add("");

            if (null != createPictureList(fridge).get(i)) {
                row.getCells().add(new Image(createPictureList(fridge).get(i), 0, 0));
            } else
                row.getCells().add("");

            i++;
        }
        return table;
    }
}
