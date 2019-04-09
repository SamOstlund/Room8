package com.example.room8;

public class SaveData {

    String firstName, lastName, age, minprice, maxprice, zip, bio;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMinprice() {
        return minprice;
    }

    public void setMinprice(String minprice) {
        this.minprice = minprice;
    }

    public String getMaxprice() {
        return maxprice;
    }

    public void setMaxprice(String maxprice) {
        this.maxprice = maxprice;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public SaveData(String firstName, String lastName, String age, String minprice, String maxprice, String zip, String bio) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.minprice = minprice;
        this.maxprice = maxprice;
        this.zip = zip;
        this.bio = bio;
    }
}
