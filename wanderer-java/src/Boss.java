import java.util.Random;

public class Boss extends Character {


  public Boss(int posX, int posY, int fieldX, int fieldY) {
    super("./img/boss.png", posX, posY, fieldX, fieldY);
  }

  @Override
  public void setStartAttributes() {
    Random random = new Random();
    maxHP = 2 * level * (random.nextInt(6) + 1) + (random.nextInt(6) + 1);
    currentHP = maxHP;
    dP = level / 2f * (random.nextInt(6) + 1) + ((random.nextInt(6) + 1) / 2f);
    sP = level * (random.nextInt(6) + 1) + level;
  }
}
