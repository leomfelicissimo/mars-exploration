package common

import main.Move

sealed trait Direction {
  def ++(instruction: Instruction): Direction
}

case object North extends Direction {
  def ++(instruction: Instruction): Direction = instruction match {
    case Left => West
    case Right => East
    case Move => North
  }
}

case object West extends Direction {
  def ++(instruction: Instruction): Direction = instruction match {
    case Left => South
    case Right => North
    case Move => West
  }
}

case object East extends Direction {
  def ++(instruction: Instruction): Direction = instruction match {
    case Left => North
    case Right => South
    case Move => East
  }
}

case object South extends Direction {
  def ++(instruction: Instruction): Direction = instruction match {
    case Left => East
    case Right => West
    case Move => South
  }
}