package com.example.woko_app.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.cete.dynamicpdf.Font;
import com.cete.dynamicpdf.pageelements.CellAlign;
import com.cete.dynamicpdf.pageelements.CellVAlign;
import com.cete.dynamicpdf.pageelements.Image;
import com.cete.dynamicpdf.pageelements.Row;
import com.cete.dynamicpdf.pageelements.forms.CheckBox;
import com.example.woko_app.R;
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
    private String onComment = null;

    @Column(name = "on_picture")
    private byte[] onPicture = null;

    @Column(name = "hasNoDamage")
    private boolean hasNoDamage = true;

    @Column(name = "isDamageOld")
    private boolean isDamageOld = false;

    @Column(name = "damage_comment")
    private String damageComment = null;

    @Column(name = "damage_picture")
    private byte[] damagePicture = null;

    @Column(name = "room", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Room room;

    @Column(name = "bathroom", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Bathroom bathroom;

    @Column(name = "kitchen", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Kitchen kitchen;

    @Column(name = "AP", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private AP ap;

    @Column(name = "name")
    private String name = "Heizkörper, Ventil";

    private static final List<String> ROW_NAMES = Arrays.asList("Heizung ist eingeschalten?", "Ist alles intakt?");

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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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
    public int countPicturesOfLast5Years(int pos, EntryStateInterface entryStateInterface) {
        RadiatorState radiator = (RadiatorState) entryStateInterface;
        AP ap = radiator.getAp();

        int counter = 0;
        int year = 0;

        while (year < 5) {

            if (null != RadiatorState.checkBelonging(radiator, ap).getPictureAtPosition(pos)) {
                counter++;
            }
            if (null != ap.getOldAP()) {
                ap = ap.getOldAP();
            } else
                break;
            year++;
        }
        return counter;
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
        frag.setTableHeader(frag.getResources().getStringArray(R.array.header_variante1));
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
    public void saveCheckEntries(List<String> check, String ex) {
        this.setIsOn(Boolean.parseBoolean(check.get(0)));
        this.setHasNoDamage(Boolean.parseBoolean(check.get(1)));
        if (check.contains("false")) {
            this.setName("Heizkörper, Ventil " + ex);
        } else
            this.setName("Heizkörper, Ventil");
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

    /**
     * add all columns which contain the verification of the correctness of the entries to a list
     * false when something is incorrect
     * true when something is correct
     * @param radiator
     * @return
     */
    private static List<Boolean> createCheckList(RadiatorState radiator) {
        return new ArrayList<>(Arrays.asList(radiator.isOn(), radiator.hasNoDamage()));
    }

    /**
     * add all columns which contain the comment to a list
     * @param radiator
     * @return
     */
    private static List<String> createCommentsList(RadiatorState radiator) {
        return new ArrayList<>(Arrays.asList(radiator.getOnComment(), radiator.getDamageComment()));
    }

    /**
     * add all columns which contain the verification if the entry is old to a list
     * false when the entry is new
     * true when the entry is old
     * @param radiator
     * @return
     */
    private static List<Boolean> createCheckOldList(RadiatorState radiator) {
        return new ArrayList<>(Arrays.asList(radiator.isOnOld(), radiator.isDamageOld()));
    }

    /**
     * add all columns which contain the picture to a list
     * @param radiator
     * @return
     */
    private static List<byte[]> createPictureList(RadiatorState radiator) {
        return new ArrayList<>(Arrays.asList(radiator.getOnPicture(), radiator.getDamagePicture()));
    }

    /**
     * copies all columns
     * @param oldRadiator
     */
    private void copyOldEntries(RadiatorState oldRadiator) {
        this.setIsOn(oldRadiator.isOn());
        this.setIsOnOld(oldRadiator.isOnOld());
        this.setOnComment(oldRadiator.getOnComment());
        this.setOnPicture(oldRadiator.getOnPicture());
        this.setHasNoDamage(oldRadiator.hasNoDamage());
        this.setIsDamageOld(oldRadiator.isDamageOld());
        this.setDamageComment(oldRadiator.getDamageComment());
        this.setDamagePicture(oldRadiator.getDamagePicture());
        this.setName(oldRadiator.getName());
    }

    /**
     * search it in the db with the room id and protocol id
     * @param room
     * @param ap
     * @return
     */
    public static RadiatorState findByRoomAndAP(Room room, AP ap) {
        return new Select().from(RadiatorState.class).where("room = ? and AP = ?", room.getId(), ap.getId()).executeSingle();
    }

    /**
     * search it in the db with the bathroom id and protocol id
     * @param bathroom
     * @param ap
     * @return
     */
    public static RadiatorState findByBathroomAndAP(Bathroom bathroom, AP ap) {
        return new Select().from(RadiatorState.class).where("bathroom = ? and AP = ?", bathroom.getId(), ap.getId()).executeSingle();
    }

    /**
     * search it in the db with the kitchen id and protocol id
     * @param kitchen
     * @param ap
     * @return
     */
    public static RadiatorState findByKitchenAndAP(Kitchen kitchen, AP ap) {
        return new Select().from(RadiatorState.class).where("kitchen = ? and AP = ?", kitchen.getId(), ap.getId()).executeSingle();
    }

    /**
     * check if the entry belong to the kitchen, bathroom or room
     * @param radiator
     * @param ap
     * @return
     */
    public static RadiatorState checkBelonging(RadiatorState radiator, AP ap) {
        if (radiator.getRoom() != null) {
            return RadiatorState.findByRoomAndAP(ap.getRoom(), ap);
        } else if (radiator.getBathroom() != null) {
            return RadiatorState.findByBathroomAndAP(ap.getBathroom(), ap);
        } else
            return RadiatorState.findByKitchenAndAP(ap.getKitchen(), ap);
    }

    /**
     * fill in the db with initial entries
     * @param aps
     * @param ex
     */
    public static void initializeRoomRadiator(List<AP> aps, String ex) {
        for (AP ap : aps) {
            RadiatorState radiator = new RadiatorState(ap.getRoom(), ap);
            radiator.setIsOn(false);
            radiator.setName(radiator.getName() + " " + ex);
            radiator.setHasNoDamage(true);
            radiator.save();
        }
    }

    /**
     * fill in the db with initial entries
     * @param aps
     */
    public static void initializeBathroomRadiator(List<AP> aps) {
        for (AP ap : aps) {
            RadiatorState radiator = new RadiatorState(ap.getBathroom(), ap);
            radiator.setIsOn(true);
            radiator.setHasNoDamage(true);
            radiator.save();
        }
    }

    /**
     * fill in the db with initial entries
     * @param aps
     * @param ex
     */
    public static void initializeKitchenRadiator(List<AP> aps, String ex) {
        for (AP ap : aps) {
            RadiatorState radiator = new RadiatorState(ap.getKitchen(), ap);
            radiator.setIsOn(true);
            radiator.setHasNoDamage(false);
            radiator.setDamageComment("Ventil ist abgebrochen");
            radiator.setName(radiator.getName() + " " + ex);
            radiator.save();
        }
    }

    public static com.cete.dynamicpdf.pageelements.Table createPDF(RadiatorState radiator, float pageWidth, float posY, byte[] cross) {
        com.cete.dynamicpdf.pageelements.Table table = new com.cete.dynamicpdf.pageelements.Table(0, posY, pageWidth, 0);

        table.getColumns().add(150);
        table.getColumns().add(30);
        table.getColumns().add(30);
        table.getColumns().add(50);
        table.getColumns().add(170);
        table.getColumns().add(320);

        Row header = table.getRows().add(30);
        header.setFont(Font.getHelveticaBold());
        header.setFontSize(11);
        header.setAlign(CellAlign.CENTER);
        header.setVAlign(CellVAlign.CENTER);
        header.getCellList().add("");
        header.getCellList().add("Ja");
        header.getCellList().add("Nein");
        header.getCellList().add("alter Eintrag");
        header.getCellList().add("Kommentar");
        header.getCellList().add("Foto");

        int i = 0;
        for (String s : ROW_NAMES) {
            Row row = table.getRows().add(30);
            row.setFontSize(11);
            row.setAlign(CellAlign.CENTER);
            row.setVAlign(CellVAlign.CENTER);

            row.getCellList().add(s);

            if (createCheckList(radiator).get(i)) {
                row.getCellList().add(new Image(cross, 0, 0));
                row.getCellList().add("");
            } else {
                row.getCellList().add("");
                row.getCellList().add(new Image(cross, 0, 0));
            }

            if (createCheckOldList(radiator).get(i)) {
                row.getCellList().add(new Image(cross, 0, 0));
            } else
                row.getCellList().add("");

            if (null != createCommentsList(radiator).get(i)) {
                row.getCellList().add(createCommentsList(radiator).get(i));
            } else
                row.getCellList().add("");

            if (null != createPictureList(radiator).get(i)) {
                Image image = new Image(createPictureList(radiator).get(i), 0, 0);
                row.getCellList().add(image);
            } else
                row.getCellList().add("");

            i++;
        }
        table.setHeight(table.getRequiredHeight());
        return table;
    }
}

