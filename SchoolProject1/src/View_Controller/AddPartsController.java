/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import static Model.Inventory.addToList;
import static Model.Inventory.getPartIdNumber;
import Model.InHoused;
import Model.Outsourced;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.InputMethodEvent;

public class AddPartsController implements Initializable {

    private static int partId;
    private static String partName;
    private static int partInv;
    private static double partPrice;
    private static int partMin;
    private static int partMax;

    MainMenuController mmController = new MainMenuController();

    @FXML
    private RadioButton InHoused;

    @FXML
    private ToggleGroup Source;

    @FXML
    private RadioButton OutSourced;

    @FXML
    private Label CompMachineWarning;

    @FXML
    private Label NameWarning;

    @FXML
    private Label InventoryWarning;

    @FXML
    private Label PriceWarning;

    @FXML
    private Label MinMaxWarning;

    @FXML
    private Label InOut;

    @FXML
    private TextField InOutInput;

    @FXML
    private TextField IdGen;

    @FXML
    private TextField Max;

    @FXML
    private TextField Min;

    @FXML
    private TextField Price;

    @FXML
    private TextField Name;

    @FXML
    private TextField Inventory;

    @FXML
    private Button Cancel;

    @FXML
    private Button Save;

    @FXML
    void handleCancelButtonAction(ActionEvent event) throws IOException {
        Integer confirmCancel = mmController.DisplayQuestion("Warning!", "Part has not been saved!", "Would you like to continue to the main menu?", "Yes", "No");

        if (confirmCancel == 1) {
            mmController.DisplayMenu("MainMenu", event);
        }
    }

    @FXML
    void handleIdGenTextFieldAction(ActionEvent event) {
    }

    @FXML
    void handleInHousedRadioAction(ActionEvent event) {
        InOut.setText("Machine ID :");
    }

    @FXML
    void handleInOutInputTextFIeld(ActionEvent event) {
    }

    @FXML
    void handleInOutLabelAction(ActionEvent event) {
    }

    @FXML
    void handleInventoryTextFieldAction(InputMethodEvent event) {
    }

    @FXML
    void handleMaxTextFieldAction(ActionEvent event) {
    }

    @FXML
    void handleMinTextFieldAction(ActionEvent event) {
    }

    @FXML
    void handleNameTextFieldAction(ActionEvent event) {
    }

    @FXML
    void handlePartNameText(InputMethodEvent event) {
    }

    @FXML
    void handleOutSourcedRadioAction(ActionEvent event) {
        InOut.setText("Company Name :");
    }

    @FXML
    void handlePriceTextFieldAction(ActionEvent event) {
    }

    @FXML
    void handleSaveButtonAction(ActionEvent event) throws IOException {
        int check = 0;

        mmController.CheckStyle(Name);
        mmController.CheckStyle(Inventory);
        mmController.CheckStyle(Price);
        mmController.CheckStyle(Max);
        mmController.CheckStyle(Min);

        if (Name.getStyle().equals("-fx-background-color: lightgreen;")) {
            check++;
        }
        if (Inventory.getStyle().equals("-fx-background-color: lightgreen;")) {
            check++;
        }
        if (Price.getStyle().equals("-fx-background-color: lightgreen;")) {
            check++;
        }
        if (Min.getStyle().equals("-fx-background-color: lightgreen;")) {
            check++;
        }
        if (Max.getStyle().equals("-fx-background-color: lightgreen;")) {
            check++;
        }
         if (InOutInput.getStyle().equals("-fx-background-color: lightgreen;")) {
            check++;
        }
        

        if (check == 6) {
            partId = getPartIdNumber();
            partName = (Name.getText());
            partInv = (Integer.parseInt(Inventory.getText()));
            partPrice = (Double.parseDouble(Price.getText()));
            partMin = (Integer.parseInt(Min.getText()));
            partMax = (Integer.parseInt(Max.getText()));

            if (InOut.getText() != "Machine ID :") {
                Outsourced createPart = new Outsourced();
                createPart.setIdNumber(partId);
                createPart.setPartName(partName);
                createPart.setPartInv(partInv);
                createPart.setPartPrice(partPrice);
                createPart.setPartMin(partMin);
                createPart.setPartMax(partMax);
                createPart.setCompanyName(InOutInput.getText());
                addToList(createPart);
            } else {
                InHoused createPart = new InHoused();
                createPart.setIdNumber(partId);
                createPart.setPartName(partName);
                createPart.setPartInv(partInv);
                createPart.setPartPrice(partPrice);
                createPart.setPartMin(partMin);
                createPart.setPartMax(partMax);
                createPart.setMachineId(Integer.parseInt(InOutInput.getText()));
                addToList(createPart);
            }
            mmController.DisplayMenu("MainMenu", event);
            return;
        } else {
            mmController.DisplayErrorMessage("Incomplete Part!", "Cannot save part, all fields MUST be filled in correctly!");
            check = 0;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
                    MinMaxWarning.setText("Please enter a whole number!");
                } else {
                    Max.setStyle("-fx-background-color: lightgreen;");
                    MinMaxWarning.setText("");
                }
                if (mmController.intValid(Max.getText()) == 1) {
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
                            MinMaxWarning.setText("'Min' cannot be more than 'Max'!");
                        } else {
                            Max.setStyle("-fx-background-color: lightgreen;");
                            Min.setStyle("-fx-background-color: lightgreen;");
                            MinMaxWarning.setText("");
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
                    MinMaxWarning.setText("Please enter a whole number!");
                    return;
                }
                if ((mmController.intValid(Max.getText()) != 1)) {
                    Min.setStyle("-fx-background-color: lightgreen;");
                    return;
                }

                if (mmController.intValid(Min.getText()) == 1) {
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
                        MinMaxWarning.setText("Min cannot be more than 'Max'!");
                    } else {
                        Max.setStyle("-fx-background-color: lightgreen;");
                        Min.setStyle("-fx-background-color: lightgreen;");
                        MinMaxWarning.setText("");
                    }
                }
            }
        }
        );
        InOut.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                if (((InOut.getText()) == ("Machine ID :")) && (mmController.intValid(InOutInput.getText()) != 1)) {
                    InOutInput.setStyle("-fx-background-color: pink;");
                    CompMachineWarning.setText("Please enter a whole number!");
                } else {
                    InOutInput.setStyle("-fx-background-color: lightgreen;");
                    CompMachineWarning.setText("");
                }
            }
        });
        InOutInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {

                if (InOutInput.getText().matches("^\\s*$")) {
                    InOutInput.setStyle("-fx-background-color: pink;");
                    CompMachineWarning.setText("This field is required!");
                } else {
                    if (((InOut.getText()) == ("Machine ID :")) && (mmController.intValid(InOutInput.getText()) != 1)) {
                        InOutInput.setStyle("-fx-background-color: pink;");
                        CompMachineWarning.setText("Please enter a whole number!");
                    } else {
                        InOutInput.setStyle("-fx-background-color: lightgreen;");
                        CompMachineWarning.setText("");
                    }
                }
            }

        });
    }
}
