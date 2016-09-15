package com.example.woko_app.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.example.woko_app.constants.ApartmentType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by camillagretsch on 08.09.16.
 */
@Table(name = "Apartment")
public class Apartment extends Model {

    @Column(name = "house", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private House house;

    @Column(name = "apartment_number", notNull = true)
    private int apartmentNumber;

    @Column(name = "type", notNull = true)
    private ApartmentType type;

    @Column(name = "rooms", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private List<Room> rooms = new ArrayList<>();

    @Column(name = "AP", onUpdate = Column.ForeignKeyAction.CASCADE)
    private List<AP> APs = new ArrayList<>();

    private int numberOfRooms;

    public Apartment() {
        super();
    }

    public Apartment(House house, int apartmentNumber, ApartmentType type, int numberOfRooms) {
        super();
        this.house = house;
        this.apartmentNumber = apartmentNumber;
        this.type = type;
        this.numberOfRooms = numberOfRooms;
    }

    public House getHouse() {
        return house;
    }

    public int getApartmentNumber() {
        return apartmentNumber;
    }

    public ApartmentType getType() {
        return type;
    }

    public List<Room> getRooms() {
        return getMany(Room.class, "apartment");
    }

    public List<AP> getAPs() {
        return getMany(AP.class, "apartment");
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public static Apartment findById(long id) {
        return new Select().from(Apartment.class).where("id = ?", id).executeSingle();
    }

    /**
     * creates shared apartments and studios and save it to the table
     * @param houses
     * @return
     */
    public static List<Apartment> initializeApartments(List<House> houses) {
        int size = new Select().from(Apartment.class).execute().size();
        int roomcounter = 3;
        List<Apartment> apartments = new ArrayList<>();

        if (size == 0) {
            for (int i = 0; i < houses.size(); i++) {
                if (i % 2 == 0) {
                    for (int j = 1; j <= houses.get(i).getNumberOfApartment(); j++) {
                        Apartment apartment = new Apartment(houses.get(i), j, ApartmentType.SHARED_APARTMENT, roomcounter++);
                        apartment.save();
                        apartments.add(apartment);
                    }
                } else {
                    for (int j = 1; j <= houses.get(i).getNumberOfApartment(); j++) {
                        Apartment apartment = new Apartment(houses.get(i), j, ApartmentType.STUDIO, 1);
                        apartment.save();
                        apartments.add(apartment);
                    }
                }
            }
        }
        return apartments;
    }
}
