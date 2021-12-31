import java.awt.Graphics;
import java.util.Random;

public abstract class Character implements Movable {
  protected int characterPosX;
  protected int characterPosY;
  protected int characterFieldX;
  protected int characterFieldY;
  protected String characterImage;

  protected int level;
  protected double maxHP;
  protected double currentHP;
  protected double dP;
  protected double sP;

  public Character(String characterImage, int characterPosX, int characterPosY, int characterFieldX, int characterFieldY){

    this.characterImage = characterImage;

    this.characterPosX = characterPosX;
    this.characterPosY = characterPosY;

    this.characterFieldX = characterFieldX;
    this.characterFieldY = characterFieldY;

    level = 1;
  }

  //-----------------METHODS------------------

  public abstract void setStartAttributes();

  public boolean isDead(){
    return currentHP <= 0;
  }

  public void draw(Graphics graphics){
    PositionedImage character = new PositionedImage(characterImage, characterPosX, characterPosY);
    character.draw(graphics);
  }

  public void increaseLevel(){
    Random random = new Random();
    this.level++;
    this.maxHP += (random.nextInt(6) + 1);
    this.dP += (random.nextInt(6) + 1);
    this.sP += (random.nextInt(6) + 1);
  }

  //-----------------INTERFACE------------------

  public void moveUp(Map map){
    if (characterFieldY > 0 &&
        !map.isWall(characterFieldX, characterFieldY - 1)) {
      characterFieldY--;
      characterPosY -= map.getFieldHeight();
      map.addSteps(this);
    }
  }

  public void moveDown(Map map){
    if (characterFieldY < map.getFIELDS_Y()-1 &&
        !map.isWall(characterFieldX, characterFieldY + 1)) {
      characterFieldY++;
      characterPosY += map.getFieldHeight();
      map.addSteps(this);
    }
  }

  public void moveLeft(Map map){
    if (characterFieldX > 0 &&
        !map.isWall(characterFieldX - 1, characterFieldY)) {
      characterFieldX--;
      characterPosX -= map.getFieldWidth();
      map.addSteps(this);
    }
  }

  public void moveRight(Map map){
    if (characterFieldX < map.getFIELDS_X()-1 &&
        !map.isWall(characterFieldX + 1, characterFieldY)) {
      characterFieldX++;
      characterPosX +=  map.getFieldWidth();
      map.addSteps(this);
    }
  }


  //-----------------GETTERS-&&-SETTERS------------------

  public int getCharacterPosX() {
    return characterPosX;
  }
  public void setCharacterPosX(int characterPosX) {
    this.characterPosX = characterPosX;
  }

  public int getCharacterPosY() {
    return characterPosY;
  }
  public void setCharacterPosY(int characterPosY) {
    this.characterPosY = characterPosY;
  }

  public int getCharacterFieldX() {
    return characterFieldX;
  }
  public void setCharacterFieldX(int characterFieldX) {
    this.characterFieldX = characterFieldX;
  }

  public int getCharacterFieldY() {
    return characterFieldY;
  }
  public void setCharacterFieldY(int characterFieldY) {
    this.characterFieldY = characterFieldY;
  }

  public String getCharacterImage() {
    return characterImage;
  }

  public double getMaxHP() {
    return maxHP;
  }
  public void setMaxHP(double maxHP) {
    this.maxHP = maxHP;
  }

  public double getCurrentHP() {
    return currentHP;
  }
  public void setCurrentHP(double currentHP) {
    this.currentHP = currentHP;
  }

  public double getDP() {
    return dP;
  }
  public void setDP(double dP) {
    this.dP = dP;
  }

  public double getSP() {
    return sP;
  }
  public void setSP(double sP) {
    this.sP = sP;
  }

  public int getLevel() {
    return level;
  }
  public void setLevel(int level) {
    this.level = level;
  }
}
