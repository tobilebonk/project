package de.tobilebonk.utils;

import de.tobilebonk.atom.Atom;
import de.tobilebonk.nucleotide3D.Residue;
import de.tobilebonk.nucleotide3D.ResiduumType;
import javafx.geometry.Point3D;

import java.util.List;

/**
 * Created by Dappsen on 25.01.2016.
 */
public class ComputationUtils {


    private static double MAX_DISTANCE = 2.8d;
    private static double MIN_ANGLE = 150d;


    //TODO Multithread this
    public static boolean computeIsResidueConnectedToResidue(Residue r1, Residue r2){

        if(r1.getType() == ResiduumType._ || r2.getType() == ResiduumType._ ||
                (r1.getType() == ResiduumType.A && r2.getType() != ResiduumType.U)
                ||(r1.getType() == ResiduumType.U && r2.getType() != ResiduumType.A)
                || (r1.getType() == ResiduumType.G && r2.getType() != ResiduumType.C)
                || (r1.getType() == ResiduumType.C && r2.getType() != ResiduumType.G)){
            return false;
        }

        //Adenine Uracil Bonds
        if(r1.getType() == ResiduumType.A || r2.getType() == ResiduumType.A) {

            // adenine
            Atom n6, h61, h62, n1;
            Point3D n6Point = null;
            Point3D h61Point = null;
            Point3D h62Point = null;
            Point3D n1Point = null;
            // uracil
            Atom n3, h3, o4;
            Point3D n3Point = null;
            Point3D h3Point = null;
            Point3D o4Point = null;

            //distances
            double o4h61SquaredDistance, h62n6SquaredDistance, h61n6SquaredDistance, o4h62SquaredDistance, n3h3SquaredDistance, h3n1SquaredDistance, n1n3SquaredDistance, n6o4SquaredDistance;
            Residue currentResidue = r1;
            for (int i = 0; i < 2; i++) {

                if (currentResidue.getType() == ResiduumType.A) {
                    n6 = currentResidue.getAtomWithName("n6");
                    h61 = currentResidue.getAtomWithName("h61");
                    h62 = currentResidue.getAtomWithName("h62");
                    n1 = currentResidue.getAtomWithName("n1");
                    n6Point = new Point3D(n6.getxCoordinate(), n6.getyCoordinate(), n6.getzCoordinate());
                    h61Point = new Point3D(h61.getxCoordinate(), h61.getyCoordinate(), h61.getzCoordinate());
                    h62Point = new Point3D(h62.getxCoordinate(), h62.getyCoordinate(), h62.getzCoordinate());
                    n1Point = new Point3D(n1.getxCoordinate(), n1.getyCoordinate(), n1.getzCoordinate());
                }
                if (currentResidue.getType() == ResiduumType.U) {
                    n3 = currentResidue.getAtomWithName("n3");
                    h3 = currentResidue.getAtomWithName("h3");
                    o4 = currentResidue.getAtomWithName("o4");
                    n3Point = new Point3D(n3.getxCoordinate(), n3.getyCoordinate(), n3.getzCoordinate());
                    h3Point = new Point3D(h3.getxCoordinate(), h3.getyCoordinate(), h3.getzCoordinate());
                    o4Point = new Point3D(o4.getxCoordinate(), o4.getyCoordinate(), o4.getzCoordinate());
                }
                currentResidue = r2;
            }

            h3n1SquaredDistance = Math.pow(h3Point.distance(n1Point), 2);
            n3h3SquaredDistance = Math.pow(n3Point.distance(h3Point), 2);
            n1n3SquaredDistance = Math.pow(n3Point.distance(n1Point), 2);
            h61n6SquaredDistance = Math.pow(h61Point.distance(n6Point), 2);
            h62n6SquaredDistance = Math.pow(h62Point.distance(n6Point), 2);
            o4h61SquaredDistance = Math.pow(o4Point.distance(h61Point), 2);
            o4h62SquaredDistance = Math.pow(o4Point.distance(h62Point), 2);
            n6o4SquaredDistance = Math.pow(n6Point.distance(o4Point), 2);

            //angles
            double n3h3n1Angle = Math.toDegrees(Math.acos((h3n1SquaredDistance + n3h3SquaredDistance - n1n3SquaredDistance) / (2 * Math.sqrt(h3n1SquaredDistance) * Math.sqrt(n3h3SquaredDistance))));
            double o4h61n6Angle = Math.toDegrees(Math.acos((h61n6SquaredDistance + o4h61SquaredDistance - n6o4SquaredDistance) / (2 * Math.sqrt(h61n6SquaredDistance) * Math.sqrt(o4h61SquaredDistance))));
            double o4h62n6Angle = Math.toDegrees(Math.acos((h62n6SquaredDistance + o4h62SquaredDistance - n6o4SquaredDistance) / (2 * Math.sqrt(h62n6SquaredDistance) * Math.sqrt(o4h62SquaredDistance))));


            System.out.println(Math.sqrt(o4h61SquaredDistance) + "  " + o4h61n6Angle);
            System.out.println(Math.sqrt(o4h62SquaredDistance) + "  " + o4h62n6Angle);
            System.out.println(Math.sqrt(h3n1SquaredDistance) + "  " + n3h3n1Angle);


            return (Math.sqrt(o4h61SquaredDistance) < MAX_DISTANCE && o4h61n6Angle > MIN_ANGLE)
                    || (Math.sqrt(h3n1SquaredDistance) < MAX_DISTANCE && n3h3n1Angle > MIN_ANGLE)
                    || (Math.sqrt(o4h62SquaredDistance) < MAX_DISTANCE && o4h62n6Angle > MIN_ANGLE);

        }
        // Guanine Cytosine bonds
        if(r1.getType() == ResiduumType.C ||  r2.getType() == ResiduumType.C){

            // cytosin
            Atom n4, h41, h42, n3, o2;
            Point3D n4Point = null;
            Point3D h41Point = null;
            Point3D h42Point = null;
            Point3D n3Point = null;
            Point3D o2Point = null;

            // guanine
            Atom o6, n1, h1, n2, h21, h22;
            Point3D o6Point = null;
            Point3D n1Point = null;
            Point3D h1Point = null;
            Point3D n2Point = null;
            Point3D h21Point = null;
            Point3D h22Point = null;

            //distances
            double n4h41SquaredDistance, n4h42SquaredDistance, h41o6SquaredDistance, h42o6SquaredDistance, o6n4SquaredDistance,
                    n3h1SquaredDistance, h1n1SquaredDistance, n1n3SquaredDistance,
                    o2h21SquaredDistance, o2h22SquaredDistance, h21n2SquaredDistance, h22n2SquaredDistance, n2o2SquaredDistance;
            Residue currentResidue = r1;
            for (int i = 0; i < 2; i++) {

                if (currentResidue.getType() == ResiduumType.C) {
                    n4 = currentResidue.getAtomWithName("n4");
                    h41 = currentResidue.getAtomWithName("h41");
                    h42 = currentResidue.getAtomWithName("h42");
                    n3 = currentResidue.getAtomWithName("n3");
                    o2 = currentResidue.getAtomWithName("o2");
                    n4Point = new Point3D(n4.getxCoordinate(), n4.getyCoordinate(), n4.getzCoordinate());
                    h41Point = new Point3D(h41.getxCoordinate(), h41.getyCoordinate(), h42.getzCoordinate());
                    h42Point = new Point3D(h42.getxCoordinate(), h42.getyCoordinate(), h42.getzCoordinate());
                    n3Point = new Point3D(n3.getxCoordinate(), n3.getyCoordinate(), n3.getzCoordinate());
                    o2Point = new Point3D(o2.getxCoordinate(), o2.getyCoordinate(), o2.getzCoordinate());
                }
                if (currentResidue.getType() == ResiduumType.G) {
                    o6 = currentResidue.getAtomWithName("o6");
                    n1 = currentResidue.getAtomWithName("n1");
                    h1 = currentResidue.getAtomWithName("h1");
                    n2 = currentResidue.getAtomWithName("n2");
                    h21 = currentResidue.getAtomWithName("h21");
                    h22 = currentResidue.getAtomWithName("h22");
                    o6Point = new Point3D(o6.getxCoordinate(), o6.getyCoordinate(), o6.getzCoordinate());
                    n1Point = new Point3D(n1.getxCoordinate(), n1.getyCoordinate(), n1.getzCoordinate());
                    h1Point = new Point3D(h1.getxCoordinate(), h1.getyCoordinate(), h1.getzCoordinate());
                    n2Point = new Point3D(n2.getxCoordinate(), n2.getyCoordinate(), n2.getzCoordinate());
                    h21Point = new Point3D(h21.getxCoordinate(), h21.getyCoordinate(), h21.getzCoordinate());
                    h22Point = new Point3D(h22.getxCoordinate(), h22.getyCoordinate(), h22.getzCoordinate());
                }
                currentResidue = r2;
            }

            n4h41SquaredDistance = Math.pow(h41Point.distance(n4Point),2);
            n4h42SquaredDistance = Math.pow(h42Point.distance(n4Point),2);
            h41o6SquaredDistance = Math.pow(h41Point.distance(o6Point),2);
            h42o6SquaredDistance = Math.pow(h42Point.distance(o6Point),2);
            o6n4SquaredDistance = Math.pow(o6Point.distance(n4Point),2);
            n3h1SquaredDistance = Math.pow(n3Point.distance(h1Point),2);
            h1n1SquaredDistance = Math.pow(h1Point.distance(n1Point),2);
            n1n3SquaredDistance = Math.pow(n1Point.distance(n3Point),2);
            o2h21SquaredDistance = Math.pow(o2Point.distance(h21Point),2);
            o2h22SquaredDistance = Math.pow(o2Point.distance(h22Point),2);
            h21n2SquaredDistance = Math.pow(h21Point.distance(n2Point),2);
            h22n2SquaredDistance = Math.pow(h22Point.distance(n2Point),2);
            n2o2SquaredDistance = Math.pow(n2Point.distance(o2Point),2);

            //angles
            double n4h41o6Angle = Math.toDegrees(Math.acos((n4h41SquaredDistance + h41o6SquaredDistance - o6n4SquaredDistance) / (2 * Math.sqrt(n4h41SquaredDistance) * Math.sqrt(h41o6SquaredDistance))));
            double n4h42o6Angle = Math.toDegrees(Math.acos((n4h42SquaredDistance + h42o6SquaredDistance - o6n4SquaredDistance) / (2 * Math.sqrt(n4h42SquaredDistance) * Math.sqrt(h42o6SquaredDistance))));
            double n3h1n1Angle = Math.toDegrees(Math.acos((n3h1SquaredDistance + h1n1SquaredDistance - n1n3SquaredDistance) / (2 * Math.sqrt(n3h1SquaredDistance) * Math.sqrt(h1n1SquaredDistance))));
            double o2h21n2Angle = Math.toDegrees(Math.acos((o2h21SquaredDistance + h21n2SquaredDistance - n2o2SquaredDistance)/(2* Math.sqrt(o2h21SquaredDistance) * Math.sqrt(h21n2SquaredDistance))));
            double o2h22n2Angle = Math.toDegrees(Math.acos((o2h22SquaredDistance + h22n2SquaredDistance - n2o2SquaredDistance)/(2* Math.sqrt(o2h22SquaredDistance) * Math.sqrt(h22n2SquaredDistance))));

/*
            System.out.println(Math.sqrt(h41o6SquaredDistance) + "  " + n4h41o6Angle);
            System.out.println(Math.sqrt(h42o6SquaredDistance) + "  " + n4h42o6Angle);
            System.out.println(Math.sqrt(n3h1SquaredDistance) + "  " + n3h1n1Angle);
            System.out.println(Math.sqrt(o2h21SquaredDistance) + "  " + o2h21n2Angle);
            System.out.println(Math.sqrt(o2h22SquaredDistance) + "  " + o2h22n2Angle);
            */

            return (Math.sqrt(h41o6SquaredDistance) < MAX_DISTANCE &&  n4h41o6Angle > MIN_ANGLE)
                    || (Math.sqrt(h42o6SquaredDistance) < MAX_DISTANCE && n4h42o6Angle > MIN_ANGLE)
                    || (Math.sqrt(n3h1SquaredDistance) < MAX_DISTANCE && n3h1n1Angle > MIN_ANGLE)
                    || (Math.sqrt(o2h21SquaredDistance) < MAX_DISTANCE && o2h21n2Angle > MIN_ANGLE)
                    || (Math.sqrt(o2h22SquaredDistance) < MAX_DISTANCE && o2h22n2Angle > MIN_ANGLE);


        }
        //not reached
        //TODO: exception
        return false;
    }
}
