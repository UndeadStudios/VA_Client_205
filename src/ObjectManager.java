import java.util.ArrayList;

final class ObjectManager {

	public ObjectManager(byte abyte0[][][], int ai[][][]) {
		highestPlane = 99;
		anInt146 = 104;
		anInt147 = 104;
		tileHeights = ai;
		tileSettings = abyte0;
		aByteArrayArrayArray142 = new byte[4][anInt146][anInt147];
		aByteArrayArrayArray130 = new byte[4][anInt146][anInt147];
		aByteArrayArrayArray136 = new byte[4][anInt146][anInt147];
		aByteArrayArrayArray148 = new byte[4][anInt146][anInt147];
		anIntArrayArrayArray135 = new int[4][anInt146 + 1][anInt147 + 1];
		aByteArrayArrayArray134 = new byte[4][anInt146 + 1][anInt147 + 1];
		anIntArrayArray139 = new int[anInt146 + 1][anInt147 + 1];
		anIntArray124 = new int[anInt147];
		anIntArray125 = new int[anInt147];
		anIntArray126 = new int[anInt147];
		anIntArray127 = new int[anInt147];
		anIntArray128 = new int[anInt147];
	}

	private static int method170(int i, int j) {
		int k = i + j * 57;
		k = k << 13 ^ k;
		int l = k * (k * k * 15731 + 0xc0ae5) + 0x5208dd0d & 0x7fffffff;
		return l >> 19 & 0xff;
	}

	public final void method171(Class11 aclass11[],
			WorldController worldController) {
		for (int j = 0; j < 4; j++) {
			for (int k = 0; k < 104; k++) {
				for (int i1 = 0; i1 < 104; i1++)
					if ((tileSettings[j][k][i1] & 1) == 1) {
						int k1 = j;
						if ((tileSettings[1][k][i1] & 2) == 2)
							k1--;
						if (k1 >= 0)
							aclass11[k1].method213(i1, k);
					}


			}


		}

		 for (int l = 0; l < 4; l++) {
             byte shadowIntensity[][] = aByteArrayArrayArray134[l];
             byte directional_light_initial_intensity = 96;
             char specular_distribution_factor = '\u0300'; 
             byte directional_light_x = -50; 
             byte directional_light_z = -10;
             byte directional_light_y = -50;
             int directional_light_length = (int) Math.sqrt(directional_light_x * directional_light_x + directional_light_z * directional_light_z + directional_light_y * directional_light_y);
             int specular_distribution = specular_distribution_factor * directional_light_length >> 8;
             for (int y = 1; y < anInt147 - 1; y++) {
                 for (int x = 1; x < anInt146 - 1; x++) {
                     int x_height_difference =  tileHeights[l][x + 1][y] - tileHeights[l][x - 1][y];
                     int y_height_difference =  tileHeights[l][x][y + 1] - tileHeights[l][x][y - 1];
                     int normal_length = (int) Math.sqrt(x_height_difference * x_height_difference + 0x10000 + y_height_difference * y_height_difference);
                     int normalized_normal_x = (x_height_difference << 8) / normal_length;
                     int normalized_normal_z = 0x10000 / normal_length;
                     int normalized_normal_y = (y_height_difference << 8) / normal_length;
                     int directional_light_intensity = directional_light_initial_intensity + (directional_light_x * normalized_normal_x + directional_light_z * normalized_normal_z + directional_light_y * normalized_normal_y) / specular_distribution;
                     int weighted_shadow_intensity = (shadowIntensity[x - 1][y] >> 2) + (shadowIntensity[x + 1][y] >> 3) + (shadowIntensity[x][y - 1] >> 2) + (shadowIntensity[x][y + 1] >> 3) + (shadowIntensity[x][y] >> 1);
                     anIntArrayArray139[x][y] = directional_light_intensity - weighted_shadow_intensity;
                 }
             }


		for (int k5 = 0; k5 < anInt147; k5++) {
			anIntArray124[k5] = 0;
			anIntArray125[k5] = 0;
			anIntArray126[k5] = 0;
			anIntArray127[k5] = 0;
			anIntArray128[k5] = 0;
		}


		for (int l6 = -5; l6 < anInt146 + 5; l6++) {
			for (int i8 = 0; i8 < anInt147; i8++) {
				int k9 = l6 + 5;
				if (k9 >= 0 && k9 < anInt146) {
					int l12 = aByteArrayArrayArray142[l][k9][i8] & 0xff;
					if (l12 > 0) {
						if (l12 > FloorDefinition.underlays.length) {
							l12 = FloorDefinition.underlays.length;
						}
						FloorDefinition flo = FloorDefinition.underlays[l12 - 1];
						anIntArray124[i8] += flo.blendHue;
						anIntArray125[i8] += flo.saturation;
						anIntArray126[i8] += flo.luminance;
						anIntArray127[i8] += flo.blendHueMultiplier;
						anIntArray128[i8]++;
					}
				}
				int i13 = l6 - 5;
				if (i13 >= 0 && i13 < anInt146) {
					int i14 = aByteArrayArrayArray142[l][i13][i8] & 0xff;
					if (i14 > 0) {
						FloorDefinition flo_1 = FloorDefinition.underlays[i14 - 1];
						anIntArray124[i8] -= flo_1.blendHue;
						anIntArray125[i8] -= flo_1.saturation;
						anIntArray126[i8] -= flo_1.luminance;
						anIntArray127[i8] -= flo_1.blendHueMultiplier;
						anIntArray128[i8]--;
					}
				}
			}


			if (l6 >= 1 && l6 < anInt146 - 1) {
				int l9 = 0;
				int j13 = 0;
				int j14 = 0;
				int k15 = 0;
				int k16 = 0;
				for (int k17 = -5; k17 < anInt147 + 5; k17++) {
					int j18 = k17 + 5;
					if (j18 >= 0 && j18 < anInt147) {
						l9 += anIntArray124[j18];
						j13 += anIntArray125[j18];
						j14 += anIntArray126[j18];
						k15 += anIntArray127[j18];
						k16 += anIntArray128[j18];
					}
					int k18 = k17 - 5;
					if (k18 >= 0 && k18 < anInt147) {
						l9 -= anIntArray124[k18];
						j13 -= anIntArray125[k18];
						j14 -= anIntArray126[k18];
						k15 -= anIntArray127[k18];
						k16 -= anIntArray128[k18];
					}
					if (k17 >= 1
							&& k17 < anInt147 - 1
							&& (!lowMem
									|| (tileSettings[0][l6][k17] & 2) != 0 || (tileSettings[l][l6][k17] & 0x10) == 0
									&& method182(k17, l, l6) == anInt131)) {
						if (l < highestPlane)
							highestPlane = l;
						int l18 = aByteArrayArrayArray142[l][l6][k17] & 0xff;
						int i19 = aByteArrayArrayArray130[l][l6][k17] & 0xff;
						if (l18 > 0 || i19 > 0) {
							int j19 = tileHeights[l][l6][k17];
							int k19 = tileHeights[l][l6 + 1][k17];
							int l19 = tileHeights[l][l6 + 1][k17 + 1];
							int i20 = tileHeights[l][l6][k17 + 1];
							int j20 = anIntArrayArray139[l6][k17];
							int k20 = anIntArrayArray139[l6 + 1][k17];
							int l20 = anIntArrayArray139[l6 + 1][k17 + 1];
							int i21 = anIntArrayArray139[l6][k17 + 1];
							int j21 = -1;
							int k21 = -1;
							if (l18 > 0) {
								int l21 = (l9 * 256) / k15;
								int j22 = j13 / k16;
								int l22 = j14 / k16;
								j21 = encode(l21, j22, l22);
		
								if (l22 < 0)
									l22 = 0;
								else if (l22 > 255)
									l22 = 255;
								k21 = encode(l21, j22, l22);
							}
							if (l > 0) {
								boolean flag = true;
								if (l18 == 0
										&& aByteArrayArrayArray136[l][l6][k17] != 0)
									flag = false;
								if (i19 > 0
										&& !FloorDefinition.overlays[i19 - 1].occlude)
									flag = false;
								if (flag && j19 == k19 && j19 == l19
										&& j19 == i20)
									anIntArrayArrayArray135[l][l6][k17] |= 0x924;
							}
							int i22 = 0;
							if (j21 != -1)
								i22 = Rasterizer3D.hslToRgb[method187(k21,
										96)];
							if (i19 == 0) {
								worldController.method279(l, l6, k17, 0, 0,
										-1, 154, j19, k19, l19, i20,
										method187(j21, j20),
										method187(j21, k20),
										method187(j21, l20),
										method187(j21, i21), 0, 0, 0, 0,
										i22, 0, false);
							} else {
								int k22 = aByteArrayArrayArray136[l][l6][k17] + 1;
								byte byte4 = aByteArrayArrayArray148[l][l6][k17];
								//Flo2 flo_2 = Flo2.flo2[i19 - 1];
								if (i19 - 1 > FloorDefinition.overlays.length) {
									i19 = FloorDefinition.overlays.length;
								}
								FloorDefinition overlay_flo = FloorDefinition.overlays[i19 - 1];
								int textureId = overlay_flo.texture;
								int j23;
								int k23;
								if (textureId > 50) {
									textureId = 3;
								}
								if (textureId >= 0) {
									k23 = Rasterizer3D.getOverallColour(textureId);
									j23 = -1;
								} else if (overlay_flo.rgb == 0xff00ff) {
									k23 = 0;
									j23 = -2;
									textureId = -1;
								} else if(overlay_flo.rgb == 0x333333) {
									k23 = Rasterizer3D.hslToRgb[method185(overlay_flo.hsl16, 96)];
									j23 = -2;
									textureId = -1;
								} else if((i19-1) == 63) {
									k23 = overlay_flo.rgb = 0x767676;
									j23 = -2;
									textureId = -1;
								} else {
									j23 = encode(overlay_flo.hue, overlay_flo.saturation, overlay_flo.luminance);
									k23 = Rasterizer3D.hslToRgb[method185(overlay_flo.hsl16, 96)];
								}
								colors.add(k23);
								worldController.method279(l, l6, k17, k22,
										byte4, textureId, 154, j19, k19,
										l19, i20, method187(j21, j20),
										method187(j21, k20),
										method187(j21, l20),
										method187(j21, i21),
										method185(j23, j20),
										method185(j23, k20),
										method185(j23, l20),
										method185(j23, i21), i22,
										k23, textureId >= 0
												&& textureId <= 50);
                                }
						}
					}
				}
			}
		}


		for (int j8 = 1; j8 < anInt147 - 1; j8++) {
			for (int i10 = 1; i10 < anInt146 - 1; i10++)
				worldController
				.method278(l, i10, j8, method182(j8, l, i10));


		}


		}


		worldController.method305(-10, -50, -50);
		for (int j1 = 0; j1 < anInt146; j1++) {
			for (int l1 = 0; l1 < anInt147; l1++)
				if ((tileSettings[1][j1][l1] & 2) == 2)
					worldController.method276(l1, j1);


		}


		int i2 = 1;
		int j2 = 2;
		int k2 = 4;
		for (int l2 = 0; l2 < 4; l2++) {
			if (l2 > 0) {
				i2 <<= 3;
				j2 <<= 3;
				k2 <<= 3;
			}
			for (int i3 = 0; i3 <= l2; i3++) {
				for (int k3 = 0; k3 <= anInt147; k3++) {
					for (int i4 = 0; i4 <= anInt146; i4++) {
						if ((anIntArrayArrayArray135[i3][i4][k3] & i2) != 0) {
							int k4 = k3;
							int l5 = k3;
							int i7 = i3;
							int k8 = i3;
							for (; k4 > 0
							&& (anIntArrayArrayArray135[i3][i4][k4 - 1] & i2) != 0; k4--)
								;
							for (; l5 < anInt147
							&& (anIntArrayArrayArray135[i3][i4][l5 + 1] & i2) != 0; l5++)
								;
							label0: for (; i7 > 0; i7--) {
								for (int j10 = k4; j10 <= l5; j10++)
									if ((anIntArrayArrayArray135[i7 - 1][i4][j10] & i2) == 0)
										break label0;


							}


							label1: for (; k8 < l2; k8++) {
								for (int k10 = k4; k10 <= l5; k10++)
									if ((anIntArrayArrayArray135[k8 + 1][i4][k10] & i2) == 0)
										break label1;


							}


							int l10 = ((k8 + 1) - i7) * ((l5 - k4) + 1);
							if (l10 >= 8) {
								char c1 = '\360';
								int k14 = tileHeights[k8][i4][k4]
								                                          - c1;
								int l15 = tileHeights[i7][i4][k4];
								WorldController.method277(l2, i4 * 128, l15,
										i4 * 128, l5 * 128 + 128, k14,
										k4 * 128, 1);
								for (int l16 = i7; l16 <= k8; l16++) {
									for (int l17 = k4; l17 <= l5; l17++)
										anIntArrayArrayArray135[l16][i4][l17] &= ~i2;


								}


							}
						}
						if ((anIntArrayArrayArray135[i3][i4][k3] & j2) != 0) {
							int l4 = i4;
							int i6 = i4;
							int j7 = i3;
							int l8 = i3;
							for (; l4 > 0
							&& (anIntArrayArrayArray135[i3][l4 - 1][k3] & j2) != 0; l4--)
								;
							for (; i6 < anInt146
							&& (anIntArrayArrayArray135[i3][i6 + 1][k3] & j2) != 0; i6++)
								;
							label2: for (; j7 > 0; j7--) {
								for (int i11 = l4; i11 <= i6; i11++)
									if ((anIntArrayArrayArray135[j7 - 1][i11][k3] & j2) == 0)
										break label2;


							}


							label3: for (; l8 < l2; l8++) {
								for (int j11 = l4; j11 <= i6; j11++)
									if ((anIntArrayArrayArray135[l8 + 1][j11][k3] & j2) == 0)
										break label3;


							}


							int k11 = ((l8 + 1) - j7) * ((i6 - l4) + 1);
							if (k11 >= 8) {
								char c2 = '\360';
								int l14 = tileHeights[l8][l4][k3]
								                                          - c2;
								int i16 = tileHeights[j7][l4][k3];
								WorldController.method277(l2, l4 * 128, i16,
										i6 * 128 + 128, k3 * 128, l14,
										k3 * 128, 2);
								for (int i17 = j7; i17 <= l8; i17++) {
									for (int i18 = l4; i18 <= i6; i18++)
										anIntArrayArrayArray135[i17][i18][k3] &= ~j2;


								}


							}
						}
						if ((anIntArrayArrayArray135[i3][i4][k3] & k2) != 0) {
							int i5 = i4;
							int j6 = i4;
							int k7 = k3;
							int i9 = k3;
							for (; k7 > 0
							&& (anIntArrayArrayArray135[i3][i4][k7 - 1] & k2) != 0; k7--)
								;
							for (; i9 < anInt147
							&& (anIntArrayArrayArray135[i3][i4][i9 + 1] & k2) != 0; i9++)
								;
							label4: for (; i5 > 0; i5--) {
								for (int l11 = k7; l11 <= i9; l11++)
									if ((anIntArrayArrayArray135[i3][i5 - 1][l11] & k2) == 0)
										break label4;


							}


							label5: for (; j6 < anInt146; j6++) {
								for (int i12 = k7; i12 <= i9; i12++)
									if ((anIntArrayArrayArray135[i3][j6 + 1][i12] & k2) == 0)
										break label5;


							}


							if (((j6 - i5) + 1) * ((i9 - k7) + 1) >= 4) {
								int j12 = tileHeights[i3][i5][k7];
								WorldController.method277(l2, i5 * 128, j12,
										j6 * 128 + 128, i9 * 128 + 128, j12,
										k7 * 128, 4);
								for (int k13 = i5; k13 <= j6; k13++) {
									for (int i15 = k7; i15 <= i9; i15++)
										anIntArrayArrayArray135[i3][k13][i15] &= ~k2;


								}
							}
						}
					}
				}
			}
		}
	}

	private static int method172(int i, int j) {
		int k = (method176(i + 45365, j + 0x16713, 4) - 128) + (method176(i + 10294, j + 37821, 2) - 128 >> 1) + (method176(i, j, 1) - 128 >> 2);
		k = (int) ((double) k * 0.29999999999999999D) + 35;
		if (k < 10)
			k = 10;
		else if (k > 60)
			k = 60;
		return k;
	}

	public static void method173(Buffer stream, OnDemandFetcher class42_sub1) {
		label0: {
			int i = -1;
			do {
				int j = stream.readUSmart2();
				if (j == 0)
					break label0;
				i += j;
				ObjectDefinition class46 = ObjectDefinition.forID(i);
				class46.method574(class42_sub1);
				do {
					int k = stream.method422();
					if (k == 0)
						break;
					stream.readUnsignedByte();
				} while (true);
			} while (true);
		}
	}

	public final void method174(int i, int j, int l, int i1) {
		for (int j1 = i; j1 <= i + j; j1++) {
			for (int k1 = i1; k1 <= i1 + l; k1++)
				if (k1 >= 0 && k1 < anInt146 && j1 >= 0 && j1 < anInt147) {
					aByteArrayArrayArray134[0][k1][j1] = 127;
					if (k1 == i1 && k1 > 0)
						tileHeights[0][k1][j1] = tileHeights[0][k1 - 1][j1];
					if (k1 == i1 + l && k1 < anInt146 - 1)
						tileHeights[0][k1][j1] = tileHeights[0][k1 + 1][j1];
					if (j1 == i && j1 > 0)
						tileHeights[0][k1][j1] = tileHeights[0][k1][j1 - 1];
					if (j1 == i + j && j1 < anInt147 - 1)
						tileHeights[0][k1][j1] = tileHeights[0][k1][j1 + 1];
				}

		}
	}

	private void method175(int y, WorldController worldController, Class11 class11, int type, int plane, int x, int object_id, int orientation) {
		if (lowMem && (tileSettings[0][x][y] & 2) == 0) {
			if ((tileSettings[plane][x][y] & 0x10) != 0)
				return;
			if (method182(y, plane, x) != anInt131)
				return;
		}
		if (plane < highestPlane)
			highestPlane = plane;
		ObjectDefinition class46 = ObjectDefinition.forID(object_id);
		int size1;
		int size2;
		if (orientation == 1 || orientation == 3) {
			size1 = class46.anInt761;//objectSizeY
			size2 = class46.anInt744;//objectSizeX
		} else {
			size1 = class46.anInt744;
			size2 = class46.anInt761;
		}
		int modX;
		int modX1;
		if (104 >= (size1 + x)) {
			modX1 = x + (size1 + 1 >> 1);
			modX = x + (size1 >> 1);
		} else {
			modX = x;
			modX1 = x + 1;
		}
		int modY;
		int modY1;
		if (104>= (size2 + y)) {
			modY1 = y + (size2 + 1 >> 1);
			modY = (size2 >> 1) + y;
		} else {
			modY = y;
			modY1 = 1 + y;
		}
		int a_y = tileHeights[plane %= 4][modX][modY];
		int b_y = tileHeights[plane][modX1][modY];
		int d_y = tileHeights[plane][modX1][modY1];
		int c_y = tileHeights[plane][modX][modY1];
		int k2 = a_y + b_y + d_y + c_y >> 2;
		long key = (long) (orientation << 20 | type << 14 | (y << 7 | x) + 0x40000000);

		//object - wilderness ditch | example
		//value    //1073916122            //key start                            (original)
		//1078110426            //key |= 0x400000L;                    (key + 4194304)
		//99949262055642         //key |= (long) object_id << 32        (key + (object_id << 32))
		//                        //99949262055642 >> 32 == 23271        (key >> 32 == object_id)

		if (!class46.isInteractive) {
			key |= ~0x7fffffffffffffffL;
		}
		if (class46.supportItems == 1) {
			key |= 0x400000L;
		}
		key |= (long) object_id << 32;
		byte byte0 = (byte) ((orientation << 6) + type);
		if (type == 22) {
			if (!Configuration.enableGroundDecors && !class46.isInteractive && !class46.aBoolean736)
				return;
			Object obj;
			if (class46.animation == -1 && class46.childrenIDs == null)
				obj = class46.method578(22, orientation, a_y, b_y, d_y, c_y, -1);
			else
				obj = new Animable_Sub5(object_id, orientation, 22, b_y, d_y, a_y, c_y, class46.animation, true);
			worldController.method280(plane, k2, y, ((Animable) (obj)), byte0, key, x);
			if (class46.aBoolean767 && class46.isInteractive && class11 != null)
				class11.method213(y, x);
			return;
		}
		if (type == 10 || type == 11) {
			Object obj1;
			if (class46.animation == -1 && class46.childrenIDs == null)
				obj1 = class46.method578(10, orientation, a_y, b_y, d_y, c_y, -1);
			else
				obj1 = new Animable_Sub5(object_id, orientation, 10, b_y, d_y, a_y, c_y, class46.animation, true);
			if (obj1 != null) {
				int i5 = 0;
				if (type == 11)
					i5 += 256;
				int j4;
				int l4;
				if (orientation == 1 || orientation == 3) {
					j4 = class46.anInt761;
					l4 = class46.anInt744;
				} else {
					j4 = class46.anInt744;
					l4 = class46.anInt761;
				}
				if (worldController.method284(key, byte0, k2, l4, ((Animable) (obj1)), j4, plane, i5, y, x) && class46.aBoolean779) {
					Model model;
					if (obj1 instanceof Model)
						model = (Model) obj1;
					else
						model = class46.method578(10, orientation, a_y, b_y, d_y, c_y, -1);
					if (model != null) {
						for (int j5 = 0; j5 <= j4; j5++) {
							for (int k5 = 0; k5 <= l4; k5++) {
								int l5 = model.XYZMag / 4;
								if (l5 > 30)
									l5 = 30;
								if (l5 > aByteArrayArrayArray134[plane][x + j5][y + k5])
									aByteArrayArrayArray134[plane][x + j5][y + k5] = (byte) l5;
							}

						}

					}
				}
			}
			if (class46.aBoolean767 && class11 != null)
				class11.method212(class46.aBoolean757, class46.anInt744, class46.anInt761, x, y, orientation);
			return;
		}
		if (type >= 12) {
			Object obj2;
			if (class46.animation == -1 && class46.childrenIDs == null)
				obj2 = class46.method578(type, orientation, a_y, b_y, d_y, c_y, -1);
			else
				obj2 = new Animable_Sub5(object_id, orientation, type, b_y, d_y, a_y, c_y, class46.animation, true);
			worldController.method284(key, byte0, k2, 1, ((Animable) (obj2)), 1, plane, 0, y, x);
			if (type >= 12 && type <= 17 && type != 13 && plane > 0)
				anIntArrayArrayArray135[plane][x][y] |= 0x924;
			if (class46.aBoolean767 && class11 != null)
				class11.method212(class46.aBoolean757, class46.anInt744, class46.anInt761, x, y, orientation);
			return;
		}
		if (type == 0) {
			Object obj3;
			if (class46.animation == -1 && class46.childrenIDs == null)
				obj3 = class46.method578(0, orientation, a_y, b_y, d_y, c_y, -1);
			else
				obj3 = new Animable_Sub5(object_id, orientation, 0, b_y, d_y, a_y, c_y, class46.animation, true);
			worldController.method282(anIntArray152[orientation], ((Animable) (obj3)), key, y, byte0, x, null, k2, 0, plane);
			if (orientation == 0) {
				if (class46.aBoolean779) {
					aByteArrayArrayArray134[plane][x][y] = 50;
					aByteArrayArrayArray134[plane][x][y + 1] = 50;
				}
				if (class46.aBoolean764)
					anIntArrayArrayArray135[plane][x][y] |= 0x249;
			} else if (orientation == 1) {
				if (class46.aBoolean779) {
					aByteArrayArrayArray134[plane][x][y + 1] = 50;
					aByteArrayArrayArray134[plane][x + 1][y + 1] = 50;
				}
				if (class46.aBoolean764)
					anIntArrayArrayArray135[plane][x][y + 1] |= 0x492;
			} else if (orientation == 2) {
				if (class46.aBoolean779) {
					aByteArrayArrayArray134[plane][x + 1][y] = 50;
					aByteArrayArrayArray134[plane][x + 1][y + 1] = 50;
				}
				if (class46.aBoolean764)
					anIntArrayArrayArray135[plane][x + 1][y] |= 0x249;
			} else if (orientation == 3) {
				if (class46.aBoolean779) {
					aByteArrayArrayArray134[plane][x][y] = 50;
					aByteArrayArrayArray134[plane][x + 1][y] = 50;
				}
				if (class46.aBoolean764)
					anIntArrayArrayArray135[plane][x][y] |= 0x492;
			}
			if (class46.aBoolean767 && class11 != null)
				class11.method211(y, orientation, x, type, class46.aBoolean757);
			if (class46.anInt775 != 16)
				worldController.method290(y, class46.anInt775, x, plane);
			return;
		}
		if (type == 1) {
			Object obj4;
			if (class46.animation == -1 && class46.childrenIDs == null)
				obj4 = class46.method578(1, orientation, a_y, b_y, d_y, c_y, -1);
			else
				obj4 = new Animable_Sub5(object_id, orientation, 1, b_y, d_y, a_y, c_y, class46.animation, true);
			worldController.method282(anIntArray140[orientation], ((Animable) (obj4)), key, y, byte0, x, null, k2, 0, plane);
			if (class46.aBoolean779)
				if (orientation == 0)
					aByteArrayArrayArray134[plane][x][y + 1] = 50;
				else if (orientation == 1)
					aByteArrayArrayArray134[plane][x + 1][y + 1] = 50;
				else if (orientation == 2)
					aByteArrayArrayArray134[plane][x + 1][y] = 50;
				else if (orientation == 3)
					aByteArrayArrayArray134[plane][x][y] = 50;
			if (class46.aBoolean767 && class11 != null)
				class11.method211(y, orientation, x, type, class46.aBoolean757);
			return;
		}
		if (type == 2) {
			int i3 = orientation + 1 & 3;
			Object obj11;
			Object obj12;
			if (class46.animation == -1 && class46.childrenIDs == null) {
				obj11 = class46.method578(2, 4 + orientation, a_y, b_y, d_y, c_y, -1);
				obj12 = class46.method578(2, i3, a_y, b_y, d_y, c_y, -1);
			} else {
				obj11 = new Animable_Sub5(object_id, 4 + orientation, 2, b_y, d_y, a_y, c_y, class46.animation, true);
				obj12 = new Animable_Sub5(object_id, i3, 2, b_y, d_y, a_y, c_y, class46.animation, true);
			}
			worldController.method282(anIntArray152[orientation], ((Animable) (obj11)), key, y, byte0, x, ((Animable) (obj12)), k2, anIntArray152[i3], plane);
			if (class46.aBoolean764)
				if (orientation == 0) {
					anIntArrayArrayArray135[plane][x][y] |= 0x249;
					anIntArrayArrayArray135[plane][x][y + 1] |= 0x492;
				} else if (orientation == 1) {
					anIntArrayArrayArray135[plane][x][y + 1] |= 0x492;
					anIntArrayArrayArray135[plane][x + 1][y] |= 0x249;
				} else if (orientation == 2) {
					anIntArrayArrayArray135[plane][x + 1][y] |= 0x249;
					anIntArrayArrayArray135[plane][x][y] |= 0x492;
				} else if (orientation == 3) {
					anIntArrayArrayArray135[plane][x][y] |= 0x492;
					anIntArrayArrayArray135[plane][x][y] |= 0x249;
				}
			if (class46.aBoolean767 && class11 != null)
				class11.method211(y, orientation, x, type, class46.aBoolean757);
			if (class46.anInt775 != 16)
				worldController.method290(y, class46.anInt775, x, plane);
			return;
		}
		if (type == 3) {
			Object obj5;
			if (class46.animation == -1 && class46.childrenIDs == null)
				obj5 = class46.method578(3, orientation, a_y, b_y, d_y, c_y, -1);
			else
				obj5 = new Animable_Sub5(object_id, orientation, 3, b_y, d_y, a_y, c_y, class46.animation, true);
			worldController.method282(anIntArray140[orientation], ((Animable) (obj5)), key, y, byte0, x, null, k2, 0, plane);
			if (class46.aBoolean779)
				if (orientation == 0)
					aByteArrayArrayArray134[plane][x][y + 1] = 50;
				else if (orientation == 1)
					aByteArrayArrayArray134[plane][x + 1][y + 1] = 50;
				else if (orientation == 2)
					aByteArrayArrayArray134[plane][x + 1][y] = 50;
				else if (orientation == 3)
					aByteArrayArrayArray134[plane][x][y] = 50;
			if (class46.aBoolean767 && class11 != null)
				class11.method211(y, orientation, x, type, class46.aBoolean757);
			return;
		}
		if (type == 9) {
			Object obj6;
			if (class46.animation == -1 && class46.childrenIDs == null)
				obj6 = class46.method578(type, orientation, a_y, b_y, d_y, c_y, -1);
			else
				obj6 = new Animable_Sub5(object_id, orientation, type, b_y, d_y, a_y, c_y, class46.animation, true);
			worldController.method284(key, byte0, k2, 1, ((Animable) (obj6)), 1, plane, 0, y, x);
			if (class46.aBoolean767 && class11 != null)
				class11.method212(class46.aBoolean757, class46.anInt744, class46.anInt761, x, y, orientation);
			return;
		}
		if (class46.aBoolean762)
			if (orientation == 1) {
				int j3 = c_y;
				c_y = d_y;
				d_y = b_y;
				b_y = a_y;
				a_y = j3;
			} else if (orientation == 2) {
				int k3 = c_y;
				c_y = b_y;
				b_y = k3;
				k3 = d_y;
				d_y = a_y;
				a_y = k3;
			} else if (orientation == 3) {
				int l3 = c_y;
				c_y = a_y;
				a_y = b_y;
				b_y = d_y;
				d_y = l3;
			}
		if (type == 4) {
			Object obj7;
			if (class46.animation == -1 && class46.childrenIDs == null)
				obj7 = class46.method578(4, 0, a_y, b_y, d_y, c_y, -1);
			else
				obj7 = new Animable_Sub5(object_id, 0, 4, b_y, d_y, a_y, c_y, class46.animation, true);
			worldController.method283(key, y, orientation * 512, plane, 0, k2, ((Animable) (obj7)), x, byte0, 0, anIntArray152[orientation]);
			return;
		}
		if (type == 5) {
			int i4 = 16;
			long k4 = worldController.get_wall_uid(plane, x, y);
			if (k4 > 0)
				i4 = ObjectDefinition.forID(((int) (k4 >>> 32) & 0x7fffffff)/*k4 >> 14 & 0x7fff*/).anInt775;
			Object obj13;
			if (class46.animation == -1 && class46.childrenIDs == null)
				obj13 = class46.method578(4, 0, a_y, b_y, d_y, c_y, -1);
			else
				obj13 = new Animable_Sub5(object_id, 0, 4, b_y, d_y, a_y, c_y, class46.animation, true);
			worldController.method283(key, y, orientation * 512, plane, anIntArray137[orientation] * i4, k2, ((Animable) (obj13)), x, byte0, anIntArray144[orientation] * i4, anIntArray152[orientation]);
			return;
		}
		if (type == 6) {
			Object obj8;
			if (class46.animation == -1 && class46.childrenIDs == null)
				obj8 = class46.method578(4, 0, a_y, b_y, d_y, c_y, -1);
			else
				obj8 = new Animable_Sub5(object_id, 0, 4, b_y, d_y, a_y, c_y, class46.animation, true);
			worldController.method283(key, y, orientation, plane, 0, k2, ((Animable) (obj8)), x, byte0, 0, 256);
			return;
		}
		if (type == 7) {
			Object obj9;
			if (class46.animation == -1 && class46.childrenIDs == null)
				obj9 = class46.method578(4, 0, a_y, b_y, d_y, c_y, -1);
			else
				obj9 = new Animable_Sub5(object_id, 0, 4, b_y, d_y, a_y, c_y, class46.animation, true);
			worldController.method283(key, y, orientation, plane, 0, k2, ((Animable) (obj9)), x, byte0, 0, 512);
			return;
		}
		if (type == 8) {
			Object obj10;
			if (class46.animation == -1 && class46.childrenIDs == null)
				obj10 = class46.method578(4, 0, a_y, b_y, d_y, c_y, -1);
			else
				obj10 = new Animable_Sub5(object_id, 0, 4, b_y, d_y, a_y, c_y, class46.animation, true);
			worldController.method283(key, y, orientation, plane, 0, k2, ((Animable) (obj10)), x, byte0, 0, 768);
		}
	}

	private static int method176(int i, int j, int k) {
		int l = i / k;
		int i1 = i & k - 1;
		int j1 = j / k;
		int k1 = j & k - 1;
		int l1 = method186(l, j1);
		int i2 = method186(l + 1, j1);
		int j2 = method186(l, j1 + 1);
		int k2 = method186(l + 1, j1 + 1);
		int l2 = method184(l1, i2, i1, k);
		int i3 = method184(j2, k2, i1, k);
		return method184(l2, i3, k1, k);
	}

	private int encode(int i, int j, int k) {
		if (k > 179)
			j /= 2;
		if (k > 192)
			j /= 2;
		if (k > 217)
			j /= 2;
		if (k > 243)
			j /= 2;
		return (i / 4 << 10) + (j / 32 << 7) + k / 2;
	}

	public static boolean method178(int i, int j) {
		ObjectDefinition class46 = ObjectDefinition.forID(i);
		if (j == 11)
			j = 10;
		if (j >= 5 && j <= 8)
			j = 4;
		return class46.method577(j);
	}

	public final void method179(int i, int j, Class11 aclass11[], int l, int i1, byte abyte0[], int j1, int k1, int l1) {
		for (int i2 = 0; i2 < 8; i2++) {
			for (int j2 = 0; j2 < 8; j2++)
				if (l + i2 > 0 && l + i2 < 103 && l1 + j2 > 0 && l1 + j2 < 103)
					aclass11[k1].anIntArrayArray294[l + i2][l1 + j2] &= 0xfeffffff;

		}
		Buffer stream = new Buffer(abyte0);
		for (int l2 = 0; l2 < 4; l2++) {
			for (int i3 = 0; i3 < 64; i3++) {
				for (int j3 = 0; j3 < 64; j3++)
					if (l2 == i && i3 >= i1 && i3 < i1 + 8 && j3 >= j1 && j3 < j1 + 8)
						method181(l1 + Class4.method156(j3 & 7, j, i3 & 7), 0, stream, l + Class4.method155(j, j3 & 7, i3 & 7), k1, j, 0);
					else
						method181(-1, 0, stream, -1, 0, 0, 0);

			}

		}

	}

	public final void method180(byte abyte0[], int i, int j, int k, int l, Class11 aclass11[]) {
		for (int i1 = 0; i1 < 4; i1++) {
			for (int j1 = 0; j1 < 64; j1++) {
				for (int k1 = 0; k1 < 64; k1++)
					if (j + j1 > 0 && j + j1 < 103 && i + k1 > 0 && i + k1 < 103)
						aclass11[i1].anIntArrayArray294[j + j1][i + k1] &= 0xfeffffff;

			}

		}

		Buffer stream = new Buffer(abyte0);
		for (int l1 = 0; l1 < 4; l1++) {
			for (int i2 = 0; i2 < 64; i2++) {
				for (int j2 = 0; j2 < 64; j2++)
					method181(j2 + i, l, stream, i2 + j, l1, 0, k);

			}

		}
	}

	private void method181(int i, int j, Buffer stream, int k, int l, int i1, int k1) {
		try {
			if (k >= 0 && k < 104 && i >= 0 && i < 104) {
				tileSettings[l][k][i] = 0;
				do {
					int l1 = stream.readUnsignedByte();
					if (l1 == 0)
						if (l == 0) {
							tileHeights[0][k][i] = -method172(0xe3b7b + k + k1, 0x87cce + i + j) * 8;
							return;
						} else {
							tileHeights[l][k][i] = tileHeights[l - 1][k][i] - 240;
							return;
						}
					if (l1 == 1) {
						int j2 = stream.readUnsignedByte();
						if (j2 == 1)
							j2 = 0;
						if (l == 0) {
							tileHeights[0][k][i] = -j2 * 8;
							return;
						} else {
							tileHeights[l][k][i] = tileHeights[l - 1][k][i] - j2 * 8;
							return;
						}
					}
					if (l1 <= 49) {
						aByteArrayArrayArray130[l][k][i] = stream.readSignedByte();
						aByteArrayArrayArray136[l][k][i] = (byte) ((l1 - 2) / 4);
						aByteArrayArrayArray148[l][k][i] = (byte) ((l1 - 2) + i1 & 3);
					} else if (l1 <= 81)
						tileSettings[l][k][i] = (byte) (l1 - 49);
					else
						aByteArrayArrayArray142[l][k][i] = (byte) (l1 - 81);
				} while (true);
			}
			do {
				int i2 = stream.readUnsignedByte();
				if (i2 == 0)
					break;
				if (i2 == 1) {
					stream.readUnsignedByte();
					return;
				}
				if (i2 <= 49)
					stream.readUnsignedByte();
			} while (true);
		} catch (Exception e) {
		}
	}

	private int method182(int i, int j, int k) {
		if ((tileSettings[j][k][i] & 8) != 0)
			return 0;
		if (j > 0 && (tileSettings[1][k][i] & 2) != 0)
			return j - 1;
		else
			return j;
	}

	public final void method183(Class11 aclass11[], WorldController worldController, int i, int j, int k, int l, byte abyte0[], int i1, int j1, int k1) {
		label0: {
			Buffer stream = new Buffer(abyte0);
			int l1 = -1;
			do {
				int i2 = stream.readUSmart2();
				if (i2 == 0)
					break label0;
				l1 += i2;
				int j2 = 0;
				do {
					int k2 = stream.method422();
					if (k2 == 0)
						break;
					j2 += k2 - 1;
					int l2 = j2 & 0x3f;
					int i3 = j2 >> 6 & 0x3f;
					int j3 = j2 >> 12;
					int k3 = stream.readUnsignedByte();
					int l3 = k3 >> 2;
					int i4 = k3 & 3;
					if (j3 == i && i3 >= i1 && i3 < i1 + 8 && l2 >= k && l2 < k + 8) {
						ObjectDefinition class46 = ObjectDefinition.forID(l1);
						int j4 = j + Class4.method157(j1, class46.anInt761, i3 & 7, l2 & 7, class46.anInt744);
						int k4 = k1 + Class4.method158(l2 & 7, class46.anInt761, j1, class46.anInt744, i3 & 7);
						if (j4 > 0 && k4 > 0 && j4 < 103 && k4 < 103) {
							int l4 = j3;
							if ((tileSettings[1][j4][k4] & 2) == 2)
								l4--;
							Class11 class11 = null;
							if (l4 >= 0)
								class11 = aclass11[l4];
							method175(k4, worldController, class11, l3, l, j4, l1, i4 + j1 & 3);
						}
					}
				} while (true);
			} while (true);
		}
	}

	private static int method184(int i, int j, int k, int l) {
		int i1 = 0x10000 - Rasterizer3D.anIntArray1471[(k * 1024) / l] >> 1;
		return (i * (0x10000 - i1) >> 16) + (j * i1 >> 16);
	}

	private int method185(int i, int j) {
		if (i == -2)
			return 0xbc614e;
		if (i == -1) {
			if (j < 0)
				j = 0;
			else if (j > 127)
				j = 127;
			j = 127 - j;
			return j;
		}
		j = (j * (i & 0x7f)) / 128;
		if (j < 2)
			j = 2;
		else if (j > 126)
			j = 126;
		return (i & 0xff80) + j;
	}

	private static int method186(int i, int j) {
		int k = method170(i - 1, j - 1) + method170(i + 1, j - 1) + method170(i - 1, j + 1) + method170(i + 1, j + 1);
		int l = method170(i - 1, j) + method170(i + 1, j) + method170(i, j - 1) + method170(i, j + 1);
		int i1 = method170(i, j);
		return k / 16 + l / 8 + i1 / 4;
	}

	private static int method187(int i, int j) {
		if (i == -1)
			return 0xbc614e;
		j = (j * (i & 0x7f)) / 128;
		if (j < 2)
			j = 2;
		else if (j > 126)
			j = 126;
		return (i & 0xff80) + j;
	}

	public static void method188(WorldController worldController, int orientation, int y, int type, int plane, Class11 class11, int vertex_heights[][][], int x, int object_id, int z) {
		int l1 = vertex_heights[plane][x][y];
		int i2 = vertex_heights[plane][x + 1][y];
		int j2 = vertex_heights[plane][x + 1][y + 1];
		int k2 = vertex_heights[plane][x][y + 1];
		int l2 = l1 + i2 + j2 + k2 >> 2;
		ObjectDefinition def = ObjectDefinition.forID(object_id);
		long key = (long) (orientation << 20 | type << 14 | (y << 7 | x) + 0x40000000);
		if (!def.isInteractive) {
			key |= ~0x7fffffffffffffffL;
		}
		if (def.supportItems == 1) {
			key |= 0x400000L;
		}
		key |= (long) object_id << 32;
		byte byte1 = (byte) ((orientation << 6) + type);
		if (type == 22) {
			Object obj;
			if (def.animation == -1 && def.childrenIDs == null)
				obj = def.method578(22, orientation, l1, i2, j2, k2, -1);
			else
				obj = new Animable_Sub5(object_id, orientation, 22, i2, j2, l1, k2, def.animation, true);
			worldController.method280(z, l2, y, ((Animable) (obj)), byte1, key, x);
			if (def.aBoolean767 && def.isInteractive)
				class11.method213(y, x);
			return;
		}
		if (type == 10 || type == 11) {
			Object obj1;
			if (def.animation == -1 && def.childrenIDs == null)
				obj1 = def.method578(10, orientation, l1, i2, j2, k2, -1);
			else
				obj1 = new Animable_Sub5(object_id, orientation, 10, i2, j2, l1, k2, def.animation, true);
			if (obj1 != null) {
				int j5 = 0;
				if (type == 11)
					j5 += 256;
				int k4;
				int i5;
				if (orientation == 1 || orientation == 3) {
					k4 = def.anInt761;
					i5 = def.anInt744;
				} else {
					k4 = def.anInt744;
					i5 = def.anInt761;
				}
				worldController.method284(key, byte1, l2, i5, ((Animable) (obj1)), k4, z, j5, y, x);
			}
			if (def.aBoolean767)
				class11.method212(def.aBoolean757, def.anInt744, def.anInt761, x, y, orientation);
			return;
		}
		if (type >= 12) {
			Object obj2;
			if (def.animation == -1 && def.childrenIDs == null)
				obj2 = def.method578(type, orientation, l1, i2, j2, k2, -1);
			else
				obj2 = new Animable_Sub5(object_id, orientation, type, i2, j2, l1, k2, def.animation, true);
			worldController.method284(key, byte1, l2, 1, ((Animable) (obj2)), 1, z, 0, y, x);
			if (def.aBoolean767)
				class11.method212(def.aBoolean757, def.anInt744, def.anInt761, x, y, orientation);
			return;
		}
		if (type == 0) {
			Object obj3;
			if (def.animation == -1 && def.childrenIDs == null)
				obj3 = def.method578(0, orientation, l1, i2, j2, k2, -1);
			else
				obj3 = new Animable_Sub5(object_id, orientation, 0, i2, j2, l1, k2, def.animation, true);
			worldController.method282(anIntArray152[orientation], ((Animable) (obj3)), key, y, byte1, x, null, l2, 0, z);
			if (def.aBoolean767)
				class11.method211(y, orientation, x, type, def.aBoolean757);
			return;
		}
		if (type == 1) {
			Object obj4;
			if (def.animation == -1 && def.childrenIDs == null)
				obj4 = def.method578(1, orientation, l1, i2, j2, k2, -1);
			else
				obj4 = new Animable_Sub5(object_id, orientation, 1, i2, j2, l1, k2, def.animation, true);
			worldController.method282(anIntArray140[orientation], ((Animable) (obj4)), key, y, byte1, x, null, l2, 0, z);
			if (def.aBoolean767)
				class11.method211(y, orientation, x, type, def.aBoolean757);
			return;
		}
		if (type == 2) {
			int j3 = orientation + 1 & 3;
			Object obj11;
			Object obj12;
			if (def.animation == -1 && def.childrenIDs == null) {
				obj11 = def.method578(2, 4 + orientation, l1, i2, j2, k2, -1);
				obj12 = def.method578(2, j3, l1, i2, j2, k2, -1);
			} else {
				obj11 = new Animable_Sub5(object_id, 4 + orientation, 2, i2, j2, l1, k2, def.animation, true);
				obj12 = new Animable_Sub5(object_id, j3, 2, i2, j2, l1, k2, def.animation, true);
			}
			worldController.method282(anIntArray152[orientation], ((Animable) (obj11)), key, y, byte1, x, ((Animable) (obj12)), l2, anIntArray152[j3], z);
			if (def.aBoolean767)
				class11.method211(y, orientation, x, type, def.aBoolean757);
			return;
		}
		if (type == 3) {
			Object obj5;
			if (def.animation == -1 && def.childrenIDs == null)
				obj5 = def.method578(3, orientation, l1, i2, j2, k2, -1);
			else
				obj5 = new Animable_Sub5(object_id, orientation, 3, i2, j2, l1, k2, def.animation, true);
			worldController.method282(anIntArray140[orientation], ((Animable) (obj5)), key, y, byte1, x, null, l2, 0, z);
			if (def.aBoolean767)
				class11.method211(y, orientation, x, type, def.aBoolean757);
			return;
		}
		if (type == 9) {
			Object obj6;
			if (def.animation == -1 && def.childrenIDs == null)
				obj6 = def.method578(type, orientation, l1, i2, j2, k2, -1);
			else
				obj6 = new Animable_Sub5(object_id, orientation, type, i2, j2, l1, k2, def.animation, true);
			worldController.method284(key, byte1, l2, 1, ((Animable) (obj6)), 1, z, 0, y, x);
			if (def.aBoolean767)
				class11.method212(def.aBoolean757, def.anInt744, def.anInt761, x, y, orientation);
			return;
		}
		if (def.aBoolean762)
			if (orientation == 1) {
				int k3 = k2;
				k2 = j2;
				j2 = i2;
				i2 = l1;
				l1 = k3;
			} else if (orientation == 2) {
				int l3 = k2;
				k2 = i2;
				i2 = l3;
				l3 = j2;
				j2 = l1;
				l1 = l3;
			} else if (orientation == 3) {
				int i4 = k2;
				k2 = l1;
				l1 = i2;
				i2 = j2;
				j2 = i4;
			}
		if (type == 4) {
			Object obj7;
			if (def.animation == -1 && def.childrenIDs == null)
				obj7 = def.method578(4, 0, l1, i2, j2, k2, -1);
			else
				obj7 = new Animable_Sub5(object_id, 0, 4, i2, j2, l1, k2, def.animation, true);
			worldController.method283(key, y, orientation * 512, z, 0, l2, ((Animable) (obj7)), x, byte1, 0, anIntArray152[orientation]);
			return;
		}
		if (type == 5) {
			int j4 = 16;
			long l4 = worldController.get_wall_uid(z, x, y);
			if (l4 > 0)
				j4 = ObjectDefinition.forID(((int) (l4 >>> 32) & 0x7fffffff)/*k4 >> 14 & 0x7fff*/).anInt775;
			Object obj13;
			if (def.animation == -1 && def.childrenIDs == null)
				obj13 = def.method578(4, 0, l1, i2, j2, k2, -1);
			else
				obj13 = new Animable_Sub5(object_id, 0, 4, i2, j2, l1, k2, def.animation, true);
			worldController.method283(key, y, orientation * 512, z, anIntArray137[orientation] * j4, l2, ((Animable) (obj13)), x, byte1, anIntArray144[orientation] * j4, anIntArray152[orientation]);
			return;
		}
		if (type == 6) {
			Object obj8;
			if (def.animation == -1 && def.childrenIDs == null)
				obj8 = def.method578(4, 0, l1, i2, j2, k2, -1);
			else
				obj8 = new Animable_Sub5(object_id, 0, 4, i2, j2, l1, k2, def.animation, true);
			worldController.method283(key, y, orientation, z, 0, l2, ((Animable) (obj8)), x, byte1, 0, 256);
			return;
		}
		if (type == 7) {
			Object obj9;
			if (def.animation == -1 && def.childrenIDs == null)
				obj9 = def.method578(4, 0, l1, i2, j2, k2, -1);
			else
				obj9 = new Animable_Sub5(object_id, 0, 4, i2, j2, l1, k2, def.animation, true);
			worldController.method283(key, y, orientation, z, 0, l2, ((Animable) (obj9)), x, byte1, 0, 512);
			return;
		}
		if (type == 8) {
			Object obj10;
			if (def.animation == -1 && def.childrenIDs == null)
				obj10 = def.method578(4, 0, l1, i2, j2, k2, -1);
			else
				obj10 = new Animable_Sub5(object_id, 0, 4, i2, j2, l1, k2, def.animation, true);
			worldController.method283(key, y, orientation, z, 0, l2, ((Animable) (obj10)), x, byte1, 0, 768);
		}
	}

	public static boolean method189(int i, byte[] is, int i_250_) // xxx bad
																	// method,
																	// decompiled
																	// with JODE
	{
		boolean bool = true;
		Buffer stream = new Buffer(is);
		int i_252_ = -1;
		for (;;) {
			int i_253_ = stream.readUnsignedIntSmartShortCompat();
			if (i_253_ == 0)
				break;
			i_252_ += i_253_;
			int i_254_ = 0;
			boolean bool_255_ = false;
			for (;;) {
				if (bool_255_) {
					int i_256_ = stream.method422();
					if (i_256_ == 0)
						break;
					stream.readUnsignedByte();
				} else {
					int i_257_ = stream.method422();
					if (i_257_ == 0)
						break;
					i_254_ += i_257_ - 1;
					int i_258_ = i_254_ & 0x3f;
					int i_259_ = i_254_ >> 6 & 0x3f;
					int i_260_ = stream.readUnsignedByte() >> 2;
					int i_261_ = i_259_ + i;
					int i_262_ = i_258_ + i_250_;
					if (i_261_ > 0 && i_262_ > 0 && i_261_ < 103 && i_262_ < 103) {
						ObjectDefinition class46 = ObjectDefinition.forID(i_252_);
						if (i_260_ != 22 || Configuration.enableGroundDecors || class46.isInteractive || class46.aBoolean736) {
							bool &= class46.method579();
							bool_255_ = true;
						}
					}
				}
			}
		}
		return bool;
	}

	public final void method190(int i, Class11 aclass11[], int j, WorldController worldController, byte abyte0[]) {
		label0: {
			Buffer stream = new Buffer(abyte0);
			int l = -1;
			do {
				int i1 = stream.readUnsignedIntSmartShortCompat();
				if (i1 == 0)
					break label0;
				l += i1;
				int j1 = 0;
				do {
					int k1 = stream.method422();
					if (k1 == 0)
						break;
					j1 += k1 - 1;
					int l1 = j1 & 0x3f;
					int i2 = j1 >> 6 & 0x3f;
					int j2 = j1 >> 12;
					int k2 = stream.readUnsignedByte();
					int l2 = k2 >> 2;
					int i3 = k2 & 3;
					int j3 = i2 + i;
					int k3 = l1 + j;
					if (j3 > 0 && k3 > 0 && j3 < 103 && k3 < 103 && j2 >= 0 && j2 < 4) {
						int l3 = j2;
						if ((tileSettings[1][j3][k3] & 2) == 2)
							l3--;
						Class11 class11 = null;
						if (l3 >= 0)
							class11 = aclass11[l3];
						method175(k3, worldController, class11, l2, j2, j3, l, i3);
					}
				} while (true);
			} while (true);
		}
	}

	private final int[] anIntArray124;
	private final int[] anIntArray125;
	private final int[] anIntArray126;
	private final int[] anIntArray127;
	private final int[] anIntArray128;
	private final int[][][] tileHeights;
	private final byte[][][] aByteArrayArrayArray130;
	static int anInt131;
	private final byte[][][] aByteArrayArrayArray134;
	private final int[][][] anIntArrayArrayArray135;
	private final byte[][][] aByteArrayArrayArray136;
	private static final int anIntArray137[] = { 1, 0, -1, 0 };
	private final int[][] anIntArrayArray139;
	private static final int anIntArray140[] = { 16, 32, 64, 128 };
	private final byte[][][] aByteArrayArrayArray142;
	private static final int anIntArray144[] = { 0, -1, 0, 1 };
	static int highestPlane = 99;
	private final int anInt146;
	private final int anInt147;
	private final byte[][][] aByteArrayArrayArray148;
	private final byte[][][] tileSettings;
	static boolean lowMem = true;
	private static final int anIntArray152[] = { 1, 2, 4, 8 };
	public final ArrayList<Integer> colors = new ArrayList<Integer>();
}
