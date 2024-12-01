package Validation;

import Data.Position;
import Data.Trade;
import Repository.TradeRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PositionValidation {
    static TradeRepository tradeRepository = new TradeRepository();

    public static boolean tradedDatetimeValidation(String tradedDateTime, String ticker) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
        tradeRepository.loadTrades();
        List<Trade> positionTrades = tradeRepository.findTradesByTicker(ticker);  //昇順
        LocalDateTime latestTradedDateTime = positionTrades.getLast().getTradedDatetime();

        if(LocalDateTime.parse(tradedDateTime,formatter).isBefore(latestTradedDateTime) || LocalDateTime.parse(tradedDateTime,formatter).equals(latestTradedDateTime)) {
            System.out.printf("最新の取引日時(%s)より後の日付を入力してください。%n", latestTradedDateTime.format(formatter));
            return false;
        }
        return true;
    }

    public static boolean tradeSideValidation(String tradeSide, String ticker) {
        tradeRepository.loadTrades();
        List<Trade> positionTrades = tradeRepository.findTradesByTicker(ticker);  //昇順
        if(positionTrades.isEmpty() && tradeSide.equals("S")) {
            System.out.println("保有していない銘柄に対して売却取引はできません。");
            return false;
        }
        return true;
    }

    public static boolean quantityValidation(String stringQuantity, String ticker, String tradeSide) {
        tradeRepository.loadTrades();
        List<Trade> positionTrades = tradeRepository.findTradesByTicker(ticker);  //昇順
        //保有数量の計算
        long quantity = 0;
        for (Trade trade : positionTrades) {
            if (trade.getTradeSide() == Trade.TradeSide.Buy) {
                quantity += trade.getQuantity();
            } else {
                quantity -= trade.getQuantity();
            }
        }
        if(tradeSide.equals("B")) {
            quantity += Long.parseLong(stringQuantity);
        } else {
            quantity -= Long.parseLong(stringQuantity);
        }
        if(quantity < 0) {
            System.out.println("保有数量を超える売却はできません。");
            return false;
        }
        return true;
    }
}
