
import java.nio.ByteBuffer;
import javax.sound.sampled.*;


public class ToneGenerator {
    final int SAMPLING_RATE = 44100;            // Audio sampling rate
    final int SAMPLE_SIZE = 2;
    //Open up audio output, using 44100hz sampling rate, 16 bit samples, mono, and big
    // endian byte ordering
    final AudioFormat format = new AudioFormat(SAMPLING_RATE, 16, 1, true, true);
    final DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
    SourceDataLine line;

    private double frequency;
    private double harmonics;
    private double filter;
    private double voices;

    public ToneGenerator() throws LineUnavailableException {
        if (!AudioSystem.isLineSupported(info)) {
            System.out.println("Line matching " + info + " is not supported.");
            throw new LineUnavailableException();
        }
    }

    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public void stop() {
        this.line.stop();
    }

    public double sineWave(double fCyclePosition) {
       return Math.sin(2 * Math.PI * fCyclePosition);
    }
    public double triangleWave(double fCyclePosition) {
        return 2 / Math.PI * Math.asin(Math.sin(2 * Math.PI * fCyclePosition));
    }

    public double sawToothWave(double fCyclePosition) {
        return -2 / Math.PI * Math.atan(1 / Math.tan(2 * Math.PI * fCyclePosition));
    }


    //This is just an example - you would want to handle LineUnavailable properly...
    public void generateTone() throws InterruptedException, LineUnavailableException {
        // Audio sample size in bytes
        this.line = (SourceDataLine) AudioSystem.getLine(info);
        //double fFreq = 440;                         // Frequency of sine wave in hz

        //Position through the sine wave as a percentage (i.e. 0 to 1 is 0 to 2*PI)
        double fCyclePosition = 0;

        this.line = (SourceDataLine) AudioSystem.getLine(info);
        this.line.open(format);
        this.line.start();

        // Make our buffer size match audio system's buffer
        ByteBuffer cBuf = ByteBuffer.allocate(this.line.getBufferSize());

        int ctSamplesTotal = SAMPLING_RATE * 5;         // Output for roughly 5 seconds


        //On each pass main loop fills the available free space in the audio buffer
        //Main loop creates audio samples for sine wave, runs until we tell the thread to exit
        //Each sample is spaced 1/SAMPLING_RATE apart in time
        while (ctSamplesTotal > 0) {
            double fCycleInc = this.frequency / SAMPLING_RATE;  // Fraction of cycle between samples

            cBuf.clear();                            // Discard samples from previous pass

            // Figure out how many samples we can add
            int ctSamplesThisPass = line.available() / SAMPLE_SIZE;
            for (int i = 0; i < ctSamplesThisPass; i++) {
                cBuf.putShort((short) (Short.MAX_VALUE * sineWave(fCyclePosition)));

                fCyclePosition += fCycleInc;
                if (fCyclePosition > 1)
                    fCyclePosition -= 1;
            }

            //Write sine samples to the line buffer.  If the audio buffer is full, this will
            // block until there is room (we never write more samples than buffer will hold)
            line.write(cBuf.array(), 0, cBuf.position());
            ctSamplesTotal -= ctSamplesThisPass;     // Update total number of samples written

            //Wait until the buffer is at least half empty  before we add more
            while (line.getBufferSize() / 2 < line.available())
                Thread.sleep(1);
        }


        //Done playing the whole waveform, now wait until the queued samples finish
        //playing, then clean up and exit
        line.drain();
        line.close();
    }

}
