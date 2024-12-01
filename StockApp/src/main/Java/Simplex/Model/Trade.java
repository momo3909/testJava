package Simplex.Model;

import Simplex.Controller.StockRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Trade extends MasterFile {
    private LocalDateTime tradedDatetime;
    private TradeSide tradeSide;
    private int quantity;
    private BigDecimal tradedUnitPrice;
    private LocalDateTime inputDatetime;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");

    public Trade(String ticker, LocalDateTime tradedDatetime, TradeSide tradeSide, int quantity, double tradedUnitPrice, LocalDateTime inputDatetime) {
        super(ticker);
        this.tradedDatetime = tradedDatetime;
        this.tradeSide = tradeSide;
        this.quantity = quantity;
        this.tradedUnitPrice = BigDecimal.valueOf(tradedUnitPrice).setScale(2, RoundingMode.DOWN);
        this.inputDatetime = inputDatetime;
    }

    public LocalDateTime getTradedDatetime() {
        return tradedDatetime;
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
        StockRepository stockRepository = new StockRepository();
        stockRepository.loadStocks("src/main/resource/StockMasterFile.txt");
        return stockRepository.findStock(getTicker()).getProductName();
    }

    @Override
    public String toString() {
        return "Trade{" +
                "tradedDatetime=" + tradedDatetime +
                ", tradeSide=" + tradeSide +
                ", quantity=" + quantity +
                ", tradedUnitPrice=" + tradedUnitPrice +
                ", inputDatetime=" + inputDatetime +
                '}';
    }
}
