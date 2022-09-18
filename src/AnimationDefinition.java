
public final class AnimationDefinition {

	private int[] frameSounds;

	public static void unpackConfig(StreamLoader streamLoader)
    {
    	Buffer stream = new Buffer(streamLoader.getDataForName("seq.dat"));
        int length = stream.readUnsignedShort();
        //System.out.println("Animations Loaded: "+length);
        if(anims == null)
            anims = new AnimationDefinition[length + 5000];
        for(int j = 0; j < length; j++) {
            if(anims[j] == null)
                anims[j] = new AnimationDefinition();
            anims[j].readValues(stream);
			
        }
    }

    public int method258(int i) {
        int j = durations[i];
        if(j == 0)
        {
            SequenceFrame class36 = SequenceFrame.method531(primaryFrames[i]);
            if(class36 != null)
                j = durations[i] = class36.anInt636;
        }
        if(j == 0)
            j = 1;
        return j;
    }

    private void readValues(Buffer stream) {
		int i;
		while ((i = stream.readUnsignedByte()) != 0) {


			if (i == 1) {
				anInt352 = stream.readUnsignedShort();
				primaryFrames = new int[anInt352];
				secondaryFrames = new int[anInt352];
				durations = new int[anInt352];

				for (int j = 0; j < anInt352; j++)
					durations[j] = stream.readUnsignedShort();


				for (int j = 0; j < anInt352; j++) {
					primaryFrames[j] = stream.readUnsignedShort();
					secondaryFrames[j] = -1;
				}

				for (int j = 0; j < anInt352; j++) {
					primaryFrames[j] += stream.readUnsignedShort() << 16;
				}
			} else if (i == 2)
				anInt356 = stream.readUnsignedShort();
			else if (i == 3) {
				int k = stream.readUnsignedByte();
				anIntArray357 = new int[k + 1];
				for (int l = 0; l < k; l++)
					anIntArray357[l] = stream.readUnsignedByte();
				anIntArray357[k] = 9999999;
			} else if (i == 4)
				aBoolean358 = true;
			else if (i == 5)
				anInt359 = stream.readUnsignedByte();
			else if (i == 6)
				anInt360 = stream.readUnsignedShort();
			else if (i == 7)
				anInt361 = stream.readUnsignedShort();
			else if (i == 8)
				anInt362 = stream.readUnsignedByte();
			else if (i == 9)
				anInt363 = stream.readUnsignedByte();
			else if (i == 10)
				anInt364 = stream.readUnsignedByte();
			else if (i == 11)
				anInt365 = stream.readUnsignedByte();
			else if (i == 12) {
				int len = stream.readUnsignedByte();

				for (int i1 = 0; i1 < len; i1++) {
					stream.readUnsignedShort();
				}

				for (int i1 = 0; i1 < len; i1++) {
					stream.readUnsignedShort();
				}
			} else if (i == 13) {
				int var3 = stream.readUnsignedByte();
				frameSounds = new int[var3];
				for (int var4 = 0; var4 < var3; ++var4) {
					frameSounds[var4] = stream.read24BitInt();
					if (0 != frameSounds[var4]) {
						int var6 = frameSounds[var4] >> 8;
						int var8 = frameSounds[var4] >> 4 & 7;
						int var9 = frameSounds[var4] & 15;
						frameSounds[var4] = var6;
					}
				}
			} else if (i == 14) {
				skeletalId = stream.readInt();
			} else if (i == 15) {
				int count = stream.readUShort();
				skeletalsoundEffect = new int[count];
				skeletalsoundRange = new int[count];
				for (int index = 0; index < count; ++index) {
					skeletalsoundEffect[index] = stream.readUShort();
					skeletalsoundRange[index] = stream.read24BitInt();
				}
			} else if (i == 16) {
				skeletalRangeBegin = stream.readUShort();
				skeletalRangeEnd = stream.readUShort();
			} else if (i == 17) {
				int count = stream.readUnsignedByte();
				unknown = new int[count];
				for (int index = 0; index < count; ++index) {
					unknown[index] = stream.readUnsignedByte();
				}
			}
		}
		if (anInt352 == 0) {
		anInt352 = 1;
		primaryFrames = new int[1];
		primaryFrames[0] = -1;
		secondaryFrames = new int[1];
		secondaryFrames[0] = -1;
		durations = new int[1];
		durations[0] = -1;
	}
	if (anInt363 == -1)
		if (anIntArray357 != null)
			anInt363 = 2;
		else
			anInt363 = 0;
	if (anInt364 == -1) {
		if (anIntArray357 != null) {
			anInt364 = 2;
			return;
		}
		anInt364 = 0;
	}
}

    private AnimationDefinition() {
        anInt356 = -1;
        aBoolean358 = false;
        anInt359 = 5;
        anInt360 = -1; //Removes shield
        anInt361 = -1; //Removes weapon
        anInt362 = 99;
        anInt363 = -1; //Stops character from moving
        anInt364 = -1;
        anInt365 = 1; 
    }

    public static AnimationDefinition anims[];
    public int anInt352;
    public int primaryFrames[];
    public int secondaryFrames[];
    public int[] durations;
    public int anInt356;
    public int anIntArray357[];
    public boolean aBoolean358;
    public int anInt359;
    public int anInt360;
    public int anInt361;
    public int anInt362;
    public int anInt363;
    public int anInt364;
    public int anInt365;
	private int skeletalRangeBegin = -1;
	private int skeletalRangeEnd = -1;
	private int skeletalId = -1;
	private int[] skeletalsoundEffect;
	private int[] unknown;
	private int[] skeletalsoundRange;
    public static int anInt367;
}
