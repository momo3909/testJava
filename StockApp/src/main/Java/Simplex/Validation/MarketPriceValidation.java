package Simplex.Validation;

public class MarketPriceValidation {
    public static boolean checkLoadMarketPrice(String[] data) {
        if (data.length != 2) {
            System.out.println("データの個数が不正です。");
            return false;
        }
        String ticker = data[0];
        String marketPrice = data[1];

        if (!tickerValidation(ticker) || !marketPriceValidation(marketPrice)) {
            return false;
        }
        return true;
    }

    public static boolean tickerValidation(String ticker) {
        return TradeValidation.tickerValidation(ticker);
    }

    public static boolean marketPriceValidation(String marketPrice) {
        try {
            double doubleMarketPrice = Double.parseDouble(marketPrice);
            if (0 > doubleMarketPrice || doubleMarketPrice > 1000000) {
                System.out.println("市場価格は0以上1000000未満で入力してください。");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("市場価格は数値で入力してください。");
            return false;
        }
        return true;
    }
}
