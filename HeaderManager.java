import java.io.File;

public class HeaderManager{

	private static final char DISCRIMINATOR = '~';
	private static final int HEADER_LEANGTH = 50;
	private static final int NAME_LENGTH = 40; 
	private static final int SIZE_LENGTH = 9; 


	public static String formHeader(File f){

		String fName = f.getName();
		String fLength = String.valueOf(f.length());

		if(fName.length() > NAME_LENGTH )fName = fName.substring(0,NAME_LENGTH);

		while( fName.length() < NAME_LENGTH )fName = "_"+fName;

		while( fLength.length() < SIZE_LENGTH )fLength = "_"+fLength;

		return fName+DISCRIMINATOR+fLength;
	}

	public static String getName(String Header){


		return ((Header.substring(0,(Header.indexOf(DISCRIMINATOR)))).replaceAll("_"," ")).trim();
	}

	public static int getLength(String Header){


		return Integer.parseInt(((Header.substring((Header.indexOf(DISCRIMINATOR)+1))).replaceAll("_"," ")).trim());
	}


}