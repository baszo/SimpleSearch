package parser

import java.io.File

object ArgsParser {

  private def validateDirectory(path: String): File = {
    val directory = new File(path)
    if (!directory.exists() && !directory.isDirectory) {
      throw new IllegalArgumentException("given path not exists or it is not a​​ directory.")
    }
    directory
  }


  def validateAndParseArgs(args: Array[String]) = args match {
    case _ if args.isEmpty => throw new IllegalArgumentException("No​ ​ directory​ ​ given​ ​ to​ ​ index.")
    case array => validateDirectory(array.head)
    case _ => throw new IllegalArgumentException("Usage: Searcher Directory")
  }

}
