import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;

public class Merge {
	private String destination;			// holds parent dir
	private String folderA;
	private String folderB;
	private boolean isADone = false;	// when true, merge() switches then to B 
	private int uniqueNum = 1;			// lazy counter
	
	
	public Merge( String dir )
	{
		this.destination = dir;
	}

	public void setFolderA(String dir) {
		this.folderA = dir;
	}
	public void setFolderB(String dir) {
		this.folderB = dir;
	}
	
	public boolean merge()
	{
		/*
		 * Renames each file to a new PK and moves its directory to this.destination
		 * 
		 * New name(PK) = <date_last_modified> + <uniqueNum(date_last_modified)>
		 */
		try {
			File f;
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");		// sets date name format
			for(int x = 0; x < 2; x++)
			{
				if(!isADone)
					f = new File(this.folderA);		// does folder A first
				else
					f = new File(this.folderB);		// then B
				
		        String[] pathnames = f.list();		// gets contents
		
		        for (String pathname : pathnames) 
		        {
		        	File toMove = new File(f.getPath() + "\\" + pathname);
		            String dateTaken = sdf.format(toMove.lastModified()) + "-";	// gets date
		            
		            
		            Path from    = Paths.get(toMove.getAbsolutePath());
		            // TODO get FS syntax 
		            String nativeNumber = toMove.toString().substring(0, toMove.toString().lastIndexOf('.')); // DCSM - 000.png
		            
		            
		            Path newDir  = Paths.get(this.destination + "\\" + dateTaken + (this.uniqueNum++) + getExtension(from.toString()));

		            System.out.println(from.toString());
		            System.out.println(newDir.toString());
		            Files.move(from, newDir, StandardCopyOption.REPLACE_EXISTING);
 		        }
		        this.isADone = !this.isADone;		// flips the A|B switch 
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	private String getExtension(String filename) {
		return filename.substring(filename.lastIndexOf('.'));
	}
}
