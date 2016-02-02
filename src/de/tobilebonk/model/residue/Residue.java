package de.tobilebonk.model.residue;

import de.tobilebonk.model.atom.Atom;

import java.util.List;

/**
 * Created by Dappsen on 14.12.2015.
 */
public class Residue {

    private ResiduumType type;
    private List<Atom> atoms;
    private int sequenceNumber;

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
    
    public Atom getAtomWithName(String name) throws  IllegalArgumentException{
        for (Atom atom : atoms) {
            if(atom.getAtomName().toUpperCase().equals(name.toUpperCase())){
                return atom;
            }
        }
        throw new IllegalArgumentException("Atom " + name + " was not found in residue " + sequenceNumber);
    }

}
