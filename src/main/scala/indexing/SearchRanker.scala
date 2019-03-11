package indexing


case class QueryResult(fileName: String, percentage: Int)

object QueryResult {
  implicit def convert2QueryResult(data: Seq[(String, Int)]): Seq[QueryResult] = {
    data.map(dat => QueryResult(dat._1, dat._2))
  }
}

case class SearchRanker(invertedIndex: InvertedIndex) {
  private def Desc[T: Ordering] = implicitly[Ordering[T]].reverse

  private def convertToPercent(mapFileWordsMatched: Map[String, SearchResult], totalCount: Int): Seq[(String, Int)] = {
    mapFileWordsMatched.map {
      case (k, v) =>
        k -> ((v.numberOfWordsFound.toDouble / totalCount) * 100).toInt
    }.toSeq
  }

  private def sortByMatchedWordsAndNumberOfOccurrence(results: Map[String, SearchResult]) = {
    results.toSeq.sortBy(d => (-1 * d._2.numberOfWordsFound, -1 * d._2.sumOfWordFound)).toMap
  }

  private def sortByMachedWords(results: Map[String, SearchResult]) = {
    results.toSeq.sortBy(d => d._2.sumOfWordFound)(Desc).toMap
  }

  def query(words: List[String]): Seq[QueryResult] = {
    val documents = invertedIndex.search(words)
    val sorted = sortByMatchedWordsAndNumberOfOccurrence(documents)
    convertToPercent(sorted, words.size).take(10)
  }


}
