package main

import common.North

object Main {
  def main(args: Array[String]): Unit = {
    val controlCenter = new ControlCenter()
    controlCenter.sendProbe("MARS-1", Coordinate(1, 2, North))
    val result = controlCenter.sendCommand("LMLMLMLMM", "MARS-1")
    println(result)
  }
}