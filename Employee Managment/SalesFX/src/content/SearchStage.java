/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package content;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class SearchStage extends Stage {

    private final ArrayList<Employee> employeeList;

    private final ToggleGroup toggleGroup = new ToggleGroup();
    private final RadioButton radioButtonPosition = new RadioButton("Position");
    private final RadioButton radioButtonCity = new RadioButton("City");

    private final TextArea textDisplayData = new TextArea();

    private final TextField textFieldSearch = new TextField();
    private final Button buttonSearch = new Button("Search");

    private final VBox vbox = new VBox();
    private final HBox hboxRadio = new HBox();
    private final HBox hboxText = new HBox();
    private final HBox hboxSearch = new HBox();

    public SearchStage(ArrayList<Employee> employeeList) {
        this.employeeList = employeeList;

        // initialize search stage components
        initializeSearchFields();
    }
    
    /**
     * initialize search stage components
     */
    private void initializeSearchFields() {

        radioButtonPosition.setToggleGroup(toggleGroup);
        radioButtonCity.setToggleGroup(toggleGroup);
        radioButtonPosition.setSelected(true);

        buttonSearch.setId("search");

        HBox.setMargin(radioButtonPosition, new Insets(5, 5, 5, 5));
        HBox.setMargin(radioButtonCity, new Insets(5, 5, 5, 0));
        hboxRadio.getChildren().addAll(radioButtonPosition, radioButtonCity);

        textDisplayData.maxHeight(150);
        textDisplayData.maxWidth(150);
        hboxText.getChildren().add(textDisplayData);

        HBox.setMargin(textFieldSearch, new Insets(5, 5, 5, 5));
        HBox.setMargin(buttonSearch, new Insets(5, 5, 5, 0));
        hboxSearch.getChildren().addAll(textFieldSearch, buttonSearch);

        vbox.getChildren().addAll(hboxRadio, hboxText, hboxSearch);

        buttonSearch.setOnAction(new SearchOperation());

        // set scene
        Scene scene = new Scene(vbox, 300, 150);
        setScene(scene);
        setResizable(true);
        setTitle("Search Data");
    }

    /**
     * Local class event handle for search button
     */
    public class SearchOperation implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {

            if (((Node) e.getSource()).getId().equalsIgnoreCase("search")) {
                String searchString = textFieldSearch.getText().trim();
                // display search data using text
                textDisplayData.setText(getSearchData(searchString));
            }
        }
    }

    /**
     * 
     * @param searchString
     * @return search data from list by position or city
     */
    private String getSearchData(String searchString) {
        String searchData = "";
        for (int i = 0; i < employeeList.size(); i++) {
            Employee employee = employeeList.get(i);
            if (radioButtonCity.isSelected()) {
                if (employee.getCity().equalsIgnoreCase(searchString)) {
                    searchData += employee.toStringData() + "\n";
                }
            } else {
                if (employee.getPosition().equalsIgnoreCase(searchString)) {
                    searchData += employee.toStringData() + "\n";
                }
            }
        }
        if (searchData.isEmpty()) {
            searchData = "No data found!";
        }
        return searchData;
    }
}
