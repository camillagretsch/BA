package com.example.woko_app.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.cete.dynamicpdf.Font;
import com.cete.dynamicpdf.Page;
import com.cete.dynamicpdf.PageOrientation;
import com.cete.dynamicpdf.PageSize;
import com.cete.dynamicpdf.TextAlign;
import com.cete.dynamicpdf.pageelements.Label;
import com.cete.dynamicpdf.pageelements.TextArea;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by camillagretsch on 19.09.16.
 */
@Table(name = "Kitchen")
public class Kitchen  extends Model{

    @Column(name = "apartment", unique = true, notNull = true, onUpdate = Column.ForeignKeyAction.CASCADE)
    private Apartment apartment;

    @Column(name = "name")
    private String name = "Küche";

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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * set the name of the kitchen
     * first add the icon
     * if any of the children contains an exclamation mark in his name, then add one to the kitchen name
     * @param ap
     * @param newEx
     * @param oldEx
     * @param icon
     * @return
     */
    public static String updateKitchenName(AP ap, String newEx, String oldEx, String icon) {
        Kitchen kitchen = Kitchen.findByApartment(ap.getApartment());
        kitchen.setName(icon + " Küche ");
        kitchen.save();

        int i = 0;
        while (i < updateKitchenItems(ap).size()) {
            if (updateKitchenItems(ap).get(i).contains(newEx)) {
                kitchen.setName(kitchen.getName().concat(newEx));
                break;
            }
            i++;
        }

        int j = 0;
        while (j < updateKitchenItems(ap).size()) {
            if (updateKitchenItems(ap).get(j).contains(oldEx)) {
                kitchen.setName(kitchen.getName().concat(oldEx));
                break;
            }
            j++;
        }
        kitchen.save();
        return kitchen.getName();
    }

    /**
     * get all names of the entries which belong to the kitchen
     * and save them to a list
     * fridge, ventilation, oven, cutlery, cupboard, wall, floor, window, door, socket and radiator
     * @param ap
     * @return
     */
    public static ArrayList<String> updateKitchenItems(AP ap) {
        ArrayList<String> kitchenItems = new ArrayList<>();
        kitchenItems.add(FridgeState.findByKitchenAndAP(ap.getKitchen(), ap).getName());
        kitchenItems.add(VentilationState.findByKitchenAndAP(ap.getKitchen(), ap).getName());
        kitchenItems.add(OvenState.findByKitchenAndAP(ap.getKitchen(), ap).getName());
        kitchenItems.add(CutleryState.findByKitchenAndAP(ap.getKitchen(), ap).getName());
        kitchenItems.add(CupboardState.findByKitchenAndAP(ap.getKitchen(), ap).getName());
        kitchenItems.add(WallState.findByKitchenAndAP(ap.getKitchen(), ap).getName());
        kitchenItems.add(FloorState.findByKitchenAndAP(ap.getKitchen(), ap).getName());
        kitchenItems.add(WindowState.findByKitchenAndAP(ap.getKitchen(), ap).getName());
        kitchenItems.add(DoorState.findByKitchenAndAP(ap.getKitchen(), ap).getName());
        kitchenItems.add(SocketState.findByKitchenAndAP(ap.getKitchen(), ap).getName());
        kitchenItems.add(RadiatorState.findByKitchenAndAP(ap.getKitchen(), ap).getName());
        return kitchenItems;
    }

    /**
     * search it in the db with the apartment id
     * @param apartment
     * @return
     */
    public static Kitchen findByApartment(Apartment apartment) {
        return new Select().from(Kitchen.class).where("apartment = ?", apartment.getId()).executeSingle();
    }

    /**
     * fill in the db with initial entries
     * @param apartments
     */
    public static void initializeKitchen(List<Apartment> apartments) {
        for (Apartment apartment : apartments) {
            Kitchen kitchen = new Kitchen(apartment);
            kitchen.save();
        }
    }
}
