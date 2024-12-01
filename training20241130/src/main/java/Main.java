import View.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        showMenu();
    }

    private static void showMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean loopFlag =true;
        System.out.println("取引管理システムを開始します。");
        while (loopFlag) {
            System.out.print("""
                    1. 銘柄マスタ一覧表示
                    2. 銘柄マスタ新規登録
                    3. 銘柄マスタ詳細表示
                    4. 取引マスタ一覧表示
                    5. 取引マスタ新規登録
                    6. 保有ポジション表示
                    7. 時価情報表示
                    8. 時価情報新規登録
                    9. 終了
                    入力してください>
                    """);

            int inputNum = scanner.nextInt();

            switch (inputNum) {
                case 1:
                    System.out.println("銘柄マスタ一覧表示");
                    ShowStocks showStocks = new ShowStocks();
                    showStocks.show();
                    System.out.println("-----------------------------");
                    break;
                case 2:
                    System.out.println("銘柄マスタ新規登録");
                    AddStock addStock = new AddStock();
                    addStock.add();
                    System.out.println("-----------------------------");
                    break;
                case 3:
                    System.out.println("銘柄マスタ詳細表示");
                    ShowStockDetails showStockDetails = new ShowStockDetails();
                    showStockDetails.show();
                    System.out.println("-----------------------------");
                    break;
                case 4:
                    System.out.println("取引マスタ一覧表示");
                    ShowTrades showTradeMaster = new ShowTrades();
                    showTradeMaster.show();
                    System.out.println("-----------------------------");
                    break;
                case 5:
                    System.out.println("取引マスタ新規登録");
                    AddTrade addTrade = new AddTrade();
                    addTrade.add();
                    System.out.println("-----------------------------");
                    break;
                case 6:
                    System.out.println("保有ポジション表示");
                    ShowPositions showPositions = new ShowPositions();
                    showPositions.show();
                    System.out.println("-----------------------------");
                    break;
                case 7:
                    System.out.println("時価情報表示");
                    ShowMarketPrice showMarketPrice = new ShowMarketPrice();
                    showMarketPrice.show();
                    System.out.println("-----------------------------");
                    break;
                case 8:
                    System.out.println("時価情報新規登録");
                    AddMarketPrice addMarketPrice = new AddMarketPrice();
                    addMarketPrice.add();
                    System.out.println("-----------------------------");
                    break;
                case 9:
                    System.out.println("取引管理システムを終了します。");
                    loopFlag = false;
                    break;
                default:
                    System.out.println("無効な入力です。");
                    break;
            }
        }
    }
}
