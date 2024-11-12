package org.iitcs.depreciated.cli;

import org.iitcs.util.Constants;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Spec;

@Command(
    resourceBundle = "default_properties",
    name = "${bundle:name}",
    description = "${bundle:description}",
    version = "${bundle:version}",
    mixinStandardHelpOptions = true,
    subcommands = {Search.class/*, View.class*/}
)
public class Cli implements Runnable {
    @Spec CommandSpec spec;
    QueryExecutor qexec = new QueryExecutor();
    // first, the main function that defers all actions to subcommands
    @Override
    public void run() {
        throw new ParameterException(spec.commandLine(), "Specify a subcommand");
    };

    //
    // SUBCOMMANDS
    //

    // check out a book
    @Command(name = "check-out", description = "Check out a book to a cardholder")
    void checkOut(
        @Parameters(index = "0", paramLabel = "<copy id number>") int copyId,
        @Parameters(index = "1", paramLabel = "<member's id number>") int cardholderId
    ) {
        switch(qexec.executeCheckOut(copyId, cardholderId)){
            case(0):
                System.out.println("Attempted to check-out, but no check-out was found with these parameters.");
                break;
            case(1):
                System.out.println("The copy was successfully checked out!");
                break;
            case(-1):
                System.out.println("Check-out attempt failed.");
                break;
        }
    }

    // return a book
    @Command(name = "check-in", description = "Return a book from a cardholder")
    void checkIn(
        @Parameters(index = "0", paramLabel = "<copy id number>") int copyId,
        @Parameters(index = "1", paramLabel = "<member's id number>") int cardholderId
    ) {
        switch(qexec.executeCheckIn(copyId, cardholderId)){
            case(0):
                System.out.println("Attempted to check-in, but no check-out was found with these parameters.");
                break;
            case(1):
                System.out.println("The copy was successfully checked in!");
                break;
            case(-1):
                System.out.println("Check-in attempt failed.");
                break;
        }
    }

    // request a hold
    @Command(name = "hold-request", description = "Request a hold be placed on a book for a cardholder")
    void holdRequest(
        @Parameters(index = "0", paramLabel = "<book id number>") int copyId,
        @Parameters(index = "1", paramLabel = "<member's id number>") int cardholderId
    ) {
        switch(qexec.executePreparedStatement(Constants.CRUD.UPDATE, "placehold", copyId, cardholderId, "Hold placed.")){
            case(0):
                System.out.println("Attempted to place hold, but nothing was updated.");
                break;
            case(1):
                System.out.println("The hold was successfully placed!");
                break;
            case(-1):
                System.out.println("Hold attempt failed.");
                break;
        }
    }

    // cancel a hold
    @Command(name = "hold-cancel", description = "Cancel a previously requested hold for a cardholder")
    void holdCancel(
        @Parameters(index = "0", paramLabel = "<book id number>") int copyId,
        @Parameters(index = "1", paramLabel = "<member's id number>") int cardholderId
    ) {
        switch(qexec.executePreparedStatement(Constants.CRUD.UPDATE, "cancelhold", copyId, cardholderId, "Hold cancelled.")){
            case(0):
                System.out.println("Attempted to cancel hold, but no hold was found.");
                break;
            case(1):
                System.out.println("The hold was successfully cancelled!");
                break;
            case(-1):
                System.out.println("Hold cancel failed.");
                break;
        }
    }
}

@Command(name = "search", description = "Search for...", mixinStandardHelpOptions = true)
class Search implements Runnable {
    QueryExecutor qexec = new QueryExecutor();
    @Spec CommandSpec spec;

    // first, the main function that defers all actions to subcommands
    @Override
    public void run() {
        // TODO: maybe this actually can be run w/out subcommands, instead searching across all 3
        // types, but without any flags for narrowing...
        throw new ParameterException(spec.commandLine(), "Specify a subcommand");
    };

    //
    // SUBCOMMANDS
    //

    // search for books
    @Command(name = "book", description = "books by a simple string, or by providing specific values for various properties, such as author, genre, isbn, etc")
    void book(
        //Removing the generic query for now, save time on dev work
        //@Parameters(index = "0", paramLabel = "<search terms>") String query,
        @Option(names = { "-a", "--author" }, description = "narrow search by author") String author,
        @Option(names = { "-g", "--genre" }, description = "narrow search by genre") String genre,
        @Option(names = { "-i", "--isbn" }, description = "narrow search by isbn") String isbn,
        @Option(names = { "-l", "--language" }, description = "narrow search by language") String language,
        @Option(names = { "-s", "--subject" }, description = "narrow search by subject") String subject
    ) {
        qexec.executeBookSearch(author, genre, isbn, language, subject);
    };

    // search for cardholders
    @Command(name = "cardholder", description = "cardholders by a simple string, or by providing specific values for various properties, such as name, address, phone number, etc.")
    void cardholder(
        //Removing the generic query for now, save time on dev work
        //@Parameters(index = "0", paramLabel = "<search terms>") String query,
        @Option(names = { "-a", "--address" }, description = "narrow search by address") String address,
        @Option(names = { "-n", "--name" }, description = "narrow search by name") String name,
        @Option(names = { "-p", "--phone-number" }, description = "narrow search by phone number") String phoneNumber
    ) {
       qexec.executeCardholderSearch(name, address, phoneNumber);
    };
}

//TODO Will come back to these later!
/*
@Command(name = "view", description = "View information about...", mixinStandardHelpOptions = true)
class View implements Runnable {
    @Spec CommandSpec spec;

    // first, the main function that defers all actions to subcommands
    @Override
    public void run() {
        throw new ParameterException(spec.commandLine(), "Specify a subcommand");
    };

    //
    // SUBCOMMANDS
    //

    // view a collection of stuff about a cardholder, including:
    // - books checked out
    //   - current/past/all time
    //   - due soon
    //   - overdue
    // - holds requested
    //   - current/past/all time
    //   - position in hold queue (& expected wait time)
    @Command(name = "cardholder", description = "a cardholder")
    void cardholder(
        @Parameters(index = "0", paramLabel = "<cardholder id number>") int cardholderId
    ) {
        // TODO...
    }

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
    @Command(name = "book", description = "a book")
    void book(
        @Parameters(index = "0", paramLabel = "<book id number>") int bookId
    ) {
        // TODO...
    }

    // view a collection of stuff about an author, including:
    // - bio/about the author
    // - genres/subjects the author has published in/on that the library has
    // - all books by the author the library has
    // - maybe other authors that people who've checked out books by this author also like?
    @Command(name = "author", description = "an author")
    void author(
        @Parameters(index = "0", paramLabel = "<author id number>") int authorId
    ) {
        // TODO...
    }
}*/
