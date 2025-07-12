package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Η κλάση Category αναπαριστά μια κατηγορία για τις εργασίες.
 * Κάθε δημιουργημένη κατηγορία προστίθεται αυτόματα σε μια στατική λίστα.
 */
public class Category {
    private String name;
    private static List<Category> categories = new ArrayList<>();

    /**
     * Δημιουργεί μια νέα κατηγορία με το δοσμένο όνομα και την προσθέτει στη λίστα.
     *
     * @param name το όνομα της κατηγορίας.
     */
    public Category(String name) {
        this.name = name;
        categories.add(this);
    }

    /**
     * Επιστρέφει το όνομα της κατηγορίας.
     *
     * @return το όνομα.
     */
    public String getName() { 
        return name; 
    }

    /**
     * Θέτει το όνομα της κατηγορίας.
     *
     * @param name το νέο όνομα.
     */
    public void setName(String name) { 
        this.name = name; 
    }

    /**
     * Αφαιρεί την κατηγορία με το δοσμένο όνομα από τη στατική λίστα.
     *
     * @param name το όνομα της κατηγορίας προς διαγραφή.
     */
    public static void removeCategory(String name) {
        categories.removeIf(category -> category.getName().equals(name));
    }

    /**
     * Επιστρέφει τη λίστα όλων των κατηγοριών.
     *
     * @return μια λίστα με τις κατηγορίες.
     */
    public static List<Category> getCategories() {
        return categories;
    }

    /**
     * Επιστρέφει το όνομα της κατηγορίας ως συμβολοσειρά.
     *
     * @return το όνομα της κατηγορίας.
     */
    @Override
    public String toString() {
        return name;
    }
}
