/*
     Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

     Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file
     except in compliance with the License. A copy of the License is located at

         http://aws.amazon.com/apache2.0/

     or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
     the specific language governing permissions and limitations under the License.
*/

package main.com.amazon.ask.colorpicker.handlers;

import main.com.amazon.ask.colorpicker.GameMatch;
import main.com.amazon.ask.colorpicker.Player;
import main.com.amazon.ask.colorpicker.SegnaPuntiServlet;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.RequestHelper;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class SegnaNomiIntent implements RequestHandler {
	public static final String POINTS_SLOT = "Points";
	public static final String NAME_SLOT = "Name";

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("SegnaNomiIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
		RequestHelper requestHelper = RequestHelper.forHandlerInput(input);
		Optional<String> name = requestHelper.getSlotValue(NAME_SLOT);
		Optional<String> points = requestHelper.getSlotValue(POINTS_SLOT);
		String speechText = "Puoi aggiungere o aggiornare il punteggio di un giocatore dicendo, segna 5 punti per Francesco.";
		String repromptText = "Puoi aggiungere o aggiornare il punteggio di un giocatore dicendo, segna 5 punti per Francesco.";
		String targetName = null;
		int targetPoints = 0;

		try {
			targetName = name.get();
			targetPoints = Integer.parseInt(points.get());
		} catch(Exception e) {
			// Render an error since user input is out of list of color defined in interaction model.
			System.out.println(e.getMessage());
            speechText = "Dimmi a chi devo aggiungere i punti, e quanti.";
            repromptText = "Dimmi a chi devo aggiungere i punti, e quanti.";
		}
		if (targetName != null && targetPoints != 0) {
			Player player = new Player(targetName, targetPoints);
			String userid = input.getRequestEnvelope().getContext().getSystem().getUser().getUserId();
			GameMatch match = SegnaPuntiServlet.users_matches.get(userid);
			if(match == null) {
				match = new GameMatch();
				SegnaPuntiServlet.users_matches.put(userid, match);
			}
			Player existingPlayer = match.findNamedPlayer(player);
			if(existingPlayer != null) {
				existingPlayer.setPoints(existingPlayer.getPoints() + player.getPoints());
				speechText = String.format("%d punti aggiunti al giocatore %s.", targetPoints, existingPlayer.getName());
			} else {
				match.addPlayer(player);
				existingPlayer = player;
				speechText = String.format("Giocatore %s creato con %d punti.", player.getName(), player.getPoints());
			}
			if(SegnaPuntiServlet.max_points != 0 && existingPlayer.getPoints() >= SegnaPuntiServlet.max_points) {
				speechText += String.format(" Il giocatore %s ha vinto, con %d punti.", existingPlayer.getName(), existingPlayer.getPoints());
			}
		}
        return input.getResponseBuilder()
                .withSimpleCard("Punteggio", speechText)
                .withSpeech(speechText)
                .withReprompt(repromptText)
                .withShouldEndSession(false)
				.build();
    }

}
