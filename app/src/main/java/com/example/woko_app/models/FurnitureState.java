package com.example.woko_app.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
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
    private String bedFrameComment;

    @Column(name = "brokenMattress")
    private int brokenMattress = 0;

    @Column(name = "isMattressOld")
    private boolean isMattressOld = false;

    @Column(name = "mattressComment")
    private String mattressComment;

    @Column(name = "brokenDesk")
    private int brokenDesk = 0;

    @Column(name = "isDeskOld")
    private boolean isDeskOld = false;

    @Column(name = "deskComment")
    private String deskComment;

    @Column(name = "brokenBookShelf")
    private int brokenBookShelf = 0;

    @Column(name = "isBookShelfOld")
    private boolean isBookShelfOld = false;

    @Column(name = "bookShelfComment")
    private String bookShelfComment;

    @Column(name = "brokenCupboard")
    private int brokenCupboard = 0;

    @Column(name = "isCupboardOld")
    private boolean isCupboardOld = false;

    @Column(name = "cupboardComment")
    private String cupboardComment;

    @Column(name = "brokenChair")
    private int brokenChair = 0;

    @Column(name = "isChairOld")
    private boolean isChairOld = false;

    @Column(name = "chairComment")
    private String chairComment;

    @Column(name = "brokenCurtain")
    private int brokenCurtain = 0;

    @Column(name = "isCurtainOld")
    private boolean isCurtainOld = false;

    @Column(name = "curtainComment")
    private String curtainComment;

    @Column(name = "brokenDayCurtain")
    private int brokenDayCurtain = 0;

    @Column(name = "isDayCurtainOld")
    private boolean isDayCurtainOld = false;

    @Column(name = "dayCurtainComment")
    private String dayCurtainComment;

    @Column(name = "brokenClothesHanger")
    private int brokenClothesHanger = 0;

    @Column(name = "isClothesHangerOld")
    private boolean isClothesHangerOld = false;

    @Column(name = "clothesHangerComment")
    private String clothesHangerComment;

    @Column(name = "brokenBlanket")
    private int brokenBlanket = 0;

    @Column(name = "isBlanketOld")
    private boolean isBlanketOld = false;

    @Column(name = "blanketComment")
    private String blanketComment;

    @Column(name = "brokenPillow")
    private int brokenPillow = 0;

    @Column(name = "isPillowOld")
    private boolean isPillowOld = false;

    @Column(name = "pillowComment")
    private String pillowComment;

    @Column(name = "brokenBedSheet")
    private int brokenBedSheet = 0;

    @Column(name = "isBedSheetOld")
    private boolean isBedSheetOld = false;

    @Column(name = "bedSheetComment")
    private String bedSheetComment;

    @Column(name = "brokenBedLinen")
    private int brokenBedLinen = 0;

    @Column(name = "isBedLinenOld")
    private boolean isBedLinenOld = false;

    @Column(name = "bedLinenComment")
    private String bedLinenComment;

    @Column(name = "brokenTVBox")
    private int brokenTVBox = 0;

    @Column(name = "isTVBoxOld")
    private boolean isTVBoxOld = false;

    @Column(name = "TVBoxComment")
    private String TVBoxComment;

    @Column(name = "brokenTVControl")
    private int brokenTVControl;

    @Column(name = "isTVControlOld")
    private boolean isTVControlOld = false;

    @Column(name = "TVControlComment")
    private String TVControlComment;

    @Column(name = "brokenAccessCard")
    private int brokenAccessCard = 0;

    @Column(name = "isAccessCardOld")
    private boolean isAccessCardOld = false;

    @Column(name = "accessCardComment")
    private String accessCardComment;

    private static final List<String> ROW_NAMES = Arrays.asList("Bettgestell", "Matratze", "Schreibtisch", "Bücherregal", "Schrank", "Stuhl", "Nachtvorhang", "Tagesvorhang", "Kleiderbügel", "Decke", "Kissen", "Spannbettlaken", "Bettbezüge", "TV-Empfänger Box inkl. Kabel", "TV-Fernbedingung", "TV Sat Access Card");

    private static final List<Integer> COUNT = Arrays.asList(1, 1, 1, 1, 1, 2, 1, 1, 8, 1, 1, 1, 1, 1, 1, 1);

    @Column(name = "room", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private Room room;

    @Column(name = "AP", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private AP ap;

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

    public List<String> getRowNames() {
        return ROW_NAMES;
    }

    public List<Integer> getCOUNT() {
        return COUNT;
    }

    public Room getRoom() {
        return room;
    }

    public AP getAp() {
        return ap;
    }

    private List<Integer> createBrokenList(FurnitureState furniture) {
        return new ArrayList<>(Arrays.asList(furniture.getBrokenBedFrame(), furniture.getBrokenMattress(), furniture.getBrokenDesk(), furniture.getBrokenBookShelf(), furniture.getBrokenCupboard(), furniture.getBrokenChair(), furniture.getBrokenCurtain(), furniture.getBrokenDayCurtain(), furniture.getBrokenClothesHanger(), furniture.getBrokenBlanket(), furniture.getBrokenPillow(), furniture.getBrokenBedSheet(), furniture.getBrokenBedLinen(), furniture.getBrokenTVBox(), furniture.getBrokenTVControl(), furniture.getBrokenAccessCard()));
    }
    private List<String> createCommentsList(FurnitureState furniture) {
        return new ArrayList<>(Arrays.asList(furniture.getBedFrameComment(), furniture.getMattressComment(), furniture.getDeskComment(), furniture.getBookShelfComment(), furniture.getCupboardComment(), furniture.getChairComment(), furniture.getCurtainComment(), furniture.getDayCurtainComment(), furniture.getClothesHangerComment(), furniture.getBlanketComment(), furniture.getPillowComment(), furniture.getBedSheetComment(), furniture.getBedLinenComment(), furniture.getTVBoxComment(), furniture.getTVControlComment(), furniture.getAccessCardComment()));
    }

    private List<Boolean> createCheckOldList(FurnitureState furniture) {
        return new ArrayList<>(Arrays.asList(furniture.isBedFrameOld(), furniture.isMattressOld(), furniture.isDeskOld(), furniture.isBookShelfOld(), furniture.isCupboardOld(), furniture.isChairOld(), furniture.isCurtainOld(), furniture.isDayCurtainOld(), furniture.isClothesHangerOld(), furniture.isBlanketOld(), furniture.isPillowOld(), furniture.isBedSheetOld(), furniture.isBedLinenOld(), furniture.isTVBoxOld(), furniture.isTVControlOld(), furniture.isAccessCardOld()));
    }

    @Override
    public void getEntries(DataGridFragment frag) {
        frag.setHeaderVariante2();
        frag.getRowNames().addAll(this.ROW_NAMES);
        frag.getCheckOld().addAll(createCheckOldList(this));
        frag.getComments().addAll(createCommentsList(this));
        frag.getCount().addAll(this.COUNT);
        frag.getCountBroken().addAll(createBrokenList(this));
        frag.setTableContentVarainte2();
    }

    @Override
    public void duplicateEntries(AP ap, AP oldAP) {
        FurnitureState oldFurniture = FurnitureState.findByRoomAndAP(oldAP.getRoom(), oldAP);
        this.copyOldEntries(oldFurniture);
        this.save();
    }

    public void copyOldEntries(FurnitureState oldFurniture) {
        this.setBrokenBedFrame(oldFurniture.getBrokenBedFrame());
        this.setIsBedFrameOld(oldFurniture.isBedFrameOld());
        this.setBedFrameComment(oldFurniture.getBedFrameComment());
        this.setBrokenMattress(oldFurniture.getBrokenMattress());
        this.setIsMattressOld(oldFurniture.isMattressOld());
        this.setMattressComment(oldFurniture.getMattressComment());
        this.setBrokenDesk(oldFurniture.getBrokenDesk());
        this.setIsDeskOld(oldFurniture.isDeskOld());
        this.setDeskComment(oldFurniture.getDeskComment());
        this.setBrokenBookShelf(oldFurniture.getBrokenBookShelf());
        this.setIsBookShelfOld(oldFurniture.isBookShelfOld());
        this.setBookShelfComment(oldFurniture.getBookShelfComment());
        this.setBrokenCupboard(oldFurniture.getBrokenCupboard());
        this.setIsCupboardOld(oldFurniture.isCupboardOld());
        this.setCupboardComment(oldFurniture.getCupboardComment());
        this.setBrokenChair(oldFurniture.getBrokenChair());
        this.setIsChairOld(oldFurniture.isChairOld());
        this.setChairComment(oldFurniture.getChairComment());
        this.setBrokenCurtain(oldFurniture.getBrokenCurtain());
        this.setIsCurtainOld(oldFurniture.isCurtainOld());
        this.setCurtainComment(oldFurniture.getCurtainComment());
        this.setBrokenDayCurtain(oldFurniture.getBrokenDayCurtain());
        this.setIsDayCurtainOld(oldFurniture.isDayCurtainOld());
        this.setDayCurtainComment(oldFurniture.getDayCurtainComment());
        this.setBrokenClothesHanger(oldFurniture.getBrokenClothesHanger());
        this.setIsClothesHangerOld(oldFurniture.isClothesHangerOld());
        this.setClothesHangerComment(oldFurniture.getClothesHangerComment());
        this.setBrokenBlanket(oldFurniture.getBrokenBlanket());
        this.setIsBlanketOld(oldFurniture.isBlanketOld());
        this.setBlanketComment(oldFurniture.getBlanketComment());
        this.setBrokenPillow(oldFurniture.getBrokenPillow());
        this.setIsPillowOld(oldFurniture.isPillowOld());
        this.setPillowComment(oldFurniture.getPillowComment());
        this.setBrokenBedSheet(oldFurniture.getBrokenBedSheet());
        this.setIsBedSheetOld(oldFurniture.isBedSheetOld());
        this.setBedSheetComment(oldFurniture.getBedSheetComment());
        this.setBrokenBedLinen(oldFurniture.getBrokenBedLinen());
        this.setIsBedLinenOld(oldFurniture.isBedLinenOld());
        this.setBedLinenComment(oldFurniture.getBedLinenComment());
        this.setBrokenTVBox(oldFurniture.getBrokenTVBox());
        this.setIsTVBoxOld(oldFurniture.isTVBoxOld());
        this.setTVBoxComment(oldFurniture.getTVBoxComment());
        this.setBrokenTVControl(oldFurniture.getBrokenTVControl());
        this.setIsTVControlOld(oldFurniture.isTVControlOld());
        this.setTVControlComment(oldFurniture.getTVControlComment());
        this.setBrokenAccessCard(oldFurniture.getBrokenAccessCard());
        this.setIsAccessCardOld(oldFurniture.isAccessCardOld());
        this.setAccessCardComment(oldFurniture.getAccessCardComment());
    }

    @Override
    public void createNewEntry(AP ap) {
        this.save();
    }

    @Override
    public void saveCheckEntries(List<Boolean> check) {

    }

    public void saveBrokenEntries(List<Integer> countBroken) {
        this.setBrokenBedFrame(countBroken.get(0));
        this.setBrokenMattress(countBroken.get(1));
        this.setBrokenDesk(countBroken.get(2));
        this.setBrokenBookShelf(countBroken.get(3));
        this.setBrokenCupboard(countBroken.get(4));
        this.setBrokenChair(countBroken.get(5));
        this.setBrokenCurtain(countBroken.get(6));
        this.setBrokenDayCurtain(countBroken.get(7));
        this.setBrokenClothesHanger(countBroken.get(8));
        this.setBrokenBlanket(countBroken.get(9));
        this.setBrokenPillow(countBroken.get(10));
        this.setBrokenBedSheet(countBroken.get(11));
        this.setBrokenBedLinen(countBroken.get(12));
        this.setBrokenTVBox(countBroken.get(13));
        this.setBrokenTVControl(countBroken.get(14));
        this.setBrokenAccessCard(countBroken.get(15));
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

    public static FurnitureState findByRoomAndAP(Room room, AP ap) {
        return new Select().from(FurnitureState.class).where("room = ? and AP = ?", room.getId(), ap.getId()).executeSingle();
    }

    public static FurnitureState findById(long id) {
        return new Select().from(FurnitureState.class).where("id = ?", id).executeSingle();
    }

    public static void initializeRoomFurniture(List<AP> aps) {
        for (AP ap : aps) {
            FurnitureState furniture = new FurnitureState(ap.getRoom(), ap);
            furniture.setBrokenMattress(1);
            furniture.setBrokenBookShelf(1);
            furniture.setIsBookShelfOld(true);
            furniture.setBookShelfComment("Kratzer");
            furniture.save();
        }
    }
}
