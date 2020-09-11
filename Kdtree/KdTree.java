/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KdTree {
    private int size;
    private Node root;

    public KdTree() {
        size = 0;
        root = null;


    }// construct an empty set of points

    public boolean isEmpty() {
        return root == null;
    }                // is the set empty?

    public int size() {
        return size;
    }                // number of points in the set

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (root == null || !contains(p)) {
            size++;
            root = helpinsert(root, p, true);
        }

    }            // add the point to the set (if it is not already in the set)

    private Node helpinsert(Node root, Point2D p, boolean horizontalSplit) {
        if (root == null) {
            return new Node(p, horizontalSplit);
        }
        if (root.horizontalSplit) {

            if (root.point.x() > p.x()) {
                root.left = helpinsert(root.left, p, !horizontalSplit);
            }
            if (root.point.x() < p.x()) {
                root.right = helpinsert(root.right, p, !horizontalSplit);
            }

        }
        else {

            if (root.point.y() > p.y()) {
                root.left = helpinsert(root.left, p, !horizontalSplit);
            }
            if (root.point.y() < p.y()) {
                root.right = helpinsert(root.right, p, !horizontalSplit);
            }

        }

        return root;


    }

    public boolean contains(Point2D p) {

        if (p == null) {
            throw new IllegalArgumentException();
        }
        return helpcontains(root, p);
    }           // does the set contain point p?

    private boolean helpcontains(Node root, Point2D p) {

        if (root == null) {
            return false;
        }
        if (p.equals(root.point)) {
            return true;
        }

        if (root.horizontalSplit && p.x() < root.point.x() ||
                !root.horizontalSplit && p.y() < root.point.y()) {
            return helpcontains(root.left, p);
        }
        else {
            return helpcontains(root.right, p);
        }


    }

    public void draw() {
        if (size != 0) {
            root.draw();
        }
    }                     // draw all points to standard draw


    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        return helprange(rect, root);

    }            // all points that are inside the rectangle (or on the boundary)

   private List<Point2D> points = new ArrayList<>();

    private List<Point2D> helprange(RectHV queryRect, Node node) {
        if (node == null) return Collections.emptyList();

        if (node.doesSpittingLineIntersect(queryRect)) {

            if (queryRect.contains(node.point)) {
                points.add(node.point);
            }
            points.addAll(helprange(queryRect, node.left));
            points.addAll(helprange(queryRect, node.right));
            return points;
        }
        else {
            if (node.isRightOf(queryRect)) return helprange(queryRect, node.left);
            else return helprange(queryRect, node.right);
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (root == null) {
            return null;
        }

        return nearest(root, p, root).point;
    }

    private Node nearest(Node current, Point2D p, Node nearest) {
        if (current == null || nearest.point.distanceSquaredTo(p) < current.point
                .distanceSquaredTo(p)) {
            return nearest;
        }

        if (current.point.distanceSquaredTo(p) < nearest.point.distanceSquaredTo(p)) {
            nearest = current;
        }

        Node left = nearest(current.left, p, nearest);
        if (left.point.distanceSquaredTo(p) < nearest.point.distanceSquaredTo(p)) {
            nearest = left;
        }

        Node right = nearest(current.right, p, nearest);
        if (right.point.distanceSquaredTo(p) < nearest.point.distanceSquaredTo(p)) {
            nearest = right;
        }

        return nearest;
    }


    private static class Node {
        private final Point2D point;
        private boolean horizontalSplit;
        private Node left;
        private Node right;

        private Node(Point2D point, boolean horizontalSplit) {
            this.point = point;
            this.horizontalSplit = horizontalSplit;
        }

        private void draw() {
            point.draw();
            if (left != null) {
                left.draw();
            }

            if (right != null) {
                right.draw();
            }
        }

        public boolean doesSpittingLineIntersect(final RectHV rectToCheck) {
            if (horizontalSplit) {
                return rectToCheck.xmin() <= point.x() && point.x() <= rectToCheck.xmax();
            }
            else {
                return rectToCheck.ymin() <= point.y() && point.y() <= rectToCheck.ymax();
            }
        }

        public boolean isRightOf(final RectHV rectToCheck) {
            if (horizontalSplit) {
                return rectToCheck.xmin() < point.x() && rectToCheck.xmax() < point.x();
            }
            else {
                return rectToCheck.ymin() < point.y() && rectToCheck.ymax() < point.y();
            }
        }
    }
}
