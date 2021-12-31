import java.util.Random;

public class Fight {
  private Character attacker;
  private Character defender;

  public Fight(Character attacker, Character defender){
    this.attacker = attacker;
    this.defender = defender;
  }

  public void fight(){
    Random random = new Random();
    double attackValue = attacker.getSP() + 2 * (random.nextInt(6)+1);

    if(attackValue > defender.getDP()){
      defender.setCurrentHP(defender.getCurrentHP() - (attackValue - defender.getDP()));
    }

    if(!defender.isDead()){
      Fight fight =  new Fight(defender, attacker);
      fight.fight();
    }
    else{
      attacker.increaseLevel();
    }

  }
}
