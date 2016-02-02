package de.tobilebonk.model.residue;

/**
 * Created by Dappsen on 13.12.2015.
 */
public enum ResiduumType {
    A, C, G, U, OTHER;

    public static ResiduumType parseResiduum(String type) {
        switch (type) {
            case "A":
                return A;
            case "C":
                return C;
            case "G":
                return G;
            case "U":
                return U;
            case "ADE":
                return A;
            case "CYT":
                return C;
            case "GUA":
                return G;
            case "URA":
                return U;
            default:
                return OTHER;
        }
    }


}
