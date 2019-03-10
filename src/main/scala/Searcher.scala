import java.util.Scanner

import parser.ArgsParser

object Searcher extends App {

  override def main(args: Array[String]): Unit = {

    try {
      val directory = ArgsParser.validateAndParseArgs(args)

      // ​ TODO: ​ ​ Index ​ ​ all ​ ​ files ​ ​ in ​ ​ indexableDirectory
      val keyboard​ = new Scanner(System.in)

      while (true) {
        System.out.println("search> ")
        val line = keyboard​.nextLine()
        // ​ TODO: ​ ​ Search ​ ​ indexed ​ ​ files ​ ​ for ​ ​ words ​ ​ in ​ ​ line
      }
    } catch {
      case e: IllegalArgumentException => {
        System.out.println(e.getMessage)
        System.exit(1)
      }
    }

  }

}
