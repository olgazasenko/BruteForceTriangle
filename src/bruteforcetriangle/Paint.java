/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bruteforcetriangle;

import java.applet.Applet;
import java.awt.*;
import java.awt.geom.Point2D;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import org.math.plot.Plot2DPanel;

/**
 *
 * @author Olga
 */
public class Paint extends Applet {

    Graphics2D g2;
    Plot2DPanel plot;

    Paint() {
        JFrame frame = new JFrame("a plot panel");
        frame.setSize(700, 700);
        plot = new Plot2DPanel();
        frame.setContentPane(plot);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void paint(Point2D.Double p1, Point2D.Double p2, Point2D.Double p3) {
        paintPoints(p1, p2, p3);

    }

    private void paintPoints(Point2D.Double p1, Point2D.Double p2, Point2D.Double p3) {

        plot.removeAllPlots();
        Color[] colors = {Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA,
            Color.ORANGE, Color.PINK, Color.RED, Color.YELLOW};
        double[][] points = new double[4][2];
        points[0][0] = p1.x;
        points[0][1] = p1.y;
        points[1][0] = p2.x;
        points[1][1] = p2.y;
        points[2][0] = p3.x;
        points[2][1] = p3.y;
        points[3][0] = p1.x;
        points[3][1] = p1.y;
        plot.addScatterPlot("plot1", Color.PINK, points);
        plot.addLinePlot("plot2", Color.CYAN, points);
       
        double[][] centre = {{0}, {0}};
        plot.addScatterPlot("plot", Color.BLACK, centre);
    }
}
