/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Matt
 */
public class Products {

    private final IntegerProperty prodId;
    private final StringProperty prodName;
    private final IntegerProperty prodInv;
    private final DoubleProperty prodPrice;
    private final IntegerProperty prodMin;
    private final IntegerProperty prodMax;
    private ArrayList<Parts> containedParts = new ArrayList<Parts>();

    public Products() {
        prodId = new SimpleIntegerProperty();
        prodName = new SimpleStringProperty();
        prodInv = new SimpleIntegerProperty();
        prodPrice = new SimpleDoubleProperty();
        prodMin = new SimpleIntegerProperty();
        prodMax = new SimpleIntegerProperty();
        containedParts = new ArrayList<Parts>();
    }

    public ArrayList<Parts> getContainedParts() {
        return containedParts;
    }

    public void setContainedParts(ArrayList parts) {
        this.containedParts = parts;
    }

    public void ClearContainedParts() {
        containedParts.clear();
    }

    public int getProdId() {
        return prodId.get();
    }

    public void setProdId(int prodId) {
        this.prodId.set(prodId);
    }

    public String getProdName() {
        return prodName.get();
    }

    public void setProdName(String prodName) {
        this.prodName.set(prodName);
    }

    public int getProdInv() {
        return prodInv.get();
    }

    public void setProdInv(int prodInv) {
        this.prodInv.set(prodInv);
    }

    public double getProdPrice() {
        return prodPrice.get();
    }

    public void setProdPrice(double prodPrice) {
        this.prodPrice.set(prodPrice);
    }

    public int getProdMin() {
        return prodMin.get();
    }

    public void setProdMin(int prodMin) {
        this.prodMin.set(prodMin);
    }

    public int getProdMax() {
        return prodMax.get();
    }

    public void setProdMax(int prodMax) {
        this.prodMax.set(prodMax);
    }

    public StringProperty ProductName() {
        return prodName;
    }

    public IntegerProperty ProductInv() {
        return prodInv;
    }

    public IntegerProperty ProductId() {
        return prodId;
    }

    public DoubleProperty ProductPrice() {
        return prodPrice;
    }

}
