package brainstorm.core

class MindMapTest extends CommonSpec {
  // getText
  // addRoot
  // root

  "A MindMap" when {
    "created empty" should {
      val mm = new MindMap("testing")
      "have no root" in {
        mm.root shouldBe None
      }
      "return empty text" in {
        mm.getText(" ") shouldBe 'empty
      }
    }
    "non empty" should {
      val root = new Node("root", None)
      val child1 = new Node("first child", Some(root))
      val child2 = new Node("second child", Some(child1))
      val child3 = new Node("third child", Some(root))
      val mm = new MindMap("testing", Some(root))
      "have specified root" in {
        mm.root.get shouldEqual root
      }
      "return appropriate text" in {
        val expected = Seq("root", "  first child", "    second child", "  third child")
        mm.getText("  ") should contain theSameElementsInOrderAs expected
      }
    }
  }
}
