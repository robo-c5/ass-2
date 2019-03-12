package mapping;

public class Bearing {
    private static final int ANGULAR_OFFSET = 90;
    private static final int MAX_DEGREES = 360;
    private int intRep;
    //with respect to the (0,0) to (0,1) direction ("North"). In degrees
    private int angle;

    public Bearing(int index) {
        intRep = index;
        angle = minimiseAngle(ANGULAR_OFFSET*(index-1));
    }

    //no point turning 270 degrees where we could turn -90. Probably not necessary thanks to navigation but may be handy
    public static int minimiseAngle(int angle) {
        if (angle > MAX_DEGREES/2)
            angle -= MAX_DEGREES;
        return angle;
    }

    public int getIntRep() {
        return intRep;
    }

    public int getAngle() {
        return angle;
    }
}
