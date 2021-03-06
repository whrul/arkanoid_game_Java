// Author: Walerij Hrul
//
package arkanoid.controllersTests;

import arkanoid.controllers.PlayerController;
import arkanoid.models.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerControllerTests {

    private Player player = Player.builder().build();
    private PlayerController playerController = new PlayerController(player);

    @Test
    void constructedPlayerControllerHasProperPlayer() {
        assertEquals(player, playerController.getPlayer());
    }

    @Test
    void afterMovingToLeftPlayerPositionUpdates() {
        player.setPosX(100);
        player.setDirX(10);

        playerController.moveLeft();

        assertEquals(90, player.getPosX());
    }

    @Test
    void afterMovingToRightPlayerPositionUpdates() {
        player.setPosX(100);
        player.setDirX(10);

        playerController.moveRight();

        assertEquals(110, player.getPosX());
    }

}
