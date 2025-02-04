package eb_services;

import eb_services.TriviaAPIService;


public class TriviaAPI {

    // Μέθοδος που δημιουργεί και επιστρέφει ένα νέο αντικείμενο TriviaAPIService.
    public static TriviaAPIService getTriviaService() {
        return new TriviaAPIService();
    }
}
