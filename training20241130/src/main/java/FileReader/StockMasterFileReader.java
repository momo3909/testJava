package FileReader;

import Data.Stock;
import Validation.StockValidation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StockMasterFileReader {
    private Path path;
    private File file;

    public StockMasterFileReader(String filePath) {
        this.path = Path.of(filePath);
        this.file = this.path.toFile();
    }

    public List<Stock> readFile(){
        List<Stock> stockList = new ArrayList<>();

        try(BufferedReader reader = Files.newBufferedReader(path)){
            String line;
            int i = 0;
            while((line = reader.readLine()) != null){
                if(i == 0){
                    i++;
                    continue;
                }
                String[] data = line.split(",");
                List<String> separatedLine = separateLine(line);
                if(StockValidation.tickerValidation(separatedLine.get(0)) &&
                        StockValidation.productNameValidation(separatedLine.get(1)) &&
                        StockValidation.marketTypeValidation(separatedLine.get(2)) &&
                        StockValidation.listingDateValidation(separatedLine.get(3)) &&
                        StockValidation.sharesIssuedValidation(separatedLine.get(4))){
                    Stock stock = new Stock(separatedLine.get(0), separatedLine.get(1), Stock.Market.fromString(separatedLine.get(2)), LocalDate.parse(separatedLine.get(3)), Long.parseLong(separatedLine.get(4)));
                    stockList.add(stock);
                }
            }
        } catch (IOException e) {
            System.out.println("ファイルの読み込みに失敗しました。");
        } catch (Exception e) {
            System.out.println("予期せぬエラーが発生しました。");
        }
        return stockList;
    }

    public void addStock(String ticker, String productName, String marketType, String listingDate,String sharesIssued){
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            writer.newLine();
            writer.write(ticker + "," + productName + "," + marketType + "," + listingDate +","+ sharesIssued);
        } catch (IOException e) {
            System.out.println("ファイルが見つかりません");
        }
    }

    List<String> separateLine(String line){
        List<String> separatedLine = new ArrayList<>();
        String[] data = line.split(",",-1);
        int dataLength = data.length;

        String ticker = data[0];
        String sharesIssued = data[dataLength - 1];
        String listingDate = data[dataLength - 2];
        String marketType = data[dataLength - 3];
        String productName="";

        if(dataLength > 4){
            for(int i = 1; i < dataLength - 3; i++){
                if(data[i].isEmpty()){
                    productName += ",";
                }else {
                    productName += data[i];
                }
            }
        } else {
            productName = data[1];
        }

        separatedLine.add(ticker);
        separatedLine.add(productName);
        separatedLine.add(marketType);
        separatedLine.add(listingDate);
        separatedLine.add(sharesIssued);
        return separatedLine;
    }
}
