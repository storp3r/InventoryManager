package View_Controller;

import Model.Inventory;
import static Model.Inventory.retrieveParts;
import static Model.Inventory.retrieveProducts;
import Model.Products;
import Model.Parts;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainMenuController implements Initializable {

    private int check;

    private static int modPartIndex;

    public static int getModPartIndex() {
        return modPartIndex;
    }

    private static int modProductIndex;

    public static int getModProductIndex() {
        return modProductIndex;
    }

    private static int deletePartIndex;

    public static int getdeletePartIndex() {
        return deletePartIndex;
    }

    private static int deleteProductIndex;

    public static int getdeleteProductIndex() {
        return deleteProductIndex;
    }

    @FXML
    private Button Exit;

    @FXML
    private TableView<Products> ProdTable;

    @FXML
    private TableColumn<Products, Integer> ProdPrtId;

    @FXML
    private TableColumn<Products, String> ProdPrtName;

    @FXML
    private TableColumn<Products, Integer> ProdPartInv;

    @FXML
    private TableColumn<Products, String> ProdPrtPrice;

    @FXML
    private TextField SearchProds;

    @FXML
    private Button SearchProd;

    @FXML
    private Button AddProd;

    @FXML
    private Button ModProd;

    @FXML
    private Button DeleteProd;

    @FXML
    private TextField SearchParts;

    @FXML
    public TableView<Parts> PartTable;

    @FXML
    public TableColumn<Parts, Integer> PartPrtId;

    @FXML
    private TableColumn<Parts, String> PartPrtName;

    @FXML
    private TableColumn<Parts, Integer> PartPrtInv;

    @FXML
    private TableColumn<Parts, String> PartPrtPrice;

    @FXML
    private Button AddPart;

    @FXML
    private Button ModPart;

    @FXML
    private Button DeletePart;

    @FXML
    private Button SearchPart;

    @FXML
    private Button ResetPart;

    @FXML
    private Button ResetProd;

    @FXML
    void handleAddPartButtonAction(ActionEvent event) throws IOException {
        DisplayMenu("AddParts", event);
    }

    @FXML
    void handleRowSelected(MouseEvent event) throws IOException {
    }

    @FXML
    void handleAddProdButtonAction(ActionEvent event) throws IOException {
        DisplayMenu("AddProduct", event);
    }

    @FXML
    void handleDeletePartButtonAction(ActionEvent event) {

        if (retrieveParts().isEmpty()) {
            DisplayErrorMessage("No Parts Available", "There are currently no parts to delete");
            return;
        }

        Parts deletePart = PartTable.getSelectionModel().getSelectedItem();
        deletePartIndex = retrieveParts().indexOf(deletePart);

        if (deletePartIndex >= 0) {
            Integer delConfirm = DisplayQuestion("Warning", "", "Are you sure you want to delete ' " + deletePart.getPartName()
                    + " ' from parts?", "Yes", "No");
            if (delConfirm == 1) {
                Inventory.removeFromList(deletePart);
                PartTable.setItems(FXCollections.observableList(retrieveParts()));
            }

        }

        if (deletePart == null) {
            DisplayErrorMessage("No Parts Selected", "You have not selected a part to delete.");
        }

    }

    @FXML
    void handleDeleteProdButtonAction(ActionEvent event) {

        if (retrieveProducts().isEmpty()) {
            DisplayErrorMessage("No Products Available", "There are currently no products to delete");
            return;
        }

        Products deleteProduct = ProdTable.getSelectionModel().getSelectedItem();
        deleteProductIndex = retrieveProducts().indexOf(deleteProduct);

        if (deleteProduct == null) {
            DisplayErrorMessage("No Products Selected", "You have not selected a product to delete.");
            return;
        }

        if (deleteProduct.getContainedParts().size() > 0) {
            DisplayErrorMessage("Error", "Cannot delete a product that contains parts, please remove all parts before trying to delete.");
        } else if (deletePartIndex >= 0) {
            Integer delConfirm = DisplayQuestion("Warning", "", "Are you sure you want to delete ' " + deleteProduct.getProdName()
                    + " ' from products?", "Yes", "No");
            if (delConfirm == 1) {
                Inventory.removeFromProductList(deleteProductIndex);
                ProdTable.setItems(FXCollections.observableList(retrieveProducts()));
            }
        }
    }

    @FXML
    void handleExitButtonAction(ActionEvent event) {
        Integer exitApp = DisplayQuestion("Exit", "You are about to close the application", "Would you like to continue?", "Yes", "No");

        if (exitApp == 1) {
            Platform.exit();
        }
    }

    @FXML
    void handleModPartButtonAction(ActionEvent event) throws IOException {

        if (retrieveParts().isEmpty()) {
            DisplayErrorMessage("No Parts Available", "There are currently no parts to modify");
            return;
        }

        Parts modifiedPart = PartTable.getSelectionModel().getSelectedItem();
        modPartIndex = retrieveParts().indexOf(modifiedPart);

        if (modPartIndex >= 0) {
            DisplayMenu("ModifyParts", event);
            return;
        }
        DisplayErrorMessage("No Parts Selected", "You have not selected a part to modify.");
    }

    @FXML
    void handleModifyProdButtonAction(ActionEvent event) throws IOException {

        Products modifiedProduct = ProdTable.getSelectionModel().getSelectedItem();
        modProductIndex = retrieveProducts().indexOf(modifiedProduct);

        if (retrieveProducts().isEmpty()) {
            DisplayErrorMessage("No Products Available", "There are currently no products to modify.");
            return;
        }

        if (modProductIndex >= 0) {
            DisplayMenu("ModifyProduct", event);
            return;
        }
        DisplayErrorMessage("No Parts Selected", "You have not selected a product to modify.");
    }

    @FXML
    void handleResetPartButton(ActionEvent event) {
        PartTable.setItems(FXCollections.observableList(retrieveParts()));
        SearchParts.setText("");
    }

    @FXML
    void handleResetProdButton(ActionEvent event) {
        ProdTable.setItems(FXCollections.observableList(retrieveProducts()));
        SearchProds.setText("");
    }

    @FXML
    void handleSearchPartButtonAction(ActionEvent event) {

        if (retrieveParts().isEmpty()) {
            DisplayErrorMessage("No Parts Available", "There are currently no parts to search.");
            return;
        }

        String inquireParts = SearchParts.getText();

        if (Inventory.partLookUp(inquireParts.toLowerCase()) == null) {
            DisplayErrorMessage("No Results", "Part was not found.");
            return;
        }
        ObservableList<Parts> partsList = FXCollections.observableArrayList();
        partsList.add(Inventory.partLookUp(inquireParts.toLowerCase()));
        PartTable.setItems(partsList);
    }

    @FXML
    void handleSearchPartTextFieldAction(ActionEvent event) {
    }

    @FXML
    void handleSearchProdButtonAction(ActionEvent event) {

        String inquireProducts = SearchProds.getText();

        if (retrieveProducts().isEmpty()) {
            DisplayErrorMessage("No Products Available", "There are currently no products to search.");
            return;
        }

        if (Inventory.prodLookUp(inquireProducts.toLowerCase()) == null) {
            DisplayErrorMessage("No Results", "Product was not found.");
            return;
        }
        ObservableList<Products> productsList = FXCollections.observableArrayList();
        productsList.add(Inventory.prodLookUp(inquireProducts.toLowerCase()));
        ProdTable.setItems(productsList);
    }

    @FXML
    void handleSearchProdTextFieldAction(ActionEvent event) {
    }

    void CheckStyle(TextField theFieldName) {

        if (theFieldName.getText().length() == 0) {
            theFieldName.setStyle("-fx-background-color: pink;");
            return;
        }
    }

    public void DisplayMenu(String menuName, ActionEvent event) throws IOException {
        Parent activeWindow = FXMLLoader.load(getClass().getResource(menuName + ".fxml"));
        Scene scene = new Scene(activeWindow);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        stage.show();
    }

    public void DisplayErrorMessage(String windowTitle, String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle(windowTitle);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    public int DisplayQuestion(String windowTitle, String headerText, String contentText, String optionYes, String optionNo) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle(windowTitle);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        ButtonType buttonOne = new ButtonType(optionYes);
        ButtonType buttonTwo = new ButtonType(optionNo);
        alert.getButtonTypes().setAll(buttonOne, buttonTwo);
        Optional<ButtonType> myResult = alert.showAndWait();

        if (myResult.get() == buttonOne) {
            return 1;
        }
        return 2;
    }

    public int numValidation(String input) {

        try {
            double dbl = Double.parseDouble(input);
            if (dbl >= 0) {
                return 1;
            }
        } catch (NumberFormatException e) {
            return -1;
        }
        return 0;
    }

    public int intValid(String input) {

        try {

            int num = Integer.parseInt(input);
            if (num >= 0) {
                return 1;
            }
        } catch (NumberFormatException e) {
            return -1;
        }
        return 0;
    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PartPrtId.setCellValueFactory(cellValue -> cellValue.getValue().PartPrtId().asObject());
        PartPrtName.setCellValueFactory(cellValue -> cellValue.getValue().PartPrtName());
        PartPrtPrice.setCellValueFactory(cellValue -> Bindings.format("%.2f", cellValue.getValue().getPartPrice()));
        PartPrtInv.setCellValueFactory(cellValue -> cellValue.getValue().PartPrtInv().asObject());
        PartTable.setItems(FXCollections.observableList(retrieveParts()));

        ProdPrtId.setCellValueFactory(cellValue -> cellValue.getValue().ProductId().asObject());
        ProdPrtName.setCellValueFactory(cellValue -> cellValue.getValue().ProductName());
        ProdPrtPrice.setCellValueFactory(cellValue -> Bindings.format("%.2f", cellValue.getValue().getProdPrice()));
        ProdPartInv.setCellValueFactory(cellValue -> cellValue.getValue().ProductInv().asObject());

        ProdTable.setItems(FXCollections.observableList(retrieveProducts()));

    }
}
