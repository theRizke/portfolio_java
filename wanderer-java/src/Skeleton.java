import java.util.Random;

public class Skeleton extends Character {

  public Skeleton(int posX, int posY, int fieldX, int fieldY){
    super("./img/skeleton.png", posX, posY, fieldX, fieldY);
  }

  @Override
  public void setStartAttributes() {
    Random random = new Random();
    maxHP = 2 * level * (random.nextInt(6)+1);
    currentHP = maxHP;
    dP = level / 2f * (random.nextInt(6)+1);
    sP = level * (random.nextInt(6)+1);
  }

  public void setNextLevelAttributes(){
    Random random = new Random();
    currentHP = 2 * level * (random.nextInt(6)+1);
    dP = level / 2f * (random.nextInt(6)+1);
    sP = level * (random.nextInt(6)+1);
  }
}
