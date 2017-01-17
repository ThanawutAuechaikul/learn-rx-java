import yahoofinance.YahooFinance;

import java.io.IOException;

/**
 * Created by LJsupreme on 1/7/2017 AD.
 */
public class StockFetcher {
    public static StockInfo fetch(String symbol) {
        /*if (Math.random() > 0.5) {
            System.out.println("Make Exception");
            throw new RuntimeException("Ohh no");
        }
        */
        try {
            double price = YahooFinance.get(symbol).getQuote().getPrice().doubleValue();
            return new StockInfo(symbol, price);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
