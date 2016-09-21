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
@Table(name = "ShowerState")
public class ShowerState extends Model implements EntryStateInterface {

    @Column(name = "showerCurtainIsClean")
    private boolean showerCurtainIsClean = true;

    @Column(name = "isShowerCurtainOld")
    private boolean isShowerCurtainOld = false;

    @Column(name = "showerCurtain_comment")
    private String showerCurtainComment;

    @Column(name = "showerIsOK")
    private boolean showerIsOK = true;

    @Column(name = "isShowerOKOld")
    private boolean isShowerOKOld = false;

    @Column(name = "shower_comment")
    private String showerComment;

    @Column(name = "toiletIsOK")
    private boolean toiletIsOK = true;

    @Column(name = "isToiletOkOld")
    private boolean isToiletOKOld = false;

    @Column(name = "toilet_comment")
    private String toiletComment;

    @Column(name = "sinkIsOK")
    private boolean sinkIsOK;

    @Column(name = "isSinkOKOld")
    private boolean isSinkOKOld = false;

    @Column(name = "sink_comment")
    private String sinkComment;

    @Column(name = "hasNoDamage")
    private boolean hasNoDamage = true;

    @Column(name = "isDamageOld")
    private boolean isDamageOld = false;

    @Column(name = "damage_comment")
    private String damageComment;

    private static final List<String> ROW_NAMES = Arrays.asList("Duschvorhang wurde gewaschen & aufgehängt", "Dusche ist in Ordnung", "WC ist in Ordnung", "Lavabo ist in Ordnung", "Ist alles intakt?");

    @Column(name = "bathroom", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Bathroom bathroom;

    @Column(name = "AP", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private AP ap;

    public ShowerState() {
        super();
    }

    public ShowerState(Bathroom bathroom, AP ap) {
        super();
        this.bathroom = bathroom;
        this.ap = ap;
    }

    public void setShowerCurtainIsClean(boolean showerCurtainIsClean) {
        this.showerCurtainIsClean = showerCurtainIsClean;
    }

    public boolean getShowerCurtainIsClean() {
        return showerCurtainIsClean;
    }

    public void setIsShowerCurtainOld(boolean isShowerCurtainOld) {
        this.isShowerCurtainOld = isShowerCurtainOld;
    }

    public boolean isShowerCurtainOld() {
        return isShowerCurtainOld;
    }

    public void setShowerCurtainComment(String showerCurtainComment) {
        this.showerCurtainComment = showerCurtainComment;
    }

    public String getShowerCurtainComment() {
        return showerCurtainComment;
    }

    public void setShowerIsOK(boolean showerIsOK) {
        this.showerIsOK = showerIsOK;
    }

    public boolean getShowerIsOK() {
        return showerIsOK;
    }

    public void setIsShowerOKOld(boolean isShowerOKOld) {
        this.isShowerOKOld = isShowerOKOld;
    }

    public boolean isShowerOKOld() {
        return isShowerOKOld;
    }

    public void setShowerComment(String showerComment) {
        this.showerComment = showerComment;
    }

    public String getShowerComment() {
        return showerComment;
    }

    public void setToiletIsOK(boolean toiletIsOK) {
        this.toiletIsOK = toiletIsOK;
    }

    public boolean getToiletIsOK() {
        return toiletIsOK;
    }

    public void setIsToiletOKOld(boolean isToiletOKOld) {
        this.isToiletOKOld = isToiletOKOld;
    }

    public boolean isToiletOKOld() {
        return isToiletOKOld;
    }

    public void setToiletComment(String toiletComment) {
        this.toiletComment = toiletComment;
    }

    public String getToiletComment() {
        return toiletComment;
    }

    public void setSinkIsOK(boolean sinkIsOK) {
        this.sinkIsOK = sinkIsOK;
    }

    public boolean getSinkIsOK() {
        return sinkIsOK;
    }

    public void setIsSinkOKOld(boolean isSinkOKOld) {
        this.isSinkOKOld = isSinkOKOld;
    }

    public boolean isSinkOKOld() {
        return isSinkOKOld;
    }

    public void setSinkComment(String sinkComment) {
        this.sinkComment = sinkComment;
    }

    public String getSinkComment() {
        return sinkComment;
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

    public Bathroom getBathroom() {
        return bathroom;
    }

    public AP getAp() {
        return ap;
    }

    public List<Boolean> createCheckList(ShowerState shower) {
        return new ArrayList<>(Arrays.asList(shower.getShowerCurtainIsClean(), shower.getShowerIsOK(), shower.getToiletIsOK(), shower.getSinkIsOK(), shower.hasNoDamage()));
    }

    public List<String> createCommentsList(ShowerState shower) {
        return new ArrayList<>(Arrays.asList(shower.getShowerCurtainComment(), shower.getShowerComment(), shower.getToiletComment(), shower.getSinkComment(), shower.getDamageComment()));
    }

    public List<Boolean> createCheckOldList(ShowerState shower) {
        return new ArrayList<>(Arrays.asList(shower.isShowerCurtainOld(), shower.isShowerOKOld(), shower.isToiletOKOld(), shower.isSinkOKOld(), shower.isDamageOld()));
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
            ShowerState oldShower = ShowerState.findByBathroomAndAP(oldAP.getBathroom(), oldAP);
            this.copyOldEntries(oldShower);
            this.save();
        }
    }

    public void copyOldEntries(ShowerState oldShower) {
        this.setShowerCurtainIsClean(oldShower.getShowerCurtainIsClean());
        this.setIsShowerCurtainOld(oldShower.isShowerCurtainOld());
        this.setShowerCurtainComment(oldShower.getShowerCurtainComment());
        this.setShowerIsOK(oldShower.getShowerIsOK());
        this.setIsShowerOKOld(oldShower.isShowerOKOld());
        this.setShowerComment(oldShower.getShowerComment());
        this.setToiletIsOK(oldShower.getToiletIsOK());
        this.setIsToiletOKOld(oldShower.isToiletOKOld());
        this.setToiletComment(oldShower.getToiletComment());
        this.setSinkIsOK(oldShower.getSinkIsOK());
        this.setIsSinkOKOld(oldShower.isSinkOKOld());
        this.setSinkComment(oldShower.getSinkComment());
        this.setHasNoDamage(oldShower.hasNoDamage());
        this.setIsDamageOld(oldShower.isDamageOld());
        this.setDamageComment(oldShower.getDamageComment());
    }

    public void createNewEntry(AP ap) {
        if (ApartmentType.STUDIO.equals(ap.getApartment().getType())) {
            this.save();
        }
    }

    public static ShowerState findByBathroomAndAP(Bathroom bathroom, AP ap) {
        return new Select().from(ShowerState.class).where("bathroom = ? and AP = ?", bathroom.getId(), ap.getId()).executeSingle();
    }

    public static ShowerState findById(long id) {
        return new Select().from(ShowerState.class).where("id = ?", id).executeSingle();
    }

    public static void initializeBathroomShower(List<AP> aps) {
        for (AP ap : aps) {
            ShowerState shower = new ShowerState(ap.getBathroom(), ap);
            shower.setSinkIsOK(false);
            shower.setSinkComment("Risse im Lavabo");
            shower.setToiletIsOK(false);
            shower.setToiletComment("WC Deckel hat einen Sprung");
            shower.save();
        }
    }
}
