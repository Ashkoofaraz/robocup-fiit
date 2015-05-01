package sk.fiit.jim.agent.moves.kinematics.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Diffs
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(new File("out_klby4_interval5.csv")));
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("out_klby4_interval5_diffs.csv")));
        String line = null;
        while((line = reader.readLine()) != null)
        {
            System.out.println(line);
            String[] data = line.split(",");
            int lle1 = Integer.parseInt(data[0]);
            int lle2 = Integer.parseInt(data[1]);
            int lle3 = Integer.parseInt(data[2]);
            int lle4 = Integer.parseInt(data[3]);
            int lle5 = Integer.parseInt(data[4]);
            int lle6 = Integer.parseInt(data[5]);
            
            
            int lle1_ik = Integer.parseInt(data[12]);
            int lle2_ik = Integer.parseInt(data[13]);
            int lle3_ik = Integer.parseInt(data[14]);
            int lle4_ik = Integer.parseInt(data[15]);
            int lle5_ik = Integer.parseInt(data[16]);
            int lle6_ik = Integer.parseInt(data[17]);
            
            int diff1 = Math.abs(lle1_ik - lle1);
            int diff2 = Math.abs(lle2_ik - lle2);
            int diff3 = Math.abs(lle3_ik - lle3);
            int diff4 = Math.abs(lle4_ik - lle4);
            int diff5 = Math.abs(lle5_ik - lle5);
            int diff6 = Math.abs(lle6_ik - lle6);
            
            StringBuilder newLine = new StringBuilder();
            newLine.append(diff1);
            newLine.append(";");
            newLine.append(diff2);
            newLine.append(";");
            newLine.append(diff3);
            newLine.append(";");
            newLine.append(diff4);
            newLine.append(";");
            newLine.append(diff5);
            newLine.append(";");
            newLine.append(diff6);
            newLine.append(";");
            
            writer.append(newLine.toString() + "\n");
            writer.flush();
        }
        reader.close();
        writer.close();
    }
}
