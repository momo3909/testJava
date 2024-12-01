package Simplex.Model;

public class Stock extends MasterFile {
    private String productName;
    private MarketType marketType;
    private long sharesIssued;

    public Stock(String ticker, String productName, MarketType marketType, long sharesIssued) {
        super(ticker);
        this.productName = productName;
        this.marketType = marketType;
        this.sharesIssued = sharesIssued;
    }

    public MarketType getMarketType() {
        return marketType;
    }

    public long getSharesIssued() {
        return sharesIssued;
    }

    public String getProductName() {
        return productName;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "ticker='" + getTicker() + '\'' +
                ", productName='" + getProductName() + '\'' +
                ", marketType=" + marketType +
                ", sharesIssued=" + sharesIssued +
                '}';
    }
}
