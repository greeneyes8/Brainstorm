package brainstorm.core

class MindMapTest extends CommonSpec {
  "A MindMap" when {
    "created empty" must {
      val mm = new MindMap("testing")
      "have only root" which {
        val root = mm.root
        "has the same name" in {
          root should have ('text ("testing"))
        }
        "has no children" in {
          root.children shouldBe empty
        }
        "has no parent" in {
          root.parent shouldBe None
        }
      }
    }
  }

}
