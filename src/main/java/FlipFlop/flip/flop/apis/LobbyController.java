package FlipFlop.flip.flop.apis;

import FlipFlop.flip.flop.models.communicationObjects.JoinRoomRequest;
import FlipFlop.flip.flop.models.flipFlopGameObjects.User;
import FlipFlop.flip.flop.service.LobbyActionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Objects;

@Controller
public class LobbyController {
    private LobbyActionService lobbyActionService;

    @GetMapping("/")
    public String getLobby(){
        return "Lobby/HomePage";
    }

    @PostMapping("/join")
    public String join(){

        return "redirect:/room";

    }

}
