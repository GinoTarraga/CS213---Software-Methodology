/*
 * Gino Tarraga
 * Kaival Patel
 */
package utils;

import java.io.Serializable;


public class Tag implements Serializable{

    private static final long serialVersionUID = 1L;
    private String tagType;
    private String tagValue;
    
   
    public Tag(String type, String value)
    {
        this.tagType = type;
        this.tagValue = value;
    }

    
    public boolean equals(Object o) {
        boolean retVal = false;

        if (o == null || !(o instanceof Tag)) {
            return false;
        }

        Tag compareTag = (Tag) o;

        if (compareTag.getTagType().equals(this.tagType)) {
            if (compareTag.getTagValue().equals(this.tagValue)) {
                return true;
            }
        }

        return false;
    }

    
    
    public String getTagType() {
        return tagType;
    }

    
    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    
    public String getTagValue() {
        return tagValue;
    }

    
    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }
}
