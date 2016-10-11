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
    private String foodComment;

    @Column(name = "food_picture")
    private byte[] foodPicture;

    @Column(name = "isClean")
    private boolean isClean = true;

    @Column(name = "isCleanOld")
    private boolean isCleanOld = false;

    @Column(name = "clean_comment")
    private String cleanComment;

    @Column(name = "clean_picture")
    private byte[] cleanPicture;

    @Column(name = "isDefrosted")
    private boolean isDefrosted;

    @Column(name = "isDefrostedOld")
    private boolean isDefrostedOld = false;

    @Column(name = "defrosted_comment")
    private String defrostedComment;

    @Column(name = "defrosted_picture")
    private byte[] defrostedPicture;

    @Column(name = "hasNoDamage")
    private boolean hasNoDamage = true;

    @Column(name = "isDamageOld")
    private boolean isDamageOld = false;

    @Column(name = "damage_comment")
    private String damageComment;

    @Column(name = "damage_picture")
    private byte[] damagePicture;

    private static final List<String> ROW_NAMES = Arrays.asList("Sind alle Esswaren entsorgt?", "Starke Verschmutzungen sind gereinigt?", "Wurden abgetaut und das Wasser aufgewischt?", "Ist alles intakt?");

    @Column(name = "kitchen", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Kitchen kitchen;

    @Column(name = "AP", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private AP ap;

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

    public List<String> getRowNames() {
        return ROW_NAMES;
    }

    public Kitchen getKitchen() {
        return kitchen;
    }

    public AP getAp() {
        return ap;
    }

    private List<Boolean> createCheckList(FridgeState fridge) {
        return new ArrayList<>(Arrays.asList(fridge.hasNoFood(), fridge.isClean(), fridge.isDefrosted(), fridge.hasNoDamage()));
    }

    private List<String> createCommentsList(FridgeState fridge) {
        return new ArrayList<>(Arrays.asList(fridge.getFoodComment(), fridge.getCleanComment(), fridge.getDefrostedComment(), fridge.getDamageComment()));
    }

    private List<Boolean> createCheckOldList(FridgeState fridge) {
        return new ArrayList<>(Arrays.asList(fridge.isFoodOld(), fridge.isCleanOld(), fridge.isDefrostedOld(), fridge.isDamageOld()));
    }

    private List<byte[]> createPictureList(FridgeState fridge) {
        return new ArrayList<>(Arrays.asList(fridge.getFoodPicture(), fridge.getCleanPicture(), fridge.getDefrostedPicture(), fridge.getDamagePicture()));
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
        frag.setHeaderVariante1();
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
    }

    @Override
    public void createNewEntry(AP ap) {
        if (ApartmentType.STUDIO.equals(ap.getApartment().getType())) {
            this.save();
        }
    }

    @Override
    public void saveCheckEntries(List<Boolean> check) {
        this.setHasNoFood(check.get(0));
        this.setIsClean(check.get(1));
        this.setIsDefrosted(check.get(2));
        this.setHasNoDamage(check.get(3));
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

    public static FridgeState findByKitchenAndAP(Kitchen kitchen, AP ap) {
        return new Select().from(FridgeState.class).where("kitchen = ? and AP = ?", kitchen.getId(), ap.getId()).executeSingle();
    }

    public static FridgeState findById(long id) {
        return new Select().from(FridgeState.class).where("id = ?", id).executeSingle();
    }

    public static void initializeKitchenFridge(List<AP> aps) {
        for (AP ap : aps) {
            FridgeState fridge = new FridgeState(ap.getKitchen(), ap);
            fridge.setHasNoFood(true);
            fridge.setIsClean(true);
            fridge.setIsDefrosted(false);
            fridge.setHasNoDamage(true);
            fridge.save();
        }
    }
}
