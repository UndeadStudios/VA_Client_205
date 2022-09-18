public final class SimpleTile {

	public SimpleTile(int i, int j, int k, int l, int i1, int j1, boolean flag, boolean tex) {
		nothEastColor = i;
		northColor = j;
		centerColor = k;
		eastColor = l;
		texture = i1;
		colorRGB = j1;
		flat = flag;
	}

	public final int nothEastColor;
	public final int northColor;
	public final int centerColor;
	public final int eastColor;
	public final int texture;
	public boolean flat;
	public final int colorRGB;
}