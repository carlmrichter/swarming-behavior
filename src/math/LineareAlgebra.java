package math;

public final class LineareAlgebra {

    // private constructor because of static-only member functions
    private LineareAlgebra(){}

    //ADD/////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // add Vektor2D
    public static Vektor2D add(Vektor2D v1, Vektor2D v2) {
        Vektor2D tmp = new Vektor2D(v1);
        tmp.add(v2);
        return tmp;
    }

    // add Vektor3D
    public static Vektor3D add(Vektor3D v1, Vektor3D v2) {
        Vektor3D tmp = new Vektor3D(v1);
        tmp.add(v2);
        return tmp;
    }

    //SUB/////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // sub Vektor2D
    public static Vektor2D sub(Vektor2D v1, Vektor2D v2) {
        Vektor2D tmp = new Vektor2D(v1);
        tmp.sub(v2);
        return tmp;
    }

    // sub Vektor3D
    public static Vektor3D sub(Vektor3D v1, Vektor3D v2) {
        Vektor3D tmp = new Vektor3D(v1);
        tmp.sub(v2);
        return tmp;
    }

    //MULT////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // mult Vektor2D
    public static Vektor2D mult(Vektor2D v, double scalar) {
        Vektor2D tmp = new Vektor2D(v);
        tmp.mult(scalar);
        return tmp;
    }

    // mult Vektor3D
    public static Vektor3D mult(Vektor3D v, double scalar) {
        Vektor3D tmp = new Vektor3D(v);
        tmp.mult(scalar);
        return tmp;
    }

    //DIV//////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static Vektor2D div(Vektor2D v, double divisor) {
        Vektor2D tmp = new Vektor2D(v);
        tmp.div(divisor);
        return tmp;
    }

    // div Vector3D
    public static Vektor3D div(Vektor3D v, double divisor) {
        Vektor3D tmp = new Vektor3D(v);
        tmp.div(divisor);
        return tmp;
    }

    //isEqual / isNotEqual/////////////////////////////////////////////////////////////////////////////////////////////

    public static boolean isEqual(Vektor2D v1, Vektor2D v2) {
        return (v1.x == v2.x && v1.y == v2.y);
    }

    public static boolean isEqual(Vektor3D v1, Vektor3D v2) {
        return (v1.x == v2.x && v1.y == v2.y && v1.z == v2.z);
    }

    public static boolean isNotEqual(Vektor2D v1, Vektor2D v2) {
        return (!isEqual(v1, v2));
    }

    public static boolean isNotEqual(Vektor3D v1, Vektor3D v2) {
        return !isEqual(v1, v2);
    }

    //LENGTH///////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static double length(Vektor2D v) {
        return v.length();
    }

    public static double length(Vektor3D v) {
        return v.length();
    }

    //NORMALIZE////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static Vektor2D normalize(Vektor2D v) {
        Vektor2D tmp = new Vektor2D(v);
        tmp.normalize();
        return tmp;
    }

    public static Vektor3D normalize(Vektor3D v) {
        Vektor3D tmp = new Vektor3D(v);
        tmp.normalize();
        return tmp;
    }

    //euklDistance/////////////////////////////////////////////////////////////////////////////////////////////////////

    public static double euklDistance(Vektor2D v1, Vektor2D v2) {
        return sub(v1, v2).length();
    }

    public static double euklDistance(Vektor3D v1, Vektor3D v2) {
        return sub(v1, v2).length();
    }

    //manhattanDistance////////////////////////////////////////////////////////////////////////////////////////////////

    public static double manhattanDistance(Vektor2D v1, Vektor2D v2){
        Vektor2D tmp = abs(sub(v1, v2));
        double erg = tmp.x + tmp.y;
        if (Double.isInfinite(erg)) {
            throw new ArithmeticException();
        } else {
            return erg;
        }
    }

    public static double manhattanDistance(Vektor3D v1, Vektor3D v2) {
        Vektor3D tmp = abs(sub(v1, v2));
        double erg = tmp.x + tmp.y + tmp.z;
        if (Double.isInfinite(erg)) {
            throw new ArithmeticException();
        } else {
            return erg;
        }
    }

    //crossProduct/////////////////////////////////////////////////////////////////////////////////////////////////////

    // Fehlermeldung, wenn bei der Subtraktion bzw. Multiplikation der einzelnen Werte ein Infiniter Wert heraus kommt
    // (mithilfe von Double.isInfinite(value))

    public static double crossProduct (Vektor2D v1, Vektor2D v2){
        double erg = v1.x * v2.y - v1.y * v2.x;
        if (Double.isInfinite(erg)) {
            throw new ArithmeticException();
        } else {
            return erg;
        }
    }

    public static Vektor3D crossProduct(Vektor3D v1, Vektor3D v2) {
        double x = v1.y * v2.z - v1.z * v2.y;
        double y = v1.z * v2.x - v1.x * v2.z;
        double z = v1.x * v2.y - v1.y * v2.x;

        if (Double.isInfinite(x) || Double.isInfinite(y) || Double.isInfinite(z)) {
            throw new ArithmeticException();
        } else {
            return new Vektor3D(x, y, z);
        }
    }

    //dotProduct///////////////////////////////////////////////////////////////////////////////////////////////////////

    public static double dotProduct(Vektor2D v1, Vektor2D v2){
        double tmp = v1.x * v2.x + v1.y * v2.y;
        if (Double.isInfinite(tmp)) {
            throw new ArithmeticException();
        } else {
            return tmp;
        }
    }

    public static double dotProduct(Vektor3D v1, Vektor3D v2) {
        double tmp = v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
        if (Double.isInfinite(tmp)) {
            throw new ArithmeticException();
        } else {
            return tmp;
        }
    }

    //cosEquation//////////////////////////////////////////////////////////////////////////////////////////////////////

    public static double cosEquation(Vektor2D v1, Vektor2D v2){
        if (v1.isNullVector() || v2.isNullVector()) {
            throw new ArithmeticException();
        } else {
            return radToDegree(Math.acos(dotProduct(v1, v2) / (length(v1) * length(v2))));
        }

    }

    public static double cosEquation(Vektor3D v1, Vektor3D v2) {
        if (v1.isNullVector() || v2.isNullVector()) {
            throw new ArithmeticException();
        } else {
            return radToDegree(Math.acos(dotProduct(v1, v2) / (length(v1) * length(v2))));
        }
    }

    //sinEquation//////////////////////////////////////////////////////////////////////////////////////////////////////

    public static double sinEquation(Vektor2D v1, Vektor2D v2) {
        if (v1.isNullVector() || v2.isNullVector()) {
            throw new ArithmeticException();
        } else {
            return radToDegree(Math.asin((crossProduct(v1, v2)) / (length(v1) * length(v2))));
        }
    }

    public static double sinEquation(Vektor3D v1, Vektor3D v2) {
        if (v1.isNullVector() || v2.isNullVector()) {
            throw new ArithmeticException();
        } else {
            return radToDegree(Math.asin(length(crossProduct(v1, v2)) / (length(v1) * length(v2))));
        }
    }

    //angleRad/////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static double angleRad(Vektor2D v, String axis) {
        return degreeToRad(angleDegree(v, axis));
    }

    public static double angleRad(Vektor3D v, String axis) {
        return degreeToRad(angleDegree(v, axis));
    }

    //angleDegree//////////////////////////////////////////////////////////////////////////////////////////////////////

    public static double angleDegree(Vektor2D v, String axis) {
        switch (axis) {
            case "x":
            case "x1":
                return cosEquation(v, new Vektor2D(1, 0));
            case "y":
            case "x2":
                return cosEquation(v, new Vektor2D(0, 1));
            default:
                throw new IllegalArgumentException(axis + " is not a legal axis");
        }
    }

    public static double angleDegree(Vektor3D v, String axis) {
        switch (axis) {
            case "x":
            case "x1":
                return cosEquation(v, new Vektor3D(1, 0, 0));
            case "y":
            case "x2":
                return cosEquation(v, new Vektor3D(0, 1, 0));
            case "z":
            case "x3":
                return cosEquation(v, new Vektor3D(0, 0, 1));
            default:
                throw new IllegalArgumentException(axis + " is not a legal axis");
        }
    }

    //radToDegree//////////////////////////////////////////////////////////////////////////////////////////////////////

    public static double radToDegree(double value){
        return 180.0 / Math.PI * value;
    }

    //degreeToRad//////////////////////////////////////////////////////////////////////////////////////////////////////

    public static double degreeToRad(double value){
        return Math.PI / 180.0 * value;
    }

    //determinante //////////////////////////////////////////////////////////////////////////////////////////////////////

    public static double determinante(Vektor2D v1, Vektor2D v2) {
        return crossProduct(v1, v2);
    }

    public static double determinante(Vektor3D a, Vektor3D b, Vektor3D c) {
        double tmp = (a.x * b.y * c.z) + (a.y * b.z * c.x) + (a.z * b.x * c.y) - (c.x * b.y * a.z) - (c.y * b.z * a.x) - (c.z * b.x * a.y);
        if (Double.isInfinite(tmp) || Double.isNaN(tmp)) {
            throw new ArithmeticException();
        } else {
            return tmp;
        }
    }

    //abs//////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static Vektor2D abs(Vektor2D v){
        return new Vektor2D(Math.abs(v.x), Math.abs(v.y));
    }

    public static Vektor3D abs(Vektor3D v){
        return new Vektor3D(Math.abs(v.x), Math.abs(v.y), Math.abs(v.z));
    }

    //show/////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void show(Vektor2D v){
        System.out.println(v.toString());
    }

    public static void show(Vektor3D v){
        System.out.println(v.toString());
    }

}
