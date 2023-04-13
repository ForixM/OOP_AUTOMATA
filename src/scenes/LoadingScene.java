package scenes;

import gameEngine.Automata;
import gameEngine.registry.Registration;
import gameEngine.rendering.Scene;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class LoadingScene extends Scene {

    private Image logo;
    private int progression;
    public LoadingScene(){
        progression = 0;
        try {
            logo = ImageIO.read(new File("./assets/icon.png"));
            Thread thread = new Thread(() -> {
                int last = Registration.loaded;
                while (progression < 100){
                    progression = (Registration.loaded * 100) /Registration.REGISTRY_COUNT;
//                    System.out.println("progression = " + progression);
                    if (last != Registration.loaded)
                        repaint();
                    last = Registration.loaded;
                    try {
                        Thread.sleep(1000/60);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                Automata.INSTANCE.beginInitializers();
                Automata.INSTANCE.switchScene(new GameScene());
                Automata.INSTANCE.gameLoop();
            });
            thread.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(logo, getWidth()/2-50, getHeight()/2-100, 100, 100, null);
        g.setColor(Color.GRAY);
        g.drawRect(getWidth()/2-100, getHeight()/2+20, 200, 20);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(getWidth()/2-100+2, getHeight()/2+22, (Registration.loaded*196)/Registration.REGISTRY_COUNT, 16);
    }
}
