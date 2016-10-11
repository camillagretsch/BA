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
@Table(name = "BasementState")
public class BasementState extends Model implements EntryStateInterface {

    @Column(name = "isClean")
    private boolean isClean = true;

    @Column(name = "isCleanOld")
    private boolean isCleanOld = false;

    @Column(name = "cleanComment")
    private String cleanComment;

    @Column(name = "clean_picture")
    private byte[] cleanPicture;

    @Column(name = "isEmpty")
    private boolean isEmpty = true;

    @Column(name = "isEmptyOld")
    private boolean isEmptyOld = false;

    @Column(name = "empty_comment")
    private String emptyComment;

    @Column(name = "empty_picture")
    private byte[] emptyPicture;

    @Column(name = "hasNoDamage")
    private boolean hasNoDamage = true;

    @Column(name = "isDamageOld")
    private boolean isDamageOld = false;

    @Column(name = "damage_comment")
    private String damageComment;

    @Column(name = "damage_picture")
    private byte[] damagePicture;

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

    public void setCleanPicture(byte[] cleanPicture) {
        this.cleanPicture = cleanPicture;
    }

    public byte[] getCleanPicture() {
        return cleanPicture;
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

    public void setEmptyPicture(byte[] emptyPicture) {
        this.emptyPicture = emptyPicture;
    }

    public byte[] getEmptyPicture() {
        return emptyPicture;
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

    public Apartment getApartment() {
        return apartment;
    }

    public AP getAp() {
        return ap;
    }

    private List<Boolean> createCheckList(BasementState basement) {
        return new ArrayList<>(Arrays.asList(basement.isClean(), basement.isEmpty(), basement.hasNoDamage()));
    }

    private List<String> createCommentsList(BasementState basement) {
        return new ArrayList<>(Arrays.asList(basement.getCleanComment(), basement.getEmptyComment(), basement.getDamageComment()));
    }

    private List<Boolean> createCheckOldList(BasementState basement) {
        return new ArrayList<>(Arrays.asList(basement.isCleanOld(), basement.isEmptyOld(), basement.isDamageOld()));
    }

    private List<byte[]> createPictureList(BasementState basement) {
        return new ArrayList<>(Arrays.asList(basement.getCleanPicture(), basement.getEmptyPicture(), basement.getDamagePicture()));
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
            BasementState oldBasement = BasementState.findByApartmentAndAP(oldAP.getApartment(), oldAP);
            this.copyOldEntries(oldBasement);
            this.save();
        }
    }

    private void copyOldEntries(BasementState oldBasement) {
        this.setIsClean(oldBasement.isClean());
        this.setIsCleanOld(oldBasement.isCleanOld());
        this.setCleanComment(oldBasement.getCleanComment());
        this.setCleanPicture(oldBasement.getCleanPicture());
        this.setIsEmpty(oldBasement.isEmpty());
        this.setIsEmptyOld(oldBasement.isEmptyOld());
        this.setEmptyComment(oldBasement.getEmptyComment());
        this.setEmptyPicture(oldBasement.getEmptyPicture());
        this.setHasNoDamage(oldBasement.hasNoDamage());
        this.setIsDamageOld(oldBasement.isDamageOld());
        this.setDamageComment(oldBasement.getDamageComment());
        this.setDamagePicture(oldBasement.getDamagePicture());
    }

    @Override
    public void createNewEntry(AP ap) {
        if (ApartmentType.STUDIO.equals(ap.getApartment().getType())) {
            this.save();
        }
    }

    @Override
    public void saveCheckEntries(List<Boolean> check) {
        this.setIsClean(check.get(0));
        this.setIsEmpty(check.get(1));
        this.setHasNoDamage(check.get(2));
        this.save();
    }

    @Override
    public void saveCheckOldEntries(List<Boolean> checkOld) {
        this.setIsCleanOld(checkOld.get(0));
        this.setIsEmptyOld(checkOld.get(1));
        this.setIsDamageOld(checkOld.get(2));
        this.save();
    }

    @Override
    public void saveCommentsEntries(List<String> comments) {
        this.setCleanComment(comments.get(0));
        this.setEmptyComment(comments.get(1));
        this.setDamageComment(comments.get(2));
        this.save();
    }

    @Override
    public void savePicture(int pos, byte[] picture) {
        switch (pos) {
            case 0:
                this.setCleanPicture(picture);
                break;
            case 1:
                this.setEmptyPicture(picture);
                break;
            case 2:
                this.setDamagePicture(picture);
                break;
        }
        this.save();
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
