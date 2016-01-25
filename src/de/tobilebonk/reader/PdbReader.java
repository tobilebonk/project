package de.tobilebonk.reader;

import com.sun.org.apache.xpath.internal.SourceTree;
import de.tobilebonk.atom.Atom;
import de.tobilebonk.atom.AtomType;
import de.tobilebonk.nucleotide3D.ResiduumType;
import de.tobilebonk.nucleotide3D.Residue;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by Dappsen on 07.11.2015.
 */
public class PdbReader {

    private List<Atom> atoms;
    private List<ResiduumType> residuumTypes;
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
        residuumTypes = new LinkedList<ResiduumType>();
        sequenceNumbers = new HashSet<Integer>();

        try (BufferedReader reader =  Files.newBufferedReader(Paths.get(path))) {

            String line;

            int i = 0;
            while ((line = reader.readLine()) != null) {
                i++;
                //http://www.wwpdb.org/documentation/file-format-content/format33/sect8.html#ATOM
                if (line.equals("END") || !line.substring(0, 4).equals("ATOM")) {
                    continue;
                }
                else{
                    //read
                    int id = Integer.parseInt(line.substring(6, 11).replaceAll(" ", ""));
                    String atomName = line.substring(12,16).replaceAll(" ", "");
                    ResiduumType residuumType = ResiduumType.parseResiduum(line.substring(17,20).replaceAll(" ", ""));
                    int sequenceNumber = Integer.parseInt(line.substring(22, 26).replaceAll(" ", ""));
                    float xCoordinate = Float.parseFloat(line.substring(30, 38).replace(" ", ""));
                    float yCoordinate = Float.parseFloat(line.substring(38,46).replace(" ", ""));
                    float zCoordinate = Float.parseFloat(line.substring(46,54).replace(" ", ""));
                    AtomType atomType = AtomType.parseAtom(line.substring(76, 78).replace(" ", ""));

                    // handle missing sequence numbers
                    if(!atoms.isEmpty()){
                        int previousSequenceNumber = atoms.get(atoms.size() - 1).getSequenceNumber();
                        if(sequenceNumber != previousSequenceNumber){
                            int dummySequenceNumber = previousSequenceNumber;
                            while(++dummySequenceNumber < sequenceNumber){
                                residuumTypes.add(ResiduumType._);
                                atoms.add(new Atom(-1, "DUMMY", AtomType._, ResiduumType._, dummySequenceNumber, 0,0,0));
                                sequenceNumbers.add(dummySequenceNumber);
                            }
                            //add to current (existing) residue to residue list
                            residuumTypes.add(residuumType);
                        }
                    }else{
                        residuumTypes.add(residuumType);
                    }
                    //add current atom to atoms
                    atoms.add(new Atom(id, atomName, atomType, residuumType, sequenceNumber, xCoordinate, yCoordinate, zCoordinate));
                    if(sequenceNumber == 100)
                    System.out.println(new Atom(id, atomName, atomType, residuumType, sequenceNumber, xCoordinate, yCoordinate, zCoordinate).toString());

                    //add current sequenceNumber to sequence numbers
                    sequenceNumbers.add(sequenceNumber);
                }
            }
            reader.close();
            System.out.println(i);
        }catch (IOException e){
            System.err.println("Caught IOException: " + e.getMessage());
        }
    }

    public List<Residue> getResiduesOfType(ResiduumType type){
        List<Residue> residues = new LinkedList<>();
        for(int sequenceNumber : sequenceNumbers){
            List<Atom> residuumAtoms = new LinkedList<>();
            for(Atom atom : atoms){
                if(atom.getResiduumType() == type && atom.getSequenceNumber() == sequenceNumber){
                    residuumAtoms.add(atom);
                }
            }
            if(! residuumAtoms.isEmpty() && residuumAtoms.get(0).getSequenceNumber() == sequenceNumber){
                residues.add(new Residue(type, residuumAtoms, sequenceNumber));
            }
        }

        return residues;
    }

    public List<ResiduumType> getResiduumTypes(){
        return residuumTypes;
    }
}
