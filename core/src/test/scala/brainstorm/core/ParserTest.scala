package brainstorm.core

class ParserTest extends CommonSpec {
  "Result of parsing line" when {
    "is empty" should {
      "throw an Exeption" in (pending)
    }
    "has text" should {
      "return Node" which {
        val parent = new Node("parent", None)
        val node = Parser.parseLine("  someText", Some(parent))
        "has the same text without indentation" in {
          node should have ('text ("someText"))
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

  "Result of parsing text" when {
    "has root and two children" should {
      "be such structure" in (pending)
    }
    "has root, children and grandchildren" should {
      "be such structure" in (pending)
    }
  }
}
