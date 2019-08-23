package com.felicissimo

import common._
import org.scalatest._

class DirectionSpec extends FlatSpec with Matchers {
  "North" should "return West to instruction Left" in {
    North ++ Left should be (West)
  }

  it should "return East to instruction Right" in {
    North ++ Right should be (East)
  }

  it should "return North to instruction Move" in {
    North ++ Move should be (North)
  }

  "West" should "return South to instruction Left" in {
    West ++ Left should be (South)
  }

  it should "return North to instruction Right" in {
    West ++ Right should be (North)
  }

  it should "return West to instruction Move" in {
    West ++ Move should be (West)
  }

  "East" should "return North to instruction Left" in {
    East ++ Left should be (North)
  }

  it should "return South to instruction Right" in {
    East ++ Right should be (South)
  }

  it should "return West to instruction Move" in {
    East ++ Move should be (East)
  }
}
