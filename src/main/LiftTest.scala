package main

import main.ObservableExtensions._
import rx.lang.scala.Observable

object LiftTest {

  def main(args: Array[String]) {
    Observable.from(List(1,2,3,4,5)).mytail.subscribe(println(_))
  }

}
