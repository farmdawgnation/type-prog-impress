package com.joescii.typeprog

import Nat._

sealed trait Vector[Size <: Nat] {
  def ::(head:Int):Vector[NatN[Size]] =
    NonEmptyVector(head, this)
  def +(that:Vector[Size]):Vector[Size]
  def ??[ThatSize <: Nat]
    (that:Vector[ThatSize])
    :Vector[Size + ThatSize]
  def ++[ThatSize <: Nat](that:Vector[ThatSize])
    :Vector[Size + ThatSize]
}

case object VNil extends Vector[Nat0] {
  def +(that:Vector[Nat0]) = this
  def ??[ThatSize <: Nat](that:Vector[ThatSize]) = that
  def ++[ThatSize <: Nat](that:Vector[ThatSize]) = that
}

case class NonEmptyVector[TailSize <: Nat]
  (head:Int, tail:Vector[TailSize])
  extends Vector[NatN[TailSize]]
{
  type Size = NatN[TailSize]
  def +(that:Vector[Size]) = {
    that match {
      case NonEmptyVector(head2, tail2) =>
        NonEmptyVector(head + head2, tail + tail2)
    }
  }
  def ??[ThatSize <: Nat](that:Vector[ThatSize]) =
    NonEmptyVector(head, tail ++ that)
  def ++[ThatSize <: Nat](that:Vector[ThatSize]) =
    NonEmptyVector(head, tail ++ that)
}
