package View_Controller;

import static Model.Inventory.addToProdList;
import static Model.Inventory.getProductIdNumber;
import static Model.Inventory.partLookUp;
import static Model.Inventory.retrieveParts;
import Model.Products;
import Model.Parts;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AddProductController implements Initializable {

    private static int addPartIndex;
    private static int prodId;
    private static String prodName;
    private static int prodInv;
    private static double prodPrice;
    private static int prodMin;
    private static int prodMax;
    private static int removePartIndex;
    private double totalCostOfParts;

    public static int getAddPartIndex() {
        return addPartIndex;
    }

    public static int getRemovePartIndex() {
        return removePartIndex;
    }

    MainMenuController mmController = new MainMenuController();
    ArrayList<Parts> foundItems = new ArrayList<Parts>();
    Products products = new Products();
    
    
    @FXML
    private TextField IdGen;

    @FXML
    private TextField SearchBar;

    @FXML
    private TextField Name;

    @FXML
    private TextField Inventory;

    @FXML
    private TextField Price;

    @FXML
    private TextField Max;

    @FXML
    private TextField Min;

    @FXML
    private TableView<Parts> AddPartTable;

    @FXML
    private TableColumn<Parts, Integer> AddPartID;

    @FXML
    private TableColumn<Parts, String> AddName;

    @FXML
    private TableColumn<Parts, Integer> AddInventory;

    @FXML
    private TableColumn<Parts, String> AddPrice;

    @FXML
    private TableView<Parts> StoredPartTable;

    @FXML
    private TableColumn<Parts, Integer> StoredPartID;

    @FXML
    private TableColumn<Parts, String> StoredName;

    @FXML
    private TableColumn<Parts, Integer> StoredInventory;

    @FXML
    private TableColumn<Parts, String> StoredPrice;

    @FXML
    private Button Search;

    @FXML
    private Button Add;

    @FXML
    private Button Delete;

    @FXML
    private Button Cancel;
    
    @FXML
    private Button ResetPart;

    @FXML
    private Button Save;

    @FXML
    private Label NameWarning;

    @FXML
    private Label InventoryWarning;

    @FXML
    private Label PriceWarning;

    @FXML
    private Label MaxWarning;

    @FXML
    private Label MinWarning;

    @FXML
    void handleAddButtonAction(ActionEvent event) throws IOException {
        if (retrieveParts().isEmpty()) {
            mmController.DisplayErrorMessage("No Parts Available", "There are currently no parts to add");
            return;
        }
        Parts addPart = AddPartTable.getSelectionModel().getSelectedItem();

        addPartIndex = retrieveParts().indexOf(addPart);
        if (addPartIndex >= 0) {
            foundItems.add(addPart);
            StoredPartTable.setItems(FXCollections.observableList(foundItems));

        } else {
            mmController.DisplayErrorMessage("No Parts Selected", "You have not selected any parts!");
        }
    }

    @FXML
    void handleCancelButtonAction(ActionEvent event) throws IOException {
        Integer confirmCancel = mmController.DisplayQuestion("Warning!", "Product has not been saved!", "Would you like to continue to the main menu?", "Yes", "No");

        if (confirmCancel == 1) {
            mmController.DisplayMenu("MainMenu", event);
        }
    }

    @FXML
    void handleDeleteButtonAction(ActionEvent event) {

        if (foundItems.isEmpty()) {
            mmController.DisplayErrorMessage("No Parts Available", "There are currently no parts to delete");
            return;
        }
        
        Parts removePart = StoredPartTable.getSelectionModel().getSelectedItem();
        removePartIndex = foundItems.indexOf(removePart);
        if (removePartIndex >= 0) {
            foundItems.remove(removePartIndex);
            StoredPartTable.setItems(FXCollections.observableList(foundItems));

        } else {

            mmController.DisplayErrorMessage("No Parts Selected", "You have not selected any parts!");
        }
    }

    @FXML
    void handleIdGenTextFieldAction(ActionEvent event) {

    }

    @FXML
    void handleInventoryTextFieldAction(ActionEvent event) {

    }

    @FXML
    void handleMaxTextFieldAction(ActionEvent event) {

    }

    @FXML
    void handleMinTextFieldAction(ActionEvent event) {

    }

    @FXML
    void handlePartNameTextFieldAction(ActionEvent event) {

    }

    @FXML
    void handlePriceTextFieldAction(ActionEvent event) {

    }
    
    @FXML
    void handleResetPartButton(ActionEvent event) {
        AddPartTable.setItems(FXCollections.observableList(retrieveParts()));
        SearchBar.setText("");
    }

    @FXML
    void handleSaveButtonAction(ActionEvent event) throws IOException {
        int check = 0;
        totalCostOfParts = 0;

        mmController.CheckStyle(Name);
        mmController.CheckStyle(Price);
        mmController.CheckStyle(Inventory);
        mmController.CheckStyle(Max);
        mmController.CheckStyle(Min);

        if (foundItems.isEmpty()) {
            check++;
        }
        if (Name.getStyle().equals("-fx-background-color: pink;")) {
            check++;
        }
        if (Inventory.getStyle().equals("-fx-background-color: pink;")) {
            check++;
        }
        if (Price.getStyle().equals("-fx-background-color: pink;")) {
            check++;
        }
        if (Min.getStyle().equals("-fx-background-color: pink;")) {
            check++;
        }
        if (Max.getStyle().equals("-fx-background-color: pink;")) {
            check++;
        }       
        
       for(int i =  0; i < foundItems.size(); i++)
       {
           totalCostOfParts += foundItems.get(i).getPartPrice();
       }        

        if (check == 0) {
            prodId = getProductIdNumber();
            prodName = (Name.getText());
            prodInv = (Integer.parseInt(Inventory.getText()));
            prodPrice = (Double.parseDouble(Price.getText()));
            prodMin = (Integer.parseInt(Min.getText()));
            prodMax = (Integer.parseInt(Max.getText()));

            Products createProduct = new Products();
            createProduct.setProdId(prodId);
            createProduct.setProdName(prodName);
            createProduct.setProdInv(prodInv);
            createProduct.setProdPrice(prodPrice);
            createProduct.setProdMin(prodMin);
            createProduct.setProdMax(prodMax);
            createProduct.setContainedParts(foundItems);
            
            if(totalCostOfParts <= prodPrice) {
            addToProdList(createProduct);
            mmController.DisplayMenu("MainMenu", event);
            } else {
            mmController.DisplayErrorMessage("Price Error", "Price of the product must be more than the cost of the parts included in the product!");
            }

        } else {
            if (foundItems.isEmpty()) {
                mmController.DisplayErrorMessage("Part needed", "Product must contain at least 1 part in order to be saved!");
            } else {
                mmController.DisplayErrorMessage("Incomplete Part!", "Cannot save part, all fields MUST be filled in correctly!");
                check = 0;
            }
        }

    }

    @FXML
    void handleSearchBarTextFieldAction(ActionEvent event) {

    }

    @FXML
    void handleSearchButtonAction(ActionEvent event) {
        if (retrieveParts().isEmpty()) {
            mmController.DisplayErrorMessage("No Parts Available", "There are currently no parts to search");
            return;
        }

        String inquireParts = SearchBar.getText();

        if (partLookUp(inquireParts.toLowerCase()) == null) {
            mmController.DisplayErrorMessage("No Results", "Part not found!");
        } else {
            ObservableList<Parts> partsList = FXCollections.observableArrayList();
            partsList.add(partLookUp(inquireParts.toLowerCase()));
            AddPartTable.setItems(partsList);
        }
    }

    @Override

    public void initialize(URL location, ResourceBundle resources) {
        Inventory.setText("0");

        AddPartID.setCellValueFactory(cellValue -> cellValue.getValue().PartPrtId().asObject());
        AddName.setCellValueFactory(cellValue -> cellValue.getValue().PartPrtName());
        AddPrice.setCellValueFactory(cellValue -> Bindings.format("%.2f", cellValue.getValue().getPartPrice()));
        AddInventory.setCellValueFactory(cellValue -> cellValue.getValue().PartPrtInv().asObject());

        AddPartTable.setItems(FXCollections.observableList(retrieveParts()));

        StoredPartID.setCellValueFactory(cellValue -> cellValue.getValue().PartPrtId().asObject());
        StoredName.setCellValueFactory(cellValue -> cellValue.getValue().PartPrtName());
        StoredPrice.setCellValueFactory(cellValue -> Bindings.format("%.2f", cellValue.getValue().getPartPrice()));
        StoredInventory.setCellValueFactory(cellValue -> cellValue.getValue().PartPrtInv().asObject());

       Name.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {

                if (Name.getText().matches("^\\s*$")) {
                    Name.setStyle("-fx-background-color: pink;");
                    NameWarning.setText("A Part name is required!");
                } else {
                    Name.setStyle("-fx-background-color: lightgreen;");
                    NameWarning.setText("");
                }
            }
        });
        Inventory.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {

                if ((mmController.intValid(Inventory.getText()) != 1)) {
                    Inventory.setStyle("-fx-background-color: pink;");
                    InventoryWarning.setText("Please enter a whole number!");
                } else {
                    Inventory.setStyle("-fx-background-color: lightgreen;");
                    InventoryWarning.setText("");
                }
                if (mmController.intValid(Inventory.getText()) == 1) {
                    if ((mmController.intValid(Max.getText()) == 1) && (mmController.intValid(Min.getText()) == 1)) {
                        if ((Integer.parseInt(Inventory.getText()) >= (Integer.parseInt(Min.getText()))) && ((Integer.parseInt(Inventory.getText())) <= Integer.parseInt(Max.getText()))) {
                            Inventory.setStyle("-fx-background-color: lightgreen;");
                        } else {
                            Inventory.setStyle("-fx-background-color: pink;");
                            InventoryWarning.setText("Inventory MUST be between 'Min' and 'Max'!");
                        }
                    }
                }
            }
        });
        Price.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {

                if ((mmController.numValidation(Price.getText()) != 1)) {
                    Price.setStyle("-fx-background-color: pink;");
                    PriceWarning.setText("Price of part is required!");
                } else {
                    Price.setStyle("-fx-background-color: lightgreen;");
                    PriceWarning.setText("");
                }
            }
        });
        Max.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {

                if ((mmController.intValid(Max.getText()) != 1)) {
                    Max.setStyle("-fx-background-color: pink;");
                    MaxWarning.setText("Please enter a whole number!");
                } else {
                    Max.setStyle("-fx-background-color: lightgreen;");
                    MaxWarning.setText("");
                }
                if(mmController.intValid(Max.getText()) == 1) {
                if ((mmController.intValid(Inventory.getText()) == 1) && (mmController.intValid(Min.getText()) == 1)) {
                    if ((Integer.parseInt(Inventory.getText()) >= (Integer.parseInt(Min.getText()))) && ((Integer.parseInt(Inventory.getText())) <= Integer.parseInt(Max.getText()))) {
                        Inventory.setStyle("-fx-background-color: lightgreen;");
                        InventoryWarning.setText("");
                    } else {
                        Inventory.setStyle("-fx-background-color: pink;");
                        InventoryWarning.setText("Inventory MUST be between 'Min' and 'Max'!");
                    }
                }
                if ((mmController.intValid(Max.getText()) == 1) && (mmController.intValid(Min.getText()) == 1)) {
                    if (Integer.parseInt(Max.getText()) < Integer.parseInt(Min.getText())) {
                        Max.setStyle("-fx-background-color: pink;");
                        Min.setStyle("-fx-background-color: pink;");
                        MaxWarning.setText("'Max' cannot be less than 'Min'!");
                    } else {
                        Max.setStyle("-fx-background-color: lightgreen;");
                        Min.setStyle("-fx-background-color: lightgreen;");
                        MaxWarning.setText("");
                    }
                }
                }
            }
        });
        Min.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {

                if ((mmController.intValid(Min.getText()) != 1)) {
                    Min.setStyle("-fx-background-color: pink;");
                    MinWarning.setText("Please enter a whole number!");
                    return;
                }
                if ((mmController.intValid(Max.getText()) != 1)) {
                    Min.setStyle("-fx-background-color: lightgreen;");
                    return;
                }
                
                if(mmController.intValid(Min.getText()) == 1) {
                if ((mmController.intValid(Inventory.getText()) == 1) && (mmController.intValid(Max.getText()) == 1)) {
                    if ((Integer.parseInt(Inventory.getText()) >= (Integer.parseInt(Min.getText()))) && ((Integer.parseInt(Inventory.getText())) <= Integer.parseInt(Max.getText()))) {
                        Inventory.setStyle("-fx-background-color: lightgreen;");
                        InventoryWarning.setText("");
                    } else {
                        Inventory.setStyle("-fx-background-color: pink;");
                        InventoryWarning.setText("Inventory MUST be between 'Min' and 'Max'!");
                    }
                }
                if ((Integer.parseInt(Min.getText()) > (Integer.parseInt(Max.getText())))) {
                    Max.setStyle("-fx-background-color: pink;");
                    Min.setStyle("-fx-background-color: pink;");
                    MinWarning.setText("Min cannot be more than 'Max'!");
                } else {
                    Max.setStyle("-fx-background-color: lightgreen;");
                    Min.setStyle("-fx-background-color: lightgreen;");
                    MinWarning.setText("");
                }
                }
            }
        }
        );
    }
}
