package Data;

import Repository.StockRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Trade {
    public enum TradeSide {
        Buy, Sell;

        public static TradeSide fromString(String tradeSide) {
            return switch (tradeSide) {
                case "B" -> Buy;
                case "S" -> Sell;
                default -> throw new IllegalArgumentException("Unexpected value: " + tradeSide);
            };
        }
        @Override
        public String toString() {
            return switch (this) {
                case Buy -> "Buy";
                case Sell -> "Sell";
            };
        }
    }
    StockRepository stockRepository = new StockRepository();

    private LocalDateTime tradedDatetime;
    private String ticker;
    private TradeSide tradeSide;
    private int quantity;
    private BigDecimal tradedUnitPrice;
    private LocalDateTime inputDatetime;
    private String productName;

    public Trade(LocalDateTime tradedDatetime, String ticker, TradeSide tradeSide, int quantity, BigDecimal tradedUnitPrice, LocalDateTime inputDatetime) {
        this.tradedDatetime = tradedDatetime;
        this.ticker = ticker;
        this.tradeSide = tradeSide;
        this.quantity = quantity;
        this.tradedUnitPrice = tradedUnitPrice;
        this.inputDatetime = inputDatetime;

        stockRepository.loadStocks();
        this.productName = stockRepository.findStock(ticker).getProductName();
    }

    public LocalDateTime getTradedDatetime() {
        return tradedDatetime;
    }

    public String getTicker() {
        return ticker;
    }

    public TradeSide getTradeSide() {
        return tradeSide;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getTradedUnitPrice() {
        return tradedUnitPrice;
    }

    public LocalDateTime getInputDatetime() {
        return inputDatetime;
    }

    public String getProductName() {
        return productName;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "tradedDatetime=" + tradedDatetime +
                ", ticker='" + ticker + '\'' +
                ", tradeSide=" + tradeSide +
                ", quantity=" + quantity +
                ", tradedUnitPrice=" + tradedUnitPrice +
                ", inputDatetime=" + inputDatetime +
                '}';
    }
}
