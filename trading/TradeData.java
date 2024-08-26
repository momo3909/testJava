package simplex.bn25.momonoi.trading;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class TradeData {
    private final List<Trade> trades = new ArrayList<>();

    void add(Trade trade) {trades.add(trade);}

    void displayAll(){
        for (Trade trade: trades) trade.displayAll();
    }

    void displayTradeData(){
        List<Trade> sortedTrades = sortTradeData(); //TradedDatetimeでソート
        System.out.println("|" + "=".repeat(132) + "|");
        System.out.printf("| %20s | %6s | %30s | %8s | %10s | %18s | %20s | %n", "Traded Datetime", "Ticker", "Product Name", "Side", "Quantity", "Traded Unit Price", "Input Datetime");
        System.out.println("|" + "-".repeat(22) + "+" + "-".repeat(8) + "+" + "-".repeat(32) + "+" + "-".repeat(10) + "+" + "-".repeat(12) + "+" + "-".repeat(20) + "+" + "-".repeat(22) + "|");
        for (Trade trade: sortedTrades) {
            String dispProductName = (trade.getProductName().length() <= 30) ? trade.getProductName() : (trade.getProductName().substring(0, 27) + "...");  //文字数の指定
            System.out.printf("| %20s | %6s | %-30s | %-8s | %10s | %18s | %20s | %n", trade.getTradedDatetime(), trade.getTicker(), dispProductName, trade.getSide(), trade.getQuantity(), trade.getTradedUnitPrice(), trade.getInputDatetime());
            }
        System.out.println("|" + "=".repeat(132) + "|");
    }

    List<Trade> sortTradeData(){
        List<Trade> sortedTrades =
                trades.stream()
                        .sorted((trade1, trade2) -> trade2.getTradedDatetime()
                                .compareTo(trade1.getTradedDatetime()))
                        .toList();
        return sortedTrades;
    }
}
