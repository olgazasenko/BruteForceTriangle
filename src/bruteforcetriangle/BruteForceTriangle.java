/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bruteforcetriangle;

/**
 *
 * @author Olga
 */
public class BruteForceTriangle {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Setup setup = new Setup();
        //System.out.println("D = " + setup.computeMonotoneCommon());
        //System.out.println("Sum2 = " + setup.computeDI());
        //System.out.println("Sum1 = " + setup.computeSum1());
        System.out.println("CSD = " + setup.computeColourfulDepth());
    }
    
}
