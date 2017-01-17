import rx.Subscriber;
import rx.Observable;
import java.util.*;

public class StockServer {

    public static Observable<StockInfo> getFeed(final List<String> symbols) {
       return Observable.create(
               subscriber -> processRequest(subscriber, symbols));
    }

    public static Observable<Integer> getData() {
        return Observable.create(subscriber -> processRequest_2(subscriber));
    }

    private static void processRequest_2(Subscriber<? super Integer> subscriber) {
        int count = 0;
        while (true) {
            subscriber.onNext(count++);
            sleep(1000);
        }
    }

    private static void sleep(int sleepMs) {
        try {
            Thread.sleep(sleepMs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private static void processRequest(Subscriber<? super StockInfo> subscriber,
                                       List<String> symbols) {
        System.out.println("processing..");

        while(!subscriber.isUnsubscribed()) {
            symbols.stream()
                    .map(StockFetcher::fetch)
                    .forEach(subscriber::onNext);
        }

        //subscriber.onCompleted();
        //subscriber.onNext(new StockInfo("blah", 0.0));
    }
}
