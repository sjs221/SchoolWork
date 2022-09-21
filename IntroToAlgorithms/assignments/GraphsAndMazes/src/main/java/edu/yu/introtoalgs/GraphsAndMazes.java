package edu.yu.introtoalgs;

import java.rmi.MarshalException;
import java.util.*;

public class GraphsAndMazes {

    /** A immutable coordinate in 2D space.
     *
     * Students must NOT modify the constructor (or its semantics) in any way,
     * but can ADD whatever they choose.
     */
    public static class Coordinate {
        public final int x, y;

        /** Constructor, defines an immutable coordinate in 2D space.
         *
         * @param x specifies x coordinate
         * @param y specifies x coordinate
         */
        public Coordinate(final int x, final int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Coordinate{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        /** Add any methods, instance variables, static variables that you choose
         */
    } // Coordinate class

    /** Given a maze (specified by a 2D integer array, and start and end
     * Coordinate instances), return a path (beginning with the start
     * coordinate, and terminating wih the end coordinate), that legally
     * traverses the maze from the start to end coordinates.  If no such
     * path exists, returns an empty list.  The path need need not be a
     * "shortest path".
     *
     * @param maze 2D int array whose "0" entries are interpreted as
     * "coordinates that can be navigated to in a maze traversal (can be
     * part of a maze path)" and "1" entries are interpreted as
     * "coordinates that cannot be navigated to (part of a maze wall)".
     * @param start maze navigation must begin here, must have a value
     * of "0"
     * @param end maze navigation must terminate here, must have a value
     * of "0"
     * @return a path, beginning with the start coordinate, terminating
     * with the end coordinate, and intervening elements represent a
     * legal navigation from maze start to maze end.  If no such path
     * exists, returns an empty list.  A legal navigation may only
     * traverse maze coordinates, may not contain coordinates whose
     * value is "1", may only traverse from a coordinate to one of its
     * immediate neighbors using one of the standard four compass
     * directions (no diagonal movement allowed).  A legal path may not
     * contain a cycle.  It is legal for a path to contain only the
     * start coordinate, if the start coordinate is equal to the end
     * coordinate.
     */
    public static
    List<Coordinate> searchMaze
    (final int[][] maze, final Coordinate start, final Coordinate end) {
        validateInput(maze, start, end);

        List<Coordinate> returnListStack = new LinkedList<>();
        if(start.equals(end)){
            returnListStack.add(start);
        }else{
            returnListStack = bfs(returnListStack, maze, start, end);
        }
        return returnListStack;
    }

    private static void validateInput(int[][] maze, Coordinate start, Coordinate end) {
        if(maze.length <= 0 || start == null || end == null){
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < maze.length; i++) {
            if(maze[i].length <= 0){
                throw new IllegalArgumentException();
            }
        }
    }

    private static List<Coordinate> bfs(List<Coordinate> returnListStack, int[][] maze, Coordinate start, Coordinate end) {
        Queue<Coordinate> q = new LinkedList<>();
        Map<Coordinate,Boolean> marked = new HashMap<>();
        Map<Coordinate,Coordinate> edgeFrom = new HashMap<>();
        List<Coordinate> returnList = new LinkedList<>();
        boolean solved = false;
        q.add(start);
        marked.put(start, true);

        while(!q.isEmpty() && !solved){
            Coordinate c = q.poll();
            for(Coordinate adjC : getAdj(c, maze)){
                if((!marked.getOrDefault(adjC,false)) && (maze[adjC.x][adjC.y] == 0) && !solved){
                    q.add(adjC);
                    marked.put(adjC,true);
                    edgeFrom.put(adjC,c);
                    if(adjC.equals(end)){
                        solved = true;
                        break;
                    }
                }
            }
        }

        if(solved){
            Stack<Coordinate> s = new Stack<>();
            boolean done = false;
            Coordinate c = end;
            while(!done){
                s.add(c);
                c = edgeFrom.get(c);
                if (c != null && c.equals(start)){
                    s.add(start);
                    done = true;
                }
                if (c == null){
                    done = true;
                }
            }
            while(s.size() != 0){
                returnList.add(s.pop());
            }
        }

        return returnList;
    }

    private static List<Coordinate> getAdj(Coordinate c, int[][] maze) {
        List<Coordinate> returnList = new LinkedList<>();
        //(x+1,y)
        if((c.x+1) <= maze.length-1){
            returnList.add(new Coordinate(c.x+1,c.y));
        }
        //(x-1,y)
        if((c.x-1) >= 0){
            returnList.add(new Coordinate(c.x-1,c.y));
        }
        //(x,y+1)
        if((c.y+1) <= maze[c.x].length-1){
            returnList.add(new Coordinate(c.x,c.y+1));
        }
        //(x,y-1)
        if((c.y-1) >= 0){
            returnList.add(new Coordinate(c.x,c.y-1));
        }
        return returnList;
    }

    /** minimal main() demonstrates use of APIs
     */
    public static void main (final String[] args) {
        final int[][] exampleMaze = {
                {0, 0, 0},
                {0, 1, 1},
                {0, 1, 0}
        };

        final Coordinate start = new Coordinate(2, 0);
        final Coordinate end = new Coordinate(0, 2);
        final List<Coordinate> path = searchMaze(exampleMaze, start, end);
        System.out.println("path="+path);
    }

}