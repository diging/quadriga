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
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((expression == null) ? 0 : expression.hashCode());
        result = prime * result + ((format == null) ? 0 : format.hashCode());
        result = prime * result + ((formattedPointer == null) ? 0 : formattedPointer.hashCode());
        result = prime * result + position;
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TextPhrase other = (TextPhrase) obj;
        if (expression == null) {
            if (other.expression != null)
                return false;
        } else if (!expression.equals(other.expression))
            return false;
        if (format == null) {
            if (other.format != null)
                return false;
        } else if (!format.equals(other.format))
            return false;
        if (formattedPointer == null) {
            if (other.formattedPointer != null)
                return false;
        } else if (!formattedPointer.equals(other.formattedPointer))
            return false;
        if (position != other.position)
            return false;
        return true;
    }
}
