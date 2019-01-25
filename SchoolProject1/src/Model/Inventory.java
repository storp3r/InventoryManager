/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author Matt
 */
public class Inventory {
 
    public static int partIdNumber = 1;
    private static int productIdNumber = 1;

    public Inventory() {
    }

    public static int getPartIdNumber() {
        return partIdNumber;
    }    
    public static int getProductIdNumber() {
        return productIdNumber;
    }
    public static void setPartIdNumber(int partIdNumber) {
        Inventory.partIdNumber = partIdNumber;
    }
    public static void setProductIdNumber(int productIdNumber) {
        Inventory.productIdNumber = productIdNumber;
    }   
              
    private static ArrayList<Parts> storeTheParts = new ArrayList<Parts>();
    private static ArrayList<Products> storeTheProducts = new ArrayList<Products>();

    public static void addToList(Parts parts) {
        storeTheParts.add(parts);        
        partIdNumber++; // needs to be adjusted so that when modifying a part, a new part id is not made       
    }
    
    public static void modifyPart(Parts parts, int partIndex) {
        storeTheParts.set(partIndex, parts);        
    }
    
    
    public static void addToProdList (Products products) {
        
        storeTheProducts.add(products);        
        productIdNumber++; // needs to be adjusted so that when modifying a part , a new part is not made        
    }
    
    public static void removeFromList(Parts parts) {
        storeTheParts.remove(parts);        
    }
    
    public static void removeFromProductList(int productIndex) {
        storeTheProducts.remove(productIndex);
    }
    
    
    public static Parts partLookUp(String partName) {
        
        for (int i = 0; i < storeTheParts.size(); i++) {
            if (partName.equals(storeTheParts.get(i).getPartName().toLowerCase())) { 
                return storeTheParts.get(i);
            }
        }
        return null;
    }
    
    public static Products prodLookUp(String productName) {
        
        for (int i = 0; i < storeTheProducts.size(); i++) {
            if (productName.equals(storeTheProducts.get(i).getProdName().toLowerCase())) { 
                return storeTheProducts.get(i);
            }
        }
        return null;
    
    }
    
    public static void updatedPart(Parts parts, int upDex) {
        storeTheParts.set(upDex, parts);
    }
    
    public static void updatedProduct(Products products, int upDex) {
        storeTheProducts.set(upDex, products);
    }
    
    public static ArrayList<Parts> retrieveParts() {
        return storeTheParts;
    }
    
    public static ArrayList<Products> retrieveProducts() {
        return storeTheProducts;
    }

}
    