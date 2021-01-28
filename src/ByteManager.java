public class ByteManager {

	static private int flag = 0;
	static private ByteEmbedderPatternStrategy patternStrategy;

	static {
		flag = 0;
		patternStrategy = ByteEmbedderPatternStrategy.KEEP_SAME;
	}

	private static void updateFlag() {
		if(patternStrategy == ByteEmbedderPatternStrategy.INCREMENTAL_1) {
			flag = (flag+1)%3;
		} else if(patternStrategy == ByteEmbedderPatternStrategy.INCREMENTAL_2) {
			flag = (flag+2)%3;
		}
	}

	static int[] embedAlienData(int alienData,int[] nativeData){

		int noOfBytesToEmbed;

		for(int i=0;i<3;i++) {
			noOfBytesToEmbed = (i==flag)?2:3;
			nativeData[i] >>= noOfBytesToEmbed;
			nativeData[i] <<= noOfBytesToEmbed;
			nativeData[i] |= (alienData&(1<<(noOfBytesToEmbed+1))-1);
			alienData >>= noOfBytesToEmbed;
		}

		updateFlag();

		return nativeData;
	}


	public static int getAlienData(int[] arg){

		int extractedData = 0, bytesAddedTillNow = 0, noOfBytesToAdd;

		for(int i=0;i<3;i++) {
			noOfBytesToAdd = (flag == i)?2:3;
			extractedData |= (arg[i]&((1<<noOfBytesToAdd))-1) << bytesAddedTillNow;
			bytesAddedTillNow += noOfBytesToAdd;
		}

		updateFlag();

		return extractedData;
	}

	public static void setFlag(int flag) {
		ByteManager.flag = flag % 3;
	}

	public static int getFlag(){
		return ByteManager.flag;
	}

	public static void setPatternStrategy(ByteEmbedderPatternStrategy newPatternStrategy) {
		patternStrategy = newPatternStrategy;
	}

	public static ByteEmbedderPatternStrategy getPatternStrategy() {
		return patternStrategy;
	}

}