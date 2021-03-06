package caseapp
package demo

import caseapp.core.app.CommandAppA
import caseapp.core.help.CommandsHelp

@AppVersion("0.1.0")
@ArgsName("files")
final case class DemoOptions(
  first: Boolean,
  @ExtraName("V") @HelpMessage("Set a value") value : Option[String],
  @ExtraName("v") @HelpMessage("Be verbose") verbose : Int @@ Counter,
  @ExtraName("S") @ValueDescription("stages")  stages : List[String]
)

object Demo extends CaseApp[DemoOptions] {

  def run(options: DemoOptions, remainingArgs: RemainingArgs) =
    Console.err.println(this)
}

@AppName("Glorious App")
@AppVersion("0.1.0")
final case class MyAppOptions(
  @ValueDescription("a foo") @HelpMessage("Specify some foo") foo: Option[String],
  bar: Int
)

object MyApp extends CaseApp[MyAppOptions] {
  def run(options: MyAppOptions, remainingArgs: RemainingArgs) = {}
}


@AppName("Demo")
@AppVersion("1.0.0")
@ProgName("demo-cli")
sealed abstract class DemoCommand extends Product with Serializable

final case class First(
  @ExtraName("v") verbose: Int @@ Counter,
  @ValueDescription("a bar") @HelpMessage("Set bar") bar: String = "default-bar"
) extends DemoCommand

@CommandName("second")
final case class Secondd(
  extra: List[Int],
  @ExtraName("S")
  @ValueDescription("stages")
    stages : List[String]
) extends DemoCommand

object CommandAppTest extends CommandApp[DemoCommand] {

  def run(options: DemoCommand, remainingArgs: RemainingArgs): Unit =
    options match {
      case _: First =>
        Console.err.println(s"First: $this")
      case _: Secondd =>
        Console.err.println(s"Second: $this")
    }
}


object ManualCommandStuff {

  case object Command1 extends CaseApp[Command1Opts] {
    def run(options: Command1Opts, args: RemainingArgs): Unit = {

    }
  }

  case object Command2 extends CaseApp[Command2Opts] {
    def run(options: Command2Opts, args: RemainingArgs): Unit = {

    }
  }

  val parser = CommandParser.nil
    .add(Command1)
    .add(Command2)
    .as[ManualCommandOptions]

  val help = CommandsHelp.nil
    .add(Command1)
    .add(Command2)
    .as[ManualCommandOptions]

}

object ManualCommand extends CommandApp()(ManualCommandStuff.parser, ManualCommandStuff.help) {
  def run(options: ManualCommandOptions, args: RemainingArgs): Unit = {

  }
}

object ManualCommandNotAdtStuff {

  case object Command1 extends CaseApp[ManualCommandNotAdtOptions.Command1Opts] {
    def run(options: ManualCommandNotAdtOptions.Command1Opts, args: RemainingArgs): Unit = {

    }
  }

  case object Command2 extends CaseApp[ManualCommandNotAdtOptions.Command2Opts] {
    def run(options: ManualCommandNotAdtOptions.Command2Opts, args: RemainingArgs): Unit = {

    }
  }

  val parser = CommandParser.nil
    .add(Command1)
    .add(Command2)
    .reverse

  val help = CommandsHelp.nil
    .add(Command1)
    .add(Command2)
    .reverse

}

object ManualCommandNotAdt extends CommandAppA(ManualCommandNotAdtStuff.parser, ManualCommandNotAdtStuff.help) {
  def runA = args => options => {

  }
}