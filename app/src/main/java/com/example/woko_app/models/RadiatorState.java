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
@Table(name = "RadiatorState")
public class RadiatorState extends Model implements EntryStateInterface {

    @Column(name = "isOn")
    private boolean isOn = true;

    @Column(name = "isOnOld")
    private boolean isOnOld = false;

    @Column(name = "on_comment")
    private String onComment;

    @Column(name = "on_picture")
    private byte[] onPicture;

    @Column(name = "hasNoDamage")
    private boolean hasNoDamage = true;

    @Column(name = "isDamageOld")
    private boolean isDamageOld = false;

    @Column(name = "damage_comment")
    private String damageComment;

    @Column(name = "damage_picture")
    private byte[] damagePicture;

    private static final List<String> ROW_NAMES = Arrays.asList("Heizung ist eingeschalten?", "Ist alles intakt?");

    @Column(name = "room", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Room room;

    @Column(name = "bathroom", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Bathroom bathroom;

    @Column(name = "kitchen", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Kitchen kitchen;

    @Column(name = "AP", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private AP ap;

    public RadiatorState() {
        super();
    }

    public RadiatorState(Room room, AP ap) {
        super();
        this.room = room;
        this.ap = ap;
    }

    public RadiatorState(Bathroom bathroom, AP ap) {
        super();
        this.bathroom = bathroom;
        this.ap = ap;
    }

    public RadiatorState(Kitchen kitchen, AP ap) {
        super();
        this.kitchen = kitchen;
        this.ap = ap;
    }

    public void setIsOn(boolean isOn) {
        this.isOn = isOn;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setIsOnOld(boolean isOnOld) {
        this.isOnOld = isOnOld;
    }

    public boolean isOnOld() {
        return isOnOld;
    }

    public void setOnComment(String onComment) {
        this.onComment = onComment;
    }

    public String getOnComment() {
        return onComment;
    }

    public void setOnPicture(byte[] onPicture) {
        this.onPicture = onPicture;
    }

    public byte[] getOnPicture() {
        return onPicture;
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

    public Room getRoom() {
        return room;
    }

    public Bathroom getBathroom() {
        return bathroom;
    }

    public Kitchen getKitchen() {
        return kitchen;
    }

    public AP getAp() {
        return ap;
    }

    private List<Boolean> createCheckList(RadiatorState radiator) {
        return new ArrayList<>(Arrays.asList(radiator.isOn(), radiator.hasNoDamage()));
    }

    private List<String> createCommentsList(RadiatorState radiator) {
        return new ArrayList<>(Arrays.asList(radiator.getOnComment(), radiator.getDamageComment()));
    }

    private List<Boolean> createCheckOldList(RadiatorState radiator) {
        return new ArrayList<>(Arrays.asList(radiator.isOnOld(), radiator.isDamageOld()));
    }

    private List<byte[]> createPictureList(RadiatorState radiator) {
        return new ArrayList<>(Arrays.asList(radiator.getOnPicture(), radiator.getDamagePicture()));
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
        RadiatorState radiator = RadiatorState.findByRoomAndAP(frag.getCurrentAP().getRoom(), frag.getCurrentAP());

        if (ApartmentType.STUDIO.equals(frag.getCurrentAP().getApartment().getType())) {
            if (frag.getParent() == 0) {
                radiator = RadiatorState.findByKitchenAndAP(frag.getCurrentAP().getKitchen(), frag.getCurrentAP());
                frag.setTableEntries(RadiatorState.findByKitchenAndAP(frag.getCurrentAP().getKitchen(), frag.getCurrentAP()));
            } else if (frag.getParent() == 1) {
                radiator = RadiatorState.findByBathroomAndAP(frag.getCurrentAP().getBathroom(), frag.getCurrentAP());
                frag.setTableEntries(RadiatorState.findByBathroomAndAP(frag.getCurrentAP().getBathroom(), frag.getCurrentAP()));
            }
        }
        frag.setHeaderVariante1();
        frag.getRowNames().addAll(radiator.ROW_NAMES);
        frag.getCheck().addAll(createCheckList(radiator));
        frag.getCheckOld().addAll(createCheckOldList(radiator));
        frag.getComments().addAll(createCommentsList(radiator));
        frag.getCurrentAP().setLastOpend(radiator);
        frag.setTableContentVariante1();
    }

    @Override
    public void duplicateEntries(AP ap, AP oldAP) {
        RadiatorState oldRadiator = RadiatorState.findByRoomAndAP(oldAP.getRoom(), oldAP);
        this.copyOldEntries(oldRadiator);
        this.save();

        if (ApartmentType.STUDIO.equals(ap.getApartment().getType())) {
            RadiatorState radiator= new RadiatorState(ap.getKitchen(), ap);
            oldRadiator = RadiatorState.findByKitchenAndAP(oldAP.getKitchen(), oldAP);
            radiator.copyOldEntries(oldRadiator);
            radiator.save();

            radiator = new RadiatorState(ap.getBathroom(), ap);
            oldRadiator = RadiatorState.findByBathroomAndAP(oldAP.getBathroom(), oldAP);
            radiator.copyOldEntries(oldRadiator);
            radiator.save();
        }
    }

    private void copyOldEntries(RadiatorState oldRadiator) {
        this.setIsOn(oldRadiator.isOn());
        this.setIsOnOld(oldRadiator.isOnOld());
        this.setOnComment(oldRadiator.getOnComment());
        this.setOnPicture(oldRadiator.getOnPicture());
        this.setHasNoDamage(oldRadiator.hasNoDamage());
        this.setIsDamageOld(oldRadiator.isDamageOld());
        this.setDamageComment(oldRadiator.getDamageComment());
        this.setDamagePicture(oldRadiator.getDamagePicture());
    }

    @Override
    public void createNewEntry(AP ap) {
        this.save();

        if (ApartmentType.STUDIO.equals(ap.getApartment().getType())) {
            RadiatorState radiator = new RadiatorState(ap.getKitchen(), ap);
            radiator.save();

            radiator = new RadiatorState(ap.getBathroom(), ap);
            radiator.save();
        }
    }

    @Override
    public void saveCheckEntries(List<Boolean> check) {
        this.setIsOn(check.get(0));
        this.setHasNoDamage(check.get(1));
        this.save();
    }

    @Override
    public void saveCheckOldEntries(List<Boolean> checkOld) {
        this.setIsOnOld(checkOld.get(0));
        this.setIsDamageOld(checkOld.get(1));
        this.save();
    }

    @Override
    public void saveCommentsEntries(List<String> comments) {
        this.setOnComment(comments.get(0));
        this.setDamageComment(comments.get(1));
        this.save();
    }

    @Override
    public void savePicture(int pos, byte[] picture) {
        switch (pos) {
            case 0:
                this.setOnPicture(picture);
                break;
            case 1:
                this.setDamagePicture(picture);
                break;
        }
        this.save();
    }

    public static RadiatorState findByRoomAndAP(Room room, AP ap) {
        return new Select().from(RadiatorState.class).where("room = ? and AP = ?", room.getId(), ap.getId()).executeSingle();
    }

    public static RadiatorState findByBathroomAndAP(Bathroom bathroom, AP ap) {
        return new Select().from(RadiatorState.class).where("bathroom = ? and AP = ?", bathroom.getId(), ap.getId()).executeSingle();
    }

    public static RadiatorState findByKitchenAndAP(Kitchen kitchen, AP ap) {
        return new Select().from(RadiatorState.class).where("kitchen = ? and AP = ?", kitchen.getId(), ap.getId()).executeSingle();
    }

    public static RadiatorState findById(long id) {
        return new Select().from(RadiatorState.class).where("id = ?", id).executeSingle();
    }

    public static RadiatorState checkBelonging(RadiatorState radiator, AP ap) {
        if (radiator.getRoom() != null) {
            return RadiatorState.findByRoomAndAP(ap.getRoom(), ap);
        } else if (radiator.getBathroom() != null) {
            return RadiatorState.findByBathroomAndAP(ap.getBathroom(), ap);
        } else
            return RadiatorState.findByKitchenAndAP(ap.getKitchen(), ap);
    }

    public static void initializeRoomRadiator(List<AP> aps) {
        for (AP ap : aps) {
            RadiatorState radiator = new RadiatorState(ap.getRoom(), ap);
            radiator.setIsOn(false);
            radiator.setHasNoDamage(true);
            radiator.save();
        }
    }

    public static void initializeBathroomRadiator(List<AP> aps) {
        for (AP ap : aps) {
            RadiatorState radiator = new RadiatorState(ap.getBathroom(), ap);
            radiator.setIsOn(true);
            radiator.setHasNoDamage(true);
            radiator.save();
        }
    }

    public static void initializeKitchenRadiator(List<AP> aps) {
        for (AP ap : aps) {
            RadiatorState radiator = new RadiatorState(ap.getKitchen(), ap);
            radiator.setIsOn(true);
            radiator.setHasNoDamage(false);
            radiator.setDamageComment("Ventil ist abgebrochen");
            radiator.save();
        }
    }
}

