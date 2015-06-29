package bno.swing2;

public class Angle {

	public static double degreeToRadians(int deg) {
		return deg * Math.PI / 180.0;
	}

	public static double radiansToDegree(int rad) {
		return rad * 180.0 / Math.PI;
	}

}
