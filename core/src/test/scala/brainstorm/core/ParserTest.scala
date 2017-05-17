package brainstorm.core

class ParserTest extends CommonSpec {

  // parseFile
  // parseMap
  // parseText
  // parseLine

  "Result of parsing text" when {
    "has root and two children" should {
      "be such structure" in {
        val text = Seq("root", "  chld1", "  chld2")
        val expected = new Node("root", None)
        val chld1 = new Node("chld1", Some(expected))
        val chld2 = new Node("chld2", Some(expected))
        val root = Parser.parseText(text, None)
        root.getText shouldEqual text
        root shouldEqual expected
      }
    }
    "has root, children and grandchildren" should {
      "be such structure" in {
        val text = Seq("root", "  chld1", "    chld2")
        val expected = new Node("root", None)
        val chld1 = new Node("chld1", Some(expected))
        val chld2 = new Node("chld2", Some(chld1))
        val root = Parser.parseText(text, None)
        root.getText shouldEqual text
        root shouldEqual expected
      }
    }
  }

  "Result of parsing line" when {
    "is empty" should {
      "throw an Exeption" in (pending)
    }
    "has text" should {
      "return Node" which {
        val parent = new Node("parent", None)
        val node = Parser.parseLine("  someText", Some(parent))
        "has right text" in {
          node should have ('text ("someText"))
        }
        "has right line" in {
           node should have ('line ("someText"))
        }
        "has specified parent" in {
          node.parent.get should be (parent)
        }
        "has no children" in {
          node.children shouldBe empty
        }
      }
    }
  }
}
