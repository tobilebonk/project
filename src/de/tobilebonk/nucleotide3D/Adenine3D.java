package de.tobilebonk.nucleotide3D;

import javafx.scene.paint.Color;

/**
 * Created by Dappsen on 05.01.2016.
 */
public class Adenine3D extends Nucleotide3D {

    public Adenine3D(Residue atomsOfResidue){

        super(atomsOfResidue);

    }

    @Override
    protected void initMeshs() {
        initSugar(c1_, c2_, c3_, c4_, o4_);
        initBase(n1, c2, n3, c4, c5, c6, n7, c8, n9);
    }

    @Override
    public void setColoring() {
        changeSugarColoringTo(Color.GREEN);
        changeBaseColoringTo(Color.FORESTGREEN);
        changePhosphorColoringTo(Color.DARKOLIVEGREEN);
        defaultColor = false;
    }


}
