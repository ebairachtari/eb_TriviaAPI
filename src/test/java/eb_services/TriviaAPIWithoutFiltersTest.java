package eb_services;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eb_exceptions.TriviaAPIException;
import eb_model.Erwtisi;

import java.util.List;

class TriviaAPIWithoutFiltersTest {

    private TriviaAPIService triviaAPIService;

    @BeforeEach
    void setUp() {
        triviaAPIService = new TriviaAPIService();
    }

    @Test
    void testGetQuestionsWithoutFilters() throws TriviaAPIException, InterruptedException {
        Thread.sleep(5000); // Delay to avoid rate limits

        List<Erwtisi> questions = triviaAPIService.getQuestionsWithoutFilters(5);

        assertNotNull(questions, "Η λίστα ερωτήσεων δεν πρέπει να είναι null.");
        assertFalse(questions.isEmpty(), "Η λίστα ερωτήσεων δεν πρέπει να είναι κενή.");

        questions.forEach(q -> System.out.println(
            "Question: " + q.getKeimeno() + "\n" +
            "Category: " + q.getKathgoria() + "\n" +
            "Difficulty: " + q.getDiskolia() + "\n"
        ));
    }
}
