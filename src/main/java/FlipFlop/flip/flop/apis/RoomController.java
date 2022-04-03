package FlipFlop.flip.flop.apis;

import FlipFlop.flip.flop.models.communicationObjects.BoardObject;
import FlipFlop.flip.flop.models.communicationObjects.FlipFlopSocketObject;
import FlipFlop.flip.flop.models.flipFlopGameObjects.User;
import FlipFlop.flip.flop.service.RoomActionService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.websocket.server.PathParam;

@Controller
public class RoomController {

    RoomActionService roomActionService = new RoomActionService();

    @GetMapping("/room")
    public String getRoom(
            @ModelAttribute("roomKey") String roomKey,
            @ModelAttribute("userName") String userName,
            Model model
    ){
        model.addAttribute("roomKey", roomKey);
        model.addAttribute("gameIsStart", roomActionService.gameIsStart(roomKey));
        model.addAttribute("userName", userName);
        return "Room/Room";
    }

    @MessageMapping("/newGame/{key}")
    @SendTo("/flipflop/{key}")
    public FlipFlopSocketObject startNewGame(
            @DestinationVariable(value = "key") String key
    ){
        FlipFlopSocketObject flipFlopSocketObject = new FlipFlopSocketObject();
        BoardObject newBoard = roomActionService.getNewBoard(key);
        flipFlopSocketObject.setBoardObject(newBoard);
        return flipFlopSocketObject;
    }

    @MessageMapping("/currentGame/{key}")
    @SendTo("/flipflop/{key}")
    public FlipFlopSocketObject getCurrentGame(
            @DestinationVariable(value = "key") String key
    ){
        FlipFlopSocketObject flipFlopSocketObject = new FlipFlopSocketObject();
        BoardObject currentBoard = roomActionService.getCurrentBoard(key);
        flipFlopSocketObject.setBoardObject(currentBoard);
        return flipFlopSocketObject;
    }

    @PostMapping("/leave/{key}")
    public RedirectView leaveRoom(
            @RequestParam(value = "userName") String userName,
            @RequestParam(value = "roomKey") String roomKey
    ){
        roomActionService.leave(new User(userName), roomKey);
        return new RedirectView("/lobby");
    }
}
