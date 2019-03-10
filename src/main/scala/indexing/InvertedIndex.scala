package indexing

import java.io.File

import scala.collection.mutable

class InvertedIndex(private val invertedIndex: mutable.Map[String, Set[String]]) {

  def search(queries: Seq[String]): Seq[(String, Int)] = ???

  def getIndexSize = ???
}

object InvertedIndex {

  def apply(files: Seq[File]): InvertedIndex = ???

}
