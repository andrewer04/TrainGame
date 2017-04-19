package application;

public class Main {

    public static void main(String[] args){
        while (true){
            Commander commander = new Commander();
            commander.observer();
        }
        /*for(int level = 1; level <=2; level++){
            controller = new Controller(mapcreator.build(level));
            controller.makeTrain(level);
            while(controller.getWinFlag()){
                controller.observer();
                if (timer.start()){
                    controller.run();
                }
            }
        }*/
    }
}
