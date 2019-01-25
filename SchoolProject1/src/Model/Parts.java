/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

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
public class Parts {

    private final IntegerProperty idNumber;
    private final StringProperty partName;
    private final IntegerProperty partInv;
    private final DoubleProperty partPrice;
    private final IntegerProperty partMax;
    private final IntegerProperty partMin;
    
    
    public Parts() {
       idNumber = new SimpleIntegerProperty();
       partName = new SimpleStringProperty();
       partInv = new SimpleIntegerProperty();
       partPrice = new SimpleDoubleProperty();
       partMax = new SimpleIntegerProperty();
       partMin = new SimpleIntegerProperty();
    }
    
    public int getIdNumber() {
        return idNumber.get();    
    }

    public void setIdNumber(int idNumber) {
        this.idNumber.set(idNumber);
    }

    public String getPartName() {
        return partName.get();
    }

    public void setPartName(String partName) {
        this.partName.set(partName);
    }

    public int getPartInv() {
        return partInv.get();
    }

    public void setPartInv(int partInv) {
        this.partInv.set(partInv);
    }

    public Double getPartPrice() {
        return partPrice.get();
    }

    public void setPartPrice(Double partPrice) {
        this.partPrice.set(partPrice);
    }

    public int getPartMax() {
        return partMax.get();
    }

    public void setPartMax(int partMax) {
        this.partMax.set(partMax);
    }

    public int getPartMin() {
        return partMin.get();
    }

    public void setPartMin(int partMin) {
        this.partMin.set(partMin);
    }
    
    public StringProperty PartPrtName() {
    return partName;
    }
    
    public IntegerProperty PartPrtInv() {
    return partInv;
    }
    
    public IntegerProperty PartPrtId() {
    return idNumber;
    }
    
    public DoubleProperty PartPrtPrice() {
    return partPrice;
    }
}  
 

