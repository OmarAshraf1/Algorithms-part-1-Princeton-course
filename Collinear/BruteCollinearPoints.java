/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;

public class BruteCollinearPoints {


    private int line = 0;
    private ArrayList<LineSegment> isline = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();

        }
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException();
            }
        }

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int z = k + 1; z < points.length; z++) {
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])
                                && points[i].slopeTo(points[j]) == points[i].slopeTo(points[z])) {
                            //it is a line
                            line++;
                            isline.add(new LineSegment(points[i], points[z]));
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return line;
    }

    public LineSegment[] segments() {
        return (LineSegment[]) isline.toArray();
    }


}
