package com.example.woko_app.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by camillagretsch on 18.09.16.
 */
@Table(name = "Bathroom")
public class Bathroom extends Model{

    @Column(name = "apartment", unique = true, notNull = true, onUpdate = Column.ForeignKeyAction.CASCADE)
    private Apartment apartment;

    @Column(name = "APs", onUpdate = Column.ForeignKeyAction.CASCADE)
    private List<AP> APs = new ArrayList<>();

    public Bathroom() {
        super();
    }

    public Bathroom(Apartment apartment) {
        super();
        this.apartment = apartment;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public List<AP> getAPs() {
        return getMany(AP.class, "bathroom");
    }

    public static Bathroom findByApartment(Apartment apartment) {
        return new Select().from(Bathroom.class).where("apartment = ?", apartment.getId()).executeSingle();
    }

    public static void initializeBathroom(List<Apartment> apartments) {
        for (Apartment apartment : apartments) {
            Bathroom bathroom = new Bathroom(apartment);
            bathroom.save();
        }
    }
}
