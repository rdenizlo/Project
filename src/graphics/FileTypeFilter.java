/** 
 *  Group:       1
 *  Name: 		 Thomas Chilton, Ramon Deniz, Antonio Gomez, Maximus Kieu
 *  Class:		 CSE360
 *  Section: 	 85141
 *  Assignment:  Final Project
 */

/**
* 
* 
*  @author Antonio Gomez
*/

package graphics;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class FileTypeFilter extends FileFilter {

    private final String extension;
    private final String description;

    /**
     * Instantiates a class of type FileTypeFilter and sets the extent

     * @param extension
     * @param description
     */
    public FileTypeFilter(String extension, String description) {
        this.extension = extension;
        this.description = description;

    }

    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        }
        return file.getName().endsWith(extension);
    }

    @Override
    public String getDescription() {
        return description + String.format(" (*%s)", extension);
    }

}
