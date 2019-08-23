package com.felicissimo

import com.felicissimo.controller.Coordinate
import common.North
import org.scalatest._
import repository.ControlCenterRepository
import entity.Probe

class ControlCenterRepositorySpec extends FlatSpec with Matchers {
  val repository = new ControlCenterRepository

  "ControlCenterRepository" should "return a empty iterable of Probes" in {
    repository.findAll.size should be (0)
  }

  "ControlCenterRepository" should "return a empty iterable of Probes" in {
    val probe = Probe("MARS-1", Coordinate(1, 2, North))
    repository.insert(probe) should be (probe)
  }
}
