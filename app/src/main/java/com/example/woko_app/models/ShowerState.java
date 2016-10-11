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

    @Column(name = "showerCurtain_picture")
    private byte[] showerCurtainPicture;

    @Column(name = "showerIsOK")
    private boolean showerIsOK = true;

    @Column(name = "isShowerOKOld")
    private boolean isShowerOKOld = false;

    @Column(name = "shower_comment")
    private String showerComment;

    @Column(name = "shower_picture")
    private byte[] showerPicture;

    @Column(name = "toiletIsOK")
    private boolean toiletIsOK = true;

    @Column(name = "isToiletOkOld")
    private boolean isToiletOKOld = false;

    @Column(name = "toilet_comment")
    private String toiletComment;

    @Column(name = "toilet_picture")
    private byte[] toiletPicture;

    @Column(name = "sinkIsOK")
    private boolean sinkIsOK;

    @Column(name = "isSinkOKOld")
    private boolean isSinkOKOld = false;

    @Column(name = "sink_comment")
    private String sinkComment;

    @Column(name = "sink_picture")
    private byte[] sinkPicture;

    @Column(name = "hasNoDamage")
    private boolean hasNoDamage = true;

    @Column(name = "isDamageOld")
    private boolean isDamageOld = false;

    @Column(name = "damage_comment")
    private String damageComment;

    @Column(name = "damage_picture")
    private byte[] damagePicture;

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

    public void setShowerCurtainPicture(byte[] showerCurtainPicture) {
        this.showerCurtainPicture = showerCurtainPicture;
    }

    public byte[] getShowerCurtainPicture() {
        return showerCurtainPicture;
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

    public void setShowerPicture(byte[] showerPicture) {
        this.showerPicture = showerPicture;
    }

    public byte[] getShowerPicture() {
        return showerPicture;
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

    public void setToiletPicture(byte[] toiletPicture) {
        this.toiletPicture = toiletPicture;
    }

    public byte[] getToiletPicture() {
        return toiletPicture;
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

    public void setSinkPicture(byte[] sinkPicture) {
        this.sinkPicture = sinkPicture;
    }

    public byte[] getSinkPicture() {
        return sinkPicture;
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

    public Bathroom getBathroom() {
        return bathroom;
    }

    public AP getAp() {
        return ap;
    }

    private List<Boolean> createCheckList(ShowerState shower) {
        return new ArrayList<>(Arrays.asList(shower.getShowerCurtainIsClean(), shower.getShowerIsOK(), shower.getToiletIsOK(), shower.getSinkIsOK(), shower.hasNoDamage()));
    }

    private List<String> createCommentsList(ShowerState shower) {
        return new ArrayList<>(Arrays.asList(shower.getShowerCurtainComment(), shower.getShowerComment(), shower.getToiletComment(), shower.getSinkComment(), shower.getDamageComment()));
    }

    private List<Boolean> createCheckOldList(ShowerState shower) {
        return new ArrayList<>(Arrays.asList(shower.isShowerCurtainOld(), shower.isShowerOKOld(), shower.isToiletOKOld(), shower.isSinkOKOld(), shower.isDamageOld()));
    }

    private List<byte[]> createPictureList(ShowerState shower) {
        return new ArrayList<>(Arrays.asList(shower.getShowerCurtainPicture(), shower.getShowerPicture(), shower.getToiletPicture(), shower.getSinkPicture(), shower.getDamagePicture()));
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
            ShowerState oldShower = ShowerState.findByBathroomAndAP(oldAP.getBathroom(), oldAP);
            this.copyOldEntries(oldShower);
            this.save();
        }
    }

    private void copyOldEntries(ShowerState oldShower) {
        this.setShowerCurtainIsClean(oldShower.getShowerCurtainIsClean());
        this.setIsShowerCurtainOld(oldShower.isShowerCurtainOld());
        this.setShowerCurtainComment(oldShower.getShowerCurtainComment());
        this.setShowerCurtainPicture(oldShower.getShowerCurtainPicture());
        this.setShowerIsOK(oldShower.getShowerIsOK());
        this.setIsShowerOKOld(oldShower.isShowerOKOld());
        this.setShowerComment(oldShower.getShowerComment());
        this.setShowerPicture(oldShower.getShowerPicture());
        this.setToiletIsOK(oldShower.getToiletIsOK());
        this.setIsToiletOKOld(oldShower.isToiletOKOld());
        this.setToiletComment(oldShower.getToiletComment());
        this.setToiletPicture(oldShower.getToiletPicture());
        this.setSinkIsOK(oldShower.getSinkIsOK());
        this.setIsSinkOKOld(oldShower.isSinkOKOld());
        this.setSinkComment(oldShower.getSinkComment());
        this.setSinkPicture(oldShower.getSinkPicture());
        this.setHasNoDamage(oldShower.hasNoDamage());
        this.setIsDamageOld(oldShower.isDamageOld());
        this.setDamageComment(oldShower.getDamageComment());
        this.setDamagePicture(oldShower.getDamagePicture());
    }

    @Override
    public void createNewEntry(AP ap) {
        if (ApartmentType.STUDIO.equals(ap.getApartment().getType())) {
            this.save();
        }
    }

    @Override
    public void saveCheckEntries(List<Boolean> check) {
        this.setShowerCurtainIsClean(check.get(0));
        this.setShowerIsOK(check.get(1));
        this.setToiletIsOK(check.get(2));
        this.setSinkIsOK(check.get(3));
        this.setHasNoDamage(check.get(4));
        this.save();
    }

    @Override
    public void saveCheckOldEntries(List<Boolean> checkOld) {
        this.setIsShowerCurtainOld(checkOld.get(0));
        this.setIsShowerOKOld(checkOld.get(1));
        this.setIsToiletOKOld(checkOld.get(2));
        this.setIsSinkOKOld(checkOld.get(3));
        this.setIsDamageOld(checkOld.get(4));
        this.save();
    }

    @Override
    public void saveCommentsEntries(List<String> comments) {
        this.setShowerCurtainComment(comments.get(0));
        this.setShowerComment(comments.get(1));
        this.setToiletComment(comments.get(2));
        this.setSinkComment(comments.get(3));
        this.setDamageComment(comments.get(4));
        this.save();
    }

    @Override
    public void savePicture(int pos, byte[] picture) {
        switch (pos) {
            case 0:
                this.setShowerCurtainPicture(picture);
                break;
            case 1:
                this.setShowerPicture(picture);
                break;
            case 2:
                this.setToiletPicture(picture);
                break;
            case 3:
                this.setSinkPicture(picture);
                break;
            case 4:
                this.setDamagePicture(picture);
                break;
        }
        this.save();
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
