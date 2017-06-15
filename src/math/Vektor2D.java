package math;

public class Vektor2D {
    public double x, y;

    public Vektor2D(double x, double y) {
        setPosition(x, y);
    }

    public Vektor2D() {
        this(0, 0);
    }


    public Vektor2D(Vektor2D a){
        this(a.x, a.y);
    }

    public void setPosition(double x, double y){
        if (Double.isInfinite(x) || Double.isInfinite(y)) {
            throw new ArithmeticException();
        } else {
            this.x = x;
            this.y = y;
        }
    }

    public boolean isNullVector() {
        return (this.x == 0 && this.y == 0);
    }

    public void add(Vektor2D b) {
        if (this.x >= Double.MAX_VALUE - b.x || this.y >= Double.MAX_VALUE - b.y
                || this.x <= (-Double.MAX_VALUE) - b.x || this.y <= (-Double.MAX_VALUE) - b.y) {
            throw new ArithmeticException();
        } else {
            this.x += b.x;
            this.y += b.y;
        }
    }

    public void sub(Vektor2D b) {
        Vektor2D tmp = new Vektor2D(-b.x, -b.y);
        this.add(tmp);
    }

    public void mult(double scalar) {
        double x = this.x * scalar;
        double y = this.y * scalar;

        if (Double.isInfinite(x) || Double.isInfinite(y)) {
            throw new ArithmeticException();
        } else {
            this.x = x;
            this.y = y;
        }
    }

    public void div(double divisor) {
        if (divisor == 0) {
            throw new ArithmeticException();
        }
        else {
            this.mult(1 / divisor);
        }
    }

    public boolean isEqual(Vektor2D b) {
        return (this.x == b.x && this.y == b.y);
    }

    public boolean isNotEqual(Vektor2D b) {
        return (!isEqual(b));
    }

    public double length() {
        double tmp = this.x * this.x + this.y * this.y;
        if (Double.isInfinite(tmp)) {
            throw new ArithmeticException();
        } else {
            return Math.sqrt(Math.abs(tmp));
        }
    }

    public void normalize() {
        this.div(this.length());
    }

    @Override
    public String toString() {
        return "x: " + this.x + "\ny: " + this.y + "\n";
    }

    public void rotate(double angle){
        double xTemp = this.x;
        double yTemp = this.y;
        this.x = xTemp * Math.cos(LineareAlgebra.degreeToRad(angle)) - yTemp * Math.sin(LineareAlgebra.degreeToRad(angle));
        this.y = xTemp * Math.sin(LineareAlgebra.degreeToRad(angle)) + yTemp * Math.cos(LineareAlgebra.degreeToRad(angle));
    }
}