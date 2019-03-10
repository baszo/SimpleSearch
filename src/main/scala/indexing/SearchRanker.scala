package indexing

import indexing.Sort.SortStrategy


case class QueryResult(fileName: String, percentage: Int)

object QueryResult {
  implicit def convert2QueryResult(data: Seq[(String, Int)]): Seq[QueryResult] = {
    data.map(dat => QueryResult(dat._1, dat._2))
  }
}

case class SearchRanker(invertedIndex: InvertedIndex) {

  private def convertToPercent(mapFileWordsMatched: Map[String, SearchResult], totalCount: Int): Seq[(String, Int)] = {
    mapFileWordsMatched.map {
      case (k, v) =>
        k -> ((v.numberOfWordsFound.toDouble / totalCount) * 100).toInt
    }.toSeq
  }


  def query(words: List[String]): Seq[QueryResult] = {
    val documents = invertedIndex.search(words)
    val sorted = SortStrategy.sort(SortStrategy.sortByMatchedWordsAndWordsInRow, documents)
    convertToPercent(sorted, words.size).take(10)
  }


}
