import java.util.ArrayList;

public class Model extends Animable {
	
	private final void removeColor(int color) {
        if (colors != null) {
            for (int triangle = 0; triangle < trianglesCount; triangle++) {
                if (triangle < colors.length) {
                    if (colors[triangle] == color) {
                    	trianglesX[triangle] = 0;
                    	trianglesY[triangle] = 0;
                    	trianglesZ[triangle] = 0;
                    }
                }
            }
        }
    }

	public static void nullLoader() {
		aClass21Array1661 = null;
		hasAnEdgeToRestrict = null;
		outOfReach = null;
		projected_verticesY = null;
		projected_verticesZ = null;
		anIntArray1668 = null;
		camera_verticesY = null;
		viewportTextureZ = null;
		depthListIndices = null;
		faceLists = null;
		anIntArray1673 = null;
		anIntArrayArray1674 = null;
		anIntArray1675 = null;
		anIntArray1676 = null;
		anIntArray1677 = null;
		SINE = null;
		COSINE = null;
		modelIntArray3 = null;
		modelIntArray4 = null;
	}

	private Model(int modelId) {

		try {
			byte[] is = aClass21Array1661[modelId].aByteArray368;
			if (is[is.length - 1] == -3 && is[is.length - 2] == -1) {
				ModelLoader.decodeType3(this, is);
			} else if (is[is.length - 1] == -2 && is[is.length - 2] == -1) {
				ModelLoader.decodeType2(this, is);
			} else if (is[is.length - 1] == -1 && is[is.length - 2] == -1) {
				ModelLoader.decodeType1(this, is);
			} else {
				ModelLoader.decodeOldFormat(this, is);
			}
			if (newmodel[modelId]) {
				scale2(4);// 2 is too big -- 3 is almost right
				if (face_render_priorities != null) {
					for (int j = 0; j < face_render_priorities.length; j++) {
						face_render_priorities[j] = 10;
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Error decoding model: " + modelId);
			e.printStackTrace();
		}
	}

	public void scale2(int i) {
		for (int i1 = 0; i1 < verticesCount; i1++) {
			verticesX[i1] = verticesX[i1] / i;
			verticesY[i1] = verticesY[i1] / i;
			verticesZ[i1] = verticesZ[i1] / i;
		}
	}

	public static void method460(byte abyte0[], int j) {
	try {
		if (abyte0 == null) {
			ModelHeader class21 = aClass21Array1661[j] = new ModelHeader();
			class21.anInt369 = 0;
			class21.anInt370 = 0;
			class21.anInt371 = 0;
			return;
		}
		Buffer stream = new Buffer(abyte0);
		stream.currentPosition = abyte0.length - 18;
		ModelHeader class21_1 = aClass21Array1661[j] = new ModelHeader();
		class21_1.aByteArray368 = abyte0;
		class21_1.anInt369 = stream.readUnsignedShort();
		class21_1.anInt370 = stream.readUnsignedShort();
		class21_1.anInt371 = stream.readUnsignedByte();
		int k = stream.readUnsignedByte();
		int l = stream.readUnsignedByte();
		int i1 = stream.readUnsignedByte();
		int j1 = stream.readUnsignedByte();
		int k1 = stream.readUnsignedByte();
		int l1 = stream.readUnsignedShort();
		int i2 = stream.readUnsignedShort();
		int j2 = stream.readUnsignedShort();
		int k2 = stream.readUnsignedShort();
		int l2 = 0;
		class21_1.anInt372 = l2;
		l2 += class21_1.anInt369;
		class21_1.anInt378 = l2;
		l2 += class21_1.anInt370;
		class21_1.anInt381 = l2;
		if (l == 255)
			l2 += class21_1.anInt370;
		else
			class21_1.anInt381 = -l - 1;
		class21_1.anInt383 = l2;
		if (j1 == 1)
			l2 += class21_1.anInt370;
		else
			class21_1.anInt383 = -1;
		class21_1.anInt380 = l2;
		if (k == 1)
			l2 += class21_1.anInt370;
		else
			class21_1.anInt380 = -1;
		class21_1.anInt376 = l2;
		if (k1 == 1)
			l2 += class21_1.anInt369;
		else
			class21_1.anInt376 = -1;
		class21_1.anInt382 = l2;
		if (i1 == 1)
			l2 += class21_1.anInt370;
		else
			class21_1.anInt382 = -1;
		class21_1.anInt377 = l2;
		l2 += k2;
		class21_1.anInt379 = l2;
		l2 += class21_1.anInt370 * 2;
		class21_1.anInt384 = l2;
		l2 += class21_1.anInt371 * 6;
		class21_1.anInt373 = l2;
		l2 += l1;
		class21_1.anInt374 = l2;
		l2 += i2;
		class21_1.anInt375 = l2;
		l2 += j2;
		} catch (Exception _ex) {
		}
	}

	public static boolean newmodel[];

	public static void method459(int i,
			OnDemandFetcherParent onDemandFetcherParent) {
		aClass21Array1661 = new ModelHeader[80000];
		newmodel = new boolean[100000];
		resourceProvider = onDemandFetcherParent;
	}

	public static void method461(int j) {
		aClass21Array1661[j] = null;
	}

	public static Model method462(int j) {
		if (aClass21Array1661 == null)
			return null;
		ModelHeader class21 = aClass21Array1661[j];
		if (class21 == null) {
			resourceProvider.method548(j);
			return null;
		} else {
			return new Model(j);
		}
	}

	public static boolean method463(int i) {
		if (aClass21Array1661 == null)
			return false;

		ModelHeader class21 = aClass21Array1661[i];
		if (class21 == null) {
			resourceProvider.method548(i);
			return false;
		} else {
			return true;
		}
	}

	private Model(boolean flag) {
		aBoolean1618 = true;
		fits_on_single_square = false;
		if (!flag)
			aBoolean1618 = !aBoolean1618;
	}

	public Model(int length, Model model_segments[]) {
		try {
			aBoolean1618 = true;
			fits_on_single_square = false;
			anInt1620++;
			boolean type_flag = false;
			boolean priority_flag = false;
			boolean alpha_flag = false;
			boolean tSkin_flag = false;
			boolean color_flag = false;
			boolean texture_flag = false;
			boolean coordinate_flag = false;
			verticesCount = 0;
			trianglesCount = 0;
			texturesCount = 0;
			face_priority = -1;
			Model build;
			for (int segment_index = 0; segment_index < length; segment_index++) {
				build = model_segments[segment_index];
				if (build != null) {
					verticesCount += build.verticesCount;
					trianglesCount += build.trianglesCount;
					texturesCount += build.texturesCount;
					type_flag |= build.types != null;
					alpha_flag |= build.alphas != null;
					if (build.face_render_priorities != null) {
						priority_flag = true;
					} else {
						if (face_priority == -1)
							face_priority = build.face_priority;

						if (face_priority != build.face_priority)
							priority_flag = true;
					}
					tSkin_flag |= build.triangleData != null;
					color_flag |= build.colors != null;
					texture_flag |= build.materials != null;
					coordinate_flag |= build.textures != null;
				}
			}
			verticesX = new int[verticesCount];
			verticesY = new int[verticesCount];
			verticesZ = new int[verticesCount];
			vertexData = new int[verticesCount];
			trianglesX = new int[trianglesCount];
			trianglesY = new int[trianglesCount];
			trianglesZ = new int[trianglesCount];
			if(color_flag)
				colors = new short[trianglesCount];

			if (type_flag)
				types = new int[trianglesCount];

			if (priority_flag)
				face_render_priorities = new byte[trianglesCount];

			if (alpha_flag)
				alphas = new int[trianglesCount];

			if (tSkin_flag)
				triangleData = new int[trianglesCount];

			if(texture_flag)
				materials = new short[trianglesCount];

			if (coordinate_flag)
				textures = new byte[trianglesCount];

			if(texturesCount > 0) {
				textureTypes = new byte[texturesCount];
				texturesX = new short[texturesCount];
				texturesY = new short[texturesCount];
				texturesZ = new short[texturesCount];
			}
			verticesCount = 0;
			trianglesCount = 0;
			texturesCount = 0;
			int texture_face = 0;
			for (int segment_index = 0; segment_index < length; segment_index++) {
				build = model_segments[segment_index];
				if (build != null) {
					for (int face = 0; face < build.trianglesCount; face++) {
						if(type_flag && build.types != null)
							types[trianglesCount] = build.types[face];

						if (priority_flag)
							if (build.face_render_priorities == null)
								face_render_priorities[trianglesCount] = (byte) build.face_priority;
							else
								face_render_priorities[trianglesCount] = build.face_render_priorities[face];

						if (alpha_flag && build.alphas != null)
							alphas[trianglesCount] = build.alphas[face];

						if (tSkin_flag && build.triangleData != null)
							triangleData[trianglesCount] = build.triangleData[face];

						if(texture_flag) {
							if(build.materials != null)
								materials[trianglesCount] = build.materials[face];
							else
								materials[trianglesCount] = -1;
						}
						if(coordinate_flag) {
							if(build.textures != null && build.textures[face] != -1) {
								textures[trianglesCount] = (byte) (build.textures[face] + texture_face);
							} else {
								textures[trianglesCount] = -1;
							}
						}
						colors[trianglesCount] = build.colors[face];
						trianglesX[trianglesCount] = method465(build, build.trianglesX[face]);
						trianglesY[trianglesCount] = method465(build, build.trianglesY[face]);
						trianglesZ[trianglesCount] = method465(build, build.trianglesZ[face]);
						trianglesCount++;
					}
					for (int texture_edge = 0; texture_edge < build.texturesCount; texture_edge++) {
						texturesX[texturesCount] = (short) method465(build, build.texturesX[texture_edge]);
						texturesY[texturesCount] = (short) method465(build, build.texturesY[texture_edge]);
						texturesZ[texturesCount] = (short) method465(build, build.texturesZ[texture_edge]);
						texturesCount++;
					}
					texture_face += build.texturesCount;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Model(Model amodel[]) {
		int i = 2;
		aBoolean1618 = true;
		fits_on_single_square = false;
		anInt1620++;
		boolean flag1 = false;
		boolean flag2 = false;
		boolean flag3 = false;
		boolean flag4 = false;
		boolean texture_flag = false;
		boolean coordinate_flag = false;
		verticesCount = 0;
		trianglesCount = 0;
		texturesCount = 0;
		face_priority = -1;
		for (int k = 0; k < i; k++) {
			Model model = amodel[k];
			if (model != null) {
				verticesCount += model.verticesCount;
				trianglesCount += model.trianglesCount;
				texturesCount += model.texturesCount;
				flag1 |= model.types != null;
				if (model.face_render_priorities != null) {
					flag2 = true;
				} else {
					if (face_priority == -1)
						face_priority = model.face_priority;
					if (face_priority != model.face_priority)
						flag2 = true;
				}
				flag3 |= model.alphas != null;
				flag4 |= model.colors != null;
				texture_flag |= model.materials != null;
				coordinate_flag |= model.textures != null;
			}
		}
		verticesX = new int[verticesCount];
		verticesY = new int[verticesCount];
		verticesZ = new int[verticesCount];
		trianglesX = new int[trianglesCount];
		trianglesY = new int[trianglesCount];
		trianglesZ = new int[trianglesCount];
		colorsX = new int[trianglesCount];
		colorsY = new int[trianglesCount];
		colorsZ = new int[trianglesCount];
		texturesX = new short[texturesCount];
		texturesY = new short[texturesCount];
		texturesZ = new short[texturesCount];
		if (flag1)
			types = new int[trianglesCount];
		if (flag2)
			face_render_priorities = new byte[trianglesCount];
		if (flag3)
			alphas = new int[trianglesCount];
		if (flag4)
			colors = new short[trianglesCount];
		if (texture_flag)
			materials = new short[trianglesCount];

		if (coordinate_flag)
			textures = new byte[trianglesCount];
		verticesCount = 0;
		trianglesCount = 0;
		texturesCount = 0;
		int i1 = 0;
		for (int j1 = 0; j1 < i; j1++) {
			Model model_1 = amodel[j1];
			if (model_1 != null) {
				int k1 = verticesCount;
				for (int l1 = 0; l1 < model_1.verticesCount; l1++) {
					int x = model_1.verticesX[l1];
					int y = model_1.verticesY[l1];
					int z = model_1.verticesZ[l1];
					verticesX[verticesCount] = x;
					verticesY[verticesCount] = y;
					verticesZ[verticesCount] = z;
					++verticesCount;
				}

				for (int i2 = 0; i2 < model_1.trianglesCount; i2++) {
					trianglesX[trianglesCount] = model_1.trianglesX[i2] + k1;
					trianglesY[trianglesCount] = model_1.trianglesY[i2] + k1;
					trianglesZ[trianglesCount] = model_1.trianglesZ[i2] + k1;
					colorsX[trianglesCount] = model_1.colorsX[i2];
					colorsY[trianglesCount] = model_1.colorsY[i2];
					colorsZ[trianglesCount] = model_1.colorsZ[i2];
					if (flag1)
						if (model_1.types == null) {
							types[trianglesCount] = 0;
						} else {
							int j2 = model_1.types[i2];
							if ((j2 & 2) == 2)
								j2 += i1 << 2;
							types[trianglesCount] = j2;
						}
					if (flag2)
						if (model_1.face_render_priorities == null)
							face_render_priorities[trianglesCount] = (byte) model_1.face_priority;
						else
							face_render_priorities[trianglesCount] = model_1.face_render_priorities[i2];
					if (flag3)
						if (model_1.alphas == null)
							alphas[trianglesCount] = 0;
						else
							alphas[trianglesCount] = model_1.alphas[i2];
					if (flag4 && model_1.colors != null)
						colors[trianglesCount] = model_1.colors[i2];

					if (texture_flag) {
						if (model_1.materials != null) {
							materials[trianglesCount] = model_1.materials[trianglesCount];
						} else {
							materials[trianglesCount] = -1;
						}
					}

					if (coordinate_flag) {
						if (model_1.textures != null && model_1.textures[trianglesCount] != -1)
							textures[trianglesCount] = (byte)(model_1.textures[trianglesCount] + texturesCount);
						else
							textures[trianglesCount] = -1;

					}

					trianglesCount++;
				}
				for (int k2 = 0; k2 < model_1.texturesCount; k2++) {
					texturesX[texturesCount] = (short) (model_1.texturesX[k2] + k1);
					texturesY[texturesCount] = (short) (model_1.texturesY[k2] + k1);
					texturesZ[texturesCount] = (short) (model_1.texturesZ[k2] + k1);
					texturesCount++;
				}
				i1 += model_1.texturesCount;
			}
		}
		method466();
	}
	public Model(boolean color_flag, boolean alpha_flag, boolean animated, Model model) {
		this(color_flag, alpha_flag, animated, false, model);
	}
	public Model(boolean color_flag, boolean alpha_flag, boolean animated, boolean texture_flag, Model model) {
		aBoolean1618 = true;
		fits_on_single_square = false;
		anInt1620++;
		verticesCount = model.verticesCount;
		trianglesCount = model.trianglesCount;
		texturesCount = model.texturesCount;
		if (animated) {
			verticesX = model.verticesX;
			verticesY = model.verticesY;
			verticesZ = model.verticesZ;
		} else {
			verticesX = new int[verticesCount];
			verticesY = new int[verticesCount];
			verticesZ = new int[verticesCount];
			for (int j = 0; j < verticesCount; j++) {
				verticesX[j] = model.verticesX[j];
				verticesY[j] = model.verticesY[j];
				verticesZ[j] = model.verticesZ[j];
			}

		}
		if (color_flag) {
			colors = model.colors;
		} else {
			colors = new short[trianglesCount];
			for (int k = 0; k < trianglesCount; k++) {
				colors[k] = model.colors[k];
			}

		}

		if(!texture_flag && model.materials != null) {
			materials = new short[trianglesCount];
			for(int face = 0; face < trianglesCount; face++) {
				materials[face] = model.materials[face];
			}
		} else {
			materials = model.materials;
		}

		if (alpha_flag) {
			alphas = model.alphas;
		} else {
			alphas = new int[trianglesCount];
			if (model.alphas == null) {
				for (int l = 0; l < trianglesCount; l++) {
					alphas[l] = 0;
				}

			} else {
				for (int i1 = 0; i1 < trianglesCount; i1++) {
					alphas[i1] = model.alphas[i1];
				}

			}
		}
		repeatTexture = model.repeatTexture;
		vertexData = model.vertexData;
		triangleData = model.triangleData;
		types = model.types;
		trianglesX = model.trianglesX;
		trianglesY = model.trianglesY;
		trianglesZ = model.trianglesZ;
		face_render_priorities = model.face_render_priorities;
		face_priority = model.face_priority;
		texturesX = model.texturesX;
		texturesY = model.texturesY;
		texturesZ = model.texturesZ;
		textures = model.textures;
		textureTypes = model.textureTypes;
		vertexNormals = model.vertexNormals;
		faceNormals = model.faceNormals;
		vertexNormalsOffsets = model.vertexNormalsOffsets;
	}

	public Model(boolean flag, boolean flag1, Model model) {
		aBoolean1618 = true;
		fits_on_single_square = false;
		anInt1620++;
		verticesCount = model.verticesCount;
		trianglesCount = model.trianglesCount;
		texturesCount = model.texturesCount;
		if (flag) {
			verticesY = new int[verticesCount];
			for (int j = 0; j < verticesCount; j++) {
				verticesY[j] = model.verticesY[j];
			}

		} else {
			verticesY = model.verticesY;
		}
//		if (flag1) {
//			colorsX = new int[trianglesCount];
//			colorsY = new int[trianglesCount];
//			colorsZ = new int[trianglesCount];
//			for (int k = 0; k < trianglesCount; k++) {
//				colorsX[k] = model.colorsX[k];
//				colorsY[k] = model.colorsY[k];
//				colorsZ[k] = model.colorsZ[k];
//			}
//
//			types = new int[trianglesCount];
//			if (model.types == null) {
//				for (int l = 0; l < trianglesCount; l++) {
//					types[l] = 0;
//				}
//
//			} else {
//				for (int i1 = 0; i1 < trianglesCount; i1++) {
//					types[i1] = model.types[i1];
//				}
//
//			}
//			super.vertexNormals = new VertexNormal[verticesCount];
//			for (int j1 = 0; j1 < verticesCount; j1++) {
//				VertexNormal class33 = super.vertexNormals[j1] = new VertexNormal();
//				VertexNormal class33_1 = model.vertexNormals[j1];
//				class33.x = class33_1.x;
//				class33.y = class33_1.y;
//				class33.z = class33_1.z;
//				class33.magnitude = class33_1.magnitude;
//			}
//
//			gouraudVertex = model.gouraudVertex;
//		} else {
		colorsX = model.colorsX;
		colorsY = model.colorsY;
		colorsZ = model.colorsZ;
		types = model.types;
//		}
		verticesX = model.verticesX;
		verticesZ = model.verticesZ;
		colors = model.colors;
		alphas = model.alphas;
		repeatTexture = model.repeatTexture;
		face_render_priorities = model.face_render_priorities;
		face_priority = model.face_priority;
		trianglesX = model.trianglesX;
		trianglesY = model.trianglesY;
		trianglesZ = model.trianglesZ;
		texturesX = model.texturesX;
		texturesY = model.texturesY;
		texturesZ = model.texturesZ;
		super.modelHeight = model.modelHeight;
		textures = model.textures;
		materials = model.materials;
		XYZMag = model.XYZMag;
		diagonal3DAboveOrigin = model.diagonal3DAboveOrigin;
		maxRenderDepth = model.maxRenderDepth;
		minimumXVertex = model.minimumXVertex;
		maximumZVertex = model.maximumZVertex;
		minimumZVertex = model.minimumZVertex;
		maximumXVertex = model.maximumXVertex;
	}


	public void method464(Model model, boolean flag) {
		verticesCount = model.verticesCount;
		trianglesCount = model.trianglesCount;
		texturesCount = model.texturesCount;
		if (anIntArray1622.length < verticesCount) {
			anIntArray1622 = new int[verticesCount + 10000];
			anIntArray1623 = new int[verticesCount + 10000];
			anIntArray1624 = new int[verticesCount + 10000];
		}
		verticesX = anIntArray1622;
		verticesY = anIntArray1623;
		verticesZ = anIntArray1624;
		for (int k = 0; k < verticesCount; k++) {
			verticesX[k] = model.verticesX[k];
			verticesY[k] = model.verticesY[k];
			verticesZ[k] = model.verticesZ[k];
		}

		if (flag) {
			alphas = model.alphas;
		} else {
			if (anIntArray1625.length < trianglesCount) {
				anIntArray1625 = new int[trianglesCount + 100];
			}
			alphas = anIntArray1625;
			if (model.alphas == null) {
				for (int l = 0; l < trianglesCount; l++) {
					alphas[l] = 0;
				}

			} else {
				for (int i1 = 0; i1 < trianglesCount; i1++) {
					alphas[i1] = model.alphas[i1];
				}

			}
		}
		repeatTexture = model.repeatTexture;
		types = model.types;
		colors = model.colors;
		face_render_priorities = model.face_render_priorities;
		face_priority = model.face_priority;
		faceGroups = model.faceGroups;
		vertexGroups = model.vertexGroups;
		trianglesX = model.trianglesX;
		trianglesY = model.trianglesY;
		trianglesZ = model.trianglesZ;
		colorsX = model.colorsX;
		colorsY = model.colorsY;
		colorsZ = model.colorsZ;
		texturesX = model.texturesX;
		texturesY = model.texturesY;
		texturesZ = model.texturesZ;
		textures = model.textures;
		textureTypes = model.textureTypes;
		materials = model.materials;
		faceNormals = model.faceNormals;
		vertexNormalsOffsets = model.vertexNormalsOffsets;
	}


	private final int method465(Model model, int i) {
		int j = -1;
		int k = model.verticesX[i];
		int l = model.verticesY[i];
		int i1 = model.verticesZ[i];
		for (int j1 = 0; j1 < verticesCount; j1++) {
			if (k != verticesX[j1] || l != verticesY[j1]
			                                                   || i1 != verticesZ[j1])
				continue;
			j = j1;
			break;
		}

		if (j == -1) {
			verticesX[verticesCount] = k;
			verticesY[verticesCount] = l;
			verticesZ[verticesCount] = i1;
			if (model.vertexData != null)
				vertexData[verticesCount] = model.vertexData[i];
			j = verticesCount++;
		}
		return j;
	}

	public void method466() {
		super.modelHeight = 0;
		XYZMag = 0;
		maximumYVertex = 0;
		for (int i = 0; i < verticesCount; i++) {
			int j = verticesX[i];
			int k = verticesY[i];
			int l = verticesZ[i];
			if (-k > super.modelHeight)
				super.modelHeight = -k;
			if (k > maximumYVertex)
				maximumYVertex = k;
			int i1 = j * j + l * l;
			if (i1 > XYZMag)
				XYZMag = i1;
		}
		XYZMag = (int) (Math.sqrt(XYZMag) + 0.98999999999999999D);
		diagonal3DAboveOrigin = (int) (Math.sqrt(XYZMag * XYZMag + super.modelHeight
				* super.modelHeight) + 0.98999999999999999D);
		maxRenderDepth = diagonal3DAboveOrigin
		+ (int) (Math.sqrt(XYZMag * XYZMag + maximumYVertex
				* maximumYVertex) + 0.98999999999999999D);
	}

	public void method467() {
		super.modelHeight = 0;
		maximumYVertex = 0;
		for (int i = 0; i < verticesCount; i++) {
			int j = verticesY[i];
			if (-j > super.modelHeight)
				super.modelHeight = -j;
			if (j > maximumYVertex)
				maximumYVertex = j;
		}

		diagonal3DAboveOrigin = (int) (Math.sqrt(XYZMag * XYZMag + super.modelHeight
				* super.modelHeight) + 0.98999999999999999D);
		maxRenderDepth = diagonal3DAboveOrigin
		+ (int) (Math.sqrt(XYZMag * XYZMag + maximumYVertex
				* maximumYVertex) + 0.98999999999999999D);
	}

	public void method468(int i) {
		super.modelHeight = 0;
		XYZMag = 0;
		maximumYVertex = 0;
		minimumXVertex = 0xf423f;
		maximumXVertex = 0xfff0bdc1;
		maximumZVertex = 0xfffe7961;
		minimumZVertex = 0x1869f;
		for (int j = 0; j < verticesCount; j++) {
			int k = verticesX[j];
			int l = verticesY[j];
			int i1 = verticesZ[j];
			if (k < minimumXVertex)
				minimumXVertex = k;
			if (k > maximumXVertex)
				maximumXVertex = k;
			if (i1 < minimumZVertex)
				minimumZVertex = i1;
			if (i1 > maximumZVertex)
				maximumZVertex = i1;
			if (-l > super.modelHeight)
				super.modelHeight = -l;
			if (l > maximumYVertex)
				maximumYVertex = l;
			int j1 = k * k + i1 * i1;
			if (j1 > XYZMag)
				XYZMag = j1;
		}

		XYZMag = (int) Math.sqrt(XYZMag);
		diagonal3DAboveOrigin = (int) Math.sqrt(XYZMag * XYZMag + super.modelHeight
				* super.modelHeight);
		if (i != 21073) {
			return;
		} else {
			maxRenderDepth = diagonal3DAboveOrigin
			+ (int) Math.sqrt(XYZMag * XYZMag + maximumYVertex
					* maximumYVertex);
			return;
		}
	}

	public void method469() {
		if (vertexData != null) {
			int ai[] = new int[256];
			int j = 0;
			for (int l = 0; l < verticesCount; l++) {
				int j1 = vertexData[l];
				ai[j1]++;
				if (j1 > j)
					j = j1;
			}

			vertexGroups = new int[j + 1][];
			for (int k1 = 0; k1 <= j; k1++) {
				vertexGroups[k1] = new int[ai[k1]];
				ai[k1] = 0;
			}

			for (int j2 = 0; j2 < verticesCount; j2++) {
				int l2 = vertexData[j2];
				vertexGroups[l2][ai[l2]++] = j2;
			}

			vertexData = null;
		}
		if (triangleData != null) {
			int ai1[] = new int[256];
			int k = 0;
			for (int i1 = 0; i1 < trianglesCount; i1++) {
				int l1 = triangleData[i1];
				ai1[l1]++;
				if (l1 > k)
					k = l1;
			}

			faceGroups = new int[k + 1][];
			for (int i2 = 0; i2 <= k; i2++) {
				faceGroups[i2] = new int[ai1[i2]];
				ai1[i2] = 0;
			}

			for (int k2 = 0; k2 < trianglesCount; k2++) {
				int i3 = triangleData[k2];
				faceGroups[i3][ai1[i3]++] = k2;
			}

			triangleData = null;
		}
	}
	
	public void interpolateFrames(int frame, int nextFrame, int cycle, int length) {
		if (!Configuration.enableTweening) {
			method470(frame);
			return;
		}
		if (vertexGroups == null) {
			return;
		}
		if (frame == -1) {
			return;
		}
		final SequenceFrame currAnim = SequenceFrame.method531(frame);
		if (currAnim == null) {
			return;
		}
		final Class18 skinList = currAnim.aClass18_637;
		SequenceFrame nextAnim = null;
    	if (nextFrame != -1) {
    		nextAnim = SequenceFrame.method531(nextFrame);
    		if (nextAnim.aClass18_637 != skinList) {
    			nextAnim = null;
    		}
    	}
    	xAnimOffset = 0;
    	yAnimOffset = 0;
    	zAnimOffset = 0;
		if (nextAnim == null) {
			for (int index = 0; index < currAnim.anInt638; index++) {
				int anim = currAnim.anIntArray639[index];
				method472(skinList.anIntArray342[anim], skinList.anIntArrayArray343[anim], currAnim.anIntArray640[index], currAnim.anIntArray641[index], currAnim.anIntArray642[index]);
			}
		} else {
	    	int currFrameId = 0;
	    	int nextFrameId = 0;
		    for (int index = 0; index < skinList.length; index++) {
  		    	boolean interpolate = false;
		    	if (currFrameId < currAnim.anInt638 && currAnim.anIntArray639[currFrameId] == index) {
		    		interpolate = true;
		    	}
		    	boolean interpolate2 = false;
		    	if (nextFrameId < nextAnim.anInt638 && nextAnim.anIntArray639[nextFrameId] == index) {
		    		interpolate2 = true;
		    	}
		    	if (interpolate || interpolate2) {
					int defaultModifier = 0;
					int opcode = skinList.anIntArray342[index];
					if (opcode == 3) {
						defaultModifier = 128;
					}
					int currAnimX;
					int currAnimY;
					int currAnimZ;
					if (interpolate) {
						currAnimX = currAnim.anIntArray640[currFrameId];
						currAnimY = currAnim.anIntArray641[currFrameId];
						currAnimZ = currAnim.anIntArray642[currFrameId];
						currFrameId++;
					} else {
						currAnimX = defaultModifier;
						currAnimY = defaultModifier;
						currAnimZ = defaultModifier;
					}
					int nextAnimX;
					int nextAnimY;
					int nextAnimZ;
					if (interpolate2) {
						nextAnimX = nextAnim.anIntArray640[nextFrameId];
						nextAnimY = nextAnim.anIntArray641[nextFrameId];
						nextAnimZ = nextAnim.anIntArray642[nextFrameId];
						nextFrameId++;
					} else {
						nextAnimX = defaultModifier;
						nextAnimY = defaultModifier;
						nextAnimZ = defaultModifier;
					}
					int interpolatedX;
					int interpolatedY;
					int interpolatedZ;
					if (opcode == 2) {
						int deltaX = nextAnimX - currAnimX & 0xff;
						int deltaY = nextAnimY - currAnimY & 0xff;
						int deltaZ = nextAnimZ - currAnimZ & 0xff;
						if (deltaX >= 128) {
							deltaX -= 256;
						}
						if (deltaY >= 128) {
							deltaY -= 256;
						}
						if (deltaZ >= 128) {
							deltaZ -= 256;
						}
						interpolatedX = currAnimX + deltaX * cycle / length & 0xff;
						interpolatedY = currAnimY + deltaY * cycle / length & 0xff;
						interpolatedZ = currAnimZ + deltaZ * cycle / length & 0xff;
					} else {
						interpolatedX = currAnimX + (nextAnimX - currAnimX) * cycle / length;
						interpolatedY = currAnimY + (nextAnimY - currAnimY) * cycle / length;
						interpolatedZ = currAnimZ + (nextAnimZ - currAnimZ) * cycle / length;
					}
					method472(opcode, skinList.anIntArrayArray343[index], interpolatedX, interpolatedY, interpolatedZ);
		    	}
		    }
	    }
	}

	public void method470(int i) {
		if (vertexGroups == null)
			return;
		if (i == -1)
			return;
		SequenceFrame class36 = SequenceFrame.method531(i);
		if (class36 == null)
			return;
		Class18 class18 = class36.aClass18_637;
		xAnimOffset = 0;
		yAnimOffset = 0;
		zAnimOffset = 0;
		for (int k = 0; k < class36.anInt638; k++) {
			int l = class36.anIntArray639[k];
			method472(class18.anIntArray342[l], class18.anIntArrayArray343[l],
					class36.anIntArray640[k], class36.anIntArray641[k],
					class36.anIntArray642[k]);
		}

	}

	public void method471(int ai[], int j, int k) {
		if (k == -1)
			return;
		if (ai == null || j == -1) {
			method470(k);
			return;
		}
		SequenceFrame class36 = SequenceFrame.method531(k);
		if (class36 == null)
			return;
		SequenceFrame class36_1 = SequenceFrame.method531(j);
		if (class36_1 == null) {
			method470(k);
			return;
		}
		Class18 class18 = class36.aClass18_637;
		xAnimOffset = 0;
		yAnimOffset = 0;
		zAnimOffset = 0;
		int l = 0;
		int i1 = ai[l++];
		for (int j1 = 0; j1 < class36.anInt638; j1++) {
			int k1;
			for (k1 = class36.anIntArray639[j1]; k1 > i1; i1 = ai[l++])
				;
			if (k1 != i1 || class18.anIntArray342[k1] == 0)
				method472(class18.anIntArray342[k1],
						class18.anIntArrayArray343[k1],
						class36.anIntArray640[j1], class36.anIntArray641[j1],
						class36.anIntArray642[j1]);
		}

		xAnimOffset = 0;
		yAnimOffset = 0;
		zAnimOffset = 0;
		l = 0;
		i1 = ai[l++];
		for (int l1 = 0; l1 < class36_1.anInt638; l1++) {
			int i2;
			for (i2 = class36_1.anIntArray639[l1]; i2 > i1; i1 = ai[l++])
				;
			if (i2 == i1 || class18.anIntArray342[i2] == 0)
				method472(class18.anIntArray342[i2],
						class18.anIntArrayArray343[i2],
						class36_1.anIntArray640[l1],
						class36_1.anIntArray641[l1],
						class36_1.anIntArray642[l1]);
		}

	}

	private void method472(int i, int ai[], int j, int k, int l) {

		int i1 = ai.length;
		if (i == 0) {
			int j1 = 0;
			xAnimOffset = 0;
			yAnimOffset = 0;
			zAnimOffset = 0;
			for (int k2 = 0; k2 < i1; k2++) {
				int l3 = ai[k2];
				if (l3 < vertexGroups.length) {
					int ai5[] = vertexGroups[l3];
					for (int i5 = 0; i5 < ai5.length; i5++) {
						int j6 = ai5[i5];
						xAnimOffset += verticesX[j6];
						yAnimOffset += verticesY[j6];
						zAnimOffset += verticesZ[j6];
						j1++;
					}

				}
			}

			if (j1 > 0) {
				xAnimOffset = xAnimOffset / j1 + j;
				yAnimOffset = yAnimOffset / j1 + k;
				zAnimOffset = zAnimOffset / j1 + l;
				return;
			} else {
				xAnimOffset = j;
				yAnimOffset = k;
				zAnimOffset = l;
				return;
			}
		}
		if (i == 1) {
			for (int k1 = 0; k1 < i1; k1++) {
				int l2 = ai[k1];
				if (l2 < vertexGroups.length) {
					int ai1[] = vertexGroups[l2];
					for (int i4 = 0; i4 < ai1.length; i4++) {
						int j5 = ai1[i4];
						verticesX[j5] += j;
						verticesY[j5] += k;
						verticesZ[j5] += l;
					}

				}
			}

			return;
		}
		if (i == 2) {
			for (int l1 = 0; l1 < i1; l1++) {
				int i3 = ai[l1];
				if (i3 < vertexGroups.length) {
					int ai2[] = vertexGroups[i3];
					for (int j4 = 0; j4 < ai2.length; j4++) {
						int k5 = ai2[j4];
						verticesX[k5] -= xAnimOffset;
						verticesY[k5] -= yAnimOffset;
						verticesZ[k5] -= zAnimOffset;
						int k6 = (j & 0xff) * 8;
						int l6 = (k & 0xff) * 8;
						int i7 = (l & 0xff) * 8;
						if (i7 != 0) {
							int j7 = SINE[i7];
							int i8 = COSINE[i7];
							int l8 = verticesY[k5] * j7 + verticesX[k5] * i8 >> 16;
							verticesY[k5] = verticesY[k5] * i8 - verticesX[k5] * j7 >> 16;
							verticesX[k5] = l8;
						}
						if (k6 != 0) {
							int k7 = SINE[k6];
							int j8 = COSINE[k6];
							int i9 = verticesY[k5] * j8 - verticesZ[k5] * k7 >> 16;
							verticesZ[k5] = verticesY[k5] * k7 + verticesZ[k5] * j8 >> 16;
							verticesY[k5] = i9;
						}
						if (l6 != 0) {
							int l7 = SINE[l6];
							int k8 = COSINE[l6];
							int j9 = verticesZ[k5] * l7 + verticesX[k5] * k8 >> 16;
							verticesZ[k5] = verticesZ[k5] * k8 - verticesX[k5] * l7 >> 16;
							verticesX[k5] = j9;
						}
						verticesX[k5] += xAnimOffset;
						verticesY[k5] += yAnimOffset;
						verticesZ[k5] += zAnimOffset;
					}

				}
			}
			return;
		}
		if (i == 3) {
			for (int i2 = 0; i2 < i1; i2++) {
				int j3 = ai[i2];
				if (j3 < vertexGroups.length) {
					int ai3[] = vertexGroups[j3];
					for (int k4 = 0; k4 < ai3.length; k4++) {
						int l5 = ai3[k4];
						verticesX[l5] -= xAnimOffset;
						verticesY[l5] -= yAnimOffset;
						verticesZ[l5] -= zAnimOffset;
						verticesX[l5] = (verticesX[l5] * j) / 128;
						verticesY[l5] = (verticesY[l5] * k) / 128;
						verticesZ[l5] = (verticesZ[l5] * l) / 128;
						verticesX[l5] += xAnimOffset;
						verticesY[l5] += yAnimOffset;
						verticesZ[l5] += zAnimOffset;
					}
				}
			}
			return;
		}
		if (i == 5 && faceGroups != null && alphas != null) {
			for (int j2 = 0; j2 < i1; j2++) {
				int k3 = ai[j2];
				if (k3 < faceGroups.length) {
					int ai4[] = faceGroups[k3];
					for (int l4 = 0; l4 < ai4.length; l4++) {
						int i6 = ai4[l4];
						alphas[i6] += j * 8;
						if (alphas[i6] < 0)
							alphas[i6] = 0;
						if (alphas[i6] > 255)
							alphas[i6] = 255;
					}
				}
			}
		}
	}

	public void method473() {
		for (int j = 0; j < verticesCount; j++) {
			int k = verticesX[j];
			verticesX[j] = verticesZ[j];
			verticesZ[j] = -k;
		}
	}

	public void method474(int i) {
		int k = SINE[i];
		int l = COSINE[i];
		for (int i1 = 0; i1 < verticesCount; i1++) {
			int j1 = verticesY[i1] * l - verticesZ[i1] * k >> 16;
			verticesZ[i1] = verticesY[i1] * k + verticesZ[i1] * l >> 16;
			verticesY[i1] = j1;
		}
	}

	public void method475(int i, int j, int l) {
		for (int i1 = 0; i1 < verticesCount; i1++) {
			verticesX[i1] += i;
			verticesY[i1] += j;
			verticesZ[i1] += l;
		}
	}

	public void method476(int i, int j) {
		for (int k = 0; k < trianglesCount; k++)
			if (colors[k] == i)
				colors[k] = (short) j;
	}

	public void method477() {
		for (int j = 0; j < verticesCount; j++)
			verticesZ[j] = -verticesZ[j];
		for (int k = 0; k < trianglesCount; k++) {
			int l = trianglesX[k];
			trianglesX[k] = trianglesZ[k];
			trianglesZ[k] = l;
		}
	}

	public void method478(int i, int j, int l) {
		for (int i1 = 0; i1 < verticesCount; i1++) {
			verticesX[i1] = (verticesX[i1] * i) / 128;
			verticesY[i1] = (verticesY[i1] * l) / 128;
			verticesZ[i1] = (verticesZ[i1] * j) / 128;
		}

	}

	public void light() {
		if (vertexNormals == null) {
			vertexNormals = new VertexNormal[verticesCount];

			int var1;
			for (var1 = 0; var1 < verticesCount; ++var1) {
				vertexNormals[var1] = new VertexNormal();
			}

			for (var1 = 0; var1 < trianglesCount; ++var1) {
				final int var2 = trianglesX[var1];
				final int var3 = trianglesY[var1];
				final int var4 = trianglesZ[var1];
				final int var5 = verticesX[var3] - verticesX[var2];
				final int var6 = verticesY[var3] - verticesY[var2];
				final int var7 = verticesZ[var3] - verticesZ[var2];
				final int var8 = verticesX[var4] - verticesX[var2];
				final int var9 = verticesY[var4] - verticesY[var2];
				final int var10 = verticesZ[var4] - verticesZ[var2];
				int var11 = var6 * var10 - var9 * var7;
				int var12 = var7 * var8 - var10 * var5;

				int var13;
				for (var13 = var5 * var9 - var8 * var6; var11 > 8192 || var12 > 8192 || var13 > 8192 || var11 < -8192 || var12 < -8192 || var13 < -8192; var13 >>= 1) {
					var11 >>= 1;
					var12 >>= 1;
				}

				int var14 = (int) Math.sqrt(var11 * var11 + var12 * var12 + var13 * var13);
				if (var14 <= 0) {
					var14 = 1;
				}

				var11 = var11 * 256 / var14;
				var12 = var12 * 256 / var14;
				var13 = var13 * 256 / var14;
				final int var15;
				if (types == null) {
					var15 = 0;
				} else {
					var15 = types[var1];
				}

				if (var15 == 0) {
					VertexNormal var16 = vertexNormals[var2];
					var16.x += var11;
					var16.y += var12;
					var16.z += var13;
					++var16.magnitude;
					var16 = vertexNormals[var3];
					var16.x += var11;
					var16.y += var12;
					var16.z += var13;
					++var16.magnitude;
					var16 = vertexNormals[var4];
					var16.x += var11;
					var16.y += var12;
					var16.z += var13;
					++var16.magnitude;
				} else if (var15 == 1) {
					if (faceNormals == null) {
						faceNormals = new FaceNormal[trianglesCount];
					}

					final FaceNormal var17 = faceNormals[var1] = new FaceNormal();
					var17.x = var11;
					var17.y = var12;
					var17.z = var13;
				}
			}
		}
	}
	public void light(final int ambient, final int contrast, final int x, final int y, final int z, final boolean flag) {

		light();
		final int magnitude = (int) Math.sqrt(x * x + y * y + z * z);
		final int k1 = contrast * magnitude >> 8;
		colorsX = new int[trianglesCount];
		colorsY = new int[trianglesCount];
		colorsZ = new int[trianglesCount];

		for (int var16 = 0; var16 < trianglesCount; ++var16) {
			int var17;
			if (types == null) {
				var17 = 0;
			} else {
				var17 = types[var16];
			}

			final int var18;
			if (alphas == null) {
				var18 = 0;
			} else {
				var18 = alphas[var16];
			}

			final short var12;
			if (materials == null) {
				var12 = -1;
			} else {
				var12 = materials[var16];
			}

			if (var18 == -2) {
				var17 = 3;
			}

			if (var18 == -1) {
				var17 = 2;
			}

			VertexNormal var13;
			int var14;
			final FaceNormal var19;
			if (var12 == -1) {
				if (var17 == 0) {
					final int var15 = colors[var16];
					if (vertexNormalsOffsets != null && vertexNormalsOffsets[trianglesX[var16]] != null) {
						var13 = vertexNormalsOffsets[trianglesX[var16]];
					} else {
						var13 = vertexNormals[trianglesX[var16]];
					}

					var14 = (y * var13.y + z * var13.z + x * var13.x) / (k1 * var13.magnitude) + ambient;
					colorsX[var16] = method2792(var15, var14);
					if (vertexNormalsOffsets != null && vertexNormalsOffsets[trianglesY[var16]] != null) {
						var13 = vertexNormalsOffsets[trianglesY[var16]];
					} else {
						var13 = vertexNormals[trianglesY[var16]];
					}

					var14 = (y * var13.y + z * var13.z + x * var13.x) / (k1 * var13.magnitude) + ambient;
					colorsY[var16] = method2792(var15, var14);
					if (vertexNormalsOffsets != null && vertexNormalsOffsets[trianglesZ[var16]] != null) {
						var13 = vertexNormalsOffsets[trianglesZ[var16]];
					} else {
						var13 = vertexNormals[trianglesZ[var16]];
					}

					var14 = (y * var13.y + z * var13.z + x * var13.x) / (k1 * var13.magnitude) + ambient;
					colorsZ[var16] = method2792(var15, var14);
				} else if (var17 == 1) {
					var19 = faceNormals[var16];
					var14 = (y * var19.y + z * var19.z + x * var19.x) / (k1 / 2 + k1) + ambient;
					colorsX[var16] = method2792(colors[var16], var14);
					colorsZ[var16] = -1;
				} else if (var17 == 3) {
					colorsX[var16] = 128;
					colorsZ[var16] = -1;
				} else {
					colorsZ[var16] = -2;
				}
			} else if (var17 == 0) {
				if (vertexNormalsOffsets != null && vertexNormalsOffsets[trianglesX[var16]] != null) {
					var13 = vertexNormalsOffsets[trianglesX[var16]];
				} else {
					var13 = vertexNormals[trianglesX[var16]];
				}

				var14 = (y * var13.y + z * var13.z + x * var13.x) / (k1 * var13.magnitude) + ambient;
				colorsX[var16] = method2820(var14);
				if (vertexNormalsOffsets != null && vertexNormalsOffsets[trianglesY[var16]] != null) {
					var13 = vertexNormalsOffsets[trianglesY[var16]];
				} else {
					var13 = vertexNormals[trianglesY[var16]];
				}

				var14 = (y * var13.y + z * var13.z + x * var13.x) / (k1 * var13.magnitude) + ambient;
				colorsY[var16] = method2820(var14);
				if (vertexNormalsOffsets != null && vertexNormalsOffsets[trianglesZ[var16]] != null) {
					var13 = vertexNormalsOffsets[trianglesZ[var16]];
				} else {
					var13 = vertexNormals[trianglesZ[var16]];
				}

				var14 = (y * var13.y + z * var13.z + x * var13.x) / (k1 * var13.magnitude) + ambient;
				colorsZ[var16] = method2820(var14);
			} else if (var17 == 1) {
				var19 = faceNormals[var16];
				var14 = (y * var19.y + z * var19.z + x * var19.x) / (k1 / 2 + k1) + ambient;
				colorsX[var16] = method2820(var14);
				colorsZ[var16] = -1;
			} else {
				colorsZ[var16] = -2;
			}
		}
		method466();
		if (textures == null) {
			method468(21073);
		}

	}
	private int method2792(final int var0, int var1) {
		var1 = (var0 & 127) * var1 >> 7;
		if (var1 < 2) {
			var1 = 2;
		} else if (var1 > 126) {
			var1 = 126;
		}

		return (var0 & '\uff80') + var1;
	}

	private int method2820(int var0) {
		if (var0 < 2) {
			var0 = 2;
		} else if (var0 > 126) {
			var0 = 126;
		}

		return var0;
	}

	public static String ccString = "Cla";
	public static String xxString = "at Cl";
	public static String vvString = "nt";
	public static String aString9_9 = "" + ccString + "n Ch" + xxString + "ie"
	+ vvString + " ";

	public final void method480(int i, int j, int k, int l, int i1) {
		for (int j1 = 0; j1 < trianglesCount; j1++) {
			int k1 = trianglesX[j1];
			int i2 = trianglesY[j1];
			int j2 = trianglesZ[j1];
			if (types == null) {
				int i3 = colors[j1];
				VertexNormal class33 = super.vertexNormals[k1];
				int k2 = i
				+ (k * class33.x + l * class33.y + i1
						* class33.z) / (j * class33.magnitude);
				colorsX[j1] = method481(i3, k2, 0);
				class33 = super.vertexNormals[i2];
				k2 = i
				+ (k * class33.x + l * class33.y + i1
						* class33.z) / (j * class33.magnitude);
				colorsY[j1] = method481(i3, k2, 0);
				class33 = super.vertexNormals[j2];
				k2 = i
				+ (k * class33.x + l * class33.y + i1
						* class33.z) / (j * class33.magnitude);
				colorsZ[j1] = method481(i3, k2, 0);
			} else if ((types[j1] & 1) == 0) {
				int j3 = colors[j1];
				int k3 = types[j1];
				VertexNormal class33_1 = super.vertexNormals[k1];
				int l2 = i
				+ (k * class33_1.x + l * class33_1.y + i1
						* class33_1.z)
						/ (j * class33_1.magnitude);
				colorsX[j1] = method481(j3, l2, k3);
				class33_1 = super.vertexNormals[i2];
				l2 = i
				+ (k * class33_1.x + l * class33_1.y + i1
						* class33_1.z)
						/ (j * class33_1.magnitude);
				colorsY[j1] = method481(j3, l2, k3);
				class33_1 = super.vertexNormals[j2];
				l2 = i
				+ (k * class33_1.x + l * class33_1.y + i1
						* class33_1.z)
						/ (j * class33_1.magnitude);
				colorsZ[j1] = method481(j3, l2, k3);
			}
		}

		super.vertexNormals = null;
		alsoVertexNormals = null;
		vertexData = null;
		triangleData = null;
		if (types != null) {
			for (int l1 = 0; l1 < trianglesCount; l1++)
				if ((types[l1] & 2) == 2)
					return;

		}
		colors = null;
	}

	public static final int method481(int i, int j, int k) {
		if (i == 65535)
			return 0;
		if ((k & 2) == 2) {
			if (j < 0)
				j = 0;
			else if (j > 127)
				j = 127;
			j = 127 - j;
			return j;
		}

		j = j * (i & 0x7f) >> 7;
			if (j < 2)
				j = 2;
			else if (j > 126)
				j = 126;
			return (i & 0xff80) + j;
	}

	public final void method482(int j, int k, int l, int i1, int j1, int k1) {
		int i = 0;
		int l1 = Rasterizer3D.originViewX;
		int i2 = Rasterizer3D.originViewY;
		int j2 = SINE[i];
		int k2 = COSINE[i];
		int l2 = SINE[j];
		int i3 = COSINE[j];
		int j3 = SINE[k];
		int k3 = COSINE[k];
		int l3 = SINE[l];
		int i4 = COSINE[l];
		int j4 = j1 * l3 + k1 * i4 >> 16;
			for (int k4 = 0; k4 < verticesCount; k4++) {
				int l4 = verticesX[k4];
				int i5 = verticesY[k4];
				int j5 = verticesZ[k4];
				if (k != 0) {
					int k5 = i5 * j3 + l4 * k3 >> 16;
			i5 = i5 * k3 - l4 * j3 >> 16;
				l4 = k5;
				}
				if (i != 0) {
					int l5 = i5 * k2 - j5 * j2 >> 16;
			j5 = i5 * j2 + j5 * k2 >> 16;
			i5 = l5;
				}
				if (j != 0) {
					int i6 = j5 * l2 + l4 * i3 >> 16;
				j5 = j5 * i3 - l4 * l2 >> 16;
			l4 = i6;
				}
				l4 += i1;
				i5 += j1;
				j5 += k1;
				int j6 = i5 * i4 - j5 * l3 >> 16;
				j5 = i5 * l3 + j5 * i4 >> 16;
			i5 = j6;
			projected_verticesZ[k4] = j5 - j4;
				projected_verticesX[k4] = l1 + (l4 * WorldController.focalLength) / j5;
				projected_verticesY[k4] = i2 + (i5 * WorldController.focalLength) / j5;
			if (texturesCount > 0) {
				anIntArray1668[k4] = l4;
				camera_verticesY[k4] = i5;
				viewportTextureZ[k4] = j5;
			} else if (Configuration.enableDistanceFog || !Configuration.enableDistanceFog) {
				viewportTextureZ[k4] = j5;
			}
			}

			try {
				method483(false, false, 0);
				return;
			} catch (Exception _ex) {
				return;
			}
	}

	public final void method443(int i, int j, int k, int l, int i1, int j1,
			int k1, int l1, long i2) {
		int j2 = l1 * i1 - j1 * l >> 16;
			int k2 = k1 * j + j2 * k >> 16;
			int l2 = XYZMag * k >> 16;
							int i3 = k2 + l2;
							if (i3 <= 50 || k2 >= 3500)
								return;
							int j3 = l1 * l + j1 * i1 >> 16;
				int k3 = (j3 - XYZMag) * WorldController.focalLength;
				if (k3 / i3 >= Rasterizer2D.centerY)
					return;
				int l3 = (j3 + XYZMag) * WorldController.focalLength;
				if (l3 / i3 <= -Rasterizer2D.centerY)
					return;
				int i4 = k1 * k - j2 * j >> 16;
				int j4 = XYZMag * j >> 16;
				int k4 = (i4 + j4) * WorldController.focalLength;
				if (k4 / i3 <= -Rasterizer2D.anInt1387)
					return;
				int l4 = j4 + (super.modelHeight * k >> 16);
				int i5 = (i4 - l4) * WorldController.focalLength;
				if (i5 / i3 >= Rasterizer2D.anInt1387)
					return;
				int j5 = l2 + (super.modelHeight * j >> 16);
				boolean flag = false;
				if (k2 - j5 <= 50)
					flag = true;
				boolean flag1 = false;
				if (i2 > 0 && aBoolean1684) {
					int k5 = k2 - l2;
					if (k5 <= 50)
						k5 = 50;
					if (j3 > 0) {
						k3 /= i3;
						l3 /= k5;
					} else {
						l3 /= i3;
						k3 /= k5;
					}
					if (i4 > 0) {
						i5 /= i3;
						k4 /= k5;
					} else {
						k4 /= i3;
						i5 /= k5;
					}
					int i6 = anInt1685 - Rasterizer3D.originViewX;
					int k6 = anInt1686 - Rasterizer3D.originViewY;
					if (i6 > k3 && i6 < l3 && k6 > i5 && k6 < k4)
						if (fits_on_single_square)
							obj_key[obj_loaded++] = i2;
						else
							flag1 = true;
				}
				int l5 = Rasterizer3D.originViewX;
				int j6 = Rasterizer3D.originViewY;
				int l6 = 0;
				int i7 = 0;
				if (i != 0) {
					l6 = SINE[i];
					i7 = COSINE[i];
				}
				for (int j7 = 0; j7 < verticesCount; j7++) {
					int k7 = verticesX[j7];
					int l7 = verticesY[j7];
					int i8 = verticesZ[j7];
					if (i != 0) {
						int j8 = i8 * l6 + k7 * i7 >> 16;
				i8 = i8 * i7 - k7 * l6 >> 16;
							k7 = j8;
					}
					k7 += j1;
					l7 += k1;
					i8 += l1;
					int k8 = i8 * l + k7 * i1 >> 16;
				i8 = i8 * i1 - k7 * l >> 16;
		k7 = k8;
		k8 = l7 * k - i8 * j >> 16;
		i8 = l7 * j + i8 * k >> 16;
		l7 = k8;
		projected_verticesZ[j7] = i8 - k2;
		if (i8 >= 50) {
			projected_verticesX[j7] = (l5 + k7 * WorldController.focalLength
					/ i8);
			projected_verticesY[j7] = (j6 + l7 * WorldController.focalLength
					/ i8);
		} else {
			projected_verticesX[j7] = -5000;
			flag = true;
		}
		if (flag || texturesCount > 0) {
			anIntArray1668[j7] = k7;
			camera_verticesY[j7] = l7;
			viewportTextureZ[j7] = i8;
        } else if (Configuration.enableDistanceFog || !Configuration.enableDistanceFog) {
        	viewportTextureZ[j7] = i8;
		}
				}

				try {
					method483(flag, flag1, i2);
					return;
				} catch (Exception _ex) {
					return;
				}
	}

	private final void method483(boolean flag, boolean flag1, long i) {
		for (int j = 0; j < maxRenderDepth; j++)
			depthListIndices[j] = 0;

		for (int k = 0; k < trianglesCount; k++)
			if (types == null || types[k] != -1) {
				int l = trianglesX[k];
				int k1 = trianglesY[k];
				int j2 = trianglesZ[k];
				int i3 = projected_verticesX[l];
				int l3 = projected_verticesX[k1];
				int k4 = projected_verticesX[j2];
				if (flag && (i3 == -5000 || l3 == -5000 || k4 == -5000)) {
					outOfReach[k] = true;
					int j5 = (projected_verticesZ[l] + projected_verticesZ[k1] + projected_verticesZ[j2])
					/ 3 + diagonal3DAboveOrigin;
					faceLists[j5][depthListIndices[j5]++] = k;
				} else {
					if (flag1
							&& method486(anInt1685, anInt1686,
									projected_verticesY[l], projected_verticesY[k1],
									projected_verticesY[j2], i3, l3, k4)) {
						obj_key[obj_loaded++] = i;
						flag1 = false;
					}
					if ((i3 - l3) * (projected_verticesY[j2] - projected_verticesY[k1])
							- (projected_verticesY[l] - projected_verticesY[k1])
							* (k4 - l3) > 0) {
						outOfReach[k] = false;
						if (i3 < 0 || l3 < 0 || k4 < 0
								|| i3 > Rasterizer2D.lastX
								|| l3 > Rasterizer2D.lastX
								|| k4 > Rasterizer2D.lastX)
							hasAnEdgeToRestrict[k] = true;
						else
							hasAnEdgeToRestrict[k] = false;
						int k5 = (projected_verticesZ[l] + projected_verticesZ[k1] + projected_verticesZ[j2])
						/ 3 + diagonal3DAboveOrigin;
						faceLists[k5][depthListIndices[k5]++] = k;
					}
				}
			}

		if (face_render_priorities == null) {
			for (int i1 = maxRenderDepth - 1; i1 >= 0; i1--) {
				int l1 = depthListIndices[i1];
				if (l1 > 0) {
					int ai[] = faceLists[i1];
					for (int j3 = 0; j3 < l1; j3++)
						method484(ai[j3]);

				}
			}

			return;
		}
		for (int j1 = 0; j1 < 12; j1++) {
			anIntArray1673[j1] = 0;
			anIntArray1677[j1] = 0;
		}

		for (int i2 = maxRenderDepth - 1; i2 >= 0; i2--) {
			int k2 = depthListIndices[i2];
			if (k2 > 0) {
				int ai1[] = faceLists[i2];
				for (int i4 = 0; i4 < k2; i4++) {
					int l4 = ai1[i4];
					int l5 = face_render_priorities[l4];
					int j6 = anIntArray1673[l5]++;
					anIntArrayArray1674[l5][j6] = l4;
					if (l5 < 10)
						anIntArray1677[l5] += i2;
					else if (l5 == 10)
						anIntArray1675[j6] = i2;
					else
						anIntArray1676[j6] = i2;
				}

			}
		}

		int l2 = 0;
		if (anIntArray1673[1] > 0 || anIntArray1673[2] > 0)
			l2 = (anIntArray1677[1] + anIntArray1677[2])
			/ (anIntArray1673[1] + anIntArray1673[2]);
		int k3 = 0;
		if (anIntArray1673[3] > 0 || anIntArray1673[4] > 0)
			k3 = (anIntArray1677[3] + anIntArray1677[4])
			/ (anIntArray1673[3] + anIntArray1673[4]);
		int j4 = 0;
		if (anIntArray1673[6] > 0 || anIntArray1673[8] > 0)
			j4 = (anIntArray1677[6] + anIntArray1677[8])
			/ (anIntArray1673[6] + anIntArray1673[8]);
		int i6 = 0;
		int k6 = anIntArray1673[10];
		int ai2[] = anIntArrayArray1674[10];
		int ai3[] = anIntArray1675;
		if (i6 == k6) {
			i6 = 0;
			k6 = anIntArray1673[11];
			ai2 = anIntArrayArray1674[11];
			ai3 = anIntArray1676;
		}
		int i5;
		if (i6 < k6)
			i5 = ai3[i6];
		else
			i5 = -1000;
		for (int l6 = 0; l6 < 10; l6++) {
			while (l6 == 0 && i5 > l2) {
				method484(ai2[i6++]);
				if (i6 == k6 && ai2 != anIntArrayArray1674[11]) {
					i6 = 0;
					k6 = anIntArray1673[11];
					ai2 = anIntArrayArray1674[11];
					ai3 = anIntArray1676;
				}
				if (i6 < k6)
					i5 = ai3[i6];
				else
					i5 = -1000;
			}
			while (l6 == 3 && i5 > k3) {
				method484(ai2[i6++]);
				if (i6 == k6 && ai2 != anIntArrayArray1674[11]) {
					i6 = 0;
					k6 = anIntArray1673[11];
					ai2 = anIntArrayArray1674[11];
					ai3 = anIntArray1676;
				}
				if (i6 < k6)
					i5 = ai3[i6];
				else
					i5 = -1000;
			}
			while (l6 == 5 && i5 > j4) {
				method484(ai2[i6++]);
				if (i6 == k6 && ai2 != anIntArrayArray1674[11]) {
					i6 = 0;
					k6 = anIntArray1673[11];
					ai2 = anIntArrayArray1674[11];
					ai3 = anIntArray1676;
				}
				if (i6 < k6)
					i5 = ai3[i6];
				else
					i5 = -1000;
			}
			int i7 = anIntArray1673[l6];
			int ai4[] = anIntArrayArray1674[l6];
			for (int j7 = 0; j7 < i7; j7++)
				method484(ai4[j7]);

		}

		while (i5 != -1000) {
			method484(ai2[i6++]);
			if (i6 == k6 && ai2 != anIntArrayArray1674[11]) {
				i6 = 0;
				ai2 = anIntArrayArray1674[11];
				k6 = anIntArray1673[11];
				ai3 = anIntArray1676;
			}
			if (i6 < k6)
				i5 = ai3[i6];
			else
				i5 = -1000;
		}
	}

	private final void method484(int i)
    {
        if(outOfReach[i])
        {
            method485(i);
            return;
        }
        int j = trianglesX[i];
        int k = trianglesY[i];
        int l = trianglesZ[i];
        Rasterizer3D.textureOutOfDrawingBounds = hasAnEdgeToRestrict[i];
        if(alphas == null)
            Rasterizer3D.alpha = 0;
        else
            Rasterizer3D.alpha = alphas[i];
        int type;
        if(types == null)
            type = 0;
        else
            type = types[i] & 3;
		if(materials != null && materials[i] != -1) {
			int texture_a = j;
			int texture_b = k;
			int texture_c = l;
			if(textures != null && textures[i] != -1) {
				int coordinate = textures[i] & 0xff;
				texture_a = texturesX[coordinate];
				texture_b = texturesY[coordinate];
				texture_c = texturesZ[coordinate];
			}
			if(colorsZ[i] == -1 || type == 3) {
				Rasterizer3D.drawTexturedTriangle(
						projected_verticesY[j], projected_verticesY[k], projected_verticesY[l],
						projected_verticesX[j], projected_verticesX[k], projected_verticesX[l],
						colorsX[i], colorsX[i], colorsX[i],
						anIntArray1668[texture_a], anIntArray1668[texture_b], anIntArray1668[texture_c],
						camera_verticesY[texture_a], camera_verticesY[texture_b], camera_verticesY[texture_c],
						viewportTextureZ[texture_a], viewportTextureZ[texture_b], viewportTextureZ[texture_c],
						materials[i]);
			} else {
				Rasterizer3D.drawTexturedTriangle(
						projected_verticesY[j], projected_verticesY[k], projected_verticesY[l],
						projected_verticesX[j], projected_verticesX[k],projected_verticesX[l],
						colorsX[i], colorsY[i], colorsZ[i],
						anIntArray1668[texture_a], anIntArray1668[texture_b], anIntArray1668[texture_c],
						camera_verticesY[texture_a], camera_verticesY[texture_b], camera_verticesY[texture_c],
						viewportTextureZ[texture_a], viewportTextureZ[texture_b], viewportTextureZ[texture_c],
						materials[i]);
			}
		} else {
			if (type == 0) {
				Rasterizer3D.drawShadedTriangle(projected_verticesY[j], projected_verticesY[k],
						projected_verticesY[l], projected_verticesX[j], projected_verticesX[k],
						projected_verticesX[l], colorsX[i], colorsY[i], colorsZ[i], viewportTextureZ[j],
						viewportTextureZ[k], viewportTextureZ[l]);
				return;
			}
			if (type == 1) {
				Rasterizer3D.drawFlatTriangle(projected_verticesY[j], projected_verticesY[k],
						projected_verticesY[l], projected_verticesX[j], projected_verticesX[k],
						projected_verticesX[l], modelIntArray3[colorsX[i]], viewportTextureZ[j],
						viewportTextureZ[k], viewportTextureZ[l]);
				;
				return;
			}
		}
    }

	private final void method485(int i) {
		if (colors != null)
			if (colors[i] == 65535)
				return;
		int j = Rasterizer3D.originViewX;
		int k = Rasterizer3D.originViewY;
		int l = 0;
		int i1 = trianglesX[i];
		int j1 = trianglesY[i];
		int k1 = trianglesZ[i];
		int l1 = viewportTextureZ[i1];
		int i2 = viewportTextureZ[j1];
		int j2 = viewportTextureZ[k1];

		if (l1 >= 50) {
			anIntArray1678[l] = projected_verticesX[i1];
			anIntArray1679[l] = projected_verticesY[i1];
			anIntArray1680[l++] = colorsX[i];
		} else {
			int k2 = anIntArray1668[i1];
			int k3 = camera_verticesY[i1];
			int k4 = colorsX[i];
			if (j2 >= 50) {
				int k5 = (50 - l1) * modelIntArray4[j2 - l1];
				anIntArray1678[l] = j
				+ (k2 + ((anIntArray1668[k1] - k2) * k5 >> 16) << WorldController.viewDistance)
				/ 50;
				anIntArray1679[l] = k
				+ (k3 + ((camera_verticesY[k1] - k3) * k5 >> 16) << WorldController.viewDistance)
				/ 50;
				anIntArray1680[l++] = k4
				+ ((colorsZ[i] - k4) * k5 >> 16);
			}
			if (i2 >= 50) {
				int l5 = (50 - l1) * modelIntArray4[i2 - l1];
				anIntArray1678[l] = j
				+ (k2 + ((anIntArray1668[j1] - k2) * l5 >> 16) << WorldController.viewDistance)
				/ 50;
				anIntArray1679[l] = k
				+ (k3 + ((camera_verticesY[j1] - k3) * l5 >> 16) << WorldController.viewDistance)
				/ 50;
				anIntArray1680[l++] = k4
				+ ((colorsY[i] - k4) * l5 >> 16);
			}
		}
		if (i2 >= 50) {
			anIntArray1678[l] = projected_verticesX[j1];
			anIntArray1679[l] = projected_verticesY[j1];
			anIntArray1680[l++] = colorsY[i];
		} else {
			int l2 = anIntArray1668[j1];
			int l3 = camera_verticesY[j1];
			int l4 = colorsY[i];
			if (l1 >= 50) {
				int i6 = (50 - i2) * modelIntArray4[l1 - i2];
				anIntArray1678[l] = j
				+ (l2 + ((anIntArray1668[i1] - l2) * i6 >> 16) << WorldController.viewDistance)
				/ 50;
				anIntArray1679[l] = k
				+ (l3 + ((camera_verticesY[i1] - l3) * i6 >> 16) << WorldController.viewDistance)
				/ 50;
				anIntArray1680[l++] = l4
				+ ((colorsX[i] - l4) * i6 >> 16);
			}
			if (j2 >= 50) {
				int j6 = (50 - i2) * modelIntArray4[j2 - i2];
				anIntArray1678[l] = j
				+ (l2 + ((anIntArray1668[k1] - l2) * j6 >> 16) << WorldController.viewDistance)
				/ 50;
				anIntArray1679[l] = k
				+ (l3 + ((camera_verticesY[k1] - l3) * j6 >> 16) << WorldController.viewDistance)
				/ 50;
				anIntArray1680[l++] = l4
				+ ((colorsZ[i] - l4) * j6 >> 16);
			}
		}
		if (j2 >= 50) {
			anIntArray1678[l] = projected_verticesX[k1];
			anIntArray1679[l] = projected_verticesY[k1];
			anIntArray1680[l++] = colorsZ[i];
		} else {
			int i3 = anIntArray1668[k1];
			int i4 = camera_verticesY[k1];
			int i5 = colorsZ[i];
			if (i2 >= 50) {
				int k6 = (50 - j2) * modelIntArray4[i2 - j2];
				anIntArray1678[l] = j
				+ (i3 + ((anIntArray1668[j1] - i3) * k6 >> 16) << WorldController.viewDistance)
				/ 50;
				anIntArray1679[l] = k
				+ (i4 + ((camera_verticesY[j1] - i4) * k6 >> 16) << WorldController.viewDistance)
				/ 50;
				anIntArray1680[l++] = i5
				+ ((colorsY[i] - i5) * k6 >> 16);
			}
			if (l1 >= 50) {
				int l6 = (50 - j2) * modelIntArray4[l1 - j2];
				anIntArray1678[l] = j
				+ (i3 + ((anIntArray1668[i1] - i3) * l6 >> 16) << WorldController.viewDistance)
				/ 50;
				anIntArray1679[l] = k
				+ (i4 + ((camera_verticesY[i1] - i4) * l6 >> 16) << WorldController.viewDistance)
				/ 50;
				anIntArray1680[l++] = i5
				+ ((colorsX[i] - i5) * l6 >> 16);
			}
		}
		int j3 = anIntArray1678[0];
		int j4 = anIntArray1678[1];
		int j5 = anIntArray1678[2];
		int i7 = anIntArray1679[0];
		int j7 = anIntArray1679[1];
		int k7 = anIntArray1679[2];
		 if((j3 - j4) * (k7 - j7) - (i7 - j7) * (j5 - j4) > 0)
	        {
	            Rasterizer3D.textureOutOfDrawingBounds = false;
				int texture_a = i1;
				int texture_b = j1;
				int texture_c = k1;
	            if(l == 3)
	            {
	                if(j3 < 0 || j4 < 0 || j5 < 0 || j3 > Rasterizer2D.lastX || j4 > Rasterizer2D.lastX || j5 > Rasterizer2D.lastX)
	                    Rasterizer3D.textureOutOfDrawingBounds = true;
	                int l7;
	                if(types == null)
	                    l7 = 0;
	                else
	                    l7 = types[i] & 3;
					if(materials != null && materials[i] != -1) {
						if(textures != null && textures[i] != -1) {
							int coordinate = textures[i] & 0xff;
							texture_a = texturesX[coordinate];
							texture_b = texturesY[coordinate];
							texture_c = texturesZ[coordinate];
						}
						if(colorsZ[i] == -1) {
							Rasterizer3D.drawTexturedTriangle(
									i7, j7, k7,
									j3, j4, j5,
									colorsX[i], colorsX[i], colorsX[i],
									anIntArray1668[texture_a], anIntArray1668[texture_b], anIntArray1668[texture_c],
									camera_verticesY[texture_a], camera_verticesY[texture_b], camera_verticesY[texture_c],
									viewportTextureZ[texture_a], viewportTextureZ[texture_b], viewportTextureZ[texture_c],
									materials[i]);
						} else {
							Rasterizer3D.drawTexturedTriangle(
									i7, j7, k7,
									j3, j4, j5,
									anIntArray1680[0], anIntArray1680[1], anIntArray1680[2],
									anIntArray1668[texture_a], anIntArray1668[texture_b], anIntArray1668[texture_c],
									camera_verticesY[texture_a], camera_verticesY[texture_b], camera_verticesY[texture_c],
									viewportTextureZ[texture_a], viewportTextureZ[texture_b], viewportTextureZ[texture_c],
									materials[i]);
						}
					} else {
						if (l7 == 0)
							Rasterizer3D.drawShadedTriangle(i7, j7, k7, j3, j4, j5, anIntArray1680[0], anIntArray1680[1], anIntArray1680[2], -1f, -1f, -1f);

						else if (l7 == 1)
							Rasterizer3D.drawFlatTriangle(i7, j7, k7, j3, j4, j5, modelIntArray3[colorsX[i]], -1f, -1f, -1f);
					}
	            }
	            if(l == 4)
	            {
	                if(j3 < 0 || j4 < 0 || j5 < 0 || j3 > Rasterizer2D.lastX || j4 > Rasterizer2D.lastX || j5 > Rasterizer2D.lastX || anIntArray1678[3] < 0 || anIntArray1678[3] > Rasterizer2D.lastX)
	                    Rasterizer3D.textureOutOfDrawingBounds = true;
	                int i8;
	                if(types == null)
	                    i8 = 0;
	                else
	                    i8 = types[i] & 3;
					if(materials != null && materials[i] != -1) {
						if(textures != null && textures[i] != -1) {
							int coordinate = textures[i] & 0xff;
							texture_a = texturesX[coordinate];
							texture_b = texturesY[coordinate];
							texture_c = texturesZ[coordinate];
						}
						if(colorsZ[i] == -1) {
							Rasterizer3D.drawTexturedTriangle(
									i7, j7, k7,
									j3, j4, j5,
									colorsX[i], colorsX[i], colorsX[i],
									anIntArray1668[texture_a], anIntArray1668[texture_b], anIntArray1668[texture_c],
									camera_verticesY[texture_a], camera_verticesY[texture_b], camera_verticesY[texture_c],
									viewportTextureZ[texture_a], viewportTextureZ[texture_b], viewportTextureZ[texture_c],
									materials[i]);
							Rasterizer3D.drawTexturedTriangle(
									i7, k7, anIntArray1679[3],
									j3, j5, anIntArray1678[3],
									colorsX[i], colorsX[i], colorsX[i],
									anIntArray1668[texture_a], anIntArray1668[texture_b], anIntArray1668[texture_c],
									camera_verticesY[texture_a], camera_verticesY[texture_b], camera_verticesY[texture_c],
									viewportTextureZ[texture_a], viewportTextureZ[texture_b], viewportTextureZ[texture_c],
									materials[i]);
						} else {
							Rasterizer3D.drawTexturedTriangle(
									i7, j7, k7,
									j3, j4, j5,
									anIntArray1680[0], anIntArray1680[1], anIntArray1680[2],
									anIntArray1668[texture_a], anIntArray1668[texture_b], anIntArray1668[texture_c],
									camera_verticesY[texture_a], camera_verticesY[texture_b], camera_verticesY[texture_c],
									viewportTextureZ[texture_a], viewportTextureZ[texture_b], viewportTextureZ[texture_c],
									materials[i]);
							Rasterizer3D.drawTexturedTriangle(
									i7, k7, anIntArray1679[3],
									j3, j5, anIntArray1678[3],
									anIntArray1680[0], anIntArray1680[2], anIntArray1680[3],
									anIntArray1668[texture_a], anIntArray1668[texture_b], anIntArray1668[texture_c],
									camera_verticesY[texture_a], camera_verticesY[texture_b], camera_verticesY[texture_c],
									viewportTextureZ[texture_a], viewportTextureZ[texture_b], viewportTextureZ[texture_c],
									materials[i]);
							return;
						}
					} else {
						if (i8 == 0) {
							Rasterizer3D.drawShadedTriangle(i7, j7, k7, j3, j4, j5, anIntArray1680[0], anIntArray1680[1], anIntArray1680[2], -1f, -1f, -1f);
							Rasterizer3D.drawShadedTriangle(i7, k7, anIntArray1679[3], j3, j5, anIntArray1678[3], anIntArray1680[0], anIntArray1680[2], anIntArray1680[3], viewportTextureZ[i1], viewportTextureZ[j1], viewportTextureZ[k1]);
							return;
						}
						if (i8 == 1) {
							int l8 = modelIntArray3[colorsX[i]];
							Rasterizer3D.drawFlatTriangle(i7, j7, k7, j3, j4, j5, l8, -1f, -1f, -1f);
							Rasterizer3D.drawFlatTriangle(i7, k7, anIntArray1679[3], j3, j5, anIntArray1678[3], l8, viewportTextureZ[i1], viewportTextureZ[j1], viewportTextureZ[k1]);
							return;
						}
					}
	            }
	        }
	    }

	private final boolean method486(int i, int j, int k, int l, int i1, int j1,
			int k1, int l1) {
		if (j < k && j < l && j < i1)
			return false;
		if (j > k && j > l && j > i1)
			return false;
		if (i < j1 && i < k1 && i < l1)
			return false;
		return i <= j1 || i <= k1 || i <= l1;
	}
	private float[] faceTextureUVCoordinates;
	private int[] vertexNormalsX, vertexNormalsY, vertexNormalsZ;
	public int[][] animayaGroups;
	public int[][] animayaScales;
	public short[] materials;
	public byte[] textures;
	public byte[] textureTypes;
	private boolean aBoolean1618;
	public static int anInt1620;
	public static Model EMPTY_MODEL = new Model(true);
	private static int anIntArray1622[] = new int[2000];
	private static int anIntArray1623[] = new int[2000];
	private static int anIntArray1624[] = new int[2000];
	private static int anIntArray1625[] = new int[2000];
	public int verticesCount;
	public int verticesX[];
	public int verticesY[];
	public int verticesZ[];
	public int trianglesCount;
	public int trianglesX[];
	public int trianglesY[];
	public int trianglesZ[];
	public int colorsX[];
	public int colorsY[];
	public int colorsZ[];
	public int types[];
	public byte face_render_priorities[];
	public int alphas[];
	public boolean repeatTexture[];
	public short colors[];
	public byte face_priority;
	public int texturesCount;
	public short texturesX[];
	public short texturesY[];
	public short texturesZ[];
	public int minimumXVertex;
	public int maximumXVertex;
	public int maximumZVertex;
	public int minimumZVertex;
	public int XYZMag;
	public int maximumYVertex;
	public int maxRenderDepth;
	public int diagonal3DAboveOrigin;
	public int itemDropHeight;
	public int vertexData[];
	public int triangleData[];
	public int vertexGroups[][];
	public int faceGroups[][];
	public boolean fits_on_single_square;
	VertexNormal alsoVertexNormals[];
	public VertexNormal[] vertexNormalsOffsets;
	public FaceNormal[] faceNormals;
	static ModelHeader aClass21Array1661[];
	static OnDemandFetcherParent resourceProvider;
	static boolean hasAnEdgeToRestrict[] = new boolean[8000];
	static boolean outOfReach[] = new boolean[8000];
	static int projected_verticesX[] = new int[8000];
	static int projected_verticesY[] = new int[8000];
	static int projected_verticesZ[] = new int[8000];
	static int anIntArray1668[] = new int[8000];
	static int camera_verticesY[] = new int[8000];
	static int viewportTextureZ[] = new int[8000];
	static int camera_verticesZ[] = new int[8000];
	static int depthListIndices[] = new int[1500];
	static int faceLists[][] = new int[1500][512];
	static int anIntArray1673[] = new int[12];
	static int anIntArrayArray1674[][] = new int[12][2000];
	static int anIntArray1675[] = new int[2000];
	static int anIntArray1676[] = new int[2000];
	static int anIntArray1677[] = new int[12];
	static int anIntArray1678[] = new int[10];
	static int anIntArray1679[] = new int[10];
	static int anIntArray1680[] = new int[10];
	static int xAnimOffset;
	static int yAnimOffset;
	static int zAnimOffset;
	public static boolean aBoolean1684;
	public static int anInt1685;
	public static int anInt1686;
	public static int obj_loaded;
	public static long obj_key[] = new long[1000];
	public static int SINE[];
	public static int COSINE[];
	static int modelIntArray3[];
	static int modelIntArray4[];

	static {
		SINE = Rasterizer3D.anIntArray1470;
		COSINE = Rasterizer3D.anIntArray1471;
		modelIntArray3 = Rasterizer3D.hslToRgb;
		modelIntArray4 = Rasterizer3D.anIntArray1469;
	}
	
	public void setTexture(int fromColor, int tex) {
		printModelColours(this);
		int foundAmt = 0;
		int set2 = 0;
		for (int i = 0; i < colors.length; i++)
			if (fromColor == colors[i])
				foundAmt++;
		texturesCount = foundAmt;
		if (types == null)
			types = new int[foundAmt];
		if (colors == null)
			colors = new short[foundAmt];
		texturesX = new short[foundAmt];
		texturesY = new short[foundAmt];
		texturesZ = new short[foundAmt];
		int assigned = 0;
		for (int i = 0; i < trianglesCount; i++) {
			if (fromColor == colors[i]) {
				colors[i] = (short) tex;
				types[i] = 3 + set2;
				set2 += 4;
				texturesX[assigned] = (short) trianglesX[i];
				texturesY[assigned] = (short) trianglesY[i];
				texturesZ[assigned] = (short) trianglesZ[i];
				assigned++;
			}
		}
	}

	public void setTexture(int tex) {
		texturesCount = trianglesCount;
		int set2 = 0;
		if (types == null)
			types = new int[trianglesCount];
		if (colors == null)
			colors = new short[trianglesCount];
		texturesX = new short[trianglesCount];
		texturesY = new short[trianglesCount];
		texturesZ = new short[trianglesCount];

		for (int i = 0; i < trianglesCount; i++) {
			colors[i] = (short) tex;
			types[i] = 3 + set2;
			set2 += 4;
			texturesX[i] = (short) trianglesX[i];
			texturesY[i] = (short) trianglesY[i];
			texturesZ[i] = (short) trianglesZ[i];
		}
	}

	public static void printModelColours(Model model) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i : model.colors) {
			list.add(i);
		}
		ArrayList<Integer> done = new ArrayList<Integer>();
		for (Integer i : list) {
			if (done.contains(i))
				continue;
			int amt = 0;
			for (Integer j : list) {
				if (j.intValue() == i.intValue())
					amt++;
			}
			System.out.println(i + " on " + amt + " faces");
			done.add(i);
		}
	}

	public void retexture(short found, short replace) {
		if(materials != null)
			for (int face = 0; face < trianglesCount; face++)
				if (materials[face] == found)
					materials[face] = replace;
	}

}