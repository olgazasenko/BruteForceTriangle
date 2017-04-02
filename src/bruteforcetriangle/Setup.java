/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bruteforcetriangle;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Olga
 */
public class Setup {

    Scanner in = null;
    protected ArrayList<Point2D.Double[]> colourSets;
    protected ArrayList<Point2D.Double> commonArr;
    int n; // total num of points
    int k;
    int[] sizes;
    private static final double EPSILON = 0.0000001; //+9
    protected int smallDiff = 0;

    Setup() {

        try {
            String filePath = new File("").getAbsolutePath();
            in = new Scanner(new FileReader(filePath
                    + "/data/genposK10.txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Setup.class.getName()).log(Level.SEVERE, null, ex);
        }
        k = in.nextInt(); // total number of colours
        if (k < 3) {
            System.out.println("Not enough colours! CSD = 0.");
            System.exit(0);
        }

        colourSets = new ArrayList<>(k);
        commonArr = new ArrayList<>();
        sizes = new int[k];
        initialize();
    }

    private void initialize() {

        int i = 0;
        while (in.hasNext()) {
            int ni = in.nextInt(); // n_i
            sizes[i] = ni;
            n += ni;

            colourSets.add(new Point2D.Double[ni]);

            for (int j = 0; j < ni; j++) {
                double x = in.nextDouble();
                double y = in.nextDouble();
                Point2D.Double pt = new Point2D.Double(x, y);
                colourSets.get(i)[j] = pt;
                commonArr.add(new Point2D.Double(-x, -y));
            }
            i++;
        }
        System.out.println("n = " + n);
    }

    protected int computeColourfulDepth() {
      //  Paint paint = new Paint();
        Point2D.Double p1, p2, p3;
        int depth = 0;
        for (int i1 = 0; i1 < k - 2; i1++) { // first colour
            for (int j1 = 0; j1 < sizes[i1]; j1++) { // j1 = 0..n_i1
                for (int i2 = i1 + 1; i2 < k - 1; i2++) { // second colour
                    for (int j2 = 0; j2 < sizes[i2]; j2++) {
                        for (int i3 = i2 + 1; i3 < k; i3++) {
                            for (int j3 = 0; j3 < sizes[i3]; j3++) {
                                p1 = colourSets.get(i1)[j1];
                                p2 = colourSets.get(i2)[j2];
                                p3 = colourSets.get(i3)[j3];
                                if (checkIfInside(p1, p2, p3)) {
                                    depth++;
                                  //  paint.paint(p1, p2, p3);
                                    double theta1 = Math.toDegrees(Math.atan2(p1.y, p1.x));
                                    double theta2 = Math.toDegrees(Math.atan2(p2.y, p2.x));
                                    double theta3 = Math.toDegrees(Math.atan2(p3.y, p3.x));
                                    if (theta1 < 0) {
                                        theta1 += 360;
                                    }
                                    if (theta2 < 0) {
                                        theta2 += 360;
                                    }
                                    if (theta3 < 0) {
                                        theta3 += 360;
                                    }
                                    if (Math.abs(theta2 - theta1) == 180 ||
                                    Math.abs(theta3 - theta2) == 180 ||
                                    Math.abs(theta1 - theta3) == 180){
                                        System.out.println("HEY HO");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Small Differences in CSD = " + smallDiff);
        return depth;
    }

    protected int computeDI() {
        int depth = 0, depthI = 0;
        for (int i1 = 0; i1 < k; i1++) { // first colour
            for (int j1 = 0; j1 < sizes[i1]; j1++) { // j1 = 0..n_i1
                for (int j2 = j1 + 1; j2 < sizes[i1]; j2++) { // second colour
                    for (int i2 = 0; i2 < k; i2++) {
                        //if (i2 != i1) {
                        for (int j3 = 0; j3 < sizes[i2]; j3++) {

                            if (checkIfInside(colourSets.get(i1)[j1],
                                    colourSets.get(i1)[j2], colourSets.get(i2)[j3])) {
                                depth++;
                                depthI++;
                            }

                        }
                        // }
                    }
                }
            }
            // System.out.println("D^" + i1 + " = " + depthI);
            depthI = 0;
        }
        System.out.println("Small Differences in D^i= " + smallDiff);
        return depth;
    }

    protected int computeMonotoneCommon() {
        int depth = 0;
        for (int j1 = 0; j1 < commonArr.size() - 2; j1++) {
            for (int j2 = j1 + 1; j2 < commonArr.size() - 1; j2++) {
                for (int j3 = j2 + 1; j3 < commonArr.size(); j3++) {
                    if (checkIfInside(commonArr.get(j1), commonArr.get(j2),
                            commonArr.get(j3))) {
                        depth++;
                    }
                }
            }
        }
        System.out.println("Small Differences in Common = " + smallDiff);
        return depth;
    }

    protected int computeSum1() {
        int depth = 0;
        for (int i = 0; i < k; i++) {
            depth += computeMonotoneDepth(i);
        }
        return depth;
    }

    private int computeMonotoneDepth(int i) {
        int depth = 0;
        for (int j1 = 0; j1 < sizes[i] - 2; j1++) {
            for (int j2 = j1 + 1; j2 < sizes[i] - 1; j2++) {
                for (int j3 = j2 + 1; j3 < sizes[i]; j3++) {
                    if (checkIfInside(colourSets.get(i)[j1],
                            colourSets.get(i)[j2], colourSets.get(i)[j3])) {
                        depth++;
                    }
                }
            }
        }
        return depth;
    }

    /*
    source: http://www.blackpawn.com/texts/pointinpoly/
     */
    private boolean checkIfInside(Point2D.Double A, Point2D.Double B,
            Point2D.Double C) {
        Point2D.Double P = new Point2D.Double(0, 0); // origin
        Point2D.Double v0 = new Point2D.Double(C.x - A.x, C.y - A.y);
        Point2D.Double v1 = new Point2D.Double(B.x - A.x, B.y - A.y);
        Point2D.Double v2 = new Point2D.Double(P.x - A.x, P.y - A.y);

        // Compute dot products
        double dot00 = v0.x * v0.x + v0.y * v0.y;
        double dot01 = v0.x * v1.x + v0.y * v1.y;
        double dot02 = v0.x * v2.x + v0.y * v2.y;
        double dot11 = v1.x * v1.x + v1.y * v1.y;
        double dot12 = v1.x * v2.x + v1.y * v2.y;

        // Compute barycentric coordinates
        double invDenom = 1.0 / (dot00 * dot11 - dot01 * dot01);
        double u = (dot11 * dot02 - dot01 * dot12) * invDenom;
        double v = (dot00 * dot12 - dot01 * dot02) * invDenom; // Check if point is in triangle

        if (Math.abs(u + v - 1) < EPSILON) {
            //  System.out.println("HEY");
            smallDiff++;
            // return true;
        }
        return (u >= 0) && (v >= 0) && (u + v < 1);

    }

}
