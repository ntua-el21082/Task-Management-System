package model;

import java.time.LocalDate;

/**
 * Η κλάση Task αναπαριστά μια εργασία με τα σχετικά πεδία:
 * τίτλος, περιγραφή, κατηγορία, προτεραιότητα, προθεσμία, και κατάσταση.
 * Περιλαμβάνει τη λογική ενημέρωσης της κατάστασης (π.χ. "Delayed") αν έχει περάσει η προθεσμία.
 */
public class Task {
    private String title;
    private String description;
    private String category;
    private Priority priority;
    private LocalDate deadline;
    private Status status;

    /**
     * Δημιουργεί ένα νέο Task με τις δοσμένες παραμέτρους.
     * Αν το status είναι null, ορίζεται ως "Open". Καλείται η μέθοδος updateStatus().
     *
     * @param title ο τίτλος της εργασίας.
     * @param description η περιγραφή της εργασίας.
     * @param category η κατηγορία της εργασίας.
     * @param priority η προτεραιότητα της εργασίας.
     * @param deadline η προθεσμία της εργασίας.
     * @param status η αρχική κατάσταση της εργασίας.
     */
    public Task(String title, String description, String category, Priority priority, LocalDate deadline, Status status) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.priority = priority;
        this.deadline = deadline;
        this.status = (status != null) ? status : new Status("Open"); // Προκαθορισμένο Open αν είναι null
        updateStatus();
    }

    /**
     * Ενημερώνει την κατάσταση της εργασίας σε "Delayed" αν η προθεσμία έχει περάσει και δεν είναι Completed.
     */
    public void updateStatus() {
        if (this.deadline != null && this.deadline.isBefore(LocalDate.now()) && !this.status.getName().equals("Completed")) {
            this.status = new Status("Delayed");
        }
    }

    // Getters και Setters

    /**
     * Επιστρέφει τον τίτλο της εργασίας.
     *
     * @return ο τίτλος.
     */
    public String getTitle() { 
        return title; 
    }

    /**
     * Θέτει τον τίτλο της εργασίας.
     *
     * @param title ο νέος τίτλος.
     */
    public void setTitle(String title) { 
        this.title = title; 
    }

    /**
     * Επιστρέφει την περιγραφή της εργασίας.
     *
     * @return η περιγραφή.
     */
    public String getDescription() { 
        return description; 
    }

    /**
     * Θέτει την περιγραφή της εργασίας.
     *
     * @param description η νέα περιγραφή.
     */
    public void setDescription(String description) { 
        this.description = description; 
    }

    /**
     * Επιστρέφει την κατηγορία της εργασίας.
     *
     * @return η κατηγορία.
     */
    public String getCategory() { 
        return category; 
    }

    /**
     * Θέτει την κατηγορία της εργασίας.
     *
     * @param category η νέα κατηγορία.
     */
    public void setCategory(String category) { 
        this.category = category; 
    }

    /**
     * Επιστρέφει την προτεραιότητα της εργασίας.
     *
     * @return το επίπεδο προτεραιότητας.
     */
    public Priority getPriority() { 
        return priority; 
    }

    /**
     * Θέτει την προτεραιότητα της εργασίας.
     *
     * @param priority η νέα προτεραιότητα.
     */
    public void setPriority(Priority priority) { 
        this.priority = priority; 
    }

    /**
     * Επιστρέφει την προθεσμία της εργασίας.
     *
     * @return την προθεσμία.
     */
    public LocalDate getDeadline() { 
        return deadline; 
    }

    /**
     * Θέτει την προθεσμία της εργασίας και ενημερώνει την κατάσταση.
     *
     * @param deadline η νέα προθεσμία.
     */
    public void setDeadline(LocalDate deadline) { 
        this.deadline = deadline; 
        updateStatus(); 
    }

    /**
     * Επιστρέφει την κατάσταση της εργασίας.
     *
     * @return το status.
     */
    public Status getStatus() { 
        return status; 
    }

    /**
     * Θέτει την κατάσταση της εργασίας.
     *
     * @param status η νέα κατάσταση.
     */
    public void setStatus(Status status) { 
        this.status = status; 
    }

    /**
     * Επιστρέφει μια συμβολοσειρά που περιγράφει την εργασία.
     *
     * @return περιγραφή της εργασίας.
     */
    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", priority=" + priority +
                ", deadline=" + (deadline != null ? deadline.toString() : "No Deadline") +
                ", status=" + status.getName() +
                '}';
    }
}
