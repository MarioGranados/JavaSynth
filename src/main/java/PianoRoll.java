import java.lang.reflect.Array;
import java.util.ArrayList;

public class PianoRoll {
    protected final int keys = 7;

    private String sharp;
    private String natural;
    private String middleC;

    public void generateKeys() {
        ArrayList<String> keys = new ArrayList();
        int octave = 0;
        for (int i = 0; i < this.keys; i++) {
            switch (octave) {
                case 0:
                    keys.add("do");
                    break;
                case 1:
                    keys.add("re");
                    break;
                case 2:
                    keys.add("mi");
                    break;
                case 3:
                    keys.add("fa");
                    break;
                case 4:
                    keys.add("so");
                    break;
                case 5:
                    keys.add("la");
                    break;
                case 6:
                    keys.add("si");
                    break;
            }
            if (octave == 7) {
                octave = 0;
            } else {
                octave++;
            }

        }
        System.out.println(keys);
    }

}
