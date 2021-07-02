/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systeminventorymanagment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Morgynn
 */
public class Inventory {
    
    
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    public static ObservableList<Part> getAllParts() {
        
        return allParts;
        
    }
    
    public static ObservableList<Product> getAllProducts() {
        
        return allProducts;
        
    }
    public static void addPart(Part newPart){
        
        allParts.add(newPart);
    }
    
    public static void addProduct(Product newProduct){
        
        allProducts.add(newProduct);
    }
    
    public static Part lookupPart(int partID){
        
        for(Part part : getAllParts())
        {
            if(part.getId() == partID)
                return part;
        }
        return null;

        
    }
    
    public static Product lookupProduct(int productID){
        
        for(Product product : getAllProducts())
        {
            if(product.getId() == productID)
                return product;
        }
        return null;
    }
    
    public static ObservableList<Part> lookupPart(String partName){
        
        ObservableList<Part> allMatchingParts = FXCollections.observableArrayList();
        
        for(Part part : getAllParts())
        {
            if(part.getName().equals(partName))
                allMatchingParts.add(part);
        }
            return allMatchingParts;
    }
    
    public static ObservableList<Product> lookupProduct(String productName){
        
        ObservableList<Product> allMatchingProducts = FXCollections.observableArrayList();
        
        for(Product product : getAllProducts())
        {
            if(product.getName().equals(productName))
                allMatchingProducts.add(product);
        }
            return allMatchingProducts;
    }
    
    public static void updatePart(int index, Part selectedPart){
        
        for(Part part : getAllParts())
        {
            if(part.getId() == selectedPart.getId()){
                getAllParts().set(index, selectedPart);
            }
        }
    }
    
    public static void updateProduct(int index, Product selectedProduct){
        
        for(Product product : getAllProducts())
        {
            
            if(product.getId() == selectedProduct.getId()){
                getAllProducts().set(index, selectedProduct);
            }
        }
    }
    
    public static boolean deletePart(Part selectedPart){
        
       allParts.remove(selectedPart);
       return true;
    }
    
    public static boolean deleteProduct(Product selectedProduct){
        
        allProducts.remove(selectedProduct);
        return true;
    }
}
