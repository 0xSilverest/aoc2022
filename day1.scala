import scala.io.Source

def readInput(fileName: String): List[List[Int]] =
  splitInput(
    Source.fromFile(s"./input/${fileName}").getLines.toList)

def splitInput(xs: List[String], acc: List[List[Int]] = List.empty): List[List[Int]] =
  xs match
    case List() => acc
    case _ =>
      val (ys, rest) = xs.span( _.nonEmpty )
      splitInput(
        if rest.isEmpty then rest else rest.tail,
        ys.map(_.toInt) :: acc)

def howMuchCalsOfBuffElf(elfCals: List[List[Int]]) =
  elfCals.map(_.sum).max

val input = readInput("day1.txt")
val maxCal = howMuchCalsOfBuffElf(input)
println(s"Buff elf : ${maxCal}")

val top3ElfsCals = input.map(_.sum).sorted(Ordering.Int.reverse).take(3).sum
println(s"Next 3 fat elfs calories : ${top3ElfsCals}")
