package entity

import common._
import main.Coordinate

case class Probe(name: String, coordinate: Coordinate) {
  private def applyMove(coordinate: Coordinate, instruction: Instruction): Coordinate = {
    instruction match {
      case Move => moveTo(from = coordinate)
      case common.Left => directTo(from = coordinate, common.Left)
      case common.Right => directTo(from = coordinate, common.Right)
    }
  }

  private def moveTo(from: Coordinate): Coordinate = from.direction match {
    case West => from.copy(x = from.x - 1)
    case East => from.copy(x = from.x + 1)
    case South => from.copy(y = from.y - 1)
    case North => from.copy(y = from.y + 1)
  }

  private def directTo(from: Coordinate, instruction: Instruction): Coordinate = {
    val newDirection = from.direction ++ instruction
    return from.copy(direction = newDirection)
  }

  def executeInstruction(instructions: List[Char], coordinate: Coordinate): Coordinate = {
    def toInstruction(letter: Char): Instruction = letter match {
      case 'L' => common.Left
      case 'R' => common.Right
      case 'M' => Move
    }

    instructions match {
      case Nil => coordinate
      case c :: tail => executeInstruction(tail, applyMove(coordinate, toInstruction(c)))
    }
  }
}