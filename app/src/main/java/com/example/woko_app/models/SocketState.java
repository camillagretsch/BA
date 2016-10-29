package com.example.woko_app.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.cete.dynamicpdf.Font;
import com.cete.dynamicpdf.TextAlign;
import com.cete.dynamicpdf.VAlign;
import com.cete.dynamicpdf.pageelements.Image;
import com.cete.dynamicpdf.pageelements.Row2;
import com.cete.dynamicpdf.pageelements.Table2;
import com.example.woko_app.R;
import com.example.woko_app.constants.ApartmentType;
import com.example.woko_app.fragment.DataGridFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by camillagretsch on 18.09.16.
 */
@Table(name = "SocketState")
public class SocketState extends Model implements EntryStateInterface {

    @Column(name = "isWorking")
    private boolean isWorking = true;

    @Column(name = "isWorkingOld")
    private boolean isWorkingOld = false;

    @Column(name = "working_comment")
    private String workingComment = null;

    @Column(name = "working_picture")
    private byte[] workingPicture = null;

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
    private String name = "Lampen, Steckdosen";

    private static final List<String> ROW_NAMES = Arrays.asList("Funktionieren?", "Ist alles intakt?");

    public SocketState() {
        super();
    }

    public SocketState(Room room, AP ap) {
        super();
        this.room = room;
        this.ap = ap;
    }

    public SocketState(Bathroom bathroom, AP ap) {
        super();
        this.bathroom = bathroom;
        this.ap = ap;
    }

    public SocketState(Kitchen kitchen, AP ap) {
        super();
        this.kitchen = kitchen;
        this.ap = ap;
    }

    public void setIsWorking(boolean isWorking) {
        this.isWorking = isWorking;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setIsWorkingOld(boolean isWorkingOld) {
        this.isWorkingOld = isWorkingOld;
    }

    public boolean isWorkingOld() {
        return isWorkingOld;
    }

    public void setWorkingComment(String workingComment) {
        this.workingComment = workingComment;
    }

    public String getWorkingComment() {
        return workingComment;
    }

    public void setWorkingPicture(byte[] workingPicture) {
        this.workingPicture = workingPicture;
    }

    public byte[] getWorkingPicture() {
        return workingPicture;
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

    public void setAp(AP ap) {
        this.ap = ap;
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
        SocketState socket = (SocketState) entryStateInterface;
        AP ap = socket.getAp();

        int counter = 0;
        int year = 0;

        while (year < 5) {

            if (null != SocketState.checkBelonging(socket, ap).getPictureAtPosition(pos)) {
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
        SocketState socket = SocketState.findByRoomAndAP(frag.getCurrentAP().getRoom(), frag.getCurrentAP());

        if (ApartmentType.STUDIO.equals(frag.getCurrentAP().getApartment().getType())) {
            if (frag.getParent() == 0) {
                socket = SocketState.findByKitchenAndAP(frag.getCurrentAP().getKitchen(), frag.getCurrentAP());
                frag.setTableEntries(SocketState.findByKitchenAndAP(frag.getCurrentAP().getKitchen(), frag.getCurrentAP()));
            } else if (frag.getParent() == 1) {
                socket = SocketState.findByBathroomAndAP(frag.getCurrentAP().getBathroom(), frag.getCurrentAP());
                frag.setTableEntries(SocketState.findByBathroomAndAP(frag.getCurrentAP().getBathroom(), frag.getCurrentAP()));
            }
        }
        frag.setTableHeader(frag.getResources().getStringArray(R.array.header_variante1));
        frag.getRowNames().addAll(socket.ROW_NAMES);
        frag.getCheck().addAll(createCheckList(socket));
        frag.getCheckOld().addAll(createCheckOldList(socket));
        frag.getComments().addAll(createCommentsList(socket));
        frag.getCurrentAP().setLastOpend(socket);
        frag.setTableContentVariante1();
    }

    @Override
    public void duplicateEntries(AP ap, AP oldAP) {
        SocketState oldSocket = SocketState.findByRoomAndAP(oldAP.getRoom(), oldAP);
        this.copyOldEntries(oldSocket);
        this.save();

        if (ApartmentType.STUDIO.equals(ap.getApartment().getType())) {
            SocketState socket = new SocketState(ap.getKitchen(), ap);
            oldSocket = SocketState.findByKitchenAndAP(oldAP.getKitchen(), oldAP);
            socket.copyOldEntries(oldSocket);
            socket.save();

            socket = new SocketState(ap.getBathroom(), ap);
            oldSocket = SocketState.findByBathroomAndAP(oldAP.getBathroom(), oldAP);
            socket.copyOldEntries(oldSocket);
            socket.save();
        }
    }

    @Override
    public void createNewEntry(AP ap) {
        this.save();

        if (ApartmentType.STUDIO.equals(ap.getApartment().getType())) {
            SocketState socket = new SocketState(ap.getKitchen(), ap);
            socket.save();

            socket = new SocketState(ap.getBathroom(), ap);
            socket.save();
        }
    }

    @Override
    public void saveCheckEntries(List<String> check, String ex) {
        this.setIsWorking(Boolean.parseBoolean(check.get(0)));
        this.setHasNoDamage(Boolean.parseBoolean(check.get(1)));
        if (check.contains("false")) {
            this.setName("Lampen, Steckdosen " + ex);
        } else
            this.setName("Lampen, Steckdosen");
        this.save();
    }

    @Override
    public void saveCheckOldEntries(List<Boolean> checkOld) {
        this.setIsWorkingOld(checkOld.get(0));
        this.setIsDamageOld(checkOld.get(1));
        this.save();
    }

    @Override
    public void saveCommentsEntries(List<String> comments) {
        this.setWorkingComment(comments.get(0));
        this.setDamageComment(comments.get(1));
        this.save();
    }

    @Override
    public void savePicture(int pos, byte[] picture) {
        switch (pos) {
            case 0:
                this.setWorkingPicture(picture);
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
     * @param socket
     * @return
     */
    private static List<Boolean> createCheckList(SocketState socket) {
        return new ArrayList<>(Arrays.asList(socket.isWorking(), socket.hasNoDamage()));
    }

    /**
     * add all columns which contain the comment to a list
     * @param socket
     * @return
     */
    private static List<String> createCommentsList(SocketState socket) {
        return new ArrayList<>(Arrays.asList(socket.getWorkingComment(), socket.getDamageComment()));
    }

    /**
     * add all columns which contain the verification if the entry is old to a list
     * false when the entry is new
     * true when the entry is old
     * @param socket
     * @return
     */
    private static List<Boolean> createCheckOldList(SocketState socket) {
        return new ArrayList<>(Arrays.asList(socket.isWorkingOld(), socket.isDamageOld()));
    }

    /**
     * add all columns which contain the picture to a list
     * @param socket
     * @return
     */
    private static List<byte[]> createPictureList(SocketState socket) {
        return new ArrayList<>(Arrays.asList(socket.getWorkingPicture(), socket.getDamagePicture()));
    }

    /**
     * copies all columns
     * @param oldSocket
     */
    private void copyOldEntries(SocketState oldSocket) {
        this.setIsWorking(oldSocket.isWorking());
        this.setIsWorkingOld(oldSocket.isWorkingOld());
        this.setWorkingComment(oldSocket.getWorkingComment());
        this.setWorkingPicture(oldSocket.getWorkingPicture());
        this.setHasNoDamage(oldSocket.hasNoDamage());
        this.setIsDamageOld(oldSocket.isDamageOld());
        this.setDamageComment(oldSocket.getDamageComment());
        this.setDamagePicture(oldSocket.getDamagePicture());
        this.setName(oldSocket.getName());
    }

    /**
     * search it in the db with the room id and protocol id
     * @param room
     * @param ap
     * @return
     */
    public static SocketState findByRoomAndAP(Room room, AP ap) {
        return new Select().from(SocketState.class).where("room = ? and AP = ?", room.getId(), ap.getId()).executeSingle();
    }

    /**
     * search it in the db with the bathroom id and protocol id
     * @param bathroom
     * @param ap
     * @return
     */
    public static SocketState findByBathroomAndAP(Bathroom bathroom, AP ap) {
        return new Select().from(SocketState.class).where("bathroom = ? and AP = ?", bathroom.getId(), ap.getId()).executeSingle();
    }

    /**
     * search it in the db with the kitchen id and protocol id
     * @param kitchen
     * @param ap
     * @return
     */
    public static SocketState findByKitchenAndAP(Kitchen kitchen, AP ap) {
        return new Select().from(SocketState.class).where("kitchen = ? and AP = ?", kitchen.getId(), ap.getId()).executeSingle();
    }

    /**
     * check if the entry belong to the kitchen, bathroom or room
     * @param socket
     * @param ap
     * @return
     */
    public static SocketState checkBelonging(SocketState socket, AP ap) {
        if (socket.getRoom() != null) {
            return SocketState.findByRoomAndAP(ap.getRoom(), ap);
        } else if (socket.getBathroom() != null) {
            return SocketState.findByBathroomAndAP(ap.getBathroom(), ap);
        } else
            return SocketState.findByKitchenAndAP(ap.getKitchen(), ap);
    }

    /**
     * fill in the db with initial entries
     * @param aps
     * @param ex
     */
    public static void initializeRoomSocket(List<AP> aps, String ex) {
        for (AP ap : aps) {
            SocketState socket = new SocketState(ap.getRoom(), ap);
            socket.setIsWorking(false);
            socket.setWorkingComment("Deckenlampe ist kaputt");
            socket.setName(socket.getName() + " " + ex);
            socket.setHasNoDamage(true);
            socket.save();
        }
    }

    /**
     * fill in the db with initial entries
     * @param aps
     */
    public static void initializeBathroomSocket(List<AP> aps) {
        for (AP ap : aps) {
            SocketState socket = new SocketState(ap.getBathroom(), ap);
            socket.setIsWorking(true);
            socket.setHasNoDamage(true);
            socket.save();
        }
    }

    /**
     * fill in the db with initial entries
     * @param aps
     * @param ex
     */
    public static void initializeKitchenSocket(List<AP> aps, String ex) {
        for (AP ap : aps) {
            SocketState socket = new SocketState(ap.getKitchen(), ap);
            socket.setIsWorking(false);
            socket.setName(socket.getName() + " " + ex);
            socket.setHasNoDamage(true);
            socket.save();
        }
    }

    public static Table2 createPDF(SocketState socket, float posX, float posY, float pageWidth, byte[] cross) {
        Table2 table = new Table2(posX, posY, pageWidth, 700);

        table.getColumns().add(100);
        table.getColumns().add(30);
        table.getColumns().add(30);
        table.getColumns().add(50);
        table.getColumns().add(100);
        table.getColumns().add(100);

        Row2 header = table.getRows().add(30);
        header.getCellDefault().setFont(Font.getHelveticaBold());
        header.getCellDefault().setFontSize(11);
        header.getCellDefault().setAlign(TextAlign.CENTER);
        header.getCellDefault().setVAlign(VAlign.CENTER);
        header.getCells().add("");
        header.getCells().add("Ja");
        header.getCells().add("Nein");
        header.getCells().add("alter Eintrag");
        header.getCells().add("Kommentar");
        header.getCells().add("Foto");

        int i = 0;
        for (String s : ROW_NAMES) {
            Row2 row = table.getRows().add(30);
            row.getCellDefault().setFontSize(11);
            row.getCellDefault().setAlign(TextAlign.CENTER);
            row.getCellDefault().setVAlign(VAlign.CENTER);

            row.getCells().add(s);

            if (createCheckList(socket).get(i)) {
                row.getCells().add(new Image(cross, 0, 0));
                row.getCells().add("");
            } else {
                row.getCells().add("");
                row.getCells().add(new Image(cross, 0, 0));
            }

            if (createCheckOldList(socket).get(i)) {
                row.getCells().add(new Image(cross, 0, 0));
            } else
                row.getCells().add("");

            if (null != createCommentsList(socket).get(i)) {
                row.getCells().add(createCommentsList(socket).get(i));
            } else
                row.getCells().add("");

            if (null != createPictureList(socket).get(i)) {
                row.getCells().add(new Image(createPictureList(socket).get(i), 0, 0));
            } else
                row.getCells().add("");

            i++;
        }
        return table;
    }
}
