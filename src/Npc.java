// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public final class Npc extends Entity
{

	private Model method450()
	{
		if(super.animation >= 0 && super.anInt1529 == 0)
		{
			int k = AnimationDefinition.anims[super.animation].primaryFrames[super.anInt1527];
			int i1 = -1;
			if(super.queued_animation_id >= 0 && super.queued_animation_id != super.idle_animation_id)
				i1 = AnimationDefinition.anims[super.queued_animation_id].primaryFrames[super.anInt1518];
			return desc.method164(i1, k, AnimationDefinition.anims[super.animation].anIntArray357);
		}
		int l = -1;
		if(super.queued_animation_id >= 0)
			l = AnimationDefinition.anims[super.queued_animation_id].primaryFrames[super.anInt1518];
		return desc.method164(-1, l, null);
	}

	public Model getRotatedModel()
	{
		if(desc == null)
			return null;
		Model model = method450();
		if(model == null)
			return null;
		super.height = model.modelHeight;
		if(super.graphic_id != -1 && super.anInt1521 != -1)
		{
			GraphicsDefinition spotAnim = GraphicsDefinition.cache[super.graphic_id];
			Model model_1 = spotAnim.getModel();
			if(model_1 != null)
			{
				int j = spotAnim.aAnimation_407.primaryFrames[super.anInt1521];
				Model model_2 = new Model(true, SequenceFrame.method532(j), false, model_1);
				model_2.method475(0, -super.anInt1524, 0);
				model_2.method469();
				model_2.method470(j);
				model_2.faceGroups = null;
				model_2.vertexGroups = null;
				if(spotAnim.anInt410 != 128 || spotAnim.anInt411 != 128)
					model_2.method478(spotAnim.anInt410, spotAnim.anInt410, spotAnim.anInt411);
				model_2.method479(64 + spotAnim.anInt413, 850 + spotAnim.anInt414, -30, -50, -30, true);
				Model aModel[] = {
						model, model_2
				};
				model = new Model(aModel);
			}
		}
		if(desc.occupied_tiles == 1)
			model.fits_on_single_square = true;
		return model;
	}

	public boolean isVisible()
	{
		return desc != null;
	}

	Npc()
	{
	}

	public EntityDef desc;
}
