package edu.zsk.terraquest;
public class Hotel {
    private String name;
    private String location;
    private String imageUrl;
    private int originalPrice;
    private int discountedPrice;
    private int nights;

    public Hotel(String name, String location, String imageUrl, int originalPrice, int discountedPrice, int nights) {
        this.name = name;
        this.location = location;
        this.imageUrl = imageUrl;
        this.originalPrice = originalPrice;
        this.discountedPrice = discountedPrice;
        this.nights = nights;
    }

    public String getName() { return name; }
    public String getLocation() { return location; }
    public String getImageUrl() { return imageUrl; }
    public int getOriginalPrice() { return originalPrice; }
    public int getDiscountedPrice() { return discountedPrice; }
    public int getNights() { return nights; }
}
