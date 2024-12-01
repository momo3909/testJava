package Validation;

import java.math.BigDecimal;

public class MarketPriceValidation {
    public static boolean tickerValidation(String ticker) {
        return StockValidation.tickerValidation(ticker);
    }

    public static boolean priceValidation(String price) {
        try {
            BigDecimal priceBigDecimal = new BigDecimal(price);
            if(priceBigDecimal.subtract(BigDecimal.ZERO).doubleValue() < 0){
                System.out.println("価格は0以上で入力してください。");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("価格は数値で入力してください。");
            return false;
        }
        return true;
    }
}
