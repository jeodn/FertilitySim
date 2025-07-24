import javax.swing.*;
import java.awt.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Boids Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int WIDTH = Constants.SCREEN_WIDTH;
        int HEIGHT = Constants.SCREEN_HEIGHT + 200;

        PopulationGraphPanel graphPanel = new PopulationGraphPanel();
        PopulationPanel simPanel = new PopulationPanel(graphPanel);  // pass graph in constructor
        ToolbarPanel toolbarPanel = new ToolbarPanel(simPanel);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(toolbarPanel, BorderLayout.NORTH);

        frame.setLayout(new BorderLayout());
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(simPanel, BorderLayout.CENTER);
        frame.add(graphPanel, BorderLayout.SOUTH);

        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
    }
}