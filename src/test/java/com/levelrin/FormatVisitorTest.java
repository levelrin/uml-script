package com.levelrin;

import com.levelrin.antlr.generated.UmlScriptLexer;
import com.levelrin.antlr.generated.UmlScriptParser;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

final class FormatVisitorTest {

    /**
     * Assert that the formatter formats the code written in the `before` file that matches with the code written in the `after` file.
     * @param before The file name that has code before formatting.
     * @param after The file name that has code after formatting.
     */
    void compare(final String before, final String after) {
        try {
            final Path beforePath = Paths.get(ClassLoader.getSystemResource(before).toURI());
            final String originalText = Files.readString(beforePath, StandardCharsets.UTF_8);
            final CharStream charStream = CharStreams.fromString(originalText);
            final UmlScriptLexer lexer = new UmlScriptLexer(charStream);
            final CommonTokenStream tokens = new CommonTokenStream(lexer);
            final UmlScriptParser parser = new UmlScriptParser(tokens);
            final ParseTree tree = parser.root();
            final FormatVisitor visitor = new FormatVisitor(tokens);
            final String result = visitor.visit(tree);
            final Path afterPath = Paths.get(ClassLoader.getSystemResource(after).toURI());
            final String expectedText = Files.readString(afterPath, StandardCharsets.UTF_8);
            MatcherAssert.assertThat(result, Matchers.equalTo(expectedText));
        } catch (final URISyntaxException | IOException ex) {
            throw new IllegalStateException(
                String.format(
                    "Failed to read files. before: %s, after: %s",
                    before,
                    after
                ),
                ex
            );
        }
    }

    @Test
    void shouldFormatParticipants() {
        this.compare("format/participants-before.us", "format/participants-after.us");
    }

    @Test
    void shouldFormatMetadata() {
        this.compare("format/metadata-before.us", "format/metadata-after.us");
    }

}
