import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.util.Scanner;
import java.io.File;
import javax.imageio.ImageIO;

class Embedder 
{

	private File vessel;
	private File msgFile;
	private File outFile;

	public Embedder(File vessel,File msgFile,File outFile)throws Exception
	{

		if(vessel.exists() == false)throw new Exception("Source file doesnt exists");
		if(msgFile.exists() == false)throw new Exception("message file doesnt exists");

		this.vessel = vessel;
		this.msgFile = msgFile;
		this.outFile = outFile;

	}

	public  boolean embedd(){

		try{

			BufferedImage vesselImage = ImageIO.read(vessel);

			int vesselWidth = vesselImage.getWidth();
       		int vesselHeight = vesselImage.getHeight();

       		if(vesselHeight*vesselWidth < msgFile.length()+HeaderManager.getHeaderLength())throw new Exception("insufficient Size");

       		int slices[] = new int[3];

       		Raster raster = vesselImage.getData();

	        for (int i = 0; i < vesselWidth; i++) {
	            for (int j = 0; j < vesselHeight; j++) {
	               	
	            	slices = HeaderManager.Slices(data);

	            	raster.setSample(i,j,0,R);

	            }
	        }


		}

		catch(Exception ex){

			System.out.println(ex.getMessage());

			return false;

		}

		return true;
	}

	public static void main(String args[]){

		Scanner scn = new Scanner(System.in);

		try{

			File vessel = new File(scn.nextLine());
			File msgFile = new File(scn.nextLine());
			File outFile = new File(scn.nextLine());

			Embedder embd = new Embedder(vessel,msgFile,outFile);
			embd.embedd();

		}

		catch(Exception ex){

			System.out.println("Some Exception occured");
			System.out.println(ex.getMessage());
		}

	}
}