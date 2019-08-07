public class ByteManager{

	static private int flag;

	static
	{

		flag = 0;
	}

	static int[] modify(byte b,int[] originalBytes){

		if(flag == 0){

			originalBytes[0]>>=2;
			originalBytes[0]<<=2;
			originalBytes[0] |= b&3;
			b >>= 2;

			originalBytes[1]>>=3;
			originalBytes[1]<<=3;
			originalBytes[1] |= b&7;
			b >>= 3;

			originalBytes[2]>>=3;
			originalBytes[2]<<=3;
			originalBytes[2] |= b&7;
			b >>= 3;

		}

		else if(flag == 1){

			originalBytes[0]>>=3;
			originalBytes[0]<<=3;
			originalBytes[0] |= b&7;
			b >>= 3;

			originalBytes[1]>>=2;
			originalBytes[1]<<=2;
			originalBytes[1] |= b&3;
			b >>= 2;

			originalBytes[2]>>=3;
			originalBytes[2]<<=3;
			originalBytes[2] |= b&7;
			b >>= 3;

		}

		else {

			originalBytes[0]>>=3;
			originalBytes[0]<<=3;
			originalBytes[0] |= b&7;
			b >>= 3;

			originalBytes[1]>>=3;
			originalBytes[1]<<=3;
			originalBytes[1] |= b&7;
			b >>= 3;

			originalBytes[2]>>=2;
			originalBytes[2]<<=2;
			originalBytes[2] |= b&3;
			b >>= 2;


		}

		flag = (flag+1)%3;

		return originalBytes;
	}


	public static int patchUp(int[] arg){

		int res = 0;

		if(flag == 0){

			res |= (arg[2] <<= 5 ) | (arg[1] <<= 2 ) | arg[0]  ;
		}

		else if(flag == 0){

			res |= (arg[2] <<= 5 ) | (arg[1] <<= 3 ) | arg[0] ;

		}

		else{

			res |= (arg[2] <<= 6 ) | (arg[1] <<= 3 ) | arg[0] ;

		}

		return res;
	}

	static public void setFlag(int flag){

		ByteManager.flag = flag%3;

	}

	static int getFlag(){

		return ByteManager.flag;
	}

}