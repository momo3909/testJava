package Simplex.Model.DataAccess;

import Simplex.Controller.MarketPriceRepository;
import Simplex.Controller.TradeRepository;
import Simplex.Model.Position;
import Simplex.Model.Trade;
import Simplex.Model.TradeSide;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class PositionLoader {
    private final TradeRepository tradeRepository = new TradeRepository();
    private final MarketPriceRepository marketPriceRepository = new MarketPriceRepository();
    private final List<String> tradedTicker = new ArrayList<>();

    public PositionLoader() {
        tradeRepository.loadTrades("src/main/resource/TradeMasterFile.txt");
        marketPriceRepository.loadMarketPrices("src/main/resource/MarketPriceMasterFile.txt");
        tradedTicker.addAll(tradeRepository.getTickers()); //重複はない
    }

    public List<Position> load() {
        List<Position> positionList = new ArrayList<>();
        for (String ticker : tradedTicker) {
            int quantity = calculateQuantity(ticker);
            BigDecimal averageUnitPrice = calculateAverageUnitPrice(ticker);
            BigDecimal realizedProfitAndLoss = calculateRealizedProfitAndLoss(ticker);
            BigDecimal valuation = calculateValuation(ticker);
            BigDecimal unrealizedProfitAndLoss = calculateUnrealizedProfitAndLoss(ticker);
            positionList.add(new Position(ticker, quantity, averageUnitPrice, realizedProfitAndLoss, valuation, unrealizedProfitAndLoss));
        }
        return positionList;
    }

    private int calculateQuantity(String ticker) {
        int quantity = 0;
        for (Trade trade : tradeRepository.getTradeList()) {
            if (trade.getTicker().equals(ticker)) {
                quantity += trade.getTradeSide() == TradeSide.BUY ? trade.getQuantity() : -trade.getQuantity();
            }
        }
        return quantity;
    }

    private BigDecimal calculateAverageUnitPrice(String ticker) {
        int quantity = 0;
        BigDecimal price = BigDecimal.ZERO;
        for (Trade trade : tradeRepository.getTradeList()) {
            if (trade.getTradeSide() == TradeSide.BUY && trade.getTicker().equals(ticker)) {
                quantity += trade.getQuantity();
                price = price.add(trade.getTradedUnitPrice().multiply(BigDecimal.valueOf(trade.getQuantity())));
            }
        }
        return price.divide(BigDecimal.valueOf(quantity), 2, RoundingMode.DOWN);
    }

    private BigDecimal calculateRealizedProfitAndLoss(String ticker) {
        double profitAndLoss = 0;
        BigDecimal averageUnitPrice = calculateAverageUnitPrice(ticker);
        for (Trade trade : tradeRepository.getTradeList()) {
            if (trade.getTicker().equals(ticker)) {
                if (trade.getTradeSide() == TradeSide.SELL) {
                    profitAndLoss += trade.getQuantity() * (trade.getTradedUnitPrice().subtract(averageUnitPrice)).doubleValue();
                }
            }
        }
        return BigDecimal.valueOf(profitAndLoss);
    }

    private BigDecimal calculateValuation(String ticker) {
        double marketPrice;
        int quantity = calculateQuantity(ticker);
        try {
            marketPrice = marketPriceRepository.findMarketPrice(ticker).getMarketPrice();  //時価
        } catch (NullPointerException e) {
            marketPrice = 0;
        }
        return BigDecimal.valueOf(marketPrice).multiply(BigDecimal.valueOf(quantity));
    }

    private BigDecimal calculateUnrealizedProfitAndLoss(String ticker) {
        BigDecimal valuation = calculateValuation(ticker);
        BigDecimal averageUnitPrice = calculateAverageUnitPrice(ticker);
        int quantity = calculateQuantity(ticker);

        return valuation.subtract(averageUnitPrice.multiply(BigDecimal.valueOf(quantity)));
    }
}
