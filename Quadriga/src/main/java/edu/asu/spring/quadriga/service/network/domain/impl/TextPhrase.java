package edu.asu.spring.quadriga.service.network.domain.impl;

public class TextPhrase {

    private int position;
    private String formattedPointer;
    private String format;
    private String expression;
    
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    public String getFormattedPointer() {
        return formattedPointer;
    }
    public void setFormattedPointer(String formattedPointer) {
        this.formattedPointer = formattedPointer;
    }
    public String getFormat() {
        return format;
    }
    public void setFormat(String format) {
        this.format = format;
    }
    public String getExpression() {
        return expression;
    }
    public void setExpression(String expression) {
        this.expression = expression;
    }
}
