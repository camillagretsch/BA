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
 * Created by camillagretsch on 18.09.16.
 */
@Table(name = "MattressState")
public class MattressState extends Model implements EntryStateInterface {

    @Column(name = "beddingIsClean")
    private boolean beddingIsClean = true;

    @Column(name = "isBeddingOld")
    private boolean isBeddingOld = false;

    @Column(name = "bedding_comment")
    private String beddingComment;

    @Column(name = "linenIsClean")
    private boolean linenIsClean = true;

    @Column(name = "isLinenOld")
    private boolean isLinenOld = false;

    @Column(name = "linen_comment")
    private String linenComment;

    @Column(name = "hasNoDamage")
    private boolean hasNoDamage = true;

    @Column(name = "isDamageOld")
    private boolean isDamageOld = false;

    @Column(name = "damage_comment")
    private String damageComment;

    //TODO damagePicture

    private static final List<String> ROW_NAMES = Arrays.asList("Wurde gewaschen, getrocknet & gefaltet?", "Matratzenschoner wurde bei 60 gewaschen?", "Ist alles intakt?");

    @Column(name = "room", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private Room room;

    @Column(name = "AP", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private AP ap;

    public MattressState() {
        super();
    }

    public MattressState(Room room, AP ap) {
        super();
        this.room = room;
        this.ap = ap;
    }

    public void setBeddingIsClean(boolean beddingIsClean) {
        this.beddingIsClean = beddingIsClean;
    }

    public boolean getBeddingIsClean() {
        return beddingIsClean;
    }

    public void setIsBeddingOld(boolean isBeddingOld) {
        this.isBeddingOld = isBeddingOld;
    }

    public boolean isBeddingOld() {
        return isBeddingOld;
    }

    public void setBeddingComment(String beddingComment) {
        this.beddingComment = beddingComment;
    }

    public String getBeddingComment() {
        return beddingComment;
    }

    public void setLinenIsClean(boolean linenIsClean) {
        this.linenIsClean = linenIsClean;
    }

    public boolean getLinenIsClean() {
        return linenIsClean;
    }

    public void setIsLinenOld(boolean isLinenOld) {
        this.isLinenOld = isLinenOld;
    }

    public boolean isLinenOld() {
        return isLinenOld;
    }

    public void setLinenComment(String linenComment) {
        this.linenComment = linenComment;
    }

    public String getLinenComment() {
        return linenComment;
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

    public Room getRoom() {
        return room;
    }

    public AP getAp() {
        return ap;
    }

    public List<Boolean> createCheckList(MattressState mattress) {
        return new ArrayList<>(Arrays.asList(mattress.getBeddingIsClean(), mattress.getLinenIsClean(), mattress.hasNoDamage()));
    }

    public List<String> createCommentsList(MattressState mattress) {
        return new ArrayList<>(Arrays.asList(mattress.getBeddingComment(), mattress.getLinenComment(), mattress.getDamageComment()));
    }

    public List<Boolean> createCheckOldList(MattressState mattress) {
        return new ArrayList<>(Arrays.asList(mattress.isBeddingOld(), mattress.isLinenOld(), mattress.isDamageOld()));
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
        MattressState oldMattress = MattressState.findByRoomAndAP(oldAP.getRoom(), oldAP);
        this.copyOldEntries(oldMattress);
        this.save();
    }

    public void copyOldEntries(MattressState oldMattress) {
        this.setBeddingIsClean(oldMattress.getBeddingIsClean());
        this.setIsBeddingOld(oldMattress.isBeddingOld());
        this.setBeddingComment(oldMattress.getBeddingComment());
        this.setLinenIsClean(oldMattress.getLinenIsClean());
        this.setIsLinenOld(oldMattress.isLinenOld());
        this.setLinenComment(oldMattress.getLinenComment());
        this.setHasNoDamage(oldMattress.hasNoDamage());
        this.setIsDamageOld(oldMattress.isDamageOld());
        this.setDamageComment(oldMattress.getDamageComment());
    }

    public void createNewEntry(AP ap) {
        this.save();
    }

    public static MattressState findByRoomAndAP(Room room, AP ap) {
        return new Select().from(MattressState.class).where("room = ? and AP = ?", room.getId(), ap.getId()).executeSingle();
    }

    public static MattressState findById(long id) {
        return new Select().from(MattressState.class).where("id = ?", id).executeSingle();
    }

    public static void initializeRoomMattress(List<AP> aps) {
        for (AP ap : aps) {
            MattressState mattress = new MattressState(ap.getRoom(), ap);
            mattress.setBeddingIsClean(true);
            mattress.setLinenIsClean(true);
            mattress.setHasNoDamage(false);
            mattress.setDamageComment("Loch in der Bettdecke");
            mattress.save();
        }
    }
}
