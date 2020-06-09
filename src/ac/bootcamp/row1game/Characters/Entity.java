package ac.bootcamp.row1game.Characters;

import ac.bootcamp.row1game.RandomMonsterSkills;
import ac.bootcamp.row1game.Skills;
import ac.bootcamp.row1game.Randomizer;
import org.academiadecodigo.simplegraphics.pictures.Picture;

public abstract class Entity implements Skills {

    private final double HIT_CHANCE = 0.9;
    private final double CRIT_CHANCE = 0.2;


    private int health;
    private int maxHP;
    private int potionAvailable;
    private int level;
    private int defense;
    private boolean dead;
    private boolean blocking;
    private int baseDamage;
    private Picture[] sprite;
    private boolean isCharching = false;
    private int strongLeft;

    public Entity(EntityType entity) {
        this.health = entity.getHealth();
        this.maxHP = health;
        this.potionAvailable = 3;
        this.level = 1;
        this.defense = entity.getDefense();
        this.dead = false;
        this.blocking = false;
        this.baseDamage = entity.getDamage();
        this.strongLeft = 3;
    }

    @Override
    public void attack(Entity target) {
        int damage = baseDamage + (Randomizer.getRandom(0, baseDamage));

        if (Math.random() < getHIT_CHANCE()) {
            if(Math.random() < getCRIT_CHANCE() ){
                System.out.println("CRITICAL");
                damage *=2;
            }
            System.out.println("HIT Power: " + damage);
            target.hit(damage);
            return;
        }
        target.hit(0);
        System.out.println("MISS!");
        return;
    }

    @Override
    public void spell(Entity target) {
        int damage = Randomizer.getRandom(0, baseDamage * 3);

        if(Math.random() < getHIT_CHANCE()) {
            if(Math.random() < getCRIT_CHANCE()){
                System.out.println("CRITICAL");
                damage *=2;
            }
            System.out.println("SPELL Power: " + damage);
            target.hit(damage);
            return;
        }
        target.hit(0);
        System.out.println("MISS!");
        return;
    }
    public boolean getIsCharching(){
        return isCharching;
    }
    public void setIsCharching(boolean change){
        isCharching = change;
    }

    public int getStrongLeft(){
        return strongLeft;
    }


    @Override
    public void strongAttack(Entity monster,Entity target){

        int damage = 75 * (monster.getLevel());
        if(monster instanceof Player){
            damage = Randomizer.getRandom(30*monster.getLevel(),75*monster.getLevel());
        }

        if(getIsCharching()) {
            if (Math.random() < getHIT_CHANCE()) {
                System.out.println("HUGE DAMAGE");
                target.hit(damage);
                }

                strongLeft -=1;
            isCharching = false;
            return;
        }

        System.out.println("THE ENEMY IS STORING POWER");
        isCharching = true;
        return;

    }

    @Override
    public void block() {

        this.blocking = true;
    }

    public int getMaxHP(){
        return maxHP;
    }

    @Override
    public void heal(int min, int max) {


        int random = Randomizer.getRandom(min, max);

        if (potionAvailable <= 0) {
            System.out.println("Out of Healing Potions...");
            return;
        }

        health = (health + random <= maxHP ? health + random : maxHP);
        System.out.println("Using a " + random + " Healing Potion");
        potionAvailable -= 1;
    }


    public void getHeal() {

    }

    public int getPotionAvailable(){
        return potionAvailable;
    }

    public void stopBlocking(){
        blocking = false;
    }

    @Override
    public void hit(int damage) {

        if (blocking) {
            if (!(damage == 0)) {
                System.out.println(("Attack blocked! " + damage));
                damage = (int)(damage / 4);
            }

        }

        damage -= defense;

        if (damage <= 0) {
            damage = 0;
        }

        health = (health - damage <= 0 ? 0 : health - damage);
        System.out.println("Hit damage: " + damage);

        if (health <= 0) {
            dead = true;
            System.out.println("You dead!");
        }
    }

    public void levelUp() {

        level += 1;
        maxHP += 100;
        health = maxHP;
        baseDamage += 5;
        defense += 3;
        System.out.println(" Level up! " + level);
    }

    public double getHIT_CHANCE() {
        return HIT_CHANCE;
    }

    public double getCRIT_CHANCE(){
        return CRIT_CHANCE;
    }

    public int getHealth() {
        return health;
    }

    public double getDefense() {
        return defense;
    }

    public boolean isDead() {
        return dead;
    }

    public boolean isBlocking() {
        return blocking;
    }

    public Picture[] getSprite() {
        return sprite;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "health=" + health +
                ", maxHP=" + maxHP +
                ", potionAvailable=" + potionAvailable +
                ", level=" + level +
                ", defense=" + defense +
                ", dead=" + dead +
                ", blocking=" + blocking +
                '}';
    }
}
