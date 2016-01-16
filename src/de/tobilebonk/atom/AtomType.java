package de.tobilebonk.atom;

/**
 * Created by Dappsen on 13.12.2015.
 */
public enum AtomType {
    P, O, C, N, H;

    public static AtomType parseAtom(String type) {
        switch (type) {
            case "P":
                return P;
            case "O":
                return O;
            case "C":
                return C;
            case "N":
                return N;
            case "H":
                return H;
            default:
                throw new IllegalArgumentException("String " + type + " could not be parsed to corresponding atom type!");
        }
    }
}
