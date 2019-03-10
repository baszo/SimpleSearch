package parser

object FileParser {

  private val wordRegex = raw"([a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ]+)".r

  def parse(s: String) = wordRegex.findAllIn(s).map(_.toString.toLowerCase)

}
