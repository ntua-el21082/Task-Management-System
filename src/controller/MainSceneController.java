package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import model.Category;
import model.Priority;
import model.Reminder;
import model.Status;
import model.Task;

import utils.DataManager;


/**
 * Controller για το κύριο σκηνικό της εφαρμογής διαχείρισης εργασιών.
 * Περιέχει τις λειτουργίες CRUD για tasks, categories, priorities, reminders καθώς και
 * λειτουργίες φιλτραρίσματος, αποθήκευσης και ενημέρωσης counters.
 */
public class MainSceneController {

    private ObservableList<Task> taskList = FXCollections.observableArrayList();

    private static ObservableList<Category> categoryList = FXCollections.observableArrayList();
    
    private static ObservableList<Priority> priorityList = FXCollections.observableArrayList();

    @FXML
    private TextField searchTitleField;
    
    @FXML
    private ComboBox<Category> filterCategoryBox;

    @FXML
    private ComboBox<Priority> filterPriorityBox;

    @FXML
    private Button AddTask_button;

    @FXML
    private Button DeleteTask_button;

    @FXML
    private Button EditTask_button;

    @FXML
    private TableView<Task> TaskTable;

    @FXML
    private TableColumn<Task, String> categoryColumn;

    @FXML
    private TableColumn<Task, String> deadlineColumn;

    @FXML
    private TableColumn<Task, String> priorityColumn;

    @FXML
    private TableColumn<Task, String> titleColumn;

    @FXML
    private TableColumn<Task, String> statusColumn;

    @FXML
    private Label lblCompletedTasks;

    @FXML
    private Label lblDelayedTasks;

    @FXML
    private Label lblDueSoonTasks;

    @FXML
    private Label lblTotalTasks;

    @FXML
    private Button AddCategory_button;

    @FXML
    private TableView<Category> CategoryTable;

    @FXML
    private TableColumn<Category, String> nameColumn;

    @FXML
    private Button DeleteCategory_button;

    @FXML
    private Button EditCategory_button;

    @FXML
    private Button AddReminder_button;

    @FXML
    private Button DeleteReminder_button;

    @FXML
    private Button EditReminder_button;

    @FXML
    private TableView<Reminder> ReminderTable;
    
    @FXML
    private TableColumn<Reminder, String> rNameColumn;
    
    @FXML
    private TableColumn<Reminder, String> taskTitleColumn;
    
    @FXML
    private TableColumn<Reminder, String> reminderDateColumn;
    
    @FXML
    private TableColumn<Reminder, String> reminderTypeColumn;

    private ObservableList<Reminder> reminderList = FXCollections.observableArrayList();

    @FXML
    private Button AddPriority_button;
    
    @FXML
    private Button DeletePriority_button;

    @FXML
    private Button EditPriority_button;

    @FXML
    private TableView<Priority> PriorityTable;

    @FXML
    private TableColumn<Priority, String> piorityNameColumn;

    @FXML
    private ListView<Task> dueSoonListView;
   
    @FXML
    private Button SaveExit_button;
    
    
    /**
     * Ενημερώνει τους μετρητές εργασιών (total, completed, delayed, due soon) και καλεί την ενημέρωση της λίστας των due soon tasks.
     */
    private void updateTaskCounters() {
        int total = taskList.size();
        long completed = taskList.stream()
                                 .filter(t -> t.getStatus().getName().equals("Completed"))
                                 .count();
        long delayed = taskList.stream()
                               .filter(t -> t.getStatus().getName().equals("Delayed"))
                               .count();
        long dueSoon = taskList.stream()
                               .filter(t -> {
                                   if (t.getStatus().getName().equals("Completed")) return false;
                                   if (t.getDeadline() == null) return false;
                                   if (t.getDeadline().isBefore(LocalDate.now())) return false;
                                   return !t.getDeadline().isAfter(LocalDate.now().plusDays(7));
                               })
                               .count();
    
        lblTotalTasks.setText(String.valueOf(total));
        lblCompletedTasks.setText(String.valueOf(completed));
        lblDelayedTasks.setText(String.valueOf(delayed));
        lblDueSoonTasks.setText(String.valueOf(dueSoon));
        
        updateDueSoonTasks();
    }
    
    
    /**
     * Ανοίγει το παράθυρο προσθήκης νέας εργασίας.
     * Φορτώνει το FXML για την προσθήκη εργασίας και περνάει τη λίστα των εργασιών στον αντίστοιχο controller.
     */
    @FXML
    private void OpenAddTask() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddTask.fxml"));
            Parent root = loader.load();

            // Παίρνουμε τον controller και του περνάμε τη λίστα εργασιών
            AddTaskController controller = loader.getController();
            controller.setTaskList(taskList);

            Stage stage = new Stage();
            stage.setTitle("Add Task");
            stage.setScene(new Scene(root));

            // Κάνει το παράθυρο modal ώστε να μην μπορείς να αλληλεπιδράσεις με το κύριο
            stage.initModality(Modality.WINDOW_MODAL);

            // Εμφανίζει το νέο παράθυρο χωρίς να μπλοκάρει το κύριο
            stage.showAndWait();
            updateTaskCounters();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: Could not open AddTask.fxml");
        }

        updateTaskCounters();
    }
    
    /**
     * Διαγράφει την επιλεγμένη εργασία.
     * Ελέγχει αν υπάρχει επιλεγμένη εργασία και αν η εργασία δεν έχει προτεραιότητα "Default".
     * Διαγράφει όλες τις υπενθυμίσεις που σχετίζονται με την εργασία πριν από τη διαγραφή της.
     *
     * @param event το ActionEvent που προκάλεσε την κλήση της μεθόδου.
     */
    @FXML
    void DeleteTask(ActionEvent event) {
        Task selectedTask = TaskTable.getSelectionModel().getSelectedItem();
        if (selectedTask == null) {
            showAlert("No Task Selected", "Please select a task to delete.");
            return;
        }
    
        if (selectedTask.getPriority().getName().equals("Default")) {
            showAlert("Deletion Not Allowed", "Cannot delete a task with a default priority.");
            return;
        }
        
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText(null);
        confirm.setContentText("Are you sure you want to delete this task?");
        
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Βρίσκουμε όλες τις υπενθυμίσεις που σχετίζονται με το επιλεγμένο task
                ObservableList<Reminder> remindersToRemove = reminderList.filtered(reminder ->
                    reminder.getTask().equals(selectedTask)
                );
                
                // Διαγράφουμε τις υπενθυμίσεις αυτές
                reminderList.removeAll(remindersToRemove);
                
                // Διαγράφουμε το task
                taskList.remove(selectedTask);
                System.out.println("Deleted Task: " + selectedTask.getTitle());
            }
            updateTaskCounters();
        });
    }
        
    /**
     * Επεξεργάζεται την επιλεγμένη εργασία.
     * Ανοίγει το παράθυρο επεξεργασίας και ενημερώνει τα δεδομένα μετά την επεξεργασία.
     */
    @FXML
    void EditTask() {
        Task selectedTask = TaskTable.getSelectionModel().getSelectedItem();
        if (selectedTask == null) {
            showAlert("No Task Selected", "Please select a task to edit.");
            return;
        }
    
        // Άνοιγμα παραθύρου επεξεργασίας
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditTask.fxml"));
            Parent root = loader.load();
    
            EditTaskController controller = loader.getController();
            controller.setTask(selectedTask);
            controller.setTaskList(taskList);
    
            Stage stage = new Stage();
            stage.setTitle("Edit Task");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            
            TaskTable.refresh();
            updateTaskCounters();
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        updateTaskCounters();
    }
    
    /**
     * Προσθέτει μια νέα κατηγορία.
     * Ανοίγει ένα TextInputDialog για να εισάγει ο χρήστης το όνομα της νέας κατηγορίας.
     */
    @FXML
    void AddCategory() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Category");
        dialog.setHeaderText("Enter a new category name:");
        dialog.setContentText("Category:");
    
        dialog.showAndWait().ifPresent(categoryName -> {
            if (!categoryName.trim().isEmpty()) {
                Category newCategory = new Category(categoryName.trim());
                if (!categoryList.contains(newCategory)) {
                    categoryList.add(newCategory);
                    System.out.println("Added Category: " + newCategory.getName());
                } else {
                    showAlert("Duplicate Category", "This category already exists.");
                }
            } else {
                showAlert("Invalid Input", "Category name cannot be empty.");
            }
        });
    }
    
    /**
     * Επεξεργάζεται την επιλεγμένη κατηγορία.
     * Ανοίγει ένα TextInputDialog με το τρέχον όνομα και ενημερώνει την κατηγορία με το νέο όνομα.
     */
    @FXML
    void EditCategory() {
        Category selectedCategory = CategoryTable.getSelectionModel().getSelectedItem();
        if (selectedCategory == null) {
            showAlert("No Category Selected", "Please select a category to edit.");
            return;
        }
    
        TextInputDialog dialog = new TextInputDialog(selectedCategory.getName());
        dialog.setTitle("Edit Category");
        dialog.setHeaderText("Edit Category Name");
        dialog.setContentText("New name:");
    
        dialog.showAndWait().ifPresent(newName -> {
            if (!newName.trim().isEmpty()) {
                selectedCategory.setName(newName.trim());
                CategoryTable.refresh();
                System.out.println("Edited Category: " + newName);
            } else {
                showAlert("Invalid Input", "Category name cannot be empty.");
            }
        });
    }
    
    /**
     * Διαγράφει την επιλεγμένη κατηγορία.
     * Διαγράφει όλες τις εργασίες που ανήκουν στην κατηγορία, καθώς και τις υπενθυμίσεις τους.
     */
    @FXML
    void DeleteCategory() {
        Category selectedCategory = CategoryTable.getSelectionModel().getSelectedItem();
        if (selectedCategory == null) {
            showAlert("No Category Selected", "Please select a category to delete.");
            return;
        }
    
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText(null);
        confirm.setContentText("Are you sure you want to delete this category? All related tasks will also be deleted.");
    
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Βρίσκουμε όλες τις εργασίες που ανήκουν στην επιλεγμένη κατηγορία
                ObservableList<Task> tasksToRemove = taskList.filtered(task ->
                    task.getCategory().equals(selectedCategory.getName())
                );
                
                // Για κάθε εργασία που πρόκειται να διαγραφεί, αφαιρούμε και τις υπενθυμίσεις της
                tasksToRemove.forEach(task ->
                    reminderList.removeIf(reminder -> reminder.getTask().equals(task))
                );
                
                // Διαγραφή των εργασιών που ανήκουν στην κατηγορία
                taskList.removeAll(tasksToRemove);
                
                // Διαγραφή της κατηγορίας
                categoryList.remove(selectedCategory);
                System.out.println("Deleted Category: " + selectedCategory.getName());
            }
        });
    }
    
    /**
     * Προσθέτει μια νέα προτεραιότητα.
     * Ανοίγει ένα TextInputDialog για την εισαγωγή του ονόματος της προτεραιότητας και την προσθέτει αν δεν υπάρχει διπλότυπο.
     */
    @FXML
    void AddPriority() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Priority");
        dialog.setHeaderText("Enter a new priority name:");
        dialog.setContentText("Priority:");
    
        dialog.showAndWait().ifPresent(priorityName -> {
            if (!priorityName.trim().isEmpty()) {
                Priority newPriority = new Priority(priorityName.trim());
                if (!priorityList.contains(newPriority)) {
                    priorityList.add(newPriority);
                    System.out.println("Added Priority: " + newPriority.getName());
                } else {
                    showAlert("Duplicate Priority", "This priority already exists.");
                }
            } else {
                showAlert("Invalid Input", "Priority name cannot be empty.");
            }
        });
    }
    
    /**
     * Επεξεργάζεται την επιλεγμένη προτεραιότητα.
     * Ανοίγει ένα TextInputDialog με το τρέχον όνομα και ενημερώνει την προτεραιότητα με το νέο όνομα.
     */
    @FXML
    void EditPriority() {
        Priority selectedPriority = PriorityTable.getSelectionModel().getSelectedItem();
        if (selectedPriority == null) {
            showAlert("No Priority Selected", "Please select a priority to edit.");
            return;
        }
    
        TextInputDialog dialog = new TextInputDialog(selectedPriority.getName());
        dialog.setTitle("Edit Priority");
        dialog.setHeaderText("Edit Priority Name");
        dialog.setContentText("New name:");
    
        dialog.showAndWait().ifPresent(newName -> {
            if (!newName.trim().isEmpty()) {
                selectedPriority.setName(newName.trim());
                PriorityTable.refresh();
                System.out.println("Edited Priority: " + newName);
            } else {
                showAlert("Invalid Input", "Priority name cannot be empty.");
            }
        });
    }
    
    /**
     * Διαγράφει την επιλεγμένη προτεραιότητα.
     * Διαγράφει όλες τις εργασίες που έχουν αυτή την προτεραιότητα (και τις υπενθυμίσεις τους) και αφαιρεί την προτεραιότητα.
     */
    @FXML
    void DeletePriority() {
        Priority selectedPriority = PriorityTable.getSelectionModel().getSelectedItem();
        if (selectedPriority == null) {
            showAlert("No Priority Selected", "Please select a category to delete.");
            return;
        }
    
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText(null);
        confirm.setContentText("Are you sure you want to delete this priority? All related tasks will also be deleted.");
    
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Βρίσκουμε όλες τις εργασίες που ανήκουν στην επιλεγμένη προτεραιότητα
                ObservableList<Task> tasksToRemove = taskList.filtered(task ->
                    task.getPriority().getName().equals(selectedPriority.getName())
                );
                
                // Για κάθε εργασία που πρόκειται να διαγραφεί, αφαιρούμε και τις υπενθυμίσεις της
                tasksToRemove.forEach(task ->
                    reminderList.removeIf(reminder -> reminder.getTask().equals(task))
                );
                
                // Διαγραφή των εργασιών που ανήκουν στην προτεραιότητα
                taskList.removeAll(tasksToRemove);
                
                // Διαγραφή της προτεραιότητας
                priorityList.remove(selectedPriority);
                System.out.println("Deleted Priority: " + selectedPriority.getName());
            }
        });
    }
    
    /**
     * Προσθέτει μια νέα υπενθύμιση για την επιλεγμένη εργασία.
     * Ελέγχει αν έχει επιλεγεί task, αν το task δεν είναι σε κατάσταση "Completed" και αν έχει ορισμένη προθεσμία,
     * ανοίγει ένα custom dialog για την εισαγωγή λεπτομερειών υπενθύμισης και προσθέτει την υπενθύμιση αν όλα είναι έγκυρα.
     */
    @FXML
    void AddReminder() {
        // Έλεγχος για επιλεγμένο task
        Task selectedTask = TaskTable.getSelectionModel().getSelectedItem();
        if (selectedTask == null) {
            showAlert("No Task Selected", "Select a task to add a reminder.");
            return;
        }
        // Ελέγχουμε αν το task είναι Completed
        if (selectedTask.getStatus().getName().equals("Completed")) {
            showAlert("Invalid Operation", "Cannot add a reminder to a completed task.");
            return;
        }
        // Έλεγχος για ορισμένη προθεσμία στο task
        LocalDate deadline = selectedTask.getDeadline();
        if (deadline == null) {
            showAlert("No Deadline", "The selected task does not have a deadline.");
            return;
        }
    
        // Δημιουργία custom dialog για εισαγωγή λεπτομερειών υπενθύμισης
        Dialog<Reminder> dialog = new Dialog<>();
        dialog.setTitle("Add Reminder");
        dialog.setHeaderText("Enter Reminder Details");
    
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);
    
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
    
        TextField nameField = new TextField();
        nameField.setPromptText("Reminder Name");
    
        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll("1 day before", "1 week before", "1 month before", "Custom");
        typeComboBox.setValue("1 day before");
    
        DatePicker customDatePicker = new DatePicker();
        customDatePicker.setDisable(true); // Αρχικά απενεργοποιημένος
    
        // Ενεργοποίηση του DatePicker μόνο όταν επιλέγεται "Custom"
        typeComboBox.valueProperty().addListener((_, _, newVal) -> {
            customDatePicker.setDisable(!"Custom".equals(newVal));
        });
    
        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Reminder Type:"), 0, 1);
        grid.add(typeComboBox, 1, 1);
        grid.add(new Label("Custom Date:"), 0, 2);
        grid.add(customDatePicker, 1, 2);
    
        dialog.getDialogPane().setContent(grid);
    
        // Μετατροπή του αποτελέσματος όταν πατηθεί το κουμπί "Add"
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                String reminderName = nameField.getText();
                String reminderType = typeComboBox.getValue();
                LocalDate reminderDate = null;
                switch (reminderType) {
                    case "1 day before":
                        reminderDate = deadline.minusDays(1);
                        break;
                    case "1 week before":
                        reminderDate = deadline.minusWeeks(1);
                        break;
                    case "1 month before":
                        reminderDate = deadline.minusMonths(1);
                        break;
                    case "Custom":
                        reminderDate = customDatePicker.getValue();
                        break;
                }
                if (reminderName != null && !reminderName.trim().isEmpty() && reminderDate != null) {
                    return new Reminder(reminderName.trim(), selectedTask, reminderDate, reminderType);
                }
            }
            return null;
        });
    
        Optional<Reminder> result = dialog.showAndWait();
        result.ifPresent(newReminder -> reminderList.add(newReminder));
    }
    
    /**
     * Επεξεργάζεται την επιλεγμένη υπενθύμιση.
     * Ανοίγει ένα TextInputDialog για την αλλαγή του ονόματος της υπενθύμισης.
     */
    @FXML
    void EditReminder() {
        Reminder selectedReminder = ReminderTable.getSelectionModel().getSelectedItem();
        if (selectedReminder == null) {
            showAlert("No Reminder Selected", "Please select a reminder to edit.");
            return;
        }
    
        TextInputDialog dialog = new TextInputDialog(selectedReminder.getName());
        dialog.setTitle("Edit Reminder");
        dialog.setHeaderText("Edit Reminder Name:");
        dialog.setContentText("New Name:");
    
        dialog.showAndWait().ifPresent(newName -> {
            if (!newName.trim().isEmpty()) {
                selectedReminder.setName(newName.trim());
                ReminderTable.refresh();
            }
        });
    }
    
    /**
     * Διαγράφει την επιλεγμένη υπενθύμιση.
     * Εμφανίζει alert επιβεβαίωσης και αν ο χρήστης επιβεβαιώσει, αφαιρεί την υπενθύμιση από τη λίστα.
     */
    @FXML
    void DeleteReminder() {
        Reminder selectedReminder = ReminderTable.getSelectionModel().getSelectedItem();
        if (selectedReminder == null) {
            showAlert("No Reminder Selected", "Please select a reminder to delete.");
            return;
        }
    
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText(null);
        confirm.setContentText("Are you sure you want to delete this reminder?");
    
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                reminderList.remove(selectedReminder);
            }
        });
    }
    
    /**
     * Φιλτράρει τις εργασίες με βάση το κείμενο αναζήτησης, την επιλεγμένη κατηγορία και προτεραιότητα.
     * Τυπώνει στο console το πλήθος των αποτελεσμάτων και ενημερώνει τον TaskTable.
     */
    @FXML
    void filterTasks() {
        String searchTitle = searchTitleField.getText().toLowerCase();
        Category selectedCategory = filterCategoryBox.getValue();
        Priority selectedPriority = filterPriorityBox.getValue();
    
        // Φιλτράρισμα
        ObservableList<Task> filteredList = taskList.filtered(task -> {
            boolean matchesTitle = searchTitle.isEmpty()
                || task.getTitle().toLowerCase().contains(searchTitle);
            boolean matchesCategory = (selectedCategory == null) 
                || task.getCategory().equals(selectedCategory.getName());
            boolean matchesPriority = (selectedPriority == null)
                || (task.getPriority().equals(selectedPriority));
            return matchesTitle && matchesCategory && matchesPriority;
        });
    
        System.out.println("Filtered size: " + filteredList.size());
        TaskTable.setItems(filteredList);
    }
    
    /**
     * Επαναφέρει τα φίλτρα αναζήτησης στις αρχικές τιμές και εμφανίζει όλες τις εργασίες.
     */
    @FXML
    void resetFilters() {
        searchTitleField.clear();
        filterCategoryBox.setValue(null);
        filterPriorityBox.setValue(null);
        TaskTable.setItems(taskList); 
    }
    
    /**
     * Αρχικοποιεί το σκηνικό.
     * Φορτώνει τα αποθηκευμένα δεδομένα (tasks, categories, priorities, reminders),
     * ρυθμίζει τις στήλες των πινάκων, και ενημερώνει τους μετρητές.
     */
    @FXML
    public void initialize() {
    
        List<Task> savedTasks = DataManager.loadTasks();
        if (savedTasks != null) {
            taskList.addAll(savedTasks);
        }
    
        List<Category> savedCategories = DataManager.loadCategories();
        if (savedCategories != null) {
            categoryList.addAll(savedCategories);
        }
    
        List<Priority> savedPriorities = DataManager.loadPriorities();
        if (savedPriorities != null) {
            priorityList.addAll(savedPriorities);
        }
    
        List<Reminder> savedReminders = DataManager.loadReminders();
        if (savedReminders != null) {
            reminderList.addAll(savedReminders);
        }
    
        checkAndUpdateDelayedTasks();
    
        checkAndShowReminders();
    
        // Σύνδεση των στηλών με τα δεδομένα της λίστας
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        categoryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));
        priorityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPriority().getName()));
        deadlineColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDeadline().toString()));
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus().getName()));
    
        TaskTable.setItems(taskList);
    
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        piorityNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        CategoryTable.setItems(categoryList);
        PriorityTable.setItems(priorityList);
    
        filterCategoryBox.setItems(MainSceneController.getCategoryList());
        filterPriorityBox.setItems(MainSceneController.getPriorityList());
    
        rNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        taskTitleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTask().getTitle()));
        reminderDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReminderDate().toString()));
        reminderTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
    
        ReminderTable.setItems(reminderList);
        updateTaskCounters();
    }
        
    /**
     * Εμφανίζει ένα alert με το δοσμένο μήνυμα.
     *
     * @param title ο τίτλος του alert
     * @param message το μήνυμα του alert
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Αποθηκεύει τα δεδομένα και κλείνει την εφαρμογή.
     * Εμφανίζει alert επιβεβαίωσης για αποθήκευση προτού τερματιστεί η εφαρμογή.
     */
    @FXML
    void saveAndExit() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Exit Application");
        confirm.setHeaderText("Save changes before exiting?");
        confirm.setContentText("Do you want to save your progress before closing?");
    
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                DataManager.saveTasks(taskList);
                DataManager.saveCategories(categoryList);
                DataManager.savePriorities(priorityList);
                DataManager.saveReminders(reminderList);
                System.out.println("Data saved successfully.");
            }
            System.exit(0);
        });
    }
    
    /**
     * Ελέγχει και ενημερώνει τις καθυστερημένες εργασίες.
     * Αν μια εργασία έχει περάσει την προθεσμία της και δεν είναι Completed, η κατάσταση της αλλάζει σε Delayed.
     */
    private void checkAndUpdateDelayedTasks() {
        LocalDate today = LocalDate.now();
        boolean updated = false;
    
        for (Task task : taskList) {
            if (!task.getStatus().getName().equals("Completed") && task.getDeadline() != null) {
                if (task.getDeadline().isBefore(today)) {
                    task.setStatus(new Status("Delayed"));
                    updated = true;
                }
            }
        }
    
        if (updated) {
            TaskTable.refresh(); 
        }
        
        dueSoonListView.setCellFactory(_ -> new ListCell<Task>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                if (empty || task == null) {
                    setText(null);
                } else {
                    setText(task.getTitle());
                }
            }
        });
    
        updateDueSoonTasks();
    }
    
    /**
     * Ελέγχει και ενημερώνει τις καθυστερημένες εργασίες χωρίς να κάνει ανανέωση του πίνακα.
     */
    public void checkAndUpdateOverdueTasks() {
        for (Task task : taskList) {
            if (!task.getStatus().getName().equals("Completed") && task.getDeadline() != null) {
                if (task.getDeadline().isBefore(LocalDate.now())) {
                    task.setStatus(new Status("Delayed"));
                }
            }
        }
    }
    
    /**
     * Ελέγχει τις υπενθυμίσεις και εμφανίζει alert για κάθε υπενθύμιση που έχει ημερομηνία ίση με τη σημερινή.
     */
    public void checkAndShowReminders() {
        for (Reminder reminder : reminderList) {
            if (reminder.getReminderDate().isEqual(LocalDate.now())) {
                showReminderNotification(reminder);
            }
        }
    }
    
    /**
     * Εμφανίζει ένα alert υπενθύμισης με τις λεπτομέρειες της υπενθύμισης.
     *
     * @param reminder η υπενθύμιση για την οποία θα εμφανιστεί το alert.
     */
    private void showReminderNotification(Reminder reminder) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Reminder Notification");
        alert.setHeaderText("You have a reminder!");
        alert.setContentText("Reminder: " + reminder.getName() + "\nFor Task: " + reminder.getTask().getTitle());
        alert.showAndWait();
    }
    
    /**
     * Ενημερώνει τη λίστα των εργασιών με προθεσμία εντός 7 ημερών και την εμφανίζει στο dueSoonListView.
     */
    private void updateDueSoonTasks() {
        LocalDate today = LocalDate.now();
        ObservableList<Task> dueSoonTasks = taskList.filtered(task -> {
            // Εξαιρούμε τις ολοκληρωμένες και εκείνες χωρίς προθεσμία
            if (task.getDeadline() == null || task.getStatus().getName().equals("Completed")) {
                return false;
            }
            LocalDate deadline = task.getDeadline();
            // Επιστρέφουμε true αν η προθεσμία είναι σήμερα ή εντός των επόμενων 7 ημερών
            return !deadline.isBefore(today) && !deadline.isAfter(today.plusDays(7));
        });
        dueSoonListView.setItems(dueSoonTasks);
    }
    
    /**
     * Επιστρέφει τη λίστα των κατηγοριών.
     *
     * @return ObservableList με όλες τις κατηγορίες.
     */
    public static ObservableList<Category> getCategoryList() {
        return categoryList;
    }
    
    /**
     * Επιστρέφει τη λίστα των προτεραιοτήτων.
     *
     * @return ObservableList με όλες τις προτεραιότητες.
     */
    public static ObservableList<Priority> getPriorityList() {
        return priorityList;
    }
}
