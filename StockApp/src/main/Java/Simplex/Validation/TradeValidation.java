package Simplex.Validation;

import Simplex.Controller.PositionRepository;
import Simplex.Controller.StockRepository;
import Simplex.Controller.TradeRepository;
import Simplex.Model.Position;
import Simplex.Model.TradeSide;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TradeValidation {
    public static boolean checkLoadTrade(String[] data) {
        if (data.length != 6) {
            System.out.println("データの個数が不正です。");
            return false;
        }
        String stringTradedDatetime = data[0];
        String ticker = data[1];
        String stringSide = data[2];
        String stringQuantity = data[3];
        String stringTradedUnitPrice = data[4];
        String stringInputDatetime = data[5];

        if (!tradedDatetimeValidation(stringTradedDatetime) || !tickerValidation(ticker) || !tradeSideValidation(stringSide) || !quantityValidation(stringQuantity) || !tradedUnitPriceValidation(stringTradedUnitPrice)) {
            return false;
        }

        return true;
    }

    public static boolean tickerValidation(String ticker) {
        if (!StockValidation.tickerValidation(ticker)) {
            return false;
        }
        StockRepository stockRepository = new StockRepository();
        stockRepository.loadStocks("src/main/resource/StockMasterFile.txt");
        if (stockRepository.findStock(ticker) == null) {
            System.out.println("銘柄コードが存在しません。");
            return false;
        }
        return true;
    }

    public static boolean tradedDatetimeValidation(String stringTradedDatetime) {
        LocalDateTime tradedDatetime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
        try {
            tradedDatetime = LocalDateTime.parse(stringTradedDatetime, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("取引日時はyyyy-MM-dd-HH:mm形式で入力してください。");
            return false;
        }

        DayOfWeek dayOfWeek = tradedDatetime.getDayOfWeek();
        LocalTime time = tradedDatetime.toLocalTime();
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(15, 30);

        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            System.out.println("取引日時は平日で入力してください。");
            return false;
        }
        if (time.isBefore(startTime) || time.isAfter(endTime)) {
            System.out.println("取引日時は9:00から15:30の間で入力してください。");
            return false;
        }
        return true;
    }

    public static boolean addTradedDatetimeValidation(String stringTradedDatetime, String ticker) {
        if (!tradedDatetimeValidation(stringTradedDatetime)) {
            return false;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
        LocalDateTime tradedDatetime = LocalDateTime.parse(stringTradedDatetime, formatter);

        TradeRepository tradeRepository = new TradeRepository();
        tradeRepository.loadTrades("src/main/resource/TradeMasterFile.txt");
        LocalDateTime lastTradeDatetime = tradeRepository.getLastTradedDatetime(ticker);
        if (lastTradeDatetime == null) {
            return true;
        }
        if (tradedDatetime.isBefore(lastTradeDatetime) || tradedDatetime.equals(lastTradeDatetime)) {
            System.out.printf("取引日時は前回の取引日時(%s)以降で入力してください。%n", lastTradeDatetime.format(formatter));
            return false;
        }
        if (tradedDatetime.isAfter(LocalDateTime.now())) {
            System.out.println("取引日時は現在時刻以前で入力してください。");
            return false;
        }
        return true;
    }

    public static boolean tradeSideValidation(String stringSide) {
        if (!"B".equals(stringSide) && !"S".equals(stringSide)) {
            System.out.println("取引区分はBまたはSで入力してください。");
            return false;
        }
        return true;
    }

    public static boolean addTradeSideValidation(String stringSide, String ticker) {
        if (!tradeSideValidation(stringSide)) {
            return false;
        }
        TradeRepository tradeRepository = new TradeRepository();
        tradeRepository.loadTrades("src/main/resource/TradeMasterFile.txt");
        TradeSide lastTradeSide = tradeRepository.getTradeList().stream().filter(trade -> trade.getTicker().equals(ticker)).findFirst().orElse(null).getTradeSide();
        if (lastTradeSide == null && "S".equals(stringSide)) {
            System.out.println("保有していない銘柄に対して売却取引はできません。");
            return false;
        }
        return true;
    }

    public static boolean quantityValidation(String stringQuantity) {
        int quantity;
        try {
            quantity = Integer.parseInt(stringQuantity);
            if (quantity <= 0) {
                System.out.println("数量は1以上で入力してください。");
                return false;
            }
            if (quantity % 100 != 0) {
                System.out.println("数量は100の倍数で入力してください。");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("数量は半角数字で入力してください。");
            return false;
        }
        return true;
    }

    public static boolean addQuantityValidation(String stringQuantity, String ticker, TradeSide tradeSide) {
        if (!quantityValidation(stringQuantity)) {
            return false;
        }
        int quantity = tradeSide == TradeSide.BUY ? Integer.parseInt(stringQuantity) : -Integer.parseInt(stringQuantity);

        PositionRepository positionRepository = new PositionRepository();
        positionRepository.loadPositions();
        Position findedPosition = positionRepository.findPositionByTicker(ticker);
        if (findedPosition == null && tradeSide == TradeSide.BUY) {
            return true;
        }
        int positionQuantity = findedPosition.getQuantity();
        if (positionQuantity == 0 && tradeSide == TradeSide.SELL) {
            System.out.println("ポジション数が0の銘柄に対して売却取引はできません。");
            return false;
        }
        if (positionQuantity + quantity < 0) {
            System.out.println("ポジション数が0未満になる取引はできません。");
            return false;
        }
        return true;
    }

    public static boolean tradedUnitPriceValidation(String stringTradedUnitPrice) {
        try {
            double tradedUnitPrice = Double.parseDouble(stringTradedUnitPrice);
            if (tradedUnitPrice <= 0) {
                System.out.println("取引単価は0より大きい値で入力してください。");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("取引単価は半角数字で入力してください。");
            return false;
        }
        return true;
    }
}
