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
import main.com.amazon.ask.colorpicker.SegnaPuntiServlet;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import java.util.Optional;
import java.util.stream.Collectors;

import static com.amazon.ask.request.Predicates.intentName;

public class DimmiIlPunteggio implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("DimmiIlPunteggio"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
		// With the userid, collect the match id and use that to get a map of names and points
		GameMatch match = SegnaPuntiServlet.users_matches.get(input.getRequestEnvelope().getContext().getSystem().getUser().getUserId());
        String speechText;

        if (match != null && match.numberOfPlayers() > 0) {
			speechText = "Ecco la classifica attuale: ";
			speechText += match.getRawList().stream()
				.map(e -> e.getName() + ": " + e.getPoints() + " punti")
				.collect(Collectors.joining("; "));
			speechText += ". Per adesso sta vincendo " + match.getCurrentWinner().getName() + ".";
        } else {
            // No match is bound to the user id
            speechText = "Non ricordo nessuna partita. Prova ad iniziarne una nuova.";
        }

        return input.getResponseBuilder()
                .withSimpleCard("Punteggio", speechText)
                .withSpeech(speechText)
                .withShouldEndSession(false)
                .build();

    }
}
