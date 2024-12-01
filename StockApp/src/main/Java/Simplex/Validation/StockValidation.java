package Simplex.Validation;

public class StockValidation {
    public static boolean checkLoadStock(String[] data) {
        if (data.length != 4) {
            System.out.println("データの個数が不正です。");
            return false;
        }
        String ticker = data[0];
        String productName = data[1];
        String marketType = data[2];
        String sharesIssued = data[3];

        if (!tickerValidation(ticker) || !productNameValidation(productName) || !marketTypeValidation(marketType) || !sharesIssuedValidation(sharesIssued)) {
            return false;
        }
        return true;
    }

    public static boolean tickerValidation(String ticker) {
        String[] notUsingCharacters = {"B", "E", "I", "O", "Q", "V", "Z"};
        if (!ticker.matches("^[A-Z0-9]{4}") ||
                !ticker.substring(0, 1).matches("^[0-9]") ||
                !ticker.substring(2, 3).matches("^[0-9]")) {
            System.out.println("銘柄コードは4桁の半角英数字で入力してください。");
            return false;
        }
        for (String notUsingCharacter : notUsingCharacters) {
            if (ticker.contains(notUsingCharacter)) {
                System.out.println("銘柄コードに使用できない文字が含まれています。");
                return false;
            }
        }
        return true;
    }

    public static boolean productNameValidation(String productName) {
        if (!productName.matches("^[a-zA-Z0-9 .()]+")) {
            System.out.println("会社名は半角英数字で入力してください。");
            return false;
        }
        return true;
    }

    public static boolean marketTypeValidation(String marketType) {
        if (!marketType.matches("^[PSG]")) {
            System.out.println("市場区分はP,S,Gのいずれかで入力してください。");
            return false;
        }
        return true;
    }

    public static boolean sharesIssuedValidation(String sharesIssued) {
        try {
            long longSharesIssued = Long.parseLong(sharesIssued);
            if (0 >= longSharesIssued || longSharesIssued >= 1000000000000L) {
                System.out.println("発行済株式数は1以上1000000000000未満で入力してください。");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("発行済株式数は半角数字で入力してください。");
            return false;
        }
        return true;
    }
}
