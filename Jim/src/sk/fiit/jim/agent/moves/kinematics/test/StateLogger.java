package sk.fiit.jim.agent.moves.kinematics.test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class StateLogger
{
    private static StateLogger INSTANCE;

    private static BufferedWriter bw;

    private StateLogger(String name)
    {
        try
        {
            bw = new BufferedWriter(new FileWriter(name));
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @see String#format(Locale, String, Object...)
     */
    public void log(Locale locale, String format, Object... params)
    {
        String line = String.format(locale, format, params);
        try
        {
            bw.write(line);
            bw.flush();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @see String#format(String, Object...)
     */
    public void log(String format, Object... params)
    {
        log(Locale.getDefault(), format, params);
    }

    public static StateLogger getInstance(String name)
    {
        if(INSTANCE == null)
        {
            INSTANCE = new StateLogger(name);
        }
        return INSTANCE;
    }

    @Override
    protected void finalize() throws Throwable
    {
        bw.close();
        super.finalize();
    }
}
