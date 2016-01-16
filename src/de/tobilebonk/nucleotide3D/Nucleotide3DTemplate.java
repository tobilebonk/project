package de.tobilebonk.nucleotide3D;

import de.tobilebonk.atom.Atom;
import de.tobilebonk.utils.ResiduumAtomsSequenceNumberTriple;
import de.tobilebonk.utils.Rna3DUtils;
import javafx.scene.Group;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dappsen on 02.01.2016.
 */
public abstract class Nucleotide3DTemplate{

    //Atoms

    // base atoms
    protected Atom n1, c2, n3, c4, c5, c6, n7, c8, n9;
    //phosphor atom
    protected Atom p;
    // sugar atoms:
    protected Atom c1_, c2_, c3_, c4_, o4_;
    // atoms between sugar and phospate:
    protected Atom o5_, c5_, o3_;

    // 3D items
    private MeshView sugarMeshView;
    private MeshView baseMeshView;
    private Sphere phosphorSphere;
    private List<Cylinder> innerBondCylinders; //cylinders within the group
    private List<Cylinder> phosphorToPhosphorBondCylinders = new ArrayList<>();
    private List<Cylinder> sugarToPhosphorBondCylinders = new ArrayList<>();;
    private Group nucleotideGroup;

    // other stuff
    private float[] texCoords = {0.0f, 1.0f, 0.5f, 0.5f, 0.5f, 1.0f };
    private ResiduumType type;
    private int sequenceNumber;
    protected boolean defaultColor = true;

    public Nucleotide3DTemplate(ResiduumAtomsSequenceNumberTriple atomsOfResiduum){

        this.type = atomsOfResiduum.getType();
        this.sequenceNumber = atomsOfResiduum.getSequenceNumber();

        initAtoms(atomsOfResiduum);
        initPhosphor();
        initBonds();
        initMeshs();
        initGroup();
    }

    // method to complete the template
    protected abstract void initMeshs();

    // phosphor sphere
    private void initPhosphor(){
        if(p != null) {
            Sphere phosphorSphere = new Sphere();
            phosphorSphere.setTranslateX(p.getxCoordinate());
            phosphorSphere.setTranslateY(p.getyCoordinate());
            phosphorSphere.setTranslateZ(p.getzCoordinate());
            this.phosphorSphere = phosphorSphere;
        }
    };

    //bond cylinders
    private void initBonds(){
        //bonds between sugar and base
        List<Cylinder> bondCylinders = new ArrayList<>();
        if(p != null && o5_ != null){
            bondCylinders.addAll(Rna3DUtils.calculateBonds(new Atom[]{p, o5_}));
        }
        if(c5_ != null && o5_ != null) {
            bondCylinders.addAll(Rna3DUtils.calculateBonds(new Atom[]{o5_, c5_}));
        }
        if(c5_ != null && c4_ != null) {
            bondCylinders.addAll(Rna3DUtils.calculateBonds(new Atom[]{c5_, c4_}));
        }

        //bonds between the sugars and the phosphors
        if ((n9 != null && c1_ != null) && (n9.getResiduumType() == ResiduumType.A || n9.getResiduumType() == ResiduumType.G)) {
            bondCylinders.addAll(Rna3DUtils.calculateBonds(new Atom[]{n9, c1_}));
        } else if(n1 != null && c1_ != null){
            bondCylinders.addAll(Rna3DUtils.calculateBonds(new Atom[]{n1, c1_}));
        }

        this.innerBondCylinders = bondCylinders;
    }

    // sugar mesh, to be called by extending classes
    protected void initSugar(Atom... atoms) {

        //check if one of atoms is not initialized --> do not create mesh
        for(Atom atom : atoms){
            if(atom == null){
                return;
            }
        }

        TriangleMesh sugarMesh = new TriangleMesh();
        MeshView sugarMeshView = new MeshView(sugarMesh);
        // tex coords
        sugarMesh.getTexCoords().addAll(texCoords);
        // points
        for (Atom atom : atoms) {
            sugarMesh.getPoints().addAll(atom.getxCoordinate(), atom.getyCoordinate(), atom.getzCoordinate());
        }
        // faces (the same for all nucleotides)
        sugarMesh.getFaces().addAll(
                    0,0,  1,1,  2,2, 2,2, 1,1, 0,0,
                    0,0,  2,1,  4,2, 4,2, 2,1, 0,0,
                    2,0,  3,1,  4,2, 4,2, 3,1, 2,0
        );
        this.sugarMeshView = sugarMeshView;

    }

    // base mesh, to be called by extending classes
    protected void initBase(Atom... atoms) {

        //check if one of atoms is not initialized --> do not create mesh
        for(Atom atom : atoms){
            if(atom == null){
                return;
            }
        }

        TriangleMesh baseMesh = new TriangleMesh();
        MeshView baseMeshView = new MeshView(baseMesh);
        // tex coords
        baseMesh.getTexCoords().addAll(texCoords);
        // points
        for (Atom atom : atoms) {
            baseMesh.getPoints().addAll(atom.getxCoordinate(), atom.getyCoordinate(), atom.getzCoordinate());
        }
        // faces (A and G have the same structure; U and C have the same structure)
        if(type == ResiduumType.A || type == ResiduumType.G){
            baseMesh.getFaces().addAll(
                    0,0,  1,1,  2,2, 2,2, 1,1, 0,0,
                    0,0,  2,1,  3,2, 3,2, 2,1, 0,0,
                    0,0,  3,1,  4,2, 4,2, 3,1, 0,0,
                    0,0,  4,1,  5,2, 5,2, 4,1, 0,0,
                    3,0,  8,1,  7,2, 7,2, 8,1, 3,0,
                    3,0,  7,1,  6,2, 6,2, 7,1, 3,0,
                    3,0,  6,1,  4,2, 4,2, 6,1, 3,0
            );
        }
        else{
            baseMesh.getFaces().addAll(
                    0,0,  1,1,  2,2, 2,2, 1,1, 0,0,
                    0,0,  2,1,  3,2, 3,2, 2,1, 0,0,
                    0,0,  3,1,  4,2, 4,2, 3,1, 0,0,
                    0,0,  4,1,  5,2, 5,2, 4,1, 0,0
            );
        }
        this.baseMeshView = baseMeshView;

    }

    // group
    protected void initGroup() {
        nucleotideGroup = new Group();
        addNucleotideElementsCheckedToChildren(innerBondCylinders);
        addNucleotideElementsCheckedToChildren(sugarMeshView, baseMeshView, phosphorSphere);
        Tooltip t = new Tooltip(type.toString() + sequenceNumber);
        Tooltip.install(nucleotideGroup, t);
        this.nucleotideGroup = nucleotideGroup;
    }

    //instantiate the atoms that are part of the current molecule
    private void initAtoms(ResiduumAtomsSequenceNumberTriple atomsOfResiduum) {
        for (Atom atom : atomsOfResiduum.getAtoms()) {
            switch (atom.getAtomName().toUpperCase()) {
                case "P":
                    p = atom;
                    continue;
                case "N1":
                    n1 = atom;
                    continue;
                case "C2":
                    c2 = atom;
                    continue;
                case "N3":
                    n3 = atom;
                    continue;
                case "C4":
                    c4 = atom;
                    continue;
                case "C5":
                    c5 = atom;
                    continue;
                case "C6":
                    c6 = atom;
                    continue;
                case "N7":
                    n7 = atom;
                    continue;
                case "C8":
                    c8 = atom;
                    continue;
                case "N9":
                    n9 = atom;
                    continue;
                case "C1'":
                    c1_ = atom;
                    continue;
                case "C2'":
                    c2_ = atom;
                    continue;
                case "C3'":
                    c3_ = atom;
                    continue;
                case "C4'":
                    c4_ = atom;
                    continue;
                case "O4'":
                    o4_ = atom;
                    continue;
                case "O5'":
                    o5_ = atom;
                    continue;
                case "C5'":
                    c5_ = atom;
                    continue;
                case "O3'":
                    o3_ = atom;
                    continue;
            }
        }
    }


    protected Atom getPhosphor(){
        return this.p;
    }


    // public methods

    public Group getNucleotideGroup(){
        return nucleotideGroup;
    }

    public void connectSugarToPhosphorOf(Nucleotide3DTemplate nextNucleotide3D){
        if(nextNucleotide3D.getPhosphor() != null && this.c3_ != null && this.o3_ != null){
            sugarToPhosphorBondCylinders = Rna3DUtils.calculateBonds(new Atom[]{c3_, o3_, o3_, nextNucleotide3D.getPhosphor()});
            addNucleotideElementsCheckedToChildren(sugarToPhosphorBondCylinders);
        }
    };

    public void connectPhosphorToPhosphorOf(Nucleotide3DTemplate nextNucleotide3D){
        if(nextNucleotide3D.getPhosphor() != null && this.p != null){
            phosphorToPhosphorBondCylinders = Rna3DUtils.calculateBonds(new Atom[]{nextNucleotide3D.getPhosphor(), p});
            addNucleotideElementsCheckedToChildren(phosphorToPhosphorBondCylinders);

            PhongMaterial bondMaterial = new PhongMaterial();
            bondMaterial.setDiffuseColor(Color.BLACK);
            phosphorToPhosphorBondCylinders.forEach(b -> b.setMaterial(bondMaterial));
        }
    };

    public void disconnectSugarFromPhosphor(){
        nucleotideGroup.getChildren().removeAll(sugarToPhosphorBondCylinders);
        sugarToPhosphorBondCylinders.clear();
    }

    public void disconnectPhosphorFromPhosphor(){
        nucleotideGroup.getChildren().removeAll(phosphorToPhosphorBondCylinders);
        phosphorToPhosphorBondCylinders.clear();
    }

    public void changeSugarColoringTo(Color color){
        if(sugarMeshView != null) {
            PhongMaterial sugarMaterial = new PhongMaterial();
            sugarMaterial.setDiffuseColor(color);
            sugarMeshView.setMaterial(sugarMaterial);
        }
    }

    public void changeBaseColoringTo(Color color){
        if(baseMeshView != null) {
            PhongMaterial baseMaterial = new PhongMaterial();
            baseMaterial.setDiffuseColor(color);
            baseMeshView.setMaterial(baseMaterial);
        }
    }

    public void resetColoring(){
        this.changeSugarColoringTo(Color.WHITE);
        this.changeBaseColoringTo(Color.WHITE);
        this.changePhosphorColoringTo(Color.WHITE);
        defaultColor = true;
    }

    public void switchColoring(){
        if(defaultColor){
            setColoring();
        }else{
            resetColoring();
        }
    }

    public abstract void setColoring();

    public void changePhosphorColoringTo(Color color){
        if(phosphorSphere != null) {
            PhongMaterial phosphorMaterial = new PhongMaterial();
            phosphorMaterial.setDiffuseColor(color);
            phosphorSphere.setMaterial(phosphorMaterial);
        }
    }

    public MeshView getSugarMeshView() {
        return sugarMeshView;
    }

    public MeshView getBaseMeshView() {
        return baseMeshView;
    }

    public Sphere getPhosphorSphere() {
        return phosphorSphere;
    }

    public List<Cylinder> getInnerBondCylinders() {
        return innerBondCylinders;
    }

    public List<Cylinder> getPhosphorToPhosphorBondCylinders() {
        return phosphorToPhosphorBondCylinders;
    }

    public List<Cylinder> getSugarToPhosphorBondCylinders() {
        return sugarToPhosphorBondCylinders;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public ResiduumType getType() {
        return type;
    }

    private void addNucleotideElementsCheckedToChildren(Shape3D... elements){
        for(Shape3D element : elements){
            if(element != null){
                this.nucleotideGroup.getChildren().add(element);
            }
        }
    }

    private void addNucleotideElementsCheckedToChildren(List<? extends Shape3D> elements){
        for(Shape3D element : elements){
            if(element != null){
                this.nucleotideGroup.getChildren().add(element);
            }
        }
    }


}
