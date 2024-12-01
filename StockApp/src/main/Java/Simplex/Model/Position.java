package Simplex.Model;

import Simplex.Controller.TradeRepository;

import java.math.BigDecimal;

public class Position extends MasterFile {
    int quantity;
    BigDecimal averageUnitPrice;
    BigDecimal realizedProfitAndLoss;
    BigDecimal valuation;
    BigDecimal UnrealizedProfitAndLoss;
    String productName;

    public Position(String ticker, int quantity, BigDecimal averageUnitPrice, BigDecimal realizedProfitAndLoss, BigDecimal valuation, BigDecimal unrealizedProfitAndLoss) {
        super(ticker);
        this.quantity = quantity;
        this.averageUnitPrice = averageUnitPrice;
        this.realizedProfitAndLoss = realizedProfitAndLoss;
        this.valuation = valuation;
        this.UnrealizedProfitAndLoss = unrealizedProfitAndLoss;
        this.productName = getProductName();
    }

    public String getTicker() {
        return super.getTicker();
    }

    public int getQuantity() {
        return quantity;
    }

    public String getProductName() {
        TradeRepository tradeRepository = new TradeRepository();
        tradeRepository.loadTrades("src/main/resource/TradeMasterFile.txt");
        return tradeRepository.getProductName(getTicker());
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
        return UnrealizedProfitAndLoss;
    }

    @Override
    public String toString() {
        return String.format("銘柄コード：%s, ポジション数：%d", getTicker(), quantity);
    }

}
