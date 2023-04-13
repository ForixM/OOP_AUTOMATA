import gameEngine.Automata;
import gameEngine.registry.Registration;
import gameEngine.rendering.Scene;
import scenes.LoadingScene;
import scenes.PerlinScene;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
//        test();
        game();
    }

    private static void game(){
        Automata engine = new Automata();
        Scene scene;
//        scene = new GameScene();
        //scene = new PerlinScene();
        scene = new LoadingScene();
        engine.switchScene(scene);
        Registration.beginRegistration();
    }

    private static void test(){
        JFrame window = new JFrame();
        window.setTitle("Test");
        window.setSize(1280, 720);
        window.setLocationRelativeTo(null);
        JPanel panel = new PerlinScene();
        window.setContentPane(panel);
        window.setVisible(true);
    }
}