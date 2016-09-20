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
@Table(name = "BalconyState")
public class BalconyState extends Model{

    @Column(name = "isClean")
    private boolean isClean = true;

    @Column(name = "isCleanOld")
    private boolean isCleanOld = false;

    @Column(name = "cleanComment")
    private String cleanComment;

    @Column(name = "hasNoDamage")
    private boolean hasNoDamage = true;

    @Column(name = "isDamageOld")
    private boolean isDamageOld = false;

    @Column(name = "damage_comment")
    private String damageComment;

    //TODO damagePicture

    private static final List<String> ROW_NAMES = Arrays.asList("Ist besenrein?", "Ist alles intakt?");

    @Column(name = "apartment", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private Apartment apartment;

    @Column(name = "AP", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private AP ap;

    public BalconyState() {
        super();
    }

    public BalconyState(Apartment apartment, AP ap) {
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

    public static List<Boolean> createCheckList(BalconyState balcony) {
        return new ArrayList<>(Arrays.asList(balcony.isClean(), balcony.hasNoDamage()));
    }

    public static List<String> createCommentsList(BalconyState balcony) {
        return new ArrayList<>(Arrays.asList(balcony.getCleanComment(), balcony.getDamageComment()));
    }

    public static List<Boolean> createCheckOldList(BalconyState balcony) {
        return new ArrayList<>(Arrays.asList(balcony.isCleanOld(), balcony.isDamageOld()));
    }

    public static void duplicateBalconyEntries(BalconyState balcony, BalconyState oldBalcony) {
        balcony.setIsClean(oldBalcony.isClean());
        balcony.setIsCleanOld(oldBalcony.isCleanOld());
        balcony.setCleanComment(oldBalcony.getCleanComment());
        balcony.setHasNoDamage(oldBalcony.hasNoDamage());
        balcony.setIsDamageOld(oldBalcony.isDamageOld());
        balcony.setDamageComment(oldBalcony.getDamageComment());
    }

    public static BalconyState findByApartmentAndAP(Apartment apartment, AP ap) {
        return new Select().from(BalconyState.class).where("apartment = ? and AP = ?", apartment.getId(), ap.getId()).executeSingle();
    }

    public static BalconyState findById(long id) {
        return new Select().from(BalconyState.class).where("id = ?", id).executeSingle();
    }

    public static void initializeBalcony(List<AP> aps) {
        for (AP ap : aps) {
            BalconyState balcony = new BalconyState(ap.getApartment(), ap);
            balcony.setIsClean(false);
            balcony.save();
        }
    }
}
