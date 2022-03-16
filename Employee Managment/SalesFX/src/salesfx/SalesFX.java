/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesfx;

import content.Employee;
import content.EmployeeFile;
import content.SearchStage;
import java.util.ArrayList;
import java.util.Optional;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class SalesFX extends Application {

    private final String BUTTON_ADD = "add";
    private final String BUTTON_UPDATE = "update";
    private final String BUTTON_DELETE = "delete";
    private final String BUTTON_SEARCH = "search";
    private final String BUTTON_FIRST = "first";
    private final String BUTTON_NEXT = "NEXT";
    private final String BUTTON_PREVIOUS = "previous";
    private final String BUTTON_LAST = "last";
    private final String BUTTON_CLEAR = "clear";

    private final Label labelId = new Label("Id:");
    private final Label labelName = new Label("Name:");
    private final Label labelCity = new Label("City:");
    private final Label labelPosition = new Label("Position:");

    private final TextField textFieldId = new TextField();
    private final TextField textFieldName = new TextField();
    private final TextField textFieldCity = new TextField();
    private final TextField textFieldPosition = new TextField();

    private final Button buttonAdd = new Button("Add");
    private final Button buttonUpdate = new Button("Update");
    private final Button buttonDelete = new Button("Delete");
    private final Button buttonSearch = new Button("Search");

    private final Button buttonFirst = new Button("First");
    private final Button buttonNext = new Button("Next");
    private final Button buttonPrevious = new Button("Previous");
    private final Button buttonLast = new Button("Last");
    private final Button buttonClear = new Button("Clear");

    private final Text textMessage = new Text();

    private final VBox vbox = new VBox();
    private final HBox hbox = new HBox();

    private static ArrayList<Employee> employeeList = new ArrayList<>();

    private int index = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        if (EmployeeFile.isFileExists()) {
            employeeList = EmployeeFile.readData();
        } else {
            EmployeeFile.setupFileAndWriteInitialData(employeeList);
        }

        // initialize fields
        initializeFields();

        // show first data
        firstData();

        // set scene
        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.setTitle("SalesFX");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    /**
     * Initialize all views 
     */
    private void initializeFields() {
        // set id to all buttons for click events
        buttonAdd.setId(BUTTON_ADD);
        buttonUpdate.setId(BUTTON_UPDATE);
        buttonSearch.setId(BUTTON_SEARCH);
        buttonDelete.setId(BUTTON_DELETE);
        buttonNext.setId(BUTTON_NEXT);
        buttonPrevious.setId(BUTTON_PREVIOUS);
        buttonFirst.setId(BUTTON_FIRST);
        buttonLast.setId(BUTTON_LAST);
        buttonClear.setId(BUTTON_CLEAR);

        // gridpane for label, textfield and button
        GridPane gridPane = new GridPane();
        gridPane.addRow(0, labelId, textFieldId, buttonAdd);
        gridPane.addRow(1, labelName, textFieldName, buttonUpdate);
        gridPane.addRow(2, labelCity, textFieldCity, buttonDelete);
        gridPane.addRow(3, labelPosition, textFieldPosition, buttonSearch);

        hbox.getChildren().addAll(buttonFirst, buttonNext, buttonPrevious, buttonLast, buttonClear);

        // set margin in all views 
        GridPane.setMargin(labelId, getInsets());
        GridPane.setMargin(labelName, getInsets());
        GridPane.setMargin(labelCity, getInsets());
        GridPane.setMargin(labelPosition, getInsets());

        GridPane.setMargin(textFieldId, new Insets(5, 5, 5, 0));
        GridPane.setMargin(textFieldName, new Insets(5, 5, 5, 0));
        GridPane.setMargin(textFieldCity, new Insets(5, 5, 5, 0));
        GridPane.setMargin(textFieldPosition, new Insets(5, 5, 5, 0));

        GridPane.setMargin(buttonAdd, new Insets(5, 5, 5, 0));
        GridPane.setMargin(buttonUpdate, new Insets(5, 5, 5, 0));
        GridPane.setMargin(buttonDelete, new Insets(5, 5, 5, 0));
        GridPane.setMargin(buttonSearch, new Insets(5, 5, 5, 0));

        HBox.setMargin(buttonFirst, getInsets());
        HBox.setMargin(buttonNext, new Insets(5, 5, 5, 0));
        HBox.setMargin(buttonPrevious, new Insets(5, 5, 5, 0));
        HBox.setMargin(buttonLast, new Insets(5, 5, 5, 0));
        HBox.setMargin(buttonClear, new Insets(5, 5, 5, 0));

        GridPane.setMargin(textMessage, getInsets());

        vbox.getChildren().addAll(gridPane, hbox, textMessage);

        buttonAdd.setOnAction(new SalesOperation());
        buttonUpdate.setOnAction(new SalesOperation());
        buttonDelete.setOnAction(new SalesOperation());
        buttonSearch.setOnAction(new SalesOperation());
        buttonFirst.setOnAction(new SalesOperation());
        buttonNext.setOnAction(new SalesOperation());
        buttonPrevious.setOnAction(new SalesOperation());
        buttonLast.setOnAction(new SalesOperation());
        buttonClear.setOnAction(new SalesOperation());
    }

    @Override
    public void stop() {
        Alert alertInfo = new Alert(AlertType.INFORMATION);
        alertInfo.setTitle("Message");
        alertInfo.setHeaderText("Message");
        alertInfo.setContentText("Data saved - Program Ending");
        Optional<ButtonType> result = alertInfo.showAndWait();
        alertInfo.show();

        if (!result.isPresent()) {
            alertInfo.close();
        } else if (result.get() == ButtonType.OK) {
            // Save file
            EmployeeFile.writeData(employeeList);
            alertInfo.close();
        }
    }

    /**
     * Local class event handle for several operations add, update, delete, and so on.
     */
    public class SalesOperation implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {

            switch (((Node) e.getSource()).getId()) {

                case BUTTON_ADD:
                    if (isValidate()) {
                        addData();
                    }
                    break;
                case BUTTON_UPDATE:
                    if (isValidate()) {
                        updateData();
                    }
                    break;

                case BUTTON_DELETE:
                    if (isValidate()) {
                        deleteData();
                    }
                    break;

                case BUTTON_SEARCH:
                    searchData();
                    break;

                case BUTTON_FIRST:
                    firstData();
                    break;

                case BUTTON_NEXT:
                    nextData();
                    break;

                case BUTTON_PREVIOUS:
                    previousData();
                    break;

                case BUTTON_LAST:
                    lastData();
                    break;

                case BUTTON_CLEAR:
                    clearText();
                    clearTextMessage();
                    break;

            }
        }
    }

    /**
     *
     * @return true if user filled all the details else false
     */
    private boolean isValidate() {

        if (textFieldId.getText().trim().isEmpty()) {
            setTextMessage("Please enter Id", Color.RED);
            textFieldId.requestFocus();
            return false;
        } else if (!isValidId()) {
            setTextMessage("Please enter valid Id", Color.RED);
            textFieldId.requestFocus();
            return false;
        } else if (textFieldName.getText().trim().isEmpty()) {
            setTextMessage("Please enter Name", Color.RED);
            textFieldName.requestFocus();
            return false;
        } else if (textFieldCity.getText().trim().isEmpty()) {
            setTextMessage("Please enter City", Color.RED);
            textFieldCity.requestFocus();
            return false;
        } else if (textFieldPosition.getText().trim().isEmpty()) {
            setTextMessage("Please enter Position", Color.RED);
            textFieldPosition.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * 
     * @return true if user entered valid id
     */
    private boolean isValidId() {
        try {
            int id = Integer.parseInt(textFieldId.getText().trim());
            System.out.println("Valid input");
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Input is not an int value");
            return false;
        }
    }

    /**
     * Add data into list
     */
    private void addData() {
        int pos = isDataInList();
        if (pos != -1) {
            updateList(pos);
            setTextMessage("Data already in list. Data updated successfully!", Color.BLUE);
            return;
        }
        // if data is not in list then add into list
        employeeList.add(new Employee(Integer.parseInt(textFieldId.getText()), textFieldName.getText(),
                textFieldCity.getText(), textFieldPosition.getText()));
        setTextMessage("Data added successfully!", Color.BLUE);
    }

    /**
     * Update data in list
     */
    private void updateData() {
        Alert alertInfo = new Alert(AlertType.CONFIRMATION);
        alertInfo.setTitle("Confirmation");
        alertInfo.setHeaderText("Confirmation");
        alertInfo.setContentText("Do you wish to Update?");
        Optional<ButtonType> result = alertInfo.showAndWait();
        alertInfo.show();

        if (!result.isPresent()) {
            alertInfo.close();
        } else if (result.get() == ButtonType.OK) {
            // update list
            int pos = isDataInList();
            if (pos != -1) {
                updateList(pos);
                setTextMessage("Data updated successfully!", Color.BLUE);
            } else {
                setTextMessage("Data is not found. Please click button to add data.", Color.BLUE);
            }
            alertInfo.close();

        } else if (result.get() == ButtonType.CANCEL) {
            alertInfo.close();
        }
    }

    /**
     * 
     * @param pos update list by position
     */
    private void updateList(int pos) {
        Employee employee = employeeList.get(pos);
        employee.setName(textFieldName.getText());
        employee.setCity(textFieldCity.getText());
        employee.setPosition(textFieldPosition.getText());
        employeeList.set(pos, employee);
    }

    /**
     * Check data is in list or not
     * @return position
     */
    private int isDataInList() {
        for (int i = 0; i < employeeList.size(); i++) {
            Employee employee = employeeList.get(i);
            if (employee.getId() == Integer.parseInt(textFieldId.getText())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Delete the data from list
     */
    private void deleteData() {
        Alert alertInfo = new Alert(AlertType.CONFIRMATION);
        alertInfo.setTitle("Confirmation");
        alertInfo.setHeaderText("Confirmation");
        alertInfo.setContentText("Do you wish to Delete?");
        Optional<ButtonType> result = alertInfo.showAndWait();
        alertInfo.show();

        if (!result.isPresent()) {
            alertInfo.close();
        } else if (result.get() == ButtonType.OK) {
            employeeList.remove(index);
            setText(employeeList.get(index));
            alertInfo.close();
        } else if (result.get() == ButtonType.CANCEL) {
            alertInfo.close();
        }

    }

    /**
     * Open search stage for searching the data
     */
    private void searchData() {
        clearTextMessage();
        SearchStage searchStage = new SearchStage(employeeList);
        searchStage.show();
    }

    /**
     * First data of the list will display
     */
    private void firstData() {
        index = 0;
        setText(employeeList.get(index));
        clearTextMessage();
    }

    /**
     * It displays next data of the list
     */
    private void nextData() {
        clearTextMessage();
        index++;
        if (index >= employeeList.size()) {
            index--;
            setTextMessage("This is last data!", Color.RED);
            return;
        }
        setText(employeeList.get(index));

    }

    /**
     * It displays previous data of the list
     */
    private void previousData() {
        clearTextMessage();
        index--;
        if (index < 0) {
            index++;
            setTextMessage("This is First data!", Color.RED);
            return;
        }
        setText(employeeList.get(index));
    }

    /**
     * Last data of the list will display
     */
    private void lastData() {
        index = employeeList.size() - 1;
        setText(employeeList.get(index));
        clearTextMessage();
    }

    /**
     * Clear all text field
     */
    private void clearText() {
        textFieldId.clear();
        textFieldName.clear();
        textFieldCity.clear();
        textFieldPosition.clear();
    }

    /**
     * Clear bottom text message text
     */
    private void clearTextMessage() {
        textMessage.setText("");
    }

    /**
     *
     * @param message set text message
     * @param paint set text color
     */
    private void setTextMessage(String message, Paint paint) {
        textMessage.setText(message);
        textMessage.setFill(paint);
    }

    /**
     *
     * @return get 4 side margin
     */
    private Insets getInsets() {
        return new Insets(5, 5, 5, 5);
    }

    /**
     * Display data from list object
     * @param employee 
     */
    private void setText(Employee employee) {
        textFieldId.setText(String.valueOf(employee.getId()));
        textFieldName.setText(employee.getName());
        textFieldCity.setText(employee.getCity());
        textFieldPosition.setText(employee.getPosition());
    }
}
