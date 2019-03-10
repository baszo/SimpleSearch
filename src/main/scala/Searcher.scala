import java.util.Scanner

import indexing.InvertedIndex
import parser.ArgsParser

object Searcher extends App {

  def startProgram(index: InvertedIndex) = {

    // ​ TODO: ​ ​ Index ​ ​ all ​ ​ files ​ ​ in ​ ​ indexableDirectory
    val keyboard​ = new Scanner(System.in)

    while (true) {
      System.out.println("search> ")
      val line = keyboard​.nextLine()
      // ​ TODO: ​ ​ Search ​ ​ indexed ​ ​ files ​ ​ for ​ ​ words ​ ​ in ​ ​ line
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
