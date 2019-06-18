package main

sealed trait Instruction
case object Left extends Instruction
case object Right extends Instruction
case object Move extends Instruction