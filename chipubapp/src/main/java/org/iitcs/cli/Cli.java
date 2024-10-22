package org.iitcs.cli;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "chipub", mixinStandardHelpOptions = true)
public class Cli {
    int cli() {
        return CommandLine.ExitCode.OK;
    }

}
