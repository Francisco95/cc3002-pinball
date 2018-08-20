package gui.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Auxiliar class used for define coordinates on the table to set the positions of the game elements
 *
 * @author Francisco Munoz Ponce
 */
public class Coordinates {
    private double x;
    private double y;

    public Coordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates(int i, int j, double step){
        this.x = (i+1) * step;
        this.y = (j+1) * step;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public static Coordinates[] pickNRandomTuple(List<Coordinates> coords, int n) {

        Collections.shuffle(coords);
        Coordinates[] answer = new Coordinates[n];
        for (int i = 0; i < n; i++)
            answer[i] = coords.get(i % coords.size());

        return answer;

    }
    public static List<Coordinates> arrayOfPositions(double totalSpaceWidth, double totalSpaceHeight, double step){
        int nPositionsInX = (int)((totalSpaceWidth - 2*step) / step);
        int nPositionsInY = (int)((totalSpaceHeight - 2*step) / step);
        List<Coordinates> positions = new ArrayList<>();
        for (int i=0; i < nPositionsInX; i++){
            for (int j=0; j < nPositionsInY; j++){
                positions.add(new Coordinates(i, j, step));
            }
        }
        return positions;
    }
}
