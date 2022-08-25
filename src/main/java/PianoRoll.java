import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PianoRoll {

    public static void main(String args[]) {
        JFrame piano = new JFrame("PianoRoll");
        Panel panel = new Panel();
        ArrayList<Button> keys = new ArrayList<>();

        String[] notes = {"C", "C#", "D", "D#", "E", "F","G", "G#" , "A", "A#", "B"};

        int ocatve = 4;

        for(int i = 0; i < notes.length; i++) {
            keys.add(new Button(notes[i] + " " + ocatve));
            ActionListener actionListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(e.getActionCommand());
                }
            };
            keys.get(i).addActionListener(actionListener);
            panel.add(keys.get(i));
        }
        piano.add(panel);
        piano.setSize(500,200);
        piano.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        piano.setVisible(true);
    }

}
