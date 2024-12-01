package Simplex.View;

import Simplex.Controller.PositionRepository;
import Simplex.Controller.TradeRepository;
import Simplex.Model.Position;

public class ShowPosition {
    TradeRepository tradeRepository = new TradeRepository();

    public void play() {
        PositionRepository positionRepository = new PositionRepository();
        positionRepository.loadPositions();
        System.out.println("|" + "=".repeat(163) + "|");
        System.out.printf("| %6s | %30s | %15s | %20s | %20s | %26s | %26s | %n",
                "Ticker", "Product Name", "Quantity", "Average Unit Price", "Valuation", "Unrealized Profit and Loss", "Realized Profit and Loss");
        System.out.println("|" + "-".repeat(8) + "+" + "-".repeat(32) + "+" + "-".repeat(17) + "|" + "-".repeat(22) + "+" + "-".repeat(22) + "+" + "-".repeat(28) + "+" + "-".repeat(28) + "|");
        for (Position position : positionRepository.getPositionList()) {
            String productName = position.getProductName();
            String dispProductName = (productName.length() <= 30) ? productName : (productName.substring(0, 27) + "...");  //文字数の指定
            String dispValuation = "¥" + String.format("%,.0f", position.getValuation());
            String dispUnrealizedProfitAndLoss = position.getUnrealizedProfitAndLoss().doubleValue() > 0 ? "¥ +" + String.format("%,.0f", position.getUnrealizedProfitAndLoss())
                    : position.getUnrealizedProfitAndLoss().doubleValue() < 0 ? "¥ -" + String.format("%,.0f", position.getUnrealizedProfitAndLoss().negate())
                    : "¥ ±" + String.format("%,.0f", position.getUnrealizedProfitAndLoss());
            String dispRealizedProfitAndLoss = position.getRealizedProfitAndLoss().doubleValue() > 0 ? "¥ +" + String.format("%,.0f", position.getRealizedProfitAndLoss())
                    : position.getRealizedProfitAndLoss().doubleValue() < 0 ? "¥ -" + String.format("%,.0f", position.getRealizedProfitAndLoss().negate())
                    : "¥ ±" + String.format("%,.0f", position.getRealizedProfitAndLoss());
            System.out.printf("| %6s | %30s | %15s | %,20.2f | %20s | %26s | %26s | %n",
                    position.getTicker(), dispProductName, position.getQuantity(), position.getAverageUnitPrice(), dispValuation, dispUnrealizedProfitAndLoss, dispRealizedProfitAndLoss);
        }
        System.out.println("|" + "=".repeat(163) + "|");
    }
}
