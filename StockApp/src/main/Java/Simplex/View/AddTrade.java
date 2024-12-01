package Simplex.View;

import Simplex.Controller.StockRepository;
import Simplex.Controller.TradeRepository;
import Simplex.Model.Trade;
import Simplex.Model.TradeSide;
import Simplex.Validation.TradeValidation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AddTrade {
    private final TradeRepository tradeRepository = new TradeRepository();
    Scanner scanner = new Scanner(System.in);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");

    public void play() {
        String ticker = inputTicker();
        LocalDateTime tradeDate = inputTradeDate(ticker);
        TradeSide tradeSide = inputTradeSide(ticker);
        int quantity = inputQuantity(ticker, tradeSide);
        double tradedUnitPrice = inputTradedUnitPrice();
        LocalDateTime inputDatetime = LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter);

        Trade trade = new Trade(ticker, tradeDate, tradeSide, quantity, tradedUnitPrice, inputDatetime);

        tradeRepository.saveTrade(trade, "src/main/resource/TradeMasterFile.txt");

        System.out.printf("%sの取引情報を取引マスタに新規登録しました。%n", ticker);
    }

    private LocalDateTime inputTradeDate(String ticker) {
        while (true) {
            System.out.print("取引日時>");
            String stringTradeDatetime = scanner.nextLine();
            if (TradeValidation.addTradedDatetimeValidation(stringTradeDatetime, ticker)) {
                return LocalDateTime.parse(stringTradeDatetime, formatter);
            }
        }
    }

    private String inputTicker() {
        while (true) {
            System.out.print("銘柄コード>");
            String ticker = scanner.nextLine();
            if (TradeValidation.tickerValidation(ticker)) {
                StockRepository stockRepository = new StockRepository();
                stockRepository.loadStocks("src/main/resource/StockMasterFile.txt");
                System.out.printf("銘柄名：%s%n", stockRepository.findStock(ticker).getProductName());
                return ticker;
            }
        }
    }

    private TradeSide inputTradeSide(String ticker) {
        while (true) {
            System.out.print("売買区分>");
            String stringTradeSide = scanner.nextLine();
            if (TradeValidation.addTradeSideValidation(stringTradeSide, ticker)) {
                TradeSide tradeSide = stringTradeSide.equals("B") ? TradeSide.BUY : stringTradeSide.equals("S") ? TradeSide.SELL : null;
                return tradeSide;
            }
        }
    }

    private int inputQuantity(String ticker, TradeSide tradeSide) {
        while (true) {
            System.out.print("数量>");
            String stringQuantity = scanner.nextLine();
            if (TradeValidation.addQuantityValidation(stringQuantity, ticker, tradeSide)) {
                return Integer.parseInt(stringQuantity);
            }
        }
    }

    private double inputTradedUnitPrice() {
        while (true) {
            System.out.print("取引単価>");
            String stringTradedUnitPrice = scanner.nextLine();
            if (TradeValidation.tradedUnitPriceValidation(stringTradedUnitPrice)) {
                return Double.parseDouble(stringTradedUnitPrice);
            }
        }
    }
}
