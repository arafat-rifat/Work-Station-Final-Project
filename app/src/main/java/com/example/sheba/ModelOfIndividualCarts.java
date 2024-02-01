package com.example.sheba;

public class ModelOfIndividualCarts {
   private String location,serviceName,priceRange,providerName,contactNumber,profilePic,mail,locationLink;

    public ModelOfIndividualCarts(String mail,String location,String serviceName, String priceRange, String providerName, String contactNumber,String profilePic,String locationLink) {
        this.mail=mail;
        this.location=location;
        this.serviceName = serviceName;
        this.priceRange = priceRange;
        this.providerName = providerName;
        this.contactNumber = contactNumber;
        this.profilePic=profilePic;
        this.locationLink=locationLink;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ModelOfIndividualCarts() {
    }

    public String getLocationLink() {
        return locationLink;
    }

    public void setLocationLink(String locationLink) {
        this.locationLink = locationLink;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
