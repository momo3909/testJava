package Simplex.View;

import Simplex.Controller.MarketPriceRepository;
import Simplex.Model.MarketPrice;
import Simplex.Validation.MarketPriceValidation;

import java.util.Scanner;

public class AddMarketPrice {
    private final MarketPriceRepository marketPriceRepository = new MarketPriceRepository();
    Scanner scanner = new Scanner(System.in);

    public void play() {
        String ticker = inputTicker();
        double marketPrice = inputMarketPrice();
        marketPriceRepository.loadMarketPrices("src/main/resource/MarketPriceMasterFile.txt");

        if (marketPriceRepository.findMarketPrice(ticker) != null) {
            MarketPrice marketPriceObj = new MarketPrice(ticker, marketPrice);
            marketPriceRepository.fixMarketPrice(marketPriceObj);
            marketPriceRepository.saveMarketPrice("src/main/resource/MarketPriceMasterFile.txt");
        } else {
            MarketPrice marketPriceObj = new MarketPrice(ticker, marketPrice);
            marketPriceRepository.getMarketPriceList().add(marketPriceObj);
            marketPriceRepository.saveMarketPrice("src/main/resource/MarketPriceMasterFile.txt");
        }
        System.out.printf("%sの時価情報を時価情報マスタに新規登録しました。%n", ticker);
    }


    private String inputTicker() {
        while (true) {
            System.out.print("銘柄コード>");
            String ticker = scanner.nextLine();
            if (MarketPriceValidation.tickerValidation(ticker)) {
                return ticker;
            }
        }
    }

    private double inputMarketPrice() {
        while (true) {
            System.out.print("現在値>");
            String stringMarketPrice = scanner.nextLine();
            if (MarketPriceValidation.marketPriceValidation(stringMarketPrice)) {
                return Double.parseDouble(stringMarketPrice);
            }
        }
    }
}
