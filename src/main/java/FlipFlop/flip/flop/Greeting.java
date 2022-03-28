package FlipFlop.flip.flop;

import java.util.ArrayList;
import java.util.List;

public class Greeting {

    private String content;
    private List<List<String>> flips;


    public Greeting() {
    }

    public Greeting(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setFlips(List<List<String>> flips) {
        this.flips = flips;
    }

    public List<List<String>> getFlips() {
        return flips;
    }
}