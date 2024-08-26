package simplex.bn25.momonoi.trading;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

abstract class MasterFile {
    private final Path filePath;

    public MasterFile(String inputPath){
        this.filePath = Path.of(inputPath);
        File file = new File(inputPath);
        try {
            if (file.createNewFile()) {
                System.out.println("ファイルを作成しました。");
            }
        } catch (IOException e) {
            System.out.println("ファイルの作成に失敗しました。");
        }
    }

    public Path getFilePath() {
        return filePath;
    }

    public ArrayList<String> readFile(){
        ArrayList<String> fileContents = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(this.filePath, StandardCharsets.UTF_8)) {
            for (String line; (line = reader.readLine()) != null; ) {
                fileContents.add(line);
            }
        } catch (NoSuchFileException e) {  //e.getFile()
            System.out.printf("ファイルが見つかりません。 %nアプリケーションを終了します。");
            System.exit(1);
        } catch (IOException e) {
            System.out.printf("Dataが見つかりません。 %nアプリケーションを終了します。");
            System.exit(1);
        }
        return fileContents;
    }

    void writeFile(String writeLine){
        try (BufferedWriter writer = Files.newBufferedWriter(this.filePath, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            writer.append("\r\n");
            writer.append(writeLine);
        } catch (NoSuchFileException e) {
            System.out.printf("ファイルが見つかりません。 %nアプリケーションを終了します。");
            System.exit(1);
        } catch (IOException e) {
            System.out.printf("Dataが見つかりません。 %nアプリケーションを終了します。");
            System.exit(1);
        }
        System.out.println("正常に保存されました。");
    }

}
