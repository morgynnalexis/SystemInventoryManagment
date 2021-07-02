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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Morgynn
 */
public class ModifyProductController implements Initializable {
    
    Stage stage;
    Parent scene;
    Product product;
    
    @FXML private TextField modProductIdField;
    @FXML private TextField modProductNameField;
    @FXML private TextField modProductInvField;
    @FXML private TextField modProductPriceField;
    @FXML private TextField modProductMaxField;
    @FXML private TextField modProductMinField;
    @FXML private TextField modProductSearchField;
    @FXML private Button searchButton;
    @FXML private Button addButton;
    @FXML private Button delButton;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
            
    @FXML
    private TableView<Part> bottomTableView;
    
    @FXML
    private TableView<Part> topTableView;
    
    @FXML
    private TableColumn<Part, Integer> partIDColAdd;

    @FXML
    private TableColumn<Part, String> partNameColAdd;

    @FXML
    private TableColumn<Part, Integer> partInventoryColAdd;

    @FXML
    private TableColumn<Part, Double> partPriceColAdd;

    @FXML
    private TableColumn<Part, Integer> partIDColDel;

    @FXML
    private TableColumn<Part, String> partNameColDel;

    @FXML
    private TableColumn<Part, Integer> partInventoryColDel;

    @FXML
    private TableColumn<Part, Double> partPriceColDel;
    
    private ObservableList<Part> partInventory = FXCollections.observableArrayList();
    private ObservableList<Part> partSearchResults = FXCollections.observableArrayList();
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    
    public void setProduct(Product product){
        
        this.product = product;
        
        int id = product.getId();
        String idStr = Integer.toString(id);
        modProductIdField.setText(idStr);
        String name = product.getName();
        modProductNameField.setText(name);
        int inv = product.getStock();
        String invStr = Integer.toString(inv);
        modProductInvField.setText(invStr);
        double price = product.getPrice();
        String priceStr = Double.toString(price);
        modProductPriceField.setText(priceStr);
        int max = product.getMax();
        String maxStr = Integer.toString(max);
        modProductMaxField.setText(maxStr);
        int min = product.getMin();
        String minStr = Integer.toString(min);
        modProductMinField.setText(minStr);
        
        associatedParts.addAll(product.getAllAssociatedParts());
        
        
    }

    
    @FXML
    void addPart(ActionEvent event) {
        
        Part newPart = topTableView.getSelectionModel().getSelectedItem();
        associatedParts.add(newPart);

    }

    @FXML
    void delPart(ActionEvent event) {
        
        Part part = bottomTableView.getSelectionModel().getSelectedItem();
        associatedParts.remove(part);

    }

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation Dialog");
    alert.setContentText("Are you sure you want to cancel?");
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK){
    Stage stage;
    Parent root;
    stage = (Stage) cancelButton.getScene().getWindow();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/systeminventorymanagment/Views/MainDocument.fxml"));
    root = loader.load();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
        }
    
    }
    

    @FXML
    void saveProduct(ActionEvent event) throws IOException {
        
    if(associatedParts.isEmpty()){
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setContentText("At least one part must be associated with the Product!");
        alert.showAndWait();
        
    } else {
        
    int id = Integer.parseInt(modProductIdField.getText());
    String name = modProductNameField.getText();
    int stock = Integer.parseInt(modProductInvField.getText());
    double price = Double.parseDouble(modProductPriceField.getText());
    int max = Integer.parseInt(modProductMaxField.getText());
    int min = Integer.parseInt(modProductMinField.getText());
    Product newProduct = new Product(id, name, price, stock, min, max);
    
    for(Part part: associatedParts){
        newProduct.addAssociatedPart(part);
    }
    
    Inventory.addProduct(newProduct);
    Inventory.deleteProduct(product);
        
    Stage stage;
    Parent root;
    stage = (Stage) saveButton.getScene().getWindow();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/systeminventorymanagment/Views/MainDocument.fxml"));
    root = loader.load();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    }
    }

    @FXML
    void searchParts(ActionEvent event) {
        
        String searchItem = modProductSearchField.getText();
        
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
        topTableView.setItems(partSearchResults);

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        partIDColAdd.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColAdd.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColAdd.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColAdd.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        partIDColDel.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColDel.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColDel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColDel.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        topTableView.setItems(Inventory.getAllParts());
        bottomTableView.setItems(associatedParts);
    }    
    
}
