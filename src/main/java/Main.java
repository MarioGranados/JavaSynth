
import org.w3c.dom.Text;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) throws LineUnavailableException, InterruptedException {

        ToneGenerator toneGenerator = new ToneGenerator();
        toneGenerator.setFrequency(440);

        JFrame frame = new JFrame("Tone Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(500,500);
        JButton button = new JButton("Start");
        JSlider slider = new JSlider(0, 1000, 440 );
        JButton button1 = new JButton("Stop");
        TextArea textField = new TextArea();
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setPaintTrack(true);

        // set spacing
        slider.setMajorTickSpacing(500);
        slider.setMinorTickSpacing(100);

        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int frequency = slider.getValue();
                toneGenerator.setFrequency(frequency);
                textField.setText(slider.getValue() + "Hz");
            }
        };
        slider.addChangeListener(changeListener);

        ActionListener stopTone = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toneGenerator.stop();
            }
        };

        ActionListener playTone = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    toneGenerator.generateTone();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } catch (LineUnavailableException ex) {
                    ex.printStackTrace();
                }
            }
        };

        button.addActionListener(playTone);

        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(button); // Adds Button to content pane of frame
        frame.getContentPane().add(slider);
        frame.getContentPane().add(textField);
        frame.setVisible(true);


    }
}
