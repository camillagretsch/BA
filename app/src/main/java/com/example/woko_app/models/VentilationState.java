package com.example.woko_app.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by camillagretsch on 19.09.16.
 */
@Table(name = "VentilationState")
public class VentilationState extends Model{

    @Column(name = "isClean")
    private boolean isClean = true;

    @Column(name = "isCleanOld")
    private boolean isCleanOld = false;

    @Column(name = "clean_comment")
    private String cleanComment;

    @Column(name = "isWorking")
    private boolean isWorking = true;

    @Column(name = "isWorkingOld")
    private boolean isWorkingOld = false;

    @Column(name = "working_comment")
    private String workingComment;

    @Column(name = "lampIsWorking")
    private boolean lampIsWorking = true;

    @Column(name = "isLampWorkingOld")
    private boolean isLampWorkingOld = false;

    @Column(name = "lamp_comment")
    private String lampComment;

    @Column(name = "hasNoDamage")
    private boolean hasNoDamage = true;

    @Column(name = "isDamageOld")
    private boolean isDamageOld = false;

    @Column(name = "damage_comment")
    private String damageComment;

    private static final List<String> ROW_NAMES = Arrays.asList("st gereinigt?", "Funktioniert?", "Lämpchen sind intakt?", "Ist alles intakt?");

    @Column(name = "kitchen", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Kitchen kitchen;

    @Column(name = "AP", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private AP ap;

    public VentilationState() {
        super();
    }

    public VentilationState(Kitchen kitchen, AP ap) {
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

    public void setLampIsWorking(boolean lampIsWorking) {
        this.lampIsWorking = lampIsWorking;
    }

    public boolean getLampIsWorking() {
        return lampIsWorking;
    }

    public void setIsLampWorkingOld(boolean isLampWorkingOld) {
        this.isLampWorkingOld = isLampWorkingOld;
    }

    public boolean isLampWorkingOld() {
        return isLampWorkingOld;
    }

    public void setLampComment(String lampComment) {
        this.lampComment = lampComment;
    }

    public String getLampComment() {
        return lampComment;
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

    public static List<Boolean> createCheckList(VentilationState ventilation) {
        return new ArrayList<>(Arrays.asList(ventilation.isClean(), ventilation.isWorking(), ventilation.getLampIsWorking(), ventilation.hasNoDamage()));
    }

    public static List<String> createCommentsList(VentilationState ventilation) {
        return new ArrayList<>(Arrays.asList(ventilation.getCleanComment(), ventilation.getWorkingComment(), ventilation.getLampComment(), ventilation.getDamageComment()));
    }

    public static List<Boolean> createCheckOldList(VentilationState ventilation) {
        return new ArrayList<>(Arrays.asList(ventilation.isCleanOld(), ventilation.isWorkingOld(), ventilation.isLampWorkingOld(), ventilation.isDamageOld()));
    }

    public static void duplicateVentilationEntries(VentilationState ventilation, VentilationState oldVentilation) {
        ventilation.setIsClean(oldVentilation.isClean());
        ventilation.setIsCleanOld(ventilation.isCleanOld());
        ventilation.setCleanComment(oldVentilation.getCleanComment());
        ventilation.setIsWorking(oldVentilation.isWorking());
        ventilation.setIsWorkingOld(oldVentilation.isWorkingOld());
        ventilation.setWorkingComment(oldVentilation.getWorkingComment());
        ventilation.setLampIsWorking(oldVentilation.getLampIsWorking());
        ventilation.setIsLampWorkingOld(oldVentilation.isLampWorkingOld());
        oldVentilation.setLampComment(oldVentilation.getLampComment());
        ventilation.setHasNoDamage(oldVentilation.hasNoDamage());
        ventilation.setIsDamageOld(oldVentilation.isDamageOld());
        ventilation.setDamageComment(oldVentilation.getDamageComment());
    }

    public static VentilationState findByKitchenAndAP(Kitchen kitchen, AP ap) {
        return new Select().from(VentilationState.class).where("kitchen = ? and AP = ?", kitchen.getId(), ap.getId()).executeSingle();
    }

    public static VentilationState findById(long id) {
        return new Select().from(VentilationState.class).where("id = ?", id).executeSingle();
    }

    public static void initializeKitchenVentilation(List<AP> aps) {
        for (AP ap : aps) {
            VentilationState ventilation = new VentilationState(ap.getKitchen(), ap);
            ventilation.setLampIsWorking(false);
            ventilation.save();
        }
    }

}
