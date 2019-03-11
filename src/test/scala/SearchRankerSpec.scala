import java.io.File

import indexing.{InvertedIndex, SearchRanker}
import org.scalatest.FlatSpec

class SearchRankerSpec extends FlatSpec {

  private def getSearchRanker(path: String) = {
    val resourcePath = getClass.getResource(path).getPath
    val directory = new File(resourcePath)
    val invertedIndex = InvertedIndex(directory.listFiles.filter(_.isFile))
    SearchRanker(invertedIndex)
  }

  "SearchRanker" should "return one file for word skorupach" in {
    val searchRanker = getSearchRanker("/testData")

    val result = searchRanker.query(List("skorupach"))
    result.size equals 1
    result.head.percentage equals 100

  }

  it should "return empty map if no word was found" in {
    val searchRanker = getSearchRanker("/testData")

    val result = searchRanker.query(List("notExistingWord"))
    result.isEmpty equals true
  }

  it should "return result in order of how many words was found in array" in {
    val searchRanker = getSearchRanker("/testData")

    val result = searchRanker.query(List("nie", "na", "skorupach"))
    result.size equals 3
    assert(result.head.percentage > result.last.percentage)
  }

  it should "both files have both words but test1 have more occurrence of word" in {
    val searchRanker = getSearchRanker("/testData")

    val result = searchRanker.query(List("dużo", "kosztowali"))
    result.size equals 2
    assert(result.head.percentage == result(1).percentage)
    assert(result.head.fileName.equals("test1.txt"))
    assert(result(1).fileName.equals("test2.txt"))
  }

}
