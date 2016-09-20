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
@Table(name = "BasementState")
public class BasementState extends Model{

    @Column(name = "isClean")
    private boolean isClean = true;

    @Column(name = "isCleanOld")
    private boolean isCleanOld = false;

    @Column(name = "cleanComment")
    private String cleanComment;

    @Column(name = "isEmpty")
    private boolean isEmpty = true;

    @Column(name = "isEmptyOld")
    private boolean isEmptyOld = false;

    @Column(name = "empty_comment")
    private String emptyComment;

    @Column(name = "hasNoDamage")
    private boolean hasNoDamage = true;

    @Column(name = "isDamageOld")
    private boolean isDamageOld = false;

    @Column(name = "damage_comment")
    private String damageComment;

    //TODO damagePicture

    private static final List<String> ROW_NAMES = Arrays.asList("Ist besenrein?", "Der Keller ist geräumt", "Ist alles intakt?");

    @Column(name = "apartment", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private Apartment apartment;

    @Column(name = "AP", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private AP ap;

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

    public Apartment getApartment() {
        return apartment;
    }

    public AP getAp() {
        return ap;
    }

    public static List<Boolean> createCheckList(BasementState basement) {
        return new ArrayList<>(Arrays.asList(basement.isClean(), basement.isEmpty(), basement.hasNoDamage()));
    }

    public static List<String> createCommentsList(BasementState basement) {
        return new ArrayList<>(Arrays.asList(basement.getCleanComment(), basement.getEmptyComment(), basement.getDamageComment()));
    }

    public static List<Boolean> createCheckOldList(BasementState basement) {
        return new ArrayList<>(Arrays.asList(basement.isCleanOld(), basement.isEmptyOld(), basement.isDamageOld()));
    }
    public static void duplicateBasementEntries(BasementState basement, BasementState oldBasement) {
        basement.setIsClean(oldBasement.isClean());
        basement.setIsCleanOld(oldBasement.isCleanOld());
        basement.setCleanComment(oldBasement.getCleanComment());
        basement.setIsEmpty(oldBasement.isEmpty());
        basement.setIsEmptyOld(oldBasement.isEmptyOld());
        basement.setEmptyComment(oldBasement.getEmptyComment());
        basement.setHasNoDamage(oldBasement.hasNoDamage());
        basement.setIsDamageOld(oldBasement.isDamageOld());
        basement.setDamageComment(oldBasement.getDamageComment());
    }

    public static BasementState findByApartmentAndAP(Apartment apartment, AP ap) {
        return new Select().from(BasementState.class).where("apartment = ? and AP = ?", apartment.getId(), ap.getId()).executeSingle();
    }

    public static BasementState findById(long id) {
        return new Select().from(BasementState.class).where("id = ?", id).executeSingle();
    }

    public static void initializeBasement(List<AP> aps) {
        for (AP ap : aps) {
            BasementState basement = new BasementState(ap.getApartment(), ap);
            basement.setIsEmpty(false);
            basement.save();
        }
    }
}
