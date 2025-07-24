import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import java.awt.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try {
            // Set a FlatLaf theme
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Population Fertility Simulator");
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