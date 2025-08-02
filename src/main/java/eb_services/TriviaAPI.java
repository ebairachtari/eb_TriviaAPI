package eb_services;

import eb_services.TriviaAPIService;


public class TriviaAPI {

    // Method that creates and returns a new TriviaAPIService object.
    public static TriviaAPIService getTriviaService() {
        return new TriviaAPIService();
    }
}
