package View_Controller;

import Model.InHoused;
import static Model.Inventory.modifyPart;
import static Model.Inventory.retrieveParts;
import Model.Outsourced;
import Model.Parts;
import static View_Controller.MainMenuController.getModPartIndex;
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
import javafx.scene.layout.Pane;

public class ModifyPartsController implements Initializable {

    private static int modPartId;
    private static String modPartName;
    private static int modPartInv;
    private static double modPartPrice;
    private static int modPartMin;
    private static int modPartMax;

    MainMenuController mmController = new MainMenuController();

    @FXML
    private Pane ModifyForm;

    @FXML
    private RadioButton InHoused;

    @FXML
    private ToggleGroup Source;

    @FXML
    private RadioButton OutSourced;

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
    private TextField PartName;

    @FXML
    private TextField Inventory;

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
    private Button Cancel;

    @FXML
    private Button Save;

    @FXML
    void handleCancelButtonAction(ActionEvent event) throws IOException {
        Integer confirmCancel = mmController.DisplayQuestion("Warning!", "Changes to part have not been saved!", "Would you like to continue to the main menu?", "Yes", "No");

        if (confirmCancel == 1) {
            mmController.DisplayMenu("MainMenu", event);
        }
    }

    @FXML
    void handleIdGenTextFieldAction(ActionEvent event) {

    }

    @FXML
    void handleInHousedRadioButtonAction(ActionEvent event) {
        InOut.setText("Machine ID :");
    }

    @FXML
    void handleInOutInputTextFieldAction(ActionEvent event) {

    }

    @FXML
    void handleInOutLabelAction(ActionEvent event) {

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
    void handleOutSourcedRadioButtonAction(ActionEvent event) {
        InOut.setText("Company Name :");
    }

    @FXML
    void handlePartNameTextFieldAction(ActionEvent event) {

    }

    @FXML
    void handlePriceTextFieldAction(ActionEvent event) {

    }

    @FXML
    void handleSaveButtonAction(ActionEvent event) throws IOException {

        int check = 0;

        if (PartName.getStyle().equals("-fx-background-color: pink;")) {
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
        if (InOutInput.getStyle().equals("-fx-background-color: pink;")) {
            check++;
        }

        if (check == 0) {
            modPartId = (Integer.parseInt(IdGen.getText()));
            modPartName = (PartName.getText());
            modPartInv = (Integer.parseInt(Inventory.getText()));
            modPartPrice = (Double.parseDouble(Price.getText()));
            modPartMin = (Integer.parseInt(Min.getText()));
            modPartMax = (Integer.parseInt(Max.getText()));

            Parts createPart = new Parts();
            createPart.setIdNumber(modPartId);
            createPart.setPartName(modPartName);
            createPart.setPartInv(modPartInv);
            createPart.setPartPrice(modPartPrice);
            createPart.setPartMin(modPartMin);
            createPart.setPartMax(modPartMax);
            modifyPart(createPart, getModPartIndex());

            mmController.DisplayMenu("MainMenu", event);

        } else {
            mmController.DisplayErrorMessage("Incomplete Part!", "Cannot save part, please make sure all fields have been filled in correctly!");
            check = 0;
        }
    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Parts alteredPart = retrieveParts().get(getModPartIndex());
        IdGen.setText(String.valueOf(alteredPart.getIdNumber()));
        PartName.setText(alteredPart.getPartName());
        Inventory.setText(String.valueOf(alteredPart.getPartInv()));
        Price.setText(String.format("%.2f", alteredPart.getPartPrice()));
        Min.setText(String.valueOf(alteredPart.getPartMin()));
        Max.setText(String.valueOf(alteredPart.getPartMax()));

        if (alteredPart instanceof InHoused) {
            InOutInput.setText(Integer.toString(((InHoused) retrieveParts().get(getModPartIndex())).getMachineId()));
            InOut.setText("Machine ID :");
            InHoused.setSelected(true);
        } else {
            InOutInput.setText((((Outsourced) retrieveParts().get(getModPartIndex())).getCompanyName()));
            InOut.setText("Company Name :");
            OutSourced.setSelected(true);
        }

        PartName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {

                if (PartName.getText().matches("^\\s*$")) {
                    PartName.setStyle("-fx-background-color: pink;");
                    NameWarning.setText("A Part name is required!");
                } else {
                    PartName.setStyle("-fx-background-color: lightgreen;");
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
