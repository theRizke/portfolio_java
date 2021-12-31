import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class UI {

  public void showAlert(String title, String description, Graphics graphics, int startX,
                        int startY, int width, int height) {

    graphics.setColor(new Color(0, 0, 0, 200));
    graphics.fillRect(0, 0, width, height);
    graphics.fillRect(startX, startY, width, height / 3);
    Font currentFont = graphics.getFont();
    Font newFont = currentFont.deriveFont(currentFont.getSize() * 4F);
    graphics.setFont(newFont);
    graphics.setColor(Color.WHITE);
    graphics.drawString(title, 100, height / 2);
    graphics.setFont(currentFont);
    graphics.drawString(description, 100, height / 2 + 40);
    graphics.setColor(Color.darkGray);
  }

  public void showCharactersInfo(Graphics graphics, int posX, int posY, Hero hero, Map map) {

    graphics.drawString(" Map level: " + map.getLevel(), posX + 20, 20);

    CharacterInfoPanel heroInfo = new CharacterInfoPanel(hero, "", posX + 20, posY + 40);
    heroInfo.drawInfo(graphics);

    if (map.hasCreatureOnHeroField(hero) && !map.creatureOnHeroField(hero).isDead()) {
      Character creature = map.creatureOnHeroField(hero);
      CharacterInfoPanel creatureInfo =
          new CharacterInfoPanel(creature, creature.getClass().getSimpleName(), posX + 20,
              posY + 500);
      creatureInfo.drawInfo(graphics);
    }

  }
}
