package FileReader;

import Data.Trade;
import Validation.TradeValidation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TradeMasterFileReader {
    private Path path;
    private File file;

    public TradeMasterFileReader(String filePath) {
        this.path = Path.of(filePath);
        this.file = this.path.toFile();
    }

    public List<Trade> readFile() {
        List<Trade> tradeList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                if (i == 0) {
                    i++;
                    continue;
                }
                String[] data = line.split(",");
                if (TradeValidation.tradedDatetimeValidation(data[0]) &&
                        TradeValidation.tickerValidation(data[1]) &&
                        TradeValidation.tradeSideValidation(data[2]) &&
                        TradeValidation.quantityValidation(data[3]) &&
                        TradeValidation.tradedUnitPriceValidation(data[4])) {
                    Trade trade = new Trade(LocalDateTime.parse(data[0],formatter), data[1], Trade.TradeSide.fromString(data[2]), Integer.parseInt(data[3]), new BigDecimal(data[4]), LocalDateTime.parse(data[5],formatter));
                    tradeList.add(trade);
                }
            }
        } catch (IOException e) {
            System.out.println("ファイルの読み込みに失敗しました。");
        } catch (Exception e) {
            System.out.println("予期せぬエラーが発生しました。");
        }
        return tradeList;
    }

    public void addTrade(String tradedDatetime, String ticker, String tradeSide, String quantity, String tradedUnitPrice, String inputDatetime) {
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            writer.newLine();
            writer.write(tradedDatetime + "," + ticker + "," + tradeSide + "," + quantity + "," + tradedUnitPrice + "," + inputDatetime);
        } catch (IOException e) {
            System.out.println("ファイルが見つかりません");
        }
    }
}
