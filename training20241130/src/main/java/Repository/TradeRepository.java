package Repository;

import Data.Trade;
import FileReader.TradeMasterFileReader;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class TradeRepository {
    TradeMasterFileReader tradeMasterFileReader = new TradeMasterFileReader("src/main/resources/TradeMasterFile.txt");
    private List<Trade> tradeList;

    public void loadTrades() {
        tradeList = tradeMasterFileReader.readFile();
    }

    public List<Trade> getTradeList() {
        return tradeList;
    }

    public List<String> getTickers() {
        return tradeList.stream().map(Trade::getTicker).distinct().toList();
    }

    public void sortTradedDatetime() {
        tradeList.sort(Comparator.comparing(Trade::getTradedDatetime));
    }

    public void sortReverseTradedDatetime() {
        tradeList.sort(Comparator.comparing(Trade::getTradedDatetime).reversed());
    }

    public void sortTicker() {
        tradeList.sort(Comparator.comparing(Trade::getTicker));
    }

    public void sortReverseTicker() {
        tradeList.sort(Comparator.comparing(Trade::getTicker).reversed());
    }

    public void sortQuantity(){
        tradeList.sort(Comparator.comparing((Trade trade) -> trade.getQuantity() * trade.getTradedUnitPrice().intValue()).reversed());
    }

    public List<Trade> findTradesByTicker(String ticker) {
        return tradeList.stream().filter(trade -> trade.getTicker().equals(ticker)).
                sorted(Comparator.comparing(Trade::getTradedDatetime)).toList();
    }
}
