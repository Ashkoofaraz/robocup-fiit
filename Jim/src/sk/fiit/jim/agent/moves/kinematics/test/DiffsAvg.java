package sk.fiit.jim.agent.moves.kinematics.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

public class DiffsAvg
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(new File("out_klby4_interval5_diffs.csv")));
        int lines = 0;
        String line = null;
        int lle1Sum = 0;
        int lle2Sum = 0;
        int lle3Sum = 0;
        int lle4Sum = 0;
        int lle5Sum = 0;
        int lle6Sum = 0;
        
        int maxLle1diff = Integer.MIN_VALUE;
        int maxLle2diff = Integer.MIN_VALUE;
        int maxLle3diff = Integer.MIN_VALUE;
        int maxLle4diff = Integer.MIN_VALUE;
        int maxLle5diff = Integer.MIN_VALUE;
        int maxLle6diff = Integer.MIN_VALUE;
        while((line = reader.readLine()) != null)
        {
            System.out.println(line);
            String[] data = line.split(";");
            int lle1 = Integer.parseInt(data[0]);
            int lle2 = Integer.parseInt(data[1]);
            int lle3 = Integer.parseInt(data[2]);
            int lle4 = Integer.parseInt(data[3]);
            int lle5 = Integer.parseInt(data[4]);
            int lle6 = Integer.parseInt(data[5]);
            lines++;
            
            if(lle1 > maxLle1diff)
            {
                maxLle1diff = lle1;
            }
            if(lle2 > maxLle2diff)
            {
                maxLle2diff = lle2;
            }
            if(lle3 > maxLle3diff)
            {
                maxLle3diff = lle3;
            }
            if(lle4 > maxLle4diff)
            {
                maxLle4diff = lle4;
            }
            if(lle4 > maxLle5diff)
            {
                maxLle5diff = lle5;
            }
            
            if(lle6 > maxLle6diff)
            {
                maxLle6diff = lle6;
            }
            
            lle1Sum += lle1;
            lle2Sum += lle2;
            lle3Sum += lle3;
            lle4Sum += lle4;
            lle5Sum += lle5;
            lle6Sum += lle6;
        }
        System.out.println("avg lle1 diff: " + String.format(Locale.GERMAN, "%.3f", (double)lle1Sum / lines));
        System.out.println("avg lle2 diff: " + String.format(Locale.GERMAN, "%.3f", (double)lle2Sum / lines));
        System.out.println("avg lle3 diff: " + String.format(Locale.GERMAN, "%.3f", (double)lle3Sum / lines));
        System.out.println("avg lle4 diff: " + String.format(Locale.GERMAN, "%.3f", (double)lle4Sum / lines));
        System.out.println("avg lle5 diff: " + String.format(Locale.GERMAN, "%.3f", (double)lle5Sum / lines));
        System.out.println("avg lle6 diff: " + String.format(Locale.GERMAN, "%.3f", (double)lle6Sum / lines));
        
        System.out.println("max lle1 diff: " + maxLle1diff);
        System.out.println("max lle2 diff: " + maxLle2diff);
        System.out.println("max lle3 diff: " + maxLle3diff);
        System.out.println("max lle4 diff: " + maxLle4diff);
        System.out.println("max lle5 diff: " + maxLle5diff);
        System.out.println("max lle6 diff: " + maxLle6diff);
        
        reader.close();
    }
}
