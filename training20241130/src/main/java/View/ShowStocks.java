package View;

import Data.Stock;
import Repository.StockRepository;

public class ShowStocks {
    private final StockRepository stockRepository = new StockRepository();

    public void show() {
        stockRepository.loadStocks();
        System.out.println("|" + "=".repeat(88) + "|");
        System.out.printf("| %.6s | %-30s | %-8s | %-15s | %-15s | %n", "Ticker", "Product Name", "Market","Listing Date", "Shares Issued");
        System.out.println("|" + "-".repeat(8) + "+" + "-".repeat(32) + "+" + "-".repeat(10) + "+" + "-".repeat(17)+ "+" + "-".repeat(17) + "|");
        for (Stock stock : stockRepository.getStockList()) {
            String dispProductName = (stock.getProductName().length() <= 30) ? stock.getProductName() : (stock.getProductName().substring(0, 27) + "...");  //文字数の指定
            System.out.printf("| %6s | %-30s | %-8s | %15s | %,15d | %n", stock.getTicker(), dispProductName, stock.getMarket().toString(), stock.getListingDate(),stock.getSharesIssued());
        }
        System.out.println("|" + "=".repeat(88) + "|");
    }
}
