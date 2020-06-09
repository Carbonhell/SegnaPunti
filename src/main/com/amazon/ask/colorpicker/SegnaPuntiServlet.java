/*
     Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

     Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file
     except in compliance with the License. A copy of the License is located at

         http://aws.amazon.com/apache2.0/

     or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
     the specific language governing permissions and limitations under the License.
*/

package main.com.amazon.ask.colorpicker;

import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import main.com.amazon.ask.colorpicker.handlers.FallbackIntentHandler;
import main.com.amazon.ask.colorpicker.handlers.HelpIntentHandler;
import main.com.amazon.ask.colorpicker.handlers.ImpostaMaxIntent;
import main.com.amazon.ask.colorpicker.handlers.LaunchRequestHandler;
import main.com.amazon.ask.colorpicker.handlers.RicominciaPartitaIntent;
import main.com.amazon.ask.colorpicker.handlers.SessionEndedRequestHandler;
import com.amazon.ask.servlet.SkillServlet;
import main.com.amazon.ask.colorpicker.handlers.DimmiIlPunteggio;
import main.com.amazon.ask.colorpicker.handlers.CancelandStopIntentHandler;
import main.com.amazon.ask.colorpicker.handlers.SegnaNomiIntent;

import java.util.HashMap;

public class SegnaPuntiServlet extends SkillServlet {
	//ArrayList of players represents a match
	public static HashMap<String, GameMatch> users_matches = new HashMap<>();
	public static int max_points = 0;

    private static Skill getSkill() {
        return Skills.standard()
                .addRequestHandlers(
                        new DimmiIlPunteggio(),
						new SegnaNomiIntent(),
						new ImpostaMaxIntent(),
						new RicominciaPartitaIntent(),
                        new LaunchRequestHandler(),
                        new CancelandStopIntentHandler(),
                        new SessionEndedRequestHandler(),
                        new HelpIntentHandler(),
                        new FallbackIntentHandler())
                // Add your skill id below
                //.withSkillId("")
                .build();
    }

    public SegnaPuntiServlet() {

        super(getSkill());
    }

}
