package de.tobilebonk.nucleotide3D;

import de.tobilebonk.atom.Atom;
import de.tobilebonk.nucleotide3D.ResiduumType;

import java.util.List;

/**
 * Created by Dappsen on 14.12.2015.
 */
public class Residue {

    ResiduumType type;
    List<Atom> atoms;
    int sequenceNumber;

    public Residue(ResiduumType type, List<Atom> atoms, int sequenceNumber) {
        this.type = type;
        this.atoms = atoms;
        this.sequenceNumber = sequenceNumber;
    }

    public ResiduumType getType(){
        return type;
    }

    public List<Atom> getAtoms(){
        return atoms;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }
}
