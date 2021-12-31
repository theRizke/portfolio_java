import java.awt.Graphics;

public class WallTile extends Tile{
  private final String WALL_TILE_IMAGE = "./img/wall.png";

  public WallTile(int posX, int posY){
    this.image = new PositionedImage(WALL_TILE_IMAGE, posX, posY);
    isBlocked = true;
  }
}
