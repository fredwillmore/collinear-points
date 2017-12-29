import edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import java.util.Arrays;


public class BruteCollinearPoints {
    private final Point[] points;
    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {    // finds all line segments containing 4 points
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

    private void findSegments() {
        ArrayList<Point[]> pointPairs = new ArrayList<Point[]>();
        double slope12, slope13, slope14;
        for (Point point1: points) {
            for (Point point2: points) {
                if (point2 != point1) {
                    slope12 = point1.slopeTo(point2);
                    for (Point point3 : points) {
                        if (point3 != point1 && point3 != point2) {
                            slope13 = point1.slopeTo(point3);
                            for (Point point4 : points) {
                                if (point4 != point1 && point4 != point2 && point4 != point3) {
                                    slope14 = point1.slopeTo(point4);
                                    if (Double.compare(slope12, slope13) == 0 && Double.compare(slope12, slope14) == 0) {
                                        Point[] segmentPoints = {point1, point2, point3, point4};
                                        Arrays.sort(segmentPoints);
                                        Point beginning = segmentPoints[0];
                                        Point end = segmentPoints[3];

                                        boolean duplicate = false;
                                        for (Point[] pointPair : pointPairs) {
                                            if (((beginning == pointPair[0]) && (end == pointPair[1]) || (beginning == pointPair[1]) && (end == pointPair[0]))) {
                                                duplicate = true;
                                                break;
                                            }
                                        }
                                        if (!duplicate) {
                                            pointPairs.add(new Point[]{beginning, end});
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
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

        System.out.println("here are the segments:");
        for (LineSegment lineSegment: (new BruteCollinearPoints(points)).lineSegments) {
            System.out.println("segment: " + lineSegment.toString());
        }

    }

    private static void test2() {
        Point[] points = {
            new Point(23051, 31012),
            new Point(23051, 31012),
            null,
            new Point(23636, 19957),
            new Point(5614, 24587),
            new Point(22322, 20002),
            new Point(22094, 26034),
            new Point(15658, 21495),
            new Point(18961, 28355),
            new Point(10242, 23307),
            new Point(29468,  4329)
        };
        BruteCollinearPoints b = new BruteCollinearPoints(points);

        System.out.println("here are the segments:");
        for (LineSegment lineSegment: b.lineSegments) {
            System.out.println("segment: " + lineSegment.toString());
        }

    }

    //    n-by-1 grid
    private static void test3() {
        int n = 32;
        int cx = 10;
        int cy = 20;
        Point[] points = new Point[n];
        for(int i = 0; i < n; i++) {
            points[i] = new Point(cx + i, cy);
        }
        BruteCollinearPoints b = new BruteCollinearPoints(points);
        printCount();
//        printSegments(b.lineSegments);
    }

    private static void printSegments(LineSegment[] lineSegments) {
        System.out.println("here are the segments:");
        for (LineSegment lineSegment: lineSegments) {
            System.out.println("segment: " + lineSegment.toString());
        }
    }

    private static void printCount() {
//        System.out.println("count is: " + Point.count);
    }

}
