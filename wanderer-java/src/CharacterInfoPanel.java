import java.awt.Font;
import java.awt.Graphics;

public class CharacterInfoPanel {
  Character character;
  String text;
  int posX;
  int posY;

  public CharacterInfoPanel(Character character, String text, int posX, int posY) {
    this.character = character;
    this.text = text;
    this.posX = posX;
    this.posY = posY;
  }

  public void drawInfo(Graphics graphics){

    Font currentFont = graphics.getFont();
    Font newFont = currentFont.deriveFont(currentFont.getSize() * 1.4F);
    graphics.setFont(newFont);
    graphics.drawString(text,  posX, posY);
    graphics.setFont(currentFont);

    PositionedImage characterImage = new PositionedImage(character.getCharacterImage(), posX + 10, posY + 10);
    characterImage.draw(graphics);

    graphics.drawString("Level " + character.getLevel(),  posX, posY + 110);
    graphics.drawString("HP:  " + String.format("%.1f", character.getCurrentHP()) + " / " + character.getMaxHP(),  posX, posY + 130);
    graphics.drawString("DP:  " + character.getDP(),  posX, posY + 150);
    graphics.drawString("SP:  " + character.getSP(),  posX, posY + 170);

  }
}
