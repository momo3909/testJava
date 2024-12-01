package Simplex.View;

import Simplex.Controller.StockRepository;
import Simplex.Model.Stock;

public class ShowStockMaster {
    private final StockRepository stockRepository = new StockRepository();

    public void play() {
        stockRepository.loadStocks("src/main/resource/StockMasterFile.txt");
        System.out.println("|" + "=".repeat(70) + "|");
        System.out.printf("| %.6s | %-30s | %-8s | %-15s | %n", "Ticker", "Product Name", "Market", "Shares Issued");
        System.out.println("|" + "-".repeat(8) + "+" + "-".repeat(32) + "+" + "-".repeat(10) + "+" + "-".repeat(17) + "|");
        for (Stock stock : stockRepository.getStockList()) {
            String dispProductName = (stock.getProductName().length() <= 30) ? stock.getProductName() : (stock.getProductName().substring(0, 27) + "...");  //文字数の指定
            System.out.printf("| %6s | %-30s | %-8s | %,15d | %n", stock.getTicker(), dispProductName, stock.getMarketType(), stock.getSharesIssued());
        }
        System.out.println("|" + "=".repeat(70) + "|");
    }
}
