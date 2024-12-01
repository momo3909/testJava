package Simplex.Controller;

import Simplex.Model.DataAccess.PositionLoader;
import Simplex.Model.Position;

import java.util.List;

public class PositionRepository extends DataRepository {
    private List<Position> positionList;

    public void loadPositions() {
        PositionLoader positionLoader = new PositionLoader();
        positionList = positionLoader.load();
        this.positionList = positionList.stream().sorted((p1, p2) -> p1.getTicker().compareTo(p2.getTicker())).toList();
    }

    public List<Position> getPositionList() {
        return positionList;
    }

    public Position findPositionByTicker(String ticker) {
        return positionList.stream().filter(position -> position.getTicker().equals(ticker)).findFirst().orElse(null);
    }

}
