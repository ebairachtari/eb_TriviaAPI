package eb_services;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import eb_erwtisidb.ErwtisiResult;
import eb_erwtisidb.Result;
import eb_exceptions.TriviaAPIException;
import eb_model.Erwtisi;

public class TriviaAPIService {
    private final String API_URL = "https://opentdb.com/api.php"; // Βασικό URL για την ανάκτηση ερωτήσεων
    private final String CATEGORIES_URL = "https://opentdb.com/api_category.php"; // URL για την ανάκτηση κατηγοριών
    private final CloseableHttpClient httpClient; // HTTP Client για την αποστολή αιτημάτων

    // Κατασκευαστής της TriviaAPIService που αρχικοποιεί τον HTTP Client
    public TriviaAPIService() {
        this.httpClient = HttpClients.createDefault();
    }

    // Μέθοδος για την αποκωδικοποίηση HTML χαρακτήρων στις ερωτήσεις
    private Erwtisi decodeQuestion(Result r) {
        return new Erwtisi(
            StringEscapeUtils.unescapeHtml4(r.getQuestion()), // Αποκωδικοποίηση ερώτησης
            StringEscapeUtils.unescapeHtml4(r.getCategory()), // Αποκωδικοποίηση κατηγορίας
            r.getDifficulty(), // Δυσκολία
            r.getType(), // Τύπος 
            StringEscapeUtils.unescapeHtml4(r.getCorrectAnswer()), // Αποκωδικοποίηση σωστής απάντησης
            r.getIncorrectAnswers().stream()
                .map(StringEscapeUtils::unescapeHtml4) // Αποκωδικοποίηση λανθασμένων απαντήσεων
                .toList()
        );
    }

    // Μέθοδος για την ανάκτηση κατηγοριών από το Trivia API
    public Map<String, Integer> getCategories() throws TriviaAPIException {
        Map<String, Integer> categories = new HashMap<>();
        try {
            HttpGet request = new HttpGet(CATEGORIES_URL);  // Δημιουργία HTTP GET request
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                    throw new TriviaAPIException("Σφάλμα API κατά την ανάκτηση κατηγοριών: " + response.getStatusLine());
                }
                HttpEntity entity = response.getEntity();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(entity.getContent());
                JsonNode categoriesArray = rootNode.get("trivia_categories");
                
             // Αποθήκευση κατηγοριών σε έναν χάρτη (όνομα -> ID)
                for (JsonNode categoryNode : categoriesArray) {
                    String name = categoryNode.get("name").asText();
                    int id = categoryNode.get("id").asInt();
                    categories.put(name, id);
                }
            }
        } catch (IOException e) {
            System.out.println("Σφάλμα: Δεν είναι δυνατή η σύνδεση με το API.");
            throw new TriviaAPIException("Σφάλμα κατά την επικοινωνία με το Trivia API", e);
        }
        return categories; // Επιστροφή κατηγοριών
    }

    // Μέθοδος για την ανάκτηση ερωτήσεων με φίλτρα
    public List<Erwtisi> getQuestionsWithFilters(String difficulty, String type, Integer amount, Integer categoryId) throws TriviaAPIException {
        List<Erwtisi> questionsList = new ArrayList<>();
        try {
            if (amount == null || amount <= 0) {
                amount = 5; // Προεπιλογή 5 ερωτήσεων
            }

            // Δημιουργία του URI με τα φίλτρα που έχει επιλέξει ο χρήστης
            URIBuilder uriBuilder = new URIBuilder(API_URL).addParameter("amount", String.valueOf(amount));

            // Αν το categoryId δεν είναι null, προστίθεται στην κλήση
            if (categoryId != null && categoryId > 0) {
                uriBuilder.addParameter("category", String.valueOf(categoryId));
            }

            // Προσθήκη δυσκολίας αν είναι επιλεγμένη
            if (difficulty != null && !difficulty.equals("Any") && !difficulty.isEmpty()) {
                uriBuilder.addParameter("difficulty", difficulty);
            }

            // Προσθήκη τύπου αν είναι επιλεγμένος
            if (type != null && !type.equals("Any Type") && !type.isEmpty()) {
                uriBuilder.addParameter("type", type);
            }

            URI uri = uriBuilder.build(); // Κατασκευή του URI

            // Εκτύπωση του URI για έλεγχο
            System.out.println("Generated URI: " + uri);

            // Δημιουργία HTTP GET request προς το Trivia API
            HttpGet request = new HttpGet(uri);
            // Εκτέλεση του request και αποθήκευση της απόκρισης
            try (CloseableHttpResponse response = httpClient.execute(request)) {
            	// Έλεγχος αν το JSON από το API είναι επιτυχής (HTTP 200 OK)
                if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                    throw new TriviaAPIException("Σφάλμα API: " + response.getStatusLine());
                }
                
                // Ανάκτηση του JSON
                HttpEntity entity = response.getEntity();
                // Μετατροπή του JSON σε αντικείμενο ErwtisiResult
                ObjectMapper mapper = new ObjectMapper();
                ErwtisiResult result = mapper.readValue(entity.getContent(), ErwtisiResult.class);

                // Εκτύπωση της απόκρισης JSON
                System.out.println("Response JSON: " + result);
                
                // Μετατροπή των αποτελεσμάτων σε αντικείμενα Erwtisi
                for (Result r : result.getResults()) {
                    questionsList.add(decodeQuestion(r));
                }
            }
        } catch (URISyntaxException | IOException e) {
            throw new TriviaAPIException("Σφάλμα κατά την επικοινωνία με το Trivia API", e);
        }

        // Εκτύπωση των ερωτήσεων που επιστράφηκαν
        System.out.println("Fetched Questions: " + questionsList);

        return questionsList;
    }

    // Μέθοδος για την ανάκτηση ερωτήσεων χωρίς φίλτρα (με τις προεπιλεγμένες παραμέτρους)
    public List<Erwtisi> getQuestionsWithoutFilters(int amount) throws TriviaAPIException {
        return getQuestionsWithFilters(null, null, amount, null);
    }
}
