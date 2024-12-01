package Simplex.View;

import Simplex.Controller.MarketPriceRepository;
import Simplex.Model.MarketPrice;

public class ShowMarketPrice {
    MarketPriceRepository marketPriceRepository = new MarketPriceRepository();

    public void play() {
        marketPriceRepository.loadMarketPrices("src/main/resource/MarketPriceMasterFile.txt");
        System.out.println("|" + "=".repeat(26) + "|");
        System.out.printf("| %6s | %15s | %n", "Ticker", "Market Price");
        System.out.println("|" + "-".repeat(8) + "|" + "-".repeat(17) + "|");
        for (MarketPrice marketPrice : marketPriceRepository.getMarketPriceList()) {
            System.out.printf("| %6s | %,15.3f | %n", marketPrice.getTicker(), marketPrice.getMarketPrice());
        }
        System.out.println("|" + "=".repeat(26) + "|");
    }
}
