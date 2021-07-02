/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systeminventorymanagment;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author Morgynn
 */
public class MainDocumentController implements Initializable {
    
    Stage stage;
    Parent scene;
    Part part;
    Product product;
    
    @FXML private Label titleLabel;
    @FXML private Button partSearchButton;
    @FXML private TextField partSearchBox;
    @FXML private Button productSearchButton;
    @FXML private TextField productSearchBox;
    @FXML private TableView<Part> allPartsTable;
    @FXML private TableColumn<Part, Integer> partIDCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Integer> partInventoryCol;
    @FXML private TableColumn<Part, Double> partPriceCol;
    @FXML private TableView<Product> allProductsTable;
    @FXML private TableColumn<Product, Integer> productIDCol;
    @FXML private TableColumn<Product, String> productNameCol;
    @FXML private TableColumn<Product, Integer> productInventoryCol;
    @FXML private TableColumn<Product, Double> productPriceCol;
    @FXML private Button addPartBttn;
    @FXML private Button modPartBttn;
    @FXML private Button delPartBttn;
    @FXML private Button addProductBttn;
    @FXML private Button modProductBttn;
    @FXML private Button delProductBttn;
    
    @FXML
    private Button exitButton;

    
    private ObservableList<Part> partInventory = FXCollections.observableArrayList();
    private ObservableList<Part> partSearchResults = FXCollections.observableArrayList();
    private ObservableList<Product> productInventory = FXCollections.observableArrayList();
    private ObservableList<Product> productSearchResults = FXCollections.observableArrayList();

    
    @FXML
    void addProduct(ActionEvent event) throws IOException {
        
    Stage stage;
    Parent root;
    stage = (Stage) addProductBttn.getScene().getWindow();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/systeminventorymanagment/Views/AddProduct.fxml"));
    root = loader.load();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();

    }
    
    @FXML
    void addPart(ActionEvent event) throws IOException {
        
    Stage stage;
    Parent root;
    stage = (Stage) addPartBttn.getScene().getWindow();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/systeminventorymanagment/Views/AddPart.fxml"));
    root = loader.load();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    
    } 

    @FXML
    void delPart(ActionEvent event) {
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Are you sure you want to delete this part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Part selectedPart = allPartsTable.getSelectionModel().getSelectedItem();
            Inventory.deletePart(selectedPart);
        }
    }

    @FXML
    void delProduct(ActionEvent event) {
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Are you sure you want to delete this product?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Product selectedProduct = allProductsTable.getSelectionModel().getSelectedItem();
            Inventory.deleteProduct(selectedProduct);
        }
    }

    @FXML
    void modPart(ActionEvent event) throws IOException {
        
    Stage stage;
    Parent root;
    stage = (Stage) modPartBttn.getScene().getWindow();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/systeminventorymanagment/Views/ModifyPart.fxml"));
    root = loader.load();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    ModifyPartController controller = loader.getController();
    
    part = allPartsTable.getSelectionModel().getSelectedItem();
    controller.setPart(part);
    
    }

    @FXML
    void modProduct(ActionEvent event) throws IOException {
        
    Stage stage;
    Parent root;
    stage = (Stage) modProductBttn.getScene().getWindow();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/systeminventorymanagment/Views/ModifyProduct.fxml"));
    root = loader.load();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    ModifyProductController controller = loader.getController();
    product = allProductsTable.getSelectionModel().getSelectedItem();
    controller.setProduct(product);
    
    }

    @FXML
    void searchParts(ActionEvent event) {

        String searchItem = partSearchBox.getText();
        
        partSearchResults = Inventory.lookupPart(searchItem);
        if(partSearchResults.isEmpty()){
            try{
                int x = Integer.parseInt(searchItem);
                Part p = Inventory.lookupPart(x);
                partSearchResults.add(p);
            }catch(NumberFormatException e){
                System.out.println("Number Format Exception Occured");
            }
        }
        allPartsTable.setItems(partSearchResults);
        
        if(partSearchBox.getText().isEmpty()){
            allPartsTable.setItems(Inventory.getAllParts());
        }

    }

    @FXML
    void searchProducts(ActionEvent event) {
        
        String searchItem = productSearchBox.getText();
        productSearchResults = Inventory.lookupProduct(searchItem);
        if(productSearchResults.isEmpty()){
            try{
                int x = Integer.parseInt(searchItem);
                Product p = Inventory.lookupProduct(x);
                productSearchResults.add(p);
            }catch(NumberFormatException e){
                System.out.println("Number Format Exception Occured");
            }
        }
        allProductsTable.setItems(productSearchResults);
        
        if(productSearchBox.getText().isEmpty()){
            allProductsTable.setItems(Inventory.getAllProducts());
        }

    }
    
    @FXML
    void exitProgram(ActionEvent event) {
        
    Stage stage = (Stage) exitButton.getScene().getWindow();
    stage.close();

    }
    
    static boolean entered;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if(!entered){
        Part part1 = new InHouse(1, "Part 1", 2.50, 25, 5, 100, 12345);
        Part part2 = new InHouse(2, "Part 2", 1.27, 17, 5, 100, 12346);
        Part part3 = new InHouse(3, "Part 3", 2.35, 65, 5, 100, 12347);
        Part part4 = new Outsourced(4, "Part 4", 1.34, 35, 5, 100, "Part Company");
        Part part5 = new Outsourced(5, "Part 5", 3.50, 74, 5, 100, "Part Company");
        
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addPart(part5);
        
        Product product1 = new Product(1, "Product 1", 10.55, 67, 5, 100);
        Product product2 = new Product(2, "Product 2", 10.29, 27, 5, 100);
        Product product3 = new Product(3, "Product 3", 20.54, 18, 5, 100);
        Product product4 = new Product(4, "Product 4", 23.94, 45, 5, 100);
        Product product5 = new Product(5, "Product 5", 24.20, 24, 5, 100);
        
        Inventory.addProduct(product1);
        product1.addAssociatedPart(part1);
        Inventory.addProduct(product2);
        product2.addAssociatedPart(part2);
        Inventory.addProduct(product3);
        product3.addAssociatedPart(part3);
        Inventory.addProduct(product4);
        product4.addAssociatedPart(part4);
        Inventory.addProduct(product5);
        product5.addAssociatedPart(part5);
        
        entered = true;
        }
        
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        allPartsTable.setItems(Inventory.getAllParts());
        allProductsTable.setItems(Inventory.getAllProducts());
        
    }    
    

}
