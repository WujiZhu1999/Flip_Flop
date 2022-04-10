package FlipFlop.flip.flop.models.flipFlopGameObjects;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class BoardUnitTest {

    @Test
    void testUploadImage(){
        Board board = new Board();
        board.uploadImage("testString0");
        board.uploadImage("print('hello')");

        Image expected0 = new Image("testString0");
        Image expected1 = new Image("print('hello')");

        assertEquals(expected0, board.getImage(0));
        assertEquals(expected1, board.getImage(1));
    }

    @Test
    void testUploadImageObject(){
        Board board = new Board();

        Image expected0 = new Image("testString");
        Image expected1 = new Image("print('hello')");

        board.uploadImage(expected0);
        board.uploadImage(expected1);

        assertEquals(expected0, board.getImage(0));
        assertEquals(expected1, board.getImage(1));
    }

    @Test
    void testDeleteImage(){
        Board board = new Board();

        Image expected0 = new Image("testString");
        Image expected1 = new Image("print('hello')");

        board.uploadImage(expected0);
        board.uploadImage(expected1);
        board.deleteImage("testString");
        board.deleteImage("testString1");
        assertEquals(expected1, board.getImage(0));

        assertNull(board.getImage(1));

    }

    @Test
    void testDeleteImageObject(){
        Board board = new Board();

        Image expected0 = new Image("testString");
        Image expected1 = new Image("print('hello')");

        board.uploadImage(expected0);
        board.uploadImage(expected1);
        board.deleteImage(new Image("testString"));
        board.deleteImage(new Image("testString1"));
        assertEquals(expected1, board.getImage(0));

        assertNull(board.getImage(1));

    }

    @Test
    void testClearImage(){
        Board board = new Board();

        Image expected0 = new Image("testString");
        Image expected1 = new Image("print('hello')");

        board.uploadImage(expected0);
        board.uploadImage(expected1);

        board.clearImage();

        assertNull(board.getImage(0));

    }

    @Test
    void testUploadBackGroundImage(){
        Board board = new Board();
        board.uploadBackgroundImage("backgroundString");

        Image expected = new Image("backgroundString");
        assertEquals(expected, board.getBackgroundImage());

    }

    @Test
    void testRemoveBackGroundImage(){
        Board board = new Board();
        board.uploadBackgroundImage(new Image("backgroundString"));

        Image expected = new Image("backgroundString");
        assertEquals(expected, board.getBackgroundImage());
    }

    @Test
    void testGenerateNewBoard(){
        Board board = new Board();

        board.generateNewBoard(4,4,2);

        //no bg image or images should return null
        assertEquals(-1, board.getBoardWidth());
        assertEquals(-1, board.getBoardHeight());

        board.uploadBackgroundImage("bgImage");
        board.generateNewBoard(4,4,2);

        assertEquals(-1, board.getBoardWidth());
        assertEquals(-1, board.getBoardHeight());

        for(int i = 0; i < 5 ; i++){
            board.uploadImage("Image"+i);
        }

        board.generateNewBoard(4,4,2);

        assertEquals(4, board.getBoardWidth());
        assertEquals(4, board.getBoardHeight());
        assertEquals(2, board.getFlipCount());

    }

    @Test
    void testSetOnHoldImageId(){
        Board board = new Board();

        board.setOnHoldImageId(12345);
        assertEquals(-1, board.getOnHoldImageId());

        board.setOnHoldImageId(0);
        assertEquals(-1, board.getOnHoldImageId());

        board.uploadImage("image0");
        board.setOnHoldImageId(0);
        assertEquals(0, board.getOnHoldImageId());
        board.setOnHoldImageId(1);
        assertEquals(0, board.getOnHoldImageId());

        board.uploadImage("image1");
        board.setOnHoldImageId(1);
        assertEquals(1, board.getOnHoldImageId());

        board.setOnHoldImageId(-2);
        assertEquals(1, board.getOnHoldImageId());



    }
}
