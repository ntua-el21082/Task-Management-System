package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Task;
import model.Category;
import model.Priority;
import model.Status;

/**
 * Controller για το παράθυρο επεξεργασίας εργασίας.
 * Διαχειρίζεται την προβολή και επεξεργασία των στοιχείων μιας υπάρχουσας εργασίας.
 */
public class EditTaskController {

    @FXML
    private Button SaveTask_button;

    @FXML
    private Button Cancel_button;

    @FXML
    private ComboBox<Category> Category_box;

    @FXML
    private ComboBox<Status> Status_box;

    @FXML
    private TextArea Description_textarea;

    @FXML
    private ComboBox<Priority> Priority_box;

    @FXML
    private DatePicker Reminder_datepicker;

    @FXML
    private TextField Title_textfield;

    private Task task;

    /**
     * Θέτει την εργασία που πρόκειται να επεξεργαστεί.
     * Ενημερώνει τα πεδία της φόρμας με τις τρέχουσες τιμές της εργασίας.
     *
     * @param task η εργασία που θα επεξεργαστεί.
     */
    public void setTask(Task task) {
        this.task = task;
        Title_textfield.setText(task.getTitle());
        Description_textarea.setText(task.getDescription());
        Category_box.setValue(new Category(task.getCategory()));
        Priority_box.setValue(task.getPriority());
        Status_box.setValue(task.getStatus());
        Reminder_datepicker.setValue(task.getDeadline());
    }

    /**
     * Θέτει τη λίστα εργασιών. (Δεν χρησιμοποιείται στην τρέχουσα υλοποίηση.)
     *
     * @param taskList η ObservableList των εργασιών.
     */
    public void setTaskList(ObservableList<Task> taskList) {
    }

    /**
     * Αποθηκεύει τις αλλαγές στην εργασία με βάση τα πεδία της φόρμας.
     * Ενημερώνει τα στοιχεία της εργασίας και κλείνει το παράθυρο επεξεργασίας.
     *
     * @param event το ActionEvent που προκάλεσε την αποθήκευση.
     */
    @FXML
    void SaveTask(ActionEvent event) {
        task.setTitle(Title_textfield.getText());
        task.setDescription(Description_textarea.getText());
        task.setCategory(Category_box.getValue().getName());
        task.setPriority(Priority_box.getValue());
        task.setStatus(Status_box.getValue());
        task.setDeadline(Reminder_datepicker.getValue());

        closeWindow();
    }

    /**
     * Ακυρώνει την επεξεργασία και κλείνει το παράθυρο.
     *
     * @param event το ActionEvent που προκάλεσε την ακύρωση.
     */
    @FXML
    void Cancel(ActionEvent event) {
        closeWindow();
    }

    /**
     * Κλείνει το παράθυρο επεξεργασίας.
     */
    private void closeWindow() {
        Stage stage = (Stage) Cancel_button.getScene().getWindow();
        stage.close();
    }

    /**
     * Αρχικοποιεί το παράθυρο επεξεργασίας.
     * Καλεί τις μεθόδους αρχικοποίησης για τις κατηγορίες, προτεραιότητες και καταστάσεις.
     */
    public void initialize() {
        initializeCategory();
        initializePriority();
        initializeStatus();
    }

    /**
     * Αρχικοποιεί το ComboBox κατηγοριών με προκαθορισμένες τιμές.
     */
    private void initializeCategory() {
        ObservableList<Category> categories = FXCollections.observableArrayList(
                new Category("Work"),
                new Category("Personal"),
                new Category("Health"),
                new Category("Other")
        );
        Category_box.setItems(categories);
    }

    /**
     * Αρχικοποιεί το ComboBox προτεραιοτήτων με προκαθορισμένες τιμές.
     */
    private void initializePriority() {
        ObservableList<Priority> priorities = FXCollections.observableArrayList(
                new Priority("High"),
                new Priority("Moderate"),
                new Priority("Low")
        );
        Priority_box.setItems(priorities);
    }

    /**
     * Αρχικοποιεί το ComboBox καταστάσεων με τις προκαθορισμένες τιμές.
     */
    private void initializeStatus() {
        ObservableList<Status> statuses = FXCollections.observableArrayList(
                new Status("Open"),
                new Status("In Progress"),
                new Status("Postponed"),
                new Status("Completed"),
                new Status("Delayed")
        );
        Status_box.setItems(statuses);
    }
}
