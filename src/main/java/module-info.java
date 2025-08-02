module eb.TriviaAPI {
    exports eb_services;
    exports eb_model;
    exports eb_erwtisidb;
    exports eb_exceptions;

    
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.commons.text;
}
