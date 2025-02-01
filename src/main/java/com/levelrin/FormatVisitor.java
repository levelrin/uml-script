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
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(metadataHeaderContext));
        this.appendNewLinesAndIndent(text, 2);
        text.append(this.visit(metadataContentContext));
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
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(participantsHeaderContext));
        this.appendNewLinesAndIndent(text, 2);
        text.append(this.visit(participantsContentContext));
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
    public String visitParticipantsContent(final UmlScriptParser.ParticipantsContentContext context) {
        final List<UmlScriptParser.ParticipantsElementContext> participantsElementContexts = context.participantsElement();
        final StringBuilder text = new StringBuilder();
        for (int index = 0; index < participantsElementContexts.size(); index++) {
            final UmlScriptParser.ParticipantsElementContext participantsElementContext = participantsElementContexts.get(index);
            text.append(this.visit(participantsElementContext));
            if (index < participantsElementContexts.size() - 1) {
                this.appendNewLinesAndIndent(text, 2);
            }
        }
        return text.toString();
    }

    @Override
    public String visitParticipantsElement(final UmlScriptParser.ParticipantsElementContext context) {
        final UmlScriptParser.ParticipantContext participantContext = context.participant();
        final UmlScriptParser.BoxContext boxContext = context.box();
        final StringBuilder text = new StringBuilder();
        if (participantContext != null) {
            text.append(this.visit(participantContext));
        } else if (boxContext != null) {
            text.append(this.visit(boxContext));
        }
        return text.toString();
    }

    @Override
    public String visitParticipant(final UmlScriptParser.ParticipantContext context) {
        final TerminalNode atTerminal = context.AT();
        final TerminalNode referenceNameTerminal = context.REFERENCE_NAME();
        final UmlScriptParser.ParticipantPartAfterTypeContext participantPartAfterTypeContext = context.participantPartAfterType();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(atTerminal));
        text.append(this.visit(referenceNameTerminal));
        if (participantPartAfterTypeContext != null) {
            text.append(" ");
            text.append(this.visit(participantPartAfterTypeContext));
        }
        return text.toString();
    }

    @Override
    public String visitParticipantPartAfterType(final UmlScriptParser.ParticipantPartAfterTypeContext context) {
        final TerminalNode referenceNameTerminal = context.REFERENCE_NAME();
        final UmlScriptParser.ParticipantPartAfterReferenceNameContext participantPartAfterReferenceNameContext = context.participantPartAfterReferenceName();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(referenceNameTerminal));
        if (participantPartAfterReferenceNameContext != null) {
            text.append(" ");
            text.append(this.visit(participantPartAfterReferenceNameContext));
        }
        return text.toString();
    }

    @Override
    public String visitParticipantPartAfterReferenceName(final UmlScriptParser.ParticipantPartAfterReferenceNameContext context) {
        final TerminalNode singleLineStringTerminal = context.SINGLE_LINE_STRING();
        final UmlScriptParser.MapContext mapContext = context.map();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(singleLineStringTerminal));
        if (mapContext != null) {
            text.append(" ");
            text.append(this.visit(mapContext));
        }
        return text.toString();
    }

    @Override
    public String visitBox(final UmlScriptParser.BoxContext context) {
        final TerminalNode singleLineStringTerminal = context.SINGLE_LINE_STRING();
        final TerminalNode openingBracketTerminal = context.OPENING_BRACKET();
        final UmlScriptParser.ParticipantsContentContext participantsContentContext = context.participantsContent();
        final TerminalNode closingBracketTerminal = context.CLOSING_BRACKET();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(singleLineStringTerminal));
        text.append(" ");
        text.append(this.visit(openingBracketTerminal));
        this.currentIndentLevel++;
        this.appendNewLinesAndIndent(text, 1);
        text.append(this.visit(participantsContentContext));
        this.currentIndentLevel--;
        this.appendNewLinesAndIndent(text, 1);
        text.append(this.visit(closingBracketTerminal));
        return text.toString();
    }

    @Override
    public String visitList(final UmlScriptParser.ListContext context) {
        final TerminalNode openingBracketTerminal = context.OPENING_BRACKET();
        final UmlScriptParser.ElementsContext elementsContext = context.elements();
        final TerminalNode closingBracketTerminal = context.CLOSING_BRACKET();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(openingBracketTerminal));
        this.currentIndentLevel++;
        final String elementsText = this.visit(elementsContext);
        if (elementsText.isEmpty()) {
            this.currentIndentLevel--;
            text.append(this.visit(closingBracketTerminal));
        } else {
            this.appendNewLinesAndIndent(text, 1);
            text.append(elementsText);
            this.currentIndentLevel--;
            this.appendNewLinesAndIndent(text, 1);
            text.append(this.visit(closingBracketTerminal));
        }
        return text.toString();
    }

    @Override
    public String visitElements(final UmlScriptParser.ElementsContext context) {
        final List<UmlScriptParser.ElementContext> elementContexts = context.element();
        final List<TerminalNode> commaTerminals = context.COMMA();
        final StringBuilder text = new StringBuilder();
        for (int index = 0; index < elementContexts.size(); index++) {
            final UmlScriptParser.ElementContext elementContext = elementContexts.get(index);
            final TerminalNode commaTerminal = commaTerminals.get(index);
            text.append(this.visit(elementContext));
            text.append(this.visit(commaTerminal));
            if (index < elementContexts.size() - 1) {
                this.appendNewLinesAndIndent(text, 1);
            }
        }
        return text.toString();
    }

    @Override
    public String visitElement(final UmlScriptParser.ElementContext context) {
        final TerminalNode singleLineStringTerminal = context.SINGLE_LINE_STRING();
        final TerminalNode numberTerminal = context.NUMBER();
        final TerminalNode booleanTerminal = context.BOOLEAN();
        final UmlScriptParser.ListContext listContext = context.list();
        final UmlScriptParser.MapContext mapContext = context.map();
        final StringBuilder text = new StringBuilder();
        if (singleLineStringTerminal != null) {
            text.append(this.visit(singleLineStringTerminal));
        } else if (numberTerminal != null) {
            text.append(this.visit(numberTerminal));
        } else if (booleanTerminal != null) {
            text.append(this.visit(booleanTerminal));
        } else if (listContext != null) {
            text.append(this.visit(listContext));
        } else if (mapContext != null) {
            text.append(this.visit(mapContext));
        }
        return text.toString();
    }

    @Override
    public String visitMap(final UmlScriptParser.MapContext context) {
        final TerminalNode openingBraceTerminal = context.OPENING_BRACE();
        final UmlScriptParser.PairsContext pairsContext = context.pairs();
        final TerminalNode closingBraceTerminal = context.CLOSING_BRACE();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(openingBraceTerminal));
        this.currentIndentLevel++;
        final String pairsText = this.visit(pairsContext);
        if (pairsText.isEmpty()) {
            this.currentIndentLevel--;
            text.append(this.visit(closingBraceTerminal));
        } else {
            this.appendNewLinesAndIndent(text, 1);
            text.append(pairsText);
            this.currentIndentLevel--;
            this.appendNewLinesAndIndent(text, 1);
            text.append(this.visit(closingBraceTerminal));
        }
        return text.toString();
    }

    @Override
    public String visitPairs(final UmlScriptParser.PairsContext context) {
        final List<UmlScriptParser.PairContext> pairContexts = context.pair();
        final List<TerminalNode> commaTerminals = context.COMMA();
        final StringBuilder text = new StringBuilder();
        for (int index = 0; index < pairContexts.size(); index++) {
            final UmlScriptParser.PairContext pairContext = pairContexts.get(index);
            text.append(this.visit(pairContext));
            text.append(this.visit(commaTerminals.get(index)));
            if (index < pairContexts.size() - 1) {
                this.appendNewLinesAndIndent(text, 1);
            }
        }
        return text.toString();
    }

    @Override
    public String visitPair(final UmlScriptParser.PairContext context) {
        final TerminalNode referenceNameTerminal = context.REFERENCE_NAME();
        final TerminalNode colonTerminal = context.COLON();
        final UmlScriptParser.ElementContext elementContext = context.element();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(referenceNameTerminal));
        text.append(this.visit(colonTerminal));
        text.append(" ");
        text.append(this.visit(elementContext));
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
