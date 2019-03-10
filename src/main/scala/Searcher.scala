import java.util.Scanner

import indexing.InvertedIndex
import parser.{ArgsParser, WordParser}

object Searcher extends App {

  def startProgram(index: InvertedIndex) = {

    print("search> ")
    Iterator.continually(scala.io.StdIn.readLine).takeWhile(!_.equals(":quit")).foreach { line =>
      val result = index.search(WordParser.parse(line).toList)

      result.foreach {
        case (k, v) =>
          println(s"File $k : $v %")
      }

      println("search> ")
    }

  }

  override def main(args: Array[String]): Unit = {

    try {
      val directory = ArgsParser.validateAndParseArgs(args)

      val index = InvertedIndex(ArgsParser.getFilesForDirectory(directory))

      startProgram(index)

    } catch {
      case e: IllegalArgumentException => {
        println(e.getMessage)
        System.exit(1)
      }
    }

  }

}
