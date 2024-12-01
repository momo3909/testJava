package View;

import Data.MarketPrice;
import Repository.MarketPriceRepository;

public class ShowMarketPrice {
    MarketPriceRepository marketPriceRepository = new MarketPriceRepository();

    public void show() {
        marketPriceRepository.loadMarketPrices();
        System.out.println("|" + "=".repeat(26) + "|");
        System.out.printf("| %6s | %15s | %n", "Ticker", "Market Price");
        System.out.println("|" + "-".repeat(8) + "|" + "-".repeat(17) + "|");
        for (MarketPrice marketPrice : marketPriceRepository.getMarketPriceList()) {
            System.out.printf("| %6s | %,15.3f | %n", marketPrice.getTicker(), marketPrice.getPrice());
        }
        System.out.println("|" + "=".repeat(26) + "|");
    }
}
