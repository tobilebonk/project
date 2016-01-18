package de.tobilebonk.reader;

import de.tobilebonk.atom.Atom;
import de.tobilebonk.atom.AtomType;
import de.tobilebonk.nucleotide3D.ResiduumType;
import de.tobilebonk.utils.ResiduumAtomsSequenceNumberTriple;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Dappsen on 07.11.2015.
 */
public class PdbReader {

    private List<Atom> atoms;
    private List<ResiduumType> residues;
    private Set<Integer> sequenceNumbers;

    public PdbReader(String path){
        createAtomsFromPdbFile(path);
    }


    /**
     * Reader for pdb files
     *
     * @param path - the directory of the pdb file
     */
    private void createAtomsFromPdbFile(String path){

        atoms = new LinkedList<Atom>();
        residues = new LinkedList<ResiduumType>();
        sequenceNumbers = new HashSet<Integer>();

        try (BufferedReader reader =  Files.newBufferedReader(Paths.get(path))) {

            String line;

            while ((line = reader.readLine()) != null) {
                //http://www.wwpdb.org/documentation/file-format-content/format33/sect8.html#ATOM
                if (line.equals("END") || !line.substring(0, 4).equals("ATOM")) {
                    continue;
                }
                else if((line.length() == 78 || line.length() == 79)){
                    //read
                    int id = Integer.parseInt(line.substring(6, 11).replaceAll(" ", ""));
                    String atomName = line.substring(12,16).replaceAll(" ", "");
                    ResiduumType residuumType = ResiduumType.parseResiduum(line.substring(17,20).replaceAll(" ", ""));
                    int sequenceNumber = Integer.parseInt(line.substring(22, 26).replaceAll(" ", ""));
                    float xCoordinate = Float.parseFloat(line.substring(30, 38).replace(" ", ""));
                    float yCoordinate = Float.parseFloat(line.substring(38,46).replace(" ", ""));
                    float zCoordinate = Float.parseFloat(line.substring(46,54).replace(" ", ""));
                    AtomType atomType = AtomType.parseAtom(line.substring(76, 78).replace(" ", ""));

                    //add to residue list
                    if(atoms.isEmpty() || sequenceNumber != atoms.get(atoms.size() - 1).getSequenceNumber()){
                        residues.add(residuumType);
                    }
                    //add to atoms
                    atoms.add(new Atom(id, atomName, atomType, residuumType, sequenceNumber, xCoordinate, yCoordinate, zCoordinate));
                    //add to sequence numbers
                    sequenceNumbers.add(sequenceNumber);
                    System.out.println(sequenceNumbers.size() + "   " + getResidues().size());
                }
            }
            reader.close();
        }catch (IOException e){
            System.err.println("Caught IOException: " + e.getMessage());
        }
    }

    public List<ResiduumAtomsSequenceNumberTriple> getResiduumTypeAtomsSequenceNumberTriple(ResiduumType type){
        List<ResiduumAtomsSequenceNumberTriple> triples = new LinkedList<>();
        for(int sequenceNumber : sequenceNumbers){
            List<Atom> residuumAtoms = new LinkedList<>();
            for(Atom atom : atoms){
                if(atom.getResiduumType() == type && atom.getSequenceNumber() == sequenceNumber){
                    residuumAtoms.add(atom);
                }
            }
            if(! residuumAtoms.isEmpty() && residuumAtoms.get(0).getSequenceNumber() == sequenceNumber){
                triples.add(new ResiduumAtomsSequenceNumberTriple(type, residuumAtoms, sequenceNumber));
            }
        }
        return triples;
    }

    public Set<Integer> getSequenceNumbers(){
        return this.sequenceNumbers;
    }

    public List<Atom> getAtoms(){
        return atoms;
    }

    public List<ResiduumType> getResidues(){
        return  residues;
    }
}
