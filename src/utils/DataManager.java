package utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import model.Task;
import model.Category;
import model.Priority;
import model.Reminder;
import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Η κλάData διαχειρίζεται την αποθήκευση και φόρτωση δεδομένων σε μορφή JSON.
 * Χρησιμοποιεί τη βιβλιοθήκη Gson για τη μετατροπή αντικειμένων σε JSON και αντίστροφα.
 * Υποστηρίζει την αποθήκευση και φόρτωση δεδομένων για tasks, categories, priorities και reminders.
 */
public class DataManager {

    /** Ο φάκελος όπου αποθηκεύονται τα JSON αρχεία */
    private static final String DIRECTORY = "multimedia-project/medialab";
    /** Το αρχείο των tasks */
    private static final String TASKS_FILE = DIRECTORY + "/tasks.json";
    /** Το αρχείο των categories */
    private static final String CATEGORIES_FILE = DIRECTORY + "/categories.json";
    /** Το αρχείο των priorities */
    private static final String PRIORITIES_FILE = DIRECTORY + "/priorities.json";
    /** Το αρχείο των reminders */
    private static final String REMINDERS_FILE = DIRECTORY + "/reminders.json";

    /** Ο Gson με pretty printing και εγγεγραμμένο adapter για LocalDate */
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    /**
     * Εξασφαλίζει ότι ο φάκελος αποθήκευσης υπάρχει. Αν δεν υπάρχει, δημιουργείται.
     */
    private static void ensureDirectoryExists() {
        File dir = new File(DIRECTORY);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    /**
     * Αποθηκεύει τη λίστα των tasks στο αρχείο JSON.
     *
     * @param taskList η λίστα των tasks προς αποθήκευση.
     */
    public static void saveTasks(List<Task> taskList) {
        saveToFile(TASKS_FILE, taskList);
    }

    /**
     * Φορτώνει τη λίστα των tasks από το αρχείο JSON.
     *
     * @return μια λίστα με τα tasks, ή μια άδεια λίστα αν δεν βρεθούν δεδομένα.
     */
    public static List<Task> loadTasks() {
        return loadFromFile(TASKS_FILE, new TypeToken<List<Task>>() {}.getType());
    }

    /**
     * Αποθηκεύει τη λίστα των categories στο αρχείο JSON.
     *
     * @param categoryList η λίστα των categories προς αποθήκευση.
     */
    public static void saveCategories(List<Category> categoryList) {
        saveToFile(CATEGORIES_FILE, categoryList);
    }

    /**
     * Φορτώνει τη λίστα των categories από το αρχείο JSON.
     *
     * @return μια λίστα με τα categories, ή μια άδεια λίστα αν δεν βρεθούν δεδομένα.
     */
    public static List<Category> loadCategories() {
        return loadFromFile(CATEGORIES_FILE, new TypeToken<List<Category>>() {}.getType());
    }

    /**
     * Αποθηκεύει τη λίστα των priorities στο αρχείο JSON.
     *
     * @param priorityList η λίστα των priorities προς αποθήκευση.
     */
    public static void savePriorities(List<Priority> priorityList) {
        saveToFile(PRIORITIES_FILE, priorityList);
    }

    /**
     * Φορτώνει τη λίστα των priorities από το αρχείο JSON.
     *
     * @return μια λίστα με τα priorities, ή μια άδεια λίστα αν δεν βρεθούν δεδομένα.
     */
    public static List<Priority> loadPriorities() {
        return loadFromFile(PRIORITIES_FILE, new TypeToken<List<Priority>>() {}.getType());
    }

    /**
     * Αποθηκεύει τη λίστα των reminders στο αρχείο JSON.
     *
     * @param reminderList η λίστα των reminders προς αποθήκευση.
     */
    public static void saveReminders(List<Reminder> reminderList) {
        saveToFile(REMINDERS_FILE, reminderList);
    }

    /**
     * Φορτώνει τη λίστα των reminders από το αρχείο JSON.
     *
     * @return μια λίστα με τα reminders, ή μια άδεια λίστα αν δεν βρεθούν δεδομένα.
     */
    public static List<Reminder> loadReminders() {
        return loadFromFile(REMINDERS_FILE, new TypeToken<List<Reminder>>() {}.getType());
    }

    /**
     * Γενική μέθοδος αποθήκευσης λίστας αντικειμένων σε αρχείο JSON.
     *
     * @param filename το όνομα του αρχείου όπου θα αποθηκευτούν τα δεδομένα.
     * @param list η λίστα των αντικειμένων προς αποθήκευση.
     * @param <T> ο τύπος των αντικειμένων της λίστας.
     */
    private static <T> void saveToFile(String filename, List<T> list) {
        ensureDirectoryExists();
        try (Writer writer = new FileWriter(filename)) {
            gson.toJson(list, writer);
            System.out.println("Data saved successfully to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving data to " + filename);
            e.printStackTrace();
        }
    }
    
    /**
     * Γενική μέθοδος φόρτωσης λίστας αντικειμένων από αρχείο JSON.
     *
     * @param filename το όνομα του αρχείου από το οποίο θα φορτωθούν τα δεδομένα.
     * @param type ο τύπος των δεδομένων που περιμένουμε να φορτωθούν.
     * @param <T> ο τύπος των αντικειμένων που περιέχονται στη λίστα.
     * @return μια λίστα με τα δεδομένα ή μια άδεια λίστα αν δεν βρεθούν δεδομένα.
     */
    private static <T> List<T> loadFromFile(String filename, Type type) {
        ensureDirectoryExists(); // Βεβαιωνόμαστε ότι υπάρχει ο φάκελος αποθήκευσης
        try (Reader reader = new FileReader(filename)) {
            if (new File(filename).length() == 0) {
                return new ArrayList<>();
            }
            return gson.fromJson(reader, type);
        } catch (FileNotFoundException e) {
            System.out.println("No saved data found in " + filename);
            return new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
