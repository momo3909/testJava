package View;

import FileReader.StockMasterFileReader;
import Validation.StockValidation;

import java.util.Scanner;

public class AddStock {
    Scanner scanner = new Scanner(System.in);

    public void add() {
        String ticker = inputTicker();
        String productName = inputProductName();
        String marketType = inputMarketType();
        String listingDate = inputListingDate();
        String sharesIssued = inputSharesIssued();

        StockMasterFileReader stockMasterFileReader = new StockMasterFileReader("src/main/resources/StockMasterFile.txt");
        stockMasterFileReader.addStock(ticker, productName, marketType, listingDate,sharesIssued);

        System.out.printf("%sを銘柄マスタに新規登録しました。%n", productName);
    }

    public String inputTicker() {
        while (true) {
            System.out.print("銘柄コード>");
            String ticker = scanner.nextLine();
            if (StockValidation.tickerValidation(ticker)) {
                return ticker;
            }
        }
    }

    public String inputProductName() {
        while (true) {
            System.out.print("会社名>");
            String productName = scanner.nextLine();
            if (StockValidation.productNameValidation(productName)) {
                return productName;
            }
        }
    }

    public String inputMarketType() {
        while (true) {
            System.out.print("市場区分(P,S,G,PROのいずれか)>");
            String marketType = scanner.nextLine();
            if (StockValidation.marketTypeValidation(marketType)) {
                return marketType;
            }
        }
    }

    public String inputListingDate() {
        while (true) {
            System.out.print("上場日(yyyy-MM-dd)>");
            String listingDate = scanner.nextLine();
            if (StockValidation.listingDateValidation(listingDate)) {
                return listingDate;
            }
        }
    }

    public String inputSharesIssued() {
        while (true) {
            System.out.print("発行済株式数>");
            String sharesIssued = scanner.nextLine();
            if (StockValidation.sharesIssuedValidation(sharesIssued)) {
                return sharesIssued;
            }
        }
    }
}
