package Simplex;

import Simplex.View.*;

import java.util.Scanner;

public class Main {
    private static boolean endFlag = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            showMenu(scanner);
            if (endFlag) {
                break;
            }
        }
    }

    public static void showMenu(Scanner scanner) {
        System.out.print("""
                株式取引システムを開始します。
                操作するメニューを選択してください。
                1.銘柄マスタ一覧表示
                2.銘柄マスタ新規登録
                3.取引マスタ一覧表示
                4.取引マスタ新規登録
                5.ポジション一覧表示
                6.時価情報一覧表示
                7.時価情報新規登録
                9.終了
                入力してください：
                """);

        int input = scanner.nextInt();

        switch (input) {
            case 1 -> {
                System.out.print("""
                        銘柄マスタ一覧表示
                        """);
                new ShowStockMaster().play();
            }
            case 2 -> {
                System.out.print("""
                        銘柄マスタ新規登録
                        """);
                new AddStock().play();
            }

            case 3 -> {
                System.out.print("""
                        取引マスタ一覧表示
                        """);
                new ShowTradeMaster().play();
            }

            case 4 -> {
                System.out.print("""
                        取引マスタ新規登録
                        """);
                new AddTrade().play();
            }

            case 5 -> {
                System.out.print("""
                        ポジション一覧表示
                        """);
                new ShowPosition().play();
            }

            case 6 -> {
                System.out.print("""
                        時価情報一覧表示
                        """);
                new ShowMarketPrice().play();
            }

            case 7 -> {
                System.out.print("""
                        時価情報新規登録
                        """);
                new AddMarketPrice().play();
            }
            case 9 -> {
                System.out.println("終了");
                endFlag = true;
            }
            default -> System.out.println("不正な入力です。");
        }
    }
}

