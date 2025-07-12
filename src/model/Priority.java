package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Η κλάση Priority αναπαριστά το επίπεδο προτεραιότητας μιας εργασίας.
 * Περιλαμβάνει επίσης τη στατική λίστα όλων των προτεραιοτήτων και τη
 * διαχείριση της προκαθορισμένης προτεραιότητας "Default".
 */
public class Priority {
    private String name;
    private static final String DEFAULT_PRIORITY = "Default";
    private static List<Priority> priorities = new ArrayList<>();

    /**
     * Δημιουργεί ένα νέο επίπεδο προτεραιότητας.
     * Αν το όνομα δεν είναι "Default", προστίθεται στη λίστα προτεραιοτήτων.
     * 
     * @param name το όνομα της προτεραιότητας.
     */
    public Priority(String name) {
        if (!name.equals(DEFAULT_PRIORITY)) {
            this.name = name;
            priorities.add(this);
        } else {
            this.name = DEFAULT_PRIORITY;
        }
    }

    /**
     * Επιστρέφει το όνομα της προτεραιότητας.
     *
     * @return το όνομα.
     */
    public String getName() { 
        return name; 
    }

    /**
     * Θέτει το όνομα της προτεραιότητας, εφόσον δεν είναι "Default".
     *
     * @param name το νέο όνομα.
     */
    public void setName(String name) {
        if (!name.equals(DEFAULT_PRIORITY)) {
            this.name = name;
        }
    }

    /**
     * Αφαιρεί από τη λίστα τις προτεραιότητες με το δοσμένο όνομα,
     * εφόσον το όνομα δεν είναι "Default".
     *
     * @param name το όνομα της προτεραιότητας προς διαγραφή.
     */
    public static void removePriority(String name) {
        if (!name.equals(DEFAULT_PRIORITY)) {
            priorities.removeIf(priority -> priority.getName().equals(name));
        }
    }

    /**
     * Επιστρέφει τη λίστα όλων των προτεραιοτήτων.
     *
     * @return μια λίστα με τις προτεραιότητες.
     */
    public static List<Priority> getPriorities() {
        return priorities;
    }

    /**
     * Επιστρέφει το όνομα της προτεραιότητας ως συμβολοσειρά.
     *
     * @return το όνομα.
     */
    @Override
    public String toString() {
        return name;
    }
}
