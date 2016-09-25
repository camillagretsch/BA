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
@Table(name = "CupboardState")
public class CupboardState extends Model implements EntryStateInterface{

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

    public List<Boolean> createCheckList(CupboardState cupboard) {
        return new ArrayList<>(Arrays.asList(cupboard.isClean(), cupboard.hasNoDamage()));
    }

    public List<String> createCommentsList(CupboardState cupboard) {
        return new ArrayList<>(Arrays.asList(cupboard.getCleanComment(), cupboard.getDamageComment()));
    }

    public List<Boolean> createCheckOldList(CupboardState cupboard) {
        return new ArrayList<>(Arrays.asList(cupboard.isCleanOld(), cupboard.isDamageOld()));
    }

    @Override
    public void getEntries(DataGridFragment frag) {
        frag.setHeaderVariante1();
        frag.getRowNames().addAll(this.ROW_NAMES);
        frag.getCheck().addAll(createCheckList(this));
        frag.getCheckOld().addAll(createCheckOldList(this));
        frag.getComments().addAll(createCommentsList(this));
        frag.setTableContentVariante1();
    }

    @Override
    public void duplicateEntries(AP ap, AP oldAP) {
        if (ApartmentType.STUDIO.equals(ap.getApartment().getType())) {
            CupboardState oldCupboard = CupboardState.findByKitchenAndAP(oldAP.getKitchen(), oldAP);
            this.copyOldEntries(oldCupboard);
            this.save();
        }
    }

    public void copyOldEntries(CupboardState oldCupboard) {
        this.setIsClean(oldCupboard.isClean());
        this.setIsCleanOld(oldCupboard.isCleanOld());
        this.setCleanComment(oldCupboard.getCleanComment());
        this.setHasNoDamage(oldCupboard.hasNoDamage());
        this.setIsDamageOld(oldCupboard.isDamageOld());
        this.setDamageComment(oldCupboard.getDamageComment());
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
        this.setHasNoDamage(check.get(1));
        this.save();
    }

    @Override
    public void saveCheckOldEntries(List<Boolean> checkOld) {
        this.setIsCleanOld(checkOld.get(0));
        this.setIsDamageOld(checkOld.get(1));
        this.save();
    }

    @Override
    public void saveCommentsEntries(List<String> comments) {
        this.setCleanComment(comments.get(0));
        this.setDamageComment(comments.get(1));
        this.save();
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
