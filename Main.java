package mainGame;
import javax.swing.JFrame;

public class Main
{
    public static void main(String[] args)
    {
        JFrame game= new JFrame("Frogger!!!");
        Display frogger= new Display();
        game.add(frogger);
        game.setSize(1080,1080);
        game.setResizable(false);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setVisible(true);
    }
}