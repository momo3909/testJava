package View;

import FileReader.TradeMasterFileReader;
import Repository.StockRepository;
import Validation.PositionValidation;
import Validation.TradeValidation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AddTrade {
    Scanner scanner = new Scanner(System.in);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");

    public void add(){
        String ticker = inputTicker();
        String tradedDatetime = inputTradedDatetime(ticker);
        String tradeSide = inputTradeSide(ticker);
        String quantity = inputQuantity(ticker, tradeSide);
        String tradedUnitPrice = inputTradedUnitPrice();
        String inputDatetime = LocalDateTime.now().format(formatter);

        TradeMasterFileReader tradeMasterFileReader = new TradeMasterFileReader("src/main/resources/TradeMasterFile.txt");
        tradeMasterFileReader.addTrade(tradedDatetime, ticker, tradeSide, quantity, tradedUnitPrice, inputDatetime);

        System.out.printf("%sの取引情報を取引マスタに新規登録しました。%n", ticker);
    }

    public String inputTradedDatetime(String ticker){
        while(true){
            System.out.print("取引日時>");
            String tradedDatetime = scanner.nextLine();
            if(TradeValidation.tradedDatetimeValidation(tradedDatetime) && PositionValidation.tradedDatetimeValidation(tradedDatetime,ticker)){
                return tradedDatetime;
            }
        }
    }

    public String inputTicker(){
        StockRepository stockRepository = new StockRepository();
        stockRepository.loadStocks();
        while(true){
            System.out.print("銘柄コード>");
            String ticker = scanner.nextLine();
            if(TradeValidation.tickerValidation(ticker)){
                System.out.println("銘柄名：" + stockRepository.findStock(ticker).getProductName());
                return ticker;
            }
        }
    }

    public String inputTradeSide(String ticker){
        while(true){
            System.out.print("売買区分(B,Sのいずれか)>");
            String tradeSide = scanner.nextLine();
            if(TradeValidation.tradeSideValidation(tradeSide) && PositionValidation.tradeSideValidation(tradeSide,ticker)){
                return tradeSide;
            }
        }
    }

    public String inputQuantity(String ticker, String tradeSide){
        while(true){
            System.out.print("数量>");
            String quantity = scanner.nextLine();
            if(TradeValidation.quantityValidation(quantity) && PositionValidation.quantityValidation(quantity,ticker,tradeSide)){
                return quantity;
            }
        }
    }

    public String inputTradedUnitPrice(){
        while(true){
            System.out.print("取引単価>");
            String tradedUnitPrice = scanner.nextLine();
            if(TradeValidation.tradedUnitPriceValidation(tradedUnitPrice)){
                BigDecimal bd = new BigDecimal(tradedUnitPrice);
                return bd.setScale(2, RoundingMode.DOWN).toString();
            }
        }
    }
}
