package com.felicissimo.controller

import com.typesafe.scalalogging.Logger
import common.Direction
import entity.Probe
import repository.ControlCenterRepository

case class Coordinate(x: Int, y: Int, direction: Direction)

class ControlCenter() {
  private val repository = new ControlCenterRepository()
  private val logger = Logger("ControlCenterController")

  def sendProbe(probe: Probe) = {
    val inserted = repository.insert(probe)
    logger.info("Probe deployed: {} at {}", probe.name, probe.coordinate.toString)
    inserted
  }
  def getProbe(probeName: String) = {
    val founded = repository.findByName(probeName)
    logger.info("Probe obtained: {}", founded.toString)
    founded
  }
  def getProbes() = {
    val probes = repository.findAll()
    logger.info("Probes obtained: {} probe(s)", probes.size)
    probes
  }

  def sendCommand(command: String, probeName: String): Option[Probe] = {
    val instructions = command.toList
    val probe = repository.findByName(probeName)
    probe match {
      case Some(probe) => {
        logger.info("Probe identified: {}", probe.name)
        val newCoordinate = probe.executeInstruction(instructions, probe.coordinate)
        logger.info("New coordinate: {}", newCoordinate.toString)
        Some(repository.updatePosition(probe.name, newCoordinate))
      }
      case None => None
    }
  }
}