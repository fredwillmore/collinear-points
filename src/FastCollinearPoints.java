import edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final Point[] points;
    private LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {    // finds all line segments containing 4 points
        if (points == null)
            throw new java.lang.IllegalArgumentException();
        this.points = points.clone();
        for (Point point: this.points)
            if (point == null)
                throw new java.lang.IllegalArgumentException();
        Arrays.sort(this.points);
        Point last = null;
        for (Point point: this.points) {
            if (last != null && point.compareTo(last) == 0)
                throw new java.lang.IllegalArgumentException();
            last = point;
        }
        findSegments();
    }

    public int numberOfSegments() {        // the number of line segments
        return lineSegments.length;
    }

    public LineSegment[] segments() {                // the line segments
        return lineSegments.clone();
    }

    private class PointGroup {
        private static final double EPSILON = 0.00001;
        private final Point reference;
        private ArrayList<Point> points;
        private double groupingAttribute;

        public PointGroup(Point reference) {
            this.reference = reference;
            points = new ArrayList<Point>();
            groupingAttribute = Double.NEGATIVE_INFINITY;
        }

        public void add(Point point) {
            points.add(point);
            if (size() == 2) {
                setGroupingAttribute(points.get(0).slopeTo(points.get(1)));
            }
        }

        private void sortPoints() {
            points.sort((a, b) -> a.compareTo(b));
            return;
        }

        public boolean belongs(Point p) {
            if (size() < 2) return true;
            return Double.compare(reference.slopeTo(p), groupingAttribute) == 0;
        }

        public double getGroupingAttribute() {
            return groupingAttribute;
        }

        public void setGroupingAttribute(double groupingAttribute) {
            this.groupingAttribute = groupingAttribute;
        }

        public int size() {
            return points.size();
        }

        public Point beginning() {
            return endpoints()[0];
        }

        public Point end() {
            return endpoints()[1];
        }

        private Point[] endpoints() {
            return new Point[] { points.get(0), points.get(points.size() - 1) };
        }

        public boolean compareEndpoints(Point[] toCompare) {
            int endIndex = toCompare.length - 1;
            return ((beginning() == toCompare[0]) && (end() == toCompare[endIndex])) || ((beginning() == toCompare[endIndex]) && (end() == toCompare[0]));
        }

        private void exportPointPair(ArrayList<Point[]> pointPairs) {
            sortPoints(); // TODO: dependency on sorting is hidden in here
            if (size() >= 4) {
                boolean duplicate = false;
                for (Point[] pointPair : pointPairs) {
                    duplicate |= compareEndpoints(pointPair);
                }
                if (!duplicate)
                    pointPairs.add(endpoints());
            }
        }

    }

    private void findSegments() {
        ArrayList<Point[]> pointPairs = new ArrayList<Point[]>();
        for (Point point: points) {
            PointGroup pointGroup = new PointGroup(point);
            Point[] candidates = points.clone();
            Arrays.sort(candidates, point.slopeOrder());
//            NOTE: there is an issue with assuming the candidates array will group segments together by slopeOrder
//            or - the dependency is dually on the response from belongs(candidate)
            for (Point candidate: candidates) {
                if (!pointGroup.belongs(candidate)) {
                    pointGroup.exportPointPair(pointPairs);
                    pointGroup = new PointGroup(point);
                    pointGroup.add(point);
                }
                pointGroup.add(candidate);
            }
            pointGroup.exportPointPair(pointPairs);
        }

        lineSegments = new LineSegment[pointPairs.size()];
        int i = 0;
        for (Point[] pointPair: pointPairs) {
            lineSegments[i++] = new LineSegment(pointPair[0], pointPair[1]);
        }

    }

    public static void main(String[] args) {
        test1(args);
        test2();
        test3();
        test4();
        test5();
        test6();
        test7();
        testn1();
    }

    private static Point[] readFile(String filename) {
        In in = new In(filename);      // input file
        int n = in.readInt();         // n-by-n percolation system
        Point[] points = new Point[n];

        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        return points;
    }

    private static void test1(String[] args) {
        Point[] points = readFile(args[0]);
        FastCollinearPoints f = new FastCollinearPoints(points);

        for (LineSegment segment: f.segments()) {
            System.out.println("segment: " + segment.toString());
        }
    }

    private static void test2() {
        Point[] points = new Point[] {
                new Point(0,10),
                new Point(0,2),
                new Point(0,3),
                new Point(0,4),
        };
        FastCollinearPoints f = new FastCollinearPoints(points);

        for (LineSegment segment: f.segments()) {
            System.out.println("segment: " + segment.toString());
        }
    }

    private static void test3() {
        Point[] points = new Point[] {
                new Point(10,10),
                new Point(2,10),
                new Point(4,10),
                new Point(5,10),
        };
        FastCollinearPoints f = new FastCollinearPoints(points);

        for (LineSegment segment: f.segments()) {
            System.out.println("segment: " + segment.toString());
        }
    }

    private static void test4() {
        Point[] points = new Point[] {
                new Point(10,9),
                new Point(2,1),
                new Point(4,3),
                new Point(5,4),
        };
        FastCollinearPoints f = new FastCollinearPoints(points);

        for (LineSegment segment: f.segments()) {
            System.out.println("segment: " + segment.toString());
        }
    }

    private static void test5() {
        Point[] points = new Point[] {
                new Point(10000,      0),
                new Point(3000,   7000),
                new Point(7000,   3000),
                new Point(20000,  21000),
                new Point(3000,   4000),
                new Point(14000,  15000),
                new Point(6000,   7000),
                new Point(0,  10000),
        };
        FastCollinearPoints f = new FastCollinearPoints(points);

        for (LineSegment segment: f.segments()) {
            System.out.println("segment: " + segment.toString());
        }
    }

    private static void test6() {
        Point[] points = new Point[] {
//            new Point(3000, 4000),
//            new Point(6000, 7000),
//            new Point(14000, 15000),
//            new Point(20000, 21000)
            new Point(10000, 0),
            new Point( 8000, 2000),
            new Point(2000, 8000),
            new Point(0,  10000),

            new Point(20000, 0),
            new Point(18000, 2000),
            new Point(2000, 18000),

            new Point(10000, 20000),
            new Point(30000, 0),
            new Point(0, 30000),
            new Point(20000, 10000),

            new Point(13000, 0),
            new Point(11000, 3000),
            new Point(5000, 12000),
            new Point(9000, 6000)
        };
        FastCollinearPoints f = new FastCollinearPoints(points);

        for (LineSegment segment: f.segments()) {
            System.out.println("segment: " + segment.toString());
        }
    }

    private static void test7() {
        Point[] points = new Point[] {
            new Point(  1000,  17000),
            new Point(14000,  24000),
            new Point(26000,   8000),
            new Point(10000,  28000),
            new Point(18000,   5000),
            new Point(1000,  27000),
            new Point(14000,  14000),
            new Point(11000,  16000),
            new Point(29000,  17000),
            new Point(5000,  21000),
            new Point(19000,  26000),
            new Point(28000,  21000),
            new Point(25000,  24000),
            new Point(30000,  10000),
            new Point(25000,  14000),
            new Point(31000,  16000),
            new Point(5000,  12000),
            new Point(1000,  31000),
            new Point(2000,  24000),
            new Point(13000,  17000),
            new Point(1000,  28000),
            new Point(14000,  16000),
            new Point(26000,  26000),
            new Point(10000,  31000),
            new Point(12000,   4000),
            new Point(9000,  24000),
            new Point(28000,  29000),
            new Point(12000,  20000),
            new Point(13000,  11000),
            new Point(4000,  26000),
            new Point(8000,  10000),
            new Point(15000,  12000),
            new Point(22000,  29000),
            new Point(7000,  15000),
            new Point(10000,   4000),
            new Point(2000,  29000),
            new Point(17000,  17000),
            new Point(3000,  15000),
            new Point(4000,  29000),
            new Point(19000,   2000)
        };
        FastCollinearPoints f = new FastCollinearPoints(points);

        for (LineSegment segment: f.segments()) {
            System.out.println("segment: " + segment.toString());
        }
    }

    //    n-by-1 grid
    private static void testn1() {
        int n = 1024;
        int cx = 10;
        int cy = 20;
        Point[] points = new Point[n];
        for(int i = 0; i < n; i++) {
            points[i] = new Point(cx + i, cy);
        }
        FastCollinearPoints f = new FastCollinearPoints(points);
        printCount();
        printSegments(f.lineSegments);
    }

    private static void printSegments(LineSegment[] lineSegments) {
        System.out.println("here are the segments:");
        for (LineSegment lineSegment: lineSegments) {
            System.out.println("segment: " + lineSegment.toString());
        }
    }

    private static void printCount() {
        try {
            System.out.println(Point.class.getField("count"));
//            System.out.println("count is: " + Point.count); // this doesn't compile when the field is not defined
        } catch (NoSuchFieldException e) {
            System.out.println("caught exception - not counting method calls");
        }
    }

}