package org.iitcs.database.dao.models;

public abstract class CplEntity {
    public static final String BR_TAG = "<br/>";
    public static final String NO_ATTRIBUTE = "N/A";
    public static final String COLON_SPACE = ": ";
    public static final String HTML_OPEN_TAG = "<html>";
    public static final String HTML_CLOSE_TAG = "</html>";
    public abstract String toStringJLabelDetail();
    public void appendLineBreakWithLabel(StringBuilder sb, String tag, String s){
        if(s!=null){
            sb.append(tag.concat(COLON_SPACE).concat(s).concat(BR_TAG));
        }else{
            sb.append(tag.concat(COLON_SPACE).concat( NO_ATTRIBUTE).concat(BR_TAG));
        }
    }

    public void appendHtmlOpenTag(StringBuilder sb){
        sb.append(HTML_OPEN_TAG);
    }

    public void appendHtmlClosingTag(StringBuilder sb){
        sb.append(HTML_CLOSE_TAG);
    }
}
