package main

import common.Direction
import entity.Probe
import repository.ControlCenterRepository

case class Coordinate(x: Int, y: Int, direction: Direction)

class ControlCenter() {
  private val repository = new ControlCenterRepository()

  def sendProbe(probe: Probe) = repository.insert(probe)
  def getProbe(probeName: String) = repository.findByName(probeName)
  def getProbes() = repository.findAll()
  def sendCommand(command: String, probeName: String): Option[Probe] = {
    val instructions = command.toList
    val probe = repository.findByName(probeName)
    probe match {
      case Some(probe) => {
        val newCoordinate = probe.executeInstruction(instructions, probe.coordinate)
        Some(repository.updatePosition(probe.name, newCoordinate))
      }
      case None => None
    }
  }
}