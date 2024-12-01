package Data;

import java.math.BigDecimal;
import java.util.List;

public class Position {
    private String ticker;
    private String productName;
    private long quantity;
    private BigDecimal averageUnitPrice;
    private BigDecimal realizedProfitAndLoss;
    private BigDecimal valuation;
    private BigDecimal unrealizedProfitAndLoss;

    public Position(String ticker, String productName, long quantity, BigDecimal averageUnitPrice, BigDecimal realizedProfitAndLoss, BigDecimal valuation, BigDecimal unrealizedProfitAndLoss) {
        this.ticker = ticker;
        this.productName = productName;
        this.quantity = quantity;
        this.averageUnitPrice = averageUnitPrice;
        this.realizedProfitAndLoss = realizedProfitAndLoss;
        this.valuation = valuation;
        this.unrealizedProfitAndLoss = unrealizedProfitAndLoss;
    }

    public String getTicker() {
        return ticker;
    }

    public String getProductName() {
        return productName;
    }

    public long getQuantity() {
        return quantity;
    }

    public BigDecimal getAverageUnitPrice() {
        return averageUnitPrice;
    }

    public BigDecimal getRealizedProfitAndLoss() {
        return realizedProfitAndLoss;
    }

    public BigDecimal getValuation() {
        return valuation;
    }

    public BigDecimal getUnrealizedProfitAndLoss() {
        return unrealizedProfitAndLoss;
    }
}
