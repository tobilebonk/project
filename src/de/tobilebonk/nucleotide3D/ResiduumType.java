package de.tobilebonk.nucleotide3D;

/**
 * Created by Dappsen on 13.12.2015.
 */
public enum ResiduumType {
    A, C, G, U, _;

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
                throw new IllegalArgumentException("String " + type + " could not be parsed to corresponding residuum type!");
        }
    }


}
