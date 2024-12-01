package Simplex.View;

import Simplex.Controller.StockRepository;
import Simplex.Model.MarketType;
import Simplex.Model.Stock;
import Simplex.Validation.StockValidation;

import java.util.Scanner;

public class AddStock {
    private final StockRepository stockRepository = new StockRepository();
    Scanner scanner = new Scanner(System.in);

    public void play() {
        String productName = inputProductName();
        String ticker = inputTicker();
        MarketType marketType = inputMarketType();
        long sharesIssued = inputSharesIssued();

        Stock stock = new Stock(ticker, productName, marketType, sharesIssued);
        stockRepository.saveStock(stock, "src/main/resource/StockMasterFile.txt");

        System.out.printf("%sを銘柄マスタに新規登録しました。%n", productName);
    }

    private String inputProductName() {
        while (true) {
            System.out.print("銘柄名>");
            String productName = scanner.nextLine();
            if (StockValidation.productNameValidation(productName)) {
                return productName;
            }
        }
    }

    private String inputTicker() {
        while (true) {
            System.out.print("銘柄コード>");
            String ticker = scanner.nextLine();
            if (StockValidation.tickerValidation(ticker)) {
                return ticker;
            }
        }
    }

    private MarketType inputMarketType() {
        while (true) {
            System.out.print("市場区分>");
            String stringMarketType = scanner.nextLine();
            MarketType marketType = stringMarketType.equals("Prime") ? MarketType.Prime : stringMarketType.equals("Standard") ? MarketType.Standard : stringMarketType.equals("Growth") ? MarketType.Growth : null;
            if (marketType != null) {
                return marketType;
            }
            System.out.println("市場区分はPrime, Standard, Growthのいずれかで入力してください。");
        }
    }

    private long inputSharesIssued() {
        while (true) {
            System.out.print("発行済株式数>");
            String sharesIssued = scanner.nextLine();
            if (StockValidation.sharesIssuedValidation(sharesIssued)) {
                return Long.parseLong(sharesIssued);
            }
        }
    }
}
