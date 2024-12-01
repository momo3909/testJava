package Repository;

import Data.Position;
import Data.PositionCalculator;
import Data.Trade;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PositionRepository {
    private List<Position> positionList = new ArrayList<>();
    TradeRepository tradeRepository = new TradeRepository();

    public void loadPositions() {
        PositionCalculator positionCalculator = new PositionCalculator();
        tradeRepository.loadTrades();
        List<String> tickers = tradeRepository.getTickers();
        for (String ticker : tickers) {
            positionList.add(positionCalculator.calculatePosition(ticker));
        }
    }

    public List<Position> getPositionList(){
        //positionList.sort(Comparator.comparing(Position::getTicker));  //sortは並び替えるだけで、新しいリストを返さない
        //return positionList;
        return positionList.stream().sorted(Comparator.comparing(Position::getTicker)).toList();  //新しいリストを返す
    }
}
