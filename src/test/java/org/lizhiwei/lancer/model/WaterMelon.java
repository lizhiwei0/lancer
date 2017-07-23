package org.lizhiwei.lancer.model;

import java.util.Random;
import java.util.UUID;

/**
 * Created by lizhiwe on 7/22/2017.
 */
public class WaterMelon {

    private double weight;
    private short length;
    private double width;
    private int height;
    private double price;
    private long barcode;
    private String label;
    private String description;

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public short getLength() {
        return length;
    }

    public void setLength(short length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getBarcode() {
        return barcode;
    }

    public void setBarcode(long barcode) {
        this.barcode = barcode;
    }

    @Override
    public String toString() {
        return "WaterMelon{" +
                "weight=" + weight +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", price=" + price +
                ", barcode=" + barcode +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static  WaterMelon randomWaterMelon() {
        Random random = new Random();
        WaterMelon waterMelon = new WaterMelon();
        waterMelon.setBarcode(UUID.randomUUID().hashCode());
        waterMelon.setDescription(UUID.randomUUID().toString());
        waterMelon.setHeight(random.nextInt());
        waterMelon.setLabel("WaterMelon");
        waterMelon.setLength((short)random.nextInt());
        waterMelon.setPrice(random.nextDouble());
        waterMelon.setWidth(random.nextDouble());
        return waterMelon;
    }
}
