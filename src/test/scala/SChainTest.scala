import org.scalatest.FunSuite
import schain._

class SChainTest extends FunSuite {
  val genesis = Block(0, "0", 1573744002L, "SChain genesis block", "12a0cf414ae478c80e1c94bc527a50946aef91c3be7f7d9bc541d42101ca1ebd")

  val block1 = Block(
    1,
    "12a0cf414ae478c80e1c94bc527a50946aef91c3be7f7d9bc541d42101ca1ebd",
    1573744003L,
    "SChain block 1",
    "1027e139bb4141ce431f73eb03849c77679139b255eae03f611a42f1ffb2df8f"
  )
  Main.addBlock(block1)

  val block2 = Block(
    2,
    "1027e139bb4141ce431f73eb03849c77679139b255eae03f611a42f1ffb2df8f",
    1573744004L,
    "SChain block 2",
    "685803c34f2d83a6a92e5c91b520a98bfdc5711c8949f5cfb5beed6913346af1"
  )
  Main.addBlock(block2)

  val block3 = Block(
    3,
    "685803c34f2d83a6a92e5c91b520a98bfdc5711c8949f5cfb5beed6913346af1",
    1573744005L,
    "SChain block 3",
    "3dacec367de2de3f699eebd4b2e58dd3d26cfed7aafb41be55fd2a02527a6e24"
  )
  Main.addBlock(block3)

  val newBlock = Block(
    4,
    "3dacec367de2de3f699eebd4b2e58dd3d26cfed7aafb41be55fd2a02527a6e24",
    1573744006L,
    "SChain block 4",
    "5f339149f19bf84a02feaac29db45fd2eb389dd942609d6023a3d106c8ea2555"
  )

  test("Genesis block test") {
    assert(Main.getGenesisBlock == genesis)
  }

  test("Calculate hash test") {
    val hash = Main.calculateHash(genesis.index, genesis.previousHash, genesis.timestamp, genesis.data)
    assert(hash == "12a0cf414ae478c80e1c94bc527a50946aef91c3be7f7d9bc541d42101ca1ebd")
  }

  test("Latest block test") {
    assert(Main.getLatestBlock == block3)
  }

  test("Valid new block test") {
    assert(Main.isValidNewBlock(newBlock, Main.getLatestBlock) == (true, ""))
  }

  test("Valid chain test") {
    assert(Main.isValidChain(Main.blockchain))
  }

  test("Generate next block test") {
    assert(Main.generateNextBlock("SChain block 4").index == 4)
    assert(Main.generateNextBlock("SChain block 4").previousHash == "3dacec367de2de3f699eebd4b2e58dd3d26cfed7aafb41be55fd2a02527a6e24")
  }
}
