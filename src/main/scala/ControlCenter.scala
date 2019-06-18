package main

case class Coordinate(x: Int, y: Int, direction: Direction)
case class ControlCenter(coord: Coordinate) {
  val moveTransitionMap = Map[String, Direction](
    "NL" -> West,
    "WL" -> South,
    "SL" -> East,
    "EL" -> North,
    "NR" -> East,
    "WR" -> North,
    "SR" -> West,
    "ER" -> South,
  )

  def toInstruction(letter: Char): Instruction = letter match {
    case 'L' => Left
    case 'R' => Right
    case 'M' => Move
  }

  def moveTo(from: Coordinate): Coordinate = from.direction match {
    case West => from.copy(x = from.x - 1)
    case East => from.copy(x = from.x + 1)
    case South => from.copy(y = from.y - 1)
    case North => from.copy(y = from.y + 1)
  }

  def directTo(from: Coordinate, instruction: Instruction): Coordinate = {
    val movement = from.direction ++ instruction
    val newDirection = moveTransitionMap(movement)
    return from.copy(direction = newDirection)
  }

  def reducer(coordinate: Coordinate, letter: Char): Coordinate = {
    val instruction = toInstruction(letter);
    instruction match {
      case Move => moveTo(from = coordinate)
      case Left => directTo(from = coordinate, Left)
      case Right => directTo(from = coordinate, Right)
    }
  }

  def parseCommand(command: String): Coordinate = {
    def transduce(instructions: List[Char], coordinate: Coordinate): Coordinate = {
      instructions match {
        case Nil => coordinate
        case c :: tail => transduce(tail, reducer(coordinate, c))
      }
    }

    val instructions = command.toList
    return transduce(instructions, coord)
  }
}