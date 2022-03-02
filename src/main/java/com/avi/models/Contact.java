package com.avi.models;

import lombok.Data;

import java.util.Comparator;

@Data
public class Contact implements Comparable<Contact> {

    private String operationType;
    private String uniqueKey;


    private String name;
    private String surname;
    private String telephone;
    private String email;
    private String age;
    private String hairColor;

    //FAMILY, FRIEND, ACQUAINTANCE
    public String type;

    //only used for type=="FAMILY"
    public String relationShip;

    //only used for type=="FRIEND"
    public String  friendshipYears;


    @Override
    public String toString() {
        return "Contact{" +
                "uniqueKey='" + uniqueKey + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", hairColor='" + hairColor + '\'' +
                ", type='" + type + '\'' +
                ", relationShip='" + relationShip + '\'' +
                ", friendshipYears=" + friendshipYears +
                '}';
    }

    @Override
    public int compareTo(Contact o) {
        return this.getSurname().compareTo(o.getSurname());
    }
}
