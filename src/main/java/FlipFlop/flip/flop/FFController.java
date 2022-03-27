package FlipFlop.flip.flop;

import FlipFlopGame.FlipFlopBoard;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class FFController {
    @MessageMapping("/start")
    @SendTo("/ffGame/room1")
    public Greeting greeting(HelloMessage message) throws Exception {
        //Thread.sleep(1000); // simulated delay
        //return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
        return new Greeting("aaa");
        //return new Greeting(FlipFlopB.getBoard().boardString());
    }

    @MessageMapping("/flip")
    @SendTo("/ffGame/room1")
    public Greeting click(ClickCoor message) throws Exception {
        //Thread.sleep(1000); // simulated delay
        //return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
        FlipFlopBoard flipFlopBoard = FlipFlopB.getBoard();
        flipFlopBoard.click(message.getW(),message.getH());
        return new Greeting("aaab");
        //return new Greeting(FlipFlopB.getBoard().boardString());
    }
}
