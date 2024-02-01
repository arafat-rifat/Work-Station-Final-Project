package com.example.sheba;

public class ModelOfParticularServiceWithPersons {
    private String name,profilePic,contactNumber,priceRange,location,mail,locationLink;

    public ModelOfParticularServiceWithPersons(String name, String profilePic, String contactNumber, String priceRange, String location, String email,String locationLink) {
        this.name = name;
        this.profilePic = profilePic;
        this.contactNumber = contactNumber;
        this.priceRange = priceRange;
        this.location = location;
        this.mail=email;
        this.locationLink=locationLink;
    }

    public ModelOfParticularServiceWithPersons() {
    }

    public String getLocationLink() {
        return locationLink;
    }

    public void setLocationLink(String locationLink) {
        this.locationLink = locationLink;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
