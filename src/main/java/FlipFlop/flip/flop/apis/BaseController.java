package FlipFlop.flip.flop.apis;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {
    //ToDo: Add a separate base page
    @GetMapping("/")
    public String indexPage(){
        return "redirect:/lobby";
    }
}
