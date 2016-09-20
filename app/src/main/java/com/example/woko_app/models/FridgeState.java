package com.example.woko_app.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

/**
 * Created by camillagretsch on 19.09.16.
 */
@Table(name = "FridgeState")
public class FridgeState extends Model{

    @Column(name = "hasNoFood")
    private boolean hasNoFood = true;

    @Column(name = "isFoodOld")
    private boolean isFoodOld = false;

    @Column(name = "food_comment")
    private String foodComment;

    @Column(name = "isClean")
    private boolean isClean = true;

    @Column(name = "isCleanOld")
    private boolean isCleanOld = false;

    @Column(name = "clean_comment")
    private String cleanComment;

    @Column(name = "isDefrosted")
    private boolean isDefrosted;

    @Column(name = "isDefrostedOld")
    private boolean isDefrostedOld = false;

    @Column(name = "defrosted_comment")
    private String defrostedComment;

    @Column(name = "hasNoDamage")
    private boolean hasNoDamage = true;

    @Column(name = "isDamageOld")
    private boolean isDamageOld = false;

    @Column(name = "damage_comment")
    private String damageComment;

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

    public List<String> getRowNames() {
        return ROW_NAMES;
    }

    public Kitchen getKitchen() {
        return kitchen;
    }

    public AP getAp() {
        return ap;
    }

    public static List<Boolean> createCheckList(FridgeState fridge) {
        return new ArrayList<>(Arrays.asList(fridge.hasNoFood(), fridge.isClean(), fridge.isDefrosted(), fridge.hasNoDamage()));
    }

    public static void saveCheckList(List<Boolean> check, Kitchen kitchen, AP ap) {
        FridgeState fridge = FridgeState.findByKitchenAndAP(kitchen, ap);
        fridge.setHasNoFood(check.get(0));
        fridge.setIsClean(check.get(1));
        fridge.setIsDefrosted(check.get(2));
        fridge.setHasNoDamage(check.get(3));
        fridge.save();
    }

    public static List<String> createCommentsList(FridgeState fridge) {
        return new ArrayList<>(Arrays.asList(fridge.getFoodComment(), fridge.getCleanComment(), fridge.getDefrostedComment(), fridge.getDamageComment()));
    }

    public static List<Boolean> createCheckOldList(FridgeState fridge) {
        return new ArrayList<>(Arrays.asList(fridge.isFoodOld(), fridge.isCleanOld(), fridge.isDefrostedOld(), fridge.isDamageOld()));
    }

    public static void duplicateFridgeEntries(FridgeState fridge, FridgeState oldFridge) {
        fridge.setHasNoFood(oldFridge.hasNoFood());
        fridge.setIsFoodOld(oldFridge.isFoodOld());
        fridge.setFoodComment(oldFridge.getFoodComment());
        fridge.setIsClean(oldFridge.isClean());
        fridge.setIsCleanOld(oldFridge.isCleanOld());
        fridge.setCleanComment(oldFridge.getCleanComment());
        fridge.setIsDefrosted(oldFridge.isDefrosted());
        fridge.setIsDefrostedOld(oldFridge.isDefrostedOld());
        fridge.setDefrostedComment(oldFridge.getDefrostedComment());
        fridge.setHasNoDamage(oldFridge.hasNoDamage());
        fridge.setIsDamageOld(oldFridge.isDamageOld());
        fridge.setDamageComment(oldFridge.getDamageComment());
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
