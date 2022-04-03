package FlipFlop.flip.flop.apis;

import FlipFlop.flip.flop.models.flipFlopGameObjects.User;
import FlipFlop.flip.flop.service.LobbyActionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Objects;

@Controller
public class LobbyController {
    private LobbyActionService lobbyActionService = new LobbyActionService();

    @GetMapping("/")
    public String getLobby(
            @RequestParam(value = "errorMessage", required=false) String errorMessage,
            Model model
    ){
        if(!Objects.isNull(errorMessage)){
            model.addAttribute("errorMessage", errorMessage);
        }
        return "Lobby/HomePage";
    }

    @PostMapping("/join")
    public RedirectView join(
            @RequestParam(value = "userName") String userName,
            @RequestParam(value = "roomKey") String roomKey,
            RedirectAttributes attributes
    ){
        String returnStringKey = null;
        if(Objects.isNull(roomKey) || roomKey.isEmpty()) {
            returnStringKey = lobbyActionService.joinRoom(new User(userName));
        } else {
            returnStringKey = lobbyActionService.joinRoom(new User(userName), roomKey);
        }

        if(Objects.isNull(returnStringKey)){
            if(Objects.isNull(roomKey) || roomKey.isEmpty()){
                attributes.addAttribute("errorMessage","Server full.");
            } else {
                attributes.addAttribute("errorMessage","Room not exist.");
            }
            return new RedirectView("/");
        } else {
            return new RedirectView("/room");
        }



    }

}
