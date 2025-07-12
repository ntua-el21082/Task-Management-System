package model;

import java.time.LocalDate;

/**
 * Η κλάση Reminder αναπαριστά μια υπενθύμιση που σχετίζεται με μια εργασία.
 * Περιέχει το όνομα της υπενθύμισης, την εργασία που αφορά, την ημερομηνία υπενθύμισης και τον τύπο υπενθύμισης.
 */
public class Reminder {
    private String name;
    private Task task;
    private LocalDate reminderDate;
    private String type;

    /**
     * Δημιουργεί μια νέα υπενθύμιση για μια εργασία.
     *
     * @param name το όνομα της υπενθύμισης.
     * @param task η εργασία που αφορά η υπενθύμιση.
     * @param reminderDate η ημερομηνία της υπενθύμισης.
     * @param type ο τύπος της υπενθύμισης (π.χ. "1 day before").
     */
    public Reminder(String name, Task task, LocalDate reminderDate, String type) {
        this.name = name;
        this.task = task;
        this.reminderDate = reminderDate;
        this.type = type;
    }

    /**
     * Επιστρέφει την εργασία που αφορά η υπενθύμιση.
     *
     * @return το Task της υπενθύμισης.
     */
    public Task getTask() { 
        return task; 
    }

    /**
     * Επιστρέφει το όνομα της υπενθύμισης.
     *
     * @return το όνομα.
     */
    public String getName() { 
        return name; 
    }

    /**
     * Επιστρέφει τον τύπο της υπενθύμισης.
     *
     * @return τον τύπο.
     */
    public String getType() { 
        return type; 
    }

    /**
     * Επιστρέφει την ημερομηνία υπενθύμισης.
     *
     * @return την ημερομηνία.
     */
    public LocalDate getReminderDate() { 
        return reminderDate; 
    }

    /**
     * Επιστρέφει μια ημερομηνία υπενθύμισης για ένα Task, αφαιρώντας τον δοσμένο αριθμό ημερών από την προθεσμία.
     *
     * @param task το Task για το οποίο υπολογίζεται η υπενθύμιση.
     * @param daysBefore ο αριθμός των ημερών πριν την προθεσμία.
     * @return η ημερομηνία υπενθύμισης.
     */
    public static LocalDate getReminderDate(Task task, int daysBefore) {
        return task.getDeadline().minusDays(daysBefore);
    }

    /**
     * Θέτει το νέο όνομα για την υπενθύμιση.
     *
     * @param name το νέο όνομα.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Επιστρέφει μια συμβολοσειρά που αναπαριστά την υπενθύμιση.
     *
     * @return περιγραφή της υπενθύμισης.
     */
    @Override
    public String toString() {
        return "Reminder for " + task.getTitle() + " on " + reminderDate;
    }
}
