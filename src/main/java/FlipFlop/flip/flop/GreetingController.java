package FlipFlop.flip.flop;

import FlipFlopGame.FlipFlopBoard;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        //Thread.sleep(1000); // simulated delay
        //return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");

        return new Greeting(FlipFlopB.getBoard().boardString());
    }

    @MessageMapping("/click")
    @SendTo("/topic/greetings")
    public Greeting click(ClickCoor message) throws Exception {
        //Thread.sleep(1000); // simulated delay
        //return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
        FlipFlopBoard flipFlopBoard = FlipFlopB.getBoard();
        flipFlopBoard.click(message.getW(),message.getH());
        return new Greeting(FlipFlopB.getBoard().boardString());
    }


}