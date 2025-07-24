import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PopulationPanel extends JPanel implements ActionListener {
    List<Creature> boids = new ArrayList<>();
    Timer timer;

    public PopulationPanel() {
        for (int i = 0; i < 100; i++) boids.add(new Person(Math.random() * 800, Math.random() * 600));
        timer = new Timer(16, this); // ~60fps
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Creature b : boids) {
            g.fillOval((int)b.position.x, (int)b.position.y, 5, 5);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Creature b : boids) {
            b.applyBehaviors(boids);
            b.update();
        }
        repaint();
    }
}