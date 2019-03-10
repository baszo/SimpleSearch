package indexing

import java.io.File

import parser.WordParser

import scala.collection.mutable

class InvertedIndex(private val invertedIndex: mutable.Map[String, Set[String]]) {

  def search(queries: Seq[String]): Seq[(String, Int)] = ???

  def getIndexSize = invertedIndex.keys.size
}

object InvertedIndex {

  def apply(files: Seq[File]): InvertedIndex = {
    val index = mutable.HashMap[String, Set[String]]() withDefaultValue Set.empty

    files.foreach { file =>
      try {
        scala.io.Source.fromFile(file).getLines.flatMap(WordParser.parse).foreach { word =>
          index += (word -> (index(word) + file.getName))
        }
      }
      catch {
        case e: Exception =>
          e.printStackTrace()
          println("skipped file")
      }
    }
    new InvertedIndex(index)
  }

}
