package repository

import entity.Probe
import main.Coordinate

class ControlCenterRepository {
  var probes = Seq[Probe]()

  def insert(probe: Probe): Probe = {
    probes = probes :+ probe
    probe
  }

  def findByName(probeName: String) = {
    probes.find(p => p.name == probeName)
  }

  def updatePosition(probeName: String, coordinate: Coordinate) ={

  }
}
