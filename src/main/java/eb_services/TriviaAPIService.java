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

    // Constructor that initializes the HTTP Client
    public TriviaAPIService() {
        this.httpClient = HttpClients.createDefault();
    }

    // Method for decoding HTML characters in the question data
    private Erwtisi decodeQuestion(Result r) {
        return new Erwtisi(
            StringEscapeUtils.unescapeHtml4(r.getQuestion()), // Decodes the question text
            StringEscapeUtils.unescapeHtml4(r.getCategory()), // Decodes the category text
            r.getDifficulty(), // Difficulty level
            r.getType(), // Type of question
            StringEscapeUtils.unescapeHtml4(r.getCorrectAnswer()), // Decodes the correct answer
            r.getIncorrectAnswers().stream()
                .map(StringEscapeUtils::unescapeHtml4) // Decodes the incorrect answers
                .toList()
        );
    }

    // Method to retrieve categories from the Trivia API
    public Map<String, Integer> getCategories() throws TriviaAPIException {
        Map<String, Integer> categories = new HashMap<>();
        try {
            HttpGet request = new HttpGet(CATEGORIES_URL);  // Creates an HTTP GET request
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                    throw new TriviaAPIException("API error retrieving categories: " + response.getStatusLine());
                }
                HttpEntity entity = response.getEntity();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(entity.getContent());
                JsonNode categoriesArray = rootNode.get("trivia_categories");
                
             // Stores the categories into a map (name -> ID)
                for (JsonNode categoryNode : categoriesArray) {
                    String name = categoryNode.get("name").asText();
                    int id = categoryNode.get("id").asInt();
                    categories.put(name, id);
                }
            }
        } catch (IOException e) {
            System.out.println("Error: Unable to connect to the API.");
            throw new TriviaAPIException("Error communicating with Trivia API", e);
        }
        return categories; // Returns the categories
    }

    // Method to retrieve questions using filters
    public List<Erwtisi> getQuestionsWithFilters(String difficulty, String type, Integer amount, Integer categoryId) throws TriviaAPIException {
        List<Erwtisi> questionsList = new ArrayList<>();
        try {
            if (amount == null || amount <= 0) {
                amount = 5; // Default to 5 questions
            }

            // Builds the URI with selected filters
            URIBuilder uriBuilder = new URIBuilder(API_URL).addParameter("amount", String.valueOf(amount));

            // If categoryId is provided, add it to the URI
            if (categoryId != null && categoryId > 0) {
                uriBuilder.addParameter("category", String.valueOf(categoryId));
            }

            // Add difficulty to the URI if selected
            if (difficulty != null && !difficulty.equals("Any") && !difficulty.isEmpty()) {
                uriBuilder.addParameter("difficulty", difficulty);
            }

            // Add type to the URI if selected
            if (type != null && !type.equals("Any Type") && !type.isEmpty()) {
                uriBuilder.addParameter("type", type);
            }

            URI uri = uriBuilder.build(); // Build the final URI

            // Print the generated URI for debugging
            System.out.println("Generated URI: " + uri);

            // Create HTTP GET request to Trivia API
            HttpGet request = new HttpGet(uri);
            // Execute request and store the response
            try (CloseableHttpResponse response = httpClient.execute(request)) {
            	// Check if the JSON response from the API is successful (HTTP 200 OK)
                if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                    throw new TriviaAPIException("Σφάλμα API: " + response.getStatusLine());
                }
                
                /// Retrieve the JSON entity
                HttpEntity entity = response.getEntity();
                // Convert the JSON to an ErwtisiResult object
                ObjectMapper mapper = new ObjectMapper();
                ErwtisiResult result = mapper.readValue(entity.getContent(), ErwtisiResult.class);

                // Print the JSON response
                System.out.println("Response JSON: " + result);
                
                // Convert results into Erwtisi objects
                for (Result r : result.getResults()) {
                    questionsList.add(decodeQuestion(r));
                }
            }
        } catch (URISyntaxException | IOException e) {
            throw new TriviaAPIException("Error communicating with Trivia API", e);
        }

        // Print the fetched questions
        System.out.println("Fetched Questions: " + questionsList);

        return questionsList;
    }

    // Method to retrieve questions without filters (uses default parameters)
    public List<Erwtisi> getQuestionsWithoutFilters(int amount) throws TriviaAPIException {
        return getQuestionsWithFilters(null, null, amount, null);
    }
}
