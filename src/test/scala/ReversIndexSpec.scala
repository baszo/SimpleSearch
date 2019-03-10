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

    assert(test == 353)

  }


  it should "skip files that cannot be read" in {
    val test = getInvertedIndex("/").getIndexSize

    test equals 0
  }

  it should "return one file for word skorupach" in {
    val test = getInvertedIndex("/testData")

    val result = test.search(List("skorupach"))
    result.size equals 1

  }

  it should "return empty map if no word was found" in {
    val test = getInvertedIndex("/testData")

    val result = test.search(List("notExistingWord"))
    result.isEmpty equals true
  }

  it should "return result how many word found and occurrence in file" in {
    val test = getInvertedIndex("/testData")

    val result = test.search(List("nie", "na", "skorupach"))
    result.values.size equals 3
    assert(result.contains("test2.txt"), true)
    assert(result("test2.txt").numberOfWordsFound == 1)
    assert(result("test2.txt").sumOfWordFound == 2)
  }

  it should "return most word in row accurance" in {
    val test = getInvertedIndex("/testData")
    val result = test.search(List("to" ,"be", "or", "not", "to", "be"))
    assert(result.contains("test1.txt"), true)
    assert(result.contains("test3.txt"), true)
    assert(result("test1.txt").numberOfWordsFound == 6)
    assert(result("test3.txt").numberOfWordsFound == 6)
    assert(result("test1.txt").wordsInRow > result("test3.txt").wordsInRow)
  }

}
