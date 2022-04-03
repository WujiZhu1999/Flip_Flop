package FlipFlop.flip.flop.apis;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomController {

    @GetMapping("/room")
    public String getRoom(){
        return "Room/Room";
    }

    @MessageMapping("/newGame")
    @SendTo("/flipflop/room1")
    public String newGame(){
        return "successful connection!";
    }
}
