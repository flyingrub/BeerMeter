package me.flyingrub.beermeter;

/**
 * Created by fly on 5/19/16.
 */
public class Beer {
    private String name;
    private float price;
    private float volume;
    private float degree;

    public Beer(String name, float price, float volume, float degree) {
        this.name = name;
        this.price = price;
        this.volume = volume;
        this.degree = degree;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDegree() {
        return degree;
    }

    public void setDegree(float degree) {
        this.degree = degree;
    }

    public float getProfitability() {
        return ((degree * volume) / price)  / 10;
    }

    @Override
    public String toString() {
        return "Beer{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", volume=" + volume +
                ", degree=" + degree +
                '}';
    }

}
