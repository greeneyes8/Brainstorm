package brainstorm.core

class NodeSpec extends CommonSpec {
  "A Node" when {
    "is root" must {
      "return failure upon removal" in {
        val root = new Node("root", None)
        root.remove should be a 'failure
      }
    }
    "isn't a root" must {
      "delete itself" in {
        val root = new Node("root", None)
        val child1 = new Node("first child", Some(root))
        val child2 = new Node("second child", Some(root))
        root.children += (child1, child2)
        child1.remove should be a 'success
        root.children should contain (child2)
        root.children should not contain (child1)
      }
    }
  }
}
