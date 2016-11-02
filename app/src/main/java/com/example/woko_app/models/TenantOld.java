package com.example.woko_app.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.cete.dynamicpdf.WebColor;
import com.cete.dynamicpdf.pageelements.Column2;
import com.cete.dynamicpdf.pageelements.Row2;
import com.cete.dynamicpdf.pageelements.Table2;
import com.example.woko_app.constants.Account;

import java.util.List;

/**
 * Created by camillagretsch on 23.09.16.
 */
@Table(name = "TenantOld")
public class TenantOld extends Model{

    @Column(name = "name")
    private String name = null;

    @Column(name = "street_and_number")
    private String streetAndNumber = null;

    @Column(name = "plz_town_country")
    private String plz_town_country = null;

    @Column(name = "email")
    private String email = null;

    @Column(name = "refunder")
    private boolean refunder;

    @Column(name = "otherRefunder")
    private String otherRefunder = null;

    @Column(name = "account")
    private Account account;

    @Column(name = "account_number")
    private String accountNumber = null;

    @Column(name = "bank_name")
    private String bankName = null;

    @Column(name = "swift")
    private String swift = null;

    @Column(name = "iban")
    private String iban = null;

    @Column(name = "account_clearing_number")
    private String accountClearingNumber = null;

    @Column(name = "AP")
    private AP ap;

    @Column(name = "signature")
    private byte[] signature = null;

    @Column(name = "date")
    private String date = null;

    public TenantOld() {
        super();
    }

    public TenantOld(AP ap) {
        super();
        this.ap = ap;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setStreetAndNumber(String streetAndNumber) {
        this.streetAndNumber = streetAndNumber;
    }

    public String getStreetAndNumber() {
        return streetAndNumber;
    }

    public void setPlz_town_country(String plz_town_country) {
        this.plz_town_country = plz_town_country;
    }

    public String getPlz_town_country() {
        return plz_town_country;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setRefunder(boolean refunder) {
        this.refunder = refunder;
    }

    public boolean isRefunder() {
        return refunder;
    }

    public void setOtherRefunder(String otherRefunder) {
        this.otherRefunder = otherRefunder;
    }

    public String getOtherRefunder() {
        return otherRefunder;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setSwift(String swift) {
        this.swift = swift;
    }

    public String getSwift() {
        return swift;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getIban() {
        return iban;
    }

    public void setAccountClearingNumber(String accountClearingNumber) {
        this.accountClearingNumber = accountClearingNumber;
    }

    public String getAccountClearingNumber() {
        return accountClearingNumber;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public static TenantOld findById(long id) {
        return new Select().from(TenantOld.class).where("id = ?", id).executeSingle();
    }

    /**
     * fill in the db with initial entries
     * @return
     */
    public static TenantOld initializeTenantOld() {
            TenantOld tenantOld = new TenantOld();
            tenantOld.setRefunder(true);
            tenantOld.setName("Tim Müller");
            tenantOld.setAccount(Account.BANK);
            tenantOld.save();
        return tenantOld;
    }

    /**
     * create a new tenant
     * @return
     */
    public static TenantOld saveNewTenantOld() {
        TenantOld tenantOld = new TenantOld();
        tenantOld.save();
        return tenantOld;
    }
}
