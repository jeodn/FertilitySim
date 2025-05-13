import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class BoidPanel extends JPanel implements ActionListener {
    List<Boid> boids = new ArrayList<>();
    Timer timer;

    public BoidPanel() {
        for (int i = 0; i < 100; i++) boids.add(new Boid(Math.random() * 800, Math.random() * 600));
        timer = new Timer(16, this); // ~60fps
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Boid b : boids) {
            g.fillOval((int)b.position.x, (int)b.position.y, 5, 5);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Boid b : boids) {
            b.applyBehaviors(boids);
            b.update();
        }
        repaint();
    }
}