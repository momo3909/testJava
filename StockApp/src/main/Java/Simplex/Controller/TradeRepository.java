package Simplex.Controller;

import Simplex.Model.DataAccess.TradeLoader;
import Simplex.Model.Trade;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class TradeRepository extends DataRepository {
    private List<Trade> tradeList;

    public List<Trade> getTradeList() {
        return tradeList;
    }

    public void loadTrades(String filePath) {
        TradeLoader tradeLoader = new TradeLoader(filePath);
        tradeList = tradeLoader.load();
    }

    public void saveTrade(Trade trade, String filePath) {
        TradeLoader tradeLoader = new TradeLoader(filePath);
        tradeLoader.save(trade);
    }

    public String getProductName(String ticker) {
        return tradeList.stream().filter(trade -> trade.getTicker().equals(ticker)).findFirst().orElse(null).getProductName();
    }

    public LocalDateTime getLastTradedDatetime(String ticker) {
        return tradeList.stream().filter(trade -> trade.getTicker().equals(ticker)).map(Trade::getTradedDatetime).max(LocalDateTime::compareTo).orElse(null);
    }

    public void sortTradedDatetime() {
        tradeList.sort(Comparator.comparing(Trade::getTradedDatetime));
    }

    public void sortTicker() {
        tradeList.sort(Comparator.comparing(Trade::getTicker));
    }

    public void sortReverseTradedDatetime() {
        tradeList.sort(Comparator.comparing(Trade::getTradedDatetime).reversed());
    }

    public void sortReverseTicker() {
        tradeList.sort(Comparator.comparing(Trade::getTicker).reversed());
    }

    public List<String> getTickers() {
        return tradeList.stream().map(Trade::getTicker).distinct().toList();
    }
}
