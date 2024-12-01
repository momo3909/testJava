package Simplex.Model.DataAccess;

import Simplex.Model.MarketPrice;
import Simplex.Validation.MarketPriceValidation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class MarketPriceLoader extends MasterFileLoader {
    public MarketPriceLoader(String filePath) {
        super(filePath);
    }

    public List<MarketPrice> load() {
        List<MarketPrice> marketPriceList = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(getFilePath(), StandardCharsets.UTF_8)) {
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                if (i == 0) {
                    i++;
                    continue;
                }
                String[] data = line.split(",");
                if (!MarketPriceValidation.checkLoadMarketPrice(data)) {
                    continue;
                }
                MarketPrice marketPrice = new MarketPrice(data[0], Double.parseDouble(data[1]));
                marketPriceList.add(marketPrice);
            }
        } catch (IOException e) {
            System.out.println("ファイルが見つかりません");
        }
        return marketPriceList;
    }

    public void save(List<MarketPrice> marketPriceList) {
        try (BufferedWriter bw = Files.newBufferedWriter(getFilePath(), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            bw.write("ticker,market_price");
            bw.newLine();
            for (MarketPrice marketPrice : marketPriceList) {
                bw.write(marketPrice.getTicker() + "," + marketPrice.getMarketPrice());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("ファイルが見つかりません");
        }

    }
}
