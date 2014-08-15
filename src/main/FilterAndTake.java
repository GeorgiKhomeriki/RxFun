package main;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.internal.operators.OperatorFilter;
import rx.internal.operators.OperatorTake;

public class FilterAndTake<T> implements Observable.Operator<T, T> {

    private OperatorFilter<T> filter;
    private OperatorTake<T> take;

    public FilterAndTake(Func1<? super T, Boolean> predicate, int n) {
        this.filter = new OperatorFilter<T>(predicate);
        this.take = new OperatorTake<T>(n);
    }

    @Override
    public Subscriber<? super T> call(final Subscriber<? super T> child) {
        return filter.call(take.call(child));
    }

    public static void main(String[] args) {
        Observable<Integer> xs = Observable.range(1, 8);

        Func1<Integer, Boolean> predicate = new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer x) {
                return x % 2 == 0;
            }
        };

        Action1<Integer> action = new Action1<Integer>() {
            @Override
            public void call(Integer x) {
                System.out.println("> " + x);
            }
        };

        xs.lift(new FilterAndTake<Integer>(predicate, 2)).subscribe(action);
    }
}
