import java.io.File

import indexing.InvertedIndex
import org.scalatest.{FlatSpec, PrivateMethodTester}

class ReversIndexSpec extends FlatSpec with PrivateMethodTester {

  private def getInvertedIndex(path: String) = {
    val resourcePath = getClass.getResource(path).getPath
    val directory = new File(resourcePath)
    InvertedIndex(directory.listFiles.filter(_.isFile))
  }

  "ReverseIndex" should "build reverse files ok Index" in {

    val numberOfWord: Int = getInvertedIndex("/testData").getIndexSize

    assert(numberOfWord == 349)

  }


  it should "skip files that cannot be read" in {
    val numberOfWords = getInvertedIndex("/").getIndexSize

    numberOfWords equals 0
  }

  it should "return one file for word skorupach" in {
    val index = getInvertedIndex("/testData")

    val result = index.search(List("skorupach"))
    result.size equals 1

  }

  it should "return empty map if no word was found" in {
    val index = getInvertedIndex("/testData")

    val result = index.search(List("notExistingWord"))
    result.isEmpty equals true
  }

  it should "return result how many word found and occurrence in file" in {
    val index = getInvertedIndex("/testData")

    val result = index.search(List("nie", "na", "skorupach"))
    result.values.size equals 3
    assert(result.contains("test2.txt"), true)
    assert(result("test2.txt").numberOfWordsFound == 1)
    assert(result("test2.txt").sumOfWordFound == 2)
  }

}
