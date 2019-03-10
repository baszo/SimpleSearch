package indexing

import java.io.File

import parser.WordParser

import scala.collection.mutable

class InvertedIndex(private val invertedIndex: mutable.Map[String, mutable.HashMap[String, Int]]) {

  private def convertToPercentAndSort(mapFileWordsMatched: Map[String, Int], totalCount: Int) = {
    mapFileWordsMatched.map {
      case (k, v) =>
        k -> ((v.toDouble / totalCount) * 100).toInt
    }.toSeq.sortBy(-_._2)
  }

  def search(queries: Seq[String]): Seq[(String, Int)] = {
    val totalCount = queries.size
    println(s"""Searching for ${queries map ("\"" + _ + "\"") mkString " and "}""")

    val mapFileWordsMatched = queries.flatMap(invertedIndex).map(_._1).groupBy(identity).mapValues(_.size)
    convertToPercentAndSort(mapFileWordsMatched, totalCount).take(10)
  }

  def getIndexSize = invertedIndex.keys.size
}

object InvertedIndex {

  def apply(files: Seq[File]): InvertedIndex = {
    val index = mutable.HashMap[String, mutable.HashMap[String, Int]]() withDefaultValue mutable.HashMap.empty

    files.foreach { file =>
      try {
        scala.io.Source.fromFile(file).getLines.flatMap(WordParser.parse).foreach { word =>
          val x = index.getOrElseUpdate(word, mutable.HashMap(file.getName -> 0))
          x.put(file.getName, x.getOrElse(file.getName, 0) + 1)
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
