package com.example.woko_app.models;


import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.example.woko_app.constants.Gender;
import com.example.woko_app.constants.Role;
import com.example.woko_app.constants.UserStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by camillagretsch on 02.09.16.
 */
@Table(name = "User")
public class User extends Model {

    @Column(name = "username", unique = true, notNull = true)
    private String username;

    @Column(name = "password", notNull = true)
    private String password;

    @Column(name = "name", notNull = true)
    private String name;

    @Column(name = "gender", notNull = true)
    private Gender gender;

    @Column(name = "role", notNull = true)
    private Role role;

    @Column(name = "status", notNull = true)
    private UserStatus status;

    @Column(name = "houses", onUpdate = Column.ForeignKeyAction.CASCADE)
    private List<House> houses = new ArrayList<House>();

    public User() {
        super();
    }

    public User(String username, String password, String name, Gender gender, Role role, UserStatus status) {
        super();
        this.username = username;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.role = role;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public Role getRole() {
        return role;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public UserStatus getStatus() {
        return status;
    }

    public List<House> getHousesHV() {
        return getMany(House.class, "HV");
    }

    /**
     * find a user by his username
     * @param username
     * @return
     */
    public static User findByUsername(String username) {
        return new Select().from(User.class).where("username = ?", username).executeSingle();
    }

    /**
     * set user to online
     * @param user
     */
    public void setUserOnline(User user) {
        user.setStatus(UserStatus.ONLINE);
        user.save();
    }

    /**
     * set user to offline
     * @param user
     */
    public void setUserOffline(User user) {
        user.setStatus(UserStatus.OFFLINE);
        user.save();
    }

    /**
     * creates different users and save them to the table
     * @return
     */
    public static List<User> initializeUsers() {
        int i = new Select().from(User.class).execute().size();
        List<User> users = new ArrayList<>();

        if (i == 0) {
            User user = new User("mm", "1234", "Max Muster", Gender.MALE, Role.HAUSVERANTWORTLICHER, UserStatus.OFFLINE);
            user.save();
            users.add(user);
            Log.d("User:", "name: " + user.getName() + " Id: " + user.getId());

            user = new User("abc", "1111", "Paul Muster", Gender.MALE, Role.HAUSVERANTWORTLICHER, UserStatus.OFFLINE);
            user.save();
            users.add(user);
            Log.d("User:", "name: " + user.getName() + " Id: " + user.getId());

        }
        return users;
    }
}
