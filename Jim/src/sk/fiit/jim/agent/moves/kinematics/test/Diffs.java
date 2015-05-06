package sk.fiit.jim.agent.moves.kinematics.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class Diffs
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(new File("out_klby6_interval5.csv")));
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("left_leg_joint6_interval5_diffs.csv")));
        String line = null;
        while ((line = reader.readLine()) != null)
        {
            // System.out.println(line);
            // String[] data = line.replaceAll(",", ".").split(";");
            String[] data = line.split(",");
            double lle1 = Double.parseDouble(data[0]);
            double lle2 = Double.parseDouble(data[1]);
            double lle3 = Double.parseDouble(data[2]);
            double lle4 = Double.parseDouble(data[3]);
            double lle5 = Double.parseDouble(data[4]);
            double lle6 = Double.parseDouble(data[5]);
            if(data[12] != null && !data[12].isEmpty() && !data[12].matches("\\s+"))
            {
                double lle1_ik = Double.parseDouble(data[12]);
                double lle2_ik = Double.parseDouble(data[13]);
                double lle3_ik = Double.parseDouble(data[14]);
                double lle4_ik = Double.parseDouble(data[15]);
                double lle5_ik = Double.parseDouble(data[16]);
                double lle6_ik = Double.parseDouble(data[17]);

                double diff1 = Math.abs(lle1_ik - lle1);
                double diff2 = Math.abs(lle2_ik - lle2);
                double diff3 = Math.abs(lle3_ik - lle3);
                double diff4 = Math.abs(lle4_ik - lle4);
                double diff5 = Math.abs(lle5_ik - lle5);
                double diff6 = Math.abs(lle6_ik - lle6);

                StringBuilder newLine = new StringBuilder();
                newLine.append(String.format(Locale.GERMAN, "%.2f", diff1));
                newLine.append(";");
                newLine.append(String.format(Locale.GERMAN, "%.2f", diff2));
                newLine.append(";");
                newLine.append(String.format(Locale.GERMAN, "%.2f", diff3));
                newLine.append(";");
                newLine.append(String.format(Locale.GERMAN, "%.2f", diff4));
                newLine.append(";");
                newLine.append(String.format(Locale.GERMAN, "%.2f", diff5));
                newLine.append(";");
                newLine.append(String.format(Locale.GERMAN, "%.2f", diff6));
                newLine.append(";");

                writer.append(newLine.toString() + "\n");
                writer.flush();
            }
        }
        reader.close();
        writer.close();
    }
}
