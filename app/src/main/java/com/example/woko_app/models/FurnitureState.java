package com.example.woko_app.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.cete.dynamicpdf.Font;
import com.cete.dynamicpdf.Page;
import com.cete.dynamicpdf.TextAlign;
import com.cete.dynamicpdf.VAlign;
import com.cete.dynamicpdf.pageelements.Cell2;
import com.cete.dynamicpdf.pageelements.CellAlign;
import com.cete.dynamicpdf.pageelements.CellVAlign;
import com.cete.dynamicpdf.pageelements.Image;
import com.cete.dynamicpdf.pageelements.Row;
import com.cete.dynamicpdf.pageelements.Row2;
import com.cete.dynamicpdf.pageelements.Table2;
import com.cete.dynamicpdf.pageelements.forms.CheckBox;
import com.example.woko_app.R;
import com.example.woko_app.fragment.DataGridFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by camillagretsch on 22.09.16.
 */
@Table(name = "FurnitureState")
public class FurnitureState extends Model implements EntryStateInterface {

    @Column(name = "brokenBedFrame")
    private int brokenBedFrame = 0;

    @Column(name = "isBedFrameOld")
    private boolean isBedFrameOld = false;

    @Column(name = "bedFrameComment")
    private String bedFrameComment = null;

    @Column(name = "bedFrame_picture")
    private byte[] bedFramePicture = null;

    @Column(name = "brokenMattress")
    private int brokenMattress = 0;

    @Column(name = "isMattressOld")
    private boolean isMattressOld = false;

    @Column(name = "mattressComment")
    private String mattressComment = null;

    @Column(name = "mattress_picture")
    private byte[] mattressPicture = null;

    @Column(name = "brokenDesk")
    private int brokenDesk = 0;

    @Column(name = "isDeskOld")
    private boolean isDeskOld = false;

    @Column(name = "deskComment")
    private String deskComment = null;

    @Column(name = "desk_picture")
    private byte[] deskPicture = null;

    @Column(name = "brokenBookShelf")
    private int brokenBookShelf = 0;

    @Column(name = "isBookShelfOld")
    private boolean isBookShelfOld = false;

    @Column(name = "bookShelfComment")
    private String bookShelfComment = null;

    @Column(name = "bookShelf_picture")
    private byte[] bookShelfPicture = null;

    @Column(name = "brokenCupboard")
    private int brokenCupboard = 0;

    @Column(name = "isCupboardOld")
    private boolean isCupboardOld = false;

    @Column(name = "cupboardComment")
    private String cupboardComment = null;

    @Column(name = "cupboard_picture")
    private byte[] cupboardPicture = null;

    @Column(name = "brokenChair")
    private int brokenChair = 0;

    @Column(name = "isChairOld")
    private boolean isChairOld = false;

    @Column(name = "chairComment")
    private String chairComment = null;

    @Column(name = "chair_picture")
    private byte[] chairPicture = null;

    @Column(name = "brokenCurtain")
    private int brokenCurtain = 0;

    @Column(name = "isCurtainOld")
    private boolean isCurtainOld = false;

    @Column(name = "curtainComment")
    private String curtainComment = null;

    @Column(name = "curtain_picture")
    private byte[] curtainPicture = null;

    @Column(name = "brokenDayCurtain")
    private int brokenDayCurtain = 0;

    @Column(name = "isDayCurtainOld")
    private boolean isDayCurtainOld = false;

    @Column(name = "dayCurtainComment")
    private String dayCurtainComment = null;

    @Column(name = "dayCurtain_picture")
    private byte[] dayCurtainPicture = null;

    @Column(name = "brokenClothesHanger")
    private int brokenClothesHanger = 0;

    @Column(name = "isClothesHangerOld")
    private boolean isClothesHangerOld = false;

    @Column(name = "clothesHangerComment")
    private String clothesHangerComment = null;

    @Column(name = "clothesHanger_picture")
    private byte[] clothesHangerPicture = null;

    @Column(name = "brokenBlanket")
    private int brokenBlanket = 0;

    @Column(name = "isBlanketOld")
    private boolean isBlanketOld = false;

    @Column(name = "blanketComment")
    private String blanketComment = null;

    @Column(name = "blanket_picture")
    private byte[] blanketPicture = null;

    @Column(name = "brokenPillow")
    private int brokenPillow = 0;

    @Column(name = "isPillowOld")
    private boolean isPillowOld = false;

    @Column(name = "pillowComment")
    private String pillowComment = null;

    @Column(name = "pillow_picture")
    private byte[] pillowPicture = null;

    @Column(name = "brokenBedSheet")
    private int brokenBedSheet = 0;

    @Column(name = "isBedSheetOld")
    private boolean isBedSheetOld = false;

    @Column(name = "bedSheetComment")
    private String bedSheetComment = null;

    @Column(name = "bedSheet_picture")
    private byte[] bedSheetPicture = null;

    @Column(name = "brokenBedLinen")
    private int brokenBedLinen = 0;

    @Column(name = "isBedLinenOld")
    private boolean isBedLinenOld = false;

    @Column(name = "bedLinenComment")
    private String bedLinenComment = null;

    @Column(name = "bedLinen_picture")
    private byte[] bedLinenPicture = null;

    @Column(name = "brokenTVBox")
    private int brokenTVBox = 0;

    @Column(name = "isTVBoxOld")
    private boolean isTVBoxOld = false;

    @Column(name = "TVBoxComment")
    private String TVBoxComment = null;

    @Column(name = "TVBox_picture")
    private byte[] TVBoxPicture = null;

    @Column(name = "brokenTVControl")
    private int brokenTVControl = 0;

    @Column(name = "isTVControlOld")
    private boolean isTVControlOld = false;

    @Column(name = "TVControlComment")
    private String TVControlComment = null;

    @Column(name = "TVControl_picture")
    private byte[] TVControlPicture = null;

    @Column(name = "brokenAccessCard")
    private int brokenAccessCard = 0;

    @Column(name = "isAccessCardOld")
    private boolean isAccessCardOld = false;

    @Column(name = "accessCardComment")
    private String accessCardComment = null;

    @Column(name = "accessCard_picture")
    private byte[] accessCardPicture = null;

    @Column(name = "room", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private Room room;

    @Column(name = "AP", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private AP ap;

    @Column(name = "name")
    private String name = NAME;

    private final static String NAME = "Mobiliar ";

    private static final List<String> ROW_NAMES = Arrays.asList("Bettgestell", "Matratze", "Schreibtisch", "Bücherregal", "Schrank", "Stuhl", "Nachtvorhang", "Tagesvorhang", "Kleiderbügel", "Bettdecke", "Kissen", "Spannbettlaken", "Bettbezüge", "TV-Empfänger Box inkl. Kabel", "TV-Fernbedingung", "TV Sat Access Card");
    private static final List<Integer> COUNT = Arrays.asList(1, 1, 1, 1, 1, 2, 1, 1, 8, 1, 1, 1, 1, 1, 1, 1);

    public FurnitureState() {
        super();
    }

    public FurnitureState(Room room, AP ap) {
        super();
        this.room = room;
        this.ap = ap;
    }

    public void setBrokenBedFrame(int brokenBedFrame) {
        this.brokenBedFrame = brokenBedFrame;
    }

    public int getBrokenBedFrame() {
        return brokenBedFrame;
    }

    public void setIsBedFrameOld(boolean isBedFrameOld) {
        this.isBedFrameOld = isBedFrameOld;
    }

    public boolean isBedFrameOld() {
        return isBedFrameOld;
    }

    public void setBedFrameComment(String bedFrameComment) {
        this.bedFrameComment = bedFrameComment;
    }

    public String getBedFrameComment() {
        return bedFrameComment;
    }

    public void setBedFramePicture(byte[] bedFramePicture) {
        this.bedFramePicture = bedFramePicture;
    }

    public byte[] getBedFramePicture() {
        return bedFramePicture;
    }

    public void setBrokenMattress(int brokenMattress) {
        this.brokenMattress = brokenMattress;
    }

    public int getBrokenMattress() {
        return brokenMattress;
    }

    public void setIsMattressOld(boolean isMattressOld) {
        this.isMattressOld = isMattressOld;
    }

    public boolean isMattressOld() {
        return isMattressOld;
    }

    public void setMattressComment(String mattressComment) {
        this.mattressComment = mattressComment;
    }

    public String getMattressComment() {
        return mattressComment;
    }

    public void setMattressPicture(byte[] mattressPicture) {
        this.mattressPicture = mattressPicture;
    }

    public byte[] getMattressPicture() {
        return mattressPicture;
    }

    public void setBrokenDesk(int brokenDesk) {
        this.brokenDesk = brokenDesk;
    }

    public int getBrokenDesk() {
        return brokenDesk;
    }

    public void setIsDeskOld(boolean isDeskOld) {
        this.isDeskOld = isDeskOld;
    }

    public boolean isDeskOld() {
        return isDeskOld;
    }

    public void setDeskComment(String deskComment) {
        this.deskComment = deskComment;
    }

    public String getDeskComment() {
        return deskComment;
    }

    public void setDeskPicture(byte[] deskPicture) {
        this.deskPicture = deskPicture;
    }

    public byte[] getDeskPicture() {
        return deskPicture;
    }

    public void setBrokenBookShelf(int brokenBookShelf) {
        this.brokenBookShelf = brokenBookShelf;
    }

    public int getBrokenBookShelf() {
        return brokenBookShelf;
    }

    public void setIsBookShelfOld(boolean isBookShelfOld) {
        this.isBookShelfOld = isBookShelfOld;
    }

    public boolean isBookShelfOld() {
        return isBookShelfOld;
    }

    public void setBookShelfComment(String bookShelfComment) {
        this.bookShelfComment = bookShelfComment;
    }

    public String getBookShelfComment() {
        return bookShelfComment;
    }

    public void setBookShelfPicture(byte[] bookShelfPicture) {
        this.bookShelfPicture = bookShelfPicture;
    }

    public byte[] getBookShelfPicture() {
        return bookShelfPicture;
    }

    public void setBrokenCupboard(int brokenCupboard) {
        this.brokenCupboard = brokenCupboard;
    }

    public int getBrokenCupboard() {
        return brokenCupboard;
    }

    public void setIsCupboardOld(boolean isCupboardOld) {
        this.isCupboardOld = isCupboardOld;
    }

    public boolean isCupboardOld() {
        return isCupboardOld;
    }

    public void setCupboardComment(String cupboardComment) {
        this.cupboardComment = cupboardComment;
    }

    public String getCupboardComment() {
        return cupboardComment;
    }

    public void setCupboardPicture(byte[] cupboardPicture) {
        this.cupboardPicture = cupboardPicture;
    }

    public byte[] getCupboardPicture() {
        return cupboardPicture;
    }

    public void setBrokenChair(int brokenChair) {
        this.brokenChair = brokenChair;
    }

    public int getBrokenChair() {
        return brokenChair;
    }

    public void setIsChairOld(boolean isChairOld) {
        this.isChairOld = isChairOld;
    }

    public boolean isChairOld() {
        return isChairOld;
    }

    public void setChairComment(String chairComment) {
        this.chairComment = chairComment;
    }

    public String getChairComment() {
        return chairComment;
    }

    public void setChairPicture(byte[] chairPicture) {
        this.chairPicture = chairPicture;
    }

    public byte[] getChairPicture() {
        return chairPicture;
    }

    public void setBrokenCurtain(int brokenCurtain) {
        this.brokenCurtain = brokenCurtain;
    }

    public int getBrokenCurtain() {
        return brokenCurtain;
    }

    public void setIsCurtainOld(boolean isCurtainOld) {
        this.isCurtainOld = isCurtainOld;
    }

    public boolean isCurtainOld() {
        return isCurtainOld;
    }

    public void setCurtainComment(String curtainComment) {
        this.curtainComment = curtainComment;
    }

    public String getCurtainComment() {
        return curtainComment;
    }

    public void setCurtainPicture(byte[] curtainPicture) {
        this.curtainPicture = curtainPicture;
    }

    public byte[] getCurtainPicture() {
        return curtainPicture;
    }

    public void setBrokenDayCurtain(int brokenDayCurtain) {
        this.brokenDayCurtain = brokenDayCurtain;
    }

    public int getBrokenDayCurtain() {
        return brokenDayCurtain;
    }

    public void setIsDayCurtainOld(boolean isDayCurtainOld) {
        this.isDayCurtainOld = isDayCurtainOld;
    }

    public boolean isDayCurtainOld() {
        return isDayCurtainOld;
    }

    public void setDayCurtainComment(String dayCurtainComment) {
        this.dayCurtainComment = dayCurtainComment;
    }

    public String getDayCurtainComment() {
        return dayCurtainComment;
    }

    public void setDayCurtainPicture(byte[] dayCurtainPicture) {
        this.dayCurtainPicture = dayCurtainPicture;
    }

    public byte[] getDayCurtainPicture() {
        return dayCurtainPicture;
    }

    public void setBrokenClothesHanger(int brokenClothesHanger) {
        this.brokenClothesHanger = brokenClothesHanger;
    }

    public int getBrokenClothesHanger() {
        return brokenClothesHanger;
    }

    public void setIsClothesHangerOld(boolean isClothesHangerOld) {
        this.isClothesHangerOld = isClothesHangerOld;
    }

    public boolean isClothesHangerOld() {
        return isClothesHangerOld;
    }

    public void setClothesHangerComment(String clothesHangerComment) {
        this.clothesHangerComment = clothesHangerComment;
    }

    public String getClothesHangerComment() {
        return clothesHangerComment;
    }

    public void setClothesHangerPicture(byte[] clothesHangerPicture) {
        this.clothesHangerPicture = clothesHangerPicture;
    }

    public byte[] getClothesHangerPicture() {
        return clothesHangerPicture;
    }

    public void setBrokenBlanket(int brokenBlanket) {
        this.brokenBlanket = brokenBlanket;
    }

    public int getBrokenBlanket() {
        return brokenBlanket;
    }

    public void setIsBlanketOld(boolean isBlanketOld) {
        this.isBlanketOld = isBlanketOld;
    }

    public boolean isBlanketOld() {
        return isBlanketOld;
    }

    public void setBlanketComment(String blanketComment) {
        this.blanketComment = blanketComment;
    }

    public String getBlanketComment() {
        return blanketComment;
    }

    public void setBlanketPicture(byte[] blanketPicture) {
        this.blanketPicture = blanketPicture;
    }

    public byte[] getBlanketPicture() {
        return blanketPicture;
    }

    public void setBrokenPillow(int brokenPillow) {
        this.brokenPillow = brokenPillow;
    }

    public int getBrokenPillow() {
        return brokenPillow;
    }

    public void setIsPillowOld(boolean isPillowOld) {
        this.isPillowOld = isPillowOld;
    }

    public boolean isPillowOld() {
        return isPillowOld;
    }

    public void setPillowComment(String pillowComment) {
        this.pillowComment = pillowComment;
    }

    public String getPillowComment() {
        return pillowComment;
    }

    public void setPillowPicture(byte[] pillowPicture) {
        this.pillowPicture = pillowPicture;
    }

    public byte[] getPillowPicture() {
        return pillowPicture;
    }

    public void setBrokenBedSheet(int brokenBedSheet) {
        this.brokenBedSheet = brokenBedSheet;
    }

    public int getBrokenBedSheet() {
        return brokenBedSheet;
    }

    public void setIsBedSheetOld(boolean isBedSheetOld) {
        this.isBedSheetOld = isBedSheetOld;
    }

    public boolean isBedSheetOld() {
        return isBedSheetOld;
    }

    public void setBedSheetComment(String bedSheetComment) {
        this.bedSheetComment = bedSheetComment;
    }

    public String getBedSheetComment() {
        return bedSheetComment;
    }

    public void setBedSheetPicture(byte[] bedSheetPicture) {
        this.bedSheetPicture = bedSheetPicture;
    }

    public byte[] getBedSheetPicture() {
        return bedSheetPicture;
    }

    public void setBrokenBedLinen(int brokenBedLinen) {
        this.brokenBedLinen = brokenBedLinen;
    }

    public int getBrokenBedLinen() {
        return brokenBedLinen;
    }

    public void setIsBedLinenOld(boolean isBedLinenOld) {
        this.isBedLinenOld = isBedLinenOld;
    }

    public boolean isBedLinenOld() {
        return isBedLinenOld;
    }

    public void setBedLinenComment(String bedLinenComment) {
        this.bedLinenComment = bedLinenComment;
    }

    public String getBedLinenComment() {
        return bedLinenComment;
    }

    public void setBedLinenPicture(byte[] bedLinenPicture) {
        this.bedLinenPicture = bedLinenPicture;
    }

    public byte[] getBedLinenPicture() {
        return bedLinenPicture;
    }

    public void setBrokenTVBox(int brokenTVBox) {
        this.brokenTVBox = brokenTVBox;
    }

    public int getBrokenTVBox() {
        return brokenTVBox;
    }

    public void setIsTVBoxOld(boolean isTVBoxOld) {
        this.isTVBoxOld = isTVBoxOld;
    }

    public boolean isTVBoxOld() {
        return isTVBoxOld;
    }

    public void setTVBoxComment(String TVBoxComment) {
        this.TVBoxComment = TVBoxComment;
    }

    public String getTVBoxComment() {
        return TVBoxComment;
    }

    public void setTVBoxPicture(byte[] TVBoxPicture) {
        this.TVBoxPicture = TVBoxPicture;
    }

    public byte[] getTVBoxPicture() {
        return TVBoxPicture;
    }

    public void setBrokenTVControl(int brokenTVControl) {
        this.brokenTVControl = brokenTVControl;
    }

    public int getBrokenTVControl() {
        return brokenTVControl;
    }

    public void setIsTVControlOld(boolean isTVControlOld) {
        this.isTVControlOld = isTVControlOld;
    }

    public boolean isTVControlOld() {
        return isTVControlOld;
    }

    public void setTVControlComment(String TVControlComment) {
        this.TVControlComment = TVControlComment;
    }

    public String getTVControlComment() {
        return TVControlComment;
    }

    public void setTVControlPicture(byte[] TVControlPicture) {
        this.TVControlPicture = TVControlPicture;
    }

    public byte[] getTVControlPicture() {
        return TVControlPicture;
    }

    public void setBrokenAccessCard(int brokenAccessCard) {
        this.brokenAccessCard = brokenAccessCard;
    }

    public int getBrokenAccessCard() {
        return brokenAccessCard;
    }

    public void setIsAccessCardOld(boolean isAccessCardOld) {
        this.isAccessCardOld = isAccessCardOld;
    }

    public boolean isAccessCardOld() {
        return isAccessCardOld;
    }

    public void setAccessCardComment(String accessCardComment) {
        this.accessCardComment = accessCardComment;
    }

    public String getAccessCardComment() {
        return accessCardComment;
    }

    public void setAccessCardPicture(byte[] accessCardPicture) {
        this.accessCardPicture = accessCardPicture;
    }

    public byte[] getAccessCardPicture() {
        return accessCardPicture;
    }

    public Room getRoom() {
        return room;
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
        return null;
    }

    @Override
    public Boolean getCheckOldAtPosition(int pos) {
        return createCheckOldList(this).get(pos);
    }

    public int getCountBrokenAtPosition(int pos) {
        return createBrokenList(this).get(pos);
    }
    @Override
    public byte[] getPictureAtPosition(int pos) {
        return createPictureList(this).get(pos);
    }

    @Override
    public int countPicturesOfLast5Years(int pos, EntryStateInterface entryStateInterface) {
        FurnitureState furniture = (FurnitureState) entryStateInterface;
        AP ap = furniture.getAp();

        int counter = 0;
        int year = 0;

        while (year < 5) {

            if (null != FurnitureState.findByRoomAndAP(ap.getRoom(), ap).getPictureAtPosition(pos)) {
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
        frag.setTableHeader(frag.getResources().getStringArray(R.array.header_variante2));
        frag.getRowNames().addAll(this.ROW_NAMES);
        frag.getCheckOld().addAll(createCheckOldList(this));
        frag.getComments().addAll(createCommentsList(this));
        frag.getCount().addAll(this.COUNT);
        frag.getCountBroken().addAll(createBrokenList(this));
        frag.getCurrentAP().setLastOpend(this);
        frag.setTableContentVarainte2();
    }

    @Override
    public void duplicateEntries(AP ap, AP oldAP) {
        FurnitureState oldFurniture = FurnitureState.findByRoomAndAP(oldAP.getRoom(), oldAP);
        this.copyOldEntries(oldFurniture);
        this.save();
    }

    @Override
    public void createNewEntry(AP ap) {
        this.save();
    }

    @Override
    public void saveCheckEntries(List<String> check) {
        this.setBrokenBedFrame(Integer.parseInt(check.get(0)));
        this.setBrokenMattress(Integer.parseInt(check.get(1)));
        this.setBrokenDesk(Integer.parseInt(check.get(2)));
        this.setBrokenBookShelf(Integer.parseInt(check.get(3)));
        this.setBrokenCupboard(Integer.parseInt(check.get(4)));
        this.setBrokenChair(Integer.parseInt(check.get(5)));
        this.setBrokenCurtain(Integer.parseInt(check.get(6)));
        this.setBrokenDayCurtain(Integer.parseInt(check.get(7)));
        this.setBrokenClothesHanger(Integer.parseInt(check.get(8)));
        this.setBrokenBlanket(Integer.parseInt(check.get(9)));
        this.setBrokenPillow(Integer.parseInt(check.get(10)));
        this.setBrokenBedSheet(Integer.parseInt(check.get(11)));
        this.setBrokenBedLinen(Integer.parseInt(check.get(12)));
        this.setBrokenTVBox(Integer.parseInt(check.get(13)));
        this.setBrokenTVControl(Integer.parseInt(check.get(14)));
        this.setBrokenAccessCard(Integer.parseInt(check.get(15)));
        this.save();
    }

    @Override
    public void saveCheckOldEntries(List<Boolean> checkOld) {
        this.setIsBedFrameOld(checkOld.get(0));
        this.setIsMattressOld(checkOld.get(1));
        this.setIsDeskOld(checkOld.get(2));
        this.setIsBookShelfOld(checkOld.get(3));
        this.setIsCupboardOld(checkOld.get(4));
        this.setIsChairOld(checkOld.get(5));
        this.setIsCurtainOld(checkOld.get(6));
        this.setIsDayCurtainOld(checkOld.get(7));
        this.setIsClothesHangerOld(checkOld.get(8));
        this.setIsBlanketOld(checkOld.get(9));
        this.setIsPillowOld(checkOld.get(10));
        this.setIsBedSheetOld(checkOld.get(11));
        this.setIsBedLinenOld(checkOld.get(12));
        this.setIsTVBoxOld(checkOld.get(13));
        this.setIsTVControlOld(checkOld.get(14));
        this.setIsAccessCardOld(checkOld.get(15));
        this.save();
    }

    @Override
    public void saveCommentsEntries(List<String> comments) {
        this.setBedFrameComment(comments.get(0));
        this.setMattressComment(comments.get(1));
        this.setDeskComment(comments.get(2));
        this.setBookShelfComment(comments.get(3));
        this.setCupboardComment(comments.get(4));
        this.setChairComment(comments.get(5));
        this.setCurtainComment(comments.get(6));
        this.setDayCurtainComment(comments.get(7));
        this.setClothesHangerComment(comments.get(8));
        this.setBlanketComment(comments.get(9));
        this.setPillowComment(comments.get(10));
        this.setBedSheetComment(comments.get(11));
        this.setBedLinenComment(comments.get(12));
        this.setTVBoxComment(comments.get(13));
        this.setTVControlComment(comments.get(14));
        this.setAccessCardComment(comments.get(15));
        this.save();
    }

    @Override
    public void savePicture(int pos, byte[] picture) {
        switch (pos) {
            case 0:
                this.setBedFramePicture(picture);
                break;
            case 1:
                this.setMattressPicture(picture);
                break;
            case 2:
                this.setDeskPicture(picture);
                break;
            case 3:
                this.setBookShelfPicture(picture);
                break;
            case 4:
                this.setCupboardPicture(picture);
                break;
            case 5:
                this.setChairPicture(picture);
                break;
            case 6:
                this.setCurtainPicture(picture);
                break;
            case 7:
                this.setDayCurtainPicture(picture);
                break;
            case 8:
                this.setClothesHangerPicture(picture);
                break;
            case 9:
                this.setBlanketPicture(picture);
                break;
            case 10:
                this.setPillowPicture(picture);
                break;
            case 11:
                this.setBedSheetPicture(picture);
                break;
            case 12:
                this.setBedLinenPicture(picture);
                break;
            case 13:
                this.setTVBoxPicture(picture);
                break;
            case 14:
                this.setTVControlPicture(picture);
                break;
            case 15:
                this.setAccessCardPicture(picture);
                break;
        }
        this.save();
    }

    @Override
    public void updateName(String newEx, String oldEx) {
        this.setName(NAME);
        this.save();

        int i = 0;
        while (i < createBrokenList(this).size()) {
            if (createBrokenList(this).get(i) > 0) {
                this.setName(this.getName().concat(newEx));
                break;
            }
            i++;
        }

        if (createCheckOldList(this).contains(true)) {
            this.setName(this.getName().concat(oldEx));
        }
        this.save();
    }

    /**
     * add all columns which contain the broken entries
     * 0: everything is ok
     * 0 <: something is incorrect
     * @param furniture
     * @return
     */
    private static List<Integer> createBrokenList(FurnitureState furniture) {
        return new ArrayList<>(Arrays.asList(furniture.getBrokenBedFrame(), furniture.getBrokenMattress(), furniture.getBrokenDesk(), furniture.getBrokenBookShelf(), furniture.getBrokenCupboard(), furniture.getBrokenChair(), furniture.getBrokenCurtain(), furniture.getBrokenDayCurtain(), furniture.getBrokenClothesHanger(), furniture.getBrokenBlanket(), furniture.getBrokenPillow(), furniture.getBrokenBedSheet(), furniture.getBrokenBedLinen(), furniture.getBrokenTVBox(), furniture.getBrokenTVControl(), furniture.getBrokenAccessCard()));
    }

    /**
     * add all columns which contain the comment to a list
     * @param furniture
     * @return
     */
    private static List<String> createCommentsList(FurnitureState furniture) {
        return new ArrayList<>(Arrays.asList(furniture.getBedFrameComment(), furniture.getMattressComment(), furniture.getDeskComment(), furniture.getBookShelfComment(), furniture.getCupboardComment(), furniture.getChairComment(), furniture.getCurtainComment(), furniture.getDayCurtainComment(), furniture.getClothesHangerComment(), furniture.getBlanketComment(), furniture.getPillowComment(), furniture.getBedSheetComment(), furniture.getBedLinenComment(), furniture.getTVBoxComment(), furniture.getTVControlComment(), furniture.getAccessCardComment()));
    }

    /**
     * add all columns which contain the verification if the entry is old to a list
     * false when the entry is new
     * true when the entry is old
     * @param furniture
     * @return
     */
    private static List<Boolean> createCheckOldList(FurnitureState furniture) {
        return new ArrayList<>(Arrays.asList(furniture.isBedFrameOld(), furniture.isMattressOld(), furniture.isDeskOld(), furniture.isBookShelfOld(), furniture.isCupboardOld(), furniture.isChairOld(), furniture.isCurtainOld(), furniture.isDayCurtainOld(), furniture.isClothesHangerOld(), furniture.isBlanketOld(), furniture.isPillowOld(), furniture.isBedSheetOld(), furniture.isBedLinenOld(), furniture.isTVBoxOld(), furniture.isTVControlOld(), furniture.isAccessCardOld()));
    }

    /**
     * add all columns which contain the picture to a list
     * @param furniture
     * @return
     */
    private static List<byte[]> createPictureList(FurnitureState furniture) {
        return new ArrayList<>(Arrays.asList(furniture.getBedFramePicture(), furniture.getMattressPicture(), furniture.getDeskPicture(), furniture.getBookShelfPicture(), furniture.getCupboardPicture(), furniture.getChairPicture(), furniture.getCurtainPicture(), furniture.getDayCurtainPicture(), furniture.getClothesHangerPicture(), furniture.getBlanketPicture(), furniture.getPillowPicture(), furniture.getBedSheetPicture(), furniture.getBedLinenPicture(), furniture.getTVBoxPicture(), furniture.getTVControlPicture(), furniture.getAccessCardPicture()));
    }

    /**
     * copies all columns
     * @param oldFurniture
     */
    private void copyOldEntries(FurnitureState oldFurniture) {
        this.setBrokenBedFrame(oldFurniture.getBrokenBedFrame());
        this.setIsBedFrameOld(oldFurniture.isBedFrameOld());
        this.setBedFrameComment(oldFurniture.getBedFrameComment());
        this.setBedFramePicture(oldFurniture.getBedFramePicture());
        this.setBrokenMattress(oldFurniture.getBrokenMattress());
        this.setIsMattressOld(oldFurniture.isMattressOld());
        this.setMattressComment(oldFurniture.getMattressComment());
        this.setMattressPicture(oldFurniture.getMattressPicture());
        this.setBrokenDesk(oldFurniture.getBrokenDesk());
        this.setIsDeskOld(oldFurniture.isDeskOld());
        this.setDeskComment(oldFurniture.getDeskComment());
        this.setDeskPicture(oldFurniture.getDeskPicture());
        this.setBrokenBookShelf(oldFurniture.getBrokenBookShelf());
        this.setIsBookShelfOld(oldFurniture.isBookShelfOld());
        this.setBookShelfComment(oldFurniture.getBookShelfComment());
        this.setBookShelfPicture(oldFurniture.getBookShelfPicture());
        this.setBrokenCupboard(oldFurniture.getBrokenCupboard());
        this.setIsCupboardOld(oldFurniture.isCupboardOld());
        this.setCupboardComment(oldFurniture.getCupboardComment());
        this.setCupboardPicture(oldFurniture.getCupboardPicture());
        this.setBrokenChair(oldFurniture.getBrokenChair());
        this.setIsChairOld(oldFurniture.isChairOld());
        this.setChairComment(oldFurniture.getChairComment());
        this.setChairPicture(oldFurniture.getChairPicture());
        this.setBrokenCurtain(oldFurniture.getBrokenCurtain());
        this.setIsCurtainOld(oldFurniture.isCurtainOld());
        this.setCurtainComment(oldFurniture.getCurtainComment());
        this.setCurtainPicture(oldFurniture.getCurtainPicture());
        this.setBrokenDayCurtain(oldFurniture.getBrokenDayCurtain());
        this.setIsDayCurtainOld(oldFurniture.isDayCurtainOld());
        this.setDayCurtainComment(oldFurniture.getDayCurtainComment());
        this.setDayCurtainPicture(oldFurniture.getDayCurtainPicture());
        this.setBrokenClothesHanger(oldFurniture.getBrokenClothesHanger());
        this.setIsClothesHangerOld(oldFurniture.isClothesHangerOld());
        this.setClothesHangerComment(oldFurniture.getClothesHangerComment());
        this.setClothesHangerPicture(oldFurniture.getClothesHangerPicture());
        this.setBrokenBlanket(oldFurniture.getBrokenBlanket());
        this.setIsBlanketOld(oldFurniture.isBlanketOld());
        this.setBlanketComment(oldFurniture.getBlanketComment());
        this.setBlanketPicture(oldFurniture.getBlanketPicture());
        this.setBrokenPillow(oldFurniture.getBrokenPillow());
        this.setIsPillowOld(oldFurniture.isPillowOld());
        this.setPillowComment(oldFurniture.getPillowComment());
        this.setPillowPicture(oldFurniture.getPillowPicture());
        this.setBrokenBedSheet(oldFurniture.getBrokenBedSheet());
        this.setIsBedSheetOld(oldFurniture.isBedSheetOld());
        this.setBedSheetComment(oldFurniture.getBedSheetComment());
        this.setBedSheetPicture(oldFurniture.getBedSheetPicture());
        this.setBrokenBedLinen(oldFurniture.getBrokenBedLinen());
        this.setIsBedLinenOld(oldFurniture.isBedLinenOld());
        this.setBedLinenComment(oldFurniture.getBedLinenComment());
        this.setBedLinenPicture(oldFurniture.getBedLinenPicture());
        this.setBrokenTVBox(oldFurniture.getBrokenTVBox());
        this.setIsTVBoxOld(oldFurniture.isTVBoxOld());
        this.setTVBoxComment(oldFurniture.getTVBoxComment());
        this.setTVBoxPicture(oldFurniture.getTVBoxPicture());
        this.setBrokenTVControl(oldFurniture.getBrokenTVControl());
        this.setIsTVControlOld(oldFurniture.isTVControlOld());
        this.setTVControlComment(oldFurniture.getTVControlComment());
        this.setTVControlPicture(oldFurniture.getTVControlPicture());
        this.setBrokenAccessCard(oldFurniture.getBrokenAccessCard());
        this.setIsAccessCardOld(oldFurniture.isAccessCardOld());
        this.setAccessCardComment(oldFurniture.getAccessCardComment());
        this.setAccessCardPicture(oldFurniture.getAccessCardPicture());
        this.setName(oldFurniture.getName());
    }

    /**
     * search it in the db with the apartment id and protocol id
     * @param room
     * @param ap
     * @return
     */
    public static FurnitureState findByRoomAndAP(Room room, AP ap) {
        return new Select().from(FurnitureState.class).where("room = ? and AP = ?", room.getId(), ap.getId()).executeSingle();
    }

    /**
     * fill in the db with initial entries
     * @param aps
     * @param image
     * @param newEx
     * @param oldEx
     */
    public static void initializeRoomFurniture(List<AP> aps, byte[] image, String newEx, String oldEx) {
        for (AP ap : aps) {
            FurnitureState furniture = new FurnitureState(ap.getRoom(), ap);
            furniture.setBrokenMattress(1);
            furniture.setBrokenBookShelf(1);
            furniture.setIsBookShelfOld(true);
            furniture.setBookShelfComment("Kratzer");
            furniture.setBrokenChair(1);
            furniture.setChairComment("Stuhlbein abgebrochen");
            furniture.setChairPicture(image);
            furniture.setName(NAME + newEx + oldEx);
            furniture.save();
        }
    }

    /**
     * fill in the entries in a table to display it in a pdf
     * @param furniture
     * @param posX
     * @param posY
     * @param pageWidth
     * @param headers
     * @param cross
     * @return
     */
    public static Table2 createTable(FurnitureState furniture, float posX, float posY, float pageWidth, String[] headers, byte[] cross) {
        Table2 table = new Table2(posX, posY, pageWidth, 700);

        table.getColumns().add(100);
        table.getColumns().add(42);
        table.getColumns().add(50);
        table.getColumns().add(50);
        table.getColumns().add(120);
        table.getColumns().add(153);

        Row2 header = table.getRows().add(30);
        header.getCellDefault().setVAlign(VAlign.CENTER);
        header.getCellDefault().setAlign(TextAlign.CENTER);
        header.getCellDefault().setFontSize(11);
        header.getCellDefault().setFont(Font.getHelveticaBold());
        for (int i = 0; i < headers.length; i++) {
            header.getCells().add(headers[i]);
        }

        int i = 0;
        for (String s : ROW_NAMES) {
            Row2 row = table.getRows().add(30);
            row.getCellDefault().setFontSize(11);
            row.getCellDefault().setAlign(TextAlign.CENTER);
            row.getCellDefault().setVAlign(VAlign.CENTER);

            row.getCells().add(s);

            row.getCells().add(COUNT.get(i).toString());

            row.getCells().add(createBrokenList(furniture).get(i).toString());

            if (createCheckOldList(furniture).get(i)) {
                row.getCells().add(new Image(cross, 0, 0));
            } else {
                row.getCells().add("");
            }

            if (null != createCommentsList(furniture).get(i)) {
                row.getCells().add(createCommentsList(furniture).get(i));
            } else {
                row.getCells().add("");
            }

            if (null != createPictureList(furniture).get(i)) {
                row.getCells().add(new Image(createPictureList(furniture).get(i), 0, 0));
            } else {
                row.getCells().add("");
            }

            i++;
        }
        return table;
    }
}
