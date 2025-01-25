package com.levelrin;

import com.levelrin.antlr.generated.UmlScriptBaseVisitor;
import com.levelrin.antlr.generated.UmlScriptParser;
import java.util.List;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class FormatVisitor extends UmlScriptBaseVisitor<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FormatVisitor.class);

    private final String indentUnit = "  ";

    private int currentIndentLevel = 0;

    private final CommonTokenStream tokens;

    public FormatVisitor(final CommonTokenStream tokens) {
        this.tokens = tokens;
    }

    @Override
    public String visitRoot(final UmlScriptParser.RootContext context) {
        final List<UmlScriptParser.SectionContext> sectionContexts = context.section();
        final StringBuilder text = new StringBuilder();
        for (int index = 0; index < sectionContexts.size(); index++) {
            final UmlScriptParser.SectionContext sectionContext = sectionContexts.get(index);
            text.append(this.visit(sectionContext));
            if (index < sectionContexts.size() - 1) {
                this.appendNewLinesAndIndent(text, 2);
            } else {
                this.appendNewLinesAndIndent(text, 1);
            }
        }
        return text.toString();
    }

    @Override
    public String visitSection(final UmlScriptParser.SectionContext context) {
        final UmlScriptParser.MetadataContext metadataContext = context.metadata();
        final UmlScriptParser.ParticipantsContext participants = context.participants();
        final StringBuilder text = new StringBuilder();
        if (metadataContext != null) {
            text.append(this.visit(metadataContext));
        } else if (participants != null) {
            text.append(this.visit(participants));
        }
        return text.toString();
    }

    @Override
    public String visitMetadata(final UmlScriptParser.MetadataContext context) {
        final UmlScriptParser.MetadataHeaderContext metadataHeaderContext = context.metadataHeader();
        final UmlScriptParser.MetadataContentContext metadataContentContext = context.metadataContent();
        final UmlScriptParser.MetadataFooterContext metadataFooterContext = context.metadataFooter();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(metadataHeaderContext));
        this.appendNewLinesAndIndent(text, 2);
        text.append(this.visit(metadataContentContext));
        this.appendNewLinesAndIndent(text, 2);
        text.append(this.visit(metadataFooterContext));
        return text.toString();
    }

    @Override
    public String visitMetadataHeader(final UmlScriptParser.MetadataHeaderContext context) {
        final List<TerminalNode> doubleEqualsTerminals = context.DOUBLE_EQUALS();
        final TerminalNode metadataTerminal = context.METADATA();
        final StringBuilder text = new StringBuilder();
        final TerminalNode firstDoubleEqualsTerminal = doubleEqualsTerminals.get(0);
        text.append(this.visit(firstDoubleEqualsTerminal));
        text.append(" ");
        text.append(this.visit(metadataTerminal));
        text.append(" ");
        final TerminalNode secondDoubleEqualsTerminal = doubleEqualsTerminals.get(1);
        text.append(this.visit(secondDoubleEqualsTerminal));
        return text.toString();
    }

    @Override
    public String visitMetadataFooter(final UmlScriptParser.MetadataFooterContext context) {
        final List<TerminalNode> doubleDashesTerminals = context.DOUBLE_DASHES();
        final TerminalNode metadataTerminal = context.METADATA();
        final StringBuilder text = new StringBuilder();
        final TerminalNode firstDoubleDashesTerminal = doubleDashesTerminals.get(0);
        text.append(this.visit(firstDoubleDashesTerminal));
        text.append(" ");
        text.append(this.visit(metadataTerminal));
        text.append(" ");
        final TerminalNode secondDoubleDashesTerminal = doubleDashesTerminals.get(1);
        text.append(this.visit(secondDoubleDashesTerminal));
        return text.toString();
    }

    @Override
    public String visitMetadataContent(final UmlScriptParser.MetadataContentContext context) {
        final UmlScriptParser.TitleContext titleContext = context.title();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(titleContext));
        return text.toString();
    }

    @Override
    public String visitTitle(final UmlScriptParser.TitleContext context) {
        final TerminalNode titleTerminal = context.TITLE();
        final TerminalNode colonTerminal = context.COLON();
        final TerminalNode singleLineString = context.SINGLE_LINE_STRING();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(titleTerminal));
        text.append(this.visit(colonTerminal));
        text.append(" ");
        text.append(this.visit(singleLineString));
        return text.toString();
    }

    @Override
    public String visitParticipants(final UmlScriptParser.ParticipantsContext context) {
        final UmlScriptParser.ParticipantsHeaderContext participantsHeaderContext = context.participantsHeader();
        final UmlScriptParser.ParticipantsContentContext participantsContentContext = context.participantsContent();
        final UmlScriptParser.ParticipantsFooterContext participantsFooterContext = context.participantsFooter();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(participantsHeaderContext));
        this.appendNewLinesAndIndent(text, 2);
        text.append(this.visit(participantsContentContext));
        this.appendNewLinesAndIndent(text, 2);
        text.append(this.visit(participantsFooterContext));
        return text.toString();
    }

    @Override
    public String visitParticipantsHeader(final UmlScriptParser.ParticipantsHeaderContext context) {
        final List<TerminalNode> doubleEqualsTerminals = context.DOUBLE_EQUALS();
        final TerminalNode participantsTerminal = context.PARTICIPANTS();
        final StringBuilder text = new StringBuilder();
        final TerminalNode firstDoubleEqualsTerminal = doubleEqualsTerminals.get(0);
        text.append(this.visit(firstDoubleEqualsTerminal));
        text.append(" ");
        text.append(this.visit(participantsTerminal));
        text.append(" ");
        final TerminalNode secondDoubleEqualsTerminal = doubleEqualsTerminals.get(1);
        text.append(this.visit(secondDoubleEqualsTerminal));
        return text.toString();
    }

    @Override
    public String visitParticipantsFooter(final UmlScriptParser.ParticipantsFooterContext context) {
        final List<TerminalNode> doubleDashesTerminals = context.DOUBLE_DASHES();
        final TerminalNode participantsTerminal = context.PARTICIPANTS();
        final StringBuilder text = new StringBuilder();
        final TerminalNode firstDoubleDashesTerminal = doubleDashesTerminals.get(0);
        text.append(this.visit(firstDoubleDashesTerminal));
        text.append(" ");
        text.append(this.visit(participantsTerminal));
        text.append(" ");
        final TerminalNode secondDoubleDashesTerminal = doubleDashesTerminals.get(1);
        text.append(this.visit(secondDoubleDashesTerminal));
        return text.toString();
    }

    @Override
    public String visitParticipantsContent(final UmlScriptParser.ParticipantsContentContext context) {
        final List<UmlScriptParser.ParticipantContext> participantContexts = context.participant();
        final StringBuilder text = new StringBuilder();
        for (int index = 0; index < participantContexts.size(); index++) {
            final UmlScriptParser.ParticipantContext participantContext = participantContexts.get(index);
            text.append(this.visit(participantContext));
            if (index < participantContexts.size() - 1) {
                this.appendNewLinesAndIndent(text, 1);
            }
        }
        return text.toString();
    }

    @Override
    public String visitParticipant(final UmlScriptParser.ParticipantContext context) {
        final TerminalNode referenceNameTerminal = context.REFERENCE_NAME();
        final TerminalNode colonTerminal = context.COLON();
        final TerminalNode singleLineString = context.SINGLE_LINE_STRING();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(referenceNameTerminal));
        text.append(this.visit(colonTerminal));
        text.append(" ");
        text.append(this.visit(singleLineString));
        return text.toString();
    }

    @Override
    public String visit(final ParseTree tree) {
        final String ruleName = tree.getClass().getSimpleName();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `{}` text: {}", ruleName, tree.getText());
        }
        return tree.accept(this);
    }

    @Override
    public String visitTerminal(final TerminalNode node) {
        final int tokenIndex = node.getSymbol().getTokenIndex();
        final int commentChannel = 1;
        final List<Token> comments = this.tokens.getHiddenTokensToLeft(tokenIndex, commentChannel);
        final StringBuilder text = new StringBuilder();
        if (comments != null) {
            for (final Token comment : comments) {
                String commentText = comment.getText();
                if (commentText.length() > 1 && commentText.charAt(1) != ' ') {
                    commentText = String.format("# %s", commentText.substring(1));
                }
                text.append(commentText);
                this.appendNewLinesAndIndent(text, 1);
            }
        }
        text.append(node.getText());
        return text.toString();
    }

    /**
     * We use this to add new lines with appropriate indentations.
     * @param text We will append the new lines and indentations into this.
     * @param newLines Number of new lines before appending indentations.
     */
    private void appendNewLinesAndIndent(final StringBuilder text, final int newLines) {
        text.append("\n".repeat(newLines));
        text.append(this.indentUnit.repeat(this.currentIndentLevel));
    }

}
