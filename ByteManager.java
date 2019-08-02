public class ByteManager{

	static private int flag;

	static int[] slice(byte b){

		int[] res = new int[3];

		if(flag == 0){

			res[0] = b&3;
			b >>= 2;

			res[1] = b&7;
			b >>= 3;

			res[2] = b&7;
			b >>= 3;

		}

		else if(flag == 1){

			res[0] = b&7;
			b >>= 3;

			res[1] = b&3;
			b >>= 2;

			res[2] = b&7;
			b >>= 3;

		}

		else {

			res[0] = b&7;
			b >>= 3;

			res[1] = b&7;
			b >>= 3;

			res[2] = b&3;
			b >>= 2;

		}

		flag = (flag+1)%3;

		return res;
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

}