package de.tobilebonk.utils;

import de.tobilebonk.nucleotide3D.Nucleotide3DTemplate;

import java.util.Comparator;

/**
 * Created by Dappsen on 18.01.2016.
 */
public class Comparators {

    public static Comparator<ResiduumAtomsSequenceNumberTriple> getSequenceIdOnTriplesComparator(){
        return new Comparator<ResiduumAtomsSequenceNumberTriple>() {
            @Override
            public int compare(ResiduumAtomsSequenceNumberTriple n1, ResiduumAtomsSequenceNumberTriple n2) {
                int s1 = n1.getSequenceNumber();
                int s2 = n2.getSequenceNumber();
                return s1 < s2 ? -1 : s1 == s2 ? 0 : 1;
            }
        };
    }

    public static Comparator<Nucleotide3DTemplate> getSequenceIdOnNucleotidesComparator(){
        return new Comparator<Nucleotide3DTemplate>() {
            @Override
            public int compare(Nucleotide3DTemplate n1, Nucleotide3DTemplate n2) {
                int s1 = n1.getSequenceNumber();
                int s2 = n2.getSequenceNumber();
                return s1 < s2 ? -1 : s1 == s2 ? 0 : 1;
            }
        };
    }
}
