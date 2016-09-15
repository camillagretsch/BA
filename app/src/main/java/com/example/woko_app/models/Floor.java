package com.example.woko_app.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by camillagretsch on 11.09.16.
 */
@Table(name = "Floor")
public class Floor extends Model {

    @Column(name = "isClean")
    private boolean isClean;

    @Column(name = "hasDamage")
    private boolean hasDamage;

    @Column(name = "room", onUpdate = Column.ForeignKeyAction.CASCADE)
    private Room room;

    //TODO @Column(name = "kitchen")
    //TODO @Column(name = "bathroom")

    @Column(name = "AP", onUpdate = Column.ForeignKeyAction.CASCADE, notNull = true)
    private AP ap;

    public Floor() {
        super();
    }

    public Floor(Room room, AP ap) {
        this.room = room;
        this.ap = ap;
    }

    public void setIsClean(boolean isClean) {
        this.isClean = isClean;
    }

    public boolean isClean() {
        return isClean;
    }

    public void setHasDamage(boolean hasDamage) {
        this.hasDamage = hasDamage;
    }

    public boolean HasDamage() {
        return hasDamage;
    }

    public Room getRoom() {
        return room;
    }

    public AP getAp() {
        return ap;
    }

    public static Floor findByRoomAndAP(Room room, AP ap) {
        return new Select().from(Floor.class).where("room = ?", room).where("AP = ?", ap).executeSingle();
    }

    public static void initializeRoomFloor(List<Room> rooms) {
        for (Room r : rooms) {
            for (AP ap: r.getAPs()) {
                Floor floor = new Floor(r, ap);
                floor.setIsClean(true);
                floor.setHasDamage(false);
            }
        }
    }
}
