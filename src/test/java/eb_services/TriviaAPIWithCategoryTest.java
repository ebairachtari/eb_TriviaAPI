package eb_services;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eb_exceptions.TriviaAPIException;
import eb_model.Erwtisi;

import java.util.List;

class TriviaAPIWithCategoryTest {

    private TriviaAPIService triviaAPIService;

    @BeforeEach
    void setUp() {
        triviaAPIService = new TriviaAPIService();
    }

    @Test
    void testGetQuestionsWithCategory() throws TriviaAPIException, InterruptedException {
        Thread.sleep(5000); // Delay to avoid hitting API rate limits

        // Category "Sports" (ID: 21)
        List<Erwtisi> questions = triviaAPIService.getQuestionsWithFilters("easy", "boolean", 5, 21);

        assertNotNull(questions, "The question list should not be empty");
        assertFalse(questions.isEmpty(), "The question list should not be empty");

        questions.forEach(q -> {
            assertEquals("Sports", q.getKathgoria(), "Each question should belong to the "Sports" category");
            System.out.println("Question: " + q.getKeimeno() + " - Category: " + q.getKathgoria());
        });
    }
}
