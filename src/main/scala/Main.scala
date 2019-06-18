package main

object Main {
  def main(args: Array[String]): Unit = {
    val controlCenter = ControlCenter(Coordinate(3, 3, East))
    val result = controlCenter.parseCommand("MMRMMRMRRM")
    println(result)
  }
}