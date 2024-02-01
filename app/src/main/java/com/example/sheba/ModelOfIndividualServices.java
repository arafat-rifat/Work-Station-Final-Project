package com.example.sheba;

public class ModelOfIndividualServices {
   private String serviceName,priceRange;

    public ModelOfIndividualServices(String serviceName, String priceRange) {
        this.serviceName = serviceName;
        this.priceRange = priceRange;
    }

    public ModelOfIndividualServices() {
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
}
