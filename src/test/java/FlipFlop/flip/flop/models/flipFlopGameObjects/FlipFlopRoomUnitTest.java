package FlipFlop.flip.flop.models.flipFlopGameObjects;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class FlipFlopRoomUnitTest {

    @Test
    void testJoin(){
        FlipFlopRoom room = new FlipFlopRoom();

        assertTrue(room.isEmpty());
        room.join(new User("user"));
        assertFalse(room.isEmpty());
    }

    @Test
    void testLeaveSuccess(){
        FlipFlopRoom room = new FlipFlopRoom();

        assertTrue(room.isEmpty());
        room.join(new User("user"));
        room.leave(new User("user"));

        assertTrue(room.isEmpty());
    }

    @Test
    void repeatJoin(){
        FlipFlopRoom room = new FlipFlopRoom();

        assertTrue(room.isEmpty());
        room.join(new User("user"));
        room.join(new User("user"));
        room.leave(new User("user"));

        assertTrue(room.isEmpty());
    }

    @Test
    void leaveNotExitUser(){
        FlipFlopRoom room = new FlipFlopRoom();

        assertTrue(room.isEmpty());
        room.join(new User("user"));
        room.leave(new User("user1"));

        assertFalse(room.isEmpty());
    }
}
