package main

import main.ObservableExtensions._
import rx.lang.scala.Observable

object LiftTest {

  def main(args: Array[String]) {

    val xs = Observable.from(List(1,2,3,4,5,6))

    xs.mytail.subscribe(x => println(s"> mytail: $x"))

    xs.mymap(_ * 2).subscribe(x => println(s"> mymap: $x"))

    xs.myfilter(_ % 2 == 0).subscribe(x => println(s"> myfilter: $x"))

  }

}
