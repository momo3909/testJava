import java.util.Objects;

public final class TestStock {
    private final String ticker;
    private final String productName;
    private final String market;
    private final long sharesIssued;

    public TestStock(String ticker, String productName, String market, long sharesIssued) throws NotCorrectStockException {
        this.ticker = ticker;
        this.productName = productName;
        this.market = market;
        this.sharesIssued = sharesIssued;

        if (!ticker.matches("[A-Z0-9]{4}")
                || (ticker.contains("B")
                || ticker.contains("E")
                || ticker.contains("I")
                || ticker.contains("O")
                || ticker.contains("Q")
                || ticker.contains("V")
                || ticker.contains("Z"))
                || ((!Character.isDigit(ticker.charAt(0)) ||
                !Character.isDigit(ticker.charAt(2))))) {
            throw new NotCorrectStockException();
        }

        if (!productName.matches("[a-zA-Z0-9. ()]*")) {
            throw new NotCorrectStockException();
        }

        if ((sharesIssued <= 0) || (sharesIssued >= 1000000000000L)) {
            throw new NotCorrectStockException();
        }
    }


    public String ticker() {
        return ticker;
    }

    public String productName() {
        return productName;
    }

    public String market() {
        return market;
    }

    public long sharesIssued() {
        return sharesIssued;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (TestStock) obj;
        return Objects.equals(this.ticker, that.ticker) &&
                Objects.equals(this.productName, that.productName) &&
                Objects.equals(this.market, that.market) &&
                this.sharesIssued == that.sharesIssued;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticker, productName, market, sharesIssued);
    }

    @Override
    public String toString() {
        return "TestStock[" +
                "ticker=" + ticker + ", " +
                "productName=" + productName + ", " +
                "market=" + market + ", " +
                "sharesIssued=" + sharesIssued + ']';
    }

}
