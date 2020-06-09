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

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;

import main.com.amazon.ask.colorpicker.GameMatch;
import main.com.amazon.ask.colorpicker.SegnaPuntiServlet;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.requestType;

public class LaunchRequestHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(requestType(LaunchRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
		String speechText = "Benvenuto in Segna Punti. Dimmi quanti punti aggiungere e a chi per iniziare.";
		GameMatch match = SegnaPuntiServlet.users_matches.get(input.getRequestEnvelope().getContext().getSystem().getUser().getUserId());
		if (match != null && match.numberOfPlayers() > 0) {
			speechText += " Hai una partita in sospeso. Chiedimi a quanto stiamo!";
		}
        String repromptText = "Dimmi a chi aggiungere i punti e quanti per iniziare.";
        return input.getResponseBuilder()
                .withSimpleCard("Punteggio", speechText)
                .withSpeech(speechText)
                .withReprompt(repromptText)
                .build();
    }
}
