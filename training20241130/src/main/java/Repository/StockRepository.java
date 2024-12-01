package Repository;

import Data.Stock;
import FileReader.StockMasterFileReader;

import java.util.List;

public class StockRepository {
    StockMasterFileReader stockMasterFileReader = new StockMasterFileReader("src/main/resources/StockMasterFile.txt");
    private List<Stock> stockList;

    public void loadStocks() {
        stockList = stockMasterFileReader.readFile();
    }

    public List<Stock> getStockList() {
        return stockList;
    }

    public Stock findStock(String ticker) {
        for (Stock stock : stockList) {
            if (stock.getTicker().equals(ticker)) {
                return stock;
            }
        }
        return null;
    }
}
