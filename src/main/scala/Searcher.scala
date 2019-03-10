import java.io.File
import java.util.Scanner

object Searcher extends App {

  override def main(args: Array[String]): Unit = {

    if (args.length == 0) {
      throw new IllegalArgumentException("No​ ​ directory​ ​ given​ ​ to​ ​ index.")
    }
    val indexableDirectory = new File(args.head)
    // ​ TODO: ​ ​ Index ​ ​ all ​ ​ files ​ ​ in ​ ​ indexableDirectory
    val keyboard​ = new Scanner(System.in)

    while (true) {
      System.out.println("search> ")
      val line = keyboard​.nextLine()
      // ​ TODO: ​ ​ Search ​ ​ indexed ​ ​ files ​ ​ for ​ ​ words ​ ​ in ​ ​ line
    }

  }

}
