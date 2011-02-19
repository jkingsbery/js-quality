package net.kingsbery.js.lint;


import org.mozilla.javascript.Scriptable;

/**
 * A single issue with the code that is being checked for problems.
 *
 * @author dom
 */
public class Issue {

    /**
     * Allow creating an issue in a couple of different ways.
     */
    public static class IssueBuilder {

        /** Build from a JavaScript context 
         * @param fileName */
        public static Issue fromJavaScript( String fileName, Scriptable err) {
            // Correct the line / column numbers so that they're never zero.
            int line = Util.intValue("line", err);
            if (line == 0) {
                line = 1;
            }
            int col = Util.intValue("character", err);
            if (col == 0) {
                col = 1;
            }
            return new IssueBuilder(fileName,line, col, Util.stringValue("reason", err))
                    .evidence(Util.stringValue("evidence", err)).raw(Util.stringValue("raw", err))
                    .a(Util.stringValue("a", err)).b(Util.stringValue("b", err))
                    .c(Util.stringValue("c", err)).d(Util.stringValue("d", err)).build();
        }

        private String fileName;
        private String a;
        private String b;
        private String c;
        private final int character;
        private String d;
        private String evidence;
        private final int line;
        private String raw;
        private final String reason;

        public IssueBuilder(String fileName, int line, int character, String reason) {
        	this.fileName=fileName;
            this.character = character;
            this.line = line;
            this.reason = reason;
        }

        public Issue build() {
            return new Issue(this);
        }

        public IssueBuilder a(String a) {
            this.a = a;
            return this;
        }

        public IssueBuilder b(String b) {
            this.b = b;
            return this;
        }

        public IssueBuilder c(String c) {
            this.c = c;
            return this;
        }

        public IssueBuilder d(String d) {
            this.d = d;
            return this;
        }

        public IssueBuilder evidence(String evidence) {
            this.evidence = evidence;
            return this;
        }

        public IssueBuilder raw(String raw) {
            this.raw = raw;
            return this;
        }
    }

    private String fileName;
    private final String a;
    private final String b;
    private final String c;
    private final int character;
    private final String d;
    private final String evidence;
    private final int line;
    private final String raw;
    private final String reason;

    private Issue(IssueBuilder ib) {
    	fileName=ib.fileName;
        reason = ib.reason;
        line = ib.line;
        character = ib.character;
        evidence = ib.evidence;
        raw = ib.raw;
        a = ib.a;
        b = ib.b;
        c = ib.c;
        d = ib.d;
    }

    /**
     * @return A string of auxiliary information.
     */
    public String getA() {
        return a;
    }

    /**
     * @return A string of auxiliary information.
     */
    public String getB() {
        return b;
    }

    /**
     * @return A string of auxiliary information.
     */
    public String getC() {
        return c;
    }

    /**
     * @return the position of the issue within the line. Starts at 0.
     */
    public int getCharacter() {
        return character;
    }

    /**
     * @return a string of auxiliary information.
     */
    public String getD() {
        return d;
    }

    public String getFileName(){
    	return fileName;
    }
    
    /**
     * @return the contents of the line in which this issue occurs.
     */
    public String getEvidence() {
        return evidence;
    }

    /**
     * @return the number of the line on which this issue occurs.
     */
    public int getLine() {
        return line;
    }

    /**
     * @return a copy of the evidence, but before substitution has occurred.
     */
    public String getRaw() {
        return raw;
    }

    /**
     * @return a textual description of this issue.
     */
    public String getReason() {
        return reason;
    }

    
    /**
     * Provide four fields from this issue, separated by colons: systemId, line,
     * character, reason.
     */
    @Override
    public String toString() {
        return getFileName()+":"+ getLine() + ":" + getCharacter() + ":" + getRaw();
    }

    public String getRuleKey() {
        return raw;
    }

}
