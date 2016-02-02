package de.tobilebonk.view.nucleotide3D;

import de.tobilebonk.model.residue.Residue;
import javafx.scene.paint.Color;

/**
 * Created by Dappsen on 05.01.2016.
 */
public class Cytosin3D extends Nucleotide3D {

    public Cytosin3D(Residue atomsOfResidue) {
        super(atomsOfResidue);
    }

    @Override
    protected void initMeshs() {

        initSugar(c1_, c2_, c3_, c4_, o4_);
        initBase(n1, c2, n3, c4, c5, c6);
    }

    @Override
    public void setColoring() {
        changeSugarColoringTo(Color.CRIMSON);
        changeBaseColoringTo(Color.CRIMSON);
        changePhosphorColoringTo(Color.DARKRED);
        defaultColor = false;
    }
}
