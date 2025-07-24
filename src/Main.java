import javax.swing.JFrame;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Boids Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int WIDTH = Constants.SCREEN_WIDTH;
        int HEIGHT = Constants.SCREEN_HEIGHT;

        frame.setSize(WIDTH, HEIGHT);
        frame.add(new BoidPanel());
        frame.add(new BoidPanel());
        frame.setVisible(true);
    }
}