package nl.utwente.di.model;

public class Car {
    public String color;
    public String price;

    public String id;
    public String model;

    public Car(String id, String color, String price, String model) {
        this.id = id;
        this.color = color;
        this.price = price;
        this.model = model;
    }

    public String getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public String getPrice() {
        return price;
    }

    public String getModel() {
        return model;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public void setModel(String model) {
        this.model = model;
    }
}
