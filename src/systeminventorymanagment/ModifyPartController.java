/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systeminventorymanagment;

import static com.sun.deploy.util.ReflectionUtil.instanceOf;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Morgynn
 */
public class ModifyPartController implements Initializable {
    
    Stage stage;
    Parent scene;
    Part part;
    Inventory inv;
    
    @FXML private ToggleGroup partType;
    @FXML private TextField modPartID;
    @FXML private TextField modPartName;
    @FXML private TextField modPartInventory;
    @FXML private TextField modPartPrice;
    @FXML private TextField modPartMax;
    @FXML private TextField modPartMin;
    @FXML private TextField modPartCompanyNameMachineId;
    @FXML private Label machineIdOrCompName;
    @FXML private Button modPartSaveButton;
    @FXML private Button modPartCancelButton;
    @FXML private RadioButton modPartInHouse;
    @FXML private RadioButton modPartOutsourced;
    @FXML private TableView<Part> allPartsTable;
    
    @FXML
    void outsourcedSelected(ActionEvent event) {
        
        machineIdOrCompName.setText("Comapny Name");
        
    }
    
    @FXML
    void inHouseSelected(ActionEvent event) {
        
        machineIdOrCompName.setText("Machine ID");

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
    stage = (Stage) modPartCancelButton.getScene().getWindow();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/systeminventorymanagment/Views/MainDocument.fxml"));
    root = loader.load();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
        }
    
    }
    
    public void setPart(Part part){
        
        this.part = part;
        if(part instanceof InHouse){
            
            InHouse partIH = (InHouse) part;
            modPartInHouse.setSelected(true);
            machineIdOrCompName.setText("Machine ID");
            int id = partIH.getId();
            String idStr = Integer.toString(id);
            modPartID.setText(idStr);
            modPartName.setText(partIH.getName());
            int inv = partIH.getStock();
            String invStr = Integer.toString(inv);
            modPartInventory.setText(invStr);
            double price = partIH.getPrice();
            String priceStr = Double.toString(price);
            modPartPrice.setText(priceStr);
            int min = partIH.getMin();
            String minStr = Integer.toString(min);
            modPartMax.setText(minStr);
            int max = partIH.getMax();
            String maxStr = Integer.toString(max);
            modPartMin.setText(maxStr);
            int machId = partIH.getMachineId();
            String machIdStr = Integer.toString(machId);
            modPartCompanyNameMachineId.setText(machIdStr);
            
        }
        else{
            
            Outsourced partOS = (Outsourced) part;
            modPartOutsourced.setSelected(true);
            machineIdOrCompName.setText("Comapny Name");
            int id = partOS.getId();
            String idStr = Integer.toString(id);
            modPartID.setText(idStr);
            modPartName.setText(partOS.getName());
            int inv = partOS.getStock();
            String invStr = Integer.toString(inv);
            modPartInventory.setText(invStr);
            double price = partOS.getPrice();
            String priceStr = Double.toString(price);
            modPartPrice.setText(priceStr);
            int min = partOS.getMin();
            String minStr = Integer.toString(min);
            modPartMax.setText(minStr);
            int max = partOS.getMax();
            String maxStr = Integer.toString(max);
            modPartMin.setText(maxStr);
            modPartCompanyNameMachineId.setText(partOS.getCompanyName());
            
        }
    }
        


    @FXML
    void savePart(ActionEvent event) throws IOException {
        if(modPartOutsourced.isSelected()){
            
            Part newPart = new Outsourced(Integer.parseInt(modPartID.getText()), modPartName.getText(), Double.parseDouble(modPartPrice.getText()), 
            Integer.parseInt(modPartInventory.getText()), Integer.parseInt(modPartMax.getText()), Integer.parseInt(modPartMin.getText()), 
            modPartCompanyNameMachineId.getText());
            inv.addPart(newPart);
            inv.deletePart(part);
        
        }
        else{
            
            Part newPart = new InHouse(Integer.parseInt(modPartID.getText()), modPartName.getText(), Double.parseDouble(modPartPrice.getText()), 
            Integer.parseInt(modPartInventory.getText()), Integer.parseInt(modPartMax.getText()), Integer.parseInt(modPartMin.getText()), 
            Integer.parseInt(modPartCompanyNameMachineId.getText()));
            inv.addPart(newPart);
            inv.deletePart(part);
            
        }
        
        Stage stage;
        Parent root;
        stage = (Stage) modPartCancelButton.getScene().getWindow();
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
