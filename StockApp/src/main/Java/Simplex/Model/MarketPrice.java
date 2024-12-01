package Simplex.Model;

public class MarketPrice extends MasterFile {
    private double marketPrice;

    public MarketPrice(String ticker, double price) {
        super(ticker);
        this.marketPrice = price;
    }

    public double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        this.marketPrice = marketPrice;
    }

    @Override
    public String toString() {
        return String.format("銘柄コード：%s, 現在値：%f", getTicker(), marketPrice);
    }
}
