package main

import rx.lang.scala.{Subscriber, Observable}

package object ObservableExtensions {

  implicit class MyObservable[+T](val obs: Observable[T]) {

    def mylift[R](operator: Subscriber[R] => Subscriber[T]): Observable[R] = {
      Observable( subscriber => obs.subscribe(operator(subscriber)) )
    }

    def mytail: Observable[T] = {
      mylift (
        (subscriber: Subscriber[T]) => {
          var isFirst = true
          Subscriber[T](
            subscriber,
            (v: T) => if (isFirst) isFirst = false
                      else subscriber.onNext(v),
            e => subscriber.onError(e),
            () => if (isFirst) subscriber.onError(new Exception("tail of empty Observable"))
                  else subscriber.onCompleted()
          )
        }
      )
    }

  }

}
