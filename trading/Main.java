package simplex.bn25.momonoi.trading;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            StockData stockData = new StockData();
            TradeData tradeData = new TradeData();
            //マスタを都度更新するためにwhileループ内に配置
            StockMasterFile stockMasterFile = new StockMasterFile("/Users/yusuke/Desktop/Java/dev/BN25TechTraining/StockMasterFile.txt");
            TradeMasterFile tradeMasterFile = new TradeMasterFile("/Users/yusuke/Desktop/Java/dev/BN25TechTraining/TradeMasterFile.txt");
            int i = 0;
            for(String line: stockMasterFile.readFile()) {
                if (i == 0) {
                    i += 1;
                } else {
                    Stock stock = new Stock(line);
                    stockData.add(stock);
                }
            }
            int j = 0;
            for(String line: tradeMasterFile.readFile()) {
                if (j == 0) {
                    j += 1;
                } else {
                    Trade trade = new Trade(line);
                    tradeData.add(trade);
                }
            }

            System.out.println("株式取引管理システムを開始します。");
            System.out.println("操作するメニューを選んでください。");
            System.out.println("""
                     1. 銘柄マスタ一覧表示
                     2. 銘柄マスタ新規登録
                     3. 取引入力
                     4. 取引一覧
                     9. アプリケーションを終了する
                    """);
            System.out.print("入力してください:");
            int OperationNum = scanner.nextInt();

            switch (OperationNum) {
                case 1:
                    System.out.println("銘柄マスタを表示します");
                    stockData.displayStockData();
                    break;
                case 2:
                    String inputAddTicker;
                    String inputAddProductName;
                    String inputAddMarket;
                    String inputAddSharesIssued;

                    System.out.println("新規株式銘柄マスタを登録します");
                    System.out.print("銘柄名> ");
                    inputAddProductName = scanner.next();
                    while (true) {
                        System.out.print("銘柄コード> ");
                        inputAddTicker = scanner.next();
                        if ((inputAddTicker.matches("[A-Z0-9]{4}"))) {
                            if (stockData.equalStock(inputAddTicker)) {
                                System.out.println("銘柄コードがすでに登録されています。");
                            }else break;
                        }else System.out.println("銘柄コードが規則に従っていません。(4桁の半角英数字)");
                    }
                    while (true) {
                        System.out.print("上場市場> ");
                        inputAddMarket = scanner.next();
                        if ("Prime".equals(inputAddMarket) || "Standard".equals(inputAddMarket) || "Growth".equals(inputAddMarket)){
                            break;
                        } else System.out.println("上場市場が規則に従っていません。(Prime, Standard, Growth)");
                    }
                    while (true) {
                        System.out.print("発行済み株式数> ");
                        inputAddSharesIssued = scanner.next();
                        if (inputAddSharesIssued.matches("[1-9]*")){
                            break;
                        } else System.out.println("発行済み株式数が規則に従っていません。");
                    }
                    stockMasterFile.writeFile(inputAddTicker, inputAddProductName, inputAddMarket, inputAddSharesIssued);
                    System.out.printf("%sを新規銘柄として登録しました。%n---%n", inputAddProductName);
                    break;

                case 3:
                    String inputTradedDatetime;
                    String inputTradeTicker;
                    String inputTradeProductName;
                    String inputSide;
                    String inputQuantity;
                    String inputTradedUnitPrice;
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd/HH:mm");

                    System.out.println("株の取引を開始します");
                    while (true) {
                        System.out.print("取引日時(フォーマット: yyyy/MM/dd/HH:mm 例: 2000/05/20/09:30)> ");
                        inputTradedDatetime = scanner.next();
                        try {
                            LocalDateTime dateTime = LocalDateTime.parse(inputTradedDatetime, formatter);
                            DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
                            int hour = dateTime.getHour();
                            int minute = dateTime.getMinute();
                            boolean isWeekday = dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
                            boolean isWithinTimeRange = (hour > 9 || (hour == 9 && minute >= 0)) && (hour < 15 || (hour == 15 && minute <= 30));
                            if (isWeekday && isWithinTimeRange) break;
                            System.out.printf("取引時間が正しくありません。 (平日の9:00~15:30) %n%sは%sの%s:%sです。%n", inputTradedDatetime, dayOfWeek,hour,minute);
                        } catch (DateTimeParseException e) {
                            System.out.println("入力フォーマットが正しくありません。");
                        }
                    }

                    while (true) {
                        System.out.print("銘柄コード> ");
                        inputTradeTicker = scanner.next();
                        if ((inputTradeTicker.matches("[A-Z0-9]{4}"))) {
                            if (stockData.equalStock(inputTradeTicker)) {
                                try {
                                    inputTradeProductName = stockData.findStock(inputTradeTicker).getProductName();
                                    System.out.printf("枚柄コード%sは%sです。%n", inputTradeTicker, inputTradeProductName);
                                    break;
                                } catch (StockNotFoundException e){
                                    System.out.println(e.getMessage());
                                }
                            }else System.out.println("銘柄コードが登録されていません。");
                        }else System.out.println("銘柄コードが規則に従っていません。(4桁の半角英数字)");
                    }

                    while(true){
                        System.out.print("売買区分> ");
                        inputSide = scanner.next();
                        if ("buy".equals(inputSide) || "sell".equals(inputSide)) break;
                        System.out.println("売買区分が正しくありません。 (buy or sell)");
                    }

                    while (true){
                        System.out.print("取引数量> ");
                        inputQuantity = scanner.next();
                        BigInteger number = new BigInteger(inputQuantity);
                        BigInteger hundred = new BigInteger("100");
                        if(inputQuantity.matches("\\d+") && number.mod(hundred).equals(BigInteger.ZERO)) break;
                        System.out.println("取引数量が無効です。 (100株単位の自然数)");
                    }

                    while (true){
                        System.out.print("取引単価> ");
                        inputTradedUnitPrice = scanner.next();
                        if (Double.parseDouble(inputTradedUnitPrice) > 0){
                            break;
                        }
                        System.out.println("取引単価が正しくありません。 (正の数)");
                    }

                    tradeMasterFile.writeFile(LocalDateTime.parse(inputTradedDatetime, formatter), inputTradeTicker, inputTradeProductName, inputSide, Integer.parseInt(inputQuantity), BigDecimal.valueOf(Double.parseDouble(inputTradedUnitPrice)));
                    System.out.printf("新規取引を登録しました。%n---%n");
                    break;

                case 4:
                    System.out.println("取引一覧を表示します");
                    tradeData.displayTradeData();
                    break;

                case 9:
                    System.out.println("アプリケーションを終了します。");
                    return;
                default:
                    System.out.printf("\"%s\"に対応するメニューはありません。%n", OperationNum);
            }
        }
    }
}