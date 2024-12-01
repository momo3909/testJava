package Simplex.Controller;

import Simplex.Model.DataAccess.MarketPriceLoader;
import Simplex.Model.MarketPrice;

import java.util.List;

public class MarketPriceRepository extends DataRepository {
    private List<MarketPrice> marketPriceList;

    public List<MarketPrice> getMarketPriceList() {
        return marketPriceList;
    }

    public MarketPrice findMarketPrice(String ticker) {
        return marketPriceList.stream().filter(marketPrice -> marketPrice.getTicker().equals(ticker)).findFirst().orElse(null);
    }

    public void fixMarketPrice(MarketPrice marketPrice) {
        MarketPrice targetMarketPrice = findMarketPrice(marketPrice.getTicker());
        targetMarketPrice.setMarketPrice(marketPrice.getMarketPrice());
    }

    public void loadMarketPrices(String filePath) {
        MarketPriceLoader marketPriceLoader = new MarketPriceLoader(filePath);
        marketPriceList = marketPriceLoader.load();
    }

    public void saveMarketPrice(String filePath) {
        MarketPriceLoader marketPriceLoader = new MarketPriceLoader(filePath);
        marketPriceLoader.save(marketPriceList);
    }
}
