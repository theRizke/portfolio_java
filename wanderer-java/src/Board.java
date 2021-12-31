import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Board extends JComponent implements KeyListener {

  private static final int BOARD_WIDTH = 720;
  private static final int BOARD_HEIGHT = 720;
  private static final int INFO_WIDTH = 120;
  private static final int FIELDS_X = 10;
  private static final int FIELDS_Y = 10;

  private boolean isStarted;
  private int level;

  Hero hero;
  Map map;
  UI ui;

  //TODO startScreen method


  public Board() {
    setPreferredSize(new Dimension(BOARD_WIDTH + INFO_WIDTH, BOARD_HEIGHT));
    setVisible(true);
    initGame();
  }

  private void showStartScreen(Graphics graphics) {
    ui.showAlert("RPG GAME", "Use Arrows to start and play the game.", graphics, 0,
        BOARD_HEIGHT / 3,
        BOARD_WIDTH + INFO_WIDTH, BOARD_HEIGHT);
  }

  private void showNextLevelScreen(Graphics graphics) {
    ui.showAlert("LEVEL UP", "Current: Level " + level, graphics, 0,
        BOARD_HEIGHT / 3,
        BOARD_WIDTH + INFO_WIDTH, BOARD_HEIGHT);
  }



  public void initGame() {
    hero = new Hero(0, 0, 0, 0, HeroPostures.DOWN);
    hero.setStartAttributes();
    map = new Map(BOARD_WIDTH, BOARD_HEIGHT, FIELDS_X, FIELDS_Y, 1);
    ui = new UI();
    this.level = 1;
  }

  public void initNextLevel(int level) {
    hero.setPositionToStart();
    map.adjustCreaturesAttributes();
    map = new Map(BOARD_WIDTH, BOARD_HEIGHT, FIELDS_X, FIELDS_Y, level);
    isStarted = false;
    this.level = level;
  }

  @Override
  public void paint(Graphics graphics) {
    super.paint(graphics);

    if (!hero.isDead()) {
      map.drawTiles(graphics);
      map.drawCreatures(graphics);
      hero.draw(graphics);
      ui.showCharactersInfo(graphics, BOARD_WIDTH, 20, hero, map);

    } else {
      map.drawTiles(graphics);
      graphics.fillRect(0, 0, BOARD_WIDTH + INFO_WIDTH, BOARD_HEIGHT);
      ui.showAlert("YOU'RE LOST THE FIGHT", "You're dead - Press R to restart the game.", graphics, 0, BOARD_HEIGHT / 3,
          BOARD_WIDTH + INFO_WIDTH, BOARD_HEIGHT);
    }

    if (!isStarted && level == 1) {
      showStartScreen(graphics);
    } else if (!isStarted && level > 1){
      showNextLevelScreen(graphics);
    }


  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("GTA VI");
    Board board = new Board();
    frame.add(board);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    frame.pack();
    frame.addKeyListener(board);
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyPressed(KeyEvent e) {
  }

  @Override
  public void keyReleased(KeyEvent e) {

    int numberOfStepsBeforeKeyRelease = map.getSteps();

    if (!isStarted) {
      isStarted = true;
    }

    if (e.getKeyCode() == KeyEvent.VK_S) {
      initGame();
      isStarted = true;

    } else if (e.getKeyCode() == KeyEvent.VK_UP) {
      hero.setPosture(HeroPostures.UP);
      hero.moveUp(map);

    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
      hero.setPosture(HeroPostures.DOWN);
      hero.moveDown(map);

    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      hero.setPosture(HeroPostures.RIGHT);
      hero.moveRight(map);

    } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      hero.setPosture(HeroPostures.LEFT);
      hero.moveLeft(map);

    } else if( e. getKeyCode() == KeyEvent.VK_R){
      initGame();
    }

    //Fight
    else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
      if (map.hasCreatureOnHeroField(hero)) {
        Character creature = map.creatureOnHeroField(hero);
        Fight fight = new Fight(hero, creature);
        fight.fight();
        if (creature instanceof Boss && creature.isDead() && !hero.isDead()) {
          initNextLevel(map.getLevel() + 1);
          hero.adjustAttributes();
        }
      }
    }

    int numberOfStepsAfterKeyRelease = map.getSteps();

    if (numberOfStepsBeforeKeyRelease != numberOfStepsAfterKeyRelease && numberOfStepsAfterKeyRelease % 2 == 0) {
      map.moveCreatures();
    }

    repaint();

  }

}

