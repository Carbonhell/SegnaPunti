package main.com.amazon.ask.colorpicker.handlers;

import main.com.amazon.ask.colorpicker.SegnaPuntiServlet;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.RequestHelper;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class ImpostaMaxIntent implements RequestHandler {
	public static final String MAX_SLOT = "max";

	@Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("ImpostaMaxIntent"));
	}
	
	@Override
    public Optional<Response> handle(HandlerInput input) {
		RequestHelper requestHelper = RequestHelper.forHandlerInput(input);
		Optional<String> maxPointsSlot = requestHelper.getSlotValue(MAX_SLOT);
		String speechText = "Puoi impostare il punteggio massimo dicendo, vince chi raggiunge 50 punti.";
		String repromptText = "Puoi impostare il punteggio massimo dicendo, vince chi raggiunge 50 punti.";
		int maxPoints = 0;
		try {
			maxPoints = Integer.parseInt(maxPointsSlot.get());
		} catch(Exception e) {
			// Render an error since user input is out of list of color defined in interaction model.
			System.out.println(e.getMessage());
            speechText = "Dimmi a quanti punti bisogna arrivare per vincere.";
            repromptText = "Dimmi a quanti punti bisogna arrivare per vincere.";
		}
		if(maxPoints != 0) {
			SegnaPuntiServlet.max_points = maxPoints;
			speechText = String.format("Ora bisogna arrivare a %d punti per vincere.", maxPoints);
		}
		return input.getResponseBuilder()
                .withSimpleCard("Punteggio", speechText)
                .withSpeech(speechText)
                .withReprompt(repromptText)
                .withShouldEndSession(false)
				.build();
	}
}