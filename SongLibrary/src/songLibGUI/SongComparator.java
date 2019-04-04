/*
 * Gino Tarraga
 * Kaival Patel
 */
package songLibGUI;

import java.util.Comparator;

public class SongComparator implements Comparator<Song> {

	public int compare(Song s1, Song s2) {
		String name1 = s1.getName();
		String name2 = s2.getName();
			if(!(name1.equals(name2))){
				return s1.getName().compareToIgnoreCase(s2.getName());
			}else{
				name1 = name1 + s1.getArtist();
				name2 = name2 + s2.getArtist();
				return name1.compareToIgnoreCase(name2);
					
			}
	}
}