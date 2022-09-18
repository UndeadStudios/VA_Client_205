public final class Rasterizer3D extends Rasterizer2D {
	
	public static void nullLoader() {
		anIntArray1468 = null;
		anIntArray1468 = null;
		anIntArray1470 = null;
		anIntArray1471 = null;
		scanOffsets = null;
		textures = null;
		textureIsTransparant = null;
		anIntArray1476 = null;
		textureRequestPixelBuffer = null;
		texturesPixelBuffer = null;
		textureLastUsed = null;
		hslToRgb = null;
		currentPalette = null;
	}
	public static int fieldOfView = 512;
	public static double brightness = 0;
	public static boolean world = true;
	public static void method364()
	{
		scanOffsets = new int[Rasterizer2D.height];
		for(int j = 0; j < Rasterizer2D.height; j++)
			scanOffsets[j] = Rasterizer2D.width * j;

		originViewX = Rasterizer2D.width / 2;
		originViewY = Rasterizer2D.height / 2;
	}

	public static void method365(int j, int k)
	{
	   scanOffsets = new int[k];
		for(int l = 0; l < k; l++)
			scanOffsets[l] = j * l;

		originViewX = j / 2;
		originViewY = k / 2;
	}

	public static void method366()
	{
		textureRequestPixelBuffer = null;
		for(int j = 0; j < textureAmount; j++)
			texturesPixelBuffer[j] = null;

	}

	public static void method367() {
		if (textureRequestPixelBuffer == null) {
			textureRequestBufferPointer = 20;
			textureRequestPixelBuffer = new int[textureRequestBufferPointer][0x10000];
			for (int k = 0; k < textureAmount; k++)
				texturesPixelBuffer[k] = null;
		}
	}

	public static void method368(StreamLoader streamLoader)
	{
		textureCount = 0;
		for(int j = 0; j < textureAmount; j++)
			try
			{
				textures[j] = new IndexedImage(streamLoader, String.valueOf(j), 0);
				if(lowMem && textures[j].anInt1456 == 128)
					textures[j].method356();
				else
					textures[j].method357();
				textures[j].setTransparency(255, 0, 255);
				textureCount++;
			}
			catch(Exception _ex) { }

	}

	public static int getOverallColour(int i)
	{
		if(anIntArray1476[i] != 0)
			return anIntArray1476[i];
		int k = 0;
		int l = 0;
		int i1 = 0;
		int j1 = currentPalette[i].length;
		for(int k1 = 0; k1 < j1; k1++)
		{
			k += currentPalette[i][k1] >> 16 & 0xff;
			l += currentPalette[i][k1] >> 8 & 0xff;
			i1 += currentPalette[i][k1] & 0xff;
		}

		int l1 = (k / j1 << 16) + (l / j1 << 8) + i1 / j1;
		l1 = method373(l1, 1.3999999999999999D);
		if(l1 == 0)
			l1 = 1;
		anIntArray1476[i] = l1;
		return l1;
	}

	public static void method370(int i)
	{
		if(texturesPixelBuffer[i] == null)
			return;
		textureRequestPixelBuffer[textureRequestBufferPointer++] = texturesPixelBuffer[i];
		texturesPixelBuffer[i] = null;
	}

	public static int[] getTexturePixels(int textureId) {
		textureLastUsed[textureId] = lastTextureRetrievalCount++;
		if (texturesPixelBuffer[textureId] != null) {
			return texturesPixelBuffer[textureId];
		}
		int[] texturePixels;
		if (textureRequestBufferPointer > 0) {
			texturePixels = textureRequestPixelBuffer[--textureRequestBufferPointer];
			textureRequestPixelBuffer[textureRequestBufferPointer] = null;
		} else {
			int lastUsed = 0;
			int target = -1;
			for (int l = 0; l < textureCount; l++) {
				if (texturesPixelBuffer[l] != null && (textureLastUsed[l] < lastUsed || target == -1)) {
					lastUsed = textureLastUsed[l];
					target = l;
				}
			}

			texturePixels = texturesPixelBuffer[target];
			texturesPixelBuffer[target] = null;
		}
		texturesPixelBuffer[textureId] = texturePixels;
		IndexedImage background = textures[textureId];
		int[] texturePalette = currentPalette[textureId];

		if (background.width == 64) {
			for (int x = 0; x < 128; x++) {
				for (int y = 0; y < 128; y++) {
					texturePixels[y
							+ (x << 7)] = texturePalette[background.palettePixels[(y >> 1) + ((x >> 1) << 6)]];
				}
			}
		} else {
			for (int i = 0; i < 16384; i++) {
				try {
					texturePixels[i] = texturePalette[background.palettePixels[i]];
				} catch (Exception ignored) {

				}

			}

			textureIsTransparant[textureId] = false;
			for (int i = 0; i < 16384; i++) {
				texturePixels[i] &= 0xf8f8ff;
				int colour = texturePixels[i];
				if (colour == 0) {
					textureIsTransparant[textureId] = true;
				}
				texturePixels[16384 + i] = colour - (colour >>> 3) & 0xf8f8ff;
				texturePixels[32768 + i] = colour - (colour >>> 2) & 0xf8f8ff;
				texturePixels[49152 + i] = colour - (colour >>> 2) - (colour >>> 3) & 0xf8f8ff;
			}

		}
		return texturePixels;
	}

	public static void method372(double d)
	{
		d += Math.random() * 0.029999999999999999D - 0.014999999999999999D;
		int j = 0;
		for(int k = 0; k < 512; k++)
		{
			double d1 = (double)(k / 8) / 64D + 0.0078125D;
			double d2 = (double)(k & 7) / 8D + 0.0625D;
			for(int k1 = 0; k1 < 128; k1++)
			{
				double d3 = (double)k1 / 128D;
				double d4 = d3;
				double d5 = d3;
				double d6 = d3;
				if(d2 != 0.0D)
				{
					double d7;
					if(d3 < 0.5D)
						d7 = d3 * (1.0D + d2);
					else
						d7 = (d3 + d2) - d3 * d2;
					double d8 = 2D * d3 - d7;
					double d9 = d1 + 0.33333333333333331D;
					if(d9 > 1.0D)
						d9--;
					double d10 = d1;
					double d11 = d1 - 0.33333333333333331D;
					if(d11 < 0.0D)
						d11++;
					if(6D * d9 < 1.0D)
						d4 = d8 + (d7 - d8) * 6D * d9;
					else
					if(2D * d9 < 1.0D)
						d4 = d7;
					else
					if(3D * d9 < 2D)
						d4 = d8 + (d7 - d8) * (0.66666666666666663D - d9) * 6D;
					else
						d4 = d8;
					if(6D * d10 < 1.0D)
						d5 = d8 + (d7 - d8) * 6D * d10;
					else
					if(2D * d10 < 1.0D)
						d5 = d7;
					else
					if(3D * d10 < 2D)
						d5 = d8 + (d7 - d8) * (0.66666666666666663D - d10) * 6D;
					else
						d5 = d8;
					if(6D * d11 < 1.0D)
						d6 = d8 + (d7 - d8) * 6D * d11;
					else
					if(2D * d11 < 1.0D)
						d6 = d7;
					else
					if(3D * d11 < 2D)
						d6 = d8 + (d7 - d8) * (0.66666666666666663D - d11) * 6D;
					else
						d6 = d8;
				}
				int l1 = (int)(d4 * 256D);
				int i2 = (int)(d5 * 256D);
				int j2 = (int)(d6 * 256D);
				int k2 = (l1 << 16) + (i2 << 8) + j2;
				k2 = method373(k2, d);
				if(k2 == 0)
					k2 = 1;
				hslToRgb[j++] = k2;
			}

		}

		for(int l = 0; l < textureAmount; l++)
			if(textures[l] != null)
			{
				int ai[] = textures[l].palette;
				currentPalette[l] = new int[ai.length];
				for(int j1 = 0; j1 < ai.length; j1++)
				{
					currentPalette[l][j1] = method373(ai[j1], d);
					if((currentPalette[l][j1] & 0xf8f8ff) == 0 && j1 != 0)
						currentPalette[l][j1] = 1;
				}

			}

		for(int i1 = 0; i1 < textureAmount; i1++)
			method370(i1);

	}

	private static int method373(int i, double d)
	{
		double d1 = (double)(i >> 16) / 256D;
		double d2 = (double)(i >> 8 & 0xff) / 256D;
		double d3 = (double)(i & 0xff) / 256D;
		d1 = Math.pow(d1, d);
		d2 = Math.pow(d2, d);
		d3 = Math.pow(d3, d);
		int j = (int)(d1 * 256D);
		int k = (int)(d2 * 256D);
		int l = (int)(d3 * 256D);
		return (j << 16) + (k << 8) + l;
	}
	
	private static int texelPos(int defaultIndex) {
		int x = (defaultIndex & 127) >> mipMapLevel;
		int y = (defaultIndex >> 7) >> mipMapLevel;
		return x + (y << (7 - mipMapLevel));
	}
	
	private static void setMipmapLevel(int y1, int y2, int y3, int x1, int x2, int x3, int tex) {
		if (!aBoolean1464) {
			mipMapLevel = 0;
			return;
		}
		if (!Configuration.enableMipMapping) {
			if (mipMapLevel != 0) {
				mipMapLevel = 0;
			}
			return;
		}
		if (tex == 17 || tex == 34) {
			mipMapLevel = 0;
			return;
		}
		int textureArea = x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2) >> 1;
		if (textureArea < 0) {
			textureArea = -textureArea;
		}
		if (textureArea > 16384) {
			mipMapLevel = 0;
		} else if (textureArea > 4096) {
			mipMapLevel = 1;
		} else if (textureArea > 1024) {
			mipMapLevel = 1;
		} else if (textureArea > 256) {
			mipMapLevel = 2;
		} else if (textureArea > 64) {
			mipMapLevel = 3;
		} else if (textureArea > 16) {
			mipMapLevel = 4;
		} else if (textureArea > 4) {
			mipMapLevel = 5;
		} else if (textureArea > 1) {
			mipMapLevel = 6;
		} else {
			mipMapLevel = 7;
		}
	}
	
	public static void drawMaterializedTriangle(int y1, int y2, int y3, int x1, int x2, int x3, int hsl1, int hsl2, int hsl3, int tx1, int tx2, int tx3, int ty1, int ty2, int ty3, int tz1, int tz2, int tz3, int tex, float z1, float z2, float z3) {
		if (z1 < 0.0F || z2 < 0.0F || z3 < 0.0F) {
			return;
		}
		if (!Configuration.enableHDTextures || Texture.get(tex) == null) {
			drawShadedTriangle(y1, y2, y3, x1, x2, x3, hsl1, hsl2, hsl3, tz1, tz2 , tz3);
			return;
		}
		setMipmapLevel(y1, y2, y3, x1, x2, x3, tex);
		int[] ai = Texture.get(tex).mipmaps[mipMapLevel];
		tx2 = tx1 - tx2;
		ty2 = ty1 - ty2;
		tz2 = tz1 - tz2;
		tx3 -= tx1;
		ty3 -= ty1;
		tz3 -= tz1;
		int l4 = tx3 * ty1 - ty3 * tx1 << (WorldController.viewDistance == 9 ? 14 : 15);
		int i5 = ty3 * tz1 - tz3 * ty1 << 8;
		int j5 = tz3 * tx1 - tx3 * tz1 << 5;
		int k5 = tx2 * ty1 - ty2 * tx1 << (WorldController.viewDistance == 9 ? 14 : 15);
		int l5 = ty2 * tz1 - tz2 * ty1 << 8;
		int i6 = tz2 * tx1 - tx2 * tz1 << 5;
		int j6 = ty2 * tx3 - tx2 * ty3 << (WorldController.viewDistance == 9 ? 14 : 15);
		int k6 = tz2 * ty3 - ty2 * tz3 << 8;
		int l6 = tx2 * tz3 - tz2 * tx3 << 5;
		int i7 = 0;
		int j7 = 0;
		if (y2 != y1) {
			i7 = (x2 - x1 << 16) / (y2 - y1);
			j7 = (hsl2 - hsl1 << 15) / (y2 - y1);
		}
		int k7 = 0;
		int l7 = 0;
		if (y3 != y2) {
			k7 = (x3 - x2 << 16) / (y3 - y2);
			l7 = (hsl3 - hsl2 << 15) / (y3 - y2);
		}
		int i8 = 0;
		int j8 = 0;
		if (y3 != y1) {
			i8 = (x1 - x3 << 16) / (y1 - y3);
			j8 = (hsl1 - hsl3 << 15) / (y1 - y3);
		}
		float b_aX = x2 - x1;
		float b_aY = y2 - y1;
		float b_aZ = z2 - z1;
		float c_aX = x3 - x1;
		float c_aY = y3 - y1;
		float c_aZ = z3 - z1;

		float div = b_aX * c_aY - c_aX * b_aY;
		float depth_slope = (b_aZ * c_aY - c_aZ * b_aY) / div;
		float depth_increment = (c_aZ * b_aX - b_aZ * c_aX) / div;
		if (y1 <= y2 && y1 <= y3) {
			if (y1 >= Rasterizer2D.clip_bottom) {
				return;
			}
			if (y2 > Rasterizer2D.clip_bottom) {
				y2 = Rasterizer2D.clip_bottom;
			}
			if (y3 > Rasterizer2D.clip_bottom) {
				y3 = Rasterizer2D.clip_bottom;
			}
			z1 = z1 - depth_slope * x1 + depth_slope;
			if (y2 < y3) {
				x3 = x1 <<= 16;
				hsl3 = hsl1 <<= 15;
				if (y1 < 0) {
					x3 -= i8 * y1;
					x1 -= i7 * y1;
					z1 -= depth_increment * y1;
					hsl3 -= j8 * y1;
					hsl1 -= j7 * y1;
					y1 = 0;
				}
				x2 <<= 16;
				hsl2 <<= 15;
				if (y2 < 0) {
					x2 -= k7 * y2;
					hsl2 -= l7 * y2;
					y2 = 0;
				}
				int k8 = y1 - originViewY;
				l4 += j5 * k8;
				k5 += i6 * k8;
				j6 += l6 * k8;
				if (y1 != y2 && i8 < i7 || y1 == y2 && i8 > k7) {
					y3 -= y2;
					y2 -= y1;
					y1 = scanOffsets[y1];
					while (--y2 >= 0) {
						drawMaterializedScanline(Rasterizer2D.pixels, ai, y1, x3 >> 16, x1 >> 16, hsl3 >> 7, hsl1 >> 7, l4, k5, j6, i5, l5, k6, z1, depth_slope);
						x3 += i8;
						x1 += i7;
						z1 += depth_increment;
						hsl3 += j8;
						hsl1 += j7;
						y1 += Rasterizer2D.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					while (--y3 >= 0) {
						drawMaterializedScanline(Rasterizer2D.pixels, ai, y1, x3 >> 16, x2 >> 16, hsl3 >> 7, hsl2 >> 7, l4, k5, j6, i5, l5, k6, z1, depth_slope);
						x3 += i8;
						x2 += k7;
						z1 += depth_increment;
						hsl3 += j8;
						hsl2 += l7;
						y1 += Rasterizer2D.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					return;
				}
				y3 -= y2;
				y2 -= y1;
				y1 = scanOffsets[y1];
				while (--y2 >= 0) {
					drawMaterializedScanline(Rasterizer2D.pixels, ai, y1, x1 >> 16, x3 >> 16, hsl1 >> 7, hsl3 >> 7, l4, k5, j6, i5, l5, k6, z1, depth_slope);
					x3 += i8;
					x1 += i7;
					z1 += depth_increment;
					hsl3 += j8;
					hsl1 += j7;
					y1 += Rasterizer2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y3 >= 0) {
					drawMaterializedScanline(Rasterizer2D.pixels, ai, y1, x2 >> 16, x3 >> 16, hsl2 >> 7, hsl3 >> 7, l4, k5, j6, i5, l5, k6, z1, depth_slope);
					x3 += i8;
					x2 += k7;
					z1 += depth_increment;
					hsl3 += j8;
					hsl2 += l7;
					y1 += Rasterizer2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			x2 = x1 <<= 16;
			hsl2 = hsl1 <<= 15;
			if (y1 < 0) {
				x2 -= i8 * y1;
				x1 -= i7 * y1;
				z1 -= depth_increment * y1;
				hsl2 -= j8 * y1;
				hsl1 -= j7 * y1;
				y1 = 0;
			}
			x3 <<= 16;
			hsl3 <<= 15;
			if (y3 < 0) {
				x3 -= k7 * y3;
				hsl3 -= l7 * y3;
				y3 = 0;
			}
			int l8 = y1 - originViewY;
			l4 += j5 * l8;
			k5 += i6 * l8;
			j6 += l6 * l8;
			if (y1 != y3 && i8 < i7 || y1 == y3 && k7 > i7) {
				y2 -= y3;
				y3 -= y1;
				y1 = scanOffsets[y1];
				while (--y3 >= 0) {
					drawMaterializedScanline(Rasterizer2D.pixels, ai, y1, x2 >> 16, x1 >> 16, hsl2 >> 7, hsl1 >> 7, l4, k5, j6, i5, l5, k6, z1, depth_slope);
					x2 += i8;
					x1 += i7;
					z1 += depth_increment;
					hsl2 += j8;
					hsl1 += j7;
					y1 += Rasterizer2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y2 >= 0) {
					drawMaterializedScanline(Rasterizer2D.pixels, ai, y1, x3 >> 16, x1 >> 16, hsl3 >> 7, hsl1 >> 7, l4, k5, j6, i5, l5, k6, z1, depth_slope);
					x3 += k7;
					x1 += i7;
					z1 += depth_increment;
					hsl3 += l7;
					hsl1 += j7;
					y1 += Rasterizer2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			y2 -= y3;
			y3 -= y1;
			y1 = scanOffsets[y1];
			while (--y3 >= 0) {
				drawMaterializedScanline(Rasterizer2D.pixels, ai, y1, x1 >> 16, x2 >> 16, hsl1 >> 7, hsl2 >> 7, l4, k5, j6, i5, l5, k6, z1, depth_slope);
				x2 += i8;
				x1 += i7;
				z1 += depth_increment;
				hsl2 += j8;
				hsl1 += j7;
				y1 += Rasterizer2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--y2 >= 0) {
				drawMaterializedScanline(Rasterizer2D.pixels, ai, y1, x1 >> 16, x3 >> 16, hsl1 >> 7, hsl3 >> 7, l4, k5, j6, i5, l5, k6, z1, depth_slope);
				x3 += k7;
				x1 += i7;
				z1 += depth_increment;
				hsl3 += l7;
				hsl1 += j7;
				y1 += Rasterizer2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		if (y2 <= y3) {
			if (y2 >= Rasterizer2D.clip_bottom) {
				return;
			}
			if (y3 > Rasterizer2D.clip_bottom) {
				y3 = Rasterizer2D.clip_bottom;
			}
			if (y1 > Rasterizer2D.clip_bottom) {
				y1 = Rasterizer2D.clip_bottom;
			}
			z2 = z2 - depth_slope * x2 + depth_slope;
			if (y3 < y1) {
				x1 = x2 <<= 16;
				hsl1 = hsl2 <<= 15;
				if (y2 < 0) {
					x1 -= i7 * y2;
					x2 -= k7 * y2;
					z2 -= depth_increment * y2;
					hsl1 -= j7 * y2;
					hsl2 -= l7 * y2;
					y2 = 0;
				}
				x3 <<= 16;
				hsl3 <<= 15;
				if (y3 < 0) {
					x3 -= i8 * y3;
					hsl3 -= j8 * y3;
					y3 = 0;
				}
				int i9 = y2 - originViewY;
				l4 += j5 * i9;
				k5 += i6 * i9;
				j6 += l6 * i9;
				if (y2 != y3 && i7 < k7 || y2 == y3 && i7 > i8) {
					y1 -= y3;
					y3 -= y2;
					y2 = scanOffsets[y2];
					while (--y3 >= 0) {
						drawMaterializedScanline(Rasterizer2D.pixels, ai, y2, x1 >> 16, x2 >> 16, hsl1 >> 7, hsl2 >> 7, l4, k5, j6, i5, l5, k6, z2, depth_slope);
						x1 += i7;
						x2 += k7;
						z2 += depth_increment;
						hsl1 += j7;
						hsl2 += l7;
						y2 += Rasterizer2D.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					while (--y1 >= 0) {
						drawMaterializedScanline(Rasterizer2D.pixels, ai, y2, x1 >> 16, x3 >> 16, hsl1 >> 7, hsl3 >> 7, l4, k5, j6, i5, l5, k6, z2, depth_slope);
						x1 += i7;
						x3 += i8;
						z2 += depth_increment;
						hsl1 += j7;
						hsl3 += j8;
						y2 += Rasterizer2D.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					return;
				}
				y1 -= y3;
				y3 -= y2;
				y2 = scanOffsets[y2];
				while (--y3 >= 0) {
					drawMaterializedScanline(Rasterizer2D.pixels, ai, y2, x2 >> 16, x1 >> 16, hsl2 >> 7, hsl1 >> 7, l4, k5, j6, i5, l5, k6, z2, depth_slope);
					x1 += i7;
					x2 += k7;
					z2 += depth_increment;
					hsl1 += j7;
					hsl2 += l7;
					y2 += Rasterizer2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y1 >= 0) {
					drawMaterializedScanline(Rasterizer2D.pixels, ai, y2, x3 >> 16, x1 >> 16, hsl3 >> 7, hsl1 >> 7, l4, k5, j6, i5, l5, k6, z2, depth_slope);
					x1 += i7;
					x3 += i8;
					z2 += depth_increment;
					hsl1 += j7;
					hsl3 += j8;
					y2 += Rasterizer2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			x3 = x2 <<= 16;
			hsl3 = hsl2 <<= 15;
			if (y2 < 0) {
				x3 -= i7 * y2;
				x2 -= k7 * y2;
				z2 -= depth_increment * y2;
				hsl3 -= j7 * y2;
				hsl2 -= l7 * y2;
				y2 = 0;
			}
			x1 <<= 16;
			hsl1 <<= 15;
			if (y1 < 0) {
				x1 -= i8 * y1;
				hsl1 -= j8 * y1;
				y1 = 0;
			}
			int j9 = y2 - originViewY;
			l4 += j5 * j9;
			k5 += i6 * j9;
			j6 += l6 * j9;
			if (i7 < k7) {
				y3 -= y1;
				y1 -= y2;
				y2 = scanOffsets[y2];
				while (--y1 >= 0) {
					drawMaterializedScanline(Rasterizer2D.pixels, ai, y2, x3 >> 16, x2 >> 16, hsl3 >> 7, hsl2 >> 7, l4, k5, j6, i5, l5, k6, z2, depth_slope);
					x3 += i7;
					x2 += k7;
					z2 += depth_increment;
					hsl3 += j7;
					hsl2 += l7;
					y2 += Rasterizer2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y3 >= 0) {
					drawMaterializedScanline(Rasterizer2D.pixels, ai, y2, x1 >> 16, x2 >> 16, hsl1 >> 7, hsl2 >> 7, l4, k5, j6, i5, l5, k6, z2, depth_slope);
					x1 += i8;
					x2 += k7;
					z2 += depth_increment;
					hsl1 += j8;
					hsl2 += l7;
					y2 += Rasterizer2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			y3 -= y1;
			y1 -= y2;
			y2 = scanOffsets[y2];
			while (--y1 >= 0) {
				drawMaterializedScanline(Rasterizer2D.pixels, ai, y2, x2 >> 16, x3 >> 16, hsl2 >> 7, hsl3 >> 7, l4, k5, j6, i5, l5, k6, z2, depth_slope);
				x3 += i7;
				x2 += k7;
				z2 += depth_increment;
				hsl3 += j7;
				hsl2 += l7;
				y2 += Rasterizer2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--y3 >= 0) {
				drawMaterializedScanline(Rasterizer2D.pixels, ai, y2, x2 >> 16, x1 >> 16, hsl2 >> 7, hsl1 >> 7, l4, k5, j6, i5, l5, k6, z2, depth_slope);
				x1 += i8;
				x2 += k7;
				z2 += depth_increment;
				hsl1 += j8;
				hsl2 += l7;
				y2 += Rasterizer2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		if (y3 >= Rasterizer2D.clip_bottom) {
			return;
		}
		if (y1 > Rasterizer2D.clip_bottom) {
			y1 = Rasterizer2D.clip_bottom;
		}
		if (y2 > Rasterizer2D.clip_bottom) {
			y2 = Rasterizer2D.clip_bottom;
		}
		z3 = z3 - depth_slope * x3 + depth_slope;
		if (y1 < y2) {
			x2 = x3 <<= 16;
			hsl2 = hsl3 <<= 15;
			if (y3 < 0) {
				x2 -= k7 * y3;
				x3 -= i8 * y3;
				z3 -= depth_increment * y3;
				hsl2 -= l7 * y3;
				hsl3 -= j8 * y3;
				y3 = 0;
			}
			x1 <<= 16;
			hsl1 <<= 15;
			if (y1 < 0) {
				x1 -= i7 * y1;
				hsl1 -= j7 * y1;
				y1 = 0;
			}
			int k9 = y3 - originViewY;
			l4 += j5 * k9;
			k5 += i6 * k9;
			j6 += l6 * k9;
			if (k7 < i8) {
				y2 -= y1;
				y1 -= y3;
				y3 = scanOffsets[y3];
				while (--y1 >= 0) {
					drawMaterializedScanline(Rasterizer2D.pixels, ai, y3, x2 >> 16, x3 >> 16, hsl2 >> 7, hsl3 >> 7, l4, k5, j6, i5, l5, k6, z3, depth_slope);
					x2 += k7;
					x3 += i8;
					z3 += depth_increment;
					hsl2 += l7;
					hsl3 += j8;
					y3 += Rasterizer2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y2 >= 0) {
					drawMaterializedScanline(Rasterizer2D.pixels, ai, y3, x2 >> 16, x1 >> 16, hsl2 >> 7, hsl1 >> 7, l4, k5, j6, i5, l5, k6, z3, depth_slope);
					x2 += k7;
					x1 += i7;
					z3 += depth_increment;
					hsl2 += l7;
					hsl1 += j7;
					y3 += Rasterizer2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			y2 -= y1;
			y1 -= y3;
			y3 = scanOffsets[y3];
			while (--y1 >= 0) {
				drawMaterializedScanline(Rasterizer2D.pixels, ai, y3, x3 >> 16, x2 >> 16, hsl3 >> 7, hsl2 >> 7, l4, k5, j6, i5, l5, k6, z3, depth_slope);
				x2 += k7;
				x3 += i8;
				z3 += depth_increment;
				hsl2 += l7;
				hsl3 += j8;
				y3 += Rasterizer2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--y2 >= 0) {
				drawMaterializedScanline(Rasterizer2D.pixels, ai, y3, x1 >> 16, x2 >> 16, hsl1 >> 7, hsl2 >> 7, l4, k5, j6, i5, l5, k6, z3, depth_slope);
				x2 += k7;
				x1 += i7;
				z3 += depth_increment;
				hsl2 += l7;
				hsl1 += j7;
				y3 += Rasterizer2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		x1 = x3 <<= 16;
		hsl1 = hsl3 <<= 15;
		if (y3 < 0) {
			x1 -= k7 * y3;
			x3 -= i8 * y3;
			z3 -= depth_increment * y3;
			hsl1 -= l7 * y3;
			hsl3 -= j8 * y3;
			y3 = 0;
		}
		x2 <<= 16;
		hsl2 <<= 15;
		if (y2 < 0) {
			x2 -= i7 * y2;
			hsl2 -= j7 * y2;
			y2 = 0;
		}
		int l9 = y3 - originViewY;
		l4 += j5 * l9;
		k5 += i6 * l9;
		j6 += l6 * l9;
		if (k7 < i8) {
			y1 -= y2;
			y2 -= y3;
			y3 = scanOffsets[y3];
			while (--y2 >= 0) {
				drawMaterializedScanline(Rasterizer2D.pixels, ai, y3, x1 >> 16, x3 >> 16, hsl1 >> 7, hsl3 >> 7, l4, k5, j6, i5, l5, k6, z3, depth_slope);
				x1 += k7;
				x3 += i8;
				z3 += depth_increment;
				hsl1 += l7;
				hsl3 += j8;
				y3 += Rasterizer2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--y1 >= 0) {
				drawMaterializedScanline(Rasterizer2D.pixels, ai, y3, x2 >> 16, x3 >> 16, hsl2 >> 7, hsl3 >> 7, l4, k5, j6, i5, l5, k6, z3, depth_slope);
				x2 += i7;
				x3 += i8;
				z3 += depth_increment;
				hsl2 += j7;
				hsl3 += j8;
				y3 += Rasterizer2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		y1 -= y2;
		y2 -= y3;
		y3 = scanOffsets[y3];
		while (--y2 >= 0) {
			drawMaterializedScanline(Rasterizer2D.pixels, ai, y3, x3 >> 16, x1 >> 16, hsl3 >> 7, hsl1 >> 7, l4, k5, j6, i5, l5, k6, z3, depth_slope);
			x1 += k7;
			x3 += i8;
			z3 += depth_increment;
			hsl1 += l7;
			hsl3 += j8;
			y3 += Rasterizer2D.width;
			l4 += j5;
			k5 += i6;
			j6 += l6;
		}
		while (--y1 >= 0) {
			drawMaterializedScanline(Rasterizer2D.pixels, ai, y3, x3 >> 16, x2 >> 16, hsl3 >> 7, hsl2 >> 7, l4, k5, j6, i5, l5, k6, z3, depth_slope);
			x2 += i7;
			x3 += i8;
			z3 += depth_increment;
			hsl2 += j7;
			hsl3 += j8;
			y3 += Rasterizer2D.width;
			l4 += j5;
			k5 += i6;
			j6 += l6;
		}
	}

	private static void drawMaterializedScanline(int dst[], int src[], int off, int x1, int x2, int hsl1, int hsl2, int l1, int i2, int j2, int k2, int l2, int i3, float depth, float depth_slope) {
		int i = 0;
		int j = 0;
		if (x1 >= x2) {
			return;
		}
		int k3;
		hsl2 = (hsl2 - hsl1) / (x2 - x1);
		if (textureOutOfDrawingBounds) {
			if (x2 > Rasterizer2D.lastX) {
				x2 = Rasterizer2D.lastX;
			}
			if (x1 < 0) {
				hsl1 -= x1 * hsl2;
				x1 = 0;
			}
			if (x1 >= x2) {
				return;
			}
			k3 = x2 - x1 >> 3;
		} else {
			if (x2 - x1 > 7) {
				k3 = x2 - x1 >> 3;
			} else {
				k3 = 0;
			}
		}
		off += x1;
		depth += depth_slope * (float) x1;
		int j4 = 0;
		int l4 = 0;
		int l6 = x1 - originViewX;
		l1 += (k2 >> 3) * l6;
		i2 += (l2 >> 3) * l6;
		j2 += (i3 >> 3) * l6;
		int l5 = j2 >> 14;
		if (l5 != 0) {
			i = l1 / l5;
			j = i2 / l5;
			if (i < 0) {
				i = 0;
			} else if (i > 16256) {
				i = 16256;
			}
		}
		l1 += k2;
		i2 += l2;
		j2 += i3;
		l5 = j2 >> 14;
		if (l5 != 0) {
			j4 = l1 / l5;
			l4 = i2 / l5;
			if (j4 < 7) {
				j4 = 7;
			} else if (j4 > 16256) {
				j4 = 16256;
			}
		}
		int j7 = j4 - i >> 3;
		int l7 = l4 - j >> 3;
		int rgb1, rgb2;
		while (k3-- > 0) {
			rgb1 = hslToRgb[hsl1 >> 8];
			rgb2 = src[texelPos((j & 0x3f80) + (i >> 7))];
			if (true) {
				dst[off] = (((rgb1 >> 16 & 0xff) * (rgb2 >> 17 & 0x7f) << 11) / 3 & 0xff0000) + (((rgb1 >> 8 & 0xff) * (rgb2 >> 9 & 0x7f) << 3) / 3 & 0xff00) + (((rgb1 & 0xff) * (rgb2 >> 1 & 0x7f) >> 5) / 3 & 0xff);
				Rasterizer2D.depthBuffer[off] = depth;
			}
			off++;
			depth += depth_slope;
			i += j7;
			j += l7;
			hsl1 += hsl2;
			rgb1 = hslToRgb[hsl1 >> 8];
			rgb2 = src[texelPos((j & 0x3f80) + (i >> 7))];
			if (true) {
				dst[off] = (((rgb1 >> 16 & 0xff) * (rgb2 >> 17 & 0x7f) << 11) / 3 & 0xff0000) + (((rgb1 >> 8 & 0xff) * (rgb2 >> 9 & 0x7f) << 3) / 3 & 0xff00) + (((rgb1 & 0xff) * (rgb2 >> 1 & 0x7f) >> 5) / 3 & 0xff);
				Rasterizer2D.depthBuffer[off] = depth;
			}
			off++;
			depth += depth_slope;
			i += j7;
			j += l7;
			hsl1 += hsl2;
			rgb1 = hslToRgb[hsl1 >> 8];
			rgb2 = src[texelPos((j & 0x3f80) + (i >> 7))];
			if (true) {
				dst[off] = (((rgb1 >> 16 & 0xff) * (rgb2 >> 17 & 0x7f) << 11) / 3 & 0xff0000) + (((rgb1 >> 8 & 0xff) * (rgb2 >> 9 & 0x7f) << 3) / 3 & 0xff00) + (((rgb1 & 0xff) * (rgb2 >> 1 & 0x7f) >> 5) / 3 & 0xff);
				Rasterizer2D.depthBuffer[off] = depth;
			}
			off++;
			depth += depth_slope;
			i += j7;
			j += l7;
			hsl1 += hsl2;
			rgb1 = hslToRgb[hsl1 >> 8];
			rgb2 = src[texelPos((j & 0x3f80) + (i >> 7))];
			if (true) {
				dst[off] = (((rgb1 >> 16 & 0xff) * (rgb2 >> 17 & 0x7f) << 11) / 3 & 0xff0000) + (((rgb1 >> 8 & 0xff) * (rgb2 >> 9 & 0x7f) << 3) / 3 & 0xff00) + (((rgb1 & 0xff) * (rgb2 >> 1 & 0x7f) >> 5) / 3 & 0xff);
				Rasterizer2D.depthBuffer[off] = depth;
			}
			off++;
			depth += depth_slope;
			i += j7;
			j += l7;
			hsl1 += hsl2;
			rgb1 = hslToRgb[hsl1 >> 8];
			rgb2 = src[texelPos((j & 0x3f80) + (i >> 7))];
			if (true) {
				dst[off] = (((rgb1 >> 16 & 0xff) * (rgb2 >> 17 & 0x7f) << 11) / 3 & 0xff0000) + (((rgb1 >> 8 & 0xff) * (rgb2 >> 9 & 0x7f) << 3) / 3 & 0xff00) + (((rgb1 & 0xff) * (rgb2 >> 1 & 0x7f) >> 5) / 3 & 0xff);
				Rasterizer2D.depthBuffer[off] = depth;
			}
			off++;
			depth += depth_slope;
			i += j7;
			j += l7;
			hsl1 += hsl2;
			rgb1 = hslToRgb[hsl1 >> 8];
			rgb2 = src[texelPos((j & 0x3f80) + (i >> 7))];
			if (true) {
				dst[off] = (((rgb1 >> 16 & 0xff) * (rgb2 >> 17 & 0x7f) << 11) / 3 & 0xff0000) + (((rgb1 >> 8 & 0xff) * (rgb2 >> 9 & 0x7f) << 3) / 3 & 0xff00) + (((rgb1 & 0xff) * (rgb2 >> 1 & 0x7f) >> 5) / 3 & 0xff);
				Rasterizer2D.depthBuffer[off] = depth;
			}
			off++;
			depth += depth_slope;
			i += j7;
			j += l7;
			hsl1 += hsl2;
			rgb1 = hslToRgb[hsl1 >> 8];
			rgb2 = src[texelPos((j & 0x3f80) + (i >> 7))];
			if (true) {
				dst[off] = (((rgb1 >> 16 & 0xff) * (rgb2 >> 17 & 0x7f) << 11) / 3 & 0xff0000) + (((rgb1 >> 8 & 0xff) * (rgb2 >> 9 & 0x7f) << 3) / 3 & 0xff00) + (((rgb1 & 0xff) * (rgb2 >> 1 & 0x7f) >> 5) / 3 & 0xff);
				Rasterizer2D.depthBuffer[off] = depth;
			}
			off++;
			depth += depth_slope;
			i += j7;
			j += l7;
			hsl1 += hsl2;
			rgb1 = hslToRgb[hsl1 >> 8];
			rgb2 = src[texelPos((j & 0x3f80) + (i >> 7))];
			if (true) {
				dst[off] = (((rgb1 >> 16 & 0xff) * (rgb2 >> 17 & 0x7f) << 11) / 3 & 0xff0000) + (((rgb1 >> 8 & 0xff) * (rgb2 >> 9 & 0x7f) << 3) / 3 & 0xff00) + (((rgb1 & 0xff) * (rgb2 >> 1 & 0x7f) >> 5) / 3 & 0xff);
				Rasterizer2D.depthBuffer[off] = depth;
			}
			off++;
			depth += depth_slope;
			i = j4;
			j = l4;
			hsl1 += hsl2;
			l1 += k2;
			i2 += l2;
			j2 += i3;
			int i6 = j2 >> 14;
			if (i6 != 0) {
				j4 = l1 / i6;
				l4 = i2 / i6;
				if (j4 < 7) {
					j4 = 7;
				} else if (j4 > 16256) {
					j4 = 16256;
				}
			}
			j7 = j4 - i >> 3;
			l7 = l4 - j >> 3;
		}
		for (k3 = x2 - x1 & 7; k3-- > 0;) {
			rgb1 = hslToRgb[hsl1 >> 8];
			rgb2 = src[texelPos((j & 0x3f80) + (i >> 7))];
			if (true) {
				dst[off] = (((rgb1 >> 16 & 0xff) * (rgb2 >> 17 & 0x7f) << 11) / 3 & 0xff0000) + (((rgb1 >> 8 & 0xff) * (rgb2 >> 9 & 0x7f) << 3) / 3 & 0xff00) + (((rgb1 & 0xff) * (rgb2 >> 1 & 0x7f) >> 5) / 3 & 0xff);
				Rasterizer2D.depthBuffer[off] = depth;
			}
			off++;
			depth += depth_slope;
			i += j7;
			j += l7;
			hsl1 += hsl2;
		}		
	}

	public static void drawShadedTriangle(int y1, int y2, int y3, int x1, int x2, int x3, int hsl1, int hsl2, int hsl3, float z1, float z2, float z3) {
		if (z1 < 0.0F || z2 < 0.0F || z3 < 0.0F) {
			return;
		}
		int rgb1 = hslToRgb[hsl1];
		int rgb2 = hslToRgb[hsl2];
		int rgb3 = hslToRgb[hsl3];
		int r1 = rgb1 >> 16 & 0xff;
		int g1 = rgb1 >> 8 & 0xff;
		int b1 = rgb1 & 0xff;
		int r2 = rgb2 >> 16 & 0xff;
		int g2 = rgb2 >> 8 & 0xff;
		int b2 = rgb2 & 0xff;
		int r3 = rgb3 >> 16 & 0xff;
		int g3 = rgb3 >> 8 & 0xff;
		int b3 = rgb3 & 0xff;
		int a_to_b = 0;
		int dr1 = 0;
		int dg1 = 0;
		int db1 = 0;
		if (y2 != y1) {
			a_to_b = (x2 - x1 << 16) / (y2 - y1);
			dr1 = (r2 - r1 << 16) / (y2 - y1);
			dg1 = (g2 - g1 << 16) / (y2 - y1);
			db1 = (b2 - b1 << 16) / (y2 - y1);
		}
		int b_to_c = 0;
		int dr2 = 0;
		int dg2 = 0;
		int db2 = 0;
		if (y3 != y2) {
			b_to_c = (x3 - x2 << 16) / (y3 - y2);
			dr2 = (r3 - r2 << 16) / (y3 - y2);
			dg2 = (g3 - g2 << 16) / (y3 - y2);
			db2 = (b3 - b2 << 16) / (y3 - y2);
		}
		int c_to_a = 0;
		int dr3 = 0;
		int dg3 = 0;
		int db3 = 0;
		if (y3 != y1) {
			c_to_a = (x1 - x3 << 16) / (y1 - y3);
			dr3 = (r1 - r3 << 16) / (y1 - y3);
			dg3 = (g1 - g3 << 16) / (y1 - y3);
			db3 = (b1 - b3 << 16) / (y1 - y3);
		}
		float b_aX = x2 - x1;
		float b_aY = y2 - y1;
		float c_aX = x3 - x1;
		float c_aY = y3 - y1;
		float b_aZ = z2 - z1;
		float c_aZ = z3 - z1;

		float div = b_aX * c_aY - c_aX * b_aY;
		float depth_slope = (b_aZ * c_aY - c_aZ * b_aY) / div;
		float depth_increment = (c_aZ * b_aX - b_aZ * c_aX) / div;
		if (y1 <= y2 && y1 <= y3) {
			if (y1 >= Rasterizer2D.clip_bottom) {
				return;
			}
			if (y2 > Rasterizer2D.clip_bottom) {
				y2 = Rasterizer2D.clip_bottom;
			}
			if (y3 > Rasterizer2D.clip_bottom) {
				y3 = Rasterizer2D.clip_bottom;
			}
			z1 = z1 - depth_slope * x1 + depth_slope;
			if (y2 < y3) {
				x3 = x1 <<= 16;
				r3 = r1 <<= 16;
				g3 = g1 <<= 16;
				b3 = b1 <<= 16;
				if (y1 < 0) {
					x3 -= c_to_a * y1;
					x1 -= a_to_b * y1;
					r3 -= dr3 * y1;
					g3 -= dg3 * y1;
					b3 -= db3 * y1;
					r1 -= dr1 * y1;
					g1 -= dg1 * y1;
					b1 -= db1 * y1;
					z1 -= depth_increment * y1;
					y1 = 0;
				}
				x2 <<= 16;
				r2 <<= 16;
				g2 <<= 16;
				b2 <<= 16;
				if (y2 < 0) {
					x2 -= b_to_c * y2;
					r2 -= dr2 * y2;
					g2 -= dg2 * y2;
					b2 -= db2 * y2;
					y2 = 0;
				}
				if (y1 != y2 && c_to_a < a_to_b || y1 == y2 && c_to_a > b_to_c) {
					y3 -= y2;
					y2 -= y1;
					for (y1 = scanOffsets[y1]; --y2 >= 0; y1 += Rasterizer2D.width) {
						method375(Rasterizer2D.pixels, y1, x3 >> 16, x1 >> 16, r3, g3, b3, r1, g1, b1, z1, depth_slope);
						x3 += c_to_a;
						x1 += a_to_b;
						r3 += dr3;
						g3 += dg3;
						b3 += db3;
						r1 += dr1;
						g1 += dg1;
						b1 += db1;
						z1 += depth_increment;
					}
					while (--y3 >= 0) {
						method375(Rasterizer2D.pixels, y1, x3 >> 16, x2 >> 16, r3, g3, b3, r2, g2, b2, z1, depth_slope);
						x3 += c_to_a;
						x2 += b_to_c;
						r3 += dr3;
						g3 += dg3;
						b3 += db3;
						r2 += dr2;
						g2 += dg2;
						b2 += db2;
						y1 += Rasterizer2D.width;
						z1 += depth_increment;
					}
					return;
				}
				y3 -= y2;
				y2 -= y1;
				for (y1 = scanOffsets[y1]; --y2 >= 0; y1 += Rasterizer2D.width) {
					method375(Rasterizer2D.pixels, y1, x1 >> 16, x3 >> 16, r1, g1, b1, r3, g3, b3, z1, depth_slope);
					x3 += c_to_a;
					x1 += a_to_b;
					r3 += dr3;
					g3 += dg3;
					b3 += db3;
					r1 += dr1;
					g1 += dg1;
					b1 += db1;
					z1 += depth_increment;
				}
				while (--y3 >= 0) {
					method375(Rasterizer2D.pixels, y1, x2 >> 16, x3 >> 16, r2, g2, b2, r3, g3, b3, z1, depth_slope);
					x3 += c_to_a;
					x2 += b_to_c;
					r3 += dr3;
					g3 += dg3;
					b3 += db3;
					r2 += dr2;
					g2 += dg2;
					b2 += db2;
					y1 += Rasterizer2D.width;
					z1 += depth_increment;
				}
				return;
			}
			x2 = x1 <<= 16;
			r2 = r1 <<= 16;
			g2 = g1 <<= 16;
			b2 = b1 <<= 16;
			if (y1 < 0) {
				x2 -= c_to_a * y1;
				x1 -= a_to_b * y1;
				r2 -= dr3 * y1;
				g2 -= dg3 * y1;
				b2 -= db3 * y1;
				r1 -= dr1 * y1;
				g1 -= dg1 * y1;
				b1 -= db1 * y1;
				z1 -= depth_increment * y1;
				y1 = 0;
			}
			x3 <<= 16;
			r3 <<= 16;
			g3 <<= 16;
			b3 <<= 16;
			if (y3 < 0) {
				x3 -= b_to_c * y3;
				r3 -= dr2 * y3;
				g3 -= dg2 * y3;
				b3 -= db2 * y3;
				y3 = 0;
			}
			if (y1 != y3 && c_to_a < a_to_b || y1 == y3 && b_to_c > a_to_b) {
				y2 -= y3;
				y3 -= y1;
				for (y1 = scanOffsets[y1]; --y3 >= 0; y1 += Rasterizer2D.width) {
					method375(Rasterizer2D.pixels, y1, x2 >> 16, x1 >> 16, r2, g2, b2, r1, g1, b1, z1, depth_slope);
					x2 += c_to_a;
					x1 += a_to_b;
					r2 += dr3;
					g2 += dg3;
					b2 += db3;
					r1 += dr1;
					g1 += dg1;
					b1 += db1;
					z1 += depth_increment;
				}
				while (--y2 >= 0) {
					method375(Rasterizer2D.pixels, y1, x3 >> 16, x1 >> 16, r3, g3, b3, r1, g1, b1, z1, depth_slope);
					x3 += b_to_c;
					x1 += a_to_b;
					r3 += dr2;
					g3 += dg2;
					b3 += db2;
					r1 += dr1;
					g1 += dg1;
					b1 += db1;
					y1 += Rasterizer2D.width;
					z1 += depth_increment;
				}
				return;
			}
			y2 -= y3;
			y3 -= y1;
			for (y1 = scanOffsets[y1]; --y3 >= 0; y1 += Rasterizer2D.width) {
				method375(Rasterizer2D.pixels, y1, x1 >> 16, x2 >> 16, r1, g1, b1, r2, g2, b2, z1, depth_slope);
				x2 += c_to_a;
				x1 += a_to_b;
				r2 += dr3;
				g2 += dg3;
				b2 += db3;
				r1 += dr1;
				g1 += dg1;
				b1 += db1;
				z1 += depth_increment;
			}
			while (--y2 >= 0) {
				method375(Rasterizer2D.pixels, y1, x1 >> 16, x3 >> 16, r1, g1, b1, r3, g3, b3, z1, depth_slope);
				x3 += b_to_c;
				x1 += a_to_b;
				r3 += dr2;
				g3 += dg2;
				b3 += db2;
				r1 += dr1;
				g1 += dg1;
				b1 += db1;
				y1 += Rasterizer2D.width;
				z1 += depth_increment;
			}
			return;
		}
		if (y2 <= y3) {
			if (y2 >= Rasterizer2D.clip_bottom) {
				return;
			}
			if (y3 > Rasterizer2D.clip_bottom) {
				y3 = Rasterizer2D.clip_bottom;
			}
			if (y1 > Rasterizer2D.clip_bottom) {
				y1 = Rasterizer2D.clip_bottom;
			}
			z2 = z2 - depth_slope * x2 + depth_slope;
			if (y3 < y1) {
				x1 = x2 <<= 16;
				r1 = r2 <<= 16;
				g1 = g2 <<= 16;
				b1 = b2 <<= 16;
				if (y2 < 0) {
					x1 -= a_to_b * y2;
					x2 -= b_to_c * y2;
					r1 -= dr1 * y2;
					g1 -= dg1 * y2;
					b1 -= db1 * y2;
					r2 -= dr2 * y2;
					g2 -= dg2 * y2;
					b2 -= db2 * y2;
					z2 -= depth_increment * y2;
					y2 = 0;
				}
				x3 <<= 16;
				r3 <<= 16;
				g3 <<= 16;
				b3 <<= 16;
				if (y3 < 0) {
					x3 -= c_to_a * y3;
					r3 -= dr3 * y3;
					g3 -= dg3 * y3;
					b3 -= db3 * y3;
					y3 = 0;
				}
				if (y2 != y3 && a_to_b < b_to_c || y2 == y3 && a_to_b > c_to_a) {
					y1 -= y3;
					y3 -= y2;
					for (y2 = scanOffsets[y2]; --y3 >= 0; y2 += Rasterizer2D.width) {
						method375(Rasterizer2D.pixels, y2, x1 >> 16, x2 >> 16, r1, g1, b1, r2, g2, b2, z2, depth_slope);
						x1 += a_to_b;
						x2 += b_to_c;
						r1 += dr1;
						g1 += dg1;
						b1 += db1;
						r2 += dr2;
						g2 += dg2;
						b2 += db2;
						z2 += depth_increment;
					}
					while (--y1 >= 0) {
						method375(Rasterizer2D.pixels, y2, x1 >> 16, x3 >> 16, r1, g1, b1, r3, g3, b3, z2, depth_slope);
						x1 += a_to_b;
						x3 += c_to_a;
						r1 += dr1;
						g1 += dg1;
						b1 += db1;
						r3 += dr3;
						g3 += dg3;
						b3 += db3;
						y2 += Rasterizer2D.width;
						z2 += depth_increment;
					}
					return;
				}
				y1 -= y3;
				y3 -= y2;
				for (y2 = scanOffsets[y2]; --y3 >= 0; y2 += Rasterizer2D.width) {
					method375(Rasterizer2D.pixels, y2, x2 >> 16, x1 >> 16, r2, g2, b2, r1, g1, b1, z2, depth_slope);
					x1 += a_to_b;
					x2 += b_to_c;
					r1 += dr1;
					g1 += dg1;
					b1 += db1;
					r2 += dr2;
					g2 += dg2;
					b2 += db2;
					z2 += depth_increment;
				}
				while (--y1 >= 0) {
					method375(Rasterizer2D.pixels, y2, x3 >> 16, x1 >> 16, r3, g3, b3, r1, g1, b1, z2, depth_slope);
					x1 += a_to_b;
					x3 += c_to_a;
					r1 += dr1;
					g1 += dg1;
					b1 += db1;
					r3 += dr3;
					g3 += dg3;
					b3 += db3;
					y2 += Rasterizer2D.width;
					z2 += depth_increment;
				}
				return;
			}
			x3 = x2 <<= 16;
			r3 = r2 <<= 16;
			g3 = g2 <<= 16;
			b3 = b2 <<= 16;
			if (y2 < 0) {
				x3 -= a_to_b * y2;
				x2 -= b_to_c * y2;
				r3 -= dr1 * y2;
				g3 -= dg1 * y2;
				b3 -= db1 * y2;
				r2 -= dr2 * y2;
				g2 -= dg2 * y2;
				b2 -= db2 * y2;
				z2 -= depth_increment * y2;
				y2 = 0;
			}
			x1 <<= 16;
			r1 <<= 16;
			g1 <<= 16;
			b1 <<= 16;
			if (y1 < 0) {
				x1 -= c_to_a * y1;
				r1 -= dr3 * y1;
				g1 -= dg3 * y1;
				b1 -= db3 * y1;
				y1 = 0;
			}
			if (a_to_b < b_to_c) {
				y3 -= y1;
				y1 -= y2;
				for (y2 = scanOffsets[y2]; --y1 >= 0; y2 += Rasterizer2D.width) {
					method375(Rasterizer2D.pixels, y2, x3 >> 16, x2 >> 16, r3, g3, b3, r2, g2, b2, z2, depth_slope);
					x3 += a_to_b;
					x2 += b_to_c;
					r3 += dr1;
					g3 += dg1;
					b3 += db1;
					r2 += dr2;
					g2 += dg2;
					b2 += db2;
					z2 += depth_increment;
				}
				while (--y3 >= 0) {
					method375(Rasterizer2D.pixels, y2, x1 >> 16, x2 >> 16, r1, g1, b1, r2, g2, b2, z2, depth_slope);
					x1 += c_to_a;
					x2 += b_to_c;
					r1 += dr3;
					g1 += dg3;
					b1 += db3;
					r2 += dr2;
					g2 += dg2;
					b2 += db2;
					y2 += Rasterizer2D.width;
					z2 += depth_increment;
				}
				return;
			}
			y3 -= y1;
			y1 -= y2;
			for (y2 = scanOffsets[y2]; --y1 >= 0; y2 += Rasterizer2D.width) {
				method375(Rasterizer2D.pixels, y2, x2 >> 16, x3 >> 16, r2, g2, b2, r3, g3, b3, z2, depth_slope);
				x3 += a_to_b;
				x2 += b_to_c;
				r3 += dr1;
				g3 += dg1;
				b3 += db1;
				r2 += dr2;
				g2 += dg2;
				b2 += db2;
				z2 += depth_increment;
			}
			while (--y3 >= 0) {
				method375(Rasterizer2D.pixels, y2, x2 >> 16, x1 >> 16, r2, g2, b2, r1, g1, b1, z2, depth_slope);
				x1 += c_to_a;
				x2 += b_to_c;
				r1 += dr3;
				g1 += dg3;
				b1 += db3;
				r2 += dr2;
				g2 += dg2;
				b2 += db2;
				y2 += Rasterizer2D.width;
				z2 += depth_increment;
			}
			return;
		}
		if (y3 >= Rasterizer2D.clip_bottom) {
			return;
		}
		if (y1 > Rasterizer2D.clip_bottom) {
			y1 = Rasterizer2D.clip_bottom;
		}
		if (y2 > Rasterizer2D.clip_bottom) {
			y2 = Rasterizer2D.clip_bottom;
		}
		z3 = z3 - depth_slope * x3 + depth_slope;
		if (y1 < y2) {
			x2 = x3 <<= 16;
			r2 = r3 <<= 16;
			g2 = g3 <<= 16;
			b2 = b3 <<= 16;
			if (y3 < 0) {
				x2 -= b_to_c * y3;
				x3 -= c_to_a * y3;
				r2 -= dr2 * y3;
				g2 -= dg2 * y3;
				b2 -= db2 * y3;
				r3 -= dr3 * y3;
				g3 -= dg3 * y3;
				b3 -= db3 * y3;
				z3 -= depth_increment * y3;
				y3 = 0;
			}
			x1 <<= 16;
			r1 <<= 16;
			g1 <<= 16;
			b1 <<= 16;
			if (y1 < 0) {
				x1 -= a_to_b * y1;
				r1 -= dr1 * y1;
				g1 -= dg1 * y1;
				b1 -= db1 * y1;
				y1 = 0;
			}
			if (b_to_c < c_to_a) {
				y2 -= y1;
				y1 -= y3;
				for (y3 = scanOffsets[y3]; --y1 >= 0; y3 += Rasterizer2D.width) {
					method375(Rasterizer2D.pixels, y3, x2 >> 16, x3 >> 16, r2, g2, b2, r3, g3, b3, z3, depth_slope);
					x2 += b_to_c;
					x3 += c_to_a;
					r2 += dr2;
					g2 += dg2;
					b2 += db2;
					r3 += dr3;
					g3 += dg3;
					b3 += db3;
					z3 += depth_increment;
				}
				while (--y2 >= 0) {
					method375(Rasterizer2D.pixels, y3, x2 >> 16, x1 >> 16, r2, g2, b2, r1, g1, b1, z3, depth_slope);
					x2 += b_to_c;
					x1 += a_to_b;
					r2 += dr2;
					g2 += dg2;
					b2 += db2;
					r1 += dr1;
					g1 += dg1;
					b1 += db1;
					y3 += Rasterizer2D.width;
					z3 += depth_increment;
				}
				return;
			}
			y2 -= y1;
			y1 -= y3;
			for (y3 = scanOffsets[y3]; --y1 >= 0; y3 += Rasterizer2D.width) {
				method375(Rasterizer2D.pixels, y3, x3 >> 16, x2 >> 16, r3, g3, b3, r2, g2, b2, z3, depth_slope);
				x2 += b_to_c;
				x3 += c_to_a;
				r2 += dr2;
				g2 += dg2;
				b2 += db2;
				r3 += dr3;
				g3 += dg3;
				b3 += db3;
				z3 += depth_increment;
			}
			while (--y2 >= 0) {
				method375(Rasterizer2D.pixels, y3, x1 >> 16, x2 >> 16, r1, g1, b1, r2, g2, b2, z3, depth_slope);
				x2 += b_to_c;
				x1 += a_to_b;
				r2 += dr2;
				g2 += dg2;
				b2 += db2;
				r1 += dr1;
				g1 += dg1;
				b1 += db1;
				z3 += depth_increment;
				y3 += Rasterizer2D.width;
			}
			return;
		}
		x1 = x3 <<= 16;
		r1 = r3 <<= 16;
		g1 = g3 <<= 16;
		b1 = b3 <<= 16;
		if (y3 < 0) {
			x1 -= b_to_c * y3;
			x3 -= c_to_a * y3;
			r1 -= dr2 * y3;
			g1 -= dg2 * y3;
			b1 -= db2 * y3;
			r3 -= dr3 * y3;
			g3 -= dg3 * y3;
			b3 -= db3 * y3;
			z3 -= depth_increment * y3;
			y3 = 0;
		}
		x2 <<= 16;
		r2 <<= 16;
		g2 <<= 16;
		b2 <<= 16;
		if (y2 < 0) {
			x2 -= a_to_b * y2;
			r2 -= dr1 * y2;
			g2 -= dg1 * y2;
			b2 -= db1 * y2;
			y2 = 0;
		}
		if (b_to_c < c_to_a) {
			y1 -= y2;
			y2 -= y3;
			for (y3 = scanOffsets[y3]; --y2 >= 0; y3 += Rasterizer2D.width) {
				method375(Rasterizer2D.pixels, y3, x1 >> 16, x3 >> 16, r1, g1, b1, r3, g3, b3, z3, depth_slope);
				x1 += b_to_c;
				x3 += c_to_a;
				r1 += dr2;
				g1 += dg2;
				b1 += db2;
				r3 += dr3;
				g3 += dg3;
				b3 += db3;
				z3 += depth_increment;
			}
			while (--y1 >= 0) {
				method375(Rasterizer2D.pixels, y3, x2 >> 16, x3 >> 16, r2, g2, b2, r3, g3, b3, z3, depth_slope);
				x2 += a_to_b;
				x3 += c_to_a;
				r2 += dr1;
				g2 += dg1;
				b2 += db1;
				r3 += dr3;
				g3 += dg3;
				b3 += db3;
				z3 += depth_increment;
				y3 += Rasterizer2D.width;
			}
			return;
		}
		y1 -= y2;
		y2 -= y3;
		for (y3 = scanOffsets[y3]; --y2 >= 0; y3 += Rasterizer2D.width) {
			method375(Rasterizer2D.pixels, y3, x3 >> 16, x1 >> 16, r3, g3, b3, r1, g1, b1, z3, depth_slope);
			x1 += b_to_c;
			x3 += c_to_a;
			r1 += dr2;
			g1 += dg2;
			b1 += db2;
			r3 += dr3;
			g3 += dg3;
			b3 += db3;
			z3 += depth_increment;
		}
		while (--y1 >= 0) {
			method375(Rasterizer2D.pixels, y3, x3 >> 16, x2 >> 16, r3, g3, b3, r2, g2, b2, z3, depth_slope);
			x2 += a_to_b;
			x3 += c_to_a;
			r2 += dr1;
			g2 += dg1;
			b2 += db1;
			r3 += dr3;
			g3 += dg3;
			b3 += db3;
			y3 += Rasterizer2D.width;
			z3 += depth_increment;
		}
	}
	
	public static void method375(int[] dest, int offset, int x1, int x2, int r1, int g1, int b1, int r2, int g2, int b2, float depth, float depth_slope) {
		int n = x2 - x1;
		if (n <= 0) {
			return;
		}
		r2 = (r2 - r1) / n;
		g2 = (g2 - g1) / n;
		b2 = (b2 - b1) / n;
		if (textureOutOfDrawingBounds) {
			if (x2 > Rasterizer2D.lastX) {
				n -= x2 - Rasterizer2D.lastX;
				x2 = Rasterizer2D.lastX;
			}
			if (x1 < 0) {
				n = x2;
				r1 -= x1 * r2;
				g1 -= x1 * g2;
				b1 -= x1 * b2;
				x1 = 0;
			}
		}
		if (x1 < x2) {
			offset += x1;
			depth += depth_slope * (float) x1;
			if (alpha == 0) {
				while (--n >= 0) {
					if (true) {
						dest[offset] = (r1 & 0xff0000) | (g1 >> 8 & 0xff00) | (b1 >> 16 & 0xff);
						Rasterizer2D.depthBuffer[offset] = depth;
					}
					depth += depth_slope;
					r1 += r2;
					g1 += g2;
					b1 += b2;
					offset++;
				}
			} else {
				final int a1 = alpha;
				final int a2 = 256 - alpha;
				int rgb;
				int dst;
				while (--n >= 0) {
					rgb = (r1 & 0xff0000) | (g1 >> 8 & 0xff00) | (b1 >> 16 & 0xff);
					rgb = ((rgb & 0xff00ff) * a2 >> 8 & 0xff00ff) + ((rgb & 0xff00) * a2 >> 8 & 0xff00);
					dst = dest[offset];
					if (true) {
						dest[offset] = rgb + ((dst & 0xff00ff) * a1 >> 8 & 0xff00ff) + ((dst & 0xff00) * a1 >> 8 & 0xff00);
						Rasterizer2D.depthBuffer[offset] = depth;
					}
					depth += depth_slope;
					r1 += r2;
					g1 += g2;
					b1 += b2;
					offset++;
				}
			}
		}
	}

	public static void drawFlatTriangle(int y1, int y2, int y3, int x1, int x2, int x3, int k1, float z1, float z2, float z3) {
		if (z1 < 0.0F || z2 < 0.0F || z3 < 0.0F) {
			return;
		}
		int a_to_b = 0;
		if (y2 != y1) {
			a_to_b = (x2 - x1 << 16) / (y2 - y1);
		}
		int b_to_c = 0;
		if (y3 != y2) {
			b_to_c = (x3 - x2 << 16) / (y3 - y2);
		}
		int c_to_a = 0;
		if (y3 != y1) {
			c_to_a = (x1 - x3 << 16) / (y1 - y3);
		}
		float b_aX = x2 - x1;
		float b_aY = y2 - y1;
		float c_aX = x3 - x1;
		float c_aY = y3 - y1;
		float b_aZ = z2 - z1;
		float c_aZ = z3 - z1;

		float div = b_aX * c_aY - c_aX * b_aY;
		float depth_slope = (b_aZ * c_aY - c_aZ * b_aY) / div;
		float depth_increment = (c_aZ * b_aX - b_aZ * c_aX) / div;
		if (y1 <= y2 && y1 <= y3) {
			if (y1 >= Rasterizer2D.clip_bottom)
				return;
			if (y2 > Rasterizer2D.clip_bottom)
				y2 = Rasterizer2D.clip_bottom;
			if (y3 > Rasterizer2D.clip_bottom)
				y3 = Rasterizer2D.clip_bottom;
			z1 = z1 - depth_slope * x1 + depth_slope;
			if (y2 < y3) {
				x3 = x1 <<= 16;
				if (y1 < 0) {
					x3 -= c_to_a * y1;
					x1 -= a_to_b * y1;
					z1 -= depth_increment * y1;
					y1 = 0;
				}
				x2 <<= 16;
				if (y2 < 0) {
					x2 -= b_to_c * y2;
					y2 = 0;
				}
				if (y1 != y2 && c_to_a < a_to_b || y1 == y2 && c_to_a > b_to_c) {
					y3 -= y2;
					y2 -= y1;
					for (y1 = scanOffsets[y1]; --y2 >= 0; y1 += Rasterizer2D.width) {
						method377(Rasterizer2D.pixels, y1, k1, x3 >> 16, x1 >> 16, z1, depth_slope);
						x3 += c_to_a;
						x1 += a_to_b;
						z1 += depth_increment;
					}

					while (--y3 >= 0) {
						method377(Rasterizer2D.pixels, y1, k1, x3 >> 16, x2 >> 16, z1, depth_slope);
						x3 += c_to_a;
						x2 += b_to_c;
						y1 += Rasterizer2D.width;
						z1 += depth_increment;
					}
					return;
				}
				y3 -= y2;
				y2 -= y1;
				for (y1 = scanOffsets[y1]; --y2 >= 0; y1 += Rasterizer2D.width) {
					method377(Rasterizer2D.pixels, y1, k1, x1 >> 16, x3 >> 16, z1, depth_slope);
					x3 += c_to_a;
					x1 += a_to_b;
					z1 += depth_increment;
				}

				while (--y3 >= 0) {
					method377(Rasterizer2D.pixels, y1, k1, x2 >> 16, x3 >> 16, z1, depth_slope);
					x3 += c_to_a;
					x2 += b_to_c;
					y1 += Rasterizer2D.width;
					z1 += depth_increment;
				}
				return;
			}
			x2 = x1 <<= 16;
			if (y1 < 0) {
				x2 -= c_to_a * y1;
				x1 -= a_to_b * y1;
				z1 -= depth_increment * y1;
				y1 = 0;

			}
			x3 <<= 16;
			if (y3 < 0) {
				x3 -= b_to_c * y3;
				y3 = 0;
			}
			if (y1 != y3 && c_to_a < a_to_b || y1 == y3 && b_to_c > a_to_b) {
				y2 -= y3;
				y3 -= y1;
				for (y1 = scanOffsets[y1]; --y3 >= 0; y1 += Rasterizer2D.width) {
					method377(Rasterizer2D.pixels, y1, k1, x2 >> 16, x1 >> 16, z1, depth_slope);
					z1 += depth_increment;
					x2 += c_to_a;
					x1 += a_to_b;
				}

				while (--y2 >= 0) {
					method377(Rasterizer2D.pixels, y1, k1, x3 >> 16, x1 >> 16, z1, depth_slope);
					z1 += depth_increment;
					x3 += b_to_c;
					x1 += a_to_b;
					y1 += Rasterizer2D.width;
				}
				return;
			}
			y2 -= y3;
			y3 -= y1;
			for (y1 = scanOffsets[y1]; --y3 >= 0; y1 += Rasterizer2D.width) {
				method377(Rasterizer2D.pixels, y1, k1, x1 >> 16, x2 >> 16, z1, depth_slope);
				z1 += depth_increment;
				x2 += c_to_a;
				x1 += a_to_b;
			}

			while (--y2 >= 0) {
				method377(Rasterizer2D.pixels, y1, k1, x1 >> 16, x3 >> 16, z1, depth_slope);
				z1 += depth_increment;
				x3 += b_to_c;
				x1 += a_to_b;
				y1 += Rasterizer2D.width;
			}
			return;
		}
		if (y2 <= y3) {
			if (y2 >= Rasterizer2D.clip_bottom)
				return;
			if (y3 > Rasterizer2D.clip_bottom)
				y3 = Rasterizer2D.clip_bottom;
			if (y1 > Rasterizer2D.clip_bottom)
				y1 = Rasterizer2D.clip_bottom;
			z2 = z2 - depth_slope * x2 + depth_slope;
			if (y3 < y1) {
				x1 = x2 <<= 16;
				if (y2 < 0) {
					x1 -= a_to_b * y2;
					x2 -= b_to_c * y2;
					z2 -= depth_increment * y2;
					y2 = 0;
				}
				x3 <<= 16;
				if (y3 < 0) {
					x3 -= c_to_a * y3;
					y3 = 0;
				}
				if (y2 != y3 && a_to_b < b_to_c || y2 == y3 && a_to_b > c_to_a) {
					y1 -= y3;
					y3 -= y2;
					for (y2 = scanOffsets[y2]; --y3 >= 0; y2 += Rasterizer2D.width) {
						method377(Rasterizer2D.pixels, y2, k1, x1 >> 16, x2 >> 16, z2, depth_slope);
						z2 += depth_increment;
						x1 += a_to_b;
						x2 += b_to_c;
					}

					while (--y1 >= 0) {
						method377(Rasterizer2D.pixels, y2, k1, x1 >> 16, x3 >> 16, z2, depth_slope);
						z2 += depth_increment;
						x1 += a_to_b;
						x3 += c_to_a;
						y2 += Rasterizer2D.width;
					}
					return;
				}
				y1 -= y3;
				y3 -= y2;
				for (y2 = scanOffsets[y2]; --y3 >= 0; y2 += Rasterizer2D.width) {
					method377(Rasterizer2D.pixels, y2, k1, x2 >> 16, x1 >> 16, z2, depth_slope);
					z2 += depth_increment;
					x1 += a_to_b;
					x2 += b_to_c;
				}

				while (--y1 >= 0) {
					method377(Rasterizer2D.pixels, y2, k1, x3 >> 16, x1 >> 16, z2, depth_slope);
					z2 += depth_increment;
					x1 += a_to_b;
					x3 += c_to_a;
					y2 += Rasterizer2D.width;
				}
				return;
			}
			x3 = x2 <<= 16;
			if (y2 < 0) {
				x3 -= a_to_b * y2;
				x2 -= b_to_c * y2;
				z2 -= depth_increment * y2;
				y2 = 0;
			}
			x1 <<= 16;
			if (y1 < 0) {
				x1 -= c_to_a * y1;
				y1 = 0;
			}
			if (a_to_b < b_to_c) {
				y3 -= y1;
				y1 -= y2;
				for (y2 = scanOffsets[y2]; --y1 >= 0; y2 += Rasterizer2D.width) {
					method377(Rasterizer2D.pixels, y2, k1, x3 >> 16, x2 >> 16, z2, depth_slope);
					z2 += depth_increment;
					x3 += a_to_b;
					x2 += b_to_c;
				}

				while (--y3 >= 0) {
					method377(Rasterizer2D.pixels, y2, k1, x1 >> 16, x2 >> 16, z2, depth_slope);
					z2 += depth_increment;
					x1 += c_to_a;
					x2 += b_to_c;
					y2 += Rasterizer2D.width;
				}
				return;
			}
			y3 -= y1;
			y1 -= y2;
			for (y2 = scanOffsets[y2]; --y1 >= 0; y2 += Rasterizer2D.width) {
				method377(Rasterizer2D.pixels, y2, k1, x2 >> 16, x3 >> 16, z2, depth_slope);
				z2 += depth_increment;
				x3 += a_to_b;
				x2 += b_to_c;
			}

			while (--y3 >= 0) {
				method377(Rasterizer2D.pixels, y2, k1, x2 >> 16, x1 >> 16, z2, depth_slope);
				z2 += depth_increment;
				x1 += c_to_a;
				x2 += b_to_c;
				y2 += Rasterizer2D.width;
			}
			return;
		}
		if (y3 >= Rasterizer2D.clip_bottom)
			return;
		if (y1 > Rasterizer2D.clip_bottom)
			y1 = Rasterizer2D.clip_bottom;
		if (y2 > Rasterizer2D.clip_bottom)
			y2 = Rasterizer2D.clip_bottom;
		z3 = z3 - depth_slope * x3 + depth_slope;
		if (y1 < y2) {
			x2 = x3 <<= 16;
			if (y3 < 0) {
				x2 -= b_to_c * y3;
				x3 -= c_to_a * y3;
				z3 -= depth_increment * y3;
				y3 = 0;
			}
			x1 <<= 16;
			if (y1 < 0) {
				x1 -= a_to_b * y1;
				y1 = 0;
			}
			if (b_to_c < c_to_a) {
				y2 -= y1;
				y1 -= y3;
				for (y3 = scanOffsets[y3]; --y1 >= 0; y3 += Rasterizer2D.width) {
					method377(Rasterizer2D.pixels, y3, k1, x2 >> 16, x3 >> 16, z3, depth_slope);
					z3 += depth_increment;
					x2 += b_to_c;
					x3 += c_to_a;
				}

				while (--y2 >= 0) {
					method377(Rasterizer2D.pixels, y3, k1, x2 >> 16, x1 >> 16, z3, depth_slope);
					z3 += depth_increment;
					x2 += b_to_c;
					x1 += a_to_b;
					y3 += Rasterizer2D.width;
				}
				return;
			}
			y2 -= y1;
			y1 -= y3;
			for (y3 = scanOffsets[y3]; --y1 >= 0; y3 += Rasterizer2D.width) {
				method377(Rasterizer2D.pixels, y3, k1, x3 >> 16, x2 >> 16, z3, depth_slope);
				z3 += depth_increment;
				x2 += b_to_c;
				x3 += c_to_a;
			}

			while (--y2 >= 0) {
				method377(Rasterizer2D.pixels, y3, k1, x1 >> 16, x2 >> 16, z3, depth_slope);
				z3 += depth_increment;
				x2 += b_to_c;
				x1 += a_to_b;
				y3 += Rasterizer2D.width;
			}
			return;
		}
		x1 = x3 <<= 16;
		if (y3 < 0) {
			x1 -= b_to_c * y3;
			x3 -= c_to_a * y3;
			z3 -= depth_increment * y3;
			y3 = 0;
		}
		x2 <<= 16;
		if (y2 < 0) {
			x2 -= a_to_b * y2;
			y2 = 0;
		}
		if (b_to_c < c_to_a) {
			y1 -= y2;
			y2 -= y3;
			for (y3 = scanOffsets[y3]; --y2 >= 0; y3 += Rasterizer2D.width) {
				method377(Rasterizer2D.pixels, y3, k1, x1 >> 16, x3 >> 16, z3, depth_slope);
				z3 += depth_increment;
				x1 += b_to_c;
				x3 += c_to_a;
			}

			while (--y1 >= 0) {
				method377(Rasterizer2D.pixels, y3, k1, x2 >> 16, x3 >> 16, z3, depth_slope);
				z3 += depth_increment;
				x2 += a_to_b;
				x3 += c_to_a;
				y3 += Rasterizer2D.width;
			}
			return;
		}
		y1 -= y2;
		y2 -= y3;
		for (y3 = scanOffsets[y3]; --y2 >= 0; y3 += Rasterizer2D.width) {
			method377(Rasterizer2D.pixels, y3, k1, x3 >> 16, x1 >> 16, z3, depth_slope);
			z3 += depth_increment;
			x1 += b_to_c;
			x3 += c_to_a;
		}

		while (--y1 >= 0) {
			method377(Rasterizer2D.pixels, y3, k1, x3 >> 16, x2 >> 16, z3, depth_slope);
			z3 += depth_increment;
			x2 += a_to_b;
			x3 += c_to_a;
			y3 += Rasterizer2D.width;
		}
	}

	private static void method377(int dest[], int offset, int loops, int start_x, int end_x, float depth, float depth_slope) {
		int rgb;
		if (textureOutOfDrawingBounds) {
			if (end_x > Rasterizer2D.lastX) {
				end_x = Rasterizer2D.lastX;
			}
			if (start_x < 0) {
				start_x = 0;
			}
		}
		if (start_x >= end_x) {
			return;
		}
		offset += start_x;
		rgb = end_x - start_x >> 2;
		depth += depth_slope * (float) start_x;
		if (alpha == 0) {
			while (--rgb >= 0) {
				for (int i = 0; i < 4; i++) {
					if (true) {
						dest[offset] = loops;
						Rasterizer2D.depthBuffer[offset] = depth;
					}
					offset++;
					depth += depth_slope;
				}
			}
			for (rgb = end_x - start_x & 3; --rgb >= 0;) {
				if (true) {
					dest[offset] = loops;
					Rasterizer2D.depthBuffer[offset] = depth;
				}
				offset++;
				depth += depth_slope;
			}
			return;
		}
		int dest_alpha = alpha;
		int src_alpha = 256 - alpha;
		loops = ((loops & 0xff00ff) * src_alpha >> 8 & 0xff00ff) + ((loops & 0xff00) * src_alpha >> 8 & 0xff00);
		while (--rgb >= 0) {
			for (int i = 0; i < 4; i++) {
				if (true) {
					dest[offset] = loops + ((dest[offset] & 0xff00ff) * dest_alpha >> 8 & 0xff00ff) + ((dest[offset] & 0xff00) * dest_alpha >> 8 & 0xff00);
					Rasterizer2D.depthBuffer[offset] = depth;
				}
				offset++;
				depth += depth_slope;
			}
		}
		for (rgb = end_x - start_x & 3; --rgb >= 0;) {
			if (true) {
				dest[offset] = loops + ((dest[offset] & 0xff00ff) * dest_alpha >> 8 & 0xff00ff) + ((dest[offset] & 0xff00) * dest_alpha >> 8 & 0xff00);
				Rasterizer2D.depthBuffer[offset] = depth;
			}
			offset++;
			depth += depth_slope;
		}
	}
	public static void drawTexturedTriangle(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, int var14, int var15, int var16, int var17, int var18) {
		int[] texturePixels = getTexturePixels(var18);
		int var21;
		aBoolean1463 = !textureIsTransparant[var18];
		var21 = var4 - var3;
		int var26 = var1 - var0;
		int var27 = var5 - var3;
		int var31 = var2 - var0;
		int var28 = var7 - var6;
		int var23 = var8 - var6;
		int var29 = 0;
		if(var1 != var0) {
			var29 = (var4 - var3 << 16) / (var1 - var0);
		}

		int var30 = 0;
		if(var2 != var1) {
			var30 = (var5 - var4 << 16) / (var2 - var1);
		}

		int var22 = 0;
		if(var2 != var0) {
			var22 = (var3 - var5 << 16) / (var0 - var2);
		}

		int var32 = var21 * var31 - var27 * var26;
		if(var32 != 0) {
			int var41 = (var28 * var31 - var23 * var26 << 9) / var32;
			int var20 = (var23 * var21 - var28 * var27 << 9) / var32;
			var10 = var9 - var10;
			var13 = var12 - var13;
			var16 = var15 - var16;
			var11 -= var9;
			var14 -= var12;
			var17 -= var15;
			final int FOV = (aBoolean1464 ? fieldOfView : 512);
			int var24 = var11 * var12 - var14 * var9 << 14;
			int var38 = (int)(((long)(var14 * var15 - var17 * var12) << 3 << 14) / (long)FOV);
			int var25 = (int)(((long)(var17 * var9 - var11 * var15) << 14) / (long)FOV);
			int var36 = var10 * var12 - var13 * var9 << 14;
			int var39 = (int)(((long)(var13 * var15 - var16 * var12) << 3 << 14) / (long)FOV);
			int var37 = (int)(((long)(var16 * var9 - var10 * var15) << 14) / (long)FOV);
			int var33 = var13 * var11 - var10 * var14 << 14;
			int var40 = (int)(((long)(var16 * var14 - var13 * var17) << 3 << 14) / (long)FOV);
			int var34 = (int)(((long)(var10 * var17 - var16 * var11) << 14) / (long)FOV);


			int var35;
			if(var0 <= var1 && var0 <= var2) {
				if(var0 < Rasterizer2D.clip_bottom) {
					if(var1 > Rasterizer2D.clip_bottom) {
						var1 = Rasterizer2D.clip_bottom;
					}

					if(var2 > Rasterizer2D.clip_bottom) {
						var2 = Rasterizer2D.clip_bottom;
					}

					var6 = (var6 << 9) - var41 * var3 + var41;
					if(var1 < var2) {
						var5 = var3 <<= 16;
						if(var0 < 0) {
							var5 -= var22 * var0;
							var3 -= var29 * var0;
							var6 -= var20 * var0;
							var0 = 0;
						}

						var4 <<= 16;
						if(var1 < 0) {
							var4 -= var30 * var1;
							var1 = 0;
						}

						var35 = var0 - originViewY;
						var24 += var25 * var35;
						var36 += var37 * var35;
						var33 += var34 * var35;
						if((var0 == var1 || var22 >= var29) && (var0 != var1 || var22 <= var30)) {
							var2 -= var1;
							var1 -= var0;
							var0 = scanOffsets[var0];

							while(true) {
								--var1;
								if(var1 < 0) {
									while(true) {
										--var2;
										if(var2 < 0) {
											return;
										}

										drawTexturedLine(Rasterizer2D.pixels, texturePixels, 0, 0, var0, var4 >> 16, var5 >> 16, var6, var41, var24, var36, var33, var38, var39, var40);
										var5 += var22;
										var4 += var30;
										var6 += var20;
										var0 += Rasterizer2D.width;
										var24 += var25;
										var36 += var37;
										var33 += var34;
									}
								}

								drawTexturedLine(Rasterizer2D.pixels, texturePixels, 0, 0, var0, var3 >> 16, var5 >> 16, var6, var41, var24, var36, var33, var38, var39, var40);
								var5 += var22;
								var3 += var29;
								var6 += var20;
								var0 += Rasterizer2D.width;
								var24 += var25;
								var36 += var37;
								var33 += var34;
							}
						} else {
							var2 -= var1;
							var1 -= var0;
							var0 = scanOffsets[var0];

							while(true) {
								--var1;
								if(var1 < 0) {
									while(true) {
										--var2;
										if(var2 < 0) {
											return;
										}

										drawTexturedLine(Rasterizer2D.pixels, texturePixels, 0, 0, var0, var5 >> 16, var4 >> 16, var6, var41, var24, var36, var33, var38, var39, var40);
										var5 += var22;
										var4 += var30;
										var6 += var20;
										var0 += Rasterizer2D.width;
										var24 += var25;
										var36 += var37;
										var33 += var34;
									}
								}

								drawTexturedLine(Rasterizer2D.pixels, texturePixels, 0, 0, var0, var5 >> 16, var3 >> 16, var6, var41, var24, var36, var33, var38, var39, var40);
								var5 += var22;
								var3 += var29;
								var6 += var20;
								var0 += Rasterizer2D.width;
								var24 += var25;
								var36 += var37;
								var33 += var34;
							}
						}
					} else {
						var4 = var3 <<= 16;
						if(var0 < 0) {
							var4 -= var22 * var0;
							var3 -= var29 * var0;
							var6 -= var20 * var0;
							var0 = 0;
						}

						var5 <<= 16;
						if(var2 < 0) {
							var5 -= var30 * var2;
							var2 = 0;
						}

						var35 = var0 - originViewY;
						var24 += var25 * var35;
						var36 += var37 * var35;
						var33 += var34 * var35;
						if((var0 == var2 || var22 >= var29) && (var0 != var2 || var30 <= var29)) {
							var1 -= var2;
							var2 -= var0;
							var0 = scanOffsets[var0];

							while(true) {
								--var2;
								if(var2 < 0) {
									while(true) {
										--var1;
										if(var1 < 0) {
											return;
										}

										drawTexturedLine(Rasterizer2D.pixels, texturePixels, 0, 0, var0, var3 >> 16, var5 >> 16, var6, var41, var24, var36, var33, var38, var39, var40);
										var5 += var30;
										var3 += var29;
										var6 += var20;
										var0 += Rasterizer2D.width;
										var24 += var25;
										var36 += var37;
										var33 += var34;
									}
								}

								drawTexturedLine(Rasterizer2D.pixels, texturePixels, 0, 0, var0, var3 >> 16, var4 >> 16, var6, var41, var24, var36, var33, var38, var39, var40);
								var4 += var22;
								var3 += var29;
								var6 += var20;
								var0 += Rasterizer2D.width;
								var24 += var25;
								var36 += var37;
								var33 += var34;
							}
						} else {
							var1 -= var2;
							var2 -= var0;
							var0 = scanOffsets[var0];

							while(true) {
								--var2;
								if(var2 < 0) {
									while(true) {
										--var1;
										if(var1 < 0) {
											return;
										}

										drawTexturedLine(Rasterizer2D.pixels, texturePixels, 0, 0, var0, var5 >> 16, var3 >> 16, var6, var41, var24, var36, var33, var38, var39, var40);
										var5 += var30;
										var3 += var29;
										var6 += var20;
										var0 += Rasterizer2D.width;
										var24 += var25;
										var36 += var37;
										var33 += var34;
									}
								}

								drawTexturedLine(Rasterizer2D.pixels, texturePixels, 0, 0, var0, var4 >> 16, var3 >> 16, var6, var41, var24, var36, var33, var38, var39, var40);
								var4 += var22;
								var3 += var29;
								var6 += var20;
								var0 += Rasterizer2D.width;
								var24 += var25;
								var36 += var37;
								var33 += var34;
							}
						}
					}
				}
			} else if(var1 <= var2) {
				if(var1 < Rasterizer2D.clip_bottom) {
					if(var2 > Rasterizer2D.clip_bottom) {
						var2 = Rasterizer2D.clip_bottom;
					}

					if(var0 > Rasterizer2D.clip_bottom) {
						var0 = Rasterizer2D.clip_bottom;
					}

					var7 = (var7 << 9) - var41 * var4 + var41;
					if(var2 < var0) {
						var3 = var4 <<= 16;
						if(var1 < 0) {
							var3 -= var29 * var1;
							var4 -= var30 * var1;
							var7 -= var20 * var1;
							var1 = 0;
						}

						var5 <<= 16;
						if(var2 < 0) {
							var5 -= var22 * var2;
							var2 = 0;
						}

						var35 = var1 - originViewY;
						var24 += var25 * var35;
						var36 += var37 * var35;
						var33 += var34 * var35;
						if((var1 == var2 || var29 >= var30) && (var1 != var2 || var29 <= var22)) {
							var0 -= var2;
							var2 -= var1;
							var1 = scanOffsets[var1];

							while(true) {
								--var2;
								if(var2 < 0) {
									while(true) {
										--var0;
										if(var0 < 0) {
											return;
										}

										drawTexturedLine(Rasterizer2D.pixels, texturePixels, 0, 0, var1, var5 >> 16, var3 >> 16, var7, var41, var24, var36, var33, var38, var39, var40);
										var3 += var29;
										var5 += var22;
										var7 += var20;
										var1 += Rasterizer2D.width;
										var24 += var25;
										var36 += var37;
										var33 += var34;
									}
								}

								drawTexturedLine(Rasterizer2D.pixels, texturePixels, 0, 0, var1, var4 >> 16, var3 >> 16, var7, var41, var24, var36, var33, var38, var39, var40);
								var3 += var29;
								var4 += var30;
								var7 += var20;
								var1 += Rasterizer2D.width;
								var24 += var25;
								var36 += var37;
								var33 += var34;
							}
						} else {
							var0 -= var2;
							var2 -= var1;
							var1 = scanOffsets[var1];

							while(true) {
								--var2;
								if(var2 < 0) {
									while(true) {
										--var0;
										if(var0 < 0) {
											return;
										}

										drawTexturedLine(Rasterizer2D.pixels, texturePixels, 0, 0, var1, var3 >> 16, var5 >> 16, var7, var41, var24, var36, var33, var38, var39, var40);
										var3 += var29;
										var5 += var22;
										var7 += var20;
										var1 += Rasterizer2D.width;
										var24 += var25;
										var36 += var37;
										var33 += var34;
									}
								}

								drawTexturedLine(Rasterizer2D.pixels, texturePixels, 0, 0, var1, var3 >> 16, var4 >> 16, var7, var41, var24, var36, var33, var38, var39, var40);
								var3 += var29;
								var4 += var30;
								var7 += var20;
								var1 += Rasterizer2D.width;
								var24 += var25;
								var36 += var37;
								var33 += var34;
							}
						}
					} else {
						var5 = var4 <<= 16;
						if(var1 < 0) {
							var5 -= var29 * var1;
							var4 -= var30 * var1;
							var7 -= var20 * var1;
							var1 = 0;
						}

						var3 <<= 16;
						if(var0 < 0) {
							var3 -= var22 * var0;
							var0 = 0;
						}

						var35 = var1 - originViewY;
						var24 += var25 * var35;
						var36 += var37 * var35;
						var33 += var34 * var35;
						if(var29 < var30) {
							var2 -= var0;
							var0 -= var1;
							var1 = scanOffsets[var1];

							while(true) {
								--var0;
								if(var0 < 0) {
									while(true) {
										--var2;
										if(var2 < 0) {
											return;
										}

										drawTexturedLine(Rasterizer2D.pixels, texturePixels, 0, 0, var1, var3 >> 16, var4 >> 16, var7, var41, var24, var36, var33, var38, var39, var40);
										var3 += var22;
										var4 += var30;
										var7 += var20;
										var1 += Rasterizer2D.width;
										var24 += var25;
										var36 += var37;
										var33 += var34;
									}
								}

								drawTexturedLine(Rasterizer2D.pixels, texturePixels, 0, 0, var1, var5 >> 16, var4 >> 16, var7, var41, var24, var36, var33, var38, var39, var40);
								var5 += var29;
								var4 += var30;
								var7 += var20;
								var1 += Rasterizer2D.width;
								var24 += var25;
								var36 += var37;
								var33 += var34;
							}
						} else {
							var2 -= var0;
							var0 -= var1;
							var1 = scanOffsets[var1];

							while(true) {
								--var0;
								if(var0 < 0) {
									while(true) {
										--var2;
										if(var2 < 0) {
											return;
										}

										drawTexturedLine(Rasterizer2D.pixels, texturePixels, 0, 0, var1, var4 >> 16, var3 >> 16, var7, var41, var24, var36, var33, var38, var39, var40);
										var3 += var22;
										var4 += var30;
										var7 += var20;
										var1 += Rasterizer2D.width;
										var24 += var25;
										var36 += var37;
										var33 += var34;
									}
								}

								drawTexturedLine(Rasterizer2D.pixels, texturePixels, 0, 0, var1, var4 >> 16, var5 >> 16, var7, var41, var24, var36, var33, var38, var39, var40);
								var5 += var29;
								var4 += var30;
								var7 += var20;
								var1 += Rasterizer2D.width;
								var24 += var25;
								var36 += var37;
								var33 += var34;
							}
						}
					}
				}
			} else if(var2 < Rasterizer2D.clip_bottom) {
				if(var0 > Rasterizer2D.clip_bottom) {
					var0 = Rasterizer2D.clip_bottom;
				}

				if(var1 > Rasterizer2D.clip_bottom) {
					var1 = Rasterizer2D.clip_bottom;
				}

				var8 = (var8 << 9) - var41 * var5 + var41;
				if(var0 < var1) {
					var4 = var5 <<= 16;
					if(var2 < 0) {
						var4 -= var30 * var2;
						var5 -= var22 * var2;
						var8 -= var20 * var2;
						var2 = 0;
					}

					var3 <<= 16;
					if(var0 < 0) {
						var3 -= var29 * var0;
						var0 = 0;
					}

					var35 = var2 - originViewY;
					var24 += var25 * var35;
					var36 += var37 * var35;
					var33 += var34 * var35;
					if(var30 < var22) {
						var1 -= var0;
						var0 -= var2;
						var2 = scanOffsets[var2];

						while(true) {
							--var0;
							if(var0 < 0) {
								while(true) {
									--var1;
									if(var1 < 0) {
										return;
									}

									drawTexturedLine(Rasterizer2D.pixels, texturePixels, 0, 0, var2, var4 >> 16, var3 >> 16, var8, var41, var24, var36, var33, var38, var39, var40);
									var4 += var30;
									var3 += var29;
									var8 += var20;
									var2 += Rasterizer2D.width;
									var24 += var25;
									var36 += var37;
									var33 += var34;
								}
							}

							drawTexturedLine(Rasterizer2D.pixels, texturePixels, 0, 0, var2, var4 >> 16, var5 >> 16, var8, var41, var24, var36, var33, var38, var39, var40);
							var4 += var30;
							var5 += var22;
							var8 += var20;
							var2 += Rasterizer2D.width;
							var24 += var25;
							var36 += var37;
							var33 += var34;
						}
					} else {
						var1 -= var0;
						var0 -= var2;
						var2 = scanOffsets[var2];

						while(true) {
							--var0;
							if(var0 < 0) {
								while(true) {
									--var1;
									if(var1 < 0) {
										return;
									}

									drawTexturedLine(Rasterizer2D.pixels, texturePixels, 0, 0, var2, var3 >> 16, var4 >> 16, var8, var41, var24, var36, var33, var38, var39, var40);
									var4 += var30;
									var3 += var29;
									var8 += var20;
									var2 += Rasterizer2D.width;
									var24 += var25;
									var36 += var37;
									var33 += var34;
								}
							}

							drawTexturedLine(Rasterizer2D.pixels, texturePixels, 0, 0, var2, var5 >> 16, var4 >> 16, var8, var41, var24, var36, var33, var38, var39, var40);
							var4 += var30;
							var5 += var22;
							var8 += var20;
							var2 += Rasterizer2D.width;
							var24 += var25;
							var36 += var37;
							var33 += var34;
						}
					}
				} else {
					var3 = var5 <<= 16;
					if(var2 < 0) {
						var3 -= var30 * var2;
						var5 -= var22 * var2;
						var8 -= var20 * var2;
						var2 = 0;
					}

					var4 <<= 16;
					if(var1 < 0) {
						var4 -= var29 * var1;
						var1 = 0;
					}

					var35 = var2 - originViewY;
					var24 += var25 * var35;
					var36 += var37 * var35;
					var33 += var34 * var35;
					if(var30 < var22) {
						var0 -= var1;
						var1 -= var2;
						var2 = scanOffsets[var2];

						while(true) {
							--var1;
							if(var1 < 0) {
								while(true) {
									--var0;
									if(var0 < 0) {
										return;
									}

									drawTexturedLine(Rasterizer2D.pixels, texturePixels, 0, 0, var2, var4 >> 16, var5 >> 16, var8, var41, var24, var36, var33, var38, var39, var40);
									var4 += var29;
									var5 += var22;
									var8 += var20;
									var2 += Rasterizer2D.width;
									var24 += var25;
									var36 += var37;
									var33 += var34;
								}
							}

							drawTexturedLine(Rasterizer2D.pixels, texturePixels, 0, 0, var2, var3 >> 16, var5 >> 16, var8, var41, var24, var36, var33, var38, var39, var40);
							var3 += var30;
							var5 += var22;
							var8 += var20;
							var2 += Rasterizer2D.width;
							var24 += var25;
							var36 += var37;
							var33 += var34;
						}
					} else {
						var0 -= var1;
						var1 -= var2;
						var2 = scanOffsets[var2];

						while(true) {
							--var1;
							if(var1 < 0) {
								while(true) {
									--var0;
									if(var0 < 0) {
										return;
									}

									drawTexturedLine(Rasterizer2D.pixels, texturePixels, 0, 0, var2, var5 >> 16, var4 >> 16, var8, var41, var24, var36, var33, var38, var39, var40);
									var4 += var29;
									var5 += var22;
									var8 += var20;
									var2 += Rasterizer2D.width;
									var24 += var25;
									var36 += var37;
									var33 += var34;
								}
							}

							drawTexturedLine(Rasterizer2D.pixels, texturePixels, 0, 0, var2, var5 >> 16, var3 >> 16, var8, var41, var24, var36, var33, var38, var39, var40);
							var3 += var30;
							var5 += var22;
							var8 += var20;
							var2 += Rasterizer2D.width;
							var24 += var25;
							var36 += var37;
							var33 += var34;
						}
					}
				}
			}
		}
	}

	static void drawTexturedLine(int[] var0, int[] var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, int var14) {
		if(textureOutOfDrawingBounds) {
			if(var6 > Rasterizer2D.lastX) {
				var6 = Rasterizer2D.lastX;
			}

			if(var5 < 0) {
				var5 = 0;
			}
		}

		if(var5 < var6) {
			var4 += var5;
			var7 += var8 * var5;
			int var17 = var6 - var5;
			int var15;
			int var16;
			int var18;
			int var19;
			int var20;
			int var21;
			int var22;
			int var23;
			if(false) {
				var15 = var5 - originViewX;
				var9 += (var12 >> 3) * var15;
				var10 += (var13 >> 3) * var15;
				var11 += (var14 >> 3) * var15;
				var19 = var11 >> 12;
				if(var19 != 0) {
					var20 = var9 / var19;
					var18 = var10 / var19;
					if(var20 < 0) {
						var20 = 0;
					} else if(var20 > 4032) {
						var20 = 4032;
					}
				} else {
					var20 = 0;
					var18 = 0;
				}

				var9 += var12;
				var10 += var13;
				var11 += var14;
				var19 = var11 >> 12;
				if(var19 != 0) {
					var22 = var9 / var19;
					var16 = var10 / var19;
					if(var22 < 0) {
						var22 = 0;
					} else if(var22 > 4032) {
						var22 = 4032;
					}
				} else {
					var22 = 0;
					var16 = 0;
				}

				var2 = (var20 << 20) + var18;
				var23 = (var22 - var20 >> 3 << 20) + (var16 - var18 >> 3);
				var17 >>= 3;
				var8 <<= 3;
				var21 = var7 >> 8;
				if(aBoolean1463) {
					if(var17 > 0) {
						do {
							var3 = var1[(var2 & 4032) + (var2 >>> 26)];
							var0[var4++] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							var2 += var23;
							var3 = var1[(var2 & 4032) + (var2 >>> 26)];
							var0[var4++] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							var2 += var23;
							var3 = var1[(var2 & 4032) + (var2 >>> 26)];
							var0[var4++] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							var2 += var23;
							var3 = var1[(var2 & 4032) + (var2 >>> 26)];
							var0[var4++] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							var2 += var23;
							var3 = var1[(var2 & 4032) + (var2 >>> 26)];
							var0[var4++] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							var2 += var23;
							var3 = var1[(var2 & 4032) + (var2 >>> 26)];
							var0[var4++] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							var2 += var23;
							var3 = var1[(var2 & 4032) + (var2 >>> 26)];
							var0[var4++] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							var2 += var23;
							var3 = var1[(var2 & 4032) + (var2 >>> 26)];
							var0[var4++] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							var20 = var22;
							var18 = var16;
							var9 += var12;
							var10 += var13;
							var11 += var14;
							var19 = var11 >> 12;
							if(var19 != 0) {
								var22 = var9 / var19;
								var16 = var10 / var19;
								if(var22 < 0) {
									var22 = 0;
								} else if(var22 > 4032) {
									var22 = 4032;
								}
							} else {
								var22 = 0;
								var16 = 0;
							}

							var2 = (var20 << 20) + var18;
							var23 = (var22 - var20 >> 3 << 20) + (var16 - var18 >> 3);
							var7 += var8;
							var21 = var7 >> 8;
							--var17;
						} while(var17 > 0);
					}

					var17 = var6 - var5 & 7;
					if(var17 > 0) {
						do {
							var3 = var1[(var2 & 4032) + (var2 >>> 26)];
							var0[var4++] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							var2 += var23;
							--var17;
						} while(var17 > 0);

					}
				} else {
					if(var17 > 0) {
						do {
							if((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
								var0[var4] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							}

							++var4;
							var2 += var23;
							if((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
								var0[var4] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							}

							++var4;
							var2 += var23;
							if((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
								var0[var4] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							}

							++var4;
							var2 += var23;
							if((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
								var0[var4] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							}

							++var4;
							var2 += var23;
							if((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
								var0[var4] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							}

							++var4;
							var2 += var23;
							if((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
								var0[var4] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							}

							++var4;
							var2 += var23;
							if((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
								var0[var4] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							}

							++var4;
							var2 += var23;
							if((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
								var0[var4] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							}

							++var4;
							var20 = var22;
							var18 = var16;
							var9 += var12;
							var10 += var13;
							var11 += var14;
							var19 = var11 >> 12;
							if(var19 != 0) {
								var22 = var9 / var19;
								var16 = var10 / var19;
								if(var22 < 0) {
									var22 = 0;
								} else if(var22 > 4032) {
									var22 = 4032;
								}
							} else {
								var22 = 0;
								var16 = 0;
							}

							var2 = (var20 << 20) + var18;
							var23 = (var22 - var20 >> 3 << 20) + (var16 - var18 >> 3);
							var7 += var8;
							var21 = var7 >> 8;
							--var17;
						} while(var17 > 0);
					}

					var17 = var6 - var5 & 7;
					if(var17 > 0) {
						do {
							if((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
								var0[var4] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							}

							++var4;
							var2 += var23;
							--var17;
						} while(var17 > 0);

					}
				}
			} else {
				var15 = var5 - originViewX;
				var9 += (var12 >> 3) * var15;
				var10 += (var13 >> 3) * var15;
				var11 += (var14 >> 3) * var15;
				var19 = var11 >> 14;
				if(var19 != 0) {
					var20 = var9 / var19;
					var18 = var10 / var19;
					if(var20 < 0) {
						var20 = 0;
					} else if(var20 > 16256) {
						var20 = 16256;
					}
				} else {
					var20 = 0;
					var18 = 0;
				}

				var9 += var12;
				var10 += var13;
				var11 += var14;
				var19 = var11 >> 14;
				if(var19 != 0) {
					var22 = var9 / var19;
					var16 = var10 / var19;
					if(var22 < 0) {
						var22 = 0;
					} else if(var22 > 16256) {
						var22 = 16256;
					}
				} else {
					var22 = 0;
					var16 = 0;
				}

				var2 = (var20 << 18) + var18;
				var23 = (var22 - var20 >> 3 << 18) + (var16 - var18 >> 3);
				var17 >>= 3;
				var8 <<= 3;
				var21 = var7 >> 8;
				if(aBoolean1463) {
					if(var17 > 0) {
						do {
							var3 = var1[(var2 & 16256) + (var2 >>> 25)];
							var0[var4++] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							var2 += var23;
							var3 = var1[(var2 & 16256) + (var2 >>> 25)];
							var0[var4++] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							var2 += var23;
							var3 = var1[(var2 & 16256) + (var2 >>> 25)];
							var0[var4++] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							var2 += var23;
							var3 = var1[(var2 & 16256) + (var2 >>> 25)];
							var0[var4++] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							var2 += var23;
							var3 = var1[(var2 & 16256) + (var2 >>> 25)];
							var0[var4++] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							var2 += var23;
							var3 = var1[(var2 & 16256) + (var2 >>> 25)];
							var0[var4++] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							var2 += var23;
							var3 = var1[(var2 & 16256) + (var2 >>> 25)];
							var0[var4++] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							var2 += var23;
							var3 = var1[(var2 & 16256) + (var2 >>> 25)];
							var0[var4++] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							var20 = var22;
							var18 = var16;
							var9 += var12;
							var10 += var13;
							var11 += var14;
							var19 = var11 >> 14;
							if(var19 != 0) {
								var22 = var9 / var19;
								var16 = var10 / var19;
								if(var22 < 0) {
									var22 = 0;
								} else if(var22 > 16256) {
									var22 = 16256;
								}
							} else {
								var22 = 0;
								var16 = 0;
							}

							var2 = (var20 << 18) + var18;
							var23 = (var22 - var20 >> 3 << 18) + (var16 - var18 >> 3);
							var7 += var8;
							var21 = var7 >> 8;
							--var17;
						} while(var17 > 0);
					}

					var17 = var6 - var5 & 7;
					if(var17 > 0) {
						do {
							var3 = var1[(var2 & 16256) + (var2 >>> 25)];
							var0[var4++] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							var2 += var23;
							--var17;
						} while(var17 > 0);

					}
				} else {
					if(var17 > 0) {
						do {
							if((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
								var0[var4] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							}

							++var4;
							var2 += var23;
							if((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
								var0[var4] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							}

							++var4;
							var2 += var23;
							if((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
								var0[var4] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							}

							++var4;
							var2 += var23;
							if((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
								var0[var4] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							}

							++var4;
							var2 += var23;
							if((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
								var0[var4] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							}

							++var4;
							var2 += var23;
							if((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
								var0[var4] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							}

							++var4;
							var2 += var23;
							if((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
								var0[var4] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							}

							++var4;
							var2 += var23;
							if((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
								var0[var4] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							}

							++var4;
							var20 = var22;
							var18 = var16;
							var9 += var12;
							var10 += var13;
							var11 += var14;
							var19 = var11 >> 14;
							if(var19 != 0) {
								var22 = var9 / var19;
								var16 = var10 / var19;
								if(var22 < 0) {
									var22 = 0;
								} else if(var22 > 16256) {
									var22 = 16256;
								}
							} else {
								var22 = 0;
								var16 = 0;
							}

							var2 = (var20 << 18) + var18;
							var23 = (var22 - var20 >> 3 << 18) + (var16 - var18 >> 3);
							var7 += var8;
							var21 = var7 >> 8;
							--var17;
						} while(var17 > 0);
					}

					var17 = var6 - var5 & 7;
					if(var17 > 0) {
						do {
							if((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
								var0[var4] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							}

							++var4;
							var2 += var23;
							--var17;
						} while(var17 > 0);

					}
				}
			}
		}
	}

	public static void drawTexturedTriangle2(int y_a, int y_b, int y_c, int x_a, int x_b, int x_c, int l1, int l2, int l3, int tx1, int tx2, int tx3, int ty1, int ty2, int ty3, int tz1, int tz2, int tz3, int tex, float z1, float z2, float z3) {
		try {
		if (!aBoolean1464) {
			method378_2(y_a, y_b, y_c, x_a, x_b, x_c, l1, l2, l3, tx1, tx2, tx3, ty1, ty2, ty3, tz1, tz2, tz3, tex);
			return;
		}
		if (z1 < 0.0F || z2 < 0.0F || z3 < 0.0F) {
			return;
		}
		l1 = 0x7f - l1 << 1;
		l2 = 0x7f - l2 << 1;
		l3 = 0x7f - l3 << 1;
		setMipmapLevel(y_a, y_b, y_c, x_a, x_b, x_c, tex);
		int ai[] = getTexturePixels(tex);
		aBoolean1463 = !textureIsTransparant[tex];
		tx2 = tx1 - tx2;
		ty2 = ty1 - ty2;
		tz2 = tz1 - tz2;
		tx3 -= tx1;
		ty3 -= ty1;
		tz3 -= tz1;
		int l4 = tx3 * ty1 - ty3 * tx1 << (WorldController.viewDistance == 9 ? 14 : 15);
		int i5 = ty3 * tz1 - tz3 * ty1 << 8;
		int j5 = tz3 * tx1 - tx3 * tz1 << 5;
		int k5 = tx2 * ty1 - ty2 * tx1 << (WorldController.viewDistance == 9 ? 14 : 15);
		int l5 = ty2 * tz1 - tz2 * ty1 << 8;
		int i6 = tz2 * tx1 - tx2 * tz1 << 5;
		int j6 = ty2 * tx3 - tx2 * ty3 << (WorldController.viewDistance == 9 ? 14 : 15);
		int k6 = tz2 * ty3 - ty2 * tz3 << 8;
		int l6 = tx2 * tz3 - tz2 * tx3 << 5;
		int i7 = 0;
		int j7 = 0;
		if (y_b != y_a) {
			i7 = (x_b - x_a << 16) / (y_b - y_a);
			j7 = (l2 - l1 << 16) / (y_b - y_a);
		}
		int k7 = 0;
		int l7 = 0;
		if (y_c != y_b) {
			k7 = (x_c - x_b << 16) / (y_c - y_b);
			l7 = (l3 - l2 << 16) / (y_c - y_b);
		}
		int i8 = 0;
		int j8 = 0;
		if (y_c != y_a) {
			i8 = (x_a - x_c << 16) / (y_a - y_c);
			j8 = (l1 - l3 << 16) / (y_a - y_c);
		}
		float b_aX = x_b - x_a;
		float b_aY = y_b - y_a;
		float c_aX = x_c - x_a;
		float c_aY = y_c - y_a;
		float b_aZ = z2 - z1;
		float c_aZ = z3 - z1;

		float div = b_aX * c_aY - c_aX * b_aY;
		float depth_slope = (b_aZ * c_aY - c_aZ * b_aY) / div;
		float depth_increment = (c_aZ * b_aX - b_aZ * c_aX) / div;
		if (y_a <= y_b && y_a <= y_c) {
			if (y_a >= Rasterizer2D.clip_bottom)
				return;
			if (y_b > Rasterizer2D.clip_bottom)
				y_b = Rasterizer2D.clip_bottom;
			if (y_c > Rasterizer2D.clip_bottom)
				y_c = Rasterizer2D.clip_bottom;
			z1 = z1 - depth_slope * x_a + depth_slope;
			if (y_b < y_c) {
				x_c = x_a <<= 16;
				l3 = l1 <<= 16;
				if (y_a < 0) {
					x_c -= i8 * y_a;
					x_a -= i7 * y_a;
					z1 -= depth_increment * y_a;
					l3 -= j8 * y_a;
					l1 -= j7 * y_a;
					y_a = 0;
				}
				x_b <<= 16;
				l2 <<= 16;
				if (y_b < 0) {
					x_b -= k7 * y_b;
					l2 -= l7 * y_b;
					y_b = 0;
				}
				int k8 = y_a - originViewY;
				l4 += j5 * k8;
				k5 += i6 * k8;
				j6 += l6 * k8;
				if (y_a != y_b && i8 < i7 || y_a == y_b && i8 > k7) {
					y_c -= y_b;
					y_b -= y_a;
					y_a = scanOffsets[y_a];
					while (--y_b >= 0) {
						drawTexturedScanline(Rasterizer2D.pixels, ai, y_a, x_c >> 16, x_a >> 16, l3, l1, l4, k5, j6, i5, l5, k6, z1, depth_slope);
						x_c += i8;
						x_a += i7;
						z1 += depth_increment;
						l3 += j8;
						l1 += j7;
						y_a += Rasterizer2D.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					while (--y_c >= 0) {
						drawTexturedScanline(Rasterizer2D.pixels, ai, y_a, x_c >> 16, x_b >> 16, l3, l2, l4, k5, j6, i5, l5, k6, z1, depth_slope);
						x_c += i8;
						x_b += k7;
						z1 += depth_increment;
						l3 += j8;
						l2 += l7;
						y_a += Rasterizer2D.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					return;
				}
				y_c -= y_b;
				y_b -= y_a;
				y_a = scanOffsets[y_a];
				while (--y_b >= 0) {
					drawTexturedScanline(Rasterizer2D.pixels, ai, y_a, x_a >> 16, x_c >> 16, l1, l3, l4, k5, j6, i5, l5, k6, z1, depth_slope);
					x_c += i8;
					x_a += i7;
					z1 += depth_increment;
					l3 += j8;
					l1 += j7;
					y_a += Rasterizer2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y_c >= 0) {
					drawTexturedScanline(Rasterizer2D.pixels, ai, y_a, x_b >> 16, x_c >> 16, l2, l3, l4, k5, j6, i5, l5, k6, z1, depth_slope);
					x_c += i8;
					x_b += k7;
					z1 += depth_increment;
					l3 += j8;
					l2 += l7;
					y_a += Rasterizer2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			x_b = x_a <<= 16;
			l2 = l1 <<= 16;
			if (y_a < 0) {
				x_b -= i8 * y_a;
				x_a -= i7 * y_a;
				z1 -= depth_increment * y_a;
				l2 -= j8 * y_a;
				l1 -= j7 * y_a;
				y_a = 0;
			}
			x_c <<= 16;
			l3 <<= 16;
			if (y_c < 0) {
				x_c -= k7 * y_c;
				l3 -= l7 * y_c;
				y_c = 0;
			}
			int l8 = y_a - originViewY;
			l4 += j5 * l8;
			k5 += i6 * l8;
			j6 += l6 * l8;
			if (y_a != y_c && i8 < i7 || y_a == y_c && k7 > i7) {
				y_b -= y_c;
				y_c -= y_a;
				y_a = scanOffsets[y_a];
				while (--y_c >= 0) {
					drawTexturedScanline(Rasterizer2D.pixels, ai, y_a, x_b >> 16, x_a >> 16, l2, l1, l4, k5, j6, i5, l5, k6, z1, depth_slope);
					x_b += i8;
					x_a += i7;
					l2 += j8;
					l1 += j7;
					z1 += depth_increment;
					y_a += Rasterizer2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y_b >= 0) {
					drawTexturedScanline(Rasterizer2D.pixels, ai, y_a, x_c >> 16, x_a >> 16, l3, l1, l4, k5, j6, i5, l5, k6, z1, depth_slope);
					x_c += k7;
					x_a += i7;
					l3 += l7;
					l1 += j7;
					z1 += depth_increment;
					y_a += Rasterizer2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			y_b -= y_c;
			y_c -= y_a;
			y_a = scanOffsets[y_a];
			while (--y_c >= 0) {
				drawTexturedScanline(Rasterizer2D.pixels, ai, y_a, x_a >> 16, x_b >> 16, l1, l2, l4, k5, j6, i5, l5, k6, z1, depth_slope);
				x_b += i8;
				x_a += i7;
				l2 += j8;
				l1 += j7;
				z1 += depth_increment;
				y_a += Rasterizer2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--y_b >= 0) {
				drawTexturedScanline(Rasterizer2D.pixels, ai, y_a, x_a >> 16, x_c >> 16, l1, l3, l4, k5, j6, i5, l5, k6, z1, depth_slope);
				x_c += k7;
				x_a += i7;
				l3 += l7;
				l1 += j7;
				z1 += depth_increment;
				y_a += Rasterizer2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		if (y_b <= y_c) {
			if (y_b >= Rasterizer2D.clip_bottom)
				return;
			if (y_c > Rasterizer2D.clip_bottom)
				y_c = Rasterizer2D.clip_bottom;
			if (y_a > Rasterizer2D.clip_bottom)
				y_a = Rasterizer2D.clip_bottom;
			z2 = z2 - depth_slope * x_b + depth_slope;
			if (y_c < y_a) {
				x_a = x_b <<= 16;
				l1 = l2 <<= 16;
				if (y_b < 0) {
					x_a -= i7 * y_b;
					x_b -= k7 * y_b;
					z2 -= depth_increment * y_b;
					l1 -= j7 * y_b;
					l2 -= l7 * y_b;
					y_b = 0;
				}
				x_c <<= 16;
				l3 <<= 16;
				if (y_c < 0) {
					x_c -= i8 * y_c;
					l3 -= j8 * y_c;
					y_c = 0;
				}
				int i9 = y_b - originViewY;
				l4 += j5 * i9;
				k5 += i6 * i9;
				j6 += l6 * i9;
				if (y_b != y_c && i7 < k7 || y_b == y_c && i7 > i8) {
					y_a -= y_c;
					y_c -= y_b;
					y_b = scanOffsets[y_b];
					while (--y_c >= 0) {
						drawTexturedScanline(Rasterizer2D.pixels, ai, y_b, x_a >> 16, x_b >> 16, l1, l2, l4, k5, j6, i5, l5, k6, z2, depth_slope);
						x_a += i7;
						x_b += k7;
						l1 += j7;
						l2 += l7;
						z2 += depth_increment;
						y_b += Rasterizer2D.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					while (--y_a >= 0) {
						drawTexturedScanline(Rasterizer2D.pixels, ai, y_b, x_a >> 16, x_c >> 16, l1, l3, l4, k5, j6, i5, l5, k6, z2, depth_slope);
						x_a += i7;
						x_c += i8;
						l1 += j7;
						l3 += j8;
						z2 += depth_increment;
						y_b += Rasterizer2D.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					return;
				}
				y_a -= y_c;
				y_c -= y_b;
				y_b = scanOffsets[y_b];
				while (--y_c >= 0) {
					drawTexturedScanline(Rasterizer2D.pixels, ai, y_b, x_b >> 16, x_a >> 16, l2, l1, l4, k5, j6, i5, l5, k6, z2, depth_slope);
					x_a += i7;
					x_b += k7;
					l1 += j7;
					l2 += l7;
					z2 += depth_increment;
					y_b += Rasterizer2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y_a >= 0) {
					drawTexturedScanline(Rasterizer2D.pixels, ai, y_b, x_c >> 16, x_a >> 16, l3, l1, l4, k5, j6, i5, l5, k6, z2, depth_slope);
					x_a += i7;
					x_c += i8;
					l1 += j7;
					l3 += j8;
					z2 += depth_increment;
					y_b += Rasterizer2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			x_c = x_b <<= 16;
			l3 = l2 <<= 16;
			if (y_b < 0) {
				x_c -= i7 * y_b;
				x_b -= k7 * y_b;
				z2 -= depth_increment * y_b;
				l3 -= j7 * y_b;
				l2 -= l7 * y_b;
				y_b = 0;
			}
			x_a <<= 16;
			l1 <<= 16;
			if (y_a < 0) {
				x_a -= i8 * y_a;
				l1 -= j8 * y_a;
				y_a = 0;
			}
			int j9 = y_b - originViewY;
			l4 += j5 * j9;
			k5 += i6 * j9;
			j6 += l6 * j9;
			if (i7 < k7) {
				y_c -= y_a;
				y_a -= y_b;
				y_b = scanOffsets[y_b];
				while (--y_a >= 0) {
					drawTexturedScanline(Rasterizer2D.pixels, ai, y_b, x_c >> 16, x_b >> 16, l3, l2, l4, k5, j6, i5, l5, k6, z2, depth_slope);
					x_c += i7;
					x_b += k7;
					l3 += j7;
					l2 += l7;
					z2 += depth_increment;
					y_b += Rasterizer2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y_c >= 0) {
					drawTexturedScanline(Rasterizer2D.pixels, ai, y_b, x_a >> 16, x_b >> 16, l1, l2, l4, k5, j6, i5, l5, k6, z2, depth_slope);
					x_a += i8;
					x_b += k7;
					l1 += j8;
					l2 += l7;
					z2 += depth_increment;
					y_b += Rasterizer2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			y_c -= y_a;
			y_a -= y_b;
			y_b = scanOffsets[y_b];
			while (--y_a >= 0) {
				drawTexturedScanline(Rasterizer2D.pixels, ai, y_b, x_b >> 16, x_c >> 16, l2, l3, l4, k5, j6, i5, l5, k6, z2, depth_slope);
				x_c += i7;
				x_b += k7;
				l3 += j7;
				l2 += l7;
				z2 += depth_increment;
				y_b += Rasterizer2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--y_c >= 0) {
				drawTexturedScanline(Rasterizer2D.pixels, ai, y_b, x_b >> 16, x_a >> 16, l2, l1, l4, k5, j6, i5, l5, k6, z2, depth_slope);
				x_a += i8;
				x_b += k7;
				l1 += j8;
				l2 += l7;
				z2 += depth_increment;
				y_b += Rasterizer2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		if (y_c >= Rasterizer2D.clip_bottom)
			return;
		if (y_a > Rasterizer2D.clip_bottom)
			y_a = Rasterizer2D.clip_bottom;
		if (y_b > Rasterizer2D.clip_bottom)
			y_b = Rasterizer2D.clip_bottom;
		z3 = z3 - depth_slope * x_c + depth_slope;
		if (y_a < y_b) {
			x_b = x_c <<= 16;
			l2 = l3 <<= 16;
			if (y_c < 0) {
				x_b -= k7 * y_c;
				x_c -= i8 * y_c;
				z3 -= depth_increment * y_c;
				l2 -= l7 * y_c;
				l3 -= j8 * y_c;
				y_c = 0;
			}
			x_a <<= 16;
			l1 <<= 16;
			if (y_a < 0) {
				x_a -= i7 * y_a;
				l1 -= j7 * y_a;
				y_a = 0;
			}
			int k9 = y_c - originViewY;
			l4 += j5 * k9;
			k5 += i6 * k9;
			j6 += l6 * k9;
			if (k7 < i8) {
				y_b -= y_a;
				y_a -= y_c;
				y_c = scanOffsets[y_c];
				while (--y_a >= 0) {
					drawTexturedScanline(Rasterizer2D.pixels, ai, y_c, x_b >> 16, x_c >> 16, l2, l3, l4, k5, j6, i5, l5, k6, z3, depth_slope);
					x_b += k7;
					x_c += i8;
					l2 += l7;
					l3 += j8;
					z3 += depth_increment;
					y_c += Rasterizer2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y_b >= 0) {
					drawTexturedScanline(Rasterizer2D.pixels, ai, y_c, x_b >> 16, x_a >> 16, l2, l1, l4, k5, j6, i5, l5, k6, z3, depth_slope);
					x_b += k7;
					x_a += i7;
					l2 += l7;
					l1 += j7;
					z3 += depth_increment;
					y_c += Rasterizer2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			y_b -= y_a;
			y_a -= y_c;
			y_c = scanOffsets[y_c];
			while (--y_a >= 0) {
				drawTexturedScanline(Rasterizer2D.pixels, ai, y_c, x_c >> 16, x_b >> 16, l3, l2, l4, k5, j6, i5, l5, k6, z3, depth_slope);
				x_b += k7;
				x_c += i8;
				l2 += l7;
				l3 += j8;
				z3 += depth_increment;
				y_c += Rasterizer2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--y_b >= 0) {
				drawTexturedScanline(Rasterizer2D.pixels, ai, y_c, x_a >> 16, x_b >> 16, l1, l2, l4, k5, j6, i5, l5, k6, z3, depth_slope);
				x_b += k7;
				x_a += i7;
				l2 += l7;
				l1 += j7;
				z3 += depth_increment;
				y_c += Rasterizer2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		x_a = x_c <<= 16;
		l1 = l3 <<= 16;
		if (y_c < 0) {
			x_a -= k7 * y_c;
			x_c -= i8 * y_c;
			z3 -= depth_increment * y_c;
			l1 -= l7 * y_c;
			l3 -= j8 * y_c;
			y_c = 0;
		}
		x_b <<= 16;
		l2 <<= 16;
		if (y_b < 0) {
			x_b -= i7 * y_b;
			l2 -= j7 * y_b;
			y_b = 0;
		}
		int l9 = y_c - originViewY;
		l4 += j5 * l9;
		k5 += i6 * l9;
		j6 += l6 * l9;
		if (k7 < i8) {
			y_a -= y_b;
			y_b -= y_c;
			y_c = scanOffsets[y_c];
			while (--y_b >= 0) {
				drawTexturedScanline(Rasterizer2D.pixels, ai, y_c, x_a >> 16, x_c >> 16, l1, l3, l4, k5, j6, i5, l5, k6, z3, depth_slope);
				x_a += k7;
				x_c += i8;
				l1 += l7;
				l3 += j8;
				z3 += depth_increment;
				y_c += Rasterizer2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--y_a >= 0) {
				drawTexturedScanline(Rasterizer2D.pixels, ai, y_c, x_b >> 16, x_c >> 16, l2, l3, l4, k5, j6, i5, l5, k6, z3, depth_slope);
				x_b += i7;
				x_c += i8;
				l2 += j7;
				l3 += j8;
				z3 += depth_increment;
				y_c += Rasterizer2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		y_a -= y_b;
		y_b -= y_c;
		y_c = scanOffsets[y_c];
		while (--y_b >= 0) {
			drawTexturedScanline(Rasterizer2D.pixels, ai, y_c, x_c >> 16, x_a >> 16, l3, l1, l4, k5, j6, i5, l5, k6, z3, depth_slope);
			x_a += k7;
			x_c += i8;
			l1 += l7;
			l3 += j8;
			z3 += depth_increment;
			y_c += Rasterizer2D.width;
			l4 += j5;
			k5 += i6;
			j6 += l6;
		}
		while (--y_a >= 0) {
			drawTexturedScanline(Rasterizer2D.pixels, ai, y_c, x_c >> 16, x_b >> 16, l3, l2, l4, k5, j6, i5, l5, k6, z3, depth_slope);
			x_b += i7;
			x_c += i8;
			l2 += j7;
			l3 += j8;
			z3 += depth_increment;
			y_c += Rasterizer2D.width;
			l4 += j5;
			k5 += i6;
			j6 += l6;
		}
		} catch(Exception e) {
		}
	}

	private static void drawTexturedScanline(int dest[], int texture[], int dest_off, int start_x, int end_x, int shadeValue, int gradient, int a1, int i2, int j2, int k2, int a2, int i3, float depth, float depth_slope) {
		int i = 0;
		int j = 0;
		if (start_x >= end_x)
			return;
		int dl = (gradient - shadeValue) / (end_x - start_x);
		int n;
		if (textureOutOfDrawingBounds) {
			if (end_x > Rasterizer2D.lastX)
				end_x = Rasterizer2D.lastX;
			if (start_x < 0) {
				shadeValue -= start_x * dl;
				start_x = 0;
			}
		}
		if (start_x >= end_x) {
			return;
		}
		n = end_x - start_x >> 3;
		dest_off += start_x;
		depth += depth_slope * (float) start_x;
		int j4 = 0;
		int l4 = 0;
		int l6 = start_x - originViewX;
		a1 += (k2 >> 3) * l6;
		i2 += (a2 >> 3) * l6;
		j2 += (i3 >> 3) * l6;
		int l5 = j2 >> 14;
		if (l5 != 0) {
			i = a1 / l5;
			j = i2 / l5;
			if (i < 0)
				i = 0;
			else if (i > 16256)
				i = 16256;
		}
		a1 += k2;
		i2 += a2;
		j2 += i3;
		l5 = j2 >> 14;
		if (l5 != 0) {
			j4 = a1 / l5;
			l4 = i2 / l5;
			if (j4 < 7)
				j4 = 7;
			else if (j4 > 16256)
				j4 = 16256;
		}
		int j7 = j4 - i >> 3;
		int l7 = l4 - j >> 3;
		if (aBoolean1463) {
			while (n-- > 0) {
				int rgb;
				int l;
				rgb = texture[texelPos((j & 0x3f80) + (i >> 7))];
				l = shadeValue >> 16;
				if (true) {
					dest[dest_off] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					Rasterizer2D.depthBuffer[dest_off] = depth;
				}
				dest_off++;
				depth += depth_slope;
				i += j7;
				j += l7;
				shadeValue += dl;
				rgb = texture[texelPos((j & 0x3f80) + (i >> 7))];
				l = shadeValue >> 16;
				if (true) {
					dest[dest_off] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					Rasterizer2D.depthBuffer[dest_off] = depth;
				}
				dest_off++;
				depth += depth_slope;
				i += j7;
				j += l7;
				shadeValue += dl;
				rgb = texture[texelPos((j & 0x3f80) + (i >> 7))];
				l = shadeValue >> 16;
				if (true) {
					dest[dest_off] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					Rasterizer2D.depthBuffer[dest_off] = depth;
				}
				dest_off++;
				depth += depth_slope;
				i += j7;
				j += l7;
				shadeValue += dl;
				rgb = texture[texelPos((j & 0x3f80) + (i >> 7))];
				l = shadeValue >> 16;
				if (true) {
					dest[dest_off] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					Rasterizer2D.depthBuffer[dest_off] = depth;
				}
				dest_off++;
				depth += depth_slope;
				i += j7;
				j += l7;
				shadeValue += dl;
				rgb = texture[texelPos((j & 0x3f80) + (i >> 7))];
				l = shadeValue >> 16;
				if (true) {
					dest[dest_off] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					Rasterizer2D.depthBuffer[dest_off] = depth;
				}
				dest_off++;
				depth += depth_slope;
				i += j7;
				j += l7;
				shadeValue += dl;
				rgb = texture[texelPos((j & 0x3f80) + (i >> 7))];
				l = shadeValue >> 16;
				if (true) {
					dest[dest_off] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					Rasterizer2D.depthBuffer[dest_off] = depth;
				}
				dest_off++;
				depth += depth_slope;
				i += j7;
				j += l7;
				shadeValue += dl;
				rgb = texture[texelPos((j & 0x3f80) + (i >> 7))];
				l = shadeValue >> 16;
				if (true) {
					dest[dest_off] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					Rasterizer2D.depthBuffer[dest_off] = depth;
				}
				dest_off++;
				depth += depth_slope;
				i += j7;
				j += l7;
				shadeValue += dl;
				rgb = texture[texelPos((j & 0x3f80) + (i >> 7))];
				l = shadeValue >> 16;
				if (true) {
					dest[dest_off] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					Rasterizer2D.depthBuffer[dest_off] = depth;
				}
				dest_off++;
				depth += depth_slope;
				i += j7;
				j += l7;
				shadeValue += dl;
				a1 += k2;
				i2 += a2;
				j2 += i3;
				int i6 = j2 >> 14;
				if (i6 != 0) {
					j4 = a1 / i6;
					l4 = i2 / i6;
					if (j4 < 7)
						j4 = 7;
					else if (j4 > 16256)
						j4 = 16256;
				}
				j7 = j4 - i >> 3;
				l7 = l4 - j >> 3;
				shadeValue += dl;
			}
			for (n = end_x - start_x & 7; n-- > 0;) {
				int rgb;
				int l;
				rgb = texture[texelPos((j & 0x3f80) + (i >> 7))];
				l = shadeValue >> 16;
				if (true) {
					dest[dest_off] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					Rasterizer2D.depthBuffer[dest_off] = depth;
				}
				dest_off++;
				depth += depth_slope;
				i += j7;
				j += l7;
				shadeValue += dl;
			}
			return;
		}
		while (n-- > 0) {
			int i9;
			int l;
			if ((i9 = texture[texelPos((j & 0x3f80) + (i >> 7))]) != 0 && true) {
				l = shadeValue >> 16;
				dest[dest_off] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;
				Rasterizer2D.depthBuffer[dest_off] = depth;
			}
			depth += depth_slope;
			dest_off++;
			i += j7;
			j += l7;
			shadeValue += dl;
			if ((i9 = texture[texelPos((j & 0x3f80) + (i >> 7))]) != 0 && true) {
				l = shadeValue >> 16;
				dest[dest_off] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;
				Rasterizer2D.depthBuffer[dest_off] = depth;
			}
			depth += depth_slope;
			dest_off++;
			i += j7;
			j += l7;
			shadeValue += dl;
			if ((i9 = texture[texelPos((j & 0x3f80) + (i >> 7))]) != 0 && true) {
				l = shadeValue >> 16;
				dest[dest_off] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;
				Rasterizer2D.depthBuffer[dest_off] = depth;
			}
			depth += depth_slope;
			dest_off++;
			i += j7;
			j += l7;
			shadeValue += dl;
			if ((i9 = texture[texelPos((j & 0x3f80) + (i >> 7))]) != 0 && true) {
				l = shadeValue >> 16;
				dest[dest_off] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;
				Rasterizer2D.depthBuffer[dest_off] = depth;
			}
			depth += depth_slope;
			dest_off++;
			i += j7;
			j += l7;
			shadeValue += dl;
			if ((i9 = texture[texelPos((j & 0x3f80) + (i >> 7))]) != 0 && true) {
				l = shadeValue >> 16;
				dest[dest_off] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;
				Rasterizer2D.depthBuffer[dest_off] = depth;
			}
			depth += depth_slope;
			dest_off++;
			i += j7;
			j += l7;
			shadeValue += dl;
			if ((i9 = texture[texelPos((j & 0x3f80) + (i >> 7))]) != 0 && true) {
				l = shadeValue >> 16;
				dest[dest_off] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;
				Rasterizer2D.depthBuffer[dest_off] = depth;
			}
			depth += depth_slope;
			dest_off++;
			i += j7;
			j += l7;
			shadeValue += dl;
			if ((i9 = texture[texelPos((j & 0x3f80) + (i >> 7))]) != 0 && true) {
				l = shadeValue >> 16;
				dest[dest_off] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;
				Rasterizer2D.depthBuffer[dest_off] = depth;
			}
			depth += depth_slope;
			dest_off++;
			i += j7;
			j += l7;
			shadeValue += dl;
			if ((i9 = texture[texelPos((j & 0x3f80) + (i >> 7))]) != 0 && true) {
				l = shadeValue >> 16;
				dest[dest_off] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;
				Rasterizer2D.depthBuffer[dest_off] = depth;
			}
			dest_off++;
			i += j7;
			j += l7;
			shadeValue += dl;
			a1 += k2;
			i2 += a2;
			j2 += i3;
			int j6 = j2 >> 14;
			if (j6 != 0) {
				j4 = a1 / j6;
				l4 = i2 / j6;
				if (j4 < 7)
					j4 = 7;
				else if (j4 > 16256)
					j4 = 16256;
			}
			j7 = j4 - i >> 3;
			l7 = l4 - j >> 3;
			shadeValue += dl;
		}
		for (int l3 = end_x - start_x & 7; l3-- > 0;) {
			int j9;
			int l;
			if ((j9 = texture[texelPos((j & 0x3f80) + (i >> 7))]) != 0 && true) {
				l = shadeValue >> 16;
				dest[dest_off] = ((j9 & 0xff00ff) * l & ~0xff00ff) + ((j9 & 0xff00) * l & 0xff0000) >> 8;
				Rasterizer2D.depthBuffer[dest_off] = depth;
			}
			depth += depth_slope;
			dest_off++;
			i += j7;
			j += l7;
			shadeValue += dl;
		}
	}
	
	public static void method378_2(int i, int j, int k, int l, int i1, int j1, int k1, int l1, int i2, int j2, int k2, int l2, int i3, int j3, int k3, int l3, int i4, int j4, int k4) {
		try {
		k1 = 0x7f - k1;
		l1 = 0x7f - l1;
		i2 = 0x7f - i2;
		setMipmapLevel(i, j, k, l, i1, j1, k4);
		int ai[] = getTexturePixels(k4);
		aBoolean1463 = !textureIsTransparant[k4];
		k2 = j2 - k2;
		j3 = i3 - j3;
		i4 = l3 - i4;
		l2 -= j2;
		k3 -= i3;
		j4 -= l3;
		int l4 = l2 * i3 - k3 * j2 << (WorldController.viewDistance == 9 ? 14 : 15);
		int i5 = k3 * l3 - j4 * i3 << 8;
		int j5 = j4 * j2 - l2 * l3 << 5;
		int k5 = k2 * i3 - j3 * j2 << (WorldController.viewDistance == 9 ? 14 : 15);
		int l5 = j3 * l3 - i4 * i3 << 8;
		int i6 = i4 * j2 - k2 * l3 << 5;
		int j6 = j3 * l2 - k2 * k3 << (WorldController.viewDistance == 9 ? 14 : 15);
		int k6 = i4 * k3 - j3 * j4 << 8;
		int l6 = k2 * j4 - i4 * l2 << 5;
		int i7 = 0;
		int j7 = 0;
		if (j != i) {
			i7 = (i1 - l << 16) / (j - i);
			j7 = (l1 - k1 << 16) / (j - i);
		}
		int k7 = 0;
		int l7 = 0;
		if (k != j) {
			k7 = (j1 - i1 << 16) / (k - j);
			l7 = (i2 - l1 << 16) / (k - j);
		}
		int i8 = 0;
		int j8 = 0;
		if (k != i) {
			i8 = (l - j1 << 16) / (i - k);
			j8 = (k1 - i2 << 16) / (i - k);
		}
		if (i <= j && i <= k) {
			if (i >= clip_bottom)
				return;
			if (j > clip_bottom)
				j = clip_bottom;
			if (k > clip_bottom)
				k = clip_bottom;
			if (j < k) {
				j1 = l <<= 16;
				i2 = k1 <<= 16;
				if (i < 0) {
					j1 -= i8 * i;
					l -= i7 * i;
					i2 -= j8 * i;
					k1 -= j7 * i;
					i = 0;
				}
				i1 <<= 16;
				l1 <<= 16;
				if (j < 0) {
					i1 -= k7 * j;
					l1 -= l7 * j;
					j = 0;
				}
				int k8 = i - originViewY;
				l4 += j5 * k8;
				k5 += i6 * k8;
				j6 += l6 * k8;
				if (i != j && i8 < i7 || i == j && i8 > k7) {
					k -= j;
					j -= i;
					i = scanOffsets[i];
					while (--j >= 0) {
						method379_2(pixels, ai, i, j1 >> 16, l >> 16, i2 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
						j1 += i8;
						l += i7;
						i2 += j8;
						k1 += j7;
						i += width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					while (--k >= 0) {
						method379_2(pixels, ai, i, j1 >> 16, i1 >> 16, i2 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
						j1 += i8;
						i1 += k7;
						i2 += j8;
						l1 += l7;
						i += width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					return;
				}
				k -= j;
				j -= i;
				i = scanOffsets[i];
				while (--j >= 0) {
					method379_2(pixels, ai, i, l >> 16, j1 >> 16, k1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
					j1 += i8;
					l += i7;
					i2 += j8;
					k1 += j7;
					i += width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--k >= 0) {
					method379_2(pixels, ai, i, i1 >> 16, j1 >> 16, l1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
					j1 += i8;
					i1 += k7;
					i2 += j8;
					l1 += l7;
					i += width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			i1 = l <<= 16;
			l1 = k1 <<= 16;
			if (i < 0) {
				i1 -= i8 * i;
				l -= i7 * i;
				l1 -= j8 * i;
				k1 -= j7 * i;
				i = 0;
			}
			j1 <<= 16;
			i2 <<= 16;
			if (k < 0) {
				j1 -= k7 * k;
				i2 -= l7 * k;
				k = 0;
			}
			int l8 = i - originViewY;
			l4 += j5 * l8;
			k5 += i6 * l8;
			j6 += l6 * l8;
			if (i != k && i8 < i7 || i == k && k7 > i7) {
				j -= k;
				k -= i;
				i = scanOffsets[i];
				while (--k >= 0) {
					method379_2(pixels, ai, i, i1 >> 16, l >> 16, l1 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
					i1 += i8;
					l += i7;
					l1 += j8;
					k1 += j7;
					i += width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--j >= 0) {
					method379_2(pixels, ai, i, j1 >> 16, l >> 16, i2 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
					j1 += k7;
					l += i7;
					i2 += l7;
					k1 += j7;
					i += width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			j -= k;
			k -= i;
			i = scanOffsets[i];
			while (--k >= 0) {
				method379_2(pixels, ai, i, l >> 16, i1 >> 16, k1 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
				i1 += i8;
				l += i7;
				l1 += j8;
				k1 += j7;
				i += width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--j >= 0) {
				method379_2(pixels, ai, i, l >> 16, j1 >> 16, k1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
				j1 += k7;
				l += i7;
				i2 += l7;
				k1 += j7;
				i += width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		if (j <= k) {
			if (j >= clip_bottom)
				return;
			if (k > clip_bottom)
				k = clip_bottom;
			if (i > clip_bottom)
				i = clip_bottom;
			if (k < i) {
				l = i1 <<= 16;
				k1 = l1 <<= 16;
				if (j < 0) {
					l -= i7 * j;
					i1 -= k7 * j;
					k1 -= j7 * j;
					l1 -= l7 * j;
					j = 0;
				}
				j1 <<= 16;
				i2 <<= 16;
				if (k < 0) {
					j1 -= i8 * k;
					i2 -= j8 * k;
					k = 0;
				}
				int i9 = j - originViewY;
				l4 += j5 * i9;
				k5 += i6 * i9;
				j6 += l6 * i9;
				if (j != k && i7 < k7 || j == k && i7 > i8) {
					i -= k;
					k -= j;
					j = scanOffsets[j];
					while (--k >= 0) {
						method379_2(pixels, ai, j, l >> 16, i1 >> 16, k1 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
						l += i7;
						i1 += k7;
						k1 += j7;
						l1 += l7;
						j += width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					while (--i >= 0) {
						method379_2(pixels, ai, j, l >> 16, j1 >> 16, k1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
						l += i7;
						j1 += i8;
						k1 += j7;
						i2 += j8;
						j += width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					return;
				}
				i -= k;
				k -= j;
				j = scanOffsets[j];
				while (--k >= 0) {
					method379_2(pixels, ai, j, i1 >> 16, l >> 16, l1 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
					l += i7;
					i1 += k7;
					k1 += j7;
					l1 += l7;
					j += width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--i >= 0) {
					method379_2(pixels, ai, j, j1 >> 16, l >> 16, i2 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
					l += i7;
					j1 += i8;
					k1 += j7;
					i2 += j8;
					j += width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			j1 = i1 <<= 16;
			i2 = l1 <<= 16;
			if (j < 0) {
				j1 -= i7 * j;
				i1 -= k7 * j;
				i2 -= j7 * j;
				l1 -= l7 * j;
				j = 0;
			}
			l <<= 16;
			k1 <<= 16;
			if (i < 0) {
				l -= i8 * i;
				k1 -= j8 * i;
				i = 0;
			}
			int j9 = j - originViewY;
			l4 += j5 * j9;
			k5 += i6 * j9;
			j6 += l6 * j9;
			if (i7 < k7) {
				k -= i;
				i -= j;
				j = scanOffsets[j];
				while (--i >= 0) {
					method379_2(pixels, ai, j, j1 >> 16, i1 >> 16, i2 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
					j1 += i7;
					i1 += k7;
					i2 += j7;
					l1 += l7;
					j += width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--k >= 0) {
					method379_2(pixels, ai, j, l >> 16, i1 >> 16, k1 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
					l += i8;
					i1 += k7;
					k1 += j8;
					l1 += l7;
					j += width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			k -= i;
			i -= j;
			j = scanOffsets[j];
			while (--i >= 0) {
				method379_2(pixels, ai, j, i1 >> 16, j1 >> 16, l1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
				j1 += i7;
				i1 += k7;
				i2 += j7;
				l1 += l7;
				j += width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--k >= 0) {
				method379_2(pixels, ai, j, i1 >> 16, l >> 16, l1 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
				l += i8;
				i1 += k7;
				k1 += j8;
				l1 += l7;
				j += width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		if (k >= clip_bottom)
			return;
		if (i > clip_bottom)
			i = clip_bottom;
		if (j > clip_bottom)
			j = clip_bottom;
		if (i < j) {
			i1 = j1 <<= 16;
			l1 = i2 <<= 16;
			if (k < 0) {
				i1 -= k7 * k;
				j1 -= i8 * k;
				l1 -= l7 * k;
				i2 -= j8 * k;
				k = 0;
			}
			l <<= 16;
			k1 <<= 16;
			if (i < 0) {
				l -= i7 * i;
				k1 -= j7 * i;
				i = 0;
			}
			int k9 = k - originViewY;
			l4 += j5 * k9;
			k5 += i6 * k9;
			j6 += l6 * k9;
			if (k7 < i8) {
				j -= i;
				i -= k;
				k = scanOffsets[k];
				while (--i >= 0) {
					method379_2(pixels, ai, k, i1 >> 16, j1 >> 16, l1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
					i1 += k7;
					j1 += i8;
					l1 += l7;
					i2 += j8;
					k += width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--j >= 0) {
					method379_2(pixels, ai, k, i1 >> 16, l >> 16, l1 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
					i1 += k7;
					l += i7;
					l1 += l7;
					k1 += j7;
					k += width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			j -= i;
			i -= k;
			k = scanOffsets[k];
			while (--i >= 0) {
				method379_2(pixels, ai, k, j1 >> 16, i1 >> 16, i2 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
				i1 += k7;
				j1 += i8;
				l1 += l7;
				i2 += j8;
				k += width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--j >= 0) {
				method379_2(pixels, ai, k, l >> 16, i1 >> 16, k1 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
				i1 += k7;
				l += i7;
				l1 += l7;
				k1 += j7;
				k += width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		l = j1 <<= 16;
		k1 = i2 <<= 16;
		if (k < 0) {
			l -= k7 * k;
			j1 -= i8 * k;
			k1 -= l7 * k;
			i2 -= j8 * k;
			k = 0;
		}
		i1 <<= 16;
		l1 <<= 16;
		if (j < 0) {
			i1 -= i7 * j;
			l1 -= j7 * j;
			j = 0;
		}
		int l9 = k - originViewY;
		l4 += j5 * l9;
		k5 += i6 * l9;
		j6 += l6 * l9;
		if (k7 < i8) {
			i -= j;
			j -= k;
			k = scanOffsets[k];
			while (--j >= 0) {
				method379_2(pixels, ai, k, l >> 16, j1 >> 16, k1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
				l += k7;
				j1 += i8;
				k1 += l7;
				i2 += j8;
				k += width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--i >= 0) {
				method379_2(pixels, ai, k, i1 >> 16, j1 >> 16, l1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
				i1 += i7;
				j1 += i8;
				l1 += j7;
				i2 += j8;
				k += width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		i -= j;
		j -= k;
		k = scanOffsets[k];
		while (--j >= 0) {
			method379_2(pixels, ai, k, j1 >> 16, l >> 16, i2 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
			l += k7;
			j1 += i8;
			k1 += l7;
			i2 += j8;
			k += width;
			l4 += j5;
			k5 += i6;
			j6 += l6;
		}
		while (--i >= 0) {
			method379_2(pixels, ai, k, j1 >> 16, i1 >> 16, i2 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
			i1 += i7;
			j1 += i8;
			l1 += j7;
			i2 += j8;
			k += width;
			l4 += j5;
			k5 += i6;
			j6 += l6;
		}
		} catch(Exception e) {
		}
	}

	private static void method379_2(int ai[], int ai1[], int k, int x1, int x2, int lig1, int lig2, int l1, int i2, int j2, int k2, int l2, int i3) {
		int i = 0;// was parameter
		int j = 0;// was parameter
		if (x1 >= x2)
			return;
		int dlig = (lig2 - lig1) / (x2 - x1);
		int k3;
		if (textureOutOfDrawingBounds) {
			if (x2 > bottomX)
				x2 = bottomX;
			if (x1 < 0) {
				lig1 -= x1 * dlig;
				x1 = 0;
			}
			if (x1 >= x2)
				return;
			k3 = x2 - x1 >> 3;
		} else {
			if (x2 - x1 > 7) {
				k3 = x2 - x1 >> 3;
			} else {
				k3 = 0;
			}
		}
		k += x1;
		int j4 = 0;
		int l4 = 0;
		int l6 = x1 - originViewX;
		l1 += (k2 >> 3) * l6;
		i2 += (l2 >> 3) * l6;
		j2 += (i3 >> 3) * l6;
		int l5 = j2 >> 14;
		if (l5 != 0) {
			i = l1 / l5;
			j = i2 / l5;
			if (i < 0)
				i = 0;
			else if (i > 16256)
				i = 16256;
		}
		l1 += k2;
		i2 += l2;
		j2 += i3;
		l5 = j2 >> 14;
		if (l5 != 0) {
			j4 = l1 / l5;
			l4 = i2 / l5;
			if (j4 < 7)
				j4 = 7;
			else if (j4 > 16256)
				j4 = 16256;
		}
		int j7 = j4 - i >> 3;
		int l7 = l4 - j >> 3;
		if (aBoolean1463) {
			while (k3-- > 0) {
				int i9;
				int l;
				i9 = ai1[texelPos((j & 0x3f80) + (i >> 7))];
				l = lig1 >> 8;
				ai[k++] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 7;
				i += j7;
				j += l7;
				lig1 += dlig;
				i9 = ai1[texelPos((j & 0x3f80) + (i >> 7))];
				l = lig1 >> 8;
				ai[k++] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 7;
				i += j7;
				j += l7;
				lig1 += dlig;
				i9 = ai1[texelPos((j & 0x3f80) + (i >> 7))];
				l = lig1 >> 8;
				ai[k++] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 7;
				i += j7;
				j += l7;
				lig1 += dlig;
				i9 = ai1[texelPos((j & 0x3f80) + (i >> 7))];
				l = lig1 >> 8;
				ai[k++] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 7;
				i += j7;
				j += l7;
				lig1 += dlig;
				i9 = ai1[texelPos((j & 0x3f80) + (i >> 7))];
				l = lig1 >> 8;
				ai[k++] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 7;
				i += j7;
				j += l7;
				lig1 += dlig;
				i9 = ai1[texelPos((j & 0x3f80) + (i >> 7))];
				l = lig1 >> 8;
				ai[k++] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 7;
				i += j7;
				j += l7;
				lig1 += dlig;
				i9 = ai1[texelPos((j & 0x3f80) + (i >> 7))];
				l = lig1 >> 8;
				ai[k++] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 7;
				i += j7;
				j += l7;
				lig1 += dlig;
				i9 = ai1[texelPos((j & 0x3f80) + (i >> 7))];
				l = lig1 >> 8;
				ai[k++] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 7;
				i = j4;
				j = l4;
				lig1 += dlig;
				l1 += k2;
				i2 += l2;
				j2 += i3;
				int i6 = j2 >> 14;
				if (i6 != 0) {
					j4 = l1 / i6;
					l4 = i2 / i6;
					if (j4 < 7)
						j4 = 7;
					else if (j4 > 16256)
						j4 = 16256;
				}
				j7 = j4 - i >> 3;
				l7 = l4 - j >> 3;
			}
			for (k3 = x2 - x1 & 7; k3-- > 0;) {
				int j9;
				int l;
				j9 = ai1[texelPos((j & 0x3f80) + (i >> 7))];
				l = lig1 >> 8;
				ai[k++] = ((j9 & 0xff00ff) * l & ~0xff00ff) + ((j9 & 0xff00) * l & 0xff0000) >> 7;
				i += j7;
				j += l7;
				lig1 += dlig;
			}

			return;
		}
		while (k3-- > 0) {
			int i9;
			int l;
			if ((i9 = ai1[texelPos((j & 0x3f80) + (i >> 7))]) != 0) {
				l = lig1 >> 8;
				ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 7;
			}
			k++;
			i += j7;
			j += l7;
			lig1 += dlig;
			if ((i9 = ai1[texelPos((j & 0x3f80) + (i >> 7))]) != 0) {
				l = lig1 >> 8;
				ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 7;
			}
			k++;
			i += j7;
			j += l7;
			lig1 += dlig;
			if ((i9 = ai1[texelPos((j & 0x3f80) + (i >> 7))]) != 0) {
				l = lig1 >> 8;
				ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 7;
			}
			k++;
			i += j7;
			j += l7;
			lig1 += dlig;
			if ((i9 = ai1[texelPos((j & 0x3f80) + (i >> 7))]) != 0) {
				l = lig1 >> 8;
				ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 7;
			}
			k++;
			i += j7;
			j += l7;
			lig1 += dlig;
			if ((i9 = ai1[texelPos((j & 0x3f80) + (i >> 7))]) != 0) {
				l = lig1 >> 8;
				ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 7;
			}
			k++;
			i += j7;
			j += l7;
			lig1 += dlig;
			if ((i9 = ai1[texelPos((j & 0x3f80) + (i >> 7))]) != 0) {
				l = lig1 >> 8;
				ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 7;
			}
			k++;
			i += j7;
			j += l7;
			lig1 += dlig;
			if ((i9 = ai1[texelPos((j & 0x3f80) + (i >> 7))]) != 0) {
				l = lig1 >> 8;
				ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 7;
			}
			k++;
			i += j7;
			j += l7;
			lig1 += dlig;
			if ((i9 = ai1[texelPos((j & 0x3f80) + (i >> 7))]) != 0) {
				l = lig1 >> 8;
				ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 7;
			}
			k++;
			i = j4;
			j = l4;
			lig1 += dlig;
			l1 += k2;
			i2 += l2;
			j2 += i3;
			int j6 = j2 >> 14;
			if (j6 != 0) {
				j4 = l1 / j6;
				l4 = i2 / j6;
				if (j4 < 7)
					j4 = 7;
				else if (j4 > 16256)
					j4 = 16256;
			}
			j7 = j4 - i >> 3;
			l7 = l4 - j >> 3;
		}
		for (int l3 = x2 - x1 & 7; l3-- > 0;) {
			int j9;
			int l;
			if ((j9 = ai1[texelPos((j & 0x3f80) + (i >> 7))]) != 0) {
				l = lig1 >> 8;
				ai[k] = ((j9 & 0xff00ff) * l & ~0xff00ff) + ((j9 & 0xff00) * l & 0xff0000) >> 7;
			}
			k++;
			i += j7;
			j += l7;
			lig1 += dlig;
		}
	}

	public static int textureAmount = 117;
	public static boolean lowMem = true;
	public static boolean textureOutOfDrawingBounds;
	private static int mipMapLevel;
	private static boolean aBoolean1463;
	public static boolean aBoolean1464 = true;
	public static int alpha;
	public static int originViewX;
	public static int originViewY;
	private static int[] anIntArray1468;
	public static final int[] anIntArray1469;
	public static int anIntArray1470[];
	public static int anIntArray1471[];
	public static int scanOffsets[];
	private static int textureCount;
	public static IndexedImage textures[] = new IndexedImage[textureAmount];
	private static boolean[] textureIsTransparant = new boolean[textureAmount];
	private static int[] anIntArray1476 = new int[textureAmount];
	private static int textureRequestBufferPointer;
	private static int[][] textureRequestPixelBuffer;
	private static int[][] texturesPixelBuffer = new int[textureAmount][];
	public static int textureLastUsed[] = new int[textureAmount];
	public static int lastTextureRetrievalCount;
	public static int hslToRgb[] = new int[0x10000];
	private static int[][] currentPalette = new int[textureAmount][];

	static  {
		anIntArray1468 = new int[512];
		anIntArray1469 = new int[2048];
		anIntArray1470 = new int[2048];
		anIntArray1471 = new int[2048];
		for(int i = 1; i < 512; i++) {
			anIntArray1468[i] = 32768 / i;
		}
		for(int j = 1; j < 2048; j++) {
			anIntArray1469[j] = 0x10000 / j;
		}
		for(int k = 0; k < 2048; k++) {
			anIntArray1470[k] = (int)(65536D * Math.sin((double)k * 0.0030679614999999999D));
			anIntArray1471[k] = (int)(65536D * Math.cos((double)k * 0.0030679614999999999D));
		}
	}
}