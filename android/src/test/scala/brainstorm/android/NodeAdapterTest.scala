package brainstorm.android

class NodeAdapterTest extends CommonSpec {
    //convertOne
    //convertWhole
    //node adapter convers mindmap into view
    //it got a map and each node convers into a view 

    //czy sie parenty zgadzaja
    //czy wszystko to sa image view
    //czy struktura jest poprawna
    //mockContext (?)

    "A Node Adapter" when {
        "converting only root in a mind map" should {
            "return empty parents" in {
                pending
            }
            "got one view" in {

            } 
        }
        "converting empty mind map" should {
            "return empty Seq" in {

            } 
        }
        "converting root and two children" should {
            "return Seq with three views" in {

            }
            "first element in Seq has no parents" in {

            }
        }
        "wrong coords" should {
            "throw an exception" in {

            }
        }
        "convertOne while empty seq" should {
            "throw an exception" in {

            }
        }
        "changed root" should {
            ""
        }

    }
}