package sk.fiit.jim.agent.moves.kinematics.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import sk.fiit.jim.agent.moves.kinematics.Orientation;
import sk.fiit.robocup.library.geometry.Point3D;


class CsvOutput
{
    static final class JointAngles {
        final double theta1;
        final double theta2;
        final double theta3;
        final double theta4;
        final double theta5;
        final double theta6;
        
        JointAngles(double theta1, double theta2, double theta3, double theta4, double theta5, double theta6)
        {
            this.theta1 = theta1;
            this.theta2 = theta2;
            this.theta3 = theta3;
            this.theta4 = theta4;
            this.theta5 = theta5;
            this.theta6 = theta6;
        }
    }
    
    private BufferedWriter out;
    
    
    private CsvOutput(String file)
    {
        try
        {
            this.out = new BufferedWriter(new FileWriter(new File(file)));
        }
        catch (IOException e)
        {
            out = null;
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public static CsvOutput getInstance(String filePath) {
        return new CsvOutput(filePath);
    }
    
    public void addJointAngles(JointAngles original, Point3D origEndpoint, Orientation origOrientation, JointAngles ikAngles, Point3D ikEndpoint, Orientation ikOrientation)
    {
        String originalTheta1 = String.format(Locale.ENGLISH, "%d", original.theta1);
        String originalTheta2 = String.format(Locale.ENGLISH, "%d", original.theta2);
        String originalTheta3 = String.format(Locale.ENGLISH, "%d", original.theta3);
        String originalTheta4 = String.format(Locale.ENGLISH, "%d", original.theta4);
        String originalTheta5 = String.format(Locale.ENGLISH, "%d", original.theta5);
        String originalTheta6 = String.format(Locale.ENGLISH, "%d", original.theta6);
        String originalX = String.format(Locale.ENGLISH, "%.2f", origEndpoint.x);
        String originalY = String.format(Locale.ENGLISH, "%.2f", origEndpoint.y);
        String originalZ = String.format(Locale.ENGLISH, "%.2f", origEndpoint.z);
        String originalAx = String.format(Locale.ENGLISH, "%.2f", origOrientation.getAxRadians());
        String originalAy = String.format(Locale.ENGLISH, "%.2f", origOrientation.getAyRadians());
        String originalAz = String.format(Locale.ENGLISH, "%.2f", origOrientation.getAzRadians());
        
        String ikTheta1 = String.format(Locale.ENGLISH, "%d", ikAngles.theta1);
        String ikTheta2 = String.format(Locale.ENGLISH, "%d", ikAngles.theta2);
        String ikTheta3 = String.format(Locale.ENGLISH, "%d", ikAngles.theta3);
        String ikTheta4 = String.format(Locale.ENGLISH, "%d", ikAngles.theta4);
        String ikTheta5 = String.format(Locale.ENGLISH, "%d", ikAngles.theta5);
        String ikTheta6 = String.format(Locale.ENGLISH, "%d", ikAngles.theta6);
        String ikX = String.format(Locale.ENGLISH, "%.2f", ikEndpoint.x);
        String ikY = String.format(Locale.ENGLISH, "%.2f", ikEndpoint.y);
        String ikZ = String.format(Locale.ENGLISH, "%.2f", ikEndpoint.z);
        String ikAx = String.format(Locale.ENGLISH, "%.2f", ikOrientation.getAxRadians());
        String ikAy = String.format(Locale.ENGLISH, "%.2f", ikOrientation.getAyRadians());
        String ikAz = String.format(Locale.ENGLISH, "%.2f", ikOrientation.getAzRadians());
        
        StringBuilder outLine = new StringBuilder();
        outLine.append(originalTheta1);
        outLine.append(",");
        outLine.append(originalTheta2);
        outLine.append(",");
        outLine.append(originalTheta3);
        outLine.append(",");
        outLine.append(originalTheta4);
        outLine.append(",");
        outLine.append(originalTheta5);
        outLine.append(",");
        outLine.append(originalTheta6);
        outLine.append(",");
        
        outLine.append(originalX);
        outLine.append(",");
        outLine.append(originalY);
        outLine.append(",");
        outLine.append(originalZ);
        outLine.append(",");
        
        outLine.append(originalAx);
        outLine.append(",");
        outLine.append(originalAy);
        outLine.append(",");
        outLine.append(originalAz);
        outLine.append(",");
        
        outLine.append(ikTheta1);
        outLine.append(",");
        outLine.append(ikTheta2);
        outLine.append(",");
        outLine.append(ikTheta3);
        outLine.append(",");
        outLine.append(ikTheta4);
        outLine.append(",");
        outLine.append(ikTheta5);
        outLine.append(",");
        outLine.append(ikTheta6);
        outLine.append(",");
        
        outLine.append(ikX);
        outLine.append(",");
        outLine.append(ikY);
        outLine.append(",");
        outLine.append(ikZ);
        outLine.append(",");
        
        outLine.append(ikAx);
        outLine.append(",");
        outLine.append(ikAy);
        outLine.append(",");
        outLine.append(ikAz);
        outLine.append(",");
        
        try
        {
            out.write(outLine.toString() + "\n");
            out.flush();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void save()
    {
        if(out != null)
        {
            try
            {
                out.close();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
}
