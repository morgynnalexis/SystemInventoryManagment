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
public class AddProductController implements Initializable {
    
    Stage stage;
    Parent scene;
    Product prod;
    
    @FXML
    private TableView<Part> bottomTableView;
    
    @FXML
    private TableView<Part> topTableView;
    
    @FXML
    private TextField addProductIdField;

    @FXML
    private TextField addProductNameField;

    @FXML
    private TextField addProductInvField;

    @FXML
    private TextField addProductPriceField;

    @FXML
    private TextField addProductMaxField;

    @FXML
    private TextField addProductMinField;

    @FXML
    private Button searchButton;

    @FXML
    private TextField addProductSearchField;

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

    @FXML
    private Button addButton;

    @FXML
    private Button delButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;
    
    private ObservableList<Part> partInventory = FXCollections.observableArrayList();
    private ObservableList<Part> partSearchResults = FXCollections.observableArrayList();
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    @FXML
    void addPart(ActionEvent event) {
        
        Part newPart = topTableView.getSelectionModel().getSelectedItem();
        associatedParts.add(newPart);

    }

    @FXML
    void deletePart(ActionEvent event) {
        
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
        
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Information Dialog");
    alert.setContentText("At least one part must be associated with the Product!");
    alert.showAndWait();
        
    } else {
        
    int id = Integer.parseInt(addProductIdField.getText());
    String name = addProductNameField.getText();
    int stock = Integer.parseInt(addProductInvField.getText());
    double price = Double.parseDouble(addProductPriceField.getText());
    int max = Integer.parseInt(addProductMaxField.getText());
    int min = Integer.parseInt(addProductMinField.getText());
    Product newProduct = new Product(id, name, price, stock, min, max);
    for(Part part: associatedParts){
        newProduct.addAssociatedPart(part);
    }
    Inventory.addProduct(newProduct);
    
    
    
        
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
        
        String searchItem = addProductSearchField.getText();
        
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
