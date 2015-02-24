package sk.fiit.robocup.library.math;

import java.text.NumberFormat;

import sk.fiit.robocup.library.geometry.Vector3;

public class TransformationMatrix {

	private double[] values;

	private static TransformationMatrix identity = new TransformationMatrix(
			new double[] { 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 });

	public TransformationMatrix(double[] values) {
		this.values = values;
	}

	public double[] getValues() {
		return values;
	}
	
	public String toString() {
		String s = "[";
		int i = 0;
		for (double v : values) {
			i++;
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(3);
			s = s + nf.format(v) + ", ";
			if (i%4 == 0) s += " ;; ";
		}
		s += "]";
		return s;
	}
	
	/**
	 * Compares matrix with another matrix.
	 * 
	 * @author xsuchac
	 * @author A55-Kickers
	 * 
	 * @param matrix
	 * @return true if values of both matrices are equal, else false.
	 */
	public boolean compareWith(TransformationMatrix matrix) {
		boolean success = true;
		double[] values2 = matrix.getValues();
		for (int i = 0; i < values.length; i++) {
			if (values[i] != values2[i]) {
				success = false;
			}
		}
		return success;
	}

	public TransformationMatrix multiply(TransformationMatrix matrix) {
		double[] newValues = new double[16];
		double[] matrixBValues = matrix.getValues();
		for (int i = 0; i < 16; i += 4) {
			for (int j = 0; j < 4; j++) {
				newValues[i + j] = values[i] * matrixBValues[j] + values[i + 1]
						* matrixBValues[4 + j] + values[i + 2]
						* matrixBValues[8 + j] + values[i + 3]
						* matrixBValues[12 + j];
			}
		}
		return new TransformationMatrix(newValues);
	}
	
	public Vector3 getTranslation()
    {
        return new Vector3(values[12], values[13], values[14]);
    }
	
	public Vector3 getRotation()
	{
		double rotX= Math.atan2(values[9], values[10]);
		double rotY = Math.asin(values[8])*-1;
		double rotZ = Math.atan2(values[4], values[0]);
		
		return new Vector3(rotX,rotY,rotZ);
		
	}

	public static TransformationMatrix getIndetity() 
	{
		return identity;
	}
}
