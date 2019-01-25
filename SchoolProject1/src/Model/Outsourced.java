/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Matt
 */
public class Outsourced extends Parts {
    private final StringProperty companyName;
    
    public String getCompanyName() {
        return this.companyName.get();
    }
    
    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }
    
    public Outsourced() {
        super();
        companyName = new SimpleStringProperty();

    }    
}
