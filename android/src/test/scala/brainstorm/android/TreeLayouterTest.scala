package brainstorm.android

import brainstorm.core.MindMap
import brainstorm.core.Node

//class CommonSpec extends WordSpec with Matchers {
//}

class TreeLayouterTest extends CommonSpec {
  // From mind map gets nodes positions
  // Mind map in constructor
  // get 
  "A Tree Layouter" when {
    "given an empty mind map" should {
      "return empty sequence" in {
        val mindMap = new MindMap("Empty")
        val tl = new TreeLayouter(mindMap)
        tl.getNodesAndPositions shouldBe 'empty
      }
    }
    "given five node structure" should {
      "return these nodes with correct coordinates" in {
        val root = new Node("lala", None)
        val first = new Node("first", Some(root))
        val second = new Node("first", Some(root))
        val third = new Node("first", Some(root))
        val fourth = new Node("first", Some(third))
        val fifth = new Node("first", Some(third))
        val sixth = new Node("first", Some(third))
        val mindMap = new MindMap("Empty", Some(root))
        val tl = new TreeLayouter(mindMap)
        val expected = Seq((root, (0.0f, 0.0f)), (first, (1.0f, 0.0f)),
          (second, (1.0f, 1.7951958f)), (third, (1.0f, 3.5903916f)),
          (fourth, (2.0f, 3.5903916f)), (fifth, (2.0,4.4879894f)),
          (sixth, (2.0,5.385587f)))

        tl.getNodesAndPositions should contain theSameElementsAs expected

      }
    }
    "given only root shouldnt crash" in {
      val root = new Node("lala", None)
      val mindMap = new MindMap("Empty", Some(root))
      val tl = new TreeLayouter(mindMap)
      tl.getNodesAndPositions shouldEqual Seq((root, (0.0f,0.0f)))
    }
  }
}
