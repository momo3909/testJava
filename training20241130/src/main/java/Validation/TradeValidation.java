package Validation;

import Repository.StockRepository;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TradeValidation {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");

    public static boolean tradedDatetimeValidation(String stringTradedDatetime) {
        LocalDateTime tradedDatetime;
        try{
            tradedDatetime = LocalDateTime.parse(stringTradedDatetime, formatter);
        }catch (DateTimeParseException e){
            System.out.println("取引日時はyyyy-MM-dd-HH:mm形式で入力してください。");
            return false;
        }
        if(tradedDatetime.isAfter(LocalDateTime.now())){
            System.out.println("取引日時は現在時刻以前で入力してください。");
            return false;
        }

        DayOfWeek dayOfWeek = tradedDatetime.getDayOfWeek();
        if(dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY){
            System.out.println("取引日時は平日で入力してください。");
            return false;
        }
        LocalTime openedTime = LocalTime.of(9, 0);
        LocalTime closedTime = LocalTime.of(15, 30);
        if(tradedDatetime.toLocalTime().isBefore(openedTime) || tradedDatetime.toLocalTime().isAfter(closedTime)){
            System.out.println("取引日時は9:00から15:30の間で入力してください。");
            return false;
        }
        return true;
    }

    public static boolean tickerValidation(String ticker){
        StockRepository stockRepository = new StockRepository();
        stockRepository.loadStocks();
        if(!StockValidation.tickerValidation(ticker)){
            return false;
        }
        if(stockRepository.findStock(ticker) == null){
            System.out.printf("銘柄コード[%s]は存在しません。%n", ticker);
            return false;
        }
        return true;
    }

    public static boolean tradeSideValidation(String tradeSide){
        if(tradeSide.equals("B") || tradeSide.equals("S")){
            return true;
        }
        System.out.println("売買区分はBまたはSで入力してください。");
        return false;
    }

    public static boolean quantityValidation(String quantity){
        int intQuantity;
        try{
            intQuantity = Integer.parseInt(quantity);
        }catch (NumberFormatException e){
            System.out.println("数量は数値で入力してください。");
            return false;
        }
        if(intQuantity <= 0){
            System.out.println("数量は100以上で入力してください。");
            return false;
        }
        if(intQuantity%100 != 0){
            System.out.println("数量は100の倍数で入力してください。");
            return false;
        }
        return true;
    }

    public static boolean tradedUnitPriceValidation(String tradedUnitPrice){
        double doubleTradedUnitPrice;
        try{
            doubleTradedUnitPrice = Double.parseDouble(tradedUnitPrice);
        }catch (NumberFormatException e){
            System.out.println("取引単価は数値で入力してください。");
            return false;
        }
        if(doubleTradedUnitPrice <= 0){
            System.out.println("取引単価は0より大きい値で入力してください。");
            return false;
        }
        return true;
    }
}
