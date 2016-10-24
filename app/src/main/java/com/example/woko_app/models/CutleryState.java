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
import com.example.woko_app.R;
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
    private String forkComment = null;

    @Column(name = "fork_picture")
    private byte[] forkPicture = null;

    @Column(name = "brokenKnife")
    private int brokenKnife = 0;

    @Column(name = "isKnifeOld")
    private boolean isKnifeOld = false;

    @Column(name = "knifeComment")
    private String knifeComment = null;

    @Column(name = "knife_picture")
    private byte[] knifePicture = null;

    @Column(name = "brokenBigSpoon")
    private int brokenBigSpoon = 0;

    @Column(name = "isBigSpoonOld")
    private boolean isBigSpoonOld = false;

    @Column(name = "bigSpoonComment")
    private String bigSpoonComment = null;

    @Column(name = "bigSpoon_picture")
    private byte[] bigSpoonPicture = null;

    @Column(name = "brokenSmallSpoon")
    private int brokenSmallSpoon = 0;

    @Column(name = "isSmallSpoonOld")
    private boolean isSmallSpoonOld = false;

    @Column(name = "smallSpoonComment")
    private String smallSpoonComment = null;

    @Column(name = "smallSpoon_picture")
    private byte[] smallSpoonPicture = null;

    @Column(name = "brokenCookingKnife")
    private int brokenCookingKnife = 0;

    @Column(name = "isCookingKnifeOld")
    private boolean isCookingKnifeOld = false;

    @Column(name = "cookingKnifeComment")
    private String cookingKnifeComment = null;

    @Column(name = "cookingKnife_picture")
    private byte[] cookingKnifePicture = null;

    @Column(name = "brokenBreadKnife")
    private int brokingBreadKnife = 0;

    @Column(name = "isBreadKnifeOld")
    private boolean isBreadKnifeOld = false;

    @Column(name = "breadKnifeComment")
    private String breadKnifeComment = null;

    @Column(name = "breadKnife_picture")
    private byte[] breadKnifePicture = null;

    @Column(name = "brokenPlate")
    private int brokenPlate = 0;

    @Column(name = "isPlateOld")
    private boolean isPlateOld = false;

    @Column(name = "plateComment")
    private String plateComment = null;

    @Column(name = "plate_picture")
    private byte[] platePicture = null;

    @Column(name = "brokenSoupPlate")
    private int brokenSoupPlate = 0;

    @Column(name = "isSoupPlateOld")
    private boolean isSoupPlateOld = false;

    @Column(name = "soupPlateComment")
    private String soupPlateComment = null;

    @Column(name = "soupPlate_picture")
    private byte[] soupPlatePicture = null;

    @Column(name = "brokenSmallPlate")
    private int brokenSmallPlate = 0;

    @Column(name = "isSmallPlateOld")
    private boolean isSmallPlateOld = false;

    @Column(name = "smallPlateComment")
    private String smallPlateComment = null;

    @Column(name = "smallPlate_picture")
    private byte[] smallPlatePicture = null;

    @Column(name = "brokenMug")
    private int brokenMug = 0;

    @Column(name = "isMugOld")
    private boolean isMugOld = false;

    @Column(name = "mugComment")
    private String mugComment = null;

    @Column(name = "mug_picture")
    private byte[] mugPicture = null;

    @Column(name = "brokenTeaPot")
    private int brokenTeaPot = 0;

    @Column(name = "isTeaPotOld")
    private boolean isTeaPotOld = false;

    @Column(name = "teaPotComment")
    private String teaPotComment = null;

    @Column(name = "teaPot_picture")
    private byte[] teaPotPicture = null;

    @Column(name = "brokenSmallBowl")
    private int brokenSmallBowl = 0;

    @Column(name = "isSmallBowlOld")
    private boolean isSmallBowlOld = false;

    @Column(name = "smallBowlComment")
    private String smallBowlComment = null;

    @Column(name = "smallBowl_picture")
    private byte[] smallBowlPicture = null;

    @Column(name = "brokenFryingPan")
    private int brokenFryingPan = 0;

    @Column(name = "isFryingPanOld")
    private boolean isFryingPanOld = false;

    @Column(name = "fryingPanComment")
    private String fryingPanComment = null;

    @Column(name = "fryingPan_picture")
    private byte[] fryingPanPicture = null;

    @Column(name = "brokenGlass")
    private int brokenGlass = 0;

    @Column(name = "isGlassOld")
    private boolean isGlassOld = false;

    @Column(name = "glassComment")
    private String glassComment = null;

    @Column(name = "glass_picture")
    private byte[] glassPicture = null;

    @Column(name = "kitchen", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Kitchen kitchen;

    @Column(name = "AP", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private AP ap;

    @Column(name = "name")
    private String name = "Pfannen, Geschirr, Besteck";

    private static final List<String> ROW_NAMES = Arrays.asList("Essgabel", "Messer", "Esslöffel", "Dessertlöffel", "Küchenmeser", "Brotmesser", "Essteller", "Suppenteller", "Dessertteller", "Becher", "Teekanne", "Schälchen", "Bratpfanne", "Glas");
    private static final List<Integer> COUNT = Arrays.asList(3, 3, 3, 3, 3, 1, 3, 2, 3, 3, 1, 3, 1, 3);

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

    @Override
    public byte[] getPictureAtPosition(int pos) {
        return createPictureList(this).get(pos);
    }

    public int getCountBrokenAtPosition(int pos) {
        return createBrokenList(this).get(pos);
    }

    @Override
    public int countPicturesOfLast5Years(int pos, EntryStateInterface entryStateInterface) {
        CutleryState cutlery = (CutleryState) entryStateInterface;
        AP ap = cutlery.getAp();

        int counter = 0;
        int year = 0;

        while (year < 5) {

            if (null != CutleryState.findByKitchenAndAP(ap.getKitchen(), ap).getPictureAtPosition(pos)) {
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

    @Override
    public void createNewEntry(AP ap) {
        if (ApartmentType.STUDIO.equals(ap.getApartment().getType())) {
            this.save();
        }
    }

    @Override
    public void saveCheckEntries(List<String> check, String ex) {
        this.setBrokenFork(Integer.parseInt(check.get(0)));
        this.setBrokenKnife(Integer.parseInt(check.get(1)));
        this.setBrokenBigSpoon(Integer.parseInt(check.get(2)));
        this.setBrokenSmallSpoon(Integer.parseInt(check.get(3)));
        this.setBrokenCookingKnife(Integer.parseInt(check.get(4)));
        this.setBrokingBreadKnife(Integer.parseInt(check.get(5)));
        this.setBrokenPlate(Integer.parseInt(check.get(6)));
        this.setBrokenSoupPlate(Integer.parseInt(check.get(7)));
        this.setBrokenSmallPlate(Integer.parseInt(check.get(8)));
        this.setBrokenMug(Integer.parseInt(check.get(9)));
        this.setBrokenTeaPot(Integer.parseInt(check.get(10)));
        this.setBrokenSmallBowl(Integer.parseInt(check.get(11)));
        this.setBrokenFryingPan(Integer.parseInt(check.get(12)));
        this.setBrokenGlass(Integer.parseInt(check.get(13)));
        this.save();

        updateCutleryName(ex);
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

    /**
     * add all columns which contain the broken entries
     * 0: everything is ok
     * 0 <: something is incorrect
     * @param cutlery
     * @return
     */
    private static List<Integer> createBrokenList(CutleryState cutlery) {
        return new ArrayList<>(Arrays.asList(cutlery.getBrokenFork(), cutlery.getBrokenKnife(), cutlery.getBrokenBigSpoon(), cutlery.getBrokenSmallSpoon(), cutlery.getBrokenCookingKnife(), cutlery.getBrokingBreadKnife(), cutlery.getBrokenPlate(), cutlery.getBrokenSoupPlate(), cutlery.getBrokenSmallPlate(), cutlery.getBrokenMug(), cutlery.getBrokenTeaPot(), cutlery.getBrokenSmallBowl(), cutlery.getBrokenFryingPan(), cutlery.getBrokenGlass()));
    }

    /**
     * add all columns which contain the comment to a list
     * @param cutlery
     * @return
     */
    private static List<String> createCommentsList(CutleryState cutlery) {
        return new ArrayList<>(Arrays.asList(cutlery.getForkComment(), cutlery.getKnifeComment(), cutlery.getBigSpoonComment(), cutlery.getSmallSpoonComment(), cutlery.getCookingKnifeComment(), cutlery.getBreadKnifeComment(), cutlery.getPlateComment(), cutlery.getSoupPlateComment(), cutlery.getSmallPlateComment(), cutlery.getMugComment(), cutlery.getTeaPotComment(), cutlery.getSmallBowlComment(), cutlery.getFryingPanComment(), cutlery.getGlassComment()));
    }

    /**
     * add all columns which contain the verification if the entry is old to a list
     * false when the entry is new
     * true when the entry is old
     * @param cutlery
     * @return
     */
    private static List<Boolean> createCheckOldList(CutleryState cutlery) {
        return new ArrayList<>(Arrays.asList(cutlery.isForkOld(), cutlery.isKnifeOld(), cutlery.isBigSpoonOld(), cutlery.isSmallSpoonOld(), cutlery.isCookingKnifeOld(), cutlery.isBreadKnifeOld(), cutlery.isPlateOld(), cutlery.isSoupPlateOld(), cutlery.isSmallPlateOld(), cutlery.isMugOld(), cutlery.isTeaPotOld(), cutlery.isSmallBowlOld(), cutlery.isFryingPanOld(), cutlery.isGlassOld()));
    }

    /**
     * add all columns which contain the picture to a list
     * @param cutlery
     * @return
     */
    private static List<byte[]> createPictureList(CutleryState cutlery) {
        return new ArrayList<>(Arrays.asList(cutlery.getForkPicture(), cutlery.getKnifePicture(), cutlery.getBigSpoonPicture(), cutlery.getSmallSpoonPicture(), cutlery.getCookingKnifePicture(), cutlery.getBreadKnifePicture(), cutlery.getPlatePicture(), cutlery.getSoupPlatePicture(), cutlery.getSmallPlatePicture(), cutlery.getMugPicture(), cutlery.getTeaPotPicture(), cutlery.getSmallBowlPicture(), cutlery.getFryingPanPicture(), cutlery.getGlassPicture()));
    }

    /**
     * copies all columns
     * @param oldCutlery
     */
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
        this.setName(oldCutlery.getName());
    }

    /**
     * set the name of the cutlery
     * if some broken entries are greater then 0 add an exclamation mark to the cutlery name
     * @param ex
     */
    private void updateCutleryName(String ex) {
        int i = 0;
        while (i < createBrokenList(this).size()) {
            if (createBrokenList(this).get(i) > 0) {
                this.setName("Pfannen, Geschirr, Besteck " + ex);
                break;
            } else
                this.setName("Pfannen, Geschirr, Besteck");
            i++;
        }
        this.save();
    }

    /**
     * search it in the db with the kitchen id and protocol id
     * @param kitchen
     * @param ap
     * @return
     */
    public static CutleryState findByKitchenAndAP(Kitchen kitchen, AP ap) {
        return new Select().from(CutleryState.class).where("kitchen = ? and AP = ?", kitchen.getId(), ap.getId()).executeSingle();
    }

    /**
     * fill in the db with initial entries
     * @param aps
     * @param image
     */
    public static void initializeKitchenCutlery(List<AP> aps, byte[] image, String ex) {
        for (AP ap : aps) {
            CutleryState cutlery = new CutleryState(ap.getKitchen(), ap);
            cutlery.setBrokenBigSpoon(2);
            cutlery.setBrokenFryingPan(1);
            cutlery.setFryingPanComment("Kratzer in der Pfanne");
            cutlery.setFryingPanPicture(image);
            cutlery.setName(cutlery.getName() + " " + ex);
            cutlery.save();
        }
    }

    public static com.cete.dynamicpdf.pageelements.Table createPDF(CutleryState cutlery, float pageWidth, float posY, byte[] cross) {
        com.cete.dynamicpdf.pageelements.Table table = new com.cete.dynamicpdf.pageelements.Table(0, posY, pageWidth, 0);

        table.getColumns().add(150);
        table.getColumns().add(42);
        table.getColumns().add(50);
        table.getColumns().add(50);
        table.getColumns().add(150);
        table.getColumns().add(350);

        Row header = table.getRows().add(30);
        header.setFont(Font.getHelveticaBold());
        header.setFontSize(11);
        header.setAlign(CellAlign.CENTER);
        header.setVAlign(CellVAlign.CENTER);
        header.getCellList().add("");
        header.getCellList().add("Anzahl");
        header.getCellList().add("fehlend/defekt");
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

            row.getCellList().add(COUNT.get(i).toString());

            row.getCellList().add(createBrokenList(cutlery).get(i).toString());

            if (createCheckOldList(cutlery).get(i)) {
                row.getCellList().add(new Image(cross, 0, 0));
            } else
                row.getCellList().add("");

            if (null != createCommentsList(cutlery).get(i)) {
                row.getCellList().add(createCommentsList(cutlery).get(i));
            } else
                row.getCellList().add("");

            if (null != createPictureList(cutlery).get(i)) {
                Image image = new Image(createPictureList(cutlery).get(i), 0, 0);
                row.getCellList().add(image);
            } else
                row.getCellList().add("");

            i++;
        }
        table.setHeight(table.getRequiredHeight());
        return table;
    }
}
