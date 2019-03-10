import java.util.Scanner

import indexing.{InvertedIndex, SearchRanker}
import parser.{ArgsParser, WordParser}

object Searcher extends App {

  def startProgram(searchRanker: SearchRanker) = {

    print("search> ")
    Iterator.continually(scala.io.StdIn.readLine).takeWhile(!_.equals(":quit")).foreach { line =>
      val results = searchRanker.query(WordParser.parse(line).toList)

      if (results.isEmpty)
        println("no matches found")
      else
        results.foreach(result =>
          println(s"File ${result.fileName} : ${result.percentage} %"))

      print("search> ")
    }

  }

  override def main(args: Array[String]): Unit = {

    try {
      val directory = ArgsParser.validateAndParseArgs(args)

      val index = InvertedIndex(ArgsParser.getFilesForDirectory(directory))
      val searchRanker = SearchRanker(index)

      startProgram(searchRanker)

    } catch {
      case e: IllegalArgumentException => {
        println(e.getMessage)
        System.exit(1)
      }
    }

  }

}
