package Simplex.Model.DataAccess;

import Simplex.Model.MarketType;
import Simplex.Model.Stock;
import Simplex.Validation.StockValidation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class StockLoader extends MasterFileLoader {
    public StockLoader(String filePath) {
        super(filePath);
    }

    public List<Stock> load() {
        List<Stock> stockList = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(getFilePath(), StandardCharsets.UTF_8)) {
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                if (i == 0) {
                    i++;
                    continue;
                }
                String[] data = line.split(",");
                if (!StockValidation.checkLoadStock(data)) {
                    continue;
                }
                MarketType marketType = "P".equals(data[2]) ? MarketType.Prime :
                        "S".equals(data[2]) ? MarketType.Standard : MarketType.Growth;
                Stock stock = new Stock(data[0], data[1], marketType, Long.parseLong(data[3]));
                stockList.add(stock);
            }
        } catch (IOException e) {
            System.out.println("ファイルが見つかりません");
        }
        return stockList;
    }

    public void save(Stock stock) {
        try (BufferedWriter writer = Files.newBufferedWriter(getFilePath(), StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            writer.newLine();
            String writeMarketType = stock.getMarketType() == MarketType.Prime ? "P" : stock.getMarketType() == MarketType.Standard ? "S" : "G";
            writer.write(stock.getTicker() + "," + stock.getProductName() + "," + writeMarketType + "," + stock.getSharesIssued());
        } catch (IOException e) {
            System.out.println("ファイルが見つかりません");
        }
    }
}
