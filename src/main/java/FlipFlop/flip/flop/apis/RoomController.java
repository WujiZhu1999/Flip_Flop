package FlipFlop.flip.flop.apis;

import FlipFlop.flip.flop.models.communicationObjects.BoardObject;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@Controller
public class RoomController {

    @GetMapping("/room")
    public String getRoom(
            @ModelAttribute("roomKey") String roomKey,
            Model model
    ){
        model.addAttribute("roomKey", roomKey);
        return "Room/Room";
    }

    @RequestMapping("/newGame/{key}")
    @ResponseBody
    public BoardObject startNewGame(
            @PathVariable(value = "key") String key
    ){
        System.out.println(key);
        return new BoardObject();
    }

    @MessageMapping("/testConnection")
    @SendTo("/flipflop/room1")
    public String newGame(){
        return "successful connection!";
    }
}
