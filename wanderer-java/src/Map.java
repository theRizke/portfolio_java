import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Map {
  private final int FIELDS_X;
  private final int FIELDS_Y;
  private final int width;
  private final int height;
  private ArrayList<ArrayList<Tile>> tiles;
  private ArrayList<Character> characters;

  private int level;
  private int steps;

  public Map(int WIDTH, int HEIGHT, int FIELDS_X, int FIELDS_Y, int level) {
    this.width = WIDTH;
    this.height = HEIGHT;
    this.FIELDS_X = FIELDS_X;
    this.FIELDS_Y = FIELDS_Y;
    tiles = createTiles(new String[] {
        "OOOXOOOOOO",
        "OOOXOXOXXO",
        "OXXXOXOXXO",
        "OOOOOXOOOO",
        "XXXXOXXXXO",
        "OXOXOOOOOO",
        "OXOXOXXOXO",
        "OOOOOXXOXO",
        "OXXXOOOXOO",
        "OOOXOXOOOO"});

    characters = createCreatures(level);
    this.level = level;
  }

  //-----------------PUBLIC METHODS------------------

  public Character creatureOnHeroField(Hero hero) {
    for (Character character : characters) {
      if (character.getCharacterFieldX() == hero.getCharacterFieldX() &&
          character.getCharacterFieldY() == hero.getCharacterFieldY()) {
        return character;
      }
    }
    return null;
  }

  public ArrayList<ArrayList<Tile>> createTiles(String[] template) {
    ArrayList<ArrayList<Tile>> map = new ArrayList<>();
    for (int i = 0; i < FIELDS_X; i++) {
      ArrayList<Tile> rows = new ArrayList<>();
      map.add(rows);
      for (int j = 0; j < FIELDS_Y; j++) {
        int tilePosX = width / 10 * i;
        int tilePosY = height / 10 * j;
        Tile tile;
        if (template[j].charAt(i) == 'O') {
          tile = new FloorTile(tilePosX, tilePosY);
        } else {
          tile = new WallTile(tilePosX, tilePosY);
        }
        map.get(i).add(tile);
      }
    }
    return map;
  }


  public boolean hasCreatureOnHeroField(Hero hero) {
    for (Character character : characters) {
      if (character.getCharacterFieldX() == hero.getCharacterFieldX() &&
          character.getCharacterFieldY() == hero.getCharacterFieldY()) {
        return true;
      }
    }
    return false;
  }

  public boolean hasCharacterOnSelectedField(int fieldX, int fieldY){
    for (Character character : characters) {
      if (character.getCharacterFieldX() == fieldX &&
          character.getCharacterFieldY() == fieldY) {
        return true;
      }
    }
    return false;
  }


  public boolean isWall(int x, int y) {
    return tiles.get(x).get(y).isBlocked;
  }


  public void moveCreatures() {
    Random random = new Random();
    for (Character character : characters) {

      int prevFieldX = character.getCharacterFieldX();
      int prevFieldY = character.getCharacterFieldY();
      boolean isMoved = false;

      while (!isMoved) {
        int rand = random.nextInt(4);
        switch (rand) {
          case 0:
            character.moveUp(this);
            break;
          case 1:
            character.moveRight(this);
            break;
          case 2:
            character.moveDown(this);
            break;
          case 3:
            character.moveLeft(this);
            break;
        }

        if (prevFieldX != character.getCharacterFieldX() || prevFieldY !=
            character.getCharacterFieldY()) {
          isMoved = true;
        }
      }
    }
  }


  public void addSteps(Character character) {
    if(character instanceof Hero){
      this.steps += 1;
    }
  }

  public void increaseLevel(){
    level++;
  }

  public void adjustCreaturesAttributes(){
    for(Character creature : characters){
      if(creature instanceof Skeleton){
        ((Skeleton) creature).setNextLevelAttributes();
      }
    }
  }


  public void drawTiles(Graphics graphics) {
    for (int i = 0; i < tiles.size(); i++) {
      for (int j = 0; j < tiles.get(i).size(); j++) {
        tiles.get(i).get(j).drawTile(graphics);
      }
    }
  }

  public void drawCreatures(Graphics graphics) {
    for (Character character : characters) {
      if(character.getCurrentHP() > 0){
        character.draw(graphics);
      }
    }
  }

  //-----------------PRIVATE METHODS--------------------

  private ArrayList<Character> createCreatures(int level) {
    ArrayList<Character> characters = new ArrayList<>();
    Random random = new Random();
    int gapX = width / FIELDS_X;
    int gapY = width / FIELDS_Y;

    int numberOfCreatures = random.nextInt(3) + 3;
    for (int i = 0; i < numberOfCreatures; i++) {
      int fieldX, fieldY;
      do {
        fieldX = random.nextInt(FIELDS_X);
        fieldY = random.nextInt(FIELDS_Y);
      } while (tiles.get(fieldX).get(fieldY).isBlocked && fieldX != 0 && fieldY != 0);
      Character skeleton = new Skeleton(fieldX * gapX, fieldY * gapY, fieldX, fieldY);
      skeleton.setLevel(level);
      skeleton.setStartAttributes();
      characters.add(skeleton);
    }

    int fieldX, fieldY;
    do {
      fieldX = random.nextInt(FIELDS_X);
      fieldY = random.nextInt(FIELDS_Y);
    } while (tiles.get(fieldX).get(fieldY).isBlocked && fieldX != 0 && fieldY != 0);
    Character boss = new Boss(fieldX * gapX, fieldY * gapY, fieldX, fieldY);
    boss.setLevel(level);
    boss.setStartAttributes();
    characters.add(boss);

    return characters;
  }



  //-----------------GETTERS-&&-SETTERS------------------

  public int getFieldWidth() {
    return width / FIELDS_X;
  }

  public int getFieldHeight() {
    return height / FIELDS_Y;
  }

  public int getFIELDS_X() {
    return FIELDS_X;
  }

  public int getFIELDS_Y() {
    return FIELDS_Y;
  }

  public ArrayList<Character> getCharacters() {
    return characters;
  }

  public int getSteps() {
    return steps;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }
}
