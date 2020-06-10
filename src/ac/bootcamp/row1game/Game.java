package ac.bootcamp.row1game;

import ac.bootcamp.row1game.Characters.Entity;
import ac.bootcamp.row1game.Characters.Factory;
import ac.bootcamp.row1game.Characters.Player;
import ac.bootcamp.row1game.display.Display;

public class Game {

    private Player player;
    private Entity[] monsters;
    private Display display;
    private int monsterCounter = 0;
    private KeyboardMouse k = new KeyboardMouse();
    private MonsterSkill monsterSkill = MonsterSkill.ATTACK;


    public Game() {

        this.display = new Display();
        this.player = Factory.createPlayer();
        this.monsters = new Entity[4];
        monsters[0] = Factory.createNormalMonster();
        monsters[1] = Factory.createArmouredMonster();
        monsters[2] = Factory.createHealerMonster();
        monsters[3] = Factory.createHighDamageMonster();

        monsters[1].levelUp();
        monsters[2].levelUp();monsters[2].levelUp();
        monsters[3].levelUp();monsters[3].levelUp();monsters[3].levelUp();

        k.init();



    }




    public void start() throws InterruptedException {

        AudioEngine audio = new AudioEngine();




        display.init();

        while(!player.isDead()){


            System.out.println("\n" + "Player turn : " + player.getHealth());
            //input part

            display.drawMonsterA();

            k.setIsPressed();

            if(!player.getIsCharching()) {

                display.drawActionBar();


            while(!k.getIsPressed()) {
             //   Thread.sleep(500);
                display.drawPlayerIdle();

            }
                switch (k.getKeyPressed()) {
                    case "1":
                        display.deleteActionBar();
                        audio.atack();
                        display.drawPlayerAttack();
                        display.drawAttackDone();
                        player.attack(monsters[monsterCounter]);
                        break;
                    case "2":
                        display.deleteActionBar();
                        audio.spell();
                        player.spell(monsters[monsterCounter]);
                        display.drawPlayerAttack();
                        display.drawSpellDone();
                        break;
                    case "3":
                        display.deleteActionBar();
                        audio.spell();
                        display.drawPlayerAttack();
                        if (player.getStrongLeft() == 0) {
                            System.out.println("no more charges");
                            continue;
                        }
                        display.drawPlayerCharging();
                        player.strongAttack(player, monsters[monsterCounter]);

                        break;
                    case "4":
                        display.deleteActionBar();
                        audio.block();
                        display.drawPlayerAttack();
                        display.drawPlayerBlock();
                        player.block();
                        break;
                    case "5":
                        display.deleteActionBar();
                        audio.heal();
                        if (player.getPotionAvailable() == 0) {
                            System.out.println("no more pots");
                            continue;
                        }
                        player.heal();
                        break;

                    default:
                      //  System.out.println("invalid");
                        continue;

                }

            }else{
                display.deleteActionBar();
                player.strongAttack(player, monsters[monsterCounter]);
                display.drawChargeDone();
            }


           //RandomMonsterSkills.getRandomMonsterSkill(player,monsters[monsterCounter]);
           Thread.sleep(2000);

           monsters[monsterCounter].stopBlocking();


           if(monsters[monsterCounter].isDead()){
               display.deleteMonsterA();
               if(monsterCounter == 3){
                   break;
               }
               
               monsterCounter +=1;
               System.out.println("\n" + "a wild monster appeared");
               audio.newMonster();
               Thread.sleep(1000);
               display.drawBackground(monsterCounter);
               player.levelUp();

               continue;
           }
            System.out.println( "\n" + "monster turn : " + monsters[monsterCounter].getHealth());

           monsterSkill = RandomMonsterSkills.getRandomMonsterSkill(monsters[monsterCounter],player);

           switch (monsterSkill){
               case ATTACK: display.drawMonsterAttackDone(); break;
               case BLOCK: display.drawMonsterAttackDone(); break;
               case HEAL: display.drawMonsterAttackDone(); break;
               case SPELL: display.drawMonsterSpellDone(); break;
               case CHARGE: display.drawMonsterCharging(); break;
               case STRONG: display.drawMonsterChargeDone(); break;
           }

        //   Thread.sleep(2000);

           player.stopBlocking();

        }

        if(!player.isDead()){
            audio.youWin();
            display.drawGameEnding();
        }
        if(player.isDead()){
            audio.youDie();
            display.drawGameOver();
        }


    }


}
