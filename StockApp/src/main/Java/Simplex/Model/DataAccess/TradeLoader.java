package Simplex.Model.DataAccess;

import Simplex.Model.Trade;
import Simplex.Model.TradeSide;
import Simplex.Validation.TradeValidation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TradeLoader extends MasterFileLoader {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");

    public TradeLoader(String filePath) {
        super(filePath);
    }

    public List<Trade> load() {
        List<Trade> tradeList = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(getFilePath(), StandardCharsets.UTF_8)) {
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                if (i == 0) {
                    i++;
                    continue;
                }
                String[] data = line.split(",");
                if (!TradeValidation.checkLoadTrade(data)) {
                    continue;
                }
                TradeSide tradeSide = "B".equals(data[2]) ? TradeSide.BUY : TradeSide.SELL;
                Trade trade = new Trade(data[1], LocalDateTime.parse(data[0], formatter), tradeSide, Integer.parseInt(data[3]), Double.parseDouble(data[4]), LocalDateTime.parse(data[5], formatter));
                tradeList.add(trade);
            }
        } catch (IOException e) {
            System.out.println("ファイルが見つかりません");
        }
        return tradeList;
    }

    public void save(Trade trade) {
        try (BufferedWriter writer = Files.newBufferedWriter(getFilePath(), StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            writer.newLine();
            String tradeSide = trade.getTradeSide() == TradeSide.BUY ? "B" : "S";
            String stringTradedDatetime = trade.getTradedDatetime().format(formatter);
            String stringInputDatetime = trade.getInputDatetime().format(formatter);
            writer.write(stringTradedDatetime + "," + trade.getTicker() + "," + tradeSide + "," + trade.getQuantity() + "," + trade.getTradedUnitPrice() + "," + stringInputDatetime);
        } catch (IOException e) {
            System.out.println("ファイルが見つかりません");
        }
    }
}
