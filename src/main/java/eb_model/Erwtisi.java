package eb_model;

import java.util.List;

public class Erwtisi {

    // Πεδία για την αποθήκευση δεδομένων της ερώτησης
    private String keimeno; // Το κείμενο της ερώτησης
    private String kathgoria; // Η κατηγορία της ερώτησης
    private String diskolia; // Ο βαθμός δυσκολίας
    private String tupos; // Ο τύπος της ερώτησης (Σωστό/Λάθος ή Πολλαπλής επιλογής)
    private String swsthApanthsh; // Η σωστή απάντηση
    private List<String> lanthasmenesApanthseis; // Οι λανθασμένες απαντήσεις

    // Constructor της Erwtisi
    public Erwtisi(String keimeno, String kathgoria, String diskolia, String tupos, 
                   String swsthApanthsh, List<String> lanthasmenesApanthseis) {
        this.keimeno = keimeno;
        this.kathgoria = kathgoria;
        this.diskolia = diskolia;
        this.tupos = tupos;
        this.swsthApanthsh = swsthApanthsh;
        this.lanthasmenesApanthseis = lanthasmenesApanthseis;
    }

    // Getters των παραμέτρων
    public String getKeimeno() {
        return keimeno;
    }

    public String getKathgoria() {
        return kathgoria;
    }

    public String getDiskolia() {
        return diskolia;
    }

    public String getTupos() {
        return tupos;
    }

    public String getSwsthApanthsh() {
        return swsthApanthsh;
    }

    public List<String> getLanthasmenesApanthseis() {
        return lanthasmenesApanthseis;
    }

    // εκτύπωση των δεδομένων
    @Override
    public String toString() {
        return "Erwtisi {" +
                "keimeno='" + keimeno + '\'' +
                ", kathgoria='" + kathgoria + '\'' +
                ", diskolia='" + diskolia + '\'' +
                ", tupos='" + tupos + '\'' +
                ", swsthApanthsh='" + swsthApanthsh + '\'' +
                ", lanthasmenesApanthseis=" + lanthasmenesApanthseis +
                '}';
    }
}
