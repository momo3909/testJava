package simplex.bn25.momonoi.trading;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Trade {
    private LocalDateTime tradedDatetime;
    private String ticker;
    private String productName;
    private TradeSide side;
    private int quantity;
    private BigDecimal tradedUnitPrice;
    private LocalDateTime inputDatetime;

    public Trade(String lineInfo) {
        String[] splitInfo = lineInfo.split(",");
        try {
            this.tradedDatetime = LocalDateTime.parse(splitInfo[0]);
            this.ticker = splitInfo[1];
            this.productName = splitInfo[2];
            this.side = "buy".equals(splitInfo[3]) ? TradeSide.BUY : TradeSide.SELL ;
            this.quantity = Integer.parseInt(splitInfo[4]);
            this.tradedUnitPrice = BigDecimal.valueOf(Double.parseDouble(splitInfo[5])).setScale(2, BigDecimal.ROUND_FLOOR);
            this.inputDatetime = LocalDateTime.parse(splitInfo[6]);
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.print("マスタファイルの列数が足りません。> ");
            for(String si : splitInfo) System.out.print(si + " ");
            System.out.printf("%nアプリケーションを終了します。");
            System.exit(1);
        } catch (NumberFormatException e){
            System.out.print("マスタファイルの値が正しくありません。> ");
            for(String si : splitInfo) System.out.print(si + " ");
            System.out.printf("%nアプリケーションを終了します。");
            System.exit(1);
        }
    }

    public LocalDateTime getTradedDatetime() {
        return tradedDatetime;
    }

    public String getTicker() {
        return ticker;
    }

    public String getProductName() {
        return productName;
    }

    public TradeSide getSide() {
        return side;
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

    public void displayAll(){
        System.out.println(tradedDatetime+" "+ticker+" "+productName+" "+side+" "+quantity+" "+tradedUnitPrice+" "+inputDatetime);
    }
}
