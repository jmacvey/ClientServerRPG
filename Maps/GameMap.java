/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Maps;

import Team.TeamID;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.shape.Rectangle;

/**
 * Abstract class GameMap is represented by a hashmap of positions. Its
 * functionality serves as follows: An initial map (10 x 6) (row x col) map MUST
 * be created using the public createMap function. Descendants of this class
 * must also set blocked spaces, unoccupiable spaces, and adjacency lists for
 * the map to function properly. Once that is done, all range and adjacency
 * position tracking is done by the super class.
 *
 * @author Jmacvey
 */
public abstract class GameMap implements Serializable
{

    /**
     * Creates a 10 x 6 map for the game. All spaces are unblocked. Users should
     * block spaces using the blockSpaces function.
     */
    public void createMap()
    {

        for (String string : stringList)
        {
            addRowToMap(string, createRow(string, 1, 7));
        }
    }

    /**
     * Setter method for this hashmap. Always needs to provide an adjacency map.
     *
     * @param adjacencyMap the adjacencyMap for this map
     */
    public void setMap(HashMap<String, List<String>> adjacencyMap)
    {
        this.adjacencyMap = adjacencyMap;
    }

    /**
     * Setter method for this map's blocked spaces
     *
     * @param blockedSpaces the blocked spaces map for this map
     */
    public void setBlockedSpaces(HashMap<String, String> blockedSpaces)
    {
        this.blockedSpaces = blockedSpaces;
    }

    /**
     * Setter method for this map's unoccupied spaces.
     *
     * @param unoccupiableSpaces the map of unoccupiable spaces
     */
    public void setUnoccupiableSpaces(HashMap<String, String> unoccupiableSpaces)
    {
        this.unoccupiableSpaces = unoccupiableSpaces;
    }

    /**
     * Marks a space on the map as occupied.
     *
     * @param position the position to be occupied
     * @param name the name of the player at that position
     */
    public void setOccupiedPosition(String position, String name)
    {
        occupiedPositions.put(position, name);
    }

    /**
     * Marks a space on map as unoccupied.
     *
     * @param position the position to mark as unoccupied.
     */
    public void setUnoccupiedPosition(String position)
    {
        occupiedPositions.remove(position);
    }

    /**
     * Sets the initial starting position of a character.
     *
     * @param start the character tag associated with this position
     * @param position the position
     */
    public void setStartPosition(StartTag start, String position)
    {
        startPositions.put(start, position);
    }

    /**
     * Getter for the start positions of a character.
     *
     * @param start the tag associated with this character's starting position.
     * @return the start position of the character.
     */
    public String getStartPosition(StartTag start)
    {
        return startPositions.get(start);
    }

    /**
     * Getter for the canvas rectangle map
     *
     * @return the map containing the rectangles
     */
    public HashMap<String, Rectangle> getCanvasRectsMap()
    {
        return this.canvasRects;
    }

    /**
     * Sets the teamID list for this game.
     *
     * @param teamIDList the list of team IDs.
     */
    public void setTeamIDList(List<TeamID> teamIDList)
    {
        this.teamIDList = teamIDList;
    }

    /**
     * Gets the teamID list for this game.
     *
     * @return the team ID list
     */
    public List<TeamID> getTeamIDList()
    {
        return this.teamIDList;
    }

    /**
     * Sets the mapID for this map.
     *
     * @param mapID the map id.
     */
    public void setMapID(MapID mapID)
    {
        this.mapID = mapID;
    }

    /**
     * Gets the mapID for this map.
     *
     * @return the map id.
     */
    public MapID getMapID()
    {
        return this.mapID;
    }

    /**
     * Function allowing user to determine if two positions are adjacent toward
     * each other on the map.
     *
     * @param thisPosition the first position
     * @param thatPosition the second position
     * @return true if this position is adjacency to that position. Otherwise
     * false.
     */
    public boolean areAdjacent(String thisPosition, String thatPosition)
    {
        if (adjacencyMap != null)
        {
            List<String> adjacencyList = adjacencyMap.get(thisPosition);
            return adjacencyList.contains(thatPosition);
        } else
        {
            return false;
        }
    }

    /**
     * Sets the teams on the map.
     */
    /**
     * Function allowing user to check if two positions are in line of sight.
     * This function handles cases where the user would be in light of sight if
     * not for a blocked space.
     *
     * @param thisPosition the first position
     * @param thatPosition the second position
     * @return true if this position is in line of sight to that position.
     * Otherwise false.
     */
    public boolean inLineOfSight(String thisPosition, String thatPosition)
    {

        char[] thisArray = thisPosition.toCharArray();
        char[] thatArray = thatPosition.toCharArray();
        // same row
        if (thisArray[0] == thatArray[0])
        {
            boolean thisColGreater = (int) thisArray[1] > (int) thatArray[1];

            return !rowHasBlockedSpaces((int) thisArray[0],
                    thisColGreater ? (int) thatArray[1] : (int) thisArray[1],
                    thisColGreater ? (int) thisArray[1] : (int) thatArray[1]);
        } // same column
        else if (thisArray[1] == thatArray[1])
        {
            boolean thisRowGreater = (int) thisArray[0] > (int) thatArray[0];
            return !colHasBlockedSpaces((int) thisArray[1],
                    thisRowGreater ? (int) thatArray[0] : (int) thisArray[0],
                    thisRowGreater ? (int) thisArray[0] : (int) thatArray[0]);
        } // check for diagonal
        else
        {
            return inDiagonalSight(thisArray, thatArray);
        }
    }

    /**
     * Generates and returns a number of prospective positions given an initial
     * position and a distance. This is done recursively using adjacency lists.
     *
     * @param position the initial position.
     * @param distance the maximum allowable distance.
     * @param allies the allies whose positions are to be ignored.
     * @return a HashSet containing the list of valid positions.
     */
    public HashSet<String> getProspectivePositions(String position, int distance,
            List<String> allies)
    {
        // UBER ALGORITHM INCOMING:

        // clear it
        possibleLocs.clear();
        // get it
        getPositionHelper(position, distance, allies);
        // return it
        return possibleLocs;
    }

    /**
     * overrides toString method.
     *
     * @return Text-based version of the map.
     */
    @Override
    public String toString()
    {
        String[] strings =
        {
            "J", "I", "H", "G", "F", "E", "D", "C", "B", "A"
        };

        String mapRequest = "";

        for (String string : strings)
        {
            List<String> rowString = map.get(string);
            mapRequest += createRowBorder(rowString);
            for (int i = 1; i < rowString.size() + 1; i++)
            {
                if (blockedSpaces.containsKey(string + Integer.toString(i)))
                {
                    mapRequest += "| X ";
                } else if (unoccupiableSpaces.containsKey(string + Integer.toString(i)))
                {
                    mapRequest += "| U ";
                } else if (occupiedPositions.containsKey(string + Integer.toString(i)))
                {   // prints first letter in the characters name
                    mapRequest += "| "
                            + occupiedPositions.get(string + Integer.toString(i)).charAt(0) + " ";
                } else
                {
                    mapRequest += "|   ";
                }
            }
            mapRequest += "|\n";
        }
        mapRequest += createRowBorder(map.get("A"));
        return mapRequest;
    }

    /**
     * Public enumeration denoting tags for all starting positions.
     */
    public enum StartTag
    {

        ANAKIN,
        HAN,
        LUKE,
        MACE,
        OBIWAN,
        YODA,
        BOBA,
        DOOKU,
        JANGO,
        MAUL,
        PALPATINE,
        VADER
    }

    private String createRowBorder(List<String> spaces)
    {
        String result = "";
        result = spaces.stream().map((_item) -> "+---").reduce(result, String::concat);
        result += "+\n";
        return result;
    }

    /**
     * Assists public method getProspectivePositions(...) by using adjacency
     * lists and recursion to identify the prospective spaces.
     *
     * @param position the position to find prospectives.
     * @param distance the maximum allowable distance.
     */
    private void getPositionHelper(String position, int distance, List<String> allies)
    {

        // add all adjacent but not occupied positions
        if (distance > 0)
        {
            List<String> adjacencyList = adjacencyMap.get(position);
            adjacencyList.stream().filter((nextPos)
                    -> (!isAdjacentAndDiagonal(position, nextPos))).forEach((nextPos) ->
                            {
                                // case: occupation
                                if (occupiedPositions.containsKey(nextPos))
                                {
                                    // occupied by an ally;
                                    if (allies.contains(occupiedPositions.get(nextPos)))
                                    {
                                        getPositionHelper(nextPos, distance - 1, allies);
                                    }
                                    // occupied by an enemy -> ignore
                                } else
                                {
                                    possibleLocs.add(nextPos);
                                    getPositionHelper(nextPos, distance - 1, allies);
                                }
                    });
        }
    }

    public List<String> getAdjacencyList(String position)
    {
        return adjacencyMap.get(position);
    }

    /**
     * Provides filter for stream in getPositionHelper. Movements are only
     * manhattan-like, so we cannot count diagonal spaces as "adjacent" for
     * prospective spaces.
     *
     * @param fromPos
     * @param toPos
     * @return
     */
    private boolean isAdjacentAndDiagonal(String fromPos, String toPos)
    {
        int rowDiff = Math.abs((int) fromPos.charAt(0) - (int) toPos.charAt(0));
        int colDiff = Math.abs((int) fromPos.charAt(1) - (int) toPos.charAt(1));
        return rowDiff == 1 && colDiff == 1;
    }

    private boolean inDiagonalSight(char[] thisArray, char[] thatArray)
    {
        // the position is split into two positions: "row identifer" and column.
        // B - A, for example, is 1 A - B, for example, is also 1.
        int rowDifference = Math.abs((int) thisArray[0] - (int) thatArray[0]);
        int colDifference = Math.abs((int) thisArray[1] - (int) thatArray[1]);

        if (rowDifference != colDifference)
        {
            return false; // case 1: row and column not the same (not in diagonal sight)
        } // if they are in diagonal sight, need to make sure those diagonals
        // spaces are not blocked.
        else
        {
            // get the lesser of the arrays
            boolean thisArrayGreater = thisArray[0] > thatArray[0];
            return !diagonalContainsBlockedSpace(
                    thisArrayGreater ? thatArray : thisArray,
                    thisArrayGreater ? thisArray : thatArray
            );
        }
    }

    /**
     * Checks the row from the begin to end for a block.
     *
     * @param row the row to check
     * @param colBegin the beginning column in the row
     * @param colEnd the ending column in the row
     * @return true if there is a block. False if not.
     */
    private boolean rowHasBlockedSpaces(int row, int colBegin, int colEnd)
    {

        for (int i = colBegin; i <= colEnd; i++)
        {
            if (blockedSpaces.containsKey(String.valueOf((char) row)
                    + String.valueOf((char) i)))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks the column from the begin to end for a block.
     *
     * @param column the row to check
     * @param rowBegin the beginning space in the row
     * @param rowEnd the ending space in the row
     * @return true if there is a block. False if not.
     */
    private boolean colHasBlockedSpaces(int column, int rowBegin, int rowEnd)
    {
        for (int i = rowBegin; i <= rowEnd; i++)
        {
            if (blockedSpaces.containsKey(String.valueOf((char) i)
                    + String.valueOf((char) column)))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Function to check if a diagonal from a space to a space is blocked or
     * not.
     *
     * @param fromPos diagonal starts at this position
     * @param toPos diagonal ends at this position
     */
    private boolean diagonalContainsBlockedSpace(char[] fromPos, char[] toPos)
    {
        boolean plus = fromPos[1] < toPos[1];
        while ((int) fromPos[0] != (int) toPos[0])
        {
            fromPos[0] = (char) ((int) fromPos[0] + 1);
            fromPos[1] = plus ? (char) ((int) fromPos[1] + 1)
                    : (char) ((int) fromPos[1] - 1);
            if (blockedSpaces.containsKey(String.valueOf(fromPos[0])
                    + String.valueOf(fromPos[1])))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a list (in this case a row for the map.)
     *
     * @param row the string number representing the row
     * @param lowerBound the lower bound of this row
     * @param upperBound the upper bound of this row
     * @return A list contians strings [row][lowerbound] ... row[upperbound]
     */
    private List<String> createRow(String row, int lowerBound, int upperBound)
    {
        List<String> l = new LinkedList<>();
        for (int i = lowerBound; i <= upperBound; i++)
        {
            l.add(row + Integer.toString(i));
        }
        return l;
    }

    /**
     * Adds a generated row tot he map.
     *
     * @param row the row identifier
     * @param stringList the list of elements associated with that row
     */
    private void addRowToMap(String row, List<String> stringList)
    {
        map.put(row, stringList);
    }

    /**
     * Generates the canvas based on the occupied positions. This method should
     * be called both on initialization and after serialization to generate the
     * canvas as JavaFX fields cannot be serialized.
     */
    public void generateCanvas()
    {
        for (String s : stringList)
        {
            // get the row of strings
            List<String> rowList = createRow(s, 1, 7);
            rowList.stream().forEach((sx) ->
            {
                Rectangle rect = new Rectangle(25, 25);
                canvasRects.put(sx, rect);
            });
        }
    }

    /**
     * Getter for the canvas rectangles.
     *
     * @return the hashMap of canvas rectangles
     */
    public List<List<Rectangle>> getCanvasRects()
    {
        List<List<Rectangle>> listOfLists = new LinkedList<>();
        for (String s : stringList)
        {
            // get the rectangles
            LinkedList<Rectangle> list = new LinkedList<>();
            for (int i = 1; i <= 7; i++)
            {
                list.add(canvasRects.get(s + Integer.toString(i)));
            }
            listOfLists.add(list);
        }
        return listOfLists;
    }

    public HashMap<String, String> getOccupiedPositions()
    {
        return this.occupiedPositions;
    }

    public HashMap<String, String> getUnoccupiableSpaces()
    {
        return this.unoccupiableSpaces;
    }

    public HashMap<String, String> getBlockedSpace()
    {
        return this.blockedSpaces;
    }

    private List<TeamID> teamIDList;
    // occupied position takes position as key and value is player name
    private HashMap<String, String> occupiedPositions = new HashMap<>();
    private HashMap<StartTag, String> startPositions = new HashMap<>();
    private HashMap<String, String> unoccupiableSpaces = new HashMap<>();
    private HashMap<String, String> blockedSpaces = new HashMap<>();
    private HashMap<String, List<String>> map = new HashMap<>();
    private HashMap<String, List<String>> adjacencyMap = new HashMap<>();
    private HashSet<String> possibleLocs = new HashSet<>();

    // canvas objects
    private transient HashMap<String, Rectangle> canvasRects = new HashMap<>();
    private MapID mapID = null;

    private String[] stringList =
    {
        "J", "I", "H", "G", "F", "E", "D", "C", "B", "A"
    };

    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException
    {
        in.defaultReadObject();
        this.canvasRects = new HashMap<>();
        generateCanvas();
    }
}
