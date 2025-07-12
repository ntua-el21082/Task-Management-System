package model;

/**
 * Η κλάση Status αναπαριστά την κατάσταση μιας εργασίας.
 */
public class Status {
    private String name;

    /**
     * Δημιουργεί ένα νέο Status με το δοσμένο όνομα.
     *
     * @param name το όνομα της κατάστασης (π.χ. "Open", "Completed").
     */
    public Status(String name) {
        this.name = name;
    }

    /**
     * Επιστρέφει το όνομα της κατάστασης.
     *
     * @return το όνομα.
     */
    public String getName() {
        return name;
    }

    /**
     * Θέτει το νέο όνομα για την κατάσταση.
     *
     * @param name το νέο όνομα.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Επιστρέφει το όνομα της κατάστασης ως συμβολοσειρά.
     *
     * @return το όνομα.
     */
    @Override
    public String toString() {
        return name;
    }
}
