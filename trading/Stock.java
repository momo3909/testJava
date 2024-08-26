package simplex.bn25.momonoi.trading;

public class Stock {
    private String ticker;
    private String productName;
    private String market;
    private Long sharesIssued;

    public Stock(String lineInfo) {
        //record使ってもいいかも
        String[] splitInfo = lineInfo.split(",");
        try {
            this.ticker = splitInfo[0];
            this.productName = splitInfo[1];
            this.market = "P".equals(splitInfo[2]) ? "Prime" : "S".equals(splitInfo[2]) ? "Standard" : "Growth";
            this.sharesIssued = Long.valueOf(splitInfo[3]);
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

    public String getTicker() {
        return ticker;
    }

    public String getProductName() {
        return productName;
    }

    public String getMarket() {
        return market;
    }

    public Long getSharesIssued() {
        return sharesIssued;
    }

    public void displayAll() {
        System.out.println("Ticker:" + this.ticker + " Product Name:" + this.productName + " Market:" + this.market + " Shares Issued:" + this.sharesIssued);
    }
}
