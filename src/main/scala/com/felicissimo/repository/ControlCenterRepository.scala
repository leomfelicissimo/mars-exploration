package repository

import com.felicissimo.controller.Coordinate
import entity.Probe

class ControlCenterRepository {
  private var probes = Map[String, Probe]()

  def findAll() = probes.values
  def findByName(probeName: String): Option[Probe] = probes get probeName
  def insert(probe: Probe): Probe = {
    probes = probes + (probe.name -> probe)
    probe
  }
  def updatePosition(probeName: String, coordinate: Coordinate) ={
    val probe = Probe(probeName, coordinate)
    probes = probes + (probeName -> probe)
    probe
  }
}
