/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.List;

public class FastCollinearPoints {
    private class Poso {
        Point point;
        double slope;
    }


    public class Merge {


        // stably merge a[lo .. mid] with a[mid+1 ..hi] using aux[lo .. hi]
        private void merge(Poso[] a, Poso[] aux, int lo, int mid, int hi) {
            // precondition: a[lo .. mid] and a[mid+1 .. hi] are sorted subarrays


            // copy to aux[]
            for (int k = lo; k <= hi; k++) {
                aux[k] = a[k];
            }

            // merge back to a[]
            int i = lo, j = mid + 1;
            for (int k = lo; k <= hi; k++) {
                if (i > mid) a[k] = aux[j++];
                else if (j > hi) a[k] = aux[i++];
                else if (aux[j].slope < aux[i].slope) a[k] = aux[j++];
                else a[k] = aux[i++];
            }


        }

        // mergesort a[lo..hi] using auxiliary array aux[lo..hi]
        private void sort(Poso[] a, Poso[] aux, int lo, int hi) {
            if (hi <= lo) return;
            int mid = lo + (hi - lo) / 2;
            sort(a, aux, lo, mid);
            sort(a, aux, mid + 1, hi);
            merge(a, aux, lo, mid, hi);
        }

        /**
         * Rearranges the array in ascending order, using the natural order.
         *
         * @param a the array to be sorted
         */
        public void sort(Poso[] a) {
            Poso[] aux = new Poso[a.length];
            sort(a, aux, 0, a.length - 1);
        }


        /***************************************************************************
         *  Helper sorting function.
         ***************************************************************************/


        /***************************************************************************
         *  Check if array is sorted - useful for debugging.
         ***************************************************************************/

    }

    private ArrayList<LineSegment> isline = new ArrayList<>();
    private List<Poso> linelist = new ArrayList<>(); //to store line with thier points and slope

    public FastCollinearPoints(Point[] points) {

        if (points == null) {
            throw new IllegalArgumentException();

        }
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException();
            }
        }
        Poso[] sloppoint = new Poso[points.length - 1];
        Merge sorting = new Merge();

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                sloppoint[j - 1].point = points[j];
                sloppoint[j - 1].slope = points[i].slopeTo(points[j]);

            }
            sorting.sort(sloppoint);
            double flag = 0;
            for (int z = 3; z < sloppoint.length; z++) {
                if (sloppoint[z].slope == sloppoint[z - 1].slope && sloppoint[z].slope == sloppoint[
                        z - 2].slope && sloppoint[z].slope == sloppoint[z
                        - 3].slope) { //if 3 slopes are equal
                    flag = sloppoint[z].slope;
                }
            }
            //compare the flag with all points
            for (int q = 0; q < sloppoint.length; q++) {
                if (flag == sloppoint[q].slope) {
                    linelist.add(sloppoint[q]);
                }
            }
            isline.add(new LineSegment(linelist.get(0).point,
                                       linelist.get(linelist.size() - 1).point));
            linelist.clear();

        }

    }

    public int numberOfSegments() {
        return isline.size();
    }

    public LineSegment[] segments() {
        return (LineSegment[]) isline.toArray();
    }

}
