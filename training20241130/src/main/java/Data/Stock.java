package Data;

import java.time.LocalDate;

public class Stock {
    public enum Market {
        Prime,Standard,Growth,TokyoPRO;

        public static Market fromString(String market) {
            return switch (market) {
                case "P" -> Prime;
                case "S" -> Standard;
                case "G" -> Growth;
                case "PRO" -> TokyoPRO;
                default -> throw new IllegalArgumentException("Unexpected value: " + market);
            };
        }

        public String toString() {
            return switch (this) {
                case Prime -> "Prime";
                case Standard -> "Standard";
                case Growth -> "Growth";
                case TokyoPRO -> "TokyoPRO";
            };
        }
    }

    private String ticker;
    private String productName;
    private Market market;
    private long sharesIssued;
    private LocalDate listingDate;

    public Stock(String ticker, String productName, Market market, LocalDate listingDate, long sharesIssued) {
        this.ticker = ticker;
        this.productName = productName;
        this.market = market;
        this.sharesIssued = sharesIssued;
        this.listingDate = listingDate;
    }

    public String getTicker() {
        return ticker;
    }

    public String getProductName() {
        return productName;
    }

    public Market getMarket() {
        return market;
    }

    public long getSharesIssued() {
        return sharesIssued;
    }

    public LocalDate getListingDate() {
        return listingDate;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "ticker='" + ticker + '\'' +
                ", productName='" + productName + '\'' +
                ", market=" + market +
                ", sharesIssued=" + sharesIssued +
                ", listingDate=" + listingDate +
                '}';
    }
}
