package Repository;

import Data.MarketPrice;
import FileReader.MarketPriceMasterFileReader;

import java.util.Comparator;
import java.util.List;

public class MarketPriceRepository {
    MarketPriceMasterFileReader marketPriceMasterFileReader = new MarketPriceMasterFileReader("src/main/resources/MarketPriceMasterFile.txt");
    private List<MarketPrice> marketPriceList;

    public void loadMarketPrices() {
        marketPriceList = marketPriceMasterFileReader.readFile();
    }

    public void addMarketPrice(MarketPrice marketPrice) {
        marketPriceList.add(marketPrice);
    }

    public void updateMarketPrice(MarketPrice oldMarketPrice) {
        MarketPrice marketPrice = findMarketPrice(oldMarketPrice.getTicker());
        marketPrice.setPrice(oldMarketPrice.getPrice());
    }

    public List<MarketPrice> getMarketPriceList() {
        return marketPriceList.stream().sorted(Comparator.comparing(MarketPrice::getTicker)).toList();
    }

    public MarketPrice findMarketPrice(String ticker) {
        return marketPriceList.stream().filter(marketPrice -> marketPrice.getTicker().equals(ticker)).findFirst().orElse(null);
    }
}
