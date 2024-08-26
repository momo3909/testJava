package simplex.bn25.momonoi.trading;

import java.util.ArrayList;
import java.util.List;

public class StockData {
    private final List<Stock> stocks = new ArrayList<>();

    void add(Stock stock) {stocks.add(stock);}

    boolean equalStock(String ticker){
        for (Stock stock : stocks) {
            if (stock.getTicker().equals(ticker)) return true;
        }
        return false;
    }

    Stock findStock(String ticker) throws StockNotFoundException{
        for (Stock stock : stocks) {
            if (stock.getTicker().equals(ticker)) return stock;
        }
        throw new StockNotFoundException();
    }

    void displayAll(){
        for (Stock stock: stocks) stock.displayAll();
    }

    void displayStockData(){
        System.out.println("|" + "=".repeat(70) + "|");
        System.out.printf("| %.6s | %-30s | %-8s | %-15s | %n", "Ticker", "Product Name", "Market", "Shares Issued");
        System.out.println("|" + "-".repeat(8) + "+" + "-".repeat(32) + "+" + "-".repeat(10) + "+" + "-".repeat(17) + "|");
        for (Stock stock: stocks) {
            String dispProductName = (stock.getProductName().length() <= 30) ? stock.getProductName() : (stock.getProductName().substring(0, 27) + "...");  //文字数の指定
            System.out.printf("| %6s | %-30s | %-8s | %,15d | %n", stock.getTicker(), dispProductName, stock.getMarket(), stock.getSharesIssued());
            }
        System.out.println("|" + "=".repeat(70) + "|");
    }

}
