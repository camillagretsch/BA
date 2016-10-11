package com.example.woko_app.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by camillagretsch on 19.09.16.
 */
@Table(name = "Kitchen")
public class Kitchen  extends Model{

    @Column(name = "apartment", unique = true, notNull = true, onUpdate = Column.ForeignKeyAction.CASCADE)
    private Apartment apartment;

    @Column(name = "APs", onUpdate = Column.ForeignKeyAction.CASCADE)
    private List<AP> APs = new ArrayList<>();

    public Kitchen() {
        super();
    }

    public Kitchen(Apartment apartment) {
        super();
        this.apartment = apartment;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public List<AP> getAPs() {
        return getMany(AP.class, "kitchen");
    }

    public static Kitchen findByApartment(Apartment apartment) {
        return new Select().from(Kitchen.class).where("apartment = ?", apartment.getId()).executeSingle();
    }

    public static void initializeKitchen(List<Apartment> apartments) {
        for (Apartment apartment : apartments) {
            Kitchen kitchen = new Kitchen(apartment);
            kitchen.save();
        }
    }

    public static void addExclamationMark(List<String> child, AP ap) {

    }
}
