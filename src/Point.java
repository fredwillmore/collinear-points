import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {
//    public static long count = 0;

    private final int x;
    private final int y;

    public Point(int x, int y) {                         // constructs the point (x, y)
        this.x = x;
        this.y = y;
    }

    public void draw() {                               // draws this point
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {                   // draws the line segment from this point to that point
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public String toString() {                           // string representation
        return "(" + x + ", " + y + ")";
    }

    public int compareTo(Point that) {     // compare two points by y-coordinates, breaking ties by x-coordinates
//if(Point.count++ % 1000000 == 0)
//    System.out.println("hello count is " + Point.count);
        if (that == null)
            throw new NullPointerException();
        if (this.y == that.y)
            return Integer.compare(this.x, that.x);
        else
            return Integer.compare(this.y, that.y);
    }

    public double slopeTo(Point that) {       // the slope between this point and that point
        if (this.x == that.x)
            return this.y == that.y ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        if (this.y == that.y)
            return 0;
        return (double) (this.y - that.y) / (this.x - that.x);
    }

    public Comparator<Point> slopeOrder() {              // compare two points by slopes they make with this point
        return new SlopeComparator();
    }

    private class SlopeComparator implements Comparator<Point> {
        public int compare(Point a, Point b) {
            if (a == null || b == null)
                throw new java.lang.NullPointerException();
            return Double.compare(slopeTo(a), slopeTo(b));
        }
    }

    public static void main(String[] args) {        // test client
        Point p, q, r;
        p = new Point(56, 327);
        q = new Point(56, 327);
        if (p.slopeTo(q) == Double.NEGATIVE_INFINITY)
            System.out.println("hello this is working");
        else
            System.out.println("wtf it should be -Infinity, instead it's: " + p.slopeTo(q));

        if (p != q) {
            System.out.println("wtf these should be equal");
        }

        p = new Point(6, 0);
        q = new Point(9, 0);
        r = new Point(3, 0);

        if (p.slopeOrder().compare(q, r) == 0)
            System.out.println("hello this is working");
        else
            System.out.println("wtf it should be 0, instead it's: " + p.slopeOrder().compare(q, r));

    }
}
