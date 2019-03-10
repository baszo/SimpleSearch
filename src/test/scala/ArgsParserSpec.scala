import java.nio.file.Files

import org.scalatest.FlatSpec
import parser.ArgsParser

class ArgsParserSpec extends FlatSpec {

  "ArgParser" should "return directory if exists" in {
    val testDir = Files.createTempDirectory("test").toFile

    val directory = ArgsParser.validateAndParseArgs(Array(testDir.getAbsolutePath))

    assert(directory.equals(testDir), "Valid Directory not returned")
  }

  it should "throw exception if no args" in {
    try {
      val test = ArgsParser.validateAndParseArgs(Array.empty)
    } catch {
      case e: IllegalArgumentException => assert(e.getMessage.equals("No​ ​ directory​ ​ given​ ​ to​ ​ index."))
    }
  }


}
