/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Matt
 */
public class InHoused extends Parts {
    private final IntegerProperty machineId;

    public int getMachineId() {
        return this.machineId.get();
    }
    
    public void setMachineId(int machineId) {
        this.machineId.set(machineId);
    }
    
    public InHoused() {
        super();
        machineId = new SimpleIntegerProperty();

    }
    
}
