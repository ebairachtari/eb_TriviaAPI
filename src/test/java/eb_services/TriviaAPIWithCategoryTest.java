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
        Thread.sleep(5000); // Καθυστέρηση για αποφυγή rate limits

        // Κατηγορία "Sports" (ID: 21)
        List<Erwtisi> questions = triviaAPIService.getQuestionsWithFilters("easy", "boolean", 5, 21);

        assertNotNull(questions, "Η λίστα ερωτήσεων δεν πρέπει να είναι null.");
        assertFalse(questions.isEmpty(), "Η λίστα ερωτήσεων δεν πρέπει να είναι κενή.");

        questions.forEach(q -> {
            assertEquals("Sports", q.getKathgoria(), "Η κατηγορία πρέπει να είναι Sports.");
            System.out.println("Ερώτηση: " + q.getKeimeno() + " - Κατηγορία: " + q.getKathgoria());
        });
    }
}
