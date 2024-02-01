package com.example.sheba;

public class ModelUser {
   private String name,phoneNumber,profilePicture,Location,Occupation,locationLink,nid;

    public ModelUser(String name, String phoneNumber, String profilePicture, String location, String occupation,String locationLinkk,String nid) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.profilePicture = profilePicture;
        this.Location = location;
        this.Occupation = occupation;
        this.locationLink=locationLinkk;
        this.nid=nid;
    }

    public ModelUser() {
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getLocationLink() {
        return locationLink;
    }

    public void setLocationLink(String locationLink) {
        this.locationLink = locationLink;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getLocation() {
        return Location;
    }

    public String getOccupation() {
        return Occupation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public void setOccupation(String occupation) {
        Occupation = occupation;
    }
}
