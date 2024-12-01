package View;

import Data.Stock;
import Repository.StockRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;

public class ShowStockDetails {
    Scanner scanner = new Scanner(System.in);

    public void show() {
        StockRepository stockRepository = new StockRepository();
        stockRepository.loadStocks();

        while (true) {
            System.out.print("銘柄コードを入力してください。>");
            String inputTicker = scanner.nextLine();
            if (stockRepository.findStock(inputTicker) != null) {
                System.out.println("銘柄が見つかりました。");
                showStockDetails(stockRepository.findStock(inputTicker));
                break;
            } else {
                System.out.println("銘柄コードが存在しません。");
                continue;
            }
        }
    }
    void showStockDetails(Stock stock) {
        String ticker = stock.getTicker();
        String productName = stock.getProductName();
        Stock.Market market = stock.getMarket();
        LocalDate listingDate = stock.getListingDate();
        String dispMarket = switch (market) {
            case Stock.Market.Prime -> "東証プライム";
            case Stock.Market.Standard -> "東証スタンダード";
            case Stock.Market.Growth -> "東証グロース";
            case Stock.Market.TokyoPRO -> "Tokyo PRO Market";
        };
        long sharesIssued = stock.getSharesIssued();

        System.out.printf("""
                  銘柄コード：%s
                     銘柄名：%s
                   上場市場：%s
                     上場日：%s
                発行済株式数：%,d株
                """,
                ticker, productName, dispMarket, listingDate, sharesIssued);

    }
}
