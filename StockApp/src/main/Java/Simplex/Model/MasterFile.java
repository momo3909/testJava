package Simplex.Model;

public abstract class MasterFile {
    private String ticker;

    public MasterFile(String ticker) {
        this.ticker = ticker;
    }

    public String getTicker() {
        return ticker;
    }

    @Override
    public String toString() {
        return "MasterFile{" +
                "ticker='" + ticker + '\''
                + '}';
    }
}
