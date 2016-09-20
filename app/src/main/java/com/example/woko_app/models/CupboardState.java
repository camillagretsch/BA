package com.example.woko_app.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by camillagretsch on 19.09.16.
 */
@Table(name = "CupboardState")
public class CupboardState extends Model{

    @Column(name = "isClean")
    private boolean isClean = true;

    @Column(name = "isCleanOld")
    private boolean isCleanOld = false;

    @Column(name = "clean_comment")
    private String cleanComment;

    @Column(name = "hasNoDamage")
    private boolean hasNoDamage = true;

    @Column(name = "isDamageOld")
    private boolean isDamageOld = false;

    @Column(name = "damage_comment")
    private String damageComment;

    private static final List<String> ROW_NAMES = Arrays.asList("Sind gereinigt?", "Ist alles intakt?");

    @Column(name = "kitchen", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Kitchen kitchen;

    @Column(name = "AP", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private AP ap;

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

    public static List<Boolean> createCheckList(CupboardState cupboard) {
        return new ArrayList<>(Arrays.asList(cupboard.isClean(), cupboard.hasNoDamage()));
    }

    public static List<String> createCommentsList(CupboardState cupboard) {
        return new ArrayList<>(Arrays.asList(cupboard.getCleanComment(), cupboard.getDamageComment()));
    }

    public static List<Boolean> createCheckOldList(CupboardState cupboard) {
        return new ArrayList<>(Arrays.asList(cupboard.isCleanOld(), cupboard.isDamageOld()));
    }

    public static void duplicateCupboardEntries(CupboardState cupboard, CupboardState oldCupboard) {
        cupboard.setIsClean(oldCupboard.isClean());
        cupboard.setIsCleanOld(oldCupboard.isCleanOld());
        cupboard.setCleanComment(oldCupboard.getCleanComment());
        cupboard.setHasNoDamage(oldCupboard.hasNoDamage());
        cupboard.setIsDamageOld(oldCupboard.isDamageOld());
        cupboard.setDamageComment(oldCupboard.getDamageComment());
    }

    public static CupboardState findByKitchenAndAP(Kitchen kitchen, AP ap) {
        return new Select().from(CupboardState.class).where("kitchen = ? and AP = ?", kitchen.getId(), ap.getId()).executeSingle();
    }

    public static CupboardState findById(long id) {
        return new Select().from(CupboardState.class).where("id = ?", id).executeSingle();
    }

    public static void initializeKitchenCupboard(List<AP> aps) {
        for (AP ap : aps) {
            CupboardState cupboard = new CupboardState(ap.getKitchen(), ap);
            cupboard.save();
        }
    }
}
