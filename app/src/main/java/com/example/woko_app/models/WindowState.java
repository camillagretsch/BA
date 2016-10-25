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
@Table(name = "WindowState")
public class WindowState extends Model implements EntryStateInterface {

    @Column(name = "isClean")
    private boolean isClean = true;

    @Column(name = "isCleanOld")
    private boolean isCleanOld = false;

    @Column(name = "clean_comment")
    private String cleanComment = null;

    @Column(name = "clean_picture")
    private byte[] cleanPicture = null;

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
    private String name = "Fenster";

    private static final List<String> ROW_NAMES = Arrays.asList("Sind gereinigt?", "Ist alles intakt?");

    public WindowState() {
        super();
    }

    public WindowState(Room room, AP ap) {
        super();
        this.room = room;
        this.ap = ap;
    }

    public WindowState(Bathroom bathroom, AP ap) {
        super();
        this.bathroom = bathroom;
        this.ap = ap;
    }

    public WindowState(Kitchen kitchen, AP ap) {
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

    public void setCleanPicture(byte[] cleanPicture) {
        this.cleanPicture = cleanPicture;
    }

    public byte[] getCleanPicture() {
        return cleanPicture;
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
        WindowState window = (WindowState) entryStateInterface;
        AP ap = window.getAp();

        int counter = 0;
        int year = 0;

        while (year < 5) {

            if (null != WindowState.checkBelonging(window, ap).getPictureAtPosition(pos)) {
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
        WindowState window = WindowState.findByRoomAndAP(frag.getCurrentAP().getRoom(), frag.getCurrentAP());

        if (ApartmentType.STUDIO.equals(frag.getCurrentAP().getApartment().getType())) {
            if (frag.getParent() == 0) {
                window = WindowState.findByKitchenAndAP(frag.getCurrentAP().getKitchen(), frag.getCurrentAP());
                frag.setTableEntries(WindowState.findByKitchenAndAP(frag.getCurrentAP().getKitchen(), frag.getCurrentAP()));
            } else if (frag.getParent() == 1) {
                window = WindowState.findByBathroomAndAP(frag.getCurrentAP().getBathroom(), frag.getCurrentAP());
                frag.setTableEntries(WindowState.findByBathroomAndAP(frag.getCurrentAP().getBathroom(), frag.getCurrentAP()));
            }
        }
        frag.setTableHeader(frag.getResources().getStringArray(R.array.header_variante1));
        frag.getRowNames().addAll(window.ROW_NAMES);
        frag.getCheck().addAll(createCheckList(window));
        frag.getCheckOld().addAll(createCheckOldList(window));
        frag.getComments().addAll(createCommentsList(window));
        frag.getCurrentAP().setLastOpend(window);
        frag.setTableContentVariante1();
    }

    @Override
    public void duplicateEntries(AP ap, AP oldAP) {
        WindowState oldWindow = WindowState.findByRoomAndAP(oldAP.getRoom(), oldAP);
        this.copyOldEntries(oldWindow);
        this.save();

        if (ApartmentType.STUDIO.equals(ap.getApartment().getType())) {
            WindowState window = new WindowState(ap.getKitchen(), ap);
            oldWindow = WindowState.findByKitchenAndAP(oldAP.getKitchen(), oldAP);
            window.copyOldEntries(oldWindow);
            window.save();

            window = new WindowState(ap.getBathroom(), ap);
            oldWindow = WindowState.findByBathroomAndAP(oldAP.getBathroom(), oldAP);
            window.copyOldEntries(oldWindow);
            window.save();
        }
    }

    @Override
    public void createNewEntry(AP ap) {
        this.save();

        if (ApartmentType.STUDIO.equals(ap.getApartment().getType())) {
            WindowState window = new WindowState(ap.getKitchen(), ap);
            window.save();

            window = new WindowState(ap.getBathroom(), ap);
            window.save();
        }
    }

    @Override
    public void saveCheckEntries(List<String> check, String ex) {
        this.setIsClean(Boolean.parseBoolean(check.get(0)));
        this.setHasNoDamage(Boolean.parseBoolean(check.get(1)));
        if (check.contains("false")) {
            this.setName("Fenster " + ex);
        } else
            this.setName("Fenster");
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

    @Override
    public void savePicture(int pos, byte[] picture) {
        switch (pos) {
            case 0:
                this.setCleanPicture(picture);
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
     * @param window
     * @return
     */
    private static List<Boolean> createCheckList(WindowState window) {
        return new ArrayList<>(Arrays.asList(window.isClean(), window.hasNoDamage()));
    }

    /**
     * add all columns which contain the comment to a list
     * @param window
     * @return
     */
    private static List<String> createCommentsList(WindowState window) {
        return new ArrayList<>(Arrays.asList(window.getCleanComment(), window.getDamageComment()));
    }

    /**
     * add all columns which contain the verification if the entry is old to a list
     * false when the entry is new
     * true when the entry is old
     * @param window
     * @return
     */
    private static List<Boolean> createCheckOldList(WindowState window) {
        return new ArrayList<>(Arrays.asList(window.isCleanOld(), window.isDamageOld()));
    }

    /**
     * add all columns which contain the picture to a list
     * @param window
     * @return
     */
    private static List<byte[]> createPictureList(WindowState window) {
        return new ArrayList<>(Arrays.asList(window.getCleanPicture(), window.getDamagePicture()));
    }

    /**
     * copies all columns
     * @param oldWindow
     */
    private void copyOldEntries(WindowState oldWindow) {
        this.setIsClean(oldWindow.isClean());
        this.setIsCleanOld(oldWindow.isCleanOld());
        this.setCleanComment(oldWindow.getCleanComment());
        this.setCleanPicture(oldWindow.getCleanPicture());
        this.setHasNoDamage(oldWindow.hasNoDamage());
        this.setIsDamageOld(oldWindow.isDamageOld());
        this.setDamageComment(oldWindow.getDamageComment());
        this.setDamagePicture(oldWindow.getDamagePicture());
        this.setName(oldWindow.getName());
    }

    /**
     * search it in the db with the room id and protocol id
     * @param room
     * @param ap
     * @return
     */
    public static WindowState findByRoomAndAP(Room room, AP ap) {
        return new Select().from(WindowState.class).where("room = ? and AP = ?", room.getId(), ap.getId()).executeSingle();
    }

    /**
     * search it in the db with the bathroom id and protocol id
     * @param bathroom
     * @param ap
     * @return
     */
    public static WindowState findByBathroomAndAP(Bathroom bathroom, AP ap) {
        return new Select().from(WindowState.class).where("bathroom = ? and AP = ?", bathroom.getId(), ap.getId()).executeSingle();
    }

    /**
     * search it in the db with the kitchen id and protocol id
     * @param kitchen
     * @param ap
     * @return
     */
    public static WindowState findByKitchenAndAP(Kitchen kitchen, AP ap) {
        return new Select().from(WindowState.class).where("kitchen = ? and AP = ?", kitchen.getId(), ap.getId()).executeSingle();
    }

    /**
     * check if the entry belong to the kitchen, bathroom or room
     * @param window
     * @param ap
     * @return
     */
    public static WindowState checkBelonging(WindowState window, AP ap) {
        if (window.getRoom() != null) {
            return WindowState.findByRoomAndAP(ap.getRoom(), ap);
        } else if (window.getBathroom() != null) {
            return WindowState.findByBathroomAndAP(ap.getBathroom(), ap);
        } else
            return WindowState.findByKitchenAndAP(ap.getKitchen(), ap);
    }

    /**
     * fill in the db with initial entries
     * @param aps
     */
    public static void initializeRoomWindow(List<AP> aps) {
        for (AP ap : aps) {
            WindowState window = new WindowState(ap.getRoom(), ap);
            window.setIsClean(true);
            window.setHasNoDamage(true);
            window.save();
        }
    }

    /**
     * fill in the db with initial entries
     * @param aps
     */
    public static void initializeBathroomWindow(List<AP> aps) {
        for (AP ap : aps) {
            WindowState window = new WindowState(ap.getBathroom(), ap);
            window.setIsClean(true);
            window.setHasNoDamage(true);
            window.save();
        }
    }

    /**
     * fill in the db with initial entries
     * @param aps
     * @param ex
     */
    public static void initializeKitchenWindow(List<AP> aps, String ex) {
        for (AP ap : aps) {
            WindowState window = new WindowState(ap.getKitchen(), ap);
            window.setIsClean(true);
            window.setHasNoDamage(false);
            window.setName(window.getName() + " " + ex);
            window.setDamageComment("Sprung in einer Scheibe");
            window.save();
        }
    }

    public static com.cete.dynamicpdf.pageelements.Table createPDF(WindowState window, float pageWidth, float posX, float posY, byte[] cross) {
        com.cete.dynamicpdf.pageelements.Table table = new com.cete.dynamicpdf.pageelements.Table(posX, posY, pageWidth, 0);

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

            if (createCheckList(window).get(i)) {
                row.getCellList().add(new Image(cross, 0, 0));
                row.getCellList().add("");
            } else {
                row.getCellList().add("");
                row.getCellList().add(new Image(cross, 0, 0));
            }

            if (createCheckOldList(window).get(i)) {
                row.getCellList().add(new Image(cross, 0, 0));
            } else
                row.getCellList().add("");

            if (null != createCommentsList(window).get(i)) {
                row.getCellList().add(createCommentsList(window).get(i));
            } else
                row.getCellList().add("");

            if (null != createPictureList(window).get(i)) {
                Image image = new Image(createPictureList(window).get(i), 0, 0);
                row.getCellList().add(image);
            } else
                row.getCellList().add("");

            i++;
        }
        table.setHeight(table.getRequiredHeight());
        return table;
    }
}

