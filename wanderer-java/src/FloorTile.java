import java.awt.Graphics;

public class FloorTile extends Tile{
  private final String FLOOR_TILE_IMAGE = "./img/floor.png";

  public FloorTile(int posX, int posY){
    this.image = new PositionedImage(FLOOR_TILE_IMAGE, posX, posY);
    isBlocked = false;
  }
}
