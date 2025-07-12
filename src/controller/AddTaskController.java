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
import java.time.LocalDate;

/**
 * Controller για το παράθυρο προσθήκης νέας εργασίας.
 * Διαχειρίζεται την εισαγωγή δεδομένων για την δημιουργία νέας εργασίας και
 * ενημερώνει τη λίστα των εργασιών (taskList) του κύριου παραθύρου.
 */
public class AddTaskController {

    @FXML
    private Button AddTask_button;

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

    private ObservableList<Task> taskList;

    /**
     * Θέτει τη λίστα εργασιών στην οποία θα προστεθεί η νέα εργασία.
     *
     * @param taskList η ObservableList που περιέχει τα Task της εφαρμογής.
     */
    public void setTaskList(ObservableList<Task> taskList) {
        this.taskList = taskList;
    }

    /**
     * Προσθέτει μια νέα εργασία στη λίστα.
     * Ελέγχει αν έχουν συμπληρωθεί τα απαραίτητα πεδία και δημιουργεί ένα νέο Task,
     * το οποίο προστίθεται στη λίστα, και κλείνει το παράθυρο.
     *
     * @param event το ActionEvent που προκάλεσε την κλήση της μεθόδου.
     */
    @FXML
    void AddTask(ActionEvent event) {
        if (taskList == null) {
            System.out.println("taskList is null!");
            return;
        }
    
        String title = Title_textfield.getText();
        String description = Description_textarea.getText();
        Category category = Category_box.getValue();
        Priority priority = Priority_box.getValue();
        Status selectedStatus = Status_box.getValue();
        LocalDate deadline = Reminder_datepicker.getValue();
    
        if (title.isEmpty() || category == null) {
            showAlert("Missing Fields", "Please fill all required fields.");
            return;
        }
    
        if (selectedStatus == null) {
            selectedStatus = new Status("Open");
        }

        if (priority == null) {
            priority = new Priority("Default");
        }

        Task newTask = new Task(title, description, category.getName(), priority, deadline, selectedStatus);
        
        taskList.add(newTask);
        closeWindow();
    }
    
    /**
     * Κλείνει το παράθυρο προσθήκης εργασίας χωρίς να αποθηκεύσει αλλαγές.
     *
     * @param event το ActionEvent που προκάλεσε την κλήση της μεθόδου.
     */
    @FXML
    void Cancel(ActionEvent event) {
        closeWindow();
    }

    /**
     * Κλείνει το τρέχον παράθυρο.
     */
    private void closeWindow() {
        Stage stage = (Stage) Cancel_button.getScene().getWindow();
        stage.close();
    }

    /**
     * Αρχικοποιεί το παράθυρο προσθήκης εργασίας.
     * Καλεί τις μεθόδους αρχικοποίησης για τις κατηγορίες, προτεραιότητες και καταστάσεις.
     */
    public void initialize() {
        initializeCategory();
        initializePriority();
        initializeStatus();
    }

    /**
     * Αρχικοποιεί το ComboBox κατηγοριών με τη λίστα κατηγοριών από το MainSceneController.
     */
    private void initializeCategory() {
        Category_box.setItems(MainSceneController.getCategoryList());
    }

    /**
     * Αρχικοποιεί το ComboBox προτεραιοτήτων με τη λίστα προτεραιοτήτων από το MainSceneController.
     */
    private void initializePriority() {
        Priority_box.setItems(MainSceneController.getPriorityList());
    }

    /**
     * Αρχικοποιεί το ComboBox καταστάσεων με τις προκαθορισμένες τιμές.
     */
    private void initializeStatus() {
        ObservableList<Status> status = FXCollections.observableArrayList(
                new Status("Open"),
                new Status("In Progress"),
                new Status("Postponed"),
                new Status("Completed"),
                new Status("Delayed")
        );
        Status_box.setItems(status);
    }

    /**
     * Εκτυπώνει στο console την επιλεγμένη κατηγορία όταν αλλάζει η επιλογή στο ComboBox.
     *
     * @param event το ActionEvent που προκάλεσε την κλήση της μεθόδου.
     */
    @FXML
    void SelectCategory(ActionEvent event) {
        Category selectedCategory = Category_box.getValue();
        System.out.println("Selected category: " + selectedCategory);
    }

    /**
     * Εκτυπώνει στο console την επιλεγμένη προτεραιότητα όταν αλλάζει η επιλογή στο ComboBox.
     *
     * @param event το ActionEvent που προκάλεσε την κλήση της μεθόδου.
     */
    @FXML
    void SelectPriority(ActionEvent event) {
        Priority selectedPriority = Priority_box.getValue();
        System.out.println("Selected Priority: " + selectedPriority);
    }

    /**
     * Εμφανίζει ένα alert με το δοσμένο μήνυμα.
     *
     * @param title ο τίτλος του alert.
     * @param message το περιεχόμενο του alert.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
