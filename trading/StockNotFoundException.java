package simplex.bn25.momonoi.trading;

public class StockNotFoundException extends Exception{
    StockNotFoundException(){super("指定の銘柄コードに対応する株式が見つかりませんでした。");}
}
