import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PopulationGraphPanel extends JPanel {
    private final List<Integer> populationHistory = new ArrayList<>();

    public PopulationGraphPanel() {
        setPreferredSize(new Dimension(0, 200)); // height = 200px, width fills container
    }

    public void addPopulationData(int population) {
        int MAX_DATA_SIZE = 10000;

        populationHistory.add(population);
        if (populationHistory.size() > MAX_DATA_SIZE) {
            populationHistory.remove(0); // trim for performance
        }
        repaint();
    }

    public void reset() {
        populationHistory.clear();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (populationHistory.size() < 2) return;

        int width = getWidth();
        int height = getHeight();

        int maxPopulation = Collections.max(populationHistory);
        int minPopulation = Collections.min(populationHistory);

        int range = Math.max(1, maxPopulation - minPopulation);
        int points = populationHistory.size();

        for (int i = 1; i < points; i++) {
            int x1 = (i - 1) * width / points;
            int x2 = i * width / points;

            int y1 = height - (populationHistory.get(i - 1) - minPopulation) * height / range;
            int y2 = height - (populationHistory.get(i) - minPopulation) * height / range;

            g.setColor(Color.BLUE);
            g.drawLine(x1, y1, x2, y2);
        }

        g.setColor(Color.BLACK);
        g.drawString("Population History", 10, 15);
    }
}
