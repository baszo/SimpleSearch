package indexing

import java.io.File

import parser.WordParser

import scala.collection.mutable

case class SearchResult(numberOfWordsFound: Int, sumOfWordFound: Int)

class InvertedIndex(private val invertedIndex: mutable.Map[String, mutable.HashMap[String, Int]]) {


  def search(queries: Seq[String]): Map[String, SearchResult] = {
    println(s"""Searching for ${queries map ("\"" + _ + "\"") mkString " and "}""")

    val mapFileWordsMatched = queries.par.flatMap(invertedIndex).groupBy(_._1).mapValues { words =>
      val sumOfWords = words.map(_._2).sum
      val matched = words.size
      SearchResult(matched, sumOfWords)
    }
    mapFileWordsMatched.toList.toMap
  }

  def getIndexSize: Int = invertedIndex.keys.size
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
          println("skipped file " + file.getName)
      }
    }
    new InvertedIndex(index)
  }

}
