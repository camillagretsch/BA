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
 * Created by camillagretsch on 22.09.16.
 */
@Table(name = "CutleryState")
public class CutleryState extends Model implements EntryStateInterface {

    @Column(name = "brokenFork")
    private int brokenFork = 0;

    @Column(name = "isForkOld")
    private boolean isForkOld = false;

    @Column(name = "forkComment")
    private String forkComment;

    @Column(name = "fork_picture")
    private byte[] forkPicture;

    @Column(name = "brokenKnife")
    private int brokenKnife = 0;

    @Column(name = "isKnifeOld")
    private boolean isKnifeOld = false;

    @Column(name = "knifeComment")
    private String knifeComment;

    @Column(name = "knife_picture")
    private byte[] knifePicture;

    @Column(name = "brokenBigSpoon")
    private int brokenBigSpoon = 0;

    @Column(name = "isBigSpoonOld")
    private boolean isBigSpoonOld = false;

    @Column(name = "bigSpoonComment")
    private String bigSpoonComment;

    @Column(name = "bigSpoon_picture")
    private byte[] bigSpoonPicture;

    @Column(name = "brokenSmallSpoon")
    private int brokenSmallSpoon = 0;

    @Column(name = "isSmallSpoonOld")
    private boolean isSmallSpoonOld = false;

    @Column(name = "smallSpoonComment")
    private String smallSpoonComment;

    @Column(name = "smallSpoon_picture")
    private byte[] smallSpoonPicture;

    @Column(name = "brokenCookingKnife")
    private int brokenCookingKnife = 0;

    @Column(name = "isCookingKnifeOld")
    private boolean isCookingKnifeOld = false;

    @Column(name = "cookingKnifeComment")
    private String cookingKnifeComment;

    @Column(name = "cookingKnife_picture")
    private byte[] cookingKnifePicture;

    @Column(name = "brokenBreadKnife")
    private int brokingBreadKnife = 0;

    @Column(name = "isBreadKnifeOld")
    private boolean isBreadKnifeOld = false;

    @Column(name = "breadKnifeComment")
    private String breadKnifeComment;

    @Column(name = "breadKnife_picture")
    private byte[] breadKnifePicture;

    @Column(name = "brokenPlate")
    private int brokenPlate = 0;

    @Column(name = "isPlateOld")
    private boolean isPlateOld = false;

    @Column(name = "plateComment")
    private String plateComment;

    @Column(name = "plate_picture")
    private byte[] platePicture;

    @Column(name = "brokenSoupPlate")
    private int brokenSoupPlate = 0;

    @Column(name = "isSoupPlateOld")
    private boolean isSoupPlateOld = false;

    @Column(name = "soupPlateComment")
    private String soupPlateComment;

    @Column(name = "soupPlate_picture")
    private byte[] soupPlatePicture;

    @Column(name = "brokenSmallPlate")
    private int brokenSmallPlate = 0;

    @Column(name = "isSmallPlateOld")
    private boolean isSmallPlateOld = false;

    @Column(name = "smallPlateComment")
    private String smallPlateComment;

    @Column(name = "smallPlate_picture")
    private byte[] smallPlatePicture;

    @Column(name = "brokenMug")
    private int brokenMug = 0;

    @Column(name = "isMugOld")
    private boolean isMugOld = false;

    @Column(name = "mugComment")
    private String mugComment;

    @Column(name = "mug_picture")
    private byte[] mugPicture;

    @Column(name = "brokenTeaPot")
    private int brokenTeaPot = 0;

    @Column(name = "isTeaPotOld")
    private boolean isTeaPotOld = false;

    @Column(name = "teaPotComment")
    private String teaPotComment;

    @Column(name = "teaPot_picture")
    private byte[] teaPotPicture;

    @Column(name = "brokenSmallBowl")
    private int brokenSmallBowl = 0;

    @Column(name = "isSmallBowlOld")
    private boolean isSmallBowlOld = false;

    @Column(name = "smallBowlComment")
    private String smallBowlComment;

    @Column(name = "smallBowl_picture")
    private byte[] smallBowlPicture;

    @Column(name = "brokenFryingPan")
    private int brokenFryingPan = 0;

    @Column(name = "isFryingPanOld")
    private boolean isFryingPanOld = false;

    @Column(name = "fryingPanComment")
    private String fryingPanComment;

    @Column(name = "fryingPan_picture")
    private byte[] fryingPanPicture;

    @Column(name = "brokenGlass")
    private int brokenGlass = 0;

    @Column(name = "isGlassOld")
    private boolean isGlassOld = false;

    @Column(name = "glassComment")
    private String glassComment;

    @Column(name = "glass_picture")
    private byte[] glassPicture;

    private static final List<String> ROW_NAMES = Arrays.asList("Essgabel", "Messer", "Esslöffel", "Dessertlöffel", "Küchenmeser", "Brotmesser", "Essteller", "Suppenteller", "Dessertteller", "Becher", "Teekanne", "Schälchen", "Bratpfanne", "Glas");
    private static final List<Integer> COUNT = Arrays.asList(3, 3, 3, 3, 3, 1, 3, 2, 3, 3, 1, 3, 1, 3);


    @Column(name = "kitchen", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Kitchen kitchen;

    @Column(name = "AP", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private AP ap;

    public CutleryState() {
        super();
    }

    public CutleryState(Kitchen kitchen, AP ap) {
        super();
        this.kitchen = kitchen;
        this.ap = ap;
    }

    public void setBrokenFork(int brokenFork) {
        this.brokenFork = brokenFork;
    }

    public int getBrokenFork() {
        return brokenFork;
    }

    public void setIsForkOld(boolean isForkOld) {
        this.isForkOld = isForkOld;
    }

    public boolean isForkOld() {
        return isForkOld;
    }

    public void setForkComment(String forkComment) {
        this.forkComment = forkComment;
    }

    public String getForkComment() {
        return forkComment;
    }

    public void setForkPicture(byte[] forkPicture) {
        this.forkPicture = forkPicture;
    }

    public byte[] getForkPicture() {
        return forkPicture;
    }

    public void setBrokenKnife(int brokenKnife) {
        this.brokenKnife = brokenKnife;
    }

    public int getBrokenKnife() {
        return brokenKnife;
    }

    public void setIsKnifeOld(boolean isKnifeOld) {
        this.isKnifeOld = isKnifeOld;
    }

    public boolean isKnifeOld() {
        return isKnifeOld;
    }

    public void setKnifeComment(String knifeComment) {
        this.knifeComment = knifeComment;
    }

    public String getKnifeComment() {
        return knifeComment;
    }

    public void setKnifePicture(byte[] knifePicture) {
        this.knifePicture = knifePicture;
    }

    public byte[] getKnifePicture() {
        return knifePicture;
    }

    public void setBrokenBigSpoon(int brokenBigSpoon) {
        this.brokenBigSpoon = brokenBigSpoon;
    }

    public int getBrokenBigSpoon() {
        return brokenBigSpoon;
    }

    public void setIsBigSpoonOld(boolean isBigSpoonOld) {
        this.isBigSpoonOld = isBigSpoonOld;
    }

    public boolean isBigSpoonOld() {
        return isBigSpoonOld;
    }

    public void setBigSpoonComment(String bigSpoonComment) {
        this.bigSpoonComment = bigSpoonComment;
    }

    public String getBigSpoonComment() {
        return bigSpoonComment;
    }

    public void setBigSpoonPicture(byte[] bigSpoonPicture) {
        this.bigSpoonPicture = bigSpoonPicture;
    }

    public byte[] getBigSpoonPicture() {
        return bigSpoonPicture;
    }

    public void setBrokenSmallSpoon(int brokenSmallSpoon) {
        this.brokenSmallSpoon = brokenSmallSpoon;
    }

    public int getBrokenSmallSpoon() {
        return brokenSmallSpoon;
    }

    public void setIsSmallSpoonOld(boolean isSmallSpoonOld) {
        this.isSmallSpoonOld = isSmallSpoonOld;
    }

    public boolean isSmallSpoonOld() {
        return isSmallSpoonOld;
    }

    public void setSmallSpoonComment(String smallSpoonComment) {
        this.smallSpoonComment = smallSpoonComment;
    }

    public String getSmallSpoonComment() {
        return smallSpoonComment;
    }

    public void setSmallSpoonPicture(byte[] smallSpoonPicture) {
        this.smallSpoonPicture = smallSpoonPicture;
    }

    public byte[] getSmallSpoonPicture() {
        return smallSpoonPicture;
    }

    public void setBrokenCookingKnife(int brokenCookingKnife) {
        this.brokenCookingKnife = brokenCookingKnife;
    }

    public int getBrokenCookingKnife() {
        return brokenCookingKnife;
    }

    public void setIsCookingKnifeOld(boolean isCookingKnifeOld) {
        this.isCookingKnifeOld = isCookingKnifeOld;
    }

    public boolean isCookingKnifeOld() {
        return isCookingKnifeOld;
    }

    public void setCookingKnifeComment(String cookingKnifeComment) {
        this.cookingKnifeComment = cookingKnifeComment;
    }

    public String getCookingKnifeComment() {
        return cookingKnifeComment;
    }

    public void setCookingKnifePicture(byte[] cookingKnifePicture) {
        this.cookingKnifePicture = cookingKnifePicture;
    }

    public byte[] getCookingKnifePicture() {
        return cookingKnifePicture;
    }

    public void setBrokingBreadKnife(int brokingBreadKnife) {
        this.brokingBreadKnife = brokingBreadKnife;
    }

    public int getBrokingBreadKnife() {
        return brokingBreadKnife;
    }

    public void setIsBreadKnifeOld(boolean isBreadKnifeOld) {
        this.isBreadKnifeOld = isBreadKnifeOld;
    }

    public boolean isBreadKnifeOld() {
        return isBreadKnifeOld;
    }

    public void setBreadKnifeComment(String breadKnifeComment) {
        this.breadKnifeComment = breadKnifeComment;
    }

    public String getBreadKnifeComment() {
        return breadKnifeComment;
    }

    public void setBreadKnifePicture(byte[] breadKnifePicture) {
        this.breadKnifePicture = breadKnifePicture;
    }

    public byte[] getBreadKnifePicture() {
        return breadKnifePicture;
    }

    public void setBrokenPlate(int brokenPlate) {
        this.brokenPlate = brokenPlate;
    }

    public int getBrokenPlate() {
        return brokenPlate;
    }

    public void setIsPlateOld(boolean isPlateOld) {
        this.isPlateOld = isPlateOld;
    }

    public boolean isPlateOld() {
        return isPlateOld;
    }

    public void setPlateComment(String plateComment) {
        this.plateComment = plateComment;
    }

    public String getPlateComment() {
        return plateComment;
    }

    public void setPlatePicture(byte[] platePicture) {
        this.platePicture = platePicture;
    }

    public byte[] getPlatePicture() {
        return platePicture;
    }

    public void setBrokenSoupPlate(int brokenSoupPlate) {
        this.brokenSoupPlate = brokenSoupPlate;
    }

    public int getBrokenSoupPlate() {
        return brokenSoupPlate;
    }

    public void setIsSoupPlateOld(boolean isSoupPlateOld) {
        this.isSoupPlateOld = isSoupPlateOld;
    }

    public boolean isSoupPlateOld() {
        return isSoupPlateOld;
    }

    public void setSoupPlateComment(String soupPlateComment) {
        this.soupPlateComment = soupPlateComment;
    }

    public String getSoupPlateComment() {
        return soupPlateComment;
    }

    public void setSoupPlatePicture(byte[] soupPlatePicture) {
        this.soupPlatePicture = soupPlatePicture;
    }

    public byte[] getSoupPlatePicture() {
        return soupPlatePicture;
    }

    public void setBrokenSmallPlate(int brokenSmallPlate) {
        this.brokenSmallPlate = brokenSmallPlate;
    }

    public int getBrokenSmallPlate() {
        return brokenSmallPlate;
    }

    public void setIsSmallPlateOld(boolean isSmallPlateOld) {
        this.isSmallPlateOld = isSmallPlateOld;
    }

    public boolean isSmallPlateOld() {
        return isSmallPlateOld;
    }

    public void setSmallPlateComment(String smallPlateComment) {
        this.smallPlateComment = smallPlateComment;
    }

    public String getSmallPlateComment() {
        return smallPlateComment;
    }

    public void setSmallPlatePicture(byte[] smallPlatePicture) {
        this.smallPlatePicture = smallPlatePicture;
    }

    public byte[] getSmallPlatePicture() {
        return smallPlatePicture;
    }

    public void setBrokenMug(int brokenMug) {
        this.brokenMug = brokenMug;
    }

    public int getBrokenMug() {
        return brokenMug;
    }

    public void setIsMugOld(boolean isMugOld) {
        this.isMugOld = isMugOld;
    }

    public boolean isMugOld() {
        return isMugOld;
    }

    public void setMugComment(String mugComment) {
        this.mugComment = mugComment;
    }

    public String getMugComment() {
        return mugComment;
    }

    public void setMugPicture(byte[] mugPicture) {
        this.mugPicture = mugPicture;
    }

    public byte[] getMugPicture() {
        return mugPicture;
    }

    public void setBrokenTeaPot(int brokenTeaPot) {
        this.brokenTeaPot = brokenTeaPot;
    }

    public int getBrokenTeaPot() {
        return brokenTeaPot;
    }

    public void setIsTeaPotOld(boolean isTeaPotOld) {
        this.isTeaPotOld = isTeaPotOld;
    }

    public boolean isTeaPotOld() {
        return isTeaPotOld;
    }

    public void setTeaPotComment(String teaPotComment) {
        this.teaPotComment = teaPotComment;
    }

    public String getTeaPotComment() {
        return teaPotComment;
    }

    public void setTeaPotPicture(byte[] teaPotPicture) {
        this.teaPotPicture = teaPotPicture;
    }

    public byte[] getTeaPotPicture() {
        return teaPotPicture;
    }

    public void setBrokenSmallBowl(int brokenSmallBowl) {
        this.brokenSmallBowl = brokenSmallBowl;
    }

    public int getBrokenSmallBowl() {
        return brokenSmallBowl;
    }

    public void setIsSmallBowlOld(boolean isSmallBowlOld) {
        this.isSmallBowlOld = isSmallBowlOld;
    }

    public boolean isSmallBowlOld() {
        return isSmallBowlOld;
    }

    public void setSmallBowlComment(String smallBowlComment) {
        this.smallBowlComment = smallBowlComment;
    }

    public String getSmallBowlComment() {
        return smallBowlComment;
    }

    public void setSmallBowlPicture(byte[] smallBowlPicture) {
        this.smallBowlPicture = smallBowlPicture;
    }

    public byte[] getSmallBowlPicture() {
        return smallBowlPicture;
    }

    public void setBrokenFryingPan(int brokenFryingPan) {
        this.brokenFryingPan = brokenFryingPan;
    }

    public int getBrokenFryingPan() {
        return brokenFryingPan;
    }

    public void setIsFryingPanOld(boolean isFryingPanOld) {
        this.isFryingPanOld = isFryingPanOld;
    }

    public boolean isFryingPanOld() {
        return isFryingPanOld;
    }

    public void setFryingPanComment(String fryingPanComment) {
        this.fryingPanComment = fryingPanComment;
    }

    public String getFryingPanComment() {
        return fryingPanComment;
    }

    public void setFryingPanPicture(byte[] fryingPanPicture) {
        this.fryingPanPicture = fryingPanPicture;
    }

    public byte[] getFryingPanPicture() {
        return fryingPanPicture;
    }

    public void setBrokenGlass(int brokenGlass) {
        this.brokenGlass = brokenGlass;
    }

    public int getBrokenGlass() {
        return brokenGlass;
    }

    public void setIsGlassOld(boolean isGlassOld) {
        this.isGlassOld = isGlassOld;
    }

    public boolean isGlassOld() {
        return isGlassOld;
    }

    public void setGlassComment(String glassComment) {
        this.glassComment = glassComment;
    }

    public String getGlassComment() {
        return glassComment;
    }

    public void setGlassPicture(byte[] glassPicture) {
        this.glassPicture = glassPicture;
    }

    public byte[] getGlassPicture() {
        return glassPicture;
    }

    public List<String> getRowNames() {
        return ROW_NAMES;
    }

    public List<Integer> getCOUNT() {
        return COUNT;
    }

    private List<Integer> createBrokenList(CutleryState cutlery) {
        return new ArrayList<>(Arrays.asList(cutlery.getBrokenFork(), cutlery.getBrokenKnife(), cutlery.getBrokenBigSpoon(), cutlery.getBrokenSmallSpoon(), cutlery.getBrokenCookingKnife(), cutlery.getBrokingBreadKnife(), cutlery.getBrokenPlate(), cutlery.getBrokenSoupPlate(), cutlery.getBrokenSmallPlate(), cutlery.getBrokenMug(), cutlery.getBrokenTeaPot(), cutlery.getBrokenSmallBowl(), cutlery.getBrokenFryingPan(), cutlery.getBrokenGlass()));
    }

    private List<String> createCommentsList(CutleryState cutlery) {
        return new ArrayList<>(Arrays.asList(cutlery.getForkComment(), cutlery.getKnifeComment(), cutlery.getBigSpoonComment(), cutlery.getSmallSpoonComment(), cutlery.getCookingKnifeComment(), cutlery.getBreadKnifeComment(), cutlery.getPlateComment(), cutlery.getSoupPlateComment(), cutlery.getSmallPlateComment(), cutlery.getMugComment(), cutlery.getTeaPotComment(), cutlery.getSmallBowlComment(), cutlery.getFryingPanComment(), cutlery.getGlassComment()));
    }

    private List<Boolean> createCheckOldList(CutleryState cutlery) {
        return new ArrayList<>(Arrays.asList(cutlery.isForkOld(), cutlery.isKnifeOld(), cutlery.isBigSpoonOld(), cutlery.isSmallSpoonOld(), cutlery.isCookingKnifeOld(), cutlery.isBreadKnifeOld(), cutlery.isPlateOld(), cutlery.isSoupPlateOld(), cutlery.isSmallPlateOld(), cutlery.isMugOld(), cutlery.isTeaPotOld(), cutlery.isSmallBowlOld(), cutlery.isFryingPanOld(), cutlery.isGlassOld()));
    }

    private List<byte[]> createPictureList(CutleryState cutlery) {
        return new ArrayList<>(Arrays.asList(cutlery.getForkPicture(), cutlery.getKnifePicture(), cutlery.getBigSpoonPicture(), cutlery.getSmallSpoonPicture(), cutlery.getCookingKnifePicture(), cutlery.getBreadKnifePicture(), cutlery.getPlatePicture(), cutlery.getSoupPlatePicture(), cutlery.getSmallPlatePicture(), cutlery.getMugPicture(), cutlery.getTeaPotPicture(), cutlery.getSmallBowlPicture(), cutlery.getFryingPanPicture(), cutlery.getGlassPicture()));
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

    @Override
    public byte[] getPictureAtPosition(int pos) {
        return createPictureList(this).get(pos);
    }

    @Override
    public void getEntries(DataGridFragment frag) {
        frag.setHeaderVariante2();
        frag.getRowNames().addAll(this.ROW_NAMES);
        frag.getCount().addAll(this.COUNT);
        frag.getCheckOld().addAll(createCheckOldList(this));
        frag.getComments().addAll(createCommentsList(this));
        frag.getCountBroken().addAll(createBrokenList(this));
        frag.getCurrentAP().setLastOpend(this);
        frag.setTableContentVarainte2();
    }

    @Override
    public void duplicateEntries(AP ap, AP oldAP) {
        if (ApartmentType.STUDIO.equals(ap.getApartment().getType())) {
            CutleryState oldCutlery = CutleryState.findByKitchenAndAP(oldAP.getKitchen(), oldAP);
            this.copyOldEntries(oldCutlery);
            this.save();
        }
    }

    private void copyOldEntries(CutleryState oldCutlery) {
        this.setBrokenFork(oldCutlery.getBrokenFork());
        this.setIsForkOld(oldCutlery.isForkOld());
        this.setForkComment(oldCutlery.getForkComment());
        this.setForkPicture(oldCutlery.getForkPicture());
        this.setBrokenKnife(oldCutlery.getBrokenKnife());
        this.setIsKnifeOld(oldCutlery.isKnifeOld());
        this.setKnifeComment(oldCutlery.getKnifeComment());
        this.setKnifePicture(oldCutlery.getKnifePicture());
        this.setBrokenBigSpoon(oldCutlery.getBrokenBigSpoon());
        this.setIsBigSpoonOld(oldCutlery.isBigSpoonOld());
        this.setBigSpoonComment(oldCutlery.getBigSpoonComment());
        this.setBigSpoonPicture(oldCutlery.getBigSpoonPicture());
        this.setBrokenSmallSpoon(oldCutlery.getBrokenSmallSpoon());
        this.setIsSmallSpoonOld(oldCutlery.isSmallSpoonOld());
        this.setSmallSpoonComment(oldCutlery.getSmallSpoonComment());
        this.setSmallSpoonPicture(oldCutlery.getSmallSpoonPicture());
        this.setBrokenCookingKnife(oldCutlery.getBrokenCookingKnife());
        this.setIsCookingKnifeOld(oldCutlery.isCookingKnifeOld());
        this.setCookingKnifeComment(oldCutlery.getCookingKnifeComment());
        this.setCookingKnifePicture(oldCutlery.getCookingKnifePicture());
        this.setBrokingBreadKnife(oldCutlery.getBrokingBreadKnife());
        this.setIsBreadKnifeOld(oldCutlery.isBreadKnifeOld());
        this.setBreadKnifeComment(oldCutlery.getBreadKnifeComment());
        this.setBreadKnifePicture(oldCutlery.getBreadKnifePicture());
        this.setBrokenPlate(oldCutlery.getBrokenPlate());
        this.setIsPlateOld(oldCutlery.isPlateOld());
        this.setPlateComment(oldCutlery.getPlateComment());
        this.setPlatePicture(oldCutlery.getPlatePicture());
        this.setBrokenSoupPlate(oldCutlery.getBrokenSoupPlate());
        this.setIsSoupPlateOld(oldCutlery.isSoupPlateOld());
        this.setSoupPlateComment(oldCutlery.getSoupPlateComment());
        this.setSoupPlatePicture(oldCutlery.getSoupPlatePicture());
        this.setBrokenSmallPlate(oldCutlery.getBrokenSmallPlate());
        this.setIsSmallPlateOld(oldCutlery.isSmallPlateOld());
        this.setSmallPlateComment(oldCutlery.getSmallPlateComment());
        this.setSmallPlatePicture(oldCutlery.getSmallPlatePicture());
        this.setBrokenMug(oldCutlery.getBrokenMug());
        this.setIsMugOld(oldCutlery.isMugOld());
        this.setMugComment(oldCutlery.getMugComment());
        this.setMugPicture(oldCutlery.getMugPicture());
        this.setBrokenTeaPot(oldCutlery.getBrokenTeaPot());
        this.setIsTeaPotOld(oldCutlery.isTeaPotOld());
        this.setTeaPotComment(oldCutlery.getTeaPotComment());
        this.setTeaPotPicture(oldCutlery.getTeaPotPicture());
        this.setBrokenSmallBowl(oldCutlery.getBrokenSmallBowl());
        this.setIsSmallBowlOld(oldCutlery.isSmallBowlOld());
        this.setSmallBowlComment(oldCutlery.getSmallBowlComment());
        this.setSmallBowlPicture(oldCutlery.getSmallBowlPicture());
        this.setBrokenFryingPan(oldCutlery.getBrokenFryingPan());
        this.setIsFryingPanOld(oldCutlery.isFryingPanOld());
        this.setFryingPanComment(oldCutlery.getFryingPanComment());
        this.setFryingPanPicture(oldCutlery.getFryingPanPicture());
        this.setBrokenGlass(oldCutlery.getBrokenGlass());
        this.setIsGlassOld(oldCutlery.isGlassOld());
        this.setGlassComment(oldCutlery.getGlassComment());
        this.setGlassPicture(oldCutlery.getGlassPicture());
    }

    @Override
    public void createNewEntry(AP ap) {
        if (ApartmentType.STUDIO.equals(ap.getApartment().getType())) {
            this.save();
        }
    }

    @Override
    public void saveCheckEntries(List<Boolean> check) {

    }

    public void saveBrokenEntries(List<Integer> countBroken) {
        this.setBrokenFork(countBroken.get(0));
        this.setBrokenKnife(countBroken.get(1));
        this.setBrokenBigSpoon(countBroken.get(2));
        this.setBrokenSmallSpoon(countBroken.get(3));
        this.setBrokenCookingKnife(countBroken.get(4));
        this.setBrokingBreadKnife(countBroken.get(5));
        this.setBrokenPlate(countBroken.get(6));
        this.setBrokenSoupPlate(countBroken.get(7));
        this.setBrokenSmallPlate(countBroken.get(8));
        this.setBrokenMug(countBroken.get(9));
        this.setBrokenTeaPot(countBroken.get(10));
        this.setBrokenSmallBowl(countBroken.get(11));
        this.setBrokenFryingPan(countBroken.get(12));
        this.setBrokenGlass(countBroken.get(13));
        this.save();
    }

    @Override
    public void saveCheckOldEntries(List<Boolean> checkOld) {
        this.setIsForkOld(checkOld.get(0));
        this.setIsKnifeOld(checkOld.get(1));
        this.setIsBigSpoonOld(checkOld.get(2));
        this.setIsSmallSpoonOld(checkOld.get(3));
        this.setIsCookingKnifeOld(checkOld.get(4));
        this.setIsBreadKnifeOld(checkOld.get(5));
        this.setIsPlateOld(checkOld.get(6));
        this.setIsSoupPlateOld(checkOld.get(7));
        this.setIsSmallPlateOld(checkOld.get(8));
        this.setIsMugOld(checkOld.get(9));
        this.setIsTeaPotOld(checkOld.get(10));
        this.setIsSmallBowlOld(checkOld.get(11));
        this.setIsFryingPanOld(checkOld.get(12));
        this.setIsGlassOld(checkOld.get(13));
        this.save();
    }

    @Override
    public void saveCommentsEntries(List<String> comments) {
        this.setForkComment(comments.get(0));
        this.setKnifeComment(comments.get(1));
        this.setBigSpoonComment(comments.get(2));
        this.setSmallSpoonComment(comments.get(3));
        this.setCookingKnifeComment(comments.get(4));
        this.setBreadKnifeComment(comments.get(5));
        this.setPlateComment(comments.get(6));
        this.setSoupPlateComment(comments.get(7));
        this.setSmallPlateComment(comments.get(8));
        this.setMugComment(comments.get(9));
        this.setTeaPotComment(comments.get(10));
        this.setSmallBowlComment(comments.get(11));
        this.setFryingPanComment(comments.get(12));
        this.setGlassComment(comments.get(13));
        this.save();
    }

    @Override
    public void savePicture(int pos, byte[] picture) {
        switch (pos) {
            case 0:
                this.setForkPicture(picture);
                break;
            case 1:
                this.setKnifePicture(picture);
                break;
            case 2:
                this.setBigSpoonPicture(picture);
                break;
            case 3:
                this.setSmallSpoonPicture(picture);
                break;
            case 4:
                this.setCookingKnifePicture(picture);
                break;
            case 5:
                this.setBreadKnifePicture(picture);
                break;
            case 6:
                this.setPlatePicture(picture);
                break;
            case 7:
                this.setSoupPlatePicture(picture);
                break;
            case 8:
                this.setSmallPlatePicture(picture);
                break;
            case 9:
                this.setMugPicture(picture);
                break;
            case 10:
                this.setTeaPotPicture(picture);
                break;
            case 11:
                this.setSmallBowlPicture(picture);
                break;
            case 12:
                this.setFryingPanPicture(picture);
                break;
            case 13:
                this.setGlassPicture(picture);
                break;
        }
        this.save();
    }

    public static CutleryState findByKitchenAndAP(Kitchen kitchen, AP ap) {
        return new Select().from(CutleryState.class).where("kitchen = ? and AP = ?", kitchen.getId(), ap.getId()).executeSingle();
    }

    public static CutleryState findById(long id) {
        return new Select().from(CutleryState.class).where("id = ?", id).executeSingle();
    }

    public static void initializeKitchenCutlery(List<AP> aps, byte[] image) {
        for (AP ap : aps) {
            CutleryState cutlery = new CutleryState(ap.getKitchen(), ap);
            cutlery.setBrokenBigSpoon(2);
            cutlery.setBrokenFryingPan(1);
            cutlery.setFryingPanComment("Kratzer in der Pfanne");
            cutlery.setFryingPanPicture(image);
            cutlery.save();
        }
    }
}
