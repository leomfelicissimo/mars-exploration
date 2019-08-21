package main

import common.Direction
import entity.Probe

case class Coordinate(x: Int, y: Int, direction: Direction)

class ControlCenter() {
  var probes: Seq[Probe] = Seq()

  def sendProbe(probeName: String, coordinate: Coordinate) = {
    val probe = new Probe(probeName, coordinate)
    probes = probes :+ probe
  }

  def sendCommand(command: String, probeName: String): Option[Coordinate] = {
    val instructions = command.toList
    val probe = probes.find(p => p.name.equals(probeName))
    probe match {
      case Some(probe) => Some(probe.executeInstruction(instructions, probe.coordinate))
      case None => None
    }
  }
}