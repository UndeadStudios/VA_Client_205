public final class Player extends Entity {

	public Model getRotatedModel() {
		if (!visible)
			return null;
		Model model = method452();
		if (model == null)
			return null;
		super.height = model.modelHeight;
		model.fits_on_single_square = true;
		if (reference_pose)
			return model;
		if (super.graphic_id != -1 && super.anInt1521 != -1) {
			GraphicsDefinition spotAnim = GraphicsDefinition.cache[super.graphic_id];
			Model model_2 = spotAnim.getModel();
			if (model_2 != null) {
				Model model_3 = new Model(true, SequenceFrame.method532(super.anInt1521), false, model_2);
				model_3.method475(0, -super.anInt1524, 0);
				model_3.method469();
				model_3.method470(spotAnim.aAnimation_407.primaryFrames[super.anInt1521]);
				model_3.faceGroups = null;
				model_3.vertexGroups = null;
				if (spotAnim.anInt410 != 128 || spotAnim.anInt411 != 128)
					model_3.method478(spotAnim.anInt410, spotAnim.anInt410, spotAnim.anInt411);
				model_3.light(84 + spotAnim.anInt413, 1550 + spotAnim.anInt414, -50, -110, -50, true);
				Model aclass30_sub2_sub4_sub6_1s[] = { model, model_3 };
				model = new Model(aclass30_sub2_sub4_sub6_1s);
			} else {
				return null;
			}
		}
		if (transformed_model != null) {
			if (Client.game_tick >= transform_duration)
				transformed_model = null;
			if (Client.game_tick >= transform_delay && Client.game_tick < transform_duration) {
				Model model_1 = transformed_model;
				model_1.method475(anInt1711 - super.world_x, anInt1712 - height, anInt1713 - super.world_y);
				if (super.turnDirection == 512) {
					model_1.method473();
					model_1.method473();
					model_1.method473();
				} else if (super.turnDirection == 1024) {
					model_1.method473();
					model_1.method473();
				} else if (super.turnDirection == 1536)
					model_1.method473();
				Model aclass30_sub2_sub4_sub6s[] = { model, model_1 };
				model = new Model(aclass30_sub2_sub4_sub6s);
				if (super.turnDirection == 512)
					model_1.method473();
				else if (super.turnDirection == 1024) {
					model_1.method473();
					model_1.method473();
				} else if (super.turnDirection == 1536) {
					model_1.method473();
					model_1.method473();
					model_1.method473();
				}
				model_1.method475(super.world_x - anInt1711, height - anInt1712, super.world_y - anInt1713);
			}
		}
		model.fits_on_single_square = true;
		return model;
	}

	public void updatePlayer(Buffer stream) {
		stream.currentPosition = 0;
		anInt1702 = stream.readUnsignedByte();
		headIcon = stream.readUnsignedByte();
		skullIcon = stream.readUnsignedByte();
		hintIcon = stream.readUnsignedByte();
		desc = null;
		team = 0;
		for (int j = 0; j < 12; j++) {
			int k = stream.readUnsignedByte();
			if (k == 0) {
				equipment[j] = 0;
				continue;
			}
			int i1 = stream.readUnsignedByte();
			equipment[j] = (k << 8) + i1;
			if (j == 0 && equipment[0] == 65535) {
				desc = EntityDef.forID(stream.readUnsignedShort());
				break;
			}
			if (equipment[j] >= 512 && equipment[j] - 512 < ItemDefinition.totalItems) {
				int l1 = ItemDefinition.forID(equipment[j] - 512).team;
				if (l1 != 0)
					team = l1;
			}
		}

		for (int l = 0; l < 5; l++) {
			int j1 = stream.readUnsignedByte();
			if (j1 < 0 || j1 >= Client.anIntArrayArray1003[l].length)
				j1 = 0;
			anIntArray1700[l] = j1;
		}

		super.idle_animation_id = stream.readUnsignedShort();
		if (super.idle_animation_id == 65535)
			super.idle_animation_id = -1;
		super.standing_turn_animation_id = stream.readUnsignedShort();
		if (super.standing_turn_animation_id == 65535)
			super.standing_turn_animation_id = -1;
		super.walk_animation_id = stream.readUnsignedShort();
		if (super.walk_animation_id == 65535)
			super.walk_animation_id = -1;
		super.turn_around_animation_id = stream.readUnsignedShort();
		if (super.turn_around_animation_id == 65535)
			super.turn_around_animation_id = -1;
		super.pivot_right_animation_id = stream.readUnsignedShort();
		if (super.pivot_right_animation_id == 65535)
			super.pivot_right_animation_id = -1;
		super.pivot_left_animation_id = stream.readUnsignedShort();
		if (super.pivot_left_animation_id == 65535)
			super.pivot_left_animation_id = -1;
		super.running_animation_id = stream.readUnsignedShort();
		if (super.running_animation_id == 65535)
			super.running_animation_id = -1;
		username = TextClass.fixName(stream.readString());

		titleColor = stream.readString();
		title = TextClass.fixName(stream.readString());
		titlePrefix = stream.readUnsignedByte() == 1;

		combatLevel = stream.readUnsignedByte();
		// skill = stream.readUnsignedWord();
		visible = true;
		aLong1718 = 0L;
		for (int k1 = 0; k1 < 12; k1++) {
			aLong1718 <<= 4;
			if (equipment[k1] >= 256)
				aLong1718 += equipment[k1] - 256;
		}

		if (equipment[0] >= 256)
			aLong1718 += equipment[0] - 256 >> 4;
		if (equipment[1] >= 256)
			aLong1718 += equipment[1] - 256 >> 8;
		for (int i2 = 0; i2 < 5; i2++) {
			aLong1718 <<= 3;
			aLong1718 += anIntArray1700[i2];
		}

		aLong1718 <<= 1;
		aLong1718 += anInt1702;
	}

	public Model method452() {
		int nextAnim = -1;
		int currCycle = -1;
		int nextCycle = -1;
		long l = aLong1718;
		int k = -1;
		int i1 = -1;
		int j1 = -1;
		int k1 = -1;
		if (desc != null) {
			if (super.animation >= 0 && super.anInt1529 == 0) {
				final AnimationDefinition seq = AnimationDefinition.anims[super.animation];
				k = seq.primaryFrames[super.anInt1527];
				if (Configuration.enableTweening && super.nextAnimFrame != -1) {
					nextAnim = seq.primaryFrames[super.anInt1527];
					currCycle = seq.durations[super.anInt1527];
					nextCycle = super.anInt1528;
				}
			} else if (super.queued_animation_id >= 0) {
				final AnimationDefinition seq = AnimationDefinition.anims[super.queued_animation_id];
				k = seq.primaryFrames[super.anInt1518];
				if (Configuration.enableTweening && super.nextIdleAnimFrame != -1) {
					nextAnim = seq.primaryFrames[super.nextIdleAnimFrame];
					currCycle = seq.durations[super.anInt1518];
					nextCycle = super.anInt1519;
				}
			}
			return desc.method164(-1, k, nextAnim, currCycle, nextCycle, null);
		}
		if (super.animation >= 0 && super.anInt1529 == 0) {
			final AnimationDefinition animation = AnimationDefinition.anims[super.animation];
			k = animation.primaryFrames[super.anInt1527];
			if (Configuration.enableTweening && super.nextAnimFrame != -1) {
				nextAnim = animation.primaryFrames[super.nextAnimFrame];
				currCycle = animation.durations[super.anInt1527];
				nextCycle = super.anInt1528;
			}
			if (super.queued_animation_id >= 0 && super.queued_animation_id != super.idle_animation_id) {
				i1 = AnimationDefinition.anims[super.queued_animation_id].primaryFrames[super.anInt1518];
			}
			if (animation.anInt360 >= 0) {
				j1 = animation.anInt360;
				l += j1 - equipment[5] << 40;
			}
			if (animation.anInt361 >= 0) {
				k1 = animation.anInt361;
				l += k1 - equipment[3] << 48;
			}
		} else if (super.queued_animation_id >= 0) {
			AnimationDefinition seq = AnimationDefinition.anims[super.queued_animation_id];
			k = seq.primaryFrames[super.anInt1518];
			if (Configuration.enableTweening && super.nextIdleAnimFrame != -1) {
				nextAnim = seq.primaryFrames[super.nextIdleAnimFrame];
				currCycle = seq.durations[super.anInt1518];
				nextCycle = super.anInt1519;
			}
		}
		Model model_1 = (Model) mruNodes.insertFromCache(l);
		if (model_1 == null) {
			boolean flag = false;
			for (int i2 = 0; i2 < 12; i2++) {
				int k2 = equipment[i2];
				if (k1 >= 0 && i2 == 3)
					k2 = k1;
				if (j1 >= 0 && i2 == 5)
					k2 = j1;
				if (k2 >= 256 && k2 < 512 && !IdentityKit.cache[k2 - 256].method537())
					flag = true;
				if (k2 >= 512 && !ItemDefinition.forID(k2 - 512).method195(anInt1702))
					flag = true;
			}

			if (flag) {
				if (aLong1697 != -1L)
					model_1 = (Model) mruNodes.insertFromCache(aLong1697);
				if (model_1 == null)
					return null;
			}
		}
		if (model_1 == null) {
			Model aclass30_sub2_sub4_sub6s[] = new Model[12];
			int j2 = 0;
			for (int l2 = 0; l2 < 12; l2++) {
				int i3 = equipment[l2];
				if (k1 >= 0 && l2 == 3)
					i3 = k1;
				if (j1 >= 0 && l2 == 5)
					i3 = j1;
				if (i3 >= 256 && i3 < 512) {
					Model model_3 = IdentityKit.cache[i3 - 256].method538();
					if (model_3 != null)
						aclass30_sub2_sub4_sub6s[j2++] = model_3;
				}
				if (i3 >= 512) {
					Model model_4 = ItemDefinition.forID(i3 - 512).method196(anInt1702);
					if (model_4 != null)
						aclass30_sub2_sub4_sub6s[j2++] = model_4;
				}
			}

			model_1 = new Model(j2, aclass30_sub2_sub4_sub6s);
			for (int j3 = 0; j3 < 5; j3++)
				if (anIntArray1700[j3] != 0) {
					model_1.method476(Client.anIntArrayArray1003[j3][0], Client.anIntArrayArray1003[j3][anIntArray1700[j3]]);
					if (j3 == 1)
						model_1.method476(Client.anIntArray1204[0], Client.anIntArray1204[anIntArray1700[j3]]);
				}

			model_1.method469();
			model_1.method478(132, 132, 132);
			model_1.light(84, 1000, -90, -580, -90, true);
			mruNodes.removeFromCache(model_1, l);
			aLong1697 = l;
		}
		if (reference_pose)
			return model_1;
		Model model_2 = Model.EMPTY_MODEL;
		model_2.method464(model_1, SequenceFrame.method532(k) & SequenceFrame.method532(i1));
		if (k != -1 && i1 != -1) {
			model_2.method471(AnimationDefinition.anims[super.animation].anIntArray357, i1, k);
		} else if (k != -1) {
			model_2.interpolateFrames(k, nextAnim, nextCycle, currCycle);
		}
		model_2.method466();
		model_2.faceGroups = null;
		model_2.vertexGroups = null;
		return model_2;
	}

	public boolean isVisible() {
		return visible;
	}

	public int privelage;

	public Model method453() {
		if (!visible)
			return null;
		if (desc != null)
			return desc.method160();
		boolean flag = false;
		for (int i = 0; i < 12; i++) {
			int j = equipment[i];
			if (j >= 256 && j < 512 && !IdentityKit.cache[j - 256].method539())
				flag = true;
			if (j >= 512 && !ItemDefinition.forID(j - 512).method192(anInt1702))
				flag = true;
		}

		if (flag)
			return null;
		Model aclass30_sub2_sub4_sub6s[] = new Model[12];
		int k = 0;
		for (int l = 0; l < 12; l++) {
			int i1 = equipment[l];
			if (i1 >= 256 && i1 < 512) {
				Model model_1 = IdentityKit.cache[i1 - 256].method540();
				if (model_1 != null)
					aclass30_sub2_sub4_sub6s[k++] = model_1;
			}
			if (i1 >= 512) {
				Model model_2 = ItemDefinition.forID(i1 - 512).method194(anInt1702);
				if (model_2 != null)
					aclass30_sub2_sub4_sub6s[k++] = model_2;
			}
		}

		Model model = new Model(k, aclass30_sub2_sub4_sub6s);
		for (int j1 = 0; j1 < 5; j1++)
			if (anIntArray1700[j1] != 0) {
				model.method476(Client.anIntArrayArray1003[j1][0], Client.anIntArrayArray1003[j1][anIntArray1700[j1]]);
				if (j1 == 1)
					model.method476(Client.anIntArray1204[0], Client.anIntArray1204[anIntArray1700[j1]]);
			}

		return model;
	}

	Player() {
		aLong1697 = -1L;
		reference_pose = false;
		anIntArray1700 = new int[5];
		visible = false;
		equipment = new int[12];
	}

	public String title, titleColor;
	public boolean titlePrefix;
	private long aLong1697;
	public EntityDef desc;
	boolean reference_pose;
	final int[] anIntArray1700;
	public int team;
	int anInt1702;
	public String username;
	static MRUNodes mruNodes = new MRUNodes(260);
	public int combatLevel;
	public int headIcon;
	public int skullIcon;
	public int hintIcon;
	public int transform_delay;
	int transform_duration;
	int height;
	boolean visible;
	int anInt1711;
	int anInt1712;
	int anInt1713;
	Model transformed_model;
	public final int[] equipment;
	private long aLong1718;
	public int transform_width;
	public int transform_height;
	public int transform_width_offset;
	public int transform_height_offset;
	int skill;

}
