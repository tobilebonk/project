package de.tobilebonk.model.atom;

import de.tobilebonk.model.residue.ResiduumType;

/**
 * Created by Dappsen on 13.12.2015.
 */
public class Atom {

    private int id; //nucleotide3D sequence number
    private String atomName;
    private AtomType atomType; // element
    private ResiduumType residuumType;
    private int sequenceNumber;
    private float xCoordinate, yCoordinate, zCoordinate;

    public Atom(int id, String atomName, AtomType atomType, ResiduumType residuumType, int sequenceNumber, float xCoordinate, float yCoordinate, float zCoordinate) {
        this.id = id;
        this.atomName = atomName;
        this.atomType = atomType;
        this.residuumType = residuumType;
        this.sequenceNumber = sequenceNumber;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.zCoordinate = zCoordinate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAtomName(String atomName) {
        this.atomName = atomName;
    }

    public void setAtomType(AtomType atomType) {
        this.atomType = atomType;
    }

    public void setResiduumType(ResiduumType residuumType) {
        this.residuumType = residuumType;
    }

    public void setIndex(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public void setxCoordinate(float xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public void setyCoordinate(float yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public void setzCoordinate(float zCoordinate) {
        this.zCoordinate = zCoordinate;
    }

    public int getId() {
        return id;
    }

    public String getAtomName() {
        return atomName;
    }

    public AtomType getAtomType() {
        return atomType;
    }

    public ResiduumType getResiduumType() {
        return residuumType;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public float getxCoordinate() {
        return xCoordinate;
    }

    public float getyCoordinate() {
        return yCoordinate;
    }

    public float getzCoordinate() {
        return zCoordinate;
    }

    @Override
    public String toString(){
        return "id=" + id + "  atomName=" + atomName + "  atomType=" + atomType +
                "  residuumType=" + residuumType + " sequenceNumber=" + sequenceNumber +
                "  x=" + xCoordinate + "  y=" + yCoordinate + "  z=" + zCoordinate;
    }
}
