import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Scanner;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.FileInputStream;

class Embedder 
{

	private File vessel;
	private File msgFile;
	private File outFile;
	private byte[] buff;

	public Embedder(File vessel,File msgFile,File outFile)throws Exception
	{

		if(vessel.exists() == false)throw new Exception("Source file doesnt exists");
		if(msgFile.exists() == false)throw new Exception("message file doesnt exists");

		this.vessel = vessel;
		this.msgFile = msgFile;
		this.outFile = outFile;
		this.buff = new byte[1024];

	}

	public  void embedd()throws Exception
	{

		boolean finished = false;
		int slices[],n=0,k=0;
		ByteManager.setFlag(0);
		int R=0,G=0,B=0;
		int updatedBytes[];

		FileInputStream fin = new FileInputStream(vessel);
		BufferedImage vesselImage = ImageIO.read(vessel);

		int vesselWidth = vesselImage.getWidth();
   		int vesselHeight = vesselImage.getHeight();

   		if(vesselHeight*vesselWidth < msgFile.length()+HeaderManager.getHeaderLength())throw new Exception("insufficient Size");

   		WritableRaster raster = vesselImage.getRaster();

        for (int i = 0; i < vesselWidth; i++) {
            for (int j = 0; j < vesselHeight; j++) {
				
				if(k == n){

					n = fin.read(buff);
					k = 0;
				}

				if(n == -1){

					finished= true;
					break;
				}

				updatedBytes = ByteManager.modify(buff[k],new int[]{raster.getSample(i,j,0),raster.getSample(i,j,1),raster.getSample(i,j,2)});

				raster.setSample(i,j,0,updatedBytes[0]);
				raster.setSample(i,j,1,updatedBytes[1]);
				raster.setSample(i,j,2,updatedBytes[2]);


            }

            if(finished)break;
        }


		vesselImage.setData(raster);
		ImageIO.write(vesselImage,"jpg",outFile);   

		System.out.println("done with emedding");     

	}

	
	public static void main(String args[]){

		try{

			File vessel = new File(args[0]);
			File dataFile = new File(args[1]);
			File outputFile = new File(args[2]);

			new Embedder(vessel,dataFile,outputFile).embedd();

		}

		catch(ArrayIndexOutOfBoundsException ex ){

			System.out.println("Usage java Embedder <vesselImage> <dataFile> <OutputImage> ");
		}

		catch(Exception ex){

			System.out.println(ex.getMessage());
		}
		
	}
}