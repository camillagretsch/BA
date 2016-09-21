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
    private String ovenComment;

    @Column(name = "cookerIsClean")
    private boolean cookerIsClean = true;

    @Column(name = "isCookerOld")
    private boolean isCookerOld = false;

    @Column(name = "cooker_comment")
    private String cookerComment;

    @Column(name = "hasNoDamage")
    private boolean hasNoDamage = true;

    @Column(name = "isDamageOld")
    private boolean isDamageOld = false;

    @Column(name = "damage_comment")
    private String damageComment;

    private static final List<String> ROW_NAMES = Arrays.asList("Backofen ist gereinigt?", "Herd ist gereinigt?", "Ist alles intakt?");

    @Column(name = "kitchen", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Kitchen kitchen;

    @Column(name = "AP", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private AP ap;

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

    public List<Boolean> createCheckList(OvenState oven) {
        return new ArrayList<>(Arrays.asList(oven.getOvenIsClean(), oven.getCookerIsClean(), oven.hasNoDamage()));
    }

    public List<String> createCommentsList(OvenState oven) {
        return new ArrayList<>(Arrays.asList(oven.getOvenComment(), oven.getCookerComment(), oven.getDamageComment()));
    }

    public List<Boolean> createCheckOldList(OvenState oven) {
        return new ArrayList<>(Arrays.asList(oven.isOvenOld(), oven.isCookerOld(), oven.isDamageOld()));
    }

    public void getEntries(DataGridFragment frag) {
        frag.setHeaderVariante1();
        frag.getRowNames().addAll(this.ROW_NAMES);
        frag.getCheck().addAll(createCheckList(this));
        frag.getCheckOld().addAll(createCheckOldList(this));
        frag.getComments().addAll(createCommentsList(this));
        frag.setTableContentVariante1();
    }

    public void duplicateEntries(AP ap, AP oldAP) {
        if (ApartmentType.STUDIO.equals(ap.getApartment().getType())) {
            OvenState oldOven = OvenState.findByKitchenAndAP(oldAP.getKitchen(), oldAP);
            this.copyOldEntries(oldOven);
            this.save();
        }
    }

    public void copyOldEntries(OvenState oldOven) {
        this.setOvenIsClean(oldOven.getOvenIsClean());
        this.setIsOvenOld(oldOven.isOvenOld());
        this.setOvenComment(oldOven.getOvenComment());
        this.setCookerIsClean(oldOven.getCookerIsClean());
        this.setIsCookerOld(oldOven.isCookerOld());
        this.setCookerComment(oldOven.getCookerComment());
        this.setHasNoDamage(oldOven.hasNoDamage());
        this.setIsDamageOld(oldOven.isDamageOld());
        this.setDamageComment(oldOven.getDamageComment());
    }

    public void createNewEntry(AP ap) {
        if (ApartmentType.STUDIO.equals(ap.getApartment().getType())) {
            this.save();
        }
    }

    public static OvenState findByKitchenAndAP(Kitchen kitchen, AP ap) {
        return new Select().from(OvenState.class).where("kitchen = ? and AP = ?", kitchen.getId(), ap.getId()).executeSingle();
    }

    public static OvenState findById(long id) {
        return new Select().from(OvenState.class).where("id = ?", id).executeSingle();
    }

    public static void initializeKitchenOven(List<AP> aps) {
        for (AP ap : aps) {
            OvenState oven = new OvenState(ap.getKitchen(), ap);
            oven.setOvenIsClean(false);
            oven.setOvenComment("Verbrannte Resten im Ofen");
            oven.save();
        }
    }
}
