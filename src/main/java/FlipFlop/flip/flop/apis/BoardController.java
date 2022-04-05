package FlipFlop.flip.flop.apis;

import FlipFlop.flip.flop.models.communicationObjects.BoardObject;
import FlipFlop.flip.flop.models.communicationObjects.BoardUpdateObject;
import FlipFlop.flip.flop.models.communicationObjects.ClickRequestObject;
import FlipFlop.flip.flop.models.communicationObjects.FlipFlopSocketObject;
import FlipFlop.flip.flop.service.BoardActionService;
import FlipFlop.flip.flop.service.RoomActionService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class BoardController {

    private BoardActionService boardActionService = new BoardActionService();
    private RoomActionService roomActionService = new RoomActionService();

    @MessageMapping("/click/{key}")
    @SendTo("/flipflop/{key}")
    public FlipFlopSocketObject startNewGame(
            @DestinationVariable(value = "key") String key,
            ClickRequestObject clickRequestObject
    ){
        BoardUpdateObject boardUpdateObject = boardActionService
                .click(key, clickRequestObject.getX(), clickRequestObject.getY(), clickRequestObject.getVersion());

        BoardObject boardObject = roomActionService.getCurrentBoard(key);
        Boolean gameOver = boardActionService.gameOver(key);

        FlipFlopSocketObject flipFlopSocketObject = new FlipFlopSocketObject();
        flipFlopSocketObject.setBoardObject(boardObject);
        flipFlopSocketObject.setBoardUpdateObject(boardUpdateObject);
        flipFlopSocketObject.setGameOver(gameOver);
        return flipFlopSocketObject;

    }
}
