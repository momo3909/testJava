package FileReader;

import Data.MarketPrice;
import Repository.MarketPriceRepository;
import Validation.MarketPriceValidation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class MarketPriceMasterFileReader {
    private Path path;
    private File file;

    public MarketPriceMasterFileReader(String filePath) {
        this.path = Path.of(filePath);
        this.file = this.path.toFile();
    }

    public List<MarketPrice> readFile(){
        List<MarketPrice> marketPriceList = new ArrayList<>();

        try(BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)){
            String line;
            int i = 0;
            while((line = reader.readLine()) != null){
                if(i == 0){
                    i++;
                    continue;
                }
                String[] data = line.split(",");
                if(MarketPriceValidation.tickerValidation(data[0]) && MarketPriceValidation.priceValidation(data[1])){
                    MarketPrice marketPrice = new MarketPrice(data[0], new BigDecimal(data[1]));
                    marketPriceList.add(marketPrice);

                }
            }
        } catch (IOException e) {
            System.out.println("ファイルの読み込みに失敗しました。");
        } catch (Exception e) {
            System.out.println("予期せぬエラーが発生しました。");
        }
        return marketPriceList;
    }

    public void addMarketPrice(MarketPriceRepository marketPriceRepository){
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write("ticker,market_price");
            writer.newLine();
            for (MarketPrice marketPrice : marketPriceRepository.getMarketPriceList()) {
                writer.write(marketPrice.getTicker() + "," + marketPrice.getPrice());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("ファイルが見つかりません");
        }
    }
}
