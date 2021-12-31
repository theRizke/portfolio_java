import java.awt.Graphics;
import java.util.Random;

public class Hero extends Character {
  HeroPostures heroPostures;

  public Hero(int posX, int posY, int fieldX, int fieldY, HeroPostures heroPostures) {
    super("./img/hero-down.png", posX, posY, fieldX, fieldY);
    this.heroPostures = heroPostures;
  }

  public void setPositionToStart(){
    this.characterPosX = 0;
    this.characterPosY = 0;
    this.characterFieldX = 0;
    this.characterFieldY = 0;
    this.heroPostures = HeroPostures.DOWN;
    this.characterImage = HeroPostureToStringImage(HeroPostures.DOWN);
  }

  public void setStartAttributes() {
    Random random = new Random();
    maxHP = 20 + (3 * (random.nextInt(6) + 1));
    currentHP = maxHP;
    dP = 2 * (random.nextInt(6) + 1);
    sP = 5 + (random.nextInt(6) + 1);
  }


  public void setPosture(HeroPostures heroPosture) {
    characterImage = HeroPostureToStringImage(heroPosture);
  }

  public void adjustAttributes(){
    Random random = new Random();
    int rnd = random.nextInt(10);

    if(rnd == 0){
      currentHP = maxHP;
    }
    else if(rnd < 4){
      currentHP = currentHP * 1.3f;
    }
    else{
      currentHP *= 1.1f;
    }

    if(currentHP > maxHP){
      currentHP = maxHP;
    }
  }

  private String HeroPostureToStringImage(HeroPostures heroPostures) {
    switch (heroPostures) {
      case UP:
        return "./img/hero-up.png";
      case DOWN:
        return "./img/hero-down.png";
      case LEFT:
        return "./img/hero-left.png";
      case RIGHT:
        return "./img/hero-right.png";
      default:
        return "./img/hero-down.png";
    }
  }




}
