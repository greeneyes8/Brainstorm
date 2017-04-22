package brainstorm.core

class MindMapTest extends CommonSpec {
  "A MindMap" when {
    "created empty" should {
      val mm = new MindMap("testing")
      "have only root" in {
        val root = mm.root
        root should have ('text ("testing"))
        root.children shouldBe empty
        root.parent shouldBe None
      }
    }
  }

}
