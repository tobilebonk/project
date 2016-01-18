package de.tobilebonk.nucleotide3D;

import de.tobilebonk.utils.ResiduumAtomsSequenceNumberTriple;
import javafx.scene.paint.Color;

/**
 * Created by Dappsen on 02.01.2016.
 */
public class Guanine3D extends Nucleotide3DTemplate {

    public Guanine3D(ResiduumAtomsSequenceNumberTriple atomsOfResiduum){
        super(atomsOfResiduum);
    }

    @Override
    protected void initMeshs() {
        initSugar(c1_, c2_, c3_, c4_, o4_);
        initBase(n1, c2, n3, c4, c5, c6, n7, c8, n9);
    }

    @Override
    public void setColoring() {
        changeSugarColoringTo(Color.BLUE);
        changeBaseColoringTo(Color.BLUEVIOLET);
        changePhosphorColoringTo(Color.MEDIUMAQUAMARINE);
        defaultColor = false;
    }


}