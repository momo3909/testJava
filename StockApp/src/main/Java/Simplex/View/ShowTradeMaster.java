package Simplex.View;

import Simplex.Controller.TradeRepository;
import Simplex.Model.Trade;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ShowTradeMaster {
    private final TradeRepository tradeRepository = new TradeRepository();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
    private final Scanner scanner = new Scanner(System.in);

    public void play() {
        tradeRepository.loadTrades("src/main/resource/TradeMasterFile.txt");
        selectSortMenu();
        System.out.println("|" + "=".repeat(132) + "|");
        System.out.printf("| %20s | %6s | %30s | %8s | %10s | %18s | %20s | %n", "Traded Datetime", "Ticker", "Product Name", "Side", "Quantity", "Traded Unit Price", "Input Datetime");
        System.out.println("|" + "-".repeat(22) + "+" + "-".repeat(8) + "+" + "-".repeat(32) + "+" + "-".repeat(10) + "+" + "-".repeat(12) + "+" + "-".repeat(20) + "+" + "-".repeat(22) + "|");
        for (Trade trade : tradeRepository.getTradeList()) {
            String dispProductName = (trade.getProductName().length() <= 30) ? trade.getProductName() : (trade.getProductName().substring(0, 27) + "...");  //文字数の指定
            System.out.printf("| %20s | %6s | %-30s | %-8s | %10s | %18s | %20s | %n", formatter.format(trade.getTradedDatetime()), trade.getTicker(), dispProductName, trade.getTradeSide(), trade.getQuantity(), trade.getTradedUnitPrice(), formatter.format(trade.getInputDatetime()));
        }
        System.out.println("|" + "=".repeat(132) + "|");
    }

    public void selectSortMenu() {
        boolean loopFlag = true;
        System.out.print("""
                取引マスタの並び替えを行います。
                並び替える項目を選択してください。
                1.取引日時の昇順
                2.取引日時の降順
                3.銘柄コードの昇順
                4.銘柄コードの降順
                入力してください：
                """);
        while (loopFlag) {
            int input = scanner.nextInt();
            switch (input) {
                case 1 -> {
                    tradeRepository.sortReverseTradedDatetime();
                    loopFlag = false;
                }
                case 2 -> {
                    tradeRepository.sortTradedDatetime();
                    loopFlag = false;
                }
                case 3 -> {
                    tradeRepository.sortTicker();
                    loopFlag = false;
                }
                case 4 -> {
                    tradeRepository.sortReverseTicker();
                    loopFlag = false;
                }
                default -> {
                    System.out.println("入力が不正です。");
                }
            }
        }
    }
}
