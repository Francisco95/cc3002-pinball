package gui.table;

import com.almasb.fxgl.entity.Entity;
import facade.HomeworkTwoFacade;
import gui.components.BumperComponent;
import gui.components.TargetComponent;
import logic.gameelements.bumper.Bumper;
import logic.gameelements.target.Target;

import java.util.List;

/**
 * definition of a game table with the game elements in random positions
 *
 * @author Francisco Munoz Ponce
 */
public class TableRandomPositions extends GameTable {

    private HomeworkTwoFacade facade;

    public TableRandomPositions(HomeworkTwoFacade facade){
        super();
        this.facade = facade;

    }
    @Override
    public void setTableElements() {

        List<Target> targets = facade.getTargets();
        List<Bumper> bumpers = facade.getBumpers();
        int numberOfHittables = targets.size() + bumpers.size();
        Coordinates[] usedPositions = Coordinates.pickNRandomTuple(positions, numberOfHittables);
        Entity entity;
        int i = 0;
        while (i < bumpers.size()){
            entity = spawnGameElement(usedPositions[i].getX(), usedPositions[i].getY(), bumpers.get(i).getType());
            entity.addComponent(new BumperComponent(bumpers.get(i)));
            addBumper(entity);
            i++;
        }
        while (i < numberOfHittables){
            entity = spawnGameElement(usedPositions[i].getX(), usedPositions[i].getY(),
                    targets.get(i - bumpers.size()).getType());
            entity.addComponent(new TargetComponent(targets.get(i - bumpers.size())));
            addTarget(entity);
            i++;
        }
    }

    public void setFacade(HomeworkTwoFacade facade){
        this.facade = facade;
    }
}
