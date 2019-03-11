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

  def getFilesForDirectory(directory: File): Seq[File] = {
    val filesInDirectory = directory.listFiles.filter(_.isFile)

    if (filesInDirectory.isEmpty) {
      throw new IllegalArgumentException("no files in given directory")
    }
    filesInDirectory.toList
  }

}
