package main

sealed trait Direction {
  def turnTo(instruction: Instruction): String
}

case object North extends Direction {
  def turnTo(instruction: Instruction): String = instruction match {
    case Left => "NL"
    case Right => "NR"
    case Move => ""
  }
}

case object West extends Direction {
  def turnTo(instruction: Instruction): String = instruction match {
    case Left => "WL"
    case Right => "WR"
    case Move => ""
  }
}

case object East extends Direction {
  def turnTo(instruction: Instruction): String = instruction match {
    case Left => "EL"
    case Right => "ER"
    case Move => ""
  }
}

case object South extends Direction {
  def turnTo(instruction: Instruction): String = instruction match {
    case Left => "SL"
    case Right => "SR"
    case Move => ""
  }
}