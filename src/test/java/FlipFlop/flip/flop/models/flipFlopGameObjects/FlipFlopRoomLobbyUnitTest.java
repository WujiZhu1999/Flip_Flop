package FlipFlop.flip.flop.models.flipFlopGameObjects;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class FlipFlopRoomLobbyUnitTest {

    @Test
    void testAddRoom(){
        String roomKey = FlipFlopRoomLobby.getInstance().addRoom(new FlipFlopRoom());

        assertNotNull(FlipFlopRoomLobby.getInstance().getRoom(roomKey));
    }

    @Test
    void testGetRoomNotExist(){
        String roomKey = FlipFlopRoomLobby.getInstance().addRoom(new FlipFlopRoom()) + "abcde";

        assertNull(FlipFlopRoomLobby.getInstance().getRoom(roomKey));
    }

    @Test
    void testRemoveExistRoom(){
        String roomKey = FlipFlopRoomLobby.getInstance().addRoom(new FlipFlopRoom());
        FlipFlopRoomLobby.getInstance().removeRoom(FlipFlopRoomLobby.getInstance().getRoom(roomKey));

        assertNull(FlipFlopRoomLobby.getInstance().getRoom(roomKey));
    }
}
