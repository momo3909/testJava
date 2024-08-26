package simplex.bn25.momonoi.trading;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TradeMasterFile extends MasterFile{

    public TradeMasterFile(String inputPath) {
        super(inputPath);
    }

    public void writeFile(LocalDateTime tradedDatetime, String ticker, String productName,
                          String side, int quantity, BigDecimal tradedUnitPrice){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd/HH:mm");
        LocalDateTime now = LocalDateTime.parse(LocalDateTime.now().format(formatter),formatter);
        String writeLine = tradedDatetime + "," + ticker + "," + productName + "," + side + "," + quantity + "," + tradedUnitPrice.setScale(2, BigDecimal.ROUND_FLOOR) + "," + now;
        super.writeFile(writeLine);
    }
}
