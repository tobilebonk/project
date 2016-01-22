package de.tobilebonk.utils;

import de.tobilebonk.nucleotide3D.Residue;

import java.util.Comparator;

/**
 * Created by Dappsen on 18.01.2016.
 */
public class Comparators {

    public static Comparator<Residue> residueSequenceIdComparator(){
        return new Comparator<Residue>() {
            @Override
            public int compare(Residue r1, Residue r2) {
                int s1 = r1.getSequenceNumber();
                int s2 = r2.getSequenceNumber();
                return s1 < s2 ? -1 : s1 == s2 ? 0 : 1;
            }
        };
    }


}
