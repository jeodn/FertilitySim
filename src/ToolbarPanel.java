import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolbarPanel extends JPanel {
    private final JTextField birthRateField = new JTextField(5);
    private final JTextField deathRateField = new JTextField(5);
    private final JButton resetButton = new JButton("Reset");

    public ToolbarPanel(PopulationPanel simPanel) {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        add(new JLabel("Birth Rate (per 1000):"));
        add(birthRateField);
        birthRateField.setText(String.format("%.2f", simPanel.getBirthRatePerCapita() * 1000));

        add(new JLabel("Death Rate (per 1000):"));
        add(deathRateField);
        deathRateField.setText(String.format("%.2f", simPanel.getDeathRatePerCapita() * 1000));

        add(resetButton);

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double birthRate = Double.parseDouble(birthRateField.getText()) / 1000.0;
                    double deathRate = Double.parseDouble(deathRateField.getText()) / 1000.0;
                    simPanel.resetSimulation(birthRate, deathRate);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                            ToolbarPanel.this,
                            "Invalid input. Please enter numbers for rates.",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
    }
}
