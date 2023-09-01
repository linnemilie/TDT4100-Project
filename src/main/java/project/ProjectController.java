package project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

public class ProjectController {

    @FXML
    private DatePicker startDate, endDate;

    @FXML
    private GridPane vehicleGrid;

    @FXML
    private TextArea adminOutput;

    @FXML
    private Label chosenVehicle, priceLabel, bookingConfirmationLabel, approvedDiscountLabel, successLabelNewDiscount, successLabelDiscountRemoved;

    @FXML
    private Button book, adminButton, discountbutton, adminLoginButton, backButton, adminsearchButton, adminBackButton;

    @FXML
    private Pane driverInformation, bookingConfirmationSide, datePickerPane, adminLogin, adminPane;

    @FXML
    private TextField firstName, lastName, phoneNumber, email, age, discountTextField, adminUsername, adminPassword, searchField, newDiscountCode, newDiscountValue, oldDiscountCode;

    private LocalDate tempStartDate;
    private LocalDate tempEndDate;
    private String discountCode;
    private HeadOfBooking headOfBooking;

    @FXML
    private void initialize() throws FileNotFoundException {
        this.headOfBooking = new HeadOfBooking();
        startDate.getEditor().setDisable(true);
        endDate.getEditor().setDisable(true);
    }

    @FXML
    private void handleCheckDates(ActionEvent event){
        this.tempStartDate = startDate.getValue();
        this.tempEndDate = endDate.getValue();
        try {
            headOfBooking.setFreeVehicles(tempStartDate, tempEndDate);
            setUpGrid();
        }
        catch (IllegalArgumentException error) {
            System.out.println(error);
            showErrorMessage(error.getMessage());
        }
    }

    @FXML
    private void handleVehicleSelection(Vehicle vehicle) {
        headOfBooking.setSelectedVehicle(vehicle);
        chosenVehicle.setText(headOfBooking.selectedVehicle.vehicleTextToString());
        chosenVehicle.setFont(Font.font("normal", FontWeight.BOLD, 12));
        chosenVehicle.setTextFill(Color.BLACK);
        chosenVehicle.setStyle("-fx-text-alignment: center;");
        driverInformation.setDisable(false);
        discountTextField.clear();
        discountTextField.setVisible(true);
        discountbutton.setVisible(true);
        approvedDiscountLabel.setVisible(false);
    }

    @FXML
    private void setUpGrid() {
        vehicleGrid.setVisible(true);
        int columnCount = 0;
        int rowCount = 0;
        for (Vehicle vehicle : headOfBooking.getFreeVehicles()) {
            String vehiclePictureName = "vehicle" + vehicle.getId() + ".png";
            ImageView imageView = new ImageView(getClass().getResource(vehiclePictureName).toExternalForm());
            imageView.setFitHeight(80);
            imageView.setFitWidth(130);
            vehicleGrid.add(createVehicleButton(vehicle, imageView), columnCount, rowCount);
            columnCount++;
            if (columnCount == 4) {
                rowCount++;
                columnCount = 0;
            }

        }
    }

    @FXML
    private Node createVehicleButton(Vehicle vehicle, ImageView imageView) {
        Button button = new Button();
        button.setText(vehicle.getVehicleName()); 
        button.setFont(Font.font("normal", FontWeight.EXTRA_BOLD, 22));
        button.setStyle("-fx-text-alignment: center;");
        button.setMaxSize(Double.MAX_VALUE, 200);
        button.setGraphic(imageView);
        button.setContentDisplay(ContentDisplay.TOP);
        button.setOnAction((event) -> handleVehicleSelection(vehicle));
        return button;
    }

    @FXML
    private void resetVehicleGrid(ActionEvent event) {
        vehicleGrid.getChildren().clear();
        headOfBooking.setSelectedVehicle(null);
        chosenVehicle.setText(null);
        driverInformation.setDisable(true);
    }

    @FXML
    private void handleBookButton(ActionEvent event) throws IOException, IllegalArgumentException {
        RandomUUIDGenerator randomeUUIDGenerator = new RandomUUIDGenerator();
        try {
            headOfBooking.person = new Person(firstName.getText(), lastName.getText(), phoneNumber.getText(),
                    age.getText(), email.getText());
            headOfBooking.makeNewBooking(this.tempStartDate, this.tempEndDate, headOfBooking.person, this.discountCode,
                    "bookingFile", randomeUUIDGenerator);
            handleBookingConfirmation();
        } catch (IllegalArgumentException error) {
            System.out.println(error);
            showErrorMessage(error.getMessage());
        }
    }

    @FXML
    private void handleBackButton(ActionEvent event) {
        adminPane.setVisible(false);
        driverInformation.setVisible(true);
        bookingConfirmationSide.setVisible(false);
        datePickerPane.setVisible(true);
        adminButton.setVisible(true);
        adminLogin.setVisible(false);
        adminOutput.setText(null);
        searchField.clear();
        adminUsername.clear();
        adminPassword.clear();
        approvedDiscountLabel.setVisible(false);
        discountTextField.clear();
        discountTextField.setVisible(true);
        discountbutton.setVisible(true);
    }

    @FXML
    private void handleBookingConfirmation() {
        bookingConfirmationSide.setVisible(true);
        driverInformation.setVisible(false);
        vehicleGrid.setVisible(false);
        datePickerPane.setVisible(false);
        firstName.clear();
        lastName.clear();
        phoneNumber.clear();
        email.clear();
        discountTextField.clear();
        age.clear();
        chosenVehicle.setVisible(false);
        bookingConfirmationLabel.setText(headOfBooking.tempBooking.getBookingConfirmationText());
    }

    @FXML
    private void handleAdminMode(ActionEvent event) {
        adminLogin.setVisible(true);
        adminButton.setVisible(false);
        vehicleGrid.setVisible(false);
        adminBackButton.setVisible(true);
        driverInformation.setDisable(true);
    }

    @FXML
    private void handleAdminModeLogin(ActionEvent event) {
        try {
            headOfBooking.adminMode.verifyAdminLogIn(adminUsername.getText(), adminPassword.getText());
            adminPane.setVisible(true);
            adminLogin.setVisible(false);
        } 
        catch (IllegalArgumentException error) {
            System.out.println(error);
            showErrorMessage(error.getMessage());
        }
    }

    @FXML
    private void handleSearchForBookings(ActionEvent event) {
        adminOutput.setText(null);
        try {
            String bookingString = headOfBooking.adminMode.getBookingsWithSearchword(searchField.getText(), headOfBooking.fileHandler.readFromFile("bookingFile"));
            adminOutput.setText(bookingString);
            adminOutput.setEditable(false);
            adminOutput.setVisible(true);
        } 
        catch (FileNotFoundException error) {
            System.out.println(error);
            showErrorMessage(error.getMessage());
        } 
        catch (IllegalArgumentException error) {
            System.out.println(error);
            System.out.println(error);
            showErrorMessage(error.getMessage());
        }
    }

    @FXML
    private void handleValidateDiscount(ActionEvent event) {
        this.discountCode = discountTextField.getText();
        try {
            headOfBooking.discountHandler.checkIfValidDiscount(discountCode);
            approvedDiscountLabel.setVisible(true);
            discountTextField.setVisible(false);
            discountbutton.setVisible(false);
            chosenVehicle.setText(headOfBooking.selectedVehicle.vehicleTextToString() + headOfBooking.discountHandler.getVehicleDiscountText(discountCode));
        } 
        catch (IllegalArgumentException e) {
            discountTextField.clear();
            this.discountCode = null;
            System.out.println(e);
            showErrorMessage(e.getMessage());
        }
    }

    @FXML
    private void addNewDiscount(ActionEvent event) throws NumberFormatException, IOException {
        try {
            successLabelNewDiscount.setVisible(false);
            headOfBooking.discountHandler.addDiscount(newDiscountCode.getText(), Integer.parseInt(newDiscountValue.getText()), "discountFile");
            newDiscountCode.clear();
            newDiscountValue.clear();
            successLabelNewDiscount.setVisible(true);
        } catch (NumberFormatException nFe) {
            System.out.println(nFe);
            showErrorMessage("Input field for discount value must be filled and contain only digits");
        } catch (IllegalArgumentException error) {
            System.out.println(error);
            System.out.println(error);
            showErrorMessage(error.getMessage());
        }
    }

    @FXML
    private void removeDiscount(ActionEvent event) throws FileNotFoundException, IOException {
        try {
            successLabelDiscountRemoved.setVisible(false);
            headOfBooking.discountHandler.removeDiscount(oldDiscountCode.getText(), "discountFile");
            oldDiscountCode.clear();
            successLabelDiscountRemoved.setVisible(true);
        } 
        catch (IllegalArgumentException e) {
            System.out.println(e);
            showErrorMessage(e.getMessage());
        }
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("ErrorMessage");
        alert.setTitle("An error occured");
        alert.setContentText(message);
        alert.showAndWait();
    }

}
