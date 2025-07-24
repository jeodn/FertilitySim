import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class PopulationPanel extends JPanel implements ActionListener {
    List<Creature> population = new ArrayList<>();
    Timer timer;
    double deathRatePerCapita = 6.9 / 1000; // calculated from per 1000
    double birthRatePerCapita = 4.5 / 1000; // '""'
    double birthRate = 0.78; // per woman

    public PopulationPanel() {
        for (int i = 0; i < 100; i++) addToPopulation();
        timer = new Timer(16, this); // ~60fps
        timer.start();
    }

    private void addToPopulation() {
        population.add(new Person(Math.random() * Constants.SCREEN_WIDTH, Math.random() * Constants.SCREEN_HEIGHT));
    }

    private void updatePopulation() {
        int currentPopulation = population.size();
        // iterate over each person and decide whether to give new baby or kill

        Random random = new Random();

        // Use iterator to safely remove elements while iterating
        Iterator<Creature> iterator = population.iterator();

        for (int i = 0; i < population.size(); i++) {
            Creature parent = population.get(i);

            // birthrate  probability
            if (random.nextDouble() < birthRatePerCapita) {
                addToPopulation();
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Creature b : population) {
            g.fillOval((int)b.position.x, (int)b.position.y, 5, 5);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Creature b : population) {
            b.applyBehaviors(population);
            b.update();
        }
        updatePopulation();
        repaint();
    }
}