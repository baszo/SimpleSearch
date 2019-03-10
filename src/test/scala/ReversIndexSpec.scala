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

    val test: Int = getInvertedIndex("/testData").getIndexSize

    assert(test == 350)

  }


  it should "skip files that cannot be read" in {
    val test = getInvertedIndex("/").getIndexSize

    test equals 0
  }

  it should "return one file for word skorupach" in {
    val test = getInvertedIndex("/testData")

    val result = test.search(List("skorupach"))
    result.size equals 1
    result.head._2 equals 100

  }

  it should "return empty map if no word was found" in {
    val test = getInvertedIndex("/testData")

    val result = test.search(List("notExistingWord"))
    result.isEmpty equals true
  }

  it should "return result in order of how many words was found in array" in {
    val test = getInvertedIndex("/testData")

    val result = test.search(List("nie", "na", "skorupach"))
    result.size equals 3
    assert(result.head._2 > result.last._2)
  }

}
