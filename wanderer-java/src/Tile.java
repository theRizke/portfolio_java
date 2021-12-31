import java.awt.Graphics;

public abstract class Tile {
  protected PositionedImage image;
  protected boolean isBlocked;

  public void drawTile(Graphics graphics){
    image.draw(graphics);
  }
}
