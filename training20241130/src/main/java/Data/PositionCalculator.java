package Data;

import Repository.MarketPriceRepository;
import Repository.TradeRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class PositionCalculator {
    TradeRepository tradeRepository = new TradeRepository();

    public Position calculatePosition(String ticker) {
        tradeRepository.loadTrades();
        List<Trade> positionTrades = tradeRepository.findTradesByTicker(ticker); //tickerに対する全ての取引を取得

        String productName = positionTrades.get(0).getProductName();
        long quantity = calculateQuantity(ticker, positionTrades);
        BigDecimal averageUnitPrice = calculateAverageUnitPrice(positionTrades);
        BigDecimal realizedProfitAndLoss = calculateRealizedProfitAndLoss(positionTrades);
        BigDecimal valuation = calculateValuation(ticker, positionTrades);
        BigDecimal unrealizedProfitAndLoss = calculateUnrealizedProfitAndLoss(ticker, positionTrades);
        return new Position(ticker, productName,quantity, averageUnitPrice, realizedProfitAndLoss, valuation, unrealizedProfitAndLoss);
    }

    public long calculateQuantity(String ticker, List<Trade> positionTrades) {
        long quantity = 0;
        for (Trade trade : positionTrades) {
            if (trade.getTradeSide() == Trade.TradeSide.Buy) {
                quantity += trade.getQuantity();
            } else if (trade.getTradeSide() == Trade.TradeSide.Sell) {
                quantity -= trade.getQuantity();
            }
        }
        return quantity;
    }

    public BigDecimal calculateAverageUnitPrice(List<Trade> positionTrades) {
        int quantity = 0;  //買付数量
        BigDecimal price = BigDecimal.ZERO;  //買付金額
        for (Trade trade : positionTrades) {
            if (trade.getTradeSide() == Trade.TradeSide.Buy) {
                quantity += trade.getQuantity();
                price = price.add(trade.getTradedUnitPrice().multiply(BigDecimal.valueOf(trade.getQuantity())));
            }
        }
        return price.divide(BigDecimal.valueOf(quantity), 2, RoundingMode.DOWN);
    }

    public BigDecimal calculateRealizedProfitAndLoss(List<Trade> positionTrades) {
        BigDecimal realizedProfitAndLoss = BigDecimal.ZERO;
        List<Trade> buyTrades = positionTrades.stream()
                .filter(trade -> trade.getTradeSide() == Trade.TradeSide.Buy)
                .toList();
        for (Trade trade : positionTrades) {
            if (trade.getTradeSide() == Trade.TradeSide.Sell) {
                BigDecimal averageUnitPrice = calculateAverageUnitPrice(buyTrades); //その都度の平均取得単価を計算
                realizedProfitAndLoss = realizedProfitAndLoss
                        .add((trade.getTradedUnitPrice() //売却単価
                                .subtract(averageUnitPrice)) //平均取得単価
                                .multiply(BigDecimal.valueOf(trade.getQuantity()))); //数量
            }
        }
        return realizedProfitAndLoss;
    }

    public BigDecimal calculateValuation(String ticker, List<Trade> positionTrades) {
        MarketPriceRepository marketPriceRepository = new MarketPriceRepository();
        marketPriceRepository.loadMarketPrices();
        BigDecimal marketPrice;  //現在価格
        if (marketPriceRepository.findMarketPrice(ticker) == null) {
            marketPrice = BigDecimal.ZERO;
        } else {
            marketPrice = marketPriceRepository.findMarketPrice(ticker).getPrice();
        }
        BigDecimal valuation = BigDecimal.valueOf(calculateQuantity(ticker, positionTrades)).multiply(marketPrice);  //数量×現在価格
        return valuation;
    }

    public BigDecimal calculateAcquisitionCost(String ticker, List<Trade> positionTrades) {
        long quantity = calculateQuantity(ticker, positionTrades);
        BigDecimal averageUnitPrice = calculateAverageUnitPrice(positionTrades);
        return averageUnitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public BigDecimal calculateUnrealizedProfitAndLoss(String ticker, List<Trade> positionTrades) {
        BigDecimal valuation = calculateValuation(ticker, positionTrades);
        BigDecimal acquisitionCost = calculateAcquisitionCost(ticker, positionTrades);
        return valuation.subtract(acquisitionCost);
    }
}
