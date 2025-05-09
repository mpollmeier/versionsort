package versionsort

import org.scalatest._

class Test extends WordSpec with Matchers {

  "compare versions" in {
    import VersionHelper.compare

    compare("1.0", "0.9") shouldBe 1
    compare("1.0", "1.0") shouldBe 0
    compare("0.9", "1.0") shouldBe -1

    compare("0.0.0.2", "0.0.0.1") shouldBe 1
    compare("1.0", "0.9") shouldBe 1
    compare("2.0.1", "2.0.0") shouldBe 1
    compare("2.0.1", "2.0") shouldBe 1
    compare("2.0.1", "2") shouldBe 1
    compare("0.9.1", "0.9.0") shouldBe 1
    compare("0.9.2", "0.9.1") shouldBe 1
    compare("0.9.11", "0.9.2") shouldBe 1
    compare("0.9.12", "0.9.11") shouldBe 1
    compare("0.10", "0.9") shouldBe 1
    compare("0.10", "0.10") shouldBe 0
    compare("2.10", "2.10.1") shouldBe -1
    compare("0.0.0.2", "0.1") shouldBe -1
    compare("1.0", "0.9.2") shouldBe 1
    compare("1.10", "1.6") shouldBe 1
    compare("1.10.0.0.0.1", "1.10") shouldBe 1

    compare("1.5.0", "1.5.0-SNAPSHOT") shouldBe 1
    compare("1.5.0", "1.5.0-alpha") shouldBe 1
    compare("1.5.0-alpha", "1.5.0-SNAPSHOT") should be > 1
    compare("1.5", "1.6-SNAPSHOT") shouldBe -1
    compare("1.7", "1.7a") shouldBe -1
    compare("1.7b", "1.7a") shouldBe 1
  }

  "sort scala list" in {
    List("1.10", "1.0", "1.10.1", "0.9", "0.10").sortWith{ (v1, v2) =>
      VersionHelper.compare(v1, v2) > 0
    } shouldBe List("1.10.1", "1.10", "1.0", "0.10", "0.9")
  }

}
