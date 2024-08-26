package simplex.bn25.momonoi.trading;

public class StockMasterFile extends MasterFile{

    public StockMasterFile(String inputPath) {
        super(inputPath);
    }

    public void writeFile(String ticker, String productName, String market, String  sharesIssued){
        String writeMarket = "Prime".equals(market) ? "P" : "Standard".equals(market) ? "S" : "G";
        String writeLine = ticker + "," + productName + "," + writeMarket + "," + sharesIssued;
        super.writeFile(writeLine);
    }
}

