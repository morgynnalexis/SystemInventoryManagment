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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Morgynn
 */
public class AddPartController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    @FXML private TextField addPartID;
    @FXML private TextField addPartName;
    @FXML private TextField addPartInventory;
    @FXML private TextField addPartPrice;
    @FXML private TextField addPartMax;
    @FXML private TextField addPartMin;
    @FXML private TextField addPartCompanyNameOrMachineID;
    @FXML private Button addPartSaveButton;
    @FXML private Button addPartCancelButton;
    @FXML private RadioButton inHouse;
    @FXML private RadioButton outsourced;
    @FXML private ToggleGroup partType;
    @FXML private Label CompanyNameOrMachineID;
    
    @FXML
    void outsourcedSelected(ActionEvent event) {
        
        CompanyNameOrMachineID.setText("Comapny Name");
        
    }
    
    @FXML
    void inHouseSelected(ActionEvent event) {
        
        CompanyNameOrMachineID.setText("Machine ID");

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
            stage = (Stage) addPartCancelButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/systeminventorymanagment/Views/MainDocument.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        
    
    
    }

    @FXML
    void savePart(ActionEvent event) throws IOException {
        
    int id = Integer.parseInt(addPartID.getText());
    String name = addPartName.getText();
    int stock = Integer.parseInt(addPartInventory.getText());
    double price = Double.parseDouble(addPartPrice.getText());
    int max = Integer.parseInt(addPartMax.getText());
    int min = Integer.parseInt(addPartMin.getText());
    
    if(inHouse.isSelected()) {  
        int machineID = Integer.parseInt(addPartCompanyNameOrMachineID.getText());
        Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineID));
    }   
    else  {
        String companyName = addPartCompanyNameOrMachineID.getText();
        Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
        
    }
        
    Stage stage;
    Parent root;
    stage = (Stage) addPartSaveButton.getScene().getWindow();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/systeminventorymanagment/Views/MainDocument.fxml"));
    root = loader.load();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
