package Simplex.Model.DataAccess;

import java.io.File;
import java.nio.file.Path;

public abstract class MasterFileLoader {
    private final Path filePath;
    private final File file;

    public MasterFileLoader(String filePath) {
        this.filePath = Path.of(filePath);
        this.file = new File(filePath);
    }

    public Path getFilePath() {
        return filePath;
    }

    public File getFile() {
        return file;
    }
}
