import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class PopulationPanel extends JPanel implements ActionListener {
    private final PopulationGraphPanel graphPanel;
    List<Creature> population = new ArrayList<>();
    Timer timer;
    double deathRatePerCapita = 6.9 / 1000; // calculated from per 1000
    double birthRatePerCapita = 4.5 / 1000; // '""'
    double birthRate = 0.78; // per woman

    public PopulationPanel(PopulationGraphPanel graphPanel) {
        this.graphPanel = graphPanel;
        for (int i = 0; i < 100; i++) addToPopulation();
        timer = new Timer(16, this); // ~60fps
        timer.start();
    }

    private void addToPopulation() {
        // random version
        addToPopulation(Math.random() * Constants.SCREEN_WIDTH, Math.random() * Constants.SCREEN_HEIGHT);
    }

    private void addToPopulation(double x, double y) {
        population.add(new Person(x, y));
    }

    private void updatePopulation() {
        int currentPopulation = population.size();
        // iterate over each person and decide whether to give new baby or kill

        Random random = new Random();

        // Use iterator to safely remove elements while iterating
        Iterator<Creature> iterator = population.iterator();

        for (int i = 0; i < population.size(); i++) {
            Creature parent = population.get(i);

            // birthrate  probability with random spawn
            if (random.nextDouble() < birthRatePerCapita) {
                double locationRandomnessConstX = Math.random() * 50;
                double locationRandomnessConstY = Math.random() * 50;
                addToPopulation(
                        locationRandomnessConstX + (double) Constants.SCREEN_WIDTH / 2,
                        locationRandomnessConstY + (double) Constants.SCREEN_HEIGHT / 2);
            }
        }

        iterator = population.iterator();
        while (iterator.hasNext()) {
            Creature person = iterator.next();
            if (random.nextDouble() < deathRatePerCapita) {
                iterator.remove(); // person "dies"
            }
        }

    }

    public double getBirthRatePerCapita() {
        return birthRatePerCapita;
    }
    public double getDeathRatePerCapita() {
        return deathRatePerCapita;
    }

    public void resetSimulation(double newBirthRate, double newDeathRate) {
        this.birthRatePerCapita = newBirthRate;
        this.deathRatePerCapita = newDeathRate;
        this.population.clear();
        for (int i = 0; i < 100; i++) addToPopulation();
        graphPanel.reset(); // if needed
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Creature b : population) {
            g.fillOval((int)b.position.x, (int)b.position.y, 5, 5);
        }

        // Draw population count in top-left corner
        g.setColor(Color.BLACK);
        g.setFont(new Font("SansSerif", Font.BOLD, 14));
        g.drawString("Population: " + population.size(), 10, 20);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Creature b : population) {
            b.applyBehaviors(population);
            b.update();
        }
        updatePopulation();

        // Notify graph
        graphPanel.addPopulationData(population.size());

        repaint();
    }
}