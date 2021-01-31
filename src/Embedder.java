import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.FileInputStream;

class Embedder {

	private File vessel;
	private File msgFile;
	private File outFile;
	private byte[] buff;

	public Embedder(File vessel,File msgFile,File outFile)throws Exception
	{

		if(vessel.exists() == false) {
			throw new Exception("Source file doesn't exists");
		} else if(msgFile.exists() == false) {
			throw new Exception("message file doesn't exists");
		}

		this.vessel = vessel;
		this.msgFile = msgFile;
		this.outFile = outFile;
		this.buff = new byte[1024];
		ByteManager.setFlag(0);
		ByteManager.setPatternStrategy(ByteEmbedderPatternStrategy.INCREMENTAL_1);
	}

	public void embedd() throws Exception
	{

		boolean finished = false;
		int n,k,x,y;
		int[] updatedBytes;

		n = k = x = y = 0;

		byte[] headerBytes =  HeaderManager.getHeader(msgFile).getBytes();
		n = headerBytes.length;

		FileInputStream fin = new FileInputStream(msgFile);
		BufferedImage vesselImage = ImageIO.read(vessel);

		int vesselWidth = vesselImage.getWidth();
   		int vesselHeight = vesselImage.getHeight();

   		if(vesselHeight*vesselWidth < msgFile.length()+n)throw new Exception("insufficient Size");
   		System.out.println("Vessel Capacity :: "+vesselHeight*vesselWidth);
   		System.out.println("Data File Size :: "+msgFile.length());
		System.out.println("Header Bytes Length :: "+n);

   		WritableRaster raster = vesselImage.getRaster();

   		for( x=0; x < vesselWidth; x++ ){
   			for( y=0; y < vesselHeight; y++ ){

   				if(k == n){

   					System.out.println("HeaderBreak :: "+x+" "+y);
   					finished = true;
   					break;
   				}

   				updatedBytes = ByteManager.embedAlienData(
   						headerBytes[k++],
						new int[]{raster.getSample(x, y,0), raster.getSample(x, y,1), raster.getSample(x, y,2)});
   				raster.setSample(x, y,0, updatedBytes[0]);
				raster.setSample(x, y,1, updatedBytes[1]);
				raster.setSample(x, y,2, updatedBytes[2]);

   			}
   			if(finished)break;
   		}

   		finished = false;
   		k = n = 0;

   		System.out.println("Main body embedding starts at "+x+" "+y);
        for (int i = x; i < vesselWidth; i++) {
            for (int j = y; j < vesselHeight; j++) {
				
				if(k == n){
					n = fin.read(buff);
					k = 0;
				}

				if(n == -1){
					finished = true;
					break;
				}

				updatedBytes = ByteManager.embedAlienData(
						buff[k++],
						new int[]{raster.getSample(i, j,0), raster.getSample(i, j,1), raster.getSample(i, j,2)});
				raster.setSample(i, j,0, updatedBytes[0]);
				raster.setSample(i, j,1, updatedBytes[1]);
				raster.setSample(i, j,2, updatedBytes[2]);
            }

            if(finished)break;
        }

		vesselImage.setData(raster);
		ImageIO.write(vesselImage,"png",outFile);
		System.out.println("done with emedding");     

	}
	
	public static void main(String args[]){
		try{
			File vessel = new File(args[0]);
			File dataFile = new File(args[1]);
			File outputFile = new File(args[2]);
			new Embedder(vessel, dataFile, outputFile).embedd();
		} catch(ArrayIndexOutOfBoundsException ex ){
			System.out.println("Usage java Embedder <vesselImage> <dataFile> <OutputImage>");
		} catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
}