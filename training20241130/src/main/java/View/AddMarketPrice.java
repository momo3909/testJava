package View;

import Data.MarketPrice;
import FileReader.MarketPriceMasterFileReader;
import Repository.MarketPriceRepository;
import Validation.MarketPriceValidation;

import java.math.BigDecimal;
import java.util.Scanner;

public class AddMarketPrice {
    private final MarketPriceRepository marketPriceRepository = new MarketPriceRepository();
    Scanner scanner = new Scanner(System.in);

    public void add() {
        marketPriceRepository.loadMarketPrices();
        String ticker = inputTicker();
        String stringMarketPrice = inputMarketPrice();

        //すでに登録されている銘柄の場合は更新、新規の場合は追加
        if (marketPriceRepository.findMarketPrice(ticker) != null) {
            marketPriceRepository.updateMarketPrice(new MarketPrice(ticker, new BigDecimal(stringMarketPrice)));
        } else {
            marketPriceRepository.addMarketPrice(new MarketPrice(ticker, new BigDecimal(stringMarketPrice)));
        }

        MarketPriceMasterFileReader marketPriceMasterFileReader = new MarketPriceMasterFileReader("src/main/resources/MarketPriceMasterFile.txt");
        marketPriceMasterFileReader.addMarketPrice(marketPriceRepository);

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

    private String inputMarketPrice() {
        while (true) {
            System.out.print("現在値>");
            String stringMarketPrice = scanner.nextLine();
            if (MarketPriceValidation.priceValidation(stringMarketPrice)) {
                return stringMarketPrice;
            }
        }
    }
}
