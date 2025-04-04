package models;

public class Service {

    private String name;

    private double price;
    private int id;

    public Service(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getName(){
        return name;
    }

    public double getPrice(){
        return price;
    }

    @Override
    public String toString(){
        return name + " - " + price;
    }

    public int getId() {
        return id;
    }

}
