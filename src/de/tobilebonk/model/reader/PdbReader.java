package de.tobilebonk.model.reader;

import de.tobilebonk.model.atom.Atom;
import de.tobilebonk.model.atom.AtomType;
import de.tobilebonk.model.residue.ResiduumType;
import de.tobilebonk.model.residue.Residue;

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
    private double minX = Integer.MAX_VALUE;
    private double meanX = 0;
    private double maxX = Integer.MIN_VALUE;
    private double minY = Integer.MAX_VALUE;
    private double meanY = 0;
    private double maxY = Integer.MIN_VALUE;
    private double minZ = Integer.MAX_VALUE;
    private double meanZ = 0;
    private double maxZ = Integer.MIN_VALUE;



    public PdbReader(String path){
        createAtomsFromPdbFile(path);
    }

    /**
     * Reader for pdb files
     *
     * @param path - the directory of the pdb file
     */
    private void createAtomsFromPdbFile(String path) throws RuntimeException{

        atoms = new LinkedList<Atom>();
        residuumTypes = new LinkedList<ResiduumType>();
        sequenceNumbers = new HashSet<Integer>();

        try (BufferedReader reader =  Files.newBufferedReader(Paths.get(path))) {

            String line;
            while ((line = reader.readLine()) != null) {
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

                    //min, max, average
                    computeMaxMins(xCoordinate, yCoordinate, zCoordinate);

                    // handle missing sequence numbers
                    if(!atoms.isEmpty()){
                        int previousSequenceNumber = atoms.get(atoms.size() - 1).getSequenceNumber();
                        if(sequenceNumber != previousSequenceNumber){
                            int dummySequenceNumber = previousSequenceNumber;
                            while(++dummySequenceNumber < sequenceNumber){
                                residuumTypes.add(ResiduumType.OTHER);
                                atoms.add(new Atom(-1, "DUMMY", AtomType._, ResiduumType.OTHER, dummySequenceNumber, 0,0,0));
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
                    //add current sequenceNumber to sequence numbers
                    sequenceNumbers.add(sequenceNumber);
                }
            }
            reader.close();
            System.out.println("File reader finished");
            meanX = (maxX + minX) / 2;
            meanY = (maxY + minY) / 2;
            meanZ = (maxZ + minZ) / 2;
        }catch (IOException e){
            throw new RuntimeException("Error reading the pdb file. Does it match the pdb format?");
        }
    }

    private void computeMaxMins(float xCoordinate, float yCoordinate, float zCoordinate) {
        if(xCoordinate < minX){
            minX = xCoordinate;
        }
        if(xCoordinate > maxX){

            maxX = xCoordinate;
        }
        if(yCoordinate < minY){
            minY = yCoordinate;
        }
        if(yCoordinate > maxY){
            maxY = yCoordinate;
        }
        if(zCoordinate < minZ){
            minZ = zCoordinate;
        }
        if(zCoordinate > maxZ){
            maxZ = zCoordinate;
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


    public double getMeanZ() {
        return meanZ;
    }

    public double getMeanY() {
        return meanY;
    }

    public double getMeanX() {
        return meanX;
    }

}
