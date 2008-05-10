/*
 * AWSSearchType.java
 *
 * Created on May 9, 2008, 10:54 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package aws_owl;
import java.lang.String;
/**
 *
 * @author phelps
 */
public class AWSSearchType
{
    private int type;
    
    public final static int BOOK = 0;
    public final static int DVD = 1;
    public final static int MUSIC = 2;
    
    public AWSSearchType(int type)
    {
        this.type = type;
    }
    
    public AWSSearchType()
    {
    }
    
    public int getType()
    {
        return type;
    }
    
    public String getSearchSubject()
    {
        if(type == BOOK)
            return "Author";
        else if(type == DVD)
            return "Actor";
        else if(type == MUSIC)
            return "Artist";
        else
            return "Unknown";
    }
    
    public String toString()
    {
        if(type == BOOK)
            return "Book";
        else if(type == DVD)
            return "DVD";
        else if(type == MUSIC)
            return "Music";
        else
            return "Unknown";
    }
}
