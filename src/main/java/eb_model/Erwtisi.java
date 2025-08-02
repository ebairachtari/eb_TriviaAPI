package eb_model;

import java.util.List;

public class Erwtisi {

    // Fields for storing question data
    private String keimeno; // The question text
    private String kathgoria; // The category of the question
    private String diskolia; // The difficulty level
    private String tupos; // The type of the question (True/False or Multiple Choice)
    private String swsthApanthsh; // The correct answer
    private List<String> lanthasmenesApanthseis; // The incorrect answers

    // Constructor for the Erwtisi class
    public Erwtisi(String keimeno, String kathgoria, String diskolia, String tupos, 
                   String swsthApanthsh, List<String> lanthasmenesApanthseis) {
        this.keimeno = keimeno;
        this.kathgoria = kathgoria;
        this.diskolia = diskolia;
        this.tupos = tupos;
        this.swsthApanthsh = swsthApanthsh;
        this.lanthasmenesApanthseis = lanthasmenesApanthseis;
    }

    // Getters for the fields
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

    // Prints the question data
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
