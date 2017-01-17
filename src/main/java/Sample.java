import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import java.util.*;

public class Sample {
    public static void main(String[] args) throws InterruptedException {
        /*
        List<String> symbols = Arrays.asList("GOOG", "AAPL", "MSFT", "INTC");
        Observable<StockInfo> feed = StockServer.getFeed(symbols);
        */

        /*feed.subscribeOn(Schedulers.io())
                .onErrorResumeNext(error -> callBackupPlan(error, symbols))
                .subscribe(Sample::printStockInfo, Sample::handleError);
         Thread.sleep(100000);
        */

        /*
        feed.filter(s -> s.price > 100)
                .map(old -> new StockInfo(old.ticker, old.price * -1.0))
                .subscribeOn(Schedulers.io())
                .subscribe(Sample::printStockInfo, Sample::handleError);
        Thread.sleep(100000);
        */

        /*
        feed.skip(5)
                .subscribe(Sample::printStockInfo, Sample::handleError);
        */

        /**
        feed.subscribe(new Subscriber<StockInfo>() {
            @Override
            public void onCompleted() {
                System.out.println("Done");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Oops: " + throwable);
            }

            @Override
            public void onNext(StockInfo stockInfo) {
                if (stockInfo.ticker.equals("AAPL") && stockInfo.price > 0.8) {
                    System.out.println("Thanks, no more data..");
                    unsubscribe();
                } else {
                    System.out.println(stockInfo);
                }
            }
        });
        **/

        //Observable<Integer> feed = StockServer.getData();
        //feed.subscribe(System.out::println);


        // Cold : all subscribers always start from fresh (from 0)
        /*
        Observable<Integer> feed = StockServer.getData()
                .subscribeOn(Schedulers.io());
        feed.subscribe(System.out::println);
        Thread.sleep(5000);
        feed.subscribe(System.out::println);
        */

        // Hot : later subscribers join previous ones' state
        /*Observable<Integer> feed = StockServer.getData()
                .subscribeOn(Schedulers.io())
                .share();
        feed.subscribe(System.out::println);
        Thread.sleep(5000);
        feed.subscribe(System.out::println);
        */

        // Backpressure


        Thread.sleep(30000);
    }

    private static Observable<StockInfo> callBackupPlan(Throwable throwable, List<String> symbols) {
        System.out.println("---- callBackupPlan ----");
        return StockServer.getFeed(symbols)
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(error -> callBackupPlan(error, symbols));
    }

    private static void handleError(Throwable throwable) {
        System.out.println(throwable);
    }

    public static void printStockInfo(StockInfo stockInfo) {
        System.out.println(Thread.currentThread());
        System.out.println(stockInfo);
    }
}
