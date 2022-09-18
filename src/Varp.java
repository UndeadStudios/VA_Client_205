public final class  Varp {

	 public static void unpackConfig(StreamLoader streamLoader)
	    {
	        Buffer stream = new Buffer(streamLoader.getDataForName("varp.dat"));
	        anInt702 = 0;
	        int cacheSize = stream.readUnsignedShort();
	        if(cache == null)
	            cache = new Varp[cacheSize];
	        if(anIntArray703 == null)
	            anIntArray703 = new int[cacheSize];
	        for(int j = 0; j < cacheSize; j++)
	        {
	            if(cache[j] == null)
	                cache[j] = new Varp();
	            cache[j].readValues(stream, j);
	        }
	        if(stream.currentPosition != stream.payload.length)
	            System.out.println("varptype load mismatch");
	    }

    private void readValues(Buffer stream, int i)
    {
        do
        {
            int j = stream.readUnsignedByte();
            if(j == 0)
                return;
            if(j == 1)
                 stream.readUnsignedByte();
            else
            if(j == 2)
                stream.readUnsignedByte();
            else
            if(j == 3)
                anIntArray703[anInt702++] = i;
            else
            if(j == 4) {
			} else
            if(j == 5)
                anInt709 = stream.readUnsignedShort();
            else
            if(j == 6) {
			} else
            if(j == 7)
                stream.readDWord();
            else
            if(j == 8)
                aBoolean713 = true;
             else
            if(j == 10)
                 stream.readString();
            else
            if(j == 11)
                aBoolean713 = true;
            else
            if(j == 12)
                stream.readDWord();
            else
            if(j == 13) {
			} else
                System.out.println("Error unrecognised config code: " + j);
        } while(true);
    }

    private Varp()
    {
        aBoolean713 = false;
    }

    public static Varp cache[];
    private static int anInt702;
    private static int[] anIntArray703;
    public int anInt709;
    public boolean aBoolean713;

}
