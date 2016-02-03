/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Maps;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Jmacvey
 */
public class KaminoPlatform extends GameMap
{
    public KaminoPlatform()
    {
        this.createMap();
        this.setMap(createAdjacencyMap());
        this.setBlockedSpaces(createBlockedSpaces());
        this.setUnoccupiableSpaces(createUOSpaces());
        this.setStartPositions();
        this.generateCanvas();
    }
    
    
    private HashMap<String, List<String>> createAdjacencyMap() {
        HashMap<String, List<String>> adjacencyMap = new HashMap<>();
        placeAdjacenciesForRowA(adjacencyMap);
        placeAdjacenciesForRowB(adjacencyMap);
        placeAdjacenciesForRowC(adjacencyMap);
        placeAdjacenciesForRowD(adjacencyMap);
        placeAdjacenciesForRowE(adjacencyMap);
        placeAdjacenciesForRowF(adjacencyMap);
        placeAdjacenciesForRowG(adjacencyMap);
        placeAdjacenciesForRowH(adjacencyMap);
        placeAdjacenciesForRowI(adjacencyMap);
        placeAdjacenciesForRowJ(adjacencyMap);        
        return adjacencyMap;
    }
    
    
    private HashMap<String, String> createBlockedSpaces() {
        HashMap<String, String> blockedSpaces = new HashMap<>();
        
        blockedSpaces.put("G2", "G2");
        blockedSpaces.put("G3", "G3");
        blockedSpaces.put("G4", "G4");
        blockedSpaces.put("H2", "H2");
        blockedSpaces.put("H3", "H3");
        blockedSpaces.put("I3", "I3");
        
        return blockedSpaces;
    }
    
    private HashMap<String, String> createUOSpaces(){
        HashMap<String, String> UOSpaces = new HashMap<>();
        
        UOSpaces.put("G2", "G2");
        UOSpaces.put("G3", "G3");
        UOSpaces.put("G4", "G4");
        UOSpaces.put("H2", "H2");
        UOSpaces.put("H3", "H3");
        UOSpaces.put("I3", "I3");
        UOSpaces.put("A1", "A1");
        UOSpaces.put("A7", "A7");
        UOSpaces.put("B1", "B1");
        UOSpaces.put("B2", "B2");
        UOSpaces.put("B6", "B6");
        UOSpaces.put("B7", "B7");
        UOSpaces.put("C1", "C1");
        UOSpaces.put("C2", "C2");
        UOSpaces.put("C6", "C6");
        UOSpaces.put("C7", "C7");
        UOSpaces.put("D1", "D1");
        UOSpaces.put("D2", "D2");
        UOSpaces.put("D6", "D6");
        UOSpaces.put("D7", "D7");  
        return UOSpaces;
    }
    
    private void placeAdjacenciesForRowA(HashMap<String, List<String>> adjMap)
    {
        adjMap.put("A2", Arrays.asList("A3", "B3"));
        adjMap.put("A3", Arrays.asList("A2","A4", "B3", "B4"));
        adjMap.put("A4", Arrays.asList("A3", "A5", "B3", "B4", "B5"));
        adjMap.put("A5", Arrays.asList("A4", "A6", "B4", "B5"));
        adjMap.put("A6", Arrays.asList("A5", "B5"));
        
    }
    
    private void placeAdjacenciesForRowB(HashMap<String, List<String>> adjMap)
    {
                // B
        adjMap.put("B3", Arrays.asList("A2", "A3", "A4", 
                                       "B4", 
                                       "C3", "C4"));
        adjMap.put("B4", Arrays.asList("A3", "A4", "A5", 
                                       "B3", "B5",
                                       "C3", "C4", "C5"));
        adjMap.put("B5", Arrays.asList("A4", "A5", "A6", 
                                       "B4", 
                                       "C4", "C5"));
        
    }
    
    private void placeAdjacenciesForRowC(HashMap<String, List<String>> adjMap)
    {
         // C
        adjMap.put("C3", Arrays.asList("B3", "B4", 
                                       "C4", 
                                       "D3", "D4"));
        adjMap.put("C4", Arrays.asList("B3", "B4", "B5",
                                             "C3", "C5",
                                             "D3", "D4", "D5"));
        adjMap.put("C5", Arrays.asList("B4", "B5",
                                             "C4",
                                             "D4", "D5"));
    }
    private void placeAdjacenciesForRowD(HashMap<String, List<String>> adjMap)
    {
        // D
        adjMap.put("D3", Arrays.asList("E2", "E3", "E4",
                                             "D4",
                                             "C3", "C4"));
        adjMap.put("D4", Arrays.asList("E3", "E4", "E5",
                                             "D3", "D5",
                                             "C3", "C4", "C5"));
        adjMap.put("D5", Arrays.asList("E4", "E5", "E6",
                                             "D4",
                                             "C4", "C5"));
    }
    private void placeAdjacenciesForRowE(HashMap<String, List<String>> adjMap)
    {
        // E
        adjMap.put("E1", Arrays.asList("F1", "F2",
                                             "E2"));
        adjMap.put("E2", Arrays.asList("F1", "F2", "F3",
                                       "E1", "E3",
                                       "D3"));
        adjMap.put("E3", Arrays.asList("F2", "F3", "F4",
                                             "E2", "E4",
                                             "D3", "D4"));
        adjMap.put("E4", Arrays.asList("F3", "F4", "F5",
                                        "E3", "E5",
                                        "D3", "D4", "D5"));
        adjMap.put("E5", Arrays.asList("F4", "F5", "F6",
                                        "E4", "E6",
                                        "D4", "D5"));
        adjMap.put("E6", Arrays.asList("F5", "F6", "F7",
                                        "E5", "E7",
                                        "D5"));
        adjMap.put("E7", Arrays.asList("F6", "F7",
                                       "E6"));      
    }
    
    private void placeAdjacenciesForRowF(HashMap<String, List<String>> adjMap)
    {
       // F
        adjMap.put("F1", Arrays.asList("G1", "F2", "E1", "E2"));
        adjMap.put("F2", Arrays.asList("G1",
                                       "F1", "F3",
                                       "E1", "E2", "E3"));
        adjMap.put("F3", Arrays.asList("F2", "F4",
                                       "E2","E3", "E4"));
        adjMap.put("F4", Arrays.asList("F3", "F5",
                                       "E3", "E4", "E5",
                                       "G5"));
        adjMap.put("F5", Arrays.asList("F4", "F6",
                                        "E4", "E5", "E6",
                                        "G5", "G6"));
        adjMap.put("F6", Arrays.asList("F5", "F7",
                                        "E5","E6", "E7",
                                        "G5","G6", "G7"));
        adjMap.put("F7", Arrays.asList("G6", "G7",
                                       "F6",
                                       "E6", "E7"));
    }
    
 private void placeAdjacenciesForRowG(HashMap<String, List<String>> adjMap)
    {
       // G
        adjMap.put("G1", Arrays.asList("H1", "F1", "F2"));
        adjMap.put("G5", Arrays.asList("H4", "H5", "H6",
                                       "G6",
                                       "F4", "F5", "F6"));
        adjMap.put("G6", Arrays.asList("H5", "H6", "H7",
                                       "G5", "G7",
                                       "F5","F6", "F7"));
        adjMap.put("G7", Arrays.asList("H6", "H7",
                                       "G6",
                                       "F6", "F7"));
    }
 
  private void placeAdjacenciesForRowH(HashMap<String, List<String>> adjMap)
    {
       // H
        adjMap.put("H1", Arrays.asList("G1", "I1", "I2"));
        adjMap.put("H4", Arrays.asList("G5", "H5", "I4", "I5"));        
        adjMap.put("H5", Arrays.asList("I4", "I5", "I6",
                                       "H4", "H6",
                                       "G5", "G6"));
        adjMap.put("H6", Arrays.asList("I5", "I6", "I7",
                                       "H5", "H7",
                                       "G5","G6", "G7"));
        adjMap.put("H7", Arrays.asList("I6", "I7",
                                       "H6",
                                       "G6", "G7"));        
    }
  
    private void placeAdjacenciesForRowI(HashMap<String, List<String>> adjMap)
    {
       // I
        adjMap.put("I1", Arrays.asList("H1", "I2", "J1", "J2"));
        adjMap.put("I2", Arrays.asList("H1", "I1", "J1", "J2", "J3"));        
        adjMap.put("I4", Arrays.asList("H4", "H5", 
                                       "I5", 
                                       "J3", "J4", "J5"));        
        adjMap.put("I5", Arrays.asList("J4", "J5", "J6",
                                       "I4", "I6",
                                       "H4", "H5", "H6"));
        adjMap.put("I6", Arrays.asList("J5", "J6", "J7",
                                       "I5", "I7",
                                       "H5","H6", "H7"));
        adjMap.put("I7", Arrays.asList("J6", "J7",
                                       "H6", "H7"));        
    }
    
    private void placeAdjacenciesForRowJ(HashMap<String, List<String>> adjMap)
    {
       // J
        adjMap.put("J1", Arrays.asList("I1", "I2", "J2"));
        adjMap.put("J2", Arrays.asList("J1", "I1", "I2", "J3"));
        adjMap.put("J3", Arrays.asList("J2", "J4", "I2", "I4"));
        adjMap.put("J4", Arrays.asList("J3", "I4", "I5", "J5"));                 
        adjMap.put("J5", Arrays.asList("J4", "J6",
                                       "I4", "I5","I6"));
        adjMap.put("J6", Arrays.asList("J5", "J7",
                                       "I5", "I6", "I7"));
        adjMap.put("J7", Arrays.asList("J6", "I6", "I7"));        
    }
    
    private void setStartPositions() {
        this.setStartPosition(GameMap.StartTag.HAN, "E6");
        this.setStartPosition(GameMap.StartTag.ANAKIN, "B4");
        this.setStartPosition(GameMap.StartTag.BOBA, "J5");
        this.setStartPosition(GameMap.StartTag.DOOKU, "G7");
        this.setStartPosition(GameMap.StartTag.JANGO, "H4");
        this.setStartPosition(GameMap.StartTag.LUKE, "A3");
        this.setStartPosition(GameMap.StartTag.MAUL, "H7");
        this.setStartPosition(GameMap.StartTag.MACE, "C5");
        this.setStartPosition(GameMap.StartTag.YODA, "D4");
        this.setStartPosition(GameMap.StartTag.OBIWAN, "E4");
        this.setStartPosition(GameMap.StartTag.PALPATINE, "J2");
        this.setStartPosition(GameMap.StartTag.VADER, "I1");
    }
 
}
