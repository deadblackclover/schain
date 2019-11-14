package schain

import scala.collection.mutable

case class Block(index: Int, previousHash: String, timestamp: Long, data: String, hash: String)

object Main {
  def getGenesisBlock: Block =
    Block(0, "0", 1573744002L, "SChain genesis block", "12a0cf414ae478c80e1c94bc527a50946aef91c3be7f7d9bc541d42101ca1ebd")

  var blockchain = new mutable.Stack[Block]
  blockchain.push(getGenesisBlock)

  def sha256Hash(text: String): String =
    String.format(
      "%064x",
      new java.math.BigInteger(
        1,
        java.security.MessageDigest
          .getInstance("SHA-256")
          .digest(text.getBytes("UTF-8"))
      )
    )

  def calculateHash(index: Int, previousHash: String, timestamp: Long, data: String): String =
    sha256Hash(index + previousHash + timestamp + data)

  def getLatestBlock: Block = blockchain.top

  def isValidNewBlock(newBlock: Block, previousBlock: Block): (Boolean, String) = {
    val hashNewBlock = calculateHash(newBlock.index, newBlock.previousHash, newBlock.timestamp, newBlock.data)
    if (previousBlock.index + 1 != newBlock.index) (false, "Invalid index")
    else if (previousBlock.hash != newBlock.previousHash) (false, "Invalid previoushash")
    else if (hashNewBlock != newBlock.hash) (false, "Invalid hash")
    else (true, "")
  }

  def addBlock(newBlock: Block): Unit = {
    val (isValid, err) = isValidNewBlock(newBlock, getLatestBlock)
    if (isValid)
      blockchain.push(newBlock)
    else println(err)
  }

  def isValidChain(blockchain: mutable.Stack[Block]): Boolean = {
    for (i <- 0 until blockchain.length - 1) {
      if (blockchain(i).hash != blockchain(i + 1).previousHash) false
    }
    true
  }

  def generateNextBlock(data: String): Block = {
    val previousBlock = getLatestBlock
    val nextIndex     = previousBlock.index + 1
    val nextTimestamp = System.currentTimeMillis / 1000
    val nextHash      = calculateHash(nextIndex, previousBlock.hash, nextTimestamp, data)
    Block(nextIndex, previousBlock.hash, nextTimestamp, data, nextHash)
  }

  def replaceChain(newBlocks: mutable.Stack[Block]): Unit = {
    if (isValidChain(newBlocks) && newBlocks.length > blockchain.length)
      blockchain = newBlocks
    else
      println("No replace")
  }
}
