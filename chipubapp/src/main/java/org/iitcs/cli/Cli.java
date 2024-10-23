package org.iitcs.cli;

import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Spec;

@Command(name = "chipub", description = "A small cli for interacting with the Chicago Public Library database system.", mixinStandardHelpOptions = true)
public class Cli implements Runnable {
    @Spec CommandSpec spec;

    // first, the main function that defers all actions to subcommands
    @Override
    public void run() {
        throw new ParameterException(spec.commandLine(), "Specify a subcommand");
    };

    //
    // SUBCOMMANDS
    //
      
    // search for books
    @Command(name = "search", description = "Search for books by a simple string, or by providing specific values for various properties, such as author, genre, isbn, etc.")
    void search(
        @Parameters(index = "0", paramLabel = "<search terms>") String query
        // @Option(),
    ) {

    };

    // check out a book

    // return a book

    // request a hold

    // cancel a hold

    // view a collection of stuff about a cardholder, including:
    // - books checked out
    //   - current/past/all time
    //   - due soon
    //   - overdue
    // - holds requested
    //   - current/past/all time
    //   - position in hold queue (& expected wait time)

    // view a collection of stuff about a book, including:
    // - info about copies owned by library, such as
    //   - how many copies are available/checked out/pending holds
    //   - where copies are located
    // - genre
    // - subject
    // - language
    // - author (name, short about/bio)
    // - isbn
    // - summary/description?
    // - cover image?

    // view a collection of stuff about an author, including:
    // - bio/about the author
    // - genres/subjects the author has published in/on that the library has
    // - all books by the author the library has
    // - maybe other authors that people who've checked out books by this author also like?

}
