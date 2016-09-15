package com.example.woko_app.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by camillagretsch on 07.09.16.
 */
@Table(name = "House")
public class House extends Model{

    @Column(name = "street", notNull = true)
    private String street;

    @Column(name = "street_number", notNull = true)
    private String streetNumber;

    @Column(name = "town", notNull = true)
    private String town;

    @Column(name = "PLZ", notNull = true)
    private int PLZ;

    @Column(name = "HV", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private User HV;

    @Column(name = "apartments", onUpdate = Column.ForeignKeyAction.CASCADE)
    private List<Apartment> apartments = new ArrayList<Apartment>();

    private int numberOfApartment;

    public House() {
        super();
    }

    public House(String street, String streetNumber, String town, int PLZ, User HV, int numberOfApartment) {
        super();
        this.street = street;
        this.streetNumber = streetNumber;
        this.town = town;
        this.PLZ = PLZ;
        this.HV = HV;
        this.numberOfApartment = numberOfApartment;
    }

    public String getStreet() {
        return street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getTown() {
        return town;
    }

    public int getPLZ() {
        return PLZ;
    }

    public User getHV() {
        return HV;
    }

    public List<Apartment> getApartments() {
        return getMany(Apartment.class, "house");
    }

    public int getNumberOfApartment() {
        return numberOfApartment;
    }

    /**
     * creates houses and save them to the table
     * @param users
     * @return
     */
    public static List<House> initializeHouses(List<User> users) {
        int i = new Select().from(House.class).execute().size();
        List<House> houses = new ArrayList<>();

        if (i == 0) {
            User user = users.get(0);
            House house = new House("Musterstrasse", "1" , "Zürich", 8000, user, 4);
            house.save();
            houses.add(house);

            user = users.get(1);
            house = new House("Musterstrasse", "2" , "Zürich", 8000, users.get(1), 6);
            house.save();
            houses.add(house);

        }
        return houses;
    }

    /**
     * find a house by id
     * @param id
     * @return
     */
    public static House findById(long id) {
        return new Select().from(House.class).where("id = ?", id).executeSingle();
    }

    /**
     * find a house by his HV
     * @param user
     * @return
     */
    public static House findByHV(User user) {
        return new Select().from(House.class).where("HV = ?", user.getId()).executeSingle();
    }
}
