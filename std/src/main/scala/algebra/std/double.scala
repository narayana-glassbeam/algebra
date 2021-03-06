package algebra
package std

import algebra.lattice.DistributiveLattice
import algebra.ring.Field
import algebra.std.util.StaticMethods

import java.lang.Math

trait DoubleInstances extends cats.kernel.std.DoubleInstances {
  implicit val doubleAlgebra: Field[Double] =
    new DoubleAlgebra

  // This is not Bounded due to the presence of NaN
  val DoubleMinMaxLattice: DistributiveLattice[Double] =
    DistributiveLattice.minMax[Double]
}

/**
 * Due to the way floating-point equality works, this instance is not
 * lawful under equality, but is correct when taken as an
 * approximation of an exact value.
 *
 * If you would prefer an absolutely lawful fractional value, you'll
 * need to investigate rational numbers or more exotic types.
 */
class DoubleAlgebra extends Field[Double] with Serializable {

  def zero: Double = 0.0
  def one: Double = 1.0

  def plus(x: Double, y: Double): Double = x + y
  def negate(x: Double): Double = -x
  override def minus(x: Double, y: Double): Double = x - y

  def times(x: Double, y: Double): Double = x * y
  def div(x: Double, y: Double): Double = x / y
  override def reciprocal(x: Double): Double = 1.0 / x
  override def pow(x: Double, y: Int): Double = Math.pow(x, y.toDouble)

  def quot(x: Double, y: Double): Double = (x - (x % y)) / y
  def mod(x: Double, y: Double): Double = x % y

  override def quotmod(x: Double, y: Double): (Double, Double) = {
    val m = x % y
    ((x - m) / y, m)
  }

  def gcd(x: Double, y: Double): Double = StaticMethods.gcd(x, y)

  override def fromInt(x: Int): Double = x.toDouble
  override def fromDouble(x: Double): Double = x
}
