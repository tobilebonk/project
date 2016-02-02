package de.tobilebonk.utils;

import de.tobilebonk.model.atom.Atom;
import javafx.geometry.Point3D;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dappsen on 06.01.2016.
 */
public class Rna3DUtils {

    /**
     *
     * Creates an array of cylinders representing and drawing bonds between atoms.
     * resulting cylinders represent the bond of atoms (in array) 0 and 1, 2 and 3, ..., n and n+1 (where n is even)
     *
     * @param atoms an array of atoms, of an even length
     * @return an array of bonds (Cylinders)
     */
    public static List<Cylinder> calculateBonds(Atom[] atoms){
        assert atoms.length % 2 == 0;
        ArrayList<Cylinder> bonds = new ArrayList<>();
        for(int i = 0; i < atoms.length; i+=2){
            Atom a = atoms[i];
            Atom b = atoms[i+1];
            //code logic from netzwerg.ch/blog/2015/03/22/javafx-3d-line
            Point3D yAxis = new Point3D(0,1,0);
            Point3D pa = new Point3D(a.getxCoordinate(), a.getyCoordinate(), a.getzCoordinate());
            Point3D pb = new Point3D(b.getxCoordinate(), b.getyCoordinate(), b.getzCoordinate());
            Point3D pointDifference = pa.subtract(pb);
            double height = pointDifference.magnitude();
            Point3D rotationAxis = pointDifference.crossProduct(yAxis);
            Rotate rotate = new Rotate(-1* Math.toDegrees(Math.acos(pointDifference.normalize().dotProduct(yAxis))), rotationAxis);
            Cylinder cylinder = new Cylinder(0.05, height);
            cylinder.setTranslateX((a.getxCoordinate() + b.getxCoordinate()) / 2);
            cylinder.setTranslateY((a.getyCoordinate() + b.getyCoordinate()) / 2);
            cylinder.setTranslateZ((a.getzCoordinate() + b.getzCoordinate()) / 2);
            cylinder.getTransforms().add(rotate);
            bonds.add(cylinder);
        }
        return bonds;
    }
}
