package main.com.amazon.ask.colorpicker.handlers;

import main.com.amazon.ask.colorpicker.SegnaPuntiServlet;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class RicominciaPartitaIntent implements RequestHandler {
	@Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("RicominciaPartitaIntent"));
	}

	@Override
    public Optional<Response> handle(HandlerInput input) {
		String speechText = "La partita è finita. Inizia una nuova partita assegnando i punti a qualcuno.";
		String repromptText = "La partita è finita. Inizia una nuova partita assegnando i punti a qualcuno.";
		String userid = input.getRequestEnvelope().getContext().getSystem().getUser().getUserId();
		SegnaPuntiServlet.users_matches.remove(userid);
		return input.getResponseBuilder()
                .withSimpleCard("Punteggio", speechText)
                .withSpeech(speechText)
                .withReprompt(repromptText)
                .withShouldEndSession(false)
				.build();
	}
}