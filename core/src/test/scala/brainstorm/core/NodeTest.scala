package brainstorm.core

class NodeTest extends CommonSpec {
  "A Node" when {
    "exists" should {
      "preserve order of added children" in {
        val root = new Node("root", None)
        val child1 = new Node("first child", Some(root))
        val child2 = new Node("second child", Some(root))
        val child3 = new Node("third child", Some(root))
        root.children += (child1, child2, child3)
        root.children.toSeq should contain inOrderOnly (child1, child2, child3)
      }
      "have a line it was created with" in {
        val node = new Node("line", None)
        node.line shouldEqual "line"
      }
      "have text from a line it was created with" in {
        val node = new Node("line", None)
        node.text shouldEqual "line"
      }
      "return right strcture of children text" in {
        val root = new Node("root", None)
        val child1 = new Node("first child", Some(root))
        val child2 = new Node("second child", Some(child1))
        val child3 = new Node("third child", Some(root))
        val expected = Seq("root", "  first child", "    second child", "  third child")
        root.getText should contain theSameElementsInOrderAs expected
      }
      "be equal to node with the same structure and text" in {
        val root = new Node("root", None)
        val child1 = new Node("first child", Some(root))
        new Node("second child", Some(child1))
        new Node("third child", Some(root))
        val root2 = new Node("root", None)
        val child21 = new Node("first child", Some(root2))
        new Node("second child", Some(child21))
        new Node("third child", Some(root2))
        root shouldEqual root2
      }
      "not equal to node with different structure and text" in {
        val root = new Node("root", None)
        val child1 = new Node("first child", Some(root))
        new Node("second child", Some(child1))
        new Node("third child", Some(root))
        val root2 = new Node("root", None)
        val child21 = new Node("first child", Some(root2))
        val child22 = new Node("second child", Some(child21))
        new Node("third child", Some(child22))
        root should not equal root2
      }
      "pass equality to hashcode test" in (pending)
    }
    "is root" should {
      "return failure upon removal" in {
        val root = new Node("root", None)
        root.remove should be a 'failure
      }
    }
    "isn't a root" should {
      "delete itself gracefully" in {
        val root = new Node("root", None)
        val child1 = new Node("first child", Some(root))
        val child2 = new Node("second child", Some(root))
        root.children += (child1, child2)
        child1.remove should be a 'success
        root.children should contain only (child2)
      }
    }
  }
}
