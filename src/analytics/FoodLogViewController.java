package analytics;

import dao.DaoException;
import dao.FoodRecordDao;
import foodmood.FoodRecord;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import testHarness.Tests;

/**
 * The controller for the FXML food log viewer.
 *
 * The user can select the date range to be displayed. If the start date occurs
 * after the end date the plot will not be updated. On first launch it will
 * display the records entered for the current day and seven days previous. The
 * user can change the date range.
 *
 * @author jsm158
 */
public class FoodLogViewController implements Initializable {

    /**
     * The list of food records to display
     */
    @FXML
    private ArrayList<FoodRecord> foods;

    /**
     * The tree list displaying the food records
     */
    @FXML
    private TreeView<FoodRecord> treeView;

    /**
     * The start date selector
     */
    @FXML
    private DatePicker startDatePicker;

    /**
     * The end date selector
     */
    @FXML
    private DatePicker endDatePicker;

    /**
     * Constructs a new FoodLogViewController
     */
    public FoodLogViewController() {
    }

    /**
     * Initializes the controller class.
     *
     * @param url the url (path) to the associated FXML document
     * @param rb the application's resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            handleChangeDatesButton(null);
            Tests.testFoodLogViewControllerGetRecords();
            handleDeleteRecordButton(null);
            Tests.testFoodLogViewControllerDeleteRecord();

        } catch (DaoException ex) {
            Logger.getLogger(FoodLogViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        Parent root;
        Scene scene;
        Stage stage;
        
        try {
            root = FXMLLoader.load(getClass().getResource("/analytics/MoodLogView.fxml"));
            scene = new Scene(root);
            stage = (Stage) startDatePicker.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(FoodLogViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the date change button by updating log view to show entries
     * between the selected dates.
     *
     * If the selected start date is later than the selected end date the view
     * will not be updated and an error message will be thrown.
     *
     * @param e
     */
    @FXML
    private void handleChangeDatesButton(ActionEvent e) throws DaoException {
        LocalDate startDate = LocalDate.of(2017, Month.SEPTEMBER, 1);
        LocalDate endDate = LocalDate.of(2017, Month.SEPTEMBER, 6);
        FoodRecordDao dao = new FoodRecordDao();

        foods = dao.getFoodRecords(startDate, endDate, 0);
    }

    /**
     * Deletes the selected food record
     *
     * @param e the associated event
     */
    @FXML
    private void handleDeleteRecordButton(ActionEvent e) throws DaoException {
        FoodRecordDao dao = new FoodRecordDao();
//        foods.remove(0);
        dao.saveFoodRecords(foods);
    }

    /**
     * Updates the ObservableList
     *
     * Throws an IllegalArgumentException if the startDate occurs after the end
     * date.
     *
     * @param startDate the start date
     * @param endDate the end date
     */
    private void getRecords(LocalDate startDate, LocalDate endDate) {
        // TODO
    }

}
