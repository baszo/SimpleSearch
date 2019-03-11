package indexing.Sort

import indexing.SearchResult

object SortStrategy {
  private def Desc[T: Ordering] = implicitly[Ordering[T]].reverse

  def sortByMatchedWordsAndNumberOfOccurrence(results: Map[String, SearchResult]) = {
    results.toSeq.sortBy(d => (d._2.numberOfWordsFound, d._2.sumOfWordFound))(Desc).toMap
  }

  def sortByMatchedWordsAndWordsInRow(results: Map[String, SearchResult]) = {
    results.toSeq.sortBy(d => (d._2.wordsInRow, d._2.sumOfWordFound))(Desc).toMap
  }

  def sortByMatchedWords(results: Map[String, SearchResult]) = {
    results.toSeq.sortBy(d => d._2.sumOfWordFound)(Desc).toMap
  }


  def sort(sortFunction: Map[String, SearchResult] => Map[String, SearchResult], data: Map[String, SearchResult]) =
    sortFunction(data)

}
