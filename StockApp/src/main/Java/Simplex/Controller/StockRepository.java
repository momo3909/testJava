package Simplex.Controller;

import Simplex.Model.DataAccess.StockLoader;
import Simplex.Model.Stock;

import java.util.List;

public class StockRepository extends DataRepository {
    private List<Stock> stockList;

    public List<Stock> getStockList() {
        return stockList;
    }

    public Stock findStock(String ticker) {
        return stockList.stream().filter(stock -> stock.getTicker().equals(ticker)).findFirst().orElse(null);
    }

    public void loadStocks(String filePath) {
        StockLoader stockLoader = new StockLoader(filePath);
        stockList = stockLoader.load();
    }

    public void saveStock(Stock stock, String filePath) {
        StockLoader stockLoader = new StockLoader(filePath);
        stockLoader.save(stock);
    }


}
