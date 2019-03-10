package indexing

import java.io.File

import parser.WordParser

import scala.collection.mutable

case class SearchResult(numberOfWordsFound: Int, sumOfWordFound: Int, wordsInRow: Int)

case class WordInfo(counter: Int, position: Seq[Int])

class InvertedIndex(private val invertedIndex: mutable.Map[String, mutable.HashMap[String, WordInfo]]) {


  def search(queries: Seq[String]): Map[String, SearchResult] = {
    println(s"""Searching for ${queries map ("\"" + _ + "\"") mkString " and "}""")

    val queryResult = queries.flatMap(invertedIndex).groupBy(_._1).mapValues { words =>
      if (words.size > 1) {
        val wordsInRow = words.sliding(2).flatMap { wordPair =>
          wordPair.head._2.position.flatMap { pos1 => wordPair(1)._2.position.filter { pos2 => pos1 + 1 == pos2 } }
        }.size
        SearchResult(words.size, words.map(_._2.counter).sum, wordsInRow)
      } else SearchResult(words.size, words.map(_._2.counter).sum, 0)
    }

    queryResult
  }

  def getIndexSize: Int = invertedIndex.keys.size
}

object InvertedIndex {

  def apply(files: Seq[File]): InvertedIndex = {
    val index = mutable.HashMap[String, mutable.HashMap[String, WordInfo]]() withDefaultValue mutable.HashMap.empty

    files.foreach { file =>
      try {
        var position = 0
        scala.io.Source.fromFile(file).getLines.flatMap(WordParser.parse).foreach { word =>
          val x = index.getOrElseUpdate(word, mutable.HashMap(file.getName -> WordInfo(0, Seq.empty)))
          x.get(file.getName) match {
            case Some(value) => x.put(file.getName, WordInfo(value.counter + 1, value.position :+ position))
            case None => x.put(file.getName, WordInfo(1, Seq(position)))
          }
          position += 1
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
