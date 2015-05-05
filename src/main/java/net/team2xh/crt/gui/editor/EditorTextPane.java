/*
 * Copyright (C) 2015 Hamza Haiken <tenchi@team2xh.net>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.team2xh.crt.gui.editor;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.DocumentFilter;
import javax.swing.text.Element;
import javax.swing.text.LayeredHighlighter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import net.team2xh.crt.gui.themes.Theme;
import net.team2xh.crt.language.parser.CRTBaseListener;
import net.team2xh.crt.language.parser.CRTLexer;
import net.team2xh.crt.language.parser.CRTParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * The actual editor.
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class EditorTextPane extends JTextPane {

    private DefaultStyledDocument doc;

//    private int length = 0;
//    private int cursor = 0;
//    private int line = 0;
    final private LineHighlighter lh;
    final private LineHighlighter ll;
    final private WordHighlighter wh;
    final private ErrorHighlighter eh;

    final private Font font = new Font("Envy Code R", Font.PLAIN, 16);
    final private FontMetrics fontMetrics = getFontMetrics(font);
    final private int charWidth = fontMetrics.charWidth('0');

    private int margin = 60;
    private int marginSize = margin * charWidth + 3;

    private LinkedList<Object> occurrences = new LinkedList<>();

    public EditorTextPane() {

        // Initialize highlighters
        lh = new LineHighlighter(Theme.getTheme().COLOR_13);
        ll = new LineHighlighter(Theme.getTheme().COLOR_14);
        wh = new WordHighlighter(Theme.getTheme().COLOR_11);
        eh = new ErrorHighlighter(Color.RED);

        // Initialise colors
        initAttributeSets();

        setOpaque(false); // Background will be drawn later on
        setFont(font);

        doc = (DefaultStyledDocument) getDocument();

        // Replace all tabs with four spaces
        // TODO: tab to next multiple of 4 column
        // TODO: tab whole selection
        // TODO: insert matching brace
        doc.setDocumentFilter(new DocumentFilter() {
            private String process(int offset, String text) {
                return text.replaceAll("\t", "    ").replaceAll("\r", "");
            }

            @Override
            public void insertString(FilterBypass fb, int offset, String text,
                    AttributeSet attr) throws BadLocationException {
                super.insertString(fb, offset, process(offset, text), attr);
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text,
                    AttributeSet attr) throws BadLocationException {
                super.replace(fb, offset, length, process(offset, text), attr);
            }
        });

        // Highlight text when text changes
        doc.addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                SwingUtilities.invokeLater(() -> highlightText());
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                SwingUtilities.invokeLater(() -> highlightText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        addCaretListener(new CaretListener() {
            private boolean isAlphanum(char c) {
                return Character.isDigit(c) || Character.isLetter(c);
            }

            private boolean isAlphanum(String s) {
                for (char c : s.toCharArray()) // Allow dots in middle of words for floats
                {
                    if (c != '.' && !isAlphanum(c)) {
                        return false;
                    }
                }

                return true;
            }

            @Override
            public void caretUpdate(CaretEvent ce) {
                try {

                    // Highlight current line
                    highlightCurrentLine();

                    // Clear previously highlighted occurrences
                    for (Object o : occurrences) {
                        getHighlighter().removeHighlight(o);
                    }
                    repaint();
                    occurrences.clear();

                    // Get start and end offsets, swap them if necessary
                    int s = ce.getDot();
                    int e = ce.getMark();

                    if (s > e) {
                        s = s + e;
                        e = s - e;
                        s = s - e;
                    }

                    // If there is a selection,
                    if (s != e) {
                        // Check if the char on the left and on the right are not alphanums
                        char f = s == 0 ? ' ' : doc.getText(s - 1, 1).charAt(0);
                        char l = s == doc.getLength() - 1 ? ' ' : doc.getText(e, 1).charAt(0);
                        if (!isAlphanum(f) && !isAlphanum(l)) {
                            String word = doc.getText(s, e - s);
                            if (isAlphanum(word)) {
                                highlightOccurrences(word, s, e);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        setCaretColor(Theme.getTheme().COLOR_11);

        highlightText();
    }

    private void highlightCurrentLine() {
        if (lastLine != null) {
            getHighlighter().removeHighlight(lastLine);
        }
        try {
            int line = getLineOfOffset(getCaretPosition());
            int ss = getLineStartOffset(line);
            int ee = getLineEndOffset(line);
            lastLine = highlight(ss, ee, ll);
        } catch (BadLocationException ex) {
            Logger.getLogger(EditorTextPane.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    class SyntaxHighlightingListener extends CRTBaseListener {

        @Override
        public void visitErrorNode(@NotNull ErrorNode node) {

            Token t = node.getSymbol();
            String m = node.getText();
            System.out.println("Parse error: " + m);
            try {
                int line = t.getLine() - 1;
                int s = getLineStartOffset(line);
                int e = getLineEndOffset(line);
                int o = t.getCharPositionInLine();
                int l = m.length();
                // TODO: Don't hide under current line highlight
                highlight(s + o, s + o + l, eh);
                highlight(s, e, lh);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    private void colorize(SimpleAttributeSet style, int line, int cursor, int length) {
        try {
            int index = getLineStartOffset(line) + cursor;
            doc.setCharacterAttributes(index, length, style, true);
        } catch (BadLocationException ex) {
            Logger.getLogger(EditorTextPane.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Object highlight(int start, int end, LayeredHighlighter.LayerPainter hl)
            throws BadLocationException {
        return getHighlighter().addHighlight(start, end, hl);
    }

    private SimpleAttributeSet OPERATORS = new SimpleAttributeSet();
    private SimpleAttributeSet NAME = new SimpleAttributeSet();
    private SimpleAttributeSet IDENTIFIER = new SimpleAttributeSet();
    private SimpleAttributeSet NUMBER = new SimpleAttributeSet();
    private SimpleAttributeSet COMMENT = new SimpleAttributeSet();
    private SimpleAttributeSet BLOCK = new SimpleAttributeSet();
    private SimpleAttributeSet NORMAL = new SimpleAttributeSet();
    private SimpleAttributeSet TRANSFORM = new SimpleAttributeSet();
    private SimpleAttributeSet STRING = new SimpleAttributeSet();

    private void initAttributeSets() {
        Theme theme = Theme.getTheme();
        StyleConstants.setForeground(OPERATORS, theme.COLOR_10);
        StyleConstants.setForeground(NAME, theme.COLOR_11);
        StyleConstants.setItalic(NAME, true);
        StyleConstants.setForeground(NUMBER, theme.COLOR_09);
        StyleConstants.setForeground(COMMENT, theme.COLOR_16);
        StyleConstants.setForeground(BLOCK, theme.COLOR_06);
        StyleConstants.setItalic(BLOCK, true);
        StyleConstants.setForeground(NORMAL, theme.COLOR_11);
        StyleConstants.setForeground(IDENTIFIER, theme.COLOR_11);
        StyleConstants.setForeground(TRANSFORM, theme.COLOR_08);
        StyleConstants.setForeground(STRING, theme.COLOR_12);
    }

    private Object lastLine = null;

    private void highlightText() {
        getHighlighter().removeAllHighlights();
        colorize(COMMENT, 0, 0, getText().length());

        highlightCurrentLine();

        CRTLexer lexer = new CRTLexer(new ANTLRInputStream(getText()));
        CRTParser parser = new CRTParser(new CommonTokenStream(lexer));
        ParserRuleContext tree = parser.script();
        ParseTreeWalker walker = new ParseTreeWalker();

        lexer = new CRTLexer(new ANTLRInputStream(getText()));

        for (Token token = lexer.nextToken(); token.getType() != Token.EOF; token = lexer.nextToken()) {
            int cursor = token.getCharPositionInLine();
            int line = token.getLine() - 1;
            int length = token.getText().length();

            switch (token.getType()) {
                case CRTLexer.ASSIGN:
                case CRTLexer.ATTRIBUTE:
                case CRTLexer.ADD:
                case CRTLexer.SUBTRACT:
                case CRTLexer.INTERSECTION:
                case CRTLexer.MULTIPLY:
                case CRTLexer.DIVIDE:
                case CRTLexer.MODULO:
                case CRTLexer.NOT:
                case CRTLexer.LESS:
                case CRTLexer.GREATER:
                case CRTLexer.LESS_EQUAL:
                case CRTLexer.GREATER_EQUAL:
                case CRTLexer.EQUAL:
                case CRTLexer.NOT_EQUAL:
                case CRTLexer.AND:
                case CRTLexer.OR:
                case CRTLexer.QUESTION:
                case CRTLexer.COLON:
                    colorize(OPERATORS, line, cursor, length);
                    break;

                case CRTLexer.TRANSLATE:
                case CRTLexer.SCALE:
                case CRTLexer.ROTATE:
                    colorize(TRANSFORM, line, cursor, length);
                    break;

                case CRTLexer.NAME:
                    colorize(NAME, line, cursor, length);
                    break;

                case CRTLexer.IDENTIFIER:
                    colorize(IDENTIFIER, line, cursor, length);
                    break;

                case CRTLexer.FLOAT:
                case CRTLexer.INTEGER:
                case CRTLexer.TRUE:
                case CRTLexer.FALSE:
                    colorize(NUMBER, line, cursor, length);
                    break;

                case CRTLexer.SCENE:
                case CRTLexer.SETTINGS:
                case CRTLexer.MACRO:
                    colorize(BLOCK, line, cursor, length);
                    break;

                case CRTLexer.STRING:
                    colorize(STRING, line, cursor, length);
                    break;

                // TODO: don't hide under line highlight
                case CRTLexer.INVALID:
                    try {
                        int index = getLineStartOffset(line) + cursor;
                        int s = getLineStartOffset(index);
                        int e = getLineEndOffset(index);
                        highlight(cursor, cursor + length, eh);
                        highlight(s, e, lh);
                    } catch (BadLocationException e) {
                    }
                    break;

                default:
                    colorize(NORMAL, line, cursor, length);
                    break;
            }
        }
        
        // Reset lexer for errors because parser consumed it
        walker.walk(new SyntaxHighlightingListener(), tree);

    }

    private void highlightOccurrences(String word, int s0, int e0) {
        try {
            Pattern p = Pattern.compile("\\b" + word + "\\b"); // word surrounded by boundaries
            Matcher m = p.matcher(doc.getText(0, doc.getLength()));
            while (m.find()) {
                int s = m.start();
                int e = m.end();
                if (s != s0 && e != e0) {
                    occurrences.add(highlight(s, e, wh));
                }
            }
        } catch (Exception e) {

        }
    }

    /**
     * Paints the background, the margin background and the margin line.
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int m = marginSize;
        int w = getWidth(), h = getHeight();
        // Background
        g2d.setPaint(Theme.getTheme().COLOR_02);
        g2d.fillRect(0, 0, m, h);
        // Margin background
        if (m < w) {
            g2d.setColor(Theme.getTheme().COLOR_01);
            g2d.fillRect(m, 0, w - m, h);
        }
        // Margin line
        g2d.setColor(Theme.getTheme().COLOR_04);
        g2d.drawLine(m, 0, m, h);
        // Draw the rest
        super.paintComponent(g);
    }

    /**
     * Returns the first character offset of the given line number.
     *
     * @param line Line number.
     * @return First character offset of that line.
     * @throws BadLocationException
     */
    public int getLineStartOffset(int line) throws BadLocationException {
        Element map = doc.getDefaultRootElement();
        if (line < 0) {
            throw new BadLocationException("Negative line", -1);
        } else if (line >= map.getElementCount()) {
            throw new BadLocationException("No such line", doc.getLength() + 1);
        } else {
            Element lineElem = map.getElement(line);
            return lineElem.getStartOffset();
        }
    }

    /**
     * Returns the last character offset of the given line number.
     *
     * @param line Line number.
     * @return Last character offset of that line.
     * @throws BadLocationException
     */
    public int getLineEndOffset(int line) throws BadLocationException {
        Element map = doc.getDefaultRootElement();
        if (line < 0) {
            throw new BadLocationException("Negative line", -1);
        } else if (line >= map.getElementCount()) {
            throw new BadLocationException("No such line", doc.getLength() + 1);
        } else {
            Element lineElem = map.getElement(line);
            return lineElem.getEndOffset();
        }
    }

    /**
     * Returns the line number of the given character offset, for the current document.
     *
     * @param offset Character offset.
     * @return Line number.
     * @throws BadLocationException
     */
    public int getLineOfOffset(int offset) throws BadLocationException {
        if (offset < 0) {
            throw new BadLocationException("Can't translate offset to line", -1);
        } else if (offset > doc.getLength()) {
            throw new BadLocationException("Can't translate offset to line", doc.getLength() + 1);
        } else {
            Element map = doc.getDefaultRootElement();
            return map.getElementIndex(offset);
        }
    }
}
