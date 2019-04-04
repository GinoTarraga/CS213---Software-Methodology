/*
 * Gino Tarraga
 * Kaival Patel
 */
package songLibGUI;

public class Song {
	
	private String name;
	private String artist;
	private String album;
	private String year;

	public Song(String Name, String Artist, String Album, String Year) {
		// TODO Auto-generated constructor stub
		this.name = Name;
		this.artist = Artist;
		this.album = Album;
		this.year = Year;
	}
	
	public void setName(String nameString) {
		name = nameString;
	}
	
	public void setArtist(String artistString) {
		artist = artistString;
	}
	
	public void setAlbum(String albumString) {
		album = albumString;
	}
	
	public void setYear(String yearString) {
		year = yearString;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public String getArtist() {
		// TODO Auto-generated method stub
		return artist;
	}

	public String getAlbum() {
		// TODO Auto-generated method stub
		return album;
	}

	public String getYear() {
		// TODO Auto-generated method stub
		return year;
	}
	
	public String toString() {
		return name;
	}	
}