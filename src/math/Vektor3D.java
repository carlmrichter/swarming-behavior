package math;

public class Vektor3D {
    public double x, y, z;

    public Vektor3D(double x, double y, double z) {
        setPosition(x, y, z);
    }

    public Vektor3D() {
        this(0, 0, 0);
    }

    public Vektor3D(Vektor3D a) {
        this(a.x, a.y, a.z);
    }

    public void setPosition(double x, double y, double z) {
        if (Double.isInfinite(x) || Double.isInfinite(y) || Double.isInfinite(z)) {
            throw new ArithmeticException();
        } else {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public boolean isNullVector() {
        return (this.x == 0 && this.y == 0 && this.z == 0);
    }

    public void add(Vektor3D b) {
        if (this.x >= Double.MAX_VALUE - b.x || this.y >= Double.MAX_VALUE - b.y || this.z >= Double.MAX_VALUE - b.z
                || this.x <= (-Double.MAX_VALUE) - b.x || this.y <= (-Double.MAX_VALUE) - b.y || this.z <= (-Double.MAX_VALUE) - b.z) {
            throw new ArithmeticException();
        } else {
            this.x += b.x;
            this.y += b.y;
            this.z += b.z;
        }
    }

    public void sub(Vektor3D b) {
        Vektor3D tmp = new Vektor3D(-b.x, -b.y, -b.z);
        this.add(tmp);
    }

    public void mult(double scalar) {
        double x = this.x * scalar;
        double y = this.y * scalar;
        double z = this.z * scalar;

        if (Double.isInfinite(x) || Double.isInfinite(y) || Double.isInfinite(z)) {
            throw new ArithmeticException();
        } else {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public void div(double divisor) {
        if (divisor == 0) {
            throw new ArithmeticException();
        } else {
            this.mult(1 / divisor);
        }
    }

    public boolean isEqual(Vektor3D b) {
        return (this.x == b.x && this.y == b.y && this.z == b.z);
    }

    public boolean isNotEqual(Vektor3D b) {
        return (!isEqual(b));
    }

    public double length() {
        double tmp = this.x * this.x + this.y * this.y + this.z * this.z;
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
        return "x: " + this.x + "\ny: " + this.y + "\nz: " + this.z + "\n";
    }
}